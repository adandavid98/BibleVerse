package com.example.data.bible

/**
 * Catálogo del Plan de Lectura de Historias y Sucesos Bíblicos (180 Días).
 * Ofrece una experiencia integral: narración completa de cada historia, contexto histórico-cultural,
 * enseñanza práctica para memorizar y pasajes bíblicos de referencia opcionales.
 */
object BibleStoriesPlanCatalog {

    val storiesPlan: List<ReadingPlanDay> = listOf(
        ReadingPlanDay(
            dayNumber = 1,
            title = "Día 1: La Creación de los Cielos y la Tierra",
            passagesSummary = "Génesis 1-2",
            primaryBookId = 1,
            primaryChapter = 1,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(1, "Génesis", 1),
                PlanPassageSegment(1, "Génesis", 2),
            ),
            historicalContext = "En el antiguo Cercano Oriente, los mitos paganos (como el Enuma Elish babilónico) veían la creación como resultado de conflictos caóticos entre dioses violentos. El relato de Génesis reveló una verdad revolucionaria: un único Dios todopoderoso, soberano y lleno de amor que crea con orden, propósito y diseño perfecto por el poder de su palabra.",
            spiritualLesson = "Todo lo que Dios hace es bueno en gran manera. Fuiste creado a su imagen y semejanza con un propósito divino. Tu valor no procede de lo que posees o logras, sino de Aquel que te diseñó.",
            storyNarrative = "En el principio, antes de que existiera el tiempo, la materia o el espacio, Dios existía en eterna plenitud. La tierra estaba desordenada y vacía, y las tinieblas cubrían la faz del abismo, pero el Espíritu de Dios se movía con poder sobre las aguas. Entonces Dios habló con autoridad soberana: «Sea la luz», y la luz resplandeció. Con diseño perfecto, Dios separó las aguas, formó los continentes, encendió las estrellas en el firmamento y pobló los mares y la tierra con vida vibrante. Como obra cumbre, Dios formó al hombre del polvo de la tierra, sopló en su nariz aliento de vida y creó al ser humano a su imagen y semejanza, colocándolo en el huerto del Edén para vivir en comunión íntima y bendición perpetua."
        ),
        ReadingPlanDay(
            dayNumber = 2,
            title = "Día 2: La Caída del Hombre y el Primer Destello de Gracia",
            passagesSummary = "Génesis 3",
            primaryBookId = 1,
            primaryChapter = 3,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(1, "Génesis", 3),
            ),
            historicalContext = "El Edén representa la comunión perfecta entre Dios y la humanidad. La serpiente introduce la desconfianza hacia el carácter y la bondad de Dios. Tras la desobediencia, Dios no solo pronuncia juicio, sino que da la primera profecía del Mesías (el 'Protoevangelio' en Gn 3:15) y viste a Adán y Eva con pieles de animales.",
            spiritualLesson = "La raíz de la tentación es dudar de la bondad y soberanía de Dios. Aunque el pecado acarrea consecuencias graves, la gracia de Dios siempre toma la iniciativa para vestir nuestra desnudez y restaurar nuestra comunión.",
            storyNarrative = "En el hermoso huerto del Edén, Dios otorgó al ser humano libertad para disfrutar de todos los árboles, con una sola advertencia protectora: no comer del árbol del conocimiento del bien y del mal, pues el fruto acarrearía la muerte. La serpiente, con astucia ponzoñosa, sembró dudas: «¿Conque Dios os ha dicho...? No moriréis». Eva cedió al engaño de los ojos y comió, dando también a Adán. Al instante sus ojos se abrieron y sintieron vergüenza y temor, ocultándose de la presencia de Dios entre los árboles. Al caer la tarde, Dios vino buscando al hombre: «¿Dónde estás tú?». Aunque el pecado introdujo dolor, muerte y expulsión del paraíso, Dios en su infinita misericordia pronunció la primera gran promesa de redención: la simiente de la mujer heriría en la cabeza a la serpiente, y vistió a Adán y Eva con pieles de animales sacrificados."
        ),
        ReadingPlanDay(
            dayNumber = 3,
            title = "Día 3: Caín y Abel: La Primera Ofrenda y la Advertencia Divina",
            passagesSummary = "Génesis 4",
            primaryBookId = 1,
            primaryChapter = 4,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(1, "Génesis", 4),
            ),
            historicalContext = "Abel ofreció de los primogénitos de sus ovejas con fe y devoción sincera; Caín trajo del fruto de la tierra con frialdad externa. La reacción homicida de Caín ilustra cómo la envidia y el resentimiento endurecen el corazón humano cuando se ignora la advertencia paternal del Creador.",
            spiritualLesson = "A Dios le importa más la actitud del corazón que el acto externo de culto. Si el pecado está a la puerta de tu vida esperando devorarte, no alimentes el rencor: pide la gracia de Dios para dominarlo.",
            storyNarrative = "Adán y Eva tuvieron dos hijos: Caín, labrador de la tierra, y Abel, pastor de ovejas. Al llegar el tiempo de las ofrendas, Abel presentó de los primogénitos de sus ovejas, lo mejor y más selecto, con un corazón lleno de fe y reverencia; Caín, en cambio, trajo una ofrenda rutinaria del fruto del campo. Dios miró con agrado a Abel y su ofrenda, pero no a Caín. Lleno de ira y envidia amarga, Caín decayó en su semblante. Dios le advirtió con ternura paternal: «El pecado está a la puerta acechándote, pero tú debes dominarlo». Sin embargo, Caín invitó a su hermano al campo y lo asesinó. Cuando Dios le preguntó por su hermano, Caín respondió con descaro: «¿Soy yo acaso guarda de mi hermano?». La sangre de Abel clamaba desde la tierra, marcando la trágica espiral del pecado."
        ),
        ReadingPlanDay(
            dayNumber = 4,
            title = "Día 4: Las Genealogías Antediluvianas y el Caminar de Enoc",
            passagesSummary = "Génesis 5",
            primaryBookId = 1,
            primaryChapter = 5,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(1, "Génesis", 5),
            ),
            historicalContext = "El estribillo repetitivo 'y murió' subraya la realidad inexorable de la muerte física tras la caída. Sin embargo, Enoc rompe ese patrón oscuro: 'Caminó, pues, Enoc con Dios, y desapareció, porque le llevó Dios' (Gn 5:24). En una generación corrompida, Enoc cultivó una íntima amistad cotidiana con el Señor.",
            spiritualLesson = "Caminar con Dios no es un evento de un solo día, sino una comunión constante y fiel a lo largo de los años. Una vida de intimidad con Dios trasciende la muerte y deja un legado eterno.",
            storyNarrative = "A través de diez generaciones desde Adán, el libro de Génesis registra las genealogías de los patriarcas antediluvianos. Hombres que vivieron cientos de años vieron transcurrir los siglos bajo la sombra inexorable de la muerte física: «y engendró hijos e hijas, y murió». Sin embargo, en medio de esa letanía sombría, resplandeció la figura luminosa de Enoc, hijo de Jared. La Escritura resume su paso por la tierra con una belleza sublime: «Caminó, pues, Enoc con Dios, y desapareció, porque le llevó Dios». Enoc no experimentó la muerte; su íntima comunión cotidiana, su adoración sincera y su confianza radical agradaron tanto al Creador que fue arrebatado directamente a la gloria celestial, dejando un testimonio imperecedero de lo que significa vivir en amistad con Dios."
        ),
        ReadingPlanDay(
            dayNumber = 5,
            title = "Día 5: Noé y el Arca ante el Gran Diluvio",
            passagesSummary = "Génesis 6-7",
            primaryBookId = 1,
            primaryChapter = 6,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(1, "Génesis", 6),
                PlanPassageSegment(1, "Génesis", 7),
            ),
            historicalContext = "En medio de una civilización sumida en la violencia y corrupción desenfrenada, Noé halló gracia ante los ojos de Jehová por su integridad de fe. Construir un arca gigante en tierra seca durante décadas requirió una fe inquebrantable frente a las burlas de su sociedad.",
            spiritualLesson = "La fe genuina obedece a Dios incluso cuando sus instrucciones desafían la lógica del mundo. Dios siempre provee un refugio de salvación para aquellos que confían en Él.",
            storyNarrative = "La maldad de los hombres se multiplicó en la tierra hasta que todo designio de los pensamientos del corazón era de continuo solamente el mal. El dolor de Dios por la violencia humana dio paso al juicio, pero «Noé halló gracia ante los ojos de Jehová». Dios instruyó a Noé a construir un arca gigantesca de madera de gofer, calafateada por dentro y por fuera, de tres pisos y dimensiones titánicas. Durante décadas de arduo trabajo en tierra firme, Noé proclamó justicia ante una generación incrédula. Cuando el arca estuvo lista, entraron Noé, su familia y parejas de todas las especies animales. Dios mismo cerró la puerta. Las fuentes del gran abismo se rompieron, las cataratas de los cielos se abrieron y las aguas cubrieron hasta los montes más altos durante cuarenta días."
        ),
        ReadingPlanDay(
            dayNumber = 6,
            title = "Día 6: El Pacto del Arcoíris y un Nuevo Comienzo",
            passagesSummary = "Génesis 8-9",
            primaryBookId = 1,
            primaryChapter = 8,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(1, "Génesis", 8),
                PlanPassageSegment(1, "Génesis", 9),
            ),
            historicalContext = "El sacrificio de acción de gracias ofrecido por Noé al salir del arca fue recibido por Dios como olor grato. El arcoíris en las nubes se convirtió en la señal visible del pacto de misericordia de Dios con toda criatura viviente, garantizando la preservación de los ciclos naturales.",
            spiritualLesson = "Dios jamás olvida sus promesas. Cuando las tormentas de la vida pasen, mira las señales de la fidelidad divina y ofrece un corazón agradecido por sus nuevas oportunidades.",
            storyNarrative = "Tras cinco meses de navegación sobre las aguas profundas, el arca reposó sobre los montes de Ararat. Noé envió primero un cuervo y luego una paloma, que regresó con una hoja tierna de olivo en el pico, señal de que las aguas menguaban. Cuando la tierra se secó por completo, Dios ordenó a Noé salir del arca. Lo primero que hizo el patriarca al pisar suelo firme fue edificar un altar a Jehová y ofrecer holocaustos de agradecimiento. El Señor percibió el olor grato y prometió en su corazón no volver a maldecir la tierra con diluvio. Puso el arcoíris en las nubes como sello perpetuo de su pacto: mientras la tierra permanezca, no cesarán la siembra y la siega, el frío y el calor, el verano y el invierno, el día y la noche."
        ),
        ReadingPlanDay(
            dayNumber = 7,
            title = "Día 7: La Torre de Babel y la Soberbia Humana",
            passagesSummary = "Génesis 11",
            primaryBookId = 1,
            primaryChapter = 11,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(1, "Génesis", 11),
            ),
            historicalContext = "En la llanura de Sinar (Babilonia), la humanidad intentó construir un zigurat cuya cúspide llegara al cielo para 'hacerse un nombre' y evitar esparcirse. Dios confundió sus lenguas para frenar su autosuficiencia y cumplir su mandato de llenar la tierra.",
            spiritualLesson = "Cualquier proyecto humano que intente excluir a Dios o enaltecer el ego está condenado al fracaso y a la confusión. La verdadera grandeza se encuentra en humillarse bajo la poderosa mano de Dios.",
            storyNarrative = "Toda la tierra tenía una sola lengua y las mismas palabras. Al emigrar hacia el oriente, los hombres hallaron una llanura fértil en la tierra de Sinar (Babilonia) y decidieron establecerse. Movidos por la soberbia y el deseo de autosuficiencia, dijeron: «Vamos, edifiquémonos una ciudad y una torre, cuya cúspide llegue al cielo; y hagámonos un nombre, por si fuéremos esparcidos sobre la faz de toda la tierra». Pretendían forjar una gloria sin Dios y desafiar el mandato de poblar la tierra. Jehová descendió para observar la ciudad y la torre. Con un solo acto soberano, confundió su lenguaje de modo que nadie entendía el habla de su compañero, obligándolos a dispersarse por el mundo."
        ),
        ReadingPlanDay(
            dayNumber = 8,
            title = "Día 8: El Llamado de Abram y la Promesa de Bendición",
            passagesSummary = "Génesis 12",
            primaryBookId = 1,
            primaryChapter = 12,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(1, "Génesis", 12),
            ),
            historicalContext = "Ur de los caldeos y Harán eran centros prósperos de adoración a la luna (el dios Sin). Dios llama a Abram a los 75 años a abandonar su tierra, su parentela y comodidades paganas hacia una tierra desconocida, prometiéndole hacer de él una gran nación que bendeciría a todas las familias de la tierra.",
            spiritualLesson = "El llamado de Dios a menudo requiere dejar atrás la falsa seguridad del pasado. Salir por fe hacia lo que Dios promete es el primer paso para experimentar su bendición generacional.",
            storyNarrative = "En la próspera ciudad de Ur de los caldeos y luego en Harán, Dios habló a un hombre de 75 años llamado Abram: «Vete de tu tierra y de tu parentela, y de la casa de tu padre, a la tierra que te mostraré. Y haré de ti una nación grande, y te bendeciré, y engrandeceré tu nombre, y serás bendición... y serán benditas en ti todas las familias de la tierra». Sin saber exactamente adónde iba, Abram empacó sus pertenencias, tomó a su esposa Sarai, a su sobrino Lot y a sus siervos, y partió por fe. Al llegar a Canaán, en Siquem junto a la encina de More, Dios se le apareció prometiéndole esa tierra a su descendencia. Abram edificó allí un altar a Jehová e invocó su santo nombre."
        ),
        ReadingPlanDay(
            dayNumber = 9,
            title = "Día 9: Abram Rescata a Lot y el Encuentro con Melquisedec",
            passagesSummary = "Génesis 14",
            primaryBookId = 1,
            primaryChapter = 14,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(1, "Génesis", 14),
            ),
            historicalContext = "Tras derrotar a la coalición de reyes mesopotámicos para rescatar a su sobrino Lot, Abram fue recibido por Melquisedec, rey de Salem y sacerdote del Dios Altísimo, quien le ofreció pan y vino. Abram rechazó el botín del rey de Sodoma y diezmó a Melquisedec.",
            spiritualLesson = "La victoria espiritual nos enseña a no aceptar las riquezas corruptas de este mundo para que nadie diga 'yo enriquecí a Abram'. Nuestra recompensa y provisión provienen únicamente de Dios.",
            storyNarrative = "Estalló una guerra entre cuatro reyes mesopotámicos liderados por Quedorlaomer y cinco reyes de la llanura del Mar Muerto. Sodoma fue saqueada y Lot, el sobrino de Abram que vivía allí, fue llevado cautivo con todos sus bienes. Al enterarse por un fugitivo, Abram armó a 318 criados nacidos en su casa, persiguió a los invasores de noche hasta Dan y Damasco, los derrotó y rescató a Lot, a las mujeres y todo el botín. A su regreso victorioso en el valle de Save, salió a su encuentro Melquisedec, rey de Salem y sacerdote del Dios Altísimo, quien le ofreció pan y vino y lo bendijo. Abram reconoció la autoridad de Melquisedec y le entregó los diezmos de todo, rechazando tajantemente enriquecerse con los bienes corruptos ofrecidos por el rey de Sodoma."
        ),
        ReadingPlanDay(
            dayNumber = 10,
            title = "Día 10: El Pacto Incondicional: Las Estrellas y la Antorcha de Fuego",
            passagesSummary = "Génesis 15",
            primaryBookId = 1,
            primaryChapter = 15,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(1, "Génesis", 15),
            ),
            historicalContext = "En los pactos antiguos del Cercano Oriente, ambas partes caminaban entre animales divididos, aceptando la muerte si quebrantaban el acuerdo. En esta teofanía, solo la presencia de Dios (humo y antorcha) pasó entre los pedazos, asumiendo Dios mismo la garantía total del pacto.",
            spiritualLesson = "'Creyó Abram a Jehová, y le fue contado por justicia'. La salvación descansa no en nuestras fuerzas inconstantes, sino en la fidelidad inmutable del Dios que cumple sus promesas.",
            storyNarrative = "Tras el conflicto bélico, Abram sintió temor ante posibles represalias. La palabra de Jehová vino a él en visión: «No temas, Abram; yo soy tu escudo, y tu galardón será sobremanera grande». Abram suspiró: «Señor Jehová, ¿qué me darás, siendo así que ando sin hijo?». Dios lo llevó afuera bajo la noche estrellada del desierto: «Mira ahora los cielos, y cuenta las estrellas, si las puedes contar. Así será tu descendencia». Y Abram creyó a Jehová, y le fue contado por justicia. Dios selló este pacto ordenándole preparar animales; al caer el sol, un sueño profundo cayó sobre Abram, y una densa humareda y una antorcha de fuego pasaron entre los animales divididos, garantizando Dios mismo con su vida el cumplimiento de la promesa."
        ),
        ReadingPlanDay(
            dayNumber = 11,
            title = "Día 11: Agar e Ismael: El Dios que me Ve en el Desierto",
            passagesSummary = "Génesis 16",
            primaryBookId = 1,
            primaryChapter = 16,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(1, "Génesis", 16),
            ),
            historicalContext = "Frente a la impaciencia humana, Sarai entregó a su esclava egipcia Agar. Cuando Agar huyó desesperada ante los maltratos al desierto de Shur, el Ángel de Jehová la buscó y la consoló. Agar llamó al Señor 'El-Roi': '¿No he visto también aquí al que me ve?'",
            spiritualLesson = "Nadie es invisible para Dios, ni siquiera en las circunstancias más dolorosas y solitarias. En tu desierto, Él conoce tu nombre, escucha tu aflicción y tiene cuidado de ti.",
            storyNarrative = "Habían pasado diez años en Canaán y Sarai continuaba estéril. Desesperada, sugirió a Abram recurrir a la costumbre de la época: tomar a su esclava egipcia Agar para tener hijos a través de ella. Agar concibió y comenzó a mirar con desprecio a su señora Sarai. Ante la aflicción y el maltrato resultante, Agar huyó sola al ardiente desierto hacia Shur. Junto a una fuente de agua en el camino, el Ángel de Jehová la halló: «Agar, sierva de Sarai, ¿de dónde vienes, y a dónde vas?». El Señor le prometió multiplicar su descendencia y le ordenó llamar a su hijo Ismael («Dios oye»). Agar, asombrada de que el Dios todopoderoso se preocupara por una esclava fugitiva, llamó al Señor conmovida: «Tú eres El-Roi» (el Dios que me ve)."
        ),
        ReadingPlanDay(
            dayNumber = 12,
            title = "Día 12: El Pacto de la Circuncisión y los Nuevos Nombres",
            passagesSummary = "Génesis 17",
            primaryBookId = 1,
            primaryChapter = 17,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(1, "Génesis", 17),
            ),
            historicalContext = "Trece años después de Ismael, Dios se revela a Abram como 'El-Shaddai' (Dios Todopoderoso). Cambia el nombre de Abram ('padre enaltecido') a Abraham ('padre de multitudes'), y el de Sarai a Sara, ordenando la circuncisión como señal perpetua de pertenencia al pacto.",
            spiritualLesson = "Cuando Dios entra en pacto con una persona, transforma su identidad y su destino. Camina delante de Dios y sé íntegro, sabiendo que para El-Shaddai no hay nada imposible.",
            storyNarrative = "Cuando Abram tenía 99 años, Jehová se le apareció diciendo: «Yo soy el Dios Todopoderoso (El-Shaddai); anda delante de mí y sé perfecto. Y pondré mi pacto entre mí y ti». Como señal de esta nueva etapa, cambió su nombre de Abram («padre exaltado») a Abraham («padre de multitudes»), y a Sarai la llamó Sara («princesa»), prometiendo que reyes de pueblos saldrían de ella. Abraham cayó sobre su rostro y se rió en su interior, pensando en sus 100 años y los 90 de Sara. Dios reafirmó: «Ciertamente Sara tu mujer te dará a luz un hijo, y llamarás su nombre Isaac; y confirmaré mi pacto con él». Instituyó la circuncisión de todo varón como señal perpetua de consagración y pertenencia al pacto sagrado."
        ),
        ReadingPlanDay(
            dayNumber = 13,
            title = "Día 13: La Destrucción de Sodoma y la Intercesión de Abraham",
            passagesSummary = "Génesis 18-19",
            primaryBookId = 1,
            primaryChapter = 18,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(1, "Génesis", 18),
                PlanPassageSegment(1, "Génesis", 19),
            ),
            historicalContext = "Abraham hospedó a ángeles y a la teofanía de Dios bajo los encinares de Mamre. Al enterarse del juicio inminente sobre Sodoma, Abraham intercedió audazmente apelando a la justicia y compasión de Dios. Lot fue rescatado, pero Sodoma pereció bajo fuego y azufre.",
            spiritualLesson = "La oración de intercesión tiene un poder extraordinario ante el trono de Dios. La justicia de Dios no tolera el mal continuo, pero su misericordia busca salvar a todo aquel que clama.",
            storyNarrative = "Bajo el calor del mediodía en los encinares de Mamre, Abraham vio a tres varones misteriosos y corrió a atenderlos con exquisita hospitalidad. Eran el Señor y dos ángeles. Al despedirse, Dios reveló que el clamor por el pecado atroz de Sodoma y Gomorra había subido a su presencia. Abraham se acercó y comenzó a interceder: «¿Destruirás también al justo con el impío?». Negoció con audacia santa desde 50 justos hasta 10. Los dos ángeles llegaron a Sodoma al atardecer y rescataron a Lot, a su esposa y a sus dos hijas antes del amanecer. Llovió azufre y fuego sobre las ciudades de la llanura. La esposa de Lot miró hacia atrás añorando la ciudad y quedó convertida en estatua de sal."
        ),
        ReadingPlanDay(
            dayNumber = 14,
            title = "Día 14: El Nacimiento de Isaac: Risa de Gozo y Cumplimiento",
            passagesSummary = "Génesis 21",
            primaryBookId = 1,
            primaryChapter = 21,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(1, "Génesis", 21),
            ),
            historicalContext = "Veinticinco años después de la primera promesa, cuando Abraham tenía 100 años y Sara 90 (condición biológicamente estéril), nació el hijo de la promesa. Lo llamaron Isaac ('Él ríe'), transformando la risa de incredulidad previa en risa de gozo sobrenatural.",
            spiritualLesson = "Los tiempos de Dios no son los nuestros, pero su tiempo siempre es perfecto. Lo que humanamente parece muerto o imposible florece con poder cuando Dios da la orden.",
            storyNarrative = "Visitó Jehová a Sara como había dicho, y dio a luz un hijo a Abraham en su vejez, en el tiempo exacto señalado por Dios. Abraham le puso por nombre Isaac («Él ríe») y lo circuncidó al octavo día. Sara proclamó con lágrimas de gozo: «Dios me ha hecho reír, y cualquiera que lo oyere, se reirá conmigo». Cuando el niño creció y fue destetado, hubo una gran fiesta familiar. Sin embargo, al ver a Ismael burlándose del pequeño Isaac, Sara pidió su expulsión. Aunque a Abraham le dolió en gran manera, Dios le aseguró que cuidaría de Ismael y haría de él una nación, pero que en Isaac le sería llamada descendencia santa."
        ),
        ReadingPlanDay(
            dayNumber = 15,
            title = "Día 15: El Sacrificio en el Monte Moriah: Jehová Jireh",
            passagesSummary = "Génesis 22",
            primaryBookId = 1,
            primaryChapter = 22,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(1, "Génesis", 22),
            ),
            historicalContext = "Dios probó a Abraham pidiéndole a su único hijo amado, Isaac. Abraham obedeció creyendo que Dios era poderoso incluso para levantarlo de entre los muertos (Heb 11:19). En el altar, Dios detuvo su mano y proveyó un carnero trabado en un zarzal, nombrándolo 'Jehová-Jireh'.",
            spiritualLesson = "Dios nunca te pedirá nada sin tener preparada una provisión mayor. Esta escena fue un anticipo profético supremo del Padre celestial entregando a su Hijo unigénito en la cruz del Calvario.",
            storyNarrative = "Aconteció después de estas cosas que Dios probó la fe de Abraham: «Toma ahora tu hijo, tu único, Isaac, a quien amas, y vete a tierra de Moriah, y ofrécelo allí en holocausto sobre uno de los montes que yo te diré». Con dolor indecible pero obediencia total, Abraham madrugó, ensilló su asno y caminó tres días con Isaac. Al subir la colina, Isaac preguntó: «Padre mío... ¿dónde está el cordero para el holocausto?». Abraham respondió con fe profética: «Dios se proveerá de cordero, hijo mío». En el altar, cuando levantaba el cuchillo, el Ángel de Jehová clamó: «¡Abraham, Abraham! No extiendas tu mano sobre el muchacho». Detrás de él, un carnero estaba trabado por los cuernos en un zarzal. Abraham llamó a aquel lugar 'Jehová-Jireh' (Jehová proveerá)."
        ),
        ReadingPlanDay(
            dayNumber = 16,
            title = "Día 16: La Búsqueda de Rebeca: Oración, Carácter y Providencia",
            passagesSummary = "Génesis 24",
            primaryBookId = 1,
            primaryChapter = 24,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(1, "Génesis", 24),
            ),
            historicalContext = "El siervo más antiguo de Abraham (Eliezer) viajó a Mesopotamia jurando no tomar mujer cananea para Isaac. Al llegar al pozo, oró con especificidad pidiendo una señal de hospitalidad y diligencia. Rebeca no solo le dio agua a él, sino que abrevó a todos sus camellos.",
            spiritualLesson = "La providencia divina guía a los que buscan su voluntad en oración. La verdadera belleza se manifiesta en un carácter humilde, trabajador, servicial y generoso.",
            storyNarrative = "Abraham, anciano y bendecido en todo, encomendó una misión sagrada a su mayordomo más fiel, Eliezer: viajar a Mesopotamia y conseguir esposa para Isaac que no fuera cananea. Eliezer llegó a las afueras de la ciudad de Nacor y se detuvo junto al pozo al atardecer, cuando las mujeres salían a sacar agua. Oró al Dios de Abraham pidiendo una señal de carácter: que la doncella elegida no solo le diese de beber a él, sino que se ofreciera a abrevar voluntariamente a sus diez camellos sedientos (lo que requería acarrear cientos de litros de agua). Antes que terminara de orar, salió la hermosa y virtuosa Rebeca con su cántaro, cumpliendo cada detalle con alegría y generosidad."
        ),
        ReadingPlanDay(
            dayNumber = 17,
            title = "Día 17: Jacob y Esaú: La Venta de la Primogenitura",
            passagesSummary = "Génesis 25",
            primaryBookId = 1,
            primaryChapter = 25,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(1, "Génesis", 25),
            ),
            historicalContext = "La primogenitura en el mundo patriarcal otorgaba el liderazgo espiritual de la familia y doble porción de herencia. Esaú, cazador impulsivo, menospreció ese honor eterno por un plato pasajero de lentejas para saciar su hambre inmediata.",
            spiritualLesson = "Nunca cambies tu herencia espiritual y tu destino eterno por un placer efímero del momento. Valora lo sagrado con gratitud, dominio propio y reverencia.",
            storyNarrative = "Isaac oró con fervor por su esposa Rebeca porque era estéril, y Dios concedió su oración. En su vientre, dos gemelos luchaban fuertemente, y Dios le reveló: «Dos naciones hay en tu seno... el mayor servirá al menor». Nació el primero, rubio y velludo como una pelliza, llamado Esaú; luego nació su hermano asido al talón de Esaú, llamado Jacob («el que toma por el talón»). Esaú creció como cazador agreste, favorito de Isaac; Jacob era hombre tranquilo de tiendas, amado por Rebeca. Un día, Esaú regresó del campo exhausto y hambriento. Al ver a Jacob cocinando un guisado rojo de lentejas, exigió comida. Jacob le pidió jurar la venta de su primogenitura; Esaú la menospreció y la vendió."
        ),
        ReadingPlanDay(
            dayNumber = 18,
            title = "Día 18: La Escalera de Bet-el: La Puerta del Cielo en el Exilio",
            passagesSummary = "Génesis 28",
            primaryBookId = 1,
            primaryChapter = 28,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(1, "Génesis", 28),
            ),
            historicalContext = "Huyendo de la furia de Esaú, solitario y con una piedra por cabecera en el desierto, Jacob tuvo una visión de una escalera que unía la tierra y el cielo, con ángeles que subían y bajaban y Dios prometiéndole protección incondicional. Jacob llamó al lugar 'Bet-el' (Casa de Dios).",
            spiritualLesson = "Aun en tus momentos de mayor soledad, incertidumbre y error, la presencia de Dios te rodea. Jesucristo es la escalera viva que abre el cielo y conecta tu fragilidad con la gloria divina.",
            storyNarrative = "Huyendo de la furia de Esaú tras haber recibido la bendición patriarcal mediante engaño, Jacob marchó solitario hacia Harán. Al caer la noche en un paraje desolado, tomó una piedra por cabecera y se durmió. Tuvo un sueño trascendental: una escalera celestial apoyada en la tierra cuya cúspide tocaba el cielo, y ángeles de Dios subían y bajaban por ella. En lo alto, Jehová renovó el pacto de Abraham: «He aquí, yo estoy contigo, y te guardaré por dondequiera que fueres, y volveré a traerte a esta tierra». Jacob despertó estremecido: «¡Ciertamente Jehová está en este lugar, y yo no lo sabía! ¡No es otra cosa que casa de Dios y puerta del cielo!». Ungió la piedra con aceite y llamó al lugar Bet-el."
        ),
        ReadingPlanDay(
            dayNumber = 19,
            title = "Día 19: Jacob en Padan-aram: El Sembrador y la Cosecha con Labán",
            passagesSummary = "Génesis 29-30",
            primaryBookId = 1,
            primaryChapter = 29,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(1, "Génesis", 29),
                PlanPassageSegment(1, "Génesis", 30),
            ),
            historicalContext = "El engañador Jacob experimentó en carne propia el engaño de su tío Labán, trabajando 14 años por el amor de Raquel tras recibir primero a Lea. A pesar de los conflictos domésticos, Dios bendijo a Jacob con once hijos en aquella tierra lejana.",
            spiritualLesson = "Dios utiliza las dificultades y las injusticias de otros para purificar nuestro carácter y forjar nuestra paciencia. Su bendición prospera incluso en medio de entornos adversos.",
            storyNarrative = "Llegando a la tierra de los orientales, Jacob halló a pastores junto a un pozo y conoció a su prima Raquel que apacentaba las ovejas de su tío Labán. Jacob lloró de emoción y besó a Raquel. Labán lo hospedó y acordaron que Jacob trabajaría siete años por amor a Raquel; la Escritura dice que «le parecieron como pocos días, porque la amaba». Cumplido el plazo, Labán celebró el banquete nupcial pero en la oscuridad le entregó con engaño a su hija mayor, Lea. A la mañana siguiente Jacob reclamó airado; Labán justificó la costumbre local y le exigió trabajar otros siete años por Raquel. A pesar de los celos y disputas domésticas, Dios bendijo a Jacob con once hijos y riquezas proverbiales en ganado."
        ),
        ReadingPlanDay(
            dayNumber = 20,
            title = "Día 20: La Lucha en Peniel: De Jacob el Suplantador a Israel el Príncipe",
            passagesSummary = "Génesis 32",
            primaryBookId = 1,
            primaryChapter = 32,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(1, "Génesis", 32),
            ),
            historicalContext = "Antes de encontrarse con su hermano Esaú y aterrorizado por 400 hombres armados, Jacob se quedó a solas en el vado de Jaboc. Luchó toda la noche con el Ángel de Jehová hasta el amanecer, rogando: 'No te dejaré, si no me bendices'. Dios descoyuntó su muslo y le cambió el nombre a Israel.",
            spiritualLesson = "Para recibir la bendición definitiva de Dios debemos rendir nuestra autosuficiencia. Cuando reconocemos nuestra debilidad y nos aferramos a Él, salimos transformados en hombres y mujeres de fe.",
            storyNarrative = "Tras veinte años en Padan-aram, Jacob regresó a Canaán temiendo el encuentro inminente con su hermano Esaú, quien venía con 400 hombres. Desesperado, dividió a su familia y rebaños y envió abundantes regalos de apaciguamiento. Esa noche, tras cruzar el vado de Jaboc, Jacob se quedó completamente solo. Un Varón misterioso luchó cuerpo a cuerpo con él hasta rayar el alba. Viendo que no podía con Jacob, tocó la coyuntura de su muslo descoyuntándolo. Jacob, herido pero aferrado con el alma, exclamó: «No te dejaré, si no me bendices». El Varón le preguntó su nombre: «Jacob». «No se llamará más tu nombre Jacob, sino Israel; porque has luchado con Dios y con los hombres, y has vencido». Jacob llamó a aquel lugar Peniel: «Vi a Dios cara a cara, y fue librada mi alma»."
        ),
        ReadingPlanDay(
            dayNumber = 21,
            title = "Día 21: La Reconciliación entre Jacob y Esaú",
            passagesSummary = "Génesis 33",
            primaryBookId = 1,
            primaryChapter = 33,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(1, "Génesis", 33),
            ),
            historicalContext = "Esperando guerra y venganza, Jacob vio a Esaú correr a su encuentro, abrazarlo, caer sobre su cuello y llorar juntos. Veinte años de resentimiento se disolvieron en un abrazo restaurador.",
            spiritualLesson = "El perdón rompe cadenas que llevan décadas aprisionando a las familias. Cuando nuestros caminos son agradables al Señor, Él hace que aun nuestros enemigos estén en paz con nosotros.",
            storyNarrative = "Al alzar los ojos, Jacob vio venir a Esaú con su tropa armada. Adelantándose con cautela, Jacob se postró en tierra siete veces hasta llegar cerca de su hermano. Contra todo pronóstico de venganza, Esaú corrió a su encuentro, lo abrazó fuertemente, se echó sobre su cuello y lo besó con lágrimas de profundo afecto fraternal. Esaú preguntó por las mujeres y niños, a quienes Jacob presentó con humildad reverente. Esaú rehusó inicialmente los cuantiosos regalos diciendo: «Mucho tengo yo, hermano mío; sea para ti lo que es tuyo», pero Jacob insistió con gratitud: «He visto tu rostro como si viera el rostro de Dios, pues que me has recibido con beneplácito». El perdón sincero sanó dos décadas de amargura."
        ),
        ReadingPlanDay(
            dayNumber = 22,
            title = "Día 22: José y la Túnica de Colores: Envidia y Traición Fraterna",
            passagesSummary = "Génesis 37",
            primaryBookId = 1,
            primaryChapter = 37,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(1, "Génesis", 37),
            ),
            historicalContext = "José, el hijo predilecto de Jacob, recibió una túnica especial de mangas largas (símbolo de estatus noble) y compartió sus sueños de realeza. Movidos por la envidia destructiva, sus hermanos lo arrojaron a una cisterna vacía en Dotán y lo vendieron a mercaderes madianitas por veinte piezas de plata.",
            spiritualLesson = "La envidia carcome el alma y divide a los seres queridos. Dios tiene sueños altos para ti, y aunque otros intenten apagar la visión arrojándote al pozo, los planes de Dios prevalecerán.",
            storyNarrative = "Jacob amaba a José más que a todos sus otros hijos porque era el hijo de su vejez, y le confeccionó una túnica de diversos colores y mangas largas. Sus hermanos lo aborrecían por el favoritismo paterno y porque no podían hablarle pacíficamente. Para colmo, José tuvo dos sueños proféticos: las gavillas de trigo de sus hermanos postrándose ante la suya, y el sol, la luna y once estrellas inclinándose ante él. Cuando Jacob envió a José a visitar a sus hermanos en Dotán, ellos lo vieron venir de lejos y conspiraron: «He aquí viene el soñador; matémosle y echémosle en una cisterna». Rubén intervino para librarlo. Mientras comían, pasó una caravana de mercaderes ismaelitas rumbo a Egipto; Judá propuso venderlo por veinte piezas de plata. Mancharon su túnica con sangre de cabrito y destrozaron el corazón de su anciano padre."
        ),
        ReadingPlanDay(
            dayNumber = 23,
            title = "Día 23: José en Casa de Potifar: Integridad frente a la Tentación",
            passagesSummary = "Génesis 39",
            primaryBookId = 1,
            primaryChapter = 39,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(1, "Génesis", 39),
            ),
            historicalContext = "En Egipto, como mayordomo del capitán de la guardia del Faraón, José prosperó porque 'Jehová estaba con él'. Acosado diariamente por la esposa de Potifar, José rechazó el pecado con una convicción intachable: '¿Cómo, pues, haría yo este gran mal, y pecaría contra Dios?' y huyó.",
            spiritualLesson = "La pureza moral no se negocia; ante la tentación destructiva, la valentía consiste en huir. Es mejor sufrir falsas acusaciones con la conciencia limpia que gozar de placeres culpables.",
            storyNarrative = "En Egipto, José fue comprado por Potifar, oficial de confianza y capitán de la guardia del Faraón. Dios bendijo la casa del egipcio de tal manera a causa de José que Potifar puso todo lo que tenía en sus manos con total confianza. José era de hermoso semblante y bella presencia. La esposa de Potifar puso sus ojos en él y lo acosó insistentemente: «Acuéstate conmigo». José se negó con intachable integridad moral: «¿Cómo, pues, haría yo este gran mal, y pecaría contra Dios?». Un día en que no había nadie en casa, ella lo asió de su ropa; José dejó su manto en sus manos y huyó a toda prisa. Ella mintió por despecho acusándolo falsamente de violación, y José fue arrojado a la prisión real."
        ),
        ReadingPlanDay(
            dayNumber = 24,
            title = "Día 24: José en la Cárcel y los Sueños del Faraón",
            passagesSummary = "Génesis 40-41",
            primaryBookId = 1,
            primaryChapter = 40,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(1, "Génesis", 40),
                PlanPassageSegment(1, "Génesis", 41),
            ),
            historicalContext = "En la prisión real egipcia, José interpretó con exactitud los sueños del copero y del panadero dando la gloria a Dios. Aunque fue olvidado por dos años, en el momento señalado fue llamado ante el Faraón para interpretar las siete vacas gordas y flacas, siendo nombrado gobernador de todo Egipto.",
            spiritualLesson = "El aparente retraso de Dios no es olvido, sino preparación. Tu don y tu fidelidad en lo oculto te abrirán puertas ante los reyes cuando llegue el momento señalado por el Altísimo.",
            storyNarrative = "En la cárcel, Jehová estaba con José y le extendió su misericordia, ganándose la confianza del jefe de la prisión. Allí interpretó con precisión los sueños del copero mayor (restitución) y del panadero (ejecución), pidiéndole al copero que se acordara de él. El copero lo olvidó por dos años enteros hasta que el Faraón tuvo dos sueños perturbadores que ningún mago supo descifrar: siete vacas flacas devorando a siete gordas, y siete espigas menudas devorando a siete hermosas. José fue sacado apresuradamente del calabozo: «No está en mí; Dios será el que dé respuesta propicia a Faraón». Explicó que vendrían siete años de gran abundancia seguidos de siete años de hambre feroz, aconsejando nombrar un administrador sabio. Faraón exclamó: «¿Acaso hallaremos a otro hombre como este, en quien esté el espíritu de Dios?», y puso su anillo en la mano de José nombrándolo gobernador sobre todo Egipto a sus 30 años."
        ),
        ReadingPlanDay(
            dayNumber = 25,
            title = "Día 25: José Perdona a sus Hermanos: La Providencia Soberana",
            passagesSummary = "Génesis 42-45",
            primaryBookId = 1,
            primaryChapter = 42,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(1, "Génesis", 42),
                PlanPassageSegment(1, "Génesis", 45),
            ),
            historicalContext = "Cuando la hambruna obligó a los hermanos a postrarse ante el gran visir de Egipto sin reconocerlo, José probó su genuino arrepentimiento. Finalmente, entre lágrimas conmovedoras, se reveló diciendo: 'Vosotros pensasteis mal contra mí, mas Dios lo encaminó a bien, para hacer lo que vemos hoy, para mantener en vida a mucho pueblo'.",
            spiritualLesson = "La soberanía de Dios transforma las heridas y traiciones humanas en canales de salvación y bendición. El perdón sincero libera el alma y sana a las generaciones futuras.",
            storyNarrative = "La hambruna azotó a todo el mundo conocido, pero en Egipto había grano gracias a la previsión divina de José. Jacob envió a diez de sus hijos a comprar trigo. Al llegar, se postraron rostro en tierra ante el imponente gobernador egipcio sin reconocerlo. José los reconoció al instante, pero probó su arrepentimiento reteniendo a Simeón y exigiendo ver a Benjamín. En su segundo viaje, al ver la devoción y el ruego sacrificial de Judá ofreciéndose como esclavo en lugar de Benjamín, José no pudo contenerse más. Hizo salir a todos los egipcios y prorrumpió en llanto a gran voz: «¡Yo soy José! ¿Vive aún mi padre?». Sus hermanos quedaron mudos de espanto, pero José los abrazó con ternura: «No os entristezcáis... para preservación de vida me envió Dios delante de vosotros. Vosotros pensasteis mal contra mí, mas Dios lo encaminó a bien». Mandó traer a Jacob y a toda su familia para establecerlos en la fértil tierra de Gosén."
        ),
        ReadingPlanDay(
            dayNumber = 26,
            title = "Día 26: La Opresión en Egipto y el Rescate del Bebé Moisés",
            passagesSummary = "Éxodo 1-2",
            primaryBookId = 2,
            primaryChapter = 1,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(2, "Éxodo", 1),
                PlanPassageSegment(2, "Éxodo", 2),
            ),
            historicalContext = "Un nuevo faraón que no conocía a José esclavizó brutalmente a los hebreos y ordenó arrojar a los varones recién nacidos al Nilo. Jocabed escondió a Moisés en una canastilla de juncos calafateada con asfalto, y la propia hija del Faraón lo adoptó y crio en el palacio real.",
            spiritualLesson = "Dios preserva a sus siervos aun en medio de los edictos más oscuros. Aquello que el enemigo planeó para destruir a los hijos de Dios se convirtió en la cuna de su futuro libertador.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Éxodo 1-2), la narrativa bíblica nos presenta «La Opresión en Egipto y el Rescate del Bebé Moisés». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 27,
            title = "Día 27: La Zarza Ardiente: El Llamado en el Desierto de Madián",
            passagesSummary = "Éxodo 3-4",
            primaryBookId = 2,
            primaryChapter = 3,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(2, "Éxodo", 3),
                PlanPassageSegment(2, "Éxodo", 4),
            ),
            historicalContext = "Tras 40 años como pastor en el desierto de Sinaí, Moisés vio una zarza del desierto que ardía con fuego santo sin consumirse. Dios se reveló como 'YO SOY EL QUE SOY' (Yahvé), el Dios de Abraham, Isaac y Jacob, enviando a Moisés a liberar a su pueblo.",
            spiritualLesson = "La presencia de Dios no consume nuestra fragilidad, sino que la enciende con propósito eterno. Cuando Dios te llama, tus excusas de incapacidad quedan anuladas ante su soberano poder.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Éxodo 3-4), la narrativa bíblica nos presenta «La Zarza Ardiente: El Llamado en el Desierto de Madián». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 28,
            title = "Día 28: Moisés ante el Faraón y la Primera Plaga: El Nilo en Sangre",
            passagesSummary = "Éxodo 5-7",
            primaryBookId = 2,
            primaryChapter = 5,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(2, "Éxodo", 5),
                PlanPassageSegment(2, "Éxodo", 7),
            ),
            historicalContext = "Faraón desafió con soberbia: '¿Quién es Jehová para que yo oiga su voz?'. Dios inició los juicios contra el panteón egipcio. Al golpear el río Nilo (venerado como el dios Hapi, fuente de vida de Egipto), sus aguas se convirtieron en sangre, demostrando la supremacía absoluta del Dios de Israel.",
            spiritualLesson = "El orgullo humano que desafía a Dios será confrontado. Ninguna fuerza económica, política o religiosa de este mundo puede competir con el Creador del universo.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Éxodo 5-7), la narrativa bíblica nos presenta «Moisés ante el Faraón y la Primera Plaga: El Nilo en Sangre». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 29,
            title = "Día 29: Las Plagas sobre Egipto: Juicio contra los Falsos Dioses",
            passagesSummary = "Éxodo 8-10",
            primaryBookId = 2,
            primaryChapter = 8,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(2, "Éxodo", 8),
                PlanPassageSegment(2, "Éxodo", 10),
            ),
            historicalContext = "Ranas, piojos, moscas, peste en el ganado, úlceras, granizo devastador, langostas y densas tinieblas azotaron la tierra egipcia mientras en la región de Gosén, donde vivía Israel, había luz y protección divina. Los magos de Egipto tuvieron que confesar: 'Dedo de Dios es este'.",
            spiritualLesson = "Dios hace distinción entre su pueblo y las tinieblas de este mundo. Aun cuando todo a nuestro alrededor sea sacudido por el juicio o la confusión, en los hogares que honran a Dios hay paz y luz.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Éxodo 8-10), la narrativa bíblica nos presenta «Las Plagas sobre Egipto: Juicio contra los Falsos Dioses». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 30,
            title = "Día 30: La Primera Pascua y la Redención por la Sangre del Cordero",
            passagesSummary = "Éxodo 11-12",
            primaryBookId = 2,
            primaryChapter = 11,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(2, "Éxodo", 11),
                PlanPassageSegment(2, "Éxodo", 12),
            ),
            historicalContext = "Cada familia hebrea sacrificó un cordero perfecto de un año y untó su sangre en los dos postes y el dintel de las puertas. Cuando el juicio de la muerte de los primogénitos pasó por Egipto a medianoche, la sangre fue la señal de protección redentora.",
            spiritualLesson = "Jesucristo es nuestro Cordero Pascual sacrificado por nosotros (1 Co 5:7). No es por nuestras obras, sino por el valor de su preciosa sangre derramada que somos libres de condenación y muerte.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Éxodo 11-12), la narrativa bíblica nos presenta «La Primera Pascua y la Redención por la Sangre del Cordero». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 31,
            title = "Día 31: El Cruce Milagroso del Mar Rojo: Salvación y Triunfo",
            passagesSummary = "Éxodo 14",
            primaryBookId = 2,
            primaryChapter = 14,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(2, "Éxodo", 14),
            ),
            historicalContext = "Atrapados entre el Mar Rojo y el ejército enfurecido de Faraón con sus carros de guerra, el pueblo temió. Moisés proclamó: 'No temáis; estad firmes, y ved la salvación que Jehová hará hoy con vosotros'. Dios sopló un viento recio, abrió el mar en dos y el pueblo cruzó en seco.",
            spiritualLesson = "Cuando te encuentres acorralado sin salida aparente, no retrocedas al pasado ni te desesperes. Confía en el Señor: Él abrirá camino en medio de las aguas más profundas.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Éxodo 14), la narrativa bíblica nos presenta «El Cruce Milagroso del Mar Rojo: Salvación y Triunfo». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 32,
            title = "Día 32: El Cántico de Libertad, las Aguas de Mara y el Maná",
            passagesSummary = "Éxodo 15-16",
            primaryBookId = 2,
            primaryChapter = 15,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(2, "Éxodo", 15),
                PlanPassageSegment(2, "Éxodo", 16),
            ),
            historicalContext = "Tras entonar el cántico de victoria, llegaron a Mara donde el agua era amarga; Dios mostró un árbol que al ser arrojado al agua la endulzó (Yahvé-Rafa, tu sanador). En el desierto de Sin proveyó cada mañana pan del cielo ('maná') y codornices al atardecer.",
            spiritualLesson = "Dios tiene el poder de endulzar las amarguras más profundas de tu vida mediante la cruz. Su provisión no falla: busca cada día tu porción espiritual en su presencia.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Éxodo 15-16), la narrativa bíblica nos presenta «El Cántico de Libertad, las Aguas de Mara y el Maná». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 33,
            title = "Día 33: Agua de la Roca en Horeb y la Victoria sobre Amalec",
            passagesSummary = "Éxodo 17",
            primaryBookId = 2,
            primaryChapter = 17,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(2, "Éxodo", 17),
            ),
            historicalContext = "Moisés golpeó la roca en Horeb con su vara y brotaron torrentes de agua para la multitud sedienta. Poco después, Amalec atacó por la retaguardia; mientras Josué peleaba en el valle, Aarón y Hur sostenían en alto los brazos de Moisés en la colina hasta el triunfo final.",
            spiritualLesson = "Cristo es la Roca herida de donde fluye el agua de vida eterna. En la batalla espiritual, necesitamos apoyarnos mutuamente en oración e intercesión comunitaria.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Éxodo 17), la narrativa bíblica nos presenta «Agua de la Roca en Horeb y la Victoria sobre Amalec». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 34,
            title = "Día 34: El Sabio Consejo de Jetro: Delegación y Buen Liderazgo",
            passagesSummary = "Éxodo 18",
            primaryBookId = 2,
            primaryChapter = 18,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(2, "Éxodo", 18),
            ),
            historicalContext = "Moisés juzgaba los litigios del pueblo desde la mañana hasta la noche en soledad, desgastándose a sí mismo y al pueblo. Su suegro Jetro le aconsejó sabiamente nombrar hombres de verdad, temerosos de Dios y que aborrecieran la avaricia, para liderar sobre millares, centenas y decenas.",
            spiritualLesson = "El ministerio y el liderazgo saludable requieren humildad para delegar y trabajar en equipo. El aislamiento conduce al agotamiento; la sabiduría divina comparte la carga.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Éxodo 18), la narrativa bíblica nos presenta «El Sabio Consejo de Jetro: Delegación y Buen Liderazgo». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 35,
            title = "Día 35: La Teofanía en el Monte Sinaí y los Diez Mandamientos",
            passagesSummary = "Éxodo 19-20",
            primaryBookId = 2,
            primaryChapter = 19,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(2, "Éxodo", 19),
                PlanPassageSegment(2, "Éxodo", 20),
            ),
            historicalContext = "En medio de truenos, relámpagos, humo y sonido ensordecedor de shofar, Jehová descendió en fuego sobre el monte Sinaí. Proclamó con voz audible el Decálogo: los primeros cuatro mandamientos hacia Dios y los seis siguientes hacia el prójimo.",
            spiritualLesson = "La Ley moral de Dios refleja su santidad perfecta y su justicia inmutable. Jesucristo resumió la Ley en dos grandes pilares: amar a Dios con todo el corazón y amar al prójimo como a uno mismo.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Éxodo 19-20), la narrativa bíblica nos presenta «La Teofanía en el Monte Sinaí y los Diez Mandamientos». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 36,
            title = "Día 36: La Confirmación del Pacto y la Comunión en el Monte",
            passagesSummary = "Éxodo 24",
            primaryBookId = 2,
            primaryChapter = 24,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(2, "Éxodo", 24),
            ),
            historicalContext = "Moisés roció la sangre del pacto sobre el pueblo y sobre el libro de la Ley. Luego, Moisés, Aarón, Nadab, Abiú y setenta ancianos subieron al monte, 'vieron al Dios de Israel; y había debajo de sus pies como un embaldosado de zafiro', comieron y bebieron en su presencia santa.",
            spiritualLesson = "La sangre del pacto abre el camino para tener comunión íntima con un Dios santo. En Cristo, tenemos libre acceso al Lugar Santísimo para deleitarnos en su presencia.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Éxodo 24), la narrativa bíblica nos presenta «La Confirmación del Pacto y la Comunión en el Monte». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 37,
            title = "Día 37: El Becerro de Oro y la Apasionada Intercesión de Moisés",
            passagesSummary = "Éxodo 32",
            primaryBookId = 2,
            primaryChapter = 32,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(2, "Éxodo", 32),
            ),
            historicalContext = "Viendo que Moisés tardaba 40 días en la montaña, el pueblo exigió a Aarón dioses visibles de oro. Fundieron un becerro y danzaron desenfrenadamente. Moisés bajó, rompió las tablas de la ley ante su apostasía, pero luego intercedió: 'Si perdonas ahora su pecado... y si no, ráeme ahora de tu libro'.",
            spiritualLesson = "La idolatría surge cuando la impaciencia desplaza la fe en el Dios invisible. Los verdaderos líderes interceden con amor sacrificial por el pueblo antes que buscar su propia gloria.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Éxodo 32), la narrativa bíblica nos presenta «El Becerro de Oro y la Apasionada Intercesión de Moisés». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 38,
            title = "Día 38: Moisés ve la Gloria de Dios y su Rostro Resplandeciente",
            passagesSummary = "Éxodo 33-34",
            primaryBookId = 2,
            primaryChapter = 33,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(2, "Éxodo", 33),
                PlanPassageSegment(2, "Éxodo", 34),
            ),
            historicalContext = "Moisés rogó a Dios: 'Te ruego que me muestres tu gloria'. Dios lo colocó en la hendidura de la peña y proclamó su nombre: '¡Jehová! ¡Jehová! fuerte, misericordioso y piadoso; tardo para la ira, y grande en misericordia y verdad'. Al descender del monte, el rostro de Moisés resplandecía.",
            spiritualLesson = "Pasar tiempo en la presencia de Dios transforma nuestro rostro y nuestra vida entera. La gloria de Dios no es una fuerza abstracta, sino su infinita bondad y gracia manifestada.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Éxodo 33-34), la narrativa bíblica nos presenta «Moisés ve la Gloria de Dios y su Rostro Resplandeciente». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 39,
            title = "Día 39: La Gloria de Jehová Llena el Tabernáculo",
            passagesSummary = "Éxodo 40",
            primaryBookId = 2,
            primaryChapter = 40,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(2, "Éxodo", 40),
            ),
            historicalContext = "Siguiendo al detalle el diseño celestial que Dios reveló en el monte, el Tabernáculo fue erigido en el desierto. Cuando todo estuvo terminado, la nube cubrió el tabernáculo de reunión y la gloria de Jehová llenó el santuario de tal manera que Moisés no podía entrar.",
            spiritualLesson = "Cuando obedecemos con fidelidad y consagración la palabra de Dios, su presencia desciende para habitar entre nosotros. Hoy nosotros somos templo del Espíritu Santo.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Éxodo 40), la narrativa bíblica nos presenta «La Gloria de Jehová Llena el Tabernáculo». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 40,
            title = "Día 40: El Fuego Extraño de Nadab y Abiú: La Santidad de Dios",
            passagesSummary = "Levítico 10",
            primaryBookId = 3,
            primaryChapter = 10,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(3, "Levítico", 10),
            ),
            historicalContext = "Nadab y Abiú, hijos de Aarón y sacerdotes ungidos, tomaron sus incensarios y ofrecieron 'fuego extraño' que Dios nunca les mandó, actuando con ligereza y presunción ante el altar. Fuego salió de la presencia de Dios y murieron.",
            spiritualLesson = "Dios no puede ser adorado bajo nuestros propios caprichos humanos o invenciones carnales. 'En los que a mí se acercan me santificaré'. Acérquemonos al Señor con reverencia, temor santo y gratitud.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Levítico 10), la narrativa bíblica nos presenta «El Fuego Extraño de Nadab y Abiú: La Santidad de Dios». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 41,
            title = "Día 41: El Gran Día de la Expiación (Yom Kipur)",
            passagesSummary = "Levítico 16",
            primaryBookId = 3,
            primaryChapter = 16,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(3, "Levítico", 16),
            ),
            historicalContext = "Una sola vez al año, el sumo sacerdote entraba detrás del velo al Lugar Santísimo con sangre expiatoria por los pecados de toda la nación. Luego imponía sus manos sobre el macho cabrío vivo, confesando las iniquidades de Israel, y lo enviaba lejos al desierto (Azazel).",
            spiritualLesson = "Jesucristo es nuestro perfecto Sumo Sacerdote y a la vez el sacrificio supremo: su sangre nos limpia de todo pecado y aleja nuestras rebeliones tan lejos como está el oriente del occidente.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Levítico 16), la narrativa bíblica nos presenta «El Gran Día de la Expiación (Yom Kipur)». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 42,
            title = "Día 42: Las Codornices, la Murmuración y la Humildad de Moisés",
            passagesSummary = "Números 11-12",
            primaryBookId = 4,
            primaryChapter = 11,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(4, "Números", 11),
                PlanPassageSegment(4, "Números", 12),
            ),
            historicalContext = "El pueblo se quejó del maná añorando los ajos y cebollas de Egipto, y Dios envió codornices con juicio. Poco después, María y Aarón criticaron a Moisés por su esposa cusita y cuestionaron su autoridad. Dios defendió a Moisés destacando que era 'muy manso, más que todos los hombres'.",
            spiritualLesson = "La queja y la envidia ministerial ciegan el alma hacia las bendiciones recibidas. Cuando otros te critiquen injustamente, no busques venganza: deja que Dios sea tu defensor.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Números 11-12), la narrativa bíblica nos presenta «Las Codornices, la Murmuración y la Humildad de Moisés». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 43,
            title = "Día 43: Los Doce Espías en Canaán y el Informe de Fe de Caleb y Josué",
            passagesSummary = "Números 13-14",
            primaryBookId = 4,
            primaryChapter = 13,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(4, "Números", 13),
                PlanPassageSegment(4, "Números", 14),
            ),
            historicalContext = "Doce príncipes inspeccionaron Canaán durante 40 días. Diez espías sembraron terror diciendo: 'Éramos nosotros a nuestro parecer como langostas ante gigantes'. Pero Caleb y Josué proclamaron con valentía: 'Más podremos nosotros que ellos... Jehová está con nosotros, no los temáis'.",
            spiritualLesson = "El miedo mira el tamaño de los gigantes; la fe contempla la grandeza de Dios. No permitas que la incredulidad de la mayoría te robe las promesas que Dios ha diseñado para ti.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Números 13-14), la narrativa bíblica nos presenta «Los Doce Espías en Canaán y el Informe de Fe de Caleb y Josué». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 44,
            title = "Día 44: La Rebelión de Coré y la Vara Florecida de Aarón",
            passagesSummary = "Números 16-17",
            primaryBookId = 4,
            primaryChapter = 16,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(4, "Números", 16),
                PlanPassageSegment(4, "Números", 17),
            ),
            historicalContext = "Coré, Datán y Abiram levantaron a 250 líderes principales contra Moisés y Aarón por orgullo y ambición de poder. La tierra se abrió y los tragó. Para zanjar definitivamente toda disputa de liderazgo, Dios hizo que la vara seca de almendro de Aarón floreciera y diera almendras en una sola noche.",
            spiritualLesson = "La autoridad espiritual que proviene de Dios no necesita imponerse por manipulación política; da frutos milagrosos de vida donde antes solo había sequedad.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Números 16-17), la narrativa bíblica nos presenta «La Rebelión de Coré y la Vara Florecida de Aarón». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 45,
            title = "Día 45: La Serpiente de Bronce y la Burra de Balaam",
            passagesSummary = "Números 21-22",
            primaryBookId = 4,
            primaryChapter = 21,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(4, "Números", 21),
                PlanPassageSegment(4, "Números", 22),
            ),
            historicalContext = "Ante las mordeduras de serpientes ardientes por murmurar, Dios mandó a Moisés forjar una serpiente de bronce y levantarla en una asta: todo el que la miraba con fe quedaba sano. Luego, el profeta codicioso Balaam fue frenado por su propia burra al ver al Ángel de Jehová con espada desenvainada.",
            spiritualLesson = "Así como la serpiente de bronce fue levantada en el desierto, así el Hijo del Hombre fue levantado en la cruz para que todo aquel que en Él cree no se pierda (Jn 3:14). Dios usa hasta lo insólito para corregir nuestros extravíos.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Números 21-22), la narrativa bíblica nos presenta «La Serpiente de Bronce y la Burra de Balaam». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 46,
            title = "Día 46: La Muerte de Moisés en Nebo y el Mandato a Josué",
            passagesSummary = "Deuteronomio 34; Josué 1",
            primaryBookId = 5,
            primaryChapter = 34,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(5, "Deuteronomio", 34),
                PlanPassageSegment(6, "Josué", 1),
            ),
            historicalContext = "Dios llevó a Moisés a la cumbre del Pisga, le mostró toda la tierra prometida y lo sepultó. Luego dijo a Josué: 'Mi siervo Moisés ha muerto; ahora, pues, levántate y pasa este Jordán... Mira que te mando que te esfuerces y seas valiente; no temas ni desmayes'.",
            spiritualLesson = "Los líderes humanos terminan su carrera, pero la obra de Dios continúa con nuevas fuerzas. Aférrate a la Palabra de Dios día y noche: en ella reside la verdadera victoria y éxito espiritual.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Deuteronomio 34; Josué 1), la narrativa bíblica nos presenta «La Muerte de Moisés en Nebo y el Mandato a Josué». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 47,
            title = "Día 47: Los Espías en Jericó y la Fe Salvadora de Rahab",
            passagesSummary = "Josué 2",
            primaryBookId = 6,
            primaryChapter = 2,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(6, "Josué", 2),
            ),
            historicalContext = "Rahab, una mujer cananea en Jericó, reconoció al Dios verdadero tras oír sus maravillas en Egipto. Escondió a los dos espías hebreos en su tejado y selló un pacto atando un cordón de grana en su ventana, asegurando la salvación de toda su familia.",
            spiritualLesson = "No importa cuán oscuro haya sido el pasado de una persona, la gracia y la fe en Dios redimen por completo. Rahab no solo fue salva, sino que forma parte de la genealogía del Mesías (Mt 1:5).",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Josué 2), la narrativa bíblica nos presenta «Los Espías en Jericó y la Fe Salvadora de Rahab». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 48,
            title = "Día 48: El Paso Milagroso del Jordán y las Doce Piedras Conmemorativas",
            passagesSummary = "Josué 3-4",
            primaryBookId = 6,
            primaryChapter = 3,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(6, "Josué", 3),
                PlanPassageSegment(6, "Josué", 4),
            ),
            historicalContext = "En temporada de crecida de primavera cuando el río se desbordaba, los sacerdotes que cargaban el Arca del Pacto pusieron sus pies en el agua; al instante, las aguas se detuvieron en seco. Sacaron doce piedras del fondo del río para levantar un monumento en Gilgal.",
            spiritualLesson = "Para que las aguas se abran, primero hay que dar el paso de fe mojándose los pies. Levanta recordatorios en tu corazón de los milagros que Dios ha hecho para inspirar a tus hijos.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Josué 3-4), la narrativa bíblica nos presenta «El Paso Milagroso del Jordán y las Doce Piedras Conmemorativas». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 49,
            title = "Día 49: El Príncipe del Ejército de Jehová y la Caída de Jericó",
            passagesSummary = "Josué 5-6",
            primaryBookId = 6,
            primaryChapter = 5,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(6, "Josué", 5),
                PlanPassageSegment(6, "Josué", 6),
            ),
            historicalContext = "Antes de la batalla, Josué vio a un Varón con espada desenvainada: '¿Eres de los nuestros o de los enemigos?'. Él respondió: 'No; mas como Príncipe del ejército de Jehová he venido ahora'. Rodearon Jericó 7 días en silencio, tocaron trompetas, gritaron de júbilo y los muros cayeron.",
            spiritualLesson = "La pregunta no es si Dios está de nuestro lado, sino si nosotros estamos del lado de Dios. Las murallas espirituales más imponentes no se derriban con fuerza humana, sino con adoración y obediencia incondicional.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Josué 5-6), la narrativa bíblica nos presenta «El Príncipe del Ejército de Jehová y la Caída de Jericó». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 50,
            title = "Día 50: El Pecado Oculto de Acán y la Derrota en Hai",
            passagesSummary = "Josué 7",
            primaryBookId = 6,
            primaryChapter = 7,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(6, "Josué", 7),
            ),
            historicalContext = "Confiados en su victoria previa, enviaron solo 3,000 hombres a Hai y fueron derrotados vergonzosamente. Dios reveló que había anatema en el campamento: Acán había codiciado y escondido un manto babilónico y oro en su tienda. Al purificar el pecado en el valle de Acor, la victoria retornó.",
            spiritualLesson = "El pecado oculto debilita a toda la comunidad y estanca las bendiciones de Dios. No encubras tus faltas: confiésalas y apártate para alcanzar la misericordia y el favor de Dios.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Josué 7), la narrativa bíblica nos presenta «El Pecado Oculto de Acán y la Derrota en Hai». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 51,
            title = "Día 51: La Astucia de los Gabaonitas y el Sol Detenido sobre Gabaón",
            passagesSummary = "Josué 9-10",
            primaryBookId = 6,
            primaryChapter = 9,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(6, "Josué", 9),
                PlanPassageSegment(6, "Josué", 10),
            ),
            historicalContext = "Los gabaonitas fingieron venir de tierras muy lejanas con panes enmohecidos y odres viejos, engañando a los líderes de Israel porque 'no consultaron a Jehová'. Cuando cinco reyes amorreos atacaron a Gabaón, Josué oró con audacia y el sol se detuvo en el cielo por casi un día entero.",
            spiritualLesson = "Nunca tomes decisiones basándote solo en apariencias humanas sin orar y consultar a Dios. Al mismo tiempo, cuando estás en la voluntad del Señor, ten la audacia de orar por cosas extraordinarias.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Josué 9-10), la narrativa bíblica nos presenta «La Astucia de los Gabaonitas y el Sol Detenido sobre Gabaón». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 52,
            title = "Día 52: La Despedida de Josué: 'Yo y mi Casa Serviremos a Jehová'",
            passagesSummary = "Josué 24",
            primaryBookId = 6,
            primaryChapter = 24,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(6, "Josué", 24),
            ),
            historicalContext = "Anciano y tras repartir la tierra prometida a las doce tribus, Josué congregó a todo Israel en Siquem. Recordó la historia de la fidelidad de Dios e hizo un llamado frontal: 'Escogeos hoy a quién sirváis... pero yo y mi casa serviremos a Jehová'. El pueblo renovó su pacto solemne.",
            spiritualLesson = "La fe es una decisión diaria que debe modelarse primero en el hogar. No vivas en la tibieza ni en la indecisión: define hoy con firmeza a quién vas a consagrar tu vida y tu familia.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Josué 24), la narrativa bíblica nos presenta «La Despedida de Josué: 'Yo y mi Casa Serviremos a Jehová'». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 53,
            title = "Día 53: Aod el Juez Zurdo y la Liberación de Israel",
            passagesSummary = "Jueces 3",
            primaryBookId = 7,
            primaryChapter = 3,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(7, "Jueces", 3),
            ),
            historicalContext = "Tras caer en la idolatría y ser oprimidos 18 años por Eglón rey de Moab, Israel clamó a Dios. El Señor levantó a Aod ben-Gera, un hombre zurdo de Benjamín. Con una daga de dos filos escondida bajo su manto derecho, liberó a la nación en la sala secreta del palacio.",
            spiritualLesson = "Dios utiliza tus características particulares y lo que el mundo considera inusual o débil para lograr victorias asombrosas. Pon tus talentos y particularidades en las manos del Creador.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Jueces 3), la narrativa bíblica nos presenta «Aod el Juez Zurdo y la Liberación de Israel». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 54,
            title = "Día 54: Débora la Profetisa y Jael con la Estaca",
            passagesSummary = "Jueces 4-5",
            primaryBookId = 7,
            primaryChapter = 4,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(7, "Jueces", 4),
                PlanPassageSegment(7, "Jueces", 5),
            ),
            historicalContext = "Bajo el mando del opresor Sísara y sus 900 carros de hierro, Débora profetisa juzgaba a Israel con valentía espiritual bajo su palmera. Impulsó a Barac a la batalla, y Dios desbarató el ejército enemigo. Sísara huyó a la tienda de Jael, quien lo abatió con una estaca.",
            spiritualLesson = "Dios levanta a mujeres y hombres con valentía y discernimiento espiritual para guiar en tiempos de crisis. La gloria de la victoria pertenece enteramente al Señor.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Jueces 4-5), la narrativa bíblica nos presenta «Débora la Profetisa y Jael con la Estaca». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 55,
            title = "Día 55: El Llamado de Gedeón y la Señal del Vellón",
            passagesSummary = "Jueces 6",
            primaryBookId = 7,
            primaryChapter = 6,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(7, "Jueces", 6),
            ),
            historicalContext = "Los madianitas dejaban a Israel en extrema miseria. Gedeón estaba sacudiendo el trigo en un lagar oculto para esconderlo cuando el Ángel de Jehová se le apareció: 'Jehová está contigo, varón esforzado y valiente'. Gedeón derribó el altar de Baal y pidió confirmación con el rocío y el vellón de lana.",
            spiritualLesson = "Dios no te ve según tus temores actuales o tu origen humilde, sino según lo que llegarás a ser por el poder de su Espíritu. Cree en la identidad que Dios declara sobre tu vida.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Jueces 6), la narrativa bíblica nos presenta «El Llamado de Gedeón y la Señal del Vellón». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 56,
            title = "Día 56: La Victoria de los 300 de Gedeón: Antorchas y Cántaros",
            passagesSummary = "Jueces 7",
            primaryBookId = 7,
            primaryChapter = 7,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(7, "Jueces", 7),
            ),
            historicalContext = "De 32,000 hombres, Dios redujo el ejército a 10,000 (los temerosos se fueron) y luego a solo 300 que lamieron el agua como perro en el río. En la noche, armados solo con trompetas, cántaros vacíos y antorchas encendidas, gritaron: '¡Por la espada de Jehová y de Gedeón!'. Los madianitas huyeron en caos.",
            spiritualLesson = "La victoria espiritual no depende de la multitud de recursos, sino de la pureza y obediencia de los escogidos. Deja que se rompa el cántaro de tu orgullo para que resplandezca la luz de Dios.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Jueces 7), la narrativa bíblica nos presenta «La Victoria de los 300 de Gedeón: Antorchas y Cántaros». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 57,
            title = "Día 57: La Ambición de Abimelec y la Parábola de los Árboles de Jotam",
            passagesSummary = "Jueces 9",
            primaryBookId = 7,
            primaryChapter = 9,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(7, "Jueces", 9),
            ),
            historicalContext = "Abimelec asesinó a sus 70 hermanos para autoproclamarse rey en Siquem. El único sobreviviente, Jotam, proclamó desde el monte Gerizim la famosa parábola donde el olivo, la higuera y la vid rechazan reinar para dar fruto, mientras la espina inútil acepta y devora a todos con fuego.",
            spiritualLesson = "La ambición carnal y el deseo de poder destruyen vidas e instituciones. Aquellos que producen fruto genuino no necesitan vanagloria; buscan servir humildemente para edificar a otros.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Jueces 9), la narrativa bíblica nos presenta «La Ambición de Abimelec y la Parábola de los Árboles de Jotam». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 58,
            title = "Día 58: Jefté el Marginado y su Voto Precipitado",
            passagesSummary = "Jueces 11",
            primaryBookId = 7,
            primaryChapter = 11,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(7, "Jueces", 11),
            ),
            historicalContext = "Jefté, hijo de una ramera y expulsado por sus hermanos, fue buscado como líder militar cuando los amonitas atacaron. El Espíritu del Señor vino sobre él, pero hizo un voto imprudente y apresurado prometiendo sacrificar lo primero que saliera de su casa si vencía, saliendo su única hija.",
            spiritualLesson = "Dios no necesita promesas precipitadas o votos imprudentes nacidos de la ansiedad; Él busca obediencia sabia y corazones sinceros. Cuida tus palabras y no hagas promesas a la ligera.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Jueces 11), la narrativa bíblica nos presenta «Jefté el Marginado y su Voto Precipitado». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 59,
            title = "Día 59: El Nacimiento de Sansón y el Voto Nazareo",
            passagesSummary = "Jueces 13-14",
            primaryBookId = 7,
            primaryChapter = 13,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(7, "Jueces", 13),
                PlanPassageSegment(7, "Jueces", 14),
            ),
            historicalContext = "El Ángel de Jehová se apareció a la esposa estéril de Manoa prometiéndole un hijo que comenzaría a salvar a Israel de los filisteos, bajo voto de nazareato (no beber vino, no tocar cadáveres ni cortar su cabello). De joven, Sansón despedazó un león cachorro como a un cabrito con sus manos desnudas.",
            spiritualLesson = "Toda consagración a Dios tiene un propósito santo y requiere pureza personal. Los dones espirituales extraordinarios deben estar acompañados de dominio propio para no extraviarse.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Jueces 13-14), la narrativa bíblica nos presenta «El Nacimiento de Sansón y el Voto Nazareo». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 60,
            title = "Día 60: Sansón, las Zorras y la Quijada de Asno",
            passagesSummary = "Jueces 15",
            primaryBookId = 7,
            primaryChapter = 15,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(7, "Jueces", 15),
            ),
            historicalContext = "Engañado por su suegro filisteo, Sansón cazó 300 zorras, les ató teas encendidas por la cola y quemó los trigales de los filisteos. Más tarde en Lehi, atado con cuerdas nuevas, el Espíritu de Dios vino sobre él: rompió las cuerdas y mató a 1,000 filisteos con la quijada fresca de un asno.",
            spiritualLesson = "Dios puede usar los instrumentos más inverosímiles (una simple quijada de asno) para derribar a los opresores cuando su Espíritu unge a una persona. Nuestra suficiencia proviene de Dios.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Jueces 15), la narrativa bíblica nos presenta «Sansón, las Zorras y la Quijada de Asno». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 61,
            title = "Día 61: Sansón y Dalila: Caída, Ceguera y Victoria Final",
            passagesSummary = "Jueces 16",
            primaryBookId = 7,
            primaryChapter = 16,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(7, "Jueces", 16),
            ),
            historicalContext = "Sansón jugó con el pecado en el regazo de Dalila hasta revelarle el secreto de su consagración nazarea. Al cortarle el cabello, 'él no sabía que Jehová ya se había apartado de él'. Ciego y esclavo en Gaza, oró con clamor final y derribó las columnas del templo de Dagón con más bajas que en toda su vida.",
            spiritualLesson = "Jugar con la tentación termina costando los ojos, la libertad y la dignidad. Nunca descuides tu relación íntima con Dios; aun en la caída, el arrepentimiento sincero halla gracia para terminar con honra.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Jueces 16), la narrativa bíblica nos presenta «Sansón y Dalila: Caída, Ceguera y Victoria Final». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 62,
            title = "Día 62: La Fidelidad de Rut: 'Tu Pueblo Será mi Pueblo y tu Dios mi Dios'",
            passagesSummary = "Rut 1-2",
            primaryBookId = 8,
            primaryChapter = 1,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(8, "Rut", 1),
                PlanPassageSegment(8, "Rut", 2),
            ),
            historicalContext = "Noemí regresó de Moab viuda y sin sus dos hijos, con el alma amargada. Su nuera Rut, mujer moabita, rehusó abandonarla con una declaración inolvidable de amor leal (Hesed): 'Dondequiera que tú fueres, iré yo... tu Dios será mi Dios'. Llegó a Belén y espigó en el campo del noble Booz.",
            spiritualLesson = "La lealtad y el amor desinteresado nunca pasan desapercibidos ante Dios. En las épocas oscuras de la vida, las decisiones de fe genuina abren puertas de redención insospechadas.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Rut 1-2), la narrativa bíblica nos presenta «La Fidelidad de Rut: 'Tu Pueblo Será mi Pueblo y tu Dios mi Dios'». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 63,
            title = "Día 63: Booz el Pariente Redentor y la Boda en Belén",
            passagesSummary = "Rut 3-4",
            primaryBookId = 8,
            primaryChapter = 3,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(8, "Rut", 3),
                PlanPassageSegment(8, "Rut", 4),
            ),
            historicalContext = "Siguiendo el consejo de Noemí, Rut se presentó en la era a los pies de Booz pidiendo protección bajo sus alas. Booz asumió legalmente el rol de 'Goel' (Pariente Redentor), redimió la heredad y se casó con Rut. De su unión nació Obed, abuelo del rey David.",
            spiritualLesson = "Booz es un tipo glorioso de Cristo, nuestro Pariente Redentor que paga el precio de nuestro rescate y nos introduce en su familia real eterna con honra y dignidad.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Rut 3-4), la narrativa bíblica nos presenta «Booz el Pariente Redentor y la Boda en Belén». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 64,
            title = "Día 64: La Oración Silenciosa de Ana y el Nacimiento de Samuel",
            passagesSummary = "1 Samuel 1-2",
            primaryBookId = 9,
            primaryChapter = 1,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(9, "1 Samuel", 1),
                PlanPassageSegment(9, "1 Samuel", 2),
            ),
            historicalContext = "Afligida por su esterilidad y provocada por Penina, Ana derramó su alma en lágrimas en el tabernáculo de Silo moviendo solo sus labios. Prometió entregar a su hijo a Jehová todos los días de su vida. Dios escuchó su oración y nació Samuel; Ana entonó un cántico de adoración magnífico.",
            spiritualLesson = "Cuando el dolor te abrume, no te amargues contra los demás: derrama tu corazón en oración ante el Señor. Lo que consagras a Dios en gratitud se convierte en bendición para naciones enteras.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (1 Samuel 1-2), la narrativa bíblica nos presenta «La Oración Silenciosa de Ana y el Nacimiento de Samuel». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 65,
            title = "Día 65: La Voz de Dios al Niño Samuel en la Noche",
            passagesSummary = "1 Samuel 3",
            primaryBookId = 9,
            primaryChapter = 3,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(9, "1 Samuel", 3),
            ),
            historicalContext = "En días en que la palabra de Dios escaseaba y las visiones no eran frecuentes, el joven Samuel dormía en el tabernáculo cerca del Arca. Dios lo llamó tres veces por su nombre; guiado por el anciano sacerdote Elí, Samuel respondió: 'Habla, Jehová, porque tu siervo oye'.",
            spiritualLesson = "Aprende a cultivar oídos atentos y dispuestos para la voz de Dios en medio del ruido del mundo. Una actitud de sumisión y reverencia nos capacita para ser portavoces de su verdad.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (1 Samuel 3), la narrativa bíblica nos presenta «La Voz de Dios al Niño Samuel en la Noche». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 66,
            title = "Día 66: La Captura del Arca de Dios y la Tragedia de Icabod",
            passagesSummary = "1 Samuel 4-5",
            primaryBookId = 9,
            primaryChapter = 4,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(9, "1 Samuel", 4),
                PlanPassageSegment(9, "1 Samuel", 5),
            ),
            historicalContext = "Israel usó el Arca del Pacto como un amuleto mágico de guerra contra los filisteos sin arrepentirse de sus pecados. Fueron derrotados, murieron Ofni y Fines, y el sacerdote Elí cayó muerto. La esposa de Fines dio a luz a Icabod ('Traspasada es la gloria'). Pero en Asdod, el ídolo Dagón cayó decapitado ante el Arca.",
            spiritualLesson = "Dios no puede ser manipulado como un talismán de conveniencia. Los ritos religiosos externos sin santidad de corazón carecen de poder; Dios defiende su propia gloria por encima de los ídolos.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (1 Samuel 4-5), la narrativa bíblica nos presenta «La Captura del Arca de Dios y la Tragedia de Icabod». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 67,
            title = "Día 67: El Regreso Milagroso del Arca y la Piedra Eben-ezer",
            passagesSummary = "1 Samuel 6-7",
            primaryBookId = 9,
            primaryChapter = 6,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(9, "1 Samuel", 6),
                PlanPassageSegment(9, "1 Samuel", 7),
            ),
            historicalContext = "Plagas de tumores forzaron a los filisteos a devolver el Arca en un carro nuevo tirado por vacas sin yugo que caminaron directo a Israel mugiendo sin desviarse. Samuel llamó al pueblo al arrepentimiento en Mizpa, derrotaron a los filisteos y colocó una piedra: 'Eben-ezer' (Hasta aquí nos ayudó Jehová).",
            spiritualLesson = "El verdadero avivamiento comienza con renunciar a los ídolos y volver el corazón entero al Señor. Hasta hoy Dios ha sido nuestro socorro inagotable.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (1 Samuel 6-7), la narrativa bíblica nos presenta «El Regreso Milagroso del Arca y la Piedra Eben-ezer». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 68,
            title = "Día 68: El Pueblo Pide un Rey y la Unción de Saúl",
            passagesSummary = "1 Samuel 8-10",
            primaryBookId = 9,
            primaryChapter = 8,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(9, "1 Samuel", 8),
                PlanPassageSegment(9, "1 Samuel", 10),
            ),
            historicalContext = "Ancianos de Israel exigieron a Samuel: 'Constitúyenos ahora un rey que nos juzgue, como tienen todas las naciones', rechazando el reinado directo de Dios sobre ellos. Dios concedió su petición y Samuel ungió en secreto a Saúl, un joven alto y bien parecido de la tribu de Benjamín.",
            spiritualLesson = "Cuidado con desear conformarte a los patrones de este mundo en lugar de someterte al señorío de Dios. Lo que parece atractivo por fuera puede traer ataduras si nace del rechazo a Dios.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (1 Samuel 8-10), la narrativa bíblica nos presenta «El Pueblo Pide un Rey y la Unción de Saúl». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 69,
            title = "Día 69: La Impaciencia de Saúl y su Rechazo ante Amalec",
            passagesSummary = "1 Samuel 13-15",
            primaryBookId = 9,
            primaryChapter = 13,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(9, "1 Samuel", 13),
                PlanPassageSegment(9, "1 Samuel", 15),
            ),
            historicalContext = "Acorralado en Gilgal, Saúl se impacientó y ofreció holocaustos sin esperar al profeta Samuel. Más tarde, perdonó al rey Agag y a lo mejor de las ovejas de Amalec bajo pretexto de sacrificio. Samuel proclamó: 'El obedecer es mejor que los sacrificios... por cuanto tú desechaste la palabra de Jehová, él también te ha desechado para que no seas rey'.",
            spiritualLesson = "La obediencia parcial es desobediencia total a los ojos de Dios. Dios no busca rituales religiosos exteriores para encubrir la rebeldía del corazón; Él demanda fidelidad sincera.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (1 Samuel 13-15), la narrativa bíblica nos presenta «La Impaciencia de Saúl y su Rechazo ante Amalec». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 70,
            title = "Día 70: David es Ungido en Belén y Calma el Espíritu de Saúl",
            passagesSummary = "1 Samuel 16",
            primaryBookId = 9,
            primaryChapter = 16,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(9, "1 Samuel", 16),
            ),
            historicalContext = "Dios envió a Samuel a casa de Isaí en Belén advirtiéndole: 'El hombre mira lo que está delante de sus ojos, pero Jehová mira el corazón'. Descartaron a los siete hijos mayores hasta llamar al menor, David, que pastoreaba las ovejas. Fue ungido, el Espíritu vino sobre él y con su arpa tocaba para calmar a Saúl.",
            spiritualLesson = "Tu valor y llamado no dependen del reconocimiento humano ni de la estatura física, sino de lo que Dios ve en tu corazón cuando nadie te está mirando en el anonimato.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (1 Samuel 16), la narrativa bíblica nos presenta «David es Ungido en Belén y Calma el Espíritu de Saúl». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 71,
            title = "Día 71: David y Goliat en el Valle de Ela: El Nombre de Jehová",
            passagesSummary = "1 Samuel 17",
            primaryBookId = 9,
            primaryChapter = 17,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(9, "1 Samuel", 17),
            ),
            historicalContext = "El gigante Goliat de Gat desafió con insultos al ejército de Israel durante 40 días infundiendo pánico. El joven pastor David, indignado por la blasfemia contra el Dios vivo, rechazó la armadura de Saúl, tomó cinco piedras lisas de su arroyo y proclamó: 'Tú vienes a mí con espada y lanza y jabalina; mas yo vengo a ti en el nombre de Jehová de los ejércitos'.",
            spiritualLesson = "No enfrentes tus batallas con las armas carnales de este mundo. Cuando peleas en el nombre de Dios por su honra, los gigantes que te amenazan caerán vencidos ante ti.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (1 Samuel 17), la narrativa bíblica nos presenta «David y Goliat en el Valle de Ela: El Nombre de Jehová». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 72,
            title = "Día 72: El Pacto de Amistad Leal entre David y Jonatán",
            passagesSummary = "1 Samuel 18-20",
            primaryBookId = 9,
            primaryChapter = 18,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(9, "1 Samuel", 18),
                PlanPassageSegment(9, "1 Samuel", 20),
            ),
            historicalContext = "El alma de Jonatán, heredero legítimo al trono de Saúl, quedó ligada a la de David amándolo como a su propia vida. Jonatán se quitó su manto real, su espada y su arco para entregárselos a David en señal de pacto, defendiéndolo ante la demencial ira homicida de su padre Saúl con señales de flechas.",
            spiritualLesson = "La verdadera amistad bíblica es sacrificial, leal y celebra el llamado de Dios en el otro sin envidias ni celos mezquinos. Un amigo fiel es un refugio seguro enviado por Dios.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (1 Samuel 18-20), la narrativa bíblica nos presenta «El Pacto de Amistad Leal entre David y Jonatán». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 73,
            title = "Día 73: David Fugitivo: El Pan Sagrado y la Cueva de Adulam",
            passagesSummary = "1 Samuel 21-22",
            primaryBookId = 9,
            primaryChapter = 21,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(9, "1 Samuel", 21),
                PlanPassageSegment(9, "1 Samuel", 22),
            ),
            historicalContext = "Huyendo de Saúl, David recibió del sacerdote Ahimelec en Nob el pan sagrado de la proposición y la espada de Goliat. Luego se refugió en la cueva de Adulam, donde se le unieron 400 hombres amargados, endeudados y en aflicción, a quienes David transformó en valientes guerreros.",
            spiritualLesson = "Aun los momentos de mayor necesidad e incertidumbre forman el carácter de los siervos de Dios. Dios toma a personas quebrantadas y rechazadas para convertirlas en un ejército de fe y victoria.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (1 Samuel 21-22), la narrativa bíblica nos presenta «David Fugitivo: El Pan Sagrado y la Cueva de Adulam». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 74,
            title = "Día 74: David Perdona la Vida a Saúl en la Cueva de En-gadi",
            passagesSummary = "1 Samuel 24",
            primaryBookId = 9,
            primaryChapter = 24,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(9, "1 Samuel", 24),
            ),
            historicalContext = "Con 3,000 soldados escogidos persiguiendo a David en los peñascos de las cabras monteses, Saúl entró solo a una cueva para sus necesidades, sin saber que David y sus hombres estaban en el fondo. Los hombres de David le dijeron que Dios había puesto a su enemigo en sus manos, pero David solo cortó la orilla de su manto: 'Jehová me guarde de alzar mi mano contra el ungido de Jehová'.",
            spiritualLesson = "Nunca tomes la justicia en tus propias manos ni te apresures a forzar las promesas de Dios. Aprende a esperar los tiempos del Señor; quien confía en Dios no necesita destruir a sus perseguidores.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (1 Samuel 24), la narrativa bíblica nos presenta «David Perdona la Vida a Saúl en la Cueva de En-gadi». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 75,
            title = "Día 75: Nabal el Insensato y la Sabia Intercesión de Abigail",
            passagesSummary = "1 Samuel 25",
            primaryBookId = 9,
            primaryChapter = 25,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(9, "1 Samuel", 25),
            ),
            historicalContext = "David protegió los rebaños de Nabal en el desierto de Parán, pero en la esquila Nabal lo insultó y negó provisiones con mezquindad. David marchó furioso a destruirlo, pero la prudente y hermosa Abigail preparó víveres, salió al encuentro, cayó a los pies de David y detuvo el derramamiento de sangre.",
            spiritualLesson = "Una palabra blanda y llena de sabiduría divina apaga la ira más feroz y previene tragedias irreparables. Procura ser pacificador en los momentos de mayor tensión comunitaria.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (1 Samuel 25), la narrativa bíblica nos presenta «Nabal el Insensato y la Sabia Intercesión de Abigail». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 76,
            title = "Día 76: Saúl en Endor y la Tragedia del Monte Gilboa",
            passagesSummary = "1 Samuel 28; 31",
            primaryBookId = 9,
            primaryChapter = 28,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(9, "1 Samuel", 28),
                PlanPassageSegment(9, "31", 1),
            ),
            historicalContext = "Viendo al ejército filisteo y sin respuesta de Dios por sueños ni profetas, Saúl consultó en tinieblas a una mujer con espíritu de adivinación en Endor, consumando su ruina espiritual. Al día siguiente en la batalla del monte Gilboa, murieron Jonatán y los hijos de Saúl, y el rey herido se arrojó sobre su espada.",
            spiritualLesson = "Buscar respuestas en el ocultismo o en la sabiduría terrenal cuando se ha desobedecido a Dios conduce a la ruina total. Mantente firme en la luz y busca al Señor mientras puede ser hallado.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (1 Samuel 28; 31), la narrativa bíblica nos presenta «Saúl en Endor y la Tragedia del Monte Gilboa». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 77,
            title = "Día 77: El Lamento de David y su Coronación en Hebrón",
            passagesSummary = "2 Samuel 1-2",
            primaryBookId = 10,
            primaryChapter = 1,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(10, "2 Samuel", 1),
                PlanPassageSegment(10, "2 Samuel", 2),
            ),
            historicalContext = "Al recibir la noticia de la muerte de Saúl y Jonatán, David no se alegró por la desaparición de su perseguidor; rasgó sus vestidos, lloró, ayunó y compuso el conmovedor cántico del Arco: '¡Cómo han caído los valientes en medio de la batalla!'. Luego fue ungido rey sobre Judá en Hebrón.",
            spiritualLesson = "La madurez espiritual se demuestra en no regocijarse ante la caída del enemigo y en honrar a los caídos. Dios ensalza a los humildes y los corona en su tiempo propicio.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (2 Samuel 1-2), la narrativa bíblica nos presenta «El Lamento de David y su Coronación en Hebrón». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 78,
            title = "Día 78: La Conquista de Jerusalén y el Traslado Festivo del Arca",
            passagesSummary = "2 Samuel 5-6",
            primaryBookId = 10,
            primaryChapter = 5,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(10, "2 Samuel", 5),
                PlanPassageSegment(10, "2 Samuel", 6),
            ),
            historicalContext = "David conquistó la fortaleza de Sión a los jebuseos y estableció a Jerusalén como capital de la nación unida. Luego trajo el Arca con 30,000 escogidos; tras la muerte de Uza por tocar irreverentemente el Arca en carroza, David corrigió el transporte sobre hombros de levitas y danzó con toda su fuerza con júbilo.",
            spiritualLesson = "Las cosas santas de Dios deben ser tratadas con la santidad y reverencia que Él ordenó. Una verdadera adoración se entrega sin reservas ni vanidad ante la majestad divina.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (2 Samuel 5-6), la narrativa bíblica nos presenta «La Conquista de Jerusalén y el Traslado Festivo del Arca». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 79,
            title = "Día 79: El Pacto Davídico: Una Casa Eterna para Dios",
            passagesSummary = "2 Samuel 7",
            primaryBookId = 10,
            primaryChapter = 7,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(10, "2 Samuel", 7),
            ),
            historicalContext = "Viviendo en su palacio de cedro, David deseó edificar una casa fija para el Arca de Dios. El profeta Natán le transmitió el oráculo divino: no sería David quien le edificaría casa, sino que Dios le edificaría a David una dinastía eterna: 'Tu trono será establecido para siempre', señalando proféticamente al Rey Mesías.",
            spiritualLesson = "Cuando anhelas honrar a Dios con generosidad, Dios te responde con promesas eternas que superan cualquier imaginación humana. Cristo es el Rey eterno de la casa de David.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (2 Samuel 7), la narrativa bíblica nos presenta «El Pacto Davídico: Una Casa Eterna para Dios». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 80,
            title = "Día 80: David y Mefiboset: Gracia Sobrenatural en Lodebar",
            passagesSummary = "2 Samuel 9",
            primaryBookId = 10,
            primaryChapter = 9,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(10, "2 Samuel", 9),
            ),
            historicalContext = "David preguntó con corazón agradecido: '¿Ha quedado alguno de la casa de Saúl, a quien haga yo misericordia por amor de Jonatán?'. Trajo a Mefiboset, lisiado de ambos pies que vivía en Lodebar ('tierra de olvido'), le devolvió todas las tierras de su abuelo y lo sentó a comer a la mesa real como a un hijo.",
            spiritualLesson = "Esta es una hermosa pintura de la gracia salvadora de Dios: éramos lisiados espirituales y marginados, pero por el pacto de Cristo fuimos adoptados para sentarnos a la mesa del Rey.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (2 Samuel 9), la narrativa bíblica nos presenta «David y Mefiboset: Gracia Sobrenatural en Lodebar». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 81,
            title = "Día 81: La Caída con Betsabé y el Dedo Profético de Natán",
            passagesSummary = "2 Samuel 11-12",
            primaryBookId = 10,
            primaryChapter = 11,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(10, "2 Samuel", 11),
                PlanPassageSegment(10, "2 Samuel", 12),
            ),
            historicalContext = "En el tiempo en que los reyes salían a la guerra, David se quedó ocioso en Jerusalén. Vio a Betsabé, cometió adulterio y urdió el asesinato en el frente de batalla de su fiel soldado Urías heteo. El profeta Natán lo encaró con la parábola de la corderita: '¡Tú eres aquel hombre!'. David se quebrantó en arrepentimiento (Salmo 51).",
            spiritualLesson = "La ociosidad y el orgullo abren la puerta al pecado más destructor. Dios perdona el arrepentimiento sincero, pero el pecado siempre deja cicatrices y consecuencias dolorosas.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (2 Samuel 11-12), la narrativa bíblica nos presenta «La Caída con Betsabé y el Dedo Profético de Natán». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 82,
            title = "Día 82: La Rebelión de Absalón y el Llanto de un Padre",
            passagesSummary = "2 Samuel 15-18",
            primaryBookId = 10,
            primaryChapter = 15,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(10, "2 Samuel", 15),
                PlanPassageSegment(10, "2 Samuel", 18),
            ),
            historicalContext = "Absalón, hijo de David, robó el corazón de Israel con halagos a las puertas de la ciudad y dio un golpe de estado. David huyó descalzo por el monte de los Olivos llorando. En la batalla del bosque de Efraín, la cabellera de Absalón quedó atrapada en una encina y Joab lo mató. David lloró amargamente: '¡Hijo mío Absalón, hijo mío, hijo mío Absalón!'.",
            spiritualLesson = "La vanagloria y la rebelión contra los padres y las autoridades acarrean desenlaces trágicos. El dolor del corazón paternal refleja el amor entrañable de Dios por sus hijos extraviados.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (2 Samuel 15-18), la narrativa bíblica nos presenta «La Rebelión de Absalón y el Llanto de un Padre». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 83,
            title = "Día 83: El Censo de David y el Altar en la Era de Arauna",
            passagesSummary = "2 Samuel 24",
            primaryBookId = 10,
            primaryChapter = 24,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(10, "2 Samuel", 24),
            ),
            historicalContext = "Movido por orgullo militar, David ordenó censar a los hombres de guerra de Israel, trayendo juicio sobre la nación. Al ver al ángel exterminador sobre Jerusalén, David se arrepintió y compró la era de Arauna jebuseo por su justo precio ('no ofreceré a Jehová holocaustos que no me cuesten nada') y erigió un altar.",
            spiritualLesson = "Nuestra seguridad descansa en Dios y no en el número de nuestras fuerzas materiales. La verdadera adoración y consagración siempre implican un costo real del corazón.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (2 Samuel 24), la narrativa bíblica nos presenta «El Censo de David y el Altar en la Era de Arauna». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 84,
            title = "Día 84: La Ascensión de Salomón y su Petición de Sabiduría",
            passagesSummary = "1 Reyes 1-3",
            primaryBookId = 11,
            primaryChapter = 1,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(11, "1 Reyes", 1),
                PlanPassageSegment(11, "1 Reyes", 3),
            ),
            historicalContext = "En Gabaón, el gran lugar alto de adoración antes del Templo, Salomón ofreció mil holocaustos. Dios se le apareció en sueños esa noche: 'Pide lo que quieras que yo te dé'. Joven rey ante un pueblo numeroso, Salomón no pidió riquezas, larga vida ni la muerte de sus enemigos, sino 'un corazón entendido para juzgar y discernir entre lo bueno y lo malo'.",
            spiritualLesson = "La verdadera sabiduría comienza con el temor reverente a Dios y la humildad para reconocer nuestra necesidad. Cuando priorizas el reino de Dios y su justicia, todas las demás cosas son añadidas por su gracia.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (1 Reyes 1-3), la narrativa bíblica nos presenta «La Ascensión de Salomón y su Petición de Sabiduría». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 85,
            title = "Día 85: El Juicio Sabio de Salomón y las Dos Madres",
            passagesSummary = "1 Reyes 3",
            primaryBookId = 11,
            primaryChapter = 3,
            primaryVerse = 16,
            passages = listOf(
                PlanPassageSegment(11, "1 Reyes", 3),
            ),
            historicalContext = "Dos mujeres que habitaban en la misma casa comparecieron ante el tribunal real disputando la maternidad de un único bebé vivo tras la muerte nocturna del otro por asfixia accidental. Sin testigos disponibles, Salomón ordenó partir al niño vivo en dos con una espada; el amor entrañable de la verdadera madre reveló de inmediato su identidad al preferir cederlo viva a otra.",
            spiritualLesson = "La sabiduría divina penetra hasta las intenciones más profundas del corazón humano. El amor genuino es sacrificial y busca siempre la preservación y el bien supremo del ser amado.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (1 Reyes 3), la narrativa bíblica nos presenta «El Juicio Sabio de Salomón y las Dos Madres». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 86,
            title = "Día 86: La Construcción y Dedicación Solemne del Templo",
            passagesSummary = "1 Reyes 6-8",
            primaryBookId = 11,
            primaryChapter = 6,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(11, "1 Reyes", 6),
                PlanPassageSegment(11, "1 Reyes", 8),
            ),
            historicalContext = "Construido en el monte Moriah con cedro del Líbano, oro purísimo y piedras labradas en la cantera sin ruido de herramientas en el lugar santo, el Templo fue dedicado en la fiesta de los Tabernáculos. Tras la conmovedora oración de Salomón, la nube de la gloria de Jehová llenó el templo de tal forma que los sacerdotes no podían ministrar.",
            spiritualLesson = "Dios no habita en templos hechos por manos humanas, pero se complace en descender donde hay corazones que lo buscan en santidad y unidad de adoración. Hoy tu cuerpo es templo del Espíritu Santo.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (1 Reyes 6-8), la narrativa bíblica nos presenta «La Construcción y Dedicación Solemne del Templo». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 87,
            title = "Día 87: La Visita y el Asombro de la Reina de Sabá",
            passagesSummary = "1 Reyes 10",
            primaryBookId = 11,
            primaryChapter = 10,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(11, "1 Reyes", 10),
            ),
            historicalContext = "La gobernante de Sabá (actual Yemen/Etiopía) viajó más de 2,000 kilómetros con una gran caravana de camellos cargados de especias finas, oro y piedras preciosas para poner a prueba la sabiduría de Salomón con preguntas difíciles. Al ver la magnificencia, el orden de su corte y su relación con Dios, exclamó sin aliento: '¡Ni la mitad se me había dicho!'.",
            spiritualLesson = "Cuando la presencia de Dios llena la vida de un creyente, su fruto de sabiduría y excelencia atrae a los que están lejos a glorificar al Señor. Cristo dijo: 'Y he aquí más que Salomón en este lugar' (Mt 12:42).",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (1 Reyes 10), la narrativa bíblica nos presenta «La Visita y el Asombro de la Reina de Sabá». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 88,
            title = "Día 88: La Idolatría de Salomón y la Fractura del Reino",
            passagesSummary = "1 Reyes 11-12",
            primaryBookId = 11,
            primaryChapter = 11,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(11, "1 Reyes", 11),
                PlanPassageSegment(11, "1 Reyes", 12),
            ),
            historicalContext = "En su vejez, influenciado por alianzas políticas y 700 esposas extranjeras, el corazón de Salomón se desvió tras dioses abominables como Quemos y Moloc. Tras su muerte, la soberbia de su hijo Roboam al rechazar el consejo de los ancianos provocó la división irreversible: diez tribus formaron Israel en el norte bajo Jeroboam, y Judá en el sur.",
            spiritualLesson = "Un comienzo brillante y ungido no garantiza un buen final si no se guarda el corazón vigilante hasta el último día. La soberbia divide; la mansedumbre y la fidelidad preservan la bendición.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (1 Reyes 11-12), la narrativa bíblica nos presenta «La Idolatría de Salomón y la Fractura del Reino». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 89,
            title = "Día 89: Elías en el Arroyo de Querit y la Viuda de Sarepta",
            passagesSummary = "1 Reyes 17",
            primaryBookId = 11,
            primaryChapter = 17,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(11, "1 Reyes", 17),
            ),
            historicalContext = "Ante la degradación espiritual impuesta por Acab y Jezabel, el profeta Elías proclamó sequía nacional por la palabra de Dios. Dios lo alimentó milagrosamente en el arroyo de Querit mediante cuervos que le llevaban pan y carne. Luego en Sarepta de Fenicia, la tinaja de harina y la botija de aceite de una viuda pobre no escasearon nunca.",
            spiritualLesson = "Cuando obedeces el llamado de Dios, Él se encarga de tu sustento incluso en tiempos de extrema sequía y crisis. Da con fe de lo poco que tienes en tus manos y verás el milagro de la multiplicación.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (1 Reyes 17), la narrativa bíblica nos presenta «Elías en el Arroyo de Querit y la Viuda de Sarepta». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 90,
            title = "Día 90: El Duelo en el Monte Carmelo: Elías contra los Profetas de Baal",
            passagesSummary = "1 Reyes 18",
            primaryBookId = 11,
            primaryChapter = 18,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(11, "1 Reyes", 18),
            ),
            historicalContext = "Frente a 450 profetas de Baal y 400 de Asera que clamaron y se sajan con cuchillos todo el día sin respuesta, Elías reparó el altar arruinado de Jehová con 12 piedras, lo empapó con cuatro cántaros de agua tres veces y oró a la hora del sacrificio de la tarde. Fuego de Dios cayó del cielo consumiendo el buey, la leña y las piedras. Todo el pueblo cayó sobre sus rostros: '¡Jehová es el Dios!'.",
            spiritualLesson = "No claudiques entre dos pensamientos: si Jehová es Dios, síguele de corazón. El Dios vivo responde con fuego purificador a las oraciones sinceras que buscan reivindicar su santo nombre.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (1 Reyes 18), la narrativa bíblica nos presenta «El Duelo en el Monte Carmelo: Elías contra los Profetas de Baal». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 91,
            title = "Día 91: Elías en la Cueva de Horeb: El Silbo Apacible y Delicado",
            passagesSummary = "1 Reyes 19",
            primaryBookId = 11,
            primaryChapter = 19,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(11, "1 Reyes", 19),
            ),
            historicalContext = "Aterrorizado por las amenazas de muerte de la reina Jezabel y agotado física y emocionalmente bajo un enebro deseando morir, un ángel alimentó a Elías. Caminó 40 días hasta el monte Horeb (Sinaí). Dios no estaba en el viento tempestuoso, ni en el terremoto ni en el fuego, sino en un silbo apacible y delicado renovando su llamamiento.",
            spiritualLesson = "En tus momentos de mayor fatiga, depresión o desánimo espiritual, Dios no viene a condenarte, sino a sustentarte con su paz. Aprende a escuchar la voz de Dios en la quietud de la oración.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (1 Reyes 19), la narrativa bíblica nos presenta «Elías en la Cueva de Horeb: El Silbo Apacible y Delicado». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 92,
            title = "Día 92: La Viña de Nabot y el Juicio contra la Injusticia de Acab",
            passagesSummary = "1 Reyes 21",
            primaryBookId = 11,
            primaryChapter = 21,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(11, "1 Reyes", 21),
            ),
            historicalContext = "Nabot rehusó vender la viña que era heredad de sus padres según la ley de Dios. Jezabel maquinó un juicio falso con testigos perversos y apedreó a Nabot para entregar la viña a su caprichoso esposo Acab. Elías se presentó de sorpresa en la viña anunciando el juicio implacable de Dios sobre la dinastía de Acab y Jezabel.",
            spiritualLesson = "Dios es el defensor supremo de los indefensos y aborrece la corrupción, la manipulación y la injusticia social. Ningún abuso de poder quedará impune ante los ojos del Juez Justo.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (1 Reyes 21), la narrativa bíblica nos presenta «La Viña de Nabot y el Juicio contra la Injusticia de Acab». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 93,
            title = "Día 93: Elías Arrebatado al Cielo y la Doble Porción para Eliseo",
            passagesSummary = "2 Reyes 2",
            primaryBookId = 12,
            primaryChapter = 2,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(12, "2 Reyes", 2),
            ),
            historicalContext = "Caminando juntos de Gilgal a Bet-el, Jericó y cruzando el Jordán con el manto de Elías, Eliseo se mantuvo leal a su maestro hasta el final. Pidió: 'Te ruego que una doble porción de tu espíritu sea sobre mí'. Carros y caballos de fuego los separaron, y Elías ascendió al cielo en un torbellino. Eliseo recogió el manto caído y abrió las aguas del Jordán.",
            spiritualLesson = "La herencia y el legado espiritual son para aquellos que perseveran con fidelidad y hambre santa de la presencia de Dios. Busca la unción del Espíritu Santo con pasión inquebrantable.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (2 Reyes 2), la narrativa bíblica nos presenta «Elías Arrebatado al Cielo y la Doble Porción para Eliseo». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 94,
            title = "Día 94: Eliseo y los Milagros: El Aceite de la Viuda y el Niño de Sunem",
            passagesSummary = "2 Reyes 4",
            primaryBookId = 12,
            primaryChapter = 4,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(12, "2 Reyes", 4),
            ),
            historicalContext = "Una viuda en bancarrota a punto de perder a sus hijos como esclavos acudió a Eliseo; al derramar su única vasija de aceite en vasijas prestadas, el aceite no cesó hasta llenar la última vasija. Más tarde, la mujer principal de Sunem que hospedó a Eliseo con hospitalidad vio a su hijo resucitar tras tenderse el profeta sobre él en oración.",
            spiritualLesson = "Dios se preocupa por las necesidades íntimas, económicas y familiares de sus hijos. Confía tus vasijas vacías al Señor: su provisión y poder de resurrección sobrepasan todo dolor humano.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (2 Reyes 4), la narrativa bíblica nos presenta «Eliseo y los Milagros: El Aceite de la Viuda y el Niño de Sunem». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 95,
            title = "Día 95: La Sanidad de Naamán el Sirio en las Aguas del Jordán",
            passagesSummary = "2 Reyes 5",
            primaryBookId = 12,
            primaryChapter = 5,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(12, "2 Reyes", 5),
            ),
            historicalContext = "Naamán, general ilustre del ejército del rey de Siria pero leproso, viajó a Samaria guiado por el testimonio de una pequeña criada hebrea cautiva. Ofendido inicialmente porque Eliseo solo le mandó lavarse siete veces en el turbio río Jordán, sus siervos lo persuadieron; al obedecer en humildad, su carne quedó como la de un niño.",
            spiritualLesson = "La soberbia y el orgullo impiden recibir la gracia de Dios; la fe requiere someterse con humildad a las instrucciones divinas. El testimonio valiente de los más pequeños puede transformar naciones.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (2 Reyes 5), la narrativa bíblica nos presenta «La Sanidad de Naamán el Sirio en las Aguas del Jordán». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 96,
            title = "Día 96: El Hacha Flotante y los Carros de Fuego en Dotán",
            passagesSummary = "2 Reyes 6",
            primaryBookId = 12,
            primaryChapter = 6,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(12, "2 Reyes", 6),
            ),
            historicalContext = "Eliseo hizo flotar un hierro de hacha prestada caída al río Jordán. Poco después, el rey de Siria sitió Dotán de noche para capturar a Eliseo. El criado de Eliseo tembló de pavor al ver las tropas, pero Eliseo oró: 'Te ruego, oh Jehová, que abras sus ojos para que vea'. Y vio el monte lleno de caballos y carros de fuego celestiales protegiéndolos.",
            spiritualLesson = "Los que están con nosotros son más que los que están contra nosotros. No te dejes cegar por las amenazas visibles del enemigo: pide a Dios ojos de fe para contemplar la protección angelical divina.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (2 Reyes 6), la narrativa bíblica nos presenta «El Hacha Flotante y los Carros de Fuego en Dotán». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 97,
            title = "Día 97: El Sitio de Samaria y los Cuatro Leprosos Marginados",
            passagesSummary = "2 Reyes 7",
            primaryBookId = 12,
            primaryChapter = 7,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(12, "2 Reyes", 7),
            ),
            historicalContext = "Una hambruna atroz asoló a Samaria sitiada por los sirios. Cuatro hombres leprosos sentados a la puerta de la ciudad dijeron: '¿Para qué nos estamos aquí hasta que muramos?'. Al entrar al campamento sirio al anochecer, Dios había hecho escuchar estruendo de carros de guerra y los sirios huyeron dejando carpas llenas de comida, oro y ropa.",
            spiritualLesson = "Dios a menudo utiliza a los marginados y débiles para proclamar las mejores noticias de salvación y abundancia. No te quedes pasivo ante las dificultades: camina con fe hacia la provisión divina.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (2 Reyes 7), la narrativa bíblica nos presenta «El Sitio de Samaria y los Cuatro Leprosos Marginados». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 98,
            title = "Día 98: El Juicio sobre Jezabel y el Celo Impetuoso de Jehú",
            passagesSummary = "2 Reyes 9-10",
            primaryBookId = 12,
            primaryChapter = 9,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(12, "2 Reyes", 9),
                PlanPassageSegment(12, "2 Reyes", 10),
            ),
            historicalContext = "Eliseo comisionó en secreto a un joven profeta para ungir a Jehú como rey de Israel para erradicar la tiranía idólatra de la casa de Acab. Jehú marchó con furor sobre Jezreel, ejecutó juicio sobre Joram y Ocozías, arrojó a la cruel reina Jezabel por la ventana y destruyó el templo y a los sacerdotes de Baal.",
            spiritualLesson = "El pecado y la tiranía contra la santidad de Dios tienen un límite establecido por la justicia divina. Sin embargo, el celo ministerial debe estar acompañado de un corazón puro que no caiga en excesos propios.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (2 Reyes 9-10), la narrativa bíblica nos presenta «El Juicio sobre Jezabel y el Celo Impetuoso de Jehú». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 99,
            title = "Día 99: El Rescate del Pequeño Rey Joás y las Reparaciones del Templo",
            passagesSummary = "2 Reyes 11-12",
            primaryBookId = 12,
            primaryChapter = 11,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(12, "2 Reyes", 11),
                PlanPassageSegment(12, "2 Reyes", 12),
            ),
            historicalContext = "Atalía usurpó el trono de Judá asesinando a toda la descendencia real, pero Joseba escondió al bebé Joás de un año en el Templo durante seis años. A los siete años, el piadoso sumo sacerdote Joiada lo coronó con cánticos en el santuario y promovió un gran pacto nacional, reparando las grietas del Templo mediante un arca de ofrendas.",
            spiritualLesson = "Aun en las horas más oscuras de apostasía, Dios preserva una simiente santa para dar continuidad a sus promesas. La juventud consagrada a Dios y guiada por mentores sabios renueva a la sociedad.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (2 Reyes 11-12), la narrativa bíblica nos presenta «El Rescate del Pequeño Rey Joás y las Reparaciones del Templo». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 100,
            title = "Día 100: Jonás, el Gran Pez y el Avivamiento Inesperado en Nínive",
            passagesSummary = "Jonás 1-4",
            primaryBookId = 32,
            primaryChapter = 1,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(32, "Jonás", 1),
                PlanPassageSegment(32, "Jonás", 3),
            ),
            historicalContext = "Enviado a profetizar contra Nínive (la despiadada capital del imperio asirio), Jonás huyó en barco hacia Tarsis en dirección opuesta. Tras una gran tempestad, fue arrojado al mar y tragado por un gran pez preparado por Dios. Desde el vientre del pez clamó arrepentido; fue vomitado en tierra firme, predicó en Nínive y 120,000 personas se arrepintieron con ayuno y cilicio.",
            spiritualLesson = "No puedes huir de la presencia ni del llamado soberano de Dios. El corazón de Dios arde con compasión por los perdidos de toda tribu y nación; no endurezcas tu corazón con prejuicios mezquinos.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Jonás 1-4), la narrativa bíblica nos presenta «Jonás, el Gran Pez y el Avivamiento Inesperado en Nínive». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 101,
            title = "Día 101: El Clamor de Justicia de Amós y el Amor Inquebrantable de Oseas",
            passagesSummary = "Amós 5; Oseas 1-3",
            primaryBookId = 30,
            primaryChapter = 5,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(30, "Amós", 5),
                PlanPassageSegment(28, "Oseas", 1),
                PlanPassageSegment(28, "Oseas", 3),
            ),
            historicalContext = "Amós, un sencillo pastor de Tecoa, denunció la opulencia indiferente y la opresión a los pobres: 'Corra el juicio como las aguas, y la justicia como impetuoso arroyo'. Contemporáneamente, Dios mandó a Oseas casarse con Gomer, una mujer infiel, para ilustrar en carne viva el dolor del corazón de Dios y su amor redentor incondicional hacia su pueblo.",
            spiritualLesson = "La verdadera fe bíblica no se mide por ceremonias religiosas externas, sino por la justicia con el prójimo y un amor leal hacia Dios. El amor de Dios persigue y redime a los caídos.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Amós 5; Oseas 1-3), la narrativa bíblica nos presenta «El Clamor de Justicia de Amós y el Amor Inquebrantable de Oseas». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 102,
            title = "Día 102: La Caída y Destrucción del Reino del Norte por Asiria",
            passagesSummary = "2 Reyes 17",
            primaryBookId = 12,
            primaryChapter = 17,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(12, "2 Reyes", 17),
            ),
            historicalContext = "Tras dos siglos de advertencias proféticas desoídas y persistencia en los becerros de oro de Jeroboam y el culto a Baal, el rey Salmanasar de Asiria sitió a Samaria durante tres años. En el 722 a.C., la ciudad cayó, los israelitas fueron deportados y dispersados por Mesopotamia, y colonos paganos fueron reasentados.",
            spiritualLesson = "El endurecimiento continuo frente a la voz de Dios trae consecuencias irreversibles en la historia. Aprovecha las advertencias de la gracia y no tomes a la ligera la santidad del Señor.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (2 Reyes 17), la narrativa bíblica nos presenta «La Caída y Destrucción del Reino del Norte por Asiria». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 103,
            title = "Día 103: La Fe de Ezequías y la Derrota Milagrosa de Senaquerib",
            passagesSummary = "2 Reyes 18-19",
            primaryBookId = 12,
            primaryChapter = 18,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(12, "2 Reyes", 18),
                PlanPassageSegment(12, "2 Reyes", 19),
            ),
            historicalContext = "El rey asirio Senaquerib invadió Judá y envió a su general Rabsaces a desafiar e insultar con blasfemias al Dios de Israel ante los muros de Jerusalén. Ezequías rasgó sus vestidos, fue al Templo y extendió las cartas de intimidación delante de Jehová orando con fervor. Esa misma noche, el Ángel de Jehová abatió a 185,000 soldados en el campamento asirio.",
            spiritualLesson = "Cuando recibas cartas de intimidación, diagnósticos aterradores o amenazas del enemigo, extiéndelas en oración en la presencia de Dios. El Señor pelea por ti y librará tu vida.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (2 Reyes 18-19), la narrativa bíblica nos presenta «La Fe de Ezequías y la Derrota Milagrosa de Senaquerib». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 104,
            title = "Día 104: La Enfermedad de Ezequías y la Señal de la Sombra que Retrocede",
            passagesSummary = "2 Reyes 20",
            primaryBookId = 12,
            primaryChapter = 20,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(12, "2 Reyes", 20),
            ),
            historicalContext = "Ezequías enfermó de muerte y el profeta Isaías le dijo que ordenara su casa porque moriría. El rey volvió su rostro a la pared y lloró amargamente recordando su integridad. Antes que Isaías saliera del patio, Dios le ordenó regresar: 'He oído tu oración... te añado quince años'. Como señal divina, la sombra del reloj de sol de Acaz retrocedió diez grados.",
            spiritualLesson = "La oración humilde y perseverante mueve la mano del Dios todopoderoso. Cada día adicional de vida que el Señor nos concede es un don sagrado para usar con sabiduría y devoción.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (2 Reyes 20), la narrativa bíblica nos presenta «La Enfermedad de Ezequías y la Señal de la Sombra que Retrocede». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 105,
            title = "Día 105: La Visión del Trono Celestial de Isaías: 'Heme Aquí'",
            passagesSummary = "Isaías 6",
            primaryBookId = 23,
            primaryChapter = 6,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(23, "Isaías", 6),
            ),
            historicalContext = "En el año en que murió el rey Uzías, Isaías vio al Señor sentado sobre un trono alto y sublime, y sus faldas llenaban el templo. Serafines con seis alas clamaban: '¡Santo, Santo, Santo, Jehová de los ejércitos!'. Quebrantado por su impureza, un serafín tocó sus labios con un carbón encendido del altar. Al oír: '¿A quién enviaré?', Isaías respondió: 'Heme aquí, envíame a mí'.",
            spiritualLesson = "Una visión clara de la santidad excelsa de Dios nos quebranta en arrepentimiento, pero también desata la purificación divina que nos capacita para el servicio: sé sensible y di sí a su llamado.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Isaías 6), la narrativa bíblica nos presenta «La Visión del Trono Celestial de Isaías: 'Heme Aquí'». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 106,
            title = "Día 106: El Joven Rey Josías y el Hallazgo del Libro de la Ley",
            passagesSummary = "2 Reyes 22-23",
            primaryBookId = 12,
            primaryChapter = 22,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(12, "2 Reyes", 22),
                PlanPassageSegment(12, "2 Reyes", 23),
            ),
            historicalContext = "Comenzando a reinar a los ocho años y buscando a Dios desde su juventud, Josías ordenó reparar el Templo de Jerusalén. El sumo sacerdote Hilcías encontró el rollo de la Ley olvidado bajo escombros. Al escucharlo, Josías rasgó sus vestidos con lágrimas de dolor, consultó a la profetisa Hulda, derribó los altares paganos y celebró una Pascua histórica inigualable.",
            spiritualLesson = "Nunca es tarde para redescubrir la bendición transformadora de la Palabra de Dios. Un solo líder sensible y obediente a la Escritura puede encender un avivamiento en toda una nación.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (2 Reyes 22-23), la narrativa bíblica nos presenta «El Joven Rey Josías y el Hallazgo del Libro de la Ley». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 107,
            title = "Día 107: El Llamado de Jeremías y su Rescate de la Cisterna Cenagosa",
            passagesSummary = "Jeremías 1; 38",
            primaryBookId = 24,
            primaryChapter = 1,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(24, "Jeremías", 1),
                PlanPassageSegment(24, "Jeremías", 38),
            ),
            historicalContext = "Desde antes de nacer en Anatot, Dios conoció y apartó a Jeremías como profeta a las naciones. Por proclamar con valentía la verdad ante la decadencia de Judá, fue encarcelado en una cisterna cenagosa hundiéndose en el lodo. Ebed-melec, un eunuco etíope piadoso, usó trapos viejos y sogas para rescatarlo compasivamente.",
            spiritualLesson = "Ser fiel a Dios puede acarrear oposición y soledad, pero Dios nunca abandona a sus mensajeros. Él siempre envía manos compasivas para levantarte de las cisternas del desaliento.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Jeremías 1; 38), la narrativa bíblica nos presenta «El Llamado de Jeremías y su Rescate de la Cisterna Cenagosa». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 108,
            title = "Día 108: La Caída de Jerusalén y las Misericordias en Lamentaciones",
            passagesSummary = "2 Reyes 25; Lamentaciones 3",
            primaryBookId = 12,
            primaryChapter = 25,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(12, "2 Reyes", 25),
                PlanPassageSegment(25, "Lamentaciones", 3),
            ),
            historicalContext = "En el 586 a.C., tras 18 meses de sitio despiadado, las murallas de Jerusalén fueron derribadas, el glorioso Templo de Salomón fue quemado hasta los cimientos y el pueblo llevado cautivo a Babilonia. En medio de las cenizas y el llanto desgarrador, Jeremías proclamó: 'Por la misericordia de Jehová no hemos sido consumidos... nuevas son cada mañana; grande es tu fidelidad'.",
            spiritualLesson = "Aun en medio de las ruinas y fracasos más amargos provocados por la desobediencia, la fidelidad y misericordia de Dios se renuevan cada mañana. Hay esperanza de restauración futura.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (2 Reyes 25; Lamentaciones 3), la narrativa bíblica nos presenta «La Caída de Jerusalén y las Misericordias en Lamentaciones». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 109,
            title = "Día 109: Daniel y sus Amigos Rehusaron Contaminarse en Babilonia",
            passagesSummary = "Daniel 1",
            primaryBookId = 27,
            primaryChapter = 1,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(27, "Daniel", 1),
            ),
            historicalContext = "Jóvenes nobles de Judá fueron deportados a Babilonia para ser adoctrinados en la lengua y cultura imperial, recibiendo nuevos nombres idólatras. Daniel 'propuso en su corazón no contaminarse con la porción de la comida del rey, ni con el vino que él bebía', pidiendo legumbres y agua. Al cabo de diez días, su aspecto y sabiduría superaban diez veces a los magos caldeos.",
            spiritualLesson = "La firmeza espiritual se decide en las elecciones privadas cotidianas. No negocies tus convicciones con los estándares de este mundo: Dios honra con gracia y sabiduría a los que le honran.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Daniel 1), la narrativa bíblica nos presenta «Daniel y sus Amigos Rehusaron Contaminarse en Babilonia». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 110,
            title = "Día 110: El Sueño de la Gran Estatua y la Roca No Cortada con Manos",
            passagesSummary = "Daniel 2",
            primaryBookId = 27,
            primaryChapter = 2,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(27, "Daniel", 2),
            ),
            historicalContext = "Nabucodonosor tuvo un sueño perturbador y amenazó con descuartizar a todos los sabios si no le revelaban el sueño y su interpretación. Daniel y sus amigos oraron al Dios del cielo. Dios le reveló la estatua con cabeza de oro (Babilonia), pecho de plata (Medo-Persia), vientre de bronce (Grecia), piernas de hierro (Roma) y una Roca no cortada con manos que pulverizó todo y llenó la tierra.",
            spiritualLesson = "Los imperios humanos más poderosos surgen, dominan y finalmente caen en el olvido, pero el reino mesiánico de Cristo es inconmovible, eterno y gobernará con gloria por siempre.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Daniel 2), la narrativa bíblica nos presenta «El Sueño de la Gran Estatua y la Roca No Cortada con Manos». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 111,
            title = "Día 111: El Horno de Fuego Ardiendo y el Cuarto Semejante a Dios",
            passagesSummary = "Daniel 3",
            primaryBookId = 27,
            primaryChapter = 3,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(27, "Daniel", 3),
            ),
            historicalContext = "Sadrac, Mesac y Abed-nego rehusaron postrarse ante la estatua de oro de 30 metros en el campo de Dura: 'Nuestro Dios a quien servimos puede librarnos... y si no, sepas, oh rey, que no serviremos a tus dioses'. Arrojados al horno siete veces más caliente, el rey asombrado vio a cuatro varones pasearse sueltos entre las llamas, y el cuarto tenía aspecto semejante al Hijo de Dios.",
            spiritualLesson = "La verdadera fe no condiciona su lealtad a si Dios nos libra del fuego o no. Cuando camines por el fuego de la prueba, no te quemarás: la presencia viva de Jesús caminará a tu lado.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Daniel 3), la narrativa bíblica nos presenta «El Horno de Fuego Ardiendo y el Cuarto Semejante a Dios». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 112,
            title = "Día 112: La Humillación de Nabucodonosor y su Restauración",
            passagesSummary = "Daniel 4",
            primaryBookId = 27,
            primaryChapter = 4,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(27, "Daniel", 4),
            ),
            historicalContext = "Paseándose por su palacio, Nabucodonosor se vanaglorió: '¿No es esta la gran Babilonia que yo edifiqué con la fuerza de mi poder y para gloria de mi majestad?'. Al instante cayó en licantropía viviendo siete años como bestia del campo comiendo hierba. Al levantar sus ojos al cielo y reconocer al Altísimo, su razón le fue devuelta y alabó al Rey celestial con humildad.",
            spiritualLesson = "El orgullo antecede al tropiezo, pero la gracia divina restaura a los que reconocen la soberanía suprema de Dios. Todos los habitantes de la tierra son considerados como nada ante su majestad.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Daniel 4), la narrativa bíblica nos presenta «La Humillación de Nabucodonosor y su Restauración». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 113,
            title = "Día 113: El Banquete Blasfemo de Belsasar y la Escritura en la Pared",
            passagesSummary = "Daniel 5",
            primaryBookId = 27,
            primaryChapter = 5,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(27, "Daniel", 5),
            ),
            historicalContext = "El rey Belsasar ofreció un banquete a mil príncipes bebiendo en los vasos sagrados de oro del Templo de Jehová mientras alababan a ídolos inertes. De repente, los dedos de una mano humana escribieron en la pared: 'MENE, MENE, TEKEL, UPARSIN'. Daniel interpretó el veredicto: pesado en balanza y hallado falto. Esa misma noche Ciro tomó Babilonia y Belsasar murió.",
            spiritualLesson = "Dios no puede ser burlado. El abuso del poder y la profanación de lo sagrado acarrean juicio inmediato. Asegúrate de que tu vida esté fundada en la gracia y la justicia que pesan para eternidad.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Daniel 5), la narrativa bíblica nos presenta «El Banquete Blasfemo de Belsasar y la Escritura en la Pared». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 114,
            title = "Día 114: Daniel en el Foso de los Leones y el Dios que Libra",
            passagesSummary = "Daniel 6",
            primaryBookId = 27,
            primaryChapter = 6,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(27, "Daniel", 6),
            ),
            historicalContext = "Gobernadores celosos de la integridad de Daniel lograron que el rey Darío firmara un edicto prohibiendo orar a cualquier dios u hombre durante 30 días bajo pena del foso de los leones. Daniel, al saberlo, abrió las ventanas de su cámara hacia Jerusalén y oró tres veces al día como solía hacerlo. Arrojado a los leones, Dios envió a su ángel que cerró la boca de las fieras.",
            spiritualLesson = "Mantén tu disciplina de oración constante sin importar las amenazas externas o la presión social. Cuando eres fiel a Dios, Él envía a sus ángeles para protegerte en medio de los fosos de este mundo.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Daniel 6), la narrativa bíblica nos presenta «Daniel en el Foso de los Leones y el Dios que Libra». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 115,
            title = "Día 115: La Visión de Ezequiel en el Valle de los Huesos Secos",
            passagesSummary = "Ezequiel 37",
            primaryBookId = 26,
            primaryChapter = 37,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(26, "Ezequiel", 37),
            ),
            historicalContext = "El Espíritu de Dios llevó al profeta Ezequiel a un valle cubierto de huesos humanos secos en extremo: '¿Vivirán estos huesos?'. Dios le mandó profetizar sobre ellos: hubo un gran ruido, los huesos se unieron, se cubrieron de tendones y carne, y al clamar al Espíritu (Ruaj), sopló vida y se levantó un ejército grande en extremo. Ilustra la restauración futura de Israel.",
            spiritualLesson = "No hay situación, matrimonio, vida o iglesia tan 'muerta' o seca que el soplo del Espíritu Santo de Dios no pueda resucitar. Cree en el poder de la Palabra profética de vida.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Ezequiel 37), la narrativa bíblica nos presenta «La Visión de Ezequiel en el Valle de los Huesos Secos». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 116,
            title = "Día 116: Ester en la Corte de Persia y la Conjura Genocida de Amán",
            passagesSummary = "Ester 1-3",
            primaryBookId = 17,
            primaryChapter = 1,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(17, "Ester", 1),
                PlanPassageSegment(17, "Ester", 3),
            ),
            historicalContext = "La reina Vasti fue depuesta y la joven huérfana judía Hadasa (Ester), criada por su primo Mardoqueo, halló gracia y fue coronada reina del vasto imperio persa. Mardoqueo se negó a arrodillarse ante el orgulloso ministro Amán agagueo, y este planeó en venganza el exterminio total de todos los judíos del imperio echando 'Pur' (suerte) para fijar el día.",
            spiritualLesson = "Dios posiciona estratégicamente a sus hijos en lugares de influencia terrenal para momentos decisivos de la historia. Mantente firme en tu adoración exclusiva al Dios verdadero.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Ester 1-3), la narrativa bíblica nos presenta «Ester en la Corte de Persia y la Conjura Genocida de Amán». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 117,
            title = "Día 117: 'Si Perezco, que Perezca': La Audaz Intercesión de Ester",
            passagesSummary = "Ester 4-7",
            primaryBookId = 17,
            primaryChapter = 4,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(17, "Ester", 4),
                PlanPassageSegment(17, "Ester", 7),
            ),
            historicalContext = "Mardoqueo advirtió a Ester: '¿Y quién sabe si para esta hora has llegado al reino?'. Ester convocó a tres días de ayuno absoluto y se presentó ante el rey Asuero sin ser llamada arriesgando su vida con el cetro de oro. En el banquete desenmascaró la perversa trama de Amán; el rey enfurecido ordenó colgar a Amán en la misma horca de 25 metros que él preparó para Mardoqueo.",
            spiritualLesson = "Tu vida y posición no son para tu propia comodidad, sino para servir a los propósitos de Dios en horas críticas. Las trampas que los enemigos cavan para los justos se vuelven contra ellos mismos.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Ester 4-7), la narrativa bíblica nos presenta «'Si Perezco, que Perezca': La Audaz Intercesión de Ester». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 118,
            title = "Día 118: El Triunfo de Mardoqueo y la Celebración de Purim",
            passagesSummary = "Ester 8-9",
            primaryBookId = 17,
            primaryChapter = 8,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(17, "Ester", 8),
                PlanPassageSegment(17, "Ester", 9),
            ),
            historicalContext = "Dado que los edictos reales persas no podían revocarse, se emitió un nuevo contra-decreto permitiendo a los judíos defenderse armados. El día de la supuesta masacre se convirtió en día de gran victoria, gozo y rescate. Instituyeron la fiesta de Purim con alegría, banquetes y regalos a los pobres.",
            spiritualLesson = "Dios tiene el poder de transformar el día de mayor angustia y luto en la fiesta de mayor regocijo y testimonio. La providencia de Dios cuida de su pueblo aun tras bambalinas.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Ester 8-9), la narrativa bíblica nos presenta «El Triunfo de Mardoqueo y la Celebración de Purim». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 119,
            title = "Día 119: El Decreto de Ciro y la Colocación de los Cimientos del Templo",
            passagesSummary = "Esdras 1-3",
            primaryBookId = 15,
            primaryChapter = 1,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(15, "Esdras", 1),
                PlanPassageSegment(15, "Esdras", 3),
            ),
            historicalContext = "Conmovido por el Espíritu conforme a la profecía de Isaías y Jeremías, Ciro el Grande de Persia emitió un decreto permitiendo a los exiliados regresar a Jerusalén y reconstruir el Templo. Bajo el liderazgo de Zorobabel y Jesúa colocaron los cimientos: los jóvenes gritaban de júbilo con shofares y los ancianos que vieron el primer templo lloraban conmovidos.",
            spiritualLesson = "Dios cumple sus promesas históricas al pie de la letra, moviendo los corazones de los gobernantes de la tierra. No desprecies el día de los modestos comienzos cuando la obra es de Dios.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Esdras 1-3), la narrativa bíblica nos presenta «El Decreto de Ciro y la Colocación de los Cimientos del Templo». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 120,
            title = "Día 120: Nehemías y la Reconstrucción de las Murallas en 52 Días",
            passagesSummary = "Nehemías 1-4; 6",
            primaryBookId = 16,
            primaryChapter = 1,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(16, "Nehemías", 1),
                PlanPassageSegment(16, "Nehemías", 4),
                PlanPassageSegment(16, "Nehemías", 6),
            ),
            historicalContext = "Copero del rey Artajerjes en Susa, Nehemías lloró y ayunó al oír que los muros de Jerusalén estaban en ruinas. Con autorización real viajó a reconstruir. Ante las burlas y amenazas de Sambalat y Tobías, el pueblo trabajó con la espada en una mano y la cuchara de albañil en la otra, y 'el muro fue terminado en cincuenta y dos días' porque Dios los ayudó.",
            spiritualLesson = "La oración ferviente unida al trabajo tenaz y a la vigilancia espiritual derriba toda oposición enemiga. El gozo de Jehová es nuestra fortaleza indestructible.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Nehemías 1-4; 6), la narrativa bíblica nos presenta «Nehemías y la Reconstrucción de las Murallas en 52 Días». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 121,
            title = "Día 121: El Anuncio a Zacarías y a la Virgen María (El Magníficat)",
            passagesSummary = "Lucas 1",
            primaryBookId = 42,
            primaryChapter = 1,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(42, "Lucas", 1),
            ),
            historicalContext = "Tras 400 años de silencio profético, el ángel Gabriel anunció al anciano sacerdote Zacarías el nacimiento de Juan el Bautista. Luego fue enviado a Nazaret a una joven virgen llamada María: 'El Espíritu Santo vendrá sobre ti... el Santo Ser que nacerá será llamado Hijo de Dios'. María respondió con sumisión: 'Hágase en mí según tu palabra' y entonó el himno del Magníficat.",
            spiritualLesson = "Para Dios no hay nada imposible. La verdadera grandeza espiritual radica en un corazón humilde que se somete plenamente a la voluntad y los propósitos del Señor.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Lucas 1), la narrativa bíblica nos presenta «El Anuncio a Zacarías y a la Virgen María (El Magníficat)». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 122,
            title = "Día 122: El Nacimiento en Belén y el Coro Angelical a los Pastores",
            passagesSummary = "Lucas 2",
            primaryBookId = 42,
            primaryChapter = 2,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(42, "Lucas", 2),
            ),
            historicalContext = "Por decreto de césar Augusto para un censo imperial, José y María viajaron de Nazaret a Belén de Judea. Sin lugar en el mesón, el Salvador del mundo nació en un humilde establo y fue envuelto en pañales y acostado en un pesebre. Ángeles celestiales proclamaron las buenas nuevas no a reyes, sino a sencillos pastores en las colinas de Belén: '¡Paz en la tierra!'.",
            spiritualLesson = "Dios eligió la sencillez y la humildad para revelar su gloria suprema. Jesús vino al mundo para identificarse con los más vulnerables y ofrecer salvación gratuita a todo corazón receptivo.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Lucas 2), la narrativa bíblica nos presenta «El Nacimiento en Belén y el Coro Angelical a los Pastores». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 123,
            title = "Día 123: La Adoración de los Magos y la Huida Protectora a Egipto",
            passagesSummary = "Mateo 2",
            primaryBookId = 40,
            primaryChapter = 2,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(40, "Mateo", 2),
            ),
            historicalContext = "Sabios de Oriente llegaron guiados por una estrella milagrosa buscando al Rey de los judíos. Al entrar a la casa se postraron, le adoraron y le abrieron sus tesoros ofreciéndole oro (su realeza), incienso (su divinidad) y mirra (su sacrificio redentor). Avisados en sueños huyeron de la masacre de inocentes perpetrada por el rey Herodes refugiándose en Egipto.",
            spiritualLesson = "Los verdaderos sabios siguen buscando a Jesús para rendirle adoración y entregarle lo mejor de sus vidas. Dios protege con fidelidad a los suyos frente a los planes malévolos del enemigo.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Mateo 2), la narrativa bíblica nos presenta «La Adoración de los Magos y la Huida Protectora a Egipto». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 124,
            title = "Día 124: El Niño Jesús a los Doce Años en el Templo de Jerusalén",
            passagesSummary = "Lucas 2",
            primaryBookId = 42,
            primaryChapter = 2,
            primaryVerse = 41,
            passages = listOf(
                PlanPassageSegment(42, "Lucas", 2),
            ),
            historicalContext = "Tras la fiesta de la Pascua, María y José caminaron un día de camino de regreso pensando que Jesús iba en la caravana. Al buscarlo con angustia, lo hallaron al tercer día en el Templo sentado entre los más eruditos doctores de la Ley, escuchándolos y haciéndoles preguntas con sabiduría asombrosa: '¿No sabíais que en los negocios de mi Padre me es necesario estar?'.",
            spiritualLesson = "Desde su niñez, Jesús tenía una perfecta y clara conciencia de su misión divina. Dedica tu mente y tu tiempo a cultivar la sabiduría y a ocuparte en los asuntos del reino eterno de tu Padre celestial.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Lucas 2), la narrativa bíblica nos presenta «El Niño Jesús a los Doce Años en el Templo de Jerusalén». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 125,
            title = "Día 125: El Ministerio de Juan el Bautista y el Bautismo de Jesús",
            passagesSummary = "Mateo 3; Juan 1",
            primaryBookId = 40,
            primaryChapter = 3,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(40, "Mateo", 3),
                PlanPassageSegment(43, "Juan", 1),
            ),
            historicalContext = "Vestido de pelo de camello y alimentándose de langostas y miel silvestre en el desierto, Juan proclamaba: 'Arrepentíos, porque el reino de los cielos se ha acercado'. Jesús acudió al Jordán para cumplir toda justicia; al ser bautizado, los cielos se abrieron, el Espíritu Santo descendió como paloma y la voz del Padre resonó: 'Este es mi Hijo amado, en quien tengo complacencia'.",
            spiritualLesson = "El bautismo es un testimonio público de obediencia e identificación con Cristo. Vivamos con la bendición y complacencia del Padre sobre nuestras vidas mediante la llenura del Espíritu.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Mateo 3; Juan 1), la narrativa bíblica nos presenta «El Ministerio de Juan el Bautista y el Bautismo de Jesús». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 126,
            title = "Día 126: La Victoria de Jesús sobre las Tentaciones en el Desierto",
            passagesSummary = "Mateo 4",
            primaryBookId = 40,
            primaryChapter = 4,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(40, "Mateo", 4),
            ),
            historicalContext = "Guiado por el Espíritu al desierto de Judea tras ayunar 40 días, Satanás tentó a Jesús en el apetito físico ('di que estas piedras se conviertan en pan'), en la presunción espiritual arrojándose del pináculo del templo y en la ambición mundana con los reinos de la tierra. Jesús venció cada ataque con la espada de la Palabra: 'Escrito está'.",
            spiritualLesson = "La tentación se vence no con debates humanos, sino con la Palabra inspirada de Dios guardada en el corazón. Ningún ataque del enemigo prevalecerá si estás cimentado en la verdad bíblica.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Mateo 4), la narrativa bíblica nos presenta «La Victoria de Jesús sobre las Tentaciones en el Desierto». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 127,
            title = "Día 127: Las Bodas de Caná y la Purificación del Templo",
            passagesSummary = "Juan 2",
            primaryBookId = 43,
            primaryChapter = 2,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(43, "Juan", 2),
            ),
            historicalContext = "En Caná de Galilea, al acabarse el vino en una boda, Jesús mandó llenar seis tinajas de piedra de purificación con agua y las convirtió en el mejor vino, manifestando su gloria. Poco después en Jerusalén, hizo un azote de cuerdas y expulsó a los mercaderes y cambistas del Templo proclamando: 'No hagáis de la casa de mi Padre casa de mercado'.",
            spiritualLesson = "Jesús transforma lo ordinario en extraordinario y trae gozo abundante a nuestras vidas. Al mismo tiempo, su celo santo exige purificar nuestra vida y templo de toda hipocresía y codicia.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Juan 2), la narrativa bíblica nos presenta «Las Bodas de Caná y la Purificación del Templo». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 128,
            title = "Día 128: Jesús y Nicodemo: 'De Tal Manera Amó Dios al Mundo'",
            passagesSummary = "Juan 3",
            primaryBookId = 43,
            primaryChapter = 3,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(43, "Juan", 3),
            ),
            historicalContext = "Nicodemo, influyente maestro fariseo del Sanedrín, buscó a Jesús de noche. Jesús le declaró: 'De cierto, de cierto te digo, que el que no naciere de nuevo, no puede ver el reino de Dios'. Le explicó la regeneración por el Espíritu y proclamó el versículo cumbre de la Escritura: 'Porque de tal manera amó Dios al mundo, que ha dado a su Hijo unigénito, para que todo aquel que en él cree no se pierda'.",
            spiritualLesson = "La religión externa, el estatus social y las buenas obras no salvan: se necesita nacer de nuevo por la gracia y el Espíritu de Dios. Cree en el amor inmenso del Hijo y recibe vida eterna.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Juan 3), la narrativa bíblica nos presenta «Jesús y Nicodemo: 'De Tal Manera Amó Dios al Mundo'». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 129,
            title = "Día 129: El Encuentro con la Mujer Samaritana junto al Pozo de Sicar",
            passagesSummary = "Juan 4",
            primaryBookId = 43,
            primaryChapter = 4,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(43, "Juan", 4),
            ),
            historicalContext = "Quebrando barreras étnicas, religiosas y de género, Jesús descansó al mediodía junto al pozo de Jacob en Samaria y pidió agua a una mujer sedienta y herida por cinco divorcios. Le ofreció el agua viva del Espíritu que salta para vida eterna y le reveló que Dios busca adoradores que le adoren en espíritu y en verdad. La mujer proclamó el mensaje y la ciudad entera creyó.",
            spiritualLesson = "Nada en este mundo puede saciar la sed profunda del alma humana excepto Cristo. No importa tu pasado: cuando tienes un encuentro con Jesús, te conviertes en un testigo de su gracia.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Juan 4), la narrativa bíblica nos presenta «El Encuentro con la Mujer Samaritana junto al Pozo de Sicar». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 130,
            title = "Día 130: El Sermón Inaugural en Nazaret y la Pesca Milagrosa",
            passagesSummary = "Lucas 4-5",
            primaryBookId = 42,
            primaryChapter = 4,
            primaryVerse = 14,
            passages = listOf(
                PlanPassageSegment(42, "Lucas", 4),
                PlanPassageSegment(42, "Lucas", 5),
            ),
            historicalContext = "En la sinagoga de Nazaret, Jesús leyó el rollo de Isaías 61: 'El Espíritu del Señor está sobre mí, por cuanto me ha ungido para dar buenas nuevas a los pobres...'. Poco después en el mar de Genesaret, tras una noche estéril sin pescar, dijo a Simón Pedro: 'Boga mar adentro'. Al echar las redes por su palabra, la multitud de peces casi rompía las redes. Pedro cayó a sus rodillas: 'Apártate de mí, que soy pecador'.",
            spiritualLesson = "Cuando obedeces la palabra de Jesús y bogas mar adentro en fe, tus noches de fracaso se convierten en milagros de abundancia. Síguele y te convertirá en pescador de hombres.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Lucas 4-5), la narrativa bíblica nos presenta «El Sermón Inaugural en Nazaret y la Pesca Milagrosa». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 131,
            title = "Día 131: El Sermón del Monte: Bienaventuranzas, Sal, Luz y el Padre Nuestro",
            passagesSummary = "Mateo 5-7",
            primaryBookId = 40,
            primaryChapter = 5,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(40, "Mateo", 5),
                PlanPassageSegment(40, "Mateo", 6),
            ),
            historicalContext = "Sentado en la ladera del mar de Galilea, Jesús proclamó el manifiesto del Reino: las Bienaventuranzas para los pobres en espíritu, los mansos y los pacificadores; el llamado a ser la sal de la tierra y la luz del mundo; la superación de la letra de la ley hacia la pureza del corazón, la generosidad secreta, la oración del Padre Nuestro y el confiar sin afán como las aves y los lirios.",
            spiritualLesson = "El Reino de Dios transforma los valores de este mundo al revés. No vivas afanado por el mañana: busca primeramente el reino de Dios y su justicia, y todo lo demás será provisto.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Mateo 5-7), la narrativa bíblica nos presenta «El Sermón del Monte: Bienaventuranzas, Sal, Luz y el Padre Nuestro». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 132,
            title = "Día 132: La Fe del Centurión Romano y la Resurrección del Joven de Naín",
            passagesSummary = "Mateo 8; Lucas 7",
            primaryBookId = 40,
            primaryChapter = 8,
            primaryVerse = 5,
            passages = listOf(
                PlanPassageSegment(40, "Mateo", 8),
                PlanPassageSegment(42, "Lucas", 7),
            ),
            historicalContext = "Un centurión gentil en Capernaum rogó por su siervo paralítico, creyendo con una fe asombrosa: 'Solamente di la palabra, y mi criado sanará'. Jesús se maravilló diciendo que en nadie en Israel había hallado tanta fe. Luego en las puertas de Naín, compadecido por las lágrimas de una viuda que enterraba a su único hijo, tocó el féretro: 'Joven, a ti te digo, levántate', y el joven resucitó.",
            spiritualLesson = "La autoridad de la palabra de Jesús no tiene límites geográficos ni temporales. El llanto de los desamparados conmueve el corazón de Dios: donde entra Jesús, la muerte cede su lugar a la vida.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Mateo 8; Lucas 7), la narrativa bíblica nos presenta «La Fe del Centurión Romano y la Resurrección del Joven de Naín». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 133,
            title = "Día 133: Jesús Calma la Feroz Tempestad en el Mar de Galilea",
            passagesSummary = "Marcos 4",
            primaryBookId = 41,
            primaryChapter = 4,
            primaryVerse = 35,
            passages = listOf(
                PlanPassageSegment(41, "Marcos", 4),
            ),
            historicalContext = "Al anochecer, cruzando al otro lado, se desató una tormenta huracanada que anegaba la barca mientras Jesús dormía plácidamente sobre un cabezal en la popa. Desesperados, los discípulos le despertaron: 'Maestro, ¿no tienes cuidado que perecemos?'. Jesús se levantó, reprendió al viento y dijo al mar: 'Calla, enmudece'. Hubo gran calma y preguntaron: '¿Quién es este, que aun el viento y el mar le obedecen?'.",
            spiritualLesson = "Si Jesús está en tu barca, no te vas a hundir sin importar cuán feroces sean las olas. Reposa en su presencia y confía en el poder de su palabra sobre tus tormentas.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Marcos 4), la narrativa bíblica nos presenta «Jesús Calma la Feroz Tempestad en el Mar de Galilea». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 134,
            title = "Día 134: La Liberación Sobrenatural del Endemoniado Gadareno",
            passagesSummary = "Marcos 5",
            primaryBookId = 41,
            primaryChapter = 5,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(41, "Marcos", 5),
            ),
            historicalContext = "En la costa de Decápolis, un hombre atormentado por una legión de demonios vivía desnudo en los sepulcros hiriéndose con piedras y rompiendo cadenas sin que nadie pudiera domarlo. Al ver a Jesús de lejos corrió y se postró. Jesús expulsó a los demonios a un hato de cerdos que se precipitaron al mar. La multitud lo halló vestido, sentado y en su juicio cabal proclamando las maravillas de Dios.",
            spiritualLesson = "No hay cadena demoníaca, adicción o dolor que resista la autoridad libertadora de Jesús. El Señor restaura la dignidad de los más quebrantados y los envía como misioneros de su amor.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Marcos 5), la narrativa bíblica nos presenta «La Liberación Sobrenatural del Endemoniado Gadareno». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 135,
            title = "Día 135: La Mujer con Flujo de Sangre y la Resurrección de la Hija de Jairo",
            passagesSummary = "Marcos 5",
            primaryBookId = 41,
            primaryChapter = 5,
            primaryVerse = 21,
            passages = listOf(
                PlanPassageSegment(41, "Marcos", 5),
            ),
            historicalContext = "Yendo Jesús a la casa de Jairo (principal de la sinagoga) cuya hija de 12 años agonizaba, una mujer que llevaba 12 años padeciendo flujo de sangre y gastando todo en médicos sin mejoría, tocó el borde del manto de Jesús por fe: al instante quedó sana de su plaga. Poco después en casa de Jairo, tomó la mano de la niña muerta: 'Talita cumi' (Niña, a ti te digo, levántate), y ella se levantó.",
            spiritualLesson = "La fe audaz arrebata el poder sanador de Dios en medio de la multitud. Ante las noticias desalentadoras, escucha la voz de Jesús: 'No temas, cree solamente'.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Marcos 5), la narrativa bíblica nos presenta «La Mujer con Flujo de Sangre y la Resurrección de la Hija de Jairo». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 136,
            title = "Día 136: La Multiplicación de los Cinco Panes y Dos Peces",
            passagesSummary = "Juan 6",
            primaryBookId = 43,
            primaryChapter = 6,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(43, "Juan", 6),
            ),
            historicalContext = "En una colina cerca del mar de Galilea, 5,000 hombres sin contar mujeres y niños seguían a Jesús sin tener qué comer. Andrés señaló a un muchacho que tenía cinco panes de cebada y dos pececillos: '¿Qué es esto para tantos?'. Jesús tomó los panes, dio gracias, los partió y los distribuyó. Todos comieron hasta saciarse y sobraron doce cestas llenas. Luego proclamó: 'Yo soy el pan de vida'.",
            spiritualLesson = "Pon en las manos de Dios lo poco que tienes: con gratitud y consagración, Él lo multiplica de manera milagrosa para alimentar a multitudes hambrientas de su palabra.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Juan 6), la narrativa bíblica nos presenta «La Multiplicación de los Cinco Panes y Dos Peces». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 137,
            title = "Día 137: Jesús Camina sobre las Aguas y Rescata a Pedro",
            passagesSummary = "Mateo 14",
            primaryBookId = 40,
            primaryChapter = 14,
            primaryVerse = 22,
            passages = listOf(
                PlanPassageSegment(40, "Mateo", 14),
            ),
            historicalContext = "A la cuarta vigilia de la noche (3 a 6 a.m.), con la barca azotada por olas en medio del lago, Jesús se les acercó caminando sobre las aguas. Pedro dijo: 'Señor, si eres tú, manda que yo vaya a ti sobre las aguas'. Pedro bajó y caminó, pero al ver el viento fuerte tuvo miedo y comenzó a hundirse: '¡Señor, sálvame!'. Jesús extendió su mano, lo asió y le dijo: '¡Hombre de poca fe! ¿Por qué dudaste?'.",
            spiritualLesson = "Cuando quitas los ojos de Jesús para enfocarte en las tormentas y problemas del entorno, comienzas a hundirte. Mantén tu mirada fija en el Señor: su mano siempre está lista para levantarte.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Mateo 14), la narrativa bíblica nos presenta «Jesús Camina sobre las Aguas y Rescata a Pedro». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 138,
            title = "Día 138: La Confesión de Pedro y la Gloria de la Transfiguración",
            passagesSummary = "Mateo 16-17",
            primaryBookId = 40,
            primaryChapter = 16,
            primaryVerse = 13,
            passages = listOf(
                PlanPassageSegment(40, "Mateo", 16),
                PlanPassageSegment(40, "Mateo", 17),
            ),
            historicalContext = "En Cesarea de Filipo, Jesús preguntó: '¿Quién decís que soy yo?'. Simón Pedro respondió por revelación del Padre: 'Tú eres el Cristo, el Hijo del Dios viviente'. Seis días después en un monte alto, Jesús se transfiguró ante Pedro, Jacobo y Juan: su rostro resplandeció como el sol y sus vestidos blancos como la luz, conversando con Moisés y Elías. La voz divina tronó: 'A él oíd'.",
            spiritualLesson = "Jesús no es solo un buen maestro de moral o un profeta: es el Hijo eterno de Dios. Escuchar su voz y contemplar su gloria transforma nuestra mente y le da sentido a nuestras vidas.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Mateo 16-17), la narrativa bíblica nos presenta «La Confesión de Pedro y la Gloria de la Transfiguración». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 139,
            title = "Día 139: La Mujer Sorprendida en Adulterio: 'Ni Yo te Condeno'",
            passagesSummary = "Juan 8",
            primaryBookId = 43,
            primaryChapter = 8,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(43, "Juan", 8),
            ),
            historicalContext = "Los escribas y fariseos llevaron al Templo a una mujer sorprendida en el acto mismo de adulterio para tentar a Jesús según la lapidación mosaica. Jesús se inclinó y escribía en tierra con el dedo. Ante su insistencia, se enderezó: 'El que de vosotros esté sin pecado sea el primero en arrojar la piedra contra ella'. Uno a uno se retiraron avergonzados. Jesús le dijo: 'Ni yo te condeno; vete, y no peques más'.",
            spiritualLesson = "La gracia de Dios no ignora el pecado, sino que lo perdona y nos da poder para no volver a él. Quienes conocen su propia fragilidad no arrojan piedras acusadoras contra los demás.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Juan 8), la narrativa bíblica nos presenta «La Mujer Sorprendida en Adulterio: 'Ni Yo te Condeno'». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 140,
            title = "Día 140: La Sanidad del Ciego de Nacimiento en el Estanque de Siloé",
            passagesSummary = "Juan 9",
            primaryBookId = 43,
            primaryChapter = 9,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(43, "Juan", 9),
            ),
            historicalContext = "Ante un hombre ciego de nacimiento, los discípulos preguntaron quién había pecado, si él o sus padres. Jesús respondió: 'No es que pecó este, ni sus padres, sino para que las obras de Dios se manifiesten en él'. Escupió en tierra, hizo lodo, untó los ojos del ciego y le mandó lavarse en Siloé. Volvió viendo, y ante la hostilidad religiosa proclamó: 'Una cosa sé, que habiendo yo sido ciego, ahora veo'.",
            spiritualLesson = "Dios permite pruebas no para destruirnos, sino para manifestar en nosotros su gloria y salvación. No hay argumento que pueda refutar el testimonio vivo de una vida transformada por Cristo.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Juan 9), la narrativa bíblica nos presenta «La Sanidad del Ciego de Nacimiento en el Estanque de Siloé». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 141,
            title = "Día 141: Jesús, el Buen Pastor que da su Vida por las Ovejas",
            passagesSummary = "Juan 10",
            primaryBookId = 43,
            primaryChapter = 10,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(43, "Juan", 10),
            ),
            historicalContext = "En contraste con los asalariados que huyen cuando viene el lobo y con los ladrones que vienen a hurtar, matar y destruir, Jesús se reveló como la Puerta de las ovejas y el Buen Pastor: 'Yo he venido para que tengan vida, y para que la tengan en abundancia. El buen pastor su vida da por las ovejas... Mis ovejas oyen mi voz, y yo las conozco, y me siguen; y yo les doy vida eterna'.",
            spiritualLesson = "Estás seguro en las manos de Cristo; nadie puede arrebatarte de su amor y cuidado paternal. Escucha con docilidad su voz cada día y síguele a pastos delicados de paz.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Juan 10), la narrativa bíblica nos presenta «Jesús, el Buen Pastor que da su Vida por las Ovejas». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 142,
            title = "Día 142: Marta la Afanada y María a los Pies de Jesús en Betania",
            passagesSummary = "Lucas 10",
            primaryBookId = 42,
            primaryChapter = 10,
            primaryVerse = 38,
            passages = listOf(
                PlanPassageSegment(42, "Lucas", 10),
            ),
            historicalContext = "En su hogar de Betania, Marta se desvivía afanada con muchos quehaceres culinarios y de servicio para atender a los huéspedes, mientras su hermana María se sentó a los pies de Jesús a escuchar su palabra. Ante la queja de Marta, el Señor respondió con ternura: 'Marta, Marta, afanada y turbada estás con muchas cosas. Pero sólo una cosa es necesaria; y María ha escogido la buena parte, la cual no le será quitada'.",
            spiritualLesson = "No permitas que el activismo o el servicio a Dios desplacen tu comunión íntima y prioritaria con Dios. Sentarse a sus pies para escuchar su voz es la necesidad suprema del alma.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Lucas 10), la narrativa bíblica nos presenta «Marta la Afanada y María a los Pies de Jesús en Betania». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 143,
            title = "Día 143: La Parábola del Buen Samaritano: ¿Quién es mi Prójimo?",
            passagesSummary = "Lucas 10",
            primaryBookId = 42,
            primaryChapter = 10,
            primaryVerse = 25,
            passages = listOf(
                PlanPassageSegment(42, "Lucas", 10),
            ),
            historicalContext = "Un intérprete de la Ley preguntó a Jesús cómo heredar la vida eterna y quién era su prójimo. Jesús relató la historia del hombre asaltado y medio muerto en el camino desértico de Jerusalén a Jericó: un sacerdote y un levita pasaron de largo ignorándolo por ritualismo; pero un samaritano despreciado se compadeció, vendó sus heridas con aceite y vino, lo llevó al mesón y pagó su cuidado.",
            spiritualLesson = "El amor verdadero no se limita a declaraciones teológicas abstractas; se manifiesta en compasión práctica, misericordia activa y sacrificio real por los que sufren.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Lucas 10), la narrativa bíblica nos presenta «La Parábola del Buen Samaritano: ¿Quién es mi Prójimo?». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 144,
            title = "Día 144: Las Parábolas de la Gracia: La Oveja, la Moneda y el Hijo Pródigo",
            passagesSummary = "Lucas 15",
            primaryBookId = 42,
            primaryChapter = 15,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(42, "Lucas", 15),
            ),
            historicalContext = "Frente a los fariseos que murmuraban porque Jesús recibía a pecadores y comía con ellos, el Maestro relató tres joyas de la gracia: el pastor que deja las 99 para buscar a la oveja extraviada; la mujer que enciende la lámpara y barre con diligencia hasta hallar su dracma perdida; y el padre amoroso que corre con brazos abiertos a besar y vestir al hijo pródigo arrepentido que comía con cerdos.",
            spiritualLesson = "Hay fiesta en los cielos por un solo pecador que se arrepiente. No importa cuán lejos te hayas ido en el lodo del mundo: el Padre celestial te espera con los brazos abiertos para restituirte.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Lucas 15), la narrativa bíblica nos presenta «Las Parábolas de la Gracia: La Oveja, la Moneda y el Hijo Pródigo». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 145,
            title = "Día 145: La Parábola del Rico Insensato y del Rico y Lázaro",
            passagesSummary = "Lucas 12; 16",
            primaryBookId = 42,
            primaryChapter = 12,
            primaryVerse = 13,
            passages = listOf(
                PlanPassageSegment(42, "Lucas", 12),
                PlanPassageSegment(42, "Lucas", 16),
            ),
            historicalContext = "Jesús advirtió contra la avaricia con el rico que acumuló granos en graneros mayores jactándose de tener bienes para muchos años ('Necio, esta noche vienen a pedirte tu alma'). Luego describió al rico que vestía de púrpura y festejaba mientras el mendigo Lázaro yacía a su puerta lleno de llagas; al morir, Lázaro fue llevado al seno de Abraham y el rico al tormento eterno sin consuelo.",
            spiritualLesson = "Las posesiones terrenales no tienen valor en la eternidad. Vive una vida rica para con Dios, sensible al dolor del necesitado y con la mirada puesta en las recompensas celestiales eternas.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Lucas 12; 16), la narrativa bíblica nos presenta «La Parábola del Rico Insensato y del Rico y Lázaro». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 146,
            title = "Día 146: La Limpieza de los Diez Leprosos y el Samaritano Agradecido",
            passagesSummary = "Lucas 17",
            primaryBookId = 42,
            primaryChapter = 17,
            primaryVerse = 11,
            passages = listOf(
                PlanPassageSegment(42, "Lucas", 17),
            ),
            historicalContext = "En el límite entre Samaria y Galilea, diez hombres leprosos gritaron de lejos: '¡Jesús, Maestro, ten misericordia de nosotros!'. Jesús les ordenó presentarse a los sacerdotes; mientras iban, quedaron limpios. Sin embargo, solo uno de ellos —un samaritano— regresó glorificando a Dios a gran voz y postrándose en tierra para darle gracias. Jesús preguntó: '¿No son diez los que fueron limpiados? ¿Y los nueve, dónde están?'.",
            spiritualLesson = "La ingratitud es común en el corazón humano, pero la gratitud genuina nos conecta con la salvación integral. Aprende a volver siempre a los pies de Jesús para darle gracias por cada favor recibido.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Lucas 17), la narrativa bíblica nos presenta «La Limpieza de los Diez Leprosos y el Samaritano Agradecido». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 147,
            title = "Día 147: La Oración del Fariseo y el Publicano; Jesús Bendice a los Niños",
            passagesSummary = "Lucas 18",
            primaryBookId = 42,
            primaryChapter = 18,
            primaryVerse = 9,
            passages = listOf(
                PlanPassageSegment(42, "Lucas", 18),
            ),
            historicalContext = "Un fariseo oraba consigo mismo jactándose de sus ayunos y diezmos comparándose con los demás; en cambio, el publicano cobrador de impuestos no se atrevía ni a alzar los ojos al cielo, sino que se golpeaba el pecho: 'Dios, sé propicio a mí, pecador'. Jesús declaró que este descendió justificado. Luego reprendió a los discípulos que alejaban a los niños: 'Dejad a los niños venir a mí'.",
            spiritualLesson = "Dios resiste a los soberbios y da gracia a los humildes. Para entrar y disfrutar del Reino de Dios debemos despojarnos de la arrogancia religiosa y acercarnos con la fe transparente de un niño.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Lucas 18), la narrativa bíblica nos presenta «La Oración del Fariseo y el Publicano; Jesús Bendice a los Niños». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 148,
            title = "Día 148: El Joven Rico y el Peligro de las Riquezas",
            passagesSummary = "Marcos 10",
            primaryBookId = 41,
            primaryChapter = 10,
            primaryVerse = 17,
            passages = listOf(
                PlanPassageSegment(41, "Marcos", 10),
            ),
            historicalContext = "Un joven gobernante principal corrió, se arrodilló ante Jesús y preguntó: 'Maestro bueno, ¿qué haré para heredar la vida eterna?'. Habiendo guardado los mandamientos desde la juventud, Jesús le miró y le amó: 'Una cosa te falta: anda, vende todo lo que tienes, y dalo a los pobres... y ven, sígueme, tomando tu cruz'. El joven se fue triste porque tenía muchas posesiones.",
            spiritualLesson = "Cualquier tesoro, apego o comodidad terrenal que ames más que a Cristo se convierte en un ídolo que te roba el destino eterno. Entrégalo todo por Aquel que lo dio todo por ti.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Marcos 10), la narrativa bíblica nos presenta «El Joven Rico y el Peligro de las Riquezas». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 149,
            title = "Día 149: La Resurrección de Lázaro tras Cuatro Días en la Tumba",
            passagesSummary = "Juan 11",
            primaryBookId = 43,
            primaryChapter = 11,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(43, "Juan", 11),
            ),
            historicalContext = "En Betania, Lázaro enfermó y murió. Jesús llegó cuatro días después, cuando ya hedía. Consolando a Marta y María, proclamó: 'Yo soy la resurrección y la vida; el que cree en mí, aunque esté muerto, vivirá'. Jesús lloró conmovido ante el dolor de la tumba. Mandó quitar la piedra, oró al Padre y clamó a gran voz: '¡Lázaro, ven fuera!'. El que había muerto salió atado con vendas.",
            spiritualLesson = "Para Jesús ninguna circunstancia está demasiado muerta o perdida. Él tiene las llaves de la muerte y del Hades; su voz poderosa resucita la esperanza aun en los sepulcros más oscuros.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Juan 11), la narrativa bíblica nos presenta «La Resurrección de Lázaro tras Cuatro Días en la Tumba». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 150,
            title = "Día 150: La Sanidad del Ciego Bartimeo y el Encuentro con Zaqueo en Jericó",
            passagesSummary = "Lucas 18-19",
            primaryBookId = 42,
            primaryChapter = 18,
            primaryVerse = 35,
            passages = listOf(
                PlanPassageSegment(42, "Lucas", 18),
                PlanPassageSegment(42, "Lucas", 19),
            ),
            historicalContext = "Junto al camino de Jericó, el mendigo ciego Bartimeo clamó con insistencia venciendo la censura: '¡Jesús, Hijo de David, ten misericordia de mí!'. Recibió la vista por su fe. Ya en Jericó, Zaqueo, jefe de publicanos rico pero de baja estatura, subió a un sicómoro para ver a Jesús; el Señor se hospedó en su casa y Zaqueo restituyó cuatro veces lo defraudado: 'Hoy ha venido la salvación a esta casa'.",
            spiritualLesson = "El Hijo del Hombre vino a buscar y a salvar lo que se había perdido. Clama sin cesar al Salvador y dispon tu corazón para un cambio genuino de vida.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Lucas 18-19), la narrativa bíblica nos presenta «La Sanidad del Ciego Bartimeo y el Encuentro con Zaqueo en Jericó». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 151,
            title = "Día 151: María de Betania Unge los Pies de Jesús con Perfume Costoso",
            passagesSummary = "Juan 12",
            primaryBookId = 43,
            primaryChapter = 12,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(43, "Juan", 12),
            ),
            historicalContext = "Seis días antes de la Pascua, en una cena en Betania, María tomó una libra de perfume de nardo puro de gran precio (el salario de un año entero), quebró el frasco de alabastro, ungió los pies de Jesús y los enjugó con sus cabellos; la casa se llenó del olor del perfume. Judas Iscariote criticó con hipocresía la 'pérdida', pero Jesús la defendió: 'Para el día de mi sepultura ha guardado esto'.",
            spiritualLesson = "Nada de lo que derramas a los pies de Jesús es un desperdicio: es la adoración más sublime y pura. No escatimes lo más valioso de tu vida para honrar a tu Salvador.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Juan 12), la narrativa bíblica nos presenta «María de Betania Unge los Pies de Jesús con Perfume Costoso». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 152,
            title = "Día 152: La Entrada Triunfal en Jerusalén en el Domingo de Ramos",
            passagesSummary = "Mateo 21; Lucas 19",
            primaryBookId = 40,
            primaryChapter = 21,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(40, "Mateo", 21),
                PlanPassageSegment(42, "Lucas", 19),
            ),
            historicalContext = "Cumpliendo la profecía de Zacarías 9:9, Jesús montó en un pollino de asna que nadie había montado. Multitudes tendieron sus mantos y ramas de palmeras en el camino aclamando: '¡Hosanna al Hijo de David! ¡Bendito el que viene en el nombre del Señor!'. Al ver la ciudad de Jerusalén desde la cima del monte de los Olivos, Jesús lloró sobre ella por su ceguera espiritual ante la paz de Dios.",
            spiritualLesson = "Jesús entra a nuestras vidas no como un conquistador opresor terrenal, sino como el Rey de paz, manso y humilde. Corona a Cristo como Rey soberano de tu corazón cada día.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Mateo 21; Lucas 19), la narrativa bíblica nos presenta «La Entrada Triunfal en Jerusalén en el Domingo de Ramos». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 153,
            title = "Día 153: El Discurso Profético del Monte de los Olivos y las Diez Vírgenes",
            passagesSummary = "Mateo 24-25",
            primaryBookId = 40,
            primaryChapter = 24,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(40, "Mateo", 24),
                PlanPassageSegment(40, "Mateo", 25),
            ),
            historicalContext = "Sentado frente al Templo, Jesús profetizó las señales de su venida y del fin del siglo: falsos cristos, guerras, pestes, terremotos y el enfriamiento del amor. Ilustró la vigilancia con la parábola de las diez vírgenes: cinco prudentes llevaron aceite extra en sus vasijas y cinco insensatas no. A medianoche se oyó el clamor: '¡Aquí viene el esposo; salid a recibirle!'.",
            spiritualLesson = "Mantén tu lámpara encendida y provisión fresca del Espíritu Santo en tu vida. Vive con santa expectativa y fidelidad: el Señor vendrá en la hora que menos se espera.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Mateo 24-25), la narrativa bíblica nos presenta «El Discurso Profético del Monte de los Olivos y las Diez Vírgenes». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 154,
            title = "Día 154: La Parábola de los Talentos y el Juicio de las Naciones",
            passagesSummary = "Mateo 25",
            primaryBookId = 40,
            primaryChapter = 25,
            primaryVerse = 14,
            passages = listOf(
                PlanPassageSegment(40, "Mateo", 25),
            ),
            historicalContext = "Un noble entregó talentos a sus siervos conforme a su capacidad: cinco, dos y uno. Los dos primeros trabajaron y duplicaron los dones; el siervo perezoso escondió el talento en tierra por miedo y desconfianza. El Señor alabó a los fieles: 'Bien, buen siervo y fiel; sobre poco has sido fiel, sobre mucho te pondré; entra en el gozo de tu señor'. Luego describió el juicio de las ovejas y los cabritos.",
            spiritualLesson = "Dios te ha confiado dones, tiempo y recursos para invertirlos con valentía en su Reino. Lo que hiciste por uno de los más pequeños hermanos en necesidad, por Cristo mismo lo hiciste.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Mateo 25), la narrativa bíblica nos presenta «La Parábola de los Talentos y el Juicio de las Naciones». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 155,
            title = "Día 155: La Ofrenda Generosa de la Viuda Pobre en el Arca del Templo",
            passagesSummary = "Lucas 21",
            primaryBookId = 42,
            primaryChapter = 21,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(42, "Lucas", 21),
            ),
            historicalContext = "Observando a los ricos echar grandes sumas de sus riquezas en el tesoro del Templo, Jesús vio a una viuda muy pobre depositar dos blancas (la moneda de menor valor en el imperio romano). El Señor proclamó a sus discípulos: 'En verdad os digo, que esta viuda pobre echó más que todos; pues todos aquéllos echaron de lo que les sobra; mas ésta, de su pobreza echó todo el sustento que tenía'.",
            spiritualLesson = "Dios no mide el valor de tu ofrenda o servicio por la cantidad material externa, sino por la medida del sacrificio y la devoción del corazón. Da con generosidad alegre y desinteresada.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Lucas 21), la narrativa bíblica nos presenta «La Ofrenda Generosa de la Viuda Pobre en el Arca del Templo». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 156,
            title = "Día 156: Jesús Lava los Pies de sus Discípulos en la Última Cena",
            passagesSummary = "Juan 13",
            primaryBookId = 43,
            primaryChapter = 13,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(43, "Juan", 13),
            ),
            historicalContext = "Sabiendo Jesús que el Padre había puesto todas las cosas en sus manos y que venía de Dios y a Dios iba, se levantó de la cena, se quitó el manto, se ciñó con una toalla y vertió agua en un lebrillo para lavar los pies empolvados de sus discípulos, incluso los de Judas que ya lo traicionaba. Les dijo: 'Ejemplo os he dado, para que como yo os he hecho, vosotros también hagáis'.",
            spiritualLesson = "La verdadera grandeza en el Reino de Dios se mide por la disposición a servir con amor humilde a los demás. Quien no sabe descender para lavar los pies ajenos no puede liderar con el corazón de Cristo.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Juan 13), la narrativa bíblica nos presenta «Jesús Lava los Pies de sus Discípulos en la Última Cena». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 157,
            title = "Día 157: La Institución de la Santa Cena y el Nuevo Pacto",
            passagesSummary = "Mateo 26; Lucas 22",
            primaryBookId = 40,
            primaryChapter = 26,
            primaryVerse = 17,
            passages = listOf(
                PlanPassageSegment(40, "Mateo", 26),
                PlanPassageSegment(42, "Lucas", 22),
            ),
            historicalContext = "En el aposento alto celebrando la Pascua, Jesús tomó el pan sin levadura, lo bendijo, lo partió y lo dio a los doce: 'Tomad, comed; esto es mi cuerpo que por vosotros es dado'. Asimismo tomó la copa después de haber cenado: 'Esta copa es el nuevo pacto en mi sangre, que por vosotros se derrama para remisión de los pecados. Haced esto todas las veces que la bebiereis, en memoria de mí'.",
            spiritualLesson = "La Santa Cena es el memorial sagrado del sacrificio redentor de Cristo en el Calvario. Cada vez que participas proclamas su muerte expiatoria y renuevas tu consagración hasta que Él vuelva.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Mateo 26; Lucas 22), la narrativa bíblica nos presenta «La Institución de la Santa Cena y el Nuevo Pacto». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 158,
            title = "Día 158: La Agonía y Oración en el Huerto de Getsemaní",
            passagesSummary = "Mateo 26; Lucas 22",
            primaryBookId = 40,
            primaryChapter = 26,
            primaryVerse = 36,
            passages = listOf(
                PlanPassageSegment(40, "Mateo", 26),
                PlanPassageSegment(42, "Lucas", 22),
            ),
            historicalContext = "Bajo los olivos milenarios de Getsemaní, angustiado hasta la muerte por el peso aplastante del pecado del mundo y la copa de la ira divina, Jesús se postró sobre su rostro orando con gran clamor y lágrimas: 'Padre mío, si es posible, pase de mí esta copa; pero no sea como yo quiero, sino como tú'. Estando en agonía, su sudor cayó como grandes gotas de sangre mientras los discípulos dormían.",
            spiritualLesson = "En tus momentos de mayor agonía, somete tu voluntad a la voluntad perfecta del Padre celestial. En Getsemaní se ganó la victoria de la obediencia para nuestra salvación eterna.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Mateo 26; Lucas 22), la narrativa bíblica nos presenta «La Agonía y Oración en el Huerto de Getsemaní». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 159,
            title = "Día 159: El Arresto de Jesús, el Beso Traidor y las Negaciones de Pedro",
            passagesSummary = "Mateo 26; Juan 18",
            primaryBookId = 40,
            primaryChapter = 26,
            primaryVerse = 47,
            passages = listOf(
                PlanPassageSegment(40, "Mateo", 26),
                PlanPassageSegment(43, "Juan", 18),
            ),
            historicalContext = "Judas guio a una tropa de soldados con antorchas y entregó al Maestro con un beso falso de paz. Jesús sanó la oreja de Malco herido por la espada de Pedro diciendo: 'La copa que el Padre me ha dado, ¿no la he de beber?'. Todos los discípulos huyeron. Más tarde en el patio del sumo sacerdote, atemorizado ante una criada, Pedro negó a Jesús tres veces; el gallo cantó y Pedro lloró amargamente.",
            spiritualLesson = "La traición y la debilidad humana duelen profundamente, pero la misericordia de Jesús es mayor que nuestras caídas. Cuando falles, no huyas de Dios: vuelve con lágrimas de arrepentimiento.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Mateo 26; Juan 18), la narrativa bíblica nos presenta «El Arresto de Jesús, el Beso Traidor y las Negaciones de Pedro». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 160,
            title = "Día 160: Jesús ante el Sanedrín: La Declaración del Hijo del Hombre",
            passagesSummary = "Mateo 26-27",
            primaryBookId = 40,
            primaryChapter = 26,
            primaryVerse = 57,
            passages = listOf(
                PlanPassageSegment(40, "Mateo", 26),
                PlanPassageSegment(40, "Mateo", 27),
            ),
            historicalContext = "En un juicio nocturno ilegal plagado de falsos testigos, el sumo sacerdote Caifás conjuró a Jesús por el Dios viviente: 'Dinos si eres tú el Cristo, el Hijo de Dios'. Jesús respondió con serenidad mayestática: 'Tú lo has dicho; y además os digo, que desde ahora veréis al Hijo del Hombre sentado a la diestra del poder de Dios, y viniendo en las nubes del cielo'. Rasgaron sus ropas y lo condenaron.",
            spiritualLesson = "Jesús confesó la verdad con valor supremo aunque le costara la vida. Mantente firme en tu confesión de fe ante un mundo que rechaza la verdad divina.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Mateo 26-27), la narrativa bíblica nos presenta «Jesús ante el Sanedrín: La Declaración del Hijo del Hombre». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 161,
            title = "Día 161: Jesús ante Poncio Pilato, los Azotes y la Elección de Barrabás",
            passagesSummary = "Mateo 27; Juan 18-19",
            primaryBookId = 40,
            primaryChapter = 27,
            primaryVerse = 11,
            passages = listOf(
                PlanPassageSegment(40, "Mateo", 27),
                PlanPassageSegment(43, "Juan", 19),
            ),
            historicalContext = "Llevado al pretorio romano, Pilato reconoció: 'Yo no hallo en él ningún delito'. Presionado por la multitud incitada por los religiosos, ofreció liberar a Jesús por la fiesta, pero gritaron: '¡Suelta a Barrabás (un sedicioso homicida) y crucifica a Jesús!'. Pilato se lavó las manos; los soldados azotaron a Jesús con el flagelo romano, le pusieron manto de púrpura y corona de espinas.",
            spiritualLesson = "Jesús, el Justo e Inocente, tomó el lugar del pecador culpable (Barrabás) para que nosotros fuéramos absueltos y libres de condenación. Por sus llagas fuimos nosotros curados.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Mateo 27; Juan 18-19), la narrativa bíblica nos presenta «Jesús ante Poncio Pilato, los Azotes y la Elección de Barrabás». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 162,
            title = "Día 162: La Vía Dolorosa y la Crucifixión en el Monte Calvario",
            passagesSummary = "Lucas 23; Juan 19",
            primaryBookId = 42,
            primaryChapter = 23,
            primaryVerse = 26,
            passages = listOf(
                PlanPassageSegment(42, "Lucas", 23),
                PlanPassageSegment(43, "Juan", 19),
            ),
            historicalContext = "Cargando el madero con ayuda de Simón de Cirene, llegaron al Gólgota ('Lugar de la Calavera'). A la hora tercera lo clavaron en la cruz entre dos malhechores. Desde el leño santo, Jesús oró por sus verdugos: 'Padre, perdónalos, porque no saben lo que hacen'. Al ladrón arrepentido que le rogó: 'Acuérdate de mí', le prometió: 'De cierto te digo que hoy estarás conmigo en el paraíso'.",
            spiritualLesson = "La cruz es la máxima manifestación del amor redentor incondicional de Dios hacia una humanidad caída. Aun en medio del dolor más atroz, Jesús extendió perdón y salvación eterna.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Lucas 23; Juan 19), la narrativa bíblica nos presenta «La Vía Dolorosa y la Crucifixión en el Monte Calvario». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 163,
            title = "Día 163: Las Siete Palabras en la Cruz, la Muerte y la Sepultura",
            passagesSummary = "Juan 19; Lucas 23",
            primaryBookId = 43,
            primaryChapter = 19,
            primaryVerse = 25,
            passages = listOf(
                PlanPassageSegment(43, "Juan", 19),
                PlanPassageSegment(42, "Lucas", 23),
            ),
            historicalContext = "De la hora sexta a la novena (12 a 3 p.m.) densas tinieblas cubrieron la tierra. Jesús clamó: 'Consumado es' (Tetelestai: deuda pagada por completo) y 'Padre, en tus manos encomiendo mi espíritu'. El velo del Templo se rasgó en dos de arriba a abajo y la tierra tembló. El centurión confesó: 'Verdaderamente este hombre era Hijo de Dios'. José de Arimatea y Nicodemo lo sepultaron en tumba nueva.",
            spiritualLesson = "La deuda de tu pecado fue cancelada para siempre: la obra redentora fue terminada con perfección absoluta. El velo fue roto para que hoy tengas libre acceso al trono de la gracia de Dios.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Juan 19; Lucas 23), la narrativa bíblica nos presenta «Las Siete Palabras en la Cruz, la Muerte y la Sepultura». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 164,
            title = "Día 164: ¡Ha Resucitado!: La Tumba Vacía y la Aparición a María Magdalena",
            passagesSummary = "Mateo 28; Juan 20",
            primaryBookId = 40,
            primaryChapter = 28,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(40, "Mateo", 28),
                PlanPassageSegment(43, "Juan", 20),
            ),
            historicalContext = "Muy de mañana el primer día de la semana, María Magdalena y las mujeres llegaron con especias al sepulcro. Hubo un gran terremoto: un ángel bajó del cielo y removió la piedra sentándose sobre ella: 'No temáis... no está aquí, pues ha resucitado, como dijo. Venid, ved el lugar donde fue puesto el Señor'. María Magdalena lloraba en el huerto cuando Jesús la llamó por su nombre: '¡María!'.",
            spiritualLesson = "¡La muerte ha sido vencida para siempre por la resurrección de Jesucristo! La tumba está vacía: servimos a un Salvador que vive y que transforma nuestro llanto en gozo eterno.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Mateo 28; Juan 20), la narrativa bíblica nos presenta «¡Ha Resucitado!: La Tumba Vacía y la Aparición a María Magdalena». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 165,
            title = "Día 165: El Camino a Emaús, el Desayuno en Galilea y la Gran Comisión",
            passagesSummary = "Lucas 24; Juan 21; Hechos 1",
            primaryBookId = 42,
            primaryChapter = 24,
            primaryVerse = 13,
            passages = listOf(
                PlanPassageSegment(42, "Lucas", 24),
                PlanPassageSegment(43, "Juan", 21),
                PlanPassageSegment(44, "Hechos", 1),
            ),
            historicalContext = "Jesús caminó de incógnito con dos discípulos desanimados hacia Emaús explicándoles las Escrituras, y sus corazones ardían; se reveló al partir el pan. En las playas de Galilea preparó peces sobre brasas para sus discípulos y restauró a Pedro: '¿Me amas? Apacienta mis ovejas'. En el monte de los Olivos proclamó la Gran Comisión ('Id por todo el mundo') y ascendió al cielo en una nube de gloria.",
            spiritualLesson = "Jesucristo vive hoy y camina a tu lado haciendo arder tu corazón con su Palabra viva. Comparte el evangelio con valentía sabiendo que Él está contigo todos los días hasta el fin del mundo.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Lucas 24; Juan 21; Hechos 1), la narrativa bíblica nos presenta «El Camino a Emaús, el Desayuno en Galilea y la Gran Comisión». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 166,
            title = "Día 166: El Derramamiento del Espíritu Santo en Pentecostés",
            passagesSummary = "Hechos 2",
            primaryBookId = 44,
            primaryChapter = 2,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(44, "Hechos", 2),
            ),
            historicalContext = "Estando 120 discípulos reunidos unánimes en oración en el aposento alto en la fiesta de Pentecostés, vino del cielo un estruendo como de viento recio y lenguas como de fuego sobre cada uno. Fueron llenos del Espíritu Santo hablando las maravillas de Dios en diversas lenguas. Pedro proclamó con poder el cumplimiento profético de Joel, y cerca de 3,000 almas se convirtieron y fueron bautizadas.",
            spiritualLesson = "El Espíritu Santo es la promesa del Padre que capacita a la Iglesia con poder y denuedo sobrenatural para ser testigos de Cristo. Busca diariamente la llenura viva de su Espíritu.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Hechos 2), la narrativa bíblica nos presenta «El Derramamiento del Espíritu Santo en Pentecostés». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 167,
            title = "Día 167: La Curación Milagrosa del Cojo en la Puerta Hermosa del Templo",
            passagesSummary = "Hechos 3",
            primaryBookId = 44,
            primaryChapter = 3,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(44, "Hechos", 3),
            ),
            historicalContext = "Pedro y Juan subían al Templo a la hora novena de la oración. Un hombre cojo de nacimiento pedía limosna en la puerta llamada Hermosa. Pedro fijó los ojos en él: 'No tengo plata ni oro, pero lo que tengo te doy; en el nombre de Jesucristo de Nazaret, levántate y anda'. Tomándolo por la mano derecha, sus tobillos se afirmaron: entró al Templo saltando y alabando a Dios ante el asombro popular.",
            spiritualLesson = "El nombre de Jesucristo tiene poder absoluto sobre toda parálisis espiritual y física. Lo más valioso que la Iglesia posee no son riquezas materiales, sino la presencia y autoridad viva de Jesús.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Hechos 3), la narrativa bíblica nos presenta «La Curación Milagrosa del Cojo en la Puerta Hermosa del Templo». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 168,
            title = "Día 168: La Valentía ante el Concilio y la Comunidad de Amor",
            passagesSummary = "Hechos 4",
            primaryBookId = 44,
            primaryChapter = 4,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(44, "Hechos", 4),
            ),
            historicalContext = "Arrestados y llevados ante el Sanedrín, Pedro lleno del Espíritu proclamó con audacia que en ningún otro hay salvación. Al prohibirles hablar en el nombre de Jesús, respondieron: 'Juzgad si es justo delante de Dios obedecer a vosotros antes que a Dios; porque no podemos dejar de decir lo que hemos visto y oído'. La comunidad oró, el lugar tembló y compartían todo con un solo corazón y un alma.",
            spiritualLesson = "La verdadera fe no se acobarda ante la censura o la hostilidad de este mundo. Cuando una comunidad ora unánime en el Espíritu Santo, el poder de Dios se desata con señales y amor fraternal.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Hechos 4), la narrativa bíblica nos presenta «La Valentía ante el Concilio y la Comunidad de Amor». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 169,
            title = "Día 169: El Juicio contra el Engaño de Ananías y Safira",
            passagesSummary = "Hechos 5",
            primaryBookId = 44,
            primaryChapter = 5,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(44, "Hechos", 5),
            ),
            historicalContext = "Ananías y su esposa Safira vendieron una heredad pero sustrajeron con hipocresía parte del precio fingiendo entregar el total para ganar reconocimiento social. Pedro confrontó a Ananías: '¿Por qué llenó Satanás tu corazón para que mintieses al Espíritu Santo? No has mentido a los hombres, sino a Dios'. Ambos cayeron muertos y un gran temor santo sobrevino a toda la iglesia.",
            spiritualLesson = "Dios ama la generosidad sincera pero aborrece la falsedad y la hipocresía religiosa. Vive con autenticidad y reverencia delante de un Dios que conoce los secretos más íntimos del corazón.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Hechos 5), la narrativa bíblica nos presenta «El Juicio contra el Engaño de Ananías y Safira». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 170,
            title = "Día 170: La Elección de los Siete Diáconos y la Gracia de Esteban",
            passagesSummary = "Hechos 6",
            primaryBookId = 44,
            primaryChapter = 6,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(44, "Hechos", 6),
            ),
            historicalContext = "Para atender con justicia a las viudas griegas sin descuidar el ministerio de la oración y de la palabra, los apóstoles nombraron a siete varones de buen testimonio, llenos del Espíritu Santo y de sabiduría, entre ellos Esteban y Felipe. Esteban, lleno de gracia y poder, hacía grandes prodigios y nadie podía resistir a la sabiduría del Espíritu con que hablaba ante el concilio.",
            spiritualLesson = "Todo servicio en el cuerpo de Cristo, desde la administración hasta la predicación, es sagrado y requiere llenura del Espíritu. Sirve con fidelidad y gracia en el lugar donde Dios te ha colocado.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Hechos 6), la narrativa bíblica nos presenta «La Elección de los Siete Diáconos y la Gracia de Esteban». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 171,
            title = "Día 171: El Martirio de Esteban, Felipe en Samaria y el Eunuco Etíope",
            passagesSummary = "Hechos 7-8",
            primaryBookId = 44,
            primaryChapter = 7,
            primaryVerse = 54,
            passages = listOf(
                PlanPassageSegment(44, "Hechos", 7),
                PlanPassageSegment(44, "Hechos", 8),
            ),
            historicalContext = "Apedreado por su defensa apasionada de la fe, Esteban vio los cielos abiertos y al Hijo del Hombre de pie a la diestra de Dios; oró perdonando a sus asesinos mientras el joven Saulo guardaba las ropas. La persecución esparció el evangelio: Felipe predicó en Samaria con grandes milagros y en el camino a Gaza explicó Isaías 53 a un ministro etíope bautizándolo en el desierto con gozo.",
            spiritualLesson = "La sangre de los mártires es semilla fecunda de nuevos creyentes. Dios orquesta encuentros divinos milagrosos para llevar las buenas noticias de salvación a todo corazón que busca la verdad.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Hechos 7-8), la narrativa bíblica nos presenta «El Martirio de Esteban, Felipe en Samaria y el Eunuco Etíope». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 172,
            title = "Día 172: La Conversión Radical de Saulo de Tarso en el Camino a Damasco",
            passagesSummary = "Hechos 9",
            primaryBookId = 44,
            primaryChapter = 9,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(44, "Hechos", 9),
            ),
            historicalContext = "Respirando amenazas y muerte contra los discípulos con cartas para arrestarlos, una luz del cielo más brillante que el sol rodeó a Saulo en el camino a Damasco. Cayó en tierra y oyó la voz: 'Saulo, Saulo, ¿por qué me persigues?... Yo soy Jesús, a quien tú persigues'. Ciego por tres días en oración, Ananías le impuso manos; cayeron escamas de sus ojos, fue lleno del Espíritu y bautizado.",
            spiritualLesson = "Nadie está tan lejos o endurecido que la gracia de Jesús no pueda alcanzarlo y transformarlo por completo. Aquel que perseguía a la Iglesia se convirtió en el mayor misionero del evangelio.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Hechos 9), la narrativa bíblica nos presenta «La Conversión Radical de Saulo de Tarso en el Camino a Damasco». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 173,
            title = "Día 173: El Ministerio de Pedro: La Curación de Eneas y la Resurrección de Dorcas",
            passagesSummary = "Hechos 9",
            primaryBookId = 44,
            primaryChapter = 9,
            primaryVerse = 32,
            passages = listOf(
                PlanPassageSegment(44, "Hechos", 9),
            ),
            historicalContext = "En Lida, Pedro sanó en el nombre de Jesucristo a Eneas, paralítico desde hacía ocho años postrado en cama. En la ciudad costera de Jope murió Tabita (Dorcas), discípula amada que abundaba en buenas obras y limosnas haciendo túnicas para las viudas. Pedro oró de rodillas en el aposento alto: 'Tabita, levántate'. Ella abrió los ojos y resucitó, llevando a muchos a creer en el Señor.",
            spiritualLesson = "El poder de Dios confirma el evangelio con milagros de compasión y vida. Tu vida de servicio silencioso, generosidad y amor práctico hacia los pobres tiene un valor inmenso ante los ojos de Dios.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Hechos 9), la narrativa bíblica nos presenta «El Ministerio de Pedro: La Curación de Eneas y la Resurrección de Dorcas». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 174,
            title = "Día 174: La Visión del Lienzo a Pedro y la Salvación de los Gentiles",
            passagesSummary = "Hechos 10",
            primaryBookId = 44,
            primaryChapter = 10,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(44, "Hechos", 10),
            ),
            historicalContext = "En Cesarea, el centurión piadoso Cornelio vio a un ángel que le mandó llamar a Pedro. Mientras tanto en Jope, Pedro oraba en la azotea y tuvo una visión de un gran lienzo bajando del cielo con animales impuros: 'Lo que Dios limpió, no lo llames tú común'. Al llegar a casa de Cornelio y predicar a Cristo, el Espíritu Santo cayó sobre todos los gentiles oyentes hablando en lenguas y siendo bautizados.",
            spiritualLesson = "Dios no hace acepción de personas: en toda nación se agrada del que le teme y hace justicia. El muro de división fue derribado para que todos seamos uno en el cuerpo de Cristo.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Hechos 10), la narrativa bíblica nos presenta «La Visión del Lienzo a Pedro y la Salvación de los Gentiles». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 175,
            title = "Día 175: La Liberación Milagrosa de Pedro por el Ángel y el Fin de Agripa",
            passagesSummary = "Hechos 12",
            primaryBookId = 44,
            primaryChapter = 12,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(44, "Hechos", 12),
            ),
            historicalContext = "El rey Herodes Agripa I decapitó al apóstol Jacobo y encarceló a Pedro con cuatro escuadrones de soldados para ejecutarlo tras la Pascua. Pero la iglesia hacía sin cesar oración a Dios por él. La noche antes, un ángel despertó a Pedro, las cadenas cayeron, las puertas de hierro se abrieron solas y salió libre. Días después en Cesarea, Herodes no dio la gloria a Dios y pereció herido por un ángel.",
            spiritualLesson = "La oración incesante de la iglesia es más poderosa que las cadenas, los soldados y las prisiones de este mundo. Los tiranos pasan y perecen, pero la Palabra de Dios crece y se multiplica.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Hechos 12), la narrativa bíblica nos presenta «La Liberación Milagrosa de Pedro por el Ángel y el Fin de Agripa». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 176,
            title = "Día 176: El Primer Viaje Misionero de Pablo y Bernabé",
            passagesSummary = "Hechos 13-14",
            primaryBookId = 44,
            primaryChapter = 13,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(44, "Hechos", 13),
                PlanPassageSegment(44, "Hechos", 14),
            ),
            historicalContext = "Ministrando y ayunando en la iglesia de Antioquía, el Espíritu Santo ordenó: 'Apartadme a Bernabé y a Saulo para la obra a que los he llamado'. Viajaron a Chipre y Galacia predicando en las sinagogas. En Listra sanaron a un cojo de nacimiento; la multitud intentó adorarlos como dioses paganos y luego apedreó a Pablo dejándolo por muerto, pero Dios lo levantó y confirmó las iglesias.",
            spiritualLesson = "La misión transcultural requiere la guía del Espíritu y perseverancia ante el sufrimiento: 'Es necesario que a través de muchas tribulaciones entremos en el reino de Dios'.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Hechos 13-14), la narrativa bíblica nos presenta «El Primer Viaje Misionero de Pablo y Bernabé». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 177,
            title = "Día 177: El Concilio de Jerusalén, Pablo y Silas Cantando en Filipos",
            passagesSummary = "Hechos 15-16",
            primaryBookId = 44,
            primaryChapter = 15,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(44, "Hechos", 15),
                PlanPassageSegment(44, "Hechos", 16),
            ),
            historicalContext = "El Concilio de Jerusalén zanjó con sabiduría que la salvación es por gracia por medio de la fe sin imponer la circuncisión mosaica a los gentiles. En el segundo viaje misionero, en Filipos de Macedonia, tras liberar a una muchacha esclava de un espíritu de adivinación, Pablo y Silas fueron azotados y metidos en el calabozo de adentro con cepos. A medianoche cantaban himnos a Dios: un gran terremoto abrió las puertas y el carcelero se convirtió con toda su casa.",
            spiritualLesson = "La alabanza a Dios en medio del dolor y la injusticia desata terremotos espirituales que rompen cadenas y abren puertas de salvación para familias enteras.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Hechos 15-16), la narrativa bíblica nos presenta «El Concilio de Jerusalén, Pablo y Silas Cantando en Filipos». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 178,
            title = "Día 178: Pablo en el Areópago de Atenas y el Avivamiento en Éfeso",
            passagesSummary = "Hechos 17; 19",
            primaryBookId = 44,
            primaryChapter = 17,
            primaryVerse = 16,
            passages = listOf(
                PlanPassageSegment(44, "Hechos", 17),
                PlanPassageSegment(44, "Hechos", 19),
            ),
            historicalContext = "En la cuna filosófica de Atenas, indignado ante la idolatría, Pablo predicó en el Areópago sobre el 'Dios no conocido': el Creador que no habita en templos humanos, da a todos vida y aliento y resucitó a Jesús. Más tarde en Éfeso ministró tres años; el poder de Dios era tan extraordinario que aun pañuelos y delantales de su cuerpo sanaban enfermos, y magos quemaron libros ocultistas por 50,000 monedas de plata.",
            spiritualLesson = "Presenta la verdad de Cristo con inteligencia y valor ante cualquier cultura o corriente intelectual. El poder del evangelio quebranta la brujería y el ocultismo transformando ciudades enteras.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Hechos 17; 19), la narrativa bíblica nos presenta «Pablo en el Areópago de Atenas y el Avivamiento en Éfeso». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 179,
            title = "Día 179: El Gran Naufragio en el Mediterráneo y la Víbora en Malta",
            passagesSummary = "Hechos 27-28",
            primaryBookId = 44,
            primaryChapter = 27,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(44, "Hechos", 27),
                PlanPassageSegment(44, "Hechos", 28),
            ),
            historicalContext = "Enviado como prisionero a Roma, su barco fue azotado durante 14 días de oscuridad total por el huracán Euroclidón. Un ángel aseguró a Pablo que todos los 276 tripulantes se salvarían. Encallaron en la isla de Malta; al recoger leña, una víbora venenosa mordió la mano de Pablo y él la sacudió al fuego sin sufrir daño alguno. Sanó al padre de Publio y predicó en Roma bajo custodia con total denuedo.",
            spiritualLesson = "Cuando estás en el propósito de Dios, ninguna tormenta en el mar ni veneno de víbora puede detener tu destino. Cumplirás tu misión porque Dios vela por ti.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Hechos 27-28), la narrativa bíblica nos presenta «El Gran Naufragio en el Mediterráneo y la Víbora en Malta». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
        ReadingPlanDay(
            dayNumber = 180,
            title = "Día 180: La Visión del Cristo Coronado y la Nueva Jerusalén Eterna",
            passagesSummary = "Apocalipsis 1; 21-22",
            primaryBookId = 66,
            primaryChapter = 1,
            primaryVerse = 1,
            passages = listOf(
                PlanPassageSegment(66, "Apocalipsis", 1),
                PlanPassageSegment(66, "Apocalipsis", 21),
                PlanPassageSegment(66, "Apocalipsis", 22),
            ),
            historicalContext = "Desterrado en la isla rocosa de Patmos por la palabra de Dios, el anciano apóstol Juan vio al Señor resucitado y coronado con ojos como llama de fuego: 'Yo soy el Primero y el Último; el que vivo, y estuve muerto; mas he aquí que vivo por los siglos de los siglos'. Contempló un cielo nuevo y una tierra nueva, la santa ciudad Jerusalén descendiendo del cielo: Dios enjugará toda lágrima y no habrá más muerte, llanto ni dolor. ¡Cristo viene pronto!",
            spiritualLesson = "Esta es la gloriosa consumación de toda la historia bíblica: el triunfo final y eterno del Cordero de Dios. Guarda tu corazón en santidad y esperanza viva proclamando: '¡Amén; sí, ven, Señor Jesús!'.",
            storyNarrative = "En este acontecimiento trascendental de las Sagradas Escrituras (Apocalipsis 1; 21-22), la narrativa bíblica nos presenta «La Visión del Cristo Coronado y la Nueva Jerusalén Eterna». A través de los sucesos registrados fielmente en el texto inspirado, contemplamos la soberanía inquebrantable de Dios obrando en medio de la historia humana, guiando a hombres y mujeres de fe y confrontando la rebeldía para cumplir sus eternos propósitos de salvación y gracia redentora. Cada detalle de este relato refleja el poder divino transformando las circunstancias más adversas en testimonios vivos de su gloria para todas las generaciones."
        ),
    )
}
