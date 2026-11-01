package com.example.data.bible.theology

import com.example.data.bible.ReadingPlanDay
import com.example.data.bible.theology.TheologyStudyHelper.createStudyDay
import com.example.data.bible.theology.TheologyStudyHelper.seg

/**
 * Estudio Bíblico Teológico y Exegético: Escatología Bíblica: Profecías (30 Días).
 * Análisis riguroso, accesible y sistemático de la profecía bíblica: Daniel, los profetas,
 * el Discurso de los Olivos, las epístolas paulinas y petrinas, y recorrido integral por Apocalipsis.
 */
object EschatologyStudyPlan {

    val days: List<ReadingPlanDay> by lazy {
        listOf(
            // DÍA 1
            createStudyDay(
                dayNumber = 1,
                title = "Día 1: El Propósito de la Profecía Bíblica: Esperanza y Santidad",
                passagesSummary = "2 Pedro 1:19-21; Amós 3:7-8; 1 Juan 3:1-3",
                primaryBookId = 61,
                primaryChapter = 1,
                passages = listOf(seg(61, "2 Pedro", 1), seg(30, "Amós", 3), seg(62, "1 Juan", 3)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En 2 Pedro 1:19-21, el apóstol Pedro define el valor y la función divina de la profecía bíblica frente a las fábulas engañosas del mundo: «Tenemos también la palabra profética más segura, a la cual hacéis bien en estar atentos como a una antorcha que alumbra en lugar oscuro, hasta que el día esclarezca y el lucero de la mañana salga en vuestros corazones».
                    
                    Pedro utiliza la metáfora de la «antorcha» («lychnōi»): en medio de las tinieblas morales, políticas e ideológicas del mundo presente, la profecía divina no fue dada para alimentar debates de especulación vanidosa o fabricar calendarios sensacionalistas de fechas exactas, sino para iluminar el camino de la Iglesia y afirmarla en la soberanía de Dios.
                    
                    En los versículos 20 y 21 se establece el origen divino inerrante de las Escrituras proféticas (en armonía con Amós 3:7: «Porque no hará nada Jehová el Señor, sin que revele su secreto a sus siervos los profetas»): «entendiendo primero esto, que ninguna profecía de la Escritura es de interpretación privada, porque nunca la profecía fue traída por voluntad humana, sino que los santos hombres de Dios hablaron siendo inspirados por el Espíritu Santo». La palabra para «inspirados» es «pheromenoi» (llevados o impulsados por el viento soberano del Espíritu, como las velas de un barco impulsadas por el viento en altamar).
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    En la teología bíblica, la escatología (el estudio de las últimas cosas, «ta eschata») es eminentemente pastoral y santificadora. Cada vez que el Nuevo Testamento revela una verdad sobre el retorno de Cristo o el juicio final, la conclusión inmediata no es una teoría fría, sino un imperativo ético de consagración.
                    
                    Como enseña el apóstol Juan en 1 Juan 3:2-3: «sabemos que cuando él se manifieste, seremos semejantes a él, porque le veremos tal como él es. Y todo aquel que tiene esta esperanza en él, se purifica a sí mismo, así como él es puro». La escatología purifica nuestras prioridades, consuela al creyente atribulado y nos recuerda que la historia humana avanza bajo el control soberano del Dios que promete y cumple.
                """,
                doctrinalThesis = "La profecía bíblica es la revelación infalible del Espíritu Santo dada a la Iglesia para ser una antorcha de esperanza que purifica y santifica el caminar de los santos.",
                exegeticalAnalysis = "El término «pheromenoi» en 2 Pedro 1:21 certifica que la profecía no tiene origen humano, sino que fue inspirada soberanamente por el Espíritu Santo para consuelo y guía del pueblo de Dios."
            ),

            // DÍA 2
            createStudyDay(
                dayNumber = 2,
                title = "Día 2: Daniel 2: La Estatua de los Imperios y la Piedra Inconmovible",
                passagesSummary = "Daniel 2:31-45; Lucas 20:17-18",
                primaryBookId = 27,
                primaryChapter = 2,
                passages = listOf(seg(27, "Daniel", 2), seg(42, "Lucas", 20)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En el siglo VI a.C., en el corazón de Babilonia la opulenta capital del imperio que había deportado a Judá, el rey Nabucodonosor tuvo un sueño perturbador que ningún sabio pagano pudo revelar ni descifrar. Dios reveló el misterio al joven profeta Daniel en una visión de la noche.
                    
                    Daniel describió una estatua colosal de brillo extraordinario compuesta por cuatro metales sucesivos de valor decreciente pero de dureza creciente (Daniel 2:31-35):
                    1. La Cabeza de Oro Fino: El Imperio Babilónico («tú eres aquella cabeza de oro», v. 38).
                    2. El Pecho y los Brazos de Plata: El Imperio Medo-Persa que conquistó Babilonia en el 539 a.C.
                    3. El Vientre y los Muslos de Bronce: El Imperio Greco-Macedonio de Alejandro Magno.
                    4. Las Piernas de Hierro y los Pies de Hierro mezclado con Barro cocido: El formidable e implacable Imperio Romano, cuya fragilidad final radicaría en alianzas humanas fraccionadas.
                    
                    Pero en el versículo 34 acontece el clímax escatológico: «Estabas mirando, hasta que una piedra fue cortada, no con mano, la cual hirió a la estatua en sus pies de hierro y de barro cocido, y los desmenuzó... y la piedra que hirió a la estatua fue hecha un gran monte que llenó toda la tierra».
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    La frase «cortada no con mano» («di-la biydayin») señala un origen puramente sobrenatural, milagroso y divino. Esa Piedra es Jesucristo, la Roca de los siglos desechada por los edificadores humanos pero constituida por Dios en Cabeza del ángulo (Salmo 118:22; Lucas 20:17-18).
                    
                    En Daniel 2:44 se entrega la interpretación infalible: «Y en los días de estos reyes el Dios del cielo levantará un reino que no será jamás destruido, ni será el reino dejado a otro pueblo; desmenuzará y consumirá a todos estos reinos, pero él permanecerá para siempre». 
                    
                    Fue precisamente en los días del Imperio Romano (las piernas de hierro) cuando el Verbo se hizo carne, murió en la cruz y resucitó victorioso inaugurando el Reino de Dios. Los grandes imperios del mundo terrenal (Babilonia, Persia, Grecia, Roma y todos los imperios modernos) se desvanecen como el tamo que se lleva el viento; solo el Reino eterno de Jesucristo permanecerá inconmovible por la eternidad.
                """,
                doctrinalThesis = "Los imperios humanos mundiales son transitorios y frágiles, mientras que el Reino mesiánico inaugurado por Cristo es la Piedra sobrenatural que llenará toda la tierra para siempre.",
                exegeticalAnalysis = "La frase aramea «di-la biydayin» (no con manos humanas) en Daniel 2:34 subraya la naturaleza divina e indestructible del Reino de Dios frente a los reinos seculares."
            ),

            // DÍA 3
            createStudyDay(
                dayNumber = 3,
                title = "Día 3: Daniel 7: Las Cuatro Bestias y el Hijo del Hombre",
                passagesSummary = "Daniel 7:1-14, 21-27; Marcos 14:61-62",
                primaryBookId = 27,
                primaryChapter = 7,
                passages = listOf(seg(27, "Daniel", 7), seg(41, "Marcos", 14)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    Cincuenta años después de la visión de Daniel 2, Dios concedió al profeta una visión paralela pero desde una perspectiva espiritual mucho más reveladora (Daniel 7). Mientras que Nabucodonosor vio a los imperios como una estatua espléndida de metales brillantes (la gloria vana según el orgullo humano), Daniel los contempla surgiendo del mar agitado como cuatro bestias monstruosas y voraces (la realidad cruel y rapaz según los ojos de Dios):
                    1. El León con alas de águila (Babilonia).
                    2. El Oso voraz con tres costillas en la boca (Medo-Persia).
                    3. El Leopardo con cuatro alas y cuatro cabezas (Grecia y su rápida división entre cuatro generales).
                    4. La Bestia espantosa, terrible en gran manera y con diez cuernos (el poder romano y su prolongación escatológica final con el cuerno pequeño que blasfema contra el Altísimo).
                    
                    Pero en el versículo 9, el caos terrenal se detiene súbitamente ante la apertura del tribunal celestial supremo: «Estuve mirando hasta que fueron puestos tronos, y se sentó un Anciano de días, cuyo vestido era blanco como la nieve, y el pelo de su cabeza como lana limpia; su trono llama de fuego... El Juez se sentó, y los libros fueron abiertos».
                    
                    Y en los versículos 13 y 14 se produce la escena mesiánica cumbre: «Miraba yo en la visión de la noche, y he aquí con las nubes del cielo venía uno como un hijo de hombre, que vino hasta el Anciano de días, y le hicieron acercarse delante de él. Y le fue dado dominio, gloria y reino, para que todos los pueblos, naciones y lenguas le sirvieran; su dominio es dominio eterno, que nunca pasará, y su reino uno que no será destruido».
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    El título favorito que el Señor Jesús utilizó para Sí mismo en los cuatro Evangelios fue precisamente este: «el Hijo del Hombre» (Bar Enash). Cuando el sumo sacerdote Caifás le preguntó bajo juramento si era el Mesías, Jesús respondió citando directamente este texto: «Yo soy; y veréis al Hijo del Hombre sentado a la diestra del poder de Dios, y viniendo en las nubes del cielo» (Marcos 14:62). 
                    
                    Daniel 7 revela que el juicio final y la victoria final de la historia están decididos: la bestia y el cuerno blasfemo serán juzgados y destruidos en el fuego, y los santos del Altísimo recibirán el reino eterno bajo el señorío universal de Cristo (Daniel 7:27).
                """,
                doctrinalThesis = "Detrás de la violencia de los poderes tiránicos mundiales, el tribunal celestial del Anciano de Días juzga a las bestias y otorga el señorío universal y eterno al Hijo del Hombre.",
                exegeticalAnalysis = "La designación aramea «Bar Enash» en Daniel 7:13 combina la verdadera humanidad con atributos divinos (venir en las nubes, recibir adoración universal), fundamentando la cristología de Jesús."
            ),

            // DÍA 4
            createStudyDay(
                dayNumber = 4,
                title = "Día 4: Daniel 9: Las Setenta Semanas y la Muerte del Mesías",
                passagesSummary = "Daniel 9:20-27; Nehemías 2:1-8; Mateo 24:15",
                primaryBookId = 27,
                primaryChapter = 9,
                passages = listOf(seg(27, "Daniel", 9), seg(16, "Nehemías", 2), seg(40, "Mateo", 24)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    Mientras Daniel intercedía con ayuno, cilicio y ceniza por la restauración de Jerusalén al cumplirse los setenta años de desolación profetizados por Jeremías, el ángel Gabriel fue enviado para entregarle la revelación cronológica más asombrosa de todo el canon bíblico: la profecía de las Setenta Semanas (Daniel 9:24-27).
                    
                    En el hebreo bíblico, la palabra para semana es «Shavuá», que significa literalmente una «unidad de siete» (en este contexto, semanas de años: 70 x 7 = 490 años). Dios fijó este período para cumplir seis propósitos redentores supremos (v. 24):
                    1. Terminar la prevaricación.
                    2. Poner fin al pecado.
                    3. Expiar la iniquidad.
                    4. Traer la justicia perdurable.
                    5. Sellar la visión y la profecía.
                    6. Ungir al Santo de los santos.
                    
                    El punto de partida del reloj profético se estableció con precisión matemática en el versículo 25: «desde la salida de la orden para restaurar y edificar a Jerusalén». Históricamente, este decreto fue emitido por el rey persa Artajerjes Longímano en el mes de Nisán del año 445 a.C. (Nehemías 2:1-8). 
                    
                    Desde esa orden hasta la llegada del «Mesías Príncipe» habrían 7 semanas (49 años para la reedificación de los muros) más 62 semanas (434 años), sumando un total de 69 semanas (483 años proféticos). El cálculo histórico culmina con asombrosa exactitud en la semana de la entrada triunfal de Jesús en Jerusalén.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    El versículo 26 registra una declaración profética estremecedora: «Y después de las sesenta y dos semanas se quitará la vida al Mesías, mas no por sí». El verbo hebreo para «se quitará la vida» es «yikaret» (será cortado, ejecutado violentamente como reo de muerte). El Mesías moriría no por culpa propia («mas no por sí»), sino como el Siervo Sustituto cargando los pecados del pueblo (Isaías 53).
                    
                    Posteriormente, el texto profetiza que el pueblo de un príncipe que ha de venir destruiría la ciudad y el santuario (cumplido históricamente por las legiones romanas de Tito en el año 70 d.C.). Y el versículo 27 describe la semana final septuagésima, donde el pacto es quebrantado y se levanta «la abominación desoladora» citada por Cristo en Mateo 24:15. La cruz del Mesías es el centro gravitacional de toda la profecía.
                """,
                doctrinalThesis = "La profecía de las setenta semanas trazó con exactitud milimétrica la venida y muerte expiatoria del Mesías en la cruz, expiando la iniquidad y trayendo la justicia perdurable.",
                exegeticalAnalysis = "El término hebreo «yikaret» en Daniel 9:26 predice la muerte penal sustitutiva del Mesías previa a la destrucción de Jerusalén del año 70 d.C., autenticando la veracidad histórica de las Escrituras."
            ),

            // DÍA 5
            createStudyDay(
                dayNumber = 5,
                title = "Día 5: El Día de Jehová en los Profetas: Juicio y Salvación",
                passagesSummary = "Joel 2:1-13; Sofonías 1:14-18; Amós 5:18-24",
                primaryBookId = 29,
                primaryChapter = 2,
                passages = listOf(seg(29, "Joel", 2), seg(36, "Sofonías", 1), seg(30, "Amós", 5)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    A lo largo de los libros proféticos resuena como un trueno una frase solemne cargada de peso escatológico: «El Día de Jehová» («Yom YHWH»). En el antiguo Israel existía la falsa presunción popular de que el Día de Jehová sería un día automático de fiesta y victoria militar de Israel sobre las naciones paganas. En Amós 5:18, el profeta destruye esa complacencia superficial: «¡Ay de los que desean el día de Jehová! ¿Para qué queréis este día de Jehová? Será de tinieblas, y no de luz».
                    
                    En Sofonías 1:14-15 se describe con dramática elocuencia: «Cercano está el día grande de Jehová, cercano y muy próximo... Día de ira aquel día, día de angustia y de aprieto, día de alboroto y de asolamiento, día de tiniebla y de oscuridad, día de nublado y de entenebrecimiento».
                    
                    El Día de Jehová posee un doble cumplimiento en la teología bíblica:
                    1. Cumplimientos Históricos Preliminares: Días de juicio temporal inminente sobre naciones del pasado (como la caída de Babilonia, la invasión de Asiria o la destrucción de Jerusalén).
                    2. El Cumplimiento Escatológico Final: El día cósmico definitivo de la venida de Cristo para juzgar a vivos y muertos y poner fin a la rebelión del mundo.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    Sin embargo, el Día de Jehová no es únicamente un mensaje de juicio; es una invitación apremiante a la gracia. En Joel 2:12-13, en medio de la advertencia del juicio inminente, Dios clama con corazón pastoral: «Por eso pues, ahora, dice Jehová, convertíos a mí con todo vuestro corazón, con ayuno y lloro y lamento. Rasgad vuestro corazón, y no vuestros vestidos, y convertíos a Jehová vuestro Dios; porque misericordioso es y clemente, tardo para la ira y grande en misericordia».
                    
                    En la cruz del Calvario, el «Día de la Ira» cayó concentrado sobre la persona bendita de nuestro Salvador Jesucristo: el sol se oscureció al mediodía y la tierra tembló cuando Él bebió la copa de la retribución divina. Quien halla refugio en la cruz de Cristo ya no teme el Día del Señor como reo de juicio, sino que aguarda aquel día glorioso como la hora de su redención final y consuelo eterno.
                """,
                doctrinalThesis = "El Día de Jehová (Yom YHWH) es la intervención culminante de Dios para juzgar la soberbia de las naciones y vindicar a su pueblo arrepentido que se refugia en la gracia.",
                exegeticalAnalysis = "El llamado profético de Joel 2:13 a rasgar el corazón y no las vestiduras («qir'u levavjem ve'al bigdeyjem») enseña que solo el arrepentimiento interior genuino libra del juicio en el Día del Señor."
            ),

            // DÍA 6
            createStudyDay(
                dayNumber = 6,
                title = "Día 6: La Promesa de Cielos Nuevos y Nueva Tierra en Isaías",
                passagesSummary = "Isaías 65:17-25; Isaías 66:22-24; 2 Pedro 3:13",
                primaryBookId = 23,
                primaryChapter = 65,
                passages = listOf(seg(23, "Isaías", 65), seg(23, "Isaías", 66), seg(61, "2 Pedro", 3)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En la última sección del libro de Isaías (capítulos 65 y 66), el profeta evangélico mira más allá del retorno del cautiverio babilónico y contempla el horizonte de la eternidad. En Isaías 65:17 proclama el decreto soberano del Creador: «Porque he aquí que yo crearé nuevos cielos y nueva tierra; y de lo primero no habrá memoria, ni más vendrá al pensamiento».
                    
                    El verbo hebreo utilizado para crear es «bará», el mismo verbo reservado exclusivamente para la actividad creadora soberana de Dios desde la nada en Génesis 1:1. Dios no remendará el viejo sistema corrupto del pecado; llevará a cabo una recreación ontológica gloriosa.
                    
                    En los versículos 18 al 25 se describe la paz cósmica y la santidad de este orden renovado:
                    - Desaparición del Dolor: «nunca más más se oirán en ella voz de lloro, ni voz de clamor» (v. 19).
                    - Reversión de la Maldición: El lobo y el cordero serán apacentados juntos, el león comerá paja como el buey y el polvo será el alimento de la serpiente; «no afligirán, ni harán mal en todo mi santo monte, dijo Jehová» (v. 25).
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    La escatología bíblica no enseña un escape incorpóreo del alma a un limbo abstracto; enseña la renovación física y cósmica de toda la creación terrenal liberada de la esclavitud de la decadencia (Romanos 8:21).
                    
                    En Isaías 66:22, Dios confirma la permanencia eterna de su pueblo en esta morada: «Porque como los cielos nuevos y la nueva tierra que yo hago permanecerán delante de mí, dice Jehová, así permanecerá vuestra descendencia y vuestro nombre». 
                    
                    El apóstol Pedro cita esta misma promesa en 2 Pedro 3:13 como el ancla de la esperanza de la Iglesia: «Pero nosotros esperamos, según sus promesas, cielos nuevos y tierra nueva, en los cuales mora la justicia». Toda la redención de Cristo apunta a este desenlace glorioso donde Dios habitará en medio de su pueblo en una creación transfigurada por su santidad.
                """,
                doctrinalThesis = "La salvación culminará en la recreación cósmica de cielos nuevos y nueva tierra, donde la justicia de Dios morará para siempre libre de dolor, pecado y maldición.",
                exegeticalAnalysis = "El uso del verbo exclusivo «bará» en Isaías 65:17 confirma que los nuevos cielos y la nueva tierra son el fruto de una acción soberana y sobrenatural del Creador que supera el orden caído."
            ),

            // DÍA 7
            createStudyDay(
                dayNumber = 7,
                title = "Día 7: Malaquías: El Mensajero del Pacto y el Sol de Justicia",
                passagesSummary = "Malaquías 3:1-6; Malaquías 4:1-6; Lucas 1:76-79",
                primaryBookId = 39,
                primaryChapter = 3,
                passages = listOf(seg(39, "Malaquías", 3), seg(39, "Malaquías", 4), seg(42, "Lucas", 1)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    El libro de Malaquías constituye el último aliento profético del Antiguo Testamento antes de los cuatrocientos años de silencio intertestamentario. El pueblo que había retornado de Babilonia había caído en un cinismo espiritual lamentable: sacerdotes corruptos que ofrecían animales ciegos y cojos en el altar, infidelidad matrimonial y una queja descarada: «¿Dónde está el Dios de justicia?» (Malaquías 2:17).
                    
                    La respuesta profética en Malaquías 3:1 es inmediata: «He aquí, yo envío mi mensajero, el cual preparará el camino delante de mí; y vendrá súbitamente a su templo el Señor a quien vosotros buscáis, y el ángel del pacto, a quien deseáis vosotros. He aquí viene, ha dicho Jehová de los ejércitos».
                    
                    La profecía anuncia dos figuras:
                    1. El Heraldo Precursor: Cumplido históricamente en Juan el Bautista, quien vino en el espíritu y poder de Elías preparando el camino en el desierto (Lucas 1:17; Mateo 11:10).
                    2. El Señor Mismo, el Ángel del Pacto: El Señor Jesucristo entrando en el templo de Jerusalén. Su venida es purificadora: «¿Y quién podrá soportar el tiempo de su venida? ¿o quién podrá estar en pie cuando él se manifieste? Porque él es como fuego purificador, y como jabón de lavadores» (Mal 3:2).
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    En Malaquías 4 se traza el contraste final entre el destino de los soberbios y la gloria de los que temen al Señor:
                    - Para los impíos: «viene el día ardiente como un horno, y todos los soberbios y todos los que hacen maldad serán estopa» (v. 1).
                    - Para el remanente fiel (v. 2): «Mas a vosotros los que teméis mi nombre, nacerá el Sol de justicia, y en sus alas traerá salvación; y saldréis, y saltaréis como becerros de la manada».
                    
                    Cristo es el Sol de Justicia («Shemesh Tzedaká»). El Antiguo Testamento concluye con la mirada puesta en el horizonte esperando el amanecer del Mesías. Cuando Zacarías profetizó en el nacimiento de Juan el Bautista, declaró: «por la entrañable misericordia de nuestro Dios, con que nos visitó desde lo alto la aurora, para dar luz a los que habitan en tinieblas y en sombra de muerte» (Lucas 1:78-79). Cristo disipa la noche del pecado e inaugura el día eterno de la salvación.
                """,
                doctrinalThesis = "Malaquías cierra el canon del Antiguo Testamento anunciando al precursor profético y la venida del Mesías como el Sol de Justicia que purifica y redime a los suyos.",
                exegeticalAnalysis = "El título mesiánico «Shemesh Tzedaká» (Sol de Justicia) en Malaquías 4:2 proclama a Cristo como la fuente de justicia y sanidad («marpé») que disipa las tinieblas espirituales de la humanidad."
            ),

            // DÍA 8
            createStudyDay(
                dayNumber = 8,
                title = "Día 8: El Discurso de los Olivos I: Señales y Principio de Dolores",
                passagesSummary = "Mateo 24:1-14; Marcos 13:1-13; Lucas 21:5-19",
                primaryBookId = 40,
                primaryChapter = 24,
                passages = listOf(seg(40, "Mateo", 24), seg(41, "Marcos", 13), seg(42, "Lucas", 21)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    Pocos días antes de su crucifixión, Jesús salió del templo de Jerusalén por última vez (relato registrado en los tres sinópticos: Mateo 24:1-14; Marcos 13:1-13; Lucas 21:5-19). Cuando sus discípulos le señalaron con admiración los colosales sillares de piedra del complejo herodiano, el Señor profetizó con solemnidad: «¿Veis todo esto? De cierto os digo, que no quedará aquí piedra sobre piedra, que no sea derribada» (Mateo 24:2).
                    
                    Sentado después en la ladera del Monte de los Olivos con vista panorámica a la ciudad santa, los discípulos se acercaron aparte formulándole una triple pregunta (v. 3):
                    1. «¿Cuándo serán estas cosas?» (la destrucción del templo).
                    2. «¿Y qué señal habrá de tu venida [parousia]?».
                    3. «¿Y del fin del siglo [synteleia tou aiōnos]?».
                    
                    La primera advertencia de Jesús en el versículo 4 no fue una predicción cósmica, sino un mandato pastoral de discernimiento: «Mirad que nadie os engañe. Porque vendrán muchos en mi nombre, diciendo: Yo soy el Cristo; y a muchos engañarán». 
                    
                    Jesús enumera luego una serie de fenómenos que sacudirían al mundo: guerras y rumores de guerras, levantamiento de nación contra nación y reino contra reino, pestes, hambres y terremotos en diferentes lugares (vv. 6-7). Pero el Señor añade con serenidad soberana: «pero aún no es el fin... Y todo esto será principio de dolores» («archē ōdinōn»).
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    La palabra griega para dolores es «ōdinōn», que designa específicamente los dolores de parto de una mujer a punto de dar a luz. En los dolores de parto ocurren dos cosas: aumentan en frecuencia y aumentan en intensidad a medida que el nacimiento se aproxima. Las crisis globales no son el fin en sí mismas; son los espasmos precursores que anuncian que el viejo orden caduca y que el Reino de Dios se acerca.
                    
                    En medio de la persecución y el enfriamiento del amor de muchos por haberse multiplicado la maldad (v. 12), Jesús establece la prioridad suprema e innegociable de la Iglesia en el versículo 14: «Y será predicado este evangelio del reino en todo el mundo, para testimonio a todas las naciones; y entonces vendrá el fin». El reloj escatológico no se acelera con pánico ni con especulaciones geopolíticas; avanza al compás de la proclamación misionera del Evangelio a todas las etnias de la tierra.
                """,
                doctrinalThesis = "Las catástrofes mundiales y los engaños religiosos son dolores de parto preliminares; la prioridad de la Iglesia es la proclamación incansable del Evangelio a todas las naciones.",
                exegeticalAnalysis = "La metáfora de los «archē ōdinōn» (dolores de parto) en Mateo 24:8 ilustra la progresión e intensidad de las señales escatológicas subordinadas a la consumación misionera de Mateo 24:14."
            ),

            // DÍA 9
            createStudyDay(
                dayNumber = 9,
                title = "Día 9: El Discurso de los Olivos II: La Gran Tribulación y la Segunda Venida",
                passagesSummary = "Mateo 24:15-31; Daniel 12:1-3; Apocalipsis 1:7",
                primaryBookId = 40,
                primaryChapter = 24,
                passages = listOf(seg(40, "Mateo", 24), seg(27, "Daniel", 12), seg(66, "Apocalipsis", 1)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En Mateo 24:15, el Señor Jesús introduce una clave hermenéutica directa: «Por tanto, cuando veáis en el lugar santo la abominación desoladora de que habló el profeta Daniel (el que lee, entienda), entonces los que estén en Judea, huyan a los montes».
                    
                    En el versículo 21 se profetiza el período de mayor angustia de la historia humana (anunciado en Daniel 12:1): «porque habrá entonces gran tribulación, cual no la ha habido desde el principio del mundo hasta ahora, ni la habrá. Y si aquellos días no fuesen acortados, nadie sería salvo; mas por causa de los escogidos, aquellos días serán acortados».
                    
                    Jesús advierte que en esos días se levantarán falsos cristos y falsos profetas haciendo grandes señales y prodigios para engañar, si fuese posible, aun a los escogidos (v. 24). Si alguien dice «mira, el Cristo está en el desierto o en las habitaciones secretas», no le creáis.
                    
                    ¿Por qué? Porque la venida de Cristo no será un evento clandestino, secreto ni local: «Porque como el relámpago que sale del oriente y se muestra hasta el occidente, así será también la venida del Hijo del Hombre» (v. 27; como proclama Apocalipsis 1:7: «He aquí que viene con las nubes, y todo ojo le verá»). La Segunda Venida de Cristo será un acontecimiento cósmico, visible, inconfundible y público ante los ojos de toda la humanidad.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    En los versículos 29 al 31 se describe el desenlace triunfal:
                    «E inmediatamente después de la tribulación de aquellos días, el sol se oscurecerá, y la luna no dará su resplandor, y las estrellas caerán del cielo, y las potencias de los cielos serán conmovidas. Entonces aparecerá la señal del Hijo del Hombre en el cielo; y entonces lamentarán todas las tribus de la tierra, y verán al Hijo del Hombre viniendo sobre las nubes del cielo, con poder y gran gloria. Y enviará sus ángeles con gran voz de trompeta, y juntarán a sus escogidos, de los cuatro vientos, desde un extremo del cielo hasta el otro».
                    
                    El Señor que vino por primera vez en humildad, nacido en un pesebre y crucificado en flaqueza como Siervo Sufriente, regresará en gloria majestuosa como Rey soberano y Juez supremo de la creación. La reunión de los escogidos por el toque de trompeta es la consumación de la esperanza de todos los redimidos de la historia.
                """,
                doctrinalThesis = "La Segunda Venida de Cristo será un evento público, glorioso y cósmico que pondrá fin a la tribulación, juzgará a los rebeldes y reunirá a todos los escogidos en gloria.",
                exegeticalAnalysis = "La comparación de la Parusía con el relámpago que cruza el cielo (Mt 24:27) desmantela cualquier pretensión de apariciones secretas o sectarias, confirmando la visibilidad universal de su venida."
            ),

            // DÍA 10
            createStudyDay(
                dayNumber = 10,
                title = "Día 10: Las Parábolas de la Vigilancia: Las Vírgenes y los Talentos",
                passagesSummary = "Mateo 24:36-51; Mateo 25:1-30",
                primaryBookId = 40,
                primaryChapter = 24,
                passages = listOf(seg(40, "Mateo", 24), seg(40, "Mateo", 25)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    Tras revelar los acontecimientos proféticos del fin, Jesús pronuncia una advertencia perentoria en Mateo 24:36: «Pero del día y la hora nadie sabe, ni aun los ángeles de los cielos, sino sólo mi Padre». Quien pretenda calcular fechas exactas para el retorno de Cristo usurpa las prerrogativas del Padre y contradice las palabras directas del Salvador. Por tanto, el mandamiento innegociable es: «Velad, pues, porque no sabéis a qué hora ha de venir vuestro Señor» (v. 42).
                    
                    Para ilustrar la actitud debida ante la espera, Jesús relata dos parábolas inmortales en Mateo 25:
                    1. La Parábola de las Diez Vírgenes (Mt 25:1-13): Diez doncellas salieron a recibir al novio en las bodas judías. Cinco eran prudentes y cinco insensatas. Las prudentes tomaron aceite en sus vasijas juntamente con sus lámparas; las insensatas solo tomaron las lámparas sin reserva de aceite. El novio se tardó; a la medianoche se oyó el clamor: «¡Aquí viene el esposo; salid a recibirle!». Las insensatas no pudieron entrar porque sus lámparas se apagaban y la puerta fue cerrada con la sentencia aterradora: «De cierto os digo, que no os conozco».
                    2. La Parábola de los Talentos (Mt 25:14-30): Un señor entregó sus bienes a sus siervos según su capacidad (a uno cinco talentos, a otro dos y a otro uno). Los dos primeros negociaron fielmente y duplicaron sus talentos, recibiendo el elogio: «Bien, buen siervo y fiel; sobre poco has sido fiel, sobre mucho te pondré; entra en el gozo de tu señor». El tercero escondió el talento en la tierra por desconfianza y pereza, siendo reprendido como siervo malo y negligente.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    Ambas parábolas enseñan que la vigilancia escatológica no es un misticismo ocioso que se sienta en una colina a mirar las nubes; es una vida activa, fiel y santa:
                    - Las diez vírgenes enseñan la necesidad de la gracia interior genuina (el aceite del Espíritu Santo): la religión externa vacía no resistirá la tardanza de la medianoche.
                    - Los talentos enseñan la mayordomía diligente: el Señor nos ha confiado dones, tiempo, Evangelio y recursos materiales para trabajar activamente por su Reino mientras Él regresa.
                    
                    Vivir despiertos significa vivir cada día como si Cristo pudiera regresar hoy mismo, y trabajar con diligencia misionera como si fuera a tardar cien años más.
                """,
                doctrinalThesis = "La ignorancia del día y de la hora demanda una vigilancia espiritual constante caracterizada por la vida regenerada en el Espíritu y la mayordomía fiel de los dones de Dios.",
                exegeticalAnalysis = "El imperativo «Grēgoreite» (velad) en Mateo 24:42 y las parábolas de Mateo 25 exigen una preparación interior genuina (el aceite) y un servicio activo (los talentos) frente a la venida inesperada del Esposo."
            ),

            // DÍA 11
            createStudyDay(
                dayNumber = 11,
                title = "Día 11: 1 Tesalonicenses 4: El Arrebatamiento y el Consuelo de la Resurrección",
                passagesSummary = "1 Tesalonicenses 4:13-18; 1 Corintios 15:51-54",
                primaryBookId = 52,
                primaryChapter = 4,
                passages = listOf(seg(52, "1 Tesalonicenses", 4), seg(46, "1 Corintios", 15)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En la joven iglesia de Tesalónica existía una profunda zozobra pastoral: algunos miembros de la congregación habían muerto físicamente, y los creyentes temían que sus seres queridos fallecidos se perdieran la gloria y el gozo de la venida de Cristo. En 1 Tesalonicenses 4:13-18, el apóstol Pablo disipa toda angustia exponiendo una revelación recibida «en palabra del Señor» (v. 15):
                    
                    «Tampoco queremos, hermanos, que ignoréis acerca de los que duermen, para que no os entristezcáis como los otros que no tienen esperanza» (v. 13). Pablo utiliza el hermoso eufemismo cristiano de «dormir» («koimaō»): para el creyente, la muerte física no es la extinción aniquiladora ni una prisión definitiva; es un sueño temporal del cuerpo mientras el alma está con Cristo, aguardando el despertar glorioso de la resurrección.
                    
                    En los versículos 16 y 17 se detalla la secuencia triunfal (complementada con 1 Corintios 15:51-54, donde lo corruptible se viste de incorrupción en un abrir y cerrar de ojos a la final trompeta):
                    1. «Porque el Señor mismo con voz de mando, con voz de arcángel, y con trompeta de Dios, descenderá del cielo»: Cristo no enviará a un delegado; Él mismo descenderá con autoridad soberana.
                    2. «Y los muertos en Cristo resucitarán primero»: Lejos de quedar en desventaja, los creyentes fallecidos tendrán la primacía de ser levantados en cuerpos incorruptibles en primer lugar.
                    3. «Luego nosotros los que vivimos, los que hayamos quedado, seremos arrebatados juntamente con ellos en las nubes para recibir al Señor en el aire, y así estaremos siempre con el Señor».
                    
                    El verbo traducido como «seremos arrebatados» es el griego «harpagēsomethá» (del verbo «harpazō», en la Vulgata latina: «rapiemur», de donde proviene el término «rapto» o arrebatamiento). Significa arrebatar con fuerza irresistible, rescatar o tomar posesión soberana de algo.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    La frase «para recibir al Señor en el aire» utiliza el término técnico «apantēsis». En la cultura helenística, cuando un rey victorioso o un dignatario se aproximaba a una ciudad, los ciudadanos salían fuera de las murallas a su encuentro («apantēsis») para recibirlo con aclamación y escoltarlo triunfalmente de regreso.
                    
                    El versículo 18 concluye con el propósito exclusivo de este pasaje: «Por tanto, alentaos los unos a los otros con estas palabras». La doctrina del arrebatamiento y de la resurrección no fue escrita para generar discusiones teológicas divisivas, sino para ser el bálsamo supremo de consuelo pastoral junto a la tumba de los santos. La muerte física ha sido vencida en Cristo.
                """,
                doctrinalThesis = "En la venida del Señor, los muertos en Cristo resucitarán primero y los creyentes vivos serán arrebatados (harpazō) para estar siempre con el Señor en gloria inmarcesible.",
                exegeticalAnalysis = "El vocablo «harpazō» en 1 Tesalonicenses 4:17 y la designación de la muerte como «dormir» («koimōmenōn») fundamentan la esperanza consoladora de la resurrección corporal frente al duelo pagano sin esperanza."
            ),

            // DÍA 12
            createStudyDay(
                dayNumber = 12,
                title = "Día 12: 1 Tesalonicenses 5: Hijos de la Luz y la Sobriedad en la Espera",
                passagesSummary = "1 Tesalonicenses 5:1-11; Efesios 5:8-14",
                primaryBookId = 52,
                primaryChapter = 5,
                passages = listOf(seg(52, "1 Tesalonicenses", 5), seg(49, "Efesios", 5)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    Continuando de forma inmediata con el tema de la venida de Cristo, Pablo aborda en 1 Tesalonicenses 5:1-3 la cuestión de «los tiempos y las ocasiones» («chronoi kai kairoi»): «Porque vosotros sabéis perfectamente que el día del Señor vendrá así como ladrón en la noche; que cuando digan: Paz y seguridad, entonces vendrá sobre ellos destrucción repentina, como los dolores a la mujer encinta, y no escaparán».
                    
                    Para el mundo incrédulo sumido en la autosuficiencia y la diversión mundana, el Día del Señor irrumpirá como un ladrón en la noche («kleptēs en nykti»), tomando a todos por sorpresa en medio de sus falsas ilusiones de paz terrenal.
                    
                    Sin embargo, en el versículo 4 Pablo introduce un contraste fundamental que cambia por completo la condición del creyente: «Mas vosotros, hermanos, no estáis en tinieblas, para que aquel día os sorprenda como ladrón. Porque todos vosotros sois hijos de luz e hijos del día; no somos de la noche ni de las tinieblas. Por tanto, no durmamos como los demás, sino velemos y seamos sobrios» (vv. 4-6; como exhorta también Efesios 5:8-14: «antes erais tinieblas, mas ahora sois luz en el Señor; andad como hijos de luz»).
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    Estar despiertos y ser sobrios («nēphōmen») implica una actitud de lucidez moral y equilibrio espiritual. Pablo describe la armadura del creyente escatológico en el versículo 8: «vestidos con la coraza de fe y de amor, y con la esperanza de salvación como yelmo». Las tres virtudes teologales —fe, amor y esperanza (1 Corintios 13:13)— son el equipamiento que protege nuestro corazón y nuestra mente de las seducciones del mundo.
                    
                    El versículo 9 corona la seguridad del pueblo de Dios con una promesa rotunda: «Porque no nos ha puesto Dios para ira, sino para alcanzar salvación por medio de nuestro Señor Jesucristo, quien murió por nosotros para que ya sea que velemos, o que durmamos, vivamos juntamente con él».
                    
                    El destino del creyente no es la ira judicial de Dios, la cual fue absorbida por Cristo en la cruz; nuestro destino es la plenitud de la salvación. Con esta certeza inamovible, la Iglesia vive con serenidad, consolando y edificando a los hermanos en el camino hacia la patria celestial.
                """,
                doctrinalThesis = "Los creyentes, como hijos de la luz, no serán sorprendidos por el Día del Señor en tinieblas, pues Dios no nos ha puesto para ira sino para salvación eterna en Cristo.",
                exegeticalAnalysis = "La antítesis entre tinieblas/noche y luz/día en 1 Tesalonicenses 5:4-8 establece el imperativo de la sobriedad ética («nēphōmen») armada con fe, amor y la esperanza de la salvación."
            ),

            // DÍA 13
            createStudyDay(
                dayNumber = 13,
                title = "Día 13: 2 Tesalonicenses 2: La Apostasía y el Hombre de Pecado",
                passagesSummary = "2 Tesalonicenses 2:1-12; 1 Juan 2:18-22",
                primaryBookId = 53,
                primaryChapter = 2,
                passages = listOf(seg(53, "2 Tesalonicenses", 2), seg(62, "1 Juan", 2)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En la segunda epístola a los Tesalonicenses (capítulo 2), Pablo corrige una grave perturbación doctrinal que había alarmado a la iglesia: algunos falsos maestros habían falsificado una supuesta carta de Pablo afirmando que «el día del Señor ya había comenzado» y que la iglesia se hallaba en medio del gran juicio de la tribulación.
                    
                    Pablo apela a la calma pastoral en los versículos 1 al 3: «Pero con respecto a la venida de nuestro Señor Jesucristo, y nuestra reunión con él, os rogamos, hermanos, que no os dejéis mover fácilmente de vuestro modo de pensar... Nadie os engañe en ninguna manera; porque no vendrá sin que antes venga la apostasía, y se manifieste el hombre de pecado, el hijo de perdición».
                    
                    Pablo revela dos señales precursoras ineludibles:
                    1. «La Apostasía» («hē apostasia»): No un enfriamiento espiritual común, sino un abandono y rebelión deliberada a gran escala contra la verdad del Evangelio desde dentro de la profesión religiosa.
                    2. «El Hombre de Pecado, el Hijo de Perdición» (el Anticristo escatológico, prefigurado por los muchos anticristos de los que advierte 1 Juan 2:18-22): Un líder inicuo «el cual se opone y se levanta contra todo lo que se llama Dios o es objeto de culto; tanto que se sienta en el templo de Dios como Dios, haciéndose pasar por Dios» (v. 4).
                    
                    En los versículos 6 y 7, Pablo explica que actualmente opera «el misterio de la iniquidad» («to mystērion tēs anomias», el espíritu anticristiano ya activo en el mundo), pero hay un poder y agente que lo detiene («to katechon» / «ho katechōn», tradicionalmente identificado con la presencia del Espíritu Santo y el gobierno moral ordenado por Dios) hasta que a su tiempo sea quitado de en medio.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    El versículo 8 describe la facilidad soberana con la que el Señor Jesús aniquilará a este formidable gobernante demoníaco en su venida: «Y entonces se manifestará aquel inicuo, a quien el Señor matará con el espíritu de su boca, y destruirá con el resplandor de su venida».
                    
                    No habrá una batalla cósmica incierta de fuerzas equivalentes: el Anticristo, con todas sus señales mentirosas y prodigios engañosos satánicos (v. 9), será destruido simplemente por el aliento («pneuma») de la boca de Cristo y el resplandor radiante de su manifestación («epiphaneia»). Dios tiene el control absoluto de los tiempos y del desenlace final.
                """,
                doctrinalThesis = "Antes del día final se manifestará la apostasía y el hombre de pecado (Anticristo), a quien el Señor Jesús destruirá sin esfuerzo con el aliento de su boca en su gloriosa venida.",
                exegeticalAnalysis = "La derrota del inicuo «tōi pneumati tou stomatos autou» (con el aliento de su boca) en 2 Tesalonicenses 2:8 ratifica la omnipotencia de Cristo sobre el engaño satánico escatológico."
            ),

            // DÍA 14
            createStudyDay(
                dayNumber = 14,
                title = "Día 14: 2 Pedro 3: La Paciencia del Señor y la Disolución Cósmica por Fuego",
                passagesSummary = "2 Pedro 3:1-14; Salmo 90:1-4",
                primaryBookId = 61,
                primaryChapter = 3,
                passages = listOf(seg(61, "2 Pedro", 3), seg(19, "Salmos", 90)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En el último capítulo de su segunda epístola, el apóstol Pedro advierte a la Iglesia sobre la proliferación de burladores escépticos en los postreros días: «diciendo: ¿Dónde está la promesa de su advenimiento? Porque desde el día en que los padres durmieron, todas las cosas permanecen así como desde el principio de la creación» (2 Pedro 3:3-4). Los burladores abrazan un naturalismo cerrado (uniformitarismo) que niega la intervención sobrenatural de Dios en la historia humana.
                    
                    Pedro refuta su necedad recordándoles el cataclismo del Diluvio: el mundo antiguo fue destruido anegado en agua por la misma Palabra divina (vv. 5-6). Y en el versículo 7 añade la advertencia para el presente: «pero los cielos y la tierra que existen ahora, están reservados por la misma palabra, guardados para el fuego en el día del juicio y de la perdición de los hombres impíos».
                    
                    En los versículos 8 y 9 se explica la aparente tardanza del Señor desde la perspectiva divina (aludiendo al Salmo 90:1-4: «Porque mil años delante de tus ojos son como el día de ayer, que pasó»): «que para con el Señor un día es como mil años, y mil años como un día. El Señor no retarda su promesa, según algunos la tienen por tardanza, sino que es paciente para con nosotros, no queriendo que ninguno perezca, sino que todos procedan al arrepentimiento». Dios no está retrasado según un reloj humano; su paciencia dilata el juicio para que la plenitud de sus escogidos sea alcanzada por la gracia salvadora.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    En el versículo 10 se profetiza la disolución purificadora final: «Pero el día del Señor vendrá como ladrón en la noche; en el cual los cielos pasarán con grande estruendo, y los elementos ardiendo serán deshechos, y la tierra y las obras que en ella hay serán quemadas».
                    
                    La conclusión pastoral de Pedro en los versículos 11 y 14 es el corazón de la vida cristiana: «Puesto que todas estas cosas han de ser deshechas, ¡cómo no debéis vosotros andar en santa y piadosa manera de vivir, esperando y apresurando la venida del día de Dios!... Por lo cual, oh amados, estando en espera de estas cosas, procurad con diligencia ser hallados por él sin mancha y sin reproche, en paz». Si este mundo material presente será purificado por fuego, no pongamos nuestro corazón en las riquezas perecederas; atesoremos la justicia del Reino que permanecerá para siempre.
                """,
                doctrinalThesis = "La paciencia de Dios dilata el juicio cósmico por fuego para permitir la salvación de sus escogidos, llamando a la Iglesia a vivir en santidad irreprochable mientras aguarda la venida del Señor.",
                exegeticalAnalysis = "El término «makrothymia» (paciencia/longanimidad) en 2 Pedro 3:9 define el motivo del intervalo histórico presente como una oportunidad soberana de gracia y evangelización previa a la purificación cósmica."
            ),

            // DÍA 15
            createStudyDay(
                dayNumber = 15,
                title = "Día 15: Apocalipsis 1: La Revelación de Jesucristo y su Gloria Presente",
                passagesSummary = "Apocalipsis 1:1-20; Daniel 7:9-14",
                primaryBookId = 66,
                primaryChapter = 1,
                passages = listOf(seg(66, "Apocalipsis", 1), seg(27, "Daniel", 7)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    El último libro de las Sagradas Escrituras fue escrito por el apóstol Juan alrededor del año 95 d.C., durante el reinado del cruel emperador romano Domiciano, estando desterrado en la inhóspita isla de Patmos «por causa de la palabra de Dios y el testimonio de Jesucristo» (Ap 1:9).
                    
                    El título del libro se revela en su primera palabra: «Apocalipsis» («Apokalypsis Iēsou Christou», que significa literalmente «quitar el velo», revelación o manifestación visible de Jesucristo). No es un libro diseñado para confundir ni aterrorizar; es la revelación de la victoria consumada de Cristo entregada a una Iglesia perseguida y ensangrentada.
                    
                    El versículo 3 pronuncia la primera de las siete bienaventuranzas del libro: «Bienaventurado el que lee, y los que oyen las palabras de esta profecía, y guardan las cosas en ella escritas; porque el tiempo está cerca».
                    
                    En el versículo 7 resuena la proclamación de la Parusía: «He aquí que viene con las nubes, y todo ojo le verá, y los que le traspasaron; y todos los linajes de la tierra harán lamentación por él. Sí, amén».
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    Estando Juan en el Espíritu en el día del Señor, escuchó detrás de sí una gran voz como de trompeta y se volvió para ver la voz que hablaba con él. Al volverse, contempló siete candeleros de oro (las siete iglesias) y en medio de ellos a una figura de majestad sobrecogedora: Cristo glorificado (vv. 12-16).
                    - Vestido de una ropa que llegaba hasta los pies, ceñido con cinto de oro (Sumo Sacerdote y Rey).
                    - Su cabeza y sus cabellos blancos como lana blanca (Eternidad y Sabiduría del Anciano de Días).
                    - Sus ojos como llama de fuego (Omnisciencia que escudriña los corazones).
                    - Sus pies semejantes al bronce bruñido refulgente (Juicio santo inflexible).
                    - Su voz como estruendo de muchas aguas (Soberanía y autoridad omnipotente).
                    - De su boca salía una espada aguda de dos filos (La Palabra viva que juzga).
                    - Su rostro como el sol cuando brilla con toda su fuerza.
                    
                    Al verle, Juan cayó a sus pies como muerto. Pero Jesús puso su diestra sobre él diciéndole: «No temas; yo soy el primero y el último; y el que vivo, y estuve muerto; mas he aquí que vivo por los siglos de los siglos, amén. Y tengo las llaves de la muerte y del Hades» (vv. 17-18). La Iglesia no está a merced de los césares terrenales; está en las manos del Salvador resucitado que gobierna la vida, la muerte y la eternidad.
                """,
                doctrinalThesis = "El Apocalipsis es la manifestación gloriosa de Jesucristo resucitado, quien tiene soberanía absoluta sobre la historia y cuida a su Iglesia como Sumo Sacerdote victorioso.",
                exegeticalAnalysis = "El término «Apokalypsis» (desvelar) en Apocalipsis 1:1 y la teofanía de los versículos 12 al 18 presentan a Cristo glorificado con las prerrogativas exclusivas del Anciano de Días de Daniel 7."
            ),

            // DÍA 16
            createStudyDay(
                dayNumber = 16,
                title = "Día 16: Apocalipsis 2 y 3: Las Cartas a las Siete Iglesias y la Victoria",
                passagesSummary = "Apocalipsis 2:1-11; Apocalipsis 3:14-22",
                primaryBookId = 66,
                primaryChapter = 2,
                passages = listOf(seg(66, "Apocalipsis", 2), seg(66, "Apocalipsis", 3)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En Apocalipsis 2 y 3 se registran las siete cartas pastorales que el Cristo resucitado dictó a siete iglesias históricas del Asia Menor (Éfeso, Esmirna, Pérgamo, Tiatira, Sardis, Filadelfia y Laodicea). Estas cartas poseen un triple valor profético: eran mensajes directos a congregaciones reales del siglo I, son retratos representativos de las diversas condiciones espirituales de cualquier iglesia en cualquier época, y prefiguran el desarrollo histórico del testimonio eclesial.
                    
                    Cada carta sigue una estructura teológica magistral:
                    1. Presentación de Cristo: Usando un rasgo de la visión de gloria del capítulo 1 adecuado a la necesidad de la congregación.
                    2. Diagnóstico y Elogio: Cristo conoce íntimamente las obras, aflicciones y fidelidad de su pueblo («Yo conozco tus obras»).
                    3. Reprensión Pastoral: Identificando los peligros mortales (la pérdida del primer amor en Éfeso, la tolerancia de falsas doctrinas en Pérgamo y Tiatira, la fama de estar vivo estando muerto en Sardis, o la tibieza nauseabunda en Laodicea).
                    4. Promesa al Vencedor («tōi nikōnti»): Una bendición escatológica de recompensa eterna ligada a la perseverancia en la gracia.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    Consideremos dos ejemplos de contraste elocuente:
                    - La Iglesia de Esmirna (Ap 2:8-11): Una iglesia pobre económicamente y ferozmente perseguida por el martirio («Sé fiel hasta la muerte, y yo te daré la corona de la vida»). Cristo no le dirige ninguna reprensión; solo consuelo y victoria sobre la muerte segunda.
                    - La Iglesia de Laodicea (Ap 3:14-22): Una iglesia próspera, rica y autosuficiente («Yo soy rico, y me he enriquecido, y de ninguna cosa tengo necesidad»). Cristo le diagnostica su verdadera condición espiritual: «desventurado, miserable, pobre, ciego y desnudo». Por ser tibia, el Señor amenaza con vomitarla de su boca; pero con infinita ternura añade: «He aquí, yo estoy a la puerta y llamo; si alguno oye mi voz y abre la puerta, entraré a él, y cenaré con él, y él conmigo» (v. 20).
                    
                    Cristo purifica a su Iglesia porque la ama. El llamado perentorio para nosotros hoy es: «El que tiene oído, oiga lo que el Espíritu dice a las iglesias».
                """,
                doctrinalThesis = "Cristo escudriña, purifica y pastorea soberanamente a su Iglesia, llamando a los santos a vencer las seducciones del mundo y la tibieza espiritual mediante la perseverancia en el primer amor.",
                exegeticalAnalysis = "La fórmula repetida «tōi nikōnti» (al que venciere) en las siete cartas de Apocalipsis 2 y 3 vincula la fidelidad presente con la participación consumada en los galardones del Reino eterno."
            ),

            // DÍA 17
            createStudyDay(
                dayNumber = 17,
                title = "Día 17: Apocalipsis 4: El Trono Celestial y la Adoración del Creador",
                passagesSummary = "Apocalipsis 4:1-11; Isaías 6:1-5",
                primaryBookId = 66,
                primaryChapter = 4,
                passages = listOf(seg(66, "Apocalipsis", 4), seg(23, "Isaías", 6)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En Apocalipsis 4:1, una puerta se abre en el cielo y la voz de trompeta ordena a Juan: «Sube acá, y yo te mostraré las cosas que sucederán después de estas». Inmediatamente el apóstol es arrebatado en el Espíritu y contempla el centro neurálgico del universo entero: «y he aquí, un trono establecido en el cielo, y en el trono, uno sentado» (v. 2).
                    
                    La palabra «trono» («thronos») aparece más de cuarenta veces en Apocalipsis. Antes de que se desaten los juicios de los sellos, trompetas y copas sobre la tierra, Dios sitúa la mirada de la Iglesia en el Trono celestial inamovible. Mientras en la tierra los emperadores romanos dictaban decretos de persecución creyéndose dueños del mundo, en el cielo hay un solo Trono supremo que gobierna la historia con soberanía incontestable.
                    
                    Alrededor del trono resplandece un arco iris semejante a la esmeralda (símbolo del pacto de gracia inmutable). Rodeando el trono se sientan veinticuatro ancianos vestidos de ropas blancas con coronas de oro (representando al pueblo de Dios de ambos pactos: los doce patriarcas y los doce apóstoles). Del trono salen relámpagos, truenos y voces de majestad santa.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    En medio del trono y alrededor de él se hallan cuatro seres vivientes llenos de ojos por delante y por detrás, con figuras de león, becerro, rostro de hombre y águila volando (representando la plenitud de la creación redimida). Día y noche proclaman sin cesar el Trisagio celestial de Isaías 6:
                    «Santo, santo, santo es el Señor Dios Todopoderoso, el que era, el que es, y el que ha de venir» (v. 8).
                    
                    Y cada vez que los seres vivientes adoran, los veinticuatro ancianos se postran de rodillas y echan sus coronas delante del trono diciendo: «Señor, digno eres de recibir la gloria y la honra y el poder; porque tú creaste todas las cosas, y por tu voluntad existen y fueron creadas» (v. 11).
                    
                    La verdadera adoración bíblica es teocéntrica: consiste en rendir todas nuestras coronas humanas a los pies del Creador soberano, reconociendo que nuestra vida, aliento y redención existen únicamente por el puro afecto de su santa voluntad.
                """,
                doctrinalThesis = "El trono celestial es el centro soberano del universo, donde el Dios santo y todopoderoso es adorado eternamente como el Creador y Sustentador de todas las cosas.",
                exegeticalAnalysis = "El vocablo «thronos» en Apocalipsis 4 establece el punto de anclaje teológico de todo el libro: nada ocurre en la tierra fuera de los decretos y permisiones soberanas del Dios tres veces Santo."
            ),

            // DÍA 18
            createStudyDay(
                dayNumber = 18,
                title = "Día 18: Apocalipsis 5: El Cordero Inmolado y el Libro de los Sellos",
                passagesSummary = "Apocalipsis 5:1-14; Juan 1:29; Filipenses 2:9-11",
                primaryBookId = 66,
                primaryChapter = 5,
                passages = listOf(seg(66, "Apocalipsis", 5), seg(43, "Juan", 1), seg(50, "Filipenses", 2)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En Apocalipsis 5:1, Juan ve en la mano derecha de Aquel que está sentado en el trono un libro escrito por dentro y por fuera, sellado con siete sellos. En el mundo antiguo, un documento sellado con siete sellos era un título de propiedad o el testamento legal de un monarca que solo el heredero legítimo con autoridad plenipotenciaria podía abrir.
                    
                    Un ángel fuerte proclama a gran voz: «¿Quién es digno de abrir el libro y desatar sus sellos?». Y el versículo 3 registra un silencio cósmico desolador: ninguno, ni en el cielo, ni en la tierra, ni debajo de la tierra, podía abrir el libro ni aun mirarlo. Ante la tragedia de que el plan redentor quedara sellado y sin heredero, Juan llora amargamente.
                    
                    Pero uno de los veinticuatro ancianos le consuela con palabras inmortales: «No llores. He aquí que el León de la tribu de Judá, la raíz de David, ha vencido para abrir el libro y desatar sus siete sellos» (v. 5).
                    
                    Y cuando Juan se vuelve esperando ver a un león feroz rugiendo con garras destructivas, lo que contempla en el versículo 6 lo deja sobrecogido: «Y miré, y vi... en medio de los ancianos, estaba en pie un Cordero como inmolado» (Arnion hōs esphagmenon).
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    La paradoja teológica más sublime de la Escritura resplandece aquí: el León que vence es el Cordero inmolado (el Cordero de Dios que quita el pecado del mundo, Juan 1:29). Cristo conquista no mediante la violencia opresora de los tiranos mundiales, sino mediante su sacrificio expiatorio en la cruz del Calvario. El Cordero está «en pie» (resucitado y victorioso), pero lleva las señales de haber sido degollado («esphagmenon», en tiempo perfecto: las marcas eternas de los clavos que testifican de su victoria).
                    
                    El Cordero se acerca y toma el libro de la mano derecha del que está sentado en el trono. En ese instante, los cuatro seres vivientes y los veinticuatro ancianos caen postrados con arpas y copas de oro llenas de incienso (las oraciones de los santos) y entonan un cántico nuevo (vv. 9-10):
                    «Digno eres de tomar el libro y de abrir sus sellos; porque tú fuiste inmolado, y con tu sangre nos has redimido para Dios, de todo linaje y lengua y pueblo y nación; y nos has hecho para nuestro Dios reyes y sacerdotes, y reinaremos sobre la tierra».
                    
                    La adoración se expande en círculos concéntricos infinitos: millones de ángeles claman «El Cordero que fue inmolado es digno» (v. 12), y finalmente toda criatura en los cielos, la tierra y el mar proclama gloria eterna al que está sentado en el trono y al Cordero (v. 13; cumpliéndose Filipenses 2:9-11). Jesucristo tiene en sus manos el destino de la historia.
                """,
                doctrinalThesis = "Jesucristo es el León de Judá y el Cordero inmolado, el único digno en todo el universo de desatar los sellos de la historia y consumar la redención eterna con su sangre.",
                exegeticalAnalysis = "La designación «Arnion hōs esphagmenon» en Apocalipsis 5:6 combina la realeza davídica con la expiación sacrificial pascual, proclamando que la cruz es el fundamento de su señorío cósmico."
            ),

            // DÍA 19
            createStudyDay(
                dayNumber = 19,
                title = "Día 19: Apocalipsis 6: La Apertura de los Sellos y los Cuatro Jinetes",
                passagesSummary = "Apocalipsis 6:1-17; Zacarías 1:7-11; Zacarías 6:1-8",
                primaryBookId = 66,
                primaryChapter = 6,
                passages = listOf(seg(66, "Apocalipsis", 6), seg(38, "Zacarías", 1), seg(38, "Zacarías", 6)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En Apocalipsis 6, el Cordero comienza a abrir uno a uno los siete sellos del rollo de la historia, desatando juicios providenciales sobre una tierra rebelde a través de los cuatro jinetes (cuyo simbolismo cromático toma su trasfondo profético de las visiones de caballos y carros de Zacarías 1:7-11 y Zacarías 6:1-8):
                    
                    1. El Primer Sello (vv. 1-2): Un caballo blanco; el que lo montaba tenía un arco militar y le fue dada una corona («stephanos»), y salió venciendo y para vencer. Representa la marcha del militarismo conquistador y las falsas promesas de paz imperial que engañan a las naciones.
                    2. El Segundo Sello (vv. 3-4): Un caballo bermejo (rojo sangre); al jinete le fue dado poder de quitar de la tierra la paz y que se matasen unos a otros, entregándosele una gran espada. Representa la carnicería sangrienta de la guerra civil e internacional.
                    3. El Tercer Sello (vv. 5-6): Un caballo negro; el jinete sostenía una balanza en la mano. Se oye una voz: «Dos libras de trigo por un denario, y seis libras de cebada por un denario; pero no dañes el aceite ni el vino». Representa la hambruna y la inflación económica asfixiante donde el salario de un día entero de trabajo (un denario) apenas alcanza para el pan diario, mientras los lujos de los poderosos permanecen intocables.
                    4. El Cuarto Sello (vv. 7-8): Un caballo amarillo verdoso («chlōros», el color cadavérico de un cuerpo en descomposición); su jinete tenía por nombre Muerte, y el Hades le seguía. Se les dio potestad sobre la cuarta parte de la tierra para matar con espada, con hambre, con mortandad y con las fieras de la tierra.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    En el Quinto Sello (vv. 9-11), la escena cambia al altar celestial: Juan ve bajo el altar las almas de los mártires que habían sido muertos por causa de la palabra de Dios y el testimonio. Claman a gran voz: «¿Hasta cuándo, Señor, santo y verdadero, no juzgas y vengas nuestra sangre en los que moran en la tierra?». Se les dieron vestiduras blancas y se les ordenó descansar un poco de tiempo hasta que se completara el número de sus consiervos que también habían de ser muertos. Dios no olvida el sufrimiento de sus santos; cada lágrima y cada gota de sangre tiene un registro sagrado en el cielo.
                    
                    En el Sexto Sello (vv. 12-17) se produce un colapso cósmico: terremoto colosal, el sol negro como cilicio, la luna como sangre y las estrellas cayendo. Los reyes de la tierra, los magnates, los capitanes y los poderosos huyen aterrorizados a las cavernas de los montes gritando a las peñas: «Caed sobre nosotros, y escondednos del rostro de aquel que está sentado sobre el trono, y de la ira del Cordero; porque el gran día de su ira ha llegado; ¿y quién podrá sostenerse en pie?».
                    
                    No hay nada más aterrador para la soberbia humana que la «ira del Cordero». Aquellos que despreciaron su gracia expiatoria tendrán que encarar su justicia inflexible.
                """,
                doctrinalThesis = "La apertura de los sellos demuestra que Jesucristo gobierna la historia humana, juzgando la injusticia de las naciones y consolando a los mártires que esperan su justicia.",
                exegeticalAnalysis = "La paradoja de «hē orgē tou arniou» (la ira del Cordero) en Apocalipsis 6:16 desmantela el sentimentalismo secular, revelando la santidad judicial ineludible del Redentor frente a la rebelión humana."
            ),

            // DÍA 20
            createStudyDay(
                dayNumber = 20,
                title = "Día 20: Apocalipsis 7: Los 144,000 Sellados y la Multitud Incontable",
                passagesSummary = "Apocalipsis 7:1-17; Ezequiel 9:1-6",
                primaryBookId = 66,
                primaryChapter = 7,
                passages = listOf(seg(66, "Apocalipsis", 7), seg(26, "Ezequiel", 9)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    Entre el sexto sello y el séptimo sello, Dios inserta un interludio celestial de infinito consuelo para responder a la pregunta angustiosa del capítulo 6: «¿Y quién podrá sostenerse en pie?». En Apocalipsis 7 se revela quiénes son los que se sostendrán en pie protegidos por el poder de Dios:
                    
                    1. Los 144,000 Sellados en la Tierra (vv. 1-8): Cuatro ángeles detienen los cuatro vientos de la tierra hasta que los siervos de Dios sean sellados en sus frentes con el sello del Dios vivo (aludiendo a la señal protectora de Ezequiel 9:4). Juan escucha el número de los sellados: 144,000 de todas las tribus de los hijos de Israel (12 x 12 x 1,000: una cifra simbólica que representa la plenitud perfecta y completa del pueblo de Dios preservado invenciblemente en medio de la tribulación terrenal).
                    
                    2. La Gran Multitud Incontable en el Cielo (vv. 9-17): Acto seguido, Juan alza la mirada hacia el trono celestial y contempla una escena gloriosa: «he aquí una gran multitud, la cual nadie podía contar, de todas naciones y tribus y pueblos y lenguas, que estaban delante del trono y en la presencia del Cordero, vestidos de ropas blancas, y con palmas en las manos; y clamaban a gran voz, diciendo: La salvación pertenece a nuestro Dios que está sentado en el trono, y al Cordero».
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    Uno de los ancianos pregunta a Juan: «Estos que están vestidos de ropas blancas, ¿quiénes son, y de dónde han venido?». Y el anciano mismo responde en el versículo 14: «Estos son los que han salido de la gran tribulación, y han lavado sus ropas, y las han emblanquecido en la sangre del Cordero».
                    
                    Nótese la paradoja evangélica: en el mundo físico terrenal, la sangre mancha de rojo; pero en el misterio de la cruz, la sangre del Cordero emblanquece el alma y la hace más blanca que la nieve.
                    
                    Los versículos 15 al 17 concluyen con la bienaventuranza pastoral más dulce de la eternidad:
                    «Ya no tendrán hambre ni sed, y el sol no caerá más sobre ellos, ni calor alguno; porque el Cordero que está en medio del trono los pastoreará, y los guiará a fuentes de aguas de vida; y Dios enjugará toda lágrima de los ojos de ellos».
                    
                    El Cordero inmolado es simultáneamente el Buen Pastor eterno que cuida y sacia para siempre a su grey redimida. Ningún sufrimiento de esta vida presente es comparable al reposo glorioso que aguarda a los santos.
                """,
                doctrinalThesis = "El pueblo de Dios está sellado y protegido soberanamente por la gracia divina en medio de la aflicción, lavando sus vestiduras en la sangre del Cordero para gozar de consuelo eterno.",
                exegeticalAnalysis = "La visión dual de los 144,000 sellados en la tierra (la Iglesia militante protegida) y la multitud incontable en el cielo (la Iglesia triunfante glorificada, Ap 7:9-14) confirma la preservación invencible de los santos."
            ),

            // DÍA 21
            createStudyDay(
                dayNumber = 21,
                title = "Día 21: Apocalipsis 8 y 9: Las Trompetas de Juicio y las Oraciones de los Santos",
                passagesSummary = "Apocalipsis 8:1-13; Apocalipsis 9:1-12, 20-21",
                primaryBookId = 66,
                primaryChapter = 8,
                passages = listOf(seg(66, "Apocalipsis", 8), seg(66, "Apocalipsis", 9)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En Apocalipsis 8:1, el Cordero abre el séptimo y último sello, y acontece un suceso sobrecogedor: «se hizo silencio en el cielo como por media hora». Todo el coro celestial de miríadas de ángeles calla en reverente expectativa.
                    
                    En ese silencio solemne se revela la causa que desata los juicios divinos sobre la tierra (vv. 3-5): un ángel se para ante el altar de oro con un incensario de oro, y se le da mucho incienso para añadirlo a «las oraciones de todos los santos». El humo del incienso con las oraciones del pueblo perseguido asciende a la presencia de Dios; acto seguido, el ángel toma el incensario, lo llena del fuego del altar y lo arroja a la tierra, produciéndose truenos, voces, relámpagos y terremoto.
                    
                    ¡Los juicios de Dios sobre los poderes opresores de la tierra son la respuesta directa del cielo a las oraciones clamorosas de su Iglesia sufrida! Ninguna oración de los santos cae en saco roto.
                    
                    A continuación, los siete ángeles tocan sucesivamente sus trompetas:
                    - Las cuatro primeras trompetas (capítulo 8) afectan la creación ecológica en un tercio (granizo y fuego en la vegetación, una montaña ardiendo en el mar, la estrella Ajenjo en las aguas dulces, y el oscurecimiento del sol y la luna). Son juicios parciales (un tercio) diseñados como toques de advertencia para llamar al arrepentimiento.
                    - Las trompetas quinta y sexta (capítulo 9) desatan tormentos demoníacos: la estrella caída del cielo que abre el pozo del abismo de donde surge una plaga de langostas demoníacas con corazas de hierro y picadura de escorpión capitaneadas por Abadón/Apolión (el Destructor).
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    La tragedia de la dureza del corazón humano sin gracia regeneradora se expone con crudeza al final del capítulo 9 (vv. 20-21): a pesar de estos terribles juicios, «los hombres que no fueron muertos con estas plagas, ni aun así se arrepintieron de las obras de sus manos, ni dejaron de adorar a los demonios y a las imágenes... y no se arrepintieron de sus homicidios, ni de sus hechicerías, ni de su fornicación, ni de sus hurtos».
                    
                    El juicio por sí solo no puede ablandar el corazón humano caído; solo la gracia soberana del Espíritu Santo que aplica la cruz de Cristo puede transformar un corazón de piedra en un corazón quebrantado y salvo.
                """,
                doctrinalThesis = "Las trompetas de juicio son advertencias solemnes que responden a las oraciones de los santos, exponiendo la dureza pertinaz del corazón humano no regenerado.",
                exegeticalAnalysis = "La vinculación entre las oraciones de los santos («hai proseuchai tōn hagiōn») en Apocalipsis 8:3-5 y el lanzamiento del fuego a la tierra demuestra el papel de la intercesión de la Iglesia en el gobierno de la historia."
            ),

            // DÍA 22
            createStudyDay(
                dayNumber = 22,
                title = "Día 22: Apocalipsis 10 y 11: El Librito Dulce y los Dos Testigos",
                passagesSummary = "Apocalipsis 10:1-11; Apocalipsis 11:1-14; Ezequiel 3:1-3",
                primaryBookId = 66,
                primaryChapter = 10,
                passages = listOf(seg(66, "Apocalipsis", 10), seg(66, "Apocalipsis", 11), seg(26, "Ezequiel", 3)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    Entre la sexta y la séptima trompeta se despliega un segundo interludio profético de suprema trascendencia misionera (capítulos 10 y 11). En Apocalipsis 10, un ángel fuerte desciende del cielo envuelto en una nube con el arco iris sobre su cabeza y un librito abierto en su mano. Pone su pie derecho sobre el mar y el izquierdo sobre la tierra jurando solemnemente por el Creador eterno que «el tiempo no sería más» (no habrá más dilación, el misterio de Dios se consumará, vv. 6-7).
                    
                    Dios ordena a Juan tomar el librito y comerlo: «Tómalo, y cómelo; y te amargará el vientre, pero en tu boca será dulce como la miel» (v. 9; como Ezequiel 3:1-3). La Palabra profética de Dios es dulce en la boca por la gloria de sus promesas y salvación, pero amarga en el vientre por la desgarradora realidad del juicio venidero sobre los impíos. Y la orden divina resuena: «Es necesario que profetices otra vez sobre muchos pueblos, naciones, lenguas y reyes» (v. 11).
                    
                    En Apocalipsis 11 entran en escena los «Dos Testigos» proféticos vestidos de cilicio (símbolo del testimonio fiel e inquebrantable de la Iglesia a través del espíritu y poder de Moisés y Elías, la Ley y los Profetas). Tienen poder de cerrar los cielos y herir la tierra con plagas mientras dan su testimonio.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    El versículo 7 contiene una verdad ministerial conmovedora: «Cuando hayan acabado su testimonio, la bestia que sube del abismo hará guerra contra ellos, y los vencerá y los matará». 
                    
                    ¡Los siervos de Dios son inmortales hasta que hayan acabado su testimonio! La bestia no pudo tocarlos mientras su misión estaba vigente. Sus cuerpos yacen en la plaza del mundo tres días y medio mientras los impíos se regocijan; pero el aliento de vida de Dios entra en ellos, se levantan en pie ante el terror del mundo y son arrebatados al cielo en una nube ante los ojos de sus enemigos (vv. 11-12).
                    
                    Y en el versículo 15 suena la séptima trompeta y resuena la proclamación triunfal en los cielos: «Los reinos del mundo han venido a ser de nuestro Señor y de su Cristo; y él reinará por los siglos de los siglos». La victoria final pertenece a Dios.
                """,
                doctrinalThesis = "La Iglesia está comisionada como un testigo profético fiel que proclama la Palabra dulce y amarga, preservada soberanamente por Dios hasta consumar su testimonio para gloria del Reino eterno.",
                exegeticalAnalysis = "La frase «hotan telesōsin tēn martyrian autōn» (cuando hayan acabado su testimonio) en Apocalipsis 11:7 garantiza la custodia divina invencible sobre los siervos del Señor hasta cumplir su misión."
            ),

            // DÍA 23
            createStudyDay(
                dayNumber = 23,
                title = "Día 23: Apocalipsis 12: La Mujer, el Dragón y la Victoria de la Sangre",
                passagesSummary = "Apocalipsis 12:1-17; Génesis 3:15; Romanos 8:33-34",
                primaryBookId = 66,
                primaryChapter = 12,
                passages = listOf(seg(66, "Apocalipsis", 12), seg(1, "Génesis", 3), seg(45, "Romanos", 8)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En Apocalipsis 12 se abre el telón espiritual del conflicto cósmico que subyace a toda la historia humana desde Génesis 3:15. Juan contempla una gran señal en el cielo:
                    1. La Mujer vestida del sol (vv. 1-2): Con la luna debajo de sus pies y una corona de doce estrellas sobre su cabeza, estando encinta con dolores de parto. Representa a la comunidad del pacto de Dios (el Israel fiel que dio a luz al Mesías y se prolonga en la Iglesia de los doce apóstoles).
                    2. El Gran Dragón Escarlata (vv. 3-4): Con siete cabezas, diez cuernos y siete diademas, cuya cola arrastra la tercera parte de las estrellas (los ángeles caídos). Es identificado explícitamente en el versículo 9: «la serpiente antigua, que se llama diablo y Satanás, el cual engaña al mundo entero».
                    3. El Hijo Varón (v. 5): El Señor Jesucristo, «el cual regirá con vara de hierro a todas las naciones; y su hijo fue arrebatado para Dios y para su trono» (su encarnación, resurrección y ascensión gloriosa).
                    
                    El dragón intentó devorar al Niño en cuanto naciese (a través de la matanza de los inocentes por Herodes, la tentación en el desierto y la cruz); pero Cristo resucitó victorioso y ascendió al trono de la gloria.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    En los versículos 7 al 10 se describe la batalla celestial: Miguel y sus ángeles luchan contra el dragón, y Satanás es arrojado a la tierra desprovisto de todo poder de acusación: «ha sido lanzado fuera el acusador de nuestros hermanos, el que los acusaba delante de nuestro Dios día y noche» (en plena armonía con Romanos 8:33-34: «¿Quién acusará a los escogidos de Dios? Dios es el que justifica»).
                    
                    En el versículo 11 resuena la fórmula apostólica de la victoria cristiana sobre el enemigo espiritual:
                    «Y ellos le han vencido por medio de la sangre del Cordero y de la palabra del testimonio de ellos, y menospreciaron sus vidas hasta la muerte».
                    
                    Las tres armas invencibles del creyente son:
                    - La sangre del Cordero: El mérito infinito de la expiación que cancela toda acusación penal y culpabilidad de pecado.
                    - La palabra del testimonio: La proclamación valiente y confesión pública de la verdad del Evangelio.
                    - La consagración sacrificial: Un amor a Cristo tan supremo que supera incluso el temor a la muerte física. Satanás es un enemigo vencido y desarmado en la cruz (Colosenses 2:15).
                """,
                doctrinalThesis = "Satanás ha sido derrotado y despojado de su poder acusador por la victoria de Cristo en la cruz, siendo vencido por los santos mediante la sangre del Cordero y el testimonio fiel.",
                exegeticalAnalysis = "La triple victoria de Apocalipsis 12:11 («dia to haima tou arniou», por la sangre del Cordero) sitúa el fundamento exclusivo de la guerra espiritual en la obra legal consumada del Calvario."
            ),

            // DÍA 24
            createStudyDay(
                dayNumber = 24,
                title = "Día 24: Apocalipsis 13: Las Dos Bestias: La Tiranía Política y el Falso Profeta",
                passagesSummary = "Apocalipsis 13:1-18; 2 Tesalonicenses 2:8-12",
                primaryBookId = 66,
                primaryChapter = 13,
                passages = listOf(seg(66, "Apocalipsis", 13), seg(53, "2 Tesalonicenses", 2)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En Apocalipsis 13 se describe la manifestación terrenal de la trinidad satánica que usurpa y parodia la gloria del Dios Trino:
                    - El Dragón parodia al Padre celestial otorgando su trono y autoridad.
                    - La Bestia que sube del Mar (vv. 1-10) parodia al Hijo: un líder político y militar imperial soberbio, con una herida mortal que fue sanada (parodia engañosa de la resurrección), a quien se le da boca que habla grandes cosas y blasfemias contra Dios, su santuario y los que moran en el cielo. Se le permitió hacer guerra contra los santos y vencerlos en el plano físico terrenal, recibiendo la adoración de todos los moradores de la tierra cuyos nombres no están inscritos en el libro de la vida del Cordero inmolado (v. 8).
                    - La Bestia que sube de la Tierra (vv. 11-18, el Falso Profeta): Tenía dos cuernos semejantes a los de un cordero pero hablaba como un dragón. Parodia al Espíritu Santo: realiza señales prodigiosas mentirosas (como advierte 2 Tesalonicenses 2:8-12) para que la tierra adore a la primera bestia, levanta una imagen a la bestia y fuerza a todos los hombres a llevar una marca en la mano derecha o en la frente para poder comprar o vender («el número de su nombre: seiscientos sesenta y seis», 666, el número del ser humano caído imperfecto repetido tres veces en contraste con el siete de la perfección divina).
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    A lo largo de la historia de la Iglesia, los poderes totalitarios paganos han encarnado este espíritu de la bestia, exigiendo una lealtad absoluta y adoración idolátrica que solo pertenece a Dios, persiguiendo económicamente y martirizando a quienes rehúsan claudicar.
                    
                    En medio de este imperio de coacción satánica, el versículo 10 emite la exhortación que sostiene el alma del pueblo de Dios: «Aquí está la paciencia y la fe de los santos». Los creyentes no se dejan seducir por las promesas de prosperidad mundana ni tiemblan ante las amenazas de los tiranos temporales; saben que su fidelidad está sellada en el Cordero y que la bestia marcha inexorablemente hacia su destrucción eterna.
                """,
                doctrinalThesis = "Los sistemas totalitarios y las religiones apóstatas parodian la gloria divina persiguiendo a la Iglesia, pero los santos permanecen fieles sabiendo que sus nombres están en el libro de la vida.",
                exegeticalAnalysis = "El contraste entre la marca de la bestia (conformismo con el mundo) y el estar inscritos en «to biblion tēs zōēs tou arniou» (Ap 13:8) establece la división escatológica definitiva entre la condenación y la gracia."
            ),

            // DÍA 25
            createStudyDay(
                dayNumber = 25,
                title = "Día 25: Apocalipsis 14: Los Tres Ángeles y la Siega de la Tierra",
                passagesSummary = "Apocalipsis 14:1-20; Joel 3:12-14; Mateo 13:37-43",
                primaryBookId = 66,
                primaryChapter = 14,
                passages = listOf(seg(66, "Apocalipsis", 14), seg(29, "Joel", 3), seg(40, "Mateo", 13)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En Apocalipsis 14, la visión traslada de inmediato al lector desde la opresión de las bestias en la tierra hasta el monte de Sión celestial, donde el Cordero se halla en pie rodeado de los 144,000 que tienen el nombre del Padre escrito en sus frentes, redimidos de entre los hombres como primicias para Dios y para el Cordero, intachables y sin engaño en sus bocas (vv. 1-5).
                    
                    En los versículos 6 al 12 irrumpen tres ángeles que surcan en medio del cielo proclamando el mensaje final para la humanidad:
                    1. El Primer Ángel (vv. 6-7): Con «el evangelio eterno» para predicarlo a los moradores de la tierra: «Temed a Dios, y dadle gloria, porque la hora de su juicio ha llegado; y adorad a aquel que hizo el cielo y la tierra, el mar y las fuentes de las aguas».
                    2. El Segundo Ángel (v. 8): «Ha caído, ha caído Babilonia, la gran ciudad, porque ha hecho beber a todas las naciones del vino del furor de su fornicación».
                    3. El Tercer Ángel (vv. 9-11): Una solemne advertencia contra quien adore a la bestia o reciba su marca: beberá del vino de la ira pura de Dios y será atormentado con fuego y azufre delante de los santos ángeles y del Cordero, y el humo de su tormento sube por los siglos de los siglos.
                    
                    El versículo 13 registra una bendita voz del cielo: «Escribe: Bienaventurados de aquí en adelante los muertos que mueren en el Señor. Sí, dice el Espíritu, descansarán de sus trabajos, porque sus obras con ellos siguen».
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    En los versículos 14 al 20 se ejecuta la doble cosecha de la historia humana (en cumplimiento de Joel 3:12-14 y Mateo 13:37-43):
                    - La Siega del Trigo (vv. 14-16): Uno semejante al Hijo del Hombre sentado sobre una nube blanca con una hoz aguda en su mano siega la tierra: la recolección de los redimidos en los graneros del Reino celestial (Mateo 13:30).
                    - La Vendimia de la Ira (vv. 17-20): Los racimos de la vid de la tierra pecadora son vendimiados y arrojados al gran lagar del furor de Dios fuera de la ciudad. 
                    
                    La justicia y la misericordia de Dios convergen en el desenlace final. Esta visión infunde una profunda urgencia misionera en la Iglesia: anunciar el Evangelio eterno mientras hay tiempo y descansar en la certeza de que morir en el Señor es una bienaventuranza eterna.
                """,
                doctrinalThesis = "El Evangelio eterno llama a toda la creación a adorar al Creador soberano, mientras que la siega escatológica final separa definitivamente a los redimidos para gloria y a los rebeldes para retribución.",
                exegeticalAnalysis = "La proclamación del «euangelion aiōnion» en Apocalipsis 14:6 une la universalidad del llamado al arrepentimiento con la advertencia indeclinable de la inminencia del juicio divino."
            ),

            // DÍA 26
            createStudyDay(
                dayNumber = 26,
                title = "Día 26: Apocalipsis 15 y 16: Las Siete Copas de la Ira y el Armagedón",
                passagesSummary = "Apocalipsis 15:1-8; Apocalipsis 16:1-21; Éxodo 15:1-18",
                primaryBookId = 66,
                primaryChapter = 15,
                passages = listOf(seg(66, "Apocalipsis", 15), seg(66, "Apocalipsis", 16), seg(2, "Éxodo", 15)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En Apocalipsis 15 se revela la preparación de las últimas plagas: «siete ángeles que tenían las siete plagas postreras; porque en ellas se consumaba la ira de Dios» (v. 1). Antes de que los ángeles derramen sus copas, Juan contempla a los vencedores de la bestia sobre un mar de vidrio mezclado con fuego, teniendo las arpas de Dios y entonando una alabanza sublime:
                    «el cántico de Moisés siervo de Dios, y el cántico del Cordero, diciendo: Grandes y maravillosas son tus obras, Señor Dios Todopoderoso; justos y verdaderos son tus caminos, Rey de los santos» (v. 3).
                    
                    Así como el pueblo de Israel entonó el cántico de Moisés a orillas del Mar Rojo tras contemplar la destrucción del ejército de Faraón (Éxodo 15), la Iglesia de Cristo entona el cántico de la victoria definitiva sobre todos los opresores demoníacos.
                    
                    En Apocalipsis 16 se desatan los juicios vertiginosos de las Siete Copas de Oro de la ira divina («phialas tou thymou tou Theou»): úlceras malignas, el mar y los ríos convertidos en sangre, calor sofocante del sol quemando a los hombres, tinieblas sobre el trono de la bestia, y el secado del río Éufrates para preparar el camino de los reyes del oriente congregados en el lugar llamado en hebreo «Armagedón» («Har-Megiddo», la montaña de la decisión final).
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    En medio del derramamiento de las copas, el ángel de las aguas proclama con santa reverencia en los versículos 5 al 7: «Justo eres tú, oh Señor... porque has juzgado estas cosas. Por cuanto derramaron la sangre de los santos y de los profetas, también tú les has dado a beber sangre; pues lo merecen... Ciertamente, Señor Dios Todopoderoso, tus juicios son verdaderos y justos».
                    
                    Cuando el séptimo ángel derrama su copa en el aire, una gran voz sale del templo del trono diciendo: «¡HECHO ESTÁ!» («Gegonen», v. 17). 
                    
                    La ira de Dios no es injusticia; es la santa rectitud cósmica que pone fin al derramamiento de sangre inocente y a la tiranía del mal. En la cruz, Cristo bebió voluntariamente esa copa de juicio por los suyos; aquellos que rechazan al Salvador deberán beberla por sí mismos en el juicio final.
                """,
                doctrinalThesis = "Las siete copas consuman la ira de Dios sobre el imperio del mal con justicia perfecta y distributiva, respondiendo a la sangre de los mártires con el veredicto final: «¡Hecho está!».",
                exegeticalAnalysis = "El cántico conjunto de Moisés y del Cordero en Apocalipsis 15:3 integra orgánicamente la redención del éxodo con la victoria escatológica universal de Cristo en la cruz."
            ),

            // DÍA 27
            createStudyDay(
                dayNumber = 27,
                title = "Día 27: Apocalipsis 17 y 18: La Caída de Babilonia la Grande",
                passagesSummary = "Apocalipsis 17:1-14; Apocalipsis 18:1-24; Isaías 21:9",
                primaryBookId = 66,
                primaryChapter = 17,
                passages = listOf(seg(66, "Apocalipsis", 17), seg(66, "Apocalipsis", 18), seg(23, "Isaías", 21)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En Apocalipsis 17 y 18 se expone la naturaleza y el juicio definitivo de «Babilonia la Grande»: el sistema mundial apóstata, seductor, idólatra y mercantil que a lo largo de las edades ha intentado seducir al ser humano con el lujo, la fornicación espiritual y el desprecio a Dios.
                    
                    En el capítulo 17 se presenta como una ramera vestida de púrpura y escarlata, adornada de oro y piedras preciosas, sentada sobre una bestia escarlata y embriagada con la sangre de los santos y de los mártires de Jesús (vv. 4-6). En el versículo 14 se proclama el desenlace de la confrontación: «Pelearán contra el Cordero, y el Cordero los vencerá, porque él es Señor de señores y Rey de reyes; y los que están con él son llamados y elegidos y fieles».
                    
                    En Apocalipsis 18, un ángel con gran poder alumbra la tierra con su gloria clamando con voz potente: «Ha caído, ha caído la gran Babilonia» (v. 2; cumpliendo el clamor profético de Isaías 21:9). En el versículo 4 resuena el mandamiento urgente de santidad para el pueblo de Dios: «Salid de ella, pueblo mío, para que no seáis partícipes de sus pecados, ni recibáis parte de sus plagas».
                    
                    El juicio sobre el sistema babilónico es fulminante e instantáneo: «en un solo día vendrán sus plagas; muerte, llanto y hambre... en una sola hora ha venido tu juicio» (vv. 8, 10). Los reyes y los mercaderes de la tierra lloran al ver arder el humo de su incendio porque nadie compra más sus mercaderías; pero en el cielo se escucha la orden: «Alégrate sobre ella, cielo, y vosotros, santos, apóstoles y profetas; porque Dios os ha hecho justicia en ella» (v. 20).
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    Babilonia representa la soberbia de la civilización humana construida sin Dios, desde la torre de Babel en Génesis 11 hasta la metrópolis globalizada de los últimos tiempos. Nos deslumbra con su lujo terrenal y su tecnología hedonista, pero está vacía de vida eterna y cimentada sobre la opresión.
                    
                    Un ángel poderoso toma una piedra como una gran rueda de molino y la arroja con violencia al mar diciendo: «Con el mismo ímpetu será derribada Babilonia, la gran ciudad, y nunca más será hallada» (Ap 18:21). No pongamos nuestra esperanza en los imperios financieros y placeres efímeros de este mundo que se hunden; pongamos nuestro corazón en la Ciudad Eterna cuyo artífice y constructor es Dios (Hebreos 11:10).
                """,
                doctrinalThesis = "El sistema seductor del mundo caído (Babilonia la Grande) será destruido fulminantemente por el juicio de Dios, llamando a los santos a no contaminarse con sus placeres idólatras.",
                exegeticalAnalysis = "El imperativo de separación «Exelthate ex autēs, ho laos mou» (Salid de ella, pueblo mío) en Apocalipsis 18:4 demanda una fidelidad moral y espiritual intransigente frente a la apostasía secular."
            ),

            // DÍA 28
            createStudyDay(
                dayNumber = 28,
                title = "Día 28: Apocalipsis 19: Las Bodas del Cordero y el Jinete Fiel y Verdadero",
                passagesSummary = "Apocalipsis 19:1-21; Isaías 63:1-4; Mateo 22:1-14",
                primaryBookId = 66,
                primaryChapter = 19,
                passages = listOf(seg(66, "Apocalipsis", 19), seg(23, "Isaías", 63), seg(40, "Mateo", 22)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    Apocalipsis 19 se abre con la primera ocasión en todo el Nuevo Testamento donde se utiliza la palabra hebrea de alabanza universal: «¡Aleluya!» («Hallēlu-Yāh», ¡Alabad a Jehová!). Una inmensa voz de multitud resuena en los cielos cuatro veces consecutivas celebrando la caída de la opresión babilónica y el inicio del clímax de la historia:
                    «Gocémonos y alegrémonos y démosle gloria; porque han llegado las bodas del Cordero, y su esposa se ha preparado. Y a ella se le ha concedido que se vista de lino fino, limpio y resplandeciente; porque el lino fino es las acciones justas de los santos» (vv. 7-8; consumando la parábola de las bodas del hijo del rey en Mateo 22:1-14).
                    
                    El matrimonio celestial entre Cristo y su Iglesia redimida llega a su consumación eterna. El versículo 9 proclama: «Bienaventurados los que son llamados a la cena de las bodas del Cordero».
                    
                    Acto seguido, en el versículo 11, el cielo se abre de par en par y entra en escena la manifestación más imponente de toda la profecía (en cumplimiento de Isaías 63:1-4):
                    «Y vi el cielo abierto; y he aquí un caballo blanco, y el que lo montaba se llamaba Fiel y Verdadero, y con justicia juzga y pelea. Sus ojos eran como llama de fuego, y había en su cabeza muchas diademas... Estaba vestido de una ropa teñida en sangre; y su nombre es: EL VERBO DE DIOS. Y los ejércitos celestiales, vestidos de lino finísimo, blanco y limpio, le seguían en caballos blancos. De su boca sale una espada aguda, para herir con ella a las naciones... Y en su vestidura y en su muslo tiene escrito este nombre: REY DE REYES Y SEÑOR DE SEÑORES».
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    La bestia y los reyes de la tierra con sus ejércitos se congregan en rebelión abierta contra el Jinete del caballo blanco (v. 19). Pero la supuesta batalla culmina de forma fulminante: la bestia y el falso profeta son capturados vivos y arrojados directamente al lago de fuego que arde con azufre (v. 20), y sus ejércitos son destruidos por la espada de la boca del Verbo de Dios.
                    
                    El Jinete del caballo blanco no es otro que nuestro Salvador Jesucristo. En su primera venida cabalgó en humildad sobre un pollino de asna para ofrecer su vida por los pecados del mundo; en su segunda venida cabalga en majestad sobre el caballo blanco de la victoria universal para reclamar su señorío soberano e instaurar la justicia inmutable del Dios viviente.
                """,
                doctrinalThesis = "Cristo regresa glorioso como Rey de reyes y Señor de señores para celebrar las bodas eternas con su Esposa y derrotar definitivamente a todos los poderes anticristianos del mundo.",
                exegeticalAnalysis = "Los títulos mesiánicos de Apocalipsis 19:11-16 («Fiel y Verdadero», «El Verbo de Dios», «Rey de reyes y Señor de señores») coronan la cristología triunfal del Nuevo Testamento."
            ),

            // DÍA 29
            createStudyDay(
                dayNumber = 29,
                title = "Día 29: Apocalipsis 20: El Milenio, la Derrota Final y el Gran Trono Blanco",
                passagesSummary = "Apocalipsis 20:1-15; 1 Corintios 15:24-28",
                primaryBookId = 66,
                primaryChapter = 20,
                passages = listOf(seg(66, "Apocalipsis", 20), seg(46, "1 Corintios", 15)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En Apocalipsis 20 se relata el cierre judicial de la historia temporal humana:
                    1. El Encarcelamiento de Satanás y el Reinado de los Santos (vv. 1-6): Un ángel desciende con la llave del abismo, prende al dragón, la serpiente antigua, y lo ata por mil años en el abismo para que no engañe más a las naciones. Los santos y mártires que no adoraron a la bestia participan de la «primera resurrección» (la resurrección para vida) y reinan con Cristo.
                    2. La Rebelión Final y la Extinción Definitiva del Diablo (vv. 7-10): Al consumarse el período, Satanás es desatado por un breve tiempo y engaña a las naciones rebeldes (Gog y Magog) para rodear el campamento de los santos y la ciudad amada. Pero desciende fuego del cielo de Dios y los consume, y el diablo es lanzado al lago de fuego y azufre donde ya estaban la bestia y el falso profeta, para ser atormentados día y noche por los siglos de los siglos. El mal cósmico queda erradicado para siempre.
                    
                    3. El Juicio del Gran Trono Blanco (vv. 11-15):
                    «Y vi un gran trono blanco y al que estaba sentado en él, de delante del cual huyeron la tierra y el cielo, y ningún lugar se encontró para ellos. Y vi a los muertos, grandes y pequeños, de pie ante Dios; y los libros fueron abiertos, y otro libro fue abierto, el cual es el libro de la vida; y fueron juzgados los muertos por las cosas que estaban escritas en los libros, según sus obras».
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    La muerte y el Hades entregan a los muertos y son arrojados al lago de fuego: «Esta es la muerte segunda» (v. 14). El último enemigo vencido es la muerte física (1 Corintios 15:26).
                    
                    El versículo 15 sella el veredicto definitivo de la eternidad: «Y el que no se halló inscrito en el libro de la vida fue lanzado al lago de fuego». 
                    
                    La justicia de Dios no fallará. Ningún pecado quedará impune: o fue pagado por Cristo en la cruz para quien se arrepiente y cree, o será pagado eternamente por el pecador obstinado. El mayor motivo de gozo de nuestra vida es el que Jesús proclamó a sus discípulos: «regocijaos de que vuestros nombres están escritos en los cielos» (Lucas 10:20).
                """,
                doctrinalThesis = "El juicio final del Gran Trono Blanco pondrá fin definitivo a la muerte y al diablo, determinando el destino eterno según la inscripción soberana en el libro de la vida del Cordero.",
                exegeticalAnalysis = "La apertura de los libros de las obras frente al libro de la vida en Apocalipsis 20:12 demuestra que la justificación por gracia mediante la fe es el único fundamento para librarse de la muerte segunda."
            ),

            // DÍA 30
            createStudyDay(
                dayNumber = 30,
                title = "Día 30: Apocalipsis 21 y 22: La Nueva Jerusalén y la Oración Final: ¡Ven, Señor Jesús!",
                passagesSummary = "Apocalipsis 21:1-8, 22-27; Apocalipsis 22:1-21",
                primaryBookId = 66,
                primaryChapter = 21,
                passages = listOf(seg(66, "Apocalipsis", 21), seg(66, "Apocalipsis", 22)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    Los dos capítulos finales del Apocalipsis y de toda la Biblia nos introducen en la morada consumada de la gloria: la Nueva Jerusalén descendiendo del cielo de Dios, ataviada como una novia engalanada para su esposo (Ap 21:2).
                    
                    La comunión con Dios alcanza su perfección absoluta en los versículos 3 y 4:
                    «He aquí el tabernáculo de Dios con los hombres, y él morará con ellos; y ellos serán su pueblo, y Dios mismo estará con ellos como su Dios. Enjugará Dios toda lágrima de los ojos de ellos; y ya no habrá muerte, ni habrá más llanto, ni clamor, ni dolor; porque las primeras cosas pasaron».
                    
                    En la ciudad santa no hay templo físico material (v. 22), «porque el Señor Dios Todopoderoso es el templo de ella, y el Cordero». La ciudad no tiene necesidad de sol ni de luna que brillen en ella, «porque la gloria de Dios la ilumina, y el Cordero es su lumbrera» (v. 23).
                    
                    En Apocalipsis 22:1-2, Juan contempla el Río Limpio de Agua de Vida, resplandeciente como cristal, saliendo del trono de Dios y del Cordero, y a ambos lados del río el Árbol de la Vida que produce doce frutos. La maldición del Edén es totalmente revocada: «Y no habrá más maldición; y el trono de Dios y del Cordero estará en ella, y sus siervos le servirán, y verán su rostro, y su nombre estará en sus frentes» (vv. 3-4).
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    La conclusión de toda la revelación divina culmina con una triple promesa directa de los labios de nuestro Salvador:
                    - Versículo 7: «¡He aquí, vengo pronto! Bienaventurado el que guarda las palabras de la profecía de este libro».
                    - Versículo 12: «He aquí yo vengo pronto, y mi galardón conmigo, para recompensar a cada uno según sea su obra».
                    - Versículo 20: «El que da testimonio de estas cosas dice: Ciertamente vengo en breve».
                    
                    Y la respuesta del apóstol Juan, que resuena como la voz ardiente y anhelante de toda la Iglesia fiel a través de los siglos, sella el canon sagrado:
                    «¡Amén; sí, ven, Señor Jesús!».
                    
                    «La gracia de nuestro Señor Jesucristo sea con todos vosotros. Amén» (v. 21).
                    
                    La escatología bíblica no concluye con temor, parálisis ni incertidumbre; concluye con el triunfo absoluto de nuestro Señor Jesucristo y la gloria inmarcesible de su pueblo redimido en la eternidad. La esperanza es cierta, la victoria es segura, y el Rey soberano viene pronto.
                """,
                doctrinalThesis = "Toda la revelación bíblica culmina en la gloria de la Nueva Jerusalén, donde los redimidos vivirán en comunión eterna con Dios y el Cordero, exclamando con gozo: «¡Sí, ven, Señor Jesús!».",
                exegeticalAnalysis = "La proclamación final «Ciertamente vengo en breve» y la respuesta «Amēn, erchou Kyrie Iēsou» (Ap 22:20) sellan el canon bíblico en una expectativa viva y gozosa de la Parusía."
            )
        )
    }
}
