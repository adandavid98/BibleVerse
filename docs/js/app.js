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

  // =========================================================================
  // 1. ESTADO PERSISTENTE Y PREFERENCIAS (Sincronizado en localStorage)
  // =========================================================================
  const state = {
    currentTab: "verses", // "verses" | "reader" | "search" | "favorites" | "settings"
    version: localStorage.getItem("bv_version") || "RVR1960",
    bookId: parseInt(localStorage.getItem("bv_bookId") || "19", 10),
    chapter: parseInt(localStorage.getItem("bv_chapter") || "23", 10),
    targetScrollVerse: null,

    // Preferencias de Apariencia y Lector
    theme: localStorage.getItem("bv_theme") || "light",
    fontFamily: localStorage.getItem("bv_fontFamily") || "serif",
    fontSize: parseInt(localStorage.getItem("bv_fontSize") || "19", 10),
    lineHeight: parseFloat(localStorage.getItem("bv_lineHeight") || "1.8"),
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
    activePlanModalTab: "CATALOG", // "CATALOG" | "ACTIVE" | "COMPLETED"
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
    state.currentTab = tabId;

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
  }

  document.querySelectorAll(".nav-item").forEach(btn => {
    btn.addEventListener("click", () => switchTab(btn.dataset.tab));
  });

  document.getElementById("btn-scroll-top")?.addEventListener("click", () => {
    if (mainScrollContainer) mainScrollContainer.scrollTo({ top: 0, behavior: "smooth" });
  });

  document.getElementById("btn-open-settings")?.addEventListener("click", () => {
    switchTab("settings");
  });

  document.getElementById("btn-back-from-settings")?.addEventListener("click", () => {
    switchTab("verses");
  });

  // Auto-ocultar BottomBar al hacer scroll hacia abajo en el Lector
  let lastScrollY = 0;
  mainScrollContainer?.addEventListener("scroll", () => {
    if (state.currentTab !== "reader") return;
    const currentY = mainScrollContainer.scrollTop;
    if (currentY > lastScrollY + 18 && currentY > 80) {
      mainBottomNav.classList.add("auto-hidden");
    } else if (currentY < lastScrollY - 12 || currentY < 40) {
      mainBottomNav.classList.remove("auto-hidden");
    }
    lastScrollY = currentY;
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

  function renderVerseOfTheDay() {
    const list = state.versesCollection;
    if (!list.length) return;
    const dayOfYear = Math.floor((Date.now() - new Date(new Date().getFullYear(), 0, 0)) / 86400000);
    currentVodVerse = list[dayOfYear % list.length];

    document.getElementById("vod-tag").textContent = currentVodVerse.topic;
    document.getElementById("vod-quote").textContent = `«${currentVodVerse.text}»`;
    document.getElementById("vod-ref").textContent = `${currentVodVerse.reference} (${state.version})`;
    document.getElementById("vod-reflection").textContent = currentVodVerse.context;

    const favBtn = document.getElementById("btn-fav-vod");
    if (favBtn) {
      favBtn.classList.toggle("active", !!currentVodVerse.isFavorite);
    }
  }

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

  // Modal de Filtrar por Libro en la pestaña Versículos
  const bookFilterModal = document.getElementById("book-filter-modal");
  document.getElementById("btn-open-book-filter-modal")?.addEventListener("click", () => {
    renderBookFilterModalList("");
    bookFilterModal.classList.add("open");
  });
  document.getElementById("btn-close-book-filter")?.addEventListener("click", () => {
    bookFilterModal.classList.remove("open");
  });
  document.getElementById("book-filter-search")?.addEventListener("input", e => {
    renderBookFilterModalList(e.target.value);
  });

  function renderBookFilterModalList(searchStr) {
    const listEl = document.getElementById("book-filter-list");
    if (!listEl) return;
    const counts = {};
    state.versesCollection.forEach(v => {
      counts[v.book] = (counts[v.book] || 0) + 1;
    });
    const q = stripAccents(searchStr || "").toLowerCase();
    const booksWithVerses = Object.keys(counts).filter(b => stripAccents(b).toLowerCase().includes(q));

    listEl.innerHTML = booksWithVerses.map(bName => `
      <div class="version-card-row ${state.versesBookFilter === bName ? "selected" : ""}" data-bname="${bName}">
        <strong>${bName}</strong>
        <span class="testament-mini-badge">${counts[bName]} versículos</span>
      </div>
    `).join("");

    listEl.querySelectorAll(".version-card-row").forEach(row => {
      row.addEventListener("click", () => {
        state.versesBookFilter = row.dataset.bname;
        state.versesTestamentFilter = "ALL";
        document.querySelectorAll("#canonical-filter-chips .chip").forEach(c => c.classList.remove("active"));
        const bookChip = document.getElementById("btn-open-book-filter-modal");
        bookChip.textContent = `📚 ${state.versesBookFilter} ✕`;
        bookChip.classList.add("active");
        bookFilterModal.classList.remove("open");
        renderPopularVersesFeed();
      });
    });
  }

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

    // Renderizar tarjetas (con soporte de resaltado, nota e indicador de favorito)
    feed.innerHTML = filtered.slice(0, 120).map(v => {
      const isAT = (v.testament || "").includes("Antiguo");
      const hlStyle = v.highlightColor ? `border-left: 5px solid ${v.highlightColor}; background-color: ${v.highlightColor}22;` : "";
      return `
        <div class="verse-item-card" data-vid="${v.id}" style="${hlStyle}">
          <div class="verse-card-top">
            <div class="verse-card-badges">
              <span class="verse-tag" style="margin:0;">${v.topic}</span>
              <span class="testament-mini-badge">${isAT ? "A.T." : "N.T."}</span>
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

  function initAddVerseForm() {
    if (!addBookSel) return;
    addBookSel.innerHTML = BIBLE_BOOKS.map(b => `<option value="${b.id}">${b.name}</option>`).join("");
    addBookSel.value = String(state.bookId);
    updateAddVerseChapters();
  }

  function updateAddVerseChapters() {
    const b = getBookById(addBookSel.value);
    addChapSel.innerHTML = Array.from({ length: b.chapters }, (_, i) => `<option value="${i + 1}">${i + 1}</option>`).join("");
    updateAddVerseNumbers();
  }

  function updateAddVerseNumbers() {
    const bId = Number(addBookSel.value);
    const ch = Number(addChapSel.value);
    const vCount = getCanonicalVerseCount(bId, ch);
    addNumSel.innerHTML = Array.from({ length: vCount }, (_, i) => `<option value="${i + 1}">${i + 1}</option>`).join("");
    autofillAddVerseText();
  }

  async function autofillAddVerseText() {
    const bId = Number(addBookSel.value);
    const ch = Number(addChapSel.value);
    const vNum = Number(addNumSel.value);
    const ver = addVerSel.value;
    const chData = await fetchChapterVerses(ver, bId, ch);
    const found = chData.verses.find(x => x.verse === vNum);
    if (found) {
      document.getElementById("add-verse-text").value = found.text;
    }
  }

  addBookSel?.addEventListener("change", updateAddVerseChapters);
  addChapSel?.addEventListener("change", updateAddVerseNumbers);
  addNumSel?.addEventListener("change", autofillAddVerseText);
  addVerSel?.addEventListener("change", autofillAddVerseText);

  document.getElementById("btn-fab-add-verse")?.addEventListener("click", () => {
    initAddVerseForm();
    document.getElementById("add-verse-context").value = "";
    document.getElementById("add-verse-notes").value = "";
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
    ctxBox.value = "⏳ Generando contexto histórico y teológico con IA...";
    const res = await generateVerseExegesisWithAI(b.name, ch, vNum, txt, addVerSel.value);
    ctxBox.value = res.text;
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

  async function loadReaderChapter(appendContinuous = false) {
    const book = getBookById(state.bookId);
    if (bookNameBtn) bookNameBtn.textContent = `${book.name} ${state.chapter}`;
    if (versionCodeEl) versionCodeEl.textContent = state.version;
    if (chapterSubtitleEl) chapterSubtitleEl.textContent = `${book.name} • Capítulo ${state.chapter} (${book.testamentFull})`;

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

    const htmlChunk = data.verses.map(v => {
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

  document.getElementById("btn-prev-chapter")?.addEventListener("click", () => navigateChapter(-1));
  document.getElementById("btn-next-chapter")?.addEventListener("click", () => navigateChapter(1));
  document.getElementById("btn-prev-chapter-bottom")?.addEventListener("click", () => navigateChapter(-1));
  document.getElementById("btn-next-chapter-bottom")?.addEventListener("click", () => navigateChapter(1));

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
    if (count > 0) {
      document.getElementById("selection-count-text").textContent = `${count} selec.`;
      actionBar.classList.add("visible");
    } else {
      actionBar.classList.remove("visible");
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
    contentBox.innerHTML = `<div style="padding:24px; text-align:center; color:var(--text-secondary);">✨ Analizando <strong>${payload.citation}</strong> con Inteligencia Artificial y Motor Exegético...</div>`;

    const res = await generateVerseExegesisWithAI(
      payload.book.name,
      state.chapter,
      payload.rangeStr,
      payload.combinedText,
      state.version
    );
    lastExplanationText = `${payload.citation} (${state.version})\n«${payload.combinedText}»\n\n${res.text}`;
    if (badgeEl) badgeEl.textContent = `Fuente: ${res.source}`;

    contentBox.innerHTML = `
      <div class="detail-verse-quote">
        <strong>${payload.citation} (${state.version})</strong><br>
        «${payload.combinedText}»
      </div>
      <div style="white-space:pre-line; line-height:1.68; padding:4px 2px;">${res.text}</div>
    `;
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

  document.getElementById("btn-action-references")?.addEventListener("click", async () => {
    if (state.selectedVerses.size === 0) return;
    const payload = getSelectedVersesPayload();
    const primaryVerse = payload.sorted[0];
    document.getElementById("xref-subtitle").textContent = `${payload.book.name} ${state.chapter}:${primaryVerse}`;
    const container = document.getElementById("xref-list-container");
    container.innerHTML = `<div style="text-align:center; padding:24px;">Cargando referencias cruzadas canónicas...</div>`;
    xrefModal.classList.add("open");

    const { exactRefs, chapterRefs } = await getCrossReferences(state.bookId, state.chapter, primaryVerse);
    const allRefs = [...exactRefs, ...chapterRefs].slice(0, 16);

    if (allRefs.length === 0) {
      container.innerHTML = `<div style="text-align:center; padding:24px; color:var(--text-secondary);">No hay referencias cruzadas directas registradas para este versículo.</div>`;
      return;
    }

    // Cargar vista previa de texto para cada referencia cruzada
    const enriched = await Promise.all(allRefs.map(async ref => {
      const chData = await fetchChapterVerses(state.version, ref.bookId, ref.chapter);
      const vObj = chData.verses.find(v => v.verse === ref.startVerse);
      return {
        ...ref,
        previewText: vObj ? vObj.text : "",
        isExact: ref.sourceVerse === primaryVerse
      };
    }));

    container.innerHTML = enriched.map(item => `
      <div class="detail-section-card xref-item-card" style="cursor:pointer;" data-bid="${item.bookId}" data-ch="${item.chapter}" data-v="${item.startVerse}">
        <div class="detail-section-header" style="margin-bottom:4px;">
          <strong style="color:var(--primary-color);">🔗 ${item.reference}</strong>
          <span class="testament-mini-badge">${item.isExact ? "Directa" : `Paralelo (v.${item.sourceVerse})`}</span>
        </div>
        <p style="font-family:var(--font-serif); font-size:0.9rem; color:var(--text-primary); line-height:1.5;">
          «${item.previewText}»
        </p>
      </div>
    `).join("");

    container.querySelectorAll(".xref-item-card").forEach(card => {
      card.addEventListener("click", () => {
        state.bookId = Number(card.dataset.bid);
        state.chapter = Number(card.dataset.ch);
        state.targetScrollVerse = Number(card.dataset.v);
        xrefModal.classList.remove("open");
        clearVerseSelection();
        loadReaderChapter(false);
      });
    });
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
        grad.addColorStop(0, "#3D2E1E");
        grad.addColorStop(1, "#1F160E");
        textColor = "#F5E6D3";
        accentColor = "#E5B869";
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
  document.getElementById("btn-download-share-image")?.addEventListener("click", () => {
    const canvas = document.getElementById("share-canvas");
    const link = document.createElement("a");
    link.download = `BibleVerse_${currentShareCitation.replace(/[^a-zA-Z0-9]/g, "_")}.png`;
    link.href = canvas.toDataURL("image/png");
    link.click();
    showToast("🖼️ Imagen PNG descargada");
  });

  // ACCIÓN 5 Y 6: COPIAR Y GUARDAR EN COLECCIÓN
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
  // 7. SELECTOR DE 3 PASOS: LIBRO -> CAPÍTULO -> VERSÍCULO (BookChapterSelectorSheet.kt)
  // =========================================================================
  const bookSelectorModal = document.getElementById("book-selector-modal");
  let tempPickerBook = getBookById(state.bookId);
  let tempPickerChapter = state.chapter;
  let pickerTestamentFilter = "ALL";

  function showPickerStep(stepNum) {
    document.getElementById("selector-step-books").style.display = stepNum === 1 ? "block" : "none";
    document.getElementById("selector-step-chapters").style.display = stepNum === 2 ? "block" : "none";
    document.getElementById("selector-step-verses").style.display = stepNum === 3 ? "block" : "none";

    document.getElementById("step-tab-book").classList.toggle("active", stepNum === 1);
    document.getElementById("step-tab-chapter").classList.toggle("active", stepNum === 2);
    document.getElementById("step-tab-verse").classList.toggle("active", stepNum === 3);
  }

  document.getElementById("btn-select-book")?.addEventListener("click", () => {
    tempPickerBook = getBookById(state.bookId);
    tempPickerChapter = state.chapter;
    showPickerStep(1);
    renderPickerBooksList();
    bookSelectorModal.classList.add("open");
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
    const filtered = BIBLE_BOOKS.filter(b => {
      if (pickerTestamentFilter !== "ALL" && b.testament !== pickerTestamentFilter) return false;
      return stripAccents(b.name).toLowerCase().includes(q) || stripAccents(b.category).toLowerCase().includes(q);
    });

    container.innerHTML = filtered.map(b => `
      <div class="version-card-row ${b.id === state.bookId ? "selected" : ""}" data-bid="${b.id}">
        <div>
          <strong>${b.name}</strong>
          <div style="font-size:0.74rem; color:var(--text-secondary);">${b.category} • ${b.testamentFull}</div>
        </div>
        <span class="testament-mini-badge">${b.chapters} caps.</span>
      </div>
    `).join("");

    container.querySelectorAll(".version-card-row").forEach(row => {
      row.addEventListener("click", () => {
        tempPickerBook = getBookById(Number(row.dataset.bid));
        renderPickerChaptersGrid(tempPickerBook);
        showPickerStep(2);
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
        state.bookId = book.id;
        state.chapter = chapter;
        state.targetScrollVerse = Number(btn.dataset.v);
        bookSelectorModal.classList.remove("open");
        loadReaderChapter(false);
      });
    });
  }

  document.getElementById("btn-open-chapter-from-verse-step")?.addEventListener("click", () => {
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

  function renderVersionsPicker() {
    const container = document.getElementById("versions-list-container");
    if (!container) return;
    container.innerHTML = BIBLE_VERSIONS.map(v => `
      <div class="version-card-row ${v.code === state.version ? "selected" : ""}" data-code="${v.code}">
        <div style="padding-right:10px;">
          <div style="display:flex; align-items:center; gap:8px; margin-bottom:4px;">
            <strong style="color:var(--primary-color);">${v.name}</strong>
            <span class="testament-mini-badge">${v.tag}</span>
            <span class="testament-mini-badge" style="color:#16A34A;">${v.isOfflineAsset ? "✓ Offline Integrada" : "☁️ Online + Caché"}</span>
          </div>
          <p style="font-size:0.78rem; color:var(--text-secondary); line-height:1.4;">${v.desc}</p>
        </div>
        <strong style="color:var(--secondary-color);">${v.shortName}</strong>
      </div>
    `).join("");

    container.querySelectorAll(".version-card-row").forEach(row => {
      row.addEventListener("click", () => {
        state.version = row.dataset.code;
        versionModal.classList.remove("open");
        loadReaderChapter(false);
        renderVerseOfTheDay();
        showToast(`📖 Versión cambiada a ${state.version}`);
      });
    });
  }

  document.getElementById("btn-open-version-selector")?.addEventListener("click", () => {
    renderVersionsPicker();
    versionModal.classList.add("open");
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

  function applyReaderVisualPreferences() {
    if (state.theme === "light") {
      document.documentElement.removeAttribute("data-theme");
    } else {
      document.documentElement.setAttribute("data-theme", state.theme);
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

    document.querySelectorAll(".theme-btn, .theme-option-card").forEach(btn => {
      btn.classList.toggle("active", btn.dataset.theme === state.theme);
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

  document.querySelectorAll(".theme-btn, .theme-option-card").forEach(btn => {
    btn.addEventListener("click", () => {
      state.theme = btn.dataset.theme;
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
        container.innerHTML = `<div style="text-align:center; padding:32px; color:var(--text-secondary);">No tienes planes en curso actualmente. Explora el <strong>Catálogo (26)</strong> para iniciar uno.</div>`;
        return;
      }
      container.innerHTML = activeList.map(p => renderPlanCardHTML(p)).join("");
    } else if (state.activePlanModalTab === "COMPLETED") {
      const compList = allPlans.filter(p => {
        const u = state.userPlans[p.id];
        return u && (u.completedDays || []).length >= p.durationDays;
      });
      if (compList.length === 0) {
        container.innerHTML = `<div style="text-align:center; padding:32px; color:var(--text-secondary);">Aún no has completado ningún plan. ¡Comienza hoy tu recorrido bíblico!</div>`;
        return;
      }
      container.innerHTML = compList.map(p => renderPlanCardHTML(p)).join("");
    } else {
      // CATALOG: Botón Crear Plan Personalizado + 26 planes agrupados por categoría
      const categories = [];
      allPlans.forEach(p => {
        if (!categories.includes(p.category)) categories.push(p.category);
      });

      let html = `
        <button class="primary-full-btn" id="btn-open-create-custom-plan" style="margin-bottom:14px;">
          ✨ Crear Plan de Estudio Personalizado a tu Medida
        </button>
      `;

      for (const cat of categories) {
        const catPlans = allPlans.filter(p => p.category === cat);
        html += `<h4 style="font-size:0.78rem; color:var(--secondary-color); margin:12px 0 8px; letter-spacing:0.4px;">${cat}</h4>`;
        html += catPlans.map(p => renderPlanCardHTML(p)).join("");
      }
      container.innerHTML = html;

      document.getElementById("btn-open-create-custom-plan")?.addEventListener("click", () => {
        const sel = document.getElementById("custom-plan-book");
        if (sel) sel.innerHTML = BIBLE_BOOKS.map(b => `<option value="${b.id}">${b.name} (${b.chapters} caps.)</option>`).join("");
        showPlansModalView("CUSTOM");
      });
    }

    container.querySelectorAll(".plan-catalog-card").forEach(card => {
      card.addEventListener("click", () => openPlanDaysDetail(card.dataset.pid));
    });
  }

  function renderPlanCardHTML(p) {
    const u = state.userPlans[p.id];
    const doneCount = u ? (u.completedDays || []).length : 0;
    const pct = Math.min(100, Math.round((doneCount / p.durationDays) * 100));
    const statusLabel = u
      ? (doneCount >= p.durationDays ? "🏆 Completado (100%)" : `🔥 En curso: ${doneCount}/${p.durationDays} días (${pct}%)`)
      : p.badge;

    return `
      <div class="plan-catalog-card" data-pid="${p.id}">
        <img src="./${p.coverImage}" alt="${p.title}" class="plan-card-thumb" loading="lazy">
        <div style="flex:1;">
          <span class="testament-mini-badge" style="color:var(--secondary-color);">${statusLabel}</span>
          <h4 style="font-size:0.93rem; color:var(--primary-color); margin:3px 0;">${p.title}</h4>
          <p style="font-size:0.77rem; color:var(--text-secondary); line-height:1.38;">${p.subtitle}</p>
        </div>
      </div>
    `;
  }

  async function openPlanDaysDetail(planId) {
    state.currentViewingPlanId = planId;
    const allPlans = getAllPlansMetadata();
    const meta = allPlans.find(p => p.id === planId);
    if (!meta) return;

    if (!state.userPlans[planId]) {
      state.userPlans[planId] = { startedAt: Date.now(), completedDays: [] };
      saveUserPlans();
    }

    showPlansModalView("DAYS");
    document.getElementById("plan-detail-title").textContent = meta.title;
    const container = document.getElementById("plan-days-list-container");
    container.innerHTML = `<div style="text-align:center; padding:24px;">Cargando días del plan...</div>`;

    const days = await ensurePlanDaysLoaded(planId);
    const completed = state.userPlans[planId].completedDays || [];
    const pct = Math.min(100, Math.round((completed.length / meta.durationDays) * 100));

    document.getElementById("plan-detail-progress-text").textContent = `${completed.length} de ${meta.durationDays} días completados (${pct}%)`;
    document.getElementById("plan-detail-progress-bar").style.width = `${pct}%`;

    container.innerHTML = days.map(d => {
      const isDone = completed.includes(d.dayNumber);
      return `
        <div class="version-card-row ${isDone ? "selected" : ""}" data-day="${d.dayNumber}">
          <div style="padding-right:10px;">
            <strong style="color:var(--primary-color); font-size:0.9rem;">${d.title}</strong>
            <div style="font-size:0.78rem; color:var(--secondary-color); font-weight:700; margin-top:2px;">📖 ${d.passagesSummary}</div>
          </div>
          <span style="font-size:1.2rem;">${isDone ? "✅" : "⭕"}</span>
        </div>
      `;
    }).join("");

    container.querySelectorAll(".version-card-row").forEach(row => {
      row.addEventListener("click", () => {
        openPlanDayReaderModal(planId, Number(row.dataset.day));
      });
    });
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
      showToast(`🎉 ¡Día ${dayNum} marcado como completado!`);
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

  // Crear Plan Personalizado (CreateCustomPlanDialog.kt)
  document.getElementById("btn-back-from-custom-plan")?.addEventListener("click", () => showPlansModalView("MAIN"));
  document.getElementById("btn-Generate-custom-plan")?.addEventListener("click", () => {
    const title = document.getElementById("custom-plan-title").value.trim() || "Mi Plan de Estudio Bíblico";
    const bookId = Number(document.getElementById("custom-plan-book").value || 19);
    const duration = Number(document.getElementById("custom-plan-duration").value || 7);
    const book = getBookById(bookId);

    const customId = `CUSTOM_${Date.now()}`;
    const customDays = [];
    for (let d = 1; d <= duration; d++) {
      const ch = ((d - 1) % book.chapters) + 1;
      customDays.push({
        dayNumber: d,
        title: `Día ${d}: ${book.name} ${ch}`,
        passagesSummary: `${book.name} ${ch}`,
        primaryBookId: book.id,
        primaryChapter: ch,
        passages: [{ bookId: book.id, bookName: book.name, chapter: ch }],
        storyNarrative: getLocalScholarlyExegesis(book.name, ch, "1", ""),
        spiritualLesson: `Meditación sistemática en ${book.name} capítulo ${ch} dentro de tu plan personalizado "${title}".`,
        historicalContext: "¿Qué enseñanza de este capítulo puedes aplicar hoy en tu vida y oración?"
      });
    }

    state.userPlans[customId] = {
      startedAt: Date.now(),
      completedDays: [],
      customMeta: {
        id: customId,
        title,
        subtitle: `Estudio personalizado en ${book.name} (${duration} días)`,
        durationDays: duration,
        badge: `${duration} Días • Personalizado`,
        coverImage: "assets/drawable/plan_cover_custom_default.webp",
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
  const searchBookDropdown = document.getElementById("search-book-select");
  let currentSearchScope = "ALL";
  let searchDebounceTimer = null;

  if (searchBookDropdown) {
    searchBookDropdown.innerHTML = `<option value="">Por Libro específico...</option>` +
      BIBLE_BOOKS.map(b => `<option value="${b.id}">${b.name}</option>`).join("");
  }

  document.querySelectorAll("#search-scope-chips .chip").forEach(chip => {
    chip.addEventListener("click", () => {
      currentSearchScope = chip.dataset.scope;
      searchBookDropdown.value = "";
      document.querySelectorAll("#search-scope-chips .chip").forEach(c => c.classList.toggle("active", c === chip));
      triggerFullBibleSearch();
    });
  });

  searchBookDropdown?.addEventListener("change", () => {
    if (searchBookDropdown.value) {
      currentSearchScope = "BOOK";
      document.querySelectorAll("#search-scope-chips .chip").forEach(c => c.classList.remove("active"));
    } else {
      currentSearchScope = "ALL";
    }
    triggerFullBibleSearch();
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
      resContainer.innerHTML = `<div style="text-align:center; padding:32px; color:var(--text-secondary);">Escribe una palabra, frase o referencia bíblica (ej. <em>gracia, fe, Salmos 23, Juan 3:16</em>) para buscar en los 66 libros de la Biblia.</div>`;
      statusBar.style.display = "none";
      return;
    }

    resContainer.innerHTML = `<div style="text-align:center; padding:28px; color:var(--text-secondary);">🔍 Buscando "${q}" en la Biblia (${searchVersionSel.value})...</div>`;

    const matches = await searchFullBible(q, {
      version: searchVersionSel.value,
      scope: currentSearchScope,
      bookId: searchBookDropdown.value ? Number(searchBookDropdown.value) : null,
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
    const notesList = state.versesCollection.filter(v => v.notes && v.notes.trim().length > 0);

    // Unificar resaltados de tarjetas + resaltados hechos en el Lector Bíblico
    const hlMap = new Map();
    state.versesCollection.filter(v => v.highlightColor).forEach(v => {
      hlMap.set(v.reference, {
        id: v.id,
        reference: v.reference,
        text: v.text,
        color: v.highlightColor,
        fromCard: true
      });
    });

    Object.values(state.highlights).forEach(h => {
      const ref = `${h.bookName} ${h.chapter}:${h.verse}`;
      if (!hlMap.has(ref)) {
        hlMap.set(ref, {
          bookId: h.bookId,
          chapter: h.chapter,
          verse: h.verse,
          reference: ref,
          text: h.text || "",
          color: h.color,
          fromReader: true
        });
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
        <div class="verse-item-card fav-unified-card" data-vid="${it.id || ""}" data-ref="${it.reference}" style="border-left:5px solid ${borderCol};">
          <div class="verse-card-top">
            <strong style="color:var(--primary-color);">${it.reference}</strong>
            <button class="action-pill btn-fav-goto-reader" data-ref="${it.reference}">📖 Ir al Lector</button>
          </div>
          <p style="font-family:var(--font-serif); font-size:0.95rem; line-height:1.55;">«${it.text}»</p>
          ${it.notes ? `<div class="reflection-box" style="margin-top:8px;"><strong>✏️ Mi Nota:</strong> ${it.notes}</div>` : ""}
        </div>
      `;
    }).join("");

    container.querySelectorAll(".fav-unified-card").forEach(card => {
      card.addEventListener("click", e => {
        if (e.target.closest(".btn-fav-goto-reader")) return;
        const vid = card.dataset.vid;
        if (vid) {
          openVerseDetailDialog(Number(vid));
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
      return `
        <div style="align-self:${msg.isUser ? "flex-end" : "flex-start"}; max-width:88%; background:${msg.isUser ? "var(--primary-color)" : "var(--surface-color)"}; color:${msg.isUser ? "var(--on-primary)" : "var(--text-primary)"}; padding:12px 14px; border-radius:18px; border:1px solid var(--border-color); font-size:0.9rem; line-height:1.58;">
          <div>${formattedHtml}</div>
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
          navigator.clipboard.writeText(m.text);
          showToast("📋 Respuesta copiada");
        }
      });
    });

    aiMessagesEl.querySelectorAll(".btn-chat-tts").forEach(btn => {
      btn.addEventListener("click", () => {
        const m = state.chatMessages[Number(btn.dataset.idx)];
        if (m && "speechSynthesis" in window) {
          window.speechSynthesis.cancel();
          const u = new SpeechSynthesisUtterance(m.text.replace(/[*#_]/g, ""));
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
      .replace(/\*\*(.+?)\*\*/g, "<strong>$1</strong>")
      .replace(/\*(.+?)\*/g, "<em>$1</em>")
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

    state.chatMessages.push({ isUser: false, text: "⏳ Consultando las Escrituras y exégesis teológica..." });
    renderChatMessages();

    const reply = await askBibleChatAssistant(state.chatMessages.slice(0, -1), clean);
    state.chatMessages[state.chatMessages.length - 1] = { isUser: false, text: reply };
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
  function renderSettingsTab() {
    updatePlansSummariesUI();
    applyReaderVisualPreferences();

    const verList = document.getElementById("settings-versions-list");
    if (verList) {
      verList.innerHTML = BIBLE_VERSIONS.map(v => `
        <div class="version-card-row" style="cursor:default;">
          <div>
            <strong style="color:var(--primary-color);">${v.name} (${v.code})</strong>
            <div style="font-size:0.76rem; color:var(--text-secondary);">${v.isOfflineAsset ? "✓ 66 libros integrados localmente (100% Offline)" : "Disponible en línea con caché local"}</div>
          </div>
          <span class="testament-mini-badge" style="color:#16A34A;">✓ Lista</span>
        </div>
      `).join("");
    }
  }

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
  // INICIALIZACIÓN DE LA APLICACIÓN
  // =========================================================================
  initVersesCollection();
  applyReaderVisualPreferences();
  updateContinueReadingBanner();
  renderVerseOfTheDay();
  renderTopicFilterChips();
  renderPopularVersesFeed();
  initPhotoBackgroundThumbs();
  updatePlansSummariesUI();
  triggerFullBibleSearch();
});
