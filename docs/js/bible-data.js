/**
 * BibleVerse Web PWA - Motor de Datos Bíblicos Completo
 * Incluye los 66 libros canónicos, catálogo de versiones, devocionales y API de pasajes.
 */

const BIBLE_VERSIONS = [
  {
    code: "RVR1960",
    bollsSlug: "RV1960",
    name: "Reina-Valera 1960",
    shortName: "RVR 1960",
    tag: "Clásica",
    desc: "Traducción clásica y solemne. La más extendida e histórica en el mundo hispanohablante."
  },
  {
    code: "NVI",
    bollsSlug: "NVI",
    name: "Nueva Versión Internacional",
    shortName: "NVI",
    tag: "Contemporánea",
    desc: "Traducción fiel y contemporánea directamente de los textos originales."
  },
  {
    code: "NTV",
    bollsSlug: "NTV",
    name: "Nueva Traducción Viviente",
    shortName: "NTV",
    tag: "Dinámica",
    desc: "Prosa cálida, clara y de fácil comprensión, ideal para lectura diaria."
  },
  {
    code: "DHH",
    bollsSlug: "DHH",
    name: "Dios Habla Hoy",
    shortName: "DHH",
    tag: "Popular",
    desc: "Traducción popular ecuménica con vocabulario claro y accesible."
  },
  {
    code: "TLA",
    bollsSlug: "PDT",
    name: "Traducción en Lenguaje Actual",
    shortName: "TLA",
    tag: "Sencilla",
    desc: "Lenguaje sencillo, directo y cotidiano, ideal para toda la familia."
  },
  {
    code: "LBLA",
    bollsSlug: "LBLA",
    name: "La Biblia de las Américas",
    shortName: "LBLA",
    tag: "Literal",
    desc: "Traducción con alta fidelidad literal y precisión académica."
  },
  {
    code: "NBLA",
    bollsSlug: "LBLA",
    name: "Nueva Biblia de las Américas",
    shortName: "NBLA",
    tag: "Moderna",
    desc: "Fidelidad formal con español latinoamericano actual."
  }
];

const BIBLE_BOOKS = [
  // Antiguo Testamento (1 - 39)
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

  // Nuevo Testamento (40 - 66)
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

const DEVOTIONAL_VERSES = [
  {
    id: 1,
    bookId: 50,
    book: "Filipenses",
    chapter: 4,
    verse: 13,
    text: "Todo lo puedo en Cristo que me fortalece.",
    theme: "Fortaleza",
    reflection: "Cuando sientas que tus fuerzas se agotan, recuerda que no dependes de tu propia energía sino de la gracia sustentadora de Dios."
  },
  {
    id: 2,
    bookId: 24,
    book: "Jeremías",
    chapter: 29,
    verse: 11,
    text: "Porque yo sé los pensamientos que tengo acerca de vosotros, dice Jehová, pensamientos de paz, y no de mal, para daros el fin que esperáis.",
    theme: "Esperanza",
    reflection: "El futuro no está en manos del azar. Dios tiene trazado un propósito eterno lleno de paz y bienaventuranza para tu vida."
  },
  {
    id: 3,
    bookId: 19,
    book: "Salmos",
    chapter: 23,
    verse: 1,
    text: "Jehová es mi pastor; nada me faltará.",
    theme: "Paz en la Ansiedad",
    reflection: "El buen Pastor cuida, sacia y guía a sus ovejas. En Su presencia encontramos descanso pleno y provisión sobrenatural."
  },
  {
    id: 4,
    bookId: 20,
    book: "Proverbios",
    chapter: 3,
    verse: 5,
    text: "Fíate de Jehová de todo tu corazón, y no te apoyes en tu propia prudencia.",
    theme: "Sabiduría",
    reflection: "Rendir nuestro entendimiento ante la dirección divina es el principio de una vida con propósito y pasos firmes."
  },
  {
    id: 5,
    bookId: 40,
    book: "Mateo",
    chapter: 6,
    verse: 33,
    text: "Mas buscad primeramente el reino de Dios y su justicia, y todas estas cosas os serán añadidas.",
    theme: "Propósito de Dios",
    reflection: "Cuando alineamos nuestras prioridades con el corazón de Dios, cada necesidad cotidiana queda bajo Su fiel cuidado."
  },
  {
    id: 6,
    bookId: 45,
    book: "Romanos",
    chapter: 8,
    verse: 28,
    text: "Y sabemos que a los que aman a Dios, todas las cosas les ayudan a bien, esto es, a los que conforme a su propósito son llamados.",
    theme: "Sanidad y Fe",
    reflection: "Aun en medio de las tormentas más oscuras, Dios obra soberanamente para tu crecimiento espiritual y Su gloria."
  },
  {
    id: 7,
    bookId: 19,
    book: "Salmos",
    chapter: 91,
    verse: 1,
    text: "El que habita al abrigo del Altísimo morará bajo la sombra del Omnipotente.",
    theme: "Paz en la Ansiedad",
    reflection: "Bajo las alas del Señor ningún temor nocturno puede robarte la paz. Su presencia es tu castillo inexpugnable."
  },
  {
    id: 8,
    bookId: 23,
    book: "Isaías",
    chapter: 40,
    verse: 31,
    text: "Pero los que esperan a Jehová tendrán nuevas fuerzas; levantarán alas como las águilas; correrán, y no se cansarán; caminarán, y no se fatigarán.",
    theme: "Fortaleza",
    reflection: "Esperar en Dios no es pasividad; es renovar el vigor espiritual para remontar vuelo por encima de las dificultades."
  }
];

// Servicio de Descarga y Carga Dinámica de Capítulos con Cache
const BibleService = {
  // Limpia etiquetas HTML raras que puedan venir de algunas traducciones
  cleanText(raw) {
    if (!raw) return "";
    return raw
      .replace(/<[^>]*>/g, "")
      .replace(/&nbsp;/g, " ")
      .replace(/&quot;/g, '"')
      .replace(/&amp;/g, '&')
      .trim();
  },

  // Obtiene un capítulo completo de la Biblia
  async getChapter(versionCode, bookId, chapterNum) {
    const version = BIBLE_VERSIONS.find(v => v.code === versionCode) || BIBLE_VERSIONS[0];
    const cacheKey = `bv_ch_${version.code}_${bookId}_${chapterNum}`;

    // 1. Revisar si está guardado en localStorage (Modo Offline)
    const cached = localStorage.getItem(cacheKey);
    if (cached) {
      try {
        return JSON.parse(cached);
      } catch (e) {
        console.warn("Error leyendo cache local", e);
      }
    }

    // 2. Si hay conexión, descargar de Bolls Life API
    const slug = version.bollsSlug || "RV1960";
    const url = `https://bolls.life/get-chapter/${slug}/${bookId}/${chapterNum}/`;

    try {
      const response = await fetch(url, { headers: { "Accept": "application/json" } });
      if (!response.ok) throw new Error("Error en red Bolls");
      const data = await response.json();

      const verses = data.map((item, index) => ({
        verse: item.verse || (index + 1),
        text: BibleService.cleanText(item.text)
      }));

      // Guardar en cache offline para la próxima vez
      try {
        localStorage.setItem(cacheKey, JSON.stringify(verses));
      } catch (quotaError) {
        console.warn("Storage lleno, continuando sin guardar:", quotaError);
      }

      return verses;
    } catch (err) {
      console.warn("Fallo descarga de capítulo, usando respaldo:", err);
      // Respaldo de emergencia si falla la red
      return BibleService.getFallbackChapter(bookId, chapterNum);
    }
  },

  // Obtiene un versículo en múltiples versiones para el comparador
  async compareVerse(bookId, chapterNum, verseNum) {
    const comparisonVersions = [
      { code: "RVR1960", name: "Reina-Valera 1960", slug: "RV1960" },
      { code: "NTV", name: "Nueva Traducción Viviente", slug: "NTV" },
      { code: "LBLA", name: "La Biblia de las Américas", slug: "LBLA" },
      { code: "PDT", name: "Palabra de Dios para Todos (TLA)", slug: "PDT" }
    ];

    const results = await Promise.all(
      comparisonVersions.map(async (v) => {
        try {
          const chVerses = await BibleService.getChapter(v.code, bookId, chapterNum);
          const found = chVerses.find(item => item.verse === verseNum);
          return {
            versionCode: v.code,
            versionName: v.name,
            text: found ? found.text : "Texto no disponible en esta versión."
          };
        } catch (e) {
          return {
            versionCode: v.code,
            versionName: v.name,
            text: "No se pudo cargar esta versión."
          };
        }
      })
    );

    return results;
  },

  getFallbackChapter(bookId, chapterNum) {
    if (bookId === 19 && chapterNum === 23) {
      return [
        { verse: 1, text: "Jehová es mi pastor; nada me faltará." },
        { verse: 2, text: "En lugares de delicados pastos me hará descansar; junto a aguas de reposo me pastoreará." },
        { verse: 3, text: "Confortará mi alma; me guiará por sendas de justicia por amor de su nombre." },
        { verse: 4, text: "Aunque ande en valle de sombra de muerte, no temeré mal alguno, porque tú estarás conmigo; tu vara y tu cayado me infundirán aliento." },
        { verse: 5, text: "Aderezas mesa delante de mí en presencia de mis angustiadores; unges mi cabeza con aceite; mi copa está rebosando." },
        { verse: 6, text: "Ciertamente el bien y la misericordia me seguirán todos los días de mi vida, y en la casa de Jehová moraré por largos días." }
      ];
    }
    return [
      { verse: 1, text: "En el principio creó Dios los cielos y la tierra." },
      { verse: 2, text: "Y la tierra estaba desordenada y vacía, y las tinieblas estaban sobre la faz del abismo." },
      { verse: 3, text: "Y dijo Dios: Sea la luz; y fue la luz." }
    ];
  }
};
