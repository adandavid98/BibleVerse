const CACHE_NAME = 'bibleverse-pwa-v2.5.25';
const OFFLINE_BIBLE_CACHE = 'bibleverse-offline-bibles-v1';
const STATIC_ASSETS = [
  './',
  './app',
  './app.html',
  './index.html',
  './manifest.json',
  './css/landing.css',
  './css/app.css',
  './js/landing.js',
  './js/data/initial-verses.js',
  './js/data/bible-metadata.js',
  './js/data/plans-catalog.js',
  './js/bible-data.js',
  './js/cloud-sync.js',
  './js/app.js',
  './assets/icons/app-logo.svg',
  './assets/icons/icon-192.png',
  './assets/icons/icon-512.png',
  './assets/icons/apple-touch-icon.png',
  './assets/icons/favicon.png',
  './assets/bible/bible_stories_749.json',
  './assets/bible/cross_references.bin',
  './assets/bible/pericopes_es.json',
  './assets/bible/pericopes_nvi.json',
  './assets/bible/pericopes_tla.json',
  './assets/bible/pericopes_dhh.json',
  './assets/bible/pericopes_nbla.json',
  './assets/bible/pericopes_ntv.json',
  './assets/bible/pericopes_lbla.json',
  './assets/bible/plan_chronological.json',
  './assets/bible/plan_traditional.json',
  './assets/bible/plans/plan_bible_stories.json',
  './assets/bible/plans/thematic_plans.json',
  './assets/drawable/plan_cover_affliction_hope.webp',
  './assets/drawable/plan_cover_armor_of_god.webp',
  './assets/drawable/plan_cover_calling_purpose.webp',
  './assets/drawable/plan_cover_chronological.webp',
  './assets/drawable/plan_cover_classic.webp',
  './assets/drawable/plan_cover_completed.webp',
  './assets/drawable/plan_cover_covenants.webp',
  './assets/drawable/plan_cover_custom.webp',
  './assets/drawable/plan_cover_enduring_love.webp',
  './assets/drawable/plan_cover_eschatology.webp',
  './assets/drawable/plan_cover_eternal_life.webp',
  './assets/drawable/plan_cover_faith_works.webp',
  './assets/drawable/plan_cover_forgiveness.webp',
  './assets/drawable/plan_cover_gods_will.webp',
  './assets/drawable/plan_cover_god_says_you_are.webp',
  './assets/drawable/plan_cover_jesus30.webp',
  './assets/drawable/plan_cover_names_of_god.webp',
  './assets/drawable/plan_cover_nehemiah.webp',
  './assets/drawable/plan_cover_new_life.webp',
  './assets/drawable/plan_cover_peace_anxiety.webp',
  './assets/drawable/plan_cover_pneumatology.webp',
  './assets/drawable/plan_cover_proverbs.webp',
  './assets/drawable/plan_cover_romans.webp',
  './assets/drawable/plan_cover_soul_rest.webp',
  './assets/drawable/plan_cover_stewardship.webp',
  './assets/drawable/plan_cover_stories.webp',
  './assets/drawable/plan_cover_tabernacle.webp',
  './assets/drawable/plan_cover_trinity.webp',
  './assets/drawable/verse_bg_01.webp',
  './assets/drawable/verse_bg_02.webp',
  './assets/drawable/verse_bg_03.webp',
  './assets/drawable/verse_bg_04.webp',
  './assets/drawable/verse_bg_05.webp',
  './assets/drawable/verse_bg_06.webp',
  './assets/drawable/verse_bg_07.webp',
  './assets/drawable/verse_bg_08.webp',
  './assets/drawable/verse_bg_09.webp',
  './assets/drawable/verse_bg_10.webp',
  './assets/drawable/verse_bg_11.webp',
  './assets/drawable/verse_bg_12.webp'
];

// Elimina la bandera `redirected` de respuestas (Vercel cleanUrls devuelve 308 -> /app)
// para evitar que Chrome bloquee respuestas cacheadas en navegación offline.
async function toCleanResponse(response) {
  if (!response || !response.redirected) return response;
  const body = await response.arrayBuffer();
  return new Response(body, {
    status: response.status,
    statusText: response.statusText,
    headers: new Headers(response.headers)
  });
}

self.addEventListener('message', (event) => {
  if (event.data && event.data.type === 'SKIP_WAITING') {
    self.skipWaiting();
  }
});

self.addEventListener('install', (event) => {
  event.waitUntil((async () => {
    const cache = await caches.open(CACHE_NAME);
    await Promise.all(
      STATIC_ASSETS.map(async (assetUrl) => {
        try {
          const resp = await fetch(assetUrl, { cache: 'reload' });
          if (resp && resp.ok) {
            const clean = await toCleanResponse(resp);
            await cache.put(assetUrl, clean);
          }
        } catch {
          // Continuar si un recurso individual falla temporalmente
        }
      })
    );
    // Pre-cachear los 66 libros de las 5 versiones integradas en la instalación del SW
    try {
      const bibleCache = await caches.open(OFFLINE_BIBLE_CACHE);
      const bundledVersions = ['RVR1960', 'NVI', 'TLA', 'DHH', 'NBLA'];
      const allBookUrls = [];
      for (const ver of bundledVersions) {
        for (let b = 1; b <= 66; b++) {
          allBookUrls.push(`./assets/bible/books/${ver}/${b}.json`);
        }
      }
      for (let i = 0; i < allBookUrls.length; i += 18) {
        const batch = allBookUrls.slice(i, i + 18);
        await Promise.all(
          batch.map(async (bookUrl) => {
            try {
              const exists = await bibleCache.match(bookUrl);
              if (!exists) {
                const resp = await fetch(bookUrl);
                if (resp && resp.ok) await bibleCache.put(bookUrl, resp);
              }
            } catch {
              // Ignorar fallo individual
            }
          })
        );
      }
    } catch {
      // Ignorar si falla la apertura de caché
    }
  })());
  self.skipWaiting();
});

self.addEventListener('activate', (event) => {
  event.waitUntil((async () => {
    const cacheNames = await caches.keys();
    await Promise.all(
      cacheNames.map((cache) => {
        if (cache !== CACHE_NAME && cache !== OFFLINE_BIBLE_CACHE) {
          return caches.delete(cache);
        }
      })
    );
    try {
      const bibleCache = await caches.open(OFFLINE_BIBLE_CACHE);
      const updatedRvrBooks = [20, 24, 26, 44, 55, 60];
      await Promise.all(
        updatedRvrBooks.map(async (bookId) => {
          const bookUrl = `./assets/bible/books/RVR1960/${bookId}.json`;
          try {
            const resp = await fetch(bookUrl, { cache: 'reload' });
            if (resp && resp.ok) {
              await bibleCache.put(bookUrl, resp);
            }
          } catch {
            // Si está offline al activar, sanitizeOfflineBookData corrige al leer
          }
        })
      );
    } catch {
      // Ignorar error de caché offline
    }
  })());
  self.clients.claim();
});

self.addEventListener('fetch', (event) => {
  if (event.request.method !== 'GET') return;

  const url = new URL(event.request.url);

  // External API calls (GitHub releases, Gemini AI, Bolls) -> Network first
  if (url.origin !== self.location.origin) {
    return;
  }

  // App shell files (.html, .js, .css, /app) -> Network first con fallback limpio a caché
  if (
    event.request.mode === 'navigate' ||
    url.pathname.endsWith('.html') ||
    url.pathname.endsWith('.js') ||
    url.pathname.endsWith('.css') ||
    url.pathname === '/' ||
    url.pathname === '/app'
  ) {
    event.respondWith((async () => {
      try {
        const networkResponse = await fetch(event.request);
        if (networkResponse && networkResponse.ok) {
          const clean = await toCleanResponse(networkResponse.clone());
          const cache = await caches.open(CACHE_NAME);
          await cache.put(event.request, clean);
        }
        return networkResponse;
      } catch {
        const cached = await caches.match(event.request, { ignoreSearch: true });
        if (cached) return toCleanResponse(cached);
        if (event.request.mode === 'navigate') {
          const fallback =
            (await caches.match('./app')) ||
            (await caches.match('./app.html')) ||
            (await caches.match('./')) ||
            (await caches.match('./index.html'));
          if (fallback) return toCleanResponse(fallback);
        }
        return new Response('Sin conexión', { status: 503, statusText: 'Offline' });
      }
    })());
    return;
  }

  // Heavy offline assets (Bible books JSON, drawables, pericopes, cross_references.bin) -> Cache first
  event.respondWith(
    caches.match(event.request).then((cachedResponse) => {
      if (cachedResponse) return cachedResponse;
      return fetch(event.request).then((networkResponse) => {
        if (!networkResponse || networkResponse.status !== 200) {
          return networkResponse;
        }
        const responseToCache = networkResponse.clone();
        caches.open(CACHE_NAME).then((cache) => {
          cache.put(event.request, responseToCache);
        });
        return networkResponse;
      }).catch(() => new Response('', { status: 404 }));
    })
  );
});
