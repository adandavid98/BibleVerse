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
        // === PENTATEUCO ===
        // Génesis 1:1
        "1_1_1" to listOf(
            CrossReferenceItem("Juan 1:1-3", 43, 1, 1, "En el principio era el Verbo; todas las cosas por él fueron hechas."),
            CrossReferenceItem("Hebreos 11:3", 58, 11, 3, "Por la fe entendemos haber sido constituido el universo por la palabra de Dios."),
            CrossReferenceItem("Salmos 102:25", 19, 102, 25, "Desde el principio tú fundaste la tierra, y los cielos son obra de tus manos."),
            CrossReferenceItem("Colosenses 1:16", 51, 1, 16, "Porque en él fueron creadas todas las cosas que están en los cielos y en la tierra.")
        ),
        // Génesis 1:26
        "1_1_26" to listOf(
            CrossReferenceItem("Génesis 9:6", 1, 9, 6, "A imagen de Dios es hecho el hombre."),
            CrossReferenceItem("Santiago 3:9", 59, 3, 9, "Los hombres están hechos a la semejanza de Dios."),
            CrossReferenceItem("Efesios 4:24", 49, 4, 24, "Creado según Dios en la justicia y santidad de la verdad.")
        ),
        // Génesis 3:15 (Protoevangelio)
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
        // Génesis 15:6
        "1_15_6" to listOf(
            CrossReferenceItem("Romanos 4:3", 45, 4, 3, "Porque ¿qué dice la Escritura? Creyó Abraham a Dios, y le fue contado por justicia."),
            CrossReferenceItem("Gálatas 3:6", 48, 3, 6, "Así Abraham creyó a Dios, y le fue contado por justicia."),
            CrossReferenceItem("Santiago 2:23", 59, 2, 23, "Y se cumplió la Escritura que dice: Abraham creyó a Dios.")
        ),
        // Génesis 50:20
        "1_50_20" to listOf(
            CrossReferenceItem("Romanos 8:28", 45, 8, 28, "A los que aman a Dios, todas las cosas les ayudan a bien."),
            CrossReferenceItem("Salmos 105:17", 19, 105, 17, "Envió un varón delante de ellos; a José, que fue vendido por siervo.")
        ),
        // Éxodo 3:14
        "2_3_14" to listOf(
            CrossReferenceItem("Juan 8:58", 43, 8, 58, "Antes que Abraham fuese, ¡YO SOY!"),
            CrossReferenceItem("Apocalipsis 1:8", 66, 1, 8, "Yo soy el Alfa y la Omega, principio y fin, dice el Señor.")
        ),
        // Éxodo 14:14
        "2_14_14" to listOf(
            CrossReferenceItem("Deuteronomio 20:4", 5, 20, 4, "Porque Jehová vuestro Dios va con vosotros, para pelear por vosotros."),
            CrossReferenceItem("2 Crónicas 20:17", 14, 20, 17, "No habrá para qué peleéis vosotros en este caso; paraos, estad quietos, y ved la salvación de Jehová.")
        ),
        // Éxodo 20:3
        "2_20_3" to listOf(
            CrossReferenceItem("Deuteronomio 6:4-5", 5, 6, 4, "Oye, Israel: Jehová nuestro Dios, Jehová uno es."),
            CrossReferenceItem("Mateo 22:37", 40, 22, 37, "Amarás al Señor tu Dios con todo tu corazón, y con toda tu alma.")
        ),
        // Levítico 19:18
        "3_19_18" to listOf(
            CrossReferenceItem("Mateo 22:39", 40, 22, 39, "Y el segundo es semejante: Amarás a tu prójimo como a ti mismo."),
            CrossReferenceItem("Romanos 13:9", 45, 13, 9, "El amor no hace mal al prójimo; así que el cumplimiento de la ley es el amor.")
        ),
        // Números 6:24-26 (Bendición sacerdotal)
        "4_6_24" to listOf(
            CrossReferenceItem("Salmos 67:1", 19, 67, 1, "Dios tenga misericordia de nosotros, y nos bendiga; haga resplandecer su rostro sobre nosotros."),
            CrossReferenceItem("2 Tesalonicenses 3:16", 53, 3, 16, "Y el mismo Señor de paz os dé siempre paz en toda manera.")
        ),
        // Deuteronomio 6:4-5 (Shemá)
        "5_6_4" to listOf(
            CrossReferenceItem("Marcos 12:29-30", 41, 12, 29, "El primer mandamiento de todos es: Oye, Israel; el Señor nuestro Dios, el Señor uno es."),
            CrossReferenceItem("1 Corintios 8:6", 46, 8, 6, "Para nosotros, sin embargo, sólo hay un Dios, el Padre.")
        ),
        // Deuteronomio 31:6
        "5_31_6" to listOf(
            CrossReferenceItem("Josué 1:5", 6, 1, 5, "No te dejaré, ni te desampararé."),
            CrossReferenceItem("Hebreos 13:5", 58, 13, 5, "No te desampararé, ni te dejaré.")
        ),

        // === LIBROS HISTÓRICOS ===
        // Josué 1:8
        "6_1_8" to listOf(
            CrossReferenceItem("Salmos 1:2", 19, 1, 2, "Sino que en la ley de Jehová está su delicia, y en su ley medita de día y de noche."),
            CrossReferenceItem("Colosenses 3:16", 51, 3, 16, "La palabra de Cristo more en abundancia en vosotros.")
        ),
        // Josué 1:9
        "6_1_9" to listOf(
            CrossReferenceItem("Isaías 41:10", 23, 41, 10, "No temas, porque yo estoy contigo; no desmayes, porque yo soy tu Dios."),
            CrossReferenceItem("Romanos 8:31", 45, 8, 31, "Si Dios es por nosotros, ¿quién contra nosotros?")
        ),
        // 1 Samuel 16:7
        "9_16_7" to listOf(
            CrossReferenceItem("Proverbios 16:2", 20, 16, 2, "Pero Jehová pesa los espíritus."),
            CrossReferenceItem("Lucas 16:15", 42, 16, 15, "Vosotros sois los que os justificáis a vosotros mismos delante de los hombres; mas Dios conoce vuestros corazones.")
        ),
        // 1 Samuel 17:45 (David contra Goliat)
        "9_17_45" to listOf(
            CrossReferenceItem("Salmos 20:7", 19, 20, 7, "Estos confían en carros, y aquéllos en caballos; mas nosotros del nombre de Jehová nuestro Dios tendremos memoria."),
            CrossReferenceItem("2 Corintios 10:4", 47, 10, 4, "Porque las armas de nuestra milicia no son carnales, sino poderosas en Dios.")
        ),
        // 2 Samuel 7:12-16 (Pacto Davídico)
        "10_7_12" to listOf(
            CrossReferenceItem("Salmos 89:3-4", 19, 89, 3, "Hice pacto con mi escogido; juré a David mi siervo, diciendo: Para siempre confirmaré tu descendencia."),
            CrossReferenceItem("Lucas 1:32-33", 42, 1, 32, "El Señor Dios le dará el trono de David su padre; y reinará sobre la casa de Jacob para siempre."),
            CrossReferenceItem("Hechos 2:30", 44, 2, 30, "Dios le había jurado con juramento que de su descendencia levantaría al Cristo para que se sentase en su trono.")
        ),
        // 1 Reyes 18:39
        "11_18_39" to listOf(
            CrossReferenceItem("Lucas 1:17", 42, 1, 17, "E irá delante de él con el espíritu y el poder de Elías."),
            CrossReferenceItem("Santiago 5:17-18", 59, 5, 17, "Elías era hombre sujeto a pasiones semejantes a las nuestras, y oró fervientemente.")
        ),
        // 2 Reyes 2:11 (Elías arrebatado)
        "12_2_11" to listOf(
            CrossReferenceItem("Génesis 5:24", 1, 5, 24, "Caminó, pues, Enoc con Dios, y desapareció, porque le llevó Dios."),
            CrossReferenceItem("Hebreos 11:5", 58, 11, 5, "Por la fe Enoc fue traspuesto para no ver muerte.")
        ),
        // 2 Reyes 6:16
        "12_6_16" to listOf(
            CrossReferenceItem("2 Crónicas 32:7-8", 14, 32, 7, "No temáis... porque más son con nosotros que con él."),
            CrossReferenceItem("Romanos 8:31", 45, 8, 31, "Si Dios es por nosotros, ¿quién contra nosotros?")
        ),

        // === 2 CRÓNICAS 22 (Paralelos con 2 Reyes 8, 9 y 11) ===
        "14_22_1" to listOf(
            CrossReferenceItem("2 Reyes 8:25-29", 12, 8, 25, "En el año doce de Joram hijo de Acab rey de Israel, comenzó a reinar Ocozías hijo de Joram rey de Judá."),
            CrossReferenceItem("2 Crónicas 21:17", 14, 21, 17, "Y no le quedó más hijo sino Joacaz (Ocozías), el menor de sus hijos.")
        ),
        "14_22_2" to listOf(
            CrossReferenceItem("2 Reyes 8:26", 12, 8, 26, "De veintidós años era Ocozías cuando comenzó a reinar... y el nombre de su madre fue Atalía, hija de Omri rey de Israel.")
        ),
        "14_22_3" to listOf(
            CrossReferenceItem("2 Reyes 8:27", 12, 8, 27, "Y anduvo en el camino de la casa de Acab, e hizo lo malo ante los ojos de Jehová, como la casa de Acab.")
        ),
        "14_22_5" to listOf(
            CrossReferenceItem("2 Reyes 8:28", 12, 8, 28, "Y fue con Joram hijo de Acab a la guerra contra Hazael rey de Siria en Ramot de Galaad; y los sirios hirieron a Joram.")
        ),
        "14_22_8" to listOf(
            CrossReferenceItem("2 Reyes 9:24", 12, 9, 24, "Y Jehú entesó su arco e hirió a Joram entre las espaldas; y la saeta salió por su corazón."),
            CrossReferenceItem("2 Reyes 10:13-14", 12, 10, 13, "Halló Jehú a los hermanos de Ocozías rey de Judá... y los degollaron junto al pozo de la casa de esquileo.")
        ),
        "14_22_9" to listOf(
            CrossReferenceItem("2 Reyes 9:27-28", 12, 9, 27, "Viendo esto Ocozías rey de Judá, huyó por el camino de la casa del huerto. Y lo hirieron en su carro en la subida de Gur.")
        ),
        "14_22_10" to listOf(
            CrossReferenceItem("2 Reyes 11:1", 12, 11, 1, "Viendo Atalía madre de Ocozías que su hijo era muerto, se levantó y destruyó toda la descendencia real de Judá.")
        ),
        "14_22_11" to listOf(
            CrossReferenceItem("2 Reyes 11:2", 12, 11, 2, "Pero Josaba... tomó a Joás hijo de Ocozías y lo hurtó de entre los hijos del rey a quienes mataban, y lo ocultó con su nodriza en el templo.")
        ),
        "14_22_12" to listOf(
            CrossReferenceItem("2 Reyes 11:3", 12, 11, 3, "Y estuvo con ella escondido en la casa de Jehová seis años; y Atalía reinaba sobre el país.")
        ),

        // 2 Crónicas 7:14
        "14_7_14" to listOf(
            CrossReferenceItem("1 Reyes 9:3", 11, 9, 3, "Yo he oído tu oración y tu ruego que has hecho en mi presencia."),
            CrossReferenceItem("Santiago 4:10", 59, 4, 10, "Humillaos delante del Señor, y él os exaltará."),
            CrossReferenceItem("1 Juan 1:9", 62, 1, 9, "Si confesamos nuestros pecados, él es fiel y justo para perdonar nuestros pecados.")
        ),
        // 2 Crónicas 20:15
        "14_20_15" to listOf(
            CrossReferenceItem("Éxodo 14:13-14", 2, 14, 13, "No temáis; estad firmes, y ved la salvación que Jehová hará hoy con vosotros."),
            CrossReferenceItem("1 Samuel 17:47", 9, 17, 47, "Porque de Jehová es la batalla, y él os entregará en nuestras manos.")
        ),
        // Nehemías 8:10
        "16_8_10" to listOf(
            CrossReferenceItem("Filipenses 4:4", 50, 4, 4, "Regocijaos en el Señor siempre. Otra vez digo: ¡Regocijaos!"),
            CrossReferenceItem("Salmos 28:7", 19, 28, 7, "Jehová es mi fortaleza y mi escudo; en él confió mi corazón, y fui ayudado.")
        ),
        // Ester 4:14
        "17_4_14" to listOf(
            CrossReferenceItem("Romanos 8:28", 45, 8, 28, "Sabemos que a los que aman a Dios, todas las cosas les ayudan a bien."),
            CrossReferenceItem("Génesis 45:5", 1, 45, 5, "Para preservación de vida me envió Dios delante de vosotros.")
        ),

        // === POÉTICOS Y SAPIENCIALES ===
        // Job 19:25
        "18_19_25" to listOf(
            CrossReferenceItem("Juan 11:25", 43, 11, 25, "Yo soy la resurrección y la vida; el que cree en mí, aunque esté muerto, vivirá."),
            CrossReferenceItem("1 Corintios 15:20", 46, 15, 20, "Mas ahora Cristo ha resucitado de los muertos; primicias de los que durmieron es hecho.")
        ),
        // Salmos 1:1-2
        "19_1_1" to listOf(
            CrossReferenceItem("Jeremías 17:7-8", 24, 17, 7, "Bendito el varón que confía en Jehová, y cuya confianza es Jehová. Porque será como el árbol plantado junto a las aguas."),
            CrossReferenceItem("Josué 1:8", 6, 1, 8, "Sino que de día y de noche meditarás en él.")
        ),
        // Salmos 23:1
        "19_23_1" to listOf(
            CrossReferenceItem("Juan 10:11", 43, 10, 11, "Yo soy el buen pastor; el buen pastor su vida da por las ovejas."),
            CrossReferenceItem("Filipenses 4:19", 50, 4, 19, "Mi Dios, pues, suplirá todo lo que os falta conforme a sus riquezas en gloria."),
            CrossReferenceItem("1 Pedro 2:25", 60, 2, 25, "Porque vosotros erais como ovejas descarriadas, pero ahora habéis vuelto al Pastor.")
        ),
        // Salmos 27:1
        "19_27_1" to listOf(
            CrossReferenceItem("Salmos 118:6", 19, 118, 6, "Jehová está conmigo; no temeré lo que me pueda hacer el hombre."),
            CrossReferenceItem("Romanos 8:31", 45, 8, 31, "Si Dios es por nosotros, ¿quién contra nosotros?")
        ),
        // Salmos 37:4
        "19_37_4" to listOf(
            CrossReferenceItem("Salmos 84:11", 19, 84, 11, "No quitará el bien a los que andan en integridad."),
            CrossReferenceItem("Mateo 6:33", 40, 6, 33, "Buscad primeramente el reino de Dios y su justicia, y todas estas cosas os serán añadidas.")
        ),
        // Salmos 46:1
        "19_46_1" to listOf(
            CrossReferenceItem("Salmos 91:2", 19, 91, 2, "Diré yo a Jehová: Esperanza mía, y castillo mío; mi Dios, en quien confiaré."),
            CrossReferenceItem("Hebreos 4:16", 58, 4, 16, "Para alcanzar misericordia y hallar gracia para el oportuno socorro.")
        ),
        // Salmos 91:1
        "19_91_1" to listOf(
            CrossReferenceItem("Salmos 27:5", 19, 27, 5, "Porque él me esconderá en su tabernáculo en el día del mal."),
            CrossReferenceItem("Salmos 31:20", 19, 31, 20, "Los pondrás en lo secreto de tu presencia de la conspiración del hombre.")
        ),
        // Salmos 103:1-3
        "19_103_1" to listOf(
            CrossReferenceItem("Isaías 53:4", 23, 53, 4, "Ciertamente llevó él nuestras enfermedades, y sufrió nuestros dolores."),
            CrossReferenceItem("Colosenses 1:13-14", 51, 1, 13, "El cual nos ha librado de la potestad de las tinieblas... en quien tenemos redención por su sangre.")
        ),
        // Salmos 119:105
        "19_119_105" to listOf(
            CrossReferenceItem("Proverbios 6:23", 20, 6, 23, "Porque el mandamiento es lámpara, y la enseñanza es luz."),
            CrossReferenceItem("2 Pedro 1:19", 61, 1, 19, "Como a una antorcha que alumbra en lugar oscuro.")
        ),
        // Salmos 121:1-2
        "19_121_1" to listOf(
            CrossReferenceItem("Salmos 124:8", 19, 124, 8, "Nuestro socorro está en el nombre de Jehová, que hizo el cielo y la tierra."),
            CrossReferenceItem("Hebreos 13:6", 58, 13, 6, "El Señor es mi ayudador; no temeré lo que me pueda hacer el hombre.")
        ),
        // Proverbios 3:5-6
        "20_3_5" to listOf(
            CrossReferenceItem("Jeremías 17:7", 24, 17, 7, "Bendito el varón que confía en Jehová, y cuya confianza es Jehová."),
            CrossReferenceItem("Salmos 37:5", 19, 37, 5, "Encomienda a Jehová tu camino, confía en él, y él hará.")
        ),
        // Proverbios 4:23
        "20_4_23" to listOf(
            CrossReferenceItem("Lucas 6:45", 42, 6, 45, "De la abundancia del corazón habla la boca."),
            CrossReferenceItem("Mateo 15:19", 40, 15, 19, "Porque del corazón salen los malos pensamientos, los homicidios, los adulterios.")
        ),
        // Eclesiastés 3:1
        "21_3_1" to listOf(
            CrossReferenceItem("Gálatas 4:4", 48, 4, 4, "Pero cuando vino el cumplimiento del tiempo, Dios envió a su Hijo."),
            CrossReferenceItem("Efesios 1:10", 49, 1, 10, "De reunir todas las cosas en Cristo, en la dispensación del cumplimiento de los tiempos.")
        ),

        // === PROFETAS MAYORES ===
        // Isaías 7:14
        "23_7_14" to listOf(
            CrossReferenceItem("Mateo 1:22-23", 40, 1, 22, "He aquí, una virgen concebirá y dará a luz un hijo, y llamarás su nombre Emanuel."),
            CrossReferenceItem("Lucas 1:31", 42, 1, 31, "Y concebirás en tu vientre, y darás a luz un hijo, y llamarás su nombre JESÚS.")
        ),
        // Isaías 9:6
        "23_9_6" to listOf(
            CrossReferenceItem("Lucas 2:11", 42, 2, 11, "Que os ha nacido hoy, en la ciudad de David, un Salvador, que es CRISTO el Señor."),
            CrossReferenceItem("Juan 1:14", 43, 1, 14, "Y aquel Verbo fue hecho carne, y habitó entre nosotros.")
        ),
        // Isaías 40:31
        "23_40_31" to listOf(
            CrossReferenceItem("2 Corintios 4:16", 47, 4, 16, "Aunque este nuestro hombre exterior se va desgastando, el interior no obstante se renueva de día en día."),
            CrossReferenceItem("Salmos 103:5", 19, 103, 5, "El que sacia de bien tu boca de modo que te rejuvenezcas como el águila.")
        ),
        // Isaías 53:5
        "23_53_5" to listOf(
            CrossReferenceItem("1 Pedro 2:24", 60, 2, 24, "Quien llevó él mismo nuestros pecados en su cuerpo sobre el madero... y por cuya herida fuisteis sanados."),
            CrossReferenceItem("Romanos 5:8", 45, 5, 8, "Mas Dios muestra su amor para con nosotros, en que siendo aún pecadores, Cristo murió por nosotros."),
            CrossReferenceItem("1 Corintios 15:3", 46, 15, 3, "Cristo murió por nuestros pecados, conforme a las Escrituras.")
        ),
        // Jeremías 29:11
        "24_29_11" to listOf(
            CrossReferenceItem("Romanos 8:28", 45, 8, 28, "A los que aman a Dios, todas las cosas les ayudan a bien."),
            CrossReferenceItem("Proverbios 23:18", 20, 23, 18, "Ciertamente hay fin, y tu esperanza no será cortada.")
        ),
        // Jeremías 31:31
        "24_31_31" to listOf(
            CrossReferenceItem("Hebreos 8:8", 58, 8, 8, "He aquí vienen días, dice el Señor, en que estableceré con la casa de Israel un nuevo pacto."),
            CrossReferenceItem("Lucas 22:20", 42, 22, 20, "Esta copa es el nuevo pacto en mi sangre, que por vosotros se derrama.")
        ),
        // Jeremías 33:3
        "24_33_3" to listOf(
            CrossReferenceItem("Mateo 7:7", 40, 7, 7, "Pedid, y se os dará; buscad, y hallaréis; llamad, y se os abrirá."),
            CrossReferenceItem("Santiago 1:5", 59, 1, 5, "Si alguno de vosotros tiene falta de sabiduría, pídala a Dios.")
        ),
        // Lamentaciones 3:22-23
        "25_3_22" to listOf(
            CrossReferenceItem("Salmos 89:1", 19, 89, 1, "Las misericordias de Jehová cantaré perpetuamente."),
            CrossReferenceItem("Tito 3:5", 56, 3, 5, "Nos salvó, no por obras de justicia que nosotros hubiéramos hecho, sino por su misericordia.")
        ),
        // Ezequiel 36:26
        "26_36_26" to listOf(
            CrossReferenceItem("Jeremías 31:33", 24, 31, 33, "Daré mi ley en su mente, y la escribiré en su corazón."),
            CrossReferenceItem("2 Corintios 5:17", 47, 5, 17, "De modo que si alguno está en Cristo, nueva criatura es.")
        ),
        // Daniel 3:17-18
        "27_3_17" to listOf(
            CrossReferenceItem("Hebreos 11:34", 58, 11, 34, "Apagaron fuegos impetuosos, evitaron filo de espada."),
            CrossReferenceItem("Hechos 4:19-20", 44, 4, 19, "Juzgad si es justo delante de Dios obedecer a vosotros antes que a Dios.")
        ),

        // === PROFETAS MENORES ===
        // Miqueas 5:2
        "33_5_2" to listOf(
            CrossReferenceItem("Mateo 2:1-6", 40, 2, 1, "Cuando Jesús nació en Belén de Judea en días del rey Herodes."),
            CrossReferenceItem("Juan 7:42", 43, 7, 42, "¿No dice la Escritura que del linaje de David y de la aldea de Belén vendrá el Cristo?")
        ),
        // Miqueas 6:8
        "33_6_8" to listOf(
            CrossReferenceItem("Deuteronomio 10:12", 5, 10, 12, "¿Qué pide Jehová tu Dios de ti, sino que temas a Jehová tu Dios?"),
            CrossReferenceItem("Mateo 23:23", 40, 23, 23, "Lo más importante de la ley: la justicia, la misericordia y la fe.")
        ),
        // Habacuc 2:4
        "35_2_4" to listOf(
            CrossReferenceItem("Romanos 1:17", 45, 1, 17, "Mas el justo por la fe vivirá."),
            CrossReferenceItem("Gálatas 3:11", 48, 3, 11, "El justo por la fe vivirá."),
            CrossReferenceItem("Hebreos 10:38", 58, 10, 38, "Mas el justo vivirá por fe.")
        ),

        // === EVANGELIOS Y HECHOS ===
        // Mateo 5:3
        "40_5_3" to listOf(
            CrossReferenceItem("Isaías 57:15", 23, 57, 15, "Habito con el quebrantado y humilde de espíritu."),
            CrossReferenceItem("Lucas 6:20", 42, 6, 20, "Bienaventurados vosotros los pobres, porque vuestro es el reino de Dios.")
        ),
        // Mateo 6:33
        "40_6_33" to listOf(
            CrossReferenceItem("Salmos 34:10", 19, 34, 10, "Los que buscan a Jehová no tendrán falta de ningún bien."),
            CrossReferenceItem("Filipenses 4:19", 50, 4, 19, "Mi Dios, pues, suplirá todo lo que os falta conforme a sus riquezas en gloria.")
        ),
        // Mateo 28:19
        "40_28_19" to listOf(
            CrossReferenceItem("Marcos 16:15", 41, 16, 15, "Id por todo el mundo y predicad el evangelio a toda criatura."),
            CrossReferenceItem("Hechos 1:8", 44, 1, 8, "Y me seréis testigos en Jerusalén, en toda Judea, en Samaria, y hasta lo último de la tierra.")
        ),
        // Marcos 10:45
        "41_10_45" to listOf(
            CrossReferenceItem("Mateo 20:28", 40, 20, 28, "Como el Hijo del Hombre no vino para ser servido, sino para servir, y para dar su vida en rescate por muchos."),
            CrossReferenceItem("1 Timoteo 2:6", 54, 2, 6, "El cual se dio a sí mismo en rescate por todos.")
        ),
        // Lucas 1:37
        "42_1_37" to listOf(
            CrossReferenceItem("Génesis 18:14", 1, 18, 14, "¿Hay para Dios alguna cosa difícil?"),
            CrossReferenceItem("Mateo 19:26", 40, 19, 26, "Para los hombres esto es imposible; mas para Dios todo es posible.")
        ),
        // Lucas 19:10
        "42_19_10" to listOf(
            CrossReferenceItem("Mateo 18:11", 40, 18, 11, "Porque el Hijo del Hombre ha venido para salvar lo que se había perdido."),
            CrossReferenceItem("1 Timoteo 1:15", 54, 1, 15, "Cristo Jesús vino al mundo para salvar a los pecadores.")
        ),
        // Juan 1:1
        "43_1_1" to listOf(
            CrossReferenceItem("Génesis 1:1", 1, 1, 1, "En el principio creó Dios los cielos y la tierra."),
            CrossReferenceItem("1 Juan 1:1", 62, 1, 1, "Lo que era desde el principio, lo que hemos oído, lo que hemos visto."),
            CrossReferenceItem("Apocalipsis 19:13", 66, 19, 13, "Y su nombre es: EL VERBO DE DIOS.")
        ),
        // Juan 1:14
        "43_1_14" to listOf(
            CrossReferenceItem("Filipenses 2:7", 50, 2, 7, "Se despojó a sí mismo, tomando forma de siervo, hecho semejante a los hombres."),
            CrossReferenceItem("1 Timoteo 3:16", 54, 3, 16, "Dios fue manifestado en carne, justificado en el Espíritu.")
        ),
        // Juan 3:16
        "43_3_16" to listOf(
            CrossReferenceItem("Romanos 5:8", 45, 5, 8, "Mas Dios muestra su amor para con nosotros, en que siendo aún pecadores, Cristo murió por nosotros."),
            CrossReferenceItem("1 Juan 4:9", 62, 4, 9, "En esto se mostró el amor de Dios para con nosotros, en que Dios envió a su Hijo unigénito al mundo."),
            CrossReferenceItem("Efesios 2:4-5", 49, 2, 4, "Pero Dios, que es rico en misericordia, por su gran amor con que nos amó.")
        ),
        // Juan 14:6
        "43_14_6" to listOf(
            CrossReferenceItem("Hechos 4:12", 44, 4, 12, "Y en ningún otro hay salvación; porque no hay otro nombre bajo el cielo, dado a los hombres, en que podamos ser salvos."),
            CrossReferenceItem("Hebreos 10:19-20", 58, 10, 19, "Teniendo libertad para entrar en el Lugar Santísimo por la sangre de Jesucristo, por el camino nuevo y vivo.")
        ),
        // Hechos 1:8
        "44_1_8" to listOf(
            CrossReferenceItem("Lucas 24:49", 42, 24, 49, "Quedaos en la ciudad de Jerusalén, hasta que seáis investidos de poder desde lo alto."),
            CrossReferenceItem("Mateo 28:19", 40, 28, 19, "Por tanto, id, y haced discípulos a todas las naciones.")
        ),
        // Hechos 4:12
        "44_4_12" to listOf(
            CrossReferenceItem("Juan 14:6", 43, 14, 6, "Nadie viene al Padre, sino por mí."),
            CrossReferenceItem("1 Timoteo 2:5", 54, 2, 5, "Porque hay un solo Dios, y un solo mediador entre Dios y los hombres, Jesucristo hombre.")
        ),

        // === EPÍSTOLAS ===
        // Romanos 3:23
        "45_3_23" to listOf(
            CrossReferenceItem("Eclesiastés 7:20", 21, 7, 20, "Ciertamente no hay hombre justo en la tierra, que haga el bien y nunca peque."),
            CrossReferenceItem("Romanos 5:12", 45, 5, 12, "Así la muerte pasó a todos los hombres, por cuanto todos pecaron.")
        ),
        // Romanos 6:23
        "45_6_23" to listOf(
            CrossReferenceItem("Génesis 2:17", 1, 2, 17, "El día que de él comieres, ciertamente morirás."),
            CrossReferenceItem("Romanos 5:21", 45, 5, 21, "Así también la gracia reine por la justicia para vida eterna por Jesucristo Señor nuestro.")
        ),
        // Romanos 8:28
        "45_8_28" to listOf(
            CrossReferenceItem("Génesis 50:20", 1, 50, 20, "Vosotros pensasteis mal contra mí, mas Dios lo encaminó a bien."),
            CrossReferenceItem("Jeremías 29:11", 24, 29, 11, "Porque yo sé los pensamientos que tengo acerca de vosotros, dice Jehová."),
            CrossReferenceItem("Efesios 1:11", 49, 1, 11, "Habiendo sido predestinados conforme al propósito del que hace todas las cosas según el designio de su voluntad.")
        ),
        // Romanos 8:31
        "45_8_31" to listOf(
            CrossReferenceItem("Salmos 118:6", 19, 118, 6, "Jehová está conmigo; no temeré lo que me pueda hacer el hombre."),
            CrossReferenceItem("Isaías 41:10", 23, 41, 10, "No temas, porque yo estoy contigo.")
        ),
        // Romanos 12:1
        "45_12_1" to listOf(
            CrossReferenceItem("1 Pedro 2:5", 60, 2, 5, "Para ofrecer sacrificios espirituales aceptables a Dios por medio de Jesucristo."),
            CrossReferenceItem("Efesios 4:23", 49, 4, 23, "Y renovaos en el espíritu de vuestra mente.")
        ),
        // 1 Corintios 13:13
        "46_13_13" to listOf(
            CrossReferenceItem("Gálatas 5:6", 48, 5, 6, "La fe que obra por el amor."),
            CrossReferenceItem("1 Juan 4:16", 62, 4, 16, "Dios es amor; y el que permanece en amor, permanece en Dios, y Dios en él.")
        ),
        // 2 Corintios 5:17
        "47_5_17" to listOf(
            CrossReferenceItem("Efesios 2:10", 49, 2, 10, "Porque somos hechura suya, creados en Cristo Jesús para buenas obras."),
            CrossReferenceItem("Gálatas 6:15", 48, 6, 15, "En Cristo Jesús ni la circuncisión vale nada, ni la incircuncisión, sino una nueva creación.")
        ),
        // 2 Corintios 12:9
        "47_12_9" to listOf(
            CrossReferenceItem("Filipenses 4:13", 50, 4, 13, "Todo lo puedo en Cristo que me fortalece."),
            CrossReferenceItem("Isaías 40:29", 23, 40, 29, "Él da esfuerzo al cansado, y multiplica las fuerzas al que no tiene ningunas.")
        ),
        // Gálatas 2:20
        "48_2_20" to listOf(
            CrossReferenceItem("Romanos 6:6", 45, 6, 6, "Sabiendo esto, que nuestro viejo hombre fue crucificado juntamente con él."),
            CrossReferenceItem("Colosenses 3:3", 51, 3, 3, "Porque habéis muerto, y vuestra vida está escondida con Cristo en Dios.")
        ),
        // Efesios 2:8-9
        "49_2_8" to listOf(
            CrossReferenceItem("Romanos 3:24", 45, 3, 24, "Siendo justificados gratuitamente por su gracia, mediante la redención que es en Cristo Jesús."),
            CrossReferenceItem("Tito 3:5", 56, 3, 5, "Nos salvó, no por obras de justicia que nosotros hubiéramos hecho, sino por su misericordia.")
        ),
        // Filipenses 4:6
        "50_4_6" to listOf(
            CrossReferenceItem("1 Pedro 5:7", 60, 5, 7, "Echando toda vuestra ansiedad sobre él, porque él tiene cuidado de vosotros."),
            CrossReferenceItem("Mateo 6:25", 40, 6, 25, "Por tanto os digo: No os afanéis por vuestra vida.")
        ),
        // Filipenses 4:13
        "50_4_13" to listOf(
            CrossReferenceItem("2 Corintios 12:9", 47, 12, 9, "Bástate mi gracia; porque mi poder se perfecciona en la debilidad."),
            CrossReferenceItem("Juan 15:5", 43, 15, 5, "Porque separados de mí nada podéis hacer.")
        ),
        // Colosenses 3:23
        "51_3_23" to listOf(
            CrossReferenceItem("Efesios 6:7", 49, 6, 7, "Sirviendo de buena voluntad, como al Señor y no a los hombres."),
            CrossReferenceItem("1 Corintios 10:31", 46, 10, 31, "Si, pues, coméis o bebéis, o hacéis otra cosa, hacedlo todo para la gloria de Dios.")
        ),
        // 2 Timoteo 3:16
        "55_3_16" to listOf(
            CrossReferenceItem("2 Pedro 1:20-21", 61, 1, 20, "Los santos hombres de Dios hablaron siendo inspirados por el Espíritu Santo."),
            CrossReferenceItem("Salmos 119:105", 19, 119, 105, "Lámpara es a mis pies tu palabra, y lumbrera a mi camino.")
        ),
        // Hebreos 11:1
        "58_11_1" to listOf(
            CrossReferenceItem("Romanos 8:24-25", 45, 8, 24, "Porque en esperanza fuimos salvos; pero la esperanza que se ve, no es esperanza."),
            CrossReferenceItem("2 Corintios 4:18", 47, 4, 18, "No mirando nosotros las cosas que se ven, sino las que no se ven.")
        ),
        // Hebreos 12:2
        "58_12_2" to listOf(
            CrossReferenceItem("Filipenses 2:8", 50, 2, 8, "Se humilló a sí mismo, haciéndose obediente hasta la muerte, y muerte de cruz."),
            CrossReferenceItem("Hebreos 2:9", 58, 2, 9, "Vemos a aquel que fue hecho un poco menor que los ángeles, a Jesús, coronado de gloria.")
        ),
        // Santiago 1:5
        "59_1_5" to listOf(
            CrossReferenceItem("Proverbios 2:6", 20, 2, 6, "Porque Jehová da la sabiduría, y de su boca viene el conocimiento y la inteligencia."),
            CrossReferenceItem("1 Reyes 3:9", 11, 3, 9, "Da, pues, a tu siervo corazón entendido para juzgar a tu pueblo.")
        ),
        // 1 Pedro 5:7
        "60_5_7" to listOf(
            CrossReferenceItem("Salmos 55:22", 19, 55, 22, "Echa sobre Jehová tu carga, y él te sustentará; no dejará para siempre caído al justo."),
            CrossReferenceItem("Filipenses 4:6", 50, 4, 6, "Por nada estéis afanosos, sino sean conocidas vuestras peticiones delante de Dios.")
        ),
        // 1 Juan 1:9
        "62_1_9" to listOf(
            CrossReferenceItem("Proverbios 28:13", 20, 28, 13, "El que encubre sus pecados no prosperará; mas el que los confiesa y se aparta alcanzará misericordia."),
            CrossReferenceItem("Salmos 32:5", 19, 32, 5, "Mi pecado te declaré, y no encubrí mi iniquidad.")
        ),
        // Apocalipsis 3:20
        "66_3_20" to listOf(
            CrossReferenceItem("Cantares 5:2", 22, 5, 2, "¡La voz de mi amado que llama a la puerta!"),
            CrossReferenceItem("Juan 14:23", 43, 14, 23, "El que me ama, mi palabra guardará; y mi Padre le amará, y vendremos a él, y haremos morada con él.")
        ),
        // Apocalipsis 21:4
        "66_21_4" to listOf(
            CrossReferenceItem("Isaías 25:8", 23, 25, 8, "Destruirá a la muerte para siempre; y enjugará Jehová el Señor toda lágrima de todos los rostros."),
            CrossReferenceItem("1 Corintios 15:54", 46, 15, 54, "Sorbida es la muerte en victoria.")
        )
    )

    // Key: "bookId_chapter" -> List<CrossReferenceItem>
    private val chapterReferencesMap: Map<String, List<CrossReferenceItem>> = mapOf(
        // 2 Crónicas 22 (Ocozías y Atalía)
        "14_22" to listOf(
            CrossReferenceItem("2 Reyes 8:25-29", 12, 8, 25, "Paralelo histórico: Reinado y caída de Ocozías de Judá."),
            CrossReferenceItem("2 Reyes 11:1-3", 12, 11, 1, "Paralelo histórico: Rebelión de Atalía y rescate milagroso del infante Joás.")
        ),
        // 2 Crónicas 23 (Joás coronado rey)
        "14_23" to listOf(
            CrossReferenceItem("2 Reyes 11:4-21", 12, 11, 4, "Paralelo histórico: El sacerdote Joiada unge a Joás y derroca a Atalía.")
        ),
        // 2 Crónicas 24 (Reinado de Joás)
        "14_24" to listOf(
            CrossReferenceItem("2 Reyes 12:1-21", 12, 12, 1, "Paralelo histórico: Reparación del Templo y apostasía posterior de Joás.")
        ),
        // 1 Reyes 8 (Templo de Salomón)
        "11_8" to listOf(
            CrossReferenceItem("2 Crónicas 6:1-42", 14, 6, 1, "Paralelo histórico: La solemne oración de Salomón dedicando el Santo Templo.")
        ),
        // 2 Reyes 18 (Ezequías y Asiria)
        "12_18" to listOf(
            CrossReferenceItem("Isaías 36:1-22", 23, 36, 1, "Paralelo profético: Desafío de Senaquerib contra Jerusalén y confianza en Dios."),
            CrossReferenceItem("2 Crónicas 32:1-23", 14, 32, 1, "Paralelo de Crónicas: Invasión de Senaquerib y liberación divina.")
        ),
        // Mateo 5 (Sermón del Monte)
        "40_5" to listOf(
            CrossReferenceItem("Lucas 6:20-49", 42, 6, 20, "Paralelo evangélico: El Sermón de la llanura y las Bienaventuranzas del Reino.")
        ),
        // Mateo 13 (Parábolas del Reino)
        "40_13" to listOf(
            CrossReferenceItem("Marcos 4:1-34", 41, 4, 1, "Paralelo evangélico: Parábolas del Sembrador y de la Semilla que crece."),
            CrossReferenceItem("Lucas 8:4-18", 42, 8, 4, "Paralelo evangélico: La semilla y la responsabilidad de oír la Palabra.")
        ),
        // Mateo 26 (Pasión y arresto)
        "40_26" to listOf(
            CrossReferenceItem("Marcos 14:1-72", 41, 14, 1, "Paralelo sinóptico: Getsemaní, angustia y entrega de Jesús."),
            CrossReferenceItem("Lucas 22:1-71", 42, 22, 1, "Paralelo sinóptico: La última Pascua y arresto de Jesucristo.")
        ),
        // Marcos 1
        "41_1" to listOf(
            CrossReferenceItem("Mateo 3:1-17", 40, 3, 1, "Paralelo: Ministerio de Juan el Bautista y Bautismo de Jesús."),
            CrossReferenceItem("Lucas 3:1-22", 42, 3, 1, "Paralelo de Lucas: La voz que clama en el desierto y el Espíritu descendiendo.")
        ),
        // Lucas 2 (Natividad)
        "42_2" to listOf(
            CrossReferenceItem("Mateo 1:18-25", 40, 1, 18, "Paralelo: El nacimiento virginal de Jesús en cumplimiento de la profecía."),
            CrossReferenceItem("Miqueas 5:2", 33, 5, 2, "Profecía mesiánica: Belén de Efrata dará origen al Gobernador eterno.")
        ),
        // Juan 1
        "43_1" to listOf(
            CrossReferenceItem("Génesis 1:1-3", 1, 1, 1, "Paralelo de la Creación: En el principio la luz resplandeció sobre las tinieblas."),
            CrossReferenceItem("Colosenses 1:15-17", 51, 1, 15, "Cristo la imagen del Dios invisible, primogénito de toda creación.")
        )
    )

    fun hasReferences(bookId: Int, chapter: Int, verse: Int): Boolean {
        val verseKey = "${bookId}_${chapter}_$verse"
        if (referencesMap.containsKey(verseKey)) return true

        // If chapter-level parallel exists, show badge on verse 1
        val chapterKey = "${bookId}_$chapter"
        if (verse == 1 && chapterReferencesMap.containsKey(chapterKey)) return true

        return false
    }

    fun getReferences(bookId: Int, chapter: Int, verse: Int): List<CrossReferenceItem> {
        val verseKey = "${bookId}_${chapter}_$verse"
        val direct = referencesMap[verseKey]
        if (!direct.isNullOrEmpty()) return direct

        // Fallback 1: Chapter level references
        val chapterKey = "${bookId}_$chapter"
        val chapterRefs = chapterReferencesMap[chapterKey]
        if (!chapterRefs.isNullOrEmpty()) return chapterRefs

        // Fallback 2: Any reference from this chapter
        val anyFromChapter = referencesMap.entries
            .filter { it.key.startsWith("${bookId}_${chapter}_") }
            .flatMap { it.value }
        if (anyFromChapter.isNotEmpty()) return anyFromChapter.take(3)

        // Fallback 3: Comprehensive book & canonical fallback (never empty!)
        return getCanonicalFallback(bookId, chapter)
    }

    private fun getCanonicalFallback(bookId: Int, chapter: Int): List<CrossReferenceItem> {
        return when {
            // Pentateuco (1..5)
            bookId in 1..5 -> listOf(
                CrossReferenceItem("Hebreos 10:1", 58, 10, 1, "La ley teniendo la sombra de los bienes venideros, no la imagen misma de las cosas."),
                CrossReferenceItem("Gálatas 3:24", 48, 3, 24, "De manera que la ley ha sido nuestro ayo, para llevarnos a Cristo.")
            )
            // Libros Históricos (6..17)
            bookId in 6..17 -> listOf(
                CrossReferenceItem("1 Corintios 10:11", 46, 10, 11, "Y estas cosas les acontecieron como ejemplo, y están escritas para amonestarnos a nosotros."),
                CrossReferenceItem("Romanos 15:4", 45, 15, 4, "Porque las cosas que se escribieron antes, para nuestra enseñanza se escribieron.")
            )
            // Poéticos y Salmos (18..22)
            bookId in 18..22 -> listOf(
                CrossReferenceItem("Lucas 24:44", 42, 24, 44, "Era necesario que se cumpliese todo lo que está escrito de mí en la ley de Moisés, en los profetas y en los salmos."),
                CrossReferenceItem("Colosenses 3:16", 51, 3, 16, "Cantando con gracia en vuestros corazones al Señor con salmos e himnos y cánticos espirituales.")
            )
            // Profetas (23..39)
            bookId in 23..39 -> listOf(
                CrossReferenceItem("2 Pedro 1:21", 61, 1, 21, "Porque nunca la profecía fue traída por voluntad humana, sino que los santos hombres de Dios hablaron siendo inspirados."),
                CrossReferenceItem("Lucas 24:27", 42, 24, 27, "Y comenzando desde Moisés, y siguiendo por todos los profetas, les declaraba en todas las Escrituras lo que de él decían.")
            )
            // Evangelios y Hechos (40..44)
            bookId in 40..44 -> listOf(
                CrossReferenceItem("Juan 20:31", 43, 20, 31, "Éstas se han escrito para que creáis que Jesús es el Cristo, el Hijo de Dios, y para que creyendo, tengáis vida en su nombre."),
                CrossReferenceItem("Hebreos 1:1-2", 58, 1, 1, "Dios, habiendo hablado muchas veces por los profetas, en estos postreros días nos ha hablado por el Hijo.")
            )
            // Epístolas Paulinas y Generales (45..65)
            bookId in 45..65 -> listOf(
                CrossReferenceItem("2 Timoteo 3:16-17", 55, 3, 16, "Toda la Escritura es inspirada por Dios, y útil para enseñar, para redargüir, para corregir, para instruir en justicia."),
                CrossReferenceItem("Efesios 2:20", 49, 2, 20, "Edificados sobre el fundamento de los apóstoles y profetas, siendo la principal piedra del ángulo Jesucristo mismo.")
            )
            // Apocalipsis (66)
            bookId == 66 -> listOf(
                CrossReferenceItem("Daniel 7:13-14", 27, 7, 13, "Miraba en la visión de la noche, y he aquí con las nubes del cielo venía uno como un hijo de hombre."),
                CrossReferenceItem("Isaías 65:17", 23, 65, 17, "Porque he aquí que yo crearé nuevos cielos y nueva tierra.")
            )
            else -> listOf(
                CrossReferenceItem("Salmos 119:105", 19, 119, 105, "Lámpara es a mis pies tu palabra, y lumbrera a mi camino."),
                CrossReferenceItem("2 Timoteo 3:16", 55, 3, 16, "Toda la Escritura es inspirada por Dios y útil para edificación.")
            )
        }
    }
}
