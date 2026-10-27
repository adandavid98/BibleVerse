# 📖 Plan Maestro: Catálogo de Devocionales y Estudios Bíblicos (Estilo YouVersion)

**Proyecto:** BibleVerse Android  
**Ubicación:** `BibleVerse-main/PLAN_CATALOGO_DEVOCIONALES.md`  
**Objetivo:** Diseñar y estructurar la biblioteca oficial de planes temáticos y estudios bíblicos profundos para la sección de **Catálogo**, con soporte de planes cortos (5 días), medianos (7 días), estudios teológicos (15 y 30 días), y la especificación detallada de UI/UX con **grid de 2 columnas por sección temática**.

---

## 1. 🌟 Filosofía Editorial y Anatomía Diaria

Cada día del plan sigue una **estructura formativa y reflexiva de 3 pilares**, sin oraciones prefabricadas, permitiendo que el usuario medite y responda a Dios de manera espontánea:

```
┌─────────────────────────────────────────────────────────────┐
│ 1. TÍTULO Y TESIS DEL DÍA (Pregunta o tema central)         │
├─────────────────────────────────────────────────────────────┤
│ 2. DEVOCIONAL / ESTUDIO BÍBLICO EXPOSITIVO (250-450 pal.)   │
│    - Contexto histórico, cultural y exégesis del texto      │
│    - Principio doctrinal claro y aplicación contemporánea   │
├─────────────────────────────────────────────────────────────┤
│ 3. LECTURAS BÍBLICAS DEL DÍA (1 a 3 capítulos o pasajes)    │
│    - Lectura integrada en el visor bíblico de BibleVerse    │
├─────────────────────────────────────────────────────────────┤
│ 4. PREGUNTA DE REFLEXIÓN Y APLICACIÓN PERSONAL             │
│    - Interrogante clave para profundizar o llevar a la vida │
└─────────────────────────────────────────────────────────────┘
```

---

## 2. 📱 Diseño UI/UX: Organización del Catálogo en Grid (2 Tarjetas por Fila)

### 2.1. Estructura Jerárquica de la Vista
La pestaña **Catálogo** en `ReadingPlansBottomSheet.kt` se organiza de forma clara y visualmente atractiva:

1. **Buscador y Chips de Filtro Rápido:**
   - Chips deslizables horizontales: `Todos` | `Cortos (5d)` | `1 Semana (7d)` | `15 Días` | `30 Días` | `Doctrina`
2. **Secciones Temáticas Agrupadas:**
   - Cada sección cuenta con un encabezado temático con icono y conteo (ej. `🔬 Estudios Bíblicos y Doctrinales (5)`, `🌿 Ansiedad, Duelo y Paz Interior (3)`).
3. **Distribución en Cuadrícula (Grid de 2 Tarjetas por Fila):**
   - En cada fila horizontal se muestran **2 tarjetas de planes**, optimizando el espacio en pantalla vertical y facilitando una navegación fluida estilo catálogo YouVersion / streaming.

```
┌───────────────────────────────────────────────────────────────┐
│ 🔍 Buscar en catálogo...                       [Filtros]     │
│ [Todos]  [Cortos 5d]  [1 Semana]  [15 Días]  [30 Días]       │
├───────────────────────────────────────────────────────────────┤
│ 🔬 DOCTRINA Y TEOLOGÍA BÍBLICA                                │
│ ┌───────────────────────────┐   ┌───────────────────────────┐ │
│ │ [PORTADA / IMAGEN]        │   │ [PORTADA / IMAGEN]        │ │
│ │ [🏷️ 30 DÍAS]              │   │ [🏷️ 7 DÍAS]               │ │
│ │ Escatología Bíblica       │   │ La Trinidad: Un Solo Dios │ │
│ │ Profecía y Apocalipsis    │   │ Tres Personas Divinas     │ │
│ └───────────────────────────┘   └───────────────────────────┘ │
│ ┌───────────────────────────┐   ┌───────────────────────────┐ │
│ │ [PORTADA / IMAGEN]        │   │ [PORTADA / IMAGEN]        │ │
│ │ [🏷️ 7 DÍAS]               │   │ [🏷️ 7 DÍAS]               │ │
│ │ Fe y Obras                │   │ La Vida Eterna            │ │
│ │ Santiago vs. Pablo        │   │ La Promesa de Jesús       │ │
│ └───────────────────────────┘   └───────────────────────────┘ │
├───────────────────────────────────────────────────────────────┤
│ 🌿 ANSIEDAD, DUELO Y PAZ INTERIOR                             │
│ ┌───────────────────────────┐   ┌───────────────────────────┐ │
│ │ [PORTADA / IMAGEN]        │   │ [PORTADA / IMAGEN]        │ │
│ │ [🏷️ 5 DÍAS]               │   │ [🏷️ 7 DÍAS]               │ │
│ │ Paz en la Tormenta        │   │ Descanso para el Alma     │ │
│ └───────────────────────────┘   └───────────────────────────┘ │
└───────────────────────────────────────────────────────────────┘
```

### 2.2. Anatomía de la Tarjeta del Plan (`PlanGridCard`)
- **Contenedor:** Tarjeta elevada con esquinas redondeadas (`shape = RoundedCornerShape(14.dp)`), `CardDefaults.cardElevation(2.dp)` y borde sutil.
- **Imagen / Portada Superior:**
  - Ratio aspecto 16:10 o altura fija (`110.dp`).
  - Imagen cargada desde drawable/asset o gradiente temático de alto contraste.
  - **Badge Flotante de Duración:** En la esquina superior izquierda sobre la imagen (ej. `5 DÍAS`, `7 DÍAS`, `30 DÍAS`) con fondo semitransparente oscuro o color acento.
  - **Indicador de Estado:** Si el plan ya está en curso por el usuario, muestra una pequeña barra de progreso o badge "Activo".
- **Cuerpo Inferior:**
  - **Título del Plan:** Texto bold (14sp), máximo 2 líneas con elipsis (`maxLines = 2`, `overflow = TextOverflow.Ellipsis`).
  - **Subtítulo o Breve Descripción:** Texto secundario (11sp, `maxLines = 2`), destacando el objetivo.
  - **Área táctil:** Al tocar cualquier parte de la tarjeta, se abre la hoja de detalle previo con el desglose de días y el botón "Comenzar este Plan".

---

## 3. 📚 Catálogo Completo de Planes Organizado por Secciones Temáticas

---

### SECCIÓN 1: 🔬 Doctrina y Teología Bíblica (Estudios Medianos y Largos)

#### 🕊️ Plan 1: "La Trinidad: Un Solo Dios en Tres Personas" *(NUEVO)*
- **Duración:** 7 Días (Plan promedio)
- **Audiencia:** Creyentes que desean comprender la doctrina de la Trinidad con base bíblica rigurosa.
- **Desarrollo por días:**
  - **Día 1: El Dios Único y la Pluralidad Divina en el Antiguo Testamento:** *El Shemá de Israel (Deuteronomio 6:4), el Dios Creador en plural (Génesis 1:26; Isaías 6:8).*
  - **Día 2: El Bautismo de Jesús: La Manifestación Trinitaria:** *El Padre habla desde el cielo, el Hijo en las aguas y el Espíritu descendiendo como paloma (Mateo 3:13-17).*
  - **Día 3: La Deidad Plena de Jesucristo:** *El Verbo era Dios; imagen del Dios invisible en quien habita corporalmente la plenitud de la Deidad (Juan 1:1-14; Colosenses 2:9).*
  - **Día 4: La Persona y Deidad del Espíritu Santo:** *Mentir al Espíritu es mentir a Dios; el Consolador que tiene intelecto, voluntad y afecto (Hechos 5:3-4; Juan 14:16-17; Romanos 8:26-27).*
  - **Día 5: La Gran Comisión y las Fórmulas Apostólicas:** *Bautizar en el nombre (singular) del Padre, del Hijo y del Espíritu Santo; la bendición apostólica (Mateo 28:19; 2 Corintios 13:14).*
  - **Día 6: La Armonía de la Trinidad en la Salvación:** *El Padre planifica y elige, el Hijo redime con Su sangre, el Espíritu Santo sella y regenera (Efesios 1:3-14; 1 Pedro 1:2).*
  - **Día 7: La Trinidad en la Vida Diaria:** *Adorar al Padre, por medio del Hijo, en el poder del Espíritu Santo (Efesios 2:18; Judas 1:20-21).*

#### ⚖️ Plan 2: "Fe y Obras: El Vínculo entre la Gracia y los Frutos" *(NUEVO)*
- **Duración:** 7 Días (Plan promedio)
- **Audiencia:** Quienes tienen dudas sobre la aparente discrepancia entre las enseñanzas de Pablo y Santiago.
- **Desarrollo por días:**
  - **Día 1: Justificados solo por Gracia mediante la Fe:** *La base inamovible de la salvación: no por obras humanas para que nadie se gloríe (Efesios 2:8-9; Gálatas 2:16).*
  - **Día 2: El Diagnóstico de Santiago: ¿Qué es la fe sin obras?:** *La fe que solo asiente intelectualmente sin transformar la conducta (Santiago 2:14-19).*
  - **Día 3: La Fe de Abraham Probada en Acción:** *Cómo Abraham creyó a Dios en Génesis 15 (justificación ante Dios) y ofreció a Isaac en Génesis 22 (demostración visible de su fe) (Romanos 4:1-5; Santiago 2:20-24).*
  - **Día 4: Creados en Cristo para Buenas Obras:** *El propósito de la salvación: las obras preparadas de antemano por Dios (Efesios 2:10; Tito 2:14).*
  - **Día 5: La Fe que Opera por el Amor:** *La única circuncisión que vale: la fe genuina que se manifiesta en amor activo y misericordia (Gálatas 5:6; 1 Juan 3:16-18).*
  - **Día 6: Los Frutos del Árbol:** *La advertencia de Jesús: por sus frutos los conoceréis; no todo el que dice "Señor, Señor" (Mateo 7:15-23).*
  - **Día 7: La Recompensa en el Tribunal de Cristo:** *Salvos de la condenación, pero evaluados por fidelidad y servicio en el Reino (1 Corintios 3:10-15; 2 Corintios 5:10).*

#### ✨ Plan 3: "La Vida Eterna: Promesa, Realidad y Esperanza Presente" *(NUEVO)*
- **Duración:** 7 Días (Plan promedio)
- **Audiencia:** Personas que desean entender qué significa bíblicamente la vida eterna y la seguridad de la salvación.
- **Desarrollo por días:**
  - **Día 1: Qué es la Vida Eterna según Jesús:** *Conocer íntimamente al único Dios verdadero y a Jesucristo a quien ha enviado (Juan 17:1-3; Juan 3:16).*
  - **Día 2: Una Posesión Presente, no solo Futura:** *El que cree en el Hijo tiene (tiempo presente) vida eterna y no vendrá a condenación (Juan 5:24; 1 Juan 5:11-13).*
  - **Día 3: El Agua y el Pan que Sacian para Siempre:** *El encuentro con la mujer samaritana y el discurso del Pan de Vida (Juan 4:13-14; Juan 6:35-40).*
  - **Día 4: La Garantía de la Resurrección Corporal:** *Cristo, primicias de los que durmieron; la derrota final de la muerte (1 Corintios 15:20-26, 50-57).*
  - **Día 5: La Seguridad de la Salvación en la Mano del Buen Pastor:** *Nadie las arrebatará de mi mano ni de la mano de mi Padre (Juan 10:27-30; Romanos 8:38-39).*
  - **Día 6: Nuestra Ciudadanía Celestial:** *Vivir en la tierra con perspectiva de eternidad (Filipenses 3:20-21; 2 Corintios 4:16-18).*
  - **Día 7: Enjugada Toda Lágrima: La Morada Eterna:** *La Nueva Jerusalén y la comunión ininterrumpida con Dios (Apocalipsis 21:1-7).*

#### ⏳ Plan 4: "Escatología Bíblica: Profecías, Apocalipsis y Fin de los Tiempos"
- **Duración:** 30 Días (Estudio Profundo Completo)
- **Desarrollo:** Daniel (caps. 2, 7 y 9), Mateo 24-25, 1 y 2 Tesalonicenses, y recorrido exegético integral por Apocalipsis (Cartas a las 7 iglesias, Sellos, Trompetas, Bestias de Apoc. 13, Copas, Segunda Venida en Apoc. 19, Milenio, Juicio del Gran Trono Blanco y Nueva Jerusalén).

#### ⚖️ Plan 5: "Romanos: Fundamentos de la Gracia y la Salvación"
- **Duración:** 30 Días (Estudio Exegético)
- **Desarrollo:** Condición universal del pecado (caps. 1-3), Justificación por la fe sola (caps. 4-5), Santificación y vida en el Espíritu (caps. 6-8), Soberanía y el plan para Israel (caps. 9-11), Ética del cristiano en el mundo (caps. 12-16).

#### 🕊️ Plan 6: "Pneumatología: La Persona y Obra del Espíritu Santo"
- **Duración:** 15 Días
- **Desarrollo:** Creación, unción en el A.T., promesas proféticas, ministerio de Jesús, Pentecostés, regeneración, santificación, frutos vs. obras de la carne y dones espirituales.

#### 🏛️ Plan 7: "Cristo en las Sombras del Antiguo Pacto (Hebreos y el Tabernáculo)"
- **Duración:** 15 Días
- **Desarrollo:** Altar de bronce, lavacro, candelabro, pan de la proposición, altar de incienso, el velo rasgado, sacerdocio de Melquisedec y el mediador de un mejor pacto.

#### 📜 Plan 8: "La Gran Teología de los Pactos Bíblicos"
- **Duración:** 15 Días
- **Desarrollo:** Pactos Adámico, Noé, Abrahámico, Mosaico, Davídico, anuncio de los profetas y el Nuevo Pacto sellado en la Cruz.

---

### SECCIÓN 2: 🌱 Vida Cristiana y Crecimiento Personal (Planes Cortos y Medianos)

#### 🌅 Plan 9: "Vida Nueva en Cristo: Primeros Pasos del Creyente" *(NUEVO)*
- **Duración:** 5 Días (Plan corto y directo)
- **Audiencia:** Nuevos convertidos o personas que desean reordenar su caminar con Dios.
- **Desarrollo por días:**
  - **Día 1: Una Nueva Creación:** *Las cosas viejas pasaron; he aquí todas son hechas nuevas en Cristo (2 Corintios 5:17-18; Efesios 2:1-5).*
  - **Día 2: El Despojo del Viejo Hombre:** *Quitarse la vieja vestidura y vestirse del nuevo hombre renovado en la justicia (Efesios 4:20-24; Colosenses 3:8-10).*
  - **Día 3: El Alimento Espiritual Cotidiano:** *Desead como niños recién nacidos la leche espiritual no adulterada (1 Pedro 2:1-3; Salmo 119:9-11).*
  - **Día 4: Caminar en la Luz y en Comunidad:** *La importancia de la comunión fraterna y no aislarse (1 Juan 1:5-9; Hebreos 10:24-25).*
  - **Día 5: La Mente de Cristo y el Propósito Diario:** *Llevar cautivo todo pensamiento y vivir para la gloria de Dios (Filipenses 2:5-11; 1 Corintios 10:31).*

#### 🧭 Plan 10: "La Voluntad de Dios: Cómo Conocerla y Caminar en Ella" *(NUEVO)*
- **Duración:** 7 Días (Plan promedio)
- **Audiencia:** Personas frente a decisiones cruciales (carrera, matrimonio, mudanzas, trabajo) buscando la guía de Dios.
- **Desarrollo por días:**
  - **Día 1: Buena, Agradable y Perfecta:** *La renovación del entendimiento como requisito para comprobar la voluntad de Dios (Romanos 12:1-2).*
  - **Día 2: Voluntad Revelada vs. Cosas Secretas:** *Las cosas secretas pertenecen a Dios; las reveladas son para obedecerlas hoy (Deuteronomio 29:29; 1 Tesalonicenses 4:3).*
  - **Día 3: La Palabra como Lámpara:** *Dios rara vez usa señales místicas cuando ya ha hablado claramente en Su Escritura (Salmo 119:105; 2 Timoteo 3:16-17).*
  - **Día 4: La Sabiduría del Consejo Bíblico:** *En la multitud de consejeros sabios está la seguridad (Proverbios 11:14; Proverbios 15:22).*
  - **Día 5: La Paz de Cristo como Árbitro en el Corazón:** *Cómo discernir la convicción del Espíritu frente al capricho o ansiedad personal (Colosenses 3:15; Filipenses 4:6-7).*
  - **Día 6: Cuando Dios Cierra Puertas:** *El Espíritu que impide a Pablo ir a Bitinia para abrir Macedonia (Hechos 16:6-10; Proverbios 16:9).*
  - **Día 7: La Sumisión en Getsemaní:** *El modelo supremo de Jesús: "No se haga mi voluntad, sino la tuya" (Lucas 22:39-44; Santiago 4:13-17).*

#### 👑 Plan 11: "Los Nombres de Dios y su Revelación Bíblica"
- **Duración:** 7 Días
- **Desarrollo:** Elohim, Yahweh-Jireh, Yahweh-Rafa, Yahweh-Nissi, Yahweh-Shalom, El-Roi y Emanuel.

#### ⚔️ Plan 12: "La Armadura de Dios: Estrategia Bíblica contra el Mal"
- **Duración:** 7 Días
- **Desarrollo:** Cinturón de la verdad, coraza de justicia, calzado de la paz, escudo de la fe, yelmo de la salvación y la espada del Espíritu (Efesios 6:10-18).

#### 🪞 Plan 13: "Quién Dice Dios que Eres: Tu Identidad en Cristo"
- **Duración:** 5 Días
- **Desarrollo:** Creación admirable (Salmo 139), hijos adoptados, libres de condenación, templos del Espíritu y embajadores del Reino.

#### 🎯 Plan 14: "Vocación y Propósito: Caminar en el Llamado Divino"
- **Duración:** 7 Días
- **Desarrollo:** La gran vocación de amar, descubrimiento de dones, vencer las excusas (Moisés y Jeremías), trabajo como adoración y el "Heme aquí".

---

### SECCIÓN 3: 🌿 Ansiedad, Duelo y Paz Interior

#### 🌿 Plan 15: "Paz en la Tormenta: Venciendo la Ansiedad"
- **Duración:** 5 Días (Plan corto de choque)
- **Desarrollo:** Desarmar la preocupación (Mateo 6), el filtro de Filipenses 4, Salmo 23, el yugo fácil de Cristo y la paz que sobrepasa el entendimiento.

#### 🕊️ Plan 16: "Descanso para el Alma Agotada"
- **Duración:** 7 Días
- **Desarrollo:** El Shabat, Elías bajo el enebro, renovar fuerzas como el águila, límites y delegación (Jetro), la quietud interior y el reposo en la gracia.

#### 🛡️ Plan 17: "De la Aflicción a la Esperanza: Sanando el Corazón Quebrantado"
- **Duración:** 15 Días
- **Desarrollo:** Salmos de lamento sincero, el Dios de toda consolación, soltar el rencor y la restauración del testimonio personal.

---

### SECCIÓN 4: 🤝 Relaciones, Familia y Matrimonio

#### 💍 Plan 18: "Amor que Perdura: Principios Bíblicos para el Matrimonio"
- **Duración:** 7 Días
- **Desarrollo:** El pacto original, comunicación que edifica, 1 Corintios 13 en el hogar, resolver conflictos sin enojo, intimidad y el cordón de tres dobleces.

#### 🤝 Plan 19: "El Poder del Perdón: Sanando Relaciones Fracturadas"
- **Duración:** 5 Días
- **Desarrollo:** El siervo despiadado, perdonar no es justificar, el perdón en la Cruz, límites sabios y vivir sin deudas emocionales.

---

### SECCIÓN 5: 💼 Sabiduría, Finanzas y Liderazgo

#### 🪙 Plan 20: "Mayordomía Bíblica: Principios Financieros del Reino"
- **Duración:** 7 Días
- **Desarrollo:** Dios como dueño absoluto, huir del amor al dinero, salir de deudas, presupuesto diligente, dar con alegría y ahorrar con prudencia.

#### 🏛️ Plan 21: "Liderazgo en Tiempos de Crisis: El Libro de Nehemías"
- **Duración:** 15 Días
- **Desarrollo:** Diagnóstico y oración, inspección y visión, trabajo con pala y espada frente a la oposición, justicia social y avivamiento por la Ley.

---

### SECCIÓN 6: 📖 Inmersión Bíblica Capítulo a Capítulo

#### 💎 Plan 22: "31 Días de Sabiduría Práctica en Proverbios"
- **Duración:** 31 Días (1 capítulo por día del mes)
- **Desarrollo:** Análisis diario de cada capítulo de Proverbios aplicado a decisiones, finanzas, integridad y palabras.

#### 🚶‍♂️ Plan 23: "30 Días con Jesús: Vida, Milagros y Discursos de los Evangelios"
- **Duración:** 30 Días
- **Desarrollo:** El Sermón del Monte, milagros en Marcos, parábolas en Lucas, el aposento alto y la resurrección en Juan.

---

## 4. 📊 Matriz Resumen de Planes por Duración y Secciones

| Plan | Sección Temática | Duración | Formato / Tipo |
|---|---|---|---|
| **Paz en la Tormenta** | Ansiedad y Paz | 5 Días | Devocional de choque |
| **Vida Nueva en Cristo** | Crecimiento Personal | 5 Días | Corto / Fundacional |
| **Quién Dice Dios que Eres** | Crecimiento Personal | 5 Días | Corto / Identidad |
| **El Poder del Perdón** | Relaciones y Familia | 5 Días | Corto / Reconciliación |
| **La Trinidad** | Doctrina y Teología | 7 Días | Promedio / Teológico |
| **Fe y Obras** | Doctrina y Teología | 7 Días | Promedio / Exegético |
| **La Vida Eterna** | Doctrina y Teología | 7 Días | Promedio / Promesas |
| **La Voluntad de Dios** | Crecimiento Personal | 7 Días | Promedio / Práctico |
| **Descanso para el Alma** | Ansiedad y Paz | 7 Días | Promedio / Salud mental |
| **Los Nombres de Dios** | Crecimiento Personal | 7 Días | Promedio / Alabanza |
| **La Armadura de Dios** | Crecimiento Personal | 7 Días | Promedio / Carácter |
| **Amor que Perdura (Matrimonio)** | Relaciones y Familia | 7 Días | Promedio / Matrimonial |
| **Mayordomía Bíblica** | Sabiduría y Finanzas | 7 Días | Promedio / Mayordomía |
| **Vocación y Llamado** | Crecimiento Personal | 7 Días | Promedio / Propósito |
| **De la Aflicción a la Esperanza** | Ansiedad y Paz | 15 Días | Estudio / Duelo |
| **Liderazgo con Nehemías** | Sabiduría y Finanzas | 15 Días | Estudio / Liderazgo |
| **Pneumatología (Espíritu Santo)** | Doctrina y Teología | 15 Días | Estudio Doctrinal |
| **Cristo en las Sombras (Hebreos)** | Doctrina y Teología | 15 Días | Estudio Tipológico |
| **La Teología de los Pactos** | Doctrina y Teología | 15 Días | Estudio Bíblico |
| **Escatología Bíblica (Profecía)** | Doctrina y Teología | 30 Días | Estudio Profundo |
| **Romanos (Soteriología y Gracia)** | Doctrina y Teología | 30 Días | Estudio Exegético |
| **30 Días con Jesús** | Inmersión Bíblica | 30 Días | Evangelios |
| **31 Días en Proverbios** | Inmersión Bíblica | 31 Días | Sabiduría Mensual |

---

## 5. 💻 Implementación Técnica en Jetpack Compose

### 5.1. Componente Grid de 2 Columnas por Sección Temática
Para renderizar las 2 tarjetas por fila dentro del catálogo en `ReadingPlansBottomSheet.kt`, se implementa una cuadrícula adaptativa o filas emparejadas con `chunked(2)`:

```kotlin
@Composable
fun CatalogSectionView(
    categoryTitle: String,
    categoryIcon: ImageVector,
    plans: List<ReadingPlanCatalogItem>,
    onPlanSelected: (ReadingPlanCatalogItem) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        // Encabezado de la Sección Temática
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
        ) {
            Icon(
                imageVector = categoryIcon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = categoryTitle.uppercase(),
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "(${plans.size})",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Grid de 2 tarjetas por fila horizontal
        val rows = plans.chunked(2)
        rows.forEach { pair ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Tarjeta 1
                PlanGridCard(
                    plan = pair[0],
                    modifier = Modifier.weight(1f),
                    onClick = { onPlanSelected(pair[0]) }
                )

                // Tarjeta 2 (o espacio vacío compensatorio si la fila es impar)
                if (pair.size > 1) {
                    PlanGridCard(
                        plan = pair[1],
                        modifier = Modifier.weight(1f),
                        onClick = { onPlanSelected(pair[1]) }
                    )
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}
```

### 5.2. Componente `PlanGridCard` (Tarjeta Individual con Portada y Badge)

```kotlin
@Composable
fun PlanGridCard(
    plan: ReadingPlanCatalogItem,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Portada / Imagen con Badge Superpuesto
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .background(
                        Brush.linearGradient(plan.coverGradient)
                    )
            ) {
                // Badge de Duración
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = Color.Black.copy(alpha = 0.65f),
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.TopStart)
                ) {
                    Text(
                        text = "${plan.totalDays} DÍAS",
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            // Información del Plan
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(
                    text = plan.title,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = plan.shortDescription,
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
```

---

## 6. 🚀 Plan de Ejecución

1. **Modelado y Assets:**
   - Crear los archivos `.json` en `app/src/main/assets/bible/devotionals/` para cada plan de la matriz.
2. **Repositorio:**
   - `DevotionalPlanCatalogRepository`: Carga los planes agrupándolos por su `categoryKey` y exponiéndolos mediante `StateFlow<Map<PlanCategory, List<ReadingPlanCatalogItem>>>`.
3. **UI Compose:**
   - Reemplazar la lista vertical simple de catálogo en `ReadingPlansBottomSheet.kt` por el layout temático con `CatalogSectionView` y `PlanGridCard` en formato de 2 tarjetas por fila.
