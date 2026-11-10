/**
 * BibleVerse Web - Catálogo Bíblico y Almacenamiento Local Offline
 * Contiene los 66 libros de las Sagradas Escrituras, pasajes precargados y soporte Bolls API.
 */

const BIBLE_BOOKS = [
  // Antiguo Testamento
  { id: 1, name: "Génesis", abbr: "Gén", testament: "AT", chapters: 50 },
  { id: 2, name: "Éxodo", abbr: "Éxo", testament: "AT", chapters: 40 },
  { id: 3, name: "Levítico", abbr: "Lev", testament: "AT", chapters: 27 },
  { id: 4, name: "Números", abbr: "Núm", testament: "AT", chapters: 36 },
  { id: 5, name: "Deuteronomio", abbr: "Deu", testament: "AT", chapters: 34 },
  { id: 6, name: "Josué", abbr: "Jos", testament: "AT", chapters: 24 },
  { id: 7, name: "Jueces", abbr: "Jue", testament: "AT", chapters: 21 },
  { id: 8, name: "Rut", abbr: "Rut", testament: "AT", chapters: 4 },
  { id: 9, name: "1 Samuel", abbr: "1Sa", testament: "AT", chapters: 31 },
  { id: 10, name: "2 Samuel", abbr: "2Sa", testament: "AT", chapters: 24 },
  { id: 11, name: "1 Reyes", abbr: "1Re", testament: "AT", chapters: 22 },
  { id: 12, name: "2 Reyes", abbr: "2Re", testament: "AT", chapters: 25 },
  { id: 13, name: "1 Crónicas", abbr: "1Cr", testament: "AT", chapters: 29 },
  { id: 14, name: "2 Crónicas", abbr: "2Cr", testament: "AT", chapters: 36 },
  { id: 15, name: "Esdras", abbr: "Esd", testament: "AT", chapters: 10 },
  { id: 16, name: "Nehemías", abbr: "Neh", testament: "AT", chapters: 13 },
  { id: 17, name: "Ester", abbr: "Est", testament: "AT", chapters: 10 },
  { id: 18, name: "Job", abbr: "Job", testament: "AT", chapters: 42 },
  { id: 19, name: "Salmos", abbr: "Sal", testament: "AT", chapters: 150 },
  { id: 20, name: "Proverbios", abbr: "Pro", testament: "AT", chapters: 31 },
  { id: 21, name: "Eclesiastés", abbr: "Ecl", testament: "AT", chapters: 12 },
  { id: 22, name: "Cantares", abbr: "Cnt", testament: "AT", chapters: 8 },
  { id: 23, name: "Isaías", abbr: "Isa", testament: "AT", chapters: 66 },
  { id: 24, name: "Jeremías", abbr: "Jer", testament: "AT", chapters: 52 },
  { id: 25, name: "Lamentaciones", abbr: "Lam", testament: "AT", chapters: 5 },
  { id: 26, name: "Ezequiel", abbr: "Eze", testament: "AT", chapters: 48 },
  { id: 27, name: "Daniel", abbr: "Dan", testament: "AT", chapters: 12 },
  { id: 28, name: "Oseas", abbr: "Ose", testament: "AT", chapters: 14 },
  { id: 29, name: "Joel", abbr: "Joe", testament: "AT", chapters: 3 },
  { id: 30, name: "Amós", abbr: "Amó", testament: "AT", chapters: 9 },
  { id: 31, name: "Abdías", abbr: "Abd", testament: "AT", chapters: 1 },
  { id: 32, name: "Jonás", abbr: "Jon", testament: "AT", chapters: 4 },
  { id: 33, name: "Miqueas", abbr: "Miq", testament: "AT", chapters: 7 },
  { id: 34, name: "Nahúm", abbr: "Nah", testament: "AT", chapters: 3 },
  { id: 35, name: "Habacuc", abbr: "Hab", testament: "AT", chapters: 3 },
  { id: 36, name: "Sofonías", abbr: "Sof", testament: "AT", chapters: 3 },
  { id: 37, name: "Hageo", abbr: "Hag", testament: "AT", chapters: 2 },
  { id: 38, name: "Zacarías", abbr: "Zac", testament: "AT", chapters: 14 },
  { id: 39, name: "Malaquías", abbr: "Mal", testament: "AT", chapters: 4 },

  // Nuevo Testamento
  { id: 40, name: "Mateo", abbr: "Mat", testament: "NT", chapters: 28 },
  { id: 41, name: "Marcos", abbr: "Mar", testament: "NT", chapters: 16 },
  { id: 42, name: "Lucas", abbr: "Luc", testament: "NT", chapters: 24 },
  { id: 43, name: "Juan", abbr: "Jua", testament: "NT", chapters: 21 },
  { id: 44, name: "Hechos", abbr: "Hch", testament: "NT", chapters: 28 },
  { id: 45, name: "Romanos", abbr: "Rom", testament: "NT", chapters: 16 },
  { id: 46, name: "1 Corintios", abbr: "1Co", testament: "NT", chapters: 16 },
  { id: 47, name: "2 Corintios", abbr: "2Co", testament: "NT", chapters: 13 },
  { id: 48, name: "Gálatas", abbr: "Gál", testament: "NT", chapters: 6 },
  { id: 49, name: "Efesios", abbr: "Efe", testament: "NT", chapters: 6 },
  { id: 50, name: "Filipenses", abbr: "Fil", testament: "NT", chapters: 4 },
  { id: 51, name: "Colosenses", abbr: "Col", testament: "NT", chapters: 4 },
  { id: 52, name: "1 Tesalonicenses", abbr: "1Te", testament: "NT", chapters: 5 },
  { id: 53, name: "2 Tesalonicenses", abbr: "2Te", testament: "NT", chapters: 3 },
  { id: 54, name: "1 Timoteo", abbr: "1Ti", testament: "NT", chapters: 6 },
  { id: 55, name: "2 Timoteo", abbr: "2Ti", testament: "NT", chapters: 4 },
  { id: 56, name: "Tito", abbr: "Tit", testament: "NT", chapters: 3 },
  { id: 57, name: "Filemón", abbr: "Flm", testament: "NT", chapters: 1 },
  { id: 58, name: "Hebreos", abbr: "Heb", testament: "NT", chapters: 13 },
  { id: 59, name: "Santiago", abbr: "Stg", testament: "NT", chapters: 5 },
  { id: 60, name: "1 Pedro", abbr: "1Pe", testament: "NT", chapters: 5 },
  { id: 61, name: "2 Pedro", abbr: "2Pe", testament: "NT", chapters: 3 },
  { id: 62, name: "1 Juan", abbr: "1Jn", testament: "NT", chapters: 5 },
  { id: 63, name: "2 Juan", abbr: "2Jn", testament: "NT", chapters: 1 },
  { id: 64, name: "3 Juan", abbr: "3Jn", testament: "NT", chapters: 1 },
  { id: 65, name: "Judas", abbr: "Jud", testament: "NT", chapters: 1 },
  { id: 66, name: "Apocalipsis", abbr: "Apo", testament: "NT", chapters: 22 }
];

// Versículos del Día destacados y temáticos
const DAILY_VERSES = [
  {
    id: 1,
    book: "Filipenses",
    chapter: 4,
    verse: 13,
    text: "Todo lo puedo en Cristo que me fortalece.",
    theme: "Fortaleza",
    reflection: "Cuando sientas que tus fuerzas se agotan, recuerda que no dependes de tu propia energía sino de la gracia sustentadora de Dios."
  },
  {
    id: 2,
    book: "Jeremías",
    chapter: 29,
    verse: 11,
    text: "Porque yo sé los pensamientos que tengo acerca de vosotros, dice Jehová, pensamientos de paz, y no de mal, para daros el fin que esperáis.",
    theme: "Esperanza",
    reflection: "El futuro no está en manos del azar. Dios tiene trazado un propósito eterno lleno de paz y propósito para tu vida."
  },
  {
    id: 3,
    book: "Salmos",
    chapter: 23,
    verse: 1,
    text: "Jehová es mi pastor; nada me faltará.",
    theme: "Paz",
    reflection: "El Pastor cuida, guía y protege a sus ovejas. En Su presencia encontramos descanso y provisión sobrenatural."
  },
  {
    id: 4,
    book: "Proverbios",
    chapter: 3,
    verses: "5-6",
    text: "Fíate de Jehová de todo tu corazón, y no te apoyes en tu propia prudencia. Reconócelo en todos tus caminos, y él enderezará tus veredas.",
    theme: "Sabiduría",
    reflection: "Rendir nuestro entendimiento ante Dios es el principio de una vida con dirección clara y pasos firmes."
  },
  {
    id: 5,
    book: "Mateo",
    chapter: 6,
    verse: 33,
    text: "Mas buscad primeramente el reino de Dios y su justicia, y todas estas cosas os serán añadidas.",
    theme: "Prioridad",
    reflection: "Cuando ordenamos nuestras prioridades hacia lo eterno, las necesidades temporales caen en su justo lugar bajo la provisión divina."
  },
  {
    id: 6,
    book: "Romanos",
    chapter: 8,
    verse: 28,
    text: "Y sabemos que a los que aman a Dios, todas las cosas les ayudan a bien, esto es, a los que conforme a su propósito son llamados.",
    theme: "Fe",
    reflection: "Aun en medio de las pruebas más difíciles, Dios teje cada acontecimiento para tu crecimiento y para Su gloria."
  }
];

// Pasajes emblemáticos precargados 100% offline
const PRELOADED_CHAPTERS = {
  // Salmos 23
  "19_23": [
    { verse: 1, text: "Jehová es mi pastor; nada me faltará." },
    { verse: 2, text: "En lugares de delicados pastos me hará descansar; junto a aguas de reposo me pastoreará." },
    { verse: 3, text: "Confortará mi alma; me guiará por sendas de justicia por amor de su nombre." },
    { verse: 4, text: "Aunque ande en valle de sombra de muerte, no temeré mal alguno, porque tú estarás conmigo; tu vara y tu cayado me infundirán aliento." },
    { verse: 5, text: "Aderezas mesa delante de mí en presencia de mis angustiadores; unges mi cabeza con aceite; mi copa está rebosando." },
    { verse: 6, text: "Ciertamente el bien y la misericordia me seguirán todos los días de mi vida, y en la casa de Jehová moraré por largos días." }
  ],
  // Salmos 91
  "19_91": [
    { verse: 1, text: "El que habita al abrigo del Altísimo morará bajo la sombra del Omnipotente." },
    { verse: 2, text: "Diré yo a Jehová: Esperanza mía, y castillo mío; mi Dios, en quien confiaré." },
    { verse: 3, text: "Él te librará del lazo del cazador, de la peste destructora." },
    { verse: 4, text: "Con sus plumas te cubrirá, y debajo de sus alas estarás seguro; escudo y adarga es su verdad." },
    { verse: 5, text: "No temerás el terror nocturno, ni saeta que vuele de día," },
    { verse: 6, text: "Ni pestilencia que ande en oscuridad, ni mortandad que en medio del día destruya." },
    { verse: 7, text: "Caerán a tu lado mil, y diez mil a tu diestra; mas a ti no llegará." }
  ],
  // Juan 1
  "43_1": [
    { verse: 1, text: "En el principio era el Verbo, y el Verbo era con Dios, y el Verbo era Dios." },
    { verse: 2, text: "Este era en el principio con Dios." },
    { verse: 3, text: "Todas las cosas por él fueron hechas, y sin él nada de lo que ha sido hecho, fue hecho." },
    { verse: 4, text: "En él estaba la vida, y la vida era la luz de los hombres." },
    { verse: 5, text: "La luz en las tinieblas resplandece, y las tinieblas no prevalecieron contra ella." },
    { verse: 14, text: "Y aquel Verbo fue hecho carne, y habitó entre nosotros (y vimos su gloria, gloria como del unigénito del Padre), lleno de gracia y de verdad." }
  ],
  // Juan 3
  "43_3": [
    { verse: 16, text: "Porque de tal manera amó Dios al mundo, que ha dado a su Hijo unigénito, para que todo aquel que en él cree, no se pierda, mas tenga vida eterna." },
    { verse: 17, text: "Porque no envió Dios a su Hijo al mundo para condenar al mundo, sino para que el mundo sea salvo por él." }
  ],
  // Filipenses 4
  "50_4": [
    { verse: 4, text: "Regocijaos en el Señor siempre. Otra vez digo: ¡Regocijaos!" },
    { verse: 6, text: "Por nada estéis afanosos, sino sean conocidas vuestras peticiones delante de Dios en toda oración y ruego, con acción de gracias." },
    { verse: 7, text: "Y la paz de Dios, que sobrepasa todo entendimiento, guardará vuestros corazones y vuestros pensamientos en Cristo Jesús." },
    { verse: 13, text: "Todo lo puedo en Cristo que me fortalece." },
    { verse: 19, text: "Mi Dios, pues, suplirá todo lo que os falta conforme a sus riquezas en gloria en Cristo Jesús." }
  ]
};

// Paleta oficial de resaltadores de BibleVerse (idéntica a la app de Android)
const HIGHLIGHT_PALETTE = [
  { name: "Amarillo", hex: "#FACC15", label: "Luz y Sabiduría" },
  { name: "Verde", hex: "#22C55E", label: "Vida y Esperanza" },
  { name: "Azul", hex: "#38BDF8", label: "Paz y Promesa" },
  { name: "Rosa", hex: "#EC4899", label: "Amor Divino" },
  { name: "Naranja", hex: "#F97316", label: "Ánimo y Fortaleza" }
];
