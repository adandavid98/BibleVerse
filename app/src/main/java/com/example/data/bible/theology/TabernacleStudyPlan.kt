package com.example.data.bible.theology

import com.example.data.bible.ReadingPlanDay
import com.example.data.bible.theology.TheologyStudyHelper.createStudyDay
import com.example.data.bible.theology.TheologyStudyHelper.seg

/**
 * Estudio Bíblico Teológico y Exegético: Cristo en las Sombras del A.T. (15 Días).
 * Análisis riguroso, accesible y sistemático de la tipología del Tabernáculo,
 * los utensilios sagrados, el Yom Kipur y el Sumo Sacerdocio eterno en la Epístola a los Hebreos.
 */
object TabernacleStudyPlan {

    val days: List<ReadingPlanDay> by lazy {
        listOf(
            // DÍA 1
            createStudyDay(
                dayNumber = 1,
                title = "Día 1: Tipología Bíblica: Sombras que Anuncian la Realidad",
                passagesSummary = "Éxodo 25:8-9; Hebreos 8:1-6; Colosenses 2:16-17",
                primaryBookId = 2,
                primaryChapter = 25,
                passages = listOf(seg(2, "Éxodo", 25), seg(58, "Hebreos", 8), seg(51, "Colosenses", 2)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En el monte Sinaí, Dios ordenó a Moisés una instrucción arquitectónica cargada de asombrosa trascendencia redentora: «Y harán un santuario para mí, y habitaré en medio de ellos. Conforme a todo lo que yo te muestre, el diseño del tabernáculo, y el diseño de todos sus utensilios, así lo haréis» (Éxodo 25:8-9). Dios no dejó ningún detalle al capricho o la inventiva estética humana; Moisés debía construir estrictamente según el «modelo» («tabnit» en hebreo, «typos» en el griego de Hebreos 8:5) revelado en la cumbre del monte.
                    
                    En la teología bíblica, la tipología es el estudio de aquellas personas, instituciones, objetos y ceremonias del Antiguo Testamento que fueron diseñadas providencialmente por Dios para prefigurar anticipadamente una realidad espiritual superior en el Nuevo Pacto. El objeto del Antiguo Testamento es el «tipo» (la sombra preliminar); y la persona o realidad consumada en Cristo es el «antitipo» (la sustancia verdadera y definitiva).
                    
                    Como expone el apóstol Pablo en Colosenses 2:17 al referirse a los ritos y fiestas levíticas: «todo lo cual es sombra de lo que ha de venir; pero el cuerpo es de Cristo». Una sombra proyectada en el suelo anuncia con certeza que alguien real se aproxima; cuando la persona viva y radiante se hace presente, la sombra cumple su propósito pedagógico.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    La epístola a los Hebreos es el comentario bíblico por excelencia del libro de Éxodo y Levítico. El autor de Hebreos demuestra que el tabernáculo terrenal del desierto era una copia terrenal y temporal del verdadero Santuario celestial (Hebreos 8:2).
                    
                    Cada tablón de acacia revestido de oro, cada cortina tejida con primor, cada gota de sangre derramada sobre el altar y cada destello de incienso quemado señalaban con dedo profético al Señor Jesucristo. En Él, el Creador del universo «puso su tabernáculo entre nosotros» (Juan 1:14: «eskēnōsen en hēmin»). El Tabernáculo es el Evangelio ilustrado en tres dimensiones, desplegando la majestad, el perdón y el acceso soberano a la comunión con el Padre.
                """,
                doctrinalThesis = "El Tabernáculo y el culto sacerdotal del Antiguo Testamento fueron sombras tipológicas (typoi) instituidas por Dios para prefigurar la obra perfecta y definitiva de Jesucristo.",
                exegeticalAnalysis = "El término «typos» en Hebreos 8:5 fundamenta el principio hermenéutico de que las ceremonias mosaicas derivan su valor y significado exclusivo de la realidad consumada de Cristo («el cuerpo es de Cristo», Col 2:17)."
            ),

            // DÍA 2
            createStudyDay(
                dayNumber = 2,
                title = "Día 2: La Puerta Única del Atrio: El Único Camino al Padre",
                passagesSummary = "Éxodo 27:9-19; Juan 10:7-10; Juan 14:1-6",
                primaryBookId = 2,
                primaryChapter = 27,
                passages = listOf(seg(2, "Éxodo", 27), seg(43, "Juan", 10), seg(43, "Juan", 14)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    Al contemplar el campamento de Israel en el desierto, con sus doce tribus acampadas en estricto orden militar, en el centro mismo se levantaba el atrio del Tabernáculo. El perímetro del atrio (de cien codos de longitud por cincuenta de anchura) estaba completamente cercado por una alta cortina de lino fino blanco torcido de dos metros y medio de altura sostenida por columnas de bronce y capiteles de plata (Éxodo 27:9-18).
                    
                    Aquella cerca inmaculada proclamaba a viva voz la justicia inquebrantable y la santidad de Dios: ningún ser humano pecador podía saltar la valla ni entrar por cualquier esquina. Sin embargo, en el lado oriental del atrio, Dios dispuso una sola y espaciosa abertura de veinte codos de ancho (unos nueve metros): la Puerta del Atrio. 
                    
                    Esta puerta consistía en una hermosa cortina tejida de cuatro materiales específicos: azul, púrpura, carmesí y lino fino torcido (Éxodo 27:16). No había dos ni tres puertas; había una sola puerta de acceso para entrar a la presencia de Dios.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    La simbología cristológica de esta puerta es de una belleza deslumbrante:
                    1. Azul: El origen celestial del Hijo de Dios, quien descendió de la gloria del cielo.
                    2. Púrpura: Su dignidad y realeza soberana como Rey de reyes y Señor de señores.
                    3. Carmesí: El color de la sangre sacrificial de su sufrimiento expiatorio en la cruz.
                    4. Lino fino blanco: Su humanidad pura, inmaculada e intachable en justicia perfecta.
                    
                    Los cuatro colores se entrelazan de forma inseparable para proclamar la Persona del Mediador. Cristo es el cumplimiento absoluto de esta puerta: «Yo soy la puerta; el que por mí entrare, será salvo; y entrará, y saldrá, y hallará pastos» (Juan 10:9). Frente al pluralismo religioso del mundo caído que afirma que «todos los caminos llevan a Dios», las Escrituras declaran con santo amor que solo existe un camino de reconciliación: «Yo soy el camino, y la verdad, y la vida; nadie viene al Padre, sino por mí» (Juan 14:6).
                """,
                doctrinalThesis = "Así como el Tabernáculo poseía una sola puerta espaciosa para entrar a la presencia de Dios, Jesucristo es el único y suficiente Mediador entre Dios y los hombres.",
                exegeticalAnalysis = "La exclusividad y amplitud de la puerta oriental del atrio (Éx 27:16) tipifica la universalidad de la invitación del Evangelio a toda persona mediante la única puerta que es Cristo (Jn 10:9)."
            ),

            // DÍA 3
            createStudyDay(
                dayNumber = 3,
                title = "Día 3: El Altar de Bronce: La Expiación por Sustitución",
                passagesSummary = "Éxodo 27:1-8; Levítico 1:1-9; Hebreos 10:1-10",
                primaryBookId = 2,
                primaryChapter = 27,
                passages = listOf(seg(2, "Éxodo", 27), seg(3, "Levítico", 1), seg(58, "Hebreos", 10)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    Lo primero que encontraba cualquier israelita al cruzar la puerta del atrio no era un trono de gloria ni un libro de leyes morales; era el monumental Altar de Bronce («Mizbeach ha'Olah», el altar de la ofrenda quemada, Éxodo 27:1-8). Construido de madera de acacia incorruptible recubierta enteramente de grueso bronce para resistir el fuego devorador, este altar ardía de día y de noche de manera ininterrumpida (Levítico 6:13).
                    
                    En este lugar se ejecutaba la ley inmutable de la justicia divina: «sin derramamiento de sangre no se hace remisión» (Hebreos 9:22). Cuando el oferente traía un cordero sin defecto ante el sacerdote, colocaba sus manos firmemente sobre la cabeza de la víctima inocente (Levítico 1:4: «samak yado»). Este rito solemne de la imposición de manos representaba la transferencia imputativa de la culpa del pecador al animal sustituto. Acto seguido, el animal era degollado, su sangre derramada y rociada al pie del altar, y su cuerpo consumido por el fuego. La ira justa de Dios contra el pecado caía sobre el sustituto, y el pecador quedaba perdonado.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    A pesar de su trascendencia pedagógica, los miles de carneros y toros inmolados en el altar de bronce jamás pudieron quitar ontológicamente la culpa humana; solo la cubrían temporalmente («kapper», expiación como cobertura), pues como señala Hebreos 10:4: «la sangre de los toros y de los machos cabríos no puede quitar los pecados».
                    
                    Todos aquellos altares humeantes eran flechas que apuntaban a la colina del Gólgota. Cuando Juan el Bautista vio a Jesús aproximarse a las aguas del Jordán, no exclamó «he aquí un gran profeta o filósofo», sino: «¡He aquí el Cordero de Dios, que quita el pecado del mundo!» (Juan 1:29). 
                    
                    En la cruz, Jesucristo fue simultáneamente el Altar inquebrantable, el Sacerdote perfecto y el Cordero inocente. Allí absorbió el fuego ardiente de la santa ira del Padre contra nuestra rebelión, extinguiendo la condenación para siempre. Nadie puede avanzar hacia la comunión íntima con Dios sin pasar primero por el Altar de la cruz.
                """,
                doctrinalThesis = "El Altar de Bronce tipifica el principio inmutable de la expiación sustitutiva consumada plenamente en el sacrificio de Jesucristo en la cruz del Calvario.",
                exegeticalAnalysis = "El rito de imposición de manos («samak») en Levítico 1:4 prefigura la doctrina bíblica de la doble imputación: nuestro pecado cargado sobre Cristo en la cruz y su justicia acreditada a nosotros."
            ),

            // DÍA 4
            createStudyDay(
                dayNumber = 4,
                title = "Día 4: El Lavacro de Bronce: La Santificación y Purificación Diaria",
                passagesSummary = "Éxodo 30:17-21; Tito 3:4-7; Juan 13:5-10",
                primaryBookId = 2,
                primaryChapter = 30,
                passages = listOf(seg(2, "Éxodo", 30), seg(56, "Tito", 3), seg(43, "Juan", 13)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    Avanzando en línea recta desde el altar de bronce hacia la entrada del santuario interior, se hallaba el segundo utensilio del atrio: el Lavacro de Bronce o Fuente de Agua («Kiyyor», Éxodo 30:17-21). A diferencia de todos los demás muebles del Tabernáculo, Dios no dio medidas específicas para su longitud o anchura, sugiriendo una fuente inagotable y sobreabundante de purificación.
                    
                    Un detalle arqueológico e histórico fascinante se revela en Éxodo 38:8: el lavacro fue fundido enteramente a partir de los espejos de bronce pulido de las mujeres piadosas que velaban a la puerta del tabernáculo de reunión. El espejo revela la suciedad y las imperfecciones del rostro humano; pero mientras el espejo de la ley solo puede mostrar la mancha sin poder limpiarla, el agua pura del lavacro lavaba eficazmente toda impureza.
                    
                    La ordenanza divina era taxativa: cada sacerdote, aun estando ya ungido y consagrado, debía lavarse las manos y los pies en el lavacro cada vez que iba a entrar al Lugar Santo o servir en el altar, «para que no mueran» (Éxodo 30:20). Al caminar descalzos por la arena del desierto, el polvo inevitable del camino manchaba sus extremidades.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    El Lavacro de Bronce enseña la diferencia crucial entre la regeneración inicial (la justificación de una vez y para siempre) y la santificación progresiva (la purificación continua de la comunión diaria). 
                    
                    Jesús explicó con absoluta claridad esta distinción en el lavatorio de pies de la última cena (Juan 13:10): «El que está lavado, no necesita sino lavarse los pies, pues está todo limpio». Quien ha nacido de nuevo por el lavamiento de la regeneración (Tito 3:5) no necesita volver a ser justificado cada día; pero al caminar en este mundo caído, el polvo del pecado diario contamina su comunión íntima con Dios.
                    
                    El agua del lavacro tipifica la Palabra viva de Dios aplicada por el Espíritu Santo: «Cristo amó a la iglesia, y se entregó a sí mismo por ella, para santificarla, habiéndola purificado en el lavamiento del agua por la palabra» (Efesios 5:25-26). Acudimos diariamente a la presencia de Dios examinándonos en el espejo de las Escrituras y confesando nuestros pecados para ser limpiados de toda maldad (1 Juan 1:9).
                """,
                doctrinalThesis = "El Lavacro de Bronce tipifica la purificación continua del creyente mediante la Palabra de Dios y la confesión, necesaria para mantener una comunión viva e ininterrumpida con el Padre.",
                exegeticalAnalysis = "La distinción entre el baño completo inicial («louō») y el lavamiento diario de pies («niptō») en Juan 13:10 ilustra la diferencia teológica entre la justificación definitiva y la santificación diaria."
            ),

            // DÍA 5
            createStudyDay(
                dayNumber = 5,
                title = "Día 5: El Candelabro de Oro Puro: Cristo, la Luz del Mundo",
                passagesSummary = "Éxodo 25:31-40; Juan 8:12; Apocalipsis 1:12-20",
                primaryBookId = 2,
                primaryChapter = 25,
                passages = listOf(seg(2, "Éxodo", 25), seg(43, "Juan", 8), seg(66, "Apocalipsis", 1)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    Al traspasar el primer velo y entrar en el Lugar Santo («Kodesh»), el sacerdote se encontraba en una cámara sagrada cubierta por cuatro capas de cortinas que impedían por completo la entrada de la luz natural del sol. No había ventanas ni rendijas. En este aposento, toda la iluminación dependía exclusivamente de un mueble de esplendor inigualable situado al lado izquierdo (al sur): la Menorá o Candelabro de Oro Puro (Éxodo 25:31-40).
                    
                    La construcción de la Menorá encerraba tres misterios teológicos profundos:
                    1. Oro Puro de un Talento (unos 34 kilogramos): A diferencia de los otros muebles que eran de madera recubierta, la Menorá era de oro puro macizo, proclamando la divinidad intrínseca e increada de su significado.
                    2. Labrado a Martillo («miqshah»): No fue fundido en un molde prefabricado; un hábil artesano tomó un bloque macizo de oro y lo forjó a base de golpes continuos de martillo hasta esculpir el tallo central y sus seis brazos laterales con cálices en forma de flores de almendro, manzanas y flores. Tipifica los sufrimientos del Siervo de Dios quebrantado por nuestros pecados.
                    3. Aceite Puro de Olivo Machacado: Las siete lámparas ardían incesantemente alimentadas por aceite virgen obtenido triturando las olivas en el mortero (Éxodo 27:20), símbolo del Espíritu Santo que llena y alumbra a la Iglesia.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    En la fiesta de los Tabernáculos, en el patio del templo donde ardían cuatro inmensos candelabros, Jesús alzó su voz y proclamó el cumplimiento antitípico de la Menorá: «Yo soy la luz del mundo; el que me sigue, no andará en tinieblas, sino que tendrá la luz de la vida» (Juan 8:12). 
                    
                    En las cosas de Dios no podemos guiarnos por la luz natural de la razón humana caída, la filosofía secular o las opiniones del mundo. En el santuario de la presencia de Dios solo alumbra la luz de Cristo revelada por el Espíritu Santo.
                    
                    Asimismo, en Apocalipsis 1:12-20, el apóstol Juan contempla a Cristo resucitado y glorificado caminando en medio de siete candeleros de oro, los cuales representan a las iglesias locales. La Iglesia no posee luz propia; refleja como un espejo la luz de su Señor resucitado. Si una congregación apaga el Evangelio de Cristo, pierde su aceite, se sume en la oscuridad y su candelero es quitado de su lugar.
                """,
                doctrinalThesis = "El Candelabro de Oro Puro (Menorá) tipifica a Jesucristo como la única y verdadera Luz divina que disipa las tinieblas del pecado e ilumina a su Iglesia mediante el Espíritu Santo.",
                exegeticalAnalysis = "El diseño forjado a martillo («miqshah») en Éxodo 25:31 y el aceite de oliva prensado anticipan el quebrantamiento voluntario de Cristo para derramar la luz vivificadora del Espíritu a su pueblo."
            ),

            // DÍA 6
            createStudyDay(
                dayNumber = 6,
                title = "Día 6: La Mesa de los Panes de la Proposición: Comunión y Sustento",
                passagesSummary = "Éxodo 25:23-30; Levítico 24:5-9; Juan 6:32-51",
                primaryBookId = 2,
                primaryChapter = 25,
                passages = listOf(seg(2, "Éxodo", 25), seg(3, "Levítico", 24), seg(43, "Juan", 6)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    Frente a la Menorá, en el lado norte del Lugar Santo, se situaba la Mesa de los Panes de la Proposición (Éxodo 25:23-30). Fabricada de madera de acacia incorruptible recubierta de oro purísimo y rodeada por una doble moldura o corona de oro («zer zahav»), esta mesa sostenía permanentemente doce panes horneados de flor de harina colocados en dos hileras de seis (Levítico 24:5-6).
                    
                    El nombre hebreo de estos panes es sumamente elocuente: «Léjem haPanim», que literalmente se traduce como «el Pan del Rostro» o «el Pan de la Presencia». Cada uno de los doce panes representaba a una de las doce tribus de Israel morando en comunión ininterrumpida delante del rostro de Dios.
                    
                    Sobre cada hilera de panes se colocaba incienso puro como ofrenda encendida para Jehová (Levítico 24:7). Cada sábado, los sacerdotes retiraban los panes de la semana anterior y colocaban panes frescos recién horneados, comiendo los panes retirados en el lugar santo como alimento sagrado del pacto. El pueblo de Dios permanecía perpetuamente representado y alimentado ante los ojos del Todopoderoso.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    La Mesa del Pan tipifica de manera extraordinaria a Cristo como el Alimento vital del alma y la comunión inquebrantable de la mesa celestial. Tras la multiplicación de los panes en Galilea, el Señor Jesús declaró sin rodeos: «Yo soy el pan de vida; el que a mí viene, nunca tendrá hambre; y el que en mí cree, no tendrá sed jamás... Yo soy el pan vivo que descendió del cielo; si alguno comiere de este pan, vivirá para siempre» (Juan 6:35, 51).
                    
                    Comer el pan de Cristo significa apropiarnos de Él por fe, alimentando nuestra mente y afectos con su verdad, su sacrificio y su gracia. En la mesa terrenal del mundo, los hombres buscan saciar el vacío de su alma con placeres que se descomponen; pero en la Mesa de Cristo hallamos el sustento celestial que nutre el alma para la eternidad. 
                    
                    Además, la corona de oro que rodeaba la mesa garantizaba que ningún pan pudiera caer al suelo: los doce panes estaban seguros bajo la custodia divina. Ni uno solo de los redimidos de Cristo se perderá; todos son preservados en su presencia eterna.
                """,
                doctrinalThesis = "La Mesa de los Panes de la Proposición tipifica a Cristo como el Pan de Vida (Léjem haPanim) que sustenta espiritualmente a sus redimidos y los mantiene en comunión perpetua con Dios.",
                exegeticalAnalysis = "El término «Léjem haPanim» en Levítico 24:8 une la representación de las doce tribus ante el rostro de Dios con la seguridad pactual inmutable que Cristo ofrece a su Iglesia (Jn 6:37)."
            ),

            // DÍA 7
            createStudyDay(
                dayNumber = 7,
                title = "Día 7: El Altar del Incienso: La Intercesión Perpetua de Cristo",
                passagesSummary = "Éxodo 30:1-10, 34-38; Hebreos 7:23-25; Apocalipsis 8:3-4",
                primaryBookId = 2,
                primaryChapter = 30,
                passages = listOf(seg(2, "Éxodo", 30), seg(58, "Hebreos", 7), seg(66, "Apocalipsis", 8)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En el extremo occidental del Lugar Santo, situado directamente delante del velo que separaba este aposento del Santo de los Santos, se alzaba el Altar del Incienso o Altar de Oro («Mizbeach haKetoret», Éxodo 30:1-10). Aunque estaba físicamente en el Lugar Santo, su función litúrgica estaba tan íntimamente ligada a la presencia de Dios detrás del velo que el autor de Hebreos lo asocia directamente con el Lugar Santísimo (Hebreos 9:4).
                    
                    El servicio de este altar encerraba ordenanzas estrictas:
                    1. Incienso Sagrado Exclusivo: Una fórmula aromática única prescrita por Dios (estacte, uña aromática, gálbano aromático e incienso puro, Éx 30:34-38) que nadie podía imitar para uso personal bajo pena de ser cortado del pueblo. Representa la pureza inmaculada de las oraciones en Cristo.
                    2. Fuego Exclusivo del Altar de Bronce: El sacerdote jamás podía encender el incienso con brasas comunes; debía tomar las brasas ardientes del altar de bronce donde la sangre del sacrificio había sido derramada (Levítico 16:12). Ofrecer fuego extraño ajeno a la expiación de la sangre traía juicio de muerte inmediata, como ocurrió trágicamente con Nadab y Abiú (Levítico 10:1-2).
                    3. Humo Ascendente Continuo: Mañana y tarde, cuando el sacerdote arreglaba las lámparas, quemaba incienso aromático continuo ante Jehová (Éx 30:7-8).
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    En el simbolismo bíblico universal, el incienso representa las oraciones y la intercesión de los santos ascendiendo como olor grato ante el trono celestial (Salmo 141:2; Apocalipsis 5:8; Apocalipsis 8:3-4). Pero antes de ser nuestras oraciones imperfectas, el Altar de Oro tipifica la intercesión perpetua, eficaz e intachable de nuestro Sumo Sacerdote Jesucristo.
                    
                    Hebreos 7:25 proclama el triunfo de su ministerio celestial presente: «por lo cual puede también salvar perpetuamente a los que por medio de él se acercan a Dios, viviendo siempre para interceder por ellos». Cristo no solo murió por nosotros en el pasado; vive hoy a la diestra del Padre intercediendo por nuestro nombre cada segundo. 
                    
                    Nuestras oraciones débiles son recibidas y gratas al Padre únicamente porque ascienden sazonadas y purificadas con el mérito infinito del incienso de la sangre y justicia de Cristo.
                """,
                doctrinalThesis = "El Altar del Incienso tipifica la intercesión sacerdotal continua de Jesucristo ante el Padre, que sostiene eternamente al creyente y hace aceptables sus oraciones a Dios.",
                exegeticalAnalysis = "La prohibición absoluta de usar fuego extraño en el Altar de Oro (Lv 10) enseña que ninguna oración humana puede acercarse al trono de la gracia si no está fundada exclusivamente en la sangre expiatoria de Cristo."
            ),

            // DÍA 8
            createStudyDay(
                dayNumber = 8,
                title = "Día 8: El Velo del Santuario y su Rasgado en la Muerte de Cristo",
                passagesSummary = "Éxodo 26:31-35; Mateo 27:50-54; Hebreos 10:19-22",
                primaryBookId = 2,
                primaryChapter = 26,
                passages = listOf(seg(2, "Éxodo", 26), seg(40, "Mateo", 27), seg(58, "Hebreos", 10)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    Entre el Lugar Santo y el Santo de los Santos colgaba una cortina imponente y formidable conocida como el Velo («Parojet», Éxodo 26:31-35). Tejido con primor de hilo azul, púrpura, carmesí y lino fino torcido, sobre su superficie estaban bordadas figuras de querubines celestiales con alas extendidas. 
                    
                    El velo constituía una barrera infranqueable de separación: proclamaba que el camino a la presencia íntima de Dios estaba vedado para la humanidad pecadora (Hebreos 9:8). Los querubines bordados recordaban a los querubines con espadas encendidas que custodiaban la entrada del huerto de Edén tras la caída de Adán (Génesis 3:24), advirtiendo que la santidad de Dios consume a todo pecador que intente irrumpir sin expiación. Nadie podía cruzar ese velo jamás, excepto el sumo sacerdote una sola vez al año en Yom Kipur con sangre de sacrificio.
                    
                    Sin embargo, en el instante exacto en que Jesucristo clavado en la cruz del Calvario gritó con voz potente: «¡Consumado es!» (Tetelestai) y entregó el espíritu (Mateo 27:50-51), aconteció un milagro cósmico: «Y he aquí, el velo del templo se rasgó en dos, de arriba abajo; y la tierra tembló, y las rocas se partieron».
                    
                    El texto sagrado subraya con precisión que se rasgó «de arriba abajo» («anōthen heōs katō»). La mano de ningún hombre pudo alcanzar la altura de ese velo para desgarrarlo; fue la mano soberana del Dios Todopoderoso quien rasgó la barrera desde los cielos.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    Hebreos 10:19-20 revela la exégesis divina de este acontecimiento: «Así que, hermanos, teniendo libertad para entrar en el Lugar Santísimo por la sangre de Jesucristo, por el camino nuevo y vivo que él nos abrió a través del velo, esto es, de su carne».
                    
                    El velo rasgado era la figura del cuerpo santo de Cristo desgarrado en el madero. En el momento en que su carne fue quebrantada por nuestras rebeliones, el antiguo orden ceremonial caducó para siempre y el acceso a la gloria misma de Dios quedó abierto de par en par para todo creyente. Ya no hay sacerdotes intermediarios terrenales ni templos de piedra: cada hijo de Dios puede entrar con confianza al Lugar Santísimo, adorar cara a cara y hallar gracia para el oportuno socorro.
                """,
                doctrinalThesis = "El rasgado milagroso del velo en la muerte de Cristo abolió la separación entre Dios y el hombre, inaugurando un camino nuevo y libre al Lugar Santísimo por la sangre del Cordero.",
                exegeticalAnalysis = "La dirección de la rotura «de arriba abajo» en Mateo 27:51 autentica el origen divino del acceso al trono de la gracia, consumando el tipo de la carne de Cristo como el velo inaugurador (Hebr 10:20)."
            ),

            // DÍA 9
            createStudyDay(
                dayNumber = 9,
                title = "Día 9: El Arca del Pacto y el Propiciatorio: Justicia y Gracia Reconciliadas",
                passagesSummary = "Éxodo 25:10-22; Romanos 3:23-26; Hebreos 9:1-5",
                primaryBookId = 2,
                primaryChapter = 25,
                passages = listOf(seg(2, "Éxodo", 25), seg(45, "Romanos", 3), seg(58, "Hebreos", 9)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En el corazón mismo del Santo de los Santos («Kodesh haKodashim»), en la penumbra del aposento más sagrado iluminado únicamente por la gloria radiante de la Shekiná, reposaba el mueble más sublime de toda la revelación veterotestamentaria: el Arca del Pacto («Aron haBerit», Éxodo 25:10-22).
                    
                    El Arca era un cofre de madera de acacia revestido por dentro y por fuera de oro puro. En su interior se custodiaban tres elementos sagrados cargados de simbolismo (Hebreos 9:4):
                    1. Las Tablas de la Ley: El testimonio de la santa justicia y mandamientos de Dios, los cuales el ser humano había quebrantado mereciendo juicio de muerte.
                    2. La Urna de Oro con el Maná: Testimonio del pan milagroso que descendió del cielo y de la provisión fiel de Dios frente a la murmuración de Israel.
                    3. La Vara de Aarón que floreció: Testimonio de la elección soberana del sacerdocio de vida sobre la rebelión de Coré (Números 17).
                    
                    Pero lo que coronaba el Arca era su cubierta de oro macizo: el Propiciatorio («Kapporet», de la raíz hebrea «kafar», cubrir o expiar). Sobre los dos extremos del Propiciatorio se erguían dos querubines de oro labrados a martillo, mirándose el uno al otro con las alas extendidas cubriendo la tapa, y con sus rostros inclinados hacia el Propiciatorio. Entre esos dos querubines Dios prometió a Moisés: «Y de allí me declararé a ti, y hablaré contigo de sobre el propiciatorio» (Éxodo 25:22).
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    La trascendencia teológica del Propiciatorio es asombrosa. La Ley dentro del Arca clamaba con justicia la condenación del pecador transgresor. Pero una vez al año, en el Gran Día del Perdón (Yom Kipur), el sumo sacerdote entraba con la sangre expiatoria del cordero inocente y la rociaba siete veces sobre el Propiciatorio (Levítico 16:14). 
                    
                    Cuando los santos ojos de Dios y los querubines ejecutores del juicio miraban hacia abajo sobre el Arca, no veían la Ley condenatoria del pecador; veían la sangre derramada del sustituto interponiéndose entre la Ley quebrantada y el trono de Dios. La justicia quedaba satisfecha y la misericordia fluía con libertad soberana.
                    
                    En Romanos 3:25, el apóstol Pablo aplica este simbolismo directamente a Jesús utilizando la palabra griega exacta de la Septuaginta para el propiciatorio: «Hilastērion»: «a quien Dios puso como propiciación [Hilastērion] por medio de la fe en su sangre, para manifestar su justicia». Jesucristo es nuestro verdadero Propiciatorio viviente: en su cruz la justicia inflexible y la gracia infinita de Dios se abrazaron para siempre (Salmo 85:10).
                """,
                doctrinalThesis = "El Propiciatorio (Kapporet / Hilastērion) rociado con sangre tipifica a Jesucristo como la propiciación perfecta donde la justicia santa de Dios es satisfecha y su gracia reconcilia al pecador.",
                exegeticalAnalysis = "El uso técnico de «Hilastērion» en Romanos 3:25 conecta directamente a la cruz con el rociamiento expiatorio sobre el Propiciatorio del Arca, probando que Dios justifica al pecador sin comprometer su perfecta justicia."
            ),

            // DÍA 10
            createStudyDay(
                dayNumber = 10,
                title = "Día 10: El Sumo Sacerdote Aarónico y sus Vestiduras de Gloria",
                passagesSummary = "Éxodo 28:1-12, 29-30; Levítico 8:1-12; Hebreos 4:14-16",
                primaryBookId = 2,
                primaryChapter = 28,
                passages = listOf(seg(2, "Éxodo", 28), seg(3, "Levítico", 8), seg(58, "Hebreos", 4)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En Éxodo 28 (cuya solemne investidura y unción se relata en Levítico 8:1-12), Dios ordenó confeccionar las vestiduras sagradas para Aarón y sus sucesores en el sumo sacerdocio: «Y harás vestiduras sagradas a Aarón tu hermano, para honra y hermosura» (Éxodo 28:2). Cada prenda ministerial constituía una proclamación visual de la mediación sacerdotal:
                    
                    1. Las Piedras de Ónice en las Hombreras del Efod (Éx 28:9-12): Dos piedras de ónice grabadas con los nombres de las doce tribus de Israel (seis en cada hombro) engastadas en oro. Los hombros simbolizan la fuerza y el poder sustentador: el sumo sacerdote llevaba al pueblo de Dios sobre sus hombros ante la presencia del Señor.
                    2. El Pectoral del Juicio («Joshen haMishpat», Éx 28:15-29): Una prenda cuadrada colocada directamente sobre el pecho y el corazón del sumo sacerdote, adornada con doce piedras preciosas distintas incrustadas en cuatro hileras de tres (sardio, topacio, esmeralda, zafiro, diamante, etc.), cada una con el nombre de una tribu. Al ministrar en el santuario, Aarón llevaba los nombres del pueblo de Dios sobre su propio corazón continuamente como memorial delante de Jehová.
                    3. El Urim y el Tumim (Éx 28:30): Depositados dentro del pectoral para discernir con infalibilidad la voluntad divina ante decisiones de juicio del pueblo.
                    4. La Lámina de Oro en la Mitra (Éx 28:36-38): Atada sobre la frente del sumo sacerdote con la inscripción grabada a buril: «SANTIDAD A JEHOVÁ» (Kódesh le-YHWH), para llevar las faltas de las cosas santas de los hijos de Israel y hacerlas aceptables ante Dios.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    El sumo sacerdote terrenal era una figura hermosa pero imperfecta: era mortal, pecador, sujeto a debilidades y debía ofrecer sacrificios primero por sus propios pecados antes de poder interceder por los del pueblo (Hebreos 5:1-3).
                    
                    Jesucristo es nuestro Sumo Sacerdote perfecto y celestial (Hebreos 4:14-15). Él nos lleva grabados no en piedras de ónice terrenales, sino sobre sus hombros omnipotentes con los cuales sostiene el universo entero (Hebreos 1:3); y nos lleva grabados no en un pectoral de tela que se desgasta, sino sobre su propio corazón de amor eterno que fue traspasado por nosotros. Conocer a este Sumo Sacerdote compasivo nos infunde una confianza inamovible: «Acerquémonos, pues, confiadamente al trono de la gracia, para alcanzar misericordia y hallar gracia para el oportuno socorro» (Hebreos 4:16).
                """,
                doctrinalThesis = "Las vestiduras sacerdotales del Antiguo Testamento prefiguraban la intercesión de Cristo, quien sostiene a su pueblo con su fuerza omnipotente y lo lleva grabado en su corazón de amor eterno.",
                exegeticalAnalysis = "La colocación de los nombres de Israel en las hombreras (poder) y en el pectoral sobre el corazón (amor) en Éxodo 28 define la naturaleza mediadora y pastoral de Cristo como el Gran Sumo Sacerdote."
            ),

            // DÍA 11
            createStudyDay(
                dayNumber = 11,
                title = "Día 11: Yom Kipur: La Expiación Definitiva y el Doble Macho Cabrío",
                passagesSummary = "Levítico 16:1-22; Hebreos 9:11-15",
                primaryBookId = 3,
                primaryChapter = 16,
                passages = listOf(seg(3, "Levítico", 16), seg(58, "Hebreos", 9)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    El día más solemne y reverente del calendario litúrgico de Israel era Yom Kipur, el Gran Día de la Expiación, celebrado el día diez del séptimo mes (Levítico 16). En este único día del año, y solo tras múltiples purificaciones y despojándose de sus vestiduras vistosas de gloria para vestir sencillas túnicas de lino blanco de siervo humilde, el sumo sacerdote podía penetrar detrás del velo hasta el Santo de los Santos.
                    
                    El rito central de Yom Kipur demandaba dos machos cabríos idénticos presentados a la puerta del tabernáculo sobre los cuales se echaban suertes:
                    1. El Primer Macho Cabrío (La Propiciación): La suerte caía «por Jehová» (Lv 16:9). El animal era degollado como ofrenda por el pecado del pueblo, y el sumo sacerdote llevaba su sangre al interior del Santo de los Santos para rociarla sobre el Propiciatorio. Este sacrificio satisfacía la santa justicia de Dios y aplacaba su ira mediante la expiación por sangre.
                    2. El Segundo Macho Cabrío (La Expiación Expoliatoria / El Macho Cabrío Emisario o Azazel, Lv 16:20-22): Aarón ponía ambas manos sobre la cabeza del animal vivo y confesaba sobre él todas las iniquidades, rebeliones y pecados de los hijos de Israel, «poniéndolos así sobre la cabeza del macho cabrío». Luego, un hombre designado conducía al animal lejos hacia el desierto desolado y lo soltaba en tierra inhabitada. El macho cabrío se llevaba consigo para siempre la culpa fuera del campamento, donde nadie pudiera volver a verla jamás.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    ¿Por qué se requerían dos machos cabríos en Yom Kipur? Porque un solo animal no podía representar simultáneamente la muerte expiatoria y la remoción permanente de la culpa. Cristo es el cumplimiento absoluto de ambos machos cabríos:
                    
                    Como el primer macho cabrío, Cristo murió en la cruz derramando su propia sangre para satisfacer la justicia del Padre (propiciación). Como el segundo macho cabrío, Cristo cargó sobre sus propios hombros todos nuestros pecados y los alejó tan lejos «cuanto está lejos el oriente del occidente» (Salmo 103:12), sepultándolos en las profundidades del mar del olvido de Dios (Miqueas 7:19).
                    
                    Como proclama Hebreos 9:12: Cristo «no por sangre de machos cabríos ni de becerros, sino por su propia sangre, entró una vez para siempre en el Lugar Santísimo, habiendo obtenido eterna redención». Nuestro perdón no es temporal ni revocable: es un perdón eterno, perfecto y completo.
                """,
                doctrinalThesis = "Yom Kipur prefigura la doble dimensión de la cruz de Cristo: la propiciación ante la justicia de Dios (primer macho cabrío) y la cancelación y alejamiento definitivo de la culpa del creyente (segundo macho cabrío).",
                exegeticalAnalysis = "El rito del macho cabrío emisario («Azazel») en Levítico 16:21 ilustra de forma plástica la remoción expiatoria total del pecado, consumada de una vez y para siempre por Cristo (Hebr 9:12)."
            ),

            // DÍA 12
            createStudyDay(
                dayNumber = 12,
                title = "Día 12: El Orden de Melquisedec: Un Sacerdocio Superior y Eterno",
                passagesSummary = "Hebreos 7:1-19; Génesis 14:17-20; Salmo 110:1-4",
                primaryBookId = 58,
                primaryChapter = 7,
                passages = listOf(seg(58, "Hebreos", 7), seg(1, "Génesis", 14), seg(19, "Salmos", 110)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    Para los creyentes de origen judío del primer siglo, uno de los mayores dilemas teológicos radicaba en cómo Jesús podía ser Sumo Sacerdote si no descendía de la tribu sacerdotal de Leví, sino de la tribu real de Judá, acerca de la cual Moisés nada dijo respecto al sacerdocio (Hebreos 7:13-14). En Hebreos 7, el autor sagrado responde a esta objeción desplegando un argumento teológico de magistral profundidad: el sacerdocio de Cristo no es levítico, sino según el orden superior de Melquisedec.
                    
                    Melquisedec aparece de forma enigmática y sublime en Génesis 14:18 tras la victoria de Abraham: es presentado simultáneamente como «rey de Salem» (rey de paz) y «sacerdote del Dios Altísimo» (El Elyon). Su nombre significa «Rey de Justicia» (Malki-Tzedek). Abraham no solo recibió de él pan y vino y su bendición sacerdotal, sino que le entregó voluntariamente los diezmos de todo (Gn 14:20). 
                    
                    El autor de Hebreos extrae tres conclusiones exegéticas irrefutables:
                    1. Melquisedec es superior a Abraham y a Leví: El menor es bendecido por el mayor (Hebr 7:7), y Leví mismo, estando en los lomos de Abraham, pagó diezmos a Melquisedec (Hebr 7:9-10).
                    2. Un Sacerdocio Real: En el Antiguo Testamento los reyes no podían ser sacerdotes ni los sacerdotes reyes (el rey Uzías fue castigado con lepra por pretender ofrecer incienso, 2 Crónicas 26). Solo en Melquisedec se unían la realeza y el sacerdocio.
                    3. Un Sacerdocio sin Sucesión Genealógica: En el relato de Génesis no se registra su genealogía ni su muerte, tipificando al Hijo de Dios que vive y permanece sacerdote perpetuamente.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    Mil años después de Abraham, David profetizó en el Salmo 110:4 el decreto mesiánico inmutable: «Juró Jehová, y no se arrepentirá: Tú eres sacerdote para siempre según el orden de Melquisedec». 
                    
                    El sacerdocio aarónico levítico era débil y transitorio: los sacerdotes morían y debían ser reemplazados continuamente por otros sucesores pecadores. Pero el sacerdocio de Cristo está constituido «no conforme a la ley del mandamiento carnal, sino según el poder de una vida indestructible» (Hebreos 7:16). 
                    
                    Jesús es el Rey de Justicia que nos justifica, el Rey de Paz que nos reconcilia con Dios y el Sumo Sacerdote eterno que nunca muere y que sustenta nuestra salvación por los siglos de los siglos.
                """,
                doctrinalThesis = "El sacerdocio de Cristo según el orden de Melquisedec trasciende y reemplaza al sacerdocio levítico, uniendo en su Persona la realeza soberana y el sacerdocio eterno e intransmisible.",
                exegeticalAnalysis = "El Salmo 110:4 y Hebreos 7:16 fundamentan el sacerdocio de Jesús en un juramento divino irrevocable y en «el poder de una vida indestructible» («kat' dynamin zōēs akatalytou»)."
            ),

            // DÍA 13
            createStudyDay(
                dayNumber = 13,
                title = "Día 13: El Mediador de un Mejor Pacto y de Mejores Promesas",
                passagesSummary = "Hebreos 8:1-13; Jeremías 31:31-34",
                primaryBookId = 58,
                primaryChapter = 8,
                passages = listOf(seg(58, "Hebreos", 8), seg(24, "Jeremías", 31)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En Hebreos 8:1-6, el autor sagrado sintetiza la médula de su tratado teológico: «Ahora bien, el punto principal de lo que venimos diciendo es que tenemos tal sumo sacerdote, el cual se sentó a la diestra del trono de la Majestad en los cielos, ministro del santuario, y de aquel verdadero tabernáculo que levantó el Señor, y no el hombre».
                    
                    El contraste entre ambos órdenes es colosal:
                    1. En la tierra, los sacerdotes levíticos ministraban en un santuario que era mera sombra y figura; Cristo ministra en el Santuario celestial original ante la presencia directa del Padre.
                    2. En el tabernáculo terrenal, los sacerdotes ministraban siempre de pie, pues los sacrificios nunca terminaban (Hebreos 10:11); pero Cristo, habiendo consumado un solo sacrificio perfecto, «se sentó» a la diestra de Dios, proclamando que la obra redentora está eternamente concluida y que Él reina con señorío absoluto.
                    3. Cristo es declarado «mediador de un mejor pacto, establecido sobre mejores promesas» (Hebreos 8:6).
                    
                    El autor cita íntegramente la profecía de Jeremías 31 sobre el Nuevo Pacto. Si el primer pacto del Sinaí hubiese sido perfecto e intachable, no se habría procurado lugar para un segundo pacto (Hebreos 8:7). La Ley descubría el pecado y condenaba la transgresión desde tablas de piedra exteriores; pero el Nuevo Pacto escribe la ley de Dios en las mentes y en los corazones, otorga el perdón radical de todos los pecados y concede a cada redimido el conocimiento directo de Dios (Hebreos 8:10-12).
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    El capítulo concluye con una declaración tajante en el versículo 13: «Al decir: Nuevo pacto, ha dado por viejo al primero; y lo que se da por viejo y se envejece, está próximo a desaparecer». 
                    
                    Pretender volver a los ritos ceremoniales, días festivos levíticos, sacerdotes humanos terrenales o dietas ceremoniales de la ley mosaica equivale a cambiar el oro puro por oropel, o preferir la sombra borrosa cuando ya ha llegado la radiante luz del Sol de justicia. En Cristo tenemos la plenitud de todas las promesas divinas: Él es el Mediador perfecto que garantiza nuestra comunión eterna con el Dios viviente.
                """,
                doctrinalThesis = "Cristo es el Mediador del Nuevo Pacto, superior en santuario, sacrificio y promesas, haciendo que el antiguo régimen ceremonial quede abolido y consumado en su plenitud.",
                exegeticalAnalysis = "La postura de Cristo sentado a la diestra del Padre en Hebreos 8:1 («ekathisen») simboliza la consumación definitiva de la obra expiatoria en contraste con el sacerdocio levítico continuo de pie."
            ),

            // DÍA 14
            createStudyDay(
                dayNumber = 14,
                title = "Día 14: Un Solo Sacrificio para Siempre: La Eficacia de la Cruz",
                passagesSummary = "Hebreos 10:1-18; Juan 19:28-30",
                primaryBookId = 58,
                primaryChapter = 10,
                passages = listOf(seg(58, "Hebreos", 10), seg(43, "Juan", 19)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En Hebreos 10:1-14 se formula el veredicto definitivo sobre la suficiencia absoluta de la cruz del Calvario. El autor expone la dolorosa limitación de la economía levítica: si aquellos sacrificios de animales hubiesen podido limpiar la conciencia del pecador, «¿no habrían cesado de ofrecerse?» (Hebr 10:2). La repetición incesante año tras año de miles de corderos, machos cabríos y becerros constituía un memorial continuo que recordaba que la deuda del pecado seguía pendiente de liquidación real ante Dios.
                    
                    Frente a esa procesión inacabable de sangre animal, entra en escena el Hijo de Dios encarnado: «Sacrificio y ofrenda no quisiste; mas me preparaste cuerpo... He aquí que vengo, oh Dios, para hacer tu voluntad» (Hebreos 10:5, 7, citando el Salmo 40). Cristo tomó un cuerpo humano real para poder morir en nuestro lugar.
                    
                    Y en el versículo 12 resuena la declaración triunfal del Evangelio: «pero Cristo, habiendo ofrecido una vez para siempre un solo sacrificio por los pecados, se ha sentado a la diestra de Dios... porque con una sola ofrenda hizo perfectos para siempre a los santificados» (Hebreos 10:12, 14).
                    
                    La frase «una sola ofrenda» («mia prosphora») y «para siempre» («eis to diēnekes») derriba cualquier pretensión de que el sacrificio de Cristo deba ser repetido, re-ofrecido o complementado con penitencias, purgatorios o misas terrenales. Cuando Jesús exclamó en la cruz «Consumado es» (en griego: «Tetelestai», término que se estampaba en las facturas comerciales para certificar: «la deuda ha sido pagada en su totalidad»), la redención del pueblo de Dios quedó sellada con perfección eterna.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    La perfección del sacrificio de Cristo nos concede una conciencia limpia de obras muertas (Hebreos 9:14). El creyente ya no vive en la zozobra constante del israelita del desierto que temía el juicio si olvidaba una ofrenda ceremonial; descansa con absoluta certeza en que todas sus transgresiones pasadas, presentes y futuras han sido lavadas por la sangre del Cordero.
                    
                    El Espíritu Santo da testimonio de esta paz en nuestro interior: «Y nunca más me acordaré de sus pecados y transgresiones. Pues donde hay remisión de éstos, no hay más ofrenda por el pecado» (Hebreos 10:17-18). La cruz de Cristo es completa, perfecta y eternamente victoriosa.
                """,
                doctrinalThesis = "La muerte de Jesucristo fue un sacrificio único, irrepetible y eternamente eficaz («una sola ofrenda para siempre»), haciendo perfectos a todos los redimidos sin necesidad de sacrificios adicionales.",
                exegeticalAnalysis = "El término «Tetelestai» (Jn 19:30) y la frase «mia prosphora eis to diēnekes» en Hebreos 10:14 certifican la cancelación legal y definitiva de la deuda del pecado ante el tribunal de Dios."
            ),

            // DÍA 15
            createStudyDay(
                dayNumber = 15,
                title = "Día 15: El Monte de Sión: Acerquémonos Confiadamente a la Gracia",
                passagesSummary = "Hebreos 12:18-29; Hebreos 4:14-16; Hebreos 10:19-25",
                primaryBookId = 58,
                primaryChapter = 12,
                passages = listOf(seg(58, "Hebreos", 12), seg(58, "Hebreos", 4), seg(58, "Hebreos", 10)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En la solemne conclusión de la epístola a los Hebreos (capítulo 12, versículos 18 al 24), el autor sagrado traza el contraste definitivo entre los dos montes de la revelación bíblica: el monte Sinaí (el Antiguo Pacto) y el monte de Sión (el Nuevo Pacto).
                    
                    1. El Monte Sinaí (vv. 18-21): Un monte tangible que ardía en fuego físico, rodeado de tinieblas, oscuridad, tempestad y sonido ensordecedor de trompeta con una voz tan terrible que el pueblo rogó que no se les hablase más. Si aun una bestia tocaba el monte, debía ser apedreada; y el espectáculo era tan espantoso que Moisés mismo exclamó: «Estoy espantado y temblando». El Sinaí representaba la Ley santa que confronta al pecador con el terror del juicio divino sin redención consumada.
                    2. El Monte de Sión (vv. 22-24): «Sino que os habéis acercado al monte de Sion, a la ciudad del Dios vivo, Jerusalén la celestial, a la compañía de muchos millares de ángeles, a la congregación de los primogénitos que están inscritos en los cielos, a Dios el Juez de todos, a los espíritus de los justos hechos perfectos, a Jesús el Mediador del nuevo pacto, y a la sangre rociada que habla mejor que la de Abel».
                    
                    La sangre del justo Abel clamaba desde la tierra pidiendo retribución y venganza justa contra Caín (Génesis 4:10); pero la sangre rociada de Jesucristo clama desde la cruz pidiendo misericordia, perdón soberano y reconciliación eterna para todos los transgresores que se acogen a su gracia.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    Habiendo contemplado el recorrido entero desde la puerta del atrio, pasando por el altar de bronce, el lavacro, la Menorá, la mesa del pan, el altar del incienso, el velo rasgado y el propiciatorio en el Santo de los Santos, la exhortación pastoral final resuena con poder transformador en Hebreos 10:22-23:
                    
                    «Acerquémonos con corazón sincero, en plena certidumbre de fe, purificados los corazones de mala conciencia, y lavados los cuerpos con agua pura. Mantengamos firme, sin fluctuar, la profesión de nuestra esperanza, porque fiel es el que prometió».
                    
                    El culto del Tabernáculo ha encontrado su plenitud gloriosa. Ya no nos acercamos con temor servil ni con zozobra de muerte; nos acercamos como hijos amados, comprados por precio infinito, al Trono de la Gracia del Rey del universo (Hebreos 4:14-16). Nuestra vida entera es ahora un sacerdocio santo de adoración, comunión y servicio gozoso al Señor.
                """,
                doctrinalThesis = "Bajo el Nuevo Pacto no nos acercamos al terror del monte Sinaí, sino al monte de Sión, donde la sangre rociada de Cristo habla perdón eterno y nos concede comunión confiada con el Dios vivo.",
                exegeticalAnalysis = "El contraste entre el clamor de la sangre de Abel (retribución) y la sangre de Cristo (gracia y reconciliación) en Hebreos 12:24 corona la superioridad salvífica absoluta del Nuevo Pacto."
            )
        )
    }
}
