# 📖 Plan Estratégico de Diseño, UX y Arquitectura – BibleVerse
**Rol:** Senior Android Developer & UI/UX Specialist (Kotlin & Jetpack Compose)  
**Proyecto:** BibleVerse Android  
**Fecha:** Octubre 2026  

---

## 1. 🔍 Diagnóstico del Estado Actual (Auditoría Técnica y de Diseño)

### 1.1. ¿Está bien la app actualmente?
La aplicación cuenta con una **base sólida y características de alto valor** (reproducción de audio YouVersion-style, comparación de traducciones, asistente teológico IA con Gemini, y soporte offline robusto con Room). La identidad visual cálida (tonos crema/papiro, sepia y noche profunda) es muy acertada para una app de lectura bíblica y respeta el carácter devocional.

Sin embargo, a nivel de **ingeniería de software Android, ergonomía de interfaz y diseño visual**, existen fricciones importantes que deben corregirse para llevarla a un nivel de producción prémium.

---

## 2. 🏗️ Arquitectura de Código y Estructura de Funciones en Kotlin

### ⚠️ Hallazgos Críticos
1. **El "God Composable" (`HomeScreen.kt` con +2,060 líneas):**
   - Agrupa en un solo archivo: `HomeScreen`, `VersesListTab`, `SearchTab`, `FavoritesAndNotesTab`, `SettingsTab`, diálogos de actualización, nube, exportación, chat y cartas de versículo.
   - **Impacto:** Dificulta el mantenimiento, rompe el principio de responsabilidad única (SRP), incrementa tiempos de compilación y eleva el riesgo de recomposiciones innecesarias.
2. **Violación de Arquitectura MVVM en `SearchTab`:**
   - En la línea 673 de `HomeScreen.kt`, el composable instancia directamente la base de datos Room (`BibleDatabase.getDatabase(...)`) y el repositorio de preferencias mediante `remember`.
   - **Impacto:** Los Composables deben ser funciones puras de UI reactivas a un `UiState`. Las consultas a Room y la lógica de debounce deben residir en un `ViewModel` (ej. `SearchViewModel`).
3. **Estabilidad de Compose y Manejo de Estados:**
   - Hay decenas de `mutableStateOf` dispersos a lo largo de los árboles de composición en lugar de estar unificados en `UiState` inmutables.
   - Colecciones regulares (`List<T>`) en lugar de `ImmutableList` pueden provocar que Compose no considere los parámetros como estables (`@Stable`), forzando recomposiciones de listas enteras.

### 🛠️ Plan de Refactorización de Código
* [ ] **Modularizar por Features:**
  ```text
  ui/
  ├── screens/
  │   ├── home/
  │   │   ├── HomeScreen.kt          // Solo Scaffold, BottomNav y orquestación
  │   │   ├── HomeDashboardTab.kt    // Feed devocional, versículo del día
  │   │   └── components/
  │   ├── reader/                    // (Ya modularizado, optimizar dependencias)
  │   ├── search/
  │   │   ├── SearchScreen.kt
  │   │   └── SearchViewModel.kt     // Mover lógica Room y debounce aquí
  │   ├── favorites/
  │   │   └── FavoritesScreen.kt
  │   └── settings/
  │       └── SettingsScreen.kt
  ```
* [ ] **Unificar Estado y Event Hoisting:** Cada pantalla debe exponer una clase sellada o Data Class inmutable (`UiState`) y una función unificada para eventos (`onEvent(UiEvent)`).

---

## 3. 📱 Ergonomía, Ubicación de Botones y Flujo de Usuario (UX)

### ⚠️ Fricciones Detectadas

#### A. Duplicidad de Acciones (TopBar vs FAB)
- **Problema:** En la pantalla principal, el botón del **Asistente IA** y el botón de **Añadir Versículo** aparecen repetidos dos veces en la misma vista:
  - En las acciones del `TopAppBar` (`btn_top_chat_ai` y `btn_top_add`).
  - En los Floating Action Buttons (`fab_bible_chat` y `fab_add_verse`).
- **Solución UX:**
  - El botón flotante (FAB) debe reservarse para la **acción primaria contextual única** (por ejemplo, "Continuar Lectura" o "Escribir Reflexión / Añadir").
  - El **Asistente IA** encaja mejor como un chip flotante sutil o integrado en el buscador y el lector contextual (preguntar sobre el versículo actual), en lugar de competir con dos FABs superpuestos.

#### B. Inconsistencia de Navegación en Ajustes (Settings)
- **Problema:** El `NavigationBar` inferior tiene 4 ítems: `Versículos (0)`, `Lector (1)`, `Buscador (2)` y `Favoritos (3)`. Para ir a Ajustes se pulsa un icono en la barra superior que establece `selectedTab = 4`. Cuando el usuario está en Ajustes, la barra inferior sigue visible pero con *ninguna* pestaña seleccionada.
- **Solución UX:**
  - **Opción Recomendada:** Manejar Ajustes como una pantalla de detalle con botón "Atrás" propio (ocultando la barra inferior con animación) o moverla al perfil/menú del usuario en la parte superior derecha sin romper el estado del `NavigationBar`.

#### C. Conflicto de Barras Inferiores en el Lector (`BibleReaderScreen`)
- **Problema:** Cuando el audio bíblico está reproduciéndose aparece `BibleAudioBottomBar`. Si el usuario selecciona versículos para resaltar, aparece `VerseActionBar`. Ambos elementos se disputan la zona inferior ("Thumb Zone").
- **Solución UX:**
  - Crear una pila de capas coordinada: Cuando `VerseActionBar` entra en escena, la barra de audio se minimiza automáticamente a una píldora flotante compacta (Mini-Player flotante estilo Spotify/Audible) arriba de la barra de acciones de versículo.

#### D. Ergonomía para una sola mano (Thumb Zone en móviles grandes)
- En el Lector, el cambio de libro y capítulo está en la parte superior izquierda/centro. En teléfonos de 6.7" obliga al usuario a usar las dos manos.
- **Mejora:** Permitir gestos swipe horizontales para cambiar de capítulo (con animación fluida tipo vuelta de página) y acceso rápido al selector de capítulos desde el menú flotante inferior.

---

## 4. 🎨 Diseño Visual y Correcciones Estéticas (Manteniendo la misma línea)

La identidad actual de BibleVerse (estilo clásico, reverente, con tonos pergamino y noche profunda) es excelente. No debe cambiarse radicalmente; debe **refinarse y pulirse según Material Design 3**:

### 4.1. Corrección de Colores y Tokens de Material 3
- **Eliminar colores hexadecimales en duro en componentes:** En `VerseCard.kt` y `BibleReaderScreen.kt` hay referencias fijas como `Color(0xFFE53935)` para el corazón de favoritos o `Color(0xFF1F2937)` para textos en vez de referenciar `MaterialTheme.colorScheme.error` o tokens semánticos del tema activo.
- **Contraste de Accesibilidad:** Revisar el contraste en el modo Sepia entre `SepiaTextSecondary` y `SepiaSurface` para garantizar cumplimiento WCAG AA (mínimo ratio 4.5:1 para lectura continuada).

### 4.2. Jerarquía Tipográfica y Ritmo de Lectura
- **Separación entre Versículos y Números de Versículo:**
  - Los números de versículo en superíndice deben tener un color secundario ligeramente atenuado y un espaciado óptico (`letter-spacing`) refinado para que el ojo fluya naturalmente por el texto sin tropezar con el número.
- **Interlineado Adaptativo:** Aplicar proporciones áureas en la lectura: si el usuario sube el tamaño de fuente, el interlineado (`lineHeight`) debe escalar proporcionalmente (1.4x a 1.6x) para evitar que las líneas se choquen en fuentes grandes.

### 4.3. Modo Lectura Inmersiva (Zen / Clean Reader)
- Al hacer scroll hacia abajo en el Lector, las barras superior e inferior deben retraerse suavemente (`EnterAlways` o `ExitUntilCollapsed`).
- Un simple toque central en la pantalla o scroll hacia arriba debe restaurar los controles al instante.

### 4.4. Micro-interacciones y Feedback Háptico
- Agregar una respuesta háptica sutil (`HapticFeedbackType.LongPress` / `TextHandleMove`) al seleccionar versículos o cambiar colores de subrayado. Esto aporta una sensación de calidad premium de app nativa.

---

## 5. 🚀 Nuevas Implementaciones de Diseño y Funcionalidad

1. **Dashboard de Inicio Devocional (Evolución de Tab 0):**
   - **Card "Continuar Lectura":** En la parte superior, mostrar "¿Dónde te quedaste? Salmos 23:1 (RVR1960)" con botón de un toque para reanudar la lectura directamente.
2. **Plantillas de Imagen de Versículo Compartible ("Verse on Image"):**
   - Actualmente la app genera tarjetas para compartir. Se puede potenciar con 4 estilos predefinidos (Pergamino Minimalista, Paisaje Bíblico Sutil, Noche Profunda y Tipografía Moderna).
3. **Barra Rápida de Notas y Reflexión:**
   - Permitir redactar una nota devocional rápida asociada a un versículo con autoguardado instantáneo.

---

## 6. 📅 Plan de Acción por Fases (Roadmap)

| Fase | Tareas Principales | Prioridad |
| :--- | :--- | :--- |
| **Fase 1: Limpieza Arquitectónica** | • Desacoplar `HomeScreen.kt` en pantallas independientes.<br>• Mover lógica de `SearchTab` a `SearchViewModel`.<br>• Centralizar estados inmutables. | 🔴 Alta |
| **Fase 2: Ergonomía y Botones** | • Eliminar duplicidad de Asistente IA y Botón Añadir.<br>• Corregir navegación de la pantalla de Ajustes.<br>• Coordinar `BibleAudioBottomBar` y `VerseActionBar`. | 🔴 Alta |
| **Fase 3: Refinamiento Visual M3** | • Reemplazar colores fijos por tokens semánticos de `MaterialTheme`.<br>• Ajustar contrastes en modo Sepia y Noche Profunda.<br>• Implementar modo lectura inmersivo (auto-hide en scroll). | 🟡 Media |
| **Fase 4: Nuevas Características UI** | • Implementar widget "Continuar Lectura" en la Home.<br>• Añadir micro-feedback háptico al resaltar texto.<br>• Mejorar la estética del exportador de versículos. | 🟢 Evolutiva |
