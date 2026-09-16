# 🚀 Integración con GitHub Actions y GitHub Releases

Esta aplicación cuenta con un sistema automatizado de compilación, publicación y actualización directa en el móvil mediante **GitHub Actions** y **GitHub Releases**.

---

## 1. ¿Cómo publicar una nueva versión con GitHub Actions?

El flujo de trabajo ya está configurado en `.github/workflows/release.yml`. Para generar una nueva versión automáticamente:

### Opción A: Mediante un Tag de Git (Recomendado)
Desde tu terminal o cliente Git, simplemente crea una etiqueta de versión y súbela a GitHub:
```bash
git tag v1.1
git push origin v1.1
```
> GitHub Actions se activará de inmediato, compilará el archivo instalador APK (`VersiculosBiblicos-v1.1.apk`) y creará el nuevo **Release** adjuntando el APK descargable.

### Opción B: Manualmente desde la web de GitHub
1. Ve a tu repositorio en **GitHub**.
2. Entra a la pestaña **Actions**.
3. Selecciona el workflow **"Build and Release Android APK"**.
4. Haz clic en **"Run workflow"**, indica el tag (ej. `v1.1`) y haz clic en el botón verde.

---

## 2. ¿Cómo se actualiza la aplicación desde el teléfono?

1. **Detección Automática**:
   - Cada vez que abres la app, esta consulta en segundo plano si existe un Release más reciente en tu repositorio de GitHub.
   - Si hay una nueva versión (por ejemplo `v1.1` frente a `v1.0`), aparecerá una ventana emergente (*UpdateDialog*) con las novedades y el botón de descarga.

2. **Descarga e Instalación en 1 toque**:
   - Al pulsar **"Descargar e Instalar APK"**, la app descarga el paquete APK directamente mostrando una barra de progreso.
   - Al terminar, se inicia el instalador de Android (`PackageInstaller`) para actualizar la app en tu teléfono sin perder tus favoritos ni notas guardadas.

3. **Acceso Manual en Ajustes**:
   - En la pestaña **Ajustes > Actualizaciones de la Aplicación**:
     - Puedes ver la versión actual instalada.
     - Puedes configurar o editar tu usuario/repositorio de GitHub (por defecto `adandavid98/BibleVerse`).
     - Puedes tocar **"Buscar Actualizaciones Ahora"**.
     - Puedes tocar **"Descargar APK desde GitHub Releases"** para abrir el enlace directo en el navegador web de tu móvil e instalarlo manualmente cuando lo desees.

---

## 3. 🔐 Solución definitiva al error "Conflicto de Paquete"

### ¿Por qué ocurría?
Android requiere estrictamente que cualquier actualización esté firmada con el mismo certificado de firma digital que la versión ya instalada. Antes, GitHub Actions generaba una clave aleatoria temporal en cada compilación, provocando firmas distintas entre versiones.

### Solución aplicada:
Se ha generado e integrado una clave de firma **fija y permanente** (`debug.keystore` y `debug.keystore.base64`) en el repositorio. Ahora **todas las versiones generadas por GitHub Actions compartirán exactamente la misma firma**.

> **Paso único necesario en tu teléfono:**
> 1. Si tu versión actual instalada fue compilada con una clave previa de AI Studio o de un run anterior de GitHub, desinstálala de tu teléfono **una sola vez** (puedes respaldar tus notas antes exportándolas a texto/JSON en la app).
> 2. Instala la nueva versión generada con la firma unificada.
> 3. ¡Listo! A partir de esta instalación, todas las actualizaciones futuras mediante "Buscar Actualización" se instalarán automáticamente **sin ningún conflicto de paquetes**.

---

## 4. 🔥 Configuración de Firebase y Google Sign-In

Para que el inicio de sesión con Google y Firebase funcione correctamente:

### A. Registrar la huella digital SHA-1 en Firebase Console
1. Entra a [Firebase Console](https://console.firebase.google.com/) y abre tu proyecto `bibleverse-app-46983`.
2. Ve a **Configuración del Proyecto** (icono de engranaje ⚙️) > pestaña **General**.
3. Baja hasta **Tus apps** y selecciona tu app Android (`com.aistudio.versiculosbiblia.vdbapp`).
4. Haz clic en **"Agregar huella digital"** y agrega la siguiente huella digital permanente:
   - **SHA-1:** `44:99:64:CA:F2:A6:54:72:7E:84:80:7C:25:2C:E4:FB:68:BD:96:03`
   - **SHA-256:** `51:CC:D0:49:AD:C0:CF:FB:C9:EB:53:C6:18:DC:A6:B4:5D:49:B4:30:8A:F2:0B:6D:76:D1:F7:FB:E8:C5:7F:D2`

### B. Habilitar los Proveedores de Autenticación
1. En el menú lateral de Firebase Console, ve a **Compilación > Authentication > pestaña Sign-in method**.
2. Habilita:
   - **Google** (guarda y asegúrate de que el correo de soporte esté seleccionado).
   - **Correo electrónico/contraseña** (permite a los usuarios registrarse sin Google).
   - **Anónimo** (permite guardar en la nube inmediatamente sin login obligatorio).

### C. ID de Cliente Web (Web Client ID)
1. Al activar Google en Firebase, se genera un **ID de cliente web** (termina en `.apps.googleusercontent.com`).
2. Descarga el archivo `google-services.json` actualizado y reemplázalo en la carpeta `app/` del proyecto.
3. *Alternativamente*, en la app móvil puedes tocar **Ajustes de Nube > ⚙️ Configurar Google Sign-In** y pegar directamente tu Web Client ID sin necesidad de recompilar.
