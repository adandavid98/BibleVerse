/**
 * BibleVerse Web PWA - Motor Completo de Datos, Exégesis, Referencias Cruzadas, Audio e IA
 * Paridad 100% con el APK de Android (Offline SQLite JSONs, Perícopas, Palabras en Rojo, XREF bin, Gemini IA).
 */

const BIBLE_VERSIONS = [
  {
    code: "RVR1960",
    bollsSlug: "RV1960",
    name: "Reina-Valera 1960",
    shortName: "RVR 1960",
    tag: "Clásica",
    isOfflineAsset: true,
    desc: "Traducción clásica y solemne. La más extendida e histórica en el mundo hispanohablante."
  },
  {
    code: "NVI",
    bollsSlug: "NVI",
    name: "Nueva Versión Internacional",
    shortName: "NVI",
    tag: "Contemporánea",
    isOfflineAsset: true,
    desc: "Traducción fiel y contemporánea directamente de los textos originales hebreos, arameos y griegos."
  },
  {
    code: "NTV",
    bollsSlug: "NTV",
    name: "Nueva Traducción Viviente",
    shortName: "NTV",
    tag: "Dinámica",
    isOfflineAsset: false,
    desc: "Traducción de equivalencia dinámica con prosa cálida, clara y de fácil comprensión."
  },
  {
    code: "TLA",
    bollsSlug: "TLA",
    name: "Traducción en Lenguaje Actual",
    shortName: "TLA",
    tag: "Sencilla",
    isOfflineAsset: true,
    desc: "Lenguaje sencillo, directo y cotidiano, ideal para lectura familiar y devocional."
  },
  {
    code: "DHH",
    bollsSlug: "DHH",
    name: "Dios Habla Hoy",
    shortName: "DHH",
    tag: "Popular",
    isOfflineAsset: true,
    desc: "Traducción popular ecuménica con vocabulario claro y accesible."
  },
  {
    code: "NBLA",
    bollsSlug: "LBLA",
    name: "Nueva Biblia de las Américas",
    shortName: "NBLA",
    tag: "Precisa",
    isOfflineAsset: true,
    desc: "Traducción fiel y precisa producida en español latinoamericano contemporáneo claro y exacto."
  },
  {
    code: "LBLA",
    bollsSlug: "LBLA",
    name: "La Biblia de las Américas",
    shortName: "LBLA",
    tag: "Literal",
    isOfflineAsset: false,
    desc: "Traducción formal y literal rigurosa, excelente para exégesis y estudio bíblico minucioso."
  }
];

const BIBLE_BOOKS = [
  // Antiguo Testamento (1 - 39)
  { id: 1, name: "Génesis", abbr: "Gn", testament: "AT", testamentFull: "Antiguo Testamento", category: "Pentateuco", chapters: 50 },
  { id: 2, name: "Éxodo", abbr: "Éx", testament: "AT", testamentFull: "Antiguo Testamento", category: "Pentateuco", chapters: 40 },
  { id: 3, name: "Levítico", abbr: "Lv", testament: "AT", testamentFull: "Antiguo Testamento", category: "Pentateuco", chapters: 27 },
  { id: 4, name: "Números", abbr: "Nm", testament: "AT", testamentFull: "Antiguo Testamento", category: "Pentateuco", chapters: 36 },
  { id: 5, name: "Deuteronomio", abbr: "Dt", testament: "AT", testamentFull: "Antiguo Testamento", category: "Pentateuco", chapters: 34 },
  { id: 6, name: "Josué", abbr: "Jos", testament: "AT", testamentFull: "Antiguo Testamento", category: "Históricos", chapters: 24 },
  { id: 7, name: "Jueces", abbr: "Jue", testament: "AT", testamentFull: "Antiguo Testamento", category: "Históricos", chapters: 21 },
  { id: 8, name: "Rut", abbr: "Rt", testament: "AT", testamentFull: "Antiguo Testamento", category: "Históricos", chapters: 4 },
  { id: 9, name: "1 Samuel", abbr: "1 S", testament: "AT", testamentFull: "Antiguo Testamento", category: "Históricos", chapters: 31 },
  { id: 10, name: "2 Samuel", abbr: "2 S", testament: "AT", testamentFull: "Antiguo Testamento", category: "Históricos", chapters: 24 },
  { id: 11, name: "1 Reyes", abbr: "1 R", testament: "AT", testamentFull: "Antiguo Testamento", category: "Históricos", chapters: 22 },
  { id: 12, name: "2 Reyes", abbr: "2 R", testament: "AT", testamentFull: "Antiguo Testamento", category: "Históricos", chapters: 25 },
  { id: 13, name: "1 Crónicas", abbr: "1 Cr", testament: "AT", testamentFull: "Antiguo Testamento", category: "Históricos", chapters: 29 },
  { id: 14, name: "2 Crónicas", abbr: "2 Cr", testament: "AT", testamentFull: "Antiguo Testamento", category: "Históricos", chapters: 36 },
  { id: 15, name: "Esdras", abbr: "Esd", testament: "AT", testamentFull: "Antiguo Testamento", category: "Históricos", chapters: 10 },
  { id: 16, name: "Nehemías", abbr: "Neh", testament: "AT", testamentFull: "Antiguo Testamento", category: "Históricos", chapters: 13 },
  { id: 17, name: "Ester", abbr: "Est", testament: "AT", testamentFull: "Antiguo Testamento", category: "Históricos", chapters: 10 },
  { id: 18, name: "Job", abbr: "Job", testament: "AT", testamentFull: "Antiguo Testamento", category: "Poéticos", chapters: 42 },
  { id: 19, name: "Salmos", abbr: "Sal", testament: "AT", testamentFull: "Antiguo Testamento", category: "Poéticos", chapters: 150 },
  { id: 20, name: "Proverbios", abbr: "Pr", testament: "AT", testamentFull: "Antiguo Testamento", category: "Poéticos", chapters: 31 },
  { id: 21, name: "Eclesiastés", abbr: "Ec", testament: "AT", testamentFull: "Antiguo Testamento", category: "Poéticos", chapters: 12 },
  { id: 22, name: "Cantares", abbr: "Cnt", testament: "AT", testamentFull: "Antiguo Testamento", category: "Poéticos", chapters: 8 },
  { id: 23, name: "Isaías", abbr: "Is", testament: "AT", testamentFull: "Antiguo Testamento", category: "Profetas Mayores", chapters: 66 },
  { id: 24, name: "Jeremías", abbr: "Jer", testament: "AT", testamentFull: "Antiguo Testamento", category: "Profetas Mayores", chapters: 52 },
  { id: 25, name: "Lamentaciones", abbr: "Lm", testament: "AT", testamentFull: "Antiguo Testamento", category: "Profetas Mayores", chapters: 5 },
  { id: 26, name: "Ezequiel", abbr: "Ez", testament: "AT", testamentFull: "Antiguo Testamento", category: "Profetas Mayores", chapters: 48 },
  { id: 27, name: "Daniel", abbr: "Dn", testament: "AT", testamentFull: "Antiguo Testamento", category: "Profetas Mayores", chapters: 12 },
  { id: 28, name: "Oseas", abbr: "Os", testament: "AT", testamentFull: "Antiguo Testamento", category: "Profetas Menores", chapters: 14 },
  { id: 29, name: "Joel", abbr: "Jl", testament: "AT", testamentFull: "Antiguo Testamento", category: "Profetas Menores", chapters: 3 },
  { id: 30, name: "Amós", abbr: "Am", testament: "AT", testamentFull: "Antiguo Testamento", category: "Profetas Menores", chapters: 9 },
  { id: 31, name: "Abdías", abbr: "Abd", testament: "AT", testamentFull: "Antiguo Testamento", category: "Profetas Menores", chapters: 1 },
  { id: 32, name: "Jonás", abbr: "Jon", testament: "AT", testamentFull: "Antiguo Testamento", category: "Profetas Menores", chapters: 4 },
  { id: 33, name: "Miqueas", abbr: "Miq", testament: "AT", testamentFull: "Antiguo Testamento", category: "Profetas Menores", chapters: 7 },
  { id: 34, name: "Nahúm", abbr: "Nah", testament: "AT", testamentFull: "Antiguo Testamento", category: "Profetas Menores", chapters: 3 },
  { id: 35, name: "Habacuc", abbr: "Hab", testament: "AT", testamentFull: "Antiguo Testamento", category: "Profetas Menores", chapters: 3 },
  { id: 36, name: "Sofonías", abbr: "Sof", testament: "AT", testamentFull: "Antiguo Testamento", category: "Profetas Menores", chapters: 3 },
  { id: 37, name: "Hageo", abbr: "Hag", testament: "AT", testamentFull: "Antiguo Testamento", category: "Profetas Menores", chapters: 2 },
  { id: 38, name: "Zacarías", abbr: "Zac", testament: "AT", testamentFull: "Antiguo Testamento", category: "Profetas Menores", chapters: 14 },
  { id: 39, name: "Malaquías", abbr: "Mal", testament: "AT", testamentFull: "Antiguo Testamento", category: "Profetas Menores", chapters: 4 },

  // Nuevo Testamento (40 - 66)
  { id: 40, name: "Mateo", abbr: "Mt", testament: "NT", testamentFull: "Nuevo Testamento", category: "Evangelios", chapters: 28 },
  { id: 41, name: "Marcos", abbr: "Mc", testament: "NT", testamentFull: "Nuevo Testamento", category: "Evangelios", chapters: 16 },
  { id: 42, name: "Lucas", abbr: "Lc", testament: "NT", testamentFull: "Nuevo Testamento", category: "Evangelios", chapters: 24 },
  { id: 43, name: "Juan", abbr: "Jn", testament: "NT", testamentFull: "Nuevo Testamento", category: "Evangelios", chapters: 21 },
  { id: 44, name: "Hechos", abbr: "Hch", testament: "NT", testamentFull: "Nuevo Testamento", category: "Histórico", chapters: 28 },
  { id: 45, name: "Romanos", abbr: "Ro", testament: "NT", testamentFull: "Nuevo Testamento", category: "Epístolas Paulinas", chapters: 16 },
  { id: 46, name: "1 Corintios", abbr: "1 Co", testament: "NT", testamentFull: "Nuevo Testamento", category: "Epístolas Paulinas", chapters: 16 },
  { id: 47, name: "2 Corintios", abbr: "2 Co", testament: "NT", testamentFull: "Nuevo Testamento", category: "Epístolas Paulinas", chapters: 13 },
  { id: 48, name: "Gálatas", abbr: "Gál", testament: "NT", testamentFull: "Nuevo Testamento", category: "Epístolas Paulinas", chapters: 6 },
  { id: 49, name: "Efesios", abbr: "Ef", testament: "NT", testamentFull: "Nuevo Testamento", category: "Epístolas Paulinas", chapters: 6 },
  { id: 50, name: "Filipenses", abbr: "Fil", testament: "NT", testamentFull: "Nuevo Testamento", category: "Epístolas Paulinas", chapters: 4 },
  { id: 51, name: "Colosenses", abbr: "Col", testament: "NT", testamentFull: "Nuevo Testamento", category: "Epístolas Paulinas", chapters: 4 },
  { id: 52, name: "1 Tesalonicenses", abbr: "1 Ts", testament: "NT", testamentFull: "Nuevo Testamento", category: "Epístolas Paulinas", chapters: 5 },
  { id: 53, name: "2 Tesalonicenses", abbr: "2 Ts", testament: "NT", testamentFull: "Nuevo Testamento", category: "Epístolas Paulinas", chapters: 3 },
  { id: 54, name: "1 Timoteo", abbr: "1 Ti", testament: "NT", testamentFull: "Nuevo Testamento", category: "Epístolas Paulinas", chapters: 6 },
  { id: 55, name: "2 Timoteo", abbr: "2 Ti", testament: "NT", testamentFull: "Nuevo Testamento", category: "Epístolas Paulinas", chapters: 4 },
  { id: 56, name: "Tito", abbr: "Tit", testament: "NT", testamentFull: "Nuevo Testamento", category: "Epístolas Paulinas", chapters: 3 },
  { id: 57, name: "Filemón", abbr: "Flm", testament: "NT", testamentFull: "Nuevo Testamento", category: "Epístolas Paulinas", chapters: 1 },
  { id: 58, name: "Hebreos", abbr: "He", testament: "NT", testamentFull: "Nuevo Testamento", category: "Epístolas Generales", chapters: 13 },
  { id: 59, name: "Santiago", abbr: "Stg", testament: "NT", testamentFull: "Nuevo Testamento", category: "Epístolas Generales", chapters: 5 },
  { id: 60, name: "1 Pedro", abbr: "1 P", testament: "NT", testamentFull: "Nuevo Testamento", category: "Epístolas Generales", chapters: 5 },
  { id: 61, name: "2 Pedro", abbr: "2 P", testament: "NT", testamentFull: "Nuevo Testamento", category: "Epístolas Generales", chapters: 3 },
  { id: 62, name: "1 Juan", abbr: "1 Jn", testament: "NT", testamentFull: "Nuevo Testamento", category: "Epístolas Generales", chapters: 5 },
  { id: 63, name: "2 Juan", abbr: "2 Jn", testament: "NT", testamentFull: "Nuevo Testamento", category: "Epístolas Generales", chapters: 1 },
  { id: 64, name: "3 Juan", abbr: "3 Jn", testament: "NT", testamentFull: "Nuevo Testamento", category: "Epístolas Generales", chapters: 1 },
  { id: 65, name: "Judas", abbr: "Jds", testament: "NT", testamentFull: "Nuevo Testamento", category: "Epístolas Generales", chapters: 1 },
  { id: 66, name: "Apocalipsis", abbr: "Ap", testament: "NT", testamentFull: "Nuevo Testamento", category: "Profecía", chapters: 22 }
];

// Cachés en memoria para velocidad instantánea (<15ms)
const _booksCache = {};      // { VERSION: { bookId: { chapter: { verse: text } } } }
const _pericopesCache = {};  // { fileKey: { "book_chapter_verse": "Heading" } }
const _audioCatalogCache = {}; // { normVersion: { "book_chapter": "mp3_url" } }
let _xrefDataView = null;
let _xrefLoadingPromise = null;

function stripAccents(str) {
  return (str || "").normalize("NFD").replace(/[\u0300-\u036f]/g, "");
}

function getBookById(bookId) {
  return BIBLE_BOOKS.find(b => b.id === Number(bookId)) || BIBLE_BOOKS[0];
}

function findBookFlexible(input) {
  if (!input) return null;
  let clean = stripAccents(input).toLowerCase().trim()
    .replace(/^(el\s+libro\s+de\s+|libro\s+de\s+|carta\s+a\s+los\s+|epistola\s+a\s+los\s+|evangelio\s+segun\s+san\s+|evangelio\s+de\s+|san\s+)/, "")
    .replace(/^(1ra\s*de\s*|1ra\s+|1era\s+|primera\s+de\s*|1\s*de\s*)/, "1 ")
    .replace(/^(2da\s*de\s*|2da\s+|2nda\s+|segunda\s+de\s*|2\s*de\s*)/, "2 ")
    .replace(/^(3ra\s*de\s*|3ra\s+|3era\s+|tercera\s+de\s*|3\s*de\s*)/, "3 ")
    .trim();
  if (clean === "salmo") clean = "salmos";
  if (clean.startsWith("cantar")) clean = "cantares";
  if (clean.startsWith("apoc")) clean = "apocalipsis";

  let exact = BIBLE_BOOKS.find(b => stripAccents(b.name).toLowerCase() === clean || stripAccents(b.abbr).toLowerCase() === clean);
  if (exact) return exact;
  return BIBLE_BOOKS.find(b => stripAccents(b.name).toLowerCase().startsWith(clean) || clean.startsWith(stripAccents(b.name).toLowerCase()));
}

function parseBibleReference(input) {
  if (!input) return null;
  const clean = input.replace(/📖/g, "").replace(/[^\p{L}\p{N}\s:.,-–—]/gu, "").trim();
  const matchRange = clean.match(/^(.+?)\s+(\d+)\s*[:.,]\s*(\d+)(?:\s*[-–—]\s*(\d+))?$/);
  if (matchRange) {
    const book = findBookFlexible(matchRange[1]);
    if (!book) return null;
    const chapter = Math.min(Math.max(1, parseInt(matchRange[2], 10) || 1), book.chapters);
    const startVerse = Math.max(1, parseInt(matchRange[3], 10) || 1);
    const endVerse = matchRange[4] ? Math.max(startVerse, parseInt(matchRange[4], 10)) : startVerse;
    return { bookId: book.id, bookName: book.name, chapter, startVerse, endVerse };
  }
  const matchChap = clean.match(/^(.+?)\s+(\d+)$/);
  if (matchChap) {
    const book = findBookFlexible(matchChap[1]);
    if (!book) return null;
    const chapter = Math.min(Math.max(1, parseInt(matchChap[2], 10) || 1), book.chapters);
    return { bookId: book.id, bookName: book.name, chapter, startVerse: 1, endVerse: 1 };
  }
  return null;
}

function getCanonicalVerseCount(bookId, chapter) {
  const counts = window.BIBLE_CANONICAL_COUNTS && window.BIBLE_CANONICAL_COUNTS[String(bookId)];
  if (counts && counts[chapter - 1]) return counts[chapter - 1];
  return 30;
}

function isWordsOfJesus(bookId, chapter, verseNum) {
  const map = window.WORDS_OF_JESUS && window.WORDS_OF_JESUS[String(bookId)];
  if (!map) return false;
  const arr = map[String(chapter)];
  return Array.isArray(arr) && arr.includes(Number(verseNum));
}

// =========================================================================
// PERÍCOPAS (Títulos de Sección por Versión)
// =========================================================================
async function loadPericopesForVersion(version) {
  const norm = (version || "RVR1960").toUpperCase().trim();
  const fileMap = {
    "RVR1960": "pericopes_es.json",
    "NVI": "pericopes_nvi.json",
    "NTV": "pericopes_ntv.json",
    "LBLA": "pericopes_lbla.json",
    "NBLA": "pericopes_nbla.json",
    "DHH": "pericopes_dhh.json",
    "TLA": "pericopes_tla.json"
  };
  const fileName = fileMap[norm] || "pericopes_es.json";
  if (_pericopesCache[fileName]) return _pericopesCache[fileName];
  try {
    const resp = await fetch(`./assets/bible/${fileName}`);
    if (resp.ok) {
      const data = await resp.json();
      _pericopesCache[fileName] = data;
      return data;
    }
  } catch (e) {
    console.warn("No se pudo cargar perícopas:", fileName, e);
  }
  _pericopesCache[fileName] = {};
  return {};
}

async function getSectionHeading(bookId, chapter, verseNum, version) {
  const pericopes = await loadPericopesForVersion(version);
  const key = `${bookId}_${chapter}_${verseNum}`;
  if (pericopes[key]) return pericopes[key];
  if (version !== "RVR1960") {
    const fallback = await loadPericopesForVersion("RVR1960");
    return fallback[key] || null;
  }
  return null;
}

// =========================================================================
// MOTOR DE LECTURA BÍBLICA OFFLINE + ONLINE (66 Libros, 7 Versiones)
// =========================================================================
const OFFLINE_RVR1960_FIXES = {
  20: [
    ["1", "7", "El principio de la sabiduría es el temor de Jehová; Los insensatos desprecian la sabiduría y la enseñanza."],
    ["6", "19", "El testigo falso que habla mentiras, Y el que siembra discordia entre hermanos."],
    ["22", "16", "El que oprime al pobre para aumentar sus ganancias, O que da al rico, ciertamente se empobrecerá."],
    ["31", "9", "Abre tu boca, juzga con justicia, Y defiende la causa del pobre y del menesteroso."]
  ],
  24: [
    ["3", "25", "Yacemos en nuestra confusión, y nuestra afrenta nos cubre; porque pecamos contra Jehová nuestro Dios, nosotros y nuestros padres, desde nuestra juventud y hasta este día, y no hemos escuchado la voz de Jehová nuestro Dios."]
  ],
  26: [
    ["14", "23", "Y os consolarán cuando viereis su camino y sus hechos, y conoceréis que no sin causa hice todo lo que he hecho en ella, dice Jehová el Señor."]
  ],
  44: [
    ["1", "20", "Porque está escrito en el libro de los Salmos: Sea hecha desierta su habitación, Y no haya quien more en ella; y: Tome otro su oficio."],
    ["7", "43", "Antes bien llevasteis el tabernáculo de Moloc, y la estrella de vuestro dios Renfán, figuras que os hicisteis para adorarlas. Os transportaré, pues, más allá de Babilonia."]
  ],
  55: [
    ["2", "13", "Si fuéremos infieles, él permanece fiel; El no puede negarse a sí mismo."]
  ],
  60: [
    ["2", "6", "Por lo cual también contiene la Escritura: He aquí, pongo en Sion la principal piedra del ángulo, escogida, preciosa; Y el que creyere en él, no será avergonzado."]
  ]
};

function sanitizeOfflineBookData(bookData, version, bookId) {
  if (!bookData || typeof bookData !== "object") return bookData;
  const norm = (version || "").toUpperCase().trim();
  const idNum = Number(bookId);

  if (norm === "RVR1960" && OFFLINE_RVR1960_FIXES[idNum]) {
    for (const [ch, vs, fixed] of OFFLINE_RVR1960_FIXES[idNum]) {
      if (bookData[ch] && Object.prototype.hasOwnProperty.call(bookData[ch], vs)) {
        bookData[ch][vs] = fixed;
      }
    }
  }

  for (const ch of Object.keys(bookData)) {
    const chObj = bookData[ch];
    if (!chObj || typeof chObj !== "object") continue;
    for (const vs of Object.keys(chObj)) {
      const txt = chObj[vs];
      if (typeof txt === "string" && txt.includes("\u00b6")) {
        chObj[vs] = txt.replace(/\u00b6/g, "").replace(/ {2,}/g, " ").trim();
      }
    }
  }
  return bookData;
}

async function loadOfflineBook(version, bookId) {
  const norm = (version || "RVR1960").toUpperCase().trim();
  const assetVersion = ["RVR1960", "NVI", "NBLA", "DHH", "TLA"].includes(norm)
    ? norm
    : (norm === "LBLA" ? "NBLA" : (norm === "NTV" ? "NVI" : "RVR1960"));

  if (!_booksCache[assetVersion]) _booksCache[assetVersion] = {};
  if (_booksCache[assetVersion][bookId]) return _booksCache[assetVersion][bookId];

  const bookUrl = `./assets/bible/books/${assetVersion}/${bookId}.json`;
  if ("caches" in window) {
    try {
      const cached = await caches.match(bookUrl);
      if (cached && cached.ok) {
        const bookData = sanitizeOfflineBookData(await cached.json(), assetVersion, bookId);
        _booksCache[assetVersion][bookId] = bookData;
        return bookData;
      }
    } catch {
      // Continuar con fetch normal
    }
  }

  try {
    const resp = await fetch(bookUrl);
    if (resp.ok) {
      const bookData = sanitizeOfflineBookData(await resp.json(), assetVersion, bookId);
      _booksCache[assetVersion][bookId] = bookData;
      return bookData;
    }
  } catch (e) {
    console.warn(`Error cargando libro offline ${assetVersion}/${bookId}:`, e);
  }
  return null;
}

async function loadCachedDownloadedBook(version, bookId) {
  const norm = (version || "").toUpperCase().trim();
  if (!norm) return null;
  if (_booksCache[norm] && _booksCache[norm][bookId]) return _booksCache[norm][bookId];
  if ("caches" in window) {
    try {
      const cache = await caches.open("bibleverse-offline-bibles-v1");
      const match = await cache.match(`./assets/bible/books/${norm}/${bookId}.json`);
      if (match && match.ok) {
        const bookData = await match.json();
        if (!_booksCache[norm]) _booksCache[norm] = {};
        _booksCache[norm][bookId] = bookData;
        return bookData;
      }
    } catch {
      // Ignorar error de caché
    }
  }
  return null;
}

async function fetchChapterVerses(version = "RVR1960", bookId = 19, chapter = 23) {
  const norm = (version || "RVR1960").toUpperCase().trim();
  const pericopes = await loadPericopesForVersion(norm);
  const fallbackPericopes = norm !== "RVR1960" ? await loadPericopesForVersion("RVR1960") : pericopes;

  // 1. Para las 5 versiones incluidas en assets locales (RVR1960, NVI, NBLA, DHH, TLA): lectura 100% local e instantánea
  if (["RVR1960", "NVI", "NBLA", "DHH", "TLA"].includes(norm)) {
    const bookData = await loadOfflineBook(norm, bookId);
    const chObj = bookData && bookData[String(chapter)];
    if (chObj) {
      const verses = Object.keys(chObj)
        .map(k => parseInt(k, 10))
        .sort((a, b) => a - b)
        .map(vNum => {
          const pKey = `${bookId}_${chapter}_${vNum}`;
          return {
            verse: vNum,
            text: chObj[String(vNum)],
            sectionHeading: pericopes[pKey] || fallbackPericopes[pKey] || null,
            isRedLetter: isWordsOfJesus(bookId, chapter, vNum)
          };
        });
      return { bookId, chapter, version: norm, verses };
    }
  }

  // 2. Para NTV y LBLA: primero verifica si fue descargada offline en Cache API; luego intenta API de Bolls
  if (norm === "NTV" || norm === "LBLA") {
    const downloadedBook = await loadCachedDownloadedBook(norm, bookId);
    const chDownloaded = downloadedBook && downloadedBook[String(chapter)];
    if (chDownloaded) {
      const verses = Object.keys(chDownloaded)
        .map(k => parseInt(k, 10))
        .sort((a, b) => a - b)
        .map(vNum => {
          const pKey = `${bookId}_${chapter}_${vNum}`;
          return {
            verse: vNum,
            text: chDownloaded[String(vNum)],
            sectionHeading: pericopes[pKey] || fallbackPericopes[pKey] || null,
            isRedLetter: isWordsOfJesus(bookId, chapter, vNum)
          };
        });
      return { bookId, chapter, version: norm, verses };
    }
    try {
      const resp = await fetch(`https://bolls.life/get-chapter/${norm}/${bookId}/${chapter}/`);
      if (resp.ok) {
        const data = await resp.json();
        if (Array.isArray(data) && data.length > 1) {
          const verses = data.map((item, idx) => {
            const vNum = item.verse || (idx + 1);
            const pKey = `${bookId}_${chapter}_${vNum}`;
            const cleanText = (item.text || "")
              .replace(/<p[^>]*align\s*=\s*['"]?center['"]?[^>]*>.*?<\/p>/gis, " ")
              .replace(/<h[1-6][^>]*>.*?<\/h[1-6]>/gis, " ")
              .replace(/<br\s*\/?>/gi, " ")
              .replace(/<[^>]*>/g, "")
              .replace(/&nbsp;/g, " ")
              .replace(/\s+/g, " ")
              .trim();
            return {
              verse: vNum,
              text: cleanText,
              sectionHeading: pericopes[pKey] || fallbackPericopes[pKey] || null,
              isRedLetter: isWordsOfJesus(bookId, chapter, vNum)
            };
          });
          return { bookId, chapter, version: norm, verses };
        }
      }
    } catch (e) {
      console.warn(`Fallback offline activado para ${norm} ${bookId}:${chapter}`);
    }
  }

  // 3. Fallback seguro al motor offline local (NBLA para LBLA, NVI para NTV, o RVR1960)
  const fallbackBook = await loadOfflineBook(norm, bookId);
  const chFallback = fallbackBook && fallbackBook[String(chapter)];
  if (chFallback) {
    const verses = Object.keys(chFallback)
      .map(k => parseInt(k, 10))
      .sort((a, b) => a - b)
      .map(vNum => {
        const pKey = `${bookId}_${chapter}_${vNum}`;
        return {
          verse: vNum,
          text: chFallback[String(vNum)],
          sectionHeading: pericopes[pKey] || fallbackPericopes[pKey] || null,
          isRedLetter: isWordsOfJesus(bookId, chapter, vNum)
        };
      });
    return { bookId, chapter, version: norm, verses };
  }

  return { bookId, chapter, version: norm, verses: [] };
}

// =========================================================================
// BUSCADOR BÍBLICO COMPLETO EN LOS 66 LIBROS (SearchTab)
// =========================================================================
async function searchFullBible(query, options = {}) {
  const trimmed = (query || "").trim();
  if (!trimmed) return [];

  const version = (options.version || "RVR1960").toUpperCase();
  const scope = options.scope || "ALL"; // "ALL", "OT", "NT", "BOOK"
  const targetBookId = options.bookId ? Number(options.bookId) : null;
  const limit = options.limit || 100;

  // Si el usuario escribió una referencia directa (ej: "Juan 3:16" o "Salmos 23")
  const parsedRef = parseBibleReference(trimmed);
  const results = [];

  if (parsedRef) {
    const chData = await fetchChapterVerses(version, parsedRef.bookId, parsedRef.chapter);
    for (const v of chData.verses) {
      if (v.verse >= parsedRef.startVerse && v.verse <= parsedRef.endVerse) {
        results.push({
          bookId: parsedRef.bookId,
          bookName: parsedRef.bookName,
          chapter: parsedRef.chapter,
          verse: v.verse,
          text: v.text,
          version,
          reference: `${parsedRef.bookName} ${parsedRef.chapter}:${v.verse}`
        });
      }
    }
    if (results.length > 0) return results;
  }

  const cleanQuery = stripAccents(trimmed).toLowerCase();
  const queryWords = cleanQuery.split(/\s+/).filter(Boolean);

  let bookIds = [];
  if (scope === "BOOK" && targetBookId >= 1 && targetBookId <= 66) {
    bookIds = [targetBookId];
  } else if (scope === "OT") {
    bookIds = Array.from({ length: 39 }, (_, i) => i + 1);
  } else if (scope === "NT") {
    bookIds = Array.from({ length: 27 }, (_, i) => i + 40);
  } else {
    bookIds = Array.from({ length: 66 }, (_, i) => i + 1);
  }

  // Cargar libros en lotes paralelos rápidos
  const batchSize = 22;
  for (let i = 0; i < bookIds.length; i += batchSize) {
    const batch = bookIds.slice(i, i + batchSize);
    const loadedBooks = await Promise.all(
      batch.map(async id => {
        if (version === "NTV" || version === "LBLA") {
          return (await loadCachedDownloadedBook(version, id)) || (await loadOfflineBook(version, id));
        }
        return loadOfflineBook(version, id);
      })
    );

    for (let j = 0; j < batch.length; j++) {
      const bId = batch[j];
      const bObj = loadedBooks[j];
      if (!bObj) continue;
      const bMeta = getBookById(bId);

      const chapters = Object.keys(bObj);
      for (let cIdx = 0; cIdx < chapters.length; cIdx++) {
        const chStr = chapters[cIdx];
        const versesObj = bObj[chStr];
        const vNums = Object.keys(versesObj);
        for (let vIdx = 0; vIdx < vNums.length; vIdx++) {
          const vStr = vNums[vIdx];
          const rawText = versesObj[vStr];
          const normText = stripAccents(rawText).toLowerCase();

          // Coincidencia de frase exacta o todas las palabras clave
          const matches = normText.includes(cleanQuery) ||
            (queryWords.length > 1 && queryWords.every(w => normText.includes(w)));

          if (matches) {
            results.push({
              bookId: bId,
              bookName: bMeta.name,
              testament: bMeta.testamentFull,
              chapter: Number(chStr),
              verse: Number(vStr),
              text: rawText,
              version,
              reference: `${bMeta.name} ${chStr}:${vStr}`
            });
            if (results.length >= limit) return results;
          }
        }
      }
    }
  }

  return results;
}

// =========================================================================
// REFERENCIAS CRUZADAS BINARIAS (cross_references.bin + Paralelos)
// =========================================================================
async function ensureCrossReferencesLoaded() {
  if (_xrefDataView) return _xrefDataView;
  if (_xrefLoadingPromise) return _xrefLoadingPromise;

  _xrefLoadingPromise = (async () => {
    try {
      const resp = await fetch("./assets/bible/cross_references.bin");
      if (!resp.ok) return null;
      const buf = await resp.arrayBuffer();
      const dv = new DataView(buf);
      // Verify "XREF" magic header (0x58, 0x52, 0x45, 0x46)
      if (dv.getUint8(0) === 0x58 && dv.getUint8(1) === 0x52 && dv.getUint8(2) === 0x45 && dv.getUint8(3) === 0x46) {
        _xrefDataView = dv;
        return dv;
      }
    } catch (e) {
      console.warn("Error cargando cross_references.bin:", e);
    }
    return null;
  })();

  return _xrefLoadingPromise;
}

const CHAPTER_PARALLEL_REFERENCES = {
  "1_1": [
    { reference: "Juan 1:1-18", bookId: 43, chapter: 1, startVerse: 1, endVerse: 18, note: "Paralelo del Prólogo: La creación por el Verbo eterno." },
    { reference: "Colosenses 1:15-17", bookId: 51, chapter: 1, startVerse: 15, endVerse: 17, note: "Cristo, imagen del Dios invisible y Creador de todas las cosas." },
    { reference: "Hebreos 1:1-3", bookId: 58, chapter: 1, startVerse: 1, endVerse: 3, note: "Por quien asimismo hizo el universo." }
  ],
  "1_2": [
    { reference: "1 Corintios 15:45-47", bookId: 46, chapter: 15, startVerse: 45, endVerse: 47, note: "El primer hombre Adán alma viviente; el postrer Adán, espíritu vivificante." },
    { reference: "Mateo 19:4-6", bookId: 40, chapter: 19, startVerse: 4, endVerse: 6, note: "El Creador al principio, varón y hembra los hizo." }
  ],
  "2_20": [
    { reference: "Deuteronomio 5:1-22", bookId: 5, chapter: 5, startVerse: 1, endVerse: 22, note: "Paralelo canónico: Repetición solemne del Decálogo." },
    { reference: "Mateo 22:35-40", bookId: 40, chapter: 22, startVerse: 35, endVerse: 40, note: "Los dos grandes mandamientos que resumen toda la ley." }
  ],
  "11_8": [
    { reference: "2 Crónicas 6:1-42", bookId: 14, chapter: 6, startVerse: 1, endVerse: 42, note: "Paralelo histórico: La solemne oración de Salomón dedicando el Santo Templo." }
  ],
  "12_18": [
    { reference: "Isaías 36:1-22", bookId: 23, chapter: 36, startVerse: 1, endVerse: 22, note: "Paralelo profético: Desafío de Senaquerib contra Jerusalén y confianza en Dios." },
    { reference: "2 Crónicas 32:1-23", bookId: 14, chapter: 32, startVerse: 1, endVerse: 23, note: "Paralelo de Crónicas: Invasión de Senaquerib y liberación divina." }
  ],
  "12_19": [
    { reference: "Isaías 37:1-38", bookId: 23, chapter: 37, startVerse: 1, endVerse: 38, note: "Paralelo profético: La respuesta de Dios a Ezequías a través de Isaías." }
  ],
  "12_20": [
    { reference: "Isaías 38:1-22", bookId: 23, chapter: 38, startVerse: 1, endVerse: 22, note: "Paralelo profético: El cántico de gratitud de Ezequías tras ser sanado." }
  ],
  "14_22": [
    { reference: "2 Reyes 8:25-29", bookId: 12, chapter: 8, startVerse: 25, endVerse: 29, note: "Paralelo histórico: Reinado y caída de Ocozías de Judá." },
    { reference: "2 Reyes 11:1-3", bookId: 12, chapter: 11, startVerse: 1, endVerse: 3, note: "Paralelo histórico: Rebelión de Atalía y rescate milagroso de Joás." }
  ],
  "14_23": [
    { reference: "2 Reyes 11:4-21", bookId: 12, chapter: 11, startVerse: 4, endVerse: 21, note: "Paralelo histórico: El sacerdote Joiada unge a Joás y derroca a Atalía." }
  ],
  "14_24": [
    { reference: "2 Reyes 12:1-21", bookId: 12, chapter: 12, startVerse: 1, endVerse: 21, note: "Paralelo histórico: Reparación del Templo y apostasía de Joás." }
  ],
  "40_3": [
    { reference: "Marcos 1:1-11", bookId: 41, chapter: 1, startVerse: 1, endVerse: 11, note: "Paralelo sinóptico: Comienzo del evangelio y bautismo de Jesús." },
    { reference: "Lucas 3:1-22", bookId: 42, chapter: 3, startVerse: 1, endVerse: 22, note: "Paralelo sinóptico: Ministerio y predicación de Juan el Bautista." }
  ],
  "40_4": [
    { reference: "Marcos 1:12-13", bookId: 41, chapter: 1, startVerse: 12, endVerse: 13, note: "Paralelo sinóptico: Jesús tentado en el desierto." },
    { reference: "Lucas 4:1-13", bookId: 42, chapter: 4, startVerse: 1, endVerse: 13, note: "Paralelo sinóptico: Las tres tentaciones y victoria de Cristo." }
  ],
  "40_5": [
    { reference: "Lucas 6:20-49", bookId: 42, chapter: 6, startVerse: 20, endVerse: 49, note: "Paralelo evangélico: El Sermón de la llanura y las Bienaventuranzas del Reino." }
  ],
  "40_6": [
    { reference: "Lucas 11:1-4", bookId: 42, chapter: 11, startVerse: 1, endVerse: 4, note: "Paralelo evangélico: La oración del Señor enseñada a los discípulos." },
    { reference: "Lucas 12:22-34", bookId: 42, chapter: 12, startVerse: 22, endVerse: 34, note: "Paralelo evangélico: No os afanéis por vuestra vida." }
  ],
  "40_8": [
    { reference: "Marcos 4:35-41", bookId: 41, chapter: 4, startVerse: 35, endVerse: 41, note: "Paralelo sinóptico: Jesús calma la tempestad en el mar." },
    { reference: "Lucas 8:22-25", bookId: 42, chapter: 8, startVerse: 22, endVerse: 25, note: "Paralelo de Lucas: ¿Dónde está vuestra fe?" }
  ],
  "40_13": [
    { reference: "Marcos 4:1-34", bookId: 41, chapter: 4, startVerse: 1, endVerse: 34, note: "Paralelo evangélico: Parábolas del Sembrador y de la Semilla." },
    { reference: "Lucas 8:4-18", bookId: 42, chapter: 8, startVerse: 4, endVerse: 18, note: "Paralelo evangélico: El sembrador y la lámpara bajo el almud." }
  ],
  "40_14": [
    { reference: "Marcos 6:30-44", bookId: 41, chapter: 6, startVerse: 30, endVerse: 44, note: "Paralelo sinóptico: Milagro de los panes y peces." },
    { reference: "Lucas 9:10-17", bookId: 42, chapter: 9, startVerse: 10, endVerse: 17, note: "Paralelo de Lucas: Comieron todos y se saciaron." },
    { reference: "Juan 6:1-15", bookId: 43, chapter: 6, startVerse: 1, endVerse: 15, note: "Paralelo de Juan: El Pan de vida y la multiplicación." }
  ],
  "40_16": [
    { reference: "Marcos 8:27-38", bookId: 41, chapter: 8, startVerse: 27, endVerse: 38, note: "Paralelo sinóptico: Tú eres el Cristo; tomar la cruz y seguirle." },
    { reference: "Lucas 9:18-27", bookId: 42, chapter: 9, startVerse: 18, endVerse: 27, note: "Paralelo de Lucas: La confesión de Pedro y el discipulado." }
  ],
  "40_17": [
    { reference: "Marcos 9:2-13", bookId: 41, chapter: 9, startVerse: 2, endVerse: 13, note: "Paralelo sinóptico: La gloria de Jesús en el monte alto." },
    { reference: "Lucas 9:28-36", bookId: 42, chapter: 9, startVerse: 28, endVerse: 36, note: "Paralelo de Lucas: Moisés y Elías hablando con Jesús." }
  ],
  "40_21": [
    { reference: "Marcos 11:1-11", bookId: 41, chapter: 11, startVerse: 1, endVerse: 11, note: "Paralelo sinóptico: ¡Bendito el que viene en el nombre del Señor!" },
    { reference: "Lucas 19:28-40", bookId: 42, chapter: 19, startVerse: 28, endVerse: 40, note: "Paralelo de Lucas: Si éstos callan, las piedras clamarán." },
    { reference: "Juan 12:12-19", bookId: 43, chapter: 12, startVerse: 12, endVerse: 19, note: "Paralelo de Juan: He aquí tu Rey viene sentado sobre un pollino." }
  ],
  "40_24": [
    { reference: "Marcos 13:1-37", bookId: 41, chapter: 13, startVerse: 1, endVerse: 37, note: "Paralelo sinóptico: Profecía sobre el fin y la venida del Hijo del Hombre." },
    { reference: "Lucas 21:5-36", bookId: 42, chapter: 21, startVerse: 5, endVerse: 36, note: "Paralelo de Lucas: Velad, pues, en todo tiempo orando." }
  ],
  "40_26": [
    { reference: "Marcos 14:1-72", bookId: 41, chapter: 14, startVerse: 1, endVerse: 72, note: "Paralelo sinóptico: La Santa Cena, agonía en Getsemaní y negación de Pedro." },
    { reference: "Lucas 22:1-71", bookId: 42, chapter: 22, startVerse: 1, endVerse: 71, note: "Paralelo sinóptico: La Pascua, angustia de Jesús y traición de Judas." },
    { reference: "Juan 18:1-27", bookId: 43, chapter: 18, startVerse: 1, endVerse: 27, note: "Paralelo de Juan: El huerto y el prendimiento de Jesús." }
  ],
  "40_27": [
    { reference: "Marcos 15:1-47", bookId: 41, chapter: 15, startVerse: 1, endVerse: 47, note: "Paralelo sinóptico: El juicio ante Pilato, Gólgota y sepulcro." },
    { reference: "Lucas 23:1-56", bookId: 42, chapter: 23, startVerse: 1, endVerse: 56, note: "Paralelo sinóptico: Padre, en tus manos encomiendo mi espíritu." },
    { reference: "Juan 19:1-42", bookId: 43, chapter: 19, startVerse: 1, endVerse: 42, note: "Paralelo de Juan: Consumado es; el sacrificio redentor." }
  ],
  "40_28": [
    { reference: "Marcos 16:1-20", bookId: 41, chapter: 16, startVerse: 1, endVerse: 20, note: "Paralelo sinóptico: Ha resucitado, no está aquí; id por todo el mundo." },
    { reference: "Lucas 24:1-53", bookId: 42, chapter: 24, startVerse: 1, endVerse: 53, note: "Paralelo sinóptico: El camino a Emaús y ascensión triunfal." },
    { reference: "Juan 20:1-31", bookId: 43, chapter: 20, startVerse: 1, endVerse: 31, note: "Paralelo de Juan: Paz a vosotros; bienaventurados los que no vieron." }
  ],
  "41_1": [
    { reference: "Mateo 3:1-17", bookId: 40, chapter: 3, startVerse: 1, endVerse: 17, note: "Paralelo: Ministerio de Juan el Bautista y Bautismo de Jesús." },
    { reference: "Lucas 3:1-22", bookId: 42, chapter: 3, startVerse: 1, endVerse: 22, note: "Paralelo de Lucas: La voz que clama en el desierto." }
  ],
  "42_2": [
    { reference: "Mateo 1:18-25", bookId: 40, chapter: 1, startVerse: 18, endVerse: 25, note: "Paralelo: El nacimiento virginal de Jesús en cumplimiento de la profecía." },
    { reference: "Miqueas 5:2", bookId: 33, chapter: 5, startVerse: 2, endVerse: 2, note: "Profecía mesiánica: Belén de Efrata dará origen al Gobernador eterno." }
  ],
  "43_1": [
    { reference: "Génesis 1:1-3", bookId: 1, chapter: 1, startVerse: 1, endVerse: 3, note: "Paralelo de la Creación: En el principio la luz resplandeció sobre las tinieblas." },
    { reference: "Colosenses 1:15-17", bookId: 51, chapter: 1, startVerse: 15, endVerse: 17, note: "Cristo la imagen del Dios invisible, primogénito de toda creación." }
  ]
};

function hasChapterParallelReferences(bookId, chapter) {
  return Boolean(CHAPTER_PARALLEL_REFERENCES[`${bookId}_${chapter}`]?.length);
}

function getChapterParallelReferences(bookId, chapter) {
  return CHAPTER_PARALLEL_REFERENCES[`${bookId}_${chapter}`] || [];
}

async function getCrossReferences(bookId, chapter, verseNumber) {
  const dv = await ensureCrossReferencesLoaded();
  const exactRefs = [];
  const chapterRefs = [];

  if (dv) {
    // Header: 4 bytes "XREF" + 2 bytes version + 2 bytes numChapters + 4 bytes numRecords = 12 bytes
    const numChapters = dv.getUint16(6, false);
    const recordsOffset = 12 + numChapters * 8;
    let left = 0;
    let right = numChapters - 1;
    let foundStartIdx = -1;
    let foundCount = 0;
    const targetKey = ((bookId & 0xFF) << 8) | (chapter & 0xFF);

    while (left <= right) {
      const mid = (left + right) >>> 1;
      const entryPos = 12 + mid * 8;
      const b = dv.getUint8(entryPos);
      const c = dv.getUint8(entryPos + 1);
      const key = (b << 8) | c;
      if (key === targetKey) {
        foundStartIdx = dv.getInt32(entryPos + 2, false);
        foundCount = dv.getUint16(entryPos + 6, false);
        break;
      } else if (key < targetKey) {
        left = mid + 1;
      } else {
        right = mid - 1;
      }
    }

    if (foundStartIdx >= 0 && foundCount > 0) {
      const seenExact = new Set();
      const seenChap = new Set();

      for (let i = 0; i < foundCount; i++) {
        const recPos = recordsOffset + (foundStartIdx + i) * 5;
        const v = dv.getUint8(recPos);
        const tb = dv.getUint8(recPos + 1);
        const tc = dv.getUint8(recPos + 2);
        const tv = dv.getUint8(recPos + 3);
        const tev = dv.getUint8(recPos + 4);

        const targetBook = getBookById(tb);
        if (!targetBook) continue;

        const refKey = `${tb}_${tc}_${tv}_${tev}`;
        const label = tev > tv
          ? `${targetBook.name} ${tc}:${tv}-${tev}`
          : `${targetBook.name} ${tc}:${tv}`;

        const item = {
          bookId: tb,
          bookName: targetBook.name,
          chapter: tc,
          startVerse: tv,
          endVerse: tev,
          sourceVerse: v,
          reference: label
        };

        if (v === Number(verseNumber)) {
          if (!seenExact.has(refKey)) {
            seenExact.add(refKey);
            exactRefs.push(item);
          }
        } else if (Math.abs(v - Number(verseNumber)) <= 5 && chapterRefs.length < 12) {
          if (!seenChap.has(refKey) && !seenExact.has(refKey)) {
            seenChap.add(refKey);
            chapterRefs.push(item);
          }
        }
      }
    }
  }

  return { exactRefs, chapterRefs };
}

// =========================================================================
// AUDIO OFICIAL POR CAPÍTULO (audio_catalog_*.json + Web Speech TTS)
// =========================================================================
async function getOfficialChapterAudioUrl(version, bookId, chapter) {
  const norm = (version || "RVR1960").toLowerCase().replace(/[^a-z0-9]/g, "");
  const supported = ["rvr1960", "nvi", "ntv", "nbla", "lbla"];
  const targetVer = supported.includes(norm) ? norm : "rvr1960";

  if (!_audioCatalogCache[targetVer]) {
    try {
      const resp = await fetch(`./assets/audio/audio_catalog_${targetVer}.json`);
      if (resp.ok) {
        _audioCatalogCache[targetVer] = await resp.json();
      } else {
        _audioCatalogCache[targetVer] = {};
      }
    } catch (e) {
      _audioCatalogCache[targetVer] = {};
    }
  }
  const key = `${bookId}_${chapter}`;
  return _audioCatalogCache[targetVer][key] || null;
}

// =========================================================================
// MOTOR EXEGÉTICO LOCAL + INTELIGENCIA ARTIFICIAL GEMINI (Explicar, Contexto y Chat)
// =========================================================================
const GEMINI_INTEGRATED_KEY = "AQ.Ab8RN6LZvsGY" + "zdV9eGWT7wBLkER47" + "KM7Vh6I4GgtHW_OAJfOeQ";
const GEMINI_MODELS = [
  "gemini-flash-lite-latest",
  "gemini-3.1-flash-lite",
  "gemini-flash-latest",
  "gemini-3.8-flash",
  "gemini-2.5-flash-lite"
];

function getLocalScholarlyExegesis(bookName, chapter = 1, verse = "1", verseText = "") {
  const cleanBook = (bookName || "Salmos").trim();
  const profiles = window.BOOK_THEOLOGICAL_PROFILES || {};
  const profile = profiles[cleanBook] || {
    author: "Autor bíblico inspirado por el Espíritu Santo",
    dateOrPeriod: "Período canónico bíblico",
    centralTheme: "Revelación del pacto y los propósitos redentores de Dios para su pueblo.",
    audience: "El pueblo de Dios en su contexto histórico original",
    doctrinalPurpose: "Instruir en justicia, fortalecer la fe y proclamar la fidelidad de Dios."
  };

  // Buscar si el versículo está en los 365 versículos curados con contexto exacto
  const curatedList = window.INITIAL_VERSES_DATA || [];
  const refPrefix = `${cleanBook} ${chapter}:${verse}`;
  const exactCurated = curatedList.find(v =>
    v.reference === refPrefix ||
    (v.book === cleanBook && String(v.chapterVerse).startsWith(`${chapter}:${verse}`))
  );

  const cleanQuote = (verseText || "").replace(/^[«"]+|[»"]+$/g, "").trim();

  let part1 = `📜 CONTEXTO HISTÓRICO Y LITERARIO\nEscrito por ${profile.author} (${profile.dateOrPeriod}), dirigido a ${profile.audience}. Dentro del marco general del libro: ${profile.centralTheme}`;
  let part2 = `✝️ EXÉGESIS Y ANÁLISIS TEOLÓGICO\n`;
  if (exactCurated && exactCurated.context) {
    part2 += `${exactCurated.context} `;
  }
  if (cleanQuote) {
    part2 += `El pasaje declara: «${cleanQuote}». En la revelación progresiva de las Escrituras, esta verdad afirma la soberanía de Dios y revela su carácter inmutable, invitando al creyente a descansar con certeza en la suficiencia de su Palabra.`;
  } else {
    part2 += `Este pasaje revela fundamentos esenciales de la doctrina bíblica, proclamando la fidelidad pactual de Dios y la verdad revelada en las Sagradas Escrituras.`;
  }
  let part3 = `🕊️ APLICACIÓN DEVOCIONAL Y PRÁCTICA\n${profile.doctrinalPurpose} Nos exhorta hoy a vivir con fe firme, perseverancia y gratitud, aplicando la verdad de Dios a los desafíos cotidianos y confiando plenamente en sus promesas eternas.`;

  return `${part1}\n\n${part2}\n\n${part3}`;
}

async function cascadeTextReveal(fullText, onChunk) {
  const clean = (fullText || "").trim();
  if (!onChunk || !clean) return clean;
  const words = clean.split(/(\s+)/);
  let acc = "";
  const step = 4; // 4 tokens per frame (~18ms) para cascada fluida e inmediata
  for (let i = 0; i < words.length; i += step) {
    acc += words.slice(i, i + step).join("");
    onChunk(acc);
    if (i + step < words.length) {
      await new Promise(r => setTimeout(r, 18));
    }
  }
  return clean;
}

async function streamGeminiDirect(directKey, contents, generationConfig, onChunk) {
  if (!directKey) return null;
  for (const model of GEMINI_MODELS) {
    try {
      const url = `https://generativelanguage.googleapis.com/v1beta/models/${model}:streamGenerateContent?alt=sse&key=${directKey}`;
      const resp = await fetch(url, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ contents, generationConfig })
      });
      if (!resp.ok || !resp.body) continue;

      const reader = resp.body.getReader();
      const decoder = new TextDecoder("utf-8");
      let buffer = "";
      let accumulated = "";

      while (true) {
        const { done, value } = await reader.read();
        if (done) break;
        buffer += decoder.decode(value, { stream: true });
        const lines = buffer.split("\n");
        buffer = lines.pop() || "";

        for (const line of lines) {
          const trimmed = line.trim();
          if (!trimmed.startsWith("data:")) continue;
          const jsonStr = trimmed.slice(5).trim();
          if (!jsonStr || jsonStr === "[DONE]") continue;
          try {
            const parsed = JSON.parse(jsonStr);
            const partText = parsed?.candidates?.[0]?.content?.parts?.[0]?.text || "";
            if (partText) {
              accumulated += partText;
              if (onChunk) onChunk(accumulated);
            }
          } catch (_) {}
        }
      }

      if (accumulated.trim().length > 20) {
        return accumulated.trim();
      }
    } catch (e) {
      // Continúa al siguiente modelo o fallback
    }
  }
  return null;
}

async function generateVerseExegesisWithAI(bookName, chapter, verse, verseText, version = "RVR1960", onChunk = null) {
  const reference = `${bookName} ${chapter}:${verse}`;
  const prompt =
    `Eres un erudito bíblico y teólogo con profundo conocimiento histórico, doctrinal y exegético. ` +
    `Proporciona una síntesis contextual rigurosa, clara y edificante para el siguiente pasaje bíblico:\n\n` +
    `Cita Bíblica: ${reference} (${version})\n` +
    `Texto bíblico: "${verseText}"\n\n` +
    `Estructura tu respuesta en 3 secciones breves y claras:\n` +
    `📜 CONTEXTO HISTÓRICO Y LITERARIO (autor, época y destinatario original)\n` +
    `✝️ EXÉGESIS Y SIGNIFICADO TEOLÓGICO (enseñanza doctrinal y cristocéntrica)\n` +
    `🕊️ APLICACIÓN PRÁCTICA HOY (enseñanza devocional para la vida del creyente)`;

  // 1. Intentar streaming directo si hay clave integrada o personalizada (respuesta en cascada en tiempo real)
  const customKey = (typeof localStorage !== "undefined" && localStorage.getItem("bibleverse_custom_gemini_key"))?.trim();
  const directKey = customKey || (typeof GEMINI_INTEGRATED_KEY !== "undefined" ? GEMINI_INTEGRATED_KEY : "");

  if (directKey) {
    const streamed = await streamGeminiDirect(
      directKey,
      [{ parts: [{ text: prompt }] }],
      { temperature: 0.35, maxOutputTokens: 700 },
      onChunk
    );
    if (streamed) {
      return { text: streamed, source: "IA Gemini" };
    }
  }

  // 2. Intentar endpoint serverless de Vercel (/api/gemini) con cascada inmediata
  try {
    const vResp = await fetch("/api/gemini", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        contents: [{ parts: [{ text: prompt }] }],
        generationConfig: { temperature: 0.35, maxOutputTokens: 750 }
      })
    });
    if (vResp.ok) {
      const vJson = await vResp.json();
      if (vJson?.text && vJson.text.trim().length > 30) {
        const revealed = await cascadeTextReveal(vJson.text.trim(), onChunk);
        return { text: revealed, source: vJson.source || "IA Gemini" };
      }
    }
  } catch (e) {
    // Si no está disponible en localhost o no hay serverless, continuar
  }

  const localText = await cascadeTextReveal(getLocalScholarlyExegesis(bookName, chapter, verse, verseText), onChunk);
  return {
    text: localText,
    source: "Motor Exegético Bíblico"
  };
}

async function askBibleChatAssistant(chatHistory, userMessage, onChunk = null) {
  const systemPrompt =
    "Eres un Asistente Bíblico y Teológico erudito integrado en la aplicación BibleVerse. " +
    "Responde siempre en español con rigor bíblico, claridad pastoral, respeto interdenominacional y citando pasajes exactos (ej: Juan 3:16, Romanos 8:28, Salmos 23:1, Efesios 2:8-9).";

  const contents = [];
  const recent = (chatHistory || []).slice(-6);
  for (const msg of recent) {
    contents.push({
      role: msg.isUser ? "user" : "model",
      parts: [{ text: msg.text }]
    });
  }
  contents.push({
    role: "user",
    parts: [{ text: `${systemPrompt}\n\nConsulta del usuario: ${userMessage}` }]
  });

  // 1. Intentar streaming directo en cascada en tiempo real
  const customKey = (typeof localStorage !== "undefined" && localStorage.getItem("bibleverse_custom_gemini_key"))?.trim();
  const directKey = customKey || (typeof GEMINI_INTEGRATED_KEY !== "undefined" ? GEMINI_INTEGRATED_KEY : "");

  if (directKey) {
    const streamed = await streamGeminiDirect(
      directKey,
      contents,
      { temperature: 0.4, maxOutputTokens: 900 },
      onChunk
    );
    if (streamed) return streamed;
  }

  // 2. Intentar endpoint serverless de Vercel (/api/gemini) con cascada inmediata
  try {
    const vResp = await fetch("/api/gemini", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        contents,
        generationConfig: { temperature: 0.4, maxOutputTokens: 900 }
      })
    });
    if (vResp.ok) {
      const vJson = await vResp.json();
      if (vJson?.text && vJson.text.trim().length > 20) {
        return await cascadeTextReveal(vJson.text.trim(), onChunk);
      }
    }
  } catch (e) {
    // Fallback local inteligente si no hay conexión o límite de cuota
  }

  return await cascadeTextReveal(buildLocalTheologicalChatResponse(userMessage), onChunk);
}

function buildLocalTheologicalChatResponse(userMessage) {
  const q = stripAccents(userMessage).toLowerCase();
  const curated = window.INITIAL_VERSES_DATA || [];

  // Buscar versículos relevantes en los 365 versículos curados
  const keywords = q.split(/\s+/).filter(w => w.length > 3);
  const scored = curated.map(v => {
    const hay = stripAccents(`${v.reference} ${v.topic} ${v.text} ${v.context}`).toLowerCase();
    let score = 0;
    for (const kw of keywords) {
      if (hay.includes(kw)) score += 2;
      if (stripAccents(v.topic).toLowerCase().includes(kw)) score += 4;
    }
    return { v, score };
  }).filter(x => x.score > 0).sort((a, b) => b.score - a.score).slice(0, 3);

  if (q.includes("escatologia") || q.includes("milenio") || q.includes("apocalipsis") || q.includes("profecia")) {
    return "📖 **Escatología Bíblica y la Esperanza Bienaventurada**\n\n" +
      "Las Sagradas Escrituras enseñan que la historia camina hacia el regreso visible, glorioso y triunfal de nuestro Señor Jesucristo (**Tito 2:13**, **Hechos 1:11**, **Apocalipsis 1:7**).\n\n" +
      "• **La Victoria de Cristo:** En **1 Tesalonicenses 4:16-17** y **1 Corintios 15:51-54**, Pablo revela la resurrección de los muertos en Cristo y la transformación gloriosa de los creyentes.\n" +
      "• **El Reino y Juicio Final:** En **Apocalipsis 20:1-6** y **Apocalipsis 21:1-5**, Dios promete cielos nuevos y tierra nueva donde Él enjugará toda lágrima y ya no habrá muerte, ni llanto, ni dolor.\n" +
      "• **Llamado Práctico:** Jesús nos exhorta en **Mateo 24:42-44** y **2 Pedro 3:11-14** a velar en santidad, fidelidad y amor mientras aguardamos Su venida.";
  }

  if (q.includes("gracia") || q.includes("justificacion") || q.includes("fe") || q.includes("salvacion")) {
    return "✝️ **La Doctrina de la Gracia y la Justificación por la Fe**\n\n" +
      "El corazón del Evangelio proclama que la salvación es un don gratuito de Dios recibido únicamente por medio de la fe en Jesucristo:\n\n" +
      "• **Justificados por Gracia:** *«Porque por gracia sois salvos por medio de la fe; y esto no de vosotros, pues es don de Dios; no por obras, para que nadie se gloríe»* (**Efesios 2:8-9**, **Romanos 3:24-28**).\n" +
      "• **Paz con Dios:** Como declara **Romanos 5:1**, *«Justificados, pues, por la fe, tenemos paz para con Dios por medio de nuestro Señor Jesucristo»*.\n" +
      "• **Fe que Obra por el Amor:** La fe salvadora verdadera transforma el corazón y produce frutos de obediencia y buenas obras (**Santiago 2:17**, **Tito 2:11-12**).";
  }

  if (q.includes("espiritu santo") || q.includes("consolador") || q.includes("dones") || q.includes("fruto")) {
    return "🕊️ **La Persona y Obra del Espíritu Santo (Pneumatología)**\n\n" +
      "El Espíritu Santo es la tercera Persona de la Santa Trinidad, coigual y coeterno con el Padre y el Hijo:\n\n" +
      "• **El Consolador Prometido:** Jesús prometió en **Juan 14:16-17** y **Juan 16:13** que el Espíritu de verdad moraría en los creyentes y nos guiaría a toda la verdad.\n" +
      "• **Regeneración y Sello:** Él opera el nuevo nacimiento (**Juan 3:5-8**, **Tito 3:5**), nos sella para el día de la redención (**Efesios 1:13-14**) y da testimonio a nuestro espíritu de que somos hijos de Dios (**Romanos 8:15-16**).\n" +
      "• **Fruto y Poder:** Nos reviste de poder para ser testigos (**Hechos 1:8**) y produce Su carácter en nosotros: amor, gozo, paz, paciencia, benignidad, bondad, fe, mansedumbre y templanza (**Gálatas 5:22-23**).";
  }

  if (q.includes("pacto") || q.includes("pactos")) {
    return "📜 **Los Pactos Bíblicos en la Historia de la Redención**\n\n" +
      "Toda la Biblia se articula a través de los pactos soberanos de Dios que culminan en Cristo:\n\n" +
      "• **Pacto Abrahámico (**Génesis 12:1-3**, **Génesis 15:6**):** Promesa de bendición a todas las familias de la tierra mediante la Simiente.\n" +
      "• **Pacto Mosaico (**Éxodo 19:5-6**):** Revela la santidad de la Ley de Dios y actúa como ayo para llevarnos a Cristo (**Gálatas 3:24**).\n" +
      "• **Pacto Davídico (**2 Samuel 7:12-16**):** Promete un Rey eterno del linaje de David cuyo trono permanecerá para siempre (**Lucas 1:32-33**).\n" +
      "• **El Nuevo Pacto (**Jeremías 31:31-34**, **Lucas 22:20**, **Hebreos 8:6-12**):** Sellado con la sangre preciosa de Jesús para el perdón definitivo de los pecados y la ley escrita en el corazón.";
  }

  if (scored.length > 0) {
    let reply = "📖 **Luz de las Escrituras para tu consulta:**\n\n";
    for (const item of scored) {
      reply += `• **${item.v.reference}** (*${item.v.topic}*): «${item.v.text}»\n  _${item.v.context}_\n\n`;
    }
    reply += "Puedes tocar cualquiera de las citas resaltadas para abrir el capítulo completo en el Lector Bíblico.";
    return reply;
  }

  return "📖 **Reflexión Bíblica y Teológica**\n\n" +
    "La Palabra de Dios es viva y eficaz (**Hebreos 4:12**) y lámpara a nuestros pies (**Salmos 119:105**). " +
    "Ante toda inquietud del corazón, las Escrituras nos invitan a acercarnos confiadamente al trono de la gracia (**Hebreos 4:16**), " +
    "presentar nuestras peticiones en oración (**Filipenses 4:6-7**) y descansar en la promesa de que a los que aman a Dios, todas las cosas les ayudan a bien (**Romanos 8:28**).";
}
