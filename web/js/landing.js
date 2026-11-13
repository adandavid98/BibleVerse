/**
 * BibleVerse Landing Page - Lógica Interactiva y Conexión en Vivo con GitHub API
 */

const GITHUB_REPO = 'adandavid98/BibleVerse';
let deferredInstallPrompt = null;

document.addEventListener('DOMContentLoaded', () => {
  fetchLatestGitHubRelease();
  detectUserDevice();
  setupPwaInstallPrompt();
});

// 1. Consulta en Vivo a la GitHub API (Siempre al día con el último commit / release)
async function fetchLatestGitHubRelease() {
  const versionBadge = document.getElementById('gh-version-text');
  const apkMeta = document.getElementById('apk-metadata');
  const apkDownloadBtn = document.getElementById('btn-download-apk');

  // Enlace indestructible de descarga directa permanente
  const permanentDownloadUrl = `https://github.com/${GITHUB_REPO}/releases/latest/download/BibleVerse-latest.apk`;
  if (apkDownloadBtn) {
    apkDownloadBtn.href = permanentDownloadUrl;
  }

  try {
    const response = await fetch(`https://api.github.com/repos/${GITHUB_REPO}/releases/latest`);
    if (!response.ok) throw new Error('Release no encontrado');
    const data = await response.json();

    const tagName = data.tag_name || 'v2.3.8';
    if (versionBadge) versionBadge.textContent = `${tagName} (En Vivo)`;

    // Encontrar archivo APK en los assets
    const apkAsset = data.assets?.find(a => a.name.endsWith('.apk'));
    if (apkAsset) {
      const sizeMB = (apkAsset.size / (1024 * 1024)).toFixed(1);
      const publishDate = new Date(data.published_at).toLocaleDateString('es-ES', {
        month: 'short',
        day: 'numeric'
      });
      if (apkMeta) {
        apkMeta.textContent = `${tagName} • ${sizeMB} MB • Actualizado ${publishDate}`;
      }
      // Si el asset tiene URL específica, podemos usarla o mantener el fallback
      if (apkAsset.browser_download_url && apkDownloadBtn) {
        apkDownloadBtn.href = apkAsset.browser_download_url;
      }
    }
  } catch (error) {
    console.log('Usando endpoint de descarga permanente de respaldo:', error);
    if (versionBadge) versionBadge.textContent = 'v2.3.8 (Última versión)';
    if (apkMeta) apkMeta.textContent = 'Android 7.0+ • APK Oficial';
  }
}

// 2. Detección Inteligente de Dispositivo (iPhone vs Android vs PC)
function detectUserDevice() {
  const userAgent = navigator.userAgent || navigator.vendor || window.opera;
  const isIOS = /iPad|iPhone|iPod/.test(userAgent) && !window.MSStream;
  const isAndroid = /android/i.test(userAgent);

  const btnPwa = document.getElementById('btn-open-pwa');
  const btnApk = document.getElementById('btn-download-apk');

  if (isIOS) {
    // En iPhone, destacar el botón Web/PWA
    if (btnPwa) {
      btnPwa.style.transform = 'scale(1.04)';
      const label = btnPwa.querySelector('.btn-label');
      if (label) label.textContent = 'Recomendado para tu iPhone';
    }
  } else if (isAndroid) {
    // En Android, resaltar la descarga directa de APK
    if (btnApk) {
      btnApk.style.borderColor = 'var(--gold)';
    }
  }
}

// 3. Manejo del Prompt de Instalación PWA (con Logo de BibleVerse)
function setupPwaInstallPrompt() {
  const modal = document.getElementById('install-modal');
  const btnClose = document.getElementById('btn-close-modal');
  const btnAction = document.getElementById('btn-modal-install-action');
  const iosSteps = document.getElementById('ios-install-steps');
  const androidActionBox = document.getElementById('android-install-action');
  const modalBtnOpen = document.getElementById('btn-open-install-modal');
  const btnOpenPwa = document.getElementById('btn-open-pwa');

  // Interceptar evento en Android / Chrome / Edge
  window.addEventListener('beforeinstallprompt', (e) => {
    e.preventDefault();
    deferredInstallPrompt = e;
    console.log('[PWA] Evento beforeinstallprompt capturado con éxito');
  });

  function openInstallModal() {
    const isIOS = /iPad|iPhone|iPod/.test(navigator.userAgent);
    if (modal) modal.classList.add('active');

    if (isIOS) {
      if (iosSteps) iosSteps.style.display = 'block';
      if (androidActionBox) androidActionBox.style.display = 'none';
    } else {
      if (iosSteps) iosSteps.style.display = 'none';
      if (androidActionBox) androidActionBox.style.display = 'block';
    }
  }

  function closeInstallModal() {
    if (modal) modal.classList.remove('active');
  }

  if (modalBtnOpen) modalBtnOpen.addEventListener('click', openInstallModal);
  if (btnOpenPwa) {
    btnOpenPwa.addEventListener('click', (e) => {
      e.preventDefault();
      openInstallModal();
    });
  }
  if (btnClose) btnClose.addEventListener('click', closeInstallModal);
  if (modal) {
    modal.addEventListener('click', (e) => {
      if (e.target === modal) closeInstallModal();
    });
  }

  if (btnAction) {
    btnAction.addEventListener('click', async () => {
      if (deferredInstallPrompt) {
        deferredInstallPrompt.prompt();
        const choice = await deferredInstallPrompt.userChoice;
        console.log('[PWA] Respuesta del usuario:', choice.outcome);
        deferredInstallPrompt = null;
        closeInstallModal();
      } else {
        // Redirigir a la aplicación web
        window.location.href = './app.html';
      }
    });
  }
}

if ('serviceWorker' in navigator) {
  window.addEventListener('load', () => {
    navigator.serviceWorker.register('./sw.js').catch((err) => {
      console.warn('[PWA] Error registrando Service Worker:', err);
    });
  });
}
