package com.example.data.bible.theology

import com.example.data.bible.ReadingPlanDay
import com.example.data.bible.theology.TheologyStudyHelper.createStudyDay
import com.example.data.bible.theology.TheologyStudyHelper.seg

/**
 * Estudio Bíblico Teológico y Exegético: Romanos: Fundamentos de Gracia (30 Días).
 * Análisis verso a verso y sistemático de la epístola a los Romanos:
 * la condenación universal, la justificación por la sola fe, la santificación en el Espíritu,
 * la soberanía pactual de Dios y la ética cristiana práctica.
 */
object RomansStudyPlan {

    val days: List<ReadingPlanDay> by lazy {
        listOf(
            // DÍA 1
            createStudyDay(
                dayNumber = 1,
                title = "Día 1: El Siervo de Cristo y el Evangelio Prometido",
                passagesSummary = "Romanos 1:1-7; Hechos 9:1-16",
                primaryBookId = 45,
                primaryChapter = 1,
                passages = listOf(seg(45, "Romanos", 1), seg(44, "Hechos", 9)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    La epístola a los Romanos fue escrita por el apóstol Pablo alrededor del año 57 d.C. desde la ciudad de Corinto, durante su tercer viaje misionero. Pablo se dirigía a la iglesia en la capital del Imperio Romano, una congregación mixta compuesta por creyentes de origen judío y gentil a la que él aún no había visitado en persona.
                    
                    En el versículo 1, Pablo —quien fue transformado y llamado soberanamente camino a Damasco como vaso escogido (Hechos 9:1-16)— se presenta con tres títulos trascendentales: «Pablo, siervo de Jesucristo, llamado a ser apóstol, apartado para el evangelio de Dios». La palabra griega para siervo es «doulos»: un esclavo por amor y propiedad exclusiva, cuya voluntad está enteramente rendida a su Señor (Kyrios). No se presenta con títulos honoríficos mundanos, sino como propiedad comprada de Cristo.
                    
                    Pablo define el Evangelio («Euangelion», Buena Noticia) con tres verdades estructurales:
                    1. Fue prometido de antemano por sus profetas en las santas Escrituras (v. 2): El Evangelio no es una novedad improvisada ni una religión inventada, sino el cumplimiento del plan eterno de Dios anunciado en todo el Antiguo Testamento.
                    2. Es Cristocéntrico en su totalidad (vv. 3-4): Acerca de su Hijo Jesucristo, nacido del linaje de David según la carne (su verdadera humanidad davídica), pero declarado Hijo de Dios con poder según el Espíritu de santidad por la resurrección de entre los muertos (su deidad eterna y victoria triunfal).
                    3. Su meta es la obediencia de la fe en todas las naciones (v. 5): El Evangelio no busca un asentimiento pasivo, sino la rendición salvadora de todas las etnias bajo el señorío de Cristo.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    Romanos es el manifiesto cumbre de la gracia divina. En un mundo imperial donde Roma imponía su dominio mediante el poderío militar de las legiones, Pablo proclama que el verdadero poder redentor cósmico no reside en el César, sino en un judío crucificado y resucitado.
                    
                    Al llamar a los creyentes en Roma «amados de Dios, llamados a ser santos» (v. 7), Pablo establece que nuestra identidad no depende de nuestra posición social terrenal, sino de la gracia soberana del Padre que nos amó antes de la fundación del mundo.
                """,
                doctrinalThesis = "El Evangelio es la revelación prometida de la persona y obra de Jesucristo, cuya resurrección acredita su deidad suprema y demanda la obediencia de la fe universal.",
                exegeticalAnalysis = "El término «doulos» en Romanos 1:1 y la doble designación de Cristo («según la carne» / «según el Espíritu de santidad») fundamentan la cristología apostólica de las dos naturalezas en una sola Persona."
            ),

            // DÍA 2
            createStudyDay(
                dayNumber = 2,
                title = "Día 2: El Poder de Dios para Salvación: El Justo Vivirá por la Fe",
                passagesSummary = "Romanos 1:8-17; Habacuc 2:1-4",
                primaryBookId = 45,
                primaryChapter = 1,
                passages = listOf(seg(45, "Romanos", 1), seg(35, "Habacuc", 2)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En Romanos 1:16-17 se encuentra la tesis temática de toda la epístola, el texto que detonó la Reforma Protestante en el corazón de Martín Lutero y que ha transformado a millones de almas a lo largo de los siglos:
                    
                    «Porque no me avergüenzo del evangelio, porque es poder de Dios para salvación a todo aquel que cree; al judío primeramente, y también al griego. Porque en el evangelio la justicia de Dios se revela por fe y para fe, como está escrito: Mas el justo por la fe vivirá».
                    
                    Pablo no se avergüenza del Evangelio en la Roma imperial altiva y sofisticada porque sabe que el Evangelio es «dynamis Theou» (la dinamita o poder omnipotente de Dios) para rescate radical del pecador. No es un mero consejo moral para reformar costumbres; es el poder regenerador que traslada al ser humano de la muerte a la vida.
                    
                    La frase «la justicia de Dios» («Dikaiosynē Theou») no se refiere aquí a la justicia punitiva con la que Dios castiga a los impíos, sino a la justicia perfecta que Dios provee e imputa gratuitamente al pecador por los méritos de Cristo. Esta justicia se revela «ek pisteōs eis pistin» (de principio a fin por fe).
                    
                    Pablo sella su tesis citando Habacuc 2:4: «El justo por la fe vivirá». La salvación nunca ha sido por méritos de obras humanas; la vida espiritual se recibe y se sostiene únicamente confiando en la promesa de Dios.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    La religión humana siempre intenta fabricar una justicia propia basada en obras, rituales o comparaciones morales con los demás. El Evangelio derriba esa ilusión: revela que ante la santidad infinita del Creador, nuestras mejores obras son como trapos de inmundicia (Isaías 64:6).
                    
                    Solo una justicia divina infinita —la justicia perfecta de Cristo encarnado, obediente hasta la cruz— puede hacernos aceptables ante el tribunal de Dios. Esa justicia no se compra ni se gana con sacrificios; se recibe con la mano vacía de la fe. Este es el mensaje libertador que transforma la culpabilidad en paz inamovible.
                """,
                doctrinalThesis = "El Evangelio es el poder soberano de Dios (dynamis Theou) que otorga salvación mediante la imputación gratuita de la justicia divina al que cree.",
                exegeticalAnalysis = "El término «Dikaiosynē Theou» en Romanos 1:17 y la cita de Habacuc 2:4 articulan el principio de la Sola Fide como el único instrumento para ser declarado justo ante Dios."
            ),

            // DÍA 3
            createStudyDay(
                dayNumber = 3,
                title = "Día 3: La Ira de Dios y la Supresión de la Verdad",
                passagesSummary = "Romanos 1:18-23; Salmo 19:1-6",
                primaryBookId = 45,
                primaryChapter = 1,
                passages = listOf(seg(45, "Romanos", 1), seg(19, "Salmos", 19)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    Antes de desplegar la magnificencia de la gracia justificadora, el apóstol Pablo establece con severo rigor la necesidad absoluta del Evangelio mediante el diagnóstico judicial de la humanidad caída. En Romanos 1:18 leemos: «Porque la ira de Dios se revela desde el cielo contra toda impiedad e injusticia de los hombres que detienen con injusticia la verdad».
                    
                    La «ira de Dios» («orgē Theou») no es un arrebato emocional descontrolado, rencoroso o caprichoso como la ira humana; es la santa, deliberada, constante y justa hostilidad del Dios santo contra el mal moral y la rebelión cósmica de sus criaturas.
                    
                    Pablo expone la doctrina de la Revelación General: en la creación visible (como canta el Salmo 19:1-6: «Los cielos cuentan la gloria de Dios, y el firmamento anuncia la obra de sus manos»), las obras de Dios proclaman de forma incesante «su eterno poder y deidad, de modo que no tienen excusa» (vv. 19-20). El ser humano no es inocente por falta de luz; la tragedia del hombre natural radica en que «detiene con injusticia la verdad» («katechō»: suprime, ahoga y encarcela activamente el conocimiento de Dios para vivir según sus pasiones pecaminosas).
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    La raíz de todo pecado humano es la idolatría espiritual: habiendo conocido a Dios, no le glorificaron como a Dios ni le dieron gracias, sino que se envanecieron en sus razonamientos y su necio corazón fue entenebrecido (v. 21). «Cambiaron la gloria del Dios incorruptible en semejanza de imagen de hombre corruptible, de aves, de cuadrúpedos y de reptiles» (v. 23).
                    
                    El ser humano fue creado para adorar al Creador; cuando rechaza a Dios, no deja de adorar, sino que adora a las criaturas o a sí mismo. Esta degradación inicial explica toda la bancarrota moral, social y psicológica del mundo contemporáneo. Sin una correcta comprensión de la ira justa de Dios contra el pecado, la gracia del Evangelio pierde todo su valor y asombro.
                """,
                doctrinalThesis = "La ira santa de Dios se revela contra la rebelión de la humanidad que suprime deliberadamente la verdad de la revelación general para entregarse a la idolatría.",
                exegeticalAnalysis = "El participio «katechontōn» en Romanos 1:18 describe la resistencia activa y consciente del corazón humano caído contra el conocimiento innato y cósmico del Creador (vv. 19-20)."
            ),

            // DÍA 4
            createStudyDay(
                dayNumber = 4,
                title = "Día 4: Dios los Entregó: La Degeneración Moral y la Mente Reprobada",
                passagesSummary = "Romanos 1:24-32; Efesios 4:17-19",
                primaryBookId = 45,
                primaryChapter = 1,
                passages = listOf(seg(45, "Romanos", 1), seg(49, "Efesios", 4)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En Romanos 1:24-32 se describe uno de los juicios divinos más aterradores de las Escrituras. Cuando una sociedad insiste tercamente en rechazar al Dios verdadero y entronizar la inmoralidad, el juicio de Dios muchas veces no consiste en enviar fuego físico del cielo, sino en retirar su mano de gracia refrenadora y permitir que los hombres sufran las consecuencias naturales de sus propias elecciones.
                    
                    Este juicio judicial se repite tres veces con la misma fórmula solemne:
                    1. Versículo 24: «Por lo cual también Dios los entregó a la inmundicia, en las concupiscencias de sus corazones».
                    2. Versículo 26: «Por esto Dios los entregó a pasiones vergonzosas».
                    3. Versículo 28: «Y como ellos no aprobaron tener en cuenta a Dios, Dios los entregó a una mente reprobada, para hacer cosas que no convienen».
                    
                    El verbo griego para «entregó» es «paredōken»: un término judicial que significa entregar a un convicto en manos de sus verdugos. Dios entrega al hombre pecador a la tiranía de sus propios deseos destructivos y a la vanidad de su mente entenebrecida (como describe también Efesios 4:17-19).
                    
                    El texto culmina con una lista exhaustiva de pecados que destruyen el tejido social (injusticia, avaricia, malicia, envidia, homicidios, murmuradores, soberbios, desobedientes a los padres, desleales, sin afecto natural, implacables, sin misericordia). El versículo 32 denuncia el estado más profundo de corrupción: personas que sabiendo el juicio de Dios, no solo cometen estas cosas, sino que «se complacen con los que las practican», celebrando y normalizando la iniquidad.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    Este diagnóstico no fue escrito para que los creyentes nos llenemos de arrogancia o desprecio hacia el mundo, sino para mostrarnos la anatomía del corazón humano sin Cristo. Todo ser humano sin la gracia regeneradora del Espíritu Santo camina en esa misma dirección de decadencia.
                    
                    Cristo vino a rescatar a pecadores atrapados en esta misma condición. La sangre del Cordero tiene el poder de lavar al más vil transgresor, limpiar la mente reprobada y restaurar el corazón humano a la comunión con el Padre (1 Corintios 6:9-11).
                """,
                doctrinalThesis = "El juicio judicial pasivo de Dios («paredōken») consiste en retirar el freno moral y entregar a los rebeldes a las consecuencias destructivas de su propia idolatría y corrupción.",
                exegeticalAnalysis = "La triple repetición de «paredōken» en Romanos 1:24, 26 y 28 revela la espiral descendente del juicio divino que culmina en una mente reprobada («adokimon noun») incapaz de discernir el bien."
            ),

            // DÍA 5
            createStudyDay(
                dayNumber = 5,
                title = "Día 5: La Inexcusabilidad del Crítico y la Paciencia de Dios",
                passagesSummary = "Romanos 2:1-16; Mateo 7:1-5",
                primaryBookId = 45,
                primaryChapter = 2,
                passages = listOf(seg(45, "Romanos", 2), seg(40, "Mateo", 7)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En Romanos 2:1, Pablo da un giro pedagógico de brillante maestría. Habiendo descrito la depravación del mundo pagano gentil en el capítulo 1, cualquier lector moralista o religioso judío estaría asintiendo con complacencia y condenando a los gentiles. En ese instante, Pablo dispara una saeta a la conciencia del moralista: «Por lo cual eres inexcusable, oh hombre, quienquiera que seas tú que juzgas; pues en lo que juzgas a otro, te condenas a ti mismo; porque tú que juzgas haces lo mismo» (eco directo de la advertencia de Cristo en Mateo 7:1-5 sobre la viga en el ojo propio).
                    
                    Pablo desenmascara la hipocresía del moralismo religioso: condenar el pecado en los demás mientras se practica el mismo pecado en secreto o con refinamiento exterior no libra a nadie del juicio de Dios.
                    
                    En el versículo 4, Pablo confronta la presunción del religioso: «¿O menosprecias las riquezas de su benignidad, paciencia y longanimidad, ignorando que su benignidad te guía al arrepentimiento?». El religioso confunde la paciencia de Dios con complicidad divina; cree que porque Dios no lo ha fulminado de inmediato, aprueba su conducta. Pero la bondad de Dios tiene un solo propósito pedagógico: conducir al quebrantamiento y arrepentimiento sincero.
                    
                    Pablo establece los principios objetivos del juicio universal de Dios en los versículos 6 al 11:
                    - Dios juzgará a cada uno según sus obras reales, no según sus discursos religiosos.
                    - El juicio será imparcial: «porque no hay acepción de personas para con Dios» (v. 11).
                    - Dios juzgará «los secretos de los hombres por Jesucristo» (v. 16): no solo las acciones públicas, sino las intenciones ocultas del corazón.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    Tanto el pagano desenfrenado del capítulo 1 como el religioso moralista del capítulo 2 están igualmente perdidos y desprovistos de justicia ante el Creador. La moralidad externa no puede expiar la corrupción interior.
                    
                    El único Juez infalible es Jesucristo, a quien el Padre ha encomendado todo juicio (Juan 5:22). Esta confrontación destruye el orgullo espiritual y nos prepara para comprender que no hay salvación en compararnos con otros pecadores; nuestra única esperanza es correr al único que es verdaderamente justo: Jesucristo.
                """,
                doctrinalThesis = "El juicio de Dios es perfectamente justo, imparcial y escudriñador de los secretos del corazón, dejando sin excusa tanto al inmoral como al religioso hipócrita.",
                exegeticalAnalysis = "Romanos 2:4 enseña que la benignidad («chrēstotēs») y la paciencia («anochē») de Dios son llamamientos activos a la metanoia (arrepentimiento) y no licencias de impunidad moral."
            ),

            // DÍA 6
            createStudyDay(
                dayNumber = 6,
                title = "Día 6: La Letra y el Espíritu: La Verdadera Circuncisión del Corazón",
                passagesSummary = "Romanos 2:17-29; Deuteronomio 10:12-16",
                primaryBookId = 45,
                primaryChapter = 2,
                passages = listOf(seg(45, "Romanos", 2), seg(5, "Deuteronomio", 10)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En Romanos 2:17-24, Pablo aborda directamente al judío que se gloriaba en sus privilegios históricos pactuales: tener la Ley de Moisés, conocer la voluntad de Dios, ser guía de ciegos y poseer la circuncisión en la carne. Pablo le formula preguntas penetrantes: «Tú, pues, que enseñas a otro, ¿no te enseñas a ti mismo? Tú que predicas que no se ha de hurtar, ¿hurtas? Tú que dices que no se ha de adulterar, ¿adulteras?».
                    
                    El mero conocimiento intelectual de la Ley no salva si no se vive en obediencia perfecta: «Porque el nombre de Dios es blasfemado entre los gentiles por causa de vosotros» (v. 24).
                    
                    En los versículos 28 y 29, Pablo formula una distinción teológica monumental que redefine la identidad del verdadero pueblo de Dios:
                    «Pues no es judío el que lo es exteriormente, ni es la circuncisión la que se hace exteriormente en la carne; sino que es judío el que lo es en lo interior, y la circuncisión es la del corazón, en espíritu, no en letra; la alabanza del cual no viene de los hombres, sino de Dios».
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    La religión basada en la letra externa y en los ritos ceremoniales busca la aprobación humana y produce orgullo farisaico. Pero el pacto de Dios siempre demandó la circuncisión del corazón (Deuteronomio 10:12-16; 30:6; Jeremías 4:4): el quebrantamiento de la soberbia y la regeneración de los afectos por el Espíritu Santo.
                    
                    Pablo juega con el significado hebreo de la palabra Judío («Yehudí», derivado de Yehudá, que significa «Alabanza»): el verdadero judío según el corazón de Dios es aquel cuya alabanza no procede de los hombres, sino del Creador.
                    
                    En el Nuevo Pacto, todo aquel que ha sido regenerado por el Espíritu y confía únicamente en Cristo es parte del verdadero Israel de Dios (Gálatas 6:16; Filipenses 3:3). Lo que cuenta no es el linaje biológico ni las ceremonias externas, sino una nueva criatura en Cristo Jesús.
                """,
                doctrinalThesis = "La verdadera identidad en el pueblo de Dios no reside en el linaje biológico ni en ceremonias externas, sino en la regeneración interior del corazón operada por el Espíritu Santo.",
                exegeticalAnalysis = "El juego de palabras en Romanos 2:29 entre «Yehudá» (alabanza) y la alabanza («epainos») que viene de Dios redefine espiritualmente la membresía del pacto a la luz del corazón regenerado."
            ),

            // DÍA 7
            createStudyDay(
                dayNumber = 7,
                title = "Día 7: La Universalidad del Pecado: No Hay Justo ni Aun Uno",
                passagesSummary = "Romanos 3:1-20; Salmo 14:1-3; Salmo 53:1-3",
                primaryBookId = 45,
                primaryChapter = 3,
                passages = listOf(seg(45, "Romanos", 3), seg(19, "Salmos", 14), seg(19, "Salmos", 53)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En Romanos 3:9-20, el apóstol Pablo presenta el acta de acusación final de su juicio teológico universal. Habiendo examinado al gentil en el capítulo 1 y al judío en el capítulo 2, formula la pregunta procesal: «¿Qué, pues? ¿Somos nosotros mejores que ellos? En ninguna manera; pues ya hemos acusado a judíos y a gentiles, que todos están bajo pecado» (v. 9).
                    
                    Para sustentar este veredicto categórico, Pablo compone una sobrecogedora cadena de citas del Antiguo Testamento (Salmo 14:1-3, Salmo 53:1-3, Salmos 5, 140, 10, 36 e Isaías 59) que diagnostica la corrupción humana en tres dimensiones:
                    1. En el Carácter y la Condición Moral (vv. 10-12): «No hay justo, ni aun uno; no hay quien entienda, no hay quien busque a Dios. Todos se desviaron, a una se hicieron inútiles; no hay quien haga lo bueno, no hay ni siquiera uno». La depravación humana es total: no significa que todo hombre sea tan malo como podría ser, sino que cada facultad (mente, voluntad, afectos) está infectada por el pecado.
                    2. En las Conversaciones y el Habla (vv. 13-14): «Sepulcro abierto es su garganta; con su lengua engañan. Veneno de áspides hay debajo de sus labios; su boca está llena de maldición y de amargura».
                    3. En las Acciones y la Violencia (vv. 15-18): «Sus pies se apresuran para derramar sangre; quebranto y desventura hay en sus caminos... No hay temor de Dios delante de sus ojos».
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    El versículo 19 emite la sentencia que cierra todo debate humano: «Pero sabemos que todo lo que la ley dice, lo dice a los que están bajo la ley, para que toda boca se cierre y todo el mundo quede bajo el juicio de Dios». La Ley de Dios actúa como un fiscal divino implacable: quita toda excusa y demuestra que la humanidad entera está en bancarrota culpable ante el Creador.
                    
                    Por consiguiente, el versículo 20 establece un principio dogmático innegociable: «ya que por las obras de la ley ningún ser humano será justificado delante de él; porque por medio de la ley es el conocimiento del pecado». La Ley es como un espejo limpio: nos muestra con nitidez que nuestro rostro está sucio, pero el espejo no puede lavarnos la suciedad. Para limpiarnos necesitamos la fuente de sangre que brota de la cruz de Cristo.
                """,
                doctrinalThesis = "La condición de la humanidad caída es de depravación total y culpa universal («no hay justo ni aun uno»), haciendo imposible la justificación por las obras de la ley.",
                exegeticalAnalysis = "La expresión jurídica «hina pan stoma phragēi» («para que toda boca se cierre») en Romanos 3:19 describe al reo convicto ante el tribunal supremo de Dios desprovisto de cualquier defensa legal."
            ),

            // DÍA 8
            createStudyDay(
                dayNumber = 8,
                title = "Día 8: Pero Ahora: La Justicia de Dios Revelada Gratuitamente",
                passagesSummary = "Romanos 3:21-26; 2 Corintios 5:18-21",
                primaryBookId = 45,
                primaryChapter = 3,
                passages = listOf(seg(45, "Romanos", 3), seg(47, "2 Corintios", 5)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    Tras el oscuro diagnóstico de bancarrota moral de los primeros tres capítulos, en Romanos 3:21 irrumpen las dos palabras más luminosas y gloriosas de toda la literatura teológica: «PERO AHORA» (en griego: «Nyni de»). El amanecer de la gracia soberana disipa las tinieblas de la condenación:
                    
                    «Pero ahora, aparte de la ley, se ha manifestado la justicia de Dios, testificada por la ley y por los profetas; la justicia de Dios por medio de la fe en Jesucristo, para todos los que creen en él. Porque no hay diferencia, por cuanto todos pecaron, y están destituidos de la gloria de Dios, siendo justificados gratuitamente por su gracia, mediante la redención que es en Cristo Jesús» (Ro 3:21-24).
                    
                    Analicemos con reverencia los pilares de este pasaje:
                    1. «Aparte de la ley» (chōris nomou): La justicia salvadora no se adquiere guardando preceptos legales, sino que es un don concedido enteramente aparte de nuestro desempeño.
                    2. «Destituidos de la gloria de Dios» (hysterountai tēs doxēs tou Theou): Todos carecemos de la gloria original para la cual fuimos creados; nadie puede gloriarse sobre otro.
                    3. «Justificados gratuitamente por su gracia» (dikaioumenoi dōrean tēi autou chariti): El adverbio «dōrean» significa «sin causa en nosotros», como un regalo absoluto. Es la misma palabra que Jesús usó en Juan 15:25: «me odiaron sin causa» (dōrean). Del mismo modo que el mundo odió a Jesús sin que hubiera causa en Él, Dios justifica al pecador sin que haya mérito alguno en él.
                    4. «Mediante la redención» (dia tēs apolytrōseōs): El rescate pagado para comprar la libertad de un esclavo en el mercado pagano. El precio de nuestra liberación fue la sangre de Cristo.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    En los versículos 25 y 26 se revela el misterio supremo de la cruz: Dios puso a Cristo «como propiciación [Hilastērion] por medio de la fe en su sangre, para manifestar su justicia... a fin de que él sea el justo, y el que justifica al que es de la fe de Jesús».
                    
                    ¿Cómo puede un Dios santo perdonar a pecadores culpables sin comprometer su propia justicia? Si un juez terrenal dejara libre a un criminal asesino sin castigar el delito, ese juez sería corrupto e injusto. Dios no barrió nuestros pecados debajo de la alfombra cósmica; descargó el castigo completo e implacable sobre su propio Hijo encarnado en la cruz («Al que no conoció pecado, por nosotros lo hizo pecado, para que nosotros fuésemos hechos justicia de Dios en él», 2 Corintios 5:18-21). En el Calvario, Dios demostró ser perfectamente Justo (el pecado fue castigado hasta la última gota) y al mismo tiempo el Justificador amoroso de todo aquel que cree en Jesús.
                """,
                doctrinalThesis = "La justificación es un acto judicial gratuito de Dios basado en la redención y propiciación de Cristo en la cruz, donde Dios preserva su santa justicia al justificar al impío por la fe.",
                exegeticalAnalysis = "Los términos «dōrean» (gratuitamente / sin causa), «apolytrōsis» (rescate pagado) e «Hilastērion» (propiciación) en Romanos 3:24-25 constituyen el núcleo doctrinal indestructible de la soteriología bíblica."
            ),

            // DÍA 9
            createStudyDay(
                dayNumber = 9,
                title = "Día 9: La Exclusión de la Jactancia y la Unidad por la Fe",
                passagesSummary = "Romanos 3:27-31; Efesios 2:8-9",
                primaryBookId = 45,
                primaryChapter = 3,
                passages = listOf(seg(45, "Romanos", 3), seg(49, "Efesios", 2)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En Romanos 3:27-31, habiendo establecido la justificación gratuita por la fe en la sangre propiciatoria de Cristo, el apóstol Pablo extrae tres conclusiones demoledoras contra el orgullo religioso humano:
                    
                    1. La Exclusión Total de la Jactancia (v. 27): «¿Dónde, pues, está la jactancia? Queda excluida. ¿Por cuál ley? ¿Por la de las obras? No, sino por la ley de la fe» (en plena armonía con Efesios 2:8-9: «no por obras, para que nadie se gloríe»). La palabra para excluida es «exekleisthē»: la puerta ha sido cerrada con cerrojo de una vez y para siempre. Si la salvación dependiera en un 1% de nuestro esfuerzo, tendríamos motivos para enorgullecernos ante Dios y mirar con desdén a los demás. Pero siendo 100% por gracia mediante la fe, toda vanagloria queda abolida y solo Cristo recibe la honra.
                    2. La Conclusión Dogmática Innegociable (v. 28): «Concluimos, pues, que el hombre es justificado por fe sin las obras de la ley» (pistei dikaioussthai anthrōpon chōris ergōn nomou). Esta es la célebre Sola Fide que sacudió los cimientos de la Europa medieval.
                    3. El Dios Único de Judíos y Gentiles (vv. 29-30): «¿Es Dios solamente Dios de los judíos? ¿No es también Dios de los gentiles? Ciertamente, también de los gentiles. Porque Dios es uno, y él justificará por la fe a los de la circuncisión, y por medio de la fe a los de la incircuncisión». La salvación por la sola fe es el gran nivelador de la humanidad: ante la cruz no hay privilegios de casta, raza o nación; todos somos pecadores salvados por el mismo Redentor.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    En el versículo 31, Pablo responde preventivamente a una calumnia que los legalistas siempre lanzan contra la gracia: «¿Luego por la fe invalidamos la ley? En ninguna manera, sino que confirmamos la ley». 
                    
                    La justificación por la fe no pisotea la ley moral de Dios; la confirma y establece en el nivel más elevado posible. Quien pretende justificarse por la ley la rebaja, pues rebaja las exigencias de la ley a su propia capacidad humana imperfecta; pero el creyente que mira a la cruz reconoce que la ley de Dios demandaba una justicia tan infinita y perfecta que solo la sangre del Hijo de Dios encarnado pudo satisfacerla a cabalidad.
                """,
                doctrinalThesis = "La justificación por la fe excluye categóricamente cualquier jactancia humana y confirma la dignidad perfecta de la ley divina satisfecha plenamente en la cruz de Cristo.",
                exegeticalAnalysis = "El verbo «exekleisthē» en Romanos 3:27 sella la abolición radical del mérito humano, estableciendo la «Sola Fide» (v. 28) como el fundamento exclusivo de la reconciliación con Dios."
            ),

            // DÍA 10
            createStudyDay(
                dayNumber = 10,
                title = "Día 10: David y Abraham: La Justicia Imputada sin Obras",
                passagesSummary = "Romanos 4:1-8; Salmo 32:1-5",
                primaryBookId = 45,
                primaryChapter = 4,
                passages = listOf(seg(45, "Romanos", 4), seg(19, "Salmos", 32)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En Romanos 4:1-8, Pablo acude a los dos testigos supremos de la historia de Israel para blindar su doctrina de la justificación por la fe: Abraham (el padre del pueblo de Dios) y David (el gran rey teocrático y cantor de Israel).
                    
                    En los versículos 4 y 5, Pablo expone el contraste irrefutable entre la lógica del salario y la lógica de la gracia:
                    «Pero al que obra, no se le cuenta el salario como gracia, sino como deuda; mas al que no obra, sino cree en aquel que justifica al impío, su fe le es contada por justicia».
                    
                    Obsérvese la audacia conmovedora de la frase de Pablo: Dios justifica «al impío» («ton asebē»). En el Antiguo Testamento terrenal, un juez que justificaba al impío era una abominación a Jehová (Proverbios 17:15). ¿Cómo puede Dios justificar al impío sin volverse injusto? Porque en la justificación, Dios no declara que el impío jamás haya pecado, sino que le imputa («logizomai») la justicia perfecta de Cristo, mientras que los pecados del impío fueron imputados y castigados en Cristo en el Calvario.
                    
                    Pablo convoca luego el testimonio de David en el Salmo 32:1-2: «Bienaventurado aquel cuya transgresión ha sido perdonada, y cubierto su pecado. Bienaventurado el varón a quien el Señor no inculpa de pecado» (vv. 7-8). David experimentó la culpa desgarradora del adulterio con Betsabé y el homicidio de Urías; sabía por propia experiencia que si Dios lo juzgaba conforme a sus obras, estaba irremisiblemente perdido. Su bienaventuranza descansó en que Dios no le inculpó (no le acreditó contablemente) su iniquidad, sino que perdonó su transgresión por pura misericordia.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    La justificación implica una doble transacción divina (la doctrina de la doble imputación):
                    1. Nuestros pecados fueron transferidos e imputados a Cristo en la cruz, donde Él pagó la sentencia completa en nuestro lugar.
                    2. La justicia perfecta y obediencia intachable de Cristo nos fue transferida e imputada a nosotros en el momento en que creímos.
                    
                    Cuando el Padre celestial mira al creyente justificado, no ve sus pecados y debilidades; ve la hermosura radiante, la justicia y la perfección de su propio Hijo amado. Esta es la bienaventuranza suprema que disipa la condenación y llena el alma de un cántico eterno de gratitud.
                """,
                doctrinalThesis = "La justificación imputa la justicia de Cristo al pecador que cree y no le inculpa sus transgresiones, según el testimonio unánime de Abraham y David.",
                exegeticalAnalysis = "El término contable «logizomai» en Romanos 4:3-8 define la justificación como una acreditación forense de justicia divina y no como una transformación moral intrínseca previa del creyente."
            ),

            // DÍA 11
            createStudyDay(
                dayNumber = 11,
                title = "Día 11: La Fe de Abraham: Esperanza Contra Esperanza",
                passagesSummary = "Romanos 4:13-25; Génesis 17:15-22",
                primaryBookId = 45,
                primaryChapter = 4,
                passages = listOf(seg(45, "Romanos", 4), seg(1, "Génesis", 17)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En Romanos 4:13-25, el apóstol Pablo analiza la naturaleza de la fe bíblica a través del relato de Abraham cuando recibió la promesa de que sería padre de multitudes siendo él de casi cien años de edad y considerando la matriz estéril y muerta de Sara (Génesis 17:15-22).
                    
                    El versículo 18 registra una frase inmortal: «Él creyó en esperanza contra esperanza, para llegar a ser padre de muchas gentes... Y no se debilitó en la fe al considerar su cuerpo, que estaba ya como muerto... ni tampoco la esterilidad de la matriz de Sara. Tampoco dudó, por incredulidad, de la promesa de Dios, sino que se fortaleció en fe, dando gloria a Dios, plenamente convencido de que era también poderoso para hacer todo lo que había prometido; por lo cual también su fe le fue contada por justicia» (vv. 18-22).
                    
                    La fe bíblica no es un optimismo ciego, ni una autosugestión psicológica, ni un salto al vacío irracional. Abraham no cerró los ojos ante la realidad biológica: «consideró su cuerpo» y la esterilidad de Sara. Vio la imposibilidad absoluta humana; pero fijó los ojos de su alma en el Dios «que da vida a los muertos, y llama las cosas que no son, como si fuesen» (v. 17). 
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    La fe no descansa en los recursos del hombre, sino en el carácter inmutable y el poder omnipotente de Dios. En los versículos 23 al 25, Pablo aplica este principio directamente a nuestra propia salvación:
                    
                    «Y no solamente con respecto a él se escribió que le fue contada, sino también con respecto a nosotros a quienes ha de ser contada, esto es, a los que creemos en el que levantó de los muertos a Jesús, Señor nuestro, el cual fue entregado por nuestras transgresiones, y resucitado para nuestra justificación».
                    
                    Cristo fue «entregado» («paredothē», el Padre entregó a su Hijo a la muerte expiatoria en la cruz para saldar la deuda de nuestras transgresiones); y fue «resucitado para nuestra justificación» («ēgerthē dia tēn dikaiōsin hēmōn»). La resurrección corporal de Jesucristo es el recibo divino celestial de que el pago del Calvario fue aceptado por el Padre en su totalidad: la deuda ha quedado cancelada para siempre.
                """,
                doctrinalThesis = "La fe salvadora descansa con plena convicción en el Dios que da vida a los muertos, cuya resurrección de Jesucristo es la garantía definitiva de nuestra justificación eterna.",
                exegeticalAnalysis = "La cláusula «ēgerthē dia tēn dikaiōsin hēmōn» en Romanos 4:25 establece que la resurrección de Cristo es la vindicación pública y jurídica de la eficacia total de su sacrificio en la cruz."
            ),

            // DÍA 12
            createStudyDay(
                dayNumber = 12,
                title = "Día 12: Paz con Dios, Acceso a la Gracia y Gozo en la Tribulación",
                passagesSummary = "Romanos 5:1-5; Santiago 1:2-4",
                primaryBookId = 45,
                primaryChapter = 5,
                passages = listOf(seg(45, "Romanos", 5), seg(59, "Santiago", 1)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En Romanos 5:1-5, Pablo pasa de la demostración doctrinal de la justificación a desplegar sus frutos y consecuencias bienaventuradas en la experiencia del creyente:
                    
                    1. Paz para con Dios (v. 1): «Justificados, pues, por la fe, tenemos paz para con Dios por medio de nuestro Señor Jesucristo». La hostilidad terminó; ya no somos enemigos bajo ira, sino hijos reconciliados.
                    2. Acceso a la Gracia en que Estamos Firmes (v. 2): Por medio de Cristo tenemos «entrada» («prosagōgē», introducción formal a la presencia del Rey celestial). No estamos bajo juicio temeroso; estamos firmes en un estado permanente de favor inmerecido.
                    3. La Esperanza Inconmovible de la Gloria de Dios (v. 2): Un anhelo gozoso y certero del triunfo escatológico final.
                    4. La Gloria en las Tribulaciones (vv. 3-4): «Y no sólo esto, sino que también nos gloriamos en las tribulaciones, sabiendo que la tribulación produce paciencia; y la paciencia, prueba; y la prueba, esperanza» (verdad paralela a Santiago 1:2-4: «tened por sumo gozo cuando os halléis en diversas pruebas, sabiendo que la prueba de vuestra fe produce paciencia»). La palabra griega para tribulación es «thlipsis» (presión aplastante, aflicción profunda). En las manos soberanas de Dios, el sufrimiento del creyente deja de ser un castigo penal y se convierte en un instrumento paternal de santificación que forja el carácter aprobado («dokimē», oro purificado en el crisol).
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    La cumbre de esta seguridad se corona en el versículo 5: «y la esperanza no avergüenza; porque el amor de Dios ha sido derramado en nuestros corazones por el Espíritu Santo que nos fue dado».
                    
                    El verbo «ha sido derramado» («ekkechytai») está en tiempo perfecto de indicativo pasivo: describe una inundación torrencial abundante cuyos efectos permanecen saturando el alma. El Espíritu Santo no nos da unas pocas gotas tacañas de afecto; inunda nuestra conciencia con la certeza inquebrantable de cuánto nos ama el Padre en Cristo. Por esta razón, el sufrimiento no nos destruye ni la muerte nos vence: estamos amparados por un amor que trasciende la tumba.
                """,
                doctrinalThesis = "La justificación confiere paz inmutable con Dios, acceso permanente a la gracia y un gozo victorioso que transforma las tribulaciones en instrumentos de esperanza por el amor del Espíritu.",
                exegeticalAnalysis = "El término «ekkechytai» en Romanos 5:5 retrata la efusión continua y desbordante del amor divino en el corazón del creyente mediante la presencia del Espíritu Santo."
            ),

            // DÍA 13
            createStudyDay(
                dayNumber = 13,
                title = "Día 13: El Amor Demostrado en la Cruz y la Reconciliación Plena",
                passagesSummary = "Romanos 5:6-11; 1 Juan 4:9-11",
                primaryBookId = 45,
                primaryChapter = 5,
                passages = listOf(seg(45, "Romanos", 5), seg(62, "1 Juan", 4)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En Romanos 5:6-10, el apóstol Pablo expone el argumento supremo del amor de Dios utilizando la figura lógica del argumento «a fortiori» (de lo mayor a lo menor). Pablo describe cuatro estados trágicos de nuestra condición previa a la gracia:
                    - Éramos «débiles» (v. 6: «asthenes», espiritualmente impotentes e incapaces de agradar a Dios).
                    - Éramos «impíos» (v. 6: «asebōn», rebeldes y sacrílegos).
                    - Éramos «pecadores» (v. 8: «hamartōlōn», violadores continuos de su santa ley).
                    - Éramos «enemigos» (v. 10: «echthroi», en activa hostilidad moral contra el Creador).
                    
                    En la sociedad humana, difícilmente alguien moriría por un justo, aunque tal vez por un bienhechor alguno osara morir. Pero en el versículo 8 resplandece la singularidad absoluta del Dios bíblico: «Mas Dios muestra su amor para con nosotros, en que siendo aún pecadores, Cristo murió por nosotros» (en sintonía con 1 Juan 4:9-11: «En esto consiste el amor: no en que nosotros hayamos amado a Dios, sino en que él nos amó a nosotros, y envió a su Hijo en propiciación por nuestros pecados»). Dios no esperó a que nos reformáramos, limpiáramos o buscáramos su favor; envió a su Hijo a morir por nosotros cuando éramos sus enemigos declarados.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    De aquí Pablo extrae su conclusión matemática celestial en los versículos 9 y 10:
                    «Pues mucho más, estando ya justificados en su sangre, por él seremos salvos de la ira. Porque si siendo enemigos, fuimos reconciliados con Dios por la muerte de su Hijo, mucho más, estando reconciliados, seremos salvos por su vida».
                    
                    Si Dios hizo lo más difícil —entregar a su Hijo amado a la agonía de la cruz cuando éramos sus enemigos rebeldes—, ¿cómo no hará lo menor: preservar con fidelidad hasta el final a aquellos a quienes ya ha reconciliado y justificado con la sangre de Cristo? 
                    
                    Cristo no solo murió en la cruz; resucitó y vive para siempre a la diestra del Padre intercediendo por nosotros (Hebreos 7:25). Nuestra salvación está sellada por su vida indestructible. Por eso el versículo 11 concluye con una doxología triunfal: «Y no sólo esto, sino que también nos gloriamos en Dios por el Señor nuestro Jesucristo, por quien hemos recibido ahora la reconciliación».
                """,
                doctrinalThesis = "La muerte de Cristo por nosotros cuando éramos enemigos e impíos es la demostración suprema del amor de Dios, garantizando nuestra salvación final por medio de su vida resucitada.",
                exegeticalAnalysis = "El argumento «a fortiori» de Romanos 5:9-10 («pollo mallon», mucho más) establece la seguridad eterna inquebrantable de aquellos que han sido justificados por la sangre de Cristo."
            ),

            // DÍA 14
            createStudyDay(
                dayNumber = 14,
                title = "Día 14: Los Dos Hombres: Adán y Cristo: Donde Abundó el Pecado",
                passagesSummary = "Romanos 5:12-21; 1 Corintios 15:45-49",
                primaryBookId = 45,
                primaryChapter = 5,
                passages = listOf(seg(45, "Romanos", 5), seg(46, "1 Corintios", 15)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En Romanos 5:12-21 se expone la colosal teología federal que articula toda la historia de la humanidad en torno a dos hombres representativos: el primer Adán y el postrer Adán (Jesucristo).
                    
                    El versículo 12 describe el origen de la catástrofe humana: «Por tanto, como el pecado entró en el mundo por un hombre, y por el pecado la muerte, así la muerte pasó a todos los hombres, por cuanto todos pecaron». El verbo «pecaron» («hēmarton») está en tiempo aoristo: describe una acción histórica puntual que ocurrió en el huerto del Edén cuando Adán, como cabeza federal de la raza, pecó y arrastró a toda su descendencia biológica a la culpabilidad legal y a la corrupción moral.
                    
                    Sin embargo, en el versículo 14, Pablo llama a Adán «figura del que había de venir» («typos tou mellontos», tipo de Cristo, a quien en 1 Corintios 15:45-49 denomina el «postrer Adán» y el «segundo hombre, que es el Señor mismo del cielo»). El paralelismo tipológico descansa en el principio de la representación federal: lo que una cabeza representativa hace, se aplica a todos los que están unidos a ella:
                    - En Adán: Una sola transgresión trajo condenación, juicio y reinado de la muerte sobre todos los que están en él por nacimiento carnal (vv. 16-18).
                    - En Cristo: Un solo acto de obediencia justa en la cruz trajo justificación gratuita, don de justicia y reino de vida sobre todos los que están en Él por la fe salvadora (vv. 17-19).
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    La gracia de Cristo no es un simple empate que nos regresa a la fragilidad del huerto de Edén; es una victoria infinitamente superior y sobreabundante. En el versículo 20, Pablo pronuncia una de las declaraciones más consoladoras de las Escrituras: «Pero la ley se introdujo para que el pecado abundase; mas cuando el pecado abundó, sobreabundó la gracia».
                    
                    El vocablo griego para sobreabundó es «hypereperisseusen» (abundó con hiper-abundancia, como un océano infinito que sepulta una gota de inmundicia). El pecado del hombre fue terrible y mortal, pero la gracia de Cristo en la cruz es infinitamente más poderosa que todo el pecado del mundo. Quien está en Cristo ya no está bajo el dominio tiránico de la muerte de Adán, sino bajo el reinado eterno de la gracia que reina por la justicia para vida eterna (v. 21).
                """,
                doctrinalThesis = "Toda la humanidad está ligada federalmente a Adán (condenación y muerte) o a Cristo (justificación y vida), venciendo la gracia sobreabundante de Cristo el desastre de la caída.",
                exegeticalAnalysis = "El paralelismo federal de Romanos 5:12-19 y el vocablo «hyperperisseuō» (v. 20) demuestran que la justicia imputada por Cristo supera con creces la pérdida sufrida en el pecado original de Adán."
            ),

            // DÍA 15
            createStudyDay(
                dayNumber = 15,
                title = "Día 15: Muertos al Pecado: La Unión con Cristo en su Muerte y Resurrección",
                passagesSummary = "Romanos 6:1-11; Colosenses 3:1-4",
                primaryBookId = 45,
                primaryChapter = 6,
                passages = listOf(seg(45, "Romanos", 6), seg(51, "Colosenses", 3)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    Habiendo proclamado en el capítulo 5 que «donde el pecado abundó, sobreabundó la gracia», el apóstol Pablo anticipa la calumnia o distorsión libertina que podría surgir: «¿Qué, pues, diremos? ¿Perseveraremos en el pecado para que la gracia abunde?» (Romanos 6:1). 
                    
                    La respuesta de Pablo en el versículo 2 es tajante e inflexible: «¡En ninguna manera!» (en griego: «Mē genoito», «¡Que ni siquiera se piense tal disparate!»). «Porque los que hemos muerto al pecado, ¿cómo viviremos aún en él?».
                    
                    En los versículos 3 al 6, Pablo explica la teología de la unión mística con Cristo simbolizada en las aguas del bautismo:
                    1. Sepultados en su Muerte (vv. 3-4): Al creer en Jesús, fuimos unidos espiritualmente a Él de modo que su muerte en la cruz fue nuestra muerte a la tiranía del pecado. Nuestro viejo hombre («palaios anthrōpos») fue crucificado juntamente con Él para que el cuerpo del pecado sea destruido, a fin de que no sirvamos más al pecado (v. 6).
                    2. Resucitados para Vida Nueva (vv. 4-5): Así como Cristo resucitó de los muertos por la gloria del Padre, así también nosotros fuimos levantados espiritualmente para «andar en novedad de vida» («en kainotēti zōēs peripatēsōmen»; como afirma Colosenses 3:1-4: «Habéis resucitado con Cristo, buscad las cosas de arriba... porque habéis muerto, y vuestra vida está escondida con Cristo en Dios»).
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    En el versículo 11, Pablo prescribe el ejercicio espiritual indispensable de la fe santificadora: «Así también vosotros consideraos muertos al pecado, pero vivos para Dios en Cristo Jesús, Señor nuestro».
                    
                    El verbo «consideraos» («logizesthe», la misma palabra contable de la justificación) es un imperativo presente: debemos actuar y considerar como un hecho objetivo real que en Cristo hemos muerto a la potestad legal del pecado. El pecado todavía existe en este mundo y tienta nuestra carne, pero ya no tiene autoridad legal ni derecho de amo sobre nosotros. La tumba ha quedado vacía: pertenecemos al Rey resucitado y caminamos en el poder de su vida inmortal.
                """,
                doctrinalThesis = "Por nuestra unión espiritual con Cristo, hemos muerto de una vez y para siempre al dominio del pecado y resucitado a una novedad de vida santa e incorruptible.",
                exegeticalAnalysis = "La frase «en kainotēti zōēs» en Romanos 6:4 y la crucifixión del «palaios anthrōpos» (v. 6) certifican la ruptura definitiva con la esclavitud del pecado en la regeneración."
            ),

            // DÍA 16
            createStudyDay(
                dayNumber = 16,
                title = "Día 16: Esclavos de la Justicia: La Verdadera Libertad Cristiana",
                passagesSummary = "Romanos 6:12-23; Juan 8:31-36",
                primaryBookId = 45,
                primaryChapter = 6,
                passages = listOf(seg(45, "Romanos", 6), seg(43, "Juan", 8)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En Romanos 6:12-23, Pablo aterriza la doctrina de la unión con Cristo en mandatos éticos prácticos para el gobierno cotidiano de nuestra vida corporal: «No reine, pues, el pecado en vuestro cuerpo mortal, de modo que lo obedezcáis en sus concupiscencias; ni tampoco presentéis vuestros miembros al pecado como instrumentos de iniquidad, sino presentaos vosotros mismos a Dios como vivos de entre los muertos, y vuestros miembros a Dios como instrumentos de justicia» (vv. 12-13).
                    
                    La palabra griega para instrumentos es «hopla» (armas militares). Nuestros ojos, labios, manos, pies y pensamientos ya no son armas al servicio del ejército del pecado; son armas consagradas en manos de Dios para el avance de su justicia y santidad.
                    
                    En el versículo 14 resuena la carta magna de la libertad cristiana: «Porque el pecado no se enseñoreará de vosotros; pues no estáis bajo la ley, sino bajo la gracia». No estar bajo la ley significa que ya no estamos bajo el pacto de obras que condena al transgresor sin capacitarlo; estar bajo la gracia significa que estamos bajo el poder vivificador del Espíritu que nos capacita para vencer el pecado.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    Pablo desmantela la falsa noción secular de «libertad»: el ser humano nunca es autónomo; siempre es esclavo de aquello a lo que se somete (v. 16; tal como enseñó Jesús en Juan 8:31-36: «todo aquel que hace pecado, esclavo es del pecado... Así que, si el Hijo os libertare, seréis verdaderamente libres»): o es esclavo del pecado para muerte, o es esclavo de Dios para santidad y vida eterna.
                    
                    El capítulo concluye con un versículo de solemne belleza en el versículo 23: «Porque la paga del pecado es muerte, mas la dádiva de Dios es vida eterna en Cristo Jesús Señor nuestro».
                    
                    Obsérvese el contraste deliberado de vocabulario:
                    - El salario («opsōnia», el sueldo militar que un soldado devenga por su servicio) que el pecado paga es la muerte eterna. Quien peca recibe exactamente lo que se ganó a pulso.
                    - Pero la vida eterna no es un salario devengado por méritos humanos; es un «charisma» (un regalo libre y soberano de pura gracia divina) concedido en Cristo Jesús. Servir a Cristo como esclavos de amor es la única y verdadera libertad del alma.
                """,
                doctrinalThesis = "La libertad cristiana no es autonomía para pecar, sino liberación de la esclavitud del pecado para consagrar los miembros a Dios como instrumentos de justicia y vida eterna.",
                exegeticalAnalysis = "El contraste entre «opsōnia» (salario devengado) y «charisma» (don gratuito inmerecido) en Romanos 6:23 sella la absoluta gratuidad de la vida eterna frente a la condenación merecida del pecado."
            ),

            // DÍA 17
            createStudyDay(
                dayNumber = 17,
                title = "Día 17: Muertos a la Ley para Pertenecer al Señor Resucitado",
                passagesSummary = "Romanos 7:1-6; Gálatas 2:19-20",
                primaryBookId = 45,
                primaryChapter = 7,
                passages = listOf(seg(45, "Romanos", 7), seg(48, "Gálatas", 2)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En Romanos 7:1-6, el apóstol Pablo utiliza una analogía matrimonial de la vida cotidiana para explicar cómo el creyente es liberado de la tutela condenatoria de la Ley mosaica sin que la Ley pierda su santidad intrínseca.
                    
                    Pablo explica que la mujer casada está ligada por la ley a su marido mientras este vive; pero si el marido muere, queda legalmente libre de la ley del matrimonio y puede unirse a otro varón sin ser llamada adúltera (vv. 2-3).
                    
                    En el versículo 4, Pablo aplica la analogía teológica a nuestra redención: «Así también vosotros, hermanos míos, habéis muerto a la ley mediante el cuerpo de Cristo, para que seáis de otro, del que resucitó de los muertos, a fin de que llevemos fruto para Dios» (verdad que expresa en Gálatas 2:19-20: «Porque yo por la ley soy muerto para la ley, a fin de vivir para Dios. Con Cristo estoy juntamente crucificado»).
                    
                    ¿Quién murió para romper el vínculo legal de condenación? No fue la Ley la que murió (pues la ley moral de Dios es santa y eterna); ¡fuimos nosotros los que morimos a la ley cuando nuestro viejo hombre fue crucificado en el cuerpo de Cristo! La muerte rompe todos los contratos legales: la Ley ya no tiene jurisdicción penal sobre un cadáver que ha resucitado en una nueva esfera de vida.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    El propósito de esta muerte legal es bendito y esponsal: «para que seáis de otro, del que resucitó de los muertos». El creyente ya no está casado con el severo amo del legalismo que exigía perfección sin impartir vida; ahora está unido en matrimonio espiritual con el Señor Jesucristo resucitado.
                    
                    Bajo el viejo marido del legalismo, las pasiones pecaminosas eran azuzadas por la prohibición y producían «fruto para muerte» (v. 5); pero ahora, unidos a Cristo en amor, el versículo 6 proclama nuestra gloriosa libertad: «Pero ahora estamos libres de la ley, por haber muerto para aquella en que estábamos sujetos, de modo que sirvamos bajo el régimen nuevo del Espíritu y no bajo el régimen viejo de la letra».
                    
                    La motivación de la vida cristiana ya no es el terror al látigo de la condenación, sino el amor desbordante hacia el Esposo celestial que nos rescató.
                """,
                doctrinalThesis = "Por nuestra muerte en el cuerpo de Cristo hemos sido liberados de la jurisdicción penal de la ley para unirnos a Cristo resucitado y servir en la novedad del Espíritu.",
                exegeticalAnalysis = "La analogía conyugal de Romanos 7:1-4 demuestra que la muerte judicial con Cristo disuelve el reclamo de condenación de la ley, permitiendo una unión vivificante con el Señor resucitado."
            ),

            // DÍA 18
            createStudyDay(
                dayNumber = 18,
                title = "Día 18: La Función Reveladora de la Ley y la Lucha Interior",
                passagesSummary = "Romanos 7:7-25; Salmo 119:9-16",
                primaryBookId = 45,
                primaryChapter = 7,
                passages = listOf(seg(45, "Romanos", 7), seg(19, "Salmos", 119)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En Romanos 7:7-13, Pablo defiende la santidad inmaculada de la Ley de Dios frente a cualquier malentendido: «¿Qué diremos, pues? ¿La ley es pecado? ¡En ninguna manera! Pero yo no conocí el pecado sino por la ley; porque tampoco conociera la codicia, si la ley no dijera: No codiciarás» (v. 7). La Ley no es la causa del pecado; la Ley es el detector de santidad que expone la maldad oculta del corazón caído: «De manera que la ley a la verdad es santa, y el mandamiento santo, justo y bueno» (v. 12).
                    
                    A partir del versículo 14 y hasta el 25 se describe uno de los pasajes más debatidos y vivencialmente intensos de toda la Biblia: la angustiosa batalla que el alma regenerada experimenta frente al pecado remanente en su carne mortal:
                    «Porque sabemos que la ley es espiritual; mas yo soy carnal, vendido al pecado. Porque lo que hago, no lo entiendo; pues no hago lo que quiero, sino lo que aborrezco, eso hago... Y yo sé que en mí, esto es, en mi carne, no mora el bien; porque el querer el bien está en mí, pero no el hacerlo» (vv. 14-15, 18).
                    
                    Nótese que quien habla en estos versículos ama apasionadamente la ley de Dios: «Porque según el hombre interior, me deleito en la ley de Dios» (v. 22, con el mismo afecto piadoso del Salmo 119:9-16: «En mi corazón he guardado tus dichos, para no pecar contra ti... En tus estatutos me recrearé»). Un impío no regenerado jamás se deleita en la santa ley de Dios (Romanos 8:7). Solo un creyente cuya mente ha sido iluminada por la gracia siente el dolor agudo y desgarrador de no poder alcanzar la perfección intachable que su corazón anhela.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    Esta agonía culmina en el clamor conmovedor del versículo 24: «¡Miserable de mí! ¿Quién me librará de este cuerpo de muerte?». En la antigüedad militar, algunos tiranos ataban el cadáver descompuesto de un asesinado a la espalda del homicida hasta que la putrefacción lo matara. Pablo siente el peso agobiante de la naturaleza pecaminosa residual que lucha contra su alma.
                    
                    Pero el pasaje no se hunde en la desesperación; estalla de inmediato en la alabanza victoriosa del versículo 25: «¡Gracias doy a Dios, por Jesucristo Señor nuestro!». La victoria final no proviene de nuestras fuerzas humanas ni de disciplinas de autoayuda; proviene del Libertador soberano que rompe las cadenas del pecado y nos conduce al triunfo de Romanos 8.
                """,
                doctrinalThesis = "La Ley expone la pecaminosidad de la carne humana caída, mientras que la lucha interior del creyente resalta la necesidad de la gracia libertadora en Cristo Jesús.",
                exegeticalAnalysis = "El deleite en la ley según el hombre interior («synēdomai gar tōi nomōi tou Theou kata ton esō anthrōpon») en Romanos 7:22 autentica la experiencia del creyente regenerado que combate contra el pecado remanente."
            ),

            // DÍA 19
            createStudyDay(
                dayNumber = 19,
                title = "Día 19: Ninguna Condenación: La Ley del Espíritu de Vida",
                passagesSummary = "Romanos 8:1-8; Gálatas 5:16-18",
                primaryBookId = 45,
                primaryChapter = 8,
                passages = listOf(seg(45, "Romanos", 8), seg(48, "Gálatas", 5)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    Tras la tensa batalla de Romanos 7, el capítulo 8 se abre como el más resplandeciente amanecer de la teología cristiana. El versículo 1 proclama con majestad eterna: «Ahora, pues, ninguna condenación hay para los que están en Cristo Jesús».
                    
                    Analicemos el peso gramatical del texto griego original:
                    1. «Ninguna» (ouden): Está colocada al inicio de la frase en griego en posición de máximo énfasis enfático absoluto. No hay ni un solo gramo, ni una pizca de condenación penal reservada para el creyente.
                    2. «Condenación» (katakrima): Es un término estrictamente jurídico forense. No significa que el creyente nunca cometa errores o que Dios no lo discipline paternalmente; significa que el tribunal celestial jamás emitirá una sentencia judicial penal de culpabilidad contra él.
                    3. «Para los que están en Cristo Jesús»: La esfera de protección inexpugnable es nuestra unión vital y legal con el Redentor.
                    
                    En los versículos 2 al 4, Pablo explica la causa de esta inmunidad: «Porque la ley del Espíritu de vida en Cristo Jesús me ha librado de la ley del pecado y de la muerte». La ley de Moisés era débil «por la carne», pues no podía impartir poder para cumplirla; pero «Dios, enviando a su Hijo en semejanza de carne de pecado y a causa del pecado, condenó al pecado en la carne; para que la justicia de la ley se cumpliese en nosotros, que no andamos conforme a la carne, sino conforme al Espíritu».
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    La condenación que merecíamos fue ejecutada y consumada en el cuerpo de Cristo en el madero. La justicia de Dios quedó plenamente satisfecha. Por lo tanto, condenar a quien está en Cristo equivaldría a exigir un doble pago por la misma deuda, lo cual es imposible para el Juez santo de toda la tierra.
                    
                    Vivir en Cristo transforma nuestra mente: los que son de la carne piensan en las cosas de la carne; pero los que son del Espíritu, en las cosas del Espíritu (v. 5; como exhorta Gálatas 5:16-18: «Andad en el Espíritu, y no satisfagáis los deseos de la carne»). Ocuparse de la carne es muerte, pero ocuparse del Espíritu es vida y paz (v. 6). El creyente camina con la frente en alto, libre de culpa paralizante, amparado bajo el paraguas de la gracia eterna.
                """,
                doctrinalThesis = "La justificación en Cristo elimina de forma total y definitiva toda condenación judicial (katakrima) para el creyente, inaugurando el gobierno de la ley del Espíritu de vida.",
                exegeticalAnalysis = "La posición enfática inicial de «ouden katakrima» en Romanos 8:1 establece el principio inamovible de la exoneración penal absoluta de todo aquel que está unido judicialmente a Cristo."
            ),

            // DÍA 20
            createStudyDay(
                dayNumber = 20,
                title = "Día 20: El Espíritu de Adopción: ¡Abba, Padre!",
                passagesSummary = "Romanos 8:9-17; Gálatas 4:4-7",
                primaryBookId = 45,
                primaryChapter = 8,
                passages = listOf(seg(45, "Romanos", 8), seg(48, "Gálatas", 4)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En Romanos 8:14-17 se describe una de las doctrinas más conmovedoras de todo el Evangelio: la adopción filial del creyente en la familia de Dios: «Porque todos los que son guiados por el Espíritu de Dios, éstos son hijos de Dios. Pues no habéis recibido el espíritu de esclavitud para estar otra vez en temor, sino que habéis recibido el espíritu de adopción, por el cual clamamos: ¡Abba, Padre!».
                    
                    En el derecho romano de la época de Pablo, la adopción («hyiothesia») era un acto jurídico de inmenso poder: un patriarca adoptaba formalmente a un hijo, extinguiendo por completo todas las deudas de su vida anterior y otorgándole todos los derechos hereditarios y la plena dignidad de hijo biológico legítimo. El padre jamás podía repudiar o desheredar a un hijo adoptado legalmente.
                    
                    El Espíritu Santo clama en nuestro corazón con el término arameo íntimo que Jesús mismo utilizó en el huerto de Getsemaní: «¡Abba!» (Marcos 14:36; Gálatas 4:4-7). «Abba» era la palabra del lenguaje familiar cotidiano con que los niños hebreos se dirigían a sus padres terrenales con ternura, confianza absoluta y reverencia. El creyente ya no se acerca a Dios temblando como un esclavo teme a su amo con látigo; se acerca a los brazos amorosos de su Padre celestial.
                    
                    En el versículo 16 se confirma el ministerio del consuelo interior: «El Espíritu mismo da testimonio a nuestro espíritu, de que somos hijos de Dios».
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    La filiación divina nos otorga una dignidad cósmica incomparable en el versículo 17: «Y si hijos, también herederos; herederos de Dios y coherederos con Cristo, si es que padecemos juntamente con él, para que juntamente con él seamos glorificados».
                    
                    Ser coheredero con Cristo significa que compartiremos la misma herencia de gloria, victoria y vida eterna que el Padre concedió a su Hijo resucitado. Aunque en este mundo presente padezcamos sufrimientos y hostilidades por causa del Evangelio, la certeza inamovible de nuestra adopción nos recuerda que el dolor presente es transitorio, mientras que la gloria de la herencia es eterna e indestructible.
                """,
                doctrinalThesis = "Por medio del Espíritu de adopción (hyiothesia), el creyente es incorporado legal y afectivamente a la familia de Dios, convirtiéndose en heredero del Padre y coheredero con Cristo.",
                exegeticalAnalysis = "El término arameo «Abba» en Romanos 8:15 y el testimonio del Espíritu Santo garantizan una relación filial de íntima confianza y seguridad frente al temor servil de la condenación."
            ),

            // DÍA 21
            createStudyDay(
                dayNumber = 21,
                title = "Día 21: Los Dolores de Parto de la Creación y la Esperanza de la Redención",
                passagesSummary = "Romanos 8:18-25; 2 Corintios 4:16-18",
                primaryBookId = 45,
                primaryChapter = 8,
                passages = listOf(seg(45, "Romanos", 8), seg(47, "2 Corintios", 4)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En Romanos 8:18, el apóstol Pablo establece una ecuación espiritual que recalibra por completo nuestra perspectiva ante el dolor humano: «Pues tengo por cierto que las aflicciones del tiempo presente no son comparables con la gloria venidera que en nosotros ha de manifestarse» (en plena armonía con 2 Corintios 4:16-18: «Porque esta leve tribulación momentánea produce en nosotros un cada vez más excelente y eterno peso de gloria»). La palabra griega para «tengo por cierto» es «logizomai» (hago el cálculo numérico infalible): al poner en una balanza todo el sufrimiento del mundo entero y en el otro platillo el peso eterno de gloria, las aflicciones terrenales se desvanecen como una pluma.
                    
                    En los versículos 19 al 22, Pablo amplía el lente hacia el cosmos entero: la creación material fue sujetada a vanidad («mataiotēs», futilidad, descomposición, catástrofes) no por su propia voluntad, sino por causa de la maldición del pecado de Adán (Génesis 3:17-18).
                    
                    La naturaleza entera «gime a una, y a una está con dolores de parto hasta ahora» (v. 22). Los dolores de parto no son los estertores de la agonía de la muerte; son los dolores previos que anuncian que una nueva vida está a punto de nacer. La creación entera aguarda con anhelo ardiente («apokaradokia», estirando el cuello como quien mira fijamente al horizonte) la manifestación gloriosa de los hijos de Dios, cuando todo el orden creado será liberado de la esclavitud de la corrupción a la libertad gloriosa de los redimidos.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    En los versículos 23 al 25, Pablo añade que nosotros también, que tenemos las primicias del Espíritu, «gemimos dentro de nosotros mismos, esperando la adopción, la redención de nuestro cuerpo». La salvación de nuestra alma ya fue consumada en la justificación; pero la salvación de nuestro cuerpo físico aguarda la resurrección final en la venida del Señor.
                    
                    La esperanza cristiana no es una fantasía utópica terrenal; es la certeza paciente fundada en el poder de Dios. Mientras esperamos con perseverancia, los gemidos del tiempo presente no nos hunden en la amargura, porque sabemos que cada lágrima será enjugada en la renovación gloriosa de todas las cosas.
                """,
                doctrinalThesis = "La creación entera y los creyentes gimen en esperanza escatológica hacia la resurrección corporal y la redención del orden cósmico liberado de la corrupción del pecado.",
                exegeticalAnalysis = "El vocablo «apokaradokia» en Romanos 8:19 describe la expectativa intensa y anhelante de la creación aguardando la manifestación de la gloria de los santos en la consumación de los tiempos."
            ),

            // DÍA 22
            createStudyDay(
                dayNumber = 22,
                title = "Día 22: La Intercesión del Espíritu y la Providencia Soberana",
                passagesSummary = "Romanos 8:26-30; Efesios 1:3-12",
                primaryBookId = 45,
                primaryChapter = 8,
                passages = listOf(seg(45, "Romanos", 8), seg(49, "Efesios", 1)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En Romanos 8:26-27 se revela uno de los misterios más consoladores de la vida de oración: «Y de igual manera el Espíritu nos ayuda en nuestra debilidad; pues qué hemos de pedir como conviene, no lo sabemos, pero el Espíritu mismo intercede por nosotros con gemidos indecibles. Mas el que escudriña los corazones sabe cuál es la intención del Espíritu, porque conforme a la voluntad de Dios intercede por los santos».
                    
                    En medio del dolor intenso, la aflicción extrema o la confusión, con frecuencia el creyente se queda sin palabras, sin saber exactamente cómo orar o qué pedir. En ese abismo de fragilidad, el Espíritu Santo que habita en nuestro ser toma nuestra carga («synantilambanetai», se coloca al otro lado de la carga pesada para levantarla con nosotros) e intercede directamente ante el Padre con gemidos santos e inefables que concuerdan a la perfección con el consejo divino.
                    
                    De esta intercesión omnipotente emana la célebre afirmación del versículo 28: «Y sabemos que a los que aman a Dios, todas las cosas les ayudan a bien, esto es, a los que conforme a su propósito son llamados» (quienes hacen todas las cosas según el designio de su voluntad, Efesios 1:3-12). La frase «todas las cosas» («panta») no excluye las pruebas, las pérdidas o las aflicciones; en la soberanía de Dios, incluso las circunstancias más dolorosas son tejidas providencialmente para la madurez espiritual de los santos y la gloria del Creador.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    En los versículos 29 y 30 se expone lo que la teología sistemática ha denominado «La Cadena de Oro de la Salvación» (Ordo Salutis):
                    1. A los que antes conoció («proegnō», conoció en amor pactual desde la eternidad).
                    2. También los predestinó («proōrisen», determinó de antemano) para que fuesen hechos conformes a la imagen de su Hijo.
                    3. Y a los que predestinó, a éstos también llamó («ekalesen», llamamiento eficaz por el Evangelio).
                    4. Y a los que llamó, a éstos también justificó («edikaiōsen», declarados justos por la fe en Cristo).
                    5. Y a los que justificó, a éstos también glorificó («edoxasen»).
                    
                    Nótese con asombro que el último eslabón, «glorificó», está en tiempo aoristo en griego (tiempo pasado completado). En la mente y el decreto soberano de Dios, la glorificación final de cada uno de sus redimidos es tan certera e irrevocable como si ya hubiera ocurrido en el pasado. Ni un solo eslabón de esta cadena divina puede romperse jamás.
                """,
                doctrinalThesis = "La providencia soberana de Dios encamina todas las cosas para el bien de sus escogidos a través de la cadena irrompible de la gracia: desde la predestinación eterna hasta la glorificación final.",
                exegeticalAnalysis = "El uso del tiempo aoristo para «edoxasen» (glorificó) en Romanos 8:30 sella la certeza absoluta y escatológica de la salvación de todos aquellos que han sido justificados por la gracia de Dios."
            ),

            // DÍA 23
            createStudyDay(
                dayNumber = 23,
                title = "Día 23: Más que Vencedores: El Amor Inquebrantable de Dios",
                passagesSummary = "Romanos 8:31-39; Salmo 44:22",
                primaryBookId = 45,
                primaryChapter = 8,
                passages = listOf(seg(45, "Romanos", 8), seg(19, "Salmos", 44)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    El capítulo 8 de Romanos culmina con una de las piezas retóricas y doctrinales más majestuosas de toda la historia humana (vv. 31-39). Pablo actúa como un abogado en el tribunal cósmico y lanza una serie de cinco preguntas retóricas ante las cuales el universo entero debe guardar absoluto silencio:
                    
                    1. «¿Qué, pues, diremos a esto? Si Dios es por nosotros, ¿quién contra nosotros?» (v. 31). El Dios todopoderoso está a nuestro favor.
                    2. «El que no escatimó ni a su propio Hijo, sino que lo entregó por todos nosotros, ¿cómo no nos dará también con él todas las cosas?» (v. 32). Si nos dio el regalo de valor infinito (su Hijo en la cruz), ninguna bendición necesaria menor nos será negada.
                    3. «¿Quién acusará a los escogidos de Dios? Dios es el que justifica» (v. 33). Si el Juez supremo del universo ha firmado nuestra sentencia de justificación absolutoria, ninguna acusación de Satanás, del mundo o de nuestra conciencia culpable tiene validez legal.
                    4. «¿Quién es el que condenará? Cristo es el que murió; más aun, el que también resucitó, el que además está a la diestra de Dios, el que también intercede por nosotros» (v. 34).
                    5. «¿Quién nos separará del amor de Cristo? ¿Tribulación, o angustia, o persecución, o hambre, o desnudez, o peligro, o espada?» (v. 35; citando en el v. 36 el clamor de los mártires del Salmo 44:22: «Por causa de ti somos muertos todo el tiempo; somos contados como ovejas de matadero»).
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    La respuesta de Pablo en los versículos 37 al 39 es un himno de triunfo universal: «Antes, en todas estas cosas somos más que vencedores por medio de aquel que nos amó». La palabra para «más que vencedores» es un término acuñado por Pablo: «hypernikōmen» (súper-vencedores). No vencemos escapando de las pruebas en una burbuja de inmunidad física, sino triunfando en medio del sufrimiento porque las pruebas no pueden separarnos de Cristo, sino que afirman nuestra fe.
                    
                    Pablo cierra con una proclamación victoriosa: está convencido de que ni la muerte física, ni la vida presente, ni ángeles, ni principados demoníacos, ni lo presente, ni lo por venir, ni lo alto, ni lo profundo, ni ninguna otra cosa creada «nos podrá separar del amor de Dios, que es en Cristo Jesús Señor nuestro». La salvación no pende de nuestra débil mano; está amarrada en el amor omnipotente del Dios Trino.
                """,
                doctrinalThesis = "La justificación en Cristo confiere una seguridad eterna e inexpugnable, garantizando que ninguna fuerza cósmica o terrenal podrá separar a los redimidos del amor de Dios.",
                exegeticalAnalysis = "El término compuesto «hypernikōmen» en Romanos 8:37 y el desafío legal de los versículos 31 al 34 demuestran la imposibilidad ontológica y jurídica de revocar la salvación de los escogidos."
            ),

            // DÍA 24
            createStudyDay(
                dayNumber = 24,
                title = "Día 24: La Soberanía Divina en la Elección y la Fidelidad del Pacto",
                passagesSummary = "Romanos 9:1-24; Génesis 25:19-26; Éxodo 33:17-19",
                primaryBookId = 45,
                primaryChapter = 9,
                passages = listOf(seg(45, "Romanos", 9), seg(1, "Génesis", 25), seg(2, "Éxodo", 33)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En Romanos 9, 10 y 11, Pablo aborda un dilema teológico angustioso: si Jesucristo es el Mesías prometido y la salvación es tan gloriosa e inamovible (Romanos 8), ¿por qué la gran mayoría de la nación étnica de Israel rechazó el Evangelio y cayó en endurecimiento? ¿Ha fallado la promesa pactual de Dios dada a los patriarcas?
                    
                    La respuesta categórica de Pablo en Romanos 9:6 es: «No que la palabra de Dios haya fallado; porque no todos los que descienden de Israel son israelitas». Pablo demuestra mediante las Escrituras que la pertenencia al pueblo de Dios nunca dependió del linaje biológico natural, sino de la soberana elección de gracia:
                    1. En la descendencia de Abraham (vv. 7-9): No todos los hijos carnales fueron herederos; Ismael fue despedido, pero «en Isaac te será llamada descendencia».
                    2. En los gemelos Jacob y Esaú (vv. 10-13; Génesis 25:19-26): Nacidos del mismo padre y de la misma madre en el mismo parto, antes de que hubiesen nacido ni hecho bien ni mal («para que el propósito de Dios conforme a la elección permaneciese, no por las obras sino por el que llama»), Dios declaró a Rebeca: «El mayor servirá al menor».
                    
                    Ante la objeción humana de si hay injusticia en Dios por elegir soberanamente, Pablo responde en el versículo 15 citando la revelación dada a Moisés en Éxodo 33:19: «Tendré misericordia del que yo tenga misericordia, y me compadeceré del que yo me compadezca. Así que no depende del que quiere, ni del que corre, sino de Dios que tiene misericordia» (vv. 15-16).
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    La salvación no es un derecho que el pecador pueda exigir a Dios en justicia (si Dios nos diera estricta justicia a todos, todos seríamos condenados al infierno). La salvación es misericordia soberana e inmerecida otorgada según el puro afecto de su voluntad (Efesios 1:5).
                    
                    Dios es el soberano Alfarero que tiene potestad sobre el barro caído para hacer vasos de misericordia preparados de antemano para gloria (vv. 21-23), no solo de entre los judíos, sino también de entre los gentiles. Lejos de alimentar la vanagloria, la doctrina de la elección derriba el orgullo humano, suscita la más profunda humildad y despierta un asombro reverente ante la gracia inagotable del Señor.
                """,
                doctrinalThesis = "La fidelidad de la palabra de Dios se manifiesta en su elección soberana de gracia, la cual no depende de méritos, voluntades o linajes humanos, sino de su libre misericordia.",
                exegeticalAnalysis = "La afirmación de Romanos 9:16 («ou tou thelontos oude tou trechontos», no del que quiere ni del que corre) fundamenta el carácter monergístico de la gracia salvadora frente a cualquier sinergismo humanista."
            ),

            // DÍA 25
            createStudyDay(
                dayNumber = 25,
                title = "Día 25: El Fin de la Ley: Confesar con la Boca y Creer en el Corazón",
                passagesSummary = "Romanos 10:1-17; Deuteronomio 30:11-14; Joel 2:32",
                primaryBookId = 45,
                primaryChapter = 10,
                passages = listOf(seg(45, "Romanos", 10), seg(5, "Deuteronomio", 30), seg(29, "Joel", 2)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En Romanos 10:1-4, Pablo expresa su ferviente anhelo pastoral por la salvación de sus compatriotas israelitas: «Hermanos, ciertamente el anhelo de mi corazón, y mi oración a Dios por Israel, es para salvación. Porque yo les doy testimonio de que tienen celo de Dios, pero no conforme a ciencia». El celo religioso sincero pero equivocado es mortal si desconoce la gracia. Israel tropezó porque «ignorando la justicia de Dios, y procurando establecer la suya propia, no se han sujetado a la justicia de Dios» (v. 3).
                    
                    En el versículo 4 resuena la declaración cumbre: «porque el fin de la ley es Cristo, para justicia a todo aquel que cree». La palabra griega para fin es «telos»: significa tanto el término o conclusión legal como la meta y culminación consumada. Cristo es el propósito hacia el cual apuntaba toda la Ley.
                    
                    En los versículos 8 al 10, Pablo expone la accesibilidad gloriosa de la justicia que es por fe, citando Deuteronomio 30:
                    «Cerca de ti está la palabra, en tu boca y en tu corazón. Esta es la palabra de fe que predicamos: que si confesares con tu boca que Jesús es el Señor, y creyeres en tu corazón que Dios le levantó de los muertos, serás salvo. Porque con el corazón se cree para justicia, pero con la boca se confiesa para salvación».
                    
                    Confesar que «Jesús es el Señor» («Kyrios Iēsous») era una confesión revolucionaria que costaba la vida en el primer siglo: implicaba destronar al César romano y reconocer a Jesús como el único Dios Todopoderoso y Soberano legítimo de la vida. Y creer en la resurrección en el corazón es abrazar la victoria de la justificación consumada.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    La promesa es universal e ilimitada: «porque todo aquel que invocare el nombre del Señor, será salvo» (v. 13, citando Joel 2:32). Pero esta soberanía divina no anula la responsabilidad humana de la proclamación misionera. 
                    
                    En los versículos 14 y 15, Pablo construye la cadena misionera indispensable: «¿Cómo, pues, invocarán a aquel en el cual no han creído? ¿Y cómo creerán en aquel de quien no han oído? ¿Y cómo oirán sin haber quien les predique? ¿Y cómo predicarán si no fueren enviados?». La fe viene por el oír, y el oír por la Palabra de Dios (v. 17). La soberanía de Dios en la salvación no desalienta la evangelización; es el combustible y la garantía inamovible que asegura que la predicación de la Palabra dará fruto eterno en aquellos que han de ser salvos.
                """,
                doctrinalThesis = "Cristo es el fin y cumplimiento de la ley (telos nomou), ofreciendo salvación universal a todo aquel que confiesa su señorío y cree de corazón en su resurrección mediante la predicación del Evangelio.",
                exegeticalAnalysis = "El término «telos» en Romanos 10:4 y la confesión bautismal «Kyrios Iēsous» (v. 9) definen la salvación como la rendición personal al señorío divino de Cristo mediante la fe no fingida."
            ),

            // DÍA 26
            createStudyDay(
                dayNumber = 26,
                title = "Día 26: El Misterio de Israel, el Olivo Silvestre y la Doxología Final",
                passagesSummary = "Romanos 11:1-36; Jeremías 11:16-17",
                primaryBookId = 45,
                primaryChapter = 11,
                passages = listOf(seg(45, "Romanos", 11), seg(24, "Jeremías", 11)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En Romanos 11, Pablo culmina su tratado teológico respondiendo a la pregunta sobre el destino final de Israel: «¿Ha desechado Dios a su pueblo? ¡En ninguna manera! Porque también yo soy israelita... No ha desechado Dios a su pueblo, al cual desde antes conoció» (vv. 1-2). 
                    
                    Pablo demuestra que siempre ha existido un «remanente escogido por gracia» («leimma kat' eklogēn charitos», v. 5), tal como en los días oscuros de Elías cuando Dios se reservó a siete mil hombres que no doblaron sus rodillas ante Baal.
                    
                    Para advertir a los creyentes gentiles contra el orgullo antisemita o la soberbia espiritual, Pablo utiliza la parábola del Olivo (vv. 16-24; figura pactual evocada ya en Jeremías 11:16-17):
                    - La raíz santa del olivo son los patriarcas y las promesas de pacto dadas a Abraham.
                    - Algunas de las ramas naturales (judíos incrédulos) fueron desgajadas a causa de su incredulidad.
                    - Los gentiles son ramas de un «olivo silvestre» («agrielaios») que fueron injertadas milagrosamente contra naturaleza en el buen olivo, participando de la rica savia de las promesas.
                    Pablo amonesta con santa severidad: «no te jactes contra las ramas... porque por su incredulidad fueron desgajadas, y tú por la fe estás en pie. No te ensoberbezcas, sino teme» (vv. 18, 20). Si Dios desgajó las ramas naturales por incredulidad, con cuánta mayor razón puede desgajar al gentil soberbio; y Dios es poderoso para injertar de nuevo a las ramas naturales en su propio olivo cuando se vuelvan a Cristo en fe (v. 23).
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    En el versículo 25, Pablo revela el misterio: el endurecimiento parcial de Israel durará «hasta que haya entrado la plenitud de los gentiles; y luego todo Israel será salvo» (vv. 25-26), conforme al pacto irrevocable de Dios: «Porque irrevocables son los dones y el llamamiento de Dios» (v. 29).
                    
                    Al contemplar la insondable sabiduría del plan redentor de Dios, que encerró a todos bajo desobediencia para tener misericordia de todos (v. 32), la pluma de Pablo estalla en la doxología más sublime de todas las Escrituras (vv. 33-36):
                    «¡Oh profundidad de las riquezas de la sabiduría y de la ciencia de Dios! ¡Cuán insondables son sus juicios, e inescrutables sus caminos!... Porque de él, y por él, y para él, son todas las cosas. A él sea la gloria por los siglos. Amén».
                    
                    Toda la teología bíblica culmina en doxología: la gloria absoluta, exclusiva y eterna del Dios Trino.
                """,
                doctrinalThesis = "Dios permanece fiel a su pacto histórico con Israel, injertando a los gentiles por pura gracia en el olivo de las promesas y guiando la historia hacia la gloria de su Nombre.",
                exegeticalAnalysis = "La metáfora del olivo en Romanos 11:17-24 y la doxología trinitaria de los versículos 33 al 36 («ex autou, di' autou, eis auton») proclaman que Dios es el origen, sustento y meta final de todas las cosas."
            ),

            // DÍA 27
            createStudyDay(
                dayNumber = 27,
                title = "Día 27: Sacrificio Vivo: La Renovación de la Mente y el Culto Racional",
                passagesSummary = "Romanos 12:1-8; 1 Pedro 2:4-5",
                primaryBookId = 45,
                primaryChapter = 12,
                passages = listOf(seg(45, "Romanos", 12), seg(60, "1 Pedro", 2)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En Romanos 12:1 se produce la gran transición de la epístola: habiendo expuesto durante once capítulos la majestuosa doctrina de la gracia soberana de Dios, Pablo inicia la sección práctica de la vida cristiana con un ruego pastoral apasionado: «Así que, hermanos, os ruego por las misericordias de Dios, que presentéis vuestros cuerpos en sacrificio vivo, santo, agradable a Dios, que es vuestro culto racional».
                    
                    La ética cristiana no nace de la imposición de una lista fría de reglas legalistas; nace «por las misericordias de Dios» («oiktirmoi tou Theou»): es la respuesta espontánea de amor y asombro ante todo lo que Cristo hizo por nosotros en los capítulos 1 al 11.
                    
                    Pablo utiliza el vocabulario sacrificial del templo del Antiguo Testamento pero con un giro revolucionario (en sintonía con el sacerdocio santo de 1 Pedro 2:4-5 para ofrecer sacrificios espirituales aceptables a Dios por medio de Jesucristo):
                    - En el Antiguo Pacto, los sacrificios eran animales muertos que se quemaban en el altar.
                    - En el Nuevo Pacto, nuestro sacrificio es «vivo» («thysian zōsan»): una vida corporal consagrada enteramente a Dios día tras día.
                    - Este es nuestro «culto racional» («logikē latreia»): un servicio espiritual consciente, deliberado y coherente con la verdad del Evangelio.
                    
                    En el versículo 2 se prescribe la estrategia de transformación: «No os conforméis a este siglo, sino transformaos por medio de la renovación de vuestro entendimiento, para que comprobéis cuál sea la buena voluntad de Dios, agradable y perfecta». El verbo conforméis es «syschēmatizō» (no adoptar el molde exterior transitorio y caído del mundo); el verbo transformaos es «metamorphoō» (una metamorfosis radical interior obrada por la Palabra de Dios).
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    La mente renovada por el Evangelio produce una verdadera sobriedad personal en el versículo 3: nadie debe tener más alto concepto de sí que el que debe tener, sino pensar de sí con templanza.
                    
                    A partir del versículo 4, Pablo aplica esta mente renovada a la vida de la iglesia local: somos un solo cuerpo en Cristo con diversidad de dones espirituales (profecía, servicio, enseñanza, exhortación, dar con liberalidad, presidir con solicitud, hacer misericordia con alegría). La consagración individual a Dios se manifiesta inevitablemente en el servicio humilde y desinteresado a los hermanos en la congregación.
                """,
                doctrinalThesis = "La respuesta debida a la gracia soberana es la consagración total del cuerpo como sacrificio vivo y la renovación de la mente para discernir y obedecer la voluntad de Dios.",
                exegeticalAnalysis = "El término «logikē latreia» en Romanos 12:1 y la ordenanza de «metamorphoō» (v. 2) definen la adoración cristiana como una vida éticamente transformada por la verdad de la Palabra."
            ),

            // DÍA 28
            createStudyDay(
                dayNumber = 28,
                title = "Día 28: El Amor sin Fingimiento y la Ética del Reino",
                passagesSummary = "Romanos 12:9-21; Mateo 5:43-48",
                primaryBookId = 45,
                primaryChapter = 12,
                passages = listOf(seg(45, "Romanos", 12), seg(40, "Mateo", 5)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En Romanos 12:9-21, el apóstol Pablo bosqueja el retrato más puro y exigente de la ética cristiana en las relaciones humanas. El versículo 9 abre con el principio rector: «El amor sea sin fingimiento. Aborreced lo malo, seguid lo bueno».
                    
                    La frase «sin fingimiento» traduce el vocablo griego «anypokritos»: desprovisto de máscara teatral, sin hipocresía, genuino y transparente. El amor bíblico no es un sentimentalismo complaciente que tolera el mal; aborrece la iniquidad y se aferra a la santidad.
                    
                    Pablo desgrana una serie de imperativos de asombrosa profundidad práctica:
                    - Amor fraternal entrañable («philostorgoi», v. 10), prefiriendo en honra a los demás antes que a uno mismo.
                    - Fervor espiritual continuo: «en lo que requiere diligencia, no perezosos; fervientes en espíritu [zēontes, hirviendo en el Espíritu], sirviendo al Señor» (v. 11).
                    - Resiliencia en la prueba: «gozosos en la esperanza; sufridos en la tribulación; constantes en la oración» (v. 12).
                    - Empatía profunda: «Gosaos con los que se gozan; llorad con los que lloran» (v. 15).
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    La prueba de fuego del amor cristiano se expone en los versículos 17 al 21, donde Pablo reitera la enseñanza de Jesús en el Sermón del Monte (Mateo 5:43-48: «Amad a vuestros enemigos, bendecid a los que os maldicen»):
                    «No paguéis a nadie mal por mal... Si es posible, en cuanto dependa de vosotros, estad en paz con todos los hombres. No os vengáis vosotros mismos, amados míos, sino dejad lugar a la ira de Dios; porque escrito está: Mía es la venganza, yo pagaré, dice el Señor. Así que, si tu enemigo tuviere hambre, dale de comer; si tuviere sed, dale de beber... No seas vencido de lo malo, sino vence con el bien el mal».
                    
                    Vengarse personalmente es un acto de desconfianza e insubordinación donde el ser humano intenta usurpar el trono del Juez supremo de la tierra. El cristiano confía en que Dios ejecutará perfecta justicia a su debido tiempo. Mientras tanto, desarma la hostilidad del enemigo con la generosidad y el perdón de Cristo. Esta es la victoria del Reino de los Cielos sobre el odio del mundo.
                """,
                doctrinalThesis = "El amor cristiano genuino (anypokritos) se manifiesta en la humildad fraternal, el fervor espiritual y la renuncia a la venganza personal, venciendo el mal con el bien.",
                exegeticalAnalysis = "El término «anypokritos» en Romanos 12:9 y la prohibición de la venganza personal (v. 19) encarnan la ética de la cruz enseñada por Jesús frente a la hostilidad del mundo."
            ),

            // DÍA 29
            createStudyDay(
                dayNumber = 29,
                title = "Día 29: La Autoridad Civil y el Cumplimiento de la Ley en el Amor",
                passagesSummary = "Romanos 13:1-14; 1 Pedro 2:13-17",
                primaryBookId = 45,
                primaryChapter = 13,
                passages = listOf(seg(45, "Romanos", 13), seg(60, "1 Pedro", 2)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En Romanos 13:1-7 (en plena concordancia con la exhortación apostólica de 1 Pedro 2:13-17), Pablo aborda la relación del creyente con el gobierno secular en el corazón mismo del Imperio Romano gobernado por las autoridades civiles paganas: «Sométase toda persona a las autoridades superiores; porque no hay autoridad sino de parte de Dios, y las que hay, por Dios han sido establecidas».
                    
                    Pablo enseña que el gobierno civil es una institución de la gracia común divina diseñada para refrenar el caos, castigar el malhechor y promover el orden público pacífico: el magistrado es «servidor de Dios [diakonos Theou] para tu bien... pues no en vano lleva la espada» (v. 4). Por esta razón, el cristiano cumple sus deberes cívicos con integridad: paga los tributos, honra a quien merece honra y ora por sus gobernantes (1 Timoteo 2:1-2), reconociendo que la soberanía última pertenece a Dios (y obedeciendo a Dios antes que a los hombres cuando una ley terrenal demanda pecar directamente contra el Creador, Hechos 5:29).
                    
                    En los versículos 8 al 10, Pablo regresa a la ley suprema del creyente: «No debáis a nadie nada, sino el amaros unos a otros; porque el que ama al prójimo, ha cumplido la ley». El amor no hace mal al prójimo; por tanto, el amor («agapē») es el cumplimiento pleno y glorioso de los mandamientos.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    El capítulo concluye con una urgente advertencia escatológica en los versículos 11 al 14: «Y esto, conociendo el tiempo, que es ya hora de levantarnos del sueño; porque ahora está más cerca de nosotros nuestra salvación que cuando creímos. La noche está avanzada, y se acerca el día. Desechemos, pues, las obras de las tinieblas, y vistámonos las armas de la luz».
                    
                    El versículo 14 contiene la exhortación que provocó la histórica conversión de Agustín de Hipona en el año 386 d.C.: «sino vestíos del Señor Jesucristo, y no proveáis para los deseos de la carne». Vestirse de Cristo es vivir envueltos en su santidad, imitando su carácter y descansando en su justicia mientras aguardamos el amanecer glorioso de su venida.
                """,
                doctrinalThesis = "El cristiano vive como ciudadano íntegro sometido a las autoridades ordenadas por Dios, cumpliendo la ley moral a través del amor y vistiendo el carácter santo de Cristo.",
                exegeticalAnalysis = "La designación del magistrado civil como «diakonos Theou» en Romanos 13:4 establece la legitimidad del orden civil derivado de la providencia divina, culminando en el mandato escatológico de «vestíos del Señor Jesucristo» (v. 14)."
            ),

            // DÍA 30
            createStudyDay(
                dayNumber = 30,
                title = "Día 30: Los Débiles y los Fuertes: La Edificación Mutua y la Doxología Eterna",
                passagesSummary = "Romanos 14:1-19; Romanos 15:1-7; Romanos 16:25-27",
                primaryBookId = 45,
                primaryChapter = 14,
                passages = listOf(seg(45, "Romanos", 14), seg(45, "Romanos", 15), seg(45, "Romanos", 16)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En Romanos 14 y 15, el apóstol Pablo aborda las tensiones prácticas que amenazaban la comunión de la iglesia en Roma entre creyentes de trasfondo judío («los débiles en la fe», con escrúpulos sobre comidas ceremoniales y observancia de días festivos) y creyentes gentiles («los fuertes en la fe», que comprendían su libertad en Cristo pero tendían a despreciar a los escrupulosos).
                    
                    Pablo establece principios pastorales de oro para las cosas secundarias («adiáphora»):
                    1. Ninguno Juzgue ni Menosprecie al Hermano (14:1-4): Cada uno vive y muere para el Señor; todos compareceremos ante el tribunal de Cristo.
                    2. La Prioridad del Reino de Dios (14:17): «porque el reino de Dios no es comida ni bebida, sino justicia, paz y gozo en el Espíritu Santo».
                    3. No Ser Tropiezo por Causa de la Libertad (14:20-21): La libertad cristiana se sujeta voluntariamente al amor por el hermano débil; herir la conciencia del hermano por defender un derecho culinario es pecar contra el amor de Cristo.
                    4. Seguir el Ejemplo de Cristo (15:1-3): «Cada uno de nosotros agrade a su prójimo en lo que es bueno, para edificación. Porque ni aun Cristo se agradó a sí mismo».
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    En Romanos 15:7 se sintetiza el corazón eclesiológico de la epístola: «Por tanto, recibíos los unos a los otros, como también Cristo nos recibió, para gloria de Dios». Cristo no nos puso condiciones humanas para recibirnos en su gracia; acogió a judíos y gentiles con amor infinito para que unánimes, a una voz, glorifiquemos al Dios y Padre de nuestro Señor Jesucristo (15:6).
                    
                    Y en Romanos 16:25-27, el apóstol sella este monumental monumento teológico con una solemne bendición apostólica:
                    «Y al que puede confirmaros según mi evangelio y la predicación de Jesucristo... según el mandamiento del Dios eterno, se ha dado a conocer a todas las gentes para que obedezcan a la fe, al único y sabio Dios, sea gloria mediante Jesucristo para siempre. Amén».
                    
                    Romanos nos deja postrados en adoración: somos pecadores rescatados de la condenación eterna, justificados por la sola gracia mediante la fe en la sangre del Cordero, habitados por el Espíritu de vida, amados con un amor del cual nada ni nadie podrá separarnos, y comisionados para vivir una vida de santidad y amor que proclame la gloria del Dios eterno por los siglos de los siglos.
                """,
                doctrinalThesis = "La madurez cristiana se evidencia en acoger con amor al hermano débil sin juzgarlo, anteponiendo la edificación del Cuerpo de Cristo para que Dios sea glorificado universalmente.",
                exegeticalAnalysis = "La ordenanza de Romanos 15:7 («proslambanesthe allēlous», recibíos mutuamente) fundada en la acogida de Cristo corona la armonía eclesiológica de toda la epístola a los Romanos."
            )
        )
    }
}
