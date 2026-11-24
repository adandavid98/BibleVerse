# 💧 Plan de Implementación: Marca de Agua en Lector Bíblico y Tarjetas Compartidas

**Proyecto:** BibleVerse (`Android APK` + `Web PWA`)  
**Ubicación:** `PLAN_MARCA_DE_AGUA_BIBLIA_Y_COMPARTIR.md`  
**Fecha:** 24 de noviembre de 2026  
**Texto canónico de la marca de agua:** `"BibleVerse • Lámpara a mis pies"`  

> **Nota sobre la cadena exacta:** En el código actual de Android (`CardBitmapRenderer.kt` líneas `454` y `624`) la marca de agua oficial existente es `"BibleVerse • Lámpara a mis pies"` (con tilde en `Lámpara` y punto medio `•`). Todo el plan centraliza esa cadena en una sola constante por plataforma para que, si deseas usar guion (`"BibleVerse - Lámpara a mis pies"`), baste modificar una única línea en Android y otra en Web.

---

## 1. 🎯 Alcance y Objetivos

Este plan unifica la identidad visual de **BibleVerse** en dos frentes y en ambas plataformas (**Android APK** y **Web PWA**):

1. **Pie de capítulo en el Lector Bíblico (66 libros, todos los capítulos):**
   - Mostrar al final de cada capítulo (tanto en lectura por capítulo como en lectura continua entre capítulos) únicamente una marca de agua sutil y centrada estilo YouVersion, adaptada automáticamente a los 4 temas de lectura (`Claro`, `Sepia`, `Oscuro` y `Noche`).
   - En la parte inferior del capítulo **no** se mostrará texto adicional, copyright extenso ni botones extra: solo la marca de agua `"BibleVerse • Lámpara a mis pies"`.
2. **Unificación total en Imágenes de Versículos Compartidos:**
   - Garantizar que **todas** las plantillas de tarjetas compartidas (Lector, Favoritos, Buscador/Lista y Versículo del Día) muestren exactamente `"BibleVerse • Lámpara a mis pies"` tanto en **Android APK** como en **Web PWA**, incluyendo las vistas previas en pantalla.

### Vista conceptual en el pie de capítulo (Lector Bíblico)

```text
  ...
  24 Y considerémonos unos a otros para estimularnos al amor y a las buenas obras;
  25 no dejando de congregarnos, como algunos tienen por costumbre, sino exhortándonos;
     y tanto más, cuanto veis que aquel día se acerca.

                           ──────────────────
                       BibleVerse • Lámpara a mis pies

  [ Tarjeta del Plan de Lectura si aplica / Fin del área desplazable ]
```

---

## 2. 🔍 Auditoría del Estado Actual

| Plataforma | Archivo | Ubicación actual | Estado actual | Cambio requerido |
| :--- | :--- | :--- | :--- | :--- |
| **Android APK** | `app/src/main/java/com/example/ui/reader/BibleReaderScreen.kt` | `itemsIndexed(uiState.verses)` (`L505-L623`) | Sin marca de agua al final de los capítulos | Renderizar `ChapterWatermarkFooter` al final de cada capítulo (normal y scroll continuo) |
| **Android APK** | `app/src/main/java/com/example/ui/reader/export/CardBitmapRenderer.kt` | `drawMinimalist` (`L145`) | `"BibleVerse"` | Usar `WATERMARK_TEXT` (`"BibleVerse • Lámpara a mis pies"`) |
| **Android APK** | `app/src/main/java/com/example/ui/reader/export/CardBitmapRenderer.kt` | `drawSacredGradient` (`L227`) | `"Lámpara es a mis pies tu palabra"` | Usar `WATERMARK_TEXT` |
| **Android APK** | `app/src/main/java/com/example/ui/reader/export/CardBitmapRenderer.kt` | `drawParchment` (`L315`) | `"El cielo y la tierra pasarán, mas mis palabras no pasarán"` | Usar `WATERMARK_TEXT` |
| **Android APK** | `app/src/main/java/com/example/ui/reader/export/CardBitmapRenderer.kt` | `drawDeepNight` (`L454`) | `"BibleVerse • Lámpara a mis pies"` | Migrar literal a constante `WATERMARK_TEXT` |
| **Android APK** | `app/src/main/java/com/example/ui/reader/export/CardBitmapRenderer.kt` | `renderVerseOfTheDayBitmap` (`L624`) | `"BibleVerse • Lámpara a mis pies"` | Migrar literal a constante `WATERMARK_TEXT` |
| **Android APK** | `app/src/main/java/com/example/ui/reader/components/ShareTemplateDialog.kt` | `MinimalistCardPreview`, `SacredGradientCardPreview`, `ParchmentCardPreview`, `DeepNightCardPreview` (`L201-L395`) | Sin marca de agua en la vista previa en vivo | Agregar pie con `CardBitmapRenderer.WATERMARK_TEXT` para paridad 1:1 con el PNG exportado |
| **Web PWA** | `web/js/app.js` y `docs/js/app.js` | `loadReaderChapter` (`L976-L1072`) | Sin marca de agua al final de los capítulos | Anexar `.chapter-watermark-footer` al final de `htmlChunk` |
| **Web PWA** | `web/js/app.js` y `docs/js/app.js` | `drawShareCanvas` (`L1600`) | `"BibleVerse • La Palabra Viva"` | Reemplazar por constante `BIBLEVERSE_WATERMARK` (`"BibleVerse • Lámpara a mis pies"`) |
| **Web PWA** | `web/css/app.css` y `docs/css/app.css` | Sección del lector (`~L989-L1024`) | Sin estilos `.chapter-watermark-*` | Agregar estilos sutiles ligados a `var(--reader-secondary)` y `var(--reader-font)` |
| **Web PWA** | `web/sw.js` y `docs/sw.js` | `CACHE_NAME` (`L1`) | `'bibleverse-pwa-v2.5.39'` | Subir a `'bibleverse-pwa-v2.5.40'` |

> **Nota de auditoría:** En `PlanCompletedCelebrationDialog.kt` (línea `63`) aparece el versículo bíblico *«Lámpara es a mis pies tu palabra, y lumbrera a mi camino.» — Salmos 119:105* y en `web/index.html` / `docs/index.html` (línea `51`) aparece el titular de portada *La Palabra Viva en tus Manos*. Ambos son contenido editorial legítimo y **no** deben modificarse.

---

## 3. 📱 Fase 1: Android APK — Marca de Agua al Fondo de Cada Capítulo

### Archivo a modificar
- `app/src/main/java/com/example/ui/reader/BibleReaderScreen.kt`

### Diseño técnico
1. En el `LazyColumn` del lector (dentro de `itemsIndexed(uiState.verses)`, líneas `505-623`), calcular en cada versículo si corresponde al **último versículo de ese capítulo**:

```kotlin
val isLastVerseOfChapter = nextVerse == null ||
    nextVerse.chapter != verse.chapter ||
    nextVerse.bookId != verse.bookId
```

2. Inmediatamente después de `CompactVerseRow(...)`, dentro del mismo `Column(modifier = Modifier.fillMaxWidth())`, renderizar el pie de capítulo cuando `isLastVerseOfChapter` sea `true`:

```kotlin
if (isLastVerseOfChapter) {
    ChapterWatermarkFooter(
        themeSecondary = themeSecondary,
        fontFamily = selectedFontFamily
    )
}
```

3. Definir el composable privado `ChapterWatermarkFooter` al final del archivo (junto a `ChapterBreakHeader`):

```kotlin
@Composable
private fun ChapterWatermarkFooter(
    themeSecondary: Color,
    fontFamily: FontFamily
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp, bottom = 14.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Divider(
            color = themeSecondary.copy(alpha = 0.22f),
            thickness = 0.5.dp,
            modifier = Modifier.width(56.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = CardBitmapRenderer.WATERMARK_TEXT,
            fontFamily = fontFamily,
            fontSize = 11.5.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 0.4.sp,
            textAlign = TextAlign.Center,
            color = themeSecondary.copy(alpha = 0.70f)
        )
    }
}
```

*(Recordar añadir `import androidx.compose.ui.text.style.TextAlign` y `import com.example.ui.reader.export.CardBitmapRenderer` en `BibleReaderScreen.kt`).*

### Por qué esta implementación es segura
- **Cobertura total (66 libros, todos los capítulos):** Se dispara automáticamente para cualquier libro y capítulo cargado en `uiState.verses`, independientemente de la versión bíblica (`RVR1960`, `NBLA`, `DHH`, `NTV`, etc.).
- **Compatible con lectura continua:** Cuando `continuousScrollEnabled` añade capítulos siguientes en la misma lista, `nextVerse.chapter != verse.chapter` (o `bookId` distinto al pasar de un libro al siguiente) coloca el pie justo antes del `ChapterBreakHeader` del capítulo siguiente.
- **No rompe índices de scroll ni rangos:** Al renderizarse dentro del `Column` del último ítem del capítulo en lugar de insertar un `item {}` adicional en el `LazyColumn`, los índices `0..verses.lastIndex` se mantienen idénticos (preservando la navegación directa por rango, por ejemplo `Hebreos 10:24-25`).
- **Sin colisión con otros bloques:** Queda por encima de la tarjeta `activePlanSession` (línea `625`), del `CircularProgressIndicator` de `isLoadingMore` (línea `771`) y del `Spacer` dinámico de controles inferiores (línea `789`), y fuera de `CompactVerseRow`, por lo que no afecta la selección, resaltados fusionados ni gestos horizontales.
- **Adaptación automática a los 4 temas:** Usa `themeSecondary` y `selectedFontFamily`, funcionando con contraste sutil en `LIGHT`, `SEPIA`, `DARK` y `NIGHT`.

---

## 4. 🎨 Fase 2: Android APK — Unificación en Imágenes Compartidas y Vista Previa

### 4.1. `app/src/main/java/com/example/ui/reader/export/CardBitmapRenderer.kt`
1. Definir la constante pública en `object CardBitmapRenderer`:

```kotlin
const val WATERMARK_TEXT = "BibleVerse • Lámpara a mis pies"
```

2. Unificar las 5 funciones de renderizado para usar `WATERMARK_TEXT`:
   - `drawMinimalist` (línea `145`): reemplazar `"BibleVerse"` → `WATERMARK_TEXT`.
   - `drawSacredGradient` (línea `227`): reemplazar `"Lámpara es a mis pies tu palabra"` → `WATERMARK_TEXT`.
   - `drawParchment` (línea `315`): reemplazar `"El cielo y la tierra pasarán, mas mis palabras no pasarán"` → `WATERMARK_TEXT`.
   - `drawDeepNight` (línea `454`): reemplazar literal `"BibleVerse • Lámpara a mis pies"` → `WATERMARK_TEXT`.
   - `renderVerseOfTheDayBitmap` (línea `624`): reemplazar literal `"BibleVerse • Lámpara a mis pies"` → `WATERMARK_TEXT`.

*(Nota: en `drawParchment`, `"BibleVerse • Lámpara a mis pies"` es más corto que `"El cielo y la tierra pasarán, mas mis palabras no pasarán"`, por lo que cabe holgadamente con `textSize = 22f` dentro de `1080px` sin desbordes).*

### 4.2. `app/src/main/java/com/example/ui/reader/components/ShareTemplateDialog.kt`
Para que la vista previa interactiva del diálogo coincida con el PNG generado por `CardBitmapRenderer`:
- Agregar al final de la `Column` interna (después de `citationText` con `Spacer(height = 8.dp)`) el `Text(text = CardBitmapRenderer.WATERMARK_TEXT, fontSize = 9.sp, textAlign = TextAlign.Center, ...)` en los 4 previews:
  - `MinimalistCardPreview` (color `Color(0xFF9CA3AF)`)
  - `SacredGradientCardPreview` (color `Color(0xFF93C5FD)`)
  - `ParchmentCardPreview` (color `Color(0xFF7D5836)`)
  - `DeepNightCardPreview` (color `Color(0xFF94A3B8)`)

---

## 5. 🌐 Fase 3: Web PWA — Lector Bíblico y Canvas de Compartir (`web/` y `docs/`)

### 5.1. `web/js/app.js` (y espejo exacto en `docs/js/app.js`)
1. Declarar la constante única cerca del lector / diseñador de compartir:

```js
const BIBLEVERSE_WATERMARK = "BibleVerse • Lámpara a mis pies";
```

2. **Pie de capítulo en `loadReaderChapter(appendContinuous = false)` (`~L1035`):**
   Concatenar el bloque de marca de agua al final de `htmlChunk` (después de `.map(...).join("")`), antes de insertarlo en `versesListEl`:

```js
const watermarkFooterHtml = `
  <div class="chapter-watermark-footer" aria-label="${BIBLEVERSE_WATERMARK}">
    <span class="chapter-watermark-line" aria-hidden="true"></span>
    <span class="chapter-watermark-text">${BIBLEVERSE_WATERMARK}</span>
  </div>
`;
const finalChunk = htmlChunk + watermarkFooterHtml;

if (appendContinuous) {
  const divider = `<div class="pericope-heading" style="margin-top:28px; font-size:1.1rem;">📖 ${book.name} ${state.chapter}</div>`;
  versesListEl.insertAdjacentHTML("beforeend", divider + finalChunk);
} else {
  versesListEl.innerHTML = finalChunk;
}
```

   - **Cobertura automática:** Aplica tanto en carga normal de capítulo como en `appendContinuous = true` al final de cada capítulo en los 66 libros.
   - **Aislamiento de interacciones:** No usa la clase `.verse-row`, por lo que no responde a clics de selección ni interfiere con `refreshAdjacentHighlightMerging()` ni con `state.targetScrollVerse`.

3. **Unificación en `drawShareCanvas()` (`L1600`):**
   En Web PWA todos los flujos de compartir imagen (Versículo del Día en `L418`, tarjetas de lista en `L694` y selección en el lector en `L1607`) pasan por `openShareImageDesigner` → `drawShareCanvas()`. Solo se necesita actualizar la línea `1600`:

```js
// Antes:
ctx.fillText("BibleVerse • La Palabra Viva", W / 2, H - 100);

// Después:
ctx.fillText(BIBLEVERSE_WATERMARK, W / 2, H - 100);
```

### 5.2. `web/css/app.css` (y espejo exacto en `docs/css/app.css`)
Agregar estilos sutiles en la sección del lector bíblico (después de `.verse-row`, respetando las variables CSS existentes de los 4 temas `reader-light`, `reader-sepia`, `reader-dark`, `reader-night`):

```css
.chapter-watermark-footer {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 8px;
  padding: 24px 12px 14px;
  text-align: center;
  user-select: none;
  pointer-events: none;
}

.chapter-watermark-line {
  display: block;
  width: 56px;
  height: 1px;
  background: var(--reader-secondary);
  opacity: 0.22;
}

.chapter-watermark-text {
  font-family: var(--reader-font);
  font-size: 0.72rem;
  font-weight: 500;
  letter-spacing: 0.03em;
  color: var(--reader-secondary);
  opacity: 0.70;
}
```

### 5.3. Caché del Service Worker (`web/sw.js` y `docs/sw.js`)
Actualizar la línea `1` en ambos archivos para invalidar caché de forma limpia en los clientes PWA instalados:
- `'bibleverse-pwa-v2.5.39'` → `'bibleverse-pwa-v2.5.40'`

---

## 6. ✅ Fase 4: Versionado, Pruebas y Despliegue

### 6.1. Versionado de Android (`app/build.gradle.kts`)
- `versionName`: `"2.4.10"` → `"2.4.11"` (línea `23`)
- `versionCode`: `179` → `180` (línea `27`)

### 6.2. Verificaciones automatizadas antes de confirmar
1. Validar sintaxis JS en Web PWA:
   - `node --check web/js/app.js`
   - `node --check docs/js/app.js`
2. Confirmar paridad byte a byte entre `web/` y `docs/` en `js/app.js`, `css/app.css` y `sw.js`.
3. Ejecutar búsqueda recursiva en `app/src/main/java`, `web/` y `docs/` confirmando **0** ocurrencias residuales en renderizadores/marcas de agua de:
   - `"BibleVerse • La Palabra Viva"`
   - `"Lámpara es a mis pies tu palabra"` (fuera de texto bíblico/diálogo de felicitación)
   - `"El cielo y la tierra pasarán, mas mis palabras no pasarán"` (como pie de tarjeta)
4. Compilación Android / Release vía GitHub Actions al etiquetar `v2.4.11` (recordando que en este entorno Windows con OneDrive Gradle local puede bloquear ficheros intermedios, por lo que el APK final se compila y publica en `release.yml`).

### 6.3. Checklist de validación visual manual
- **Lector Android y Web (capítulos normales):** Probar un libro inicial (`Génesis 1`), un libro poético (`Salmos 23`) y el último capítulo (`Apocalipsis 22`) en los 4 temas (`Claro`, `Sepia`, `Oscuro`, `Noche`).
- **Lector en lectura continua:** Confirmar que entre el final de un capítulo y el separador `ChapterBreakHeader` / título del siguiente aparece la marca de agua sin encimarse con versículos ni perícopas.
- **Lector en modo Plan de Lectura:** Confirmar el orden limpio: último versículo → marca de agua → tarjeta del plan (`Completar y continuar...`).
- **Navegación directa a rango:** Abrir un rango como `Hebreos 10:24-25` desde la tarjeta diaria o lista y confirmar que el scroll y resaltado transitorio siguen exactos.
- **Imágenes compartidas Android:** Probar las 4 plantillas de `ShareTemplateDialog` (`Minimalist`, `Sacred Gradient`, `Parchment`, `Deep Night`) tanto en vista previa como en imagen PNG exportada, además de `Compartir Imagen` desde el Versículo del Día (`renderVerseOfTheDayBitmap`).
- **Imágenes compartidas Web PWA:** Probar compartir imagen con fondo fotográfico y con las plantillas de color desde Lector y Versículo del Día confirmando `"BibleVerse • Lámpara a mis pies"` al pie del canvas `1080×1080`.
