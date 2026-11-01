package com.example.data.bible.theology

import com.example.data.bible.ReadingPlanDay
import com.example.data.bible.theology.TheologyStudyHelper.createStudyDay
import com.example.data.bible.theology.TheologyStudyHelper.seg

/**
 * Estudio Bíblico Teológico y Exegético: La Teología de los Pactos (15 Días).
 * Análisis riguroso, accesible y sistemático de la trama pactual de la salvación
 * desde el Edén, pasando por Noé, Abraham, Moisés y David, hasta el Nuevo Pacto eterno en Cristo.
 */
object CovenantsStudyPlan {

    val days: List<ReadingPlanDay> by lazy {
        listOf(
            // DÍA 1
            createStudyDay(
                dayNumber = 1,
                title = "Día 1: La Estructura Pactual de la Revelación Bíblica",
                passagesSummary = "Salmo 25:10-14; Hebreos 6:13-20; Salmo 89:1-4",
                primaryBookId = 19,
                primaryChapter = 25,
                passages = listOf(seg(19, "Salmos", 25), seg(58, "Hebreos", 6), seg(19, "Salmos", 89)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    La Biblia no es una recopilación inconexa de proverbios morales ni una antología desordenada de relatos piadosos; es la revelación progresiva y orgánica del plan redentor de Dios estructurado de principio a fin a través de pactos divinos. El Salmo 25:14 declara con profunda reverencia: «La comunión íntima de Jehová es con los que le temen, y a ellos hará conocer su pacto», verdad exaltada también en el Salmo 89:1-4 («Hice pacto con mi escogido; juré a David mi siervo, diciendo: Para siempre confirmaré tu descendencia»).
                    
                    El vocablo hebreo utilizado para pacto es «Berit» (en el griego neotestamentario: «diathēkē»). En la cultura del antiguo Cercano Oriente, un pacto no era un contrato comercial horizontal entre partes iguales que negocian cláusulas en paridad. En la teología bíblica, un «Berit» es un vínculo soberano y solemne establecido por Dios por pura iniciativa de gracia, sellado con juramento y sangre, donde el Señor se compromete a ser el Dios de su pueblo y demanda una lealtad exclusiva de corazón.
                    
                    La frase hebrea que describe la inauguración de un pacto es «karat berit», que literalmente significa «cortar un pacto», aludiendo al rito sacrificial donde animales eran degollados y divididos por la mitad, advirtiendo que la vida misma estaba comprometida en la fidelidad pactual (Jeremías 34:18).
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    La teología pactual (teología federal) reconoce que Dios siempre se ha relacionado con la humanidad a través de representantes corporativos o «cabezas federales»: primero a través del primer hombre Adán en el huerto, y definitivamente a través del segundo Hombre y postrer Adán, nuestro Señor Jesucristo (Romanos 5:12-19; 1 Corintios 15:45-47).
                    
                    En Hebreos 6:17-18 leemos que Dios, «queriendo mostrar más abundantemente a los herederos de la promesa la inmutabilidad de su consejo, interpuso juramento; para que por dos cosas inmutables, en las cuales es imposible que Dios mienta, tengamos un fortísimo consuelo los que hemos acudido para asirnos de la esperanza puesta delante de nosotros». Toda la historia de la salvación avanza como un río caudaloso que desemboca en la cruz de Cristo, donde todos los pactos divinos hallan su cumplimiento, firmeza y gloria eterna.
                """,
                doctrinalThesis = "El pacto divino (Berit / Diathēkē) es el marco soberano y progresivo por el cual Dios se revela y redime a su pueblo a través de la representación federal de Cristo.",
                exegeticalAnalysis = "La expresión idiomática hebrea «karat berit» («cortar un pacto») establece el carácter sagrado, sacrificial e inviolable del juramento divino con sus escogidos (Hebr 6:17)."
            ),

            // DÍA 2
            createStudyDay(
                dayNumber = 2,
                title = "Día 2: El Pacto en el Edén: Creación, Mandato y la Caída",
                passagesSummary = "Génesis 1:26-31; Génesis 2:15-17; Oseas 6:7",
                primaryBookId = 1,
                primaryChapter = 1,
                passages = listOf(seg(1, "Génesis", 1), seg(1, "Génesis", 2), seg(28, "Oseas", 6)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En los primeros dos capítulos del Génesis (Génesis 1:26-31; 2:15-17), Dios coloca al ser humano creado a su imagen en el huerto de Edén en un estado de inocencia original, bendición y comunión íntima con su Creador. Aunque el término específico «pacto» no aparece explícitamente en Génesis 2, todos los elementos constitutivos de un pacto están plenamente presentes: las partes contrayentes (Dios el Creador soberano y Adán el hombre creado), el mandato pactual, la promesa implícita de vida eterna y la advertencia penal ante la desobediencia.
                    
                    En Génesis 2:16-17 leemos el mandamiento probatorio: «De todo árbol del huerto podrás comer; mas del árbol de la ciencia del bien y del mal no comerás; porque el día que de él comieres, ciertamente morirás» (en hebreo: mot tamut, «muriendo morirás»). El árbol del conocimiento no era venenoso intrínsecamente; representaba la línea de soberanía entre el Creador y la criatura. Adán debía someterse a la revelación moral de Dios en lugar de erigirse en legislador autónomo de lo bueno y lo malo.
                    
                    El profeta Oseas confirma retrospectivamente la naturaleza pactual de este acontecimiento en Oseas 6:7: «Mas ellos, cual Adán, traspasaron el pacto; allí prevaricaron contra mí» (en hebreo: «ke'Adam averu verit»). Adán actuó en el Edén no como un individuo privado aislado, sino como la cabeza federal y representante legal de toda su posteridad biológica.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    La teología sistemática histórica denomina a este orden original «el Pacto de Obras» o «Pacto de la Creación». La tragedia de la caída en Génesis 3 arrastró a toda la raza humana: al quebrantar Adán el pacto, el pecado original, la culpa imputada, la corrupción moral y la muerte espiritual penetraron en el mundo (Romanos 5:12).
                    
                    El ser humano quedó en bancarrota absoluta, incapacitado para justificarse a sí mismo mediante las obras de la ley. Esta ruina pone de relieve la gloria insondable de la gracia: lo que el primer Adán arruinó por su desobediencia y rebelión en el huerto, el postrer Adán —Cristo Jesús— lo reparó y venció mediante su obediencia intachable hasta la muerte de cruz (Romanos 5:18-19).
                """,
                doctrinalThesis = "En el Edén, Adán actuó como cabeza federal de la humanidad bajo el pacto de creación; su transgresión arrastró a la raza humana a la muerte espiritual y a la necesidad de un Redentor.",
                exegeticalAnalysis = "La declaración explícita de Oseas 6:7 («cual Adán traspasaron el pacto») confirma la estructura pactual de Génesis 2 y la representación federal que Pablo desarrolla en Romanos 5:12-19."
            ),

            // DÍA 3
            createStudyDay(
                dayNumber = 3,
                title = "Día 3: El Protoevangelio: El Amanecer del Pacto de Gracia",
                passagesSummary = "Génesis 3:1-15, 21; Romanos 16:20",
                primaryBookId = 1,
                primaryChapter = 3,
                passages = listOf(seg(1, "Génesis", 3), seg(45, "Romanos", 16)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    Inmediatamente después de la caída de nuestros primeros padres, cuando el hombre y la mujer yacían atemorizados, avergonzados y cubiertos con precarios delantales de hojas de higuera intentando ocultar su desnudez de los ojos de Dios, el Señor descendió al huerto en el aire del día. En medio de los justos juicios pronunciados sobre la creación y los transgresores, Dios emitió una sentencia directa contra la serpiente antigua en Génesis 3:15:
                    
                    «Y pondré enemistad entre ti y la mujer, y entre tu simiente y la simiente suya; ésta te herirá en la cabeza, y tú le herirás en el calcañar».
                    
                    Este versículo es conocido en la teología bíblica como el «Protoevangelio» (el primer Evangelio, la primera proclamación de la Buena Nueva en la historia humana). La palabra para simiente es «zera» (descendencia). Dios anuncia un conflicto milenario entre dos linajes espirituales: la simiente de la serpiente (aquellos que persisten en la rebelión satánica) y la simiente de la mujer (el linaje de la fe que culmina en un Descendiente supremo singular).
                    
                    La naturaleza de las heridas revela la victoria final: la serpiente herirá al Libertador en el «calcañar» (una herida dolorosa y sangrienta, el sufrimiento y muerte temporal de Cristo en la cruz); pero la Simiente de la mujer herirá a la serpiente en la «cabeza» (un golpe fatal, destructivo y definitivo contra el señorío de Satanás y el poder de la muerte).
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    Asimismo, en Génesis 3:21, Dios realizó el primer acto sacrificial de la historia redentora: «Y Jehová Dios hizo al hombre y a su mujer túnicas de pieles, y los vistió». Las hojas de higuera cosidas por Adán representaban el esfuerzo vano de la religión humana por cubrir la propia culpa con méritos terrenales. 
                    
                    Para que Adán y Eva pudieran ser vestidos con túnicas de pieles, un animal inocente tuvo que morir y derramar su sangre en el huerto. Dios mismo proveyó la cobertura sustitutiva para la vergüenza del pecador.
                    
                    Génesis 3:15 y 3:21 inauguran formalmente la dispensación del «Pacto de Gracia»: la promesa inquebrantable de que Dios salvaría a sus escogidos no por el esfuerzo humano, sino por la venida, muerte expiatoria y victoria triunfal de Jesucristo, la Simiente de la mujer que aplastó la cabeza del dragón en la cruz del Calvario (Colosenses 2:15; Romanos 16:20).
                """,
                doctrinalThesis = "El Protoevangelio (Génesis 3:15) inaugura el Pacto de Gracia, anunciando la victoria final de Jesucristo —la Simiente de la mujer— aplastando el poder del pecado y de Satanás.",
                exegeticalAnalysis = "El contraste entre la herida en el calcañar y el aplastamiento de la cabeza en Génesis 3:15 profetiza la victoria definitiva de la cruz sobre el imperio de las tinieblas."
            ),

            // DÍA 4
            createStudyDay(
                dayNumber = 4,
                title = "Día 4: El Pacto Noéico: La Preservación Común del Orden Creado",
                passagesSummary = "Génesis 8:20-22; Génesis 9:8-17; Isaías 54:8-10",
                primaryBookId = 1,
                primaryChapter = 8,
                passages = listOf(seg(1, "Génesis", 8), seg(1, "Génesis", 9), seg(23, "Isaías", 54)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    Tras el diluvio universal que arrasó con la violencia desbordada de la humanidad impía, el arca reposó sobre los montes de Ararat y Noé ofreció holocaustos de animales limpios sobre un altar ante Jehová. En Génesis 8:21, el Señor olió olor grato y proclamó en su corazón: «No volveré más a maldecir la tierra por causa del hombre; porque el intento del corazón del hombre es malo desde su juventud; ni volveré más a destruir todo ser viviente, como he hecho».
                    
                    En Génesis 9:8-11, Dios formalizó solemnemente el «Pacto Noéico» no solo con Noé y sus hijos, sino de forma universal con «todo ser viviente que está con vosotros» y con la tierra entera. Dios garantizó la estabilidad climática y temporal de la naturaleza: «Mientras la tierra permanezca, no cesarán la siembra y la siega, el frío y el calor, el verano y el invierno, y el día y la noche» (Gn 8:22).
                    
                    La señal visible e indeleble de este pacto fue el arco iris en las nubes: «Mi arco he puesto en las nubes, el cual será por señal del pacto entre mí y la tierra» (Gn 9:13). En la lengua hebrea, la palabra para arco es «qeshet», que es el vocablo exacto utilizado para el arco de guerra militar. Lo profundamente revelador es que el arco iris en el cielo está apuntado hacia arriba, hacia el trono celestial, y no hacia abajo apuntando a la tierra. Dios ha colgado su arco de guerra: el juicio cósmico por agua no se repetirá jamás.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    La teología bíblica reconoce en el Pacto Noéico la institución divina de la «Gracia Común». Este pacto no salva almas del pecado individualmente, sino que preserva el escenario físico del planeta y la sociedad humana de la autodestrucción prematura, garantizando un orden ecológico y civil estable para que la historia de la salvación pueda avanzar hasta la venida de Cristo.
                    
                    En Isaías 54:9-10, Dios compara la fidelidad inconmovible de su amor redentor con las aguas de Noé: «Porque esto para mí será como en los días de Noé, cuando juré que nunca más las aguas de Noé pasarían sobre la tierra; así he jurado que no me enojaré contra ti, ni te reñiré. Porque los montes se moverán, y los collados temblarán, pero no se apartará de ti mi misericordia, ni el pacto de mi paz se quebrantará, dijo Jehová, el que tiene misericordia de ti».
                """,
                doctrinalThesis = "El Pacto Noéico es un pacto de gracia común y preservación cósmica que garantiza la estabilidad del orden creado para permitir el despliegue del plan de salvación.",
                exegeticalAnalysis = "El término militar «qeshet» (arco de combate) en Génesis 9:13 apuntando hacia el cielo simboliza la pacificación divina y el compromiso soberano de sostener el cosmos por su gracia."
            ),

            // DÍA 5
            createStudyDay(
                dayNumber = 5,
                title = "Día 5: El Llamado de Abraham: La Promesa de Bendición Universal",
                passagesSummary = "Génesis 12:1-9; Gálatas 3:6-9, 14",
                primaryBookId = 1,
                primaryChapter = 12,
                passages = listOf(seg(1, "Génesis", 12), seg(48, "Gálatas", 3)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En Génesis 12:1-3, tras la dispersión de las naciones rebeldes en la torre de Babel (Génesis 11), Dios interviene de forma soberana y electiva llamando a un hombre pagano idólatra en Ur de los caldeos (Josué 24:2) llamado Abram: «Pero Jehová había dicho a Abram: Vete de tu tierra y de tu parentela, y de la casa de tu padre, a la tierra que te mostraré. Y haré de ti una nación grande, y te bendeciré, y engrandeceré tu nombre, y serás bendición... y serán benditas en ti todas las familias de la tierra».
                    
                    El Pacto Abrahámico descansa sobre tres promesas medulares entrelazadas:
                    1. La Promesa de la Tierra («Eretz»): La tierra prometida de Canaán como escenario terrenal del testimonio divino.
                    2. La Promesa de la Simiente («Zera»): Una descendencia innumerable como el polvo de la tierra y como las estrellas de los cielos.
                    3. La Promesa de la Bendición Universal («Berajá»): «En ti serán benditas todas las familias de la tierra» (Gn 12:3).
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    La trascendencia neotestamentaria de este llamado es revolucionaria. En Gálatas 3:8, el apóstol Pablo escribe bajo inspiración del Espíritu una de las frases más asombrosas de la teología apostólica: «Y la Escritura, previendo que Dios había de justificar por la fe a los gentiles, dio de antemano la buena nueva [proeuēngelisato, pre-evangelizó] a Abraham, diciendo: En ti serán benditas todas las naciones».
                    
                    ¡Dios le predicó el Evangelio de Cristo a Abraham en Génesis 12! La bendición universal prometida no consistía en prosperidad material terrenal ni en privilegios políticos temporales; consistía en la justificación por la fe en Jesucristo para personas de toda tribu, lengua, pueblo y nación. 
                    
                    Pablo añade en Gálatas 3:29: «Y si vosotros sois de Cristo, ciertamente linaje de Abraham sois, y herederos según la promesa». Al poner nuestra fe en el Salvador, entramos legalmente en el cauce de la bendición pactual de Abraham, convirtiéndonos en hijos de la promesa y conciudadanos de los santos.
                """,
                doctrinalThesis = "El Pacto Abrahámico es el corazón del Evangelio veterotestamentario, anticipando la bendición de la justificación por la fe para todas las naciones a través de Jesucristo.",
                exegeticalAnalysis = "El término «proeuēngelisato» en Gálatas 3:8 certifica que la promesa de Génesis 12:3 de bendecir a todas las familias contenía el mensaje sustancial de la salvación en Cristo para los gentiles."
            ),

            // DÍA 6
            createStudyDay(
                dayNumber = 6,
                title = "Día 6: Génesis 15: Dios Pasa Solo entre las Piezas del Pacto",
                passagesSummary = "Génesis 15:1-21; Jeremías 34:18-20",
                primaryBookId = 1,
                primaryChapter = 15,
                passages = listOf(seg(1, "Génesis", 15), seg(24, "Jeremías", 34)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En Génesis 15, cuando Abraham preguntó a Dios con corazón angustiado: «Señor Jehová, ¿en qué conoceré que la he de heredar?», el Señor ordenó una ceremonia solemne según los rigurosos tratados de pacto del antiguo Cercano Oriente (Éxodo 15:9-10). Abraham tomó una becerra, una cabra, un carnero, una tórtola y un palomino, los partió por la mitad y colocó cada mitad enfrente de la otra, dejando un pasillo ensangrentado en medio.
                    
                    En la costumbre de la época (Jeremías 34:18-19), los dos contrayentes de un pacto caminaban juntos de la mano entre los animales divididos, pronunciando un juramento de auto-maldición condicional: «Que los dioses me despedacen y me hagan como a estos animales muertos si alguna vez quebranto las cláusulas de este pacto».
                    
                    Sin embargo, al caer el sol en Génesis 15:12, un terror y una gran oscuridad cayeron sobre Abraham, y Dios hizo caer sobre él un «profundo sueño» («tardemah», el mismo sueño sobrenatural en el que Dios sumió a Adán para crear a Eva). Abraham quedó completamente dormido e inmóvil al margen del sendero.
                    
                    Y en el versículo 17 acontece el milagro: «Y sucedió que puesto el sol, y ya oscurecido, se veía un horno humeante, y una antorcha de fuego que pasaba por entre los animales divididos». El horno humeante y la antorcha de fuego eran teofanías visibles de la presencia misma de Dios (como la columna de nube y fuego en el Éxodo). ¡Dios caminó solo entre las piezas ensangrentadas!
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    La trascendencia teológica de este acto unilateral es infinita. Al pasar Dios solo entre los animales mientras Abraham dormía, el Creador convirtió el pacto en una promesa incondicional de pura gracia. Dios tomó sobre Sus propios hombros divinos la responsabilidad absoluta del pacto: «Si este pacto es quebrantado, que caiga sobre Mí la maldición de la muerte».
                    
                    Siglos más tarde, la descendencia de Abraham quebrantó el pacto una y otra vez mediante la idolatría y la desobediencia moral. ¿Sobre quién cayó la maldición del pacto quebrantado? En la colina del Calvario, en medio de una densa oscuridad sobrenatural como la de Génesis 15, el Hijo de Dios encarnado fue despedazado y desamparado en el madero: «Cristo nos redimió de la maldición de la ley, hecho por nosotros maldición» (Gálatas 3:13). Dios cumplió con su propia sangre el juramento que hizo a solas en Génesis 15.
                """,
                doctrinalThesis = "En Génesis 15, Dios asumió unilateral e incondicionalmente la garantía del pacto, comprometiéndose a soportar en su propio Hijo la maldición del juicio para asegurar nuestra redención.",
                exegeticalAnalysis = "El paso solitario de la antorcha de fuego entre las piezas («karat berit») en Génesis 15:17 establece la naturaleza monergística e inquebrantable del pacto de gracia sellado en el Calvario."
            ),

            // DÍA 7
            createStudyDay(
                dayNumber = 7,
                title = "Día 7: El Pacto de la Circuncisión: Señal y Sello de la Justicia",
                passagesSummary = "Génesis 17:1-14; Romanos 4:9-12; Colosenses 2:11-12",
                primaryBookId = 1,
                primaryChapter = 17,
                passages = listOf(seg(1, "Génesis", 17), seg(45, "Romanos", 4), seg(51, "Colosenses", 2)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    Veinticuatro años después del llamado inicial de Abraham, cuando el patriarca tenía noventa y nueve años de edad, Dios se le apareció como «El-Shaddai» (el Dios Todopoderoso y Autosuficiente) para confirmar su pacto y otorgarle la señal visible del mismo: la circuncisión («Berit Milá», Génesis 17:1-14).
                    
                    Todo varón de la casa de Abraham, al octavo día de nacido, debía ser circuncidado en la carne de su prepucio como «señal del pacto entre mí y vosotros» (Gn 17:11). El corte de la carne conllevaba un significado teológico punzante:
                    1. La Incapacidad de la Carne: Abraham había intentado cumplir la promesa de la descendencia mediante sus propias fuerzas carnales engendrando a Ismael con la esclava Agar (Génesis 16). La circuncisión recordaba que la simiente de la promesa no procedía del poder fecundo natural de la carne humana caída, sino del poder milagroso de Dios.
                    2. La Necesidad de Purificación Moral Radical: La carne debía ser cortada; el pecado original contaminaba al ser humano en su misma raíz generacional.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    En Romanos 4:11, el apóstol Pablo desmantela la falsa confianza que los fariseos depositaban en el rito físico: Abraham «recibió la circuncisión como señal, como sello de la justicia de la fe que tuvo estando aún incircunciso». La circuncisión no lo hizo justo; fue el sello externo que certificó la justicia interior que ya poseía por la fe desde Génesis 15.
                    
                    En el Nuevo Pacto, la sombra de la circuncisión física halla su cumplimiento perfecto en la circuncisión espiritual del corazón obrada por el Espíritu Santo. Como enseña Pablo en Colosenses 2:11-12: «En él también fuisteis circuncidados con circuncisión no hecha a mano, al echar de vosotros el cuerpo pecaminoso carnal, en la circuncisión de Cristo; sepultados con él en el bautismo, en el cual fuisteis también resucitados con él, mediante la fe en el poder de Dios que le levantó de los muertos».
                    
                    La verdadera circuncisión según Filipenses 3:3 es la de aquellos que adoramos en el Espíritu de Dios, nos gloriamos en Cristo Jesús y no ponemos ninguna confianza en la carne.
                """,
                doctrinalThesis = "La circuncisión fue instituida como señal y sello del pacto de fe con Abraham, prefigurando la circuncisión espiritual del corazón y la regeneración en el Nuevo Testamento.",
                exegeticalAnalysis = "Los vocablos «sēmeion» (señal visible) y «sphragis» (sello de garantía legal) en Romanos 4:11 subordinan el rito externo a la justificación interior previa obrada por la fe."
            ),

            // DÍA 8
            createStudyDay(
                dayNumber = 8,
                title = "Día 8: El Sacrificio en Moriah: Jehová Proveerá el Cordero",
                passagesSummary = "Génesis 22:1-19; Juan 8:56; Hebreos 11:17-19",
                primaryBookId = 1,
                primaryChapter = 22,
                passages = listOf(seg(1, "Génesis", 22), seg(43, "Juan", 8), seg(58, "Hebreos", 11)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En Génesis 22 se relata el clímax dramático de la vida patriarcal de Abraham: Dios le pide la ofrenda de su hijo más amado: «Toma ahora tu hijo, tu único [yajid], Isaac, a quien amas, y vete a tierra de Moriah, y ofrécelo allí en holocausto» (Gn 22:2). Durante tres días de caminata agonizante hacia el monte, Abraham calló y confió con fe inconmovible en la fidelidad pactual de Dios.
                    
                    Cuando Isaac cargó sobre sus propios hombros la pesada leña para el sacrificio y preguntó con inocencia infantil: «Padre mío... he aquí el fuego y la leña; mas ¿dónde está el cordero para el holocausto?», Abraham pronunció una de las mayores profecías cristológicas de la historia: «Dios se proveerá de cordero para el holocausto, hijo mío» (Génesis 22:7-8).
                    
                    Al llegar a la cumbre del monte, Abraham edificó el altar, ató a su hijo Isaac y levantó el cuchillo en obediencia sacrificial. En ese instante, el Ángel de Jehová clamó desde los cielos deteniendo su mano: «No extiendas tu mano sobre el muchacho... porque ya conozco que temes a Dios, por cuanto no me rehusaste tu hijo, tu único». Y al alzar sus ojos, Abraham vio un carnero trabado en un zarzal por sus cuernos; tomó el carnero y lo sacrificó en holocausto en lugar de su hijo (Gn 22:13). Abraham llamó aquel lugar «YHWH-Jireh» (Jehová proveerá).
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    El monte Moriah es la colina exacta donde mil años después Salomón edificaría el templo en Jerusalén (2 Crónicas 3:1), y donde dos mil años después se levantó el madero del Calvario. Toda la escena de Moriah es una prefiguración asombrosa del sacrificio de Cristo:
                    - Dios el Padre no escatimó a su propio Hijo unigénito, sino que lo entregó por todos nosotros (Romanos 8:32).
                    - Jesucristo, el Hijo obediente, cargó con el madero de su propia cruz subiendo a la colina del sacrificio (Juan 19:17).
                    - Pero a diferencia de Isaac, en la cruz nadie gritó «¡detén tu mano!»; la espada de la santa justicia de Dios cayó con toda su fuerza sobre el Sustituto inocente para que nosotros pudiéramos ser perdonados.
                    
                    Jesús mismo confirmó esta revelación en Juan 8:56: «Abraham vuestro padre se gozó de que había de ver mi día; y lo vio, y se gozó». Asimismo, Hebreos 11:17-19 destaca que Abraham ofreció a Isaac «pensando que Dios es poderoso para levantar aun de entre los muertos, de donde, en sentido figurado, también le volvió a recibir». En el carnero trabado en el zarzal en Moriah, Abraham vio el Evangelio de la cruz y de la resurrección, y adoró al Dios que provee la redención.
                """,
                doctrinalThesis = "El sacrificio en el monte Moriah es la prefiguración tipológica cumbre de la expiación sustitutiva, donde Dios proveyó a su propio Hijo en la cruz en nuestro lugar.",
                exegeticalAnalysis = "El nombre divino «YHWH-Jireh» (Gn 22:14) y la frase profética «en el monte de Jehová será provisto» fijan geográfica y teológicamente el sacrificio redentor de Cristo en Jerusalén."
            ),

            // DÍA 9
            createStudyDay(
                dayNumber = 9,
                title = "Día 9: El Pacto Mosaico en el Sinaí: La Ley como Tutor Sagrado",
                passagesSummary = "Éxodo 19:1-8; Éxodo 24:1-11; Gálatas 3:19-25",
                primaryBookId = 2,
                primaryChapter = 19,
                passages = listOf(seg(2, "Éxodo", 19), seg(2, "Éxodo", 24), seg(48, "Gálatas", 3)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    Tres meses después de haber sido liberados con brazo extendido de la esclavitud de Egipto mediante la Pascua del cordero, Israel llegó al pie del monte Sinaí. En Éxodo 19:5-6, Dios propone los términos del pacto teocrático nacional: «Ahora, pues, si diereis oído a mi voz, y guardareis mi pacto, vosotros seréis mi especial tesoro sobre todos los pueblos... y vosotros me seréis un reino de sacerdotes, y gente santa».
                    
                    En Éxodo 24:3-8 se celebra la ratificación solemne del Pacto Sinaítico: Moisés leyó el Libro del Pacto a oídos de todo el pueblo, y ellos respondieron al unísono: «Haremos todas las cosas que Jehová ha dicho, y obedeceremos». Acto seguido, Moisés tomó la sangre de los novillos sacrificados y roció la mitad sobre el altar y la otra mitad sobre el pueblo, proclamando: «He aquí la sangre del pacto que Jehová ha hecho con vosotros sobre todas estas cosas» (Éx 24:8).
                    
                    A diferencia del Pacto Abrahámico (el cual fue incondicional y de pura promesa de gracia), el Pacto Mosaico contenía un marcado carácter condicional de bendición por obediencia y maldición por desobediencia en el ámbito del pacto nacional de la tierra (Deuteronomio 28).
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    ¿Cuál fue el propósito teológico de dar la Ley en el Sinaí cuatrocientos treinta años después de la promesa a Abraham? En Gálatas 3:19-24, el apóstol Pablo responde con brillantez: «¿Luego, para qué sirve la ley? Fue añadida a causa de las transgresiones, hasta que viniese la simiente a quien fue hecha la promesa... De manera que la ley ha sido nuestro ayo [paidagōgos], para llevarnos a Cristo, a fin de que fuésemos justificados por la fe».
                    
                    En la antigüedad greco-romana, el «paidagōgos» era el tutor severo y disciplinado que conducía al niño de la mano protegiéndolo y disciplinándolo hasta que alcanzara la madurez. La Ley no fue dada para que el hombre se salvara a sí mismo por sus obras (nadie puede cumplir la ley perfectamente, Hechos 15:10); fue dada como un espejo de santidad para exponer la gravedad del pecado humano, cerrar toda boca ante el juicio divino y conducirnos con urgencia a los pies de Cristo, el único Salvador.
                """,
                doctrinalThesis = "El Pacto Mosaico reveló la santidad de Dios y expuso la pecaminosidad del ser humano, actuando como un tutor pedagógico (paidagōgos) que conduce al pecador a Cristo.",
                exegeticalAnalysis = "La ratificación con sangre en Éxodo 24:8 prefigura la inauguración del Nuevo Pacto, mientras que Gálatas 3:24 aclara que la función de la Ley era diagnóstica y preparatoria, no justificadora."
            ),

            // DÍA 10
            createStudyDay(
                dayNumber = 10,
                title = "Día 10: La Ley de Dios: Las Tres Divisiones y su Cumplimiento en Cristo",
                passagesSummary = "Éxodo 20:1-17; Mateo 5:17-20; Romanos 13:8-10",
                primaryBookId = 2,
                primaryChapter = 20,
                passages = listOf(seg(2, "Éxodo", 20), seg(40, "Mateo", 5), seg(45, "Romanos", 13)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    La revelación de la Ley en el monte Sinaí contenía el Decálogo («las diez palabras», Éxodo 20:1-17), pronunciado audiblemente por la voz de Dios y grabado directamente por el dedo divino en dos tablas de piedra. La teología bíblica e histórica ha clasificado con gran utilidad didáctica los preceptos mosaicos en tres esferas:
                    
                    1. La Ley Ceremonial: Todos los mandamientos relativos al tabernáculo, sacrificios de sangre, dietas kosher, fiestas anuales y vestiduras levíticas. Estas ordenanzas eran sombras tipológicas que fueron cumplidas y abolidas definitivamente en la cruz de Cristo (Efesios 2:15; Hebreos 10:1).
                    2. La Ley Civil o Judicial: Las leyes específicas que gobernaban la teocracia nacional política de Israel en la tierra de Canaán (penas jurídicas, repartición de tierras, leyes agrícolas). Su vigencia como código estatal expiró con la disolución de la teocracia judía en el año 70 d.C., aunque su equidad moral general sigue siendo un principio de justicia.
                    3. La Ley Moral (El Decálogo): Los Diez Mandamientos reflejan el carácter santo, eterno e inmutable de Dios mismo (prohibición de la idolatría, el homicidio, el adulterio, el hurto, la mentira y la codicia). Esta ley jamás pierde vigencia, pues Dios no puede dejar de ser santo ni el amor a Dios y al prójimo puede ser derogado.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    En el Sermón del Monte, el Señor Jesús proclamó con autoridad suprema: «No penséis que he venido para abrogar la ley o los profetas; no he venido para abrogar, sino para cumplir» (Mateo 5:17).
                    
                    Cristo cumplió la ley de Dios de forma doble y perfecta:
                    - Obediencia Activa: Vivió una vida terrenal en obediencia intachable a cada precepto moral de la ley, sin cometer jamás un solo pecado de pensamiento, palabra u obra (1 Pedro 2:22). Esa justicia perfecta de Cristo es la que se nos acredita gratuitamente en la justificación.
                    - Obediencia Pasiva: Pagó voluntariamente en la cruz la sentencia penal de muerte que la ley exigía contra nosotros por nuestras transgresiones.
                    
                    Por tanto, el creyente ya no está bajo la ley como un sistema de condenación o justificación (Romanos 6:14); pero ahora, por la obra del Espíritu Santo en el corazón, la ley moral se convierte en la guía gozosa de amor filial: «el cumplimiento de la ley es el amor» (Romanos 13:10).
                """,
                doctrinalThesis = "Cristo cumplió perfectamente las demandas de la Ley moral, ceremonial y civil mediante su obediencia activa y pasiva, libertando al creyente de la condenación legal para vivir en obediencia por amor.",
                exegeticalAnalysis = "El término griego «plērosai» (cumplir hasta la plenitud) en Mateo 5:17 descarta el antinomianismo y confirma que la Ley halla su máxima expresión y satisfacción en la persona y obra de Cristo."
            ),

            // DÍA 11
            createStudyDay(
                dayNumber = 11,
                title = "Día 11: El Pacto Davídico: La Promesa de un Trono y Reino Eterno",
                passagesSummary = "2 Samuel 7:1-17; Salmo 89:19-37; Lucas 1:30-33",
                primaryBookId = 10,
                primaryChapter = 7,
                passages = listOf(seg(10, "2 Samuel", 7), seg(19, "Salmos", 89), seg(42, "Lucas", 1)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En 2 Samuel 7, el rey David habitaba en su palacio de cedro en Jerusalén y sintió en su corazón el noble anhelo de edificar un templo de piedra para el Arca de Dios que moraba entre cortinas. Sin embargo, a través del profeta Natán, Dios le envió una respuesta extraordinaria que dio un giro sublime a la historia de la redención: Dios le dijo a David que no sería David quien le edificaría una casa material a Jehová, sino que Jehová le edificaría a David una «casa» espiritual y una dinastía eterna.
                    
                    En 2 Samuel 7:12-16 resuena la promesa del Pacto Davídico: «Y cuando tus días sean cumplidos, y duermas con tus padres, yo levantaré después de ti a uno de tu linaje, el cual procederá de tus entrañas, y afirmaré su reino. Él edificará casa a mi nombre, y yo afirmaré para siempre el trono de su reino... Y será afirmada tu casa y tu reino para siempre delante de tu rostro, y tu trono será estable eternamente».
                    
                    Aunque esta profecía poseía una aplicación histórica inmediata en su hijo Salomón (quien construyó el templo de Jerusalén y cometió iniquidades que trajeron disciplina paternal, v. 14), los términos de la promesa —exaltados poéticamente en el Salmo 89:19-37 («No olvidaré mi pacto, ni mudaré lo que ha salido de mis labios... Su descendencia será para siempre, y su trono como el sol delante de mí»)— trascienden por completo la mortalidad humana: ningún monarca terrenal vive «para siempre», ni el reino terrenal de Judá permaneció inmutable (fue arrasado por Babilonia en el 586 a.C.).
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    La promesa de un trono eterno solo podía cumplirse en un Rey eterno que venciera a la muerte. Mil años más tarde, el ángel Gabriel se presentó ante la virgen María en Nazaret y proclamó la inauguración definitiva del Pacto Davídico en Lucas 1:31-33:
                    
                    «Y ahora, concebirás en tu vientre, y darás a luz un hijo, y llamarás su nombre JESÚS. Este será grande, y será llamado Hijo del Altísimo; y el Señor Dios le dará el trono de David su padre; y reinará sobre la casa de Jacob para siempre, y su reino no tendrá fin».
                    
                    Jesucristo es el legítimo Hijo de David que resucitó de entre los muertos para nunca más morir (Hechos 13:34). Él está sentado hoy en el trono celestial a la diestra del Padre reinando sobre su Iglesia y sobre el cosmos entero, y su Reino inamovible consumará la justicia y la paz perfecta en toda la tierra por los siglos de los siglos.
                """,
                doctrinalThesis = "El Pacto Davídico garantiza un linaje, un trono y un reino eterno que se cumplen consumadamente en Jesucristo, el Rey de reyes que reina para siempre.",
                exegeticalAnalysis = "La repetición enfática del adverbio «ad-olam» («para siempre») en 2 Samuel 7:16 eleva la promesa davídica más allá de la monarquía terrenal de Salomón hacia el señorío escatológico del Mesías."
            ),

            // DÍA 12
            createStudyDay(
                dayNumber = 12,
                title = "Día 12: La Profecía del Nuevo Pacto: Una Transformación Radical",
                passagesSummary = "Jeremías 31:31-37; Ezequiel 37:24-28",
                primaryBookId = 24,
                primaryChapter = 31,
                passages = listOf(seg(24, "Jeremías", 31), seg(26, "Ezequiel", 37)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En medio de las ruinas humeantes de Judá y la tragedia del exilio que evidenciaban el quebrantamiento total del pacto del Sinaí por la apostasía de la nación, el profeta Jeremías recibió la revelación más luminosa del Antiguo Testamento en Jeremías 31:31-34:
                    
                    «He aquí que vienen días, dice Jehová, en los cuales haré nuevo pacto con la casa de Israel y con la casa de Judá. No como el pacto que hice con sus padres el día que tomé su mano para sacarlos de la tierra de Egipto; porque ellos invalidaron mi pacto... Pero este es el pacto que haré con la casa de Israel después de aquellos días, dice Jehová: Daré mi ley en su mente, y la escribiré en su corazón; y yo seré a ellos por Dios, y ellos me serán por pueblo... Porque perdonaré la maldad de ellos, y no me acordaré más de su pecado».
                    
                    Este texto es la única ocasión en todo el Antiguo Testamento donde se utiliza la frase explícita «Nuevo Pacto» («Berit Jadashá»). Jeremías señala con precisión por qué el antiguo pacto fue insuficiente: no por defecto en la Ley divina, sino porque el corazón rebelde del pueblo lo invalidó. 
                    
                    El Nuevo Pacto introduce cuatro bendiciones incomparables:
                    1. Regeneración Interior: La ley no se impone desde tablas exteriores de piedra, sino que se graba en los afectos y en la mente del corazón regenerado.
                    2. Comunión Pactual Perfecta: «Yo seré a ellos por Dios, y ellos me serán por pueblo» (promesa que Ezequiel 37:24-28 amplía anunciando un «pacto de paz» y el santuario de Dios entre ellos para siempre).
                    3. Conocimiento Íntimo Universal de Dios: «Todos me conocerán, desde el más pequeño hasta el más grande».
                    4. Perdón Total y Olvido Soberano de los Pecados: Dios cancela la deuda y borra el expediente acusatorio para siempre.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    El autor de la epístola a los Hebreos cita este pasaje íntegramente dos veces (Hebreos 8:8-12 y 10:16-17) para demostrar a los creyentes que el régimen antiguo de sacrificios animales caducó para siempre.
                    
                    El Nuevo Pacto es el pacto supremo y definitivo. Todo lo que los pactos anteriores anticiparon en figuras y sombras —la victoria de la simiente en el Edén, la preservación cósmica con Noé, la bendición de las naciones con Abraham, la santidad de la ley con Moisés y el reino eterno con David— se funde y perfecciona en la gloria del Nuevo Pacto obrado por Jesucristo.
                """,
                doctrinalThesis = "La profecía del Nuevo Pacto (Berit Jadashá) anunció la superación definitiva de la economía mosaica mediante la regeneración interior y el perdón eterno de los pecados.",
                exegeticalAnalysis = "La designación «Berit Jadashá» en Jeremías 31:31 establece una ruptura cualitativa con el régimen externo sinaítico, instaurando un orden donde la obediencia brota de un corazón nuevo habitado por Dios."
            ),

            // DÍA 13
            createStudyDay(
                dayNumber = 13,
                title = "Día 13: El Nuevo Pacto Sellado en la Sangre de Cristo",
                passagesSummary = "Lucas 22:14-20; 1 Corintios 11:23-26; Mateo 26:26-29",
                primaryBookId = 42,
                primaryChapter = 22,
                passages = listOf(seg(42, "Lucas", 22), seg(46, "1 Corintios", 11), seg(40, "Mateo", 26)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En la noche previa a su crucifixión, en un aposento alto en Jerusalén, el Señor Jesucristo se sentó a la mesa con sus doce apóstoles para celebrar la cena de la Pascua judía. Durante mil cuatrocientos años, Israel había repetido anualmente esa liturgia comiendo el cordero asado, las hierbas amargas y el pan sin levadura en memoria de la liberación de Egipto.
                    
                    Pero en esa noche solemne, Jesús tomó los elementos tradicionales y los revistió de un significado infinitamente superior:
                    1. El Pan: «Y tomó el pan y dio gracias, y lo partió y les dio, diciendo: Esto es mi cuerpo, que por vosotros es dado; haced esto en memoria de mí» (Lucas 22:19). El pan ya no apuntaba al éxodo de Faraón; apuntaba al cuerpo santo del Cordero de Dios quebrantado por nosotros.
                    2. La Copa: «De igual manera, después que hubo cenado, tomó la copa, diciendo: Esta copa es el nuevo pacto en mi sangre, que por vosotros se derrama» (Lucas 22:20; en griego: «hē kainē diathēkē en tōi haimati mou»; instituida de igual modo en 1 Corintios 11:23-26 y Mateo 26:26-29, donde añade: «para remisión de los pecados»).
                    
                    El eco de Éxodo 24:8 resuena con fuerza: Moisés había dicho «He aquí la sangre del pacto» mientras rociaba sangre animal sobre el pueblo; pero Jesús declara: «Esta copa es el NUEVO PACTO en MI sangre». Ya no sangre de carneros ni de machos cabríos, sino la sangre inocente, pura e infinita del Hijo unigénito de Dios.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    Con la muerte de Cristo en el Calvario, el Nuevo Pacto profetizado por Jeremías quedó legal y espiritualmente ratificado en el cielo y en la tierra. Cada vez que la Iglesia participa de la Santa Cena del Señor, no asiste a un sacrificio repetido (pues Cristo murió una sola vez para siempre, Hebreos 9:28), sino a un memorial pactual sagrado de comunión y alabanza.
                    
                    El cáliz nos recuerda que nuestra salvación costó la vida misma de Dios encarnado. Bebemos de la copa del Nuevo Pacto sabiendo que Jesús bebió por nosotros en Getsemaní y en el Gólgota la amarga copa de la ira santa de Dios contra nuestro pecado, para que nosotros pudiéramos beber eternamente la copa de su bendición y gracia soberana.
                """,
                doctrinalThesis = "El Nuevo Pacto fue ratificado y sellado irrevocablemente por la sangre de Jesucristo derramada en la cruz, sustituyendo la Pascua antigua por la Santa Cena del Evangelio.",
                exegeticalAnalysis = "La proclamación «hē kainē diathēkē en tōi haimati mou» en Lucas 22:20 vincula el sacrificio expiatorio de Jesús con la consumación histórica de la promesa de Jeremías 31."
            ),

            // DÍA 14
            createStudyDay(
                dayNumber = 14,
                title = "Día 14: Cristo: La Simiente Prometida y el Sí de Todas las Promesas",
                passagesSummary = "Gálatas 3:15-18; 2 Corintios 1:18-22; Hebreos 9:15-17",
                primaryBookId = 48,
                primaryChapter = 3,
                passages = listOf(seg(48, "Gálatas", 3), seg(47, "2 Corintios", 1), seg(58, "Hebreos", 9)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En Gálatas 3:16, el apóstol Pablo realiza una de las observaciones exegéticas más profundas y detalladas de toda la literatura bíblica al analizar la promesa dada a Abraham: «Ahora bien, a Abraham fueron hechas las promesas, y a su simiente. No dice: Y a las simientes, como si hablase de muchos, sino como de uno: Y a tu simiente, la cual es Cristo».
                    
                    Pablo repara en el número gramatical de la palabra simiente («zera» en hebreo, «sperma» en griego). Aunque el término puede tener un sentido colectivo, la promesa pactual divina tenía en mente a una Persona específica singular: el Mesías. Todas las líneas genealógicas, los pactos preparatorios, las genealogías del Génesis y los reyes davídicos se estrechaban como un embudo hasta llegar a un solo Hombre: Jesucristo.
                    
                    Cristo es el verdadero Israel, el Hijo obediente que cumplió la ley a cabalidad donde la nación terrenal fracasó. Por esta razón, el apóstol Pablo proclama en 2 Corintios 1:20: «porque todas las promesas de Dios son en él Sí, y en él Amén, por medio de nosotros, para la gloria de Dios». No hay una sola promesa de redención, perdón, herencia, vida eterna, consuelo o santidad en todo el Antiguo Testamento que no encuentre su confirmación («Sí») y su cumplimiento irrevocable («Amén») en la persona de Cristo.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    En Hebreos 9:15 se corona esta doctrina explicando el alcance cósmico de la cruz: «Así que, por eso es mediador de un nuevo pacto, para que interviniendo muerte para la remisión de las transgresiones que había bajo el primer pacto, los llamados reciban la promesa de la herencia eterna».
                    
                    La sangre de Cristo posee una eficacia retroactiva y prospectiva: los santos del Antiguo Testamento (como Abraham, Moisés y David) no fueron salvados por guardar la ley o degollar corderos, sino por la sangre de Cristo que Dios acreditó de antemano a su cuenta en virtud del pacto prometido. Y nosotros hoy somos salvados por esa misma e idéntica sangre. Hay un solo Salvador, un solo mediador, un solo pueblo de Dios y un solo pacto eterno de gracia consumado en el Redentor.
                """,
                doctrinalThesis = "Jesucristo es la Simiente prometida a Abraham en quien convergen, se cumplen y se garantizan todas las promesas de los pactos divinos para salvación de su pueblo.",
                exegeticalAnalysis = "El análisis gramatical de «sperma» en singular en Gálatas 3:16 y el principio de 2 Corintios 1:20 establecen el principio cristocéntrico de que Cristo es el contenido y fin de toda la revelación pactual."
            ),

            // DÍA 15
            createStudyDay(
                dayNumber = 15,
                title = "Día 15: La Consumación del Pacto Eterno en la Gloria Venidera",
                passagesSummary = "Hebreos 13:20-21; Apocalipsis 21:1-7; Apocalipsis 22:1-5",
                primaryBookId = 58,
                primaryChapter = 13,
                passages = listOf(seg(58, "Hebreos", 13), seg(66, "Apocalipsis", 21), seg(66, "Apocalipsis", 22)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En la hermosa bendición pastoral que concluye la epístola a los Hebreos (capítulo 13, versículos 20 al 21), el autor sagrado eleva una doxología sublime que resume la totalidad de la teología de los pactos:
                    
                    «Y el Dios de paz que resucitó de los muertos a nuestro Señor Jesucristo, el gran pastor de las ovejas, por la sangre del pacto eterno, os haga aptos en toda obra buena para que hagáis su voluntad, haciendo él en vosotros lo que es agradable delante de él por Jesucristo; al cual sea la gloria por los siglos de los siglos. Amén».
                    
                    Nótese el título glorioso que recibe el pacto: «la sangre del pacto eterno» («en haimati diathēkēs aiōniou»). No es un pacto transitorio que caduca ni un arreglo temporal; es un decreto eterno concebido en el consejo trinitario antes de la fundación del mundo y cuya bienaventuranza durará por las edades sin fin.
                    
                    La meta última de todos los pactos bíblicos se sintetiza en la célebre fórmula pactual que resuena a lo largo de toda la Escritura: «Yo seré vuestro Dios, y vosotros seréis mi pueblo». Esta promesa se susurró a Abraham (Gn 17:7), se repitió a Israel en el Sinaí (Éx 6:7), se ratificó en los profetas (Jer 31:33; Ez 37:27) y alcanza su culminación monumental en la Nueva Jerusalén en Apocalipsis 21:3 y Apocalipsis 22:1-5:
                    
                    «Y oí una gran voz del cielo que decía: He aquí el tabernáculo de Dios con los hombres, y él morará con ellos; y ellos serán su pueblo, y Dios mismo estará con ellos como su Dios... y sus siervos le servirán, y verán su rostro, y su nombre estará en sus frentes» (Ap 21:3; 22:3-4).
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    La historia humana no es un ciclo ciego ni una tragedia sin sentido; es la gran marcha triunfal del Dios de los pactos redimiendo a un pueblo innumerable para su gloria. En la eternidad venidera, en los cielos nuevos y tierra nueva, contemplaremos al Cordero de Dios en el trono y comprenderemos con perfecta claridad cómo cada prueba, cada promesa y cada pacto bíblico nos condujo a la bienaventuranza eterna.
                    
                    El Dios fiel que guardó el pacto con Abraham y resucitó a Jesús de entre los muertos es el mismo Dios que custodia nuestra vida hoy. Sus promesas son rocas inamovibles. Descansamos con gozo invencible sabiendo que Aquel que comenzó en nosotros la buena obra, la perfeccionará hasta el día de Jesucristo.
                """,
                doctrinalThesis = "El pacto eterno sellado por la sangre de Cristo culminará en la eternidad gloriosa, donde la fórmula pactual «Yo seré su Dios y ellos serán mi pueblo» se vivirá en plenitud perfecta cara a cara.",
                exegeticalAnalysis = "La expresión «diathēkē aiōnios» en Hebreos 13:20 y el cumplimiento final de Apocalipsis 21:3 demuestran la fidelidad inmutable de Dios conduciendo a su pueblo redimido a la gloria consumada."
            )
        )
    }
}
