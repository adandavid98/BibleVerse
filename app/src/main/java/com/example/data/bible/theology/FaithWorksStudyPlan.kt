package com.example.data.bible.theology

import com.example.data.bible.ReadingPlanDay
import com.example.data.bible.theology.TheologyStudyHelper.createStudyDay
import com.example.data.bible.theology.TheologyStudyHelper.seg

/**
 * Estudio Bíblico Teológico y Exegético: Fe y Obras: Gracia y Fruto (7 Días).
 * Resolución exegética y sistemática de la Sola Fide y la armonía apostólica entre Pablo y Santiago.
 */
object FaithWorksStudyPlan {

    val days: List<ReadingPlanDay> by lazy {
        listOf(
            // DÍA 1
            createStudyDay(
                dayNumber = 1,
                title = "Día 1: Incapacidad Humana y Salvación Solo por Gracia",
                passagesSummary = "Efesios 2:1-10; Romanos 3:21-28",
                primaryBookId = 49,
                primaryChapter = 2,
                passages = listOf(seg(49, "Efesios", 2), seg(45, "Romanos", 3)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    El punto de partida ineludible para comprender la relación bíblica entre la fe y las obras radica en el diagnóstico que las Escrituras hacen de la condición humana caída. En Efesios 2:1, el apóstol Pablo declara con crudeza: «Y él os dio vida a vosotros, cuando estabais muertos en vuestros delitos y pecados» (en griego: nekrous tois paraptōmasin). El texto no dice que el pecador estaba herido, débil o moralmente enfermo; afirma tajantemente que estaba espiritualmente muerto. Un cadáver espiritual es absolutamente incapaz de generar méritos ante un Dios infinitamente santo.
                    
                    Por esta causa, la salvación brota de una iniciativa puramente divina y unilateral: «Porque por gracia sois salvos por medio de la fe; y esto no de vosotros, pues es don de Dios; no por obras, para que nadie se gloríe» (Ef 2:8-9). La palabra griega para don es «dōron»: un obsequio inmerecido e irrevocable. La fe no es una obra meritoria que nosotros le ofrecemos a Dios para ganarnos el cielo, sino la mano vacía del mendigo espiritual que recibe el regalo de la justicia imputada de Cristo.
                    
                    Sin embargo, el pasaje no culmina en el versículo 9. El versículo 10 añade la meta inquebrantable de este rescate: «Porque somos hechura suya, creados en Cristo Jesús para buenas obras, las cuales Dios preparó de antemano para que anduviésemos en ellas». Las obras no son la raíz ni la causa de la salvación; son su fruto deliberado, su diseño original y su consecuencia inevitable.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    La doctrina de la justificación únicamente por la gracia mediante la fe (Sola Gratia, Sola Fide) es el baluarte que preserva la gloria exclusiva de Cristo en la redención. Si el ser humano pudiera contribuir aunque fuese con un ápice de mérito propio para ser aceptado ante Dios, la cruz de Cristo perdería su suficiencia absoluta (Gálatas 2:21).
                    
                    Dios no justifica al hombre porque vea en él bondad anticipada, sino porque viste al pecador arrepentido con el manto de la justicia perfecta de su Hijo. Las buenas obras que fluyen después de la salvación no son un intento temeroso de pagar una deuda impagable, sino un acto gozoso de amor, alabanza y gratitud hacia Aquel que nos amó y se entregó a Sí mismo por nosotros.
                """,
                doctrinalThesis = "La justificación del pecador es un acto judicial gratuito de Dios recibido únicamente por la fe en Cristo, sin cooperación de méritos u obras humanas previas.",
                exegeticalAnalysis = "El término «nekrous» (muertos) en Efesios 2:1 excluye cualquier capacidad sinérgica del hombre natural para justificarse, convirtiendo las buenas obras en el fruto previsto de la gracia y no en su causa."
            ),

            // DÍA 2
            createStudyDay(
                dayNumber = 2,
                title = "Día 2: Abraham: Justificado por la Fe antes de la Ley",
                passagesSummary = "Génesis 15:1-6; Romanos 4:1-12",
                primaryBookId = 45,
                primaryChapter = 4,
                passages = listOf(seg(45, "Romanos", 4), seg(1, "Génesis", 15)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En Romanos 4, el apóstol Pablo acude al ejemplo cumbre del judaísmo para demostrar la universalidad de la justificación por la fe: Abraham, el patriarca de la promesa. Los maestros legalistas del siglo I sostenían que Abraham había sido aprobado por Dios debido a su obediencia intachable y al pacto de la circuncisión. Pablo refuta esta idea acudiendo con precisión quirúrgica a la cronología del Génesis.
                    
                    En Génesis 15:6, cuando Abraham contempló las estrellas y creyó en la promesa de una descendencia incontable, Moisés escribió: «Y creyó a Jehová, y le fue contado por justicia». El vocablo hebreo traducido como «contado» es «jashav» (en el griego de Romanos 4:3: «elogisthē», del verbo logizomai). Se trata de un término comercial y contable: significa acreditar o registrar en la cuenta legal de una persona una riqueza que no le pertenecía por esfuerzo propio.
                    
                    ¿Cuándo fue justificado Abraham en el registro divino? En Génesis 15, cuando aún era incircunciso y más de 400 años antes de que la Ley mosaica fuese dada en el monte Sinaí. La circuncisión vino catorce años más tarde (Génesis 17) no como medio de justificación, sino como «señal y sello de la justicia de la fe que tuvo estando aún incircunciso» (Ro 4:11).
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    La justificación de Abraham establece el paradigma inmutable de cómo cualquier ser humano halla aceptación ante el Creador en cualquier época de la historia: por fe en la promesa de Dios. Abraham miró hacia adelante con fe al Mesías prometido (Juan 8:56), y nosotros miramos hacia atrás con fe al Mesías crucificado y resucitado.
                    
                    El rito externo de la circuncisión —al igual que el bautismo o los sacramentos en el Nuevo Pacto— es una señal visible de una realidad espiritual invisible ya consumada en el corazón. Pretender que las ceremonias o las obras externas justifican al alma ante Dios es invertir el orden divino: primero viene la fe que recibe la justicia de Dios, y luego la señal que atestigua la obra de su gracia.
                """,
                doctrinalThesis = "La imputación legal de justicia a Abraham antes de la circuncisión demuestra que la salvación siempre ha sido por gracia mediante la fe en la promesa divina.",
                exegeticalAnalysis = "El uso contable de «logizomai» en Romanos 4:3 prueba que la justicia de Dios se acredita en el expediente del creyente como un acto de gracia pura y no como un salario adeudado por las obras (Ro 4:4)."
            ),

            // DÍA 3
            createStudyDay(
                dayNumber = 3,
                title = "Día 3: La Cruz y la Paz con Dios: El Fin de la Autosuficiencia",
                passagesSummary = "Romanos 5:1-11; Gálatas 2:15-21",
                primaryBookId = 45,
                primaryChapter = 5,
                passages = listOf(seg(45, "Romanos", 5), seg(48, "Gálatas", 2)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    Romanos 5:1 proclama el triunfo inconmovible de la soteriología apostólica: «Justificados, pues, por la fe, tenemos paz para con Dios por medio de nuestro Señor Jesucristo». La forma verbal utilizada por Pablo para «justificados» es un participio aoristo pasivo («Dikaiōthentes»): describe una acción judicial completada y definitiva en el pasado que produce un estado permanente de reconciliación en el presente.
                    
                    La «paz» (eirēnē / shalom) de la que habla el texto no es una mera tranquilidad emocional pasajera o la ausencia temporal de ansiedad; es el cese legal y definitivo de la hostilidad cósmica entre la santa justicia de Dios y el pecador culpable. Cristo pagó la sentencia completa en la cruz, absorbiendo la ira divina que merecíamos.
                    
                    Por esta razón, en Gálatas 2:16, Pablo enfatiza que «sabiendo que el hombre no es justificado por las obras de la ley, sino por la fe de Jesucristo, nosotros también hemos creído en Jesucristo, para ser justificados por la fe de Cristo y no por las obras de la ley, por cuanto por las obras de la ley nadie será justificado». El legalismo pretende que la muerte de Cristo fue insuficiente e intenta añadir méritos humanos; por eso Pablo concluye con santa vehemencia en Gálatas 2:21: «No desecho la gracia de Dios; pues si por la ley fuese la justicia, entonces por demás murió Cristo».
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    La vida cristiana victoriosa no nace de la autocomplacencia religiosa ni del miedo a la condenación, sino de la seguridad de estar plenamente aceptados en el Amado (Efesios 1:6). Cuando el creyente comprende que ya no está bajo condenación (Romanos 8:1) y que su paz con Dios está sellada con la sangre del Cordero, su motivación cambia de raíz.
                    
                    Ya no obedece a Dios para ganarse su favor (lo cual es esclavitud legalista), sino porque ya tiene su favor pleno y eterno en Cristo (lo cual es libertad filial). La fe viva produce un corazón enamorado de la santidad: «Con Cristo estoy juntamente crucificado, y ya no vivo yo, mas vive Cristo en mí; y lo que ahora vivo en la carne, lo vivo en la fe del Hijo de Dios, el cual me amó y se entregó a sí mismo por mí» (Gálatas 2:20).
                """,
                doctrinalThesis = "La justificación confiere un estado legal inalterable de paz con Dios, erradicando para siempre la culpa del creyente y la ilusión de la autosuficiencia humana.",
                exegeticalAnalysis = "El participio aoristo «Dikaiōthentes» en Romanos 5:1 subraya que la justificación es una declaración definitiva del tribunal de Dios que no admite grados ni puede perderse por fluctuaciones del desempeño humano."
            ),

            // DÍA 4
            createStudyDay(
                dayNumber = 4,
                title = "Día 4: La Epístola de Santiago: El Contexto Exegético de la Fe Muerta",
                passagesSummary = "Santiago 2:14-26; 1 Juan 3:16-18",
                primaryBookId = 59,
                primaryChapter = 2,
                passages = listOf(seg(59, "Santiago", 2), seg(62, "1 Juan", 3)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    Para comprender con rigor la epístola de Santiago es imprescindible situarse en su entorno histórico y pastoral. Santiago escribe a las doce tribus que están en la dispersión (Stg 1:1): comunidades de creyentes judeocristianos donde se había infiltrado una peligrosa distorsión de la gracia conocida como antinomianismo o laxitud moral. 
                    
                    Muchos miembros de la comunidad profesaban ser salvos por la fe mientras mostraban un desprecio escandaloso hacia los pobres, acepción de personas y una completa indolencia ante el sufrimiento ajeno. En este ambiente degradado, Santiago lanza su interpelación en Santiago 2:14: «Hermanos míos, ¿de qué aprovechará si alguno dice que tiene fe, y no tiene obras? ¿Podrá la fe salvarle?».
                    
                    Nótese con minuciosa precisión el matiz del texto griego: Santiago dice «si alguno dice que tiene fe» (ean pistin legē tis echein). El autor no cuestiona la verdadera fe, sino la mera pretensión verbal de tener fe. Además, en el griego original, la pregunta final lleva el artículo definido: «mē dynatai hē pistis sōsai auton?» («¿Acaso podrá esa clase de fe salvarle?»). La respuesta categórica es no.
                    
                    Santiago demuestra que una fe puramente intelectual es idéntica a la religión de los demonios: «Tú crees que Dios es uno; bien haces. También los demonios creen, y tiemblan» (Stg 2:19). Los demonios poseen una teología monoteísta impecable, pero están irremediablemente perdidos porque su asentimiento mental carece de regeneración, sumisión y amor.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    Santiago no está combatiendo la justificación por la fe proclamada por Pablo; está combatiendo la hipocresía de una confesión de labios que no transforma el corazón. Una fe que no produce compasión hacia el hermano desnudo o hambriento (Stg 2:15-16) no es una fe bíblica; es un cadáver espiritual: «Porque como el cuerpo sin espíritu está muerto, así también la fe sin obras está muerta» (Stg 2:26).
                    
                    Cristo enseñó exactamente este mismo principio en el Sermón del Monte: «No todo el que me dice: Señor, Señor, entrará en el reino de los cielos, sino el que hace la voluntad de mi Padre que está en los cielos» (Mateo 7:21). Las obras son el pulso vital que evidencia que el alma ha resucitado de entre los muertos por la gracia de Dios.
                """,
                doctrinalThesis = "La profesión verbal de fe sin transformación moral ni compasión práctica es una fe espuria y muerta, incapaz de salvar a nadie.",
                exegeticalAnalysis = "La expresión «hē pistis» (esa fe nominal) en Santiago 2:14 delimita el objeto de ataque del apóstol: no la fe genuina salvadora, sino la vana pretensión intelectual que imita la fe de los demonios."
            ),

            // DÍA 5
            createStudyDay(
                dayNumber = 5,
                title = "Día 5: La Armonía Apostólica: Pablo y Santiago Hablan con una Sola Voz",
                passagesSummary = "Romanos 3:28; Santiago 2:21-25; Génesis 22:1-12",
                primaryBookId = 59,
                primaryChapter = 2,
                passages = listOf(seg(59, "Santiago", 2), seg(45, "Romanos", 3)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    A primera vista, un lector superficial podría pensar que existe una contradicción insalvable entre Romanos 3:28 («Concluimos, pues, que el hombre es justificado por fe sin las obras de la ley») y Santiago 2:24 («Vosotros veis, pues, que el hombre es justificado por las obras, y no solamente por la fe»). Sin embargo, un análisis exegético cuidadoso revela que ambos apóstoles utilizan el mismo vocabulario pero en contextos polémicos distintos y con acepciones complementarias.
                    
                    El verbo griego «dikaioō» (justificar) posee en el Nuevo Testamento dos significados principales reconocidos por todos los léxicos:
                    1. Sentido Forense o Legal (el uso habitual de Pablo): Declarar judicialmente justo ante el tribunal de Dios a quien era culpable, acreditándole la justicia perfecta de Cristo. Responde a la pregunta: «¿Cómo puede un pecador ser reconciliado con un Dios santo?». Respuesta de Pablo: Únicamente por la fe en la sangre de Cristo.
                    2. Sentido Demostrativo o Reivindicatorio (el uso habitual de Santiago): Vindicarse, mostrarse o probarse justo públicamente delante de los hombres. Como cuando Jesús dijo: «La sabiduría es justificada [dikaiōthē, es decir, demostrada como verdadera] por sus hechos» (Mateo 11:19). Santiago responde a una pregunta distinta: «¿Cómo sabemos que una persona que afirma tener fe realmente la tiene?». Respuesta de Santiago: Por sus obras vivas.
                    
                    Esto se comprueba en el caso de Abraham: Pablo cita Génesis 15:6 cuando Abraham creyó y fue declarado justo ante Dios sin circuncisión ni templo. Santiago cita Génesis 22, ocurrido más de treinta años después, cuando Abraham ofreció a su hijo Isaac sobre el altar. Ofrecer a Isaac no fue la causa de su justificación ante Dios, sino la prueba pública y contundente de que la fe que había depositado treinta años antes era auténtica y viva.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    La teología de la Reforma sintetizó esta armonía con una máxima insuperable atribuida a Juan Calvino: «Solo la fe justifica; pero la fe que justifica nunca está sola». 
                    
                    Pablo combate a los legalistas judaizantes que pretendían añadir las obras de la ley como causa para ganarse la salvación. Santiago combate a los antinomianos que pretendían separar la salvación de la santidad práctica. Para Pablo, las obras son enemigas mortales de la fe si se ponen como fundamento de la salvación; pero para Santiago, las obras son amigas inseparables de la fe cuando se manifiestan como evidencia visible de la salvación. Ambos apóstoles predican exactamente el mismo Evangelio de la gracia transformadora.
                """,
                doctrinalThesis = "Pablo y Santiago armonizan perfectamente: somos justificados ante Dios únicamente por la fe (Pablo), y esa fe salvadora es vindicada ante los hombres mediante las obras (Santiago).",
                exegeticalAnalysis = "La distinción entre el sentido judicial forense de «dikaioō» en Romanos y su sentido demostrativo en Santiago resuelve cualquier conflicto aparente y confirma la unidad orgánica de las Escrituras."
            ),

            // DÍA 6
            createStudyDay(
                dayNumber = 6,
                title = "Día 6: La Regeneración Espiritual: El Buen Árbol y su Fruto",
                passagesSummary = "Tito 3:3-8; Mateo 7:15-20; Juan 15:1-8",
                primaryBookId = 56,
                primaryChapter = 3,
                passages = listOf(seg(56, "Tito", 3), seg(40, "Mateo", 7)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En Tito 3:4-8, el apóstol Pablo articula con maestría pedagógica el engranaje indivisible entre la gracia salvadora y la práctica de las buenas obras. En el versículo 5 proclama con contundencia: «Nos salvó, no por obras de justicia que nosotros hubiéramos hecho, sino por su misericordia, por el lavamiento de la regeneración y por la renovación en el Espíritu Santo».
                    
                    El término griego para regeneración es «palingenesia»: un nuevo nacimiento soberano, una recreación ontológica del corazón humano efectuada por el Espíritu de Dios (Ezequiel 36:26-27). El ser humano no es reformado exteriormente; es vivificado interiormente. Por consiguiente, tras afirmar que fuimos salvos puramente por misericordia, Pablo instruye a Tito de forma perentoria en el versículo 8: «Palabra fiel es esta, y en estas cosas quiero que insistas con firmeza, para que los que creen en Dios procuren ocuparse en buenas obras. Estas cosas son buenas y útiles a los hombres».
                    
                    Cristo ilustró esta dinámica con la metáfora botánica en Mateo 7:17-18: «Así, todo buen árbol da buenos frutos, pero el árbol malo da frutos malos. No puede el buen árbol dar malos frutos, ni el árbol malo dar frutos buenos». El árbol no produce manzanas para convertirse en manzano; produce manzanas porque por naturaleza es un manzano. Las obras son el producto espontáneo y connatural de una naturaleza espiritual regenerada.
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    La salvación bíblica no es una mera amnistía legal externa que deja al pecador en su corrupción moral. La gracia que justifica es inseparable de la gracia que santifica. Quien ha sido unido espiritualmente a Cristo mediante la fe viva, permanece en Él como el pámpano en la vid verdadera (Juan 15:5).
                    
                    Separados de Cristo nada podemos hacer; pero unidos vitalmente a Él por la fe, el fruto del Espíritu —amor, gozo, paz, paciencia, benignidad, bondad, fe, mansedumbre y templanza (Gálatas 5:22-23)— brota de manera inexorable. El creyente procura ocuparse en buenas obras no para comprar la misericordia de Dios, sino porque la vida santa del Salvador fluye a través de sus miembros para bendecir a su prójimo y glorificar al Padre celestial.
                """,
                doctrinalThesis = "La regeneración espiritual impartida por el Espíritu Santo renueva la naturaleza del creyente, haciendo que las buenas obras broten como el fruto natural y connatural de su unión con Cristo.",
                exegeticalAnalysis = "Tito 3:5-8 une de manera inquebrantable el lavamiento de la regeneración («palingenesia») con el mandato perentorio de ocuparse con celo en las buenas obras («kalōn ergōn»)."
            ),

            // DÍA 7
            createStudyDay(
                dayNumber = 7,
                title = "Día 7: El Tribunal de Cristo y la Recompensa de la Gracia",
                passagesSummary = "1 Corintios 3:10-15; 2 Corintios 5:9-10",
                primaryBookId = 46,
                primaryChapter = 3,
                passages = listOf(seg(46, "1 Corintios", 3), seg(47, "2 Corintios", 5)),
                studyNarrative = """
                    I. Contexto Histórico y Exégesis Textual:
                    En la teología paulina, la certeza inamovible de la justificación por la fe no conduce a la complacencia ni a la pereza moral; por el contrario, impulsa una vida de ferviente consagración y servicio diligente. En 2 Corintios 5:10 leemos: «Porque es necesario que todos nosotros comparezcamos ante el tribunal de Cristo, para que cada uno reciba según lo que haya hecho mientras estaba en el cuerpo, sea bueno o sea malo».
                    
                    La palabra griega utilizada para tribunal es «Bēma». En el contexto de las ciudades griegas de la antigüedad (como Corinto, donde los arqueólogos han excavado el tribunal público), el Bēma era el estrado del juez de los juegos atléticos, desde el cual se premiaba a los vencedores con guirnaldas y reconocimientos, no el tribunal penal donde se juzgaba a criminales para la ejecución capital.
                    
                    Para el creyente redimido, el juicio de condenación eterna (krisis) fue consumado y extinguido para siempre en el Calvario: «De cierto, de cierto os digo: El que oye mi palabra, y cree al que me envió, tiene vida eterna; y no vendrá a condenación, mas ha pasado de muerte a vida» (Juan 5:24; Romanos 8:1). El tribunal de Cristo (el Bēma) es una comparecencia de hijos de Dios justificados, donde sus obras son evaluadas para recompensa y galardón de gloria.
                    
                    Pablo ilustra esta verdad en 1 Corintios 3:12-15 mediante la metáfora del fuego purificador: algunos edifican sobre el único fundamento (Cristo) con oro, plata y piedras preciosas (obras motivadas por el amor genuino, la verdad bíblica y la gloria de Dios); otros edifican con madera, heno y hojarasca (obras carnales, motivadas por la vanagloria o el egoísmo). El fuego del juicio divino probará la calidad de la obra de cada uno: quien edificó con materiales nobles recibirá recompensa, y si la obra de alguno se quema, sufrirá pérdida de galardón, «si bien él mismo será salvo, aunque así como por fuego» (1 Co 3:15).
                    
                    II. Desarrollo Teológico y Cristocéntrico:
                    La doctrina de los galardones celestiales es una doctrina de pura gracia: Agustín de Hipona solía recordar que «cuando Dios corona nuestros méritos, no hace más que coronar sus propios dones en nosotros». Dios es tan bondadoso que no solo nos salva gratuitamente sin méritos propios, sino que promete recompensar con generosidad infinita las obras que su propio Espíritu capacitó en nuestra vida terrenal.
                    
                    Esta gloriosa perspectiva escatológica nos libra de dos errores mortales: por un lado, del orgullo legalista que cree poder negociar con Dios; y por otro lado, de la apatía espiritual que menosprecia las buenas obras. Servimos al Señor con pasión, predicamos el Evangelio, consolamos al afligido y vivimos en santidad sabiendo que «nuestro trabajo en el Señor no es en vano» (1 Corintios 15:58).
                """,
                doctrinalThesis = "Las obras del creyente serán evaluadas en el tribunal de Cristo (Bēma) para recompensa y galardón eterno, fundamentadas en la gracia y nunca para determinar la salvación, la cual descansa eternamente en Cristo.",
                exegeticalAnalysis = "El término «Bēma» en 2 Corintios 5:10 y el contraste de materiales en 1 Corintios 3:12-15 diferencian claramente la recompensa de la fidelidad del creyente del juicio de condenación que fue cancelado en la cruz."
            )
        )
    }
}
