const CACHE_NAME = 'bibleverse-pwa-v2.5.15';
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
  './js/app.js',
  './assets/icons/app-logo.svg',
  './assets/icons/icon-192.png',
  './assets/icons/icon-512.png',
  './assets/icons/apple-touch-icon.png',
  './assets/icons/favicon.png',
  './assets/bible/bible_stories_749.json',
  './assets/bible/cross_references.bin',
  './assets/bible/pericopes_es.json',
  './assets/bible/plan_chronological.json',
  './assets/bible/plan_traditional.json',
  './assets/bible/plans/plan_bible_stories.json',
  './assets/bible/plans/thematic_plans.json'
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
  event.waitUntil(
    caches.open(CACHE_NAME).then(async (cache) => {
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
    })
  );
  self.skipWaiting();
});

self.addEventListener('activate', (event) => {
  event.waitUntil(
    caches.keys().then((cacheNames) => {
      return Promise.all(
        cacheNames.map((cache) => {
          if (cache !== CACHE_NAME && cache !== 'bibleverse-offline-bibles-v1') {
            return caches.delete(cache);
          }
        })
      );
    })
  );
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
      });
    })
  );
});
