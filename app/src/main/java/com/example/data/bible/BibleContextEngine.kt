package com.example.data.bible

import com.example.data.initial.InitialVersesData

data class BookTheologicalProfile(
    val author: String,
    val dateOrPeriod: String,
    val centralTheme: String,
    val audience: String,
    val doctrinalPurpose: String
)

object BibleContextEngine {

    private val bookProfiles: Map<String, BookTheologicalProfile> = mapOf(
        // === ANTIGUO TESTAMENTO ===
        "Génesis" to BookTheologicalProfile(
            author = "Moisés",
            dateOrPeriod = "c. 1440-1400 a.C.",
            centralTheme = "Orígenes, creación del mundo y la elección del pueblo del pacto a través de los patriarcas.",
            audience = "El pueblo de Israel en el desierto rumbo a la Tierra Prometida.",
            doctrinalPurpose = "Establecer que el Dios de Israel es el Creador absoluto, soberano sobre la historia, y fiel a sus promesas patriarcales."
        ),
        "Éxodo" to BookTheologicalProfile(
            author = "Moisés",
            dateOrPeriod = "c. 1440-1400 a.C.",
            centralTheme = "Liberación de la esclavitud, revelación de la Ley en el Sinaí y el establecimiento del Tabernáculo.",
            audience = "Israel recién liberado de Egipto.",
            doctrinalPurpose = "Mostrar la santidad de Dios, su poder redentor y la necesidad de una comunidad consagrada a su presencia."
        ),
        "Levítico" to BookTheologicalProfile(
            author = "Moisés",
            dateOrPeriod = "c. 1440 a.C.",
            centralTheme = "Santidad personal y comunitaria, expiación por el pecado y sacerdocio ordenado.",
            audience = "La congregación sacerdotal y el pueblo de Israel.",
            doctrinalPurpose = "Enseñar cómo un Dios Santo puede habitar entre un pueblo imperfecto mediante la expiación y la obediencia."
        ),
        "Números" to BookTheologicalProfile(
            author = "Moisés",
            dateOrPeriod = "c. 1400 a.C.",
            centralTheme = "Peregrinación en el desierto, disciplina divina y preservación de la nueva generación.",
            audience = "La nueva generación de Israel antes de cruzar el Jordán.",
            doctrinalPurpose = "Advertir contra la incredulidad y evidenciar la incansable paciencia de Dios guiando a su pueblo."
        ),
        "Deuteronomio" to BookTheologicalProfile(
            author = "Moisés",
            dateOrPeriod = "c. 1406 a.C.",
            centralTheme = "Renovación del pacto, llamado a amar a Dios con todo el corazón y obediencia filial.",
            audience = "Israel acampado en las llanuras de Moab a las puertas de Canaán.",
            doctrinalPurpose = "Recordar los mandamientos y exhortar a la fidelidad absoluta para disfrutar las bendiciones de la tierra prometida."
        ),
        "Josué" to BookTheologicalProfile(
            author = "Josué / contemporáneos",
            dateOrPeriod = "c. 1380 a.C.",
            centralTheme = "Conquista y repartición de Canaán; cumplimiento de la promesa territorial de Dios.",
            audience = "Las doce tribus de Israel asentándose en la tierra prometida.",
            doctrinalPurpose = "Demostrar que ninguna palabra de Dios cayó a tierra; la victoria proviene de la valentía y fidelidad a la Ley."
        ),
        "Jueces" to BookTheologicalProfile(
            author = "Tradicionalmente Samuel",
            dateOrPeriod = "c. 1050-1000 a.C.",
            centralTheme = "Ciclos de apostasía, opresión extranjera, clamor del pueblo y liberación providencial.",
            audience = "Israel pre-monárquico.",
            doctrinalPurpose = "Ilustrar la bancarrota moral de vivir sin un rey piadoso y la misericordia inmerecida del Libertador divino."
        ),
        "Rut" to BookTheologicalProfile(
            author = "Desconocido (trad. Samuel)",
            dateOrPeriod = "Época de los Jueces (redactado c. s. X a.C.)",
            centralTheme = "Lealtad amorosa (Hesed), redención por un pariente cercano y la providencia de Dios.",
            audience = "Israel en tiempos de la dinastía davídica.",
            doctrinalPurpose = "Mostrar la gracia de Dios incluyendo a una extranjera en el linaje davídico y mesiánico mediante la fidelidad abnegada."
        ),
        "1 Samuel" to BookTheologicalProfile(
            author = "Profetas Samuel, Natán y Gad",
            dateOrPeriod = "c. 930 a.C.",
            centralTheme = "Transición de la teocracia tribal a la monarquía; el contraste entre Saúl y David.",
            audience = "Israel unificado.",
            doctrinalPurpose = "Enseñar que Dios no mira la apariencia exterior sino el corazón consagrado a su voluntad."
        ),
        "2 Samuel" to BookTheologicalProfile(
            author = "Natán y Gad",
            dateOrPeriod = "c. 930 a.C.",
            centralTheme = "Reinado de David, pacto davídico mesiánico y las consecuencias del pecado.",
            audience = "Israel bajo la corona davídica.",
            doctrinalPurpose = "Establecer la promesa eterna del Mesías en el trono de David y la necesidad de perdón y arrepentimiento."
        ),
        "1 Reyes" to BookTheologicalProfile(
            author = "Trad. Jeremías",
            dateOrPeriod = "c. 560-550 a.C.",
            centralTheme = "Gloria de Salomón, construcción del Templo y división del reino entre Judá e Israel.",
            audience = "Pueblo en exilio babilónico reflexionando en su historia.",
            doctrinalPurpose = "Enseñar que la idolatría fragmenta a la nación y que la fidelidad a la palabra profética preserva la vida."
        ),
        "2 Reyes" to BookTheologicalProfile(
            author = "Trad. Jeremías",
            dateOrPeriod = "c. 560-550 a.C.",
            centralTheme = "Decadencia espiritual de los reinos divididos y caída de Samaria y Jerusalén.",
            audience = "Exiliados en Babilonia buscando consuelo y comprensión del juicio divino.",
            doctrinalPurpose = "Explicar que el exilio fue el resultado justo de siglos de desobediencia al pacto de Dios."
        ),
        "1 Crónicas" to BookTheologicalProfile(
            author = "Trad. Esdras",
            dateOrPeriod = "c. 450-400 a.C.",
            centralTheme = "Genealogías del pueblo redimido, adoración en el Templo y el corazón de David para la alabanza.",
            audience = "Remanente post-exílico reconstruyendo la vida en Jerusalén.",
            doctrinalPurpose = "Restaurar la identidad de la adoración y reafirmar las promesas davídicas a los repatriados."
        ),
        "2 Crónicas" to BookTheologicalProfile(
            author = "Trad. Esdras",
            dateOrPeriod = "c. 450-400 a.C.",
            centralTheme = "Historia de Judá, reformas religiosas y la importancia suprema de la oración y la humildad.",
            audience = "La comunidad post-exílica.",
            doctrinalPurpose = "Inspirar avivamiento recordando que si el pueblo se humilla y ora, Dios sana su tierra."
        ),
        "Esdras" to BookTheologicalProfile(
            author = "Esdras",
            dateOrPeriod = "c. 450 a.C.",
            centralTheme = "Retorno de Babilonia, reconstrucción del Templo y restauración espiritual en la Ley.",
            audience = "Los exiliados repatriados a Judá.",
            doctrinalPurpose = "Destacar la fidelidad de Dios al guiar a gobernantes paganos para cumplir sus promesas de retorno."
        ),
        "Nehemías" to BookTheologicalProfile(
            author = "Nehemías",
            dateOrPeriod = "c. 430 a.C.",
            centralTheme = "Reconstrucción de los muros de Jerusalén, liderazgo valiente y avivamiento comunitario.",
            audience = "Comunidad judía vulnerable en Jerusalén.",
            doctrinalPurpose = "Manifestar el poder de la oración combinada con la acción perseverante ante la hostilidad externa."
        ),
        "Ester" to BookTheologicalProfile(
            author = "Desconocido (Mardoqueo / contemporáneo)",
            dateOrPeriod = "c. 470 a.C.",
            centralTheme = "Providencia invisible de Dios librando a su pueblo del exterminio en el Imperio Persa.",
            audience = "Judíos de la diáspora en el imperio aqueménida.",
            doctrinalPurpose = "Enseñar que aun cuando el nombre de Dios no se mencione explícitamente, su mano soberana guía cada destino."
        ),
        "Job" to BookTheologicalProfile(
            author = "Desconocido (época patriarcal)",
            dateOrPeriod = "Época de los patriarcas",
            centralTheme = "El sufrimiento del justo, la soberanía inescrutable de Dios y la fe que persevera sin respuestas fáciles.",
            audience = "Creyentes de todas las épocas que enfrentan dolor inexplicable.",
            doctrinalPurpose = "Desmantelar la teología de retribución mecánica y proclamar la grandeza y justicia de Dios por encima del entendimiento humano."
        ),
        "Salmos" to BookTheologicalProfile(
            author = "David, Asaf, hijos de Coré, Moisés, Salomón",
            dateOrPeriod = "c. 1000 - 450 a.C.",
            centralTheme = "Himnario y devocionario de Israel: alabanza, lamento, confesión, sabiduría y esperanza mesiánica.",
            audience = "La congregación del pueblo de Dios en la adoración pública y privada.",
            doctrinalPurpose = "Enseñar al ser humano a expresar todas sus emociones sinceras ante la presencia soberana y misericordiosa de Dios."
        ),
        "Proverbios" to BookTheologicalProfile(
            author = "Salomón y sabios de Israel",
            dateOrPeriod = "c. 950 - 700 a.C.",
            centralTheme = "Sabiduría práctica para la vida diaria fundamentada en el temor de Jehová.",
            audience = "Jóvenes y líderes de la sociedad.",
            doctrinalPurpose = "Guiar en la prudencia moral, el dominio propio, la justicia social y las relaciones interpersonales."
        ),
        "Eclesiastés" to BookTheologicalProfile(
            author = "Salomón (El Predicador)",
            dateOrPeriod = "c. 935 a.C.",
            centralTheme = "La vanidad de una vida 'debajo del sol' sin Dios y el llamado a temerle y disfrutar de sus dones.",
            audience = "Aquellos que buscan plenitud en los placeres o logros terrenales.",
            doctrinalPurpose = "Demostrar que solo en comunión con el Creador eterno el trabajo, la sabiduría y la existencia adquieren sentido real."
        ),
        "Cantares" to BookTheologicalProfile(
            author = "Salomón",
            dateOrPeriod = "c. 960 a.C.",
            centralTheme = "Celebración del amor conyugal, la belleza del matrimonio y la fidelidad romántica.",
            audience = "El pueblo de Dios.",
            doctrinalPurpose = "Afirmar la pureza y nobleza del diseño divino para la intimidad y el afecto inquebrantable."
        ),
        "Isaías" to BookTheologicalProfile(
            author = "Isaías hijo de Amoz",
            dateOrPeriod = "c. 740-680 a.C.",
            centralTheme = "El Santo de Israel: juicio por rebelión y promesa del Siervo Sufriente y el Reino Mesiánico glorioso.",
            audience = "El reino de Judá frente a las amenazas asiria y babilónica.",
            doctrinalPurpose = "Proclamar la salvación universal por gracia a través del Redentor prometido (Isaías 53)."
        ),
        "Jeremías" to BookTheologicalProfile(
            author = "Jeremías",
            dateOrPeriod = "c. 627-580 a.C.",
            centralTheme = "El profeta llorón que advierte el juicio inminente y proclama el Nuevo Pacto escrito en los corazones.",
            audience = "Judá en sus últimos años antes de la caída de Jerusalén.",
            doctrinalPurpose = "Revelar el corazón dolido de Dios por la apostasía y su fidelidad inquebrantable para renovar un pacto eterno (Jeremías 31)."
        ),
        "Lamentaciones" to BookTheologicalProfile(
            author = "Jeremías",
            dateOrPeriod = "c. 586 a.C.",
            centralTheme = "Duelo y dolor desgarrador por la destrucción del templo y la ciudad, con destellos de misericordia inagotable.",
            audience = "Sobrevivientes de la tragedia de Jerusalén.",
            doctrinalPurpose = "Enseñar a procesar el duelo profundo con esperanza en las misericordias divinas que son nuevas cada mañana."
        ),
        "Ezequiel" to BookTheologicalProfile(
            author = "Ezequiel hijo de Buzi",
            dateOrPeriod = "c. 593-570 a.C.",
            centralTheme = "La gloria de Dios que sale del templo profanado y promete regresar para transformar corazones de piedra en corazones de carne.",
            audience = "Exiliados judíos junto al río Quebar en Babilonia.",
            doctrinalPurpose = "Demostrar que 'sabrán que Yo soy Jehová' mediante la regeneración espiritual de su pueblo."
        ),
        "Daniel" to BookTheologicalProfile(
            author = "Daniel",
            dateOrPeriod = "c. 605-535 a.C.",
            centralTheme = "Fidelidad intransigente en una cultura hostil y la supremacía soberana de Dios sobre los imperios del mundo.",
            audience = "Creyentes viviendo bajo persecución e influencias paganas.",
            doctrinalPurpose = "Asegurar que los reinos humanos pasarán, pero el Reino eterno de Dios permanecerá para siempre."
        ),

        // Profetas Menores
        "Oseas" to BookTheologicalProfile(
            author = "Oseas",
            dateOrPeriod = "c. 750 a.C.",
            centralTheme = "El amor incondicional y redentor de Dios hacia un pueblo espiritualmente infiel.",
            audience = "El reino del norte (Israel).",
            doctrinalPurpose = "Mostrar que Dios persigue con amor implacable al descarriado para restaurarlo."
        ),
        "Miqueas" to BookTheologicalProfile(
            author = "Miqueas de Moreset",
            dateOrPeriod = "c. 735 a.C.",
            centralTheme = "Justicia social, denuncia de la opresión religiosa y el anuncio del Mesías nacido en Belén.",
            audience = "Judá e Israel.",
            doctrinalPurpose = "Resumir lo que Dios pide: hacer justicia, amar misericordia y humillarse ante Dios (Miq 6:8)."
        ),
        "Habacuc" to BookTheologicalProfile(
            author = "Habacuc",
            dateOrPeriod = "c. 605 a.C.",
            centralTheme = "El diálogo sincero entre la duda del profeta y la respuesta divina: 'el justo por su fe vivirá'.",
            audience = "Judá antes de la invasión caldea.",
            doctrinalPurpose = "Guiar al creyente a descansar con cánticos de gozo en Dios aun cuando todo alrededor falle."
        ),

        // === NUEVO TESTAMENTO ===
        "Mateo" to BookTheologicalProfile(
            author = "Mateo (Leví, el recaudador de impuestos)",
            dateOrPeriod = "c. 60-65 d.C.",
            centralTheme = "Jesús es el Rey Mesías prometido en el Antiguo Testamento que cumple todas las profecías.",
            audience = "Creyentes de trasfondo judío.",
            doctrinalPurpose = "Presentar la ética del Reino de los Cielos (Sermón del Monte) y comisionar a hacer discípulos a todas las naciones."
        ),
        "Marcos" to BookTheologicalProfile(
            author = "Juan Marcos (basado en el testimonio de Pedro)",
            dateOrPeriod = "c. 55-65 d.C.",
            centralTheme = "Jesucristo como el Siervo de Dios y Redentor que vino a servir y dar su vida en rescate por muchos.",
            audience = "Comunidad cristiana en Roma bajo el asedio del imperio.",
            doctrinalPurpose = "Inspirar discipulado radical y sacrificio a la luz del ministerio dinámico y la cruz de Jesús."
        ),
        "Lucas" to BookTheologicalProfile(
            author = "Lucas el médico amado",
            dateOrPeriod = "c. 60-62 d.C.",
            centralTheme = "Jesús como el Hijo del Hombre compasivo que vino a buscar y salvar lo que se había perdido.",
            audience = "Teófilo y gentiles de todo el mundo mediterráneo.",
            doctrinalPurpose = "Destacar el poder del Espíritu Santo, la oración y la inclusión de los marginados, mujeres y gentiles."
        ),
        "Juan" to BookTheologicalProfile(
            author = "Juan el apóstol amado",
            dateOrPeriod = "c. 85-95 d.C.",
            centralTheme = "Jesús es el Verbo eterno encarnado, el Hijo de Dios que da vida eterna a quienes creen en su nombre.",
            audience = "Iglesia universal y buscadores de la verdad.",
            doctrinalPurpose = "Presentar las señales y las declaraciones 'Yo Soy' para que creyendo, tengamos vida en su nombre (Jn 20:31)."
        ),
        "Hechos" to BookTheologicalProfile(
            author = "Lucas",
            dateOrPeriod = "c. 62-64 d.C.",
            centralTheme = "La expansión victoriosa de la Iglesia primitiva por el poder del Espíritu Santo desde Jerusalén hasta Roma.",
            audience = "Teófilo y la Iglesia en crecimiento.",
            doctrinalPurpose = "Modelar la audacia evangelística, la vida en comunidad y la soberanía del Espíritu guiando a los apóstoles."
        ),
        "Romanos" to BookTheologicalProfile(
            author = "Pablo apóstol",
            dateOrPeriod = "c. 57 d.C. desde Corinto",
            centralTheme = "El Evangelio como poder de Dios para salvación: justificación solo por fe, santificación en el Espíritu y soberanía de la gracia.",
            audience = "La iglesia en Roma (judíos y gentiles).",
            doctrinalPurpose = "Proveer el tratado teológico más completo del cristianismo sobre la gracia inmerecida y la vida consagrada."
        ),
        "1 Corintios" to BookTheologicalProfile(
            author = "Pablo apóstol",
            dateOrPeriod = "c. 55 d.C. desde Éfeso",
            centralTheme = "Corrección de divisiones, ética moral, uso edificante de los dones espirituales y la certeza de la resurrección.",
            audience = "La iglesia en la cosmopolita ciudad de Corinto.",
            doctrinalPurpose = "Enseñar que el amor es el camino más excelente y que el cuerpo del creyente es templo del Espíritu Santo."
        ),
        "2 Corintios" to BookTheologicalProfile(
            author = "Pablo apóstol",
            dateOrPeriod = "c. 55-56 d.C. desde Macedonia",
            centralTheme = "La defensa del ministerio apostólico, el poder divino que se perfecciona en la debilidad y el consuelo en las pruebas.",
            audience = "La iglesia en Corinto.",
            doctrinalPurpose = "Demostrar que 'llevamos este tesoro en vasos de barro' para que la gloria pertenezca a Dios y no a los hombres."
        ),
        "Gálatas" to BookTheologicalProfile(
            author = "Pablo apóstol",
            dateOrPeriod = "c. 48-49 d.C.",
            centralTheme = "La libertad cristiana en la gracia frente al legalismo de las obras; el fruto del Espíritu Santo.",
            audience = "Las iglesias de Galacia meridional.",
            doctrinalPurpose = "Defender la pureza del Evangelio: somos justificados por la fe en Cristo y no por los ritos de la Ley."
        ),
        "Efesios" to BookTheologicalProfile(
            author = "Pablo apóstol (epístola de la prisión)",
            dateOrPeriod = "c. 60-62 d.C. desde Roma",
            centralTheme = "La Iglesia como cuerpo de Cristo enriquecido con bendiciones celestiales y equipado con la armadura de Dios.",
            audience = "Creyentes en Éfeso y Asia Menor.",
            doctrinalPurpose = "Enseñar que somos salvos por gracia por medio de la fe para buenas obras, reconciliados en un solo cuerpo nuevo."
        ),
        "Filipenses" to BookTheologicalProfile(
            author = "Pablo apóstol (epístola de la prisión)",
            dateOrPeriod = "c. 61 d.C. desde Roma",
            centralTheme = "El gozo inquebrantable en Cristo independientemente de las circunstancias y la humildad sacrificial de Jesús.",
            audience = "La iglesia de Filipos en Macedonia.",
            doctrinalPurpose = "Exhortar a tener el mismo sentir que hubo en Cristo Jesús y afirmar: 'Todo lo puedo en Cristo que me fortalece'."
        ),
        "Colosenses" to BookTheologicalProfile(
            author = "Pablo apóstol (epístola de la prisión)",
            dateOrPeriod = "c. 60-62 d.C. desde Roma",
            centralTheme = "La supremacía y suficiencia absoluta de Cristo como Cabeza de la creación y de la Iglesia frente a falsas filosofías.",
            audience = "La iglesia en Colosas.",
            doctrinalPurpose = "Establecer que en Cristo habita corporalmente toda la plenitud de la Deidad y en Él estamos completos."
        ),
        "1 Tesalonicenses" to BookTheologicalProfile(
            author = "Pablo apóstol",
            dateOrPeriod = "c. 50-51 d.C. desde Corinto",
            centralTheme = "Santidad en la vida cotidiana, consuelo ante la muerte de los creyentes y la gloriosa segunda venida de Cristo.",
            audience = "La joven iglesia en Tesalónica.",
            doctrinalPurpose = "Inspirar a vivir vigilantes en fe, amor y esperanza mientras esperamos el regreso del Señor de los cielos."
        ),
        "2 Tesalonicenses" to BookTheologicalProfile(
            author = "Pablo apóstol",
            dateOrPeriod = "c. 51-52 d.C.",
            centralTheme = "Clarificación sobre los tiempos del fin, la victoria final sobre el mal y el llamado al trabajo fiel sin desmayar.",
            audience = "Creyentes en Tesalónica confundidos por falsas enseñanzas escatológicas.",
            doctrinalPurpose = "Animar la perseverancia sin caer en ociosidad mientras aguardamos la revelación triunfal de Jesucristo."
        ),
        "1 Timoteo" to BookTheologicalProfile(
            author = "Pablo apóstol",
            dateOrPeriod = "c. 63-65 d.C.",
            centralTheme = "Organización de la iglesia local, requisitos para obispos y diáconos, y salvaguarda de la sana doctrina.",
            audience = "Timoteo, joven pastor en Éfeso.",
            doctrinalPurpose = "Instruir cómo deben conducirse los creyentes en la casa de Dios, columna y baluarte de la verdad."
        ),
        "2 Timoteo" to BookTheologicalProfile(
            author = "Pablo apóstol (su carta testamentaria final)",
            dateOrPeriod = "c. 66-67 d.C. desde la prisión en Roma",
            centralTheme = "Fidelidad hasta el final de la carrera, inspiración de las Escrituras y valentía en el ministerio.",
            audience = "Timoteo, su amado hijo espiritual.",
            doctrinalPurpose = "Transmitir la antorcha del Evangelio: retener la forma de las sanas palabras y predicar a tiempo y fuera de tiempo."
        ),
        "Tito" to BookTheologicalProfile(
            author = "Pablo apóstol",
            dateOrPeriod = "c. 63-65 d.C.",
            centralTheme = "La gracia de Dios que nos enseña a renunciar a la impiedad y vivir de manera sobria y piadosa.",
            audience = "Tito, líder pastoral en la isla de Creta.",
            doctrinalPurpose = "Conectar directamente la doctrina pura con las buenas obras en el hogar y en la sociedad."
        ),
        "Filemón" to BookTheologicalProfile(
            author = "Pablo apóstol",
            dateOrPeriod = "c. 60-62 d.C.",
            centralTheme = "Perdón, reconciliación fraternal y la transformación del esclavo Onésimo en un hermano amado en Cristo.",
            audience = "Filemón, líder cristiano en Colosas.",
            doctrinalPurpose = "Demostrar cómo la gracia del Evangelio supera barreras sociales e inspira perdón genuino."
        ),
        "Hebreos" to BookTheologicalProfile(
            author = "Desconocido (trad. atribuido a Pablo, Bernabé o Apolos)",
            dateOrPeriod = "c. 65-68 d.C.",
            centralTheme = "La superioridad absoluta de Jesucristo como Sumo Sacerdote eterno sobre el antiguo pacto y el llamado a la perseverancia en la fe.",
            audience = "Creyentes de origen judío tentados a retroceder al judaísmo ritualista ante la persecución.",
            doctrinalPurpose = "Presentar a Jesús como Mediador de un pacto mejor y animar a correr con paciencia la carrera fijando los ojos en Él."
        ),
        "Santiago" to BookTheologicalProfile(
            author = "Jacobo / Santiago (hermano del Señor)",
            dateOrPeriod = "c. 45-48 d.C. (la epístola más temprana del NT)",
            centralTheme = "La fe viva manifestada en obras: control de la lengua, paciencia en las pruebas y cuidado del prójimo necesitado.",
            audience = "Las doce tribus cristianas en la dispersión.",
            doctrinalPurpose = "Demostrar que una fe sin obras está muerta y que la verdadera religión atiende a huérfanos y viudas en sus tribulaciones."
        ),
        "1 Pedro" to BookTheologicalProfile(
            author = "Simón Pedro apóstol",
            dateOrPeriod = "c. 63-64 d.C. desde Roma",
            centralTheme = "La viva esperanza en medio del sufrimiento, sacerdocio real de los santos y sumisión ejemplar.",
            audience = "Creyentes perseguidos esparcidos por el Asia Menor.",
            doctrinalPurpose = "Fortalecer la confianza en la herencia incorruptible guardada en los cielos para los que son guardados por el poder de Dios."
        ),
        "2 Pedro" to BookTheologicalProfile(
            author = "Simón Pedro apóstol",
            dateOrPeriod = "c. 65-67 d.C.",
            centralTheme = "Crecimiento en la gracia y el conocimiento de Cristo, denuncia de falsos maestros y certeza de cielos nuevos y tierra nueva.",
            audience = "La Iglesia frente a las corrientes heréticas.",
            doctrinalPurpose = "Exhortar a añadir a la fe virtud, y a la virtud conocimiento, esperando el día del Señor con vidas santas y piadosas."
        ),
        "1 Juan" to BookTheologicalProfile(
            author = "Juan el apóstol",
            dateOrPeriod = "c. 85-90 d.C. desde Éfeso",
            centralTheme = "Seguridad de la salvación, comunión con Dios en la luz, amor fraternal y victoria sobre el mundo.",
            audience = "Hijos espirituales de las iglesias de Asia Menor.",
            doctrinalPurpose = "Brindar certidumbre plena: 'Estas cosas os he escrito... para que sepáis que tenéis vida eterna'."
        ),
        "2 Juan" to BookTheologicalProfile(
            author = "Juan el anciano",
            dateOrPeriod = "c. 85-90 d.C.",
            centralTheme = "Andar en la verdad y en el amor sin dar cabida al engaño ni a falsas doctrinas.",
            audience = "La señora elegida y sus hijos (iglesia local).",
            doctrinalPurpose = "Guardar el equilibrio indisoluble entre el amor genuino y la lealtad a la doctrina de Cristo."
        ),
        "3 Juan" to BookTheologicalProfile(
            author = "Juan el anciano",
            dateOrPeriod = "c. 85-90 d.C.",
            centralTheme = "Hospitalidad cristiana, cooperación con la verdad y advertencia contra el orgullo autoritario en el liderazgo.",
            audience = "Gayo, fiel cooperador en el Evangelio.",
            doctrinalPurpose = "Elogiar la generosidad que apoya a los obreros de la fe y reprender el espíritu divisivo."
        ),
        "Judas" to BookTheologicalProfile(
            author = "Judas (hermano de Santiago y del Señor)",
            dateOrPeriod = "c. 65-70 d.C.",
            centralTheme = "Contender ardientemente por la fe una vez dada a los santos frente a la inmoralidad encubierta.",
            audience = "Los llamados y santificados en Dios Padre.",
            doctrinalPurpose = "Edificarse sobre la santísima fe y confiar en Aquel que es poderoso para guardarnos sin caída."
        ),
        "Apocalipsis" to BookTheologicalProfile(
            author = "Juan el apóstol",
            dateOrPeriod = "c. 95 d.C. desde la isla de Patmos",
            centralTheme = "La revelación de Jesucristo triunfante sobre el mal, el juicio final y la gloria de la Nueva Jerusalén.",
            audience = "Las siete iglesias de Asia y la Iglesia universal perseguida.",
            doctrinalPurpose = "Infundir consuelo y esperanza inquebrantable asegurando que el Cordero ha vencido y reinará por los siglos de los siglos."
        )
    )

    /**
     * Looks up an exact matching context from curated initial data if present.
     */
    fun findCuratedContext(book: String, chapter: Int, verse: String): String? {
        val ref1 = "$book $chapter:$verse".trim()
        val ref2 = "$book $chapter : $verse".trim()
        val match = InitialVersesData.verses.firstOrNull {
            it.reference.equals(ref1, ignoreCase = true) ||
            it.reference.replace(" ", "").equals(ref2.replace(" ", ""), ignoreCase = true)
        }
        return match?.context?.takeIf { it.isNotBlank() }
    }

    /**
     * Generates a grounded, high-value theological and historical context offline
     * using the canonical biblical profile of the book and specific chapter hints.
     */
    fun getLocalContext(
        bookName: String,
        chapter: Int = 1,
        verse: String = "1",
        verseText: String = ""
    ): String {
        // 1. Check curated exact matches first
        val curated = findCuratedContext(bookName, chapter, verse)
        if (!curated.isNullOrBlank()) {
            return curated
        }

        val profile = bookProfiles[bookName] ?: BibleCatalog.findBook(bookName)?.let { b ->
            BookTheologicalProfile(
                author = "Autor inspirado por el Espíritu Santo",
                dateOrPeriod = "Período canónico bíblico",
                centralTheme = "Revelación del pacto y los propósitos de Dios para su pueblo.",
                audience = "El pueblo creyente en su contexto original.",
                doctrinalPurpose = "Instruir en justicia y proclamar la fidelidad eterna de Dios."
            )
        } ?: BookTheologicalProfile(
            author = "Autor bíblico",
            dateOrPeriod = "Época bíblica",
            centralTheme = "Revelación divina y edificación espiritual.",
            audience = "La comunidad de fe.",
            doctrinalPurpose = "Instruir en la sana doctrina y el amor de Dios."
        )

        val bookClean = bookName.trim()
        val chapterDesc = getChapterSpecificContext(bookClean, chapter)

        return buildString {
            append("Escrito por ${profile.author} (${profile.dateOrPeriod}), dirigido a ${profile.audience}. ")
            if (chapterDesc.isNotBlank()) {
                append("$chapterDesc ")
            }
            append("Propósito teológico central: ${profile.centralTheme} ")
            append("Enseñanza moral: ${profile.doctrinalPurpose}")
        }
    }

    private fun getChapterSpecificContext(book: String, chapter: Int): String {
        return when (book) {
            "Génesis" -> when (chapter) {
                1, 2 -> "Relato fundacional de la creación cósmica y la consagración del ser humano hecho a imagen de Dios."
                3 -> "La caída del hombre, la entrada del pecado en el mundo y el protoevangelio de redención futura (Gn 3:15)."
                12 -> "El llamamiento soberano de Abram y la institución del pacto abrahámico que bendecirá a todas las naciones."
                22 -> "La prueba suprema de fe de Abraham en el monte Moriah, tipología profética de la provisión del Cordero."
                50 -> "Conclusión de la historia de los patriarcas, exaltando la providencia divina que transforma el mal humano en bien."
                else -> "Capítulo $chapter del libro de los orígenes de la fe y el linaje de la promesa."
            }
            "Salmos" -> when (chapter) {
                23 -> "Salmo de profunda confianza pastoral donde David celebra a Jehová como el Buen Pastor que nada deja faltar."
                91 -> "Cántico de amparo soberano bajo la sombra del Omnipotente, garantizando refugio y seguridad en la aflicción."
                103 -> "Himno de alabanza a las inagotables misericordias y perdón de Dios hacia la fragilidad humana."
                119 -> "Magna oda alfabética enalteciendo la pureza, suficiencia y deleite en los mandamientos de la Ley de Dios."
                121 -> "Cántico gradual de peregrinación recordando que nuestro pronto auxilio viene de Jehová que hizo los cielos y la tierra."
                else -> "Salmo $chapter dedicado a la alabanza, la meditación orante y el descanso en la soberanía divina."
            }
            "Isaías" -> when (chapter) {
                40 -> "Apertura de la sección de consolación: 'Consolaos, pueblo mío', exaltando la grandeza incomparable del Creador."
                53 -> "El cuarto cántico del Siervo del Señor, profecía cumbre del sacrificio expiatorio y vicario de Cristo por nuestras rebeliones."
                55 -> "Invitación universal y gratuita a acudir a las aguas vivas y buscar a Dios mientras puede ser hallado."
                else -> "Capítulo $chapter del profeta mesiánico proclamando santidad, juicio purificador y esperanza redentora."
            }
            "Mateo" -> when (chapter) {
                5, 6, 7 -> "El Sermón del Monte, magna constitución ética del Reino de los Cielos dictada por Jesús (bienaventuranzas y amor al prójimo)."
                28 -> "La resurrección triunfal de Jesucristo y la Gran Comisión de discipular a todas las naciones con su presencia continua."
                else -> "Capítulo $chapter del Evangelio del Rey prometido que cumple las Escrituras proféticas."
            }
            "Juan" -> when (chapter) {
                1 -> "Prólogo teológico del Verbo eterno que se hizo carne y habitó lleno de gracia y de verdad entre nosotros."
                3 -> "El diálogo nocturno con Nicodemo sobre la necesidad imperiosa del nuevo nacimiento y el amor infinito de Dios al enviar a su Hijo."
                14 -> "Discurso de despedida en el aposento alto: consuelo a los discípulos, promesa del Espíritu Santo y Jesús como el Camino, la Verdad y la Vida."
                else -> "Capítulo $chapter enfocado en las palabras y señales que revelan la gloria y divinidad del Hijo de Dios."
            }
            "Romanos" -> when (chapter) {
                8 -> "Cumbre teológica de la vida en el Espíritu: ninguna condenación para los que están en Cristo y la certeza de que nada nos separará de su amor."
                12 -> "Llamado apremiante a presentar nuestros cuerpos como sacrificio vivo y santo, renovando el entendimiento para no amoldarse al mundo."
                else -> "Capítulo $chapter del desarrollo magistral de la justificación por gracia mediante la fe en Jesucristo."
            }
            "Filipenses" -> when (chapter) {
                2 -> "El himno cristológico de kenosis: la humildad abnegada de Cristo que siendo Dios se despojó a sí mismo tomando forma de siervo."
                4 -> "Exhortación al regocijo continuo, la oración que aleja la ansiedad y la paz de Dios que sobrepasa todo entendimiento."
                else -> "Capítulo $chapter sobre el gozo interior sostenido por la comunión con el Señor resucitado."
            }
            "Hebreos" -> when (chapter) {
                11 -> "El gran monumento a la fe: galería de testigos del Antiguo Testamento que perseveraron mirando al Invisible."
                else -> "Capítulo $chapter demostrando la preeminencia sacerdotal y redentora de Jesucristo por encima de todo rito terrenal."
            }
            else -> ""
        }
    }

    /**
     * Generates a deep, comprehensive 3-part theological and historical exegesis
     * structured with clarity for "Profundizar con IA" / "Llenar con IA".
     */
    fun getDeepTheologicalExegesis(
        bookName: String,
        chapter: Int = 1,
        verse: String = "1",
        verseText: String = "",
        bibleVersion: String = "RVR1960"
    ): String {
        val bookClean = bookName.trim()
        val profile = bookProfiles[bookClean] ?: BibleCatalog.findBook(bookClean)?.let { b ->
            BookTheologicalProfile(
                author = "Autor bíblico inspirado por el Espíritu Santo",
                dateOrPeriod = "Período canónico bíblico",
                centralTheme = "Revelación del pacto y los propósitos redentores de Dios para su pueblo.",
                audience = "El pueblo de Dios en su contexto histórico original.",
                doctrinalPurpose = "Instruir en justicia, fortalecer la fe y proclamar la fidelidad de Dios."
            )
        } ?: BookTheologicalProfile(
            author = "Autor bíblico",
            dateOrPeriod = "Época bíblica",
            centralTheme = "Revelación divina y edificación espiritual.",
            audience = "La comunidad de fe.",
            doctrinalPurpose = "Instruir en la sana doctrina y el amor de Dios."
        )

        val chapterContext = getChapterSpecificContext(bookClean, chapter)
        val curatedSummary = findCuratedContext(bookClean, chapter, verse)
        val cleanVerseText = verseText.removeSurrounding("«", "»").trim()

        return buildString {
            append("📜 CONTEXTO HISTÓRICO Y LITERARIO\n")
            append("Escrito por ${profile.author} (${profile.dateOrPeriod}), dirigido a ${profile.audience}. ")
            if (chapterContext.isNotBlank()) {
                append("$chapterContext ")
            }
            append("Dentro del marco general del libro: ${profile.centralTheme}\n\n")

            append("✝️ EXÉGESIS Y ANÁLISIS TEOLÓGICO\n")
            if (!curatedSummary.isNullOrBlank()) {
                append("$curatedSummary ")
            }
            if (cleanVerseText.isNotBlank()) {
                append("El pasaje declara: «$cleanVerseText». En el plan redentor de las Escrituras, esta verdad afirma la soberanía de Dios y revela su carácter inmutable, invitando al creyente a descansar con confianza en la suficiencia de su Palabra.\n\n")
            } else {
                append("Este pasaje revela fundamentos esenciales de la doctrina bíblica, proclamando la fidelidad de Dios y la verdad revelada en las Sagradas Escrituras.\n\n")
            }

            append("🕊️ APLICACIÓN DEVOCIONAL Y PRÁCTICA\n")
            append("${profile.doctrinalPurpose} Nos exhorta hoy a vivir con fe firme, perseverancia y gratitud, aplicando la verdad de Dios a los desafíos diarios y confiando plenamente en sus promesas eternas.")
        }
    }
}
