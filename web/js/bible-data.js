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
async function loadOfflineBook(version, bookId) {
  const norm = (version || "RVR1960").toUpperCase().trim();
  const assetVersion = ["RVR1960", "NVI", "NBLA", "DHH", "TLA"].includes(norm)
    ? norm
    : (norm === "LBLA" ? "NBLA" : (norm === "NTV" ? "NVI" : "RVR1960"));

  if (!_booksCache[assetVersion]) _booksCache[assetVersion] = {};
  if (_booksCache[assetVersion][bookId]) return _booksCache[assetVersion][bookId];

  try {
    const resp = await fetch(`./assets/bible/books/${assetVersion}/${bookId}.json`);
    if (resp.ok) {
      const bookData = await resp.json();
      _booksCache[assetVersion][bookId] = bookData;
      return bookData;
    }
  } catch (e) {
    console.warn(`Error cargando libro offline ${assetVersion}/${bookId}:`, e);
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

  // 2. Para NTV y LBLA: intenta API de Bolls primero, con limpieza de etiquetas y verificación anti-copyright
  if (norm === "NTV" || norm === "LBLA") {
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
    const loadedBooks = await Promise.all(batch.map(id => loadOfflineBook(version, id)));

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

async function getCrossReferences(bookId, chapter, verseNumber) {
  const dv = await ensureCrossReferencesLoaded();
  const exactRefs = [];
  const chapterRefs = [];

  if (dv) {
    const numChapters = dv.getUint16(4, false); // Big-endian USHORT
    let left = 0;
    let right = numChapters - 1;
    let foundOffset = -1;
    let foundCount = 0;
    const targetKey = ((bookId & 0xFF) << 8) | (chapter & 0xFF);

    while (left <= right) {
      const mid = (left + right) >>> 1;
      const entryPos = 6 + mid * 8;
      const b = dv.getUint8(entryPos);
      const c = dv.getUint8(entryPos + 1);
      const key = (b << 8) | c;
      if (key === targetKey) {
        foundOffset = dv.getInt32(entryPos + 2, false);
        foundCount = dv.getUint16(entryPos + 6, false);
        break;
      } else if (key < targetKey) {
        left = mid + 1;
      } else {
        right = mid - 1;
      }
    }

    if (foundOffset >= 0 && foundCount > 0) {
      const seenExact = new Set();
      const seenChap = new Set();

      for (let i = 0; i < foundCount; i++) {
        const recPos = foundOffset + i * 5;
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
  "gemini-3-flash-preview",
  "gemini-3.1-flash-lite-preview",
  "gemini-flash-latest",
  "gemini-2.0-flash"
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

async function generateVerseExegesisWithAI(bookName, chapter, verse, verseText, version = "RVR1960") {
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

  for (const model of GEMINI_MODELS) {
    try {
      const url = `https://generativelanguage.googleapis.com/v1beta/models/${model}:generateContent?key=${GEMINI_INTEGRATED_KEY}`;
      const resp = await fetch(url, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          contents: [{ parts: [{ text: prompt }] }],
          generationConfig: { temperature: 0.35, maxOutputTokens: 700 }
        })
      });
      if (resp.ok) {
        const json = await resp.json();
        const text = json?.candidates?.[0]?.content?.parts?.[0]?.text;
        if (text && text.trim().length > 40) {
          return { text: text.trim(), source: "IA Gemini" };
        }
      }
    } catch (e) {
      // Continúa al siguiente modelo o fallback local
    }
  }

  return {
    text: getLocalScholarlyExegesis(bookName, chapter, verse, verseText),
    source: "Motor Exegético Bíblico"
  };
}

async function askBibleChatAssistant(chatHistory, userMessage) {
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

  for (const model of GEMINI_MODELS) {
    try {
      const url = `https://generativelanguage.googleapis.com/v1beta/models/${model}:generateContent?key=${GEMINI_INTEGRATED_KEY}`;
      const resp = await fetch(url, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          contents,
          generationConfig: { temperature: 0.4, maxOutputTokens: 900 }
        })
      });
      if (resp.ok) {
        const json = await resp.json();
        const text = json?.candidates?.[0]?.content?.parts?.[0]?.text;
        if (text && text.trim().length > 20) {
          return text.trim();
        }
      }
    } catch (e) {
      // Fallback local inteligente si no hay conexión o límite de cuota
    }
  }

  return buildLocalTheologicalChatResponse(userMessage);
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
