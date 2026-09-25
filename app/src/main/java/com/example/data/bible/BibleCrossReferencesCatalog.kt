package com.example.data.bible

data class CrossReferenceItem(
    val targetCitation: String,
    val targetBookId: Int,
    val targetChapter: Int,
    val targetVerse: Int,
    val note: String
)

object BibleCrossReferencesCatalog {

    // Key: "bookId_chapter_verse"
    private val referencesMap: Map<String, List<CrossReferenceItem>> = mapOf(
        // Génesis 1:1 (La Creación)
        "1_1_1" to listOf(
            CrossReferenceItem("Juan 1:1-3", 43, 1, 1, "En el principio era el Verbo; todas las cosas por él fueron hechas."),
            CrossReferenceItem("Hebreos 11:3", 58, 11, 3, "Por la fe entendemos haber sido constituido el universo por la palabra de Dios."),
            CrossReferenceItem("Salmos 102:25", 19, 102, 25, "Desde el principio tú fundaste la tierra, y los cielos son obra de tus manos."),
            CrossReferenceItem("Colosenses 1:16", 51, 1, 16, "Porque en él fueron creadas todas las cosas que están en los cielos y en la tierra.")
        ),
        // Génesis 1:26 (Creación del hombre)
        "1_1_26" to listOf(
            CrossReferenceItem("Génesis 9:6", 1, 9, 6, "A imagen de Dios es hecho el hombre."),
            CrossReferenceItem("Santiago 3:9", 59, 3, 9, "Los hombres están hechos a la semejanza de Dios."),
            CrossReferenceItem("Efesios 4:24", 49, 4, 24, "Creado según Dios en la justicia y santidad de la verdad.")
        ),
        // Génesis 3:15 (Protoevangelio - Primera profecía mesiánica)
        "1_3_15" to listOf(
            CrossReferenceItem("Gálatas 4:4", 48, 4, 4, "Dios envió a su Hijo, nacido de mujer y nacido bajo la ley."),
            CrossReferenceItem("Romanos 16:20", 45, 16, 20, "El Dios de paz aplastará en breve a Satanás bajo vuestros pies."),
            CrossReferenceItem("1 Juan 3:8", 62, 3, 8, "Para esto apareció el Hijo de Dios, para deshacer las obras del diablo.")
        ),
        // Génesis 12:3 (Pacto Abrahámico)
        "1_12_3" to listOf(
            CrossReferenceItem("Gálatas 3:8", 48, 3, 8, "En ti serán benditas todas las naciones."),
            CrossReferenceItem("Hechos 3:25", 44, 3, 25, "Vosotros sois los hijos de los profetas y del pacto."),
            CrossReferenceItem("Romanos 4:13", 45, 4, 13, "Porque no por la ley fue dada a Abraham o a su simiente la promesa.")
        ),
        // Éxodo 3:14 (YO SOY EL QUE SOY)
        "2_3_14" to listOf(
            CrossReferenceItem("Juan 8:58", 43, 8, 58, "Antes que Abraham fuese, ¡YO SOY!"),
            CrossReferenceItem("Apocalipsis 1:8", 66, 1, 8, "Yo soy el Alfa y la Omega, principio y fin, dice el Señor.")
        ),
        // Éxodo 20:3 (Los Diez Mandamientos - Primer mandamiento)
        "2_20_3" to listOf(
            CrossReferenceItem("Deuteronomio 6:4-5", 5, 6, 4, "Oye, Israel: Jehová nuestro Dios, Jehová uno es."),
            CrossReferenceItem("Mateo 22:37", 40, 22, 37, "Amarás al Señor tu Dios con todo tu corazón, y con toda tu alma.")
        ),
        // Salmos 23:1 (El Señor es mi pastor)
        "19_23_1" to listOf(
            CrossReferenceItem("Juan 10:11", 43, 10, 11, "Yo soy el buen pastor; el buen pastor su vida da por las ovejas."),
            CrossReferenceItem("Filipenses 4:19", 50, 4, 19, "Mi Dios, pues, suplirá todo lo que os falta conforme a sus riquezas en gloria."),
            CrossReferenceItem("1 Pedro 2:25", 60, 2, 25, "Porque vosotros erais como ovejas descarriadas, pero ahora habéis vuelto al Pastor.")
        ),
        // Salmos 91:1 (Bajo la sombra del Omnipotente)
        "19_91_1" to listOf(
            CrossReferenceItem("Salmos 27:5", 19, 27, 5, "Porque él me esconderá en su tabernáculo en el día del mal."),
            CrossReferenceItem("Salmos 31:20", 19, 31, 20, "Los pondrás en lo secreto de tu presencia de la conspiración del hombre.")
        ),
        // Salmos 119:105 (Lámpara es a mis pies tu palabra)
        "19_119_105" to listOf(
            CrossReferenceItem("Proverbios 6:23", 20, 6, 23, "Porque el mandamiento es lámpara, y la enseñanza es luz."),
            CrossReferenceItem("2 Pedro 1:19", 61, 1, 19, "Como a una antorcha que alumbra en lugar oscuro.")
        ),
        // Proverbios 3:5 (Fíate de Jehová de todo tu corazón)
        "20_3_5" to listOf(
            CrossReferenceItem("Jeremías 17:7", 24, 17, 7, "Bendito el varón que confía en Jehová, y cuya confianza es Jehová."),
            CrossReferenceItem("Salmos 37:5", 19, 37, 5, "Encomienda a Jehová tu camino, confía en él, y él hará.")
        ),
        // Isaías 7:14 (La virgen concebirá)
        "23_7_14" to listOf(
            CrossReferenceItem("Mateo 1:22-23", 40, 1, 22, "He aquí, una virgen concebirá y dará a luz un hijo, y llamarás su nombre Emanuel."),
            CrossReferenceItem("Lucas 1:31", 42, 1, 31, "Y concebirás en tu vientre, y darás a luz un hijo, y llamarás su nombre JESÚS.")
        ),
        // Isaías 9:6 (Porque un niño nos es nacido)
        "23_9_6" to listOf(
            CrossReferenceItem("Lucas 2:11", 42, 2, 11, "Que os ha nacido hoy, en la ciudad de David, un Salvador, que es CRISTO el Señor."),
            CrossReferenceItem("Juan 1:14", 43, 1, 14, "Y aquel Verbo fue hecho carne, y habitó entre nosotros.")
        ),
        // Isaías 53:5 (Mas él herido fue por nuestras rebeliones)
        "23_53_5" to listOf(
            CrossReferenceItem("1 Pedro 2:24", 60, 2, 24, "Quien llevó él mismo nuestros pecados en su cuerpo sobre el madero... y por cuya herida fuisteis sanados."),
            CrossReferenceItem("Romanos 5:8", 45, 5, 8, "Mas Dios muestra su amor para con nosotros, en que siendo aún pecadores, Cristo murió por nosotros."),
            CrossReferenceItem("1 Corintios 15:3", 46, 15, 3, "Cristo murió por nuestros pecados, conforme a las Escrituras.")
        ),
        // Jeremías 29:11 (Pensamientos de paz y no de mal)
        "24_29_11" to listOf(
            CrossReferenceItem("Romanos 8:28", 45, 8, 28, "A los que aman a Dios, todas las cosas les ayudan a bien."),
            CrossReferenceItem("Proverbios 23:18", 20, 23, 18, "Ciertamente hay fin, y tu esperanza no será cortada.")
        ),
        // Jeremías 31:31 (El Nuevo Pacto)
        "24_31_31" to listOf(
            CrossReferenceItem("Hebreos 8:8", 58, 8, 8, "He aquí vienen días, dice el Señor, en que estableceré con la casa de Israel un nuevo pacto."),
            CrossReferenceItem("Lucas 22:20", 42, 22, 20, "Esta copa es el nuevo pacto en mi sangre, que por vosotros se derrama.")
        ),
        // Miqueas 5:2 (Nacimiento en Belén)
        "33_5_2" to listOf(
            CrossReferenceItem("Mateo 2:1-6", 40, 2, 1, "Cuando Jesús nació en Belén de Judea en días del rey Herodes."),
            CrossReferenceItem("Juan 7:42", 43, 7, 42, "¿No dice la Escritura que del linaje de David y de la aldea de Belén vendrá el Cristo?")
        ),
        // Mateo 5:3 (Las Bienaventuranzas)
        "40_5_3" to listOf(
            CrossReferenceItem("Isaías 57:15", 23, 57, 15, "Habito con el quebrantado y humilde de espíritu."),
            CrossReferenceItem("Lucas 6:20", 42, 6, 20, "Bienaventurados vosotros los pobres, porque vuestro es el reino de Dios.")
        ),
        // Mateo 6:33 (Buscad primeramente el reino de Dios)
        "40_6_33" to listOf(
            CrossReferenceItem("Salmos 34:10", 19, 34, 10, "Los leoncillos necesitan, y tienen hambre; pero los que buscan a Jehová no tendrán falta de ningún bien."),
            CrossReferenceItem("Filipenses 4:19", 50, 4, 19, "Mi Dios, pues, suplirá todo lo que os falta conforme a sus riquezas en gloria.")
        ),
        // Mateo 28:19 (La Gran Comisión)
        "40_28_19" to listOf(
            CrossReferenceItem("Marcos 16:15", 41, 16, 15, "Id por todo el mundo y predicad el evangelio a toda criatura."),
            CrossReferenceItem("Hechos 1:8", 44, 1, 8, "Y me seréis testigos en Jerusalén, en toda Judea, en Samaria, y hasta lo último de la tierra.")
        ),
        // Juan 1:1 (En el principio era el Verbo)
        "43_1_1" to listOf(
            CrossReferenceItem("Génesis 1:1", 1, 1, 1, "En el principio creó Dios los cielos y la tierra."),
            CrossReferenceItem("1 Juan 1:1", 62, 1, 1, "Lo que era desde el principio, lo que hemos oído, lo que hemos visto."),
            CrossReferenceItem("Apocalipsis 19:13", 66, 19, 13, "Y su nombre es: EL VERBO DE DIOS.")
        ),
        // Juan 1:14 (Y el Verbo se hizo carne)
        "43_1_14" to listOf(
            CrossReferenceItem("Filipenses 2:7", 50, 2, 7, "Se despojó a sí mismo, tomando forma de siervo, hecho semejante a los hombres."),
            CrossReferenceItem("1 Timoteo 3:16", 54, 3, 16, "Dios fue manifestado en carne, justificado en el Espíritu.")
        ),
        // Juan 3:16 (Porque de tal manera amó Dios al mundo)
        "43_3_16" to listOf(
            CrossReferenceItem("Romanos 5:8", 45, 5, 8, "Mas Dios muestra su amor para con nosotros, en que siendo aún pecadores, Cristo murió por nosotros."),
            CrossReferenceItem("1 Juan 4:9", 62, 4, 9, "En esto se mostró el amor de Dios para con nosotros, en que Dios envió a su Hijo unigénito al mundo."),
            CrossReferenceItem("Efesios 2:4-5", 49, 2, 4, "Pero Dios, que es rico en misericordia, por su gran amor con que nos amó.")
        ),
        // Juan 14:6 (Yo soy el camino, la verdad y la vida)
        "43_14_6" to listOf(
            CrossReferenceItem("Hechos 4:12", 44, 4, 12, "Y en ningún otro hay salvación; porque no hay otro nombre bajo el cielo, dado a los hombres, en que podamos ser salvos."),
            CrossReferenceItem("Hebreos 10:19-20", 58, 10, 19, "Teniendo libertad para entrar en el Lugar Santísimo por la sangre de Jesucristo, por el camino nuevo y vivo.")
        ),
        // Hechos 1:8 (Poder del Espíritu Santo)
        "44_1_8" to listOf(
            CrossReferenceItem("Lucas 24:49", 42, 24, 49, "Quedaos en la ciudad de Jerusalén, hasta que seáis investidos de poder desde lo alto."),
            CrossReferenceItem("Mateo 28:19", 40, 28, 19, "Por tanto, id, y haced discípulos a todas las naciones.")
        ),
        // Hechos 4:12 (En ningún otro hay salvación)
        "44_4_12" to listOf(
            CrossReferenceItem("Juan 14:6", 43, 14, 6, "Nadie viene al Padre, sino por mí."),
            CrossReferenceItem("1 Timoteo 2:5", 54, 2, 5, "Porque hay un solo Dios, y un solo mediador entre Dios y los hombres, Jesucristo hombre.")
        ),
        // Romanos 3:23 (Por cuanto todos pecaron)
        "45_3_23" to listOf(
            CrossReferenceItem("Eclesiastés 7:20", 21, 7, 20, "Ciertamente no hay hombre justo en la tierra, que haga el bien y nunca peque."),
            CrossReferenceItem("Romanos 5:12", 45, 5, 12, "Así la muerte pasó a todos los hombres, por cuanto todos pecaron."),
            CrossReferenceItem("Gálatas 3:22", 48, 3, 22, "Mas la Escritura encerró todo bajo pecado.")
        ),
        // Romanos 6:23 (La paga del pecado es muerte)
        "45_6_23" to listOf(
            CrossReferenceItem("Génesis 2:17", 1, 2, 17, "El día que de él comieres, ciertamente morirás."),
            CrossReferenceItem("Romanos 5:21", 45, 5, 21, "Para que así como el pecado reinó para muerte, así también la gracia reine por la justicia para vida eterna.")
        ),
        // Romanos 8:28 (Todas las cosas ayudan a bien)
        "45_8_28" to listOf(
            CrossReferenceItem("Génesis 50:20", 1, 50, 20, "Vosotros pensasteis mal contra mí, mas Dios lo encaminó a bien."),
            CrossReferenceItem("Jeremías 29:11", 24, 29, 11, "Porque yo sé los pensamientos que tengo acerca de vosotros, dice Jehová."),
            CrossReferenceItem("Efesios 1:11", 49, 1, 11, "Habiendo sido predestinados conforme al propósito del que hace todas las cosas según el designio de su voluntad.")
        ),
        // Romanos 8:38-39 (Nada nos separará del amor de Dios)
        "45_8_38" to listOf(
            CrossReferenceItem("Juan 10:28", 43, 10, 28, "Y yo les doy vida eterna; y no perecerán jamás, ni nadie las arrebatará de mi mano."),
            CrossReferenceItem("Salmos 139:7-10", 19, 139, 7, "¿A dónde me iré de tu Espíritu? ¿Y a dónde huiré de tu presencia?")
        ),
        // Romanos 12:1-2 (Sacrificio vivo)
        "45_12_1" to listOf(
            CrossReferenceItem("1 Pedro 2:5", 60, 2, 5, "Para ofrecer sacrificios espirituales aceptables a Dios por medio de Jesucristo."),
            CrossReferenceItem("Efesios 4:23", 49, 4, 23, "Y renovaos en el espíritu de vuestra mente.")
        ),
        // 1 Corintios 13:13 (La fe, la esperanza y el amor)
        "46_13_13" to listOf(
            CrossReferenceItem("Gálatas 5:6", 48, 5, 6, "La fe que obra por el amor."),
            CrossReferenceItem("1 Juan 4:16", 62, 4, 16, "Dios es amor; y el que permanece en amor, permanece en Dios, y Dios en él.")
        ),
        // 2 Corintios 5:17 (Nueva criatura es)
        "47_5_17" to listOf(
            CrossReferenceItem("Efesios 2:10", 49, 2, 10, "Porque somos hechura suya, creados en Cristo Jesús para buenas obras."),
            CrossReferenceItem("Gálatas 6:15", 48, 6, 15, "En Cristo Jesús ni la circuncisión vale nada, ni la incircuncisión, sino una nueva creación.")
        ),
        // 2 Corintios 12:9 (Bástate mi gracia)
        "47_12_9" to listOf(
            CrossReferenceItem("Filipenses 4:13", 50, 4, 13, "Todo lo puedo en Cristo que me fortalece."),
            CrossReferenceItem("Isaías 40:29", 23, 40, 29, "Él da esfuerzo al cansado, y multiplica las fuerzas al que no tiene ningunas.")
        ),
        // Gálatas 2:20 (Con Cristo estoy juntamente crucificado)
        "48_2_20" to listOf(
            CrossReferenceItem("Romanos 6:6", 45, 6, 6, "Sabiendo esto, que nuestro viejo hombre fue crucificado juntamente con él."),
            CrossReferenceItem("Colosenses 3:3", 51, 3, 3, "Porque habéis muerto, y vuestra vida está escondida con Cristo en Dios.")
        ),
        // Gálatas 5:22 (El fruto del Espíritu)
        "48_5_22" to listOf(
            CrossReferenceItem("Efesios 5:9", 49, 5, 9, "Porque el fruto del Espíritu es en toda bondad, justicia y verdad."),
            CrossReferenceItem("Colosenses 3:12-14", 51, 3, 12, "Vestíos, pues, como escogidos de Dios, santos y amados, de entrañable misericordia.")
        ),
        // Efesios 2:8 (Por gracia sois salvos por medio de la fe)
        "49_2_8" to listOf(
            CrossReferenceItem("Romanos 3:24", 45, 3, 24, "Siendo justificados gratuitamente por su gracia, mediante la redención que es en Cristo Jesús."),
            CrossReferenceItem("Tito 3:5", 56, 3, 5, "Nos salvó, no por obras de justicia que nosotros hubiéramos hecho, sino por su misericordia."),
            CrossReferenceItem("2 Timoteo 1:9", 55, 1, 9, "Quien nos salvó y llamó con llamamiento santo, no conforme a nuestras obras.")
        ),
        // Filipenses 4:6 (Por nada estéis afanosos)
        "50_4_6" to listOf(
            CrossReferenceItem("1 Pedro 5:7", 60, 5, 7, "Echando toda vuestra ansiedad sobre él, porque él tiene cuidado de vosotros."),
            CrossReferenceItem("Mateo 6:25", 40, 6, 25, "Por tanto os digo: No os afanéis por vuestra vida, qué habéis de comer o qué habéis de beber.")
        ),
        // Filipenses 4:13 (Todo lo puedo en Cristo)
        "50_4_13" to listOf(
            CrossReferenceItem("2 Corintios 12:9", 47, 12, 9, "Bástate mi gracia; porque mi poder se perfecciona en la debilidad."),
            CrossReferenceItem("Juan 15:5", 43, 15, 5, "Porque separados de mí nada podéis hacer.")
        ),
        // Hebreos 11:1 (La fe es la certeza de lo que se espera)
        "58_11_1" to listOf(
            CrossReferenceItem("Romanos 8:24-25", 45, 8, 24, "Porque en esperanza fuimos salvos; pero la esperanza que se ve, no es esperanza."),
            CrossReferenceItem("2 Corintios 4:18", 47, 4, 18, "No mirando nosotros las cosas que se ven, sino las que no se ven.")
        ),
        // Hebreos 12:2 (Puestos los ojos en Jesús)
        "58_12_2" to listOf(
            CrossReferenceItem("Filipenses 2:8", 50, 2, 8, "Se humilló a sí mismo, haciéndose obediente hasta la muerte, y muerte de cruz."),
            CrossReferenceItem("Hebreos 2:9", 58, 2, 9, "Vemos a aquel que fue hecho un poco menor que los ángeles, a Jesús, coronado de gloria y de honra.")
        ),
        // Santiago 1:5 (Si alguno tiene falta de sabiduría)
        "59_1_5" to listOf(
            CrossReferenceItem("Proverbios 2:6", 20, 2, 6, "Porque Jehová da la sabiduría, y de su boca viene el conocimiento y la inteligencia."),
            CrossReferenceItem("1 Reyes 3:9", 11, 3, 9, "Da, pues, a tu siervo corazón entendido para juzgar a tu pueblo.")
        ),
        // 1 Pedro 5:7 (Echando toda vuestra ansiedad)
        "60_5_7" to listOf(
            CrossReferenceItem("Salmos 55:22", 19, 55, 22, "Echa sobre Jehová tu carga, y él te sustentará; no dejará para siempre caído al justo."),
            CrossReferenceItem("Filipenses 4:6", 50, 4, 6, "Por nada estéis afanosos, sino sean conocidas vuestras peticiones delante de Dios.")
        ),
        // 1 Juan 1:9 (Si confesamos nuestros pecados)
        "62_1_9" to listOf(
            CrossReferenceItem("Proverbios 28:13", 20, 28, 13, "El que encubre sus pecados no prosperará; mas el que los confiesa y se aparta alcanzará misericordia."),
            CrossReferenceItem("Salmos 32:5", 19, 32, 5, "Mi pecado te declaré, y no encubrí mi iniquidad.")
        ),
        // Apocalipsis 3:20 (He aquí, yo estoy a la puerta y llamo)
        "66_3_20" to listOf(
            CrossReferenceItem("Cantares 5:2", 22, 5, 2, "¡La voz de mi amado que llama a la puerta!"),
            CrossReferenceItem("Juan 14:23", 43, 14, 23, "El que me ama, mi palabra guardará; y mi Padre le amará, y vendremos a él, y haremos morada con él.")
        ),
        // Apocalipsis 21:4 (Enjugará Dios toda lágrima)
        "66_21_4" to listOf(
            CrossReferenceItem("Isaías 25:8", 23, 25, 8, "Destruirá a la muerte para siempre; y enjugará Jehová el Señor toda lágrima de todos los rostros."),
            CrossReferenceItem("1 Corintios 15:54", 46, 15, 54, "Sorbida es la muerte en victoria.")
        )
    )

    fun hasReferences(bookId: Int, chapter: Int, verse: Int): Boolean {
        return referencesMap.containsKey("${bookId}_${chapter}_$verse")
    }

    fun getReferences(bookId: Int, chapter: Int, verse: Int): List<CrossReferenceItem> {
        return referencesMap["${bookId}_${chapter}_$verse"] ?: emptyList()
    }
}
