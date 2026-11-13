/* ==========================================================================
   BibleVerse PWA - Nube y Copia de Seguridad (Firebase Auth + Cloud Firestore)
   Sincronización 100% compatible con BibleVerse Android (proyecto bibleverse-app-46983)
   ========================================================================== */
(function () {
  "use strict";

  const FIREBASE_CONFIG = {
    apiKey: "AIzaSyB9cUYSvnayovINxSXlzIqCOHHJFTef2a4",
    authDomain: "bibleverse-app-46983.firebaseapp.com",
    projectId: "bibleverse-app-46983",
    storageBucket: "bibleverse-app-46983.firebasestorage.app",
    messagingSenderId: "233274499940"
  };

  const META_STORAGE_KEY = "bv_cloud_sync_meta";
  const COLLECTION_USERS = "users";
  const COLLECTION_VERSES = "verses";
  const COLLECTION_METADATA = "metadata";

  let auth = null;
  let db = null;
  let currentUser = null;
  let syncState = {
    status: "idle", // "idle" | "loading" | "success" | "error"
    message: "",
    backedUpCount: null,
    lastSyncTimestamp: 0
  };
  let callbacks = null;
  let initialized = false;
  let autoSyncTimer = null;
  let isSyncing = false;
  let isApplyingRemoteData = false;

  function loadLocalMeta() {
    try {
      return JSON.parse(localStorage.getItem(META_STORAGE_KEY) || "{}");
    } catch {
      return {};
    }
  }

  function saveLocalMeta(patch) {
    try {
      const merged = { ...loadLocalMeta(), ...patch };
      localStorage.setItem(META_STORAGE_KEY, JSON.stringify(merged));
    } catch {
      // Ignore storage quota errors
    }
  }

  function makeVerseDocId(v) {
    if (v.isCustom) return `custom_${v.id}`;
    return String(v.reference || "")
      .replace(/\//g, "_")
      .replace(/ /g, "_")
      .trim();
  }

  function getDocIdSafeFallback(v, idx) {
    const id = makeVerseDocId(v);
    return id || `verse_${v.id || idx}`;
  }

  function setStatus(status, message, extra = {}) {
    syncState = {
      ...syncState,
      status,
      message,
      ...extra
    };
    if (typeof extra.backedUpCount === "number" || extra.lastSyncTimestamp) {
      saveLocalMeta({
        backedUpCount: syncState.backedUpCount,
        lastSyncTimestamp: syncState.lastSyncTimestamp,
        lastMessage: status === "success" ? message : loadLocalMeta().lastMessage
      });
    }
    renderCloudSyncUI();
    callbacks?.onSyncStateChange?.(syncState, currentUser);
  }

  function formatFirebaseAuthError(err) {
    const code = err?.code || "";
    const msg = err?.message || "";
    if (code === "auth/popup-closed-by-user" || code === "auth/cancelled-popup-request") {
      return "Inicio de sesión con Google cancelado.";
    }
    if (code === "auth/unauthorized-domain") {
      const host = window.location.hostname || "este dominio";
      return `El dominio "${host}" aún no está autorizado en Firebase Console > Authentication > Settings > Dominios autorizados.`;
    }
    if (code === "auth/operation-not-allowed") {
      return "El método de inicio de sesión no está habilitado en Firebase Console > Authentication > Sign-in method.";
    }
    if (code === "auth/network-request-failed") {
      return "Sin conexión a internet. Verifica tu red e inténtalo nuevamente.";
    }
    if (code === "auth/invalid-credential" || code === "auth/wrong-password") {
      return "Correo o contraseña incorrectos.";
    }
    if (code === "auth/weak-password") {
      return "La contraseña debe tener al menos 6 caracteres.";
    }
    if (code === "auth/email-already-in-use") {
      return "Este correo ya está registrado con otra contraseña.";
    }
    if (code === "auth/api-key-not-valid.-please-pass-a-valid-api-key.") {
      return "La clave API de Firebase requiere habilitar acceso Web en Firebase Console.";
    }
    return msg || "Error al conectar con la cuenta.";
  }

  function formatFirestoreError(err) {
    const code = err?.code || "";
    if (code === "permission-denied") {
      return "Permiso denegado en Firestore. Revisa las Reglas de Seguridad en Firebase Console.";
    }
    if (code === "unavailable") {
      return "Servicio de Firestore no disponible. Revisa tu conexión a internet.";
    }
    return err?.message || "Error al sincronizar con la nube.";
  }

  async function fetchCloudSyncMetadata(uid) {
    if (!db || !uid) return;
    try {
      const metaSnap = await db
        .collection(COLLECTION_USERS)
        .doc(uid)
        .collection(COLLECTION_METADATA)
        .doc("sync_info")
        .get();
      if (metaSnap.exists) {
        const data = metaSnap.data() || {};
        const count = typeof data.totalItems === "number" ? data.totalItems : null;
        const ts = typeof data.lastSyncTimestamp === "number" ? data.lastSyncTimestamp : 0;
        if (count !== null) {
          setStatus("success", `${count} elementos respaldados en la nube.`, {
            backedUpCount: count,
            lastSyncTimestamp: ts
          });
        }
      }
    } catch {
      // Non-fatal: metadata read might fail offline or before first sync
    }
  }

  function renderCloudSyncUI() {
    const statusBanner = document.getElementById("cloud-sync-status-banner");
    const statusText = document.getElementById("cloud-sync-status-text");
    const signedOutView = document.getElementById("cloud-signed-out-view");
    const signedInView = document.getElementById("cloud-signed-in-view");
    const userNameEl = document.getElementById("cloud-user-name");
    const userEmailEl = document.getElementById("cloud-user-email");
    const avatarImg = document.getElementById("cloud-user-avatar-img");
    const avatarSvg = document.getElementById("cloud-user-avatar-svg");
    const uploadBtn = document.getElementById("btn-cloud-upload");
    const downloadBtn = document.getElementById("btn-cloud-download");
    const googleBtn = document.getElementById("btn-google-signin");

    if (!statusBanner || !signedOutView || !signedInView) return;

    if (syncState.message) {
      statusBanner.style.display = "flex";
      statusBanner.className = `cloud-sync-status-pill ${syncState.status}`;
      if (statusText) statusText.textContent = syncState.message;
    } else {
      statusBanner.style.display = "none";
    }

    if (uploadBtn) uploadBtn.disabled = isSyncing;
    if (downloadBtn) downloadBtn.disabled = isSyncing;
    if (googleBtn) googleBtn.disabled = syncState.status === "loading" && !currentUser;

    if (currentUser) {
      signedOutView.style.display = "none";
      signedInView.style.display = "block";
      const displayName =
        currentUser.displayName ||
        (currentUser.email ? currentUser.email.split("@")[0] : "Usuario Conectado");
      if (userNameEl) userNameEl.textContent = displayName;
      if (userEmailEl) userEmailEl.textContent = currentUser.email || "Sesión en la Nube";

      if (currentUser.photoURL && avatarImg && avatarSvg) {
        avatarImg.src = currentUser.photoURL;
        avatarImg.style.display = "block";
        avatarSvg.style.display = "none";
      } else if (avatarImg && avatarSvg) {
        avatarImg.style.display = "none";
        avatarSvg.style.display = "flex";
      }
    } else {
      signedOutView.style.display = "block";
      signedInView.style.display = "none";
    }
  }

  async function initFirebaseIfAvailable() {
    if (typeof window.firebase === "undefined") {
      return false;
    }
    try {
      if (!window.firebase.apps || window.firebase.apps.length === 0) {
        window.firebase.initializeApp(FIREBASE_CONFIG);
      }
      auth = window.firebase.auth();
      auth.languageCode = "es";
      try {
        await auth.setPersistence(window.firebase.auth.Auth.Persistence.LOCAL);
      } catch {
        // Fallback to default browser persistence
      }
      db = window.firebase.firestore();

      auth.onAuthStateChanged(async user => {
        currentUser = user || null;
        if (currentUser) {
          saveLocalMeta({
            uid: currentUser.uid,
            email: currentUser.email || "",
            displayName: currentUser.displayName || ""
          });
          const cachedMeta = loadLocalMeta();
          if (syncState.status === "idle" && !syncState.message && typeof cachedMeta.backedUpCount === "number") {
            setStatus("success", `${cachedMeta.backedUpCount} elementos respaldados en la nube.`, {
              backedUpCount: cachedMeta.backedUpCount,
              lastSyncTimestamp: cachedMeta.lastSyncTimestamp || 0
            });
          } else {
            renderCloudSyncUI();
          }
          await fetchCloudSyncMetadata(currentUser.uid);
        } else {
          if (syncState.status === "success") {
            syncState = { status: "idle", message: "", backedUpCount: null, lastSyncTimestamp: 0 };
          }
          renderCloudSyncUI();
        }
      });

      try {
        const redirectResult = await auth.getRedirectResult();
        if (redirectResult && redirectResult.user) {
          currentUser = redirectResult.user;
          await syncWithFirestore({ showToastOnDone: true });
        }
      } catch (redirectErr) {
        setStatus("error", formatFirebaseAuthError(redirectErr));
      }

      return true;
    } catch (e) {
      console.warn("[CloudSync] Firebase init error:", e);
      return false;
    }
  }

  async function ensureFirebaseReady() {
    if (auth && db) return true;
    const ok = await initFirebaseIfAvailable();
    if (!ok) {
      const msg = navigator.onLine
        ? "No se pudo cargar el servicio de Google / Firebase. Recarga la página."
        : "Necesitas conexión a internet para conectar o sincronizar con Google.";
      setStatus("error", msg);
      callbacks?.showToast?.("⚠️ " + msg);
      return false;
    }
    return true;
  }

  async function signInWithGoogle() {
    if (!(await ensureFirebaseReady())) return;
    setStatus("loading", "Conectando con cuenta de Google...");

    try {
      const provider = new window.firebase.auth.GoogleAuthProvider();
      provider.setCustomParameters({ prompt: "select_account" });

      const result = await auth.signInWithPopup(provider);
      currentUser = result.user;
      renderCloudSyncUI();
      callbacks?.showToast?.(`✓ Sesión iniciada como ${currentUser.displayName || currentUser.email}`);
      await syncWithFirestore({ showToastOnDone: true });
    } catch (err) {
      if (err?.code === "auth/popup-blocked" || err?.code === "auth/operation-not-supported-in-this-environment") {
        try {
          const provider = new window.firebase.auth.GoogleAuthProvider();
          provider.setCustomParameters({ prompt: "select_account" });
          await auth.signInWithRedirect(provider);
          return;
        } catch (redirErr) {
          const msg = formatFirebaseAuthError(redirErr);
          setStatus("error", msg);
          callbacks?.showToast?.("⚠️ " + msg);
          return;
        }
      }
      const msg = formatFirebaseAuthError(err);
      setStatus("error", msg);
      callbacks?.showToast?.("⚠️ " + msg);
    }
  }

  async function signInWithEmailPassword(email, password) {
    if (!(await ensureFirebaseReady())) return;
    const cleanEmail = (email || "").trim();
    const cleanPass = (password || "").trim();

    if (!cleanEmail || !cleanEmail.includes("@")) {
      const msg = "Ingresa un correo electrónico válido.";
      setStatus("error", msg);
      callbacks?.showToast?.("⚠️ " + msg);
      return;
    }
    if (cleanPass.length < 6) {
      const msg = "La contraseña debe tener al menos 6 caracteres.";
      setStatus("error", msg);
      callbacks?.showToast?.("⚠️ " + msg);
      return;
    }

    setStatus("loading", "Iniciando sesión con correo...");
    try {
      let result;
      try {
        result = await auth.signInWithEmailAndPassword(cleanEmail, cleanPass);
      } catch (signInErr) {
        if (signInErr?.code === "auth/user-not-found") {
          result = await auth.createUserWithEmailAndPassword(cleanEmail, cleanPass);
        } else {
          throw signInErr;
        }
      }
      currentUser = result.user;
      renderCloudSyncUI();
      callbacks?.showToast?.(`✓ Sesión iniciada como ${currentUser.email}`);
      await syncWithFirestore({ showToastOnDone: true });
    } catch (err) {
      const msg = formatFirebaseAuthError(err);
      setStatus("error", msg);
      callbacks?.showToast?.("⚠️ " + msg);
    }
  }

  async function signOut() {
    if (auth) {
      try {
        await auth.signOut();
      } catch (e) {
        console.warn("[CloudSync] SignOut error:", e);
      }
    }
    currentUser = null;
    clearTimeout(autoSyncTimer);
    localStorage.removeItem(META_STORAGE_KEY);
    syncState = { status: "idle", message: "", backedUpCount: null, lastSyncTimestamp: 0 };
    renderCloudSyncUI();
    callbacks?.showToast?.("Sesión cerrada en la nube");
  }

  async function uploadToFirestore({ silent = false } = {}) {
    if (!(await ensureFirebaseReady())) return { ok: false };
    const user = auth.currentUser || currentUser;
    if (!user) {
      const msg = "Debes iniciar sesión con Google para respaldar en la nube.";
      if (!silent) setStatus("error", msg);
      return { ok: false, error: msg };
    }
    if (!navigator.onLine) {
      const msg = "Sin conexión a internet. Tu respaldo se guardó localmente.";
      if (!silent) setStatus("error", msg);
      return { ok: false, error: msg };
    }

    isSyncing = true;
    if (!silent) setStatus("loading", "Subiendo cambios a la nube...");
    else renderCloudSyncUI();

    try {
      const allVerses = callbacks?.getVersesCollection?.() || [];
      const modifiedVerses = allVerses.filter(
        v =>
          v.isFavorite ||
          (v.notes && String(v.notes).trim() !== "") ||
          (v.highlightColor && String(v.highlightColor).trim() !== "") ||
          v.isCustom
      );

      const userDocRef = db.collection(COLLECTION_USERS).doc(user.uid);
      const versesCol = userDocRef.collection(COLLECTION_VERSES);

      const operations = [];
      let uploadCount = 0;

      modifiedVerses.forEach((v, idx) => {
        const docId = getDocIdSafeFallback(v, idx);
        const docRef = versesCol.doc(docId);
        const payload = {
          id: Number(v.id) || 0,
          reference: String(v.reference || "").trim(),
          book: String(v.book || ""),
          chapterVerse: String(v.chapterVerse || ""),
          testament: String(v.testament || ""),
          text: String(v.text || ""),
          context: String(v.context || ""),
          topic: String(v.topic || ""),
          isFavorite: Boolean(v.isFavorite),
          notes: String(v.notes || ""),
          highlightColor: String(v.highlightColor || ""),
          highlightedPhrases: String(v.highlightedPhrases || ""),
          isCustom: Boolean(v.isCustom),
          bibleVersion: String(v.bibleVersion || "RVR1960"),
          updatedAt: Number(v.updatedAt) || Date.now()
        };
        operations.push({ ref: docRef, data: payload });
        uploadCount++;
      });

      const nowTs = Date.now();
      operations.push({
        ref: userDocRef.collection(COLLECTION_METADATA).doc("sync_info"),
        data: {
          lastSyncTimestamp: nowTs,
          totalItems: uploadCount,
          userEmail: user.email || "anónimo"
        }
      });

      const extraData = callbacks?.getExtraBackupData?.();
      if (extraData) {
        operations.push({
          ref: userDocRef.collection(COLLECTION_METADATA).doc("pwa_extra"),
          data: {
            readerHighlightsJson: JSON.stringify(extraData.highlights || {}),
            userPlansJson: JSON.stringify(extraData.userPlans || {}),
            deletedVerseIdsJson: JSON.stringify(extraData.deletedVerseIds || []),
            updatedAt: nowTs
          }
        });
      }

      // Firestore batch limit is 500 operations per commit
      for (let i = 0; i < operations.length; i += 450) {
        const chunk = operations.slice(i, i + 450);
        const batch = db.batch();
        chunk.forEach(op => batch.set(op.ref, op.data, { merge: true }));
        await batch.commit();
      }

      isSyncing = false;
      setStatus("success", `${uploadCount} elementos respaldados en la nube.`, {
        backedUpCount: uploadCount,
        lastSyncTimestamp: nowTs
      });
      if (!silent) {
        callbacks?.showToast?.(`☁️ ${uploadCount} elementos respaldados en la nube`);
      }
      return { ok: true, count: uploadCount };
    } catch (err) {
      isSyncing = false;
      const msg = formatFirestoreError(err);
      setStatus("error", msg);
      if (!silent) callbacks?.showToast?.("⚠️ " + msg);
      return { ok: false, error: msg };
    }
  }

  async function downloadFromFirestore({ silent = false } = {}) {
    if (!(await ensureFirebaseReady())) return { ok: false };
    const user = auth.currentUser || currentUser;
    if (!user) {
      const msg = "Debes iniciar sesión con Google para restaurar tus datos.";
      if (!silent) setStatus("error", msg);
      return { ok: false, error: msg };
    }
    if (!navigator.onLine) {
      const msg = "Sin conexión a internet para descargar de la nube.";
      if (!silent) setStatus("error", msg);
      return { ok: false, error: msg };
    }

    isSyncing = true;
    if (!silent) setStatus("loading", "Descargando respaldo de la nube...");
    else renderCloudSyncUI();

    try {
      const userDocRef = db.collection(COLLECTION_USERS).doc(user.uid);
      const snapshot = await userDocRef.collection(COLLECTION_VERSES).get();

      let extraDocData = null;
      try {
        const extraSnap = await userDocRef.collection(COLLECTION_METADATA).doc("pwa_extra").get();
        if (extraSnap.exists) extraDocData = extraSnap.data();
      } catch {
        // Optional PWA metadata
      }

      const remoteDocs = [];
      snapshot.forEach(doc => {
        const data = doc.data();
        if (data && data.reference) remoteDocs.push(data);
      });

      isApplyingRemoteData = true;
      let restoredCount = 0;
      try {
        if (remoteDocs.length > 0) {
          restoredCount = callbacks?.applyDownloadedData?.(remoteDocs) || remoteDocs.length;
        }
        if (extraDocData && callbacks?.applyExtraBackupData) {
          callbacks.applyExtraBackupData({
            highlights: extraDocData.readerHighlightsJson ? JSON.parse(extraDocData.readerHighlightsJson) : null,
            userPlans: extraDocData.userPlansJson ? JSON.parse(extraDocData.userPlansJson) : null,
            deletedVerseIds: extraDocData.deletedVerseIdsJson ? JSON.parse(extraDocData.deletedVerseIdsJson) : null
          });
        }
      } finally {
        isApplyingRemoteData = false;
      }

      isSyncing = false;
      const statusMsg =
        restoredCount > 0
          ? `${restoredCount} versículos restaurados desde tu cuenta.`
          : "No hay versículos respaldados en tu nube aún.";
      setStatus("success", statusMsg, {
        backedUpCount: restoredCount,
        lastSyncTimestamp: Date.now()
      });
      if (!silent) {
        callbacks?.showToast?.(
          restoredCount > 0
            ? `☁️ ${restoredCount} versículos restaurados de la nube`
            : "☁️ No hay respaldo previo en esta cuenta"
        );
      }
      return { ok: true, count: restoredCount };
    } catch (err) {
      isApplyingRemoteData = false;
      isSyncing = false;
      const msg = formatFirestoreError(err);
      setStatus("error", msg);
      if (!silent) callbacks?.showToast?.("⚠️ " + msg);
      return { ok: false, error: msg };
    }
  }

  async function syncWithFirestore({ showToastOnDone = false } = {}) {
    if (!currentUser && (!auth || !auth.currentUser)) return;
    const dl = await downloadFromFirestore({ silent: true });
    const ul = await uploadToFirestore({ silent: true });
    if (ul?.ok) {
      if (showToastOnDone) {
        callbacks?.showToast?.(`☁️ Sincronización completa (${ul.count} elementos)`);
      }
    } else if (dl?.ok && showToastOnDone) {
      callbacks?.showToast?.(`☁️ Datos descargados de la nube`);
    }
  }

  function scheduleAutoSync() {
    if (isApplyingRemoteData || !currentUser || !navigator.onLine) return;
    clearTimeout(autoSyncTimer);
    autoSyncTimer = setTimeout(() => {
      uploadToFirestore({ silent: true });
    }, 2500);
  }

  function bindUiEvents() {
    document.getElementById("btn-google-signin")?.addEventListener("click", signInWithGoogle);

    const toggleEmailBtn = document.getElementById("btn-toggle-email-signin");
    const emailForm = document.getElementById("cloud-email-login-form");
    toggleEmailBtn?.addEventListener("click", () => {
      if (!emailForm) return;
      const visible = emailForm.style.display !== "none";
      emailForm.style.display = visible ? "none" : "flex";
      toggleEmailBtn.textContent = visible
        ? "✉️ Iniciar con Correo / Contraseña"
        : "Ocultar Correo / Contraseña";
    });

    document.getElementById("btn-email-signin-submit")?.addEventListener("click", () => {
      const emailVal = document.getElementById("cloud-email-input")?.value || "";
      const passVal = document.getElementById("cloud-password-input")?.value || "";
      signInWithEmailPassword(emailVal, passVal);
    });

    document.getElementById("btn-cloud-upload")?.addEventListener("click", () => {
      uploadToFirestore({ silent: false });
    });

    document.getElementById("btn-cloud-download")?.addEventListener("click", () => {
      downloadFromFirestore({ silent: false });
    });

    document.getElementById("btn-cloud-signout")?.addEventListener("click", signOut);
  }

  function init(opts) {
    callbacks = opts || {};
    if (initialized) {
      renderCloudSyncUI();
      return;
    }
    initialized = true;
    bindUiEvents();

    const cached = loadLocalMeta();
    if (cached.email && typeof cached.backedUpCount === "number") {
      syncState.message = `${cached.backedUpCount} elementos respaldados en la nube.`;
      syncState.status = "success";
      syncState.backedUpCount = cached.backedUpCount;
    }
    renderCloudSyncUI();
    initFirebaseIfAvailable();
  }

  window.BibleCloudSync = {
    init,
    signInWithGoogle,
    signInWithEmailPassword,
    signOut,
    uploadToFirestore,
    downloadFromFirestore,
    syncWithFirestore,
    scheduleAutoSync,
    renderCloudSyncUI,
    getCurrentUser: () => currentUser
  };
})();
