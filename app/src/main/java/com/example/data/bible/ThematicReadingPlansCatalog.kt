package com.example.data.bible

/**
 * Catálogo integral de planes temáticos, devocionales y estudios bíblicos profundos.
 * Diseñado con rigor exegético y estructura de 3 pilares: Exposición bíblica, Clave teológica y Reflexión personal.
 */
object ThematicReadingPlansCatalog {

    private data class PlanDayMeta(
        val bookId: Int,
        val bookName: String,
        val chapter: Int,
        val subTitle: String
    )

    private fun p(bookId: Int, bookName: String, chapter: Int) = PlanPassageSegment(bookId, bookName, chapter)

    private fun buildRichDevotional(
        dayNum: Int,
        title: String,
        summary: String,
        baseExposition: String,
        lesson: String,
        reflection: String
    ): String {
        if (baseExposition.contains("\n\n")) return baseExposition
        val cleanTheme = title.substringAfter(": ").trim()

        val theologicalParagraph = when (dayNum % 4) {
            1 -> "Al profundizar en el contexto de $summary, descubrimos cómo las Escrituras revelan de manera progresiva y armoniosa el carácter inmutable de Dios respecto a «$cleanTheme». No estamos ante principios morales aislados ni ante filosofías humanas pasajeras, sino frente a la revelación viva del Creador. Como bien sintetiza la enseñanza central de esta jornada: $lesson Cuando miramos este pasaje a la luz de la obra redentora de Jesucristo, comprendemos que toda promesa divina encuentra en Él su sí y su amén definitivo."
            2 -> "Desde una perspectiva teológica e histórica, la lectura de $summary nos confronta con la distancia infinita entre la sabiduría del mundo y la sabiduría del Reino de los Cielos. El estudio de «$cleanTheme» demuestra que Dios no improvisa en la historia de la redención ni en el trato personal con sus hijos. La verdad medular que sostiene este texto es clara y contundente: $lesson Esta realidad doctrinal actúa como un ancla firme para el alma cuando las circunstancias externas parecen contradecir las promesas del Señor."
            3 -> "Examinar con detenimiento $summary nos invita a contemplar la riqueza inagotable de la gracia soberana en torno a «$cleanTheme». Cada versículo de esta porción bíblica fue inspirado por el Espíritu Santo para derribar nuestra autosuficiencia y llevarnos a una dependencia genuina del Padre. Aquí resplandece una verdad espiritual innegociable: $lesson Al abrazar esta doctrina con fe viva, nuestra mente es renovada y nuestros afectos son alineados con el corazón mismo de Dios."
            else -> "La unidad orgánica de toda la Biblia brilla con fuerza al estudiar $summary bajo el prisma de «$cleanTheme». Lo que el texto sagrado expone aquí trasciende la época en que fue escrito y penetra directamente en las necesidades más hondas del corazón humano contemporáneo. El fundamento teológico de esta lectura nos recuerda que: $lesson En Cristo Jesús, esta verdad deja de ser un concepto abstracto para convertirse en una realidad transformadora y palpable."
        }

        val pastoralParagraph = when (dayNum % 3) {
            1 -> "Llevando esta palabra al terreno práctico de nuestra vida diaria, es indispensable permitir que el texto bíblico examine nuestras intenciones, prioridades y reacciones cotidianas. No basta con asentir intelectualmente a la verdad de $summary; el verdadero crecimiento espiritual ocurre cuando la Palabra encarna en nuestras decisiones concretas en el hogar, el trabajo y la comunidad de fe. Por ello, hoy el Espíritu nos invita a detenernos y considerar con honestidad delante del Señor: $reflection"
            2 -> "En el caminar cristiano cotidiano, esta enseñanza sobre «$cleanTheme» tiene implicaciones directas y profundas. A menudo corremos el riesgo de leer pasajes como $summary de forma apresurada, sin dejar que su luz disipe nuestras dudas, temores o áreas de tibieza espiritual. Hoy es un día propicio para abrir el corazón sin reservas, meditar pausadamente en los versículos de referencia y responder con humildad a la pregunta que esta lectura nos plantea: $reflection"
            else -> "Para que esta semilla de la Palabra dé fruto abundante y perdurable en nosotros, necesitamos recibirla con mansedumbre y disposición de obediencia. La lectura pausada de $summary nos equipa para enfrentar las pruebas y desafíos del presente con una visión celestial renovada. Mientras recorres hoy los versículos bíblicos de referencia, deja que esta verdad repose en tu interior y examina tu caminar a la luz de esta reflexión: $reflection"
        }

        val fourthParagraph = if (dayNum % 2 == 1) {
            "\n\nDedica unos instantes adicionales a leer con atención los pasajes bíblicos de referencia incluidos aquí abajo ($summary). Subraya en tu corazón aquellas palabras que más resuenen con tu temporada actual y permite que la voz viva de Dios afirme tus pasos a lo largo de toda esta jornada."
        } else {
            ""
        }

        return "$baseExposition\n\n$theologicalParagraph\n\n$pastoralParagraph$fourthParagraph"
    }

    private fun d(
        num: Int,
        title: String,
        summary: String,
        pBId: Int,
        pC: Int,
        passages: List<PlanPassageSegment>,
        narrative: String,
        lesson: String,
        reflection: String
    ) = ReadingPlanDay(
        dayNumber = num,
        title = title,
        passagesSummary = summary,
        primaryBookId = pBId,
        primaryChapter = pC,
        primaryVerse = 1,
        passages = passages,
        storyNarrative = buildRichDevotional(num, title, summary, narrative, lesson, reflection),
        spiritualLesson = lesson,
        historicalContext = reflection
    )

    fun getThematicPlan(type: ReadingPlanType): List<ReadingPlanDay> = when (type) {
        ReadingPlanType.ESCHATOLOGY -> eschatologyPlan
        ReadingPlanType.ROMANS -> romansPlan
        ReadingPlanType.JESUS_30 -> jesus30Plan
        ReadingPlanType.PROVERBS_31 -> proverbs31Plan
        ReadingPlanType.PNEUMATOLOGY -> pneumatologyPlan
        ReadingPlanType.TABERNACLE_HEBREWS -> tabernaclePlan
        ReadingPlanType.COVENANTS -> covenantsPlan
        ReadingPlanType.NEHEMIAH -> nehemiahPlan
        ReadingPlanType.AFFLICTION_HOPE -> afflictionHopePlan
        ReadingPlanType.TRINITY -> trinityPlan
        ReadingPlanType.FAITH_WORKS -> faithWorksPlan
        ReadingPlanType.ETERNAL_LIFE -> eternalLifePlan
        ReadingPlanType.GODS_WILL -> godsWillPlan
        ReadingPlanType.NAMES_OF_GOD -> namesOfGodPlan
        ReadingPlanType.ARMOR_OF_GOD -> armorOfGodPlan
        ReadingPlanType.ENDURING_LOVE -> enduringLovePlan
        ReadingPlanType.STEWARDSHIP -> stewardshipPlan
        ReadingPlanType.CALLING_PURPOSE -> callingPurposePlan
        ReadingPlanType.SOUL_REST -> soulRestPlan
        ReadingPlanType.PEACE_ANXIETY -> peaceAnxietyPlan
        ReadingPlanType.NEW_LIFE_CHRIST -> newLifeChristPlan
        ReadingPlanType.GOD_SAYS_YOU_ARE -> godSaysYouArePlan
        ReadingPlanType.POWER_OF_FORGIVENESS -> powerOfForgivenessPlan
        else -> emptyList()
    }

    // =========================================================================
    // 1. DOCTRINA Y TEOLOGÍA BÍBLICA
    // =========================================================================

    // --- LA TRINIDAD (7 Días) ---
    val trinityPlan: List<ReadingPlanDay> by lazy {
        listOf(
            d(1, "Día 1: El Dios Único y la Pluralidad Divina", "Deuteronomio 6:4; Génesis 1:26", 5, 6, listOf(p(5, "Deuteronomio", 6), p(1, "Génesis", 1)),
                "La fe bíblica se asienta sobre la verdad inamovible de que Jehová nuestro Dios, Jehová uno es (Dt 6:4). Sin embargo, desde el primer capítulo del Génesis vislumbramos una rica pluralidad divina: 'Hagamos al hombre a nuestra imagen'. Dios no es una soledad estática, sino una comunión eterna y perfecta de amor.",
                "Dios es uno en esencia y naturaleza divina, revelándose eternamente en tres personas coeternas y consustanciales.",
                "¿Cómo transforma tu adoración saber que Dios es una comunión perfecta de amor y no una divinidad solitaria?"),
            d(2, "Día 2: El Bautismo del Señor Jesús", "Mateo 3:13-17", 40, 3, listOf(p(40, "Mateo", 3)),
                "En las aguas del río Jordán presenciamos la manifestación trinitaria más diáfana: el Hijo encarnado es bautizado en obediencia, el Espíritu Santo desciende visiblemente en forma de paloma ungiéndole, y la voz del Padre retumba desde los cielos declarando su complacencia absoluta.",
                "La Trinidad actúa en perfecta sincronía: el Padre envía y complace, el Hijo obedece y redime, el Espíritu unge y capacita.",
                "¿Reconoces la presencia activa del Padre, del Hijo y del Espíritu en tu propio bautismo y consagración?"),
            d(3, "Día 3: La Deidad Suprema de Jesucristo", "Juan 1:1-18; Colosenses 2:9", 43, 1, listOf(p(43, "Juan", 1), p(51, "Colosenses", 2)),
                "Jesús no es una criatura excelsa ni un maestro meramente humano. El apóstol Juan declara tajantemente: 'El Verbo era Dios' (Jn 1:1). En Cristo habita corporalmente toda la plenitud de la Deidad. Solo un Salvador que es verdaderamente Dios puede reconciliar al hombre infinito con el Padre Santo.",
                "Jesucristo es plenamente Dios y plenamente hombre; su deidad garantiza la eficacia eterna de nuestra redención.",
                "¿Es Jesús el Señor absoluto y Dios soberano en cada rincón de tus pensamientos y anhelos?"),
            d(4, "Día 4: La Persona y Deidad del Espíritu Santo", "Hechos 5:1-11; Juan 14:15-26", 44, 5, listOf(p(44, "Hechos", 5), p(43, "Juan", 14)),
                "El Espíritu Santo no es una fuerza impersonal ni una energía cósmica. Es una Persona divina que enseña, guía, puede ser contristada y a quien mentir es mentir a Dios mismo (Hch 5:3-4). Jesús lo llamó 'otro Consolador' (Állos Parákletos), uno de la misma naturaleza divina para estar siempre con nosotros.",
                "El Espíritu Santo posee intelecto, voluntad y afecto; es Dios morando activamente en el corazón regenerado.",
                "¿Tratas al Espíritu Santo con la reverencia debida a una Persona divina que habita en ti?"),
            d(5, "Día 5: La Gran Comisión y la Fórmula Apostólica", "Mateo 28:16-20; 2 Corintios 13:14", 40, 28, listOf(p(40, "Mateo", 28), p(47, "2 Corintios", 13)),
                "Jesús comisionó a su Iglesia a bautizar en el nombre —en singular— del Padre, del Hijo y del Espíritu Santo. Un solo nombre, tres Personas. Asimismo, la bendición apostólica sella la vida de la Iglesia invocando la gracia de Cristo, el amor del Padre y la comunión del Espíritu Santo.",
                "El nombre único de Dios contiene la plenitud trina; la bendición divina descansa sobre quienes viven en esa comunión.",
                "¿Refleja tu comunidad de fe la bendición y comunión trinitaria en el trato cotidiano entre hermanos?"),
            d(6, "Día 6: La Armonía de la Trinidad en la Salvación", "Efesios 1:3-14", 49, 1, listOf(p(49, "Efesios", 1)),
                "El himno teológico de Efesios 1 revela el reparto glorioso de nuestra redención: el Padre nos escogió antes de la fundación del mundo; el Hijo nos redimió mediante su sangre perdonando nuestros pecados; y el Espíritu Santo nos selló como garantía inquebrantable hasta la redención final.",
                "Nuestra salvación es indestructible porque está planificada por el Padre, comprada por el Hijo y sellada por el Espíritu.",
                "¿Descansas con absoluta seguridad en que las tres Personas divinas custodian tu salvación eterna?"),
            d(7, "Día 7: La Vida Trinitaria del Creyente", "Efesios 2:17-22; Judas 1:20-21", 49, 2, listOf(p(49, "Efesios", 2), p(65, "Judas", 1)),
                "Por medio de Cristo tenemos entrada al Padre en un mismo Espíritu (Ef 2:18). Nuestra vida espiritual entera es trinitaria: oramos al Padre, sustentados en los méritos del Hijo, impulsados y guiados por el gemido del Espíritu Santo. Conocer la Trinidad transforma nuestra vida diaria.",
                "El creyente vive inmerso en la vida de Dios: amado por el Padre, justificado por el Hijo y vivificado por el Espíritu.",
                "¿Cómo orientarás tus oraciones a partir de hoy reconociendo el papel vivo de cada Persona divina?")
        )
    }

    // --- FE Y OBRAS (7 Días) ---
    val faithWorksPlan: List<ReadingPlanDay> by lazy {
        listOf(
            d(1, "Día 1: Justificados solo por Gracia mediante la Fe", "Efesios 2:1-9; Gálatas 2:15-21", 49, 2, listOf(p(49, "Efesios", 2), p(48, "Gálatas", 2)),
                "El fundamento innegociable del Evangelio es que el ser humano no puede justificarse ante un Dios santo mediante el mérito propio. La salvación es un don gratuito recibido exclusivamente por medio de la fe en la justicia imputada de Jesucristo. Ninguna obra humana puede añadir nada al sacrificio perfecto del Calvario.",
                "Somos justificados únicamente por la gracia soberana a través de la fe, para que nadie se gloríe en sus fuerzas.",
                "¿Has intentado ganar el favor de Dios con tu conducta, o descansas enteramente en la obra consumada de Cristo?"),
            d(2, "Día 2: El Diagnóstico de Santiago: ¿Qué es una fe viva?", "Santiago 2:14-26", 59, 2, listOf(p(59, "Santiago", 2)),
                "Santiago no contradice a Pablo; desenmascara una fe espuria y meramente intelectual. Los demonios también creen que Dios es uno y tiemblan, pero no le obedecen. La fe genuina es un principio vivo que necesariamente transforma la voluntad y se hace visible a través del amor activo hacia el prójimo.",
                "La fe que justifica es solo la fe, pero la fe que justifica nunca está sola: siempre produce frutos visibles.",
                "¿Tu fe se queda en un mero asentimiento mental o se traduce en compasión y ayuda tangible a quienes te rodean?"),
            d(3, "Día 3: El Ejemplo de Abraham en Génesis 15 y 22", "Romanos 4:1-5; Génesis 22:1-14", 45, 4, listOf(p(45, "Romanos", 4), p(1, "Génesis", 22)),
                "Pablo cita Génesis 15: Abraham creyó a Dios y le fue contado por justicia cuando aún no estaba circuncidado ni había sacrificado nada. Santiago cita Génesis 22: la fe de Abraham fue perfeccionada y demostrada ante el mundo cuando no escatimó a su propio hijo Isaac sobre el altar.",
                "Dios ve la fe en el corazón y justifica; los hombres ven las obras y comprueban la autenticidad de esa fe.",
                "¿Qué área de tu vida te está pidiendo Dios poner sobre el altar como evidencia viva de tu confianza en Él?"),
            d(4, "Día 4: Creados en Cristo para Buenas Obras", "Efesios 2:10; Tito 2:11-14; 3:8", 49, 2, listOf(p(49, "Efesios", 2), p(56, "Tito", 2), p(56, "Tito", 3)),
                "No somos salvos por buenas obras, sino creados y redimidos PARA buenas obras, las cuales Dios preparó de antemano. Las obras no son la raíz de nuestra salvación, sino su fruto inevitable y natural. Un árbol sano no lucha por dar manzanas; las da porque su savia es viva.",
                "Las buenas obras son el diseño y propósito intencional de la nueva creación que Dios hace en cada creyente.",
                "¿Identificas con claridad las obras de servicio y bendición que Dios ha puesto en tu camino esta semana?"),
            d(5, "Día 5: La Fe que Opera por el Amor", "Gálatas 5:1-6; 1 Juan 3:16-18", 48, 5, listOf(p(48, "Gálatas", 5), p(62, "1 Juan", 3)),
                "Pablo resume la madurez espiritual con una sentencia magistral: 'en Cristo Jesús ni la circuncisión vale algo, ni la incircuncisión, sino la fe que obra por el amor' (Gál 5:6). El amor sacrificado y desinteresado es el termómetro infalible de una fe viva y regenerada por el Espíritu.",
                "El amor ágape es el motor operativo de la fe; sin compasión activa, la ortodoxia teológica es letra muerta.",
                "¿En qué acciones concretas hacia tu familia o comunidad se manifestará tu fe el día de hoy?"),
            d(6, "Día 6: Por sus Frutos los Conoceréis", "Mateo 7:15-27; Juan 15:1-8", 40, 7, listOf(p(40, "Mateo", 7), p(43, "Juan", 15)),
                "Jesús advirtió con firmeza: no todo el que me dice 'Señor, Señor' entrará en el Reino, sino el que hace la voluntad de mi Padre. Permanecer en la Vid Verdadera es la única garantía de llevar fruto abundante. El pámpano que no lleva fruto demuestra que no está vitalmente conectado con la savia de Cristo.",
                "La profesión de fe sin obediencia filial a la voluntad del Padre es un engaño peligroso del cual Jesús nos alerta.",
                "¿Qué frutos de santidad y justicia son evidentes en tu vida privada donde nadie te ve?"),
            d(7, "Día 7: La Evaluación en el Tribunal de Cristo", "1 Corintios 3:10-15; 2 Corintios 5:9-10", 46, 3, listOf(p(46, "1 Corintios", 3), p(47, "2 Corintios", 5)),
                "Los creyentes no comparecerán ante el Gran Trono Blanco de condenación, pero sí ante el Tribunal de Cristo (Bema) para recibir recompensa por lo hecho mientras estábamos en el cuerpo. El fuego probará la calidad de nuestras obras: si construimos con oro, plata y piedras preciosas o con heno y hojarasca.",
                "Nuestras obras para el Reino tienen peso de recompensa eterna; cada acto de amor cuenta ante los ojos de Dios.",
                "¿Qué materiales estás usando para edificar sobre el fundamento de Cristo: conveniencia temporal o tesoros eternos?")
        )
    }

    // --- LA VIDA ETERNA (7 Días) ---
    val eternalLifePlan: List<ReadingPlanDay> by lazy {
        listOf(
            d(1, "Día 1: Qué es la Vida Eterna según Jesús", "Juan 17:1-5; Juan 3:16", 43, 17, listOf(p(43, "Juan", 17), p(43, "Juan", 3)),
                "Jesús definió la vida eterna de una manera revolucionaria: 'Y esta es la vida eterna: que te conozcan a ti, el único Dios verdadero, y a Jesucristo, a quien has enviado' (Jn 17:3). No es una mera duración cronológica infinita, sino una calidad de vida de comunión íntima y real con el Creador.",
                "La vida eterna es conocer relacionalmente al Padre y al Hijo, comenzando aquí en la tierra y extendiéndose por la eternidad.",
                "¿Experimentas tu cristianismo como una serie de reglas religiosas o como una relación viva de conocimiento con Dios?"),
            d(2, "Día 2: Una Posesión Presente", "Juan 5:24-29; 1 Juan 5:11-13", 43, 5, listOf(p(43, "Juan", 5), p(62, "1 Juan", 5)),
                "La promesa de Jesús está redactada en tiempo presente: 'El que oye mi palabra y cree al que me envió, TIENE vida eterna, y no vendrá a condenación, mas ha pasado de muerte a vida'. No es algo que esperamos recibir recién cuando muramos; el creyente ya ha cruzado la frontera de la muerte espiritual a la vida.",
                "La vida eterna se posee hoy en Cristo; la condenación judicial ha quedado cancelada definitivamente en la cruz.",
                "¿Vives con la seguridad serena de quien ya ha pasado de muerte a vida en Cristo Jesús?"),
            d(3, "Día 3: El Agua y el Pan que Sacian para Siempre", "Juan 4:1-15; Juan 6:35-40", 43, 4, listOf(p(43, "Juan", 4), p(43, "Juan", 6)),
                "Ante el pozo de Jacob y ante las multitudes hambrientas, Jesús reveló que los anhelos humanos más profundos son espirituales. Quien bebe del agua de este mundo volverá a tener sed, pero el agua que Cristo da salta para vida eterna. Jesús es el Pan vivo descendido del cielo.",
                "Solo en Cristo el alma humana encuentra saciedad plena y permanente frente al vacío y la insatisfacción terrenal.",
                "¿En qué pozos terrenales has estado intentando apagar una sed que solo Jesucristo puede calmar?"),
            d(4, "Día 4: La Garantía de la Resurrección Corporal", "1 Corintios 15:20-28, 50-58", 46, 15, listOf(p(46, "1 Corintios", 15)),
                "La esperanza cristiana no es la de un espíritu incorpóreo flotando en el vacío. Cristo resucitó corporalmente como primicia de todos los redimidos. En el toque de la final trompeta, lo corruptible se vestirá de incorrupción y la muerte será sorbida en victoria absoluta.",
                "La resurrección de Cristo es la garantía histórica e indestructible de nuestra propia victoria sobre el sepulcro.",
                "¿Cómo te llena de consuelo y valentía saber que la muerte física ha perdido definitivamente su aguijón?"),
            d(5, "Día 5: Seguridad en la Mano del Buen Pastor", "Juan 10:22-30; Romanos 8:31-39", 43, 10, listOf(p(43, "Juan", 10), p(45, "Romanos", 8)),
                "Jesús declara sobre sus ovejas: 'Yo les doy vida eterna; y no perecerán jamás, ni nadie las arrebatará de mi mano. Mi Padre que me las dio es mayor que todos'. La perseverancia de los santos no descansa en la debilidad de nuestro agarre, sino en la omnipotencia de la mano del Padre y del Hijo.",
                "Nuestra seguridad eterna descansa en la fidelidad invencible de Dios, de cuyo amor nada ni nadie podrá separarnos.",
                "¿En qué promesas de Jesús te apoyas cuando las dudas o las acusaciones del enemigo intentan robarte la paz?"),
            d(6, "Día 6: Nuestra Ciudadanía Celestial", "Filipenses 3:17-21; 2 Corintios 4:16-18", 50, 3, listOf(p(50, "Filipenses", 3), p(47, "2 Corintios", 4)),
                "El creyente peregrina en la tierra sabiendo que su verdadera ciudadanía está en los cielos, de donde esperamos al Salvador. Por tanto, no miramos las cosas que se ven (temporales y perecederas), sino las que no se ven, las cuales son eternas y de un peso de gloria incomparable.",
                "Tener mentalidad celestial nos capacita para vivir con la máxima efectividad, pureza y generosidad en la tierra.",
                "¿Tienen tus metas actuales un impacto con eco en la eternidad o se disuelven con el paso de los años?"),
            d(7, "Día 7: La Nueva Jerusalén y la Morada Eterna", "Apocalipsis 21:1-8; 22:1-5", 66, 21, listOf(p(66, "Apocalipsis", 21), p(66, "Apocalipsis", 22)),
                "El destino final del pueblo de Dios no es la destrucción, sino la recreación gloriosa: cielos nuevos y tierra nueva. Dios mismo morará con su pueblo, enjugará toda lágrima y no habrá más muerte, llanto, clamor ni dolor. Los siervos de Dios le verán cara a cara y reinarán por los siglos de los siglos.",
                "La culminación de la historia humana es el triunfo total de la gloria de Dios y la comunión perfecta con sus redimidos.",
                "¿Llenas tu corazón de gozo anticipando el día en que contemplarás el rostro de tu Redentor?")
        )
    }

    // --- ESCATOLOGÍA BÍBLICA: PROFECÍAS Y APOCALIPSIS (30 Días) ---
    val eschatologyPlan: List<ReadingPlanDay> by lazy {
        (1..30).map { dayNum ->
            when (dayNum) {
                1 -> d(1, "Día 1: El Propósito Pastoral de la Profecía", "2 Pedro 1:19-21; Amós 3:7", 61, 1, listOf(p(61, "2 Pedro", 1), p(30, "Amós", 3)),
                    "La profecía bíblica no fue dada para satisfacer curiosidades vanas ni para construir calendarios de especulación, sino para ser una antorcha que alumbra en lugar oscuro. Su meta es afirmar nuestra esperanza, santificar nuestra conducta diaria y darnos convicción de que la historia humana avanza bajo el control soberano de Dios.",
                    "La palabra profética es segura y digna de toda confianza; nos prepara para vivir despiertos y sobrios.",
                    "¿Buscas en la profecía alimentar la vanagloria del conocimiento o santificar tu caminar cotidiano?")
                2 -> d(2, "Día 2: Daniel 2: La Estatua y los Imperios", "Daniel 2:31-45", 27, 2, listOf(p(27, "Daniel", 2)),
                    "En el sueño de Nabucodonosor, Dios bosquejó la sucesión de los grandes imperios mundiales: Babilonia (oro), Medo-Persia (plata), Grecia (bronce) y Roma (hierro). Pero la visión culmina con una piedra cortada no con mano humana que hiere la estatua y se convierte en un monte que llena toda la tierra: el Reino mesiánico.",
                    "Los reinos de este mundo son transitorios; solo el Reino establecido por Cristo permanecerá para siempre.",
                    "¿En qué reino tienes puestas tus esperanzas: en la estabilidad de los gobiernos humanos o en el Reino inamovible de Cristo?")
                3 -> d(3, "Día 3: Daniel 7: Las Cuatro Bestias y el Anciano de Días", "Daniel 7:1-14, 21-27", 27, 7, listOf(p(27, "Daniel", 7)),
                    "Daniel contempla los poderes mundiales como bestias feroces que surgen del mar agitado. Pero en medio del caos terrenal, se levanta el tribunal celestial: el Anciano de Días toma asiento con vestiduras blancas como la nieve y entrega el señorío, la gloria y el reino al Hijo del Hombre.",
                    "Detrás de la arrogancia de los imperios humanos, el trono de Dios juzga y garantiza la victoria de sus santos.",
                    "¿Te infunden temor las noticias del mundo o te afirma saber que el tribunal celestial tiene la última palabra?")
                4 -> d(4, "Día 4: Daniel 9: Las Setenta Semanas Proféticas", "Daniel 9:20-27", 27, 9, listOf(p(27, "Daniel", 9)),
                    "La profecía de las setenta semanas entregada por el ángel Gabriel marca con asombrosa precisión el reloj redentor para Israel y Jerusalén: la reconstrucción de la ciudad, la llegada y muerte del Mesías Príncipe para expiar la iniquidad y sellar la justicia perdurable, y la semana final de consumación.",
                    "Dios gobierna el tiempo con exactitud matemática; la muerte expiatoria del Mesías es el centro neurálgico de toda la profecía.",
                    "¿Descansas en que Dios tiene contados los días de tu vida con la misma fidelidad con que trazó las eras proféticas?")
                5 -> d(5, "Día 5: El Día de Jehová en los Profetas", "Joel 2:1-13, 28-32; Sofonías 1", 29, 2, listOf(p(29, "Joel", 2), p(36, "Sofonías", 1)),
                    "Los profetas del Antiguo Testamento proclamaron el 'Día de Jehová' como un día de juicio severo sobre la soberbia y la idolatría de las naciones, pero también de derramamiento glorioso del Espíritu y salvación para todo aquel que invocare el nombre del Señor.",
                    "El juicio divino revela la santidad inflexible de Dios; su gracia ofrece refugio seguro antes de aquel día grande.",
                    "¿Has acudido al refugio de la gracia de Dios con un corazón quebrantado y sincero?")
                6 -> d(6, "Día 6: El Valle de los Huesos Secos", "Ezequiel 37:1-14", 26, 37, listOf(p(26, "Ezequiel", 37)),
                    "El profeta Ezequiel es llevado a un valle lleno de huesos secos en extremo: una imagen desgarradora de un pueblo sin esperanza. Pero cuando la Palabra de Dios es proclamada y el Viento del Espíritu sopla de los cuatro puntos cardinales, los huesos cobran vida y se levantan como un ejército formidable.",
                    "No hay situación nacional, espiritual o personal tan muerta que el Espíritu de Dios no pueda resucitar.",
                    "¿Hay algún área de tu vida que parece un valle de huesos secos donde necesitas clamar por el soplo del Espíritu?")
                7 -> d(7, "Día 7: La Promesa de la Nueva Creación en Isaías", "Isaías 65:17-25", 23, 65, listOf(p(23, "Isaías", 65)),
                    "Isaías mira hacia el horizonte de la eternidad y proclama: 'Porque he aquí que yo crearé nuevos cielos y nueva tierra; y de lo primero no habrá memoria ni más vendrá al pensamiento'. Una creación liberada de la maldición del pecado donde el lobo y el cordero pacerán juntos.",
                    "El propósito redentor de Dios no se limita a salvar almas individuales, sino a redimir y renovar todo el orden creado.",
                    "¿Cómo te llena de consuelo saber que todo el sufrimiento del tiempo presente será borrado en la renovación final?")
                8 -> d(8, "Día 8: El Discurso del Monte de los Olivos I", "Mateo 24:1-14", 40, 24, listOf(p(40, "Mateo", 24)),
                    "Sentado en el Monte de los Olivos, Jesús advierte a sus discípulos sobre los dolores de parto: falsos cristos, guerras, terremotos y pestes. Sin embargo, enfatiza que 'aún no es el fin' y que el Evangelio del Reino será predicado en todo el mundo para testimonio a todas las naciones.",
                    "Las crisis mundiales son señales premonitorias; la prioridad de la Iglesia es la proclamación incansable del Evangelio.",
                    "¿Estás involucrado activamente en hacer que el Evangelio llegue a quienes aún no conocen a Cristo?")
                9 -> d(9, "Día 9: El Discurso del Monte de los Olivos II", "Mateo 24:15-31", 40, 24, listOf(p(40, "Mateo", 24)),
                    "Jesús expone la Gran Tribulación venidera y la abominación desoladora predicha por Daniel. Advierte contra los falsos profetas y aclara que su venida no será secreta en un rincón, sino tan visible y pública como el relámpago que sale del oriente y se muestra hasta el occidente.",
                    "La Segunda Venida de Cristo será un acontecimiento cósmico y glorioso, inconfundible e indiscutible para todo ojo humano.",
                    "¿Mantienes tu corazón anclado en la certeza del regreso visible del Rey de Reyes?")
                10 -> d(10, "Día 10: Parábolas de Vigilancia: Vírgenes y Talentos", "Mateo 25:1-30", 40, 25, listOf(p(40, "Mateo", 25)),
                    "La respuesta a la profecía no es el letargo sino la vigilancia activa. Las cinco vírgenes prudentes tenían aceite en sus lámparas mientras el novio tardaba; los mayordomos diligentes multiplicaron los talentos encomendados. Esperar a Cristo exige fidelidad y preparación espiritual continua.",
                    "La fidelidad diaria y la diligencia en el servicio son las marcas auténticas de quien espera con ansias al Novio.",
                    "¿Hay suficiente 'aceite' de comunión con Dios en tu vida espiritual para perseverar en la noche?")
                11 -> d(11, "Día 11: La Esperanza del Arrebatamiento", "1 Tesalonicenses 4:13-18", 52, 4, listOf(p(52, "1 Tesalonicenses", 4)),
                    "Pablo escribe para consolar a los creyentes respecto a los que duermen: el Señor mismo descenderá del cielo con voz de mando, con voz de arcángel y con trompeta de Dios; los muertos en Cristo resucitarán primero, y los vivos seremos transformados para recibir al Señor en el aire.",
                    "La muerte física no nos separará de Cristo; la resurrección y el reencuentro celestial son el consuelo supremo de la fe.",
                    "¿Consuelas y alientas a otros con la gloriosa verdad del reencuentro en la presencia del Señor?")
                12 -> d(12, "Día 12: El Misterio de la Iniquidad y el Anticristo", "2 Tesalonicenses 2:1-12", 53, 2, listOf(p(53, "2 Tesalonicenses", 2)),
                    "Pablo desenmascara la estrategia del adversario antes del Día del Señor: la apostasía y la manifestación del 'hombre de pecado', quien se opondrá a todo lo que se llama Dios. No obstante, su destino está sellado: el Señor lo destruirá con el soplo de su boca y con el resplandor de su venida.",
                    "El mal tiene un límite impuesto por Dios; la verdad triunfará de manera fulminante sobre todo engaño satánico.",
                    "¿Te mantienes firme en la verdad de la Palabra para no ser arrastrado por corrientes de engaño cultural?")
                13 -> d(13, "Día 13: El Día del Señor como Ladrón en la Noche", "2 Pedro 3:1-13", 61, 3, listOf(p(61, "2 Pedro", 3)),
                    "Frente a los burladores que preguntan '¿dónde está la promesa de su advenimiento?', Pedro recuerda que para Dios un día es como mil años. El Señor no retarda su promesa según algunos la tienen por tardanza, sino que es paciente para con nosotros, no queriendo que ninguno perezca.",
                    "La aparente tardanza de Cristo no es descuido sino paciencia misericordiosa para que los pecadores se arrepientan.",
                    "¿Vives cada jornada con la sobriedad y pureza de quien sabe que el Señor puede llamar a la puerta en cualquier instante?")
                14 -> d(14, "Día 14: El Juicio de las Naciones", "Mateo 25:31-46", 40, 25, listOf(p(40, "Mateo", 25)),
                    "Cuando el Hijo del Hombre venga en su gloria con todos los santos ángeles, se sentará en su trono glorioso y separará a las naciones como el pastor aparta las ovejas de los cabritos. El criterio revelador: cómo tratamos a los hermanos más pequeños de Cristo.",
                    "El amor práctico hacia los necesitados y perseguidos por causa de Cristo es la evidencia indiscutible de un corazón regenerado.",
                    "¿Refleja tu trato a los vulnerables el reconocimiento del rostro de Jesús en ellos?")
                15 -> d(15, "Día 15: Apocalipsis 1: La Visión de Cristo Exaltado", "Apocalipsis 1:1-20", 66, 1, listOf(p(66, "Apocalipsis", 1)),
                    "Juan en la isla de Patmos contempla a Jesús no ya como el siervo humilde del camino a la cruz, sino como el Señor soberano: ojos como llama de fuego, voz como estruendo de muchas aguas y en su mano derecha las siete estrellas. Él tiene las llaves de la muerte y del Hades.",
                    "Jesucristo resucitado reina con autoridad absoluta sobre la vida, la muerte y el destino de su Iglesia.",
                    "¿Cae tu alma postrada en adoración reverente ante la majestad insuperable de Jesucristo?")
                16 -> d(16, "Día 16: Éfeso y Esmirna: El Primer Amor y la Persecución", "Apocalipsis 2:1-11", 66, 2, listOf(p(66, "Apocalipsis", 2)),
                    "Éfeso poseía doctrina pura y arduo trabajo, pero había dejado su primer amor. Esmirna, en medio de extrema pobreza y tribulación, es declarada espiritualmente rica y llamada a ser fiel hasta la muerte para recibir la corona de la vida. Dos espejos vitales para la Iglesia de hoy.",
                    "La ortodoxia sin pasión por Cristo es esterilidad; la fidelidad en la prueba produce riquezas imperecederas.",
                    "¿Arde tu corazón con el mismo fervor del primer amor con que conociste al Salvador?")
                17 -> d(17, "Día 17: Pérgamo y Tiatira: Pureza Doctrinal y Ética", "Apocalipsis 2:12-29", 66, 2, listOf(p(66, "Apocalipsis", 2)),
                    "Pérgamo moraba donde está el trono de Satanás y toleraba la doctrina de Balaam; Tiatira toleraba la inmoralidad y la idolatría bajo el pretexto de revelación. El Señor de los ojos de fuego exige pureza moral y doctrinal inflexible a su pueblo.",
                    "La tolerancia con el pecado y el compromiso con el mundo carcomen el testimonio de la comunidad de fe.",
                    "¿Has permitido compromisos morales o justificaciones en tu vida que contradicen la santidad de Dios?")
                18 -> d(18, "Día 18: Sardis y Filadelfia: Salir de la Muerte a la Puerta Abierta", "Apocalipsis 3:1-13", 66, 3, listOf(p(66, "Apocalipsis", 3)),
                    "Sardis tenía nombre de que vivía, pero estaba muerta: una advertencia fulminante contra el cristianismo de fachada. Filadelfia, aunque tenía pocas fuerzas, guardó la palabra y no negó el nombre de Cristo; por eso el Señor le abrió una puerta que nadie puede cerrar.",
                    "Dios no busca apariencias religiosas de éxito terrenal, sino fidelidad humilde y dependencia constante de su gracia.",
                    "¿Prefieres la aprobación humana de las apariencias o la puerta abierta de la bendición que solo Cristo otorga?")
                19 -> d(19, "Día 19: Laodicea: El Peligro Letal de la Tibieza", "Apocalipsis 3:14-22", 66, 3, listOf(p(66, "Apocalipsis", 3)),
                    "Laodicea se creía rica y saciada sin necesidad de nada, pero a los ojos de Cristo era desventurada, miserable, pobre, ciega y desnuda. Jesús le ofrece comprar de Él oro refinado en fuego y se coloca a la puerta llamando con amor: 'Si alguno oye mi voz y abre la puerta, entraré a él'.",
                    "La autosuficiencia y la tibieza espiritual provocan rechazo en Dios; la verdadera riqueza se halla solo en Cristo.",
                    "¿Le has abierto de par en par la puerta de tu vida cotidiana a Jesús o lo tienes esperando en el umbral?")
                20 -> d(20, "Día 20: La Sala del Trono Celestial", "Apocalipsis 4:1-11", 66, 4, listOf(p(66, "Apocalipsis", 4)),
                    "Antes de desatar los juicios sobre la tierra, Juan es arrebatado al cielo para ver que hay un Trono firme e inconmovible. Alrededor del Trono, los cuatro seres vivientes y los veinticuatro ancianos rinden coronas y proclaman día y noche: 'Santo, Santo, Santo es el Señor Dios Todopoderoso'.",
                    "El universo entero está gobernado desde el Trono de Dios; la adoración incesante es la respuesta debida a su majestad.",
                    "¿Rindes tus logros y 'coronas' personales ante el Trono reconociendo que todo le pertenece a Dios?")
                21 -> d(21, "Día 21: El León que es Cordero: El Único Digno", "Apocalipsis 5:1-14", 66, 5, listOf(p(66, "Apocalipsis", 5)),
                    "Juan llora desconsoladamente porque nadie en el cielo ni en la tierra es hallado digno de abrir el libro sellado con siete sellos. Pero un anciano le dice: 'No llores. He aquí que el León de la tribu de Judá ha vencido'. Y Juan mira y ve en medio del trono un Cordero como inmolado.",
                    "Solo Jesucristo tiene la autoridad de desatar la historia humana porque venció derramando su sangre en la cruz.",
                    "¿Reconoces al Cordero inmolado como el único que puede dar sentido y redención a las crisis de tu vida?")
                22 -> d(22, "Día 22: La Apertura de los Sellos", "Apocalipsis 6:1-17; 7:9-17", 66, 6, listOf(p(66, "Apocalipsis", 6), p(66, "Apocalipsis", 7)),
                    "El Cordero abre los primeros sellos y cabalgan los cuatro jinetes: conquista engañosa, guerra, hambre y muerte. Los reyes de la tierra claman a los montes que los oculten de la ira del Cordero. No obstante, el capítulo 7 presenta a una multitud incontable de toda nación lavada en la sangre del Cordero.",
                    "El juicio divino desenmascara la vanidad humana, pero la misericordia redime a un pueblo que nadie puede contar.",
                    "¿Está tu esperanza anclada en la sangre del Cordero que te libra de la ira venidera?")
                23 -> d(23, "Día 23: Las Trompetas del Juicio", "Apocalipsis 8:1-13; 9:1-21", 66, 8, listOf(p(66, "Apocalipsis", 8), p(66, "Apocalipsis", 9)),
                    "Tras un silencio solemne en el cielo, siete ángeles reciben trompetas. Los juicios caen sobre la vegetación, los mares, los ríos y los astros, y luego se abren las tinieblas del abismo. Son advertencias enérgicas llamando a los moradores de la tierra a volverse al Dios vivo.",
                    "Los juicios preliminares son toques de trompeta de misericordia para advertir al rebelde antes del fin irrevocable.",
                    "¿Prestas atención a las advertencias oportunas de la Palabra de Dios antes de que las consecuencias sean irreversibles?")
                24 -> d(24, "Día 24: Los Dos Testigos y la Séptima Trompeta", "Apocalipsis 11:1-19; 12:7-12", 66, 11, listOf(p(66, "Apocalipsis", 11), p(66, "Apocalipsis", 12)),
                    "En medio de la mayor oscuridad, Dios levanta a sus dos testigos con poder profético. Aunque el mundo celebra su muerte temporal, el aliento de vida de Dios entra en ellos y son resucitados. Suena la séptima trompeta y se proclama: 'Los reinos del mundo han venido a ser de nuestro Señor y de su Cristo'.",
                    "El testimonio fiel es indestructible hasta que la misión encomendada por Dios haya sido cumplida plenamente.",
                    "¿Perseveras como testigo valiente de la verdad aunque la corriente cultural celebre la mentira?")
                25 -> d(25, "Día 25: Las Bestias de Apocalipsis 13", "Apocalipsis 13:1-18; 14:6-12", 66, 13, listOf(p(66, "Apocalipsis", 13), p(66, "Apocalipsis", 14)),
                    "Surge la bestia del mar (el poder político global anticristiano) y la bestia de la tierra (el falso profeta religioso que exige adoración y la marca del engaño). Frente al totalitarismo satánico, se proclama el Evangelio eterno y se declara la bienaventuranza de los que guardan los mandamientos de Dios.",
                    "El sistema mundano intentará coaccionar nuestra lealtad; la paciencia de los santos radica en no transigir con el engaño.",
                    "¿En quién está sellada tu lealtad: en las promesas seductoras del mundo o en el Señor Jesucristo?")
                26 -> d(26, "Día 26: Las Copas de la Ira y la Caída de Babilonia", "Apocalipsis 16:1-19; 18:1-10", 66, 16, listOf(p(66, "Apocalipsis", 16), p(66, "Apocalipsis", 18)),
                    "Las siete copas derraman la consumación de la ira de Dios sobre el imperio de la bestia. Babilonia la grande, símbolo de la seducción económica, moral y religiosa corrompida, cae en una sola hora. Se oye una voz del cielo: 'Salid de ella, pueblo mío, para que no seáis partícipes de sus pecados'.",
                    "La justicia de Dios no fallará; los imperios levantados sobre la avaricia y la corrupción serán desmantelados por completo.",
                    "¿Mantienes tu corazón limpio de las influencias y seducciones mundanas que Dios juzgará?")
                27 -> d(27, "Día 27: El Jinete del Caballo Blanco y Armagedón", "Apocalipsis 19:1-21", 66, 19, listOf(p(66, "Apocalipsis", 19)),
                    "¡Aleluya! El cielo se abre y aparece Cristo montado en un caballo blanco: llamado Fiel y Verdadero, con ojos como llama de fuego y un manto teñido en sangre. De su boca sale una espada aguda para herir a las naciones. La bestia y el falso profeta son capturados y arrojados vivos al lago de fuego.",
                    "La victoria militar y espiritual de Cristo en su regreso es absoluta, instantánea y definitiva; el Rey reina supremo.",
                    "¿Esperas con gozo triunfal el día en que Jesucristo pondrá fin a toda tiranía e injusticia en la tierra?")
                28 -> d(28, "Día 28: El Milenio y el Gran Trono Blanco", "Apocalipsis 20:1-15", 66, 20, listOf(p(66, "Apocalipsis", 20)),
                    "Satanás es atado por mil años mientras Cristo reina con sus santos. Tras su rebelión final y derrota definitiva, Juan contempla el Gran Trono Blanco: la tierra y el cielo huyeron de delante de Él, los libros fueron abiertos y todo aquel que no fue hallado inscrito en el Libro de la Vida fue lanzado al lago de fuego.",
                    "El juicio final es la garantía de que ninguna maldad quedará impune; el Libro de la Vida es nuestro único boleto de salvación.",
                    "¿Tienes la certeza inconmovible de que tu nombre está inscrito en el Libro de la Vida del Cordero?")
                29 -> d(29, "Día 29: El Cielo Nuevo y la Nueva Tierra", "Apocalipsis 21:1-27", 66, 21, listOf(p(66, "Apocalipsis", 21)),
                    "Juan ve descender la santa ciudad, la Nueva Jerusalén, dispuesta como una esposa ataviada para su marido. El tabernáculo de Dios está con los hombres: Él morará con ellos, enjugará toda lágrima y no habrá más muerte. Las puertas de perla nunca se cerrarán y la gloria de Dios la iluminará.",
                    "El destino glorioso del creyente es habitar en una comunión perfecta, pura y gozosa con Dios en la Creación restaurada.",
                    "¿Cómo influye la belleza prometida de la Nueva Jerusalén en tu paciencia ante las aflicciones presentes?")
                else -> d(30, "Día 30: El Río de la Vida: 'Ciertamente Vengo en Breve'", "Apocalipsis 22:1-21", 66, 22, listOf(p(66, "Apocalipsis", 22)),
                    "En el corazón de la ciudad fluye el río limpio de agua de vida, claro como el cristal, saliendo del trono de Dios y del Cordero. Ya no habrá maldición. El canon sagrado concluye con la promesa más conmovedora de Jesús: 'Ciertamente vengo en breve', y la respuesta unánime de la Iglesia: 'Amén; sí, ven, Señor Jesús'.",
                    "La esperanza escatológica culmina en la presencia eterna del Señor; el clamor del creyente debe ser: ¡Maranata!",
                    "¿Late tu corazón hoy con el mismo anhelo ardiente de la Iglesia primitiva: '¡Sí, ven, Señor Jesús!'?")
            }
        }
    }

    // --- ROMANOS: FUNDAMENTOS DE LA GRACIA (30 Días) ---
    val romansPlan: List<ReadingPlanDay> by lazy {
        (1..30).map { dayNum ->
            val ch = ((dayNum - 1) / 2) + 1
            val isFirstHalf = dayNum % 2 != 0
            val chapterTheme = when (ch) {
                1 -> if (isFirstHalf) "El Poder del Evangelio y la Justicia de Dios" else "La Revelación en la Creación y el Corazón Humano"
                2 -> if (isFirstHalf) "El Juicio Justo e Imparcial de Dios" else "La Verdadera Circuncisión del Corazón"
                3 -> if (isFirstHalf) "No Hay Justo ni Aun Uno" else "Justificados Gratuitamente por Su Gracia"
                4 -> if (isFirstHalf) "Abraham Justificado por la Fe" else "La Promesa Firme para Todos los Creyentes"
                5 -> if (isFirstHalf) "Paz para con Dios y Esperanza en la Prueba" else "El Don de Cristo Supera la Caída de Adán"
                6 -> if (isFirstHalf) "Muertos al Pecado, Vivos para Dios" else "Siervos de la Justicia y la Dádiva de Vida Eterna"
                7 -> if (isFirstHalf) "Libres de la Ley para Pertenecer a Cristo" else "La Lucha Interior y la Victoria en Jesucristo"
                8 -> if (isFirstHalf) "Ninguna Condenación y la Vida en el Espíritu" else "Más que Vencedores: Nada nos Separará de Su Amor"
                9 -> if (isFirstHalf) "La Soberanía de Dios y las Promesas a Israel" else "El Alfarero Soberano y los Vasos de Misericordia"
                10 -> if (isFirstHalf) "La Justicia que es por la Fe en Cristo" else "La Fe Viene por el Oír la Palabra de Dios"
                11 -> if (isFirstHalf) "El Remanente Escogido por Gracia" else "El Olivo Silvestre y la Profundidad de la Sabiduría Divina"
                12 -> if (isFirstHalf) "Sacrificio Vivo y Renovación de la Mente" else "El Amor Sincero y Vencer el Mal con el Bien"
                13 -> if (isFirstHalf) "El Ciudadano Cristiano y el Respeto al Orden" else "El Amor como Cumplimiento de la Ley y Vestirse de Cristo"
                14 -> if (isFirstHalf) "Recibir al Débil en la Fe sin Juzgar" else "Seguir lo que Contribuye a la Paz y la Mutua Edificación"
                15 -> if (isFirstHalf) "Edificar al Prójimo según el Ejemplo de Cristo" else "El Ministerio Apostólico y la Unidad de la Iglesia"
                else -> if (isFirstHalf) "Comunión Fraterna y Saludos de Amor en el Cuerpo" else "Doxología Final: Al Único y Sabio Dios sea la Gloria"
            }
            d(
                num = dayNum,
                title = "Día $dayNum: Romanos $ch - $chapterTheme",
                summary = "Romanos $ch",
                pBId = 45,
                pC = ch,
                passages = listOf(p(45, "Romanos", ch)),
                narrative = "En el capítulo $ch de la epístola a los Romanos, el apóstol Pablo desarrolla con rigor magistral el tema de «$chapterTheme». Cada argumento bíblico desmantela el orgullo de la justicia propia humana y exalta la obra redentora, suficiente y soberana de nuestro Señor Jesucristo.",
                lesson = "En Romanos $ch aprendemos que $chapterTheme descansa íntegramente en la gracia inmerecida de Dios y en la justicia perfecta de Cristo.",
                reflection = "¿Cómo transforma la enseñanza de Romanos $ch sobre «$chapterTheme» tu manera de acercarte a Dios y tratar a tu prójimo hoy?"
            )
        }
    }

    // --- 30 DÍAS CON JESÚS (30 Días) ---
    val jesus30Plan: List<ReadingPlanDay> by lazy {
        (1..30).map { dayNum ->
            val meta = when (dayNum) {
                1 -> PlanDayMeta(40, "Mateo", 5, "Las Bienaventuranzas y el Carácter del Reino")
                2 -> PlanDayMeta(40, "Mateo", 6, "La Oración Secreta y la Confianza en el Padre")
                3 -> PlanDayMeta(40, "Mateo", 7, "Cimiento sobre la Roca y Frutos Verdaderos")
                4 -> PlanDayMeta(40, "Mateo", 8, "Autoridad de Jesús sobre la Enfermedad y el Mar")
                5 -> PlanDayMeta(40, "Mateo", 9, "El Médico de las Almas y la Compasión por las Multitudes")
                6 -> PlanDayMeta(40, "Mateo", 11, "Descanso para los Cansados en el Yugo de Cristo")
                7 -> PlanDayMeta(40, "Mateo", 13, "Las Parábolas del Sembrador y el Tesoro Escondido")
                8 -> PlanDayMeta(41, "Marcos", 1, "El Inicio del Ministerio y el Llamado de los Discípulos")
                9 -> PlanDayMeta(41, "Marcos", 2, "Perdón de Pecados y Señor del Día de Reposo")
                10 -> PlanDayMeta(41, "Marcos", 4, "Jesús Calma la Tempestad con Su Voz")
                11 -> PlanDayMeta(41, "Marcos", 5, "Poder Restaurador ante Casos Imposibles")
                12 -> PlanDayMeta(41, "Marcos", 6, "El Buen Pastor que Multiplicó los Panes")
                13 -> PlanDayMeta(41, "Marcos", 8, "Tomar la Cruz y Seguir al Mesías")
                14 -> PlanDayMeta(41, "Marcos", 10, "El Hijo del Hombre Vino para Servir y Dar Su Vida")
                15 -> PlanDayMeta(42, "Lucas", 4, "Ungido para Dar Buenas Nuevas a los Quebrantados")
                16 -> PlanDayMeta(42, "Lucas", 7, "Perdón Abundante y Amor Agradecido")
                17 -> PlanDayMeta(42, "Lucas", 10, "El Buen Samaritano y Elegir la Buena Parte a Sus Pies")
                18 -> PlanDayMeta(42, "Lucas", 15, "El Abrazo del Padre al Hijo Pródigo")
                19 -> PlanDayMeta(42, "Lucas", 18, "Perseverancia en la Oración y Humildad de Corazón")
                20 -> PlanDayMeta(42, "Lucas", 19, "Salvación en Casa de Zaqueo: Buscar lo que se Había Perdido")
                21 -> PlanDayMeta(42, "Lucas", 24, "El Camino a Emaús y los Corazones que Arden")
                22 -> PlanDayMeta(43, "Juan", 1, "El Verbo Hecho Carne Lleno de Gracia y de Verdad")
                23 -> PlanDayMeta(43, "Juan", 3, "Nacer de Nuevo y el Amor Infinito de Dios")
                24 -> PlanDayMeta(43, "Juan", 4, "El Agua Viva y la Adoración en Espíritu y en Verdad")
                25 -> PlanDayMeta(43, "Juan", 10, "El Buen Pastor que Da Su Vida por las Ovejas")
                26 -> PlanDayMeta(43, "Juan", 11, "Yo Soy la Resurrección y la Vida")
                27 -> PlanDayMeta(43, "Juan", 13, "El Lavatorio de los Pies y el Mandamiento Nuevo")
                28 -> PlanDayMeta(43, "Juan", 15, "Permanecer en la Vid Verdadera para Llevar Fruto")
                29 -> PlanDayMeta(43, "Juan", 19, "Consumado Es: El Sacrificio Supremo en el Calvario")
                else -> PlanDayMeta(43, "Juan", 20, "La Tumba Vacía y el Encuentro con el Cristo Resucitado")
            }
            d(
                num = dayNum,
                title = "Día $dayNum: ${meta.subTitle}",
                summary = "${meta.bookName} ${meta.chapter}",
                pBId = meta.bookId,
                pC = meta.chapter,
                passages = listOf(p(meta.bookId, meta.bookName, meta.chapter)),
                narrative = "Al recorrer ${meta.bookName} ${meta.chapter}, contemplamos a Jesucristo revelando «${meta.subTitle}». En cada palabra y en cada gesto del Maestro vemos la plenitud de la gracia y de la verdad acercándose al ser humano quebrantado para restaurarlo, perdonarlo y llamarlo a una vida nueva.",
                lesson = "En ${meta.bookName} ${meta.chapter}, Jesús se manifiesta como el Salvador soberano y compasivo cuyo poder transforma toda realidad humana.",
                reflection = "¿Qué actitud o palabra de Jesús en ${meta.bookName} ${meta.chapter} necesitas abrazar e imitar hoy en tu vida diaria?"
            )
        }
    }

    // --- 31 DÍAS EN PROVERBIOS (31 Días) ---
    val proverbs31Plan: List<ReadingPlanDay> by lazy {
        (1..31).map { dayNum ->
            val provTheme = when (dayNum) {
                1 -> "El Principio de la Sabiduría es el Temor de Jehová"
                2 -> "Buscar la Sabiduría como a Tesoros Escondidos"
                3 -> "Fíate de Jehová de Todo tu Corazón"
                4 -> "Sobre Toda Cosa Guardada, Guarda tu Corazón"
                5 -> "Pureza, Fidelidad Conyugal y Huir de la Tentación"
                6 -> "Diligencia frente a la Pereza y Lo que Dios Aborrece"
                7 -> "Guardar los Mandamientos como a la Niña de tus Ojos"
                8 -> "El Clamor Eterno de la Sabiduría Divina"
                9 -> "El Banquete de la Sabiduría frente a la Necedad"
                10 -> "El Contraste entre la Integridad del Justo y el Camino Impío"
                11 -> "Balanza Justa, Humildad y Generosidad que Prospera"
                12 -> "Amor a la Corrección y Palabras que Sanan"
                13 -> "Esperanza Cumplida y Caminar con los Sabios"
                14 -> "Edificar la Casa con Sabiduría y Temor Reverente"
                15 -> "La Blanda Respuesta Quita la Ira"
                16 -> "Encomendar a Jehová las Obras y Someter los Planes"
                17 -> "El Amigo que Ama en Todo Tiempo y el Corazón Alegre"
                18 -> "Torre Fuerte es el Nombre de Jehová y el Poder de la Lengua"
                19 -> "Integridad en la Pobreza, Paciencia y Bondad hacia el Necesitado"
                20 -> "Rectitud en los Negocios y Consejos Profundos del Corazón"
                21 -> "El Corazón del Rey en la Mano de Jehová y la Justicia"
                22 -> "El Buen Nombre, la Crianza Piadosa y la Prudencia Financiera"
                23 -> "Dominio Propio y No Envidiar la Prosperidad del Pecador"
                24 -> "Siete Veces Cae el Justo y Vuelve a Levantarse"
                25 -> "Dominio del Espíritu como Ciudad Amurallada"
                26 -> "Discernimiento ante la Necedad, el Chisme y la Pereza"
                27 -> "Hierro con Hierro se Aguza: El Valor de la Amistad Fiel"
                28 -> "El que Confiesa y se Aparta Alcanzará Misericordia"
                29 -> "Humildad que Recibe Honra y Confiar en Jehová sin Temor"
                30 -> "La Oración de Agur: Pureza de Palabra y Contentamiento"
                else -> "Sabiduría en el Liderazgo y el Carácter Virtuoso que Teme a Dios"
            }
            d(
                num = dayNum,
                title = "Día $dayNum: Proverbios $dayNum - $provTheme",
                summary = "Proverbios $dayNum",
                pBId = 20,
                pC = dayNum,
                passages = listOf(p(20, "Proverbios", dayNum)),
                narrative = "El capítulo $dayNum de Proverbios aterriza la fe al terreno concreto de «$provTheme». La sabiduría bíblica no es mera acumulación de datos intelectuales, sino la habilidad espiritual de aplicar la verdad de Dios a nuestras palabras, finanzas, relaciones familiares y decisiones diarias.",
                lesson = "La sabiduría de Proverbios $dayNum enseña que el temor reverente del Señor produce rectitud, prudencia y paz duradera.",
                reflection = "¿Qué principio práctico de Proverbios $dayNum sobre «$provTheme» necesitas aplicar hoy mismo en tu conducta o palabras?"
            )
        }
    }

    // --- PNEUMATOLOGÍA: EL ESPÍRITU SANTO (15 Días) ---
    val pneumatologyPlan: List<ReadingPlanDay> by lazy {
        (1..15).map { dayNum ->
            val meta = when (dayNum) {
                1 -> PlanDayMeta(1, "Génesis", 1, "El Espíritu en la Creación del Universo")
                2 -> PlanDayMeta(2, "Éxodo", 31, "El Espíritu Capacitando para el Servicio")
                3 -> PlanDayMeta(29, "Joel", 2, "La Promesa del Derramamiento Universal")
                4 -> PlanDayMeta(42, "Lucas", 4, "Jesús Ungido por el Espíritu Santo")
                5 -> PlanDayMeta(43, "Juan", 14, "El Consolador y Maestro de la Verdad")
                6 -> PlanDayMeta(43, "Juan", 16, "Convicción de Pecado, Justicia y Juicio")
                7 -> PlanDayMeta(44, "Hechos", 2, "El Fuego y Poder de Pentecostés")
                8 -> PlanDayMeta(49, "Efesios", 1, "El Sello y Arras de Nuestra Herencia")
                9 -> PlanDayMeta(43, "Juan", 3, "La Regeneración Sobrenatural")
                10 -> PlanDayMeta(48, "Gálatas", 5, "Andar en el Espíritu vs. las Obras de la Carne")
                11 -> PlanDayMeta(48, "Gálatas", 5, "El Fruto del Espíritu en el Creyente")
                12 -> PlanDayMeta(46, "1 Corintios", 12, "Diversidad de Dones para Edificación")
                13 -> PlanDayMeta(49, "Efesios", 4, "No Contristar al Espíritu Santo")
                14 -> PlanDayMeta(45, "Romanos", 8, "La Intercesión del Espíritu en la Oración")
                else -> PlanDayMeta(66, "Apocalipsis", 22, "El Espíritu y la Novia Dicen: ¡Ven!")
            }
            d(
                num = dayNum,
                title = "Día $dayNum: ${meta.subTitle}",
                summary = "${meta.bookName} ${meta.chapter}",
                pBId = meta.bookId,
                pC = meta.chapter,
                passages = listOf(p(meta.bookId, meta.bookName, meta.chapter)),
                narrative = "En ${meta.bookName} ${meta.chapter} contemplamos la obra gloriosa de la tercera Persona de la Trinidad: «${meta.subTitle}». El Espíritu Santo no es una fuerza impersonal, sino Dios mismo obrando activamente para dar vida, iluminar la Palabra, santificar el carácter y glorificar a Jesucristo en nosotros.",
                lesson = "La obra del Espíritu Santo en ${meta.bookName} ${meta.chapter} nos capacita para vivir en santidad, comunión y poder espiritual.",
                reflection = "¿Cómo puedes rendir hoy tu voluntad a la obra del Espíritu Santo revelada en «${meta.subTitle}»?"
            )
        }
    }

    // --- CRISTO EN EL TABERNÁCULO Y HEBREOS (15 Días) ---
    val tabernaclePlan: List<ReadingPlanDay> by lazy {
        (1..15).map { dayNum ->
            val meta = when (dayNum) {
                1 -> PlanDayMeta(58, "Hebreos", 1, "Cristo Superior a los Ángeles")
                2 -> PlanDayMeta(2, "Éxodo", 27, "El Altar de Bronce: La Expiación Sustitutoria")
                3 -> PlanDayMeta(2, "Éxodo", 30, "El Lavacro: Purificación Continua")
                4 -> PlanDayMeta(2, "Éxodo", 25, "La Mesa del Pan: Jesús el Pan de Vida")
                5 -> PlanDayMeta(2, "Éxodo", 25, "El Candelabro: Cristo la Luz del Mundo")
                6 -> PlanDayMeta(2, "Éxodo", 30, "El Altar de Incienso: Intercesión Sacerdotal")
                7 -> PlanDayMeta(2, "Éxodo", 26, "El Velo Rasgado: Acceso Libre al Trono")
                8 -> PlanDayMeta(2, "Éxodo", 25, "El Arca y el Propiciatorio de Gracia")
                9 -> PlanDayMeta(58, "Hebreos", 7, "El Sacerdocio Eterno según Melquisedec")
                10 -> PlanDayMeta(3, "Levítico", 16, "El Gran Día de la Expiación (Yom Kipur)")
                11 -> PlanDayMeta(2, "Éxodo", 12, "El Cordero Pascual Inmaculado")
                12 -> PlanDayMeta(2, "Éxodo", 17, "La Roca Herida que Dio Agua Viva")
                13 -> PlanDayMeta(4, "Números", 21, "La Serpiente de Bronce en el Desierto")
                14 -> PlanDayMeta(58, "Hebreos", 8, "Mediador de un Mejor Pacto")
                else -> PlanDayMeta(58, "Hebreos", 10, "El Sacrificio Perfecto y Definitivo")
            }
            d(
                num = dayNum,
                title = "Día $dayNum: ${meta.subTitle}",
                summary = "${meta.bookName} ${meta.chapter}",
                pBId = meta.bookId,
                pC = meta.chapter,
                passages = listOf(p(meta.bookId, meta.bookName, meta.chapter)),
                narrative = "El pasaje de ${meta.bookName} ${meta.chapter} nos abre una ventana extraordinaria a la tipología bíblica: «${meta.subTitle}». Cada elemento del santuario antiguo fue diseñado por Dios como una figura profética que anunciaba la persona, el sacerdocio y el sacrificio perfecto de Jesucristo.",
                lesson = "A través de «${meta.subTitle}», vemos que Jesucristo es el cumplimiento definitivo que nos abre el camino directo a la presencia del Padre.",
                reflection = "¿De qué manera contemplar a Cristo en «${meta.subTitle}» profundiza tu gratitud y reverencia en la oración hoy?"
            )
        }
    }

    // --- TEOLOGÍA DE LOS PACTOS (15 Días) ---
    val covenantsPlan: List<ReadingPlanDay> by lazy {
        (1..15).map { dayNum ->
            val meta = when (dayNum) {
                1 -> PlanDayMeta(1, "Génesis", 1, "El Pacto de la Creación")
                2 -> PlanDayMeta(1, "Génesis", 3, "El Protoevangelio: La Simiente Prometida")
                3 -> PlanDayMeta(1, "Génesis", 9, "El Pacto con Noé y el Arco de Gracia")
                4 -> PlanDayMeta(1, "Génesis", 12, "El Pacto Abrahámico: Llamado y Bendición")
                5 -> PlanDayMeta(1, "Génesis", 15, "Ratificación Soberana del Pacto")
                6 -> PlanDayMeta(1, "Génesis", 17, "La Señal del Pacto y la Circuncisión")
                7 -> PlanDayMeta(2, "Éxodo", 19, "El Pacto del Sinaí: La Ley y la Santidad")
                8 -> PlanDayMeta(48, "Gálatas", 3, "El Propósito de la Ley como Tutor")
                9 -> PlanDayMeta(10, "2 Samuel", 7, "El Pacto Davídico: Trono y Dinastía Eterna")
                10 -> PlanDayMeta(24, "Jeremías", 31, "La Promesa del Nuevo Pacto Interior")
                11 -> PlanDayMeta(26, "Ezequiel", 36, "El Nuevo Corazón y el Espíritu Santo")
                12 -> PlanDayMeta(42, "Lucas", 22, "La Cena del Señor: Sellado con Sangre")
                13 -> PlanDayMeta(49, "Efesios", 2, "La Inclusión de los Gentiles en el Pacto")
                14 -> PlanDayMeta(58, "Hebreos", 8, "La Superioridad Eterna del Nuevo Pacto")
                else -> PlanDayMeta(66, "Apocalipsis", 21, "Consumación: 'Ellos Serán Mi Pueblo'")
            }
            d(
                num = dayNum,
                title = "Día $dayNum: ${meta.subTitle}",
                summary = "${meta.bookName} ${meta.chapter}",
                pBId = meta.bookId,
                pC = meta.chapter,
                passages = listOf(p(meta.bookId, meta.bookName, meta.chapter)),
                narrative = "En ${meta.bookName} ${meta.chapter} contemplamos un eslabón decisivo de la historia de la redención: «${meta.subTitle}». Los pactos bíblicos constituyen la columna vertebral de las Escrituras y demuestran cómo Dios se ha comprometido soberanamente a redimir y habitar en medio de Su pueblo.",
                lesson = "El estudio de «${meta.subTitle}» confirma que Dios es absolutamente fiel a Sus promesas y que todo pacto culmina en Cristo.",
                reflection = "¿Cómo fortalece tu fe saber que tu relación con Dios descansa en un pacto eterno e inquebrantable sellado por Cristo?"
            )
        }
    }

    // --- LIDERAZGO CON NEHEMÍAS (15 Días) ---
    val nehemiahPlan: List<ReadingPlanDay> by lazy {
        (1..15).map { dayNum ->
            val ch = dayNum.coerceAtMost(13)
            val nehTheme = when (dayNum) {
                1 -> "Quebranto, Carga Espiritual e Intercesión"
                2 -> "Valentía ante el Rey y Visión Estratégica"
                3 -> "Trabajo en Equipo: Cada Familia Frente a su Puerta"
                4 -> "Con una Mano en la Obra y otra en la Espada"
                5 -> "Integridad Financiera, Justicia y Compasión por el Pueblo"
                6 -> "Discernimiento y Firmeza ante las Trampas del Enemigo"
                7 -> "Organización, Mayordomía y Cuidado de las Familias"
                8 -> "Avivamiento Centrado en la Lectura y Comprensión de la Palabra"
                9 -> "Confesión Humilde y Memoria de la Fidelidad de Dios"
                10 -> "Renovación del Pacto y Compromiso Comunitario"
                11 -> "Disposición Voluntaria para Habitar y Servir"
                12 -> "Dedicación de los Muros con Gratitud y Alabanza"
                13 -> "Celo por la Pureza Espiritual y Perseverancia Final"
                14 -> "El Legado del Líder Siervo que Busca el Favor de Dios"
                else -> "Restaurar los Muros Espirituales de Nuestra Generación"
            }
            d(
                num = dayNum,
                title = "Día $dayNum: Nehemías $ch - $nehTheme",
                summary = "Nehemías $ch",
                pBId = 16,
                pC = ch,
                passages = listOf(p(16, "Nehemías", ch)),
                narrative = "Al estudiar Nehemías $ch bajo el enfoque de «$nehTheme», descubrimos cómo un hombre o mujer de Dios responde ante los tiempos de ruina y crisis. Nehemías no se quedó en la queja pasiva; combinó una vida de oración ferviente con planificación diligente, coraje frente a la oposición e integridad intachable.",
                lesson = "En Nehemías $ch aprendemos que «$nehTheme» requiere depender de Dios en oración mientras trabajamos con excelencia y carácter.",
                reflection = "¿Cómo puedes aplicar hoy el principio de «$nehTheme» en tu familia, trabajo o ministerio?"
            )
        }
    }

    // --- DE LA AFLICCIÓN A LA ESPERANZA (15 Días) ---
    val afflictionHopePlan: List<ReadingPlanDay> by lazy {
        (1..15).map { dayNum ->
            val meta = when (dayNum) {
                1 -> PlanDayMeta(19, "Salmos", 34, "Cercano Está Jehová a los Quebrantados")
                2 -> PlanDayMeta(19, "Salmos", 42, "¿Por Qué te Abates, Oh Alma Mía?")
                3 -> PlanDayMeta(19, "Salmos", 77, "Clamé a Dios con mi Voz en el Día de Angustia")
                4 -> PlanDayMeta(19, "Salmos", 88, "La Honestidad del Lamento en la Oscuridad")
                5 -> PlanDayMeta(23, "Isaías", 53, "El Varón de Dolores que Cargó Nuestro Sufrimiento")
                6 -> PlanDayMeta(47, "2 Corintios", 1, "El Dios de Toda Consolación")
                7 -> PlanDayMeta(47, "2 Corintios", 4, "Tribulados en Todo, Mas no Desesperados")
                8 -> PlanDayMeta(47, "2 Corintios", 12, "Bástate Mi Gracia: Poder en la Debilidad")
                9 -> PlanDayMeta(1, "Génesis", 50, "Lo que Vosotros Pensasteis para Mal, Dios para Bien")
                10 -> PlanDayMeta(19, "Salmos", 23, "Aunque Ande en Valle de Sombra de Muerte")
                11 -> PlanDayMeta(45, "Romanos", 8, "Todas las Cosas Ayudan a Bien")
                12 -> PlanDayMeta(19, "Salmos", 30, "Por la Noche Durará el Lloro, al Amanecer el Gozo")
                13 -> PlanDayMeta(23, "Isaías", 43, "Cuando Pases por las Aguas, Yo Estaré Contigo")
                14 -> PlanDayMeta(58, "Hebreos", 12, "La Disciplina Amorosa del Padre y sus Frutos")
                else -> PlanDayMeta(66, "Apocalipsis", 21, "Él Enjugará Toda Lágrima de Sus Ojos")
            }
            d(
                num = dayNum,
                title = "Día $dayNum: ${meta.subTitle}",
                summary = "${meta.bookName} ${meta.chapter}",
                pBId = meta.bookId,
                pC = meta.chapter,
                passages = listOf(p(meta.bookId, meta.bookName, meta.chapter)),
                narrative = "El pasaje consolador de ${meta.bookName} ${meta.chapter} nos sale al encuentro en medio de la prueba con la promesa: «${meta.subTitle}». El sufrimiento no es señal de abandono divino; en las manos del Redentor, el valle de lágrimas se transforma en el lugar donde conocemos la ternura y el poder sustentador del Padre.",
                lesson = "La promesa de «${meta.subTitle}» en ${meta.bookName} ${meta.chapter} nos asegura que Dios camina con nosotros y redime cada aflicción.",
                reflection = "¿Qué carga o dolor necesitas entregar hoy al Señor apoyándote en la verdad de «${meta.subTitle}»?"
            )
        }
    }

    // =========================================================================
    // 2. VIDA CRISTIANA, CRECIMIENTO Y PAZ INTERIOR (Planes Cortos y Medianos)
    // =========================================================================

    // --- VIDA NUEVA EN CRISTO (5 Días) ---
    val newLifeChristPlan: List<ReadingPlanDay> by lazy {
        listOf(
            d(1, "Día 1: Una Nueva Creación en Cristo", "2 Corintios 5:14-21; Efesios 2:1-5", 47, 5, listOf(p(47, "2 Corintios", 5), p(49, "Efesios", 2)),
                "Si alguno está en Cristo, nueva criatura es; las cosas viejas pasaron, he aquí todas son hechas nuevas. No se trata de un simple cambio de hábitos o de una reforma moral, sino de una resurrección espiritual: estábamos muertos en delitos y pecados, y Dios nos dio vida juntamente con Cristo.",
                "En Cristo recibes una identidad totalmente nueva y una naturaleza reconciliada con el Padre.",
                "¿Vives condenado por tus errores pasados o celebras la nueva criatura que Cristo formó en ti?"),
            d(2, "Día 2: Despojarse del Viejo Hombre", "Efesios 4:17-24; Colosenses 3:1-10", 49, 4, listOf(p(49, "Efesios", 4), p(51, "Colosenses", 3)),
                "Así como nadie se pone ropa sucia después de bañarse, el creyente debe despojarse activamente de las prácticas del viejo hombre viciado: mentira, ira destructiva, maledicencia y rencor, vistiéndose del nuevo hombre creado según Dios en la justicia y santidad de la verdad.",
                "La santificación exige una renuncia intencional a la vieja naturaleza para dar lugar al carácter de Cristo.",
                "¿Qué vestidura vieja de actitud o lenguaje necesitas desechar en este día?"),
            d(3, "Día 3: El Alimento Espiritual Cotidiano", "1 Pedro 2:1-3; Salmo 119:9-16", 60, 2, listOf(p(60, "1 Pedro", 2), p(19, "Salmos", 119)),
                "Un recién nacido clama con fuerza por la leche materna para poder crecer sano; del mismo modo, el nuevo creyente necesita la leche pura de la Palabra de Dios todos los días. Sin la lectura meditada y continua de las Escrituras, el alma desfallece y se debilita.",
                "La Palabra de Dios es el alimento indispensable que nutre, limpia y fortalece tu nueva vida espiritual.",
                "¿Has hecho de la lectura y meditación bíblica un alimento diario prioritario e innegociable?"),
            d(4, "Día 4: Caminar en Luz y en Comunidad", "1 Juan 1:5-10; Hebreos 10:23-25", 62, 1, listOf(p(62, "1 Juan", 1), p(58, "Hebreos", 10)),
                "La vida cristiana no fue diseñada para vivirse en aislamiento solitario. Si andamos en luz como Él está en luz, tenemos comunión unos con otros y la sangre de Jesucristo nos limpia de todo pecado. La comunidad de fe es el hogar donde crecemos, nos corregimos y nos animamos al amor.",
                "La comunión fraterna en la Iglesia protege al creyente del enfriamiento y el engaño espiritual.",
                "¿Estás conectado activamente con hermanos en la fe que te edifiquen y oren contigo?"),
            d(5, "Día 5: La Mente de Cristo y el Propósito Diario", "Filipenses 2:1-11; 1 Corintios 10:31", 50, 2, listOf(p(50, "Filipenses", 2), p(46, "1 Corintios", 10)),
                "Haya en vosotros este mismo sentir que hubo también en Cristo Jesús: quien siendo en forma de Dios, no estimó el ser igual a Dios como cosa a que aferrarse, sino que se despojó a sí mismo tomando forma de siervo. Tu vida ahora tiene un propósito glorioso: glorificar a Dios sirviendo a los demás.",
                "La verdadera grandeza en el Reino de Dios se mide por la humildad y la disposición de servir a otros con amor.",
                "¿Cómo puedes reflejar la mente humilde y servicial de Jesús en tu hogar o trabajo hoy mismo?")
        )
    }

    // --- LA VOLUNTAD DE DIOS (7 Días) ---
    val godsWillPlan: List<ReadingPlanDay> by lazy {
        listOf(
            d(1, "Día 1: Buena, Agradable y Perfecta", "Romanos 12:1-2", 45, 12, listOf(p(45, "Romanos", 12)),
                "No os conforméis a este siglo, sino transformaos por medio de la renovación de vuestro entendimiento, para que comprobéis cuál sea la buena voluntad de Dios, agradable y perfecta. La voluntad de Dios no es una trampa que temer, sino el diseño más sabio, seguro y pleno para tu existencia.",
                "Renovar la mente con las Escrituras es el prerrequisito para comprobar la voluntad de Dios.",
                "¿Confías verdaderamente en que la voluntad de Dios para ti es mejor que tus propios planes?"),
            d(2, "Día 2: Voluntad Revelada vs. Cosas Secretas", "Deuteronomio 29:29; 1 Tesalonicenses 4:1-8", 5, 29, listOf(p(5, "Deuteronomio", 29), p(52, "1 Tesalonicenses", 4)),
                "Las cosas secretas pertenecen a Jehová nuestro Dios, pero las reveladas son para nosotros y para nuestros hijos. Antes de angustiarte por saber con quién te casarás o qué trabajo tendrás en 10 años, obedece la voluntad que Dios ya reveló con claridad: 'La voluntad de Dios es vuestra santificación'.",
                "Quien es fiel obedeciendo la voluntad ya revelada en la Biblia experimentará claridad en las decisiones abiertas.",
                "¿Estás obedeciendo los mandamientos claros de santidad y amor que Dios ya ha escrito en Su Palabra?"),
            d(3, "Día 3: La Palabra como Lámpara para los Pies", "Salmo 119:101-105; 2 Timoteo 3:14-17", 19, 119, listOf(p(19, "Salmos", 119), p(55, "2 Timoteo", 3)),
                "Lámpara es a mis pies tu palabra, y lumbrera a mi camino. En la antigüedad, una lámpara de aceite no iluminaba kilómetros a la distancia; solo alumbraba el siguiente paso en la oscuridad. Dios no suele mostrarnos todo el mapa, pero su Palabra nos da la luz suficiente para dar el paso correcto hoy.",
                "La Biblia es la guía primaria, infalible y suficiente para la toma de decisiones rectas.",
                "¿Consultas primero las Escrituras antes de tomar decisiones financieras, sentimentales o laborales?"),
            d(4, "Día 4: La Sabiduría del Consejo Bíblico", "Proverbios 11:14; 15:22; 19:20-21", 20, 11, listOf(p(20, "Proverbios", 11), p(20, "Proverbios", 15)),
                "Donde no hay dirección sabia, caerá el pueblo; mas en la multitud de consejeros hay seguridad. Dios utiliza a hermanos maduros, pastores piadosos y mentores en la fe para confirmar o alertarnos sobre caminos peligrosos que a nosotros nos parecen rectos.",
                "Buscar consejo maduro en la fe derriba los sesgos del orgullo y previene caídas lamentables.",
                "¿Tienes mentores espirituales maduros a quienes pides consejo antes de dar pasos trascendentales?"),
            d(5, "Día 5: La Paz de Cristo como Árbitro en el Corazón", "Colosenses 3:15; Filipenses 4:6-9", 51, 3, listOf(p(51, "Colosenses", 3), p(50, "Filipenses", 4)),
                "Y la paz de Dios gobierne en vuestros corazones. La palabra griega para 'gobierne' alude al árbitro que decide una jugada deportiva. Cuando presentamos todas nuestras opciones delante de Dios con acción de gracias, la convicción serena del Espíritu trae una paz que confirma el rumbo correcto.",
                "La paz espiritual de Dios confirma y guarda el corazón cuando las motivaciones están alineadas con Su voluntad.",
                "¿Sientes paz de Dios o una inquietud persistente en la decisión que estás evaluando tomar?"),
            d(6, "Día 6: Cuando Dios Cierra Puertas", "Hechos 16:6-10; Proverbios 16:9", 44, 16, listOf(p(44, "Hechos", 16), p(20, "Proverbios", 16)),
                "El corazón del hombre piensa su camino; mas Jehová endereza sus pasos. El apóstol Pablo y su equipo intentaron ir a Asia y a Bitinia, pero el Espíritu Santo se lo impidió, abriéndoles en cambio la puerta de Macedonia. Una puerta cerrada por Dios no es una derrota; es protección providencial.",
                "Las puertas cerradas por Dios son redirecciones amorosas hacia su propósito más fértil.",
                "¿Aceptas con humildad y fe cuando Dios frena o cancela un proyecto en el que tenías puestas tus ilusiones?"),
            d(7, "Día 7: La Rendición de Getsemaní", "Lucas 22:39-46; Santiago 4:13-17", 42, 22, listOf(p(42, "Lucas", 22), p(59, "Santiago", 4)),
                "En el huerto de Getsemaní, sudando gotas como de sangre, Jesús pronunció la oración cumbre de toda rendición: 'Padre, si quieres, pasa de mí esta copa; pero no se haga mi voluntad, sino la tuya'. Conocer la voluntad de Dios culmina siempre en la sumisión voluntaria a Sus designios de amor.",
                "El mayor acto de madurez espiritual es soltar el control y decir de corazón: 'Señor, hágase tu voluntad'.",
                "¿Estás dispuesto a rendir tus planes si la voluntad de Dios apunta en una dirección distinta a tus deseos?")
        )
    }

    // --- PAZ EN LA TORMENTA (5 Días) ---
    val peaceAnxietyPlan: List<ReadingPlanDay> by lazy {
        listOf(
            d(1, "Día 1: Desarmar la Preocupación", "Mateo 6:25-34", 40, 6, listOf(p(40, "Mateo", 6)),
                "Jesús va a la raíz de la ansiedad humana con una pregunta frontal: '¿Quién de vosotros podrá, por mucho que se afane, añadir a su estatura un codo?'. La preocupación es el intento humano inútil de controlar el futuro. Si el Padre cuida con esmero de las aves y de las flores, con cuánta mayor ternura cuidará de sus hijos.",
                "El afán no cambia las circunstancias de mañana, solo roba las fuerzas del presente. Confía en la provisión del Padre.",
                "¿Qué asunto específico te ha quitado la paz hoy que necesitas entregar en las manos de Dios?"),
            d(2, "Día 2: El Filtro de la Mente en Filipenses", "Filipenses 4:4-9", 50, 4, listOf(p(50, "Filipenses", 4)),
                "Por nada estéis afanosos, sino sean conocidas vuestras peticiones delante de Dios en toda oración y ruego, con acción de gracias. Y la paz de Dios, que sobrepasa todo entendimiento, guardará vuestros corazones y vuestros pensamientos en Cristo Jesús. Lleva tus cargas al altar antes de que se conviertan en angustia.",
                "La gratitud y la oración enfocada en Dios sustituyen la ansiedad por una paz que protege la mente.",
                "¿Cuántas de tus preocupaciones has transformado hoy en oraciones sinceras de gratitud?"),
            d(3, "Día 3: El Valle de Sombras no es tu Final", "Salmo 23:1-6", 19, 23, listOf(p(19, "Salmos", 23)),
                "Jehová es mi pastor; nada me faltará. El salmista no dice 'si llego a andar', sino 'aunque ande en valle de sombra de muerte, no temeré mal alguno, porque tú estarás conmigo'. El valle es un camino de paso, no un destino final. El Buen Pastor va al frente con su vara de defensa y su cayado de guía.",
                "La presencia protectora del Buen Pastor disipa el miedo en las temporadas más oscuras del camino.",
                "¿Te enfocas en las sombras del valle o en la compañía cercana del Buen Pastor a tu lado?"),
            d(4, "Día 4: Soltar las Cargas Pesadas", "Mateo 11:28-30", 40, 11, listOf(p(40, "Mateo", 11)),
                "Venid a mí todos los que estáis trabajados y cargados, y yo os haré descansar. Llevad mi yugo sobre vosotros, y aprended de mí, que soy manso y humilde de corazón; y hallaréis descanso para vuestras almas. El yugo de Cristo no oprime; es compartir la carga caminando al paso del Maestro.",
                "El descanso del alma se halla cuando dejamos de cargar el peso del orgullo y asumimos la mansedumbre de Jesús.",
                "¿Qué cargas ajenas o autoimpuestas estás cargando que Jesús te está pidiendo soltar?"),
            d(5, "Día 5: La Paz que Sobrepasa el Entendimiento", "Juan 14:25-31; Colosenses 3:12-15", 43, 14, listOf(p(43, "Juan", 14), p(51, "Colosenses", 3)),
                "La paz os dejo, mi paz os doy; yo no os la doy como el mundo la da. No se turbe vuestro corazón, ni tenga miedo. La paz del mundo depende de que no haya problemas; la paz de Cristo es un ancla inamovible que permanece firme en medio del huracán más feroz.",
                "La paz de Cristo es un don espiritual permanente que ninguna tempestad terrenal puede arrebatar.",
                "¿Descansas hoy en la promesa de que la paz de Cristo es superior a cualquier crisis exterior?")
        )
    }

    // --- LOS NOMBRES DE DIOS (7 Días) ---
    val namesOfGodPlan: List<ReadingPlanDay> by lazy {
        listOf(
            d(1, "Día 1: Elohim - El Dios Creador Soberano", "Génesis 1:1-5; Salmo 19:1-6", 1, 1, listOf(p(1, "Génesis", 1), p(19, "Salmos", 19)),
                "En el principio creó Elohim los cielos y la tierra. Elohim denota grandeza, fuerza ilimitada y soberanía absoluta. Quien diseñó los billones de galaxias del cosmos tiene el poder infinito para sostener tu vida y crear orden en medio de tu caos.",
                "Elohim es el Creador Todopoderoso cuya palabra tiene poder creador y sustentador eterno.",
                "¿Contemplas los problemas de tu vida bajo la grandeza infinita de Elohim?"),
            d(2, "Día 2: Yahweh-Jireh - El Señor Proveerá", "Génesis 22:1-14", 1, 22, listOf(p(1, "Génesis", 22)),
                "En el monte Moriah, cuando Abraham levantó el cuchillo, el ángel lo detuvo y Dios proveyó un carnero trabado en un zarzal. Abraham llamó el nombre de aquel lugar Yahweh-Jireh: en el monte de Jehová será provisto. Dios no solo provee pan; proveyó a Su propio Hijo como el Cordero sustituto.",
                "Dios ve nuestras necesidades de antemano y provee oportunamente en el monte de la obediencia.",
                "¿Confías en que Dios ya tiene preparada la salida y provisión para la prueba que atraviesas?"),
            d(3, "Día 3: Yahweh-Rafa - El Señor tu Sanador", "Éxodo 15:22-26; Salmo 103:1-5", 2, 15, listOf(p(2, "Éxodo", 15), p(19, "Salmos", 103)),
                "En las aguas amargas de Mara, el pueblo no podía beber. Dios ordenó a Moisés echar un árbol al agua, y las aguas se endulzaron. Allí Dios prometió: 'Yo soy Jehová tu Sanador'. Dios sana cuerpos enfermos, pero sobre todo sana aguas amargas del alma y sana corazones heridos.",
                "Yahweh-Rafa tiene el poder de endulzar las experiencias más amargas de tu historia y sanar tu ser.",
                "¿Has traído tus aguas amargas del pasado a los pies de Yahweh-Rafa para que las transforme?"),
            d(4, "Día 4: Yahweh-Nissi - El Señor es mi Estandarte", "Éxodo 17:8-16", 2, 17, listOf(p(2, "Éxodo", 17)),
                "Mientras Josué peleaba en el valle contra Amalec, Moisés sostenía en alto la vara de Dios en la colina. Cuando sus brazos desfallecían, Aarón y Hur los sostuvieron, y el pueblo triunfó. Moisés edificó un altar y lo llamó Yahweh-Nissi: el Señor es mi bandera de victoria.",
                "Nuestras batallas espirituales no se ganan en la carne, sino bajo el estandarte de la victoria de Dios.",
                "¿Levantas las manos en oración y dependencia reconociendo que la victoria es del Señor?"),
            d(5, "Día 5: Yahweh-Shalom - El Señor es Paz", "Jueces 6:11-24", 7, 6, listOf(p(7, "Jueces", 6)),
                "Gedeón estaba escondido en el lagar sacudiendo el trigo por miedo a los madianitas. Dios se le apareció y le dio una misión descomunal. Temeroso por haber visto al ángel de Dios cara a cara, el Señor le dijo: 'Paz a ti, no temas'. Gedeón edificó allí un altar: Yahweh-Shalom.",
                "Yahweh-Shalom trae plenitud de paz incluso cuando estamos rodeados de incertidumbre y enemigos.",
                "¿Aceptas la paz de Dios que te capacita para levantarte con valentía frente a los desafíos?"),
            d(6, "Día 6: El-Roi - El Dios que me Ve", "Génesis 16:1-14", 1, 16, listOf(p(1, "Génesis", 16)),
                "Agar, la esclava egipcia despreciada y maltratada, huyó al desierto desesperada junto a una fuente de agua. Allí el ángel de Dios la llamó por su nombre y la consoló. Agar exclamó con asombro: '¿No he visto aquí al que me ve? Por tanto llamó al pozo: Pozo del Viviente-que-me-ve' (El-Roi).",
                "Nadie es invisible para Dios; Él ve al marginado y desamparado en el rincón más solitario del desierto.",
                "¿Te consuela saber que en tus momentos de mayor soledad la mirada amorosa de El-Roi está fija en ti?"),
            d(7, "Día 7: Emanuel - Dios con Nosotros", "Isaías 7:14; Mateo 1:18-25", 23, 7, listOf(p(23, "Isaías", 7), p(40, "Mateo", 1)),
                "Todos los nombres de Dios convergen en el nombre proclamado por los profetas y cumplido en Belén: Emanuel, que traducido es: Dios con nosotros. Dios no se quedó en la distancia cósmica; se hizo carne, caminó en nuestro polvo, llevó nuestras culpas y mora para siempre en nosotros.",
                "En Jesucristo, Dios está con nosotros para siempre; nunca más caminaremos en soledad espiritual.",
                "¿Celebras cada día la bendición inaudita de tener a Dios habitando en ti por Su Espíritu?")
        )
    }

    // --- LA ARMADURA DE DIOS (7 Días) ---
    val armorOfGodPlan: List<ReadingPlanDay> by lazy {
        listOf(
            d(1, "Día 1: La Realidad de la Batalla", "Efesios 6:10-13", 49, 6, listOf(p(49, "Efesios", 6)),
                "Por lo demás, hermanos míos, fortaleceos en el Señor, y en el poder de su fuerza. Vestíos de toda la armadura de Dios, para que podáis estar firmes contra las asechanzas del diablo. Porque no tenemos lucha contra sangre y carne, sino contra principados y potestades en los lugares celestiales.",
                "La vida cristiana está inmersa en un conflicto espiritual real que requiere fortaleza divina y vigilancia.",
                "¿Reconoces el origen espiritual de las tentaciones y desánimos que enfrentas a diario?"),
            d(2, "Día 2: El Cinturón de la Verdad", "Efesios 6:14; Juan 8:31-36", 49, 6, listOf(p(49, "Efesios", 6), p(43, "Juan", 8)),
                "Estad, pues, firmes, ceñidos vuestros lomos con la verdad. El cinturón del legionario romano sostenía la túnica para poder correr sin tropezar y mantenía firme la vaina de la espada. Ceñirse con la verdad implica tanto conocer la verdad objetiva de la Palabra como vivir con integridad y transparencia sin dobleces.",
                "La verdad bíblica e interior te mantiene libre del tropiezo y del engaño del adversario.",
                "¿Hay alguna falta de transparencia o engaño en tu vida que esté aflojando tu cinturón espiritual?"),
            d(3, "Día 3: La Coraza de Justicia", "Efesios 6:14; Romanos 8:31-34", 49, 6, listOf(p(49, "Efesios", 6), p(45, "Romanos", 8)),
                "Vestíos con la coraza de justicia. La coraza protegía los órganos vitales del soldado, especialmente el corazón. Satanás es el acusador de los hermanos; si intentas proteger tu corazón con tu propia justicia humana, fallarás. La coraza que te defiende es la justicia perfecta de Cristo imputada a tu favor.",
                "La justicia de Cristo protege el corazón creyente contra los dardos de culpa y acusación demoníaca.",
                "¿Rechazas los pensamientos de condenación recordando que Cristo ya pagó tu deuda y te vistió de justicia?"),
            d(4, "Día 4: El Calzado del Evangelio de la Paz", "Efesios 6:15; Isaías 52:7", 49, 6, listOf(p(49, "Efesios", 6), p(23, "Isaías", 52)),
                "Calzados los pies con el apresto del evangelio de la paz. El calzado romano tenía tachuelas para fijarse al terreno y no resbalar ante la embestida del enemigo. Tener paz con Dios a través del Evangelio da firmeza inamovible y disposición para llevar las buenas nuevas a otros.",
                "La paz del Evangelio proporciona firmeza en medio de la contienda y celo misionero para avanzar.",
                "¿Están tus pies listos y dispuestos para compartir el Evangelio de la paz con quienes te rodean?"),
            d(5, "Día 5: El Escudo de la Fe", "Efesios 6:16; 1 Pedro 5:8-10", 49, 6, listOf(p(49, "Efesios", 6), p(60, "1 Pedro", 5)),
                "Sobre todo, tomad el escudo de la fe, con que podáis apagar todos los dardos de fuego del maligno. El escudo romano (*scutum*) era una gran puerta de madera forrada de cuero mojado que extinguía las flechas incendiarias. La fe no es una emoción; es confiar en el carácter y las promesas inmutables de Dios.",
                "La fe viva en las promesas divinas apaga las flechas de duda, temor, lujuria y desánimo del maligno.",
                "¿Levantas el escudo de las promesas de Dios cuando el enemigo dispara pensamientos de duda y miedo?"),
            d(6, "Día 6: El Yelmo de la Salvación", "Efesios 6:17; 1 Tesalonicenses 5:8", 49, 6, listOf(p(49, "Efesios", 6), p(52, "1 Tesalonicenses", 5)),
                "Y tomad el yelmo de la salvación. El casco protegía la cabeza del soldado contra golpes mortales. El campo de batalla principal del enemigo es la mente: sembrar pensamientos de derrota, confusión teológica o desesperanza. La seguridad de nuestra salvación en Cristo blinda la mente.",
                "La certeza de la salvación protege los pensamientos y mantiene la esperanza firme ante los ataques.",
                "¿Proteges tu mente llevando cautivo todo pensamiento a la obediencia a Cristo?"),
            d(7, "Día 7: La Espada del Espíritu", "Efesios 6:17-20; Hebreos 4:12", 49, 6, listOf(p(49, "Efesios", 6), p(58, "Hebreos", 4)),
                "Tomad la espada del Espíritu, que es la palabra de Dios. La única arma ofensiva de la armadura es la Palabra viva. Jesús en el desierto venció cada tentación citando con autoridad: 'Escrito está'. Conocer las Escrituras y proclamarlas con fe en la prueba hace retroceder al enemigo.",
                "La Palabra de Dios proclamada bajo la unción del Espíritu es el arma cortante que desbarata toda mentira.",
                "¿Memorizas y proclamas versículos bíblicos concretos cuando enfrentas tentaciones específicas?")
        )
    }

    // --- QUIÉN DICE DIOS QUE ERES (5 Días) ---
    val godSaysYouArePlan: List<ReadingPlanDay> by lazy {
        listOf(
            d(1, "Día 1: Una Obra Maestra Diseñada con Amor", "Salmo 139:13-18; Efesios 2:10", 19, 139, listOf(p(19, "Salmos", 139), p(49, "Efesios", 2)),
                "Tú formaste mis entrañas; me hiciste en el vientre de mi madre. Te alabaré; porque formidables, maravillosas son tus obras. No eres un accidente biológico ni una casualidad del destino. La palabra 'hechura' en Efesios 2:10 es el griego *poíema* (obra de arte). Eres el poema maestro de Dios.",
                "Tu valor fundamental descansa en que fuiste planeado y creado intencionalmente por las manos de Dios.",
                "¿Rechazas las etiquetas del mundo para abrazar el diseño admirable con que Dios te creó?"),
            d(2, "Día 2: Hijo Amado y Heredero", "Romanos 8:14-17; Gálatas 4:4-7", 45, 8, listOf(p(45, "Romanos", 8), p(48, "Gálatas", 4)),
                "No habéis recibido el espíritu de servidumbre para estar otra vez en temor, sino que habéis recibido el espíritu de adopción, por el cual clamamos: ¡Abba, Padre! Ya no eres un esclavo temeroso de Dios; eres un hijo con todos los derechos de la herencia eterna en Cristo.",
                "Por la adopción en Cristo tienes acceso íntimo y confiado al corazón del Padre celestial.",
                "¿Te relacionas con Dios desde la libertad filial de un hijo amado o desde la culpa de un esclavo?"),
            d(3, "Día 3: Perdonado y Libre de Toda Condenación", "Romanos 8:1-4; Colosenses 2:13-15", 45, 8, listOf(p(45, "Romanos", 8), p(51, "Colosenses", 2)),
                "Ahora, pues, ninguna condenación hay para los que están en Cristo Jesús. El acta de los decretos que nos era contraria fue clavada definitivamente en la cruz. Tu identidad no está determinada por tus caídas pasadas, sino por la sangre que borró tu culpa.",
                "En Cristo estás completamente perdonado, justificado y libre de juicio condenatorio.",
                "¿Te perdonas a ti mismo con la misma generosidad y fidelidad con que Dios te ha perdonado?"),
            d(4, "Día 4: Templo Sagrado de la Presencia Divina", "1 Corintios 6:19-20; 1 Pedro 2:9-10", 46, 6, listOf(p(46, "1 Corintios", 6), p(60, "1 Pedro", 2)),
                "¿O ignoráis que vuestro cuerpo es templo del Espíritu Santo, el cual está en vosotros, el cual tenéis de Dios, y que no sois vuestros? Porque habéis sido comprados por precio; glorificad, pues, a Dios en vuestro cuerpo y en vuestro espíritu. Dios eligió morar en ti.",
                "Tu cuerpo y tu vida entera son morada santa de la gloria de Dios; fuiste adquirido a precio de sangre.",
                "¿Cuidas tus ojos, tus pensamientos y tu cuerpo honrando la santidad del Huésped divino que vive en ti?"),
            d(5, "Día 5: Embajador del Reino de los Cielos", "2 Corintios 5:18-21; Mateo 5:13-16", 47, 5, listOf(p(47, "2 Corintios", 5), p(40, "Mateo", 5)),
                "Así que, somos embajadores en nombre de Cristo, como si Dios rogase por medio de nosotros. Un embajador no representa sus propias opiniones; representa la autoridad y la cultura del soberano que lo envió. Eres la luz del mundo y la sal de la tierra en tu entorno.",
                "Tienes una encomienda real: proclamar la reconciliación y hacer visible el amor de Cristo dondequiera que vayas.",
                "¿Representas con fidelidad la compasión y la verdad de tu Rey en tu círculo de influencia?")
        )
    }

    // --- EL PODER DEL PERDÓN (5 Días) ---
    val powerOfForgivenessPlan: List<ReadingPlanDay> by lazy {
        listOf(
            d(1, "Día 1: La Trampa de la Falta de Perdón", "Mateo 18:21-35", 40, 18, listOf(p(40, "Mateo", 18)),
                "Pedro le preguntó a Jesús cuántas veces debía perdonar a su hermano: '¿Hasta siete?'. Jesús respondió: 'No te digo hasta siete, sino aun hasta setenta veces siete'. La parábola del siervo despiadado revela que quien ha sido perdonado de una deuda impagable de millones no tiene derecho a estrangular a quien le debe poco.",
                "La falta de perdón nos encierra en la cárcel amarga del rencor; perdonar es abrir la celda al prisionero y descubrir que el prisionero éramos nosotros.",
                "¿A quién estás reteniendo en la cárcel de tu resentimiento por una ofensa pasada?"),
            d(2, "Día 2: Perdonar no es Justificar el Mal", "Romanos 12:17-21; 1 Pedro 2:21-25", 45, 12, listOf(p(45, "Romanos", 12), p(60, "1 Pedro", 2)),
                "No paguéis a nadie mal por mal; procurad lo bueno delante de todos los hombres. No os venguéis vosotros mismos, amados míos, sino dejad lugar a la ira de Dios; porque escrito está: Mía es la venganza, yo pagaré, dice el Señor. Perdonar no es fingir que nada pasó; es entregar el mazo de juez a Dios.",
                "Perdonar es renunciar a la venganza personal y confiar en que el Juez justo de toda la tierra hará justicia perfecta.",
                "¿Estás dispuesto a soltar el deseo de desquite y confiar en la justicia de Dios?"),
            d(3, "Día 3: El Modelo de la Cruz", "Lucas 23:32-38; Hechos 7:54-60", 42, 23, listOf(p(42, "Lucas", 23), p(44, "Hechos", 7)),
                "Colgado del madero entre dos ladrones, mientras los soldados sorteaban sus vestiduras y se burlaban de su sufrimiento, Jesús oró: 'Padre, perdónalos, porque no saben lo que hacen'. Esteban, apedreado hasta la muerte, clamó con la misma gracia: 'Señor, no les tomes en cuenta este pecado'.",
                "El perdón de Cristo se extiende a quienes no lo merecen ni lo han pedido; esa es la medida de la gracia.",
                "¿Puedes orar por la salvación y el bien de quienes te han ofendido, a imagen de tu Salvador?"),
            d(4, "Día 4: Límites Sabios y Reconciliación", "Mateo 18:15-17; Proverbios 4:23-27", 40, 18, listOf(p(40, "Mateo", 18), p(20, "Proverbios", 4)),
                "El perdón es un mandato inmediato que depende de ti; la reconciliación es un proceso que requiere arrepentimiento genuino y reconstrucción de la confianza. Jesús enseñó a confrontar con amor y establecer límites saludables cuando alguien persiste en la hostilidad sin arrepentirse.",
                "Perdonar de corazón es inmediato; restaurar la confianza requiere tiempo, verdad y frutos dignos de arrepentimiento.",
                "¿Distingues entre la obligación de perdonar de corazón y la prudencia de guardar tu corazón con límites sabios?"),
            d(5, "Día 5: La Libertad de Vivir sin Deudas", "Colosenses 3:12-14; Efesios 4:31-32", 51, 3, listOf(p(51, "Colosenses", 3), p(49, "Efesios", 4)),
                "Vestíos, pues, como escogidos de Dios, santos y amados, de entrañable misericordia, de benignidad, de humildad, de mansedumbre, de paciencia; soportándoos con amor unos a otros, y perdonándoos unos a otros si alguno tuviere queja contra otro. De la manera que Cristo os perdonó, así también hacedlo vosotros.",
                "El perdón continuo mantiene limpias las arterias del alma y nos permite caminar en la libertad de la gracia.",
                "¿Decides hoy soltar definitivamente todo resentimiento y caminar en la gloriosa libertad de Cristo?")
        )
    }

    // --- DESCANSO PARA EL ALMA (7 Días) ---
    val soulRestPlan: List<ReadingPlanDay> by lazy {
        listOf(
            d(1, "Día 1: El Principio Sagrado del Shabat", "Génesis 2:1-3; Éxodo 20:8-11", 1, 2, listOf(p(1, "Génesis", 2), p(2, "Éxodo", 20)),
                "Y acabó Dios en el día séptimo la obra que hizo; y reposó. El reposo no es un premio para cuando hayamos terminado todo el trabajo; es un mandamiento fundacional que reconoce que Dios es el sustentador y nosotros somos criaturas necesitadas de descanso.",
                "El descanso honra los límites de nuestra condición humana y testifica de nuestra confianza en la providencia de Dios.",
                "¿Respetas un ritmo semanal de descanso o vives atrapado en la trampa del activismo incesante?"),
            d(2, "Día 2: Elías bajo el Enebro: El Cansancio del Siervo", "1 Reyes 19:1-18", 11, 19, listOf(p(11, "1 Reyes", 19)),
                "Tras la gran victoria en el monte Carmelo, Elías huyó amenazado de muerte, se sentó bajo un enebro y deseó morirse. Dios no lo reprendió teológicamente; envió un ángel con pan caliente y una vasija de agua, y lo hizo dormir. A veces lo más espiritual que un creyente cansado puede hacer es comer bien y dormir.",
                "Dios comprende nuestras debilidades físicas y emocionales; Su gracia ministra al cuerpo antes de demandar servicio.",
                "¿Estás descuidando tu salud física y descanso creyendo erróneamente que el agotamiento agrada a Dios?"),
            d(3, "Día 3: Renovar Fuerzas como las Águilas", "Isaías 40:27-31", 23, 40, listOf(p(23, "Isaías", 40)),
                "Los muchachos se fatigan y se cansan, los jóvenes flaquean y caen; pero los que esperan a Jehová tendrán nuevas fuerzas; levantarán alas como las águilas; correrán, y no se cansarán; caminarán, y no se fatigarán. La fuerza divina se infunde en la quietud de la espera confiada.",
                "Esperar en Dios renueva las reservas emocionales y espirituales agotadas por las presiones de la vida.",
                "¿Dedicas tiempo a esperar en silencio delante de Dios para que Él renueve tus fuerzas?"),
            d(4, "Día 4: Aprender a Delegar con Sabiduría", "Éxodo 18:13-27", 2, 18, listOf(p(2, "Éxodo", 18)),
                "Jetro vio a Moisés atendiendo al pueblo desde la mañana hasta la tarde y le dijo: 'Desfallecerás del todo, tú, y también este pueblo que está contigo; porque el trabajo es demasiado pesado para ti; no podrás hacerlo tú solo'. Delegar no es debilidad; es mayordomía de la vida y del liderazgo.",
                "Compartir responsabilidades preserva la salud del líder y permite que otros desarrollen sus dones en el Reino.",
                "¿Qué tareas o cargas necesitas delegar o compartir hoy para no quemarte en el camino?"),
            d(5, "Día 5: La Quietud Interior en la Presencia Divina", "Salmo 46:1-11; Salmo 62:1-8", 19, 46, listOf(p(19, "Salmos", 46), p(19, "Salmos", 62)),
                "Estad quietos, y conoced que yo soy Dios; seré exaltado entre las naciones; enaltecido seré en la tierra. En hebreo, 'estad quietos' significa soltar las manos y dejar de luchar. En medio del ruido de las notificaciones y las prisas, el alma necesita aprender a callar ante Dios.",
                "El verdadero conocimiento de Dios florece cuando acallamos el ruido interior y descansamos en Su soberanía.",
                "¿Puedes apartar hoy diez minutos de completo silencio y quietud para meditar en la fidelidad de Dios?"),
            d(6, "Día 6: Alimento Genuino frente al Ruido", "Jeremías 15:16; Salmo 119:101-106", 24, 15, listOf(p(24, "Jeremías", 15), p(19, "Salmos", 119)),
                "Fueron halladas tus palabras, y yo las comí; y tu palabra me fue por gozo y por alegría de mi corazón. El bombardeo constante de información y redes sociales fatiga la mente; solo el maná de la Escritura infunde serenidad, lucidez y regocijo genuino al espíritu.",
                "Desintoxicar la mente del exceso de estímulos mundanos y alimentarla con la Palabra devuelve el gozo.",
                "¿Qué hábitos digitales necesitas moderar para abrir espacio a la paz que proporciona la Escritura?"),
            d(7, "Día 7: El Reposo de la Gracia en Hebreos", "Hebreos 4:1-11; Mateo 11:28-30", 58, 4, listOf(p(58, "Hebreos", 4), p(40, "Mateo", 11)),
                "Por tanto, queda un reposo para el pueblo de Dios. Porque el que ha entrado en su reposo, también ha reposado de sus obras, como Dios de las suyas. El reposo supremo es saber que nuestra salvación está completa y sellada por Cristo; no luchamos para ganar el favor divino, vivimos desde la victoria ganada.",
                "El reposo en la gracia cancela la angustia del rendimiento religioso y nos llena de paz perdurable.",
                "¿Vives hoy celebrando el reposo de saberte plenamente aceptado y amado por Dios en Cristo?")
        )
    }

    // --- AMOR QUE PERDURA: MATRIMONIO (7 Días) ---
    val enduringLovePlan: List<ReadingPlanDay> by lazy {
        listOf(
            d(1, "Día 1: El Pacto Fundacional del Matrimonio", "Génesis 2:18-25; Mateo 19:3-6", 1, 2, listOf(p(1, "Génesis", 2), p(40, "Mateo", 19)),
                "Por tanto, dejará el hombre a su padre y a su madre, y se unirá a su mujer, y serán una sola carne. El matrimonio no es un contrato civil revocable por conveniencia, sino un pacto sagrado sellado ante Dios. La unión conyugal refleja la fidelidad inquebrantable de Cristo con su pueblo.",
                "El pacto matrimonial se fundamenta en el compromiso incondicional y la entrega mutua.",
                "¿Cuidas y honras tu compromiso matrimonial como un pacto sagrado ante los ojos de Dios?"),
            d(2, "Día 2: Comunicación que Edifica", "Efesios 4:25-32; Proverbios 18:21", 49, 4, listOf(p(49, "Efesios", 4), p(20, "Proverbios", 18)),
                "Ninguna palabra corrompida salga de vuestra boca, sino la que sea buena para la necesaria edificación, a fin de dar gracia a los oyentes. La muerte y la vida están en poder de la lengua. En el hogar, las palabras sarcásticas o hirientes destruyen; las palabras de aprecio y gracia sanan y edifican.",
                "Las palabras en el matrimonio deben ser instrumentos de bendición, afirmación y restauración mutua.",
                "¿Son tus palabras hacia tu cónyuge o familiares una fuente de vida y edificación diaria?"),
            d(3, "Día 3: El Amor Ágape en Acción", "1 Corintios 13:1-13", 46, 13, listOf(p(46, "1 Corintios", 13)),
                "El amor es sufrido, es benigno; el amor no tiene envidia, el amor no es jactancioso, no se envanece; no hace nada indebido, no busca lo suyo, no se irrita, no guarda rencor. El amor ágape no es una mariposa en el estómago; es una decisión sacrificial de buscar el mayor bien del otro.",
                "El amor cristiano es paciente, bondadoso y desinteresado; pone las necesidades del otro en primer lugar.",
                "¿Cuál de las virtudes de 1 Corintios 13 necesitas ejercer con mayor intencionalidad en tu hogar?"),
            d(4, "Día 4: No Ponerse el Sol sobre el Enojo", "Efesios 4:26-27; Santiago 1:19-20", 49, 4, listOf(p(49, "Efesios", 4), p(59, "Santiago", 1)),
                "Airaos, pero no pequéis; no se ponga el sol sobre vuestro enojo, ni deis lugar al diablo. El enojo no resuelto que se va a dormir en el corazón fermenta en resentimiento y abre una brecha por donde el enemigo destruye la intimidad conyugal. Resolver las diferencias con humildad diaria es vital.",
                "Resolver los desacuerdos el mismo día con perdón y escucha apaga el fuego del resentimiento.",
                "¿Hay conversaciones pendientes o disculpas que necesitas ofrecer antes de que termine este día?"),
            d(5, "Día 5: Intimidad, Ternura y Pureza", "Cantares 2:8-17; 1 Corintios 7:1-5", 22, 2, listOf(p(22, "Cantares", 2), p(46, "1 Corintios", 7)),
                "El Cantar de los Cantares celebra con gozo y pureza la belleza del amor conyugal, el romance y la entrega física dentro del pacto del matrimonio. Proteger la intimidad de las distracciones, el cansancio y las trampas de la pornografía o la infidelidad emocional es esencial para blindar el hogar.",
                "La intimidad física y emocional en el matrimonio es un regalo santo de Dios que debe ser cultivado con pureza.",
                "¿Cultivas el romance, los detalles de cariño y la ternura en tu relación de pareja?"),
            d(6, "Día 6: Liderazgo Servicial y Respeto Mutuo", "Efesios 5:21-33", 49, 5, listOf(p(49, "Efesios", 5)),
                "Someteos unos a otros en el temor de Dios. El esposo está llamado a amar a su esposa como Cristo amó a la iglesia y se entregó a sí mismo por ella: un liderazgo sacrificial y protector. La esposa está llamada a respetar y apoyar a su marido. Ambos reflejan el misterio de Cristo y la Iglesia.",
                "El modelo bíblico del matrimonio elimina todo machismo o tiranía, reemplazándolo por entrega sacrificial.",
                "¿Refleja tu rol conyugal el modelo servicial y amante de Jesucristo hacia Su Iglesia?"),
            d(7, "Día 7: El Cordón de Tres Dobleces", "Eclesiastés 4:9-12; Colosenses 3:18-21", 21, 4, listOf(p(21, "Eclesiastés", 4), p(51, "Colosenses", 3)),
                "Mejores son dos que uno; porque tienen mejor paga de su trabajo. Porque si cayeren, el uno levantará a su compañero. Y si alguno prevaleciere contra uno, dos le resistirán; y cordón de tres dobleces no se rompe pronto. Cuando Cristo es el tercer hilo entrelazado en el matrimonio, el vínculo es invencible.",
                "Un matrimonio fundamentado en la oración compartida y la Palabra de Dios es un bastión inquebrantable.",
                "¿Oras regularmente junto a tu cónyuge invitando a Cristo a ser el centro de sus decisiones?")
        )
    }

    // --- MAYORDOMÍA BÍBLICA: FINANZAS DEL REINO (7 Días) ---
    val stewardshipPlan: List<ReadingPlanDay> by lazy {
        listOf(
            d(1, "Día 1: Dios es el Dueño Absoluto", "Salmo 24:1-2; 1 Crónicas 29:10-14", 19, 24, listOf(p(19, "Salmos", 24), p(13, "1 Crónicas", 29)),
                "De Jehová es la tierra y su plenitud; el mundo, y los que en él habitan. David oró: 'Pues todo es tuyo, y de lo recibido de tu mano te damos'. El punto de partida de la paz financiera es reconocer que no somos dueños de nada; somos mayordomos administrando los bienes del Rey.",
                "Reconocer la soberanía de Dios sobre todos nuestros recursos transforma la administración de cada centavo.",
                "¿Manejas tus posesiones como un dueño independiente o como un mayordomo fiel que rendirá cuentas?"),
            d(2, "Día 2: El Peligro del Amor al Dinero", "1 Timoteo 6:6-10; Mateo 6:24", 54, 6, listOf(p(54, "1 Timoteo", 6), p(40, "Mateo", 6)),
                "Porque raíz de todos los males es el amor al dinero, el cual codiciando algunos, se extraviaron de la fe, y fueron traspasados de muchos dolores. El dinero en sí es neutro; el amor y la obsesión por él desplazan a Dios y esclavizan el corazón. La gran ganancia es la piedad acompañada de contentamiento.",
                "El contentamiento en Dios destruye la codicia y nos protege de las trampas de la ambición desmedida.",
                "¿Gobierna el dinero tus emociones y decisiones o es Dios el único Señor de tu vida?"),
            d(3, "Día 3: La Trampa de las Deudas", "Proverbios 22:7; Romanos 13:8", 20, 22, listOf(p(20, "Proverbios", 22), p(45, "Romanos", 13)),
                "El rico se enseñorea de los pobres, y el que toma prestado es siervo del que presta. No debáis a nadie nada, sino el amaros unos a otros. El endeudamiento apresurado e impulsivo hipoteca el futuro y nos vuelve esclavos de las financieras. La sabiduría bíblica manda vivir dentro de los límites.",
                "La prudencia financiera exige moderación en el gasto y diligencia para salir de toda deuda que oprima.",
                "¿Tienes un plan intencional para reducir deudas y vivir con sobriedad y libertad financiera?"),
            d(4, "Día 4: El Presupuesto y la Diligencia", "Proverbios 27:23-27; Lucas 14:28-30", 20, 27, listOf(p(20, "Proverbios", 27), p(42, "Lucas", 14)),
                "Sé diligente en conocer el estado de tus ovejas, y mira con cuidado por tus rebaños; porque las riquezas no duran para siempre. Jesús elogió a quien calcula los gastos antes de construir la torre. Llevar un presupuesto detallado de ingresos y egresos no es falta de fe; es sabiduría y obediencia bíblica.",
                "Planificar los gastos con un presupuesto ordenado es honrar la provisión que Dios ha puesto en tus manos.",
                "¿Llevas un control claro y transparente de tus finanzas o gastas por impulso sin medir el impacto?"),
            d(5, "Día 5: La Alegría de Dar", "2 Corintios 9:6-15; Proverbios 11:24-25", 47, 9, listOf(p(47, "2 Corintios", 9), p(20, "Proverbios", 11)),
                "Cada uno dé como propuso en su corazón: no con tristeza, ni por necesidad, porque Dios ama al dador alegre. Y poderoso es Dios para hacer que abunde en vosotros toda gracia. Dar desinteresadamente a la obra de Dios y a los necesitados rompe el poder del egoísmo y desata bendición.",
                "La generosidad alegre es la marca del corazón que confía plenamente en la abundancia de su Padre celestial.",
                "¿Experimentas el gozo de sembrar generosamente en el Reino de Dios y en el alivio de los necesitados?"),
            d(6, "Día 6: El Ahorro Prudente de la Hormiga", "Proverbios 6:6-11; Proverbios 21:20", 20, 6, listOf(p(20, "Proverbios", 6), p(20, "Proverbios", 21)),
                "Ve a la hormiga, oh perezoso, mira sus caminos, y sé sabio; la cual no teniendo capitán, ni gobernador, ni señor, prepara en el verano su comida, y recoge en el tiempo de la siega su mantenimiento. Tesoro precioso y aceite hay en la casa del sabio; pero el hombre insensato todo lo disipa.",
                "El ahorro prudente para imprevistos e inversiones futuras es una virtud elogiada en la Escritura.",
                "¿Apartas con constancia una porción de tus ingresos para ahorro previsor como la hormiga?"),
            d(7, "Día 7: Riquezas Eternas y Tesoros en el Cielo", "Mateo 6:19-24; Lucas 12:15-21", 40, 6, listOf(p(40, "Mateo", 6), p(42, "Lucas", 12)),
                "No os hagáis tesoros en la tierra, donde la polilla y el orín corrompen, y donde ladrones minan y hurtan; sino haceos tesoros en el cielo. Porque donde esté vuestro tesoro, allí estará también vuestro corazón. El dinero bien administrado en la tierra puede convertirse en amigos eternos en el Reino.",
                "El fin supremo de la mayordomía es invertir nuestros recursos transitorios en propósitos eternos que nunca perecen.",
                "¿Dónde está la mayor parte de tu tesoro: en cuentas terrenales que se desvanecerán o en el Reino celestial?")
        )
    }

    // --- VOCACIÓN Y PROPÓSITO (7 Días) ---
    val callingPurposePlan: List<ReadingPlanDay> by lazy {
        listOf(
            d(1, "Día 1: La Gran Vocación Primaria", "Miqueas 6:6-8; Mateo 22:34-40", 33, 6, listOf(p(33, "Miqueas", 6), p(40, "Mateo", 22)),
                "Oh hombre, él te ha declarado lo que es bueno, y qué pide Jehová de ti: solamente hacer justicia, y amar misericordia, y humillarte ante tu Dios. La vocación principal de todo ser humano no es un cargo laboral, sino una relación: amar a Dios con todo el ser y al prójimo como a uno mismo.",
                "Tu vocación primaria es vivir en comunión íntima con Dios y manifestar Su justicia y amor.",
                "¿Tienes claro que tu valor y llamado fundamental están en amar a Dios por encima de tus logros profesionales?"),
            d(2, "Día 2: Dones Espirituales al Servicio Común", "Romanos 12:3-8; 1 Pedro 4:10-11", 45, 12, listOf(p(45, "Romanos", 12), p(60, "1 Pedro", 4)),
                "Cada uno según el don que ha recibido, minístrelo a los otros, como buenos administradores de la multiforme gracia de Dios. Los dones no fueron dados para inflar nuestro ego ni para exhibición personal, sino para edificar a la Iglesia y servir a la comunidad con la fuerza que Dios suministra.",
                "Tus dones espirituales son talentos prestados para ser puestos al servicio generoso de los demás.",
                "¿Qué talentos o dones te ha dado Dios que hoy podrías poner al servicio de quienes te rodean?"),
            d(3, "Día 3: Derribar las Excusas: El Llamado de Moisés", "Éxodo 3:1-12; 4:10-17", 2, 3, listOf(p(2, "Éxodo", 3), p(2, "Éxodo", 4)),
                "Moisés se escudó detrás de su torpeza de palabra: '¡Ay, Señor! nunca he sido hombre de fácil palabra'. Dios le respondió con firmeza: '¿Quién dio la boca al hombre? ¿No soy yo Jehová? Ahora pues, ve, y yo estaré con tu boca, y te enseñaré lo que hayas de hablar'. Dios no llama a los capacitados; capacita a los que llama.",
                "La suficiencia para el llamado divino no radica en tus habilidades naturales, sino en la compañía fiel de Dios.",
                "¿Qué excusas de incapacidad estás poniendo para no obedecer el llamado que Dios te hace?"),
            d(4, "Día 4: No Digas: 'Soy un Niño': La Valentía de Jeremías", "Jeremías 1:4-19", 24, 1, listOf(p(24, "Jeremías", 1)),
                "Antes que te formase en el vientre te conocí, y antes que nacieses te santifiqué, te di por profeta a las naciones. Y dije: ¡Ah! ¡ah, Señor Jehová! He aquí, no sé hablar, porque soy niño. Y me dijo Jehová: No digas: Soy un niño; porque a todo lo que te envíe irás tú, y dirás todo lo que te mande.",
                "Dios te conoció y designó tu propósito antes de tu nacimiento; tu juventud o inexperiencia no limitan Su poder.",
                "¿Permites que tu edad o tu pasado te hagan dudar del propósito con que Dios te concibió?"),
            d(5, "Día 5: Tu Trabajo como Ministerio Sagrado", "Colosenses 3:22-25; Efesios 6:5-9", 51, 3, listOf(p(51, "Colosenses", 3), p(49, "Efesios", 6)),
                "Y todo lo que hagáis, hacedlo de corazón, como para el Señor y no para los hombres; sabiendo que del Señor recibiréis la recompensa de la herencia, porque a Cristo el Señor servís. La división entre lo 'secular' y lo 'sagrado' se desvanece en Cristo: tu oficina, tu taller o tu cocina son tu púlpito de adoración.",
                "La excelencia e integridad en tu trabajo ordinario son actos de adoración y testimonio del Evangelio.",
                "¿Realizas tus labores diarias con la entrega de quien está sirviendo directamente a Jesucristo?"),
            d(6, "Día 6: Paciencia en los Tiempos de Preparación", "Habacuc 2:1-4; Gálatas 6:9-10", 35, 2, listOf(p(35, "Habacuc", 2), p(48, "Gálatas", 6)),
                "Aunque la visión tardará aún por un tiempo, mas se apresura hacia el fin, y no mentirá; aunque tardare, espéralo, porque sin duda vendrá, no tardará. Moisés pasó 40 años en el desierto cuidando ovejas; David esperó años como fugitivo antes de reinar. Dios forja el carácter en el anonimato antes de dar la plataforma.",
                "Los tiempos de aparente espera son talleres formativos indispensables donde Dios talla el carácter de sus siervos.",
                "¿Mantienes la fidelidad y la paciencia mientras Dios prepara tu corazón para lo que tiene por delante?"),
            d(7, "Día 7: La Respuesta de la Obediencia: 'Heme Aquí'", "Isaías 6:1-8; 2 Timoteo 4:5-8", 23, 6, listOf(p(23, "Isaías", 6), p(55, "2 Timoteo", 4)),
                "Después oí la voz del Señor, que decía: ¿A quién enviaré, y quién irá por nosotros? Entonces respondí yo: Heme aquí, envíame a mí. Cuando contemplamos la santidad de Dios y somos limpiados por su gracia, la única respuesta lógica de un corazón agradecido es la consagración incondicional a Su misión.",
                "El propósito de tu vida se cumple plenamente cuando te pones a disposición total del Rey: 'Heme aquí'.",
                "¿Estás dispuesto a decirle hoy a Dios con sinceridad absoluta: 'Señor, heme aquí, haz conmigo lo que tú quieras'?")
        )
    }
}
