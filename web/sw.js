const CACHE_NAME = 'bibleverse-pwa-v2.5.9';
const STATIC_ASSETS = [
  './',
  './index.html',
  './app.html',
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

self.addEventListener('message', (event) => {
  if (event.data && event.data.type === 'SKIP_WAITING') {
    self.skipWaiting();
  }
});

self.addEventListener('install', (event) => {
  event.waitUntil(
    caches.open(CACHE_NAME).then((cache) => {
      return cache.addAll(STATIC_ASSETS);
    })
  );
  self.skipWaiting();
});

self.addEventListener('activate', (event) => {
  event.waitUntil(
    caches.keys().then((cacheNames) => {
      return Promise.all(
        cacheNames.map((cache) => {
          if (cache !== CACHE_NAME) {
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

  // App shell files (.html, .js, .css) -> Network first, fallback to cache so updates always apply immediately
  if (
    event.request.mode === 'navigate' ||
    url.pathname.endsWith('.html') ||
    url.pathname.endsWith('.js') ||
    url.pathname.endsWith('.css') ||
    url.pathname === '/'
  ) {
    event.respondWith(
      fetch(event.request)
        .then((networkResponse) => {
          if (networkResponse && networkResponse.status === 200) {
            const clone = networkResponse.clone();
            caches.open(CACHE_NAME).then((cache) => cache.put(event.request, clone));
          }
          return networkResponse;
        })
        .catch(async () => {
          const cached = await caches.match(event.request, { ignoreSearch: true });
          if (cached) return cached;
          if (event.request.mode === 'navigate') {
            return (await caches.match('./app.html')) || (await caches.match('./index.html'));
          }
          return undefined;
        })
    );
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
