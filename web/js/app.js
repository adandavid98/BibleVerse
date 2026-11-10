/**
 * BibleVerse Web PWA - Lógica de Interfaz y Estado
 */

// Estado global de la aplicación
const AppState = {
  activeTab: 'verses',
  currentBookId: 19, // Salmos
  currentChapter: 23,
  theme: localStorage.getItem('bv_theme') || 'light',
  fontSize: parseInt(localStorage.getItem('bv_font_size') || '17'),
  selectedVerse: null,
  highlights: JSON.parse(localStorage.getItem('bv_highlights') || '{}'),
  favorites: JSON.parse(localStorage.getItem('bv_favorites') || '[]'),
};

// Inicialización cuando carga el DOM
document.addEventListener('DOMContentLoaded', () => {
  initTheme();
  initTabs();
  initVerseOfTheDay();
  initReader();
  initSearch();
  initFavorites();
  initModals();
  registerServiceWorker();
});

// 1. Registro del Service Worker para PWA Offline
function registerServiceWorker() {
  if ('serviceWorker' in navigator) {
    window.addEventListener('load', () => {
      navigator.serviceWorker.register('./sw.js')
        .then(reg => console.log('PWA ServiceWorker registrado con éxito:', reg.scope))
        .catch(err => console.warn('Error al registrar ServiceWorker:', err));
    });
  }
}

// 2. Control del Tema (Luz, Sepia, Oscuro)
function initTheme() {
  setTheme(AppState.theme);

  document.querySelectorAll('.theme-card').forEach(card => {
    card.addEventListener('click', () => {
      const selected = card.getAttribute('data-theme-val');
      setTheme(selected);
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

// 3. Sistema de Pestañas (Material 3 Navigation Bar)
function initTabs() {
  const navItems = document.querySelectorAll('.nav-item');
  const panes = document.querySelectorAll('.tab-pane');

  navItems.forEach(item => {
    item.addEventListener('click', () => {
      const tabTarget = item.getAttribute('data-tab');
      AppState.activeTab = tabTarget;

      navItems.forEach(n => n.classList.remove('active'));
      panes.forEach(p => p.classList.remove('active'));

      item.classList.add('active');
      const activePane = document.getElementById(`tab-${tabTarget}`);
      if (activePane) activePane.classList.add('active');

      // Ocultar barra flotante de resaltado si cambiamos de tab
      const hlToolbar = document.getElementById('highlight-toolbar');
      if (hlToolbar) hlToolbar.classList.remove('visible');
      AppState.selectedVerse = null;

      // Actualizar favoritos si entramos en esa tab
      if (tabTarget === 'favorites') renderFavorites();
    });
  });
}

// 4. Versículo del Día
function initVerseOfTheDay() {
  const v = DAILY_VERSES[0];
  const card = document.getElementById('verse-day-card');
  if (!card) return;

  document.getElementById('vod-quote').textContent = `"${v.text}"`;
  document.getElementById('vod-ref').textContent = `${v.book} ${v.chapter}:${v.verse}`;
  document.getElementById('vod-reflection').textContent = v.reflection;
  document.getElementById('vod-tag').textContent = v.theme;

  document.getElementById('btn-share-vod').addEventListener('click', () => {
    const textToShare = `📖 "${v.text}" - ${v.book} ${v.chapter}:${v.verse}\n\nLeído en BibleVerse App`;
    if (navigator.share) {
      navigator.share({ title: 'BibleVerse', text: textToShare });
    } else {
      navigator.clipboard.writeText(textToShare);
      alert('¡Versículo copiado al portapapeles!');
    }
  });

  document.getElementById('btn-fav-vod').addEventListener('click', () => {
    toggleFavorite({
      book: v.book,
      chapter: v.chapter,
      verse: v.verse,
      text: v.text
    });
  });
}

// 5. Motor del Lector Bíblico 100% Interactivo
function initReader() {
  renderBookAndChapter();
  initHighlightToolbar();
}

function renderBookAndChapter() {
  const book = BIBLE_BOOKS.find(b => b.id === AppState.currentBookId) || BIBLE_BOOKS[18];
  document.getElementById('reader-book-name').textContent = `${book.name} ${AppState.currentChapter}`;

  const versesKey = `${AppState.currentBookId}_${AppState.currentChapter}`;
  const verses = PRELOADED_CHAPTERS[versesKey] || [
    { verse: 1, text: "En el principio creó Dios los cielos y la tierra." },
    { verse: 2, text: "Y la tierra estaba desordenada y vacía, y las tinieblas estaban sobre la faz del abismo." },
    { verse: 3, text: "Y dijo Dios: Sea la luz; y fue la luz." }
  ];

  const container = document.getElementById('bible-verses-list');
  container.innerHTML = '';

  verses.forEach(v => {
    const row = document.createElement('div');
    row.className = 'verse-row';
    const highlightKey = `${versesKey}_${v.verse}`;
    const existingColor = AppState.highlights[highlightKey];
    if (existingColor) {
      row.classList.add(`hl-${existingColor}`);
    }

    row.innerHTML = `<span class="verse-num">${v.verse}</span>${v.text}`;
    row.addEventListener('click', () => onVerseClick(row, v, highlightKey));
    container.appendChild(row);
  });
}

function onVerseClick(rowElement, verseData, highlightKey) {
  const toolbar = document.getElementById('highlight-toolbar');

  if (AppState.selectedVerse === highlightKey) {
    // Deseleccionar
    rowElement.classList.remove('selected');
    AppState.selectedVerse = null;
    toolbar.classList.remove('visible');
    return;
  }

  document.querySelectorAll('.verse-row').forEach(r => r.classList.remove('selected'));
  rowElement.classList.add('selected');
  AppState.selectedVerse = highlightKey;
  AppState.selectedVerseData = verseData;
  toolbar.classList.add('visible');
}

function initHighlightToolbar() {
  document.querySelectorAll('.color-dot').forEach(dot => {
    dot.addEventListener('click', () => {
      if (!AppState.selectedVerse) return;
      const color = dot.getAttribute('data-color');
      
      if (color === 'clear') {
        delete AppState.highlights[AppState.selectedVerse];
      } else {
        AppState.highlights[AppState.selectedVerse] = color;
      }
      
      localStorage.setItem('bv_highlights', JSON.stringify(AppState.highlights));
      renderBookAndChapter();
      
      const toolbar = document.getElementById('highlight-toolbar');
      toolbar.classList.remove('visible');
      AppState.selectedVerse = null;
    });
  });

  // Botón Copiar en toolbar
  document.getElementById('btn-copy-selected').addEventListener('click', () => {
    if (AppState.selectedVerseData) {
      const book = BIBLE_BOOKS.find(b => b.id === AppState.currentBookId);
      const text = `"${AppState.selectedVerseData.text}" - ${book.name} ${AppState.currentChapter}:${AppState.selectedVerseData.verse}`;
      navigator.clipboard.writeText(text);
      alert('Versículo copiado');
    }
  });
}

// 6. Favoritos
function toggleFavorite(item) {
  const existsIndex = AppState.favorites.findIndex(
    f => f.book === item.book && f.chapter === item.chapter && f.verse === item.verse
  );

  if (existsIndex >= 0) {
    AppState.favorites.splice(existsIndex, 1);
    alert('Eliminado de Favoritos');
  } else {
    AppState.favorites.push(item);
    alert('¡Guardado en Favoritos!');
  }
  localStorage.setItem('bv_favorites', JSON.stringify(AppState.favorites));
}

function renderFavorites() {
  const list = document.getElementById('favorites-list');
  if (!list) return;

  if (AppState.favorites.length === 0) {
    list.innerHTML = `
      <div class="empty-state">
        <p>Aún no has guardado versículos favoritos.</p>
        <p style="font-size:0.8rem; margin-top:8px;">Toca el corazón en cualquier pasaje o versículo para guardarlo aquí.</p>
      </div>
    `;
    return;
  }

  list.innerHTML = AppState.favorites.map(f => `
    <div class="verse-item-card">
      <div style="font-weight:700; color:var(--secondary-color); margin-bottom:6px;">${f.book} ${f.chapter}:${f.verse}</div>
      <div style="font-family:var(--font-serif); line-height:1.6;">"${f.text}"</div>
    </div>
  `).join('');
}

// 7. Buscador
function initSearch() {
  const input = document.getElementById('search-input');
  const results = document.getElementById('search-results');
  if (!input || !results) return;

  input.addEventListener('input', (e) => {
    const query = e.target.value.trim().toLowerCase();
    if (query.length < 2) {
      results.innerHTML = '<div class="empty-state">Escribe una palabra (ej. "pastor", "fuerza", "luz") para buscar.</div>';
      return;
    }

    const matches = DAILY_VERSES.filter(v => 
      v.text.toLowerCase().includes(query) || 
      v.theme.toLowerCase().includes(query) ||
      v.book.toLowerCase().includes(query)
    );

    if (matches.length === 0) {
      results.innerHTML = `<div class="empty-state">No se encontraron resultados para "${query}".</div>`;
      return;
    }

    results.innerHTML = matches.map(m => `
      <div class="verse-item-card">
        <span class="verse-tag">${m.theme}</span>
        <div style="font-weight:700; color:var(--secondary-color); margin:6px 0;">${m.book} ${m.chapter}:${m.verse}</div>
        <div style="font-family:var(--font-serif); line-height:1.6;">"${m.text}"</div>
      </div>
    `).join('');
  });
}

// 8. Modales (Ajustes y Asistente AI)
function initModals() {
  const settingsModal = document.getElementById('settings-modal');
  const aiModal = document.getElementById('ai-modal');

  document.getElementById('btn-open-settings').addEventListener('click', () => {
    settingsModal.classList.add('open');
  });
  document.getElementById('btn-close-settings').addEventListener('click', () => {
    settingsModal.classList.remove('open');
  });

  document.getElementById('btn-open-ai').addEventListener('click', () => {
    aiModal.classList.add('open');
  });
  document.getElementById('btn-close-ai').addEventListener('click', () => {
    aiModal.classList.remove('open');
  });

  // Cerrar al tocar fuera
  [settingsModal, aiModal].forEach(m => {
    m.addEventListener('click', (e) => {
      if (e.target === m) m.classList.remove('open');
    });
  });

  // Pregunta AI interactiva
  document.getElementById('btn-send-ai-prompt').addEventListener('click', () => {
    const chatBox = document.getElementById('ai-chat-body');
    const input = document.getElementById('ai-chat-input');
    const question = input.value.trim();
    if (!question) return;

    chatBox.innerHTML += `
      <div style="background:var(--surface-color); padding:10px 14px; border-radius:14px; margin-bottom:10px; text-align:right;">
        <strong>Tú:</strong> ${question}
      </div>
      <div style="background:var(--primary-color); color:var(--on-primary); padding:10px 14px; border-radius:14px; margin-bottom:10px;">
        <strong>BibleVerse AI:</strong> Dios tiene promesas eternas sobre este tema. En Filipenses 4:6-7 se nos recuerda no afanarnos por nada, sino presentar nuestras peticiones en oración con acción de gracias.
      </div>
    `;
    input.value = '';
    chatBox.scrollTop = chatBox.scrollHeight;
  });
}
