/**
 * BibleVerse Web PWA - Lógica Completa de Interfaz 1:1 con la App Android
 */

const AppState = {
  activeTab: 'verses',
  currentBookId: 19, // Salmos
  currentChapter: 23,
  currentVersion: localStorage.getItem('bv_version') || 'RVR1960',
  theme: localStorage.getItem('bv_theme') || 'light',
  fontSize: parseInt(localStorage.getItem('bv_font_size') || '18'),
  lineHeight: parseFloat(localStorage.getItem('bv_line_height') || '1.85'),
  fontFamily: localStorage.getItem('bv_font_family') || 'serif',
  selectedVerses: new Set(),
  chapterVersesData: [],
  highlights: JSON.parse(localStorage.getItem('bv_highlights') || '{}'),
  favorites: JSON.parse(localStorage.getItem('bv_favorites') || '[]'),
};

document.addEventListener('DOMContentLoaded', () => {
  initTheme();
  initTypography();
  initTabs();
  initVerseOfTheDay();
  initHomeFeed();
  initReader();
  initSearch();
  initFavorites();
  initModals();
  registerServiceWorker();
});

function registerServiceWorker() {
  if ('serviceWorker' in navigator) {
    window.addEventListener('load', () => {
      navigator.serviceWorker.register('./sw.js').catch(() => {});
    });
  }
}

// ==========================================================================
// 1. Temas y Tipografía (Fidelidad 1:1 con el APK)
// ==========================================================================
function initTheme() {
  setTheme(AppState.theme);
  document.querySelectorAll('.theme-card').forEach(card => {
    card.addEventListener('click', () => {
      setTheme(card.getAttribute('data-theme-val'));
    });
  });
}

function setTheme(themeName) {
  AppState.theme = themeName;
  localStorage.setItem('bv_theme', themeName);
  const container = document.querySelector('.app-container');
  if (themeName === 'light') {
    container.removeAttribute('data-theme');
  } else {
    container.setAttribute('data-theme', themeName);
  }
  document.querySelectorAll('.theme-card').forEach(c => {
    c.classList.toggle('active', c.getAttribute('data-theme-val') === themeName);
  });
}

function initTypography() {
  applyTypography();

  document.getElementById('btn-font-smaller')?.addEventListener('click', () => {
    if (AppState.fontSize > 14) {
      AppState.fontSize -= 2;
      localStorage.setItem('bv_font_size', AppState.fontSize);
      applyTypography();
    }
  });

  document.getElementById('btn-font-larger')?.addEventListener('click', () => {
    if (AppState.fontSize < 30) {
      AppState.fontSize += 2;
      localStorage.setItem('bv_font_size', AppState.fontSize);
      applyTypography();
    }
  });

  document.querySelectorAll('.line-spacing-pill')?.forEach(pill => {
    pill.addEventListener('click', () => {
      AppState.lineHeight = parseFloat(pill.getAttribute('data-spacing'));
      localStorage.setItem('bv_line_height', AppState.lineHeight);
      document.querySelectorAll('.line-spacing-pill').forEach(p => p.classList.remove('active'));
      pill.classList.add('active');
      applyTypography();
    });
  });

  document.querySelectorAll('.font-family-pill')?.forEach(pill => {
    pill.addEventListener('click', () => {
      AppState.fontFamily = pill.getAttribute('data-font');
      localStorage.setItem('bv_font_family', AppState.fontFamily);
      document.querySelectorAll('.font-family-pill').forEach(p => p.classList.remove('active'));
      pill.classList.add('active');
      applyTypography();
    });
  });
}

function applyTypography() {
  const root = document.documentElement;
  root.style.setProperty('--reader-size', `${AppState.fontSize}px`);
  root.style.setProperty('--reader-line-height', AppState.lineHeight);
  root.style.setProperty('--reader-font', AppState.fontFamily === 'serif' ? 'var(--font-serif)' : 'var(--font-family)');
  const sizeLabel = document.getElementById('font-size-label');
  if (sizeLabel) sizeLabel.textContent = `${AppState.fontSize}px`;
}

// ==========================================================================
// 2. Navegación por Pestañas
// ==========================================================================
function initTabs() {
  const navItems = document.querySelectorAll('.nav-item');
  const panes = document.querySelectorAll('.tab-pane');

  navItems.forEach(item => {
    item.addEventListener('click', () => {
      const tabTarget = item.getAttribute('data-tab');
      switchTab(tabTarget);
    });
  });
}

function switchTab(tabTarget) {
  AppState.activeTab = tabTarget;
  document.querySelectorAll('.nav-item').forEach(n => {
    n.classList.toggle('active', n.getAttribute('data-tab') === tabTarget);
  });
  document.querySelectorAll('.tab-pane').forEach(p => {
    p.classList.toggle('active', p.id === `tab-${tabTarget}`);
  });

  clearVerseSelection();
  if (tabTarget === 'favorites') renderFavorites();
}

// ==========================================================================
// 3. Pestaña Versículos (Home Feed & Devocionales)
// ==========================================================================
function initVerseOfTheDay() {
  const v = DEVOTIONAL_VERSES[0];
  document.getElementById('vod-quote').textContent = `"${v.text}"`;
  document.getElementById('vod-ref').textContent = `${v.book} ${v.chapter}:${v.verse}`;
  document.getElementById('vod-reflection').textContent = v.reflection;
  document.getElementById('vod-tag').textContent = v.theme;

  document.getElementById('btn-fav-vod')?.addEventListener('click', () => {
    toggleFavorite({ book: v.book, chapter: v.chapter, verse: v.verse, text: v.text });
  });

  document.getElementById('btn-share-vod')?.addEventListener('click', () => {
    shareVerseText(`${v.book} ${v.chapter}:${v.verse}`, v.text);
  });
}

function initHomeFeed() {
  const feed = document.getElementById('popular-verses-feed');
  const chips = document.querySelectorAll('.chips-scroll .chip');

  function renderCategory(cat) {
    const list = cat === 'Todos' ? DEVOTIONAL_VERSES : DEVOTIONAL_VERSES.filter(d => d.theme === cat);
    feed.innerHTML = list.map(item => `
      <div class="verse-item-card" data-book-id="${item.bookId}" data-chapter="${item.chapter}" data-verse="${item.verse}">
        <span class="verse-tag">${item.theme}</span>
        <div style="font-weight:700; color:var(--secondary-color); margin:6px 0;">${item.book} ${item.chapter}:${item.verse}</div>
        <div style="font-family:var(--font-serif); line-height:1.6; margin-bottom:8px;">"${item.text}"</div>
        <div style="font-size:0.8rem; color:var(--primary-color); font-weight:600;">📖 Toca para leer en el pasaje →</div>
      </div>
    `).join('');

    // Al tocar una tarjeta, navegar directamente a ese pasaje en el lector
    feed.querySelectorAll('.verse-item-card').forEach(card => {
      card.addEventListener('click', () => {
        const bId = parseInt(card.getAttribute('data-book-id'));
        const ch = parseInt(card.getAttribute('data-chapter'));
        loadBookAndChapter(bId, ch);
        switchTab('reader');
      });
    });
  }

  chips.forEach(chip => {
    chip.addEventListener('click', () => {
      chips.forEach(c => c.classList.remove('active'));
      chip.classList.add('active');
      renderCategory(chip.textContent.trim());
    });
  });

  renderCategory('Todos');
}

// ==========================================================================
// 4. Lector Bíblico Completo (Carga Dinámica, Bolls API, Resaltado YouVersion)
// ==========================================================================
async function initReader() {
  setupBookChapterSelector();
  setupVersionSelector();
  setupVerseActionBar();

  // Flechas de navegación de capítulos (Anterior / Siguiente)
  document.getElementById('btn-prev-chapter')?.addEventListener('click', () => {
    if (AppState.currentChapter > 1) {
      loadBookAndChapter(AppState.currentBookId, AppState.currentChapter - 1);
    }
  });

  document.getElementById('btn-next-chapter')?.addEventListener('click', () => {
    const book = BIBLE_BOOKS.find(b => b.id === AppState.currentBookId);
    if (book && AppState.currentChapter < book.chapters) {
      loadBookAndChapter(AppState.currentBookId, AppState.currentChapter + 1);
    }
  });

  await loadBookAndChapter(AppState.currentBookId, AppState.currentChapter);
}

async function loadBookAndChapter(bookId, chapterNum) {
  AppState.currentBookId = bookId;
  AppState.currentChapter = chapterNum;
  clearVerseSelection();

  const book = BIBLE_BOOKS.find(b => b.id === bookId) || BIBLE_BOOKS[18];
  document.getElementById('reader-book-name').textContent = `${book.name} ${chapterNum}`;
  document.getElementById('reader-version-code').textContent = AppState.currentVersion;

  const container = document.getElementById('bible-verses-list');
  container.innerHTML = `
    <div style="text-align:center; padding:40px; color:var(--text-secondary);">
      <div style="font-size:1.5rem; margin-bottom:8px;">📖</div>
      Cargando ${book.name} ${chapterNum}...
    </div>
  `;

  const verses = await BibleService.getChapter(AppState.currentVersion, bookId, chapterNum);
  AppState.chapterVersesData = verses;
  renderVersesList(verses);
}

function renderVersesList(verses) {
  const container = document.getElementById('bible-verses-list');
  container.innerHTML = '';

  verses.forEach(v => {
    const row = document.createElement('div');
    row.className = 'verse-row';
    row.id = `verse-row-${v.verse}`;
    row.setAttribute('data-verse-num', v.verse);

    const highlightKey = `${AppState.currentVersion}_${AppState.currentBookId}_${AppState.currentChapter}_${v.verse}`;
    const hexColor = AppState.highlights[highlightKey];
    if (hexColor) {
      row.style.backgroundColor = hexColor + '40'; // 25% opacidad
    }

    row.innerHTML = `<span class="verse-num">${v.verse}</span><span class="verse-text-body">${v.text}</span>`;
    row.addEventListener('click', () => toggleVerseSelection(v.verse, row));
    container.appendChild(row);
  });
}

// Selección Múltiple y Barra de Acción
function toggleVerseSelection(verseNum, rowElement) {
  if (AppState.selectedVerses.has(verseNum)) {
    AppState.selectedVerses.delete(verseNum);
    rowElement.classList.remove('selected');
  } else {
    AppState.selectedVerses.add(verseNum);
    rowElement.classList.add('selected');
  }

  updateVerseActionBar();
}

function clearVerseSelection() {
  AppState.selectedVerses.clear();
  document.querySelectorAll('.verse-row.selected').forEach(r => r.classList.remove('selected'));
  updateVerseActionBar();
}

function updateVerseActionBar() {
  const bar = document.getElementById('verse-action-bar');
  const countSpan = document.getElementById('selection-count-text');
  const count = AppState.selectedVerses.size;

  if (count > 0) {
    countSpan.textContent = `${count} selec.`;
    bar.classList.add('visible');
  } else {
    bar.classList.remove('visible');
  }
}

function setupVerseActionBar() {
  document.getElementById('btn-clear-selection')?.addEventListener('click', clearVerseSelection);

  // Paleta de colores para resaltar
  document.querySelectorAll('.palette-swatches .color-swatch').forEach(swatch => {
    swatch.addEventListener('click', () => {
      const hex = swatch.getAttribute('data-hex');
      applyHighlightToSelection(hex);
    });
  });

  // Quitar resaltador
  document.getElementById('btn-reset-highlight')?.addEventListener('click', () => {
    applyHighlightToSelection(null);
  });

  // Botón Copiar
  document.getElementById('btn-action-copy')?.addEventListener('click', () => {
    const selectedList = getSelectedVersesList();
    if (selectedList.length === 0) return;
    const book = BIBLE_BOOKS.find(b => b.id === AppState.currentBookId);
    const versesStr = selectedList.map(v => v.verse).join(',');
    const textStr = selectedList.map(v => `${v.verse}. ${v.text}`).join('\n');
    const citation = `${book.name} ${AppState.currentChapter}:${versesStr} (${AppState.currentVersion})`;
    navigator.clipboard.writeText(`"${textStr}"\n\n— ${citation}`);
    alert('Versículos copiados al portapapeles');
    clearVerseSelection();
  });

  // Botón Guardar en Favoritos
  document.getElementById('btn-action-save')?.addEventListener('click', () => {
    const selectedList = getSelectedVersesList();
    const book = BIBLE_BOOKS.find(b => b.id === AppState.currentBookId);
    selectedList.forEach(v => {
      toggleFavorite({
        book: book.name,
        chapter: AppState.currentChapter,
        verse: v.verse,
        text: v.text
      }, false);
    });
    alert('¡Versículos guardados en Favoritos!');
    clearVerseSelection();
  });

  // Botón Comparar Versiones (VerseVersionComparatorBottomSheet)
  document.getElementById('btn-action-compare')?.addEventListener('click', async () => {
    const selectedList = getSelectedVersesList();
    if (selectedList.length === 0) return;
    const firstVerse = selectedList[0];
    const book = BIBLE_BOOKS.find(b => b.id === AppState.currentBookId);

    const modal = document.getElementById('compare-modal');
    const container = document.getElementById('compare-results-list');
    document.getElementById('compare-citation-title').textContent = `${book.name} ${AppState.currentChapter}:${firstVerse.verse}`;
    modal.classList.add('open');

    container.innerHTML = '<div style="text-align:center; padding:30px; color:var(--text-secondary);">Consultando versiones...</div>';
    const comparisons = await BibleService.compareVerse(AppState.currentBookId, AppState.currentChapter, firstVerse.verse);

    container.innerHTML = comparisons.map(c => `
      <div class="compare-version-box">
        <div class="compare-version-name">${c.versionName} (${c.versionCode})</div>
        <div class="compare-version-text">"${c.text}"</div>
      </div>
    `).join('');
  });

  // Botón Explicar con IA (ExplainVerseBottomSheet)
  document.getElementById('btn-action-explain')?.addEventListener('click', () => {
    const selectedList = getSelectedVersesList();
    if (selectedList.length === 0) return;
    const book = BIBLE_BOOKS.find(b => b.id === AppState.currentBookId);
    const versesStr = selectedList.map(v => v.verse).join(',');
    const textStr = selectedList.map(v => v.text).join(' ');

    const modal = document.getElementById('explain-modal');
    document.getElementById('explain-citation').textContent = `${book.name} ${AppState.currentChapter}:${versesStr}`;
    document.getElementById('explain-verse-quote').textContent = `"${textStr}"`;
    document.getElementById('explain-theology-text').innerHTML = `
      <p style="margin-bottom:10px;"><strong>Contexto Histórico:</strong> Este pasaje en ${book.name} fue transmitido para edificar al pueblo en tiempos donde la fidelidad a Dios era puesta a prueba.</p>
      <p style="margin-bottom:10px;"><strong>Significado Teológico:</strong> Enfatiza la gracia incondicional y la soberanía de Dios como el guardián supremo de nuestras vidas.</p>
      <p><strong>Aplicación Práctica:</strong> Lleva este mensaje a tu jornada de hoy descansando en la promesa de que Dios cuida cada uno de tus pasos.</p>
    `;
    modal.classList.add('open');
  });

  // Botón Imagen
  document.getElementById('btn-action-image')?.addEventListener('click', () => {
    const selectedList = getSelectedVersesList();
    const book = BIBLE_BOOKS.find(b => b.id === AppState.currentBookId);
    if (selectedList.length > 0) {
      alert(`Tarjeta generada para ${book.name} ${AppState.currentChapter}:${selectedList[0].verse}`);
    }
  });
}

function getSelectedVersesList() {
  const sortedNums = Array.from(AppState.selectedVerses).sort((a, b) => a - b);
  return sortedNums.map(n => {
    return AppState.chapterVersesData.find(v => v.verse === n) || { verse: n, text: "" };
  });
}

function applyHighlightToSelection(hexColor) {
  AppState.selectedVerses.forEach(vNum => {
    const key = `${AppState.currentVersion}_${AppState.currentBookId}_${AppState.currentChapter}_${vNum}`;
    const row = document.getElementById(`verse-row-${vNum}`);
    if (hexColor) {
      AppState.highlights[key] = hexColor;
      if (row) row.style.backgroundColor = hexColor + '40';
    } else {
      delete AppState.highlights[key];
      if (row) row.style.backgroundColor = 'transparent';
    }
  });

  localStorage.setItem('bv_highlights', JSON.stringify(AppState.highlights));
  clearVerseSelection();
}

// ==========================================================================
// 5. Selector Completo de Libro y Capítulos (BookChapterSelectorSheet)
// ==========================================================================
function setupBookChapterSelector() {
  const modal = document.getElementById('book-selector-modal');
  const openBtn = document.getElementById('btn-select-book');
  const closeBtn = document.getElementById('btn-close-book-selector');
  const backToBooksBtn = document.getElementById('btn-back-to-books');
  const booksView = document.getElementById('selector-step-books');
  const chaptersView = document.getElementById('selector-step-chapters');
  const booksListContainer = document.getElementById('selector-books-list');
  const chaptersGridContainer = document.getElementById('selector-chapters-grid');
  const searchInput = document.getElementById('book-search-input');
  let selectedFilter = 'ALL';
  let tempChosenBook = null;

  openBtn?.addEventListener('click', () => {
    booksView.style.display = 'block';
    chaptersView.style.display = 'none';
    renderBooksList();
    modal.classList.add('open');
  });

  closeBtn?.addEventListener('click', () => modal.classList.remove('open'));
  backToBooksBtn?.addEventListener('click', () => {
    chaptersView.style.display = 'none';
    booksView.style.display = 'block';
  });

  // Filtros de Testamento
  document.querySelectorAll('.testament-tab')?.forEach(tab => {
    tab.addEventListener('click', () => {
      document.querySelectorAll('.testament-tab').forEach(t => t.classList.remove('active'));
      tab.classList.add('active');
      selectedFilter = tab.getAttribute('data-filter');
      renderBooksList();
    });
  });

  // Buscador de Libros
  searchInput?.addEventListener('input', () => renderBooksList());

  function renderBooksList() {
    const q = (searchInput?.value || '').trim().toLowerCase();
    let filtered = BIBLE_BOOKS;
    if (selectedFilter === 'AT') filtered = filtered.filter(b => b.testament === 'AT');
    if (selectedFilter === 'NT') filtered = filtered.filter(b => b.testament === 'NT');
    if (q) {
      filtered = filtered.filter(b => b.name.toLowerCase().includes(q) || b.abbr.toLowerCase().includes(q));
    }

    booksListContainer.innerHTML = filtered.map(b => `
      <div class="version-card-row" data-book-id="${b.id}" style="padding:12px 14px;">
        <div style="display:flex; align-items:center; gap:10px;">
          <span style="font-size:0.8rem; font-weight:bold; color:var(--text-secondary); width:24px;">${b.id}</span>
          <strong>${b.name}</strong>
        </div>
        <span style="font-size:0.75rem; background:var(--surface-variant); padding:4px 8px; border-radius:10px; color:var(--text-secondary);">${b.chapters} cap.</span>
      </div>
    `).join('');

    booksListContainer.querySelectorAll('.version-card-row').forEach(row => {
      row.addEventListener('click', () => {
        const bookId = parseInt(row.getAttribute('data-book-id'));
        tempChosenBook = BIBLE_BOOKS.find(b => b.id === bookId);
        showChaptersGrid(tempChosenBook);
      });
    });
  }

  function showChaptersGrid(book) {
    booksView.style.display = 'none';
    chaptersView.style.display = 'block';
    document.getElementById('chapters-book-title').textContent = book.name;

    chaptersGridContainer.innerHTML = '';
    for (let c = 1; c <= book.chapters; c++) {
      const btn = document.createElement('button');
      btn.className = 'chapter-square-btn' + (c === AppState.currentChapter && book.id === AppState.currentBookId ? ' active' : '');
      btn.textContent = c;
      btn.addEventListener('click', () => {
        modal.classList.remove('open');
        loadBookAndChapter(book.id, c);
      });
      chaptersGridContainer.appendChild(btn);
    }
  }
}

// ==========================================================================
// 6. Selector de Versiones Bíblicas (BibleVersionSelectorDialog)
// ==========================================================================
function setupVersionSelector() {
  const modal = document.getElementById('version-selector-modal');
  const openBtn = document.getElementById('btn-open-version-selector');
  const closeBtn = document.getElementById('btn-close-version-selector');
  const container = document.getElementById('versions-list-container');

  openBtn?.addEventListener('click', () => {
    renderVersions();
    modal.classList.add('open');
  });

  closeBtn?.addEventListener('click', () => modal.classList.remove('open'));

  function renderVersions() {
    container.innerHTML = BIBLE_VERSIONS.map(v => {
      const isSelected = v.code === AppState.currentVersion;
      return `
        <div class="version-card-row ${isSelected ? 'selected' : ''}" data-code="${v.code}">
          <div>
            <div style="display:flex; align-items:center; gap:8px; margin-bottom:4px;">
              <strong>${v.name}</strong>
              <span style="font-size:0.7rem; font-weight:bold; background:var(--primary-color); color:var(--on-primary); padding:2px 8px; border-radius:10px;">${v.tag}</span>
            </div>
            <div style="font-size:0.8rem; color:var(--text-secondary);">${v.desc}</div>
          </div>
          ${isSelected ? '<span style="color:var(--primary-color); font-size:1.2rem; font-weight:bold;">✓</span>' : ''}
        </div>
      `;
    }).join('');

    container.querySelectorAll('.version-card-row').forEach(row => {
      row.addEventListener('click', () => {
        const code = row.getAttribute('data-code');
        AppState.currentVersion = code;
        localStorage.setItem('bv_version', code);
        modal.classList.remove('open');
        loadBookAndChapter(AppState.currentBookId, AppState.currentChapter);
      });
    });
  }
}

// ==========================================================================
// 7. Modales de Ajustes, Explicación, Asistente AI
// ==========================================================================
function initModals() {
  // Ajustes de Lectura
  document.getElementById('btn-open-settings')?.addEventListener('click', () => {
    document.getElementById('settings-modal').classList.add('open');
  });
  document.getElementById('btn-close-settings')?.addEventListener('click', () => {
    document.getElementById('settings-modal').classList.remove('open');
  });

  // Asistente AI
  document.getElementById('btn-open-ai')?.addEventListener('click', () => {
    document.getElementById('ai-modal').classList.add('open');
  });
  document.getElementById('btn-close-ai')?.addEventListener('click', () => {
    document.getElementById('ai-modal').classList.remove('open');
  });

  // Cierres de modales secundarios
  document.getElementById('btn-close-explain')?.addEventListener('click', () => {
    document.getElementById('explain-modal').classList.remove('open');
  });
  document.getElementById('btn-close-compare')?.addEventListener('click', () => {
    document.getElementById('compare-modal').classList.remove('open');
  });

  // Cerrar al hacer clic en el backdrop
  document.querySelectorAll('.modal-overlay').forEach(overlay => {
    overlay.addEventListener('click', (e) => {
      if (e.target === overlay) overlay.classList.remove('open');
    });
  });

  // Chatbot interactivo
  setupAiChat();
}

function setupAiChat() {
  const chatBox = document.getElementById('ai-chat-body');
  const input = document.getElementById('ai-chat-input');
  const sendBtn = document.getElementById('btn-send-ai-prompt');

  function sendQuery(text) {
    if (!text.trim()) return;
    chatBox.innerHTML += `
      <div style="background:var(--surface-color); padding:10px 14px; border-radius:14px; margin-bottom:10px; text-align:right;">
        <strong>Tú:</strong> ${text}
      </div>
      <div style="background:var(--primary-color); color:var(--on-primary); padding:12px 14px; border-radius:14px; margin-bottom:10px; line-height:1.5;">
        <strong>BibleVerse AI:</strong> Las Sagradas Escrituras enseñan que Dios es fiel a sus promesas. En el libro de Salmos encontramos refugio y fortaleza, mientras que en los Evangelios contemplamos la máxima revelación de Su amor a través de Jesucristo.
      </div>
    `;
    input.value = '';
    chatBox.scrollTop = chatBox.scrollHeight;
  }

  sendBtn?.addEventListener('click', () => sendQuery(input.value));
  input?.addEventListener('keydown', (e) => {
    if (e.key === 'Enter') sendQuery(input.value);
  });

  // Chips de preguntas sugeridas
  document.querySelectorAll('.ai-prompt-chip')?.forEach(chip => {
    chip.addEventListener('click', () => sendQuery(chip.textContent.trim()));
  });
}

// ==========================================================================
// 8. Favoritos y Buscador
// ==========================================================================
function toggleFavorite(item, notify = true) {
  const index = AppState.favorites.findIndex(
    f => f.book === item.book && f.chapter === item.chapter && f.verse === item.verse
  );
  if (index >= 0) {
    AppState.favorites.splice(index, 1);
    if (notify) alert('Eliminado de Favoritos');
  } else {
    AppState.favorites.push(item);
    if (notify) alert('¡Guardado en Favoritos!');
  }
  localStorage.setItem('bv_favorites', JSON.stringify(AppState.favorites));
}

function renderFavorites() {
  const list = document.getElementById('favorites-list');
  if (!list) return;

  if (AppState.favorites.length === 0) {
    list.innerHTML = `
      <div style="text-align:center; padding:50px 20px; color:var(--text-secondary);">
        <p style="font-size:1.1rem; margin-bottom:8px;">Aún no tienes versículos guardados.</p>
        <p style="font-size:0.85rem;">En el lector o en el versículo del día, toca "Guardar" para conservarlos aquí.</p>
      </div>
    `;
    return;
  }

  list.innerHTML = AppState.favorites.map((f, idx) => `
    <div class="verse-item-card" style="display:flex; justify-content:space-between; align-items:flex-start;">
      <div style="flex:1;">
        <div style="font-weight:700; color:var(--secondary-color); margin-bottom:6px;">${f.book} ${f.chapter}:${f.verse}</div>
        <div style="font-family:var(--font-serif); line-height:1.6;">"${f.text}"</div>
      </div>
      <button onclick="removeFavoriteByIndex(${idx})" style="background:none; border:none; color:#EF4444; font-size:1.1rem; cursor:pointer; padding:4px 8px;">✕</button>
    </div>
  `).join('');
}

window.removeFavoriteByIndex = function(idx) {
  AppState.favorites.splice(idx, 1);
  localStorage.setItem('bv_favorites', JSON.stringify(AppState.favorites));
  renderFavorites();
};

function initSearch() {
  const input = document.getElementById('search-input');
  const results = document.getElementById('search-results');
  if (!input || !results) return;

  input.addEventListener('input', (e) => {
    const q = e.target.value.trim().toLowerCase();
    if (q.length < 2) {
      results.innerHTML = '<div style="text-align:center; padding:40px 20px; color:var(--text-secondary);">Escribe un término para buscar pasajes (ej. "pastor", "fuerzas", "gracia").</div>';
      return;
    }

    const matches = DEVOTIONAL_VERSES.filter(v =>
      v.text.toLowerCase().includes(q) ||
      v.theme.toLowerCase().includes(q) ||
      v.book.toLowerCase().includes(q)
    );

    if (matches.length === 0) {
      results.innerHTML = `<div style="text-align:center; padding:40px 20px; color:var(--text-secondary);">No se encontraron versículos que coincidan con "${q}".</div>`;
      return;
    }

    results.innerHTML = matches.map(m => `
      <div class="verse-item-card" onclick="loadBookAndChapter(${m.bookId}, ${m.chapter}); switchTab('reader');">
        <span class="verse-tag">${m.theme}</span>
        <div style="font-weight:700; color:var(--secondary-color); margin:6px 0;">${m.book} ${m.chapter}:${m.verse}</div>
        <div style="font-family:var(--font-serif); line-height:1.6;">"${m.text}"</div>
      </div>
    `).join('');
  });
}

function shareVerseText(ref, text) {
  const msg = `📖 "${text}" — ${ref}\n\nLeído en BibleVerse App`;
  if (navigator.share) {
    navigator.share({ title: 'BibleVerse', text: msg });
  } else {
    navigator.clipboard.writeText(msg);
    alert('Versículo copiado para compartir');
  }
}
