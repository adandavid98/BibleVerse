package com.example.data.bible.theology

import com.example.data.bible.ReadingPlanDay
import com.example.data.bible.theology.TheologyStudyHelper.createStudyDay
import com.example.data.bible.theology.TheologyStudyHelper.seg

/**
 * Estudio Bíblico Teológico y Exegético: Pneumatología: El Espíritu Santo (15 Días).
 * Análisis riguroso, accesible y sistemático de la Persona, deidad, atributos y obra
 * vivificadora del Espíritu Santo desde la creación hasta la consumación.
 */
object PneumatologyStudyPlan {

    val days: List<ReadingPlanDay> by lazy {
        listOf(
            // DÍA 1
            createStudyDay(
                dayNumber = 1,
                title = "Día 1: El Espíritu en la Creación: El Ruaj Elohim",
                passagesSummary = "Génesis 1:1-3; Salmo 104:24-30; Job 33:4",
                primaryBookId = 1,
                primaryChapter = 1,
                passages = listOf(seg(1, "Génesis", 1), seg(19, "Salmos", 104)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En la apertura misma del texto bíblico (Génesis 1:2), se nos presenta al Espíritu Santo actuando en el origen del cosmos: «Y la tierra estaba desordenada y vacía, y las tinieblas estaban sobre la faz del abismo, y el Espíritu de Dios se movía sobre la faz de las aguas».
                    
                    El vocablo hebreo utilizado para Espíritu es «Ruaj» (que significa aliento vital, viento poderoso o espíritu). El verbo traducido como «se movía» es «merajefet», una forma verbal intensiva que en la literatura semítica describe el aleteo tierno y vigilante de un águila sobre su nido protegiendo e incubando a sus polluelos (como en Deuteronomio 32:11). Lejos de una fuerza mecánica despersonalizada, el texto retrata al Espíritu divino como el Agente divino activo que transforma el caos y la oscuridad informe en un cosmos rebosante de belleza, orden y vida.
                    
                    El Salmo 104:30 corrobora esta función cósmica: «Envías tu Espíritu, son creados, y renuevas la faz de la tierra». Y en Job 33:4, Eliú proclama: «El espíritu de Dios me hizo, y el soplo del Omnipotente me dio vida».
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    La Creación es una obra trinitaria indivisible: el Padre es la fuente y causa originante de todas las cosas; el Hijo es el Verbo eterno por medio de quien todo fue creado (Juan 1:3; Colosenses 1:16); y el Espíritu Santo es el poder consumador y dador de vida que sostiene, embellece y llena la creación.
                    
                    Esta misma dinámica opera en la «nueva creación» espiritual del pecador. Así como en el principio la tierra estaba desordenada, vacía y en tinieblas, y el Espíritu de Dios descendió para ordenar y vivificar, de igual modo cuando el ser humano se encuentra sumido en el caos moral y la oscuridad del pecado, el Espíritu de Dios se mueve con ternura soberana para engendrar luz, nueva vida y orden conforme a la imagen de Cristo (2 Corintios 4:6; 5:17).
                """,
                doctrinalThesis = "El Espíritu Santo es el divino Dador de vida (Ruaj Elohim), coeterno y creador junto con el Padre y el Hijo, sustentador del orden cósmico y artífice de la nueva creación espiritual.",
                exegeticalAnalysis = "El verbo hebreo «merajefet» en Génesis 1:2 denota una presencia cuidadosa y vivificadora sobre la materia informe, inaugurando la obra creadora que culmina en el ser humano."
            ),

            // DÍA 2
            createStudyDay(
                dayNumber = 2,
                title = "Día 2: El Espíritu en el Antiguo Testamento: Unción Específica y Temporal",
                passagesSummary = "Éxodo 31:1-11; Jueces 14:1-9; 1 Samuel 16:13-14",
                primaryBookId = 2,
                primaryChapter = 31,
                passages = listOf(seg(2, "Éxodo", 31), seg(7, "Jueces", 14), seg(9, "1 Samuel", 16)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    Para comprender la gloria del Nuevo Pacto es fundamental analizar cómo operaba el Espíritu Santo bajo el Antiguo Testamento. En el régimen veterotestamentario, el Espíritu no habitaba de forma universal ni permanente en cada israelita común; más bien, «descendía» o «venía sobre» personas específicas para capacitarlas sobrenaturalmente en el cumplimiento de tareas de pacto.
                    
                    1. En el Ámbito Artístico y Constructivo (Éxodo 31:2-3): Dios llenó a Bezaleel «del Espíritu de Dios, en sabiduría y en inteligencia, en ciencia y en todo arte» para diseñar los sagrados utensilios del Tabernáculo. El arte sacro bíblico fue fruto de la inspiración del Espíritu.
                    2. En el Ámbito Militar y de Liderazgo (Jueces 14:6): «Y el Espíritu de Jehová vino sobre Sansón, quien despedazó al león como quien despedaza un cabrito». El poder físico de Sansón no procedía de una constitución muscular milagrosa, sino del revestimiento soberano del Espíritu.
                    3. En el Ámbito Real y Teocrático (1 Samuel 16:13): Cuando Samuel ungió a David, «desde aquel día en adelante el Espíritu de Jehová vino sobre David con poder». En contraste, debido a la rebelión persistente del rey Saúl, «el Espíritu de Jehová se apartó de Saúl» (1 S 16:14). De ahí el clamor angustiado de David en el Salmo 51:11 tras su pecado: «Y no quites de mí tu santo Espíritu».
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    La unción del Espíritu en el Antiguo Testamento prefiguraba al Mesías («el Ungido», Mashiáj). En el Antiguo Pacto, los sacerdotes, profetas y reyes recibían una unción por separado; pero todos ellos apuntaban a Aquel que reuniría los tres oficios en Su propia Persona y sobre quien el Espíritu reposaría de forma corporal, permanente y sin medida (Isaías 11:1-2; Juan 3:34).
                    
                    Asimismo, resalta el sublime privilegio del creyente del Nuevo Pacto: hoy el Espíritu no nos visita de forma intermitente ni se retira por fluctuaciones ceremoniales; Cristo nos ha sellado con una morada interior que jamás nos abandonará (Juan 14:16).
                """,
                doctrinalThesis = "En el Antiguo Testamento, el Espíritu Santo investía de forma selectiva y transitoria para oficios y ministerios específicos, anticipando la plenitud mesiánica y la morada permanente en el Nuevo Pacto.",
                exegeticalAnalysis = "El retiro del Espíritu en Saúl (1 S 16:14) ilustra la naturaleza teocrática y funcional de la unción en el antiguo orden, en contraste con la regeneración y sello permanente bajo el Nuevo Pacto."
            ),

            // DÍA 3
            createStudyDay(
                dayNumber = 3,
                title = "Día 3: La Promesa del Nuevo Pacto y el Corazón Transformado",
                passagesSummary = "Ezequiel 36:24-28; Jeremías 31:31-34",
                primaryBookId = 26,
                primaryChapter = 36,
                passages = listOf(seg(26, "Ezequiel", 36), seg(24, "Jeremías", 31)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    Durante los días sombríos del exilio babilónico, cuando el templo yacía en ruinas y el pueblo de Judá sufría el juicio del pacto quebrantado, Dios levantó a los profetas para proclamar una revolución espiritual sin precedentes: el Nuevo Pacto. En Ezequiel 36:26-27, el Señor revela la medicina definitiva para la bancarrota moral humana:
                    
                    «Os daré corazón nuevo, y pondré espíritu nuevo dentro de vosotros; y quitaré de vuestra carne el corazón de piedra, y os daré un corazón de carne. Y pondré dentro de vosotros mi Espíritu, y haré que andéis en mis estatutos, y guardéis mis preceptos, y los pongáis por obra».
                    
                    El problema insoluble bajo el pacto sinaítico de la ley no era la Ley misma (la cual es santa, justa y buena, Romanos 7:12), sino la dureza del corazón humano («lev ha'even», corazón de piedra: insensible, muerto, rebelde y frío). La Ley demandaba justicia perfecta desde tablas de piedra exteriores, pero no podía impartir la capacidad moral interna para cumplirla.
                    
                    El Nuevo Pacto transforma la dinámica de raíz: Dios no solo graba su Ley en las mentes y en los corazones (Jeremías 31:33), sino que trasplanta su propio Espíritu («Ruji», mi Espíritu) en lo más íntimo del ser humano. La causa motriz de la obediencia deja de ser la coacción externa del castigo y pasa a ser la inclinación interna de una nueva naturaleza habitada por Dios.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    Esta maravillosa promesa fue sellada con la sangre del Cordero de Dios en la última cena: «Esta copa es el nuevo pacto en mi sangre» (Lucas 22:20). El don del Espíritu Santo es el regalo supremo del Nuevo Pacto. 
                    
                    La obediencia cristiana no es moralismo ni legalismo; es el fruto espontáneo de una vida en la que Dios mismo habita y capacita. Donde antes había rechazo natural a los mandamientos de Dios, ahora hay deleite interior por complacer al Padre celestial. La regeneración interior cumple lo que la letra de la ley nunca pudo lograr por sí sola.
                """,
                doctrinalThesis = "El Nuevo Pacto promete la infusión soberana del Espíritu Santo en el creyente, transformando el corazón rebelde y capacitándolo para una obediencia genuina y gozosa.",
                exegeticalAnalysis = "La promesa de Ezequiel 36:26-27 («quitaré el corazón de piedra... y pondré mi Espíritu») define la naturaleza ontológica de la regeneración y santificación en el Nuevo Testamento."
            ),

            // DÍA 4
            createStudyDay(
                dayNumber = 4,
                title = "Día 4: El Valle de los Huesos Secos: El Poder Resucitador del Espíritu",
                passagesSummary = "Ezequiel 37:1-14; Romanos 8:10-11",
                primaryBookId = 26,
                primaryChapter = 37,
                passages = listOf(seg(26, "Ezequiel", 37), seg(45, "Romanos", 8)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En una de las visiones más conmovedoras de toda la literatura profética, la mano de Jehová lleva al profeta Ezequiel y lo sitúa en medio de un inmenso valle colmado de huesos humanos completamente secos y esparcidos («secos en gran manera», Ez 37:2). Ante la pregunta divina: «Hijo de hombre, ¿vivirán estos huesos?», Ezequiel responde con humilde reverencia: «Señor Jehová, tú lo sabes».
                    
                    Dios le ordena dos acciones proféticas indispensables:
                    1. Profetizar sobre los huesos la Palabra de Dios: «Oíd palabra de Jehová» (v. 4). Cuando la Palabra es proclamada, se produce un estremecimiento sonoro y los huesos se unen hueso con hueso, cubriéndose de tendones, carne y piel; pero el texto añade una observación crucial: «pero no había en ellos espíritu» (v. 8). La ortodoxia formal o la estructura externa sin el Espíritu sigue siendo un cadáver inerte.
                    2. Profetizar al Viento/Espíritu divino: «Profetiza al espíritu, y di al espíritu: Así ha dicho el Señor Jehová: Espíritu, ven de los cuatro vientos, y sopla sobre estos muertos, y vivirán» (v. 9). En hebreo se utiliza tres veces la palabra «Ruaj» jugando con el triple sentido de viento, aliento y Espíritu divino. Cuando el Espíritu sopló, los muertos revivieron, se pusieron en pie y formaron «un ejército grande en extremo» (v. 10).
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    Esta visión poseía una aplicación histórica inmediata: la resurrección nacional y restauración del pueblo de Israel de las tumbas del destierro pagano (vv. 11-14). Sin embargo, en el gran cuadro de la teología bíblica, representa el misterio de la regeneración espiritual de todo pecador.
                    
                    Antes de Cristo, cada alma humana yace en el valle espiritual de la muerte, desprovista de vida divina, incapaz de levantarse por sí sola. Se requiere tanto la proclamación fiel del Evangelio (la Palabra) como el soplo irresistible y soberano del Espíritu Santo para infundir vida eterna. Como enseña Pablo en Romanos 8:11: «Y si el Espíritu de aquel que levantó de los muertos a Jesús mora en vosotros, el que levantó de los muertos a Cristo Jesús vivificará también vuestros cuerpos mortales por su Espíritu que mora en vosotros».
                """,
                doctrinalThesis = "La vivificación del pecador y la resurrección final son operaciones sobrenaturales del Espíritu Santo obrando a través de la predicación de la Palabra de Dios.",
                exegeticalAnalysis = "El juego polisémico de «Ruaj» en Ezequiel 37 (viento, aliento y Espíritu) une indisolublemente la proclamación de la Palabra profética con la vivificación soberana del Espíritu de Dios."
            ),

            // DÍA 5
            createStudyDay(
                dayNumber = 5,
                title = "Día 5: La Profecía de Joel: El Derramamiento sobre Toda Carne",
                passagesSummary = "Joel 2:28-32; Hechos 2:14-21",
                primaryBookId = 29,
                primaryChapter = 2,
                passages = listOf(seg(29, "Joel", 2), seg(44, "Hechos", 2)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    Siglos antes de la llegada de Cristo, el profeta Joel contempló el horizonte de los últimos tiempos y registró una promesa revolucionaria en Joel 2:28-29: «Y después de esto derramaré mi Espíritu sobre toda carne, y profetizarán vuestros hijos y vuestras hijas; vuestros ancianos soñarán sueños, y vuestros jóvenes verán visiones. Y también sobre los siervos y sobre las siervas derramaré mi Espíritu en aquellos días».
                    
                    La trascendencia teológica de este anuncio es monumental. En el Antiguo Testamento, el acceso al Espíritu estaba severamente restringido por género, estatus social y linaje genealógico: reposaba principalmente sobre hombres israelitas selectos pertenecientes a la casta sacerdotal, real o profética. Las mujeres, los esclavos, los jóvenes y los siervos domésticos quedaban al margen de la unción de liderazgo teocrático.
                    
                    Joel profetiza la democratización universal de la gracia del Espíritu:
                    - Rompe barreras de género: «hijos e hijas».
                    - Rompe barreras generacionales: «ancianos y jóvenes».
                    - Rompe barreras socioeconómicas: «sobre los siervos y sobre las siervas».
                    La frase «derramaré» («eshpok») evoca una lluvia torrencial inagotable que inunda la tierra seca, en contraste con las gotas dispersas del pasado.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    En la fiesta de Pentecostés (Hechos 2), cuando el estruendo como de viento recio sacudió Jerusalén y lenguas como de fuego se posaron sobre los 120 discípulos, la multitud quedó perpleja. El apóstol Pedro se puso en pie y declaró con autoridad apostólica: «Esto es lo dicho por el profeta Joel» (Hch 2:16).
                    
                    Pentecostés inauguró formalmente la era escatológica del Espíritu Santo. Toda persona que se arrepiente y cree en Jesucristo —sin importar su nacionalidad, etnia, raza o condición social— es constituida en templo viviente de Dios. Cada creyente tiene acceso directo al trono de la gracia, capacitado para entender la Palabra de Dios y proclamar las maravillas del Salvador.
                """,
                doctrinalThesis = "El derramamiento del Espíritu profetizado por Joel y consumado en Pentecostés derriba toda barrera social y étnica, concediendo acceso directo y unción a todos los redimidos en Cristo.",
                exegeticalAnalysis = "La expresión «sobre toda carne» («al kol basar») en Joel 2:28 marca el paso decisivo del pacto nacional israelita a la comunión global de la Iglesia bajo el señorío de Cristo."
            ),

            // DÍA 6
            createStudyDay(
                dayNumber = 6,
                title = "Día 6: El Espíritu en la Encarnación y Ministerio de Cristo",
                passagesSummary = "Lucas 1:30-35; Lucas 4:14-21; Isaías 61:1-3",
                primaryBookId = 42,
                primaryChapter = 4,
                passages = listOf(seg(42, "Lucas", 4), seg(42, "Lucas", 1), seg(23, "Isaías", 61)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    La vida terrenal del Señor Jesucristo estuvo impregnada de principio a fin por la acción del Espíritu Santo. En la anunciación a la virgen María (Lucas 1:35), el ángel Gabriel explicó el milagro de la concepción virginal: «El Espíritu Santo vendrá sobre ti, y el poder del Altísimo te cubrirá con su sombra; por lo cual también el Santo Ser que nacerá, será llamado Hijo de Dios». La humanidad perfecta y sin pecado de Cristo fue concebida sobrenaturalmente por obra del Espíritu.
                    
                    Posteriormente, al iniciar su ministerio público en la sinagoga de Nazaret (Lucas 4:16-21), Jesús abrió el rollo del profeta Isaías (capítulo 61) y leyó con majestad: «El Espíritu del Señor está sobre mí, por cuanto me ha ungido para dar buenas nuevas a los pobres; me ha enviado a sanar a los quebrantados de corazón; a pregonar libertad a los cautivos, y vista a los ciegos; a poner en libertad a los oprimidos; a predicar el año agradable del Señor». Y cerrando el rollo, sentenció: «Hoy se ha cumplido esta Escritura delante de vosotros».
                    
                    Cristo llevó a cabo sus señales milagrosas por el poder del Espíritu: expulsaba demonios «por el Espíritu de Dios» (Mateo 12:28), anduvo haciendo bienes y sanando a todos los oprimidos por el diablo porque «Dios le ungió con el Espíritu Santo y con poder» (Hechos 10:38), y finalmente se ofreció a Sí mismo en la cruz sin mancha a Dios «mediante el Espíritu eterno» (Hebreos 9:14).
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    Aunque Jesucristo nunca dejó de ser Dios eterno durante los días de su carne terrenal, despojó voluntariamente el uso independiente de sus prerrogativas divinas (kenosis, Filipenses 2:6-8) y eligió vivir su vida humana en total dependencia y sumisión al Padre mediante el Espíritu Santo.
                    
                    Jesús es el modelo consumado del Hombre Verdadero, que vence la tentación en el desierto con la espada de la Palabra y fortalecido por el Espíritu. Esta unción mesiánica sin reservas es la misma unción que Cristo derrama hoy sobre su Iglesia: somos miembros de su Cuerpo y participamos de la misma unción que descendió sobre la Cabeza (Salmo 133; 1 Juan 2:20).
                """,
                doctrinalThesis = "Jesucristo vivió, enseñó, realizó milagros y consumó su sacrificio expiatorio en perfecta sumisión y bajo la unción plena del Espíritu Santo como el Siervo Ungido del Padre.",
                exegeticalAnalysis = "La lectura deliberada de Isaías 61:1 en Lucas 4:18 («Pneuma Kyriou ep' eme») autentica la misión de Jesús como el Ungido por excelencia prometido en las Escrituras hebreas."
            ),

            // DÍA 7
            createStudyDay(
                dayNumber = 7,
                title = "Día 7: La Promesa del Állos Parákletos en el Aposento Alto",
                passagesSummary = "Juan 14:15-26; Juan 16:7-15",
                primaryBookId = 43,
                primaryChapter = 14,
                passages = listOf(seg(43, "Juan", 14), seg(43, "Juan", 16)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En la víspera de la crucifixión, los discípulos en el aposento alto estaban sumidos en una profunda zozobra y consternación al escuchar que su Maestro pronto partiría. Para su asombro, Jesús les hizo una afirmación desconcertante: «Os conviene que yo me vaya; porque si no me fuera, el Consolador no vendría a vosotros; mas si me fuere, os lo enviaré» (Juan 16:7).
                    
                    ¿Cómo podía ser ventajoso para los discípulos que Jesús se fuera físicamente? En su estado corporal encarnado, Jesús estaba localizado en un solo lugar geográfico a la vez: si estaba en Capernaum, no estaba en Jerusalén. Pero el Consolador prometido moraría dentro de cada creyente simultáneamente en cualquier rincón del planeta y para siempre (Jn 14:16-17).
                    
                    El título que Jesús otorga al Espíritu Santo es «Parákletos» precedido por el adjetivo «állos» (Juan 14:16: «állos Parákletos»). En el léxico griego, un «Parákletos» era un abogado defensor, un asesor legal, un consolador y un aliado íntimo que se situaba al lado de alguien en el tribunal o en la batalla para auxiliarlo, defenderlo y fortalecerlo. Al decir «otro» Consolador (állos: de la misma naturaleza divina), Jesús afirma que el Espíritu Santo continuaría su misma presencia protectora e instructora en la Iglesia.
                    
                    Asimismo, en Juan 16:8-11, Jesús define la triple obra judicial del Espíritu en el mundo: convencerá al mundo de pecado (de no creer en Cristo), de justicia (porque Cristo asciende al Padre vindicado) y de juicio (por cuanto el príncipe de este mundo ha sido condenado en la cruz).
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    El Espíritu Santo es el vicario supremo y presencia viva de Jesucristo en la tierra. Su ministerio no consiste en llamar la atención hacia Sí mismo, sino en glorificar y exaltar a Cristo: «Él me glorificará; porque tomará de lo mío, y os lo hará saber» (Juan 16:14). 
                    
                    Cualquier movimiento o enseñanza que margine la persona y obra de Cristo para centrarse en manifestaciones sensoriales desconectadas del Evangelio contradice la naturaleza misma del Espíritu. El Espíritu nos guía a toda la verdad, nos recuerda las palabras del Salvador y hace que el amor de Jesús arda en nuestros corazones.
                """,
                doctrinalThesis = "El Espíritu Santo es el Abogado, Consolador y Defensor divino (Parákletos) enviado por Cristo para morar perpetuamente en la Iglesia, convencer al mundo y glorificar al Redentor.",
                exegeticalAnalysis = "El término legal y pastoral «Parákletos» en Juan 14:16 define al Espíritu como el Abogado celestial y Compañero inseparable que asiste al creyente frente a las hostilidades del mundo."
            ),

            // DÍA 8
            createStudyDay(
                dayNumber = 8,
                title = "Día 8: Pentecostés: El Nacimiento y Empoderamiento de la Iglesia",
                passagesSummary = "Hechos 2:1-13, 37-47",
                primaryBookId = 44,
                primaryChapter = 2,
                passages = listOf(seg(44, "Hechos", 2)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    Cincuenta días después de la Pascua y diez días después de la ascensión del Señor resucitado, la Iglesia primitiva se hallaba unánime en oración en Jerusalén durante la fiesta judía de Pentecostés (en hebreo: Shavuot, la fiesta de las primicias de la siega del trigo y conmemoración de la entrega de la Ley en el Sinaí). En ese contexto solemne, el Espíritu Santo descendió con tres señales audibles y visibles trascendentales:
                    
                    1. «Un estruendo como de un viento recio que soplaba» (Hch 2:2): La presencia del Ruaj divino irrumpió con fuerza incontenible llenando toda la casa.
                    2. «Lenguas repartidas, como de fuego, asentándose sobre cada uno de ellos» (Hch 2:3): El fuego, que en el Sinaí ardía sobre el monte inaccesible, ahora desciende sobre cada creyente individualmente, consagrándolos como santuarios vivientes de la presencia divina.
                    3. «Comenzaron a hablar en otras lenguas, según el Espíritu les daba que hablasen» (Hch 2:4): El texto aclara en los versículos 6 al 11 que se trataba de idiomas humanos reales y comprensibles («dialektōi») de los peregrinos judíos de toda la dispersión (partos, medos, elamitas, romanos, etc.), proclamando «las maravillas de Dios».
                    
                    Pentecostés es la reversión redentora de la Torre de Babel (Génesis 11): donde en Babel el orgullo humano dispersó y confundió las lenguas en juicio divino, en Pentecostés el Espíritu Santo une a las naciones bajo la proclamación del Evangelio de Cristo.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    Pentecostés no fue un evento que deba repetirse una y otra vez en la historia de la salvación; al igual que la encarnación, la crucifixión y la resurrección, es un hito irrepetible del calendario redentor. En Pentecostés, la Iglesia fue formalmente bautizada en el Espíritu como Cuerpo corporativo de Cristo y enviada con poder para ser testigo del Señor hasta lo último de la tierra (Hechos 1:8).
                    
                    El resultado inmediato fue la proclamación cristocéntrica de Pedro, el quebrantamiento de tres mil almas («compungidos de corazón»), el bautismo y la consolidación de una comunidad ferviente en la doctrina de los apóstoles, la comunión, el partimiento del pan y las oraciones (Hechos 2:42).
                """,
                doctrinalThesis = "Pentecostés marca el bautismo histórico e irrepetible de la Iglesia corporativa por el Espíritu Santo, capacitando al pueblo de Dios para la proclamación universal del Evangelio.",
                exegeticalAnalysis = "El uso del vocablo «dialektos» en Hechos 2:6 demuestra que el don de lenguas en Pentecostés consistió en idiomas reconocibles para la proclamación doxológica a las naciones congregadas."
            ),

            // DÍA 9
            createStudyDay(
                dayNumber = 9,
                title = "Día 9: El Templo del Dios Vivo: La Morada Interior Permanente",
                passagesSummary = "1 Corintios 6:12-20; Romanos 8:9-11",
                primaryBookId = 46,
                primaryChapter = 6,
                passages = listOf(seg(46, "1 Corintios", 6), seg(45, "Romanos", 8)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En la cosmopolita y corrompida ciudad de Corinto, famosa en la antigüedad clásica por la prostitución sagrada en el templo de Afrodita, los creyentes enfrentaban constantes tentaciones sexuales amparadas en la filosofía pagana de que «el cuerpo no importa para el alma». En 1 Corintios 6:19-20, el apóstol Pablo confronta esta abominación formulando una pregunta retórica de abrumadora contundencia teológica:
                    
                    «¿O ignoráis que vuestro cuerpo es templo del Espíritu Santo, el cual está en vosotros, el cual tenéis de Dios, y que no sois vuestros? Porque habéis sido comprados por precio; glorificad, pues, a Dios en vuestro cuerpo y en vuestro espíritu, los cuales son de Dios».
                    
                    La palabra griega empleada para templo es «naos». En la lengua griega existían dos términos para templo: «hierón» (el complejo general del templo, incluyendo los atrios exteriores abiertos al público) y «naos» (el santuario interior, el Santo de los Santos, la cámara sagrada inaccesible donde reposaba la misma presencia de la gloria divina o Shekiná). Pablo no dice que nuestro cuerpo es un atrio común; declara que es el santuario interior más santo de Dios.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    Esta verdad eleva la dignidad del ser humano y destruye cualquier dualismo que pretenda desligar la moralidad física de la vida espiritual. El cuerpo del creyente no es una cáscara desechable ni un vehículo de placer egoísta; es morada del Creador.
                    
                    En Romanos 8:9, Pablo establece el criterio definitorio de la identidad cristiana: «Y si alguno no tiene el Espíritu de Cristo, no es de él». No existen cristianos sin el Espíritu Santo. En el instante mismo de la conversión, el Espíritu fija su residencia («oikeō», habitar como en su propia casa legítima) en el redimido. Saber que llevamos la santa presencia del Espíritu en nuestro propio ser produce un celo ferviente por la pureza y una reverencia gozosa en cada aspecto de nuestra conducta.
                """,
                doctrinalThesis = "El cuerpo de cada creyente es el santuario íntimo (naos) del Espíritu Santo, sellado irrevocablemente como propiedad comprada por la sangre de Cristo para vivir en santidad.",
                exegeticalAnalysis = "La designación del creyente como «naos» en 1 Corintios 6:19 traslada la gloria de la presencia divina del tabernáculo terrenal a la persona misma del redimido por gracia."
            ),

            // DÍA 10
            createStudyDay(
                dayNumber = 10,
                title = "Día 10: El Bautismo en el Espíritu y la Unidad del Cuerpo",
                passagesSummary = "1 Corintios 12:12-14; Gálatas 3:26-29; Efesios 4:1-6",
                primaryBookId = 46,
                primaryChapter = 12,
                passages = listOf(seg(46, "1 Corintios", 12), seg(48, "Gálatas", 3), seg(49, "Efesios", 4)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    Uno de los temas más debatidos en la eclesiología contemporánea se esclarece de forma diáfana en 1 Corintios 12:13: «Porque por un solo Espíritu fuimos todos bautizados en un cuerpo, sean judíos o griegos, sean esclavos o libres; y a todos se nos dio a beber de un mismo Espíritu».
                    
                    Obsérvese la universalidad de la declaración apostólica: Pablo no divide a la iglesia de Corinto entre una élite de creyentes «espirituales» que habrían recibido un bautismo posterior y una clase inferior de creyentes que carecen de él. Dice con meridiana claridad: «fuimos TODOS bautizados» (pantes ebaptisthēmen). El bautismo en el Espíritu es la acción sobrenatural mediante la cual el Espíritu Santo incorpora a un pecador creyente en el Cuerpo místico de Cristo al momento de su regeneración.
                    
                    Nadie puede pertenecer a la Iglesia de Cristo sin haber sido bautizado por el Espíritu en ese Cuerpo. El agua bautismal externa es el testimonio visible del pacto; pero el bautismo en el Espíritu es la inmersión vivificante que nos une espiritualmente al Señor resucitado y derriba todas las barreras sociales, raciales y de casta (Gálatas 3:28).
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    De este bautismo fundacional emana el mandato solemne de Efesios 4:3: «Solícitos en guardar la unidad del Espíritu en el vínculo de la paz». Pablo no nos manda a «crear» la unidad, pues la unidad del Cuerpo ya fue creada de una vez y para siempre por el Espíritu en la cruz; se nos manda a «guardar» y proteger esa unidad frente al veneno del partidismo, el chisme, la amargura y las divisiones carnales.
                    
                    En un mundo fragmentado por el odio tribal, la polarización política y el orgullo, la Iglesia local que camina en la comunión del Espíritu es una colonia del Reino de los Cielos que exhibe ante los principados cósmicos la multiforme sabiduría y amor reconciliador de Dios.
                """,
                doctrinalThesis = "El bautismo en el Espíritu es la obra soberana de iniciación por la cual todo creyente es regenerado e incorporado plenamente en el Cuerpo de Cristo al momento de la salvación.",
                exegeticalAnalysis = "El alcance universal de «pantes» (todos) en 1 Corintios 12:13 fundamenta la naturaleza soteriológica del bautismo espiritual como patrimonio común de todos los santos y no como experiencia reservada a una élite."
            ),

            // DÍA 11
            createStudyDay(
                dayNumber = 11,
                title = "Día 11: La Iluminación de las Escrituras y el Testimonio Interno",
                passagesSummary = "1 Corintios 2:6-16; 1 Juan 2:20, 26-27",
                primaryBookId = 46,
                primaryChapter = 2,
                passages = listOf(seg(46, "1 Corintios", 2), seg(62, "1 Juan", 2)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En 1 Corintios 2:14, el apóstol Pablo diagnostica la ceguera epistemológica del ser humano alejado de la gracia: «Pero el hombre natural no percibe las cosas que son del Espíritu de Dios, porque para él son locura, y no las puede entender, porque se han de discernir espiritualmente».
                    
                    La expresión «hombre natural» (psychikos anthrōpos) designa al ser humano caído guiado únicamente por sus sentidos físicos y su intelecto finito, sin la presencia del Espíritu Santo. Aunque posea los títulos académicos más eminentes y una gran inteligencia analítica, la cruz de Cristo le resulta un absurdo irracional. Para que la verdad revelada en la Biblia penetre el corazón y sea recibida con convicción salvadora, se requiere la obra iluminadora del Espíritu Santo.
                    
                    El Espíritu que inspiró las Escrituras (inerrancia e inspiración verbal, 2 Pedro 1:21) es el mismo Espíritu que «alumbra los ojos del entendimiento» (Efesios 1:18) de los lectores. El apóstol Juan describe este ministerio en 1 Juan 2:20 llamándolo «la unción del Santo» (chrisma). La unción no nos imparte nuevas revelaciones extrabíblicas secretas, sino que nos concede discernimiento espiritual para amar, atesorar y obedecer la verdad ya revelada en las Escrituras, protegiéndonos de las herejías seductoras (1 Jn 2:26-27).
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    La teología histórica formula esta verdad bajo la doctrina del «Testimonio Interno del Espíritu Santo» (Testimonium Internum Spiritus Sancti). La autoridad de la Biblia no depende de los decretos de un concilio humano ni de la elocuencia de un orador, sino de la voz de Dios que convence al corazón regenerado de que las Escrituras son la Palabra viva del Creador.
                    
                    Al estudiar las Escrituras, no confiamos en nuestra propia autosuficiencia intelectual; doblamos las rodillas clamando como el salmista: «Abre mis ojos, y miraré las maravillas de tu ley» (Salmo 119:18). El Espíritu Santo es el Maestro interior que aplica la verdad de Cristo a nuestra conciencia.
                """,
                doctrinalThesis = "El Espíritu Santo ilumina la mente del creyente mediante la unción divina, capacitándolo para discernir, acoger y someterse a la verdad de las Sagradas Escrituras.",
                exegeticalAnalysis = "La antítesis entre el «psychikos anthrōpos» y el creyente habitado por el Espíritu en 1 Corintios 2:14 establece la necesidad ineludible de la iluminación espiritual para comprender el misterio de la cruz."
            ),

            // DÍA 12
            createStudyDay(
                dayNumber = 12,
                title = "Día 12: La Guerra Espiritual: Andar en el Espíritu vs. La Carne",
                passagesSummary = "Gálatas 5:16-25; Romanos 8:12-14",
                primaryBookId = 48,
                primaryChapter = 5,
                passages = listOf(seg(48, "Gálatas", 5), seg(45, "Romanos", 8)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En Gálatas 5:16-17, el apóstol Pablo expone la realidad de la batalla interior que experimenta todo hijo de Dios en esta vida presente: «Digo, pues: Andad en el Espíritu, y no satisfagáis los deseos de la carne. Porque el deseo de la carne es contra el Espíritu, y el del Espíritu es contra la carne; y éstos se oponen entre sí, para que no hagáis lo que quisiereis».
                    
                    El mandato «andad en el Espíritu» (en griego: «pneumati peripateite») utiliza el tiempo presente continuo de imperativo. No alude a un salto místico puntual o a un trance ocasional, sino a una caminata diaria, constante, paso a paso, bajo el gobierno, dirección y dependencia del Espíritu Santo.
                    
                    La «carne» (sarx) en la teología paulina no es el cuerpo físico material, sino la naturaleza pecaminosa residual heredada de Adán que aún lucha por recuperar el dominio de nuestros pensamientos, afectos y voluntades. El conflicto es encarnizado: la carne codicia lo terrenal y egocéntrico, mientras que el Espíritu anhela la gloria de Dios y la santidad.
                    
                    En Romanos 8:13, Pablo enseña cómo obtener la victoria en esta guerra: «Porque si vivís conforme a la carne, moriréis; mas si por el Espíritu hacéis morir las obras de la carne, viviréis». La mortificación del pecado no se logra mediante el ascetismo legalista, el aislamiento monástico o la fuerza de voluntad humana; se logra «por el Espíritu», acudiendo a la cruz y apropiándonos del poder santificador de Cristo.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    La santificación es progresiva: mientras estemos en este cuerpo mortal, no alcanzaremos una perfección sin pecado; pero sí podemos y debemos gozar de victoria sobre el pecado. El pecado ya no es nuestro amo legítimo (Romanos 6:14). 
                    
                    Cuando caemos o tropezamos, el Espíritu no nos condena (Romanos 8:1), sino que nos redarguye de pecado, nos conduce al arrepentimiento y nos recuerda la sangre purificadora de Jesucristo (1 Juan 1:7-9). Ser guiados por el Espíritu es la marca distintiva de la filiación divina: «Porque todos los que son guiados por el Espíritu de Dios, éstos son hijos de Dios» (Romanos 8:14).
                """,
                doctrinalThesis = "La vida cristiana victoriosa requiere una caminata continua en dependencia del Espíritu Santo para mortificar las pasiones de la carne y manifestar la santidad de Cristo.",
                exegeticalAnalysis = "El presente continuo de «pneumati peripateite» en Gálatas 5:16 prescribe un estilo de vida de obediencia habitual que desactiva de raíz el impulso pecaminoso de la carne."
            ),

            // DÍA 13
            createStudyDay(
                dayNumber = 13,
                title = "Día 13: El Fruto del Espíritu: La Configuración del Carácter de Cristo",
                passagesSummary = "Gálatas 5:22-26; Juan 15:1-8",
                primaryBookId = 48,
                primaryChapter = 5,
                passages = listOf(seg(48, "Gálatas", 5), seg(43, "Juan", 15)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En contraste radical con las «obras de la carne» (en plural, erga tēs sarkos, las cuales son caóticas, violentas y divisorias, Gálatas 5:19-21), el apóstol Pablo presenta la obra del Espíritu Santo en el creyente en el versículo 22 utilizando una figura profundamente significativa: «Mas el fruto del Espíritu es amor, gozo, paz, paciencia, benignidad, bondad, fe, mansedumbre, templanza; contra tales cosas no hay ley».
                    
                    Nótese con sumo rigor gramatical que Pablo escribe «el fruto» en singular («ho de karpos tou pneumatos»), no «los frutos» en plural. El fruto del Espíritu es un racimo indivisible, una sola obra armónica del Espíritu que reproduce integralmente el carácter y la santidad de Jesucristo en la vida del redimido. No es una lista de opciones independientes donde uno pueda elegir tener paciencia pero carecer de amor; todos los nueve rasgos crecen juntos en la medida en que el creyente madura espiritualmente.
                    
                    La cabeza de este racimo es el «amor» (agapē): el amor abnegado, sacrificial y soberano de Dios. De esa savia brotan el gozo (chara, la alegría que no depende de las circunstancias terrenales), la paz (eirēnē), la paciencia (makrothymia, perseverancia y longanimidad ante el agravio), la benignidad (chrēstotēs, amabilidad compasiva), la bondad (agathōsynē), la fidelidad (pistis), la mansedumbre (prautēs, fuerza bajo el control del Espíritu) y la templanza (enkrateia, dominio propio).
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    El fruto del Espíritu no es el producto de un esfuerzo de autoayuda moralista ni de una disciplina psicológica secular; es el resultado orgánico de permanecer en Cristo, la Vid verdadera. Como enseñó Jesús en Juan 15:4-5: «Como el pámpano no puede llevar fruto por sí mismo, si no permanece en la vid, así tampoco vosotros, si no permanecéis en mí. El que permanece en mí, y yo en él, éste lleva mucho fruto; porque separados de mí nada podéis hacer».
                    
                    La madurez espiritual de un cristiano no se mide por la espectacularidad de los dones que ejerce ni por los aplausos que recibe, sino por la profundidad del fruto de Cristo en su trato cotidiano con su cónyuge, sus hijos, sus hermanos de fe y sus enemigos. En el fruto del Espíritu resplandece la belleza del Evangelio vivida en carne y hueso.
                """,
                doctrinalThesis = "El fruto del Espíritu es la manifestación armónica e indivisible del carácter de Jesucristo en el creyente, generado orgánicamente por la unión vital con la Vid verdadera.",
                exegeticalAnalysis = "El singular «ho karpos» en Gálatas 5:22 evidencia la unidad simétrica del carácter cristiano frente a la naturaleza fraccionada y caótica de las obras de la carne."
            ),

            // DÍA 14
            createStudyDay(
                dayNumber = 14,
                title = "Día 14: Los Dones Espirituales: Gracia para la Edificación Mutua",
                passagesSummary = "1 Corintios 12:1-11; Romanos 12:3-8; 1 Corintios 14:1-12",
                primaryBookId = 46,
                primaryChapter = 12,
                passages = listOf(seg(46, "1 Corintios", 12), seg(45, "Romanos", 12), seg(46, "1 Corintios", 14)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En la iglesia de Corinto se había desatado una lamentable distorsión de los dones espirituales: algunos miembros competían por protagonismo, vanagloriándose de los dones más llamativos (como el hablar en lenguas) y menospreciando los ministerios menos visibles pero fundamentales para la comunidad. En 1 Corintios 12, Pablo establece los principios teológicos inmutables que deben gobernar los dones en el Cuerpo de Cristo.
                    
                    1. Origen Soberano de la Gracia: La palabra griega para don espiritual es «charisma» (plural: «charismata»), derivada de «charis» (gracia). No son habilidades humanas innatas ni premios al mérito espiritual; son manifestaciones inmerecidas de la gracia divina: «Pero todas estas cosas las hace uno y el mismo Espíritu, repartiendo a cada uno en particular como él quiere» (1 Co 12:11).
                    2. Propósito Comunitario: En el versículo 7, Pablo declara con meridiana claridad la finalidad exclusiva de todo don: «Pero a cada uno le es dada la manifestación del Espíritu para provecho» (pros to sympheron, para la edificación colectiva y provecho mutuo del Cuerpo). El don que no edifica a la iglesia, no sirve a su propósito instituido por Dios.
                    3. La Supremacía del Amor: En 1 Corintios 13, Pablo demuestra que el ejercicio del don más sublime (elocuencia angélica, profecía, fe para mover montañas o filantropía extrema) desprovisto de amor («agapē») es reducido a la nada absoluta: «vengo a ser como metal que resuena, o címbalo que retiñe».
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    Los dones espirituales no son juguetes para la vanagloria individual ni insignias de superioridad espiritual; son herramientas de mayordomía que Cristo ascendido en gloria repartió a su pueblo para servir con humildad (Efesios 4:7-12; 1 Pedro 4:10).
                    
                    Cada miembro del Cuerpo posee al menos un don indispensable: el ojo no puede decirle a la mano «no te necesito», ni la cabeza a los pies «no os necesito» (1 Co 12:21). Cuando cada creyente ejerce sus dones bajo el señorío de Cristo y en amor abnegado, la Iglesia local refleja la belleza, el orden y el poder del Dios que la habita.
                """,
                doctrinalThesis = "Los dones espirituales (charismata) son investiduras soberanas de la gracia divina otorgadas a cada creyente con el propósito exclusivo de edificar al Cuerpo de Cristo en amor.",
                exegeticalAnalysis = "La cláusula «pros to sympheron» en 1 Corintios 12:7 supedita el ejercicio legítimo de cualquier don espiritual al beneficio, orden y madurez doctrinal de toda la asamblea."
            ),

            // DÍA 15
            createStudyDay(
                dayNumber = 15,
                title = "Día 15: Sensibilidad Espiritual: No Contristar ni Apagar al Espíritu Santo",
                passagesSummary = "Efesios 4:25-32; 1 Tesalonicenses 5:19-22",
                primaryBookId = 49,
                primaryChapter = 4,
                passages = listOf(seg(49, "Efesios", 4), seg(52, "1 Tesalonicenses", 5)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En la epístola a los Efesios (capítulo 4, versículos 29 al 31), el apóstol Pablo inserta una advertencia de infinita ternura y gravedad: «Ninguna palabra corrompida salga de vuestra boca, sino la que sea buena para la necesaria edificación... Y no contristéis al Espíritu Santo de Dios, con el cual fuisteis sellados para el día de la redención. Quítense de vosotros toda amargura, enojo, ira, gritería y maledicencia, y toda malicia».
                    
                    El verbo griego para «contristar» es «lypeō»: significa entristecer profundamente, causar aflicción o dolor emocional. Una energía impersonal no puede ser contristada; solo una persona infinitamente santa y llena de amor puede sentir dolor cuando aquellos a quienes ama y en quienes mora se contaminan con el pecado. 
                    
                    Nótese el contexto exacto en el que Pablo sitúa este mandato: los pecados relacionales del habla y del corazón (chisme, amargura, palabras destructivas, ira contenida, malicia). El Espíritu Santo es la paloma celestial de santidad y paz; el ambiente de rencor, gritería y desunión en la familia o en la congregación aflige al Espíritu de Dios.
                    
                    Asimismo, en 1 Tesalonicenses 5:19, Pablo exhorta: «No apaguéis al Espíritu» (to pneuma mē sbennute). El verbo «sbennymi» se utilizaba literalmente para sofocar o extinguir una llama de fuego arrojándole agua o ahogándola con cenizas. Apagamos al Espíritu cuando despreciamos la predicación bíblica, cuando sofocamos las convicciones santas de pecado o cuando nos resistimos a obedecer la voz de Dios en las Escrituras.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    Contristar o apagar al Espíritu no significa que el Espíritu Santo revocará la salvación o abandonará al redimido, pues el mismo texto de Efesios 4:30 afirma explícitamente: «con el cual fuisteis sellados para el día de la redención». El sello de la salvación es irrevocable y eterno. 
                    
                    Sin embargo, contristar al Espíritu sí produce una devastadora pérdida del gozo de la salvación, sequedad espiritual, insensibilidad en la oración y disciplina paternal correctiva de Dios (Hebreos 12:5-11). Por el contrario, cuando confesamos con presteza nuestros pecados y cultivamos un corazón perdonador y compasivo («sed benignos unos con otros, misericordiosos, perdonándoos unos a otros, para como Dios también os perdonó a vosotros en Cristo», Ef 4:32), el Espíritu Santo llena nuestra vida de comunión desbordante, paz y poder celestial.
                """,
                doctrinalThesis = "El creyente debe cultivar una profunda reverencia hacia la Persona del Espíritu Santo, absteniéndose de contristarlo con pecados relacionales y avivando su fuego en obediencia a la Palabra.",
                exegeticalAnalysis = "El término «lypeō» en Efesios 4:30 ratifica la sensibilidad personal del Espíritu Santo ante la amargura del corazón humano, vinculando la santidad ética y relacional con la plenitud espiritual."
            )
        )
    }
}
