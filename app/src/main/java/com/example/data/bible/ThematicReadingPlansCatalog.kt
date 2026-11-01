package com.example.data.bible

import com.example.data.bible.theology.CovenantsStudyPlan
import com.example.data.bible.theology.EschatologyStudyPlan
import com.example.data.bible.theology.EternalLifeStudyPlan
import com.example.data.bible.theology.FaithWorksStudyPlan
import com.example.data.bible.theology.PneumatologyStudyPlan
import com.example.data.bible.theology.RomansStudyPlan
import com.example.data.bible.theology.TabernacleStudyPlan
import com.example.data.bible.theology.TrinityStudyPlan

/**
 * Catálogo integral de planes temáticos, devocionales y estudios bíblicos profundos.
 * Diseñado con rigor exegético y estructura de 3 pilares: Exposición bíblica, Clave teológica y Reflexión personal.
 */
object ThematicReadingPlansCatalog {

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
            1 -> "Al profundizar en el contexto bíblico de $summary, contemplamos cómo las Escrituras revelan de manera progresiva y armoniosa el carácter inmutable de Dios en torno a «$cleanTheme». No nos encontramos ante máximas de sabiduría meramente humana ni ante consejos pasajeros, sino frente a la revelación eterna del Creador. Cuando examinamos este pasaje a la luz de la persona y la obra redentora de Jesucristo, comprendemos que toda promesa divina halla en Él su cumplimiento perfecto y su firmeza inconmovible."
            2 -> "Desde una perspectiva teológica e histórica, la lectura detenida de $summary nos confronta con la diferencia radical entre los criterios del mundo y los principios del Reino de los Cielos. El desarrollo bíblico de «$cleanTheme» demuestra que Dios gobierna la historia de la salvación y el cuidado personal de Sus hijos con sabiduría soberana. Esta verdad doctrinal actúa como un ancla segura para el alma cuando las circunstancias externas intentan sacudir nuestra fe."
            3 -> "Examinar con reverencia $summary nos invita a admirar la riqueza inagotable de la gracia de Dios manifestada en «$cleanTheme». Cada versículo de esta porción sagrada fue inspirado por el Espíritu Santo para derribar nuestra autosuficiencia, alumbrar nuestro entendimiento y conducirnos a una comunión viva con el Padre. Al abrazar esta enseñanza con fe sincera, nuestra mente es renovada y nuestros afectos son alineados con la verdad del Evangelio."
            else -> "La unidad orgánica de toda la Escritura resplandece con claridad al estudiar $summary bajo el enfoque de «$cleanTheme». Lo que el texto bíblico proclama aquí trasciende el momento histórico en que fue escrito y responde directamente a los anhelos y luchas más profundos del corazón humano. En Cristo Jesús, esta enseñanza deja de ser una teoría distante para convertirse en poder espiritual que transforma nuestro carácter día tras día."
        }

        val pastoralParagraph = when (dayNum % 3) {
            1 -> "Al llevar esta enseñanza al terreno práctico de nuestra jornada, es fundamental permitir que la Palabra de Dios examine nuestras motivaciones, actitudes y prioridades. No basta con un conocimiento intelectual de $summary; la verdadera madurez espiritual florece cuando la verdad bíblica gobierna nuestras palabras en el hogar, nuestra integridad en el trabajo y nuestro amor fraternal en la iglesia."
            2 -> "En nuestro caminar cristiano diario, esta verdad sobre «$cleanTheme» nos llama a una respuesta de fe activa y obediencia humilde. A menudo corremos el peligro de leer pasajes como $summary por simple rutina, sin detenernos a escuchar la voz del Buen Pastor corrigiendo nuestro rumbo y consolando nuestras cargas. Hoy es una oportunidad preciosa para abrir el corazón sin reservas y dejar que Su gracia moldee cada área de nuestra vida."
            else -> "Para que la semilla de esta Palabra dé fruto abundante y perdurable, necesitamos recibirla con un corazón manso y dispuesto. La meditación pausada en $summary nos equipa con discernimiento, paz e integridad para enfrentar los desafíos del día con una perspectiva eterna. Permite que los versículos de referencia que leerás a continuación renueven tus fuerzas y afirmen tus pasos en el camino del Señor."
        }

        val fourthParagraph = if (dayNum % 2 == 1) {
            "\n\nTe invitamos ahora a leer con calma los versículos bíblicos de referencia incluidos más abajo ($summary), meditando en la clave doctrinal y respondiendo en tu interior a la pregunta de reflexión de este día."
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
    val trinityPlan: List<ReadingPlanDay> by lazy { TrinityStudyPlan.days }

    // --- FE Y OBRAS (7 Días) ---
    val faithWorksPlan: List<ReadingPlanDay> by lazy { FaithWorksStudyPlan.days }

    // --- LA VIDA ETERNA (7 Días) ---
    val eternalLifePlan: List<ReadingPlanDay> by lazy { EternalLifeStudyPlan.days }

    // --- ESCATOLOGÍA BÍBLICA: PROFECÍAS Y APOCALIPSIS (30 Días) ---
    val eschatologyPlan: List<ReadingPlanDay> by lazy { EschatologyStudyPlan.days }

    // --- ROMANOS: FUNDAMENTOS DE GRACIA (30 Días) ---
    val romansPlan: List<ReadingPlanDay> by lazy { RomansStudyPlan.days }

    // --- 30 DÍAS CON JESÚS (30 Días) ---
    val jesus30Plan: List<ReadingPlanDay> by lazy {
        listOf(
            d(1, "Día 1: Las Bienaventuranzas y el Carácter del Reino", "Mateo 5", 40, 5, listOf(p(40, "Mateo", 5)),
                "Jesús sube al monte, se sienta como el Legislador supremo y proclama las Bienaventuranzas. El Reino de los Cielos invierte la escala de valores del mundo: los bienaventurados no son los soberbios ni los autosuficientes, sino los pobres en espíritu, los mansos y los que tienen hambre y sed de justicia.",
                "El Sermón del Monte describe el carácter interior de quien ha nacido de nuevo y brilla como sal de la tierra y luz del mundo.",
                "¿Cuál de las Bienaventuranzas de Mateo 5 refleja hoy tu mayor necesidad de crecer a la imagen de Jesús?"),
            d(2, "Día 2: La Oración Secreta y la Confianza en el Padre", "Mateo 6", 40, 6, listOf(p(40, "Mateo", 6)),
                "Jesús nos libra de la hipocresía religiosa que busca el aplauso de los hombres y nos invita a entrar en nuestro aposento secreto, donde nuestro Padre que ve en lo secreto nos recompensa. Allí aprendemos a orar el Padre Nuestro y a vencer el afán mirando las aves del cielo y los lirios del campo.",
                "Buscar primeramente el Reino de Dios y Su justicia desarma la ansiedad y alinea el corazón con la provisión del Padre.",
                "¿Practicas tu vida de oración y generosidad para ser visto por los hombres o para agradar al Padre en lo secreto?"),
            d(3, "Día 3: Cimiento sobre la Roca y Frutos Verdaderos", "Mateo 7", 40, 7, listOf(p(40, "Mateo", 7)),
                "El Maestro concluye el Sermón del Monte llamándonos a una decisión radical: entrar por la puerta estrecha y edificar nuestra casa sobre la roca. No basta con oír Sus palabras ni con decir 'Señor, Señor'; la fe genuina se demuestra haciendo la voluntad del Padre cuando golpean los ríos y los vientos.",
                "Oír las palabras de Jesús y ponerlas en práctica es el único cimiento capaz de resistir las tormentas de la vida.",
                "¿En qué área específica estás siendo hoy solo un oidor de la Palabra y necesitas pasar a la obediencia práctica?"),
            d(4, "Día 4: Autoridad de Jesús sobre la Enfermedad y el Mar", "Mateo 8", 40, 8, listOf(p(40, "Mateo", 8)),
                "Al descender del monte, Jesús respalda Su enseñanza con poder soberano: limpia al leproso con un toque de compasión, sana al siervo del centurión con una sola palabra a distancia y reprende a los vientos y al mar embravecido hasta producir una gran bonanza.",
                "No hay enfermedad tan impura ni tormenta tan violenta que escape a la voz de mando del Hijo de Dios.",
                "¿Confías en la autoridad de una sola palabra de Jesús como lo hizo el centurión en medio de tu necesidad?"),
            d(5, "Día 5: El Médico de las Almas y la Compasión por las Multitudes", "Mateo 9", 40, 9, listOf(p(40, "Mateo", 9)),
                "Ante el paralítico, Jesús va primero a la raíz más profunda: 'Ten ánimo, hijo; tus pecados te son perdonados'. Luego llama a Mateo del banco de los tributos, recuerda que los sanos no tienen necesidad de médico sino los enfermos, y siente entrañable compasión por las multitudes desamparadas.",
                "Jesús no vino a llamar a quienes se creen justos, sino a perdonar, sanar y pastorear a pecadores quebrantados.",
                "¿Miramos a las personas necesitadas con el desdén de los fariseos o con las entrañas de compasión de Jesús?"),
            d(6, "Día 6: Descanso para los Cansados en el Yugo de Cristo", "Mateo 11", 40, 11, listOf(p(40, "Mateo", 11)),
                "En medio de una generación incrédula y cargada de exigencias legalistas, Jesús abre Sus brazos y proclama la invitación más tierna de los Evangelios: 'Venid a mí todos los que estáis trabajados y cargados, y yo os haré descansar. Llevad mi yugo sobre vosotros y aprended de mí, que soy manso y humilde de corazón'.",
                "El verdadero descanso del alma no es la ausencia de servicio, sino caminar unidos al yugo manso y humilde de Jesús.",
                "¿Qué carga pesada necesitas depositar hoy a los pies de Jesús para recibir Su descanso?"),
            d(7, "Día 7: Las Parábolas del Sembrador y el Tesoro Escondido", "Mateo 13", 40, 13, listOf(p(40, "Mateo", 13)),
                "A través de las parábolas del Reino, Jesús revela cómo la misma semilla de la Palabra cae en cuatro tipos de corazón: junto al camino, en pedregales, entre espinos y en buena tierra que da fruto a ciento por uno. El Reino de los Cielos es el tesoro escondido y la perla de gran precio por la cual vale la pena dejarlo todo.",
                "La diferencia en el fruto espiritual radica en la disposición del corazón que oye, entiende y atesora la Palabra.",
                "¿Qué espinos de afán o engaño de las riquezas necesitas arrancar hoy para que la Palabra dé fruto al ciento por uno en ti?"),
            d(8, "Día 8: El Inicio del Ministerio y el Llamado de los Discípulos", "Marcos 1", 41, 1, listOf(p(41, "Marcos", 1)),
                "El Evangelio de Marcos presenta a Jesús como el Siervo en acción inmediata: bautizado en el Jordán, victorioso en el desierto y proclamando que el tiempo se ha cumplido. Junto al mar de Galilea llama a pescadores sencillos —Simón, Andrés, Jacobo y Juan— y aun antes del amanecer se retira a un lugar desierto para orar.",
                "El secreto de la autoridad pública de Jesús estaba en Su comunión íntima con el Padre de madrugada.",
                "¿Priorizas tu tiempo a solas con Dios en oración antes de enfrentar las demandas y actividades del día?"),
            d(9, "Día 9: Perdón de Pecados y Señor del Día de Reposo", "Marcos 2", 41, 2, listOf(p(41, "Marcos", 2)),
                "Cuatro amigos abren el techo para bajar a un paralítico ante Jesús. Viendo la fe de ellos, el Señor demuestra Su deidad perdonando pecados en la tierra y proclamando que el Hijo del Hombre es Señor aun del día de reposo. El vino nuevo de Su gracia no cabe en los odres viejos del legalismo.",
                "Jesús coloca la misericordia y la redención del ser humano por encima de las tradiciones religiosas rígidas.",
                "¿Eres como aquellos cuatro amigos que llevan con fe y perseverancia a otros a los pies de Jesús?"),
            d(10, "Día 10: Jesús Calma la Tempestad con Su Voz", "Marcos 4", 41, 4, listOf(p(41, "Marcos", 4)),
                "Al cruzar el mar de Galilea al anochecer, se levanta una gran tempestad de viento que anega la barca. Los discípulos claman aterrados, y Jesús, levantándose, reprende al viento y dice al mar: 'Calla, enmudece'. Y se hizo grande bonanza. Luego les pregunta: '¿Por qué estáis así amedrentados? ¿Cómo no tenéis fe?'.",
                "La presencia de Jesús en nuestra barca no evita que surjan tormentas, pero garantiza que jamás naufragaremos.",
                "¿En qué tormenta actual necesitas escuchar hoy la voz de Jesús diciendo a tu corazón: 'Calla, enmudece'?"),
            d(11, "Día 11: Poder Restaurador ante Casos Imposibles", "Marcos 5", 41, 5, listOf(p(41, "Marcos", 5)),
                "Marcos 5 reúne tres casos humanamente desahuciados: un hombre atormentado en Gadara que nadie podía sujetar, una mujer con doce años de flujo de sangre que había gastado todo en médicos, y la hija de Jairo que acababa de morir. Para Jesús no existen casos perdidos: 'No temas, cree solamente'.",
                "Donde terminan todos los recursos humanos comienza la manifestación gloriosa del poder restaurador de Cristo.",
                "¿Te acercas a Jesús con la fe perseverante que toca Su manto aun cuando todo parece perdido?"),
            d(12, "Día 12: El Buen Pastor que Multiplicó los Panes", "Marcos 6", 41, 6, listOf(p(41, "Marcos", 6)),
                "Con apenas cinco panes y dos peces puestos en Sus manos y bendecidos mirando al cielo, Jesús alimenta a cinco mil hombres hasta saciarlos y recoger doce cestas llenas. Esa misma noche, viendo a Sus discípulos fatigados remando contra el viento, viene a ellos andando sobre el mar y les dice: '¡Tened ánimo; yo soy, no temáis!'.",
                "Lo poco que ponemos en las manos de Jesús se multiplica para bendecir a multitudes más allá de nuestra escasez.",
                "¿Qué 'cinco panes y dos peces' de tus recursos o capacidades puedes entregar hoy en las manos de Jesús?"),
            d(13, "Día 13: Tomar la Cruz y Seguir al Mesías", "Marcos 8", 41, 8, listOf(p(41, "Marcos", 8)),
                "Tras la confesión de Pedro en Cesarea de Filipo ('Tú eres el Cristo'), Jesús revela el camino del Calvario y establece la condición del verdadero discipulado: 'Si alguno quiere venir en pos de mí, niéguese a sí mismo, y tome su cruz, y sígame. Porque ¿qué aprovechará al hombre si ganare todo el mundo, y perdiere su alma?'.",
                "Seguir a Cristo implica morir al egoísmo propio para hallar la verdadera vida eterna en Él.",
                "¿Qué significa de manera práctica para ti hoy negarte a ti mismo y tomar tu cruz para seguir a Jesús?"),
            d(14, "Día 14: El Hijo del Hombre Vino para Servir y Dar Su Vida", "Marcos 10", 41, 10, listOf(p(41, "Marcos", 10)),
                "Mientras los discípulos discutían sobre puestos de grandeza, Jesús definió la verdadera autoridad espiritual: 'El que quiera hacerse grande entre vosotros será vuestro servidor... Porque el Hijo del Hombre no vino para ser servido, sino para servir, y para dar su vida en rescate por muchos'.",
                "La grandeza en el Reino de Dios nunca se mide por cuántos nos sirven, sino por a cuántos servimos con amor sacrificial.",
                "¿De qué forma concreta puedes servir hoy con humildad a quienes están en tu casa o lugar de trabajo?"),
            d(15, "Día 15: Ungido para Dar Buenas Nuevas a los Quebrantados", "Lucas 4", 42, 4, listOf(p(42, "Lucas", 4)),
                "Después de vencer las tres tentaciones del diablo en el desierto con la espada de la Escritura ('Escrito está'), Jesús regresa en el poder del Espíritu a Galilea y anuncia en Nazaret el año agradable del Señor: libertad a los cautivos, vista a los ciegos y consuelo a los oprimidos.",
                "Jesús venció donde Adán cayó y vino a romper toda cadena de opresión espiritual con Su Palabra.",
                "¿Enfrentas las tentaciones diarias apoyándote firmemente en el 'Escrito está' de la Palabra de Dios?"),
            d(16, "Día 16: Perdón Abundante y Amor Agradecido", "Lucas 7", 42, 7, listOf(p(42, "Lucas", 7)),
                "En casa de Simón el fariseo, una mujer pecadora riega los pies de Jesús con sus lágrimas, los enjuga con sus cabellos y los unge con perfume. Jesús declara una ley espiritual profunda: 'Sus muchos pecados le son perdonados, porque amó mucho; mas aquel a quien se le perdona poco, poco ama'.",
                "La medida de nuestro amor y gratitud hacia Cristo es directamente proporcional a nuestra conciencia de cuánto nos ha perdonado.",
                "¿Qué tan fresca está hoy en tu memoria la gratitud por la inmensa deuda de pecado que Jesús te perdonó?"),
            d(17, "Día 17: El Buen Samaritano y Elegir la Buena Parte a Sus Pies", "Lucas 10", 42, 10, listOf(p(42, "Lucas", 10)),
                "Lucas 10 une de manera magistral el servicio compasivo y la adoración íntima: la parábola del Buen Samaritano nos enseña a detenernos para vendar las heridas del prójimo caído, mientras que la escena en casa de Marta y María nos recuerda que sentarse a los pies de Jesús para oír Su palabra es la 'buena parte' que no nos será quitada.",
                "El servicio cristiano verdadero nace de haber estado primero sentados a los pies de Jesús escuchando Su voz.",
                "¿Estás tan afanado y turbado con muchas cosas como Marta que has descuidado sentarte a los pies de Jesús como María?"),
            d(18, "Día 18: El Abrazo del Padre al Hijo Pródigo", "Lucas 15", 42, 15, listOf(p(42, "Lucas", 15)),
                "Frente a la murmuración de los religiosos, Jesús narra tres parábolas sublimes sobre el gozo del cielo cuando un pecador se arrepiente: la oveja perdida, la moneda perdida y el hijo pródigo. Cuando el hijo aún estaba lejos, su padre fue movido a misericordia, corrió, se echó sobre su cuello y le besó.",
                "Dios no espera al pecador arrepentido con el ceño fruncido, sino con los brazos abiertos de un Padre compasivo.",
                "¿Conoces la ternura del abrazo restaurador del Padre o guardas el resentimiento frío del hermano mayor?"),
            d(19, "Día 19: Perseverancia en la Oración y Humildad de Corazón", "Lucas 18", 42, 18, listOf(p(42, "Lucas", 18)),
                "Sobre la necesidad de orar siempre y no desmayar, Jesús presenta a la viuda perseverante y luego contrasta a dos hombres en el templo: el fariseo que oraba consigo mismo jactándose de sus méritos, y el publicano que, golpeándose el pecho, decía: 'Dios, sé propicio a mí, pecador'. Este descendió a su casa justificado.",
                "Dios resiste la jactancia del orgulloso, pero justifica y exalta al corazón que se acerca a Él con humildad quebrantada.",
                "¿Oras con perseverancia humilde reconociendo tu total necesidad de la misericordia divina?"),
            d(20, "Día 20: Salvación en Casa de Zaqueo: Buscar lo que se Había Perdido", "Lucas 19", 42, 19, listOf(p(42, "Lucas", 19)),
                "En Jericó, Zaqueo, jefe de los publicanos, sube a un árbol sicómoro para ver a Jesús. El Señor alza la vista, lo llama por su nombre y se hospeda en su casa. El encuentro con la gracia de Cristo produce en Zaqueo un arrepentimiento genuino con restitución generosa: 'Hoy ha venido la salvación a esta casa'.",
                "Porque el Hijo del Hombre vino a buscar y a salvar lo que se había perdido, ningún corazón está fuera del alcance de Su gracia.",
                "¿De qué maneras tangibles se nota en tus decisiones diarias que Jesús ha entrado a morar en tu casa?"),
            d(21, "Día 21: El Camino a Emaús y los Corazones que Arden", "Lucas 24", 42, 24, listOf(p(42, "Lucas", 24)),
                "En el día de la resurrección, Jesús resucitado se acerca a dos discípulos entristecidos que caminaban hacia Emaús y, comenzando desde Moisés y siguiendo por todos los profetas, les declaraba en todas las Escrituras lo que de Él decían. Al partir el pan sus ojos fueron abiertos y exclamaron: '¿No ardía nuestro corazón en nosotros?'.",
                "Cristo resucitado es la llave maestra de toda la Biblia; cuando Él nos abre las Escrituras, el corazón vuelve a arder de esperanza.",
                "¿Pides a Jesús cada vez que lees la Biblia que abra tu entendimiento para que tu corazón arda con Su presencia?"),
            d(22, "Día 22: El Verbo Hecho Carne Lleno de Gracia y de Verdad", "Juan 1", 43, 1, listOf(p(43, "Juan", 1)),
                "En el principio era el Verbo, y el Verbo era con Dios, y el Verbo era Dios. Y aquel Verbo fue hecho carne, y habitó entre nosotros (literalmente, 'puso Su tabernáculo entre nosotros'), lleno de gracia y de verdad. Juan el Bautista lo señala ante el mundo: '¡He aquí el Cordero de Dios, que quita el pecado del mundo!'.",
                "En Jesucristo, el Dios eterno e invisible se hizo cercano y visible, trayendo gracia sobre gracia a todos los que le reciben.",
                "¿Adoras a Jesús reconociendo en Él tanto Su majestad eterna de Creador como Su ternura de Cordero redentor?"),
            d(23, "Día 23: Nacer de Nuevo y el Amor Infinito de Dios", "Juan 3", 43, 3, listOf(p(43, "Juan", 3)),
                "En Su diálogo nocturno con Nicodemo, maestro de Israel, Jesús revela que la entrada al Reino de Dios requiere nacer de nuevo del Espíritu, y pronuncia la cumbre del Evangelio: 'Porque de tal manera amó Dios al mundo, que ha dado a su Hijo unigénito, para que todo aquel que en él cree, no se pierda, mas tenga vida eterna'.",
                "La salvación nace del amor inmensurable del Padre al entregar a Su Hijo para que tengamos vida eterna por la fe.",
                "¿Vives con la gratitud diaria de saber que Dios no envió a Su Hijo para condenarte, sino para salvarte?"),
            d(24, "Día 24: El Agua Viva y la Adoración en Espíritu y en Verdad", "Juan 4", 43, 4, listOf(p(43, "Juan", 4)),
                "Cansado del camino, Jesús se sienta junto al pozo de Jacob al mediodía para salir al encuentro de una mujer samaritana marginada. Sin condenarla pero mostrándole toda su verdad, le ofrece el agua que salta para vida eterna y revela que el Padre busca adoradores que le adoren en espíritu y en verdad.",
                "Jesús derriba toda barrera social y ofrece un manantial interior que sacia para siempre la sed del alma.",
                "¿Tu adoración a Dios brota en espíritu y en verdad desde un corazón saciado por el agua viva de Cristo?"),
            d(25, "Día 25: El Buen Pastor que Da Su Vida por las Ovejas", "Juan 10", 43, 10, listOf(p(43, "Juan", 10)),
                "Yo soy el buen pastor; el buen pastor su vida da por las ovejas. A diferencia del asalariado que huye cuando viene el lobo, Jesús conoce a Sus ovejas por nombre, las llama, va delante de ellas, les da vida en abundancia y promete que nadie las arrebatará jamás de Su mano ni de la mano del Padre.",
                "Nuestras vidas están eternamente seguras bajo el cuidado amoroso y omnipotente del Buen Pastor que dio Su vida por nosotros.",
                "¿Reconoces y sigues la voz del Buen Pastor por encima del ruido de los extraños en este mundo?"),
            d(26, "Día 26: Yo Soy la Resurrección y la Vida", "Juan 11", 43, 11, listOf(p(43, "Juan", 11)),
                "Frente al sepulcro de su amigo Lázaro en Betania, Jesús llora conmovido compartiendo nuestro dolor humano, y proclama a Marta: 'Yo soy la resurrección y la vida; el que cree en mí, aunque esté muerto, vivirá'. Con voz de trueno clama: '¡Lázaro, ven fuera!', demostrando Su señorío absoluto sobre la muerte.",
                "Jesús llora con nosotros en nuestras pérdidas y al mismo tiempo tiene el poder soberano para vencer a la muerte.",
                "¿Crees de todo corazón la pregunta que Jesús le hizo a Marta: '¿Crees esto?', aun frente a las situaciones más difíciles?"),
            d(27, "Día 27: El Lavatorio de los Pies y el Mandamiento Nuevo", "Juan 13", 43, 13, listOf(p(43, "Juan", 13)),
                "Sabiendo Jesús que el Padre le había dado todas las cosas en las manos, se levantó de la cena, se ciñó una toalla y lavó los pies polvorientos de sus discípulos. Luego les entregó el distintivo de la Iglesia: 'Un mandamiento nuevo os doy: Que os améis unos a otros; como yo os he amado, que también os améis unos a otros'.",
                "La marca inconfundible de los verdaderos discípulos de Jesús ante el mundo es el amor humilde que se inclina a servir.",
                "¿A quién te está llamando el Señor a 'lavar los pies' hoy mediante un acto de humildad, perdón o servicio?"),
            d(28, "Día 28: Permanecer en la Vid Verdadera para Llevar Fruto", "Juan 15", 43, 15, listOf(p(43, "Juan", 15)),
                "En camino al Getsemaní, Jesús nos entrega el secreto de la vida espiritual fructífera: 'Yo soy la vid, vosotros los pámpanos; el que permanece en mí, y yo en él, éste lleva mucho fruto; porque separados de mí nada podéis hacer'. El Padre limpia todo pámpano que lleva fruto para que lleve más fruto y Su gozo sea cumplido en nosotros.",
                "El fruto espiritual no se produce por esfuerzo aislado, sino permaneciendo en comunión constante con Cristo y Su Palabra.",
                "¿Permanece tu corazón vitalmente conectado con Jesús a lo largo del día a través de la oración y la obediencia?"),
            d(29, "Día 29: Consumado Es: El Sacrificio Supremo en el Calvario", "Juan 19", 43, 19, listOf(p(43, "Juan", 19)),
                "En el Gólgota contemplamos el amor eterno llevado hasta el extremo: el Rey coronado de espinas, entregado como el Cordero de Dios. Antes de inclinar la cabeza y entregar el espíritu, Jesús pronuncia una sola palabra triunfal en griego (Tetélestai): '¡Consumado es!' — la deuda ha sido pagada íntegramente.",
                "En la cruz del Calvario, Jesús canceló para siempre el acta de nuestra deuda; nada falta por pagar para nuestra redención.",
                "¿Descansas con gratitud reverente en el '¡Consumado es!' de la cruz de Cristo?"),
            d(30, "Día 30: La Tumba Vacía y el Encuentro con el Cristo Resucitado", "Juan 20", 43, 20, listOf(p(43, "Juan", 20)),
                "El primer día de la semana, el sepulcro está vacío. Jesús resucitado llama a María Magdalena por su nombre, se presenta en medio de los discípulos diciendo 'Paz a vosotros', muestra Sus manos y Su costado a Tomás quien exclama: '¡Señor mío, y Dios mío!', y declara bienaventurados a los que sin haber visto creyeron.",
                "La resurrección corporal de Jesucristo es el sello de nuestra fe, la fuente de nuestra paz y la garantía de nuestra vida eterna.",
                "¿Haces tuya hoy la confesión de adoración rendida de Tomás ante el Resucitado: '¡Señor mío, y Dios mío!'?")
        )
    }

    // --- 31 DÍAS EN PROVERBIOS (31 Días) ---
        val proverbs31Plan: List<ReadingPlanDay> by lazy {
        listOf(
            d(1, "Día 1: Proverbios 1 - El Principio de la Sabiduría es el Temor de Jehová", "Proverbios 1", 20, 1, listOf(p(20, "Proverbios", 1)),
                "Proverbios abre estableciendo la brújula de toda la vida: 'El principio de la sabiduría es el temor de Jehová; los insensatos desprecian la sabiduría y la enseñanza'. Escuchar la instrucción paterna y rechazar las invitaciones a la codicia protege nuestros pasos desde el inicio.",
                "Toda verdadera sabiduría comienza con una reverencia humilde y amorosa ante la autoridad de Dios.",
                "¿En qué decisión actual necesitas demostrar que el temor reverente de Jehová es el principio de tu sabiduría?"),
            d(2, "Día 2: Proverbios 2 - Buscar la Sabiduría como a Tesoros Escondidos", "Proverbios 2", 20, 2, listOf(p(20, "Proverbios", 2)),
                "Si clamares a la inteligencia y la buscares como a la plata y como a tesoros escondidos, entonces entenderás el temor de Jehová y hallarás el conocimiento de Dios; porque Jehová da la sabiduría, y de Su boca viene el conocimiento y la inteligencia.",
                "Dios concede discernimiento y protección moral a quienes buscan Su Palabra con anhelo diligente.",
                "¿Buscas hoy la sabiduría de la Palabra de Dios con el mismo empeño con que alguien busca tesoros escondidos?"),
            d(3, "Día 3: Proverbios 3 - Fíate de Jehová de Todo tu Corazón", "Proverbios 3", 20, 3, listOf(p(20, "Proverbios", 3)),
                "Fíate de Jehová de todo tu corazón, y no te apoyes en tu propia prudencia. Reconócelo en todos tus caminos, y él enderezará tus veredas. No seas sabio en tu propia opinión; teme a Jehová, y apártate del mal, y honra a Dios con tus bienes y primicias.",
                "Confiar plenamente en el Señor por encima de nuestro propio entendimiento garantiza dirección segura y paz.",
                "¿En qué área de tu vida necesitas dejar de apoyarte en tu propia prudencia y fiarte de Jehová de todo tu corazón?"),
            d(4, "Día 4: Proverbios 4 - Sobre Toda Cosa Guardada, Guarda tu Corazón", "Proverbios 4", 20, 4, listOf(p(20, "Proverbios", 4)),
                "El camino de los justos es como la luz de la aurora, que va en aumento hasta que el día es perfecto. En el centro de este capítulo resuena el mandato vital: 'Sobre toda cosa guardada, guarda tu corazón; porque de él mana la vida'.",
                "Custodiar los afectos y pensamientos del corazón es decisivo, porque de allí brotan todas nuestras decisiones.",
                "¿Qué pensamientos, lecturas o afectos necesitas filtrar hoy para guardar tu corazón, sabiendo que de él mana la vida?"),
            d(5, "Día 5: Proverbios 5 - Pureza, Fidelidad Conyugal y Huir de la Tentación", "Proverbios 5", 20, 5, listOf(p(20, "Proverbios", 5)),
                "Con realismo pastoral, Proverbios 5 advierte contra los labios lisonjeros de la inmoralidad cuyos pasos conducen a la amargura, y exhorta a beber el agua de la propia cisterna y regocijarse con la esposa de la juventud bajo la mirada omnisciente de Dios.",
                "Los caminos del hombre están ante los ojos de Jehová; la pureza y la fidelidad conyugal son fuente de bendición.",
                "¿De qué maneras prácticas estás protegiendo la pureza de tus ojos y la fidelidad de tu hogar bajo la mirada de Dios?"),
            d(6, "Día 6: Proverbios 6 - Diligencia frente a la Pereza y Lo que Dios Aborrece", "Proverbios 6", 20, 6, listOf(p(20, "Proverbios", 6)),
                "Este capítulo exhorta a librarse de fianzas imprudentes, aprender de la diligencia previsora de la hormiga y aborrecer las siete cosas que detesta Jehová: los ojos altivos, la lengua mentirosa, el corazón que maquina iniquidad y el que siembra discordia entre hermanos.",
                "La diligencia en el trabajo y la integridad que promueve la paz honran el carácter santo de Dios.",
                "¿Trabajas hoy con la diligencia previsora de la hormiga y guardas tus labios de sembrar discordia entre hermanos?"),
            d(7, "Día 7: Proverbios 7 - Guardar los Mandamientos como a la Niña de tus Ojos", "Proverbios 7", 20, 7, listOf(p(20, "Proverbios", 7)),
                "Guarda mis mandamientos y vivirás, y mi ley como las niñas de tus ojos; átalos a tus dedos, escríbelos en la tabla de tu corazón. La Palabra atesorada en el interior nos libra de caminar ingenuamente hacia las trampas de la seducción.",
                "La victoria sobre la tentación se decide mucho antes de la prueba, atesorando la Palabra en el corazón.",
                "¿Estás escribiendo los mandamientos de Dios en la tabla de tu corazón para estar firme antes de que llegue la tentación?"),
            d(8, "Día 8: Proverbios 8 - El Clamor Eterno de la Sabiduría Divina", "Proverbios 8", 20, 8, listOf(p(20, "Proverbios", 8)),
                "La Sabiduría clama en las plazas y declara que su fruto es mejor que el oro fino, que aborrece la soberbia y la arrogancia, y que desde la eternidad estuvo junto a Jehová como arquitecta, apuntando proféticamente a Cristo, Sabiduría de Dios.",
                "Hallar la sabiduría de Dios en Cristo es hallar la vida y alcanzar el favor eterno de Jehová.",
                "¿Escuchas cada día la voz de Cristo, la Sabiduría eterna de Dios, por encima del clamor de este mundo?"),
            d(9, "Día 9: Proverbios 9 - El Banquete de la Sabiduría frente a la Necedad", "Proverbios 9", 20, 9, listOf(p(20, "Proverbios", 9)),
                "Dos mesas se extienden ante el caminante: el banquete de la Sabiduría que invita a los sencillos a dejar la insensatez y vivir, y el llamado engañoso de la necedad. El que corrige al escarnecedor se acarrea afrenta, pero da al sabio y será más sabio.",
                "La señal inconfundible de un corazón sabio es que recibe la corrección con humildad y gratitud.",
                "¿Cómo reaccionas cuando alguien te corrige con la verdad: con la defensa del orgulloso o con la gratitud del sabio?"),
            d(10, "Día 10: Proverbios 10 - El Contraste entre la Integridad del Justo y el Camino Impío", "Proverbios 10", 20, 10, listOf(p(20, "Proverbios", 10)),
                "Comienzan las máximas de Salomón contrastando dos estilos de vida: la mano diligente enriquece, el que anda en integridad anda confiado, el amor cubre todas las faltas y la bendición de Jehová es la que enriquece sin añadir tristeza con ella.",
                "Caminar en integridad y refrenar los labios trae confianza serena y la bendición limpia de Dios.",
                "¿Es tu caminar íntegro y tus palabras prudentes una fuente de confianza y bendición para quienes te rodean?"),
            d(11, "Día 11: Proverbios 11 - Balanza Justa, Humildad y Generosidad que Prospera", "Proverbios 11", 20, 11, listOf(p(20, "Proverbios", 11)),
                "El peso falso es abominación a Jehová, mas la pesa cabal le agrada. Cuando viene la soberbia viene también la deshonra, mas con los humildes está la sabiduría. Hay quienes reparten, y les es añadido más; el alma generosa será prosperada.",
                "En la economía del Reino, la honestidad intachable y la generosidad desinteresada nunca quedan sin recompensa.",
                "¿Practicas la honestidad cabal en tu trabajo y la generosidad alegre que Dios promete prosperar?"),
            d(12, "Día 12: Proverbios 12 - Amor a la Corrección y Palabras que Sanan", "Proverbios 12", 20, 12, listOf(p(20, "Proverbios", 12)),
                "El que ama la instrucción ama la sabiduría, mas el que aborrece la reprensión es ignorante. Hay hombres cuyas palabras son como golpes de espada; mas la lengua de los sabios es medicina. El labio veraz permanecerá para siempre.",
                "Nuestras palabras tienen el poder de herir como espadas o de traer medicina y consuelo a quienes nos escuchan.",
                "¿Usas hoy tu lengua para dar golpes que hieren o como medicina que trae sanidad y aliento a tu casa?"),
            d(13, "Día 13: Proverbios 13 - Esperanza Cumplida y Caminar con los Sabios", "Proverbios 13", 20, 13, listOf(p(20, "Proverbios", 13)),
                "El que guarda su boca guarda su alma. La luz de los justos se alegrará, y el principio de oro para nuestras amistades: 'El que anda con sabios, sabio será; mas el que se junta con necios será quebrantado'.",
                "Elegir amistades piadosas y sabias moldea nuestro carácter y protege el futuro de nuestra familia.",
                "¿Las amistades más cercanas que cultivas te impulsan a ser más sabio y piadoso en tu caminar con Dios?"),
            d(14, "Día 14: Proverbios 14 - Edificar la Casa con Sabiduría y Temor Reverente", "Proverbios 14", 20, 14, listOf(p(20, "Proverbios", 14)),
                "La mujer sabia edifica su casa, mas la necia con sus manos la derriba. Hay camino que al hombre le parece derecho, pero su fin es camino de muerte. En el temor de Jehová está la fuerte confianza, y esperanza tendrán sus hijos.",
                "No debemos guiarnos por lo que simplemente nos parece correcto, sino edificar el hogar en el temor de Dios.",
                "¿Estás edificando activamente tu hogar con paciencia y temor de Jehová o derribándolo con reacciones impulsivas?"),
            d(15, "Día 15: Proverbios 15 - La Blanda Respuesta Quita la Ira", "Proverbios 15", 20, 15, listOf(p(20, "Proverbios", 15)),
                "La blanda respuesta quita la ira; mas la palabra áspera hace subir el furor. Los ojos de Jehová están en todo lugar, mirando a los malos y a los buenos. Mejor es lo poco con el temor de Jehová, que el gran tesoro donde hay turbación.",
                "Responder con mansedumbre desactiva los conflictos y preserva la paz del hogar mejor que cualquier riqueza.",
                "¿Ante una tensión o desacuerdo hoy, elegirás dar la blanda respuesta que apaga la ira?"),
            d(16, "Día 16: Proverbios 16 - Encomendar a Jehová las Obras y Someter los Planes", "Proverbios 16", 20, 16, listOf(p(20, "Proverbios", 16)),
                "Encomienda a Jehová tus obras, y tus pensamientos serán afirmados. Antes del quebrantamiento es la soberbia, y antes de la caída la altivez de espíritu. El corazón del hombre piensa su camino, mas Jehová endereza sus pasos.",
                "Planificar con diligencia mientras rendimos el timón a la soberanía de Dios nos guarda del orgullo y del fracaso.",
                "¿Has encomendado hoy tus obras y proyectos en las manos del Señor para que Él afirme tus pensamientos y enderece tus pasos?"),
            d(17, "Día 17: Proverbios 17 - El Amigo que Ama en Todo Tiempo y el Corazón Alegre", "Proverbios 17", 20, 17, listOf(p(20, "Proverbios", 17)),
                "Mejor es un bocado seco, y en paz, que casa de contiendas llena de provisiones. En todo tiempo ama el amigo, y es como un hermano en tiempo de angustia. El corazón alegre constituye buen remedio; mas el espíritu triste seca los huesos.",
                "La lealtad en la amistad durante la prueba y el gozo en el Señor son medicinas invaluables para el alma.",
                "¿Eres para quienes te rodean ese amigo que ama en todo tiempo y un hermano fiel en el día de la angustia?"),
            d(18, "Día 18: Proverbios 18 - Torre Fuerte es el Nombre de Jehová y el Poder de la Lengua", "Proverbios 18", 20, 18, listOf(p(20, "Proverbios", 18)),
                "Torre fuerte es el nombre de Jehová; a él correrá el justo, y será levantado. Al que responde palabra antes de oír, le es fatuidad y oprobio. La muerte y la vida están en poder de la lengua, y el que la ama comerá de sus frutos.",
                "Escuchar con paciencia antes de hablar y refugiarse en el nombre del Señor caracteriza al creyente maduro.",
                "¿Corres primero a la Torre Fuerte del nombre de Jehová cuando surge un problema, y escuchas antes de responder?"),
            d(19, "Día 19: Proverbios 19 - Integridad en la Pobreza, Paciencia y Bondad al Necesitado", "Proverbios 19", 20, 19, listOf(p(20, "Proverbios", 19)),
                "La cordura del hombre detiene su furor, y su honra es pasar por alto la ofensa. A Jehová presta el que da al pobre, y el bien que ha hecho, se lo volverá a pagar. Muchos pensamientos hay en el corazón del hombre, mas el consejo de Jehová permanecerá.",
                "Pasar por alto la ofensa con paciencia y socorrer al necesitado reflejan el corazón mismo de nuestro Padre.",
                "¿Demuestras hoy la honra del hombre entendido pasando por alto la ofensa y tendiendo la mano al necesitado?"),
            d(20, "Día 20: Proverbios 20 - Rectitud en los Negocios y Consejos Profundos del Corazón", "Proverbios 20", 20, 20, listOf(p(20, "Proverbios", 20)),
                "Justo es el que anda en su integridad; ¡dichosos sus hijos después de él! Como aguas profundas es el consejo en el corazón del hombre; mas el hombre entendido lo alcanzará. No digas: 'Yo me vengaré'; espera a Jehová, y él te salvará.",
                "El mayor legado que podemos dejar a las siguientes generaciones es una vida de integridad coherente.",
                "¿Estás construyendo en lo privado un legado de integridad del cual tus hijos y tu familia puedan ser bendecidos?"),
            d(21, "Día 21: Proverbios 21 - El Corazón del Rey en la Mano de Jehová y la Justicia", "Proverbios 21", 20, 21, listOf(p(20, "Proverbios", 21)),
                "Como los repartimientos de las aguas, así está el corazón del rey en la mano de Jehová; a todo lo que quiere lo inclina. Hacer justicia y juicio es a Jehová más agradable que sacrificio. El caballo se alista para el día de la batalla, mas Jehová es el que da la victoria.",
                "Dios gobierna soberanamente sobre toda autoridad humana y se deleita en la rectitud más que en el mero ritual.",
                "¿Descansas en que el corazón de toda autoridad está en la mano de Jehová y que de Él viene la victoria?"),
            d(22, "Día 22: Proverbios 22 - El Buen Nombre, la Crianza Piadosa y la Prudencia Financiera", "Proverbios 22", 20, 22, listOf(p(20, "Proverbios", 22)),
                "De más estima es el buen nombre que las muchas riquezas, y la buena fama más que la plata y el oro. Instruye al niño en su camino, y aun cuando fuere viejo no se apartará de él. El que toma prestado es siervo del que presta.",
                "Cuidar el testimonio íntegro, instruir a los hijos con amor y evitar las deudas edifican un futuro libre y firme.",
                "¿Valoras más conservar un buen nombre íntegro delante de Dios que acumular ganancias pasajeras?"),
            d(23, "Día 23: Proverbios 23 - Dominio Propio y No Envidiar la Prosperidad del Pecador", "Proverbios 23", 20, 23, listOf(p(20, "Proverbios", 23)),
                "No tengas envidia de los hombres malos, ni desees estar con ellos; mas tenga tu corazón el temor de Jehová todo el tiempo, porque ciertamente hay fin, y tu esperanza no será cortada. Compra la verdad, y no la vendas.",
                "La prosperidad sin Dios es un espejismo pasajero; quien persevera en el temor del Señor tiene un futuro seguro.",
                "¿Mantienes tu corazón en el temor de Jehová todo el tiempo sin envidiar la aparente prosperidad de quienes viven sin Dios?"),
            d(24, "Día 24: Proverbios 24 - Siete Veces Cae el Justo y Vuelve a Levantarse", "Proverbios 24", 20, 24, listOf(p(20, "Proverbios", 24)),
                "Con sabiduría se edificará la casa, y con prudencia se afirmará; y con ciencia se llenarán las cámaras de todo bien preciado. Si fueres flojo en el día de trabajo, tu fuerza será reducida. Porque siete veces cae el justo, y vuelve a levantarse.",
                "La marca del justo no es que jamás tropiece, sino que por la gracia de Dios siempre vuelve a levantarse.",
                "¿Si has tropezado o sentido flaqueza en el día de la prueba, tomarás hoy la mano del Señor para volver a levantarte?"),
            d(25, "Día 25: Proverbios 25 - Dominio del Espíritu como Ciudad Amurallada", "Proverbios 25", 20, 25, listOf(p(20, "Proverbios", 25)),
                "Manzana de oro con figuras de plata es la palabra dicha como conviene. Si el que te aborrece tuviere hambre, dale de comer pan, y Jehová te recompensará. Como ciudad derribada y sin muro es el hombre cuyo espíritu no tiene rienda.",
                "El dominio propio bajo el gobierno del Espíritu Santo es la muralla que protege todas las demás virtudes.",
                "¿Gobierna el Espíritu Santo tu carácter con dominio propio como una ciudad amurallada y firme?"),
            d(26, "Día 26: Proverbios 26 - Discernimiento ante la Necedad, el Chisme y la Pereza", "Proverbios 26", 20, 26, listOf(p(20, "Proverbios", 26)),
                "Proverbios 26 nos enseña a discernir cuándo no entrar en pleitos ajenos ni responder al necio conforme a su necedad, y nos advierte que sin leña se apaga el fuego, y donde no hay chismoso, cesa la contienda.",
                "Rechazar todo chisme y pleito estéril apaga el fuego de la división y mantiene limpia la comunión.",
                "¿Sabes apartarte a tiempo de discusiones estériles y apagar el fuego del chisme con el silencio prudente?"),
            d(27, "Día 27: Proverbios 27 - Hierro con Hierro se Aguza: El Valor de la Amistad Fiel", "Proverbios 27", 20, 27, listOf(p(20, "Proverbios", 27)),
                "No te jactes del día de mañana; porque no sabes qué dará de sí el día. Fieles son las heridas del que ama; pero importunos los besos del que aborrece. Hierro con hierro se aguza; y así el hombre aguza el rostro de su amigo.",
                "Un amigo verdadero nos dice la verdad con amor para ayudarnos a crecer, en lugar de adularnos.",
                "¿Agradeces y valoras al amigo fiel que te dice la verdad con amor para aguzar tu carácter como el hierro aguza al hierro?"),
            d(28, "Día 28: Proverbios 28 - El que Confiesa y se Aparta Alcanzará Misericordia", "Proverbios 28", 20, 28, listOf(p(20, "Proverbios", 28)),
                "Huye el impío sin que nadie lo persiga; mas el justo está confiado como un león. El que encubre sus pecados no prosperará; mas el que los confiesa y se aparta alcanzará misericordia. Bienaventurado el hombre que siempre teme a Dios.",
                "La libertad espiritual nunca se halla ocultando nuestras faltas, sino confesándolas y apartándonos de ellas.",
                "¿Eliges hoy el camino de la libertad confesando y apartándote de toda falta para alcanzar la misericordia de Dios?"),
            d(29, "Día 29: Proverbios 29 - Humildad que Recibe Honra y Confiar en Jehová sin Temor", "Proverbios 29", 20, 29, listOf(p(20, "Proverbios", 29)),
                "La soberbia del hombre le abate; pero al humilde de espíritu sustenta la honra. El temor del hombre pondrá lazo; mas el que confía en Jehová será exaltado. Sin profecía (revelación de la Palabra) el pueblo se desenfrena.",
                "Vencer el miedo al qué dirán confiando en el Señor nos permite vivir con libertad y fidelidad a Su Palabra.",
                "¿Confías en Jehová por encima del temor al qué dirán de los hombres para vivir con humildad y firmeza?"),
            d(30, "Día 30: Proverbios 30 - La Oración de Agur: Pureza de Palabra y Contentamiento", "Proverbios 30", 20, 30, listOf(p(20, "Proverbios", 30)),
                "Toda palabra de Dios es limpia; él es escudo a los que en él esperan. Agur eleva una oración llena de equilibrio: 'Vanidad y palabra mentirosa aparta de mí; no me des pobreza ni riquezas; manténme del pan necesario'.",
                "La Palabra de Dios es un escudo infalible, y el contentamiento piadoso nos guarda tanto de la soberbia como de la desesperación.",
                "¿Haces tuya hoy la oración de Agur pidiendo a Dios pureza de verdad y contentamiento con el pan necesario?"),
            d(31, "Día 31: Proverbios 31 - Sabiduría en el Liderazgo y el Carácter Virtuoso que Teme a Dios", "Proverbios 31", 20, 31, listOf(p(20, "Proverbios", 31)),
                "El rey Lemuel recibe instrucción para abrir su boca por el mudo y defender la causa del pobre, y el libro culmina con el poema acróstico del carácter virtuoso, cuya cumbre declara: 'Engañosa es la gracia, y vana la hermosura; la mujer que teme a Jehová, ésa será alabada'.",
                "La belleza y los logros externos pasan, pero una vida laboriosa, compasiva y temerosa de Jehová deja una huella eterna.",
                "¿Cultivas el carácter virtuoso, compasivo y temeroso de Jehová cuya alabanza permanece más allá de las apariencias?")
        )
    }

    // --- PNEUMATOLOGÍA: EL ESPÍRITU SANTO (15 Días) ---
    val pneumatologyPlan: List<ReadingPlanDay> by lazy { PneumatologyStudyPlan.days }

    // --- CRISTO EN LAS SOMBRAS DEL A.T. (15 Días) ---
    val tabernaclePlan: List<ReadingPlanDay> by lazy { TabernacleStudyPlan.days }

    // --- LA TEOLOGÍA DE LOS PACTOS (15 Días) ---
    val covenantsPlan: List<ReadingPlanDay> by lazy { CovenantsStudyPlan.days }

    // --- LIDERAZGO CON NEHEMÍAS (15 Días) ---
    val nehemiahPlan: List<ReadingPlanDay> by lazy {
        listOf(
            d(1, "Día 1: Nehemías 1 - Quebranto, Carga Espiritual e Intercesión", "Nehemías 1", 16, 1, listOf(p(16, "Nehemías", 1)),
                "Al escuchar que los muros de Jerusalén estaban derribados y sus puertas quemadas a fuego, Nehemías se sentó, lloró e hizo duelo por días, ayunando y orando delante del Dios de los cielos. Todo gran liderazgo espiritual comienza con un corazón quebrantado que intercede antes de actuar.",
                "El verdadero líder según Dios siente el dolor de su pueblo y lleva la carga al trono de la gracia antes de trazar planes.",
                "¿Qué necesidad espiritual o familiar a tu alrededor te mueve hoy a doblar tus rodillas en intercesión ferviente?"),
            d(2, "Día 2: Nehemías 2 - Valentía ante el Rey y Visión Estratégica", "Nehemías 2", 16, 2, listOf(p(16, "Nehemías", 2)),
                "Cuando el rey Artajerjes le preguntó qué pedía, Nehemías elevó una oración relámpago al Dios de los cielos y habló con valentía y prudencia. Ya en Jerusalén, inspeccionó de noche los muros derribados antes de convocar al pueblo: 'Venid, y edifiquemos el muro de Jerusalén'.",
                "La fe bíblica no está reñida con la planificación sabia: oramos como si todo dependiera de Dios y planificamos con diligencia.",
                "¿Acompañas tus oraciones con pasos estratégicos concretos y valentía cuando Dios te abre una puerta?"),
            d(3, "Día 3: Nehemías 3 - Trabajo en Equipo: Cada Familia Frente a su Puerta", "Nehemías 3", 16, 3, listOf(p(16, "Nehemías", 3)),
                "El capítulo 3 de Nehemías es un monumento a la unidad: sacerdotes, plateros, perfumistas, gobernantes y familias enteras edificaron hombro a hombro, cada uno frente a su propia casa. Una obra colosal se vuelve alcanzable cuando cada creyente asume su tramo de responsabilidad.",
                "En la obra del Señor no hay espectadores: la restauración ocurre cuando cada familia edifica el tramo que tiene enfrente.",
                "¿Estás edificando activamente la porción del muro que corresponde a tu propio hogar y ministerio?"),
            d(4, "Día 4: Nehemías 4 - Con una Mano en la Obra y otra en la Espada", "Nehemías 4", 16, 4, listOf(p(16, "Nehemías", 4)),
                "Frente a las burlas y amenazas de Sanbalat y Tobías, Nehemías oró a Dios y puso guarda día y noche. Los edificadores trabajaban con una mano en la obra y en la otra tenían la espada, recordando: 'No les temáis; acordaos del Señor, grande y temible, y pelead por vuestros hermanos'.",
                "Todo avance espiritual enfrentará oposición; la respuesta es edificar sin detenerse mientras velamos con las armas espirituales.",
                "¿Te detienen las críticas externas o continúas edificando con una mano en el trabajo y otra en la Palabra?"),
            d(5, "Día 5: Nehemías 5 - Integridad Financiera y Compasión por el Pueblo", "Nehemías 5", 16, 5, listOf(p(16, "Nehemías", 5)),
                "Cuando surgió un clamor de los pobres oprimidos por la usura de sus propios hermanos, Nehemías confrontó la injusticia y dio ejemplo renunciando durante doce años a los privilegios económicos de gobernador, por temor de Dios. El líder piadoso sirve al pueblo, no se sirve de él.",
                "La integridad financiera y la compasión por el necesitado son el sello innegociable de un liderazgo que teme a Dios.",
                "¿Refleja tu manejo de recursos y autoridad un corazón generoso que busca aliviar la carga de los demás?"),
            d(6, "Día 6: Nehemías 6 - Discernimiento ante las Trampas del Enemigo", "Nehemías 6", 16, 6, listOf(p(16, "Nehemías", 6)),
                "Al ver que no podían detener la obra por la fuerza, los adversarios intentaron distraer a Nehemías invitándolo a una cumbre en el valle de Ono, y luego con calumnias e intimidación. La respuesta de Nehemías es inmortal: 'Yo hago una gran obra, y no puedo ir'.",
                "El discernimiento espiritual nos libra de caer en distracciones que buscan apartarnos de la misión que Dios nos encomendó.",
                "¿Sabes decir 'no' con firmeza a las distracciones que intentan sacarte del propósito prioritario de Dios?"),
            d(7, "Día 7: Nehemías 7 - Organización, Mayordomía y Cuidado de las Familias", "Nehemías 7", 16, 7, listOf(p(16, "Nehemías", 7)),
                "Una vez concluido el muro físico, Nehemías designó a hombres fieles y temerosos de Dios más que muchos (como Hananías) para custodiar las puertas, y organizó el registro genealógico de las familias. Terminar un proyecto requiere establecer estructuras sanas que lo preserven.",
                "Dios busca personas fieles y temerosas de Él en lo secreto para confiarles el cuidado de Su casa.",
                "¿Cultivas un carácter íntegro y confiable en las responsabilidades cotidianas que hoy tienes a cargo?"),
            d(8, "Día 8: Nehemías 8 - Avivamiento Centrado en la Palabra de Dios", "Nehemías 8", 16, 8, listOf(p(16, "Nehemías", 8)),
                "De nada servían muros fuertes si el corazón del pueblo seguía en ruinas. Esdras abrió el libro de la Ley desde el alba hasta el mediodía, poniendo el sentido para que entendiesen la lectura. Cuando el pueblo lloró conmovido, Nehemías proclamó: 'El gozo de Jehová es vuestra fuerza'.",
                "La verdadera restauración espiritual nace cuando la Palabra de Dios es leída, explicada con claridad y obedecida con gozo.",
                "¿Es el gozo del Señor y Su Palabra la fuente real de tu fortaleza en medio de tus responsabilidades?"),
            d(9, "Día 9: Nehemías 9 - Confesión Humilde y Memoria de la Fidelidad Divina", "Nehemías 9", 16, 9, listOf(p(16, "Nehemías", 9)),
                "La lectura de la Escritura produjo una de las oraciones penitenciales más largas y hermosas de la Biblia: el pueblo repasó la historia desde la creación y Abraham, contrastando su propia rebeldía con el carácter de Dios: 'Pero tú eres Dios que perdonas, clemente y piadoso'.",
                "Recordar la historia de la fidelidad y paciencia de Dios quebranta nuestro orgullo y aviva nuestra gratitud.",
                "¿Dedicas tiempo en tus oraciones a recordar las muchas veces que la misericordia de Dios te ha sostenido?"),
            d(10, "Día 10: Nehemías 10 - Renovación del Pacto y Compromiso Comunitario", "Nehemías 10", 16, 10, listOf(p(16, "Nehemías", 10)),
                "La emoción espiritual del capítulo 8 y el arrepentimiento del capítulo 9 se cristalizaron en el capítulo 10 en decisiones concretas firmadas por escrito: caminar en la Ley de Dios, cuidar la pureza de las familias y sostener con generosidad la Casa de nuestro Dios.",
                "Un avivamiento genuino siempre pasa de las lágrimas de emoción a compromisos firmes de obediencia práctica.",
                "¿Qué compromiso concreto de fidelidad familiar y espiritual te llama el Señor a sellar hoy?"),
            d(11, "Día 11: Nehemías 11 - Disposición Voluntaria para Habitar y Servir", "Nehemías 11", 16, 11, listOf(p(16, "Nehemías", 11)),
                "Jerusalén estaba amurallada pero aún tenía pocos habitantes, y vivir en la capital implicaba riesgos y sacrificios. El pueblo bendijo a todos los varones que voluntariamente se ofrecieron para morar en Jerusalén y asumir el servicio en los puestos difíciles.",
                "El Reino de Dios avanza gracias a creyentes que se ofrecen voluntariamente donde hay mayor necesidad, no donde hay mayor comodidad.",
                "¿Estás dispuesto a servir a Dios aun cuando implique salir de tu zona de confort personal?"),
            d(12, "Día 12: Nehemías 12 - Dedicación de los Muros con Gratitud y Alabanza", "Nehemías 12", 16, 12, listOf(p(16, "Nehemías", 12)),
                "En la dedicación del muro de Jerusalén, Nehemías organizó dos grandes coros que caminaron sobre las murallas dando gracias a Dios, de modo que el gozo de Jerusalén se oyó desde lejos. Aquellos mismos muros de los que el enemigo decía que una zorra los derribaría, ahora sostenían a un pueblo adorando.",
                "Toda victoria lograda con la ayuda de Dios debe ser consagrada a Él con alabanza pública y gratitud.",
                "¿Le das a Dios toda la gloria y el agradecimiento cuando ves terminada una etapa o meta en tu vida?"),
            d(13, "Día 13: Nehemías 13 - Celo por la Pureza Espiritual y Perseverancia", "Nehemías 13", 16, 13, listOf(p(16, "Nehemías", 13)),
                "Tras una ausencia temporal, Nehemías regresó y descubrió que el sacerdote Eliasib había cedido una cámara del Templo al enemigo Tobías y se había descuidado el día de reposo. Con celo santo, Nehemías limpió la Casa de Dios y clamó: '¡Acuérdate de mí, Dios mío, para bien!'.",
                "La vigilancia espiritual nunca termina: debemos guardar el corazón para que el enemigo no ocupe cámaras dentro del templo de nuestra vida.",
                "¿Has permitido que algún hábito mundano ocupe un espacio dentro de tu corazón que pertenece solo a Dios?"),
            d(14, "Día 14: Esdras y Nehemías - Preparar el Corazón para la Ley de Jehová", "Esdras 7; Salmo 127", 15, 7, listOf(p(15, "Esdras", 7), p(19, "Salmos", 127)),
                "Junto al liderazgo constructor de Nehemías brilló el ministerio de Esdras, quien 'había preparado su corazón para inquirir la ley de Jehová y para cumplirla, y para enseñar en Israel sus estatutos y decretos' (Esd 7:10), recordando que si Jehová no edificare la casa, en vano trabajan los que la edifican.",
                "El orden bíblico del liderazgo eficaz es inalterable: primero buscar a Dios, luego vivir Su Palabra y finalmente enseñarla.",
                "¿Preparas tu corazón para vivir primero en privado aquello que deseas enseñar o liderar en público?"),
            d(15, "Día 15: Reparadores de Portillos en Nuestra Generación", "Isaías 58; 1 Corintios 3", 23, 58, listOf(p(23, "Isaías", 58), p(46, "1 Corintios", 3)),
                "La promesa profética de Isaías 58 declara sobre quienes viven en piedad genuina: 'Y los tuyos edificarán las ruinas antiguas; los cimientos de generación y generación levantarás, y serás llamado reparador de portillos, restaurador de calzadas para habitar'. Hoy nosotros somos colaboradores de Dios edificando sobre la Roca que es Cristo.",
                "Dios te ha colocado en este momento de la historia para ser un reparador de brechas y edificador de vidas en Cristo.",
                "¿Cuál será el legado espiritual que dejarás edificado para las próximas generaciones sobre el fundamento de Jesucristo?")
        )
    }

    // --- DE LA AFLICCIÓN A LA ESPERANZA (15 Días) ---
    val afflictionHopePlan: List<ReadingPlanDay> by lazy {
        listOf(
            d(1, "Día 1: Cercano Está Jehová a los Quebrantados", "Salmo 34", 19, 34, listOf(p(19, "Salmos", 34)),
                "En medio de la persecución y el temor, David descubrió una verdad que ha consolado a millones de creyentes a través de los siglos: 'Cercano está Jehová a los quebrantados de corazón; y salva a los contritos de espíritu. Muchas son las aflicciones del justo, pero de todas ellas le librará Jehová'.",
                "El dolor no aleja a Dios de nuestro lado; por el contrario, atrae Su presencia más cercana y tierna hacia el corazón quebrantado.",
                "¿Has buscado al Señor en tu quebranto sabiendo que Él oye tu clamor y te libra de todos tus temores?"),
            d(2, "Día 2: ¿Por Qué te Abates, Oh Alma Mía?", "Salmo 42; Salmo 43", 19, 42, listOf(p(19, "Salmos", 42), p(19, "Salmos", 43)),
                "Cuando las lágrimas fueron su pan de día y de noche, el salmista no se dejó arrastrar pasivamente por sus emociones negativas, sino que le predicó a su propia alma: '¿Por qué te abates, oh alma mía, y te turbas dentro de mí? Espera en Dios; porque aún he de alabarle, salvación mía y Dios mío'.",
                "En los días de abatimiento espiritual, debemos dejar de escuchar solo a nuestra tristeza y empezar a hablarle a nuestra alma con las promesas de Dios.",
                "¿Qué verdad del carácter de Dios necesitas recordarle hoy a tu propia alma frente al desánimo?"),
            d(3, "Día 3: Clamé a Dios con mi Voz en el Día de Angustia", "Salmo 77", 19, 77, listOf(p(19, "Salmos", 77)),
                "Asaf atravesó una noche tan honda que sus ojos no podían cerrarse y se preguntaba si Dios había olvidado el tener misericordia. El punto de giro llegó cuando decidió cambiar el enfoque: 'Me acordaré de las obras de JAH; sí, haré yo memoria de tus maravillas antiguas, y meditaré en todas tus obras'.",
                "Cuando el presente parece oscuro e incomprensible, recordar las fidelidades pasadas de Dios devuelve la luz a nuestra fe.",
                "¿Qué maravillas y respuestas pasadas de Dios en tu vida puedes traer hoy a la memoria para fortalecer tu esperanza?"),
            d(4, "Día 4: La Honestidad del Lamento en la Oscuridad", "Salmo 88", 19, 88, listOf(p(19, "Salmos", 88)),
                "El Salmo 88 es el salmo más sombrío del Salterio, pero comienza con una confesión extraordinaria de fe: 'Oh Jehová, Dios de mi salvación, día y noche clamo delante de ti'. El hecho de que este salmo esté en la Biblia demuestra que Dios acoge nuestro lamento sincero cuando aún no vemos la salida.",
                "La fe verdadera no finge que no duele; lleva el dolor más crudo directamente a los pies del Dios de nuestra salvación.",
                "¿Te permites derramar tu corazón con total honestidad delante de Dios en lugar de esconder tu dolor?"),
            d(5, "Día 5: El Varón de Dolores que Cargó Nuestro Sufrimiento", "Isaías 53", 23, 53, listOf(p(23, "Isaías", 53)),
                "Nuestro Dios no es un espectador inmune al sufrimiento humano. Siete siglos antes de la cruz, Isaías contempló al Mesías como 'varón de dolores, experimentado en quebranto... Ciertamente llevó él nuestras enfermedades, y sufrió nuestros dolores; y por su llaga fuimos nosotros curados'.",
                "En Jesucristo tenemos a un Salvador que conoce por experiencia propia el rechazo, la agonía y el quebranto, y que redimió nuestro dolor en la cruz.",
                "¿Cómo consuela tu corazón mirar las heridas de Jesús y saber que Él comprende cada una de tus lágrimas?"),
            d(6, "Día 6: El Padre de Misericordias y Dios de Toda Consolación", "2 Corintios 1:1-11", 47, 1, listOf(p(47, "2 Corintios", 1)),
                "Pablo bendice al Padre de misericordias y Dios de toda consolación, 'el cual nos consuela en todas nuestras tribulaciones, para que podamos también nosotros consolar a los que están en cualquier tribulación, por medio de la consolación con que nosotros somos consolados por Dios'.",
                "Dios transforma nuestras cicatrices consoladas en un ministerio de compasión para sostener a otros que hoy sufren.",
                "¿A quién alrededor tuyo podrías consolar esta semana con el mismo consuelo que tú has recibido de Dios?"),
            d(7, "Día 7: Tribulados en Todo, Mas no Desesperados", "2 Corintios 4:7-18", 47, 4, listOf(p(47, "2 Corintios", 4)),
                "Tenemos este tesoro en vasos de barro, para que la excelencia del poder sea de Dios, y no de nosotros: que estamos atribulados en todo, mas no estrechados; en apuros, mas no desesperados; derribados, pero no destruidos. Porque esta leve tribulación momentánea produce en nosotros un cada vez más excelente y eterno peso de gloria.",
                "Nuestra fragilidad como vasos de barro no es un obstáculo, sino la vitrina donde resplandece el poder sustentador de Dios.",
                "¿Miras tus pruebas presentes a la luz de las cosas que no se ven y del eterno peso de gloria venidero?"),
            d(8, "Día 8: Bástate Mi Gracia: Poder en la Debilidad", "2 Corintios 12:1-10", 47, 12, listOf(p(47, "2 Corintios", 12)),
                "Tres veces rogó Pablo al Señor que quitara de él aquel aguijón en su carne, y recibió una respuesta mejor que la liberación física: 'Bástate mi gracia; porque mi poder se perfecciona en la debilidad'. Por tanto, podemos gloriarnos en nuestras debilidades para que repose sobre nosotros el poder de Cristo.",
                "Cuando nuestras fuerzas humanas llegan a cero, la gracia suficiente de Cristo despliega toda Su plenitud en nosotros.",
                "¿Descansas en el 'Bástate mi gracia' del Señor en aquellas situaciones cuya respuesta aún espera?"),
            d(9, "Día 9: Lo que Vosotros Pensasteis para Mal, Dios para Bien", "Génesis 50:15-26", 1, 50, listOf(p(1, "Génesis", 50)),
                "Traicionado por sus hermanos, vendido como esclavo y encarcelado injustamente en Egipto, José pudo mirar hacia atrás sin rencor y declarar la cumbre de la providencia divina: 'Vosotros pensasteis mal contra mí, mas Dios lo encaminó a bien, para hacer lo que vemos hoy, para mantener en vida a mucho pueblo'.",
                "Ni siquiera la maldad o las injusticias de otros pueden frustrar el plan soberano de Dios para bendecir y preservar tu vida.",
                "¿Confías en que Dios es capaz de entretejer aun los capítulos más injustos de tu historia para un propósito de bien?"),
            d(10, "Día 10: Aunque Ande en Valle de Sombra de Muerte", "Salmo 23", 19, 23, listOf(p(19, "Salmos", 23)),
                "Nota cómo en la mitad del Salmo 23 David deja de hablar acerca del Pastor ('Él me pastoreará') para hablar directamente CON el Pastor en medio de la oscuridad: 'Aunque ande en valle de sombra de muerte, no temeré mal alguno, porque TÚ estarás conmigo; tu vara y tu cayado me infundirán aliento'.",
                "En el valle de sombra de muerte la intimidad con el Buen Pastor se vuelve más cercana y personal que en los delicados pastos.",
                "¿Sientes hoy el aliento de la vara y el cayado del Buen Pastor caminando paso a paso a tu lado?"),
            d(11, "Día 11: Todas las Cosas Ayudan a Bien", "Romanos 8:18-39", 45, 8, listOf(p(45, "Romanos", 8)),
                "Mientras la creación gime aguardando la redención final, nosotros tenemos la certeza inamovible de Romanos 8:28: 'Y sabemos que a los que aman a Dios, todas las cosas les ayudan a bien, esto es, a los que conforme a su propósito son llamados', para ser hechos conformes a la imagen de Su Hijo.",
                "El 'bien' supremo hacia el cual Dios encamina todas las cosas en nuestra vida es esculpir en nosotros la imagen de Jesucristo.",
                "¿Cómo está usando Dios tu temporada actual para formar en ti el carácter de Su Hijo amado?"),
            d(12, "Día 12: Por la Noche Durará el Lloro, al Amanecer el Gozo", "Salmo 30", 19, 30, listOf(p(19, "Salmos", 30)),
                "El llanto puede hospedarse como un peregrino durante la noche, pero no tiene permiso para quedarse a vivir para siempre: 'Por la tarde durará el lloro, y a la mañana vendrá la alegría. Has cambiado mi lamento en baile; desataste mi cilicio, y me ceñiste de alegría'.",
                "Con Cristo, ninguna noche de aflicción es eterna; el amanecer de Su restauración y gozo está garantizado.",
                "¿Esperas con fe el amanecer de la fidelidad de Dios aun cuando todavía parezca de noche?"),
            d(13, "Día 13: Cuando Pases por las Aguas, Yo Estaré Contigo", "Isaías 43:1-7", 23, 43, listOf(p(23, "Isaías", 43)),
                "Ahora, así dice Jehová, Creador tuyo y Formador tuyo: 'No temas, porque yo te redimí; te puse nombre, mío eres tú. Cuando pases por las aguas, yo estaré contigo; y si por los ríos, no te anegarán. Cuando pases por el fuego, no te quemarás, ni la llama arderá en ti'.",
                "Porque le pertenecemos a Dios por creación y por redención, ni las aguas profundas ni el fuego de la prueba podrán destruirnos.",
                "¿Escuchas hoy la voz del Señor diciéndote por tu nombre: 'No temas, porque yo te redimí, mío eres tú'?"),
            d(14, "Día 14: La Disciplina Amorosa del Padre y sus Frutos", "Hebreos 12:1-13", 58, 12, listOf(p(58, "Hebreos", 12)),
                "Puestos los ojos en Jesús, el autor y consumador de la fe, comprendemos que el Señor al que ama disciplina, como un padre amoroso que nos forma para que participemos de Su santidad. Aunque al presente ninguna disciplina parece ser causa de gozo sino de tristeza, después da fruto apacible de justicia.",
                "Las pruebas permitidas por nuestro Padre celestial nunca son castigo vengativo, sino formación paterna para nuestro bien eterno.",
                "¿Mantienes hoy tus ojos fijos en Jesús para no cansarte ni desmayar en tu ánimo durante la carrera de la fe?"),
            d(15, "Día 15: Él Enjugará Toda Lágrima de Sus Ojos", "Apocalipsis 21:1-7", 66, 21, listOf(p(66, "Apocalipsis", 21)),
                "El recorrido de la aflicción a la esperanza culmina en la promesa eterna del Trono: 'Enjugará Dios toda lágrima de los ojos de ellos; y ya no habrá muerte, ni habrá más llanto, ni clamor, ni dolor; porque las primeras cosas pasaron. Y el que estaba sentado en el trono dijo: He aquí, yo hago nuevas todas las cosas'.",
                "La última palabra sobre la historia de cada hijo de Dios no la tiene el dolor ni la muerte, sino la mano del Padre enjugando toda lágrima para siempre.",
                "¿De qué manera esta esperanza gloriosa e infalible llena hoy tu corazón de paz y fortaleza para seguir adelante?")
        )
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
            d(4, "Día 4: La Sabiduría del Consejo Bíblico", "Proverbios 11:14; 15:22; 19:20-21", 20, 11, listOf(p(20, "Proverbios", 11), p(20, "Proverbios", 15), p(20, "Proverbios", 19)),
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
