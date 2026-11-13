/**
 * BibleVerse Web PWA - Controlador Principal Completo (Paridad 100% con APK Android)
 * Implementa los 9 módulos:
 * 1. TopAppBar dinámica, BottomBar con auto-ocultado y FAB (+)
 * 2. VersesListTab (365 versículos canónicos, Continuar Lectura, Versículo del Día, Filtros A.T./N.T./Libro/Tema)
 * 3. VerseDetailDialog (Exégesis IA, 5 colores, Notas, Editar, Eliminar, PDF) y AddVerseDialog
 * 4. BibleReaderScreen (Selector 3 pasos Libro->Cap->Versículo, Perícopas, Letras Rojas, Swipe, Scroll Continuo,
 *    VerseActionBar con 6 acciones, Comparador Dinámico, Referencias Cruzadas binarias, Audio MP3 + TTS, Ajustes Aa)
 * 5. ReadingPlansBottomSheet (26 Planes en 5 categorías + Planes Personalizados + Lector Devocional 3 Pilares)
 * 6. SearchTab (Búsqueda instantánea en los 66 libros / 31,102 versículos con filtros y resaltado)
 * 7. FavoritesAndNotesTab (3 Sub-pestañas: Favoritos, Resaltados, Con Notas sincronizados)
 * 8. BibleChatDialog (Streaming/Consulta Gemini IA + Chips de citas bíblicas + Micrófono + Voz TTS)
 * 9. SettingsTab (Temas, Fuentes, Recordatorios con Notificación, Cloud Sync, Gestor Offline y Exportador PDF/JSON/TXT)
 */

document.addEventListener("DOMContentLoaded", () => {

  // Bloquear menús contextuales del navegador al mantener presionado (comportamiento nativo de app)
  window.addEventListener("contextmenu", e => {
    const tag = e.target?.tagName;
    if (tag !== "INPUT" && tag !== "TEXTAREA") {
      e.preventDefault();
    }
  });

  function triggerHaptic(pattern = 12) {
    try {
      if (typeof navigator !== "undefined" && typeof navigator.vibrate === "function") {
        navigator.vibrate(pattern);
      }
    } catch (_) {}
  }

  function cleanAiMarkdownToPlainText(text) {
    return (text || "")
      .replace(/^#{1,6}\s+/gm, "")
      .replace(/\*\*(.*?)\*\*/g, "$1")
      .replace(/__(.*?)__/g, "$1")
      .replace(/`([^`]+)`/g, "$1")
      .replace(/^\s*[-*]\s+/gm, "• ")
      .replace(/\*{2,}|#{2,}/g, "")
      .trim();
  }

  // =========================================================================
  // 1. ESTADO PERSISTENTE Y PREFERENCIAS (Sincronizado en localStorage)
  // =========================================================================
  const state = {
    currentTab: "verses", // "verses" | "reader" | "search" | "favorites" | "settings"
    version: localStorage.getItem("bv_version") || "RVR1960",
    bookId: parseInt(localStorage.getItem("bv_bookId") || "19", 10),
    chapter: parseInt(localStorage.getItem("bv_chapter") || "23", 10),
    targetScrollVerse: null,

    // Preferencias de Apariencia (3 temas globales) y Lector (4 temas propios)
    theme: ["light", "sepia-night", "dark"].includes(localStorage.getItem("bv_theme")) ? localStorage.getItem("bv_theme") : "light",
    readerTheme: ["reader-light", "reader-sepia", "reader-dark", "reader-night"].includes(localStorage.getItem("bv_readerTheme")) ? localStorage.getItem("bv_readerTheme") : "reader-light",
    fontFamily: localStorage.getItem("bv_fontFamily") || "serif",
    fontSize: parseInt(localStorage.getItem("bv_fontSize") || "19", 10),
    lineHeight: parseFloat(localStorage.getItem("bv_lineHeight") || "1.52"),
    redLetters: localStorage.getItem("bv_redLetters") !== "false",
    sectionHeadings: localStorage.getItem("bv_sectionHeadings") !== "false",
    continuousScroll: localStorage.getItem("bv_continuousScroll") === "true",

    // Datos del capítulo cargado en el Lector
    currentChapterVerses: [],
    selectedVerses: new Set(),

    // Resaltados del Lector: { "bookId_chapter_verse": { color, text, bookName, bookId, chapter, verse, updatedAt } }
    highlights: JSON.parse(localStorage.getItem("bv_highlights_v2") || "{}"),

    // Compilación de 365 Versículos + Personalizados (con isFavorite, isHighlighted, highlightColor, notes)
    versesCollection: [],

    // Filtros en Pestaña 0 (Versículos)
    versesTestamentFilter: "ALL", // "ALL" | "AT" | "NT"
    versesBookFilter: null,       // nombre del libro o null
    versesTopicFilter: "Todos",
    versesSearchQuery: "",

    // Sub-pestaña en Favoritos
    favSubTab: "FAVORITES", // "FAVORITES" | "HIGHLIGHTS" | "NOTES"

    // Comparador de versiones activas
    compareVersions: JSON.parse(localStorage.getItem("bv_compare_versions") || '["RVR1960","NVI","NBLA","TLA","DHH"]'),

    // Planes de Lectura: { planId: { startedAt, completedDays: [1, 2...], customMeta?: {...}, customDays?: [...] } }
    userPlans: JSON.parse(localStorage.getItem("bv_user_plans") || "{}"),
    activePlanModalTab: "ALL", // "ALL" | "ACTIVE" | "CATALOG" | "COMPLETED"
    currentViewingPlanId: null,
    currentViewingDayNum: 1,
    activeReaderPlanSession: null, // { planId, dayNumber, title, passages: [] }

    // Audio del Lector
    audioMode: "TTS", // "TTS" | "MP3"
    audioPlaying: false,
    audioCurrentVerseIndex: 0,
    audioSpeed: 1.0,

    // Historial de Chat IA
    chatMessages: JSON.parse(localStorage.getItem("bv_chat_history") || "[]")
  };

  // Inicializar colección de 365 versículos fusionando cambios guardados por el usuario
  function initVersesCollection() {
    const base = Array.isArray(window.INITIAL_VERSES_DATA) ? window.INITIAL_VERSES_DATA : [];
    const savedState = JSON.parse(localStorage.getItem("bv_verses_user_state") || "{}");
    const customVerses = JSON.parse(localStorage.getItem("bv_custom_verses") || "[]");

    state.versesCollection = base.map(item => {
      const userMod = savedState[item.id] || {};
      return {
        ...item,
        text: userMod.text || item.text,
        context: userMod.context || item.context,
        topic: userMod.topic || item.topic,
        reference: userMod.reference || item.reference,
        isFavorite: !!userMod.isFavorite,
        isHighlighted: !!userMod.isHighlighted,
        highlightColor: userMod.highlightColor || null,
        notes: userMod.notes || ""
      };
    }).concat(customVerses);
  }

  function saveVersesUserState() {
    const userStateMap = {};
    const customList = [];
    for (const v of state.versesCollection) {
      if (v.isCustom) {
        customList.push(v);
      } else if (v.isFavorite || v.isHighlighted || v.notes || v._edited) {
        userStateMap[v.id] = {
          text: v.text,
          context: v.context,
          topic: v.topic,
          reference: v.reference,
          isFavorite: v.isFavorite,
          isHighlighted: v.isHighlighted,
          highlightColor: v.highlightColor,
          notes: v.notes,
          _edited: v._edited
        };
      }
    }
    localStorage.setItem("bv_verses_user_state", JSON.stringify(userStateMap));
    localStorage.setItem("bv_custom_verses", JSON.stringify(customList));
  }

  function saveHighlightsState() {
    localStorage.setItem("bv_highlights_v2", JSON.stringify(state.highlights));
  }

  function saveUserPlans() {
    localStorage.setItem("bv_user_plans", JSON.stringify(state.userPlans));
    updatePlansSummariesUI();
  }

  function showToast(msg) {
    const toast = document.getElementById("toast-notification");
    if (!toast) return;
    toast.textContent = msg;
    toast.style.opacity = "1";
    clearTimeout(toast._timer);
    toast._timer = setTimeout(() => {
      toast.style.opacity = "0";
    }, 2400);
  }

  // =========================================================================
  // 2. NAVEGACIÓN DE PESTAÑAS, TOPBAR DINÁMICA, BOTTOMBAR Y FAB (+)
  // =========================================================================
  const mainTopBar = document.getElementById("main-top-bar");
  const mainBottomNav = document.getElementById("main-bottom-nav");
  const fabAddVerse = document.getElementById("btn-fab-add-verse");
  const mainScrollContainer = document.getElementById("main-scroll-container");

  function switchTab(tabId) {
    const prevTab = state.currentTab;
    state.currentTab = tabId;
    if (tabId !== "verses" && prevTab !== tabId) {
      pushBackNavState();
    }

    document.querySelectorAll(".nav-item").forEach(btn => {
      btn.classList.toggle("active", btn.dataset.tab === tabId);
    });
    document.querySelectorAll(".tab-pane").forEach(pane => {
      pane.classList.toggle("active", pane.id === `tab-${tabId}`);
    });

    // Ocultar TopAppBar principal en Lector y Ajustes igual que en HomeScreen.kt
    if (mainTopBar) {
      mainTopBar.style.display = (tabId === "reader" || tabId === "settings") ? "none" : "flex";
    }

    // Mostrar FAB (+) solo en Versículos y Favoritos
    if (fabAddVerse) {
      fabAddVerse.style.display = (tabId === "verses" || tabId === "favorites") ? "flex" : "none";
    }

    if (tabId !== "reader") {
      clearVerseSelection();
      mainBottomNav.classList.remove("auto-hidden");
      document.getElementById("reader-floating-nav")?.classList.remove("visible", "docked-bottom");
    } else {
      document.getElementById("reader-floating-nav")?.classList.toggle("visible", state.selectedVerses.size === 0);
    }

    if (tabId === "reader") {
      loadReaderChapter();
    } else if (tabId === "favorites") {
      renderFavoritesAndNotesTab();
    } else if (tabId === "settings") {
      renderSettingsTab();
    } else if (tabId === "verses") {
      updateContinueReadingBanner();
      renderPopularVersesFeed();
    }

    if (mainScrollContainer) mainScrollContainer.scrollTop = 0;
    syncSystemThemeBars();
  }

  document.querySelectorAll(".nav-item").forEach(btn => {
    btn.addEventListener("click", () => {
      triggerHaptic(10);
      switchTab(btn.dataset.tab);
    });
  });

  document.getElementById("btn-scroll-top")?.addEventListener("click", () => {
    if (state.currentTab !== "verses") {
      switchTab("verses");
    }
    if (mainScrollContainer) mainScrollContainer.scrollTo({ top: 0, behavior: "smooth" });
  });

  document.getElementById("btn-open-settings")?.addEventListener("click", () => {
    switchTab("settings");
  });

  document.getElementById("btn-back-from-settings")?.addEventListener("click", () => {
    switchTab("verses");
  });

  // Auto-ocultar BottomBar al hacer scroll hacia abajo en el Lector + Scroll Continuo
  let lastScrollY = 0;
  let isAppendingContinuous = false;
  mainScrollContainer?.addEventListener("scroll", async () => {
    if (state.currentTab !== "reader") return;
    const currentY = mainScrollContainer.scrollTop;
    if (currentY > lastScrollY + 18 && currentY > 80) {
      mainBottomNav.classList.add("auto-hidden");
      document.getElementById("reader-floating-nav")?.classList.add("docked-bottom");
    } else if (currentY < lastScrollY - 12 || currentY < 40) {
      mainBottomNav.classList.remove("auto-hidden");
      document.getElementById("reader-floating-nav")?.classList.remove("docked-bottom");
    }
    lastScrollY = currentY;

    if (state.continuousScroll && !isAppendingContinuous) {
      const distToBottom = mainScrollContainer.scrollHeight - (mainScrollContainer.scrollTop + mainScrollContainer.clientHeight);
      if (distToBottom < 140 && !(state.bookId === 66 && state.chapter >= 22)) {
        isAppendingContinuous = true;
        try {
          const curBook = getBookById(state.bookId);
          if (state.chapter < curBook.chapters) {
            state.chapter++;
          } else if (state.bookId < 66) {
            state.bookId++;
            state.chapter = 1;
          }
          await loadReaderChapter(true);
        } finally {
          setTimeout(() => { isAppendingContinuous = false; }, 350);
        }
      }
    }
  });

  // =========================================================================
  // 3. PESTAÑA 0: VERSÍCULOS (365 Canónicos, VOD, Continuar Lectura, Filtros)
  // =========================================================================
  let currentVodVerse = null;

  function updateContinueReadingBanner() {
    const book = getBookById(state.bookId);
    const label = document.getElementById("continue-reading-label");
    if (label) {
      label.textContent = `${book.name} ${state.chapter} • ${state.version}`;
    }
  }

  document.getElementById("btn-continue-reading")?.addEventListener("click", () => {
    switchTab("reader");
  });

  function applyVodBackground(bgNum) {
    const card = document.getElementById("vod-card");
    if (!card) return;
    const padded = String(bgNum).padStart(2, "0");
    card.style.backgroundImage = `url("./assets/drawable/verse_bg_${padded}.webp")`;
    localStorage.setItem("bv_vod_bg", String(bgNum));
  }

  function renderVerseOfTheDay() {
    const list = state.versesCollection;
    if (!list.length) return;
    const dayOfYear = Math.floor((Date.now() - new Date(new Date().getFullYear(), 0, 0)) / 86400000);
    currentVodVerse = list[dayOfYear % list.length];

    const savedBg = parseInt(localStorage.getItem("bv_vod_bg") || "0", 10);
    const bgNum = (savedBg >= 1 && savedBg <= 12) ? savedBg : ((dayOfYear % 12) + 1);
    applyVodBackground(bgNum);

    document.getElementById("vod-tag").textContent = currentVodVerse.topic;
    document.getElementById("vod-quote").textContent = `«${currentVodVerse.text}»`;
    document.getElementById("vod-ref").textContent = `${currentVodVerse.reference} (${state.version})`;
    const vodReflEl = document.getElementById("vod-reflection");
    if (vodReflEl) vodReflEl.textContent = currentVodVerse.context;

    const favBtn = document.getElementById("btn-fav-vod");
    if (favBtn) {
      favBtn.classList.toggle("active", !!currentVodVerse.isFavorite);
    }
  }

  document.getElementById("btn-shuffle-vod-bg")?.addEventListener("click", e => {
    e.stopPropagation();
    const current = parseInt(localStorage.getItem("bv_vod_bg") || "1", 10);
    const next = (current % 12) + 1;
    applyVodBackground(next);
  });

  document.getElementById("vod-card")?.addEventListener("click", e => {
    if (e.target.closest("button")) return;
    if (currentVodVerse) openVerseDetailDialog(currentVodVerse.id);
  });

  document.getElementById("btn-fav-vod")?.addEventListener("click", () => {
    if (!currentVodVerse) return;
    currentVodVerse.isFavorite = !currentVodVerse.isFavorite;
    saveVersesUserState();
    renderVerseOfTheDay();
    renderPopularVersesFeed();
    showToast(currentVodVerse.isFavorite ? "❤️ Guardado en Favoritos" : "Eliminado de Favoritos");
  });

  document.getElementById("btn-read-vod-chapter")?.addEventListener("click", () => {
    if (!currentVodVerse) return;
    const parsed = parseBibleReference(currentVodVerse.reference);
    if (parsed) {
      state.bookId = parsed.bookId;
      state.chapter = parsed.chapter;
      state.targetScrollVerse = parsed.startVerse;
      switchTab("reader");
    }
  });

  document.getElementById("btn-image-vod")?.addEventListener("click", () => {
    if (!currentVodVerse) return;
    openShareImageDesigner(currentVodVerse.text, `${currentVodVerse.reference} (${state.version})`);
  });

  document.getElementById("btn-share-vod")?.addEventListener("click", () => {
    if (!currentVodVerse) return;
    const payload = `«${currentVodVerse.text}» — ${currentVodVerse.reference} (${state.version})`;
    if (navigator.share) {
      navigator.share({ title: currentVodVerse.reference, text: payload }).catch(() => {});
    } else {
      navigator.clipboard.writeText(payload);
      showToast("📋 Versículo copiado al portapapeles");
    }
  });

  function renderTopicFilterChips() {
    const container = document.getElementById("topic-filter-chips");
    if (!container) return;
    const topicsSet = new Set(["Todos"]);
    state.versesCollection.forEach(v => {
      if (v.topic) topicsSet.add(v.topic);
    });
    const topics = Array.from(topicsSet);

    container.innerHTML = topics.map(t => `
      <button class="chip ${state.versesTopicFilter === t ? "active" : ""}" data-topic="${t}">
        ${t}
      </button>
    `).join("");

    container.querySelectorAll(".chip").forEach(btn => {
      btn.addEventListener("click", () => {
        state.versesTopicFilter = btn.dataset.topic;
        renderTopicFilterChips();
        renderPopularVersesFeed();
      });
    });
  }

  // Chips Canónicos (Todos, A.T., N.T., Por Libro)
  document.querySelectorAll("#canonical-filter-chips .chip[data-testament]").forEach(btn => {
    btn.addEventListener("click", () => {
      state.versesTestamentFilter = btn.dataset.testament;
      if (state.versesTestamentFilter !== "BOOK") {
        state.versesBookFilter = null;
        document.getElementById("btn-open-book-filter-modal").textContent = "📚 Por Libro...";
        document.getElementById("btn-open-book-filter-modal").classList.remove("active");
      }
      document.querySelectorAll("#canonical-filter-chips .chip[data-testament]").forEach(b => {
        b.classList.toggle("active", b.dataset.testament === state.versesTestamentFilter);
      });
      renderPopularVersesFeed();
    });
  });

  // Botón de Filtrar por Libro en la pestaña Versículos -> usa el Modal Unificado estilo APK
  document.getElementById("btn-open-book-filter-modal")?.addEventListener("click", () => {
    openUnifiedBookSelector("VERSES_FILTER");
  });

  // Buscador rápido dentro de los 365 versículos
  const versesFilterInput = document.getElementById("verses-filter-input");
  const clearVersesFilterBtn = document.getElementById("btn-clear-verses-filter");
  versesFilterInput?.addEventListener("input", e => {
    state.versesSearchQuery = e.target.value;
    if (clearVersesFilterBtn) clearVersesFilterBtn.style.display = state.versesSearchQuery ? "block" : "none";
    renderPopularVersesFeed();
  });
  clearVersesFilterBtn?.addEventListener("click", () => {
    state.versesSearchQuery = "";
    versesFilterInput.value = "";
    clearVersesFilterBtn.style.display = "none";
    renderPopularVersesFeed();
  });

  document.getElementById("btn-reset-all-filters")?.addEventListener("click", () => {
    state.versesTestamentFilter = "ALL";
    state.versesBookFilter = null;
    state.versesTopicFilter = "Todos";
    state.versesSearchQuery = "";
    if (versesFilterInput) versesFilterInput.value = "";
    document.getElementById("btn-open-book-filter-modal").textContent = "📚 Por Libro...";
    document.querySelectorAll("#canonical-filter-chips .chip").forEach(c => {
      c.classList.toggle("active", c.dataset.testament === "ALL");
    });
    renderTopicFilterChips();
    renderPopularVersesFeed();
  });

  function renderPopularVersesFeed() {
    const feed = document.getElementById("popular-verses-feed");
    if (!feed) return;

    const q = stripAccents(state.versesSearchQuery).toLowerCase().trim();
    const filtered = state.versesCollection.filter(v => {
      if (state.versesTestamentFilter === "AT" && !v.testament.includes("Antiguo")) return false;
      if (state.versesTestamentFilter === "NT" && !v.testament.includes("Nuevo")) return false;
      if (state.versesBookFilter && v.book !== state.versesBookFilter) return false;
      if (state.versesTopicFilter !== "Todos" && v.topic !== state.versesTopicFilter) return false;
      if (q) {
        const hay = stripAccents(`${v.reference} ${v.text} ${v.topic} ${v.context} ${v.notes || ""}`).toLowerCase();
        if (!hay.includes(q)) return false;
      }
      return true;
    });

    const countLabel = document.getElementById("verses-count-label");
    const resetBtn = document.getElementById("btn-reset-all-filters");
    if (countLabel) {
      countLabel.textContent = `Mostrando ${filtered.length} de ${state.versesCollection.length} versículos`;
    }
    if (resetBtn) {
      const hasActiveFilter = state.versesTestamentFilter !== "ALL" || state.versesBookFilter || state.versesTopicFilter !== "Todos" || q;
      resetBtn.style.display = hasActiveFilter ? "inline" : "none";
    }

    const chipAll = document.getElementById("chip-testament-all");
    if (chipAll) chipAll.textContent = `Todos (${state.versesCollection.length})`;

    if (filtered.length === 0) {
      feed.innerHTML = `<div style="text-align:center; padding:32px 16px; color:var(--text-secondary);">No se encontraron versículos con esos filtros.</div>`;
      return;
    }

    // Renderizar tarjetas (con soporte de resaltado, nota, versión e indicador de favorito)
    feed.innerHTML = filtered.map(v => {
      const hlStyle = v.highlightColor ? `border-left: 5px solid ${v.highlightColor}; background-color: ${v.highlightColor}22;` : "";
      const verBadge = v.version || state.version;
      return `
        <div class="verse-item-card" data-vid="${v.id}" style="${hlStyle}">
          <div class="verse-card-top">
            <div class="verse-card-badges">
              <span class="verse-tag" style="margin:0;">${v.topic}</span>
              <span class="testament-mini-badge">${verBadge}</span>
              ${v.notes ? `<span class="testament-mini-badge" style="color:var(--secondary-color);">✏️ Con nota</span>` : ""}
            </div>
            <button class="icon-btn btn-card-fav" data-vid="${v.id}" style="width:30px; height:30px; color:${v.isFavorite ? "#EF4444" : "var(--text-secondary)"};" title="Favorito">
              ${v.isFavorite ? "❤️" : "🤍"}
            </button>
          </div>
          <div style="font-weight:800; color:var(--secondary-color); font-size:0.94rem; margin-bottom:5px;">
            ${v.reference}
          </div>
          <p style="font-family:var(--font-serif); font-size:0.97rem; line-height:1.56; color:var(--text-primary);">
            «${v.text}»
          </p>
          ${v.context ? `<p style="font-size:0.78rem; color:var(--text-secondary); margin-top:7px; display:-webkit-box; -webkit-line-clamp:2; -webkit-box-orient:vertical; overflow:hidden;">📜 ${v.context}</p>` : ""}
        </div>
      `;
    }).join("");

    // Eventos de clic en tarjeta (abre VerseDetailDialog) y botón de favorito
    feed.querySelectorAll(".verse-item-card").forEach(card => {
      card.addEventListener("click", e => {
        if (e.target.closest(".btn-card-fav")) return;
        const vid = Number(card.dataset.vid);
        openVerseDetailDialog(vid);
      });
    });

    feed.querySelectorAll(".btn-card-fav").forEach(btn => {
      btn.addEventListener("click", e => {
        e.stopPropagation();
        const vid = Number(btn.dataset.vid);
        const item = state.versesCollection.find(x => x.id === vid);
        if (item) {
          item.isFavorite = !item.isFavorite;
          saveVersesUserState();
          renderPopularVersesFeed();
          showToast(item.isFavorite ? "❤️ Añadido a Favoritos" : "Quitado de Favoritos");
        }
      });
    });
  }

  // =========================================================================
  // 4. MODAL DETALLE DE VERSÍCULO (VerseDetailDialog.kt) Y AGREGAR (AddVerseDialog.kt)
  // =========================================================================
  let currentDetailVerseId = null;
  const verseDetailModal = document.getElementById("verse-detail-modal");

  function openVerseDetailDialog(verseId) {
    const v = state.versesCollection.find(x => x.id === Number(verseId));
    if (!v) return;
    currentDetailVerseId = v.id;

    document.getElementById("detail-view-mode").style.display = "block";
    document.getElementById("detail-edit-mode").style.display = "none";

    document.getElementById("detail-verse-topic").textContent = v.topic || "Escritura";
    document.getElementById("detail-verse-ref").textContent = `${v.reference} (${v.bibleVersion || state.version})`;
    const quoteEl = document.getElementById("detail-verse-text");
    quoteEl.textContent = `«${v.text}»`;
    quoteEl.style.backgroundColor = v.highlightColor ? `${v.highlightColor}28` : "var(--surface-color)";
    quoteEl.style.borderLeftColor = v.highlightColor || "var(--secondary-color)";

    document.getElementById("detail-verse-context").textContent = v.context || "Pulsa 'Profundizar con IA' para generar la exégesis histórica y teológica de este pasaje.";
    document.getElementById("detail-verse-notes").value = v.notes || "";

    verseDetailModal.classList.add("open");
  }

  document.getElementById("btn-close-verse-detail")?.addEventListener("click", () => {
    verseDetailModal.classList.remove("open");
  });

  // Paleta de resaltado dentro del VerseDetailDialog
  document.querySelectorAll("#detail-palette-swatches [data-detail-hex]").forEach(sw => {
    sw.addEventListener("click", () => {
      const v = state.versesCollection.find(x => x.id === currentDetailVerseId);
      if (!v) return;
      const hex = sw.dataset.detailHex;
      v.highlightColor = hex || null;
      v.isHighlighted = !!hex;
      saveVersesUserState();
      openVerseDetailDialog(v.id);
      renderPopularVersesFeed();
      if (state.currentTab === "favorites") renderFavoritesAndNotesTab();
      showToast(hex ? "🎨 Versículo resaltado" : "Resaltado eliminado");
    });
  });

  // Botón "Profundizar con IA" dentro de VerseDetailDialog
  document.getElementById("btn-detail-enrich-ai")?.addEventListener("click", async () => {
    const v = state.versesCollection.find(x => x.id === currentDetailVerseId);
    if (!v) return;
    const ctxEl = document.getElementById("detail-verse-context");
    ctxEl.textContent = "⏳ Generando análisis exegético, histórico y teológico con IA...";
    const parsed = parseBibleReference(v.reference) || { bookName: v.book, chapter: 1, startVerse: 1 };
    const res = await generateVerseExegesisWithAI(parsed.bookName, parsed.chapter, parsed.startVerse, v.text, state.version);
    v.context = res.text;
    v._edited = true;
    saveVersesUserState();
    ctxEl.textContent = res.text;
    renderPopularVersesFeed();
    showToast(`✨ Contexto enriquecido (${res.source})`);
  });

  // Guardar nota personal en VerseDetailDialog
  document.getElementById("btn-save-detail-note")?.addEventListener("click", () => {
    const v = state.versesCollection.find(x => x.id === currentDetailVerseId);
    if (!v) return;
    v.notes = document.getElementById("detail-verse-notes").value.trim();
    saveVersesUserState();
    renderPopularVersesFeed();
    if (state.currentTab === "favorites") renderFavoritesAndNotesTab();
    showToast("💾 Nota personal guardada");
  });

  // Acciones inferiores del VerseDetailDialog
  document.getElementById("btn-detail-open-reader")?.addEventListener("click", () => {
    const v = state.versesCollection.find(x => x.id === currentDetailVerseId);
    if (!v) return;
    const parsed = parseBibleReference(v.reference);
    if (parsed) {
      state.bookId = parsed.bookId;
      state.chapter = parsed.chapter;
      state.targetScrollVerse = parsed.startVerse;
      verseDetailModal.classList.remove("open");
      switchTab("reader");
    }
  });

  document.getElementById("btn-detail-share-image")?.addEventListener("click", () => {
    const v = state.versesCollection.find(x => x.id === currentDetailVerseId);
    if (!v) return;
    verseDetailModal.classList.remove("open");
    openShareImageDesigner(v.text, `${v.reference} (${state.version})`);
  });

  document.getElementById("btn-detail-export-pdf")?.addEventListener("click", () => {
    const v = state.versesCollection.find(x => x.id === currentDetailVerseId);
    if (!v) return;
    exportVersesToPrintablePDF([v], `Estudio Bíblico - ${v.reference}`);
  });

  document.getElementById("btn-detail-edit-verse")?.addEventListener("click", () => {
    const v = state.versesCollection.find(x => x.id === currentDetailVerseId);
    if (!v) return;
    document.getElementById("edit-verse-ref-input").value = v.reference;
    document.getElementById("edit-verse-topic-input").value = v.topic;
    document.getElementById("edit-verse-text-input").value = v.text;
    document.getElementById("edit-verse-context-input").value = v.context;
    document.getElementById("detail-view-mode").style.display = "none";
    document.getElementById("detail-edit-mode").style.display = "block";
  });

  document.getElementById("btn-cancel-edit-verse")?.addEventListener("click", () => {
    document.getElementById("detail-edit-mode").style.display = "none";
    document.getElementById("detail-view-mode").style.display = "block";
  });

  document.getElementById("btn-save-edit-verse")?.addEventListener("click", () => {
    const v = state.versesCollection.find(x => x.id === currentDetailVerseId);
    if (!v) return;
    v.reference = document.getElementById("edit-verse-ref-input").value.trim() || v.reference;
    v.topic = document.getElementById("edit-verse-topic-input").value.trim() || v.topic;
    v.text = document.getElementById("edit-verse-text-input").value.trim() || v.text;
    v.context = document.getElementById("edit-verse-context-input").value.trim() || v.context;
    v._edited = true;
    saveVersesUserState();
    openVerseDetailDialog(v.id);
    renderTopicFilterChips();
    renderPopularVersesFeed();
    showToast("✓ Versículo actualizado");
  });

  document.getElementById("btn-detail-delete-verse")?.addEventListener("click", () => {
    const idx = state.versesCollection.findIndex(x => x.id === currentDetailVerseId);
    if (idx === -1) return;
    state.versesCollection.splice(idx, 1);
    saveVersesUserState();
    verseDetailModal.classList.remove("open");
    renderPopularVersesFeed();
    if (state.currentTab === "favorites") renderFavoritesAndNotesTab();
    showToast("🗑️ Versículo eliminado");
  });

  // =========================================================================
  // MODAL AGREGAR VERSÍCULO (AddVerseDialog.kt - FAB +)
  // =========================================================================
  const addVerseModal = document.getElementById("add-verse-modal");
  const addBookSel = document.getElementById("add-verse-book");
  const addChapSel = document.getElementById("add-verse-chapter");
  const addNumSel = document.getElementById("add-verse-number");
  const addVerSel = document.getElementById("add-verse-version");

  function updateAddVerseReferenceDisplay() {
    const b = getBookById(addBookSel?.value || state.bookId);
    const ch = Number(addChapSel?.value || 1);
    const vNum = Number(addNumSel?.value || 1);
    const ver = addVerSel?.value || state.version;
    const refDisp = document.getElementById("add-verse-ref-display");
    const refMeta = document.getElementById("add-verse-ref-meta");
    if (refDisp) refDisp.textContent = `${b.name} ${ch}:${vNum}`;
    if (refMeta) refMeta.textContent = `${b.testamentFull} • ${b.category} • ${ver}`;
  }

  function updateAddVerseVersionCard(code) {
    const verCode = code || addVerSel?.value || state.version;
    if (addVerSel) addVerSel.value = verCode;
    const vMeta = BIBLE_VERSIONS.find(v => v.code === verCode) || { code: verCode, name: verCode };
    const badgeEl = document.getElementById("add-verse-version-badge");
    const nameEl = document.getElementById("add-verse-version-name");
    if (badgeEl) badgeEl.textContent = vMeta.code;
    if (nameEl) nameEl.textContent = vMeta.name;
  }

  function initAddVerseForm() {
    if (!addBookSel) return;
    addBookSel.value = String(state.bookId);
    updateAddVerseVersionCard(state.version);
    updateAddVerseChapters(state.chapter, 1);
  }

  document.getElementById("btn-add-verse-pick-version")?.addEventListener("click", () => {
    triggerHaptic(10);
    openVersionSelectorModal("ADD_VERSE");
  });

  document.getElementById("btn-add-verse-pick-book")?.addEventListener("click", () => {
    triggerHaptic(10);
    openUnifiedBookSelector("ADD_VERSE");
  });

  function updateAddVerseChapters(presetChapter = 1, presetVerse = 1) {
    const b = getBookById(addBookSel.value);
    addChapSel.innerHTML = Array.from({ length: b.chapters }, (_, i) => `<option value="${i + 1}">${i + 1}</option>`).join("");
    addChapSel.value = String(Math.min(Math.max(1, presetChapter), b.chapters));
    updateAddVerseNumbers(presetVerse);
  }

  function updateAddVerseNumbers(presetVerse = 1) {
    const bId = Number(addBookSel.value);
    const ch = Number(addChapSel.value);
    const vCount = getCanonicalVerseCount(bId, ch);
    addNumSel.innerHTML = Array.from({ length: vCount }, (_, i) => `<option value="${i + 1}">${i + 1}</option>`).join("");
    addNumSel.value = String(Math.min(Math.max(1, presetVerse), vCount));
    updateAddVerseReferenceDisplay();
    autofillAddVerseText();
  }

  async function autofillAddVerseText() {
    const b = getBookById(addBookSel.value);
    const ch = Number(addChapSel.value);
    const vNum = Number(addNumSel.value);
    const ver = addVerSel.value;
    updateAddVerseReferenceDisplay();
    const chData = await fetchChapterVerses(ver, b.id, ch);
    const found = chData.verses.find(x => x.verse === vNum);
    if (found) {
      document.getElementById("add-verse-text").value = found.text;
      const ctxBox = document.getElementById("add-verse-context");
      const badgeEl = document.getElementById("add-verse-context-badge");
      if (ctxBox) ctxBox.value = getLocalScholarlyExegesis(b.name, ch, vNum, found.text);
      if (badgeEl) badgeEl.textContent = "Catálogo Local";
    }
  }

  addBookSel?.addEventListener("change", () => updateAddVerseChapters());
  addChapSel?.addEventListener("change", () => updateAddVerseNumbers());
  addNumSel?.addEventListener("change", autofillAddVerseText);
  addVerSel?.addEventListener("change", autofillAddVerseText);

  document.querySelectorAll("#add-verse-topic-chips [data-addtopic]").forEach(chip => {
    chip.addEventListener("click", () => {
      triggerHaptic(8);
      const t = chip.dataset.addtopic;
      const input = document.getElementById("add-verse-topic");
      if (input) input.value = t;
      document.querySelectorAll("#add-verse-topic-chips [data-addtopic]").forEach(c => {
        c.classList.toggle("active", c === chip);
      });
    });
  });

  document.getElementById("btn-fab-add-verse")?.addEventListener("click", () => {
    initAddVerseForm();
    document.getElementById("add-verse-notes").value = "";
    const topicInput = document.getElementById("add-verse-topic");
    if (topicInput) topicInput.value = "General";
    document.querySelectorAll("#add-verse-topic-chips [data-addtopic]").forEach(c => c.classList.remove("active"));
    addVerseModal.classList.add("open");
  });

  document.getElementById("btn-close-add-verse")?.addEventListener("click", () => {
    addVerseModal.classList.remove("open");
  });

  document.getElementById("btn-add-verse-ai-context")?.addEventListener("click", async () => {
    const b = getBookById(addBookSel.value);
    const ch = Number(addChapSel.value);
    const vNum = Number(addNumSel.value);
    const txt = document.getElementById("add-verse-text").value;
    const ctxBox = document.getElementById("add-verse-context");
    const badgeEl = document.getElementById("add-verse-context-badge");
    ctxBox.value = "⏳ Generando contexto histórico y teológico con IA...";
    const res = await generateVerseExegesisWithAI(b.name, ch, vNum, txt, addVerSel.value);
    ctxBox.value = cleanAiMarkdownToPlainText(res.text);
    if (badgeEl) badgeEl.textContent = res.source.includes("Gemini") ? "✨ IA (Gemini)" : "Catálogo Local";
  });

  document.getElementById("btn-add-verse-local-context")?.addEventListener("click", () => {
    triggerHaptic(8);
    const b = getBookById(addBookSel.value);
    const ch = Number(addChapSel.value);
    const vNum = Number(addNumSel.value);
    const txt = document.getElementById("add-verse-text").value;
    const ctxBox = document.getElementById("add-verse-context");
    const badgeEl = document.getElementById("add-verse-context-badge");
    if (ctxBox) ctxBox.value = getLocalScholarlyExegesis(b.name, ch, vNum, txt);
    if (badgeEl) badgeEl.textContent = "Catálogo Local";
  });

  document.getElementById("btn-submit-add-verse")?.addEventListener("click", () => {
    const b = getBookById(addBookSel.value);
    const ch = Number(addChapSel.value);
    const vNum = Number(addNumSel.value);
    const ver = addVerSel.value;
    const topic = document.getElementById("add-verse-topic").value.trim() || "Personal";
    const text = document.getElementById("add-verse-text").value.trim();
    const context = document.getElementById("add-verse-context").value.trim() || getLocalScholarlyExegesis(b.name, ch, vNum, text);
    const notes = document.getElementById("add-verse-notes").value.trim();

    if (!text) {
      showToast("⚠️ El texto del versículo no puede estar vacío");
      return;
    }

    const newVerse = {
      id: Date.now(),
      book: b.name,
      chapterVerse: `${ch}:${vNum}`,
      reference: `${b.name} ${ch}:${vNum}`,
      testament: b.testamentFull,
      text,
      context,
      topic,
      bibleVersion: ver,
      isFavorite: true,
      isHighlighted: false,
      highlightColor: null,
      notes,
      isCustom: true
    };

    state.versesCollection.unshift(newVerse);
    saveVersesUserState();
    addVerseModal.classList.remove("open");
    renderTopicFilterChips();
    renderPopularVersesFeed();
    if (state.currentTab === "favorites") renderFavoritesAndNotesTab();
    showToast("✓ Versículo guardado en tu colección y Favoritos");
  });

  // =========================================================================
  // 5. PESTAÑA 1: LECTOR BÍBLICO (Perícopas, Letras Rojas, Selector 3 Pasos, Audio, Swipe)
  // =========================================================================
  const versesListEl = document.getElementById("bible-verses-list");
  const bookNameBtn = document.getElementById("reader-book-name");
  const versionCodeEl = document.getElementById("reader-version-code");
  const chapterSubtitleEl = document.getElementById("reader-chapter-subtitle");
  const actionBar = document.getElementById("verse-action-bar");

  function refreshAdjacentHighlightMerging() {
    const rows = Array.from(versesListEl.querySelectorAll(".verse-row"));
    rows.forEach((row, idx) => {
      row.classList.remove("hl-join-prev", "hl-join-next");
      const hl = row.getAttribute("data-hl");
      if (!hl) return;
      const prev = rows[idx - 1];
      const next = rows[idx + 1];
      if (prev && prev.previousElementSibling === null || (prev && row.previousElementSibling === prev)) {
        if (prev.getAttribute("data-hl") === hl) {
          row.classList.add("hl-join-prev");
        }
      }
      if (next && row.nextElementSibling === next && next.getAttribute("data-hl") === hl) {
        row.classList.add("hl-join-next");
      }
    });
  }

  async function loadReaderChapter(appendContinuous = false) {
    const book = getBookById(state.bookId);
    if (bookNameBtn) bookNameBtn.textContent = `${book.name} ${state.chapter}`;
    if (versionCodeEl) versionCodeEl.textContent = state.version;
    if (chapterSubtitleEl) chapterSubtitleEl.textContent = `${book.name} • Capítulo ${state.chapter} (${book.testamentFull})`;

    const floatingPill = document.getElementById("btn-floating-book-pill");
    if (floatingPill) floatingPill.textContent = `${book.name.toUpperCase()} ${state.chapter}`;

    const mp3ModeBtn = document.getElementById("btn-audio-mode-mp3");
    if (mp3ModeBtn && typeof getOfficialChapterAudioUrl === "function") {
      getOfficialChapterAudioUrl(state.version, state.bookId, state.chapter).then(url => {
        mp3ModeBtn.style.display = url ? "inline-flex" : "none";
      }).catch(() => {
        mp3ModeBtn.style.display = "none";
      });
    }

    localStorage.setItem("bv_bookId", state.bookId);
    localStorage.setItem("bv_chapter", state.chapter);
    localStorage.setItem("bv_version", state.version);
    updateContinueReadingBanner();
    renderPlanSessionHeaderInReader();

    if (!appendContinuous) {
      clearVerseSelection();
      versesListEl.innerHTML = `<div style="text-align:center; padding:36px; color:var(--text-secondary);">Cargando ${book.name} ${state.chapter} (${state.version})...</div>`;
    }

    const data = await fetchChapterVerses(state.version, state.bookId, state.chapter);
    state.currentChapterVerses = data.verses;

    const parallelChipHtml = (!appendContinuous && hasChapterParallelReferences(state.bookId, state.chapter))
      ? `<button type="button" class="chapter-parallel-chip" id="btn-chapter-parallels">⊕ Pasajes paralelos del capítulo</button>`
      : "";

    const htmlChunk = parallelChipHtml + data.verses.map(v => {
      const key = `${state.bookId}_${state.chapter}_${v.verse}`;
      const hlObj = state.highlights[key];
      const hlHex = hlObj ? hlObj.color : "";
      const headingHtml = (state.sectionHeadings && v.sectionHeading)
        ? `<div class="pericope-heading">${v.sectionHeading}</div>`
        : "";
      const redClass = (state.redLetters && v.isRedLetter) ? "red-letter-text" : "";

      return `
        ${headingHtml}
        <div class="verse-row" id="reader-v-${v.verse}" data-verse="${v.verse}" ${hlHex ? `data-hl="${hlHex}"` : ""}>
          <span class="verse-num">${v.verse}</span>
          <span class="verse-text ${redClass}">${v.text}</span>
        </div>
      `;
    }).join("");

    if (appendContinuous) {
      const divider = `<div class="pericope-heading" style="margin-top:28px; font-size:1.1rem;">📖 ${book.name} ${state.chapter}</div>`;
      versesListEl.insertAdjacentHTML("beforeend", divider + htmlChunk);
    } else {
      versesListEl.innerHTML = htmlChunk;
    }

    refreshAdjacentHighlightMerging();

    document.getElementById("btn-chapter-parallels")?.addEventListener("click", () => {
      triggerHaptic(12);
      openChapterParallelsModal();
    });

    versesListEl.querySelectorAll(".verse-row").forEach(row => {
      row.onclick = () => toggleVerseSelection(parseInt(row.dataset.verse, 10), row);
    });

    // Si hay un versículo objetivo (desde el selector de 3 pasos, Buscador, Referencias o Chat IA)
    if (state.targetScrollVerse) {
      const targetEl = document.getElementById(`reader-v-${state.targetScrollVerse}`);
      if (targetEl) {
        setTimeout(() => {
          targetEl.scrollIntoView({ behavior: "smooth", block: "center" });
          targetEl.classList.add("transient-target");
        }, 120);
      }
      state.targetScrollVerse = null;
    } else if (!appendContinuous && mainScrollContainer) {
      mainScrollContainer.scrollTop = 0;
    }

    if (state.audioPlaying) {
      startChapterAudioPlayback();
    }
  }

  function navigateChapter(delta) {
    triggerHaptic(14);
    const book = getBookById(state.bookId);
    if (delta < 0) {
      if (state.chapter > 1) {
        state.chapter--;
      } else if (state.bookId > 1) {
        state.bookId--;
        state.chapter = getBookById(state.bookId).chapters;
      }
    } else {
      if (state.chapter < book.chapters) {
        state.chapter++;
      } else if (state.bookId < 66) {
        state.bookId++;
        state.chapter = 1;
      }
    }
    loadReaderChapter(false);
  }

  const readerFloatingNavEl = document.getElementById("reader-floating-nav");
  if (readerFloatingNavEl) {
    ["touchstart", "touchend", "pointerdown", "click"].forEach(evtName => {
      readerFloatingNavEl.addEventListener(evtName, e => e.stopPropagation(), { passive: true });
    });
  }
  document.getElementById("btn-prev-chapter-bottom")?.addEventListener("click", e => {
    e.stopPropagation();
    navigateChapter(-1);
  });
  document.getElementById("btn-next-chapter-bottom")?.addEventListener("click", e => {
    e.stopPropagation();
    navigateChapter(1);
  });
  document.getElementById("btn-floating-book-pill")?.addEventListener("click", e => {
    e.stopPropagation();
    triggerHaptic(10);
    openUnifiedBookSelector("READER");
  });

  // Gestos Swipe Horizontal (Izquierda/Derecha) en el Lector Bíblico
  let touchStartX = 0;
  let touchStartY = 0;
  versesListEl?.addEventListener("touchstart", e => {
    touchStartX = e.changedTouches[0].screenX;
    touchStartY = e.changedTouches[0].screenY;
  }, { passive: true });

  versesListEl?.addEventListener("touchend", e => {
    const dx = e.changedTouches[0].screenX - touchStartX;
    const dy = e.changedTouches[0].screenY - touchStartY;
    if (Math.abs(dx) > 75 && Math.abs(dx) > Math.abs(dy) * 1.8) {
      if (dx < 0) navigateChapter(1);
      else navigateChapter(-1);
    }
  }, { passive: true });

  // Selección de versículos y VerseActionBar
  function toggleVerseSelection(vNum, rowEl) {
    if (bookSelectorModal?.classList.contains("open")) return;
    triggerHaptic(10);
    if (state.selectedVerses.has(vNum)) {
      state.selectedVerses.delete(vNum);
      rowEl.classList.remove("selected");
    } else {
      state.selectedVerses.add(vNum);
      rowEl.classList.add("selected");
    }
    updateActionBarVisibility();
  }

  function clearVerseSelection() {
    state.selectedVerses.clear();
    document.querySelectorAll(".verse-row.selected").forEach(el => el.classList.remove("selected"));
    updateActionBarVisibility();
  }

  function updateActionBarVisibility() {
    const count = state.selectedVerses.size;
    const floatingNav = document.getElementById("reader-floating-nav");
    if (count > 0) {
      const wasVisible = actionBar.classList.contains("visible");
      document.getElementById("selection-count-text").textContent = `${count} selec.`;
      actionBar.classList.add("visible");
      mainBottomNav?.classList.add("auto-hidden");
      floatingNav?.classList.remove("visible");
      if (!wasVisible) pushBackNavState();
    } else {
      actionBar.classList.remove("visible");
      mainBottomNav?.classList.remove("auto-hidden");
      floatingNav?.classList.toggle("visible", state.currentTab === "reader");
      floatingNav?.classList.remove("docked-bottom");
    }
  }

  document.getElementById("btn-clear-selection")?.addEventListener("click", clearVerseSelection);

  function getSelectedVersesPayload() {
    const sorted = Array.from(state.selectedVerses).sort((a, b) => a - b);
    const book = getBookById(state.bookId);
    const texts = sorted.map(vNum => {
      const obj = state.currentChapterVerses.find(x => x.verse === vNum);
      return obj ? obj.text : "";
    });
    const rangeStr = sorted.length === 1 ? `${sorted[0]}` : `${sorted[0]}-${sorted[sorted.length - 1]}`;
    return {
      book,
      sorted,
      rangeStr,
      citation: `${book.name} ${state.chapter}:${rangeStr}`,
      combinedText: texts.join(" ")
    };
  }

  // Aplicar resaltado en el Lector y sincronizar con Favoritos > Resaltados
  document.querySelectorAll("#verse-action-bar .color-swatch").forEach(swatch => {
    swatch.addEventListener("click", () => {
      const hex = swatch.dataset.hex;
      const book = getBookById(state.bookId);
      state.selectedVerses.forEach(vNum => {
        const key = `${state.bookId}_${state.chapter}_${vNum}`;
        const vObj = state.currentChapterVerses.find(x => x.verse === vNum);
        state.highlights[key] = {
          color: hex,
          bookId: state.bookId,
          bookName: book.name,
          chapter: state.chapter,
          verse: vNum,
          text: vObj ? vObj.text : "",
          version: state.version,
          updatedAt: Date.now()
        };
        const row = versesListEl.querySelector(`.verse-row[data-verse="${vNum}"]`);
        if (row) row.setAttribute("data-hl", hex);
      });
      saveHighlightsState();
      refreshAdjacentHighlightMerging();
      clearVerseSelection();
      showToast("🎨 Versículo resaltado y guardado en tu colección");
    });
  });

  document.getElementById("btn-reset-highlight")?.addEventListener("click", () => {
    state.selectedVerses.forEach(vNum => {
      const key = `${state.bookId}_${state.chapter}_${vNum}`;
      delete state.highlights[key];
      const row = versesListEl.querySelector(`.verse-row[data-verse="${vNum}"]`);
      if (row) row.removeAttribute("data-hl");
    });
    saveHighlightsState();
    refreshAdjacentHighlightMerging();
    clearVerseSelection();
    showToast("Resaltado eliminado");
  });

  // =========================================================================
  // 6. LAS 6 ACCIONES DE VERSEACTIONBAR (Explicar, Comparar, Referencias, Imagen, Copiar, Guardar)
  // =========================================================================

  // ACCIÓN 1: EXPLICAR CON IA (ExplanationBottomSheet.kt)
  const explainModal = document.getElementById("explain-modal");
  let lastExplanationText = "";

  document.getElementById("btn-action-explain")?.addEventListener("click", async () => {
    if (state.selectedVerses.size === 0) return;
    const payload = getSelectedVersesPayload();
    const contentBox = document.getElementById("explain-content");
    const badgeEl = document.getElementById("explain-source-badge");

    explainModal.classList.add("open");
    contentBox.innerHTML = `
      <div class="detail-verse-quote">
        <strong>${payload.citation} (${state.version})</strong><br>
        «${payload.combinedText}»
      </div>
      <div id="explain-stream-body" style="line-height:1.62; padding:4px 2px; color:var(--text-secondary);">✨ Analizando ${payload.citation}...</div>
    `;
    const streamBodyEl = document.getElementById("explain-stream-body");

    const res = await generateVerseExegesisWithAI(
      payload.book.name,
      state.chapter,
      payload.rangeStr,
      payload.combinedText,
      state.version,
      partialText => {
        if (streamBodyEl) {
          streamBodyEl.style.color = "var(--text-primary)";
          streamBodyEl.innerHTML = formatChatMarkdownWithCitations(partialText);
        }
      }
    );
    lastExplanationText = cleanAiMarkdownToPlainText(`${payload.citation} (${state.version})\n«${payload.combinedText}»\n\n${res.text}`);
    if (badgeEl) badgeEl.textContent = `Fuente: ${res.source}`;
    if (streamBodyEl) {
      streamBodyEl.style.color = "var(--text-primary)";
      streamBodyEl.innerHTML = formatChatMarkdownWithCitations(res.text);
    }
  });

  document.getElementById("btn-close-explain")?.addEventListener("click", () => explainModal.classList.remove("open"));
  document.getElementById("btn-copy-explanation")?.addEventListener("click", () => {
    navigator.clipboard.writeText(lastExplanationText);
    showToast("📋 Explicación copiada al portapapeles");
  });
  document.getElementById("btn-share-explanation")?.addEventListener("click", () => {
    if (navigator.share) {
      navigator.share({ title: "Explicación Bíblica", text: lastExplanationText }).catch(() => {});
    } else {
      navigator.clipboard.writeText(lastExplanationText);
      showToast("📋 Explicación copiada");
    }
  });

  // ACCIÓN 2: COMPARAR VERSIONES DINÁMICO (VerseCompareBottomSheet.kt)
  const compareModal = document.getElementById("compare-modal");

  async function renderComparisonModal() {
    if (state.selectedVerses.size === 0) return;
    const payload = getSelectedVersesPayload();
    document.getElementById("compare-passage-title").textContent = payload.citation;

    // Barra de chips para activar/desactivar cualquiera de las 7 versiones
    const barEl = document.getElementById("compare-active-versions-bar");
    barEl.innerHTML = BIBLE_VERSIONS.map(v => {
      const isOn = state.compareVersions.includes(v.code);
      return `<button class="chip ${isOn ? "active" : ""}" data-vcode="${v.code}">${isOn ? "✓ " : "+ "}${v.shortName}</button>`;
    }).join("");

    barEl.querySelectorAll(".chip").forEach(chip => {
      chip.addEventListener("click", () => {
        const code = chip.dataset.vcode;
        if (state.compareVersions.includes(code)) {
          if (state.compareVersions.length > 1) {
            state.compareVersions = state.compareVersions.filter(c => c !== code);
          }
        } else {
          state.compareVersions.push(code);
        }
        localStorage.setItem("bv_compare_versions", JSON.stringify(state.compareVersions));
        renderComparisonModal();
      });
    });

    const listEl = document.getElementById("compare-versions-list");
    listEl.innerHTML = `<div style="text-align:center; padding:24px;">Consultando ${state.compareVersions.length} traducciones...</div>`;

    const fetched = await Promise.all(
      state.compareVersions.map(async code => {
        const vMeta = BIBLE_VERSIONS.find(x => x.code === code) || { code, name: code, tag: "" };
        const chData = await fetchChapterVerses(code, state.bookId, state.chapter);
        const textParts = payload.sorted.map(num => {
          const found = chData.verses.find(v => v.verse === num);
          return found ? found.text : "";
        }).filter(Boolean);
        return { vMeta, text: textParts.join(" ") || "Versículo no disponible en esta versión." };
      })
    );

    listEl.innerHTML = fetched.map(item => `
      <div class="detail-section-card">
        <div class="detail-section-header" style="margin-bottom:6px;">
          <div>
            <strong style="color:var(--primary-color);">${item.vMeta.name} (${item.vMeta.code})</strong>
            <span class="testament-mini-badge">${item.vMeta.tag}</span>
          </div>
          <button class="action-pill btn-copy-single-ver" data-vtext="«${item.text.replace(/"/g, "&quot;")}» — ${payload.citation} (${item.vMeta.code})">
            📋 Copiar
          </button>
        </div>
        <p style="font-family:var(--reader-font); font-size:0.96rem; line-height:1.6;">«${item.text}»</p>
      </div>
    `).join("");

    listEl.querySelectorAll(".btn-copy-single-ver").forEach(btn => {
      btn.addEventListener("click", () => {
        navigator.clipboard.writeText(btn.dataset.vtext);
        showToast("📋 Traducción copiada");
      });
    });
  }

  document.getElementById("btn-action-compare")?.addEventListener("click", () => {
    if (state.selectedVerses.size === 0) return;
    compareModal.classList.add("open");
    renderComparisonModal();
  });
  document.getElementById("btn-close-compare")?.addEventListener("click", () => compareModal.classList.remove("open"));

  // ACCIÓN 3: REFERENCIAS CRUZADAS Y PARALELOS (CrossReferencesBottomSheet.kt)
  const xrefModal = document.getElementById("xref-modal");

  async function renderXrefItemsIntoModal(items, { isParallelMode = false } = {}) {
    const container = document.getElementById("xref-list-container");
    if (items.length === 0) {
      container.innerHTML = `<div style="text-align:center; padding:24px; color:var(--text-secondary);">No hay ${isParallelMode ? "pasajes paralelos" : "referencias cruzadas directas"} registradas para este pasaje.</div>`;
      return;
    }

    const enriched = await Promise.all(items.map(async ref => {
      const chData = await fetchChapterVerses(state.version, ref.bookId, ref.chapter);
      const vObj = chData.verses.find(v => v.verse === ref.startVerse);
      return {
        ...ref,
        previewText: vObj ? vObj.text : ""
      };
    }));

    container.innerHTML = enriched.map(item => `
      <div class="detail-section-card xref-item-card" style="cursor:pointer;" data-bid="${item.bookId}" data-ch="${item.chapter}" data-v="${item.startVerse}">
        <div class="detail-section-header" style="margin-bottom:4px;">
          <strong style="color:var(--primary-color);">🔗 ${item.reference}</strong>
          <span class="testament-mini-badge">${isParallelMode ? "Paralelo Canónico" : (item.isExact ? "Directa" : `Capítulo (v.${item.sourceVerse})`)}</span>
        </div>
        ${item.note ? `<p style="font-size:0.8rem; color:var(--accent-gold); margin-bottom:6px; line-height:1.4;">${item.note}</p>` : ""}
        <p style="font-family:var(--font-serif); font-size:0.9rem; color:var(--text-primary); line-height:1.5;">
          «${item.previewText}»
        </p>
      </div>
    `).join("");

    container.querySelectorAll(".xref-item-card").forEach(card => {
      card.addEventListener("click", () => {
        triggerHaptic(10);
        state.bookId = Number(card.dataset.bid);
        state.chapter = Number(card.dataset.ch);
        state.targetScrollVerse = Number(card.dataset.v);
        xrefModal.classList.remove("open");
        clearVerseSelection();
        loadReaderChapter(false);
      });
    });
  }

  async function openChapterParallelsModal() {
    const book = getBookById(state.bookId);
    const parallels = getChapterParallelReferences(state.bookId, state.chapter);
    document.getElementById("xref-subtitle").textContent = `Pasajes Paralelos • ${book.name} ${state.chapter}`;
    document.getElementById("xref-list-container").innerHTML = `<div style="text-align:center; padding:24px;">Cargando pasajes paralelos canónicos...</div>`;
    xrefModal.classList.add("open");
    await renderXrefItemsIntoModal(parallels, { isParallelMode: true });
  }

  document.getElementById("btn-action-references")?.addEventListener("click", async () => {
    if (state.selectedVerses.size === 0) return;
    const payload = getSelectedVersesPayload();
    const primaryVerse = payload.sorted[0];
    document.getElementById("xref-subtitle").textContent = `${payload.book.name} ${state.chapter}:${primaryVerse}`;
    document.getElementById("xref-list-container").innerHTML = `<div style="text-align:center; padding:24px;">Cargando referencias cruzadas canónicas...</div>`;
    xrefModal.classList.add("open");

    const { exactRefs, chapterRefs } = await getCrossReferences(state.bookId, state.chapter, primaryVerse);
    const markedExact = exactRefs.map(r => ({ ...r, isExact: true }));
    const markedChap = chapterRefs.map(r => ({ ...r, isExact: false }));
    const allRefs = [...markedExact, ...markedChap].slice(0, 16);
    await renderXrefItemsIntoModal(allRefs, { isParallelMode: false });
  });

  document.getElementById("btn-close-xref")?.addEventListener("click", () => xrefModal.classList.remove("open"));

  // ACCIÓN 4: DISEÑADOR DE IMAGEN EN CANVAS CON 4 PLANTILLAS + 12 FONDOS .WEBP (ShareTemplateDialog.kt)
  const imageShareModal = document.getElementById("image-share-modal");
  let currentShareVerseText = "";
  let currentShareCitation = "";
  let currentShareMode = { type: "tpl", value: "minimalist" }; // o { type: "photo", value: "01" }
  const loadedBgImages = {};

  function initPhotoBackgroundThumbs() {
    const container = document.getElementById("share-photo-bgs");
    if (!container) return;
    const nums = Array.from({ length: 12 }, (_, i) => String(i + 1).padStart(2, "0"));
    container.innerHTML = nums.map(n => `
      <button class="bg-thumb-btn" data-bgnum="${n}" title="Fondo ${n}">
        <img src="./assets/drawable/verse_bg_${n}.webp" alt="Fondo ${n}" loading="lazy">
      </button>
    `).join("");

    container.querySelectorAll(".bg-thumb-btn").forEach(btn => {
      btn.addEventListener("click", () => {
        document.querySelectorAll("#share-style-templates .chip, .bg-thumb-btn").forEach(el => el.classList.remove("active"));
        btn.classList.add("active");
        currentShareMode = { type: "photo", value: btn.dataset.bgnum };
        drawShareCanvas();
      });
    });
  }

  document.querySelectorAll("#share-style-templates .chip").forEach(chip => {
    chip.addEventListener("click", () => {
      document.querySelectorAll("#share-style-templates .chip, .bg-thumb-btn").forEach(el => el.classList.remove("active"));
      chip.classList.add("active");
      currentShareMode = { type: "tpl", value: chip.dataset.tpl };
      drawShareCanvas();
    });
  });

  function openShareImageDesigner(verseText, citationText) {
    currentShareVerseText = verseText;
    currentShareCitation = citationText;
    imageShareModal.classList.add("open");
    drawShareCanvas();
  }

  async function drawShareCanvas() {
    const canvas = document.getElementById("share-canvas");
    if (!canvas) return;
    const ctx = canvas.getContext("2d");
    const W = canvas.width;
    const H = canvas.height;

    let textColor = "#F8FAFC";
    let accentColor = "#FBBF24";

    if (currentShareMode.type === "photo") {
      const num = currentShareMode.value;
      const src = `./assets/drawable/verse_bg_${num}.webp`;
      if (!loadedBgImages[num]) {
        await new Promise(resolve => {
          const img = new Image();
          img.crossOrigin = "anonymous";
          img.onload = () => { loadedBgImages[num] = img; resolve(); };
          img.onerror = () => resolve();
          img.src = src;
        });
      }
      if (loadedBgImages[num]) {
        ctx.drawImage(loadedBgImages[num], 0, 0, W, H);
      }
      // Oscurecer con viñeta elegante para legibilidad perfecta
      const grad = ctx.createLinearGradient(0, 0, 0, H);
      grad.addColorStop(0, "rgba(10, 15, 28, 0.55)");
      grad.addColorStop(0.5, "rgba(10, 15, 28, 0.68)");
      grad.addColorStop(1, "rgba(10, 15, 28, 0.85)");
      ctx.fillStyle = grad;
      ctx.fillRect(0, 0, W, H);
    } else {
      const tpl = currentShareMode.value;
      const grad = ctx.createLinearGradient(0, 0, W, H);
      if (tpl === "minimalist") {
        grad.addColorStop(0, "#FBF9F5");
        grad.addColorStop(1, "#EAE0C8");
        textColor = "#1E293B";
        accentColor = "#9E6B28";
      } else if (tpl === "sacred") {
        grad.addColorStop(0, "#1E3A5F");
        grad.addColorStop(1, "#0F172A");
        textColor = "#F8FAFC";
        accentColor = "#E5B869";
      } else if (tpl === "parchment") {
        grad.addColorStop(0, "#FBF0D9");
        grad.addColorStop(1, "#EFE1C6");
        textColor = "#3E2723";
        accentColor = "#8C5828";
      } else {
        grad.addColorStop(0, "#090D16");
        grad.addColorStop(1, "#000000");
        textColor = "#F1F5F9";
        accentColor = "#38BDF8";
      }
      ctx.fillStyle = grad;
      ctx.fillRect(0, 0, W, H);
    }

    // Marco decorativo interno
    ctx.strokeStyle = accentColor;
    ctx.globalAlpha = 0.45;
    ctx.lineWidth = 4;
    ctx.strokeRect(64, 64, W - 128, H - 128);
    ctx.globalAlpha = 1;

    // Texto envuelto centrado
    ctx.fillStyle = textColor;
    const fontSize = currentShareVerseText.length > 220 ? 38 : (currentShareVerseText.length > 130 ? 44 : 50);
    ctx.font = `italic 600 ${fontSize}px Georgia, serif`;
    ctx.textAlign = "center";

    const words = `«${currentShareVerseText}»`.split(" ");
    const lines = [];
    let line = "";
    const maxWidth = W - 220;

    for (const w of words) {
      const test = line + w + " ";
      if (ctx.measureText(test).width > maxWidth && line) {
        lines.push(line.trim());
        line = w + " ";
      } else {
        line = test;
      }
    }
    if (line) lines.push(line.trim());

    const lineHeight = fontSize * 1.48;
    let startY = (H - lines.length * lineHeight) / 2 - 20;
    for (const l of lines) {
      ctx.fillText(l, W / 2, startY);
      startY += lineHeight;
    }

    // Cita bíblica
    ctx.fillStyle = accentColor;
    ctx.font = "bold 36px sans-serif";
    ctx.fillText(currentShareCitation, W / 2, startY + 46);

    // Marca de agua BibleVerse
    ctx.fillStyle = textColor;
    ctx.globalAlpha = 0.65;
    ctx.font = "600 24px sans-serif";
    ctx.fillText("BibleVerse • La Palabra Viva", W / 2, H - 100);
    ctx.globalAlpha = 1;
  }

  document.getElementById("btn-action-image")?.addEventListener("click", () => {
    if (state.selectedVerses.size === 0) return;
    const payload = getSelectedVersesPayload();
    openShareImageDesigner(payload.combinedText, `${payload.citation} (${state.version})`);
    clearVerseSelection();
  });

  document.getElementById("btn-close-image-share")?.addEventListener("click", () => imageShareModal.classList.remove("open"));
  document.getElementById("btn-copy-share-citation")?.addEventListener("click", () => {
    navigator.clipboard.writeText(`«${currentShareVerseText}» — ${currentShareCitation}`);
    showToast("📋 Cita copiada");
  });
  document.getElementById("btn-download-share-image")?.addEventListener("click", async () => {
    const canvas = document.getElementById("share-canvas");
    const fileName = `BibleVerse_${currentShareCitation.replace(/[^a-zA-Z0-9]/g, "_")}.jpg`;
    canvas.toBlob(async (blob) => {
      if (blob && navigator.canShare) {
        const file = new File([blob], fileName, { type: "image/jpeg" });
        if (navigator.canShare({ files: [file] })) {
          try {
            await navigator.share({
              files: [file],
              title: currentShareCitation,
              text: `«${currentShareVerseText}» — ${currentShareCitation}`
            });
            return;
          } catch {
            // Cancelado o fallback a descarga
          }
        }
      }
      const link = document.createElement("a");
      link.download = fileName;
      link.href = canvas.toDataURL("image/jpeg", 0.94);
      link.click();
      showToast("🖼️ Imagen HD descargada");
    }, "image/jpeg", 0.94);
  });

  // ACCIÓN 5, 6 Y 7: COMPARTIR TEXTO, COPIAR Y GUARDAR EN COLECCIÓN
  document.getElementById("btn-action-share")?.addEventListener("click", () => {
    if (state.selectedVerses.size === 0) return;
    const payload = getSelectedVersesPayload();
    const shareText = `«${payload.combinedText}» — ${payload.citation} (${state.version})`;
    clearVerseSelection();
    if (navigator.share) {
      navigator.share({ title: `${payload.citation} (${state.version})`, text: shareText }).catch(() => {});
    } else {
      navigator.clipboard.writeText(shareText);
      showToast("📋 Texto copiado para compartir");
    }
  });
  document.getElementById("btn-action-copy")?.addEventListener("click", () => {
    if (state.selectedVerses.size === 0) return;
    const payload = getSelectedVersesPayload();
    navigator.clipboard.writeText(`«${payload.combinedText}» — ${payload.citation} (${state.version})`);
    clearVerseSelection();
    showToast("📋 Copiado al portapapeles");
  });

  document.getElementById("btn-action-save")?.addEventListener("click", () => {
    if (state.selectedVerses.size === 0) return;
    const payload = getSelectedVersesPayload();
    const existing = state.versesCollection.find(v => v.reference === payload.citation);
    if (existing) {
      existing.isFavorite = true;
    } else {
      state.versesCollection.unshift({
        id: Date.now(),
        book: payload.book.name,
        chapterVerse: `${state.chapter}:${payload.rangeStr}`,
        reference: payload.citation,
        testament: payload.book.testamentFull,
        text: payload.combinedText,
        context: getLocalScholarlyExegesis(payload.book.name, state.chapter, payload.rangeStr, payload.combinedText),
        topic: "Guardados del Lector",
        bibleVersion: state.version,
        isFavorite: true,
        isHighlighted: false,
        highlightColor: null,
        notes: "",
        isCustom: true
      });
    }
    saveVersesUserState();
    clearVerseSelection();
    showToast("🔖 Guardado en tus Favoritos");
  });

  // =========================================================================
  // 7. SELECTOR UNIFICADO DE LIBROS ESTILO APK (BookChapterSelectorSheet.kt)
  // =========================================================================
  const bookSelectorModal = document.getElementById("book-selector-modal");
  let tempPickerBook = getBookById(state.bookId);
  let tempPickerChapter = state.chapter;
  let pickerTestamentFilter = "ALL";
  let currentPickerMode = "READER"; // "READER" | "VERSES_FILTER" | "SEARCH_FILTER"
  let currentPickerStep = 1;

  function showPickerStep(stepNum) {
    const prevStep = currentPickerStep;
    currentPickerStep = stepNum;
    if (stepNum > 1 && prevStep !== stepNum) {
      pushBackNavState();
    }
    document.getElementById("selector-step-books").style.display = stepNum === 1 ? "block" : "none";
    document.getElementById("selector-step-chapters").style.display = stepNum === 2 ? "block" : "none";
    document.getElementById("selector-step-verses").style.display = stepNum === 3 ? "block" : "none";

    document.getElementById("step-tab-book")?.classList.toggle("active", stepNum === 1);
    document.getElementById("step-tab-chapter")?.classList.toggle("active", stepNum === 2);
    document.getElementById("step-tab-verse")?.classList.toggle("active", stepNum === 3);

    const backBtn = document.getElementById("btn-book-selector-back");
    if (backBtn) backBtn.style.display = ((currentPickerMode === "READER" || currentPickerMode === "ADD_VERSE") && stepNum > 1) ? "inline-flex" : "none";

    const titleEl = document.getElementById("book-selector-title");
    if (titleEl) {
      if (stepNum === 1) titleEl.textContent = "Seleccionar Libro";
      else if (stepNum === 2) titleEl.textContent = `${tempPickerBook.name} — Capítulos`;
      else titleEl.textContent = `${tempPickerBook.name} ${tempPickerChapter} — Versículos`;
    }
  }

  function openUnifiedBookSelector(mode = "READER") {
    currentPickerMode = mode;
    if (mode === "READER") {
      clearVerseSelection();
    }
    const stepTabs = document.getElementById("book-selector-step-tabs");
    if (stepTabs) stepTabs.style.display = (mode === "READER" || mode === "ADD_VERSE") ? "flex" : "none";

    const searchBox = document.getElementById("book-search-input");
    if (searchBox) searchBox.value = "";
    pickerTestamentFilter = "ALL";
    document.querySelectorAll(".testament-tab").forEach(t => {
      t.classList.toggle("active", t.dataset.filter === "ALL");
    });

    tempPickerBook = mode === "ADD_VERSE" ? getBookById(Number(addBookSel?.value || state.bookId)) : getBookById(state.bookId);
    tempPickerChapter = state.chapter;
    showPickerStep(1);
    renderPickerBooksList("");
    bookSelectorModal.classList.add("open");

    setTimeout(() => {
      const activeItem = document.querySelector("#selector-books-list .book-selector-item.selected");
      if (activeItem) activeItem.scrollIntoView({ block: "center" });
    }, 60);
  }

  document.getElementById("btn-select-book")?.addEventListener("click", () => openUnifiedBookSelector("READER"));
  document.getElementById("btn-book-selector-back")?.addEventListener("click", () => {
    if (currentPickerStep > 1) showPickerStep(currentPickerStep - 1);
  });

  document.getElementById("btn-close-book-selector")?.addEventListener("click", () => bookSelectorModal.classList.remove("open"));
  document.getElementById("step-tab-book")?.addEventListener("click", () => showPickerStep(1));
  document.getElementById("step-tab-chapter")?.addEventListener("click", () => { renderPickerChaptersGrid(tempPickerBook); showPickerStep(2); });
  document.getElementById("step-tab-verse")?.addEventListener("click", () => { renderPickerVersesGrid(tempPickerBook, tempPickerChapter); showPickerStep(3); });
  document.getElementById("btn-back-to-books")?.addEventListener("click", () => showPickerStep(1));
  document.getElementById("btn-back-to-chapters")?.addEventListener("click", () => showPickerStep(2));

  document.querySelectorAll(".testament-tab").forEach(tab => {
    tab.addEventListener("click", () => {
      document.querySelectorAll(".testament-tab").forEach(t => t.classList.remove("active"));
      tab.classList.add("active");
      pickerTestamentFilter = tab.dataset.filter;
      renderPickerBooksList(document.getElementById("book-search-input")?.value || "");
    });
  });

  document.getElementById("book-search-input")?.addEventListener("input", e => {
    renderPickerBooksList(e.target.value);
  });

  function renderPickerBooksList(searchStr = "") {
    const container = document.getElementById("selector-books-list");
    if (!container) return;
    const q = stripAccents(searchStr).toLowerCase();

    let verseCounts = null;
    if (currentPickerMode === "VERSES_FILTER") {
      verseCounts = {};
      state.versesCollection.forEach(v => {
        verseCounts[v.book] = (verseCounts[v.book] || 0) + 1;
      });
    }

    const filtered = BIBLE_BOOKS.filter(b => {
      if (pickerTestamentFilter !== "ALL" && b.testament !== pickerTestamentFilter) return false;
      return stripAccents(b.name).toLowerCase().includes(q) || stripAccents(b.category).toLowerCase().includes(q);
    });

    let html = "";
    filtered.forEach((b, idx) => {
      const prev = idx > 0 ? filtered[idx - 1] : null;
      if (prev && prev.id <= 39 && b.id > 39) {
        html += `<div class="book-nt-divider"><span>Nuevo Testamento</span></div>`;
      }

      let isCurrent = false;
      if (currentPickerMode === "READER") isCurrent = b.id === state.bookId;
      else if (currentPickerMode === "VERSES_FILTER") isCurrent = b.name === state.versesBookFilter;
      else if (currentPickerMode === "SEARCH_FILTER") isCurrent = b.id === currentSearchBookId;
      else if (currentPickerMode === "ADD_VERSE") isCurrent = b.id === Number(addBookSel?.value);

      const subText = verseCounts
        ? `${b.category} • ${verseCounts[b.name] || 0} versículos`
        : `${b.category} • ${b.chapters} cap.`;

      html += `
        <div class="book-selector-item ${isCurrent ? "selected" : ""}" data-bid="${b.id}">
          <div class="book-selector-left">
            <div class="book-order-circle">${b.id}</div>
            <div>
              <strong style="font-size:0.94rem;">${b.name}</strong>
              <div style="font-size:0.74rem; color:var(--text-secondary);">${subText}</div>
            </div>
          </div>
          <span style="font-size:1.25rem; color:var(--text-secondary);">›</span>
        </div>
      `;
    });
    container.innerHTML = html;

    container.querySelectorAll(".book-selector-item").forEach(row => {
      row.addEventListener("click", () => {
        const picked = getBookById(Number(row.dataset.bid));
        if (currentPickerMode === "VERSES_FILTER") {
          state.versesBookFilter = picked.name;
          state.versesTestamentFilter = "ALL";
          document.querySelectorAll("#canonical-filter-chips .chip").forEach(c => c.classList.remove("active"));
          const bookChip = document.getElementById("btn-open-book-filter-modal");
          if (bookChip) {
            bookChip.textContent = `📚 ${picked.name} ✕`;
            bookChip.classList.add("active");
          }
          bookSelectorModal.classList.remove("open");
          renderPopularVersesFeed();
        } else if (currentPickerMode === "SEARCH_FILTER") {
          currentSearchBookId = picked.id;
          currentSearchScope = "BOOK";
          document.querySelectorAll("#search-scope-chips .chip").forEach(c => c.classList.remove("active"));
          const searchBookBtn = document.getElementById("btn-search-book-picker");
          if (searchBookBtn) {
            searchBookBtn.textContent = `📚 ${picked.name} ✕`;
            searchBookBtn.classList.add("active");
          }
          bookSelectorModal.classList.remove("open");
          triggerFullBibleSearch();
        } else {
          tempPickerBook = picked;
          renderPickerChaptersGrid(tempPickerBook);
          showPickerStep(2);
        }
      });
    });
  }

  function renderPickerChaptersGrid(book) {
    document.getElementById("selected-book-title-chapters").textContent = `${book.name} (${book.chapters} capítulos)`;
    const grid = document.getElementById("selector-chapters-grid");
    grid.innerHTML = Array.from({ length: book.chapters }, (_, i) => {
      const ch = i + 1;
      const isCur = book.id === state.bookId && ch === state.chapter;
      return `<button class="chapter-square-btn ${isCur ? "active" : ""}" data-ch="${ch}">${ch}</button>`;
    }).join("");

    grid.querySelectorAll(".chapter-square-btn").forEach(btn => {
      btn.addEventListener("click", () => {
        tempPickerChapter = Number(btn.dataset.ch);
        renderPickerVersesGrid(tempPickerBook, tempPickerChapter);
        showPickerStep(3);
      });
    });
  }

  function renderPickerVersesGrid(book, chapter) {
    const vCount = getCanonicalVerseCount(book.id, chapter);
    document.getElementById("selected-chapter-title-verses").textContent = `${book.name} ${chapter} (${vCount} versículos)`;
    const grid = document.getElementById("selector-verses-grid");
    grid.innerHTML = Array.from({ length: vCount }, (_, i) => {
      const v = i + 1;
      return `<button class="chapter-square-btn" data-v="${v}">${v}</button>`;
    }).join("");

    grid.querySelectorAll(".chapter-square-btn").forEach(btn => {
      btn.addEventListener("click", () => {
        const pickedVerse = Number(btn.dataset.v);
        if (currentPickerMode === "ADD_VERSE") {
          if (addBookSel) addBookSel.value = String(book.id);
          updateAddVerseChapters(chapter, pickedVerse);
          bookSelectorModal.classList.remove("open");
          return;
        }
        state.bookId = book.id;
        state.chapter = chapter;
        state.targetScrollVerse = pickedVerse;
        bookSelectorModal.classList.remove("open");
        loadReaderChapter(false);
      });
    });
  }

  document.getElementById("btn-open-chapter-from-verse-step")?.addEventListener("click", () => {
    if (currentPickerMode === "ADD_VERSE") {
      if (addBookSel) addBookSel.value = String(tempPickerBook.id);
      updateAddVerseChapters(tempPickerChapter, 1);
      bookSelectorModal.classList.remove("open");
      return;
    }
    state.bookId = tempPickerBook.id;
    state.chapter = tempPickerChapter;
    state.targetScrollVerse = null;
    bookSelectorModal.classList.remove("open");
    loadReaderChapter(false);
  });

  // =========================================================================
  // 8. SELECTOR DE VERSIONES BÍBLICAS (BibleVersionPickerSheet.kt)
  // =========================================================================
  const versionModal = document.getElementById("version-selector-modal");
  let currentVersionPickerMode = "READER";

  function openVersionSelectorModal(mode = "READER") {
    currentVersionPickerMode = mode;
    renderVersionsPicker(mode);
    versionModal.classList.add("open");
  }

  async function renderVersionsPicker(mode = "READER") {
    const container = document.getElementById("versions-list-container");
    if (!container) return;
    const activeCode = mode === "ADD_VERSE" ? (addVerSel?.value || state.version) : state.version;

    const counts = {};
    await Promise.all(
      BIBLE_VERSIONS.filter(v => !v.isOfflineAsset).map(async v => {
        counts[v.code] = await getOfflineBookCacheCount(v.code);
      })
    );

    container.innerHTML = BIBLE_VERSIONS.map(v => {
      const downloading = activeOfflineDownloads[v.code];
      const cachedCount = counts[v.code] || 0;
      const isDownloaded = !v.isOfflineAsset && cachedCount >= 66;
      let statusBadge = `<span class="testament-mini-badge" style="color:#16A34A;">✓ Offline Integrada</span>`;
      if (!v.isOfflineAsset) {
        if (isDownloaded) {
          statusBadge = `<span class="testament-mini-badge" style="color:#16A34A;">✓ Descargada Offline</span>`;
        } else if (downloading) {
          const pct = Math.round((downloading.current / downloading.total) * 100);
          statusBadge = `<button type="button" class="version-dl-btn" disabled>⏳ ${pct}%</button>`;
        } else {
          statusBadge = `<button type="button" class="version-dl-btn" data-dl-ver="${v.code}">⬇ Descargar</button>`;
        }
      }
      return `
        <div class="version-card-row ${v.code === activeCode ? "selected" : ""}" data-code="${v.code}">
          <div style="padding-right:8px; min-width:0; flex:1;">
            <div style="display:flex; align-items:center; flex-wrap:wrap; gap:6px; margin-bottom:4px;">
              <strong style="color:var(--primary-color);">${v.name}</strong>
              <span class="testament-mini-badge">${v.tag}</span>
              ${statusBadge}
            </div>
            <p style="font-size:0.78rem; color:var(--text-secondary); line-height:1.4;">${v.desc}</p>
          </div>
          <strong style="color:var(--secondary-color); flex-shrink:0;">${v.shortName}</strong>
        </div>
      `;
    }).join("");

    container.querySelectorAll("[data-dl-ver]").forEach(btn => {
      btn.addEventListener("click", (e) => {
        e.stopPropagation();
        triggerHaptic(10);
        downloadVersionOffline(btn.dataset.dlVer);
      });
    });

    container.querySelectorAll(".version-card-row").forEach(row => {
      row.addEventListener("click", () => {
        triggerHaptic(10);
        const pickedCode = row.dataset.code;
        if (currentVersionPickerMode === "ADD_VERSE") {
          updateAddVerseVersionCard(pickedCode);
          versionModal.classList.remove("open");
          autofillAddVerseText();
        } else {
          state.version = pickedCode;
          versionModal.classList.remove("open");
          loadReaderChapter(false);
          renderVerseOfTheDay();
          showToast(`📖 Versión cambiada a ${state.version}`);
        }
      });
    });
  }

  document.getElementById("btn-open-version-selector")?.addEventListener("click", () => {
    openVersionSelectorModal("READER");
  });
  document.getElementById("btn-close-version-selector")?.addEventListener("click", () => versionModal.classList.remove("open"));

  // =========================================================================
  // 9. REPRODUCTOR DE AUDIO DEL LECTOR (BibleAudioBottomBar.kt - MP3 Oficial + TTS)
  // =========================================================================
  const audioBar = document.getElementById("bible-audio-bar");
  const mp3Player = document.getElementById("official-mp3-player");
  const playPauseBtn = document.getElementById("btn-audio-play-pause");
  const speeds = [0.75, 1.0, 1.25, 1.5];

  document.getElementById("btn-toggle-audio-bar")?.addEventListener("click", () => {
    const isHidden = audioBar.style.display === "none";
    audioBar.style.display = isHidden ? "block" : "none";
    if (isHidden) {
      state.audioCurrentVerseIndex = 0;
      startChapterAudioPlayback();
    } else {
      stopChapterAudioPlayback();
    }
  });

  document.getElementById("btn-close-audio-bar")?.addEventListener("click", () => {
    stopChapterAudioPlayback();
    audioBar.style.display = "none";
  });

  document.getElementById("btn-audio-mode-tts")?.addEventListener("click", () => {
    state.audioMode = "TTS";
    document.getElementById("btn-audio-mode-tts").classList.add("active");
    document.getElementById("btn-audio-mode-mp3").classList.remove("active");
    if (state.audioPlaying) startChapterAudioPlayback();
  });

  document.getElementById("btn-audio-mode-mp3")?.addEventListener("click", () => {
    state.audioMode = "MP3";
    document.getElementById("btn-audio-mode-mp3").classList.add("active");
    document.getElementById("btn-audio-mode-tts").classList.remove("active");
    if (state.audioPlaying) startChapterAudioPlayback();
  });

  document.getElementById("btn-audio-play-pause")?.addEventListener("click", () => {
    if (state.audioPlaying) {
      pauseChapterAudioPlayback();
    } else {
      startChapterAudioPlayback();
    }
  });

  document.getElementById("btn-audio-prev")?.addEventListener("click", () => {
    if (state.audioCurrentVerseIndex > 0) state.audioCurrentVerseIndex--;
    if (state.audioPlaying) startChapterAudioPlayback();
  });

  document.getElementById("btn-audio-next")?.addEventListener("click", () => {
    if (state.audioCurrentVerseIndex < state.currentChapterVerses.length - 1) {
      state.audioCurrentVerseIndex++;
      if (state.audioPlaying) startChapterAudioPlayback();
    }
  });

  document.getElementById("btn-audio-speed")?.addEventListener("click", e => {
    const idx = (speeds.indexOf(state.audioSpeed) + 1) % speeds.length;
    state.audioSpeed = speeds[idx];
    e.target.textContent = `${state.audioSpeed}x`;
    if (mp3Player) mp3Player.playbackRate = state.audioSpeed;
    if (state.audioPlaying && state.audioMode === "TTS") startChapterAudioPlayback();
  });

  async function startChapterAudioPlayback() {
    const book = getBookById(state.bookId);
    document.getElementById("audio-bar-title").textContent = `${book.name} ${state.chapter} • ${state.version}`;
    state.audioPlaying = true;
    if (playPauseBtn) playPauseBtn.textContent = "⏸";

    window.speechSynthesis?.cancel();
    if (mp3Player) mp3Player.pause();

    if (state.audioMode === "MP3") {
      const mp3Url = await getOfficialChapterAudioUrl(state.version, state.bookId, state.chapter);
      if (mp3Url && mp3Player) {
        document.getElementById("audio-bar-subtitle").textContent = "🎵 Reproduciendo Audio Dramatizado Oficial";
        mp3Player.src = mp3Url;
        mp3Player.playbackRate = state.audioSpeed;
        mp3Player.play().catch(() => {
          state.audioMode = "TTS";
          startChapterAudioPlayback();
        });
        mp3Player.onended = () => {
          state.audioPlaying = false;
          if (playPauseBtn) playPauseBtn.textContent = "▶";
        };
        return;
      } else {
        showToast("Audio MP3 no disponible para este capítulo; usando Voz Guía");
        state.audioMode = "TTS";
      }
    }

    speakCurrentVerseTTS();
  }

  function speakCurrentVerseTTS() {
    if (!state.audioPlaying || !("speechSynthesis" in window)) return;
    const verses = state.currentChapterVerses;
    if (state.audioCurrentVerseIndex >= verses.length) {
      state.audioPlaying = false;
      if (playPauseBtn) playPauseBtn.textContent = "▶";
      document.querySelectorAll(".verse-row.audio-reading").forEach(el => el.classList.remove("audio-reading"));
      return;
    }

    const vObj = verses[state.audioCurrentVerseIndex];
    const book = getBookById(state.bookId);
    document.getElementById("audio-bar-subtitle").textContent = `🗣️ Leyendo versículo ${vObj.verse} de ${verses.length}`;

    document.querySelectorAll(".verse-row.audio-reading").forEach(el => el.classList.remove("audio-reading"));
    const rowEl = document.getElementById(`reader-v-${vObj.verse}`);
    if (rowEl) {
      rowEl.classList.add("audio-reading");
      rowEl.scrollIntoView({ behavior: "smooth", block: "center" });
    }

    const utter = new SpeechSynthesisUtterance(
      state.audioCurrentVerseIndex === 0
        ? `${book.name}, capítulo ${state.chapter}. ${vObj.text}`
        : vObj.text
    );
    utter.lang = "es-ES";
    utter.rate = state.audioSpeed;
    utter.onend = () => {
      if (!state.audioPlaying) return;
      state.audioCurrentVerseIndex++;
      speakCurrentVerseTTS();
    };
    window.speechSynthesis.speak(utter);
  }

  function pauseChapterAudioPlayback() {
    state.audioPlaying = false;
    if (playPauseBtn) playPauseBtn.textContent = "▶";
    window.speechSynthesis?.cancel();
    if (mp3Player) mp3Player.pause();
  }

  function stopChapterAudioPlayback() {
    pauseChapterAudioPlayback();
    document.querySelectorAll(".verse-row.audio-reading").forEach(el => el.classList.remove("audio-reading"));
  }

  // =========================================================================
  // 10. AJUSTES DEL LECTOR BÍBLICO (ReaderSettingsBottomSheet.kt - Aa)
  // =========================================================================
  const readerSettingsModal = document.getElementById("settings-modal");

  function syncSystemThemeBars() {
    const appBgMap = {
      "light": "#F8F9FA",
      "sepia-night": "#F4EAD2",
      "dark": "#0F141C"
    };
    const readerBgMap = {
      "reader-light": "#FFFFFF",
      "reader-sepia": "#FBF0D9",
      "reader-dark": "#121417",
      "reader-night": "#090D16"
    };
    const color = state.currentTab === "reader"
      ? (readerBgMap[state.readerTheme] || "#0F141C")
      : (appBgMap[state.theme] || "#0F141C");

    const metaTheme = document.getElementById("meta-theme-color") || document.querySelector('meta[name="theme-color"]');
    if (metaTheme) metaTheme.setAttribute("content", color);
    document.documentElement.style.backgroundColor = color;
    document.body.style.backgroundColor = color;
  }

  function applyReaderVisualPreferences() {
    if (state.theme === "light") {
      document.documentElement.removeAttribute("data-theme");
    } else {
      document.documentElement.setAttribute("data-theme", state.theme);
    }
    syncSystemThemeBars();

    const readerTab = document.getElementById("tab-reader");
    if (readerTab) {
      readerTab.setAttribute("data-reader-theme", state.readerTheme);
    }

    const fontMap = {
      serif: '"Merriweather", Georgia, "Times New Roman", serif',
      sans: '-apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, sans-serif',
      condensed: '"Arial Narrow", "Roboto Condensed", sans-serif',
      casual: '"Trebuchet MS", "Comic Sans MS", sans-serif',
      cursive: '"Palatino Linotype", Georgia, serif',
      mono: '"JetBrains Mono", "Courier New", monospace'
    };

    document.documentElement.style.setProperty("--reader-font", fontMap[state.fontFamily] || fontMap.serif);
    document.documentElement.style.setProperty("--reader-size", `${state.fontSize}px`);
    document.documentElement.style.setProperty("--reader-line-height", String(state.lineHeight));

    const lbl1 = document.getElementById("font-size-label");
    const lbl2 = document.getElementById("settings-font-val");
    if (lbl1) lbl1.textContent = `${state.fontSize}px`;
    if (lbl2) lbl2.textContent = `${state.fontSize}px`;

    document.querySelectorAll(".theme-option-card").forEach(btn => {
      btn.classList.toggle("active", btn.dataset.theme === state.theme);
    });
    document.querySelectorAll(".reader-theme-btn").forEach(btn => {
      btn.classList.toggle("active", btn.dataset.readerTheme === state.readerTheme);
    });
    document.querySelectorAll(".font-family-btn").forEach(btn => {
      btn.classList.toggle("active", btn.dataset.font === state.fontFamily);
    });
    document.querySelectorAll(".line-spacing-btn").forEach(btn => {
      btn.classList.toggle("active", Math.abs(parseFloat(btn.dataset.lh) - state.lineHeight) < 0.05);
    });

    const rlSwitch = document.getElementById("toggle-red-letters");
    const shSwitch = document.getElementById("toggle-section-headings");
    const csSwitch = document.getElementById("toggle-continuous-scroll");
    if (rlSwitch) rlSwitch.checked = state.redLetters;
    if (shSwitch) shSwitch.checked = state.sectionHeadings;
    if (csSwitch) csSwitch.checked = state.continuousScroll;

    localStorage.setItem("bv_theme", state.theme);
    localStorage.setItem("bv_readerTheme", state.readerTheme);
    localStorage.setItem("bv_fontFamily", state.fontFamily);
    localStorage.setItem("bv_fontSize", state.fontSize);
    localStorage.setItem("bv_lineHeight", state.lineHeight);
    localStorage.setItem("bv_redLetters", state.redLetters);
    localStorage.setItem("bv_sectionHeadings", state.sectionHeadings);
    localStorage.setItem("bv_continuousScroll", state.continuousScroll);
  }

  document.getElementById("btn-open-reader-settings")?.addEventListener("click", () => {
    readerSettingsModal.classList.add("open");
  });
  document.getElementById("btn-close-settings")?.addEventListener("click", () => {
    readerSettingsModal.classList.remove("open");
  });

  document.querySelectorAll(".theme-option-card").forEach(btn => {
    btn.addEventListener("click", () => {
      state.theme = btn.dataset.theme;
      applyReaderVisualPreferences();
    });
  });

  document.querySelectorAll(".reader-theme-btn").forEach(btn => {
    btn.addEventListener("click", () => {
      state.readerTheme = btn.dataset.readerTheme;
      applyReaderVisualPreferences();
    });
  });

  document.querySelectorAll(".font-family-btn").forEach(btn => {
    btn.addEventListener("click", () => {
      state.fontFamily = btn.dataset.font;
      applyReaderVisualPreferences();
    });
  });

  document.querySelectorAll(".line-spacing-btn").forEach(btn => {
    btn.addEventListener("click", () => {
      state.lineHeight = parseFloat(btn.dataset.lh);
      applyReaderVisualPreferences();
    });
  });

  function changeFontSize(delta) {
    state.fontSize = Math.min(28, Math.max(14, state.fontSize + delta));
    applyReaderVisualPreferences();
  }
  document.getElementById("btn-font-decrease")?.addEventListener("click", () => changeFontSize(-1));
  document.getElementById("btn-font-increase")?.addEventListener("click", () => changeFontSize(1));
  document.getElementById("settings-font-dec")?.addEventListener("click", () => changeFontSize(-1));
  document.getElementById("settings-font-inc")?.addEventListener("click", () => changeFontSize(1));

  document.getElementById("toggle-red-letters")?.addEventListener("change", e => {
    state.redLetters = e.target.checked;
    applyReaderVisualPreferences();
    if (state.currentTab === "reader") loadReaderChapter(false);
  });
  document.getElementById("toggle-section-headings")?.addEventListener("change", e => {
    state.sectionHeadings = e.target.checked;
    applyReaderVisualPreferences();
    if (state.currentTab === "reader") loadReaderChapter(false);
  });
  document.getElementById("toggle-continuous-scroll")?.addEventListener("change", e => {
    state.continuousScroll = e.target.checked;
    applyReaderVisualPreferences();
  });

  // =========================================================================
  // 11. MÓDULO 5: PLANES DE LECTURA Y DEVOCIONALES (26 Planes + Personalizados)
  // =========================================================================
  const plansModal = document.getElementById("reading-plans-modal");
  let loadedPlansDataCache = {}; // { planId: [days...] }

  async function ensurePlanDaysLoaded(planId) {
    if (loadedPlansDataCache[planId]) return loadedPlansDataCache[planId];

    // Si es un plan personalizado creado por el usuario
    if (state.userPlans[planId] && state.userPlans[planId].customDays) {
      loadedPlansDataCache[planId] = state.userPlans[planId].customDays;
      return loadedPlansDataCache[planId];
    }

    if (planId === "TRADITIONAL" || planId === "CHRONOLOGICAL") {
      const file = planId === "TRADITIONAL" ? "plan_traditional.json" : "plan_chronological.json";
      const resp = await fetch(`./assets/bible/${file}`);
      if (resp.ok) {
        const raw = await resp.json();
        loadedPlansDataCache[planId] = raw.map(d => ({
          dayNumber: d.dayNumber,
          title: d.title,
          passagesSummary: d.passagesSummary,
          primaryBookId: d.primaryBookId,
          primaryChapter: d.primaryChapter,
          passages: d.segments || [],
          storyNarrative: `Lectura canónica correspondiente al ${d.title} (${d.passagesSummary}). Abre los capítulos de hoy en el Lector Bíblico para meditar en la Palabra.`,
          spiritualLesson: "Toda la Escritura es inspirada por Dios y útil para enseñar, para redargüir, para corregir y para instruir en justicia (2 Timoteo 3:16).",
          historicalContext: "Plan anual sistemático a través de las Sagradas Escrituras."
        }));
        return loadedPlansDataCache[planId];
      }
    }

    if (planId === "BIBLE_STORIES") {
      const resp = await fetch("./assets/bible/plans/plan_bible_stories.json");
      if (resp.ok) {
        loadedPlansDataCache[planId] = await resp.json();
        return loadedPlansDataCache[planId];
      }
    }

    // Cargar desde thematic_plans.json (los otros 23 planes temáticos y teológicos)
    if (!loadedPlansDataCache._thematicBundle) {
      const resp = await fetch("./assets/bible/plans/thematic_plans.json");
      if (resp.ok) {
        loadedPlansDataCache._thematicBundle = await resp.json();
      } else {
        loadedPlansDataCache._thematicBundle = {};
      }
    }
    loadedPlansDataCache[planId] = loadedPlansDataCache._thematicBundle[planId] || [];
    return loadedPlansDataCache[planId];
  }

  function getAllPlansMetadata() {
    const base = Array.isArray(window.READING_PLANS_CATALOG) ? window.READING_PLANS_CATALOG : [];
    const customMetas = Object.keys(state.userPlans)
      .filter(k => state.userPlans[k].customMeta)
      .map(k => state.userPlans[k].customMeta);
    return [...customMetas, ...base];
  }

  function updatePlansSummariesUI() {
    const allMeta = getAllPlansMetadata();
    let activeCount = 0;
    let completedCount = 0;

    allMeta.forEach(p => {
      const u = state.userPlans[p.id];
      if (u) {
        const done = (u.completedDays || []).length;
        if (done >= p.durationDays) completedCount++;
        else activeCount++;
      }
    });

    const elActive = document.getElementById("count-active-plans");
    const elComp = document.getElementById("count-completed-plans");
    const elSettings = document.getElementById("settings-plans-summary");
    if (elActive) elActive.textContent = activeCount;
    if (elComp) elComp.textContent = completedCount;
    if (elSettings) elSettings.textContent = `${activeCount} planes en curso • ${completedCount} completados`;
  }

  function showPlansModalView(viewName) {
    if (viewName !== "MAIN") {
      pushBackNavState();
    }
    document.getElementById("plans-main-view").style.display = viewName === "MAIN" ? "block" : "none";
    document.getElementById("plan-days-detail-view").style.display = viewName === "DAYS" ? "block" : "none";
    document.getElementById("plan-day-reader-view").style.display = viewName === "READER" ? "block" : "none";
    document.getElementById("create-custom-plan-view").style.display = viewName === "CUSTOM" ? "block" : "none";
  }

  function openReadingPlansSheet() {
    updatePlansSummariesUI();
    showPlansModalView("MAIN");
    renderPlansMainTabContent();
    plansModal.classList.add("open");
  }

  document.getElementById("btn-open-plans-top")?.addEventListener("click", openReadingPlansSheet);
  document.getElementById("btn-open-plans-banner")?.addEventListener("click", openReadingPlansSheet);
  document.getElementById("btn-settings-open-plans")?.addEventListener("click", openReadingPlansSheet);
  document.getElementById("btn-close-reading-plans")?.addEventListener("click", () => plansModal.classList.remove("open"));

  document.querySelectorAll("[data-plantab]").forEach(btn => {
    btn.addEventListener("click", () => {
      state.activePlanModalTab = btn.dataset.plantab;
      document.querySelectorAll("[data-plantab]").forEach(b => b.classList.toggle("active", b.dataset.plantab === state.activePlanModalTab));
      renderPlansMainTabContent();
    });
  });

  function renderPlansMainTabContent() {
    const container = document.getElementById("plans-tab-content");
    if (!container) return;
    const allPlans = getAllPlansMetadata();

    if (state.activePlanModalTab === "ACTIVE") {
      const activeList = allPlans.filter(p => {
        const u = state.userPlans[p.id];
        return u && (u.completedDays || []).length < p.durationDays;
      });
      if (activeList.length === 0) {
        container.innerHTML = `<div style="text-align:center; padding:32px; color:var(--text-secondary);">No tienes planes en curso actualmente. Explora el <strong>Catálogo</strong> para iniciar uno.</div>`;
        return;
      }
      container.innerHTML = `<div class="plans-grid-2col">${activeList.map(p => renderPlanCardHTML(p)).join("")}</div>`;
    } else if (state.activePlanModalTab === "COMPLETED") {
      const compList = allPlans.filter(p => {
        const u = state.userPlans[p.id];
        return u && (u.completedDays || []).length >= p.durationDays;
      });
      if (compList.length === 0) {
        container.innerHTML = `<div style="text-align:center; padding:32px; color:var(--text-secondary);">Aún no has completado ningún plan. ¡Comienza hoy tu recorrido bíblico!</div>`;
        return;
      }
      container.innerHTML = `<div class="plans-grid-2col">${compList.map(p => renderPlanCardHTML(p)).join("")}</div>`;
    } else {
      // ALL y CATALOG: Botón Crear Plan Personalizado + Filtros por categoría + Planes divididos por tema/categoría
      const categories = [];
      allPlans.forEach(p => {
        if (!categories.includes(p.category)) categories.push(p.category);
      });

      const activeCatFilter = state.activePlanCategoryFilter || "ALL";
      const visibleCategories = activeCatFilter === "ALL" ? categories : categories.filter(c => c === activeCatFilter);

      const chipShortLabel = c => {
        if (c.includes("ANUALES")) return "Anuales y Largos";
        if (c.includes("DOCTRINA")) return "Doctrina y Teología";
        if (c.includes("CRECIMIENTO")) return "Crecimiento";
        if (c.includes("PAZ")) return "Paz y Consuelo";
        if (c.includes("FAMILIA")) return "Familia y Vida";
        return c.split("(")[0].trim();
      };

      let html = `
        <button class="primary-full-btn" id="btn-open-create-custom-plan" style="margin-bottom:12px;">
          ✨ Crear Plan de Estudio Personalizado a tu Medida
        </button>
        <div class="topic-chips-row" style="margin-bottom:12px;">
          <button class="chip ${activeCatFilter === "ALL" ? "active" : ""}" data-plancat="ALL">Todos</button>
          ${categories.map(c => `<button class="chip ${activeCatFilter === c ? "active" : ""}" data-plancat="${c}">${chipShortLabel(c)}</button>`).join("")}
        </div>
      `;

      for (const cat of visibleCategories) {
        const catPlans = allPlans.filter(p => p.category === cat);
        if (catPlans.length === 0) continue;
        html += `<h4 style="font-size:0.78rem; color:var(--secondary-color); margin:12px 0 8px; letter-spacing:0.4px;">${cat}</h4>`;
        html += `<div class="plans-grid-2col">${catPlans.map(p => renderPlanCardHTML(p)).join("")}</div>`;
      }
      container.innerHTML = html;
      container.querySelectorAll("[data-plancat]").forEach(chip => {
        chip.addEventListener("click", () => {
          triggerHaptic(8);
          state.activePlanCategoryFilter = chip.dataset.plancat;
          renderPlansMainTabContent();
        });
      });
      bindCreateCustomPlanOpenButton();
    }

    container.querySelectorAll(".plan-grid-card").forEach(card => {
      card.addEventListener("click", () => openPlanDaysDetail(card.dataset.pid));
    });
  }

  function renderPlanCardHTML(p) {
    const u = state.userPlans[p.id];
    const doneCount = u ? (u.completedDays || []).length : 0;
    const pct = Math.min(100, Math.round((doneCount / p.durationDays) * 100));
    const statusLabel = u
      ? (doneCount >= p.durationDays ? "🏆 Completado" : `🔥 ${doneCount}/${p.durationDays}d (${pct}%)`)
      : p.badge;

    return `
      <div class="plan-grid-card" data-pid="${p.id}">
        <img src="./${p.coverImage}" alt="${p.title}" class="plan-grid-cover" loading="lazy">
        <div class="plan-grid-body">
          <span class="testament-mini-badge" style="color:var(--secondary-color); font-size:0.68rem;">${statusLabel}</span>
          <h4 style="font-size:0.84rem; color:var(--primary-color); line-height:1.25;">${p.title}</h4>
          <p style="font-size:0.72rem; color:var(--text-secondary); line-height:1.3; display:-webkit-box; -webkit-line-clamp:2; -webkit-box-orient:vertical; overflow:hidden;">${p.subtitle}</p>
          ${u ? `<div class="offline-progress-track" style="margin-top:auto;"><div class="offline-progress-fill" style="width:${pct}%;"></div></div>` : ""}
        </div>
      </div>
    `;
  }

  let currentPlanDetailDays = [];

  function renderPlanDaysRows(planId, meta, filterQuery = "") {
    const container = document.getElementById("plan-days-list-container");
    const u = state.userPlans[planId];
    const completed = u ? (u.completedDays || []) : [];
    const pct = Math.min(100, Math.round((completed.length / meta.durationDays) * 100));

    document.getElementById("plan-detail-progress-text").textContent = `${completed.length} de ${meta.durationDays} días completados (${pct}%)`;
    document.getElementById("plan-detail-progress-bar").style.width = `${pct}%`;

    const nextPending = currentPlanDetailDays.find(d => !completed.includes(d.dayNumber))?.dayNumber;
    const q = stripAccents(filterQuery).toLowerCase().trim();
    const visibleDays = q
      ? currentPlanDetailDays.filter(d => stripAccents(`${d.title} ${d.passagesSummary}`).toLowerCase().includes(q))
      : currentPlanDetailDays;

    if (visibleDays.length === 0) {
      container.innerHTML = `<div style="text-align:center; padding:24px; color:var(--text-secondary);">No se encontraron días que coincidan con "${filterQuery}".</div>`;
      return;
    }

    container.innerHTML = visibleDays.map(d => {
      const isDone = completed.includes(d.dayNumber);
      const isNext = !isDone && d.dayNumber === nextPending;
      return `
        <div class="plan-day-row ${isDone ? "done" : ""}" data-day="${d.dayNumber}">
          <div style="flex:1; padding-right:8px;">
            <strong style="color:var(--primary-color); font-size:0.88rem;">
              ${d.title}
              ${isNext ? `<span class="plan-day-badge-next">Siguiente</span>` : ""}
            </strong>
            <div style="font-size:0.76rem; color:var(--secondary-color); font-weight:700; margin-top:2px;">📖 ${d.passagesSummary}</div>
          </div>
          <input type="checkbox" class="plan-day-checkbox" data-day="${d.dayNumber}" ${isDone ? "checked" : ""} style="width:20px; height:20px; accent-color:var(--primary-color); cursor:pointer;">
        </div>
      `;
    }).join("");

    container.querySelectorAll(".plan-day-checkbox").forEach(cb => {
      cb.addEventListener("click", e => {
        e.stopPropagation();
        if (!state.userPlans[planId]) {
          state.userPlans[planId] = { startedAt: Date.now(), completedDays: [] };
        }
        togglePlanDayCompletion(planId, Number(cb.dataset.day));
        renderPlanDaysRows(planId, meta, document.getElementById("plan-stories-search")?.value || "");
        renderPlanDetailActionButtons(planId, meta);
      });
    });

    const isDirectReaderPlan = planId === "TRADITIONAL" || planId === "CHRONOLOGICAL";
    container.querySelectorAll(".plan-day-row").forEach(row => {
      row.addEventListener("click", () => {
        const dayNum = Number(row.dataset.day);
        if (!state.userPlans[planId]) {
          state.userPlans[planId] = { startedAt: Date.now(), completedDays: [] };
          saveUserPlans();
        }
        if (isDirectReaderPlan) {
          const dayObj = currentPlanDetailDays.find(d => d.dayNumber === dayNum);
          if (dayObj) {
            const passages = (dayObj.passages && dayObj.passages.length > 0)
              ? dayObj.passages
              : [{ bookId: dayObj.primaryBookId || 1, bookName: getBookById(dayObj.primaryBookId || 1).name, chapter: dayObj.primaryChapter || 1 }];
            state.activeReaderPlanSession = {
              planId,
              dayNumber: dayObj.dayNumber,
              title: dayObj.title,
              passages
            };
            state.bookId = passages[0].bookId;
            state.chapter = passages[0].chapter;
            plansModal.classList.remove("open");
            switchTab("reader");
            return;
          }
        }
        openPlanDayReaderModal(planId, dayNum);
      });
    });
  }

  function renderPlanDetailActionButtons(planId, meta) {
    const actionsRow = document.getElementById("plan-detail-actions-row");
    if (!actionsRow) return;
    const isActive = !!state.userPlans[planId];
    const isCustom = planId.startsWith("CUSTOM_");

    actionsRow.innerHTML = `
      <button type="button" class="offline-action-btn ${isActive ? "done" : ""}" id="btn-plan-toggle-enroll">
        ${isActive ? "✓ En Mis Planes (Quitar)" : "▶ Activar Plan"}
      </button>
      ${isActive ? `<button type="button" class="offline-action-btn" id="btn-plan-reset">↺ Reiniciar</button>` : ""}
      ${isCustom ? `<button type="button" class="offline-action-btn danger" id="btn-plan-delete-custom">🗑 Eliminar</button>` : ""}
    `;

    document.getElementById("btn-plan-toggle-enroll")?.addEventListener("click", () => {
      if (state.userPlans[planId]) {
        delete state.userPlans[planId];
        showToast("Plan quitado de Mis Planes");
      } else {
        state.userPlans[planId] = { startedAt: Date.now(), completedDays: [] };
        showToast(`🔥 Plan "${meta.title}" activado`);
      }
      saveUserPlans();
      renderPlanDetailActionButtons(planId, meta);
      renderPlanDaysRows(planId, meta, document.getElementById("plan-stories-search")?.value || "");
    });

    document.getElementById("btn-plan-reset")?.addEventListener("click", () => {
      if (state.userPlans[planId]) {
        state.userPlans[planId].completedDays = [];
        state.userPlans[planId].startedAt = Date.now();
        saveUserPlans();
        renderPlanDetailActionButtons(planId, meta);
        renderPlanDaysRows(planId, meta, document.getElementById("plan-stories-search")?.value || "");
        showToast("↺ Progreso del plan reiniciado");
      }
    });

    document.getElementById("btn-plan-delete-custom")?.addEventListener("click", () => {
      delete state.userPlans[planId];
      saveUserPlans();
      showPlansModalView("MAIN");
      renderPlansMainTabContent();
      showToast("🗑 Plan personalizado eliminado");
    });
  }

  async function openPlanDaysDetail(planId) {
    state.currentViewingPlanId = planId;
    const allPlans = getAllPlansMetadata();
    const meta = allPlans.find(p => p.id === planId);
    if (!meta) return;

    showPlansModalView("DAYS");
    document.getElementById("plan-detail-title").textContent = meta.title;
    renderPlanDetailActionButtons(planId, meta);

    const searchWrap = document.getElementById("plan-stories-search-wrap");
    const searchField = document.getElementById("plan-stories-search");
    if (searchField) searchField.value = "";

    const container = document.getElementById("plan-days-list-container");
    container.innerHTML = `<div style="text-align:center; padding:24px;">Cargando días del plan...</div>`;

    currentPlanDetailDays = await ensurePlanDaysLoaded(planId);
    if (searchWrap) {
      searchWrap.style.display = planId === "BIBLE_STORIES" ? "block" : "none";
    }
    if (searchField) {
      searchField.oninput = () => renderPlanDaysRows(planId, meta, searchField.value);
    }

    renderPlanDaysRows(planId, meta, "");
  }

  document.getElementById("btn-back-to-plans-list")?.addEventListener("click", () => {
    showPlansModalView("MAIN");
    renderPlansMainTabContent();
  });

  async function openPlanDayReaderModal(planId, dayNumber) {
    state.currentViewingPlanId = planId;
    state.currentViewingDayNum = dayNumber;
    const days = await ensurePlanDaysLoaded(planId);
    const dayObj = days.find(d => d.dayNumber === dayNumber) || days[0];
    if (!dayObj) return;

    showPlansModalView("READER");
    document.getElementById("day-reader-badge").textContent = `Día ${dayObj.dayNumber}`;
    document.getElementById("day-reader-title").textContent = dayObj.title;
    document.getElementById("day-reader-summary").textContent = dayObj.passagesSummary;

    document.getElementById("day-reader-narrative").textContent = dayObj.storyNarrative || "";
    document.getElementById("day-reader-lesson").textContent = dayObj.spiritualLesson || "";
    document.getElementById("day-reader-context").textContent = dayObj.historicalContext || "";

    const passages = dayObj.passages && dayObj.passages.length > 0
      ? dayObj.passages
      : [{ bookId: dayObj.primaryBookId || 1, bookName: getBookById(dayObj.primaryBookId || 1).name, chapter: dayObj.primaryChapter || 1 }];

    const btnsContainer = document.getElementById("day-reader-passage-buttons");
    btnsContainer.innerHTML = passages.map((seg, idx) => `
      <button class="action-pill active btn-open-plan-seg" data-idx="${idx}">
        📖 Leer ${seg.bookName} ${seg.chapter} →
      </button>
    `).join("");

    btnsContainer.querySelectorAll(".btn-open-plan-seg").forEach(btn => {
      btn.addEventListener("click", () => {
        const seg = passages[Number(btn.dataset.idx)];
        state.activeReaderPlanSession = {
          planId,
          dayNumber: dayObj.dayNumber,
          title: dayObj.title,
          passages
        };
        state.bookId = seg.bookId;
        state.chapter = seg.chapter;
        plansModal.classList.remove("open");
        switchTab("reader");
      });
    });

    updateDayCompleteModalButton();
  }

  function updateDayCompleteModalButton() {
    const btn = document.getElementById("btn-toggle-complete-day-modal");
    if (!btn) return;
    const u = state.userPlans[state.currentViewingPlanId];
    const isDone = u && (u.completedDays || []).includes(state.currentViewingDayNum);
    btn.textContent = isDone ? "✅ Día Completado (Pulsa para desmarcar)" : "✓ Marcar Día como Completado";
  }

  function togglePlanDayCompletion(planId, dayNum) {
    if (!state.userPlans[planId]) {
      state.userPlans[planId] = { startedAt: Date.now(), completedDays: [] };
    }
    const arr = state.userPlans[planId].completedDays;
    const idx = arr.indexOf(dayNum);
    if (idx === -1) {
      arr.push(dayNum);
      const meta = getAllPlansMetadata().find(p => p.id === planId);
      if (meta && arr.length >= meta.durationDays) {
        showToast(`🏆 ¡Felicidades! Completaste el plan "${meta.title}"`);
      } else {
        showToast(`🎉 ¡Día ${dayNum} marcado como completado!`);
      }
    } else {
      arr.splice(idx, 1);
    }
    saveUserPlans();
  }

  document.getElementById("btn-toggle-complete-day-modal")?.addEventListener("click", () => {
    togglePlanDayCompletion(state.currentViewingPlanId, state.currentViewingDayNum);
    updateDayCompleteModalButton();
  });

  document.getElementById("btn-back-to-plan-days")?.addEventListener("click", () => {
    openPlanDaysDetail(state.currentViewingPlanId);
  });

  // Barra de sesión activa de Plan dentro del Lector Bíblico
  function renderPlanSessionHeaderInReader() {
    const bar = document.getElementById("reader-plan-session-bar");
    if (!bar) return;
    const sess = state.activeReaderPlanSession;
    if (!sess) {
      bar.style.display = "none";
      return;
    }
    bar.style.display = "block";
    document.getElementById("reader-plan-session-title").textContent = sess.title;
    const segRow = document.getElementById("reader-plan-segments-row");
    segRow.innerHTML = sess.passages.map(seg => {
      const isCur = seg.bookId === state.bookId && seg.chapter === state.chapter;
      return `<button class="chip ${isCur ? "active" : ""}" data-bid="${seg.bookId}" data-ch="${seg.chapter}">${seg.bookName} ${seg.chapter}</button>`;
    }).join("");

    segRow.querySelectorAll(".chip").forEach(chip => {
      chip.addEventListener("click", () => {
        state.bookId = Number(chip.dataset.bid);
        state.chapter = Number(chip.dataset.ch);
        loadReaderChapter(false);
      });
    });
  }

  document.getElementById("btn-exit-plan-session")?.addEventListener("click", () => {
    const sess = state.activeReaderPlanSession;
    state.activeReaderPlanSession = null;
    renderPlanSessionHeaderInReader();
    if (sess) {
      plansModal.classList.add("open");
      openPlanDayReaderModal(sess.planId, sess.dayNumber);
    }
  });

  document.getElementById("btn-complete-plan-day-reader")?.addEventListener("click", () => {
    const sess = state.activeReaderPlanSession;
    if (!sess) return;
    togglePlanDayCompletion(sess.planId, sess.dayNumber);
    state.activeReaderPlanSession = null;
    renderPlanSessionHeaderInReader();
    plansModal.classList.add("open");
    openPlanDaysDetail(sess.planId);
  });

  // Crear Plan Personalizado (CreateCustomPlanDialog.kt - Multi-libro, Presets, Ritmo y Vista Previa)
  let customPaceMode = "BY_TARGET_DAYS";
  const customSelectedBookIds = new Set();
  let customBooksFilterTestament = "ALL";
  const customBooksPickerModal = document.getElementById("custom-books-picker-modal");

  function updateCustomPlanPreview() {
    const selectedIds = Array.from(customSelectedBookIds).sort((a, b) => a - b);
    const totalChapters = selectedIds.reduce((acc, bid) => {
      const b = getBookById(bid);
      return acc + (b ? b.chapters : 0);
    }, 0);
    const countSpan = document.getElementById("custom-books-count");
    if (countSpan) countSpan.textContent = String(selectedIds.length);
    const chaptersBadge = document.getElementById("custom-chapters-count-badge");
    if (chaptersBadge) chaptersBadge.textContent = `${totalChapters} capítulos`;

    const summaryNamesEl = document.getElementById("custom-books-summary-names");
    if (summaryNamesEl) {
      if (selectedIds.length === 0) {
        summaryNamesEl.textContent = "Ningún libro seleccionado aún. Toca 'Personalizar libros' para elegir o selecciona un preset sugerido arriba.";
      } else {
        const names = selectedIds.slice(0, 6).map(bid => getBookById(bid)?.name).filter(Boolean);
        summaryNamesEl.textContent = names.join(", ") + (selectedIds.length > 6 ? ` y ${selectedIds.length - 6} más...` : "");
      }
    }

    const val = Math.max(1, Number(document.getElementById("custom-plan-pace-value")?.value || 30));
    const daysTotal = totalChapters === 0
      ? 0
      : (customPaceMode === "BY_TARGET_DAYS"
        ? Math.min(val, totalChapters)
        : Math.max(1, Math.ceil(totalChapters / val)));
    const avgPerDay = daysTotal > 0 ? (totalChapters / daysTotal).toFixed(1) : "0";
    const endDate = new Date(Date.now() + daysTotal * 86400000);
    const previewEl = document.getElementById("custom-plan-preview");
    const endDateEl = document.getElementById("custom-plan-end-date");
    if (previewEl) {
      previewEl.textContent = selectedIds.length === 0
        ? "Selecciona libros para calcular el plan"
        : `${daysTotal} días en total • ~${avgPerDay} caps/día`;
    }
    if (endDateEl) {
      endDateEl.textContent = selectedIds.length === 0
        ? "La duración se ajustará según los libros elegidos"
        : `Fecha estimada de finalización: ${endDate.toLocaleDateString("es-ES", { day: "numeric", month: "long", year: "numeric" })}`;
    }
  }

  function renderCustomBooksPickerList(searchStr = "") {
    const listEl = document.getElementById("custom-plan-books-list");
    const modalCountEl = document.getElementById("custom-modal-books-count");
    if (modalCountEl) modalCountEl.textContent = `${customSelectedBookIds.size} libros seleccionados`;
    if (!listEl) return;

    const q = stripAccents(searchStr).toLowerCase();
    const filtered = BIBLE_BOOKS.filter(b => {
      if (customBooksFilterTestament !== "ALL" && b.testament !== customBooksFilterTestament) return false;
      return stripAccents(b.name).toLowerCase().includes(q) || stripAccents(b.category).toLowerCase().includes(q);
    });

    listEl.innerHTML = filtered.map(b => {
      const isChecked = customSelectedBookIds.has(b.id);
      return `
        <div class="book-selector-item ${isChecked ? "selected" : ""}" data-cbid="${b.id}">
          <div class="book-selector-left">
            <div class="book-order-circle">${b.id}</div>
            <div>
              <strong style="font-size:0.92rem;">${b.name}</strong>
              <div style="font-size:0.74rem; color:var(--text-secondary);">${b.category} • ${b.chapters} cap.</div>
            </div>
          </div>
          <span style="font-size:1.1rem; font-weight:800; color:${isChecked ? "var(--primary-color)" : "var(--text-secondary)"};">${isChecked ? "✓" : "+"}</span>
        </div>
      `;
    }).join("");

    listEl.querySelectorAll("[data-cbid]").forEach(row => {
      row.addEventListener("click", () => {
        triggerHaptic(8);
        const bid = Number(row.dataset.cbid);
        if (customSelectedBookIds.has(bid)) customSelectedBookIds.delete(bid);
        else customSelectedBookIds.add(bid);
        activeCustomPresetId = null;
        document.querySelectorAll("#custom-plan-presets .chip").forEach(c => c.classList.remove("active"));
        renderCustomBooksPickerList(document.getElementById("custom-books-search-input")?.value || "");
      });
    });
  }

  function renderCustomPaceQuickChips() {
    const container = document.getElementById("custom-pace-quick-chips");
    if (!container) return;
    const currentVal = Number(document.getElementById("custom-plan-pace-value")?.value || 30);
    const opts = customPaceMode === "BY_TARGET_DAYS" ? [15, 30, 60, 90, 180, 365] : [1, 2, 3, 4, 5];
    container.innerHTML = opts.map(v => {
      const label = customPaceMode === "BY_TARGET_DAYS" ? `${v} d` : `${v} cap${v > 1 ? "s" : ""}`;
      return `<button type="button" class="chip ${currentVal === v ? "active" : ""}" data-paceval="${v}">${label}</button>`;
    }).join("");
    container.querySelectorAll("[data-paceval]").forEach(btn => {
      btn.addEventListener("click", () => {
        triggerHaptic(8);
        const valEl = document.getElementById("custom-plan-pace-value");
        if (valEl) valEl.value = btn.dataset.paceval;
        renderCustomPaceQuickChips();
        updateCustomPlanPreview();
      });
    });
  }

  let activeCustomPresetId = null;

  function bindCreateCustomPlanOpenButton() {
    document.getElementById("btn-open-create-custom-plan")?.addEventListener("click", () => {
      activeCustomPresetId = null;
      customSelectedBookIds.clear();
      const titleInput = document.getElementById("custom-plan-title");
      if (titleInput) titleInput.value = "";
      document.querySelectorAll("#custom-plan-presets .chip").forEach(c => c.classList.remove("active"));
      const valEl = document.getElementById("custom-plan-pace-value");
      if (valEl && customPaceMode === "BY_TARGET_DAYS") valEl.value = "30";
      renderCustomPaceQuickChips();
      updateCustomPlanPreview();
      showPlansModalView("CUSTOM");
    });
  }

  document.getElementById("btn-toggle-custom-books-list")?.addEventListener("click", () => {
    triggerHaptic(10);
    const searchEl = document.getElementById("custom-books-search-input");
    if (searchEl) searchEl.value = "";
    customBooksFilterTestament = "ALL";
    document.querySelectorAll("[data-cbfilter]").forEach(c => c.classList.toggle("active", c.dataset.cbfilter === "ALL"));
    renderCustomBooksPickerList("");
    customBooksPickerModal?.classList.add("open");
  });

  document.getElementById("custom-books-search-input")?.addEventListener("input", e => {
    renderCustomBooksPickerList(e.target.value);
  });

  document.querySelectorAll("[data-cbfilter]").forEach(btn => {
    btn.addEventListener("click", () => {
      triggerHaptic(8);
      customBooksFilterTestament = btn.dataset.cbfilter;
      document.querySelectorAll("[data-cbfilter]").forEach(b => b.classList.toggle("active", b === btn));
      renderCustomBooksPickerList(document.getElementById("custom-books-search-input")?.value || "");
    });
  });

  document.getElementById("btn-custom-books-clear")?.addEventListener("click", () => {
    triggerHaptic(8);
    customSelectedBookIds.clear();
    renderCustomBooksPickerList(document.getElementById("custom-books-search-input")?.value || "");
  });

  document.getElementById("btn-close-custom-books-modal")?.addEventListener("click", () => {
    customBooksPickerModal?.classList.remove("open");
    updateCustomPlanPreview();
  });

  document.getElementById("btn-apply-custom-books")?.addEventListener("click", () => {
    triggerHaptic(10);
    customBooksPickerModal?.classList.remove("open");
    updateCustomPlanPreview();
  });

  document.querySelectorAll("#custom-plan-presets .chip").forEach(chip => {
    chip.addEventListener("click", () => {
      triggerHaptic(10);
      const presetMap = {
        gospels: { title: "Los 4 Evangelios", books: [40, 41, 42, 43] },
        wisdom: { title: "Salmos y Proverbios", books: [19, 20] },
        paul: { title: "Cartas de Pablo", books: [45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57] },
        pentateuch: { title: "El Pentateuco", books: [1, 2, 3, 4, 5] }
      };
      const presetId = chip.dataset.preset;
      const cfg = presetMap[presetId];
      if (!cfg) return;
      const titleInput = document.getElementById("custom-plan-title");
      customSelectedBookIds.clear();
      if (activeCustomPresetId === presetId) {
        activeCustomPresetId = null;
        if (titleInput) titleInput.value = "";
        document.querySelectorAll("#custom-plan-presets .chip").forEach(c => c.classList.remove("active"));
      } else {
        activeCustomPresetId = presetId;
        if (titleInput) titleInput.value = cfg.title;
        cfg.books.forEach(bid => customSelectedBookIds.add(bid));
        document.querySelectorAll("#custom-plan-presets .chip").forEach(c => c.classList.toggle("active", c === chip));
      }
      updateCustomPlanPreview();
    });
  });

  document.querySelectorAll("[data-pace]").forEach(btn => {
    btn.addEventListener("click", () => {
      triggerHaptic(8);
      customPaceMode = btn.dataset.pace;
      document.querySelectorAll("[data-pace]").forEach(b => b.classList.toggle("active", b === btn));
      const unitEl = document.getElementById("custom-plan-pace-unit");
      const valEl = document.getElementById("custom-plan-pace-value");
      if (customPaceMode === "BY_TARGET_DAYS") {
        if (unitEl) unitEl.textContent = "días totales";
        if (valEl) valEl.value = "30";
      } else {
        if (unitEl) unitEl.textContent = "capítulos / día";
        if (valEl) valEl.value = "2";
      }
      renderCustomPaceQuickChips();
      updateCustomPlanPreview();
    });
  });

  document.getElementById("custom-plan-pace-value")?.addEventListener("input", () => {
    renderCustomPaceQuickChips();
    updateCustomPlanPreview();
  });

  document.getElementById("btn-back-from-custom-plan")?.addEventListener("click", () => showPlansModalView("MAIN"));
  document.getElementById("btn-Generate-custom-plan")?.addEventListener("click", () => {
    const selectedIds = Array.from(customSelectedBookIds).sort((a, b) => a - b);
    if (selectedIds.length === 0) {
      showToast("⚠️ Selecciona al menos un libro");
      return;
    }

    const chaptersQueue = [];
    selectedIds.forEach(bid => {
      const b = getBookById(bid);
      if (b) {
        for (let c = 1; c <= b.chapters; c++) {
          chaptersQueue.push({ bookId: b.id, bookName: b.name, chapter: c });
        }
      }
    });

    const paceVal = Math.max(1, Number(document.getElementById("custom-plan-pace-value")?.value || 7));
    const duration = customPaceMode === "BY_TARGET_DAYS"
      ? paceVal
      : Math.max(1, Math.ceil(chaptersQueue.length / paceVal));

    const firstBookName = getBookById(selectedIds[0])?.name || "Biblia";
    const title = document.getElementById("custom-plan-title").value.trim() || `Plan ${firstBookName}${selectedIds.length > 1 ? ` +${selectedIds.length - 1}` : ""}`;

    const customId = `CUSTOM_${Date.now()}`;
    const customDays = [];
    for (let d = 0; d < duration; d++) {
      const startIdx = Math.floor((d * chaptersQueue.length) / duration);
      const endIdx = Math.max(startIdx + 1, Math.floor(((d + 1) * chaptersQueue.length) / duration));
      const slice = chaptersQueue.slice(startIdx, Math.min(endIdx, chaptersQueue.length));
      const dayPassages = slice.length > 0 ? slice : [chaptersQueue[d % chaptersQueue.length]];
      const firstSeg = dayPassages[0];
      const lastSeg = dayPassages[dayPassages.length - 1];
      const summary = dayPassages.length === 1
        ? `${firstSeg.bookName} ${firstSeg.chapter}`
        : (firstSeg.bookId === lastSeg.bookId
          ? `${firstSeg.bookName} ${firstSeg.chapter}-${lastSeg.chapter}`
          : `${firstSeg.bookName} ${firstSeg.chapter} - ${lastSeg.bookName} ${lastSeg.chapter}`);

      customDays.push({
        dayNumber: d + 1,
        title: `Día ${d + 1}: ${summary}`,
        passagesSummary: summary,
        primaryBookId: firstSeg.bookId,
        primaryChapter: firstSeg.chapter,
        passages: dayPassages,
        storyNarrative: getLocalScholarlyExegesis(firstSeg.bookName, firstSeg.chapter, "1", ""),
        spiritualLesson: `Meditación sistemática en ${summary} dentro de tu plan personalizado "${title}".`,
        historicalContext: "¿Qué enseñanza de esta lectura puedes aplicar hoy en tu vida y oración?"
      });
    }

    state.userPlans[customId] = {
      startedAt: Date.now(),
      completedDays: [],
      customMeta: {
        id: customId,
        title,
        subtitle: `${selectedIds.length} libro(s) • ${chaptersQueue.length} capítulos (${duration} días)`,
        durationDays: duration,
        badge: `${duration} Días • Personalizado`,
        coverImage: "assets/drawable/plan_cover_custom.webp",
        category: "MIS PLANES PERSONALIZADOS"
      },
      customDays
    };
    saveUserPlans();
    openPlanDaysDetail(customId);
    showToast("✨ Plan personalizado creado con éxito");
  });

  // =========================================================================
  // 12. MÓDULO 6: BUSCADOR BÍBLICO EN LOS 66 LIBROS (SearchTab.kt)
  // =========================================================================
  const searchInput = document.getElementById("search-input");
  const clearSearchBtn = document.getElementById("btn-clear-search-input");
  const searchVersionSel = document.getElementById("search-version-select");
  const searchBookPickerBtn = document.getElementById("btn-search-book-picker");
  let currentSearchScope = "ALL";
  let currentSearchBookId = null;
  let searchDebounceTimer = null;

  document.querySelectorAll("#search-scope-chips .chip[data-scope]").forEach(chip => {
    chip.addEventListener("click", () => {
      currentSearchScope = chip.dataset.scope;
      currentSearchBookId = null;
      if (searchBookPickerBtn) {
        searchBookPickerBtn.textContent = "📚 Por Libro...";
        searchBookPickerBtn.classList.remove("active");
      }
      document.querySelectorAll("#search-scope-chips .chip[data-scope]").forEach(c => c.classList.toggle("active", c === chip));
      triggerFullBibleSearch();
    });
  });

  searchBookPickerBtn?.addEventListener("click", () => {
    openUnifiedBookSelector("SEARCH_FILTER");
  });

  searchVersionSel?.addEventListener("change", triggerFullBibleSearch);

  searchInput?.addEventListener("input", () => {
    if (clearSearchBtn) clearSearchBtn.style.display = searchInput.value ? "block" : "none";
    clearTimeout(searchDebounceTimer);
    searchDebounceTimer = setTimeout(triggerFullBibleSearch, 240);
  });

  clearSearchBtn?.addEventListener("click", () => {
    searchInput.value = "";
    clearSearchBtn.style.display = "none";
    document.getElementById("search-results").innerHTML = "";
    document.getElementById("search-status-bar").style.display = "none";
  });

  async function triggerFullBibleSearch() {
    const q = (searchInput?.value || "").trim();
    const resContainer = document.getElementById("search-results");
    const statusBar = document.getElementById("search-status-bar");
    if (!q) {
      const suggestions = ["Amor", "Fe", "Paz", "Esperanza", "Sabiduría", "Perdón", "Salvación", "Oración"];
      resContainer.innerHTML = `
        <div style="text-align:center; padding:28px 16px; color:var(--text-secondary);">
          <p>Escribe una palabra, frase o referencia bíblica (ej. <em>Salmos 23</em>, <em>Juan 3:16</em>) para buscar en los 66 libros.</p>
          <div class="search-suggestions-wrap">
            ${suggestions.map(s => `<button type="button" class="chip search-suggestion-chip" data-q="${s}">${s}</button>`).join("")}
          </div>
        </div>
      `;
      resContainer.querySelectorAll(".search-suggestion-chip").forEach(btn => {
        btn.addEventListener("click", () => {
          searchInput.value = btn.dataset.q;
          if (clearSearchBtn) clearSearchBtn.style.display = "block";
          triggerFullBibleSearch();
        });
      });
      statusBar.style.display = "none";
      return;
    }

    resContainer.innerHTML = `<div style="text-align:center; padding:28px; color:var(--text-secondary);">🔍 Buscando "${q}" en la Biblia (${searchVersionSel.value})...</div>`;

    const matches = await searchFullBible(q, {
      version: searchVersionSel.value,
      scope: currentSearchScope,
      bookId: currentSearchBookId,
      limit: 100
    });

    statusBar.style.display = "flex";
    statusBar.innerHTML = `<span>Se encontraron <strong>${matches.length}${matches.length === 100 ? "+" : ""}</strong> coincidencias en ${searchVersionSel.value}</span>`;

    if (matches.length === 0) {
      resContainer.innerHTML = `<div style="text-align:center; padding:32px; color:var(--text-secondary);">No se encontraron resultados para "${q}".</div>`;
      return;
    }

    const regex = new RegExp(`(${q.replace(/[.*+?^${}()|[\]\\]/g, "\\$&")})`, "gi");
    resContainer.innerHTML = matches.map(m => {
      const highlightedText = m.text.replace(regex, `<span class="search-match-hl">$1</span>`);
      return `
        <div class="verse-item-card search-res-card" data-bid="${m.bookId}" data-ch="${m.chapter}" data-v="${m.verse}">
          <div class="verse-card-top">
            <strong style="color:var(--primary-color); font-size:0.94rem;">📖 ${m.reference} (${m.version})</strong>
            <div style="display:flex; gap:6px;">
              <button class="action-pill btn-search-copy" data-ref="${m.reference} (${m.version})" data-txt="${m.text.replace(/"/g, "&quot;")}">📋 Copiar</button>
            </div>
          </div>
          <p style="font-family:var(--font-serif); font-size:0.95rem; line-height:1.56;">«${highlightedText}»</p>
        </div>
      `;
    }).join("");

    resContainer.querySelectorAll(".search-res-card").forEach(card => {
      card.addEventListener("click", e => {
        if (e.target.closest(".btn-search-copy")) return;
        state.bookId = Number(card.dataset.bid);
        state.chapter = Number(card.dataset.ch);
        state.targetScrollVerse = Number(card.dataset.v);
        state.version = searchVersionSel.value;
        switchTab("reader");
      });
    });

    resContainer.querySelectorAll(".btn-search-copy").forEach(btn => {
      btn.addEventListener("click", e => {
        e.stopPropagation();
        navigator.clipboard.writeText(`«${btn.dataset.txt}» — ${btn.dataset.ref}`);
        showToast("📋 Versículo copiado");
      });
    });
  }

  // =========================================================================
  // 13. MÓDULO 7: FAVORITOS, RESALTADOS Y NOTAS (FavoritesAndNotesTab.kt)
  // =========================================================================
  document.querySelectorAll(".fav-subtab[data-subtab]").forEach(btn => {
    btn.addEventListener("click", () => {
      state.favSubTab = btn.dataset.subtab;
      document.querySelectorAll(".fav-subtab[data-subtab]").forEach(b => b.classList.toggle("active", b === btn));
      renderFavoritesAndNotesTab();
    });
  });

  document.getElementById("favorites-search-input")?.addEventListener("input", renderFavoritesAndNotesTab);

  function getUnifiedCollectionForFavoritesTab() {
    const favs = state.versesCollection.filter(v => v.isFavorite);
    const notesList = [...state.versesCollection.filter(v => v.notes && v.notes.trim().length > 0)];

    // Unificar resaltados de tarjetas + resaltados hechos en el Lector Bíblico
    const hlMap = new Map();
    state.versesCollection.filter(v => v.highlightColor).forEach(v => {
      hlMap.set(v.reference, {
        id: v.id,
        reference: v.reference,
        text: v.text,
        color: v.highlightColor,
        notes: v.notes || "",
        fromCard: true
      });
    });

    Object.entries(state.highlights).forEach(([hlKey, h]) => {
      const ref = `${h.bookName} ${h.chapter}:${h.verse}`;
      const hlItem = {
        hlKey,
        bookId: h.bookId,
        chapter: h.chapter,
        verse: h.verse,
        reference: ref,
        text: h.text || "",
        color: h.color,
        notes: h.note || "",
        fromReader: true
      };
      if (!hlMap.has(ref)) {
        hlMap.set(ref, hlItem);
      }
      if (h.note && h.note.trim().length > 0 && !notesList.some(n => n.reference === ref)) {
        notesList.push(hlItem);
      }
    });

    return {
      favorites: favs,
      highlights: Array.from(hlMap.values()),
      notes: notesList
    };
  }

  function renderFavoritesAndNotesTab() {
    const { favorites, highlights, notes } = getUnifiedCollectionForFavoritesTab();
    document.getElementById("count-favs").textContent = favorites.length;
    document.getElementById("count-highlights").textContent = highlights.length;
    document.getElementById("count-notes").textContent = notes.length;

    const q = stripAccents(document.getElementById("favorites-search-input")?.value || "").toLowerCase();
    const container = document.getElementById("favorites-list");
    if (!container) return;

    let items = [];
    if (state.favSubTab === "FAVORITES") items = favorites;
    else if (state.favSubTab === "HIGHLIGHTS") items = highlights;
    else items = notes;

    if (q) {
      items = items.filter(it => stripAccents(`${it.reference} ${it.text} ${it.notes || ""}`).toLowerCase().includes(q));
    }

    if (items.length === 0) {
      container.innerHTML = `<div style="text-align:center; padding:36px 16px; color:var(--text-secondary);">No hay elementos en esta sección todavía.</div>`;
      return;
    }

    container.innerHTML = items.map(it => {
      const borderCol = it.color || it.highlightColor || "var(--primary-color)";
      return `
        <div class="verse-item-card fav-unified-card" data-vid="${it.id || ""}" data-hlkey="${it.hlKey || ""}" data-ref="${it.reference}" style="border-left:5px solid ${borderCol};">
          <div class="verse-card-top">
            <strong style="color:var(--primary-color);">${it.reference}</strong>
            <div style="display:flex; gap:6px;">
              ${it.hlKey ? `<button class="action-pill btn-hl-edit-note" data-hlkey="${it.hlKey}">✏️ Nota</button>` : ""}
              <button class="action-pill btn-fav-goto-reader" data-ref="${it.reference}">📖 Ir al Lector</button>
            </div>
          </div>
          <p style="font-family:var(--font-serif); font-size:0.95rem; line-height:1.55;">«${it.text}»</p>
          ${it.notes ? `<div class="reflection-box" style="margin-top:8px;"><strong>✏️ Mi Nota:</strong> ${it.notes}</div>` : ""}
        </div>
      `;
    }).join("");

    container.querySelectorAll(".btn-hl-edit-note").forEach(btn => {
      btn.addEventListener("click", e => {
        e.stopPropagation();
        const hlKey = btn.dataset.hlkey;
        const hlObj = state.highlights[hlKey];
        if (!hlObj) return;
        const updated = window.prompt(`Nota personal para ${hlObj.bookName} ${hlObj.chapter}:${hlObj.verse}:`, hlObj.note || "");
        if (updated !== null) {
          hlObj.note = updated.trim();
          saveHighlightsState();
          renderFavoritesAndNotesTab();
          showToast(hlObj.note ? "✏️ Nota guardada" : "Nota eliminada");
        }
      });
    });

    container.querySelectorAll(".fav-unified-card").forEach(card => {
      card.addEventListener("click", e => {
        if (e.target.closest(".btn-fav-goto-reader") || e.target.closest(".btn-hl-edit-note")) return;
        const vid = card.dataset.vid;
        if (vid) {
          openVerseDetailDialog(Number(vid));
        } else if (card.dataset.hlkey) {
          card.querySelector(".btn-hl-edit-note")?.click();
        } else {
          const parsed = parseBibleReference(card.dataset.ref);
          if (parsed) {
            state.bookId = parsed.bookId;
            state.chapter = parsed.chapter;
            state.targetScrollVerse = parsed.startVerse;
            switchTab("reader");
          }
        }
      });
    });

    container.querySelectorAll(".btn-fav-goto-reader").forEach(btn => {
      btn.addEventListener("click", e => {
        e.stopPropagation();
        const parsed = parseBibleReference(btn.dataset.ref);
        if (parsed) {
          state.bookId = parsed.bookId;
          state.chapter = parsed.chapter;
          state.targetScrollVerse = parsed.startVerse;
          switchTab("reader");
        }
      });
    });
  }

  // =========================================================================
  // 14. MÓDULO 8: ASISTENTE BÍBLICO IA (BibleChatDialog.kt - Gemini + Chips + Voz)
  // =========================================================================
  const aiChatModal = document.getElementById("ai-chat-modal");
  const aiMessagesEl = document.getElementById("ai-chat-messages");
  const aiInput = document.getElementById("ai-chat-input");

  function renderChatMessages() {
    if (!aiMessagesEl) return;
    if (state.chatMessages.length === 0) {
      state.chatMessages.push({
        isUser: false,
        text: "¡Gracia y paz! Soy tu **Asistente Bíblico y Teológico IA**. Pregúntame sobre el contexto histórico de cualquier pasaje, doctrinas bíblicas (escatología, gracia, pactos, Espíritu Santo) o toca un tema rápido arriba."
      });
    }

    aiMessagesEl.innerHTML = state.chatMessages.map((msg, idx) => {
      const formattedHtml = formatChatMarkdownWithCitations(msg.text);
      const bubbleWidthStyle = msg.isUser ? "max-width:88%;" : "width:100%; max-width:100%;";
      return `
        <div class="chat-msg-bubble" data-msg-idx="${idx}" style="align-self:${msg.isUser ? "flex-end" : "flex-start"}; ${bubbleWidthStyle} background:${msg.isUser ? "var(--primary-color)" : "var(--surface-color)"}; color:${msg.isUser ? "var(--on-primary)" : "var(--text-primary)"}; padding:12px 14px; border-radius:16px; border:1px solid var(--border-color); font-size:0.9rem; line-height:1.58;">
          <div class="chat-msg-body">${formattedHtml}</div>
          ${!msg.isUser ? `
            <div style="display:flex; gap:8px; margin-top:8px; justify-content:flex-end;">
              <button class="action-pill btn-chat-tts" data-idx="${idx}" style="font-size:0.7rem; padding:3px 8px;">🔊 Escuchar</button>
              <button class="action-pill btn-chat-copy" data-idx="${idx}" style="font-size:0.7rem; padding:3px 8px;">📋 Copiar</button>
            </div>
          ` : ""}
        </div>
      `;
    }).join("");

    aiMessagesEl.scrollTop = aiMessagesEl.scrollHeight;

    aiMessagesEl.querySelectorAll(".chat-cite-chip").forEach(chip => {
      chip.addEventListener("click", () => {
        const parsed = parseBibleReference(chip.dataset.cite);
        if (parsed) {
          state.bookId = parsed.bookId;
          state.chapter = parsed.chapter;
          state.targetScrollVerse = parsed.startVerse;
          aiChatModal.classList.remove("open");
          switchTab("reader");
        }
      });
    });

    aiMessagesEl.querySelectorAll(".btn-chat-copy").forEach(btn => {
      btn.addEventListener("click", () => {
        const m = state.chatMessages[Number(btn.dataset.idx)];
        if (m) {
          navigator.clipboard.writeText(cleanAiMarkdownToPlainText(m.text));
          showToast("📋 Respuesta copiada");
        }
      });
    });

    aiMessagesEl.querySelectorAll(".btn-chat-tts").forEach(btn => {
      btn.addEventListener("click", () => {
        const m = state.chatMessages[Number(btn.dataset.idx)];
        if (m && "speechSynthesis" in window) {
          window.speechSynthesis.cancel();
          const u = new SpeechSynthesisUtterance(cleanAiMarkdownToPlainText(m.text));
          u.lang = "es-ES";
          window.speechSynthesis.speak(u);
        }
      });
    });
  }

  function formatChatMarkdownWithCitations(text) {
    let html = (text || "")
      .replace(/&/g, "&amp;")
      .replace(/</g, "&lt;")
      .replace(/>/g, "&gt;")
      .replace(/^#{1,6}\s+(.+)$/gm, '<strong style="display:block; margin-top:8px; margin-bottom:2px; color:var(--accent-gold);">$1</strong>')
      .replace(/\*\*(.+?)\*\*/g, "<strong>$1</strong>")
      .replace(/__(.+?)__/g, "<strong>$1</strong>")
      .replace(/\*(.+?)\*/g, "<em>$1</em>")
      .replace(/`([^`]+)`/g, "$1")
      .replace(/^\s*[-*]\s+/gm, "• ")
      .replace(/\*{2,}|#{2,}/g, "")
      .replace(/\n/g, "<br>");

    // Detectar referencias bíblicas y añadir chips clicables al final
    const bookNamesPattern = BIBLE_BOOKS.map(b => b.name).join("|");
    const refRegex = new RegExp(`\\b((?:${bookNamesPattern})\\s+\\d+(?::\\d+(?:-\\d+)?)?)\\b`, "g");
    const foundCites = new Set();
    let m;
    while ((m = refRegex.exec(text)) !== null) {
      foundCites.add(m[1]);
    }
    if (foundCites.size > 0) {
      const chips = Array.from(foundCites).map(c => `<button class="chat-cite-chip" data-cite="${c}">📖 ${c}</button>`).join("");
      html += `<div style="margin-top:8px;">${chips}</div>`;
    }
    return html;
  }

  async function sendUserChatMessage(text) {
    const clean = (text || "").trim();
    if (!clean) return;
    state.chatMessages.push({ isUser: true, text: clean });
    aiInput.value = "";
    renderChatMessages();

    state.chatMessages.push({ isUser: false, text: "⏳ Consultando las Escrituras..." });
    renderChatMessages();
    const msgIdx = state.chatMessages.length - 1;

    const reply = await askBibleChatAssistant(state.chatMessages.slice(0, -1), clean, partialText => {
      state.chatMessages[msgIdx] = { isUser: false, text: partialText };
      const bodyEl = aiMessagesEl?.querySelector(`.chat-msg-bubble[data-msg-idx="${msgIdx}"] .chat-msg-body`);
      if (bodyEl) {
        bodyEl.innerHTML = formatChatMarkdownWithCitations(partialText);
        if (aiMessagesEl) aiMessagesEl.scrollTop = aiMessagesEl.scrollHeight;
      }
    });
    state.chatMessages[msgIdx] = { isUser: false, text: reply };
    localStorage.setItem("bv_chat_history", JSON.stringify(state.chatMessages.slice(-24)));
    renderChatMessages();
  }

  document.getElementById("btn-open-ai")?.addEventListener("click", () => {
    renderChatMessages();
    aiChatModal.classList.add("open");
  });
  document.getElementById("btn-close-ai-chat")?.addEventListener("click", () => {
    window.speechSynthesis?.cancel();
    aiChatModal.classList.remove("open");
  });
  document.getElementById("btn-clear-ai-chat")?.addEventListener("click", () => {
    state.chatMessages = [];
    localStorage.removeItem("bv_chat_history");
    renderChatMessages();
    showToast("🗑️ Conversación limpiada");
  });
  document.getElementById("btn-send-ai-chat")?.addEventListener("click", () => sendUserChatMessage(aiInput.value));
  aiInput?.addEventListener("keydown", e => {
    if (e.key === "Enter") sendUserChatMessage(aiInput.value);
  });

  document.querySelectorAll(".ai-topic-chip").forEach(chip => {
    chip.addEventListener("click", () => sendUserChatMessage(chip.textContent));
  });

  // Entrada por voz (Micrófono webkitSpeechRecognition)
  document.getElementById("btn-voice-mic")?.addEventListener("click", () => {
    const SpeechRec = window.SpeechRecognition || window.webkitSpeechRecognition;
    if (!SpeechRec) {
      showToast("⚠️ Tu navegador no soporta dictado por voz directo");
      return;
    }
    const rec = new SpeechRec();
    rec.lang = "es-ES";
    showToast("🎤 Escuchando... habla ahora");
    rec.onresult = e => {
      const transcript = e.results[0][0].transcript;
      aiInput.value = transcript;
      sendUserChatMessage(transcript);
    };
    rec.start();
  });

  // =========================================================================
  // 15. MÓDULO 9: AJUSTES, CLOUD SYNC, VERSIONES OFFLINE Y EXPORTADOR PDF/JSON/TXT
  // =========================================================================
  const OFFLINE_CACHE_NAME = "bibleverse-pwa-v2.5.0";
  const activeOfflineDownloads = {};

  async function getOfflineBookCacheCount(versionCode) {
    if (!("caches" in window)) return 0;
    try {
      const cache = await caches.open(OFFLINE_CACHE_NAME);
      const keys = await cache.keys();
      const prefix = `/assets/bible/books/${versionCode}/`;
      let count = 0;
      for (const req of keys) {
        if (req.url.includes(prefix) && req.url.endsWith(".json")) {
          count++;
        }
      }
      return Math.min(count, 66);
    } catch {
      return 0;
    }
  }

  function cleanBollsVerseHtml(html) {
    return (html || "")
      .replace(/<p[^>]*align\s*=\s*['"]?center['"]?[^>]*>.*?<\/p>/gis, " ")
      .replace(/<h[1-6][^>]*>.*?<\/h[1-6]>/gis, " ")
      .replace(/<br\s*\/?>/gi, " ")
      .replace(/<[^>]*>/g, "")
      .replace(/&nbsp;/g, " ")
      .replace(/\s+/g, " ")
      .trim();
  }

  async function downloadVersionOffline(versionCode, { silent = false } = {}) {
    if (!("caches" in window)) {
      if (!silent) showToast("⚠️ Tu navegador no soporta almacenamiento offline");
      return;
    }
    if (activeOfflineDownloads[versionCode]) return;
    activeOfflineDownloads[versionCode] = { current: 0, total: 66 };
    if (!silent) {
      renderSettingsTab();
      if (versionModal?.classList.contains("open")) renderVersionsPicker(currentVersionPickerMode);
    }

    const updateDlProgress = (done) => {
      activeOfflineDownloads[versionCode] = { current: done, total: 66 };
      const pct = Math.round((done / 66) * 100);
      const fill = document.getElementById(`offline-progress-${versionCode}`);
      const label = document.getElementById(`offline-status-${versionCode}`);
      if (fill) fill.style.width = `${pct}%`;
      if (label) label.textContent = `Descargando libros: ${done}/66...`;
      const modalBtn = document.querySelector(`#version-selector-modal .version-dl-btn[disabled]`);
      if (modalBtn) modalBtn.textContent = `⏳ ${pct}%`;
    };

    try {
      const cache = await caches.open(OFFLINE_CACHE_NAME);
      const vMeta = BIBLE_VERSIONS.find(v => v.code === versionCode);

      if (vMeta && !vMeta.isOfflineAsset) {
        let bulkSaved = false;
        try {
          const resp = await fetch(`https://bolls.life/static/translations/${versionCode}.json`);
          if (resp.ok) {
            const allRows = await resp.json();
            if (Array.isArray(allRows) && allRows.length > 1000) {
              const byBook = {};
              for (const row of allRows) {
                const b = Number(row.book);
                const c = String(row.chapter);
                const v = String(row.verse);
                if (!byBook[b]) byBook[b] = {};
                if (!byBook[b][c]) byBook[b][c] = {};
                byBook[b][c][v] = cleanBollsVerseHtml(row.text);
              }
              for (let b = 1; b <= 66; b++) {
                const bookObj = byBook[b] || {};
                const url = `./assets/bible/books/${versionCode}/${b}.json`;
                await cache.put(url, new Response(JSON.stringify(bookObj), {
                  headers: { "Content-Type": "application/json" }
                }));
                updateDlProgress(b);
              }
              bulkSaved = true;
            }
          }
        } catch {
          // Fallback por libro/capítulo si falla la descarga en bloque
        }

        if (!bulkSaved) {
          for (let b = 1; b <= 66; b++) {
            const url = `./assets/bible/books/${versionCode}/${b}.json`;
            const existing = await cache.match(url);
            if (!existing) {
              const bookMeta = BIBLE_BOOKS.find(bk => bk.id === b);
              const totalChapters = bookMeta ? bookMeta.chapters : 1;
              const bookObj = {};
              for (let c = 1; c <= totalChapters; c++) {
                try {
                  const chResp = await fetch(`https://bolls.life/get-chapter/${versionCode}/${b}/${c}/`);
                  if (chResp.ok) {
                    const data = await chResp.json();
                    if (Array.isArray(data)) {
                      bookObj[String(c)] = {};
                      data.forEach((item, idx) => {
                        const vNum = item.verse || (idx + 1);
                        bookObj[String(c)][String(vNum)] = cleanBollsVerseHtml(item.text);
                      });
                    }
                  }
                } catch {
                  // Ignorar error individual de capítulo
                }
              }
              if (Object.keys(bookObj).length > 0) {
                await cache.put(url, new Response(JSON.stringify(bookObj), {
                  headers: { "Content-Type": "application/json" }
                }));
              }
            }
            updateDlProgress(b);
          }
        }
      } else {
        const urls = [];
        for (let b = 1; b <= 66; b++) {
          urls.push(`./assets/bible/books/${versionCode}/${b}.json`);
        }

        const optionalExtras = [
          `./assets/bible/pericopes_${versionCode.toLowerCase()}.json`,
          `./assets/audio/audio_catalog_${versionCode.toLowerCase()}.json`
        ];
        for (const extraUrl of optionalExtras) {
          try {
            const existing = await cache.match(extraUrl);
            if (!existing) {
              const resp = await fetch(extraUrl);
              if (resp.ok) await cache.put(extraUrl, resp);
            }
          } catch {
            // Opcional
          }
        }

        let done = 0;
        const batchSize = 6;
        for (let i = 0; i < urls.length; i += batchSize) {
          const batch = urls.slice(i, i + batchSize);
          await Promise.all(
            batch.map(async (url) => {
              try {
                const existing = await cache.match(url);
                if (!existing) {
                  const resp = await fetch(url);
                  if (resp.ok) await cache.put(url, resp);
                }
              } catch {
                // Ignorar error individual de red
              } finally {
                done++;
                updateDlProgress(done);
              }
            })
          );
        }
      }

      if (!silent) {
        showToast(`✅ ${versionCode} lista para leer 100% sin internet`);
      }
    } catch {
      if (!silent) showToast(`⚠️ Error descargando ${versionCode} offline`);
    } finally {
      delete activeOfflineDownloads[versionCode];
      if (state.currentTab === "settings") {
        renderSettingsTab();
      }
      if (versionModal?.classList.contains("open")) {
        renderVersionsPicker(currentVersionPickerMode);
      }
    }
  }

  async function removeVersionOffline(versionCode) {
    if (!("caches" in window)) return;
    try {
      const cache = await caches.open(OFFLINE_CACHE_NAME);
      const keys = await cache.keys();
      const prefix = `/assets/bible/books/${versionCode}/`;
      await Promise.all(
        keys
          .filter(req => req.url.includes(prefix))
          .map(req => cache.delete(req))
      );
      showToast(`🗑 Caché offline de ${versionCode} liberada`);
      renderSettingsTab();
    } catch {
      showToast("⚠️ No se pudo limpiar la caché");
    }
  }

  async function renderSettingsTab() {
    updatePlansSummariesUI();
    applyReaderVisualPreferences();

    const verList = document.getElementById("settings-versions-list");
    if (!verList) return;

    const counts = {};
    await Promise.all(
      BIBLE_VERSIONS.map(async v => {
        counts[v.code] = await getOfflineBookCacheCount(v.code);
      })
    );

    verList.innerHTML = BIBLE_VERSIONS.map(v => {
      const downloading = activeOfflineDownloads[v.code];
      const cachedCount = counts[v.code] || 0;
      const isComplete = cachedCount >= 66;
      const pct = downloading ? Math.round((downloading.current / downloading.total) * 100) : Math.round((cachedCount / 66) * 100);

      let statusText = "Disponible para descarga (~4 MB • 66 libros)";
      if (downloading) {
        statusText = `Descargando libros: ${downloading.current}/66...`;
      } else if (isComplete) {
        statusText = "✓ 66/66 libros guardados en este dispositivo (100% Offline)";
      } else if (cachedCount > 0) {
        statusText = `Parcial: ${cachedCount}/66 libros guardados offline`;
      }

      return `
        <div class="offline-version-card">
          <div class="offline-version-row">
            <div style="min-width:0;">
              <strong style="color:var(--primary-color);">${v.name} (${v.code})</strong>
              <div id="offline-status-${v.code}" style="font-size:0.76rem; color:var(--text-secondary);">${statusText}</div>
            </div>
            <div style="display:flex; gap:6px; align-items:center;">
              ${isComplete
                ? `<button type="button" class="offline-action-btn done" disabled>✓ Offline</button>
                   <button type="button" class="offline-action-btn danger" data-offline-remove="${v.code}" title="Liberar espacio">🗑</button>`
                : `<button type="button" class="offline-action-btn" data-offline-dl="${v.code}" ${downloading ? "disabled" : ""}>${downloading ? `${pct}%` : (cachedCount > 0 ? "Completar" : "⬇ Descargar")}</button>`
              }
            </div>
          </div>
          ${(downloading || (cachedCount > 0 && !isComplete)) ? `
            <div class="offline-progress-track">
              <div id="offline-progress-${v.code}" class="offline-progress-fill" style="width:${pct}%;"></div>
            </div>
          ` : ""}
        </div>
      `;
    }).join("");

    verList.querySelectorAll("[data-offline-dl]").forEach(btn => {
      btn.addEventListener("click", () => downloadVersionOffline(btn.dataset.offlineDl));
    });
    verList.querySelectorAll("[data-offline-remove]").forEach(btn => {
      btn.addEventListener("click", () => removeVersionOffline(btn.dataset.offlineRemove));
    });
  }

  // Recordatorio diario persistente y notificación
  const reminderToggle = document.getElementById("toggle-daily-reminder");
  const reminderTimeInput = document.getElementById("reminder-time-input");
  if (reminderToggle) {
    reminderToggle.checked = localStorage.getItem("bv_dailyReminder") === "true";
    reminderToggle.addEventListener("change", async () => {
      localStorage.setItem("bv_dailyReminder", String(reminderToggle.checked));
      if (reminderToggle.checked && "Notification" in window && Notification.permission === "default") {
        await Notification.requestPermission();
      }
      showToast(reminderToggle.checked ? "🔔 Recordatorio diario activado" : "🔕 Recordatorio diario desactivado");
    });
  }
  if (reminderTimeInput) {
    reminderTimeInput.value = localStorage.getItem("bv_reminderTime") || "08:00";
    reminderTimeInput.addEventListener("change", () => {
      localStorage.setItem("bv_reminderTime", reminderTimeInput.value || "08:00");
      showToast(`⏰ Hora guardada: ${reminderTimeInput.value}`);
    });
  }

  setInterval(() => {
    if (localStorage.getItem("bv_dailyReminder") !== "true") return;
    const targetTime = localStorage.getItem("bv_reminderTime") || "08:00";
    const now = new Date();
    const hhmm = `${String(now.getHours()).padStart(2, "0")}:${String(now.getMinutes()).padStart(2, "0")}`;
    const todayKey = now.toISOString().slice(0, 10);
    if (hhmm === targetTime && localStorage.getItem("bv_lastReminderSent") !== todayKey) {
      localStorage.setItem("bv_lastReminderSent", todayKey);
      const body = currentVodVerse ? `«${currentVodVerse.text}» — ${currentVodVerse.reference}` : "«Jehová es mi pastor; nada me faltará.» — Salmos 23:1";
      if ("Notification" in window && Notification.permission === "granted") {
        new Notification("BibleVerse • Versículo del Día", { body, icon: "./assets/icons/icon-192.png" });
      } else {
        showToast(`🔔 Versículo del Día: ${body}`);
      }
    }
  }, 30000);

  // Probar notificación diaria
  document.getElementById("btn-test-notification")?.addEventListener("click", async () => {
    if (!("Notification" in window)) {
      showToast("🔔 Versículo del Día: «Todo lo puedo en Cristo que me fortalece.» — Filipenses 4:13");
      return;
    }
    const perm = await Notification.requestPermission();
    if (perm === "granted") {
      new Notification("BibleVerse • Versículo del Día", {
        body: currentVodVerse ? `«${currentVodVerse.text}» — ${currentVodVerse.reference}` : "«Jehová es mi pastor; nada me faltará.» — Salmos 23:1",
        icon: "./assets/icons/icon-192.png"
      });
      showToast("🔔 Notificación enviada");
    } else {
      showToast("🔔 Recordatorio activado en la app");
    }
  });

  // Cloud Sync Modal
  const cloudSyncModal = document.getElementById("cloud-sync-modal");
  document.getElementById("btn-open-cloud-sync")?.addEventListener("click", () => cloudSyncModal.classList.add("open"));
  document.getElementById("btn-close-cloud-sync")?.addEventListener("click", () => cloudSyncModal.classList.remove("open"));

  function buildFullBackupPayload() {
    return {
      app: "BibleVerse",
      exportedAt: new Date().toISOString(),
      highlights: state.highlights,
      userPlans: state.userPlans,
      versesUserState: JSON.parse(localStorage.getItem("bv_verses_user_state") || "{}"),
      customVerses: JSON.parse(localStorage.getItem("bv_custom_verses") || "[]")
    };
  }

  document.getElementById("btn-download-backup-json")?.addEventListener("click", () => {
    const blob = new Blob([JSON.stringify(buildFullBackupPayload(), null, 2)], { type: "application/json" });
    const a = document.createElement("a");
    a.href = URL.createObjectURL(blob);
    a.download = `BibleVerse_Backup_${new Date().toISOString().slice(0, 10)}.json`;
    a.click();
    showToast("⬇️ Archivo de respaldo descargado");
  });

  document.getElementById("btn-copy-sync-code")?.addEventListener("click", () => {
    navigator.clipboard.writeText(JSON.stringify(buildFullBackupPayload()));
    showToast("📋 Código de sincronización copiado");
  });

  document.getElementById("restore-file-input")?.addEventListener("change", e => {
    const file = e.target.files[0];
    if (!file) return;
    const reader = new FileReader();
    reader.onload = ev => {
      document.getElementById("restore-sync-textarea").value = ev.target.result;
    };
    reader.readAsText(file);
  });

  document.getElementById("btn-apply-restore-sync")?.addEventListener("click", () => {
    try {
      const raw = document.getElementById("restore-sync-textarea").value.trim();
      const data = JSON.parse(raw);
      if (data.highlights) {
        state.highlights = data.highlights;
        saveHighlightsState();
      }
      if (data.userPlans) {
        state.userPlans = data.userPlans;
        saveUserPlans();
      }
      if (data.versesUserState) {
        localStorage.setItem("bv_verses_user_state", JSON.stringify(data.versesUserState));
      }
      if (data.customVerses) {
        localStorage.setItem("bv_custom_verses", JSON.stringify(data.customVerses));
      }
      initVersesCollection();
      renderPopularVersesFeed();
      cloudSyncModal.classList.remove("open");
      showToast("✅ Respaldo restaurado exitosamente");
    } catch (e) {
      showToast("⚠️ Formato de respaldo inválido");
    }
  });

  // Exportador (PDF Imprimible, TXT, JSON)
  const exportModal = document.getElementById("export-modal");
  document.getElementById("btn-open-export-dialog")?.addEventListener("click", () => exportModal.classList.add("open"));
  document.getElementById("btn-export-from-favs")?.addEventListener("click", () => exportModal.classList.add("open"));
  document.getElementById("btn-close-export")?.addEventListener("click", () => exportModal.classList.remove("open"));

  function exportVersesToPrintablePDF(versesArray, docTitle = "Mi Colección Bíblica - BibleVerse") {
    const win = window.open("", "_blank");
    if (!win) {
      showToast("⚠️ Permite las ventanas emergentes para ver el PDF");
      return;
    }
    const rowsHtml = versesArray.map(v => `
      <div style="margin-bottom:22px; padding-bottom:14px; border-bottom:1px solid #CBD5E1;">
        <h3 style="margin:0 0 6px; color:#1E3A5F;">${v.reference} ${v.topic ? `• <span style="color:#9E6B28;">${v.topic}</span>` : ""}</h3>
        <p style="font-family:Georgia,serif; font-size:1.08rem; line-height:1.6; margin:0 0 8px;">«${v.text}»</p>
        ${v.context ? `<p style="font-size:0.88rem; color:#475569; margin:0 0 6px;"><strong>Contexto:</strong> ${v.context}</p>` : ""}
        ${v.notes ? `<p style="font-size:0.88rem; color:#1E293B; background:#F8FAFC; padding:8px; border-left:3px solid #9E6B28;"><strong>Nota personal:</strong> ${v.notes}</p>` : ""}
      </div>
    `).join("");

    win.document.write(`
      <!DOCTYPE html>
      <html lang="es"><head><meta charset="UTF-8"><title>${docTitle}</title></head>
      <body style="font-family:sans-serif; max-width:760px; margin:32px auto; padding:0 20px; color:#0F172A;">
        <h1 style="color:#1E3A5F; border-bottom:2px solid #9E6B28; padding-bottom:10px;">📖 ${docTitle}</h1>
        ${rowsHtml}
        <script>window.onload = () => window.print();<\/script>
      </body></html>
    `);
    win.document.close();
  }

  document.getElementById("btn-export-pdf-collection")?.addEventListener("click", () => {
    const saved = state.versesCollection.filter(v => v.isFavorite || v.isHighlighted || v.notes);
    exportVersesToPrintablePDF(saved.length ? saved : state.versesCollection.slice(0, 25));
    exportModal.classList.remove("open");
  });

  document.getElementById("btn-export-txt-collection")?.addEventListener("click", () => {
    const saved = state.versesCollection.filter(v => v.isFavorite || v.isHighlighted || v.notes);
    const list = saved.length ? saved : state.versesCollection.slice(0, 50);
    const txt = list.map(v => `${v.reference} (${v.topic})\n"${v.text}"\n${v.notes ? `Nota: ${v.notes}\n` : ""}`).join("\n---\n\n");
    const blob = new Blob([txt], { type: "text/plain;charset=utf-8" });
    const a = document.createElement("a");
    a.href = URL.createObjectURL(blob);
    a.download = "BibleVerse_Coleccion.txt";
    a.click();
    exportModal.classList.remove("open");
    showToast("📝 Archivo .txt descargado");
  });

  document.getElementById("btn-export-json-collection")?.addEventListener("click", () => {
    document.getElementById("btn-download-backup-json")?.click();
    exportModal.classList.remove("open");
  });

  // Cerrar cualquier modal haciendo clic en el fondo oscuro
  document.querySelectorAll(".modal-overlay").forEach(overlay => {
    overlay.addEventListener("click", e => {
      if (e.target === overlay) overlay.classList.remove("open");
    });
  });

  // =========================================================================
  // INICIALIZACIÓN DE LA APLICACIÓN Y SERVICE WORKER (OFFLINE PWA)
  // =========================================================================
  function registerServiceWorkerAndOfflinePack() {
    if ("serviceWorker" in navigator) {
      navigator.serviceWorker.register("./sw.js").then(reg => {
        if (reg.waiting) {
          reg.waiting.postMessage({ type: "SKIP_WAITING" });
        }
        reg.addEventListener("updatefound", () => {
          const newWorker = reg.installing;
          if (!newWorker) return;
          newWorker.addEventListener("statechange", () => {
            if (newWorker.state === "installed" && navigator.serviceWorker.controller) {
              newWorker.postMessage({ type: "SKIP_WAITING" });
            }
          });
        });
      }).catch(err => {
        console.warn("[PWA] Error registrando Service Worker:", err);
      });
    }

    // Pre-cachear en segundo plano la versión activa si es offline (por defecto RVR1960)
    // para que usuarios de iPhone puedan leer sin internet tras su primera apertura.
    if (navigator.onLine) {
      const currentVer = BIBLE_VERSIONS.find(v => v.code === state.reader.version && v.isOfflineAsset);
      const targetCode = currentVer ? currentVer.code : "RVR1960";
      setTimeout(async () => {
        const cached = await getOfflineBookCacheCount(targetCode);
        if (cached < 66) {
          downloadVersionOffline(targetCode, { silent: true });
        }
      }, 1500);
    }
  }

  function handleLocationHash() {
    const hash = window.location.hash;
    if (hash === "#reader") {
      switchTab("reader");
    } else if (hash === "#daily") {
      switchTab("verses");
      const vod = document.querySelector(".vod-card");
      if (vod) vod.scrollIntoView({ behavior: "smooth", block: "start" });
    }
  }

  // =========================================================================
  // GESTOS DE NAVEGACIÓN HACIA ATRÁS (ANDROID / IOS PWA HISTORY API)
  // =========================================================================
  var isHandlingPopState = false;

  function hasActiveBackLayer() {
    if (document.querySelector(".modal-overlay.open")) return true;
    if (state.currentTab === "reader" && state.selectedVerses.size > 0) return true;
    if (state.currentTab !== "verses") return true;
    return false;
  }

  function pushBackNavState() {
    if (isHandlingPopState) return;
    try {
      window.history.pushState({ bvNav: true }, "");
    } catch (_) {}
  }

  function setupBackGestureSupport() {
    try {
      window.history.replaceState({ bvRoot: true }, "");
    } catch (_) {}

    // Observar apertura de cualquier modal para registrar un estado en el historial
    const modalObserver = new MutationObserver(mutations => {
      mutations.forEach(m => {
        if (m.type === "attributes" && m.attributeName === "class") {
          const el = m.target;
          const wasOpen = (m.oldValue || "").split(/\s+/).includes("open");
          const isOpen = el.classList.contains("open");
          if (!wasOpen && isOpen) {
            pushBackNavState();
          }
        }
      });
    });

    document.querySelectorAll(".modal-overlay").forEach(overlay => {
      modalObserver.observe(overlay, { attributes: true, attributeFilter: ["class"], attributeOldValue: true });
    });

    window.addEventListener("popstate", e => {
      isHandlingPopState = true;
      let handled = false;
      try {
        const openModals = Array.from(document.querySelectorAll(".modal-overlay.open"));
        if (openModals.length > 0) {
          handled = true;
          // Priorizar el selector unificado de libros si está abierto (z-index 250)
          const topModal = bookSelectorModal?.classList.contains("open")
            ? bookSelectorModal
            : openModals[openModals.length - 1];

          if (topModal === bookSelectorModal) {
            if (currentPickerStep > 1) {
              showPickerStep(currentPickerStep - 1);
            } else {
              bookSelectorModal.classList.remove("open");
            }
          } else if (topModal === plansModal) {
            const readerView = document.getElementById("plan-day-reader-view");
            const daysView = document.getElementById("plan-days-detail-view");
            const customView = document.getElementById("create-custom-plan-view");
            if (readerView && readerView.style.display !== "none") {
              showPlansModalView("DAYS");
            } else if (
              (daysView && daysView.style.display !== "none") ||
              (customView && customView.style.display !== "none")
            ) {
              showPlansModalView("MAIN");
            } else {
              plansModal.classList.remove("open");
            }
          } else {
            topModal.classList.remove("open");
          }
        } else if (state.currentTab === "reader" && state.selectedVerses.size > 0) {
          handled = true;
          clearVerseSelection();
        } else if (state.currentTab !== "verses") {
          handled = true;
          switchTab("verses");
        }
        if (handled) triggerHaptic(12);
      } finally {
        isHandlingPopState = false;
      }

      // Si aún quedan capas activas y se alcanzó el estado raíz, re-empujar estado protector
      if (hasActiveBackLayer() && e.state && e.state.bvRoot) {
        pushBackNavState();
      }
    });
  }

  setupBackGestureSupport();
  initVersesCollection();
  applyReaderVisualPreferences();
  updateContinueReadingBanner();
  renderVerseOfTheDay();
  renderTopicFilterChips();
  renderPopularVersesFeed();
  initPhotoBackgroundThumbs();
  updatePlansSummariesUI();
  triggerFullBibleSearch();
  registerServiceWorkerAndOfflinePack();
  handleLocationHash();
  window.addEventListener("hashchange", handleLocationHash);
});
