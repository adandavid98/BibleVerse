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
     - Puedes configurar o editar tu usuario/repositorio de GitHub (por defecto `adandavid9805/versiculos-biblicos`).
     - Puedes tocar **"Buscar Actualizaciones Ahora"**.
     - Puedes tocar **"Descargar APK desde GitHub Releases"** para abrir el enlace directo en el navegador web de tu móvil e instalarlo manualmente cuando lo desees.
