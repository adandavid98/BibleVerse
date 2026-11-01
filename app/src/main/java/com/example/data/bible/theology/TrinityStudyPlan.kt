package com.example.data.bible.theology

import com.example.data.bible.ReadingPlanDay
import com.example.data.bible.theology.TheologyStudyHelper.createStudyDay
import com.example.data.bible.theology.TheologyStudyHelper.seg

/**
 * Estudio Bíblico Teológico y Exegético: La Trinidad: Un Solo Dios (7 Días).
 * Análisis riguroso, accesible y sistemático del monoteísmo trinitario en todo el canon.
 */
object TrinityStudyPlan {

    val days: List<ReadingPlanDay> by lazy {
        listOf(
            // DÍA 1
            createStudyDay(
                dayNumber = 1,
                title = "Día 1: El Monoteísmo Bíblico y la Pluralidad Divina",
                passagesSummary = "Deuteronomio 6:4; Génesis 1:26-27",
                primaryBookId = 5,
                primaryChapter = 6,
                passages = listOf(seg(5, "Deuteronomio", 6), seg(1, "Génesis", 1)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    El fundamento inamovible de la fe bíblica se proclama en Deuteronomio 6:4 mediante el Shemá: «Oye, Israel: Jehová nuestro Dios, Jehová uno es» (en hebreo: «Shemá Yisra'el: YHWH Eloheinu, YHWH Ejad»). En el contexto del antiguo Cercano Oriente, plagado de deidades paganas caprichosas y politeístas, Israel recibía la revelación de un único Creador moral, soberano y santo.
                    
                    Resulta sumamente revelador el término hebreo utilizado para «uno»: Ejad. En la gramática bíblica, Ejad designa con frecuencia una unidad compuesta o armónica, como en Génesis 2:24 donde el varón y la mujer se unen para formar «una sola carne» (basar ejad), o en Esdras 2:64 donde la congregación entera actuaba «como un solo hombre» (ke'ejad). La lengua hebrea poseía otra palabra, Yajid, reservada para la soledad absoluta o aislamiento numérico estricto (utilizada, por ejemplo, para referirse a un hijo único solitario en Gn 22:2). Sin embargo, el Espíritu Santo inspiró a Moisés a escribir Ejad en el credo fundamental de la fe.
                    
                    Esta riqueza semántica se hace patente desde el primer capítulo del Génesis. En Génesis 1:26, Dios deliberó solemnemente antes de formar al ser humano: «Hagamos al hombre a nuestra imagen, conforme a nuestra semejanza». Los verbos y pronombres en plural («hagamos», «nuestra imagen») no son un plural de majestad —figura poética tardía y ajena al hebreo arcaico—, ni una consulta a los ángeles, pues los ángeles no crearon ni el hombre lleva imagen angélica (Isaías 44:24 afirma tajantemente que Jehová creó los cielos y extendió la tierra «solo» y «sin nadie»). El texto insinúa desde los albores de la revelación una misteriosa y gloriosa comunión interior en el seno de la Deidad.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    La revelación divina en las Escrituras es progresiva: lo que en el Antiguo Testamento se vislumbra como una semilla o destello de luz, florece con meridiana claridad en el Nuevo Testamento tras la encarnación de Cristo. Teológicamente, Dios jamás ha sido una entidad solitaria, fría o distante que necesitara crear el universo para experimentar el amor o romper un silencio eterno.
                    
                    Dios es amor en Sí mismo (1 Juan 4:8) desde toda la eternidad. Antes de que existiera una sola estrella o galaxia, existía un flujo inagotable y perfecto de afecto, conocimiento y comunión entre el Padre, el Hijo y el Espíritu Santo (Juan 17:24). La Trinidad nos revela que la realidad última del cosmos no es una fuerza ciega impersonal ni un monarca solitario y necesitado, sino una Santa Comunidad divina cuya plenitud desborda en gracia hacia su pueblo.
                """,
                doctrinalThesis = "Dios es un único ser divino en esencia y atributos (monoteísmo bíblico), subsistiendo eternamente en tres Personas coiguales, coeternas e inseparables.",
                exegeticalAnalysis = "El uso del vocablo hebreo «Ejad» en el Shemá (Dt 6:4) protege a la Iglesia tanto del politeísmo (creer en varios dioses) como del unitarismo que desconoce la pluralidad divina insinuada desde Génesis 1:26."
            ),

            // DÍA 2
            createStudyDay(
                dayNumber = 2,
                title = "Día 2: Las Teofanías y el Ángel de Jehová en el Antiguo Testamento",
                passagesSummary = "Éxodo 3:1-14; Génesis 18:1-15",
                primaryBookId = 2,
                primaryChapter = 3,
                passages = listOf(seg(2, "Éxodo", 3), seg(1, "Génesis", 18)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    A lo largo del relato veterotestamentario emerge con singular autoridad una figura enigmática y sublime denominada «el Ángel de Jehová» (en hebreo: Malak YHWH). A diferencia de los ángeles comunes que rehúsan la adoración y se presentan como simples mensajeros criados (como en Ap 19:10), el Ángel de Jehová habla en primera persona con la prerrogativa exclusiva del Todopoderoso y recibe honra divina.
                    
                    En Éxodo 3:2, el texto bíblico relata que «se le apareció el Ángel de Jehová en una llama de fuego en medio de una zarza». Pero inmediatamente después, en los versículos 4 y 6, el pasaje declara que «viendo Jehová que él iba a ver, lo llamó Dios de en medio de la zarza... Y dijo: Yo soy el Dios de tu padre, Dios de Abraham, Dios de Isaac, y Dios de Jacob. Entonces Moisés cubrió su rostro, porque tuvo miedo de mirar a Dios». 
                    
                    Moisés no adoró a un ser creado; se postró ante quien le reveló el nombre inefable: «YO SOY EL QUE SOY» (Éx 3:14). Este mismo fenómeno ocurre con Abraham ante los tres varones en Mamre (Gn 18), donde uno de ellos permanece dialogando con Abraham y es llamado reiteradamente Jehová, Juez de toda la tierra (Gn 18:22, 25).
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    La teología cristiana histórica ha identificado unánimemente estas apariciones como «Cristofanías»: manifestaciones pre-encarnadas de la Segunda Persona de la Trinidad, el Verbo eterno de Dios (el Logos), antes de tomar carne humana en el vientre de la virgen María.
                    
                    Juan 1:18 establece el principio rector: «A Dios nadie le vio jamás; el unigénito Hijo, que está en el seno del Padre, él le ha dado a conocer». El Padre nunca ha sido visto en forma corporal; es el Hijo quien desde el principio de los tiempos ha sido el revelador visible y mediador supremo entre la gloria inaccesible de Dios y la humanidad. Cuando el Ángel de Jehová descendía para rescatar a Agar, guiar a Israel en el desierto o llamar a Moisés, era el Hijo de Dios anticipando su misión redentora definitiva.
                """,
                doctrinalThesis = "Las apariciones del Ángel de Jehová en el Antiguo Testamento demuestran que el Hijo de Dios ya actuaba como el Mediador personal y visible del pacto mucho antes de su encarnación histórica.",
                exegeticalAnalysis = "En Éxodo 3, el Ángel de Jehová es identificado indistintamente como Jehová y Dios mismo, manifestando la distinción de personas dentro de la misma e indivisible naturaleza divina."
            ),

            // DÍA 3
            createStudyDay(
                dayNumber = 3,
                title = "Día 3: El Bautismo de Jesús: La Manifestación Trinitaria Plena",
                passagesSummary = "Mateo 3:13-17; Lucas 3:21-22",
                primaryBookId = 40,
                primaryChapter = 3,
                passages = listOf(seg(40, "Mateo", 3), seg(42, "Lucas", 3)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    El bautismo del Señor Jesús a orillas del río Jordán constituye uno de los momentos cumbres de la historia de la salvación. Juan el Bautista administraba un bautismo de arrepentimiento para la remisión de pecados; sin embargo, Jesús, siendo santo e intachable, descendió a las aguas para «cumplir toda justicia» (Mt 3:15). Al sumergirse, Cristo no confesaba pecados propios, sino que se identificaba voluntariamente con la humanidad caída que había venido a rescatar como Siervo Sufriente.
                    
                    En el instante exacto en que Jesús sale de las aguas mientras oraba (Lucas 3:21-22), los cielos se abrieron y se produjo una escena de asombrosa trascendencia: el Espíritu Santo descendió sobre Él en forma corporal como paloma, y simultáneamente resonó desde los cielos la voz del Padre: «Este es mi Hijo amado, en quien tengo complacencia» (Mateo 3:17).
                    
                    Las tres Personas divinas se manifiestan al mismo tiempo, en un mismo suceso, cumpliendo funciones distintas pero en absoluta armonía: el Hijo en las aguas en carne visible, el Espíritu descendiendo con unción capacitadora, y el Padre testificando con voz paternal desde la gloria celestial.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    Este pasaje demuele de raíz una de las herejías más persistentes de la historia: el modalismo o sabelianismo. El modalismo sostiene erróneamente que Dios es una sola persona que cambia de «máscaras» o papeles según la época (a veces actúa como Padre, a veces como Hijo y a veces como Espíritu Santo). 
                    
                    El relato del Jordán refuta categóricamente ese error: Jesús no estaba orando a un eco de Sí mismo, ni el Padre proyectaba un holograma en el agua mientras imitaba una paloma. El Padre ama al Hijo, el Hijo obedece al Padre, y el Espíritu Santo unge al Mediador. Hay una distinción real e hipostática de Personas en la comunión del único Dios verdadero. Aquí se inaugura formalmente el ministerio mesiánico del Salvador bajo el beneplácito del Padre y la unción plena del Espíritu.
                """,
                doctrinalThesis = "La revelación simultánea del Padre hablando, el Hijo encarnado en las aguas y el Espíritu ungiendo prueba la coexistencia eterna y personal de las tres Personas de la Trinidad.",
                exegeticalAnalysis = "El bautismo de Jesús refuta el modalismo al evidenciar que las tres Personas divinas interactúan simultáneamente en una relación interpersonal real y no como modos transitorios de un Dios unipersonal."
            ),

            // DÍA 4
            createStudyDay(
                dayNumber = 4,
                title = "Día 4: La Deidad Suprema de Jesucristo",
                passagesSummary = "Juan 1:1-18; Colosenses 2:9-10",
                primaryBookId = 43,
                primaryChapter = 1,
                passages = listOf(seg(43, "Juan", 1), seg(51, "Colosenses", 2)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    El prólogo del Evangelio según San Juan contiene una de las declaraciones teológicas más densas y sublimes de toda la literatura universal. En Juan 1:1 leemos: «En el principio era el Verbo, y el Verbo era con Dios, y el Verbo era Dios». La precisión del texto griego original desmantela cualquier ambigüedad:
                    
                    1. «En el principio era el Verbo» (En archē ēn ho Logos): Juan utiliza el verbo imperfecto «ēn» (era/existía continuamente), no «egeneto» (llegó a ser o fue creado). Cuando el universo comenzó a existir, el Verbo ya existía desde la eternidad sin principio ni fin.
                    2. «Y el Verbo era con Dios» (kai ho Logos ēn pros ton Theon): La preposición «pros» indica una cercanía íntima cara a cara, una comunión relacional de persona a persona con el Padre.
                    3. «Y el Verbo era Dios» (kai Theos ēn ho Logos): Juan coloca «Theos» sin artículo definido antes del verbo para enfatizar la naturaleza o cualidad esencial: el Verbo poseía exactamente la misma sustancia y esencia divina que el Dios con quien estaba.
                    
                    Pablo corrobora esta verdad en Colosenses 2:9 con una contundencia axiomática: «Porque en él habita corporalmente toda la plenitud de la Deidad». El término griego para Deidad aquí es «Theotētos», que alude a la esencia misma de ser Dios, no a un mero favor divino o título honorífico derivado.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    La deidad de Cristo no es un apéndice especulativo; es la columna vertebral de nuestra salvación. Si Jesús fuese tan solo una criatura elevada, un ángel prominente o un hombre extraordinario dotado de sabiduría moral, su muerte en la cruz carecería por completo de valor infinito para expiar los pecados del mundo. 
                    
                    Ninguna criatura puede salvar a otra criatura del juicio del Creador infinito. Únicamente porque Jesucristo es verdaderamente Dios encarnado (Dios manifestado en carne, 1 Ti 3:16), su sangre derramada posee un mérito redentor inconmensurable, capaz de reconciliarnos eternamente con el Padre y aplacar la ira santa contra el pecado. Adorar a Jesús no es idolatría; es la respuesta debida al Verbo que se hizo carne para habitar entre nosotros.
                """,
                doctrinalThesis = "Jesucristo no fue creado ni adoptado: es Dios en su misma esencia (homoousios con el Padre), coeterno, todopoderoso y digno de adoración universal.",
                exegeticalAnalysis = "El análisis gramatical de Juan 1:1 («Theos ēn ho Logos») y Colosenses 2:9 («Theotētos») confirma que Jesús posee intrínsecamente la totalidad de la naturaleza divina y no una mera semejanza o investidura moral."
            ),

            // DÍA 5
            createStudyDay(
                dayNumber = 5,
                title = "Día 5: La Persona y Divinidad del Espíritu Santo",
                passagesSummary = "Hechos 5:1-11; Juan 14:15-26",
                primaryBookId = 44,
                primaryChapter = 5,
                passages = listOf(seg(44, "Hechos", 5), seg(43, "Juan", 14)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    Con frecuencia se comete el grave error de imaginar al Espíritu Santo como una energía impersonal, una fuerza cósmica invisible o una mera influencia mística. Las Sagradas Escrituras rechazan tajantemente esa caricatura y presentan al Espíritu Santo como una Persona divina soberana, provista de intelecto, voluntad y afecto.
                    
                    En Hechos 5:3-4, durante el juicio apostólico contra Ananías por su hipocresía, el apóstol Pedro declara: «¿Por qué llenó Satanás tu corazón para que mintieses al Espíritu Santo...? No has mentido a los hombres, sino a Dios». En dos versículos sucesivos y paralelos, Pedro iguala de forma directa mentir al Espíritu Santo con mentir a Dios mismo. No se le puede mentir a una corriente eléctrica ni a una influencia etérea; solo se miente a una Persona consciente.
                    
                    Asimismo, en el Evangelio de Juan (Jn 14:16), el Señor Jesús consuela a sus apóstoles prometiéndoles: «Y yo rogaré al Padre, y os dará otro Consolador, para que esté con vosotros para siempre». En el griego koiné existen dos términos para «otro»: «héteros» (otro de distinta clase) y «állos» (otro exactamente de la misma naturaleza y sustancia). Jesús utilizó deliberadamente «állos Parákletos»: un Defensor y Consolador exactamente igual en naturaleza divina a Cristo mismo, quien continuaría su presencia viviente en medio de su pueblo.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    La Biblia atribuye al Espíritu Santo todas las facultades personales: puede ser contristado (Ef 4:30), enseña y recuerda las palabras de Cristo (Jn 14:26), reparte dones como Él quiere soberanamente (1 Co 12:11), intercede por los santos con gemidos indecibles (Ro 8:26) y puede hablar a las iglesias (Ap 2:7).
                    
                    Además, posee todos los atributos incomunicables de la deidad: es omnipresente (Salmo 139:7), omnisciente (1 Corintios 2:10) y eterno (Hebreos 9:14). El creyente no posee «algo» de Dios; posee a Dios mismo morando en su cuerpo, el cual ha sido consagrado como templo vivo del Espíritu Santo. Esta verdad infunde una profunda reverencia en la vida diaria y una convicción inconmovible de santidad.
                """,
                doctrinalThesis = "El Espíritu Santo es la Tercera Persona de la Trinidad, plenamente Dios junto con el Padre y el Hijo, dotado de personalidad divina activa y soberana.",
                exegeticalAnalysis = "El término griego «állos Parákletos» en Juan 14:16 y el paralelismo de Pedro en Hechos 5:3-4 demuestran la personalidad y deidad indiscutibles del Espíritu Santo como Dios habitando en su Iglesia."
            ),

            // DÍA 6
            createStudyDay(
                dayNumber = 6,
                title = "Día 6: La Fórmula Trinitaria Apostólica y la Comunión de la Iglesia",
                passagesSummary = "Mateo 28:16-20; 2 Corintios 13:14",
                primaryBookId = 40,
                primaryChapter = 28,
                passages = listOf(seg(40, "Mateo", 28), seg(47, "2 Corintios", 13)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    Antes de ascender a la diestra del Padre en gloria celestial, el Señor Jesucristo entregó a su Iglesia la Gran Comisión en Galilea. En Mateo 28:19 mandó a sus apóstoles: «Por tanto, id, y haced discípulos a todas las naciones, bautizándolos en el nombre del Padre, y del Hijo, y del Espíritu Santo».
                    
                    Obsérvese con sumo cuidado la gramática del texto sagrado: Jesús no dice «en los nombres» (en plural), como si se tratara de tres dioses o tres entidades independientes; utiliza la fórmula en singular estricto: «en el nombre» (eis to onoma). Un solo Nombre divino que engloba la plenitud eterna de tres Personas. El rito de iniciación a la fe cristiana sella al nuevo converso bajo la autoridad y propiedad directa de la Trinidad.
                    
                    Esta misma armonía doxológica sella la correspondencia apostólica de Pablo en 2 Corintios 13:14: «La gracia del Señor Jesucristo, el amor de Dios, y la comunión del Espíritu Santo sean con todos vosotros». Pablo no yuxtapone una criatura con el Dios altísimo; coloca a Jesucristo, al Padre y al Espíritu Santo en un plano de perfecta paridad y eficacia vivificadora para sostener la vida comunitaria del pueblo de Dios.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    La liturgia, la adoración y la misión de la Iglesia primitiva eran intrínsecamente trinitarias. Desde las aguas del bautismo hasta la bendición final en la asamblea de los santos, los cristianos experimentaban la vida divina no como un silogismo filosófico abstracto, sino como una realidad diaria de comunión espiritual.
                    
                    La gracia salvadora proviene de la encarnación y sacrificio de Cristo; esa gracia dimana de la fuente inagotable del amor soberano del Padre; y ese amor y gracia se hacen reales y palpables en el corazón de los hermanos mediante la comunión (*koinōnía*) y la unción del Espíritu Santo. La Trinidad modela la vida de la Iglesia: diversidad de dones y personas unidas en un solo propósito, en mutua edificación y sin rivalidad.
                """,
                doctrinalThesis = "La fórmula bautismal «en el nombre» (singular) y la bendición apostólica confirman que el único Dios existe como una Trinidad de Personas en igualdad de gloria y majestad.",
                exegeticalAnalysis = "La expresión «eis to onoma» en Mateo 28:19 une indisolublemente bajo una sola autoridad y señorío divino a las tres Personas, estableciendo la pauta sacramental y doctrinal de la Iglesia histórica."
            ),

            // DÍA 7
            createStudyDay(
                dayNumber = 7,
                title = "Día 7: La Sinergia Trinitaria en la Redención y la Vida del Creyente",
                passagesSummary = "Efesios 1:3-14; Efesios 2:18-22",
                primaryBookId = 49,
                primaryChapter = 1,
                passages = listOf(seg(49, "Efesios", 1), seg(49, "Efesios", 2)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En la carta a los Efesios (capítulo 1, versículos 3 al 14), el apóstol Pablo compone un colosal himno de alabanza que en el manuscrito griego original consiste en una sola oración ininterrumpida de impresionante belleza teológica. En esta doxología, la redención del ser humano es presentada como una sinergia perfecta y concertada entre las tres Personas divinas:
                    
                    1. El Padre como Arquitecto Soberano (vv. 3-6): Nos eligió en Cristo desde antes de la fundación del mundo, predestinándonos para ser adoptados como hijos suyos según el puro afecto de su voluntad, para alabanza de la gloria de su gracia.
                    2. El Hijo como Redentor y Sustituto (vv. 7-12): En Él tenemos redención por medio de su sangre, el perdón de los pecados según las riquezas de su gracia, reuniendo todas las cosas en los cielos y en la tierra bajo su señorío.
                    3. El Espíritu Santo como Garante y Sello (vv. 13-14): Habiendo creído en el Evangelio, fuimos sellados con el Espíritu Santo de la promesa, quien es las arras (*arrhabōn*, la fianza legal inmutable) de nuestra herencia eterna hasta la redención de la posesión adquirida.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    Nuestra salvación es inquebrantable precisamente porque no depende de la fragilidad humana, sino de la fidelidad del Dios Trino: fue planificada por el amor soberano del Padre, comprada con el precio infinito de la sangre del Hijo, y preservada y aplicada invenciblemente por el poder santificador del Espíritu Santo.
                    
                    Esta doctrina transforma radicalmente nuestra vida devocional diaria. Como enseña Pablo en Efesios 2:18: «Porque por medio de él [Cristo] los unos y los otros tenemos entrada por un mismo Espíritu al Padre». Cada vez que doblamos nuestras rodillas para orar, nos dirigimos confiadamente al Padre celestial, fundamentados exclusivamente en la justicia perfecta y el sacerdocio eterno del Hijo, asistidos y avivados por los gemidos y la presencia del Espíritu Santo. Toda la vida cristiana es una inmersión dichosa y continua en la comunión del Dios viviente.
                """,
                doctrinalThesis = "Nuestra redención es una obra trinitaria perfecta e indivisible: planificada por el Padre, consumada por el Hijo en la cruz y sellada eficazmente por el Espíritu Santo en el creyente.",
                exegeticalAnalysis = "Efesios 1:3-14 expone la economía de la salvación donde cada Persona de la Deidad desempeña su función soberana e interconectada para garantizar la seguridad eterna de los escogidos."
            )
        )
    }
}
