package com.example.data.bible

/**
 * Catálogo del Plan de Historias y Sucesos Bíblicos (749 Historias).
 * Índice maestro canónico completo de Génesis a Apocalipsis con búsqueda instantánea y filtrado por título.
 */
object BibleStoriesPlanCatalog {

    val storiesPlan: List<ReadingPlanDay> by lazy {
        part1() + 
        part2() + 
        part3() + 
        part4() + 
        part5() + 
        part6() + 
        part7() + 
        part8() + 
        part9() + 
        part10()
    }

    private fun part1(): List<ReadingPlanDay> = listOf(
        ReadingPlanDay(
            dayNumber = 1,
            title = "La creación de los cielos y la tierra",
            passagesSummary = "Génesis 1:1–2:3",
            primaryBookId = 1,
            primaryChapter = 1,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(1, "Génesis", 1), PlanPassageSegment(1, "Génesis", 2))
        ),
        ReadingPlanDay(
            dayNumber = 2,
            title = "La creación de Adán",
            passagesSummary = "Génesis 2:4-7",
            primaryBookId = 1,
            primaryChapter = 2,
            primaryVerse = 4,
            passages = listOf(PlanPassageSegment(1, "Génesis", 2))
        ),
        ReadingPlanDay(
            dayNumber = 3,
            title = "Adán en el huerto de Edén",
            passagesSummary = "Génesis 2:8-17",
            primaryBookId = 1,
            primaryChapter = 2,
            primaryVerse = 8,
            passages = listOf(PlanPassageSegment(1, "Génesis", 2))
        ),
        ReadingPlanDay(
            dayNumber = 4,
            title = "La creación de Eva",
            passagesSummary = "Génesis 2:18-25",
            primaryBookId = 1,
            primaryChapter = 2,
            primaryVerse = 18,
            passages = listOf(PlanPassageSegment(1, "Génesis", 2))
        ),
        ReadingPlanDay(
            dayNumber = 5,
            title = "La caída del hombre",
            passagesSummary = "Génesis 3:1-24",
            primaryBookId = 1,
            primaryChapter = 3,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(1, "Génesis", 3))
        ),
        ReadingPlanDay(
            dayNumber = 6,
            title = "Caín y Abel",
            passagesSummary = "Génesis 4:1-8",
            primaryBookId = 1,
            primaryChapter = 4,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(1, "Génesis", 4))
        ),
        ReadingPlanDay(
            dayNumber = 7,
            title = "Dios confronta a Caín",
            passagesSummary = "Génesis 4:9-16",
            primaryBookId = 1,
            primaryChapter = 4,
            primaryVerse = 9,
            passages = listOf(PlanPassageSegment(1, "Génesis", 4))
        ),
        ReadingPlanDay(
            dayNumber = 8,
            title = "Los descendientes de Caín",
            passagesSummary = "Génesis 4:17-24",
            primaryBookId = 1,
            primaryChapter = 4,
            primaryVerse = 17,
            passages = listOf(PlanPassageSegment(1, "Génesis", 4))
        ),
        ReadingPlanDay(
            dayNumber = 9,
            title = "Set y la descendencia de Adán",
            passagesSummary = "Génesis 4:25–5:32",
            primaryBookId = 1,
            primaryChapter = 4,
            primaryVerse = 25,
            passages = listOf(PlanPassageSegment(1, "Génesis", 4), PlanPassageSegment(1, "Génesis", 5))
        ),
        ReadingPlanDay(
            dayNumber = 10,
            title = "La corrupción de la humanidad",
            passagesSummary = "Génesis 6:1-8",
            primaryBookId = 1,
            primaryChapter = 6,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(1, "Génesis", 6))
        ),
        ReadingPlanDay(
            dayNumber = 11,
            title = "Noé halla gracia ante Dios",
            passagesSummary = "Génesis 6:9-12",
            primaryBookId = 1,
            primaryChapter = 6,
            primaryVerse = 9,
            passages = listOf(PlanPassageSegment(1, "Génesis", 6))
        ),
        ReadingPlanDay(
            dayNumber = 12,
            title = "El mandato de construir el arca",
            passagesSummary = "Génesis 6:13-22",
            primaryBookId = 1,
            primaryChapter = 6,
            primaryVerse = 13,
            passages = listOf(PlanPassageSegment(1, "Génesis", 6))
        ),
        ReadingPlanDay(
            dayNumber = 13,
            title = "El diluvio",
            passagesSummary = "Génesis 7:1-24",
            primaryBookId = 1,
            primaryChapter = 7,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(1, "Génesis", 7))
        ),
        ReadingPlanDay(
            dayNumber = 14,
            title = "Las aguas retroceden",
            passagesSummary = "Génesis 8:1-14",
            primaryBookId = 1,
            primaryChapter = 8,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(1, "Génesis", 8))
        ),
        ReadingPlanDay(
            dayNumber = 15,
            title = "Noé sale del arca",
            passagesSummary = "Génesis 8:15-22",
            primaryBookId = 1,
            primaryChapter = 8,
            primaryVerse = 15,
            passages = listOf(PlanPassageSegment(1, "Génesis", 8))
        ),
        ReadingPlanDay(
            dayNumber = 16,
            title = "El pacto de Dios con Noé",
            passagesSummary = "Génesis 9:1-17",
            primaryBookId = 1,
            primaryChapter = 9,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(1, "Génesis", 9))
        ),
        ReadingPlanDay(
            dayNumber = 17,
            title = "Noé y sus hijos después del diluvio",
            passagesSummary = "Génesis 9:18-29",
            primaryBookId = 1,
            primaryChapter = 9,
            primaryVerse = 18,
            passages = listOf(PlanPassageSegment(1, "Génesis", 9))
        ),
        ReadingPlanDay(
            dayNumber = 18,
            title = "La torre de Babel",
            passagesSummary = "Génesis 11:1-9",
            primaryBookId = 1,
            primaryChapter = 11,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(1, "Génesis", 11))
        ),
        ReadingPlanDay(
            dayNumber = 19,
            title = "Las generaciones desde Sem hasta Abram",
            passagesSummary = "Génesis 11:10-32",
            primaryBookId = 1,
            primaryChapter = 11,
            primaryVerse = 10,
            passages = listOf(PlanPassageSegment(1, "Génesis", 11))
        ),
        ReadingPlanDay(
            dayNumber = 20,
            title = "El llamamiento de Abram",
            passagesSummary = "Génesis 12:1-9",
            primaryBookId = 1,
            primaryChapter = 12,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(1, "Génesis", 12))
        ),
        ReadingPlanDay(
            dayNumber = 21,
            title = "Abram y Sarai en Egipto",
            passagesSummary = "Génesis 12:10-20",
            primaryBookId = 1,
            primaryChapter = 12,
            primaryVerse = 10,
            passages = listOf(PlanPassageSegment(1, "Génesis", 12))
        ),
        ReadingPlanDay(
            dayNumber = 22,
            title = "Abram y Lot se separan",
            passagesSummary = "Génesis 13:1-18",
            primaryBookId = 1,
            primaryChapter = 13,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(1, "Génesis", 13))
        ),
        ReadingPlanDay(
            dayNumber = 23,
            title = "Abram rescata a Lot",
            passagesSummary = "Génesis 14:1-24",
            primaryBookId = 1,
            primaryChapter = 14,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(1, "Génesis", 14))
        ),
        ReadingPlanDay(
            dayNumber = 24,
            title = "Abram y Melquisedec",
            passagesSummary = "Génesis 14:17-24",
            primaryBookId = 1,
            primaryChapter = 14,
            primaryVerse = 17,
            passages = listOf(PlanPassageSegment(1, "Génesis", 14))
        ),
        ReadingPlanDay(
            dayNumber = 25,
            title = "El pacto de Dios con Abram",
            passagesSummary = "Génesis 15:1-21",
            primaryBookId = 1,
            primaryChapter = 15,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(1, "Génesis", 15))
        ),
        ReadingPlanDay(
            dayNumber = 26,
            title = "Agar e Ismael",
            passagesSummary = "Génesis 16:1-16",
            primaryBookId = 1,
            primaryChapter = 16,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(1, "Génesis", 16))
        ),
        ReadingPlanDay(
            dayNumber = 27,
            title = "Dios cambia el nombre de Abram a Abraham",
            passagesSummary = "Génesis 17:1-8",
            primaryBookId = 1,
            primaryChapter = 17,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(1, "Génesis", 17))
        ),
        ReadingPlanDay(
            dayNumber = 28,
            title = "El pacto de la circuncisión",
            passagesSummary = "Génesis 17:9-27",
            primaryBookId = 1,
            primaryChapter = 17,
            primaryVerse = 9,
            passages = listOf(PlanPassageSegment(1, "Génesis", 17))
        ),
        ReadingPlanDay(
            dayNumber = 29,
            title = "Abraham recibe a los tres visitantes",
            passagesSummary = "Génesis 18:1-15",
            primaryBookId = 1,
            primaryChapter = 18,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(1, "Génesis", 18))
        ),
        ReadingPlanDay(
            dayNumber = 30,
            title = "Abraham intercede por Sodoma",
            passagesSummary = "Génesis 18:16-33",
            primaryBookId = 1,
            primaryChapter = 18,
            primaryVerse = 16,
            passages = listOf(PlanPassageSegment(1, "Génesis", 18))
        ),
        ReadingPlanDay(
            dayNumber = 31,
            title = "Los ángeles visitan Sodoma",
            passagesSummary = "Génesis 19:1-11",
            primaryBookId = 1,
            primaryChapter = 19,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(1, "Génesis", 19))
        ),
        ReadingPlanDay(
            dayNumber = 32,
            title = "Sodoma y Gomorra son destruidas",
            passagesSummary = "Génesis 19:12-29",
            primaryBookId = 1,
            primaryChapter = 19,
            primaryVerse = 12,
            passages = listOf(PlanPassageSegment(1, "Génesis", 19))
        ),
        ReadingPlanDay(
            dayNumber = 33,
            title = "Lot y sus hijas",
            passagesSummary = "Génesis 19:30-38",
            primaryBookId = 1,
            primaryChapter = 19,
            primaryVerse = 30,
            passages = listOf(PlanPassageSegment(1, "Génesis", 19))
        ),
        ReadingPlanDay(
            dayNumber = 34,
            title = "Abraham y Abimelec",
            passagesSummary = "Génesis 20:1-18",
            primaryBookId = 1,
            primaryChapter = 20,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(1, "Génesis", 20))
        ),
        ReadingPlanDay(
            dayNumber = 35,
            title = "Nace Isaac",
            passagesSummary = "Génesis 21:1-7",
            primaryBookId = 1,
            primaryChapter = 21,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(1, "Génesis", 21))
        ),
        ReadingPlanDay(
            dayNumber = 36,
            title = "Agar e Ismael son enviados al desierto",
            passagesSummary = "Génesis 21:8-21",
            primaryBookId = 1,
            primaryChapter = 21,
            primaryVerse = 8,
            passages = listOf(PlanPassageSegment(1, "Génesis", 21))
        ),
        ReadingPlanDay(
            dayNumber = 37,
            title = "Abraham hace pacto con Abimelec",
            passagesSummary = "Génesis 21:22-34",
            primaryBookId = 1,
            primaryChapter = 21,
            primaryVerse = 22,
            passages = listOf(PlanPassageSegment(1, "Génesis", 21))
        ),
        ReadingPlanDay(
            dayNumber = 38,
            title = "Abraham es probado con Isaac",
            passagesSummary = "Génesis 22:1-19",
            primaryBookId = 1,
            primaryChapter = 22,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(1, "Génesis", 22))
        ),
        ReadingPlanDay(
            dayNumber = 39,
            title = "Rebeca es presentada en la genealogía de Nacor",
            passagesSummary = "Génesis 22:20-24",
            primaryBookId = 1,
            primaryChapter = 22,
            primaryVerse = 20,
            passages = listOf(PlanPassageSegment(1, "Génesis", 22))
        ),
        ReadingPlanDay(
            dayNumber = 40,
            title = "Muerte y sepultura de Sara",
            passagesSummary = "Génesis 23:1-20",
            primaryBookId = 1,
            primaryChapter = 23,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(1, "Génesis", 23))
        ),
        ReadingPlanDay(
            dayNumber = 41,
            title = "Abraham busca esposa para Isaac",
            passagesSummary = "Génesis 24:1-67",
            primaryBookId = 1,
            primaryChapter = 24,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(1, "Génesis", 24))
        ),
        ReadingPlanDay(
            dayNumber = 42,
            title = "Abraham muere",
            passagesSummary = "Génesis 25:1-11",
            primaryBookId = 1,
            primaryChapter = 25,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(1, "Génesis", 25))
        ),
        ReadingPlanDay(
            dayNumber = 43,
            title = "Nacen Esaú y Jacob",
            passagesSummary = "Génesis 25:19-26",
            primaryBookId = 1,
            primaryChapter = 25,
            primaryVerse = 19,
            passages = listOf(PlanPassageSegment(1, "Génesis", 25))
        ),
        ReadingPlanDay(
            dayNumber = 44,
            title = "Esaú vende su primogenitura",
            passagesSummary = "Génesis 25:27-34",
            primaryBookId = 1,
            primaryChapter = 25,
            primaryVerse = 27,
            passages = listOf(PlanPassageSegment(1, "Génesis", 25))
        ),
        ReadingPlanDay(
            dayNumber = 45,
            title = "Isaac y Abimelec",
            passagesSummary = "Génesis 26:1-35",
            primaryBookId = 1,
            primaryChapter = 26,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(1, "Génesis", 26))
        ),
        ReadingPlanDay(
            dayNumber = 46,
            title = "Jacob recibe la bendición de Isaac",
            passagesSummary = "Génesis 27:1-46",
            primaryBookId = 1,
            primaryChapter = 27,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(1, "Génesis", 27))
        ),
        ReadingPlanDay(
            dayNumber = 47,
            title = "Jacob huye de Esaú",
            passagesSummary = "Génesis 28:1-9",
            primaryBookId = 1,
            primaryChapter = 28,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(1, "Génesis", 28))
        ),
        ReadingPlanDay(
            dayNumber = 48,
            title = "Jacob sueña con la escalera al cielo",
            passagesSummary = "Génesis 28:10-22",
            primaryBookId = 1,
            primaryChapter = 28,
            primaryVerse = 10,
            passages = listOf(PlanPassageSegment(1, "Génesis", 28))
        ),
        ReadingPlanDay(
            dayNumber = 49,
            title = "Jacob llega a Harán",
            passagesSummary = "Génesis 29:1-14",
            primaryBookId = 1,
            primaryChapter = 29,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(1, "Génesis", 29))
        ),
        ReadingPlanDay(
            dayNumber = 50,
            title = "Jacob trabaja por Raquel y Lea",
            passagesSummary = "Génesis 29:15-30",
            primaryBookId = 1,
            primaryChapter = 29,
            primaryVerse = 15,
            passages = listOf(PlanPassageSegment(1, "Génesis", 29))
        ),
        ReadingPlanDay(
            dayNumber = 51,
            title = "Nacen los hijos de Jacob",
            passagesSummary = "Génesis 29:31–30:24",
            primaryBookId = 1,
            primaryChapter = 29,
            primaryVerse = 31,
            passages = listOf(PlanPassageSegment(1, "Génesis", 29), PlanPassageSegment(1, "Génesis", 30))
        ),
        ReadingPlanDay(
            dayNumber = 52,
            title = "Jacob prospera con Labán",
            passagesSummary = "Génesis 30:25-43",
            primaryBookId = 1,
            primaryChapter = 30,
            primaryVerse = 25,
            passages = listOf(PlanPassageSegment(1, "Génesis", 30))
        ),
        ReadingPlanDay(
            dayNumber = 53,
            title = "Jacob abandona a Labán",
            passagesSummary = "Génesis 31:1-21",
            primaryBookId = 1,
            primaryChapter = 31,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(1, "Génesis", 31))
        ),
        ReadingPlanDay(
            dayNumber = 54,
            title = "Labán persigue a Jacob",
            passagesSummary = "Génesis 31:22-55",
            primaryBookId = 1,
            primaryChapter = 31,
            primaryVerse = 22,
            passages = listOf(PlanPassageSegment(1, "Génesis", 31))
        ),
        ReadingPlanDay(
            dayNumber = 55,
            title = "Jacob se prepara para encontrarse con Esaú",
            passagesSummary = "Génesis 32:1-21",
            primaryBookId = 1,
            primaryChapter = 32,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(1, "Génesis", 32))
        ),
        ReadingPlanDay(
            dayNumber = 56,
            title = "Jacob lucha con el ángel",
            passagesSummary = "Génesis 32:22-32",
            primaryBookId = 1,
            primaryChapter = 32,
            primaryVerse = 22,
            passages = listOf(PlanPassageSegment(1, "Génesis", 32))
        ),
        ReadingPlanDay(
            dayNumber = 57,
            title = "Jacob se reconcilia con Esaú",
            passagesSummary = "Génesis 33:1-20",
            primaryBookId = 1,
            primaryChapter = 33,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(1, "Génesis", 33))
        ),
        ReadingPlanDay(
            dayNumber = 58,
            title = "Dina y Siquem",
            passagesSummary = "Génesis 34:1-31",
            primaryBookId = 1,
            primaryChapter = 34,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(1, "Génesis", 34))
        ),
        ReadingPlanDay(
            dayNumber = 59,
            title = "Jacob vuelve a Bet-el",
            passagesSummary = "Génesis 35:1-15",
            primaryBookId = 1,
            primaryChapter = 35,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(1, "Génesis", 35))
        ),
        ReadingPlanDay(
            dayNumber = 60,
            title = "Muerte de Raquel",
            passagesSummary = "Génesis 35:16-20",
            primaryBookId = 1,
            primaryChapter = 35,
            primaryVerse = 16,
            passages = listOf(PlanPassageSegment(1, "Génesis", 35))
        ),
        ReadingPlanDay(
            dayNumber = 61,
            title = "Muerte de Isaac",
            passagesSummary = "Génesis 35:27-29",
            primaryBookId = 1,
            primaryChapter = 35,
            primaryVerse = 27,
            passages = listOf(PlanPassageSegment(1, "Génesis", 35))
        ),
        ReadingPlanDay(
            dayNumber = 62,
            title = "José y sus sueños",
            passagesSummary = "Génesis 37:1-11",
            primaryBookId = 1,
            primaryChapter = 37,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(1, "Génesis", 37))
        ),
        ReadingPlanDay(
            dayNumber = 63,
            title = "José es vendido por sus hermanos",
            passagesSummary = "Génesis 37:12-36",
            primaryBookId = 1,
            primaryChapter = 37,
            primaryVerse = 12,
            passages = listOf(PlanPassageSegment(1, "Génesis", 37))
        ),
        ReadingPlanDay(
            dayNumber = 64,
            title = "Judá y Tamar",
            passagesSummary = "Génesis 38:1-30",
            primaryBookId = 1,
            primaryChapter = 38,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(1, "Génesis", 38))
        ),
        ReadingPlanDay(
            dayNumber = 65,
            title = "José en casa de Potifar",
            passagesSummary = "Génesis 39:1-6",
            primaryBookId = 1,
            primaryChapter = 39,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(1, "Génesis", 39))
        ),
        ReadingPlanDay(
            dayNumber = 66,
            title = "José y la esposa de Potifar",
            passagesSummary = "Génesis 39:7-23",
            primaryBookId = 1,
            primaryChapter = 39,
            primaryVerse = 7,
            passages = listOf(PlanPassageSegment(1, "Génesis", 39))
        ),
        ReadingPlanDay(
            dayNumber = 67,
            title = "José en la cárcel",
            passagesSummary = "Génesis 40:1-23",
            primaryBookId = 1,
            primaryChapter = 40,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(1, "Génesis", 40))
        ),
        ReadingPlanDay(
            dayNumber = 68,
            title = "José interpreta los sueños de Faraón",
            passagesSummary = "Génesis 41:1-36",
            primaryBookId = 1,
            primaryChapter = 41,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(1, "Génesis", 41))
        ),
        ReadingPlanDay(
            dayNumber = 69,
            title = "José es elevado a gobernador de Egipto",
            passagesSummary = "Génesis 41:37-57",
            primaryBookId = 1,
            primaryChapter = 41,
            primaryVerse = 37,
            passages = listOf(PlanPassageSegment(1, "Génesis", 41))
        ),
        ReadingPlanDay(
            dayNumber = 70,
            title = "Los hermanos de José llegan a Egipto",
            passagesSummary = "Génesis 42:1-38",
            primaryBookId = 1,
            primaryChapter = 42,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(1, "Génesis", 42))
        ),
        ReadingPlanDay(
            dayNumber = 71,
            title = "Los hermanos regresan con Benjamín",
            passagesSummary = "Génesis 43:1-34",
            primaryBookId = 1,
            primaryChapter = 43,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(1, "Génesis", 43))
        ),
        ReadingPlanDay(
            dayNumber = 72,
            title = "La copa de José y la acusación contra Benjamín",
            passagesSummary = "Génesis 44:1-34",
            primaryBookId = 1,
            primaryChapter = 44,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(1, "Génesis", 44))
        ),
        ReadingPlanDay(
            dayNumber = 73,
            title = "José se revela a sus hermanos",
            passagesSummary = "Génesis 45:1-28",
            primaryBookId = 1,
            primaryChapter = 45,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(1, "Génesis", 45))
        ),
        ReadingPlanDay(
            dayNumber = 74,
            title = "Jacob desciende a Egipto",
            passagesSummary = "Génesis 46:1-34",
            primaryBookId = 1,
            primaryChapter = 46,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(1, "Génesis", 46))
        ),
        ReadingPlanDay(
            dayNumber = 75,
            title = "Jacob y su familia se establecen en Gosén",
            passagesSummary = "Génesis 47:1-31",
            primaryBookId = 1,
            primaryChapter = 47,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(1, "Génesis", 47))
        ),
    )

    private fun part2(): List<ReadingPlanDay> = listOf(
        ReadingPlanDay(
            dayNumber = 76,
            title = "Jacob bendice a Efraín y Manasés",
            passagesSummary = "Génesis 48:1-22",
            primaryBookId = 1,
            primaryChapter = 48,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(1, "Génesis", 48))
        ),
        ReadingPlanDay(
            dayNumber = 77,
            title = "Jacob bendice a sus hijos",
            passagesSummary = "Génesis 49:1-33",
            primaryBookId = 1,
            primaryChapter = 49,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(1, "Génesis", 49))
        ),
        ReadingPlanDay(
            dayNumber = 78,
            title = "Muerte y sepultura de Jacob",
            passagesSummary = "Génesis 50:1-14",
            primaryBookId = 1,
            primaryChapter = 50,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(1, "Génesis", 50))
        ),
        ReadingPlanDay(
            dayNumber = 79,
            title = "José perdona nuevamente a sus hermanos",
            passagesSummary = "Génesis 50:15-21",
            primaryBookId = 1,
            primaryChapter = 50,
            primaryVerse = 15,
            passages = listOf(PlanPassageSegment(1, "Génesis", 50))
        ),
        ReadingPlanDay(
            dayNumber = 80,
            title = "Muerte de José",
            passagesSummary = "Génesis 50:22-26",
            primaryBookId = 1,
            primaryChapter = 50,
            primaryVerse = 22,
            passages = listOf(PlanPassageSegment(1, "Génesis", 50))
        ),
        ReadingPlanDay(
            dayNumber = 81,
            title = "Israel esclavizado en Egipto",
            passagesSummary = "Éxodo 1:1-22",
            primaryBookId = 2,
            primaryChapter = 1,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(2, "Éxodo", 1))
        ),
        ReadingPlanDay(
            dayNumber = 82,
            title = "Nacimiento de Moisés",
            passagesSummary = "Éxodo 2:1-10",
            primaryBookId = 2,
            primaryChapter = 2,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(2, "Éxodo", 2))
        ),
        ReadingPlanDay(
            dayNumber = 83,
            title = "Moisés mata al egipcio y huye",
            passagesSummary = "Éxodo 2:11-25",
            primaryBookId = 2,
            primaryChapter = 2,
            primaryVerse = 11,
            passages = listOf(PlanPassageSegment(2, "Éxodo", 2))
        ),
        ReadingPlanDay(
            dayNumber = 84,
            title = "Moisés y la zarza ardiente",
            passagesSummary = "Éxodo 3:1-22",
            primaryBookId = 2,
            primaryChapter = 3,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(2, "Éxodo", 3))
        ),
        ReadingPlanDay(
            dayNumber = 85,
            title = "Dios confirma el llamamiento de Moisés",
            passagesSummary = "Éxodo 4:1-17",
            primaryBookId = 2,
            primaryChapter = 4,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(2, "Éxodo", 4))
        ),
        ReadingPlanDay(
            dayNumber = 86,
            title = "Moisés vuelve a Egipto",
            passagesSummary = "Éxodo 4:18-31",
            primaryBookId = 2,
            primaryChapter = 4,
            primaryVerse = 18,
            passages = listOf(PlanPassageSegment(2, "Éxodo", 4))
        ),
        ReadingPlanDay(
            dayNumber = 87,
            title = "Moisés y Aarón ante Faraón",
            passagesSummary = "Éxodo 5:1-23",
            primaryBookId = 2,
            primaryChapter = 5,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(2, "Éxodo", 5))
        ),
        ReadingPlanDay(
            dayNumber = 88,
            title = "Dios reafirma su pacto",
            passagesSummary = "Éxodo 6:1-13",
            primaryBookId = 2,
            primaryChapter = 6,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(2, "Éxodo", 6))
        ),
        ReadingPlanDay(
            dayNumber = 89,
            title = "Aarón convierte su vara en serpiente",
            passagesSummary = "Éxodo 7:1-13",
            primaryBookId = 2,
            primaryChapter = 7,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(2, "Éxodo", 7))
        ),
        ReadingPlanDay(
            dayNumber = 90,
            title = "Primera plaga: agua convertida en sangre",
            passagesSummary = "Éxodo 7:14-25",
            primaryBookId = 2,
            primaryChapter = 7,
            primaryVerse = 14,
            passages = listOf(PlanPassageSegment(2, "Éxodo", 7))
        ),
        ReadingPlanDay(
            dayNumber = 91,
            title = "Segunda plaga: ranas",
            passagesSummary = "Éxodo 8:1-15",
            primaryBookId = 2,
            primaryChapter = 8,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(2, "Éxodo", 8))
        ),
        ReadingPlanDay(
            dayNumber = 92,
            title = "Tercera plaga: piojos",
            passagesSummary = "Éxodo 8:16-19",
            primaryBookId = 2,
            primaryChapter = 8,
            primaryVerse = 16,
            passages = listOf(PlanPassageSegment(2, "Éxodo", 8))
        ),
        ReadingPlanDay(
            dayNumber = 93,
            title = "Cuarta plaga: moscas",
            passagesSummary = "Éxodo 8:20-32",
            primaryBookId = 2,
            primaryChapter = 8,
            primaryVerse = 20,
            passages = listOf(PlanPassageSegment(2, "Éxodo", 8))
        ),
        ReadingPlanDay(
            dayNumber = 94,
            title = "Quinta plaga: enfermedad del ganado",
            passagesSummary = "Éxodo 9:1-7",
            primaryBookId = 2,
            primaryChapter = 9,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(2, "Éxodo", 9))
        ),
        ReadingPlanDay(
            dayNumber = 95,
            title = "Sexta plaga: úlceras",
            passagesSummary = "Éxodo 9:8-12",
            primaryBookId = 2,
            primaryChapter = 9,
            primaryVerse = 8,
            passages = listOf(PlanPassageSegment(2, "Éxodo", 9))
        ),
        ReadingPlanDay(
            dayNumber = 96,
            title = "Séptima plaga: granizo",
            passagesSummary = "Éxodo 9:13-35",
            primaryBookId = 2,
            primaryChapter = 9,
            primaryVerse = 13,
            passages = listOf(PlanPassageSegment(2, "Éxodo", 9))
        ),
        ReadingPlanDay(
            dayNumber = 97,
            title = "Octava plaga: langostas",
            passagesSummary = "Éxodo 10:1-20",
            primaryBookId = 2,
            primaryChapter = 10,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(2, "Éxodo", 10))
        ),
        ReadingPlanDay(
            dayNumber = 98,
            title = "Novena plaga: tinieblas",
            passagesSummary = "Éxodo 10:21-29",
            primaryBookId = 2,
            primaryChapter = 10,
            primaryVerse = 21,
            passages = listOf(PlanPassageSegment(2, "Éxodo", 10))
        ),
        ReadingPlanDay(
            dayNumber = 99,
            title = "Anuncio de la muerte de los primogénitos",
            passagesSummary = "Éxodo 11:1-10",
            primaryBookId = 2,
            primaryChapter = 11,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(2, "Éxodo", 11))
        ),
        ReadingPlanDay(
            dayNumber = 100,
            title = "Institución de la Pascua",
            passagesSummary = "Éxodo 12:1-28",
            primaryBookId = 2,
            primaryChapter = 12,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(2, "Éxodo", 12))
        ),
        ReadingPlanDay(
            dayNumber = 101,
            title = "Muerte de los primogénitos",
            passagesSummary = "Éxodo 12:29-36",
            primaryBookId = 2,
            primaryChapter = 12,
            primaryVerse = 29,
            passages = listOf(PlanPassageSegment(2, "Éxodo", 12))
        ),
        ReadingPlanDay(
            dayNumber = 102,
            title = "Israel sale de Egipto",
            passagesSummary = "Éxodo 12:37-51",
            primaryBookId = 2,
            primaryChapter = 12,
            primaryVerse = 37,
            passages = listOf(PlanPassageSegment(2, "Éxodo", 12))
        ),
        ReadingPlanDay(
            dayNumber = 103,
            title = "La columna de nube y fuego",
            passagesSummary = "Éxodo 13:17-22",
            primaryBookId = 2,
            primaryChapter = 13,
            primaryVerse = 17,
            passages = listOf(PlanPassageSegment(2, "Éxodo", 13))
        ),
        ReadingPlanDay(
            dayNumber = 104,
            title = "El paso del Mar Rojo",
            passagesSummary = "Éxodo 14:1-31",
            primaryBookId = 2,
            primaryChapter = 14,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(2, "Éxodo", 14))
        ),
        ReadingPlanDay(
            dayNumber = 105,
            title = "El cántico de Moisés",
            passagesSummary = "Éxodo 15:1-21",
            primaryBookId = 2,
            primaryChapter = 15,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(2, "Éxodo", 15))
        ),
        ReadingPlanDay(
            dayNumber = 106,
            title = "Las aguas de Mara",
            passagesSummary = "Éxodo 15:22-27",
            primaryBookId = 2,
            primaryChapter = 15,
            primaryVerse = 22,
            passages = listOf(PlanPassageSegment(2, "Éxodo", 15))
        ),
        ReadingPlanDay(
            dayNumber = 107,
            title = "Dios alimenta a Israel con maná",
            passagesSummary = "Éxodo 16:1-36",
            primaryBookId = 2,
            primaryChapter = 16,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(2, "Éxodo", 16))
        ),
        ReadingPlanDay(
            dayNumber = 108,
            title = "Agua de la roca en Refidim",
            passagesSummary = "Éxodo 17:1-7",
            primaryBookId = 2,
            primaryChapter = 17,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(2, "Éxodo", 17))
        ),
        ReadingPlanDay(
            dayNumber = 109,
            title = "Israel lucha contra Amalec",
            passagesSummary = "Éxodo 17:8-16",
            primaryBookId = 2,
            primaryChapter = 17,
            primaryVerse = 8,
            passages = listOf(PlanPassageSegment(2, "Éxodo", 17))
        ),
        ReadingPlanDay(
            dayNumber = 110,
            title = "Jetro visita a Moisés",
            passagesSummary = "Éxodo 18:1-27",
            primaryBookId = 2,
            primaryChapter = 18,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(2, "Éxodo", 18))
        ),
        ReadingPlanDay(
            dayNumber = 111,
            title = "Dios aparece en el Sinaí",
            passagesSummary = "Éxodo 19:1-25",
            primaryBookId = 2,
            primaryChapter = 19,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(2, "Éxodo", 19))
        ),
        ReadingPlanDay(
            dayNumber = 112,
            title = "Los Diez Mandamientos",
            passagesSummary = "Éxodo 20:1-21",
            primaryBookId = 2,
            primaryChapter = 20,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(2, "Éxodo", 20))
        ),
        ReadingPlanDay(
            dayNumber = 113,
            title = "El pueblo hace pacto con Dios",
            passagesSummary = "Éxodo 24:1-18",
            primaryBookId = 2,
            primaryChapter = 24,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(2, "Éxodo", 24))
        ),
        ReadingPlanDay(
            dayNumber = 114,
            title = "Moisés recibe instrucciones para el tabernáculo",
            passagesSummary = "Éxodo 25–31",
            primaryBookId = 2,
            primaryChapter = 25,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(2, "Éxodo", 25), PlanPassageSegment(2, "Éxodo", 26), PlanPassageSegment(2, "Éxodo", 27), PlanPassageSegment(2, "Éxodo", 28), PlanPassageSegment(2, "Éxodo", 29), PlanPassageSegment(2, "Éxodo", 30), PlanPassageSegment(2, "Éxodo", 31))
        ),
        ReadingPlanDay(
            dayNumber = 115,
            title = "El becerro de oro",
            passagesSummary = "Éxodo 32:1-35",
            primaryBookId = 2,
            primaryChapter = 32,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(2, "Éxodo", 32))
        ),
        ReadingPlanDay(
            dayNumber = 116,
            title = "Moisés intercede por Israel",
            passagesSummary = "Éxodo 33:1-23",
            primaryBookId = 2,
            primaryChapter = 33,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(2, "Éxodo", 33))
        ),
        ReadingPlanDay(
            dayNumber = 117,
            title = "Dios renueva el pacto",
            passagesSummary = "Éxodo 34:1-35",
            primaryBookId = 2,
            primaryChapter = 34,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(2, "Éxodo", 34))
        ),
        ReadingPlanDay(
            dayNumber = 118,
            title = "Construcción del tabernáculo",
            passagesSummary = "Éxodo 35–40",
            primaryBookId = 2,
            primaryChapter = 35,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(2, "Éxodo", 35), PlanPassageSegment(2, "Éxodo", 36), PlanPassageSegment(2, "Éxodo", 37), PlanPassageSegment(2, "Éxodo", 38), PlanPassageSegment(2, "Éxodo", 39), PlanPassageSegment(2, "Éxodo", 40))
        ),
        ReadingPlanDay(
            dayNumber = 119,
            title = "La gloria de Dios llena el tabernáculo",
            passagesSummary = "Éxodo 40:34-38",
            primaryBookId = 2,
            primaryChapter = 40,
            primaryVerse = 34,
            passages = listOf(PlanPassageSegment(2, "Éxodo", 40))
        ),
        ReadingPlanDay(
            dayNumber = 120,
            title = "Aarón y sus hijos son consagrados",
            passagesSummary = "Levítico 8:1-36",
            primaryBookId = 3,
            primaryChapter = 8,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(3, "Levítico", 8))
        ),
        ReadingPlanDay(
            dayNumber = 121,
            title = "Primeros sacrificios de Aarón",
            passagesSummary = "Levítico 9:1-24",
            primaryBookId = 3,
            primaryChapter = 9,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(3, "Levítico", 9))
        ),
        ReadingPlanDay(
            dayNumber = 122,
            title = "Nadab y Abiú ofrecen fuego extraño y mueren",
            passagesSummary = "Levítico 10:1-7",
            primaryBookId = 3,
            primaryChapter = 10,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(3, "Levítico", 10))
        ),
        ReadingPlanDay(
            dayNumber = 123,
            title = "Leyes sobre pureza y animales",
            passagesSummary = "Levítico 11:1-47",
            primaryBookId = 3,
            primaryChapter = 11,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(3, "Levítico", 11))
        ),
        ReadingPlanDay(
            dayNumber = 124,
            title = "Día de la Expiación",
            passagesSummary = "Levítico 16:1-34",
            primaryBookId = 3,
            primaryChapter = 16,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(3, "Levítico", 16))
        ),
        ReadingPlanDay(
            dayNumber = 125,
            title = "El blasfemo es juzgado",
            passagesSummary = "Levítico 24:10-23",
            primaryBookId = 3,
            primaryChapter = 24,
            primaryVerse = 10,
            passages = listOf(PlanPassageSegment(3, "Levítico", 24))
        ),
        ReadingPlanDay(
            dayNumber = 126,
            title = "Israel es censado",
            passagesSummary = "Números 1:1-54",
            primaryBookId = 4,
            primaryChapter = 1,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(4, "Números", 1))
        ),
        ReadingPlanDay(
            dayNumber = 127,
            title = "Los levitas son apartados",
            passagesSummary = "Números 3–4",
            primaryBookId = 4,
            primaryChapter = 3,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(4, "Números", 3), PlanPassageSegment(4, "Números", 4))
        ),
        ReadingPlanDay(
            dayNumber = 128,
            title = "La nube guía a Israel",
            passagesSummary = "Números 9:15-23",
            primaryBookId = 4,
            primaryChapter = 9,
            primaryVerse = 15,
            passages = listOf(PlanPassageSegment(4, "Números", 9))
        ),
        ReadingPlanDay(
            dayNumber = 129,
            title = "Israel sale del Sinaí",
            passagesSummary = "Números 10:11-36",
            primaryBookId = 4,
            primaryChapter = 10,
            primaryVerse = 11,
            passages = listOf(PlanPassageSegment(4, "Números", 10))
        ),
        ReadingPlanDay(
            dayNumber = 130,
            title = "Eldad y Medad profetizan",
            passagesSummary = "Números 11:24-30",
            primaryBookId = 4,
            primaryChapter = 11,
            primaryVerse = 24,
            passages = listOf(PlanPassageSegment(4, "Números", 11))
        ),
        ReadingPlanDay(
            dayNumber = 131,
            title = "Dios envía codornices",
            passagesSummary = "Números 11:31-35",
            primaryBookId = 4,
            primaryChapter = 11,
            primaryVerse = 31,
            passages = listOf(PlanPassageSegment(4, "Números", 11))
        ),
        ReadingPlanDay(
            dayNumber = 132,
            title = "María y Aarón murmuran contra Moisés",
            passagesSummary = "Números 12:1-16",
            primaryBookId = 4,
            primaryChapter = 12,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(4, "Números", 12))
        ),
        ReadingPlanDay(
            dayNumber = 133,
            title = "Los doce espías exploran Canaán",
            passagesSummary = "Números 13:1-33",
            primaryBookId = 4,
            primaryChapter = 13,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(4, "Números", 13))
        ),
        ReadingPlanDay(
            dayNumber = 134,
            title = "Israel se niega a entrar en Canaán",
            passagesSummary = "Números 14:1-45",
            primaryBookId = 4,
            primaryChapter = 14,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(4, "Números", 14))
        ),
        ReadingPlanDay(
            dayNumber = 135,
            title = "Coré, Datán y Abiram se rebelan",
            passagesSummary = "Números 16:1-50",
            primaryBookId = 4,
            primaryChapter = 16,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(4, "Números", 16))
        ),
        ReadingPlanDay(
            dayNumber = 136,
            title = "La vara de Aarón florece",
            passagesSummary = "Números 17:1-13",
            primaryBookId = 4,
            primaryChapter = 17,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(4, "Números", 17))
        ),
        ReadingPlanDay(
            dayNumber = 137,
            title = "Moisés golpea la roca",
            passagesSummary = "Números 20:1-13",
            primaryBookId = 4,
            primaryChapter = 20,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(4, "Números", 20))
        ),
        ReadingPlanDay(
            dayNumber = 138,
            title = "Muere Aarón",
            passagesSummary = "Números 20:22-29",
            primaryBookId = 4,
            primaryChapter = 20,
            primaryVerse = 22,
            passages = listOf(PlanPassageSegment(4, "Números", 20))
        ),
        ReadingPlanDay(
            dayNumber = 139,
            title = "La serpiente de bronce",
            passagesSummary = "Números 21:4-9",
            primaryBookId = 4,
            primaryChapter = 21,
            primaryVerse = 4,
            passages = listOf(PlanPassageSegment(4, "Números", 21))
        ),
        ReadingPlanDay(
            dayNumber = 140,
            title = "Israel derrota a Sehón",
            passagesSummary = "Números 21:21-32",
            primaryBookId = 4,
            primaryChapter = 21,
            primaryVerse = 21,
            passages = listOf(PlanPassageSegment(4, "Números", 21))
        ),
        ReadingPlanDay(
            dayNumber = 141,
            title = "Israel derrota a Og",
            passagesSummary = "Números 21:33-35",
            primaryBookId = 4,
            primaryChapter = 21,
            primaryVerse = 33,
            passages = listOf(PlanPassageSegment(4, "Números", 21))
        ),
        ReadingPlanDay(
            dayNumber = 142,
            title = "Balac llama a Balaam",
            passagesSummary = "Números 22:1-20",
            primaryBookId = 4,
            primaryChapter = 22,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(4, "Números", 22))
        ),
        ReadingPlanDay(
            dayNumber = 143,
            title = "Balaam y su asna",
            passagesSummary = "Números 22:21-41",
            primaryBookId = 4,
            primaryChapter = 22,
            primaryVerse = 21,
            passages = listOf(PlanPassageSegment(4, "Números", 22))
        ),
        ReadingPlanDay(
            dayNumber = 144,
            title = "Las bendiciones de Balaam",
            passagesSummary = "Números 23–24",
            primaryBookId = 4,
            primaryChapter = 23,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(4, "Números", 23), PlanPassageSegment(4, "Números", 24))
        ),
        ReadingPlanDay(
            dayNumber = 145,
            title = "Balaam y la corrupción de Israel",
            passagesSummary = "Números 25:1-18; 31:1-16",
            primaryBookId = 4,
            primaryChapter = 25,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(4, "Números", 25), PlanPassageSegment(4, "Números", 31))
        ),
        ReadingPlanDay(
            dayNumber = 146,
            title = "Finees detiene la plaga",
            passagesSummary = "Números 25:1-18",
            primaryBookId = 4,
            primaryChapter = 25,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(4, "Números", 25))
        ),
        ReadingPlanDay(
            dayNumber = 147,
            title = "Segundo censo",
            passagesSummary = "Números 26:1-65",
            primaryBookId = 4,
            primaryChapter = 26,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(4, "Números", 26))
        ),
        ReadingPlanDay(
            dayNumber = 148,
            title = "Las hijas de Zelofehad",
            passagesSummary = "Números 27:1-11",
            primaryBookId = 4,
            primaryChapter = 27,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(4, "Números", 27))
        ),
        ReadingPlanDay(
            dayNumber = 149,
            title = "Josué es designado sucesor de Moisés",
            passagesSummary = "Números 27:12-23",
            primaryBookId = 4,
            primaryChapter = 27,
            primaryVerse = 12,
            passages = listOf(PlanPassageSegment(4, "Números", 27))
        ),
        ReadingPlanDay(
            dayNumber = 150,
            title = "Israel derrota a Madián",
            passagesSummary = "Números 31:1-54",
            primaryBookId = 4,
            primaryChapter = 31,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(4, "Números", 31))
        ),
    )

    private fun part3(): List<ReadingPlanDay> = listOf(
        ReadingPlanDay(
            dayNumber = 151,
            title = "Rubén, Gad y media tribu de Manasés reciben territorio",
            passagesSummary = "Números 32:1-42",
            primaryBookId = 4,
            primaryChapter = 32,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(4, "Números", 32))
        ),
        ReadingPlanDay(
            dayNumber = 152,
            title = "Moisés recuerda el viaje de Israel",
            passagesSummary = "Deuteronomio 1–4",
            primaryBookId = 5,
            primaryChapter = 1,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(5, "Deuteronomio", 1), PlanPassageSegment(5, "Deuteronomio", 2), PlanPassageSegment(5, "Deuteronomio", 3), PlanPassageSegment(5, "Deuteronomio", 4))
        ),
        ReadingPlanDay(
            dayNumber = 153,
            title = "Renovación del pacto",
            passagesSummary = "Deuteronomio 5–11",
            primaryBookId = 5,
            primaryChapter = 5,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(5, "Deuteronomio", 5), PlanPassageSegment(5, "Deuteronomio", 6), PlanPassageSegment(5, "Deuteronomio", 7), PlanPassageSegment(5, "Deuteronomio", 8), PlanPassageSegment(5, "Deuteronomio", 9), PlanPassageSegment(5, "Deuteronomio", 10), PlanPassageSegment(5, "Deuteronomio", 11))
        ),
        ReadingPlanDay(
            dayNumber = 154,
            title = "Moisés anuncia bendiciones y maldiciones",
            passagesSummary = "Deuteronomio 27–28",
            primaryBookId = 5,
            primaryChapter = 27,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(5, "Deuteronomio", 27), PlanPassageSegment(5, "Deuteronomio", 28))
        ),
        ReadingPlanDay(
            dayNumber = 155,
            title = "Renovación final del pacto",
            passagesSummary = "Deuteronomio 29–30",
            primaryBookId = 5,
            primaryChapter = 29,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(5, "Deuteronomio", 29), PlanPassageSegment(5, "Deuteronomio", 30))
        ),
        ReadingPlanDay(
            dayNumber = 156,
            title = "Josué es comisionado",
            passagesSummary = "Deuteronomio 31:1-8",
            primaryBookId = 5,
            primaryChapter = 31,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(5, "Deuteronomio", 31))
        ),
        ReadingPlanDay(
            dayNumber = 157,
            title = "El cántico de Moisés",
            passagesSummary = "Deuteronomio 32:1-47",
            primaryBookId = 5,
            primaryChapter = 32,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(5, "Deuteronomio", 32))
        ),
        ReadingPlanDay(
            dayNumber = 158,
            title = "Moisés bendice a las tribus",
            passagesSummary = "Deuteronomio 33:1-29",
            primaryBookId = 5,
            primaryChapter = 33,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(5, "Deuteronomio", 33))
        ),
        ReadingPlanDay(
            dayNumber = 159,
            title = "Moisés contempla la tierra prometida",
            passagesSummary = "Deuteronomio 34:1-4",
            primaryBookId = 5,
            primaryChapter = 34,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(5, "Deuteronomio", 34))
        ),
        ReadingPlanDay(
            dayNumber = 160,
            title = "Muerte y sepultura de Moisés",
            passagesSummary = "Deuteronomio 34:5-12",
            primaryBookId = 5,
            primaryChapter = 34,
            primaryVerse = 5,
            passages = listOf(PlanPassageSegment(5, "Deuteronomio", 34))
        ),
        ReadingPlanDay(
            dayNumber = 161,
            title = "Josué sucede a Moisés",
            passagesSummary = "Josué 1:1-18",
            primaryBookId = 6,
            primaryChapter = 1,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(6, "Josué", 1))
        ),
        ReadingPlanDay(
            dayNumber = 162,
            title = "Rahab recibe a los espías",
            passagesSummary = "Josué 2:1-24",
            primaryBookId = 6,
            primaryChapter = 2,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(6, "Josué", 2))
        ),
        ReadingPlanDay(
            dayNumber = 163,
            title = "Israel cruza el Jordán",
            passagesSummary = "Josué 3:1-17",
            primaryBookId = 6,
            primaryChapter = 3,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(6, "Josué", 3))
        ),
        ReadingPlanDay(
            dayNumber = 164,
            title = "Las doce piedras del Jordán",
            passagesSummary = "Josué 4:1-24",
            primaryBookId = 6,
            primaryChapter = 4,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(6, "Josué", 4))
        ),
        ReadingPlanDay(
            dayNumber = 165,
            title = "Circuncisión en Gilgal",
            passagesSummary = "Josué 5:1-12",
            primaryBookId = 6,
            primaryChapter = 5,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(6, "Josué", 5))
        ),
        ReadingPlanDay(
            dayNumber = 166,
            title = "El Príncipe del ejército de Jehová",
            passagesSummary = "Josué 5:13-15",
            primaryBookId = 6,
            primaryChapter = 5,
            primaryVerse = 13,
            passages = listOf(PlanPassageSegment(6, "Josué", 5))
        ),
        ReadingPlanDay(
            dayNumber = 167,
            title = "Caen los muros de Jericó",
            passagesSummary = "Josué 6:1-27",
            primaryBookId = 6,
            primaryChapter = 6,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(6, "Josué", 6))
        ),
        ReadingPlanDay(
            dayNumber = 168,
            title = "Acán y el pecado oculto",
            passagesSummary = "Josué 7:1-26",
            primaryBookId = 6,
            primaryChapter = 7,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(6, "Josué", 7))
        ),
        ReadingPlanDay(
            dayNumber = 169,
            title = "Conquista de Hai",
            passagesSummary = "Josué 8:1-29",
            primaryBookId = 6,
            primaryChapter = 8,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(6, "Josué", 8))
        ),
        ReadingPlanDay(
            dayNumber = 170,
            title = "Renovación del pacto en Ebal",
            passagesSummary = "Josué 8:30-35",
            primaryBookId = 6,
            primaryChapter = 8,
            primaryVerse = 30,
            passages = listOf(PlanPassageSegment(6, "Josué", 8))
        ),
        ReadingPlanDay(
            dayNumber = 171,
            title = "Los gabaonitas engañan a Israel",
            passagesSummary = "Josué 9:1-27",
            primaryBookId = 6,
            primaryChapter = 9,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(6, "Josué", 9))
        ),
        ReadingPlanDay(
            dayNumber = 172,
            title = "Josué hace detener el sol",
            passagesSummary = "Josué 10:1-43",
            primaryBookId = 6,
            primaryChapter = 10,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(6, "Josué", 10))
        ),
        ReadingPlanDay(
            dayNumber = 173,
            title = "Conquista del sur de Canaán",
            passagesSummary = "Josué 10",
            primaryBookId = 6,
            primaryChapter = 10,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(6, "Josué", 10))
        ),
        ReadingPlanDay(
            dayNumber = 174,
            title = "Conquista del norte",
            passagesSummary = "Josué 11:1-23",
            primaryBookId = 6,
            primaryChapter = 11,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(6, "Josué", 11))
        ),
        ReadingPlanDay(
            dayNumber = 175,
            title = "Repartición de Canaán",
            passagesSummary = "Josué 13–21",
            primaryBookId = 6,
            primaryChapter = 13,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(6, "Josué", 13), PlanPassageSegment(6, "Josué", 14), PlanPassageSegment(6, "Josué", 15), PlanPassageSegment(6, "Josué", 16), PlanPassageSegment(6, "Josué", 17), PlanPassageSegment(6, "Josué", 18), PlanPassageSegment(6, "Josué", 19), PlanPassageSegment(6, "Josué", 20), PlanPassageSegment(6, "Josué", 21))
        ),
        ReadingPlanDay(
            dayNumber = 176,
            title = "Las tribus del Jordán construyen un altar",
            passagesSummary = "Josué 22:1-34",
            primaryBookId = 6,
            primaryChapter = 22,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(6, "Josué", 22))
        ),
        ReadingPlanDay(
            dayNumber = 177,
            title = "Despedida de Josué",
            passagesSummary = "Josué 23:1-16",
            primaryBookId = 6,
            primaryChapter = 23,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(6, "Josué", 23))
        ),
        ReadingPlanDay(
            dayNumber = 178,
            title = "Renovación del pacto en Siquem",
            passagesSummary = "Josué 24:1-28",
            primaryBookId = 6,
            primaryChapter = 24,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(6, "Josué", 24))
        ),
        ReadingPlanDay(
            dayNumber = 179,
            title = "Muerte de Josué",
            passagesSummary = "Josué 24:29-31",
            primaryBookId = 6,
            primaryChapter = 24,
            primaryVerse = 29,
            passages = listOf(PlanPassageSegment(6, "Josué", 24))
        ),
        ReadingPlanDay(
            dayNumber = 180,
            title = "Israel después de Josué",
            passagesSummary = "Jueces 1–2",
            primaryBookId = 7,
            primaryChapter = 1,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(7, "Jueces", 1), PlanPassageSegment(7, "Jueces", 2))
        ),
        ReadingPlanDay(
            dayNumber = 181,
            title = "Otoniel libera a Israel",
            passagesSummary = "Jueces 3:7-11",
            primaryBookId = 7,
            primaryChapter = 3,
            primaryVerse = 7,
            passages = listOf(PlanPassageSegment(7, "Jueces", 3))
        ),
        ReadingPlanDay(
            dayNumber = 182,
            title = "Aod mata a Eglón",
            passagesSummary = "Jueces 3:12-30",
            primaryBookId = 7,
            primaryChapter = 3,
            primaryVerse = 12,
            passages = listOf(PlanPassageSegment(7, "Jueces", 3))
        ),
        ReadingPlanDay(
            dayNumber = 183,
            title = "Samgar derrota a los filisteos",
            passagesSummary = "Jueces 3:31",
            primaryBookId = 7,
            primaryChapter = 3,
            primaryVerse = 31,
            passages = listOf(PlanPassageSegment(7, "Jueces", 3))
        ),
        ReadingPlanDay(
            dayNumber = 184,
            title = "Débora y Barac",
            passagesSummary = "Jueces 4:1-24",
            primaryBookId = 7,
            primaryChapter = 4,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(7, "Jueces", 4))
        ),
        ReadingPlanDay(
            dayNumber = 185,
            title = "Jael mata a Sísara",
            passagesSummary = "Jueces 4:17-22",
            primaryBookId = 7,
            primaryChapter = 4,
            primaryVerse = 17,
            passages = listOf(PlanPassageSegment(7, "Jueces", 4))
        ),
        ReadingPlanDay(
            dayNumber = 186,
            title = "Cántico de Débora",
            passagesSummary = "Jueces 5",
            primaryBookId = 7,
            primaryChapter = 5,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(7, "Jueces", 5))
        ),
        ReadingPlanDay(
            dayNumber = 187,
            title = "Gedeón es llamado",
            passagesSummary = "Jueces 6:1-40",
            primaryBookId = 7,
            primaryChapter = 6,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(7, "Jueces", 6))
        ),
        ReadingPlanDay(
            dayNumber = 188,
            title = "Gedeón y el vellón",
            passagesSummary = "Jueces 6:36-40",
            primaryBookId = 7,
            primaryChapter = 6,
            primaryVerse = 36,
            passages = listOf(PlanPassageSegment(7, "Jueces", 6))
        ),
        ReadingPlanDay(
            dayNumber = 189,
            title = "Gedeón reduce su ejército",
            passagesSummary = "Jueces 7:1-8",
            primaryBookId = 7,
            primaryChapter = 7,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(7, "Jueces", 7))
        ),
        ReadingPlanDay(
            dayNumber = 190,
            title = "Gedeón derrota a Madián",
            passagesSummary = "Jueces 7:9-25",
            primaryBookId = 7,
            primaryChapter = 7,
            primaryVerse = 9,
            passages = listOf(PlanPassageSegment(7, "Jueces", 7))
        ),
        ReadingPlanDay(
            dayNumber = 191,
            title = "Gedeón persigue a los madianitas",
            passagesSummary = "Jueces 8:1-21",
            primaryBookId = 7,
            primaryChapter = 8,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(7, "Jueces", 8))
        ),
        ReadingPlanDay(
            dayNumber = 192,
            title = "Abimelec se convierte en rey",
            passagesSummary = "Jueces 9:1-57",
            primaryBookId = 7,
            primaryChapter = 9,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(7, "Jueces", 9))
        ),
        ReadingPlanDay(
            dayNumber = 193,
            title = "Jefté es llamado",
            passagesSummary = "Jueces 11:1-11",
            primaryBookId = 7,
            primaryChapter = 11,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(7, "Jueces", 11))
        ),
        ReadingPlanDay(
            dayNumber = 194,
            title = "El voto de Jefté",
            passagesSummary = "Jueces 11:29-40",
            primaryBookId = 7,
            primaryChapter = 11,
            primaryVerse = 29,
            passages = listOf(PlanPassageSegment(7, "Jueces", 11))
        ),
        ReadingPlanDay(
            dayNumber = 195,
            title = "Jefté pelea contra Amón",
            passagesSummary = "Jueces 11:12-28",
            primaryBookId = 7,
            primaryChapter = 11,
            primaryVerse = 12,
            passages = listOf(PlanPassageSegment(7, "Jueces", 11))
        ),
        ReadingPlanDay(
            dayNumber = 196,
            title = "Sansón nace",
            passagesSummary = "Jueces 13:1-25",
            primaryBookId = 7,
            primaryChapter = 13,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(7, "Jueces", 13))
        ),
        ReadingPlanDay(
            dayNumber = 197,
            title = "Sansón y la mujer filistea",
            passagesSummary = "Jueces 14:1-20",
            primaryBookId = 7,
            primaryChapter = 14,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(7, "Jueces", 14))
        ),
        ReadingPlanDay(
            dayNumber = 198,
            title = "Sansón y las zorras",
            passagesSummary = "Jueces 15:1-20",
            primaryBookId = 7,
            primaryChapter = 15,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(7, "Jueces", 15))
        ),
        ReadingPlanDay(
            dayNumber = 199,
            title = "Sansón y Dalila",
            passagesSummary = "Jueces 16:1-22",
            primaryBookId = 7,
            primaryChapter = 16,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(7, "Jueces", 16))
        ),
        ReadingPlanDay(
            dayNumber = 200,
            title = "Sansón derriba el templo",
            passagesSummary = "Jueces 16:23-31",
            primaryBookId = 7,
            primaryChapter = 16,
            primaryVerse = 23,
            passages = listOf(PlanPassageSegment(7, "Jueces", 16))
        ),
        ReadingPlanDay(
            dayNumber = 201,
            title = "Micaía y su ídolo",
            passagesSummary = "Jueces 17:1-13",
            primaryBookId = 7,
            primaryChapter = 17,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(7, "Jueces", 17))
        ),
        ReadingPlanDay(
            dayNumber = 202,
            title = "La tribu de Dan toma Lais",
            passagesSummary = "Jueces 18:1-31",
            primaryBookId = 7,
            primaryChapter = 18,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(7, "Jueces", 18))
        ),
        ReadingPlanDay(
            dayNumber = 203,
            title = "El crimen de Gabaa",
            passagesSummary = "Jueces 19:1-30",
            primaryBookId = 7,
            primaryChapter = 19,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(7, "Jueces", 19))
        ),
        ReadingPlanDay(
            dayNumber = 204,
            title = "Guerra contra Benjamín",
            passagesSummary = "Jueces 20:1-48",
            primaryBookId = 7,
            primaryChapter = 20,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(7, "Jueces", 20))
        ),
        ReadingPlanDay(
            dayNumber = 205,
            title = "Las mujeres para Benjamín",
            passagesSummary = "Jueces 21:1-25",
            primaryBookId = 7,
            primaryChapter = 21,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(7, "Jueces", 21))
        ),
        ReadingPlanDay(
            dayNumber = 206,
            title = "Noemí pierde a su esposo e hijos",
            passagesSummary = "Rut 1:1-5",
            primaryBookId = 8,
            primaryChapter = 1,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(8, "Rut", 1))
        ),
        ReadingPlanDay(
            dayNumber = 207,
            title = "Rut decide acompañar a Noemí",
            passagesSummary = "Rut 1:6-22",
            primaryBookId = 8,
            primaryChapter = 1,
            primaryVerse = 6,
            passages = listOf(PlanPassageSegment(8, "Rut", 1))
        ),
        ReadingPlanDay(
            dayNumber = 208,
            title = "Rut recoge espigas en el campo de Booz",
            passagesSummary = "Rut 2:1-23",
            primaryBookId = 8,
            primaryChapter = 2,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(8, "Rut", 2))
        ),
        ReadingPlanDay(
            dayNumber = 209,
            title = "Rut pide redención a Booz",
            passagesSummary = "Rut 3:1-18",
            primaryBookId = 8,
            primaryChapter = 3,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(8, "Rut", 3))
        ),
        ReadingPlanDay(
            dayNumber = 210,
            title = "Booz redime a Rut",
            passagesSummary = "Rut 4:1-12",
            primaryBookId = 8,
            primaryChapter = 4,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(8, "Rut", 4))
        ),
        ReadingPlanDay(
            dayNumber = 211,
            title = "Nace Obed",
            passagesSummary = "Rut 4:13-17",
            primaryBookId = 8,
            primaryChapter = 4,
            primaryVerse = 13,
            passages = listOf(PlanPassageSegment(8, "Rut", 4))
        ),
        ReadingPlanDay(
            dayNumber = 212,
            title = "Genealogía hasta David",
            passagesSummary = "Rut 4:18-22",
            primaryBookId = 8,
            primaryChapter = 4,
            primaryVerse = 18,
            passages = listOf(PlanPassageSegment(8, "Rut", 4))
        ),
        ReadingPlanDay(
            dayNumber = 213,
            title = "Ana ora por un hijo",
            passagesSummary = "1 Samuel 1:1-28",
            primaryBookId = 9,
            primaryChapter = 1,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(9, "1 Samuel", 1))
        ),
        ReadingPlanDay(
            dayNumber = 214,
            title = "Nace Samuel",
            passagesSummary = "1 Samuel 1:19-28",
            primaryBookId = 9,
            primaryChapter = 1,
            primaryVerse = 19,
            passages = listOf(PlanPassageSegment(9, "1 Samuel", 1))
        ),
        ReadingPlanDay(
            dayNumber = 215,
            title = "El cántico de Ana",
            passagesSummary = "1 Samuel 2:1-10",
            primaryBookId = 9,
            primaryChapter = 2,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(9, "1 Samuel", 2))
        ),
        ReadingPlanDay(
            dayNumber = 216,
            title = "Samuel sirve en el templo",
            passagesSummary = "1 Samuel 2:11-26",
            primaryBookId = 9,
            primaryChapter = 2,
            primaryVerse = 11,
            passages = listOf(PlanPassageSegment(9, "1 Samuel", 2))
        ),
        ReadingPlanDay(
            dayNumber = 217,
            title = "Dios llama a Samuel",
            passagesSummary = "1 Samuel 3:1-21",
            primaryBookId = 9,
            primaryChapter = 3,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(9, "1 Samuel", 3))
        ),
        ReadingPlanDay(
            dayNumber = 218,
            title = "El arca es capturada",
            passagesSummary = "1 Samuel 4:1-22",
            primaryBookId = 9,
            primaryChapter = 4,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(9, "1 Samuel", 4))
        ),
        ReadingPlanDay(
            dayNumber = 219,
            title = "Dagón cae ante el arca",
            passagesSummary = "1 Samuel 5:1-12",
            primaryBookId = 9,
            primaryChapter = 5,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(9, "1 Samuel", 5))
        ),
        ReadingPlanDay(
            dayNumber = 220,
            title = "El arca vuelve a Israel",
            passagesSummary = "1 Samuel 6:1-21",
            primaryBookId = 9,
            primaryChapter = 6,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(9, "1 Samuel", 6))
        ),
        ReadingPlanDay(
            dayNumber = 221,
            title = "Samuel dirige a Israel",
            passagesSummary = "1 Samuel 7:1-17",
            primaryBookId = 9,
            primaryChapter = 7,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(9, "1 Samuel", 7))
        ),
        ReadingPlanDay(
            dayNumber = 222,
            title = "Israel pide un rey",
            passagesSummary = "1 Samuel 8:1-22",
            primaryBookId = 9,
            primaryChapter = 8,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(9, "1 Samuel", 8))
        ),
        ReadingPlanDay(
            dayNumber = 223,
            title = "Saúl es ungido",
            passagesSummary = "1 Samuel 9–10",
            primaryBookId = 9,
            primaryChapter = 9,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(9, "1 Samuel", 9), PlanPassageSegment(9, "1 Samuel", 10))
        ),
        ReadingPlanDay(
            dayNumber = 224,
            title = "Saúl derrota a los amonitas",
            passagesSummary = "1 Samuel 11:1-15",
            primaryBookId = 9,
            primaryChapter = 11,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(9, "1 Samuel", 11))
        ),
        ReadingPlanDay(
            dayNumber = 225,
            title = "Saúl desobedece a Dios",
            passagesSummary = "1 Samuel 13:1-23",
            primaryBookId = 9,
            primaryChapter = 13,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(9, "1 Samuel", 13))
        ),
    )

    private fun part4(): List<ReadingPlanDay> = listOf(
        ReadingPlanDay(
            dayNumber = 226,
            title = "Jonatán derrota a los filisteos",
            passagesSummary = "1 Samuel 14:1-52",
            primaryBookId = 9,
            primaryChapter = 14,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(9, "1 Samuel", 14))
        ),
        ReadingPlanDay(
            dayNumber = 227,
            title = "Saúl desobedece respecto a Amalec",
            passagesSummary = "1 Samuel 15:1-35",
            primaryBookId = 9,
            primaryChapter = 15,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(9, "1 Samuel", 15))
        ),
        ReadingPlanDay(
            dayNumber = 228,
            title = "Samuel unge a David",
            passagesSummary = "1 Samuel 16:1-13",
            primaryBookId = 9,
            primaryChapter = 16,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(9, "1 Samuel", 16))
        ),
        ReadingPlanDay(
            dayNumber = 229,
            title = "David toca para Saúl",
            passagesSummary = "1 Samuel 16:14-23",
            primaryBookId = 9,
            primaryChapter = 16,
            primaryVerse = 14,
            passages = listOf(PlanPassageSegment(9, "1 Samuel", 16))
        ),
        ReadingPlanDay(
            dayNumber = 230,
            title = "David y Goliat",
            passagesSummary = "1 Samuel 17:1-58",
            primaryBookId = 9,
            primaryChapter = 17,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(9, "1 Samuel", 17))
        ),
        ReadingPlanDay(
            dayNumber = 231,
            title = "Jonatán y David hacen pacto",
            passagesSummary = "1 Samuel 18:1-4",
            primaryBookId = 9,
            primaryChapter = 18,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(9, "1 Samuel", 18))
        ),
        ReadingPlanDay(
            dayNumber = 232,
            title = "Saúl intenta matar a David",
            passagesSummary = "1 Samuel 18–20",
            primaryBookId = 9,
            primaryChapter = 18,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(9, "1 Samuel", 18), PlanPassageSegment(9, "1 Samuel", 19), PlanPassageSegment(9, "1 Samuel", 20))
        ),
        ReadingPlanDay(
            dayNumber = 233,
            title = "David huye y recibe ayuda de Jonatán",
            passagesSummary = "1 Samuel 20:1-42",
            primaryBookId = 9,
            primaryChapter = 20,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(9, "1 Samuel", 20))
        ),
        ReadingPlanDay(
            dayNumber = 234,
            title = "David recibe los panes de la proposición",
            passagesSummary = "1 Samuel 21:1-15",
            primaryBookId = 9,
            primaryChapter = 21,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(9, "1 Samuel", 21))
        ),
        ReadingPlanDay(
            dayNumber = 235,
            title = "David reúne a sus hombres",
            passagesSummary = "1 Samuel 22:1-5",
            primaryBookId = 9,
            primaryChapter = 22,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(9, "1 Samuel", 22))
        ),
        ReadingPlanDay(
            dayNumber = 236,
            title = "Saúl mata a los sacerdotes de Nob",
            passagesSummary = "1 Samuel 22:6-23",
            primaryBookId = 9,
            primaryChapter = 22,
            primaryVerse = 6,
            passages = listOf(PlanPassageSegment(9, "1 Samuel", 22))
        ),
        ReadingPlanDay(
            dayNumber = 237,
            title = "David perdona a Saúl en En-gadi",
            passagesSummary = "1 Samuel 24:1-22",
            primaryBookId = 9,
            primaryChapter = 24,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(9, "1 Samuel", 24))
        ),
        ReadingPlanDay(
            dayNumber = 238,
            title = "David y Abigail",
            passagesSummary = "1 Samuel 25:1-44",
            primaryBookId = 9,
            primaryChapter = 25,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(9, "1 Samuel", 25))
        ),
        ReadingPlanDay(
            dayNumber = 239,
            title = "David perdona nuevamente a Saúl",
            passagesSummary = "1 Samuel 26:1-25",
            primaryBookId = 9,
            primaryChapter = 26,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(9, "1 Samuel", 26))
        ),
        ReadingPlanDay(
            dayNumber = 240,
            title = "David entre los filisteos",
            passagesSummary = "1 Samuel 27:1-12",
            primaryBookId = 9,
            primaryChapter = 27,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(9, "1 Samuel", 27))
        ),
        ReadingPlanDay(
            dayNumber = 241,
            title = "Saúl consulta a la mujer de Endor",
            passagesSummary = "1 Samuel 28:1-25",
            primaryBookId = 9,
            primaryChapter = 28,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(9, "1 Samuel", 28))
        ),
        ReadingPlanDay(
            dayNumber = 242,
            title = "David es rechazado por los filisteos",
            passagesSummary = "1 Samuel 29:1-11",
            primaryBookId = 9,
            primaryChapter = 29,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(9, "1 Samuel", 29))
        ),
        ReadingPlanDay(
            dayNumber = 243,
            title = "David rescata a los cautivos de Siclag",
            passagesSummary = "1 Samuel 30:1-31",
            primaryBookId = 9,
            primaryChapter = 30,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(9, "1 Samuel", 30))
        ),
        ReadingPlanDay(
            dayNumber = 244,
            title = "Muerte de Saúl y Jonatán",
            passagesSummary = "1 Samuel 31:1-13",
            primaryBookId = 9,
            primaryChapter = 31,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(9, "1 Samuel", 31))
        ),
        ReadingPlanDay(
            dayNumber = 245,
            title = "David recibe noticia de la muerte de Saúl",
            passagesSummary = "2 Samuel 1:1-27",
            primaryBookId = 10,
            primaryChapter = 1,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(10, "2 Samuel", 1))
        ),
        ReadingPlanDay(
            dayNumber = 246,
            title = "David es rey de Judá",
            passagesSummary = "2 Samuel 2:1-11",
            primaryBookId = 10,
            primaryChapter = 2,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(10, "2 Samuel", 2))
        ),
        ReadingPlanDay(
            dayNumber = 247,
            title = "Guerra entre la casa de Saúl y David",
            passagesSummary = "2 Samuel 2:12-32",
            primaryBookId = 10,
            primaryChapter = 2,
            primaryVerse = 12,
            passages = listOf(PlanPassageSegment(10, "2 Samuel", 2))
        ),
        ReadingPlanDay(
            dayNumber = 248,
            title = "Abner se une a David",
            passagesSummary = "2 Samuel 3:1-21",
            primaryBookId = 10,
            primaryChapter = 3,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(10, "2 Samuel", 3))
        ),
        ReadingPlanDay(
            dayNumber = 249,
            title = "Joab mata a Abner",
            passagesSummary = "2 Samuel 3:22-39",
            primaryBookId = 10,
            primaryChapter = 3,
            primaryVerse = 22,
            passages = listOf(PlanPassageSegment(10, "2 Samuel", 3))
        ),
        ReadingPlanDay(
            dayNumber = 250,
            title = "Is-boset es asesinado",
            passagesSummary = "2 Samuel 4:1-12",
            primaryBookId = 10,
            primaryChapter = 4,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(10, "2 Samuel", 4))
        ),
        ReadingPlanDay(
            dayNumber = 251,
            title = "David es hecho rey de todo Israel",
            passagesSummary = "2 Samuel 5:1-5",
            primaryBookId = 10,
            primaryChapter = 5,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(10, "2 Samuel", 5))
        ),
        ReadingPlanDay(
            dayNumber = 252,
            title = "David conquista Jerusalén",
            passagesSummary = "2 Samuel 5:6-10",
            primaryBookId = 10,
            primaryChapter = 5,
            primaryVerse = 6,
            passages = listOf(PlanPassageSegment(10, "2 Samuel", 5))
        ),
        ReadingPlanDay(
            dayNumber = 253,
            title = "David lleva el arca a Jerusalén",
            passagesSummary = "2 Samuel 6:1-23",
            primaryBookId = 10,
            primaryChapter = 6,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(10, "2 Samuel", 6))
        ),
        ReadingPlanDay(
            dayNumber = 254,
            title = "Dios hace pacto con David",
            passagesSummary = "2 Samuel 7:1-29",
            primaryBookId = 10,
            primaryChapter = 7,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(10, "2 Samuel", 7))
        ),
        ReadingPlanDay(
            dayNumber = 255,
            title = "David derrota a sus enemigos",
            passagesSummary = "2 Samuel 8:1-18",
            primaryBookId = 10,
            primaryChapter = 8,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(10, "2 Samuel", 8))
        ),
        ReadingPlanDay(
            dayNumber = 256,
            title = "David muestra misericordia a Mefiboset",
            passagesSummary = "2 Samuel 9:1-13",
            primaryBookId = 10,
            primaryChapter = 9,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(10, "2 Samuel", 9))
        ),
        ReadingPlanDay(
            dayNumber = 257,
            title = "David derrota a los sirios y amonitas",
            passagesSummary = "2 Samuel 10:1-19",
            primaryBookId = 10,
            primaryChapter = 10,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(10, "2 Samuel", 10))
        ),
        ReadingPlanDay(
            dayNumber = 258,
            title = "David y Betsabé",
            passagesSummary = "2 Samuel 11:1-27",
            primaryBookId = 10,
            primaryChapter = 11,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(10, "2 Samuel", 11))
        ),
        ReadingPlanDay(
            dayNumber = 259,
            title = "Natán confronta a David",
            passagesSummary = "2 Samuel 12:1-25",
            primaryBookId = 10,
            primaryChapter = 12,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(10, "2 Samuel", 12))
        ),
        ReadingPlanDay(
            dayNumber = 260,
            title = "Absalón venga a Tamar",
            passagesSummary = "2 Samuel 13:1-39",
            primaryBookId = 10,
            primaryChapter = 13,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(10, "2 Samuel", 13))
        ),
        ReadingPlanDay(
            dayNumber = 261,
            title = "Absalón regresa",
            passagesSummary = "2 Samuel 14:1-33",
            primaryBookId = 10,
            primaryChapter = 14,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(10, "2 Samuel", 14))
        ),
        ReadingPlanDay(
            dayNumber = 262,
            title = "Absalón se rebela contra David",
            passagesSummary = "2 Samuel 15:1-37",
            primaryBookId = 10,
            primaryChapter = 15,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(10, "2 Samuel", 15))
        ),
        ReadingPlanDay(
            dayNumber = 263,
            title = "David huye de Jerusalén",
            passagesSummary = "2 Samuel 16:1-23",
            primaryBookId = 10,
            primaryChapter = 16,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(10, "2 Samuel", 16))
        ),
        ReadingPlanDay(
            dayNumber = 264,
            title = "Husai frustra el consejo de Ahitofel",
            passagesSummary = "2 Samuel 17:1-23",
            primaryBookId = 10,
            primaryChapter = 17,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(10, "2 Samuel", 17))
        ),
        ReadingPlanDay(
            dayNumber = 265,
            title = "Muerte de Absalón",
            passagesSummary = "2 Samuel 18:1-33",
            primaryBookId = 10,
            primaryChapter = 18,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(10, "2 Samuel", 18))
        ),
        ReadingPlanDay(
            dayNumber = 266,
            title = "David vuelve a Jerusalén",
            passagesSummary = "2 Samuel 19:1-43",
            primaryBookId = 10,
            primaryChapter = 19,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(10, "2 Samuel", 19))
        ),
        ReadingPlanDay(
            dayNumber = 267,
            title = "Rebelión de Seba",
            passagesSummary = "2 Samuel 20:1-26",
            primaryBookId = 10,
            primaryChapter = 20,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(10, "2 Samuel", 20))
        ),
        ReadingPlanDay(
            dayNumber = 268,
            title = "El censo de David",
            passagesSummary = "2 Samuel 24:1-25",
            primaryBookId = 10,
            primaryChapter = 24,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(10, "2 Samuel", 24))
        ),
        ReadingPlanDay(
            dayNumber = 269,
            title = "Salomón sucede a David",
            passagesSummary = "1 Reyes 1–2",
            primaryBookId = 11,
            primaryChapter = 1,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(11, "1 Reyes", 1), PlanPassageSegment(11, "1 Reyes", 2))
        ),
        ReadingPlanDay(
            dayNumber = 270,
            title = "Salomón pide sabiduría",
            passagesSummary = "1 Reyes 3:1-28",
            primaryBookId = 11,
            primaryChapter = 3,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(11, "1 Reyes", 3))
        ),
        ReadingPlanDay(
            dayNumber = 271,
            title = "Salomón juzga entre las dos mujeres",
            passagesSummary = "1 Reyes 3:16-28",
            primaryBookId = 11,
            primaryChapter = 3,
            primaryVerse = 16,
            passages = listOf(PlanPassageSegment(11, "1 Reyes", 3))
        ),
        ReadingPlanDay(
            dayNumber = 272,
            title = "Construcción del templo",
            passagesSummary = "1 Reyes 5–6",
            primaryBookId = 11,
            primaryChapter = 5,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(11, "1 Reyes", 5), PlanPassageSegment(11, "1 Reyes", 6))
        ),
        ReadingPlanDay(
            dayNumber = 273,
            title = "Dedicación del templo",
            passagesSummary = "1 Reyes 8:1-66",
            primaryBookId = 11,
            primaryChapter = 8,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(11, "1 Reyes", 8))
        ),
        ReadingPlanDay(
            dayNumber = 274,
            title = "La reina de Sabá visita a Salomón",
            passagesSummary = "1 Reyes 10:1-13",
            primaryBookId = 11,
            primaryChapter = 10,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(11, "1 Reyes", 10))
        ),
        ReadingPlanDay(
            dayNumber = 275,
            title = "La caída espiritual de Salomón",
            passagesSummary = "1 Reyes 11:1-43",
            primaryBookId = 11,
            primaryChapter = 11,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(11, "1 Reyes", 11))
        ),
        ReadingPlanDay(
            dayNumber = 276,
            title = "El reino se divide",
            passagesSummary = "1 Reyes 12:1-33",
            primaryBookId = 11,
            primaryChapter = 12,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(11, "1 Reyes", 12))
        ),
        ReadingPlanDay(
            dayNumber = 277,
            title = "Jeroboam establece becerros de oro",
            passagesSummary = "1 Reyes 12:25-33",
            primaryBookId = 11,
            primaryChapter = 12,
            primaryVerse = 25,
            passages = listOf(PlanPassageSegment(11, "1 Reyes", 12))
        ),
        ReadingPlanDay(
            dayNumber = 278,
            title = "El profeta de Judá contra el altar",
            passagesSummary = "1 Reyes 13:1-34",
            primaryBookId = 11,
            primaryChapter = 13,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(11, "1 Reyes", 13))
        ),
        ReadingPlanDay(
            dayNumber = 279,
            title = "Elías anuncia sequía",
            passagesSummary = "1 Reyes 17:1-7",
            primaryBookId = 11,
            primaryChapter = 17,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(11, "1 Reyes", 17))
        ),
        ReadingPlanDay(
            dayNumber = 280,
            title = "Elías y la viuda de Sarepta",
            passagesSummary = "1 Reyes 17:8-24",
            primaryBookId = 11,
            primaryChapter = 17,
            primaryVerse = 8,
            passages = listOf(PlanPassageSegment(11, "1 Reyes", 17))
        ),
        ReadingPlanDay(
            dayNumber = 281,
            title = "Elías y los profetas de Baal",
            passagesSummary = "1 Reyes 18:1-46",
            primaryBookId = 11,
            primaryChapter = 18,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(11, "1 Reyes", 18))
        ),
        ReadingPlanDay(
            dayNumber = 282,
            title = "Elías huye al desierto",
            passagesSummary = "1 Reyes 19:1-18",
            primaryBookId = 11,
            primaryChapter = 19,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(11, "1 Reyes", 19))
        ),
        ReadingPlanDay(
            dayNumber = 283,
            title = "Elías unge a Eliseo",
            passagesSummary = "1 Reyes 19:19-21",
            primaryBookId = 11,
            primaryChapter = 19,
            primaryVerse = 19,
            passages = listOf(PlanPassageSegment(11, "1 Reyes", 19))
        ),
        ReadingPlanDay(
            dayNumber = 284,
            title = "Nabot y su viña",
            passagesSummary = "1 Reyes 21:1-29",
            primaryBookId = 11,
            primaryChapter = 21,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(11, "1 Reyes", 21))
        ),
        ReadingPlanDay(
            dayNumber = 285,
            title = "Muerte de Acab",
            passagesSummary = "1 Reyes 22:1-40",
            primaryBookId = 11,
            primaryChapter = 22,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(11, "1 Reyes", 22))
        ),
        ReadingPlanDay(
            dayNumber = 286,
            title = "Elías es llevado al cielo",
            passagesSummary = "2 Reyes 2:1-18",
            primaryBookId = 12,
            primaryChapter = 2,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(12, "2 Reyes", 2))
        ),
        ReadingPlanDay(
            dayNumber = 287,
            title = "Eliseo y las aguas",
            passagesSummary = "2 Reyes 2:19-25",
            primaryBookId = 12,
            primaryChapter = 2,
            primaryVerse = 19,
            passages = listOf(PlanPassageSegment(12, "2 Reyes", 2))
        ),
        ReadingPlanDay(
            dayNumber = 288,
            title = "Eliseo multiplica el aceite de una viuda",
            passagesSummary = "2 Reyes 4:1-7",
            primaryBookId = 12,
            primaryChapter = 4,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(12, "2 Reyes", 4))
        ),
        ReadingPlanDay(
            dayNumber = 289,
            title = "Eliseo y la sunamita",
            passagesSummary = "2 Reyes 4:8-37",
            primaryBookId = 12,
            primaryChapter = 4,
            primaryVerse = 8,
            passages = listOf(PlanPassageSegment(12, "2 Reyes", 4))
        ),
        ReadingPlanDay(
            dayNumber = 290,
            title = "Eliseo alimenta a cien hombres",
            passagesSummary = "2 Reyes 4:38-44",
            primaryBookId = 12,
            primaryChapter = 4,
            primaryVerse = 38,
            passages = listOf(PlanPassageSegment(12, "2 Reyes", 4))
        ),
        ReadingPlanDay(
            dayNumber = 291,
            title = "Naamán es sanado de lepra",
            passagesSummary = "2 Reyes 5:1-27",
            primaryBookId = 12,
            primaryChapter = 5,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(12, "2 Reyes", 5))
        ),
        ReadingPlanDay(
            dayNumber = 292,
            title = "El hacha que flota",
            passagesSummary = "2 Reyes 6:1-7",
            primaryBookId = 12,
            primaryChapter = 6,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(12, "2 Reyes", 6))
        ),
        ReadingPlanDay(
            dayNumber = 293,
            title = "Eliseo y el ejército sirio",
            passagesSummary = "2 Reyes 6:8-23",
            primaryBookId = 12,
            primaryChapter = 6,
            primaryVerse = 8,
            passages = listOf(PlanPassageSegment(12, "2 Reyes", 6))
        ),
        ReadingPlanDay(
            dayNumber = 294,
            title = "Samaria es librada del hambre",
            passagesSummary = "2 Reyes 6:24–7:20",
            primaryBookId = 12,
            primaryChapter = 6,
            primaryVerse = 24,
            passages = listOf(PlanPassageSegment(12, "2 Reyes", 6), PlanPassageSegment(12, "2 Reyes", 7))
        ),
        ReadingPlanDay(
            dayNumber = 295,
            title = "Jehú destruye la casa de Acab",
            passagesSummary = "2 Reyes 9–10",
            primaryBookId = 12,
            primaryChapter = 9,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(12, "2 Reyes", 9), PlanPassageSegment(12, "2 Reyes", 10))
        ),
        ReadingPlanDay(
            dayNumber = 296,
            title = "Atalía usurpa el trono",
            passagesSummary = "2 Reyes 11:1-3",
            primaryBookId = 12,
            primaryChapter = 11,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(12, "2 Reyes", 11))
        ),
        ReadingPlanDay(
            dayNumber = 297,
            title = "Joás es coronado",
            passagesSummary = "2 Reyes 11:4-21",
            primaryBookId = 12,
            primaryChapter = 11,
            primaryVerse = 4,
            passages = listOf(PlanPassageSegment(12, "2 Reyes", 11))
        ),
        ReadingPlanDay(
            dayNumber = 298,
            title = "Caída del reino del norte",
            passagesSummary = "2 Reyes 17:1-41",
            primaryBookId = 12,
            primaryChapter = 17,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(12, "2 Reyes", 17))
        ),
        ReadingPlanDay(
            dayNumber = 299,
            title = "Ezequías y la invasión asiria",
            passagesSummary = "2 Reyes 18–19",
            primaryBookId = 12,
            primaryChapter = 18,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(12, "2 Reyes", 18), PlanPassageSegment(12, "2 Reyes", 19))
        ),
        ReadingPlanDay(
            dayNumber = 300,
            title = "El ángel destruye el ejército asirio",
            passagesSummary = "2 Reyes 19:35-37",
            primaryBookId = 12,
            primaryChapter = 19,
            primaryVerse = 35,
            passages = listOf(PlanPassageSegment(12, "2 Reyes", 19))
        ),
    )

    private fun part5(): List<ReadingPlanDay> = listOf(
        ReadingPlanDay(
            dayNumber = 301,
            title = "Ezequías enferma y es sanado",
            passagesSummary = "2 Reyes 20:1-11",
            primaryBookId = 12,
            primaryChapter = 20,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(12, "2 Reyes", 20))
        ),
        ReadingPlanDay(
            dayNumber = 302,
            title = "Manasés reina en Judá",
            passagesSummary = "2 Reyes 21:1-18",
            primaryBookId = 12,
            primaryChapter = 21,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(12, "2 Reyes", 21))
        ),
        ReadingPlanDay(
            dayNumber = 303,
            title = "Josías encuentra el libro de la Ley",
            passagesSummary = "2 Reyes 22:1-20",
            primaryBookId = 12,
            primaryChapter = 22,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(12, "2 Reyes", 22))
        ),
        ReadingPlanDay(
            dayNumber = 304,
            title = "Reforma de Josías",
            passagesSummary = "2 Reyes 23:1-27",
            primaryBookId = 12,
            primaryChapter = 23,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(12, "2 Reyes", 23))
        ),
        ReadingPlanDay(
            dayNumber = 305,
            title = "Caída de Jerusalén",
            passagesSummary = "2 Reyes 24–25",
            primaryBookId = 12,
            primaryChapter = 24,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(12, "2 Reyes", 24), PlanPassageSegment(12, "2 Reyes", 25))
        ),
        ReadingPlanDay(
            dayNumber = 306,
            title = "Destrucción del templo",
            passagesSummary = "2 Reyes 25:1-21",
            primaryBookId = 12,
            primaryChapter = 25,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(12, "2 Reyes", 25))
        ),
        ReadingPlanDay(
            dayNumber = 307,
            title = "Gedalías es asesinado",
            passagesSummary = "2 Reyes 25:22-26",
            primaryBookId = 12,
            primaryChapter = 25,
            primaryVerse = 22,
            passages = listOf(PlanPassageSegment(12, "2 Reyes", 25))
        ),
        ReadingPlanDay(
            dayNumber = 308,
            title = "Genealogías desde Adán",
            passagesSummary = "1 Crónicas 1–9",
            primaryBookId = 13,
            primaryChapter = 1,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(13, "1 Crónicas", 1), PlanPassageSegment(13, "1 Crónicas", 2), PlanPassageSegment(13, "1 Crónicas", 3), PlanPassageSegment(13, "1 Crónicas", 4), PlanPassageSegment(13, "1 Crónicas", 5), PlanPassageSegment(13, "1 Crónicas", 6), PlanPassageSegment(13, "1 Crónicas", 7), PlanPassageSegment(13, "1 Crónicas", 8), PlanPassageSegment(13, "1 Crónicas", 9))
        ),
        ReadingPlanDay(
            dayNumber = 309,
            title = "Muerte de Saúl",
            passagesSummary = "1 Crónicas 10:1-14",
            primaryBookId = 13,
            primaryChapter = 10,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(13, "1 Crónicas", 10))
        ),
        ReadingPlanDay(
            dayNumber = 310,
            title = "David llega al trono",
            passagesSummary = "1 Crónicas 11–12",
            primaryBookId = 13,
            primaryChapter = 11,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(13, "1 Crónicas", 11), PlanPassageSegment(13, "1 Crónicas", 12))
        ),
        ReadingPlanDay(
            dayNumber = 311,
            title = "David intenta trasladar el arca",
            passagesSummary = "1 Crónicas 13:1-14",
            primaryBookId = 13,
            primaryChapter = 13,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(13, "1 Crónicas", 13))
        ),
        ReadingPlanDay(
            dayNumber = 312,
            title = "Uza toca el arca y muere",
            passagesSummary = "1 Crónicas 13:9-14",
            primaryBookId = 13,
            primaryChapter = 13,
            primaryVerse = 9,
            passages = listOf(PlanPassageSegment(13, "1 Crónicas", 13))
        ),
        ReadingPlanDay(
            dayNumber = 313,
            title = "David lleva correctamente el arca",
            passagesSummary = "1 Crónicas 15–16",
            primaryBookId = 13,
            primaryChapter = 15,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(13, "1 Crónicas", 15), PlanPassageSegment(13, "1 Crónicas", 16))
        ),
        ReadingPlanDay(
            dayNumber = 314,
            title = "Pacto davídico",
            passagesSummary = "1 Crónicas 17:1-27",
            primaryBookId = 13,
            primaryChapter = 17,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(13, "1 Crónicas", 17))
        ),
        ReadingPlanDay(
            dayNumber = 315,
            title = "Guerras de David",
            passagesSummary = "1 Crónicas 18–20",
            primaryBookId = 13,
            primaryChapter = 18,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(13, "1 Crónicas", 18), PlanPassageSegment(13, "1 Crónicas", 19), PlanPassageSegment(13, "1 Crónicas", 20))
        ),
        ReadingPlanDay(
            dayNumber = 316,
            title = "David censará al pueblo",
            passagesSummary = "1 Crónicas 21:1-30",
            primaryBookId = 13,
            primaryChapter = 21,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(13, "1 Crónicas", 21))
        ),
        ReadingPlanDay(
            dayNumber = 317,
            title = "Preparativos para el templo",
            passagesSummary = "1 Crónicas 22–29",
            primaryBookId = 13,
            primaryChapter = 22,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(13, "1 Crónicas", 22), PlanPassageSegment(13, "1 Crónicas", 23), PlanPassageSegment(13, "1 Crónicas", 24), PlanPassageSegment(13, "1 Crónicas", 25), PlanPassageSegment(13, "1 Crónicas", 26), PlanPassageSegment(13, "1 Crónicas", 27), PlanPassageSegment(13, "1 Crónicas", 28), PlanPassageSegment(13, "1 Crónicas", 29))
        ),
        ReadingPlanDay(
            dayNumber = 318,
            title = "Salomón comienza a reinar",
            passagesSummary = "2 Crónicas 1:1-17",
            primaryBookId = 14,
            primaryChapter = 1,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(14, "2 Crónicas", 1))
        ),
        ReadingPlanDay(
            dayNumber = 319,
            title = "Construcción del templo",
            passagesSummary = "2 Crónicas 2–5",
            primaryBookId = 14,
            primaryChapter = 2,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(14, "2 Crónicas", 2), PlanPassageSegment(14, "2 Crónicas", 3), PlanPassageSegment(14, "2 Crónicas", 4), PlanPassageSegment(14, "2 Crónicas", 5))
        ),
        ReadingPlanDay(
            dayNumber = 320,
            title = "Gloria de Dios llena el templo",
            passagesSummary = "2 Crónicas 5:11-14",
            primaryBookId = 14,
            primaryChapter = 5,
            primaryVerse = 11,
            passages = listOf(PlanPassageSegment(14, "2 Crónicas", 5))
        ),
        ReadingPlanDay(
            dayNumber = 321,
            title = "Oración de Salomón",
            passagesSummary = "2 Crónicas 6:1-42",
            primaryBookId = 14,
            primaryChapter = 6,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(14, "2 Crónicas", 6))
        ),
        ReadingPlanDay(
            dayNumber = 322,
            title = "Fuego de Dios sobre el sacrificio",
            passagesSummary = "2 Crónicas 7:1-3",
            primaryBookId = 14,
            primaryChapter = 7,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(14, "2 Crónicas", 7))
        ),
        ReadingPlanDay(
            dayNumber = 323,
            title = "Reina de Sabá",
            passagesSummary = "2 Crónicas 9:1-12",
            primaryBookId = 14,
            primaryChapter = 9,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(14, "2 Crónicas", 9))
        ),
        ReadingPlanDay(
            dayNumber = 324,
            title = "División del reino",
            passagesSummary = "2 Crónicas 10",
            primaryBookId = 14,
            primaryChapter = 10,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(14, "2 Crónicas", 10))
        ),
        ReadingPlanDay(
            dayNumber = 325,
            title = "Asa y la reforma de Judá",
            passagesSummary = "2 Crónicas 14–16",
            primaryBookId = 14,
            primaryChapter = 14,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(14, "2 Crónicas", 14), PlanPassageSegment(14, "2 Crónicas", 15), PlanPassageSegment(14, "2 Crónicas", 16))
        ),
        ReadingPlanDay(
            dayNumber = 326,
            title = "Josafat y la victoria sobrenatural",
            passagesSummary = "2 Crónicas 20:1-30",
            primaryBookId = 14,
            primaryChapter = 20,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(14, "2 Crónicas", 20))
        ),
        ReadingPlanDay(
            dayNumber = 327,
            title = "Ezequías restaura el culto",
            passagesSummary = "2 Crónicas 29–31",
            primaryBookId = 14,
            primaryChapter = 29,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(14, "2 Crónicas", 29), PlanPassageSegment(14, "2 Crónicas", 30), PlanPassageSegment(14, "2 Crónicas", 31))
        ),
        ReadingPlanDay(
            dayNumber = 328,
            title = "Senaquerib invade Judá",
            passagesSummary = "2 Crónicas 32:1-23",
            primaryBookId = 14,
            primaryChapter = 32,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(14, "2 Crónicas", 32))
        ),
        ReadingPlanDay(
            dayNumber = 329,
            title = "Manasés se humilla ante Dios",
            passagesSummary = "2 Crónicas 33:1-20",
            primaryBookId = 14,
            primaryChapter = 33,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(14, "2 Crónicas", 33))
        ),
        ReadingPlanDay(
            dayNumber = 330,
            title = "Reforma de Josías",
            passagesSummary = "2 Crónicas 34–35",
            primaryBookId = 14,
            primaryChapter = 34,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(14, "2 Crónicas", 34), PlanPassageSegment(14, "2 Crónicas", 35))
        ),
        ReadingPlanDay(
            dayNumber = 331,
            title = "Caída de Jerusalén",
            passagesSummary = "2 Crónicas 36:1-21",
            primaryBookId = 14,
            primaryChapter = 36,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(14, "2 Crónicas", 36))
        ),
        ReadingPlanDay(
            dayNumber = 332,
            title = "Ciro permite el regreso",
            passagesSummary = "2 Crónicas 36:22-23",
            primaryBookId = 14,
            primaryChapter = 36,
            primaryVerse = 22,
            passages = listOf(PlanPassageSegment(14, "2 Crónicas", 36))
        ),
        ReadingPlanDay(
            dayNumber = 333,
            title = "Decreto de Ciro",
            passagesSummary = "Esdras 1:1-11",
            primaryBookId = 15,
            primaryChapter = 1,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(15, "Esdras", 1))
        ),
        ReadingPlanDay(
            dayNumber = 334,
            title = "Primer regreso del exilio",
            passagesSummary = "Esdras 2",
            primaryBookId = 15,
            primaryChapter = 2,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(15, "Esdras", 2))
        ),
        ReadingPlanDay(
            dayNumber = 335,
            title = "Reconstrucción del altar",
            passagesSummary = "Esdras 3:1-6",
            primaryBookId = 15,
            primaryChapter = 3,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(15, "Esdras", 3))
        ),
        ReadingPlanDay(
            dayNumber = 336,
            title = "Fundamentos del templo",
            passagesSummary = "Esdras 3:7-13",
            primaryBookId = 15,
            primaryChapter = 3,
            primaryVerse = 7,
            passages = listOf(PlanPassageSegment(15, "Esdras", 3))
        ),
        ReadingPlanDay(
            dayNumber = 337,
            title = "Oposición a la reconstrucción",
            passagesSummary = "Esdras 4",
            primaryBookId = 15,
            primaryChapter = 4,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(15, "Esdras", 4))
        ),
        ReadingPlanDay(
            dayNumber = 338,
            title = "Hageo y Zacarías animan al pueblo",
            passagesSummary = "Esdras 5:1-2",
            primaryBookId = 15,
            primaryChapter = 5,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(15, "Esdras", 5))
        ),
        ReadingPlanDay(
            dayNumber = 339,
            title = "El templo es terminado",
            passagesSummary = "Esdras 6:1-22",
            primaryBookId = 15,
            primaryChapter = 6,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(15, "Esdras", 6))
        ),
        ReadingPlanDay(
            dayNumber = 340,
            title = "Esdras llega a Jerusalén",
            passagesSummary = "Esdras 7:1-28",
            primaryBookId = 15,
            primaryChapter = 7,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(15, "Esdras", 7))
        ),
        ReadingPlanDay(
            dayNumber = 341,
            title = "Esdras confiesa los pecados del pueblo",
            passagesSummary = "Esdras 9:1-15",
            primaryBookId = 15,
            primaryChapter = 9,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(15, "Esdras", 9))
        ),
        ReadingPlanDay(
            dayNumber = 342,
            title = "Reforma matrimonial",
            passagesSummary = "Esdras 10:1-44",
            primaryBookId = 15,
            primaryChapter = 10,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(15, "Esdras", 10))
        ),
        ReadingPlanDay(
            dayNumber = 343,
            title = "Nehemías recibe noticia de Jerusalén",
            passagesSummary = "Nehemías 1:1-11",
            primaryBookId = 16,
            primaryChapter = 1,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(16, "Nehemías", 1))
        ),
        ReadingPlanDay(
            dayNumber = 344,
            title = "Nehemías pide permiso al rey",
            passagesSummary = "Nehemías 2:1-8",
            primaryBookId = 16,
            primaryChapter = 2,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(16, "Nehemías", 2))
        ),
        ReadingPlanDay(
            dayNumber = 345,
            title = "Nehemías inspecciona los muros",
            passagesSummary = "Nehemías 2:9-20",
            primaryBookId = 16,
            primaryChapter = 2,
            primaryVerse = 9,
            passages = listOf(PlanPassageSegment(16, "Nehemías", 2))
        ),
        ReadingPlanDay(
            dayNumber = 346,
            title = "Reconstrucción de los muros",
            passagesSummary = "Nehemías 3–4",
            primaryBookId = 16,
            primaryChapter = 3,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(16, "Nehemías", 3), PlanPassageSegment(16, "Nehemías", 4))
        ),
        ReadingPlanDay(
            dayNumber = 347,
            title = "Nehemías enfrenta la injusticia económica",
            passagesSummary = "Nehemías 5:1-19",
            primaryBookId = 16,
            primaryChapter = 5,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(16, "Nehemías", 5))
        ),
        ReadingPlanDay(
            dayNumber = 348,
            title = "Conspiraciones contra Nehemías",
            passagesSummary = "Nehemías 6:1-19",
            primaryBookId = 16,
            primaryChapter = 6,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(16, "Nehemías", 6))
        ),
        ReadingPlanDay(
            dayNumber = 349,
            title = "El muro es terminado",
            passagesSummary = "Nehemías 6:15-19",
            primaryBookId = 16,
            primaryChapter = 6,
            primaryVerse = 15,
            passages = listOf(PlanPassageSegment(16, "Nehemías", 6))
        ),
        ReadingPlanDay(
            dayNumber = 350,
            title = "Lectura pública de la Ley",
            passagesSummary = "Nehemías 8:1-18",
            primaryBookId = 16,
            primaryChapter = 8,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(16, "Nehemías", 8))
        ),
        ReadingPlanDay(
            dayNumber = 351,
            title = "Confesión nacional",
            passagesSummary = "Nehemías 9:1-38",
            primaryBookId = 16,
            primaryChapter = 9,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(16, "Nehemías", 9))
        ),
        ReadingPlanDay(
            dayNumber = 352,
            title = "Dedicación del muro",
            passagesSummary = "Nehemías 12:27-47",
            primaryBookId = 16,
            primaryChapter = 12,
            primaryVerse = 27,
            passages = listOf(PlanPassageSegment(16, "Nehemías", 12))
        ),
        ReadingPlanDay(
            dayNumber = 353,
            title = "Nehemías reforma nuevamente Jerusalén",
            passagesSummary = "Nehemías 13:1-31",
            primaryBookId = 16,
            primaryChapter = 13,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(16, "Nehemías", 13))
        ),
        ReadingPlanDay(
            dayNumber = 354,
            title = "La reina Vasti es destituida",
            passagesSummary = "Ester 1:1-22",
            primaryBookId = 17,
            primaryChapter = 1,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(17, "Ester", 1))
        ),
        ReadingPlanDay(
            dayNumber = 355,
            title = "Ester es escogida reina",
            passagesSummary = "Ester 2:1-18",
            primaryBookId = 17,
            primaryChapter = 2,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(17, "Ester", 2))
        ),
        ReadingPlanDay(
            dayNumber = 356,
            title = "Mardoqueo descubre una conspiración",
            passagesSummary = "Ester 2:19-23",
            primaryBookId = 17,
            primaryChapter = 2,
            primaryVerse = 19,
            passages = listOf(PlanPassageSegment(17, "Ester", 2))
        ),
        ReadingPlanDay(
            dayNumber = 357,
            title = "Amán conspira contra los judíos",
            passagesSummary = "Ester 3:1-15",
            primaryBookId = 17,
            primaryChapter = 3,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(17, "Ester", 3))
        ),
        ReadingPlanDay(
            dayNumber = 358,
            title = "Mardoqueo pide ayuda a Ester",
            passagesSummary = "Ester 4:1-17",
            primaryBookId = 17,
            primaryChapter = 4,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(17, "Ester", 4))
        ),
        ReadingPlanDay(
            dayNumber = 359,
            title = "Ester arriesga su vida ante el rey",
            passagesSummary = "Ester 5:1-8",
            primaryBookId = 17,
            primaryChapter = 5,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(17, "Ester", 5))
        ),
        ReadingPlanDay(
            dayNumber = 360,
            title = "Amán prepara la horca para Mardoqueo",
            passagesSummary = "Ester 5:9-14",
            primaryBookId = 17,
            primaryChapter = 5,
            primaryVerse = 9,
            passages = listOf(PlanPassageSegment(17, "Ester", 5))
        ),
        ReadingPlanDay(
            dayNumber = 361,
            title = "Mardoqueo es honrado",
            passagesSummary = "Ester 6:1-14",
            primaryBookId = 17,
            primaryChapter = 6,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(17, "Ester", 6))
        ),
        ReadingPlanDay(
            dayNumber = 362,
            title = "Ester denuncia a Amán",
            passagesSummary = "Ester 7:1-10",
            primaryBookId = 17,
            primaryChapter = 7,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(17, "Ester", 7))
        ),
        ReadingPlanDay(
            dayNumber = 363,
            title = "Los judíos reciben permiso para defenderse",
            passagesSummary = "Ester 8:1-17",
            primaryBookId = 17,
            primaryChapter = 8,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(17, "Ester", 8))
        ),
        ReadingPlanDay(
            dayNumber = 364,
            title = "Victoria de los judíos",
            passagesSummary = "Ester 9:1-32",
            primaryBookId = 17,
            primaryChapter = 9,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(17, "Ester", 9))
        ),
        ReadingPlanDay(
            dayNumber = 365,
            title = "Institución de Purim",
            passagesSummary = "Ester 9:20-32",
            primaryBookId = 17,
            primaryChapter = 9,
            primaryVerse = 20,
            passages = listOf(PlanPassageSegment(17, "Ester", 9))
        ),
        ReadingPlanDay(
            dayNumber = 366,
            title = "Satanás acusa a Job",
            passagesSummary = "Job 1:1-12",
            primaryBookId = 18,
            primaryChapter = 1,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(18, "Job", 1))
        ),
        ReadingPlanDay(
            dayNumber = 367,
            title = "Job pierde sus posesiones e hijos",
            passagesSummary = "Job 1:13-22",
            primaryBookId = 18,
            primaryChapter = 1,
            primaryVerse = 13,
            passages = listOf(PlanPassageSegment(18, "Job", 1))
        ),
        ReadingPlanDay(
            dayNumber = 368,
            title = "Job es afligido",
            passagesSummary = "Job 2:1-13",
            primaryBookId = 18,
            primaryChapter = 2,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(18, "Job", 2))
        ),
        ReadingPlanDay(
            dayNumber = 369,
            title = "Job y sus tres amigos",
            passagesSummary = "Job 3–31",
            primaryBookId = 18,
            primaryChapter = 3,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(18, "Job", 3), PlanPassageSegment(18, "Job", 4), PlanPassageSegment(18, "Job", 5), PlanPassageSegment(18, "Job", 6), PlanPassageSegment(18, "Job", 7), PlanPassageSegment(18, "Job", 8), PlanPassageSegment(18, "Job", 9), PlanPassageSegment(18, "Job", 10), PlanPassageSegment(18, "Job", 11), PlanPassageSegment(18, "Job", 12))
        ),
        ReadingPlanDay(
            dayNumber = 370,
            title = "Eliú habla",
            passagesSummary = "Job 32–37",
            primaryBookId = 18,
            primaryChapter = 32,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(18, "Job", 32), PlanPassageSegment(18, "Job", 33), PlanPassageSegment(18, "Job", 34), PlanPassageSegment(18, "Job", 35), PlanPassageSegment(18, "Job", 36), PlanPassageSegment(18, "Job", 37))
        ),
        ReadingPlanDay(
            dayNumber = 371,
            title = "Dios responde a Job",
            passagesSummary = "Job 38–41",
            primaryBookId = 18,
            primaryChapter = 38,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(18, "Job", 38), PlanPassageSegment(18, "Job", 39), PlanPassageSegment(18, "Job", 40), PlanPassageSegment(18, "Job", 41))
        ),
        ReadingPlanDay(
            dayNumber = 372,
            title = "Job se humilla",
            passagesSummary = "Job 42:1-6",
            primaryBookId = 18,
            primaryChapter = 42,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(18, "Job", 42))
        ),
        ReadingPlanDay(
            dayNumber = 373,
            title = "Dios restaura a Job",
            passagesSummary = "Job 42:7-17",
            primaryBookId = 18,
            primaryChapter = 42,
            primaryVerse = 7,
            passages = listOf(PlanPassageSegment(18, "Job", 42))
        ),
        ReadingPlanDay(
            dayNumber = 374,
            title = "David compone cánticos en diferentes circunstancias",
            passagesSummary = "Salmos 3; 18; 34; 51; 52; 54; 56; 57; 59; 63; 142, entre otros.",
            primaryBookId = 19,
            primaryChapter = 3,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(19, "Salmos", 3), PlanPassageSegment(19, "Salmos", 18), PlanPassageSegment(19, "Salmos", 34), PlanPassageSegment(19, "Salmos", 51), PlanPassageSegment(19, "Salmos", 52), PlanPassageSegment(19, "Salmos", 54), PlanPassageSegment(19, "Salmos", 56), PlanPassageSegment(19, "Salmos", 57), PlanPassageSegment(19, "Salmos", 59), PlanPassageSegment(19, "Salmos", 63))
        ),
        ReadingPlanDay(
            dayNumber = 375,
            title = "Salmo de David después de su pecado con Betsabé",
            passagesSummary = "Salmo 51",
            primaryBookId = 19,
            primaryChapter = 51,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(19, "Salmo", 51))
        ),
    )

    private fun part6(): List<ReadingPlanDay> = listOf(
        ReadingPlanDay(
            dayNumber = 376,
            title = "Salmo relacionado con su huida de Absalón",
            passagesSummary = "Salmo 3",
            primaryBookId = 19,
            primaryChapter = 3,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(19, "Salmo", 3))
        ),
        ReadingPlanDay(
            dayNumber = 377,
            title = "Salmo relacionado con la persecución de Saúl",
            passagesSummary = "Salmos 7; 18; 34; 52; 54; 56; 57; 59",
            primaryBookId = 19,
            primaryChapter = 7,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(19, "Salmos", 7), PlanPassageSegment(19, "Salmos", 18), PlanPassageSegment(19, "Salmos", 34), PlanPassageSegment(19, "Salmos", 52), PlanPassageSegment(19, "Salmos", 54), PlanPassageSegment(19, "Salmos", 56), PlanPassageSegment(19, "Salmos", 57), PlanPassageSegment(19, "Salmos", 59))
        ),
        ReadingPlanDay(
            dayNumber = 378,
            title = "El llamamiento de Isaías",
            passagesSummary = "Isaías 6:1-13",
            primaryBookId = 23,
            primaryChapter = 6,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(23, "Isaías", 6))
        ),
        ReadingPlanDay(
            dayNumber = 379,
            title = "Isaías anuncia el nacimiento del Mesías",
            passagesSummary = "Isaías 7:14; 9:1-7",
            primaryBookId = 23,
            primaryChapter = 7,
            primaryVerse = 14,
            passages = listOf(PlanPassageSegment(23, "Isaías", 7), PlanPassageSegment(23, "Isaías", 9))
        ),
        ReadingPlanDay(
            dayNumber = 380,
            title = "Profecía sobre Emanuel",
            passagesSummary = "Isaías 7:10-16",
            primaryBookId = 23,
            primaryChapter = 7,
            primaryVerse = 10,
            passages = listOf(PlanPassageSegment(23, "Isaías", 7))
        ),
        ReadingPlanDay(
            dayNumber = 381,
            title = "Profecía del Siervo sufriente",
            passagesSummary = "Isaías 52:13–53:12",
            primaryBookId = 23,
            primaryChapter = 52,
            primaryVerse = 13,
            passages = listOf(PlanPassageSegment(23, "Isaías", 52), PlanPassageSegment(23, "Isaías", 53))
        ),
        ReadingPlanDay(
            dayNumber = 382,
            title = "Isaías profetiza contra las naciones",
            passagesSummary = "Isaías 13–23",
            primaryBookId = 23,
            primaryChapter = 13,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(23, "Isaías", 13), PlanPassageSegment(23, "Isaías", 14), PlanPassageSegment(23, "Isaías", 15), PlanPassageSegment(23, "Isaías", 16), PlanPassageSegment(23, "Isaías", 17), PlanPassageSegment(23, "Isaías", 18), PlanPassageSegment(23, "Isaías", 19), PlanPassageSegment(23, "Isaías", 20), PlanPassageSegment(23, "Isaías", 21), PlanPassageSegment(23, "Isaías", 22))
        ),
        ReadingPlanDay(
            dayNumber = 383,
            title = "Senaquerib amenaza Jerusalén",
            passagesSummary = "Isaías 36:1-22",
            primaryBookId = 23,
            primaryChapter = 36,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(23, "Isaías", 36))
        ),
        ReadingPlanDay(
            dayNumber = 384,
            title = "Ezequías ora por liberación",
            passagesSummary = "Isaías 37:1-38",
            primaryBookId = 23,
            primaryChapter = 37,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(23, "Isaías", 37))
        ),
        ReadingPlanDay(
            dayNumber = 385,
            title = "El ejército asirio es destruido",
            passagesSummary = "Isaías 37:36-38",
            primaryBookId = 23,
            primaryChapter = 37,
            primaryVerse = 36,
            passages = listOf(PlanPassageSegment(23, "Isaías", 37))
        ),
        ReadingPlanDay(
            dayNumber = 386,
            title = "Ezequías enferma y es sanado",
            passagesSummary = "Isaías 38:1-22",
            primaryBookId = 23,
            primaryChapter = 38,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(23, "Isaías", 38))
        ),
        ReadingPlanDay(
            dayNumber = 387,
            title = "Los enviados de Babilonia visitan a Ezequías",
            passagesSummary = "Isaías 39:1-8",
            primaryBookId = 23,
            primaryChapter = 39,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(23, "Isaías", 39))
        ),
        ReadingPlanDay(
            dayNumber = 388,
            title = "Nuevos cielos y nueva tierra",
            passagesSummary = "Isaías 65:17-25",
            primaryBookId = 23,
            primaryChapter = 65,
            primaryVerse = 17,
            passages = listOf(PlanPassageSegment(23, "Isaías", 65))
        ),
        ReadingPlanDay(
            dayNumber = 389,
            title = "La futura gloria de Jerusalén",
            passagesSummary = "Isaías 66:1-24",
            primaryBookId = 23,
            primaryChapter = 66,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(23, "Isaías", 66))
        ),
        ReadingPlanDay(
            dayNumber = 390,
            title = "El llamamiento de Jeremías",
            passagesSummary = "Jeremías 1:1-19",
            primaryBookId = 24,
            primaryChapter = 1,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(24, "Jeremías", 1))
        ),
        ReadingPlanDay(
            dayNumber = 391,
            title = "Jeremías predica en el templo",
            passagesSummary = "Jeremías 7:1-34",
            primaryBookId = 24,
            primaryChapter = 7,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(24, "Jeremías", 7))
        ),
        ReadingPlanDay(
            dayNumber = 392,
            title = "Jeremías compra un campo",
            passagesSummary = "Jeremías 32:1-44",
            primaryBookId = 24,
            primaryChapter = 32,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(24, "Jeremías", 32))
        ),
        ReadingPlanDay(
            dayNumber = 393,
            title = "Jeremías es perseguido",
            passagesSummary = "Jeremías 26–29",
            primaryBookId = 24,
            primaryChapter = 26,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(24, "Jeremías", 26), PlanPassageSegment(24, "Jeremías", 27), PlanPassageSegment(24, "Jeremías", 28), PlanPassageSegment(24, "Jeremías", 29))
        ),
        ReadingPlanDay(
            dayNumber = 394,
            title = "Jeremías es encarcelado",
            passagesSummary = "Jeremías 32–33",
            primaryBookId = 24,
            primaryChapter = 32,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(24, "Jeremías", 32), PlanPassageSegment(24, "Jeremías", 33))
        ),
        ReadingPlanDay(
            dayNumber = 395,
            title = "Jeremías es arrojado al pozo",
            passagesSummary = "Jeremías 38:1-13",
            primaryBookId = 24,
            primaryChapter = 38,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(24, "Jeremías", 38))
        ),
        ReadingPlanDay(
            dayNumber = 396,
            title = "Jerusalén cae ante Babilonia",
            passagesSummary = "Jeremías 39:1-18",
            primaryBookId = 24,
            primaryChapter = 39,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(24, "Jeremías", 39))
        ),
        ReadingPlanDay(
            dayNumber = 397,
            title = "Jeremías es liberado",
            passagesSummary = "Jeremías 40:1-6",
            primaryBookId = 24,
            primaryChapter = 40,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(24, "Jeremías", 40))
        ),
        ReadingPlanDay(
            dayNumber = 398,
            title = "Gedalías es asesinado",
            passagesSummary = "Jeremías 40–41",
            primaryBookId = 24,
            primaryChapter = 40,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(24, "Jeremías", 40), PlanPassageSegment(24, "Jeremías", 41))
        ),
        ReadingPlanDay(
            dayNumber = 399,
            title = "El remanente huye a Egipto",
            passagesSummary = "Jeremías 42–44",
            primaryBookId = 24,
            primaryChapter = 42,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(24, "Jeremías", 42), PlanPassageSegment(24, "Jeremías", 43), PlanPassageSegment(24, "Jeremías", 44))
        ),
        ReadingPlanDay(
            dayNumber = 400,
            title = "Profecía de los setenta años",
            passagesSummary = "Jeremías 25:11-12; 29:10",
            primaryBookId = 24,
            primaryChapter = 25,
            primaryVerse = 11,
            passages = listOf(PlanPassageSegment(24, "Jeremías", 25), PlanPassageSegment(24, "Jeremías", 29))
        ),
        ReadingPlanDay(
            dayNumber = 401,
            title = "Lamento por la destrucción de Jerusalén",
            passagesSummary = "Lamentaciones 1–5",
            primaryBookId = 25,
            primaryChapter = 1,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(25, "Lamentaciones", 1), PlanPassageSegment(25, "Lamentaciones", 2), PlanPassageSegment(25, "Lamentaciones", 3), PlanPassageSegment(25, "Lamentaciones", 4), PlanPassageSegment(25, "Lamentaciones", 5))
        ),
        ReadingPlanDay(
            dayNumber = 402,
            title = "Ezequiel ve la gloria de Dios",
            passagesSummary = "Ezequiel 1:1-28",
            primaryBookId = 26,
            primaryChapter = 1,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(26, "Ezequiel", 1))
        ),
        ReadingPlanDay(
            dayNumber = 403,
            title = "Ezequiel recibe su comisión profética",
            passagesSummary = "Ezequiel 2–3",
            primaryBookId = 26,
            primaryChapter = 2,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(26, "Ezequiel", 2), PlanPassageSegment(26, "Ezequiel", 3))
        ),
        ReadingPlanDay(
            dayNumber = 404,
            title = "La gloria de Dios abandona el templo",
            passagesSummary = "Ezequiel 8–11",
            primaryBookId = 26,
            primaryChapter = 8,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(26, "Ezequiel", 8), PlanPassageSegment(26, "Ezequiel", 9), PlanPassageSegment(26, "Ezequiel", 10), PlanPassageSegment(26, "Ezequiel", 11))
        ),
        ReadingPlanDay(
            dayNumber = 405,
            title = "Ezequiel representa el sitio de Jerusalén",
            passagesSummary = "Ezequiel 4–5",
            primaryBookId = 26,
            primaryChapter = 4,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(26, "Ezequiel", 4), PlanPassageSegment(26, "Ezequiel", 5))
        ),
        ReadingPlanDay(
            dayNumber = 406,
            title = "La esposa de Ezequiel muere",
            passagesSummary = "Ezequiel 24:15-27",
            primaryBookId = 26,
            primaryChapter = 24,
            primaryVerse = 15,
            passages = listOf(PlanPassageSegment(26, "Ezequiel", 24))
        ),
        ReadingPlanDay(
            dayNumber = 407,
            title = "El valle de los huesos secos",
            passagesSummary = "Ezequiel 37:1-14",
            primaryBookId = 26,
            primaryChapter = 37,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(26, "Ezequiel", 37))
        ),
        ReadingPlanDay(
            dayNumber = 408,
            title = "Las dos varas representan la reunificación",
            passagesSummary = "Ezequiel 37:15-28",
            primaryBookId = 26,
            primaryChapter = 37,
            primaryVerse = 15,
            passages = listOf(PlanPassageSegment(26, "Ezequiel", 37))
        ),
        ReadingPlanDay(
            dayNumber = 409,
            title = "Gog y Magog",
            passagesSummary = "Ezequiel 38–39",
            primaryBookId = 26,
            primaryChapter = 38,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(26, "Ezequiel", 38), PlanPassageSegment(26, "Ezequiel", 39))
        ),
        ReadingPlanDay(
            dayNumber = 410,
            title = "Visión del templo futuro",
            passagesSummary = "Ezequiel 40–48",
            primaryBookId = 26,
            primaryChapter = 40,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(26, "Ezequiel", 40), PlanPassageSegment(26, "Ezequiel", 41), PlanPassageSegment(26, "Ezequiel", 42), PlanPassageSegment(26, "Ezequiel", 43), PlanPassageSegment(26, "Ezequiel", 44), PlanPassageSegment(26, "Ezequiel", 45), PlanPassageSegment(26, "Ezequiel", 46), PlanPassageSegment(26, "Ezequiel", 47), PlanPassageSegment(26, "Ezequiel", 48))
        ),
        ReadingPlanDay(
            dayNumber = 411,
            title = "Daniel y sus amigos llegan a Babilonia",
            passagesSummary = "Daniel 1:1-21",
            primaryBookId = 27,
            primaryChapter = 1,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(27, "Daniel", 1))
        ),
        ReadingPlanDay(
            dayNumber = 412,
            title = "Daniel interpreta el sueño de Nabucodonosor",
            passagesSummary = "Daniel 2:1-49",
            primaryBookId = 27,
            primaryChapter = 2,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(27, "Daniel", 2))
        ),
        ReadingPlanDay(
            dayNumber = 413,
            title = "Los tres hebreos y el horno de fuego",
            passagesSummary = "Daniel 3:1-30",
            primaryBookId = 27,
            primaryChapter = 3,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(27, "Daniel", 3))
        ),
        ReadingPlanDay(
            dayNumber = 414,
            title = "Nabucodonosor se vuelve como una bestia",
            passagesSummary = "Daniel 4:1-37",
            primaryBookId = 27,
            primaryChapter = 4,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(27, "Daniel", 4))
        ),
        ReadingPlanDay(
            dayNumber = 415,
            title = "La escritura en la pared",
            passagesSummary = "Daniel 5:1-31",
            primaryBookId = 27,
            primaryChapter = 5,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(27, "Daniel", 5))
        ),
        ReadingPlanDay(
            dayNumber = 416,
            title = "Daniel en el foso de los leones",
            passagesSummary = "Daniel 6:1-28",
            primaryBookId = 27,
            primaryChapter = 6,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(27, "Daniel", 6))
        ),
        ReadingPlanDay(
            dayNumber = 417,
            title = "Visión de las cuatro bestias",
            passagesSummary = "Daniel 7:1-28",
            primaryBookId = 27,
            primaryChapter = 7,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(27, "Daniel", 7))
        ),
        ReadingPlanDay(
            dayNumber = 418,
            title = "Visión del carnero y macho cabrío",
            passagesSummary = "Daniel 8:1-27",
            primaryBookId = 27,
            primaryChapter = 8,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(27, "Daniel", 8))
        ),
        ReadingPlanDay(
            dayNumber = 419,
            title = "Daniel ora por Jerusalén",
            passagesSummary = "Daniel 9:1-27",
            primaryBookId = 27,
            primaryChapter = 9,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(27, "Daniel", 9))
        ),
        ReadingPlanDay(
            dayNumber = 420,
            title = "Visión final de Daniel",
            passagesSummary = "Daniel 10–12",
            primaryBookId = 27,
            primaryChapter = 10,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(27, "Daniel", 10), PlanPassageSegment(27, "Daniel", 11), PlanPassageSegment(27, "Daniel", 12))
        ),
        ReadingPlanDay(
            dayNumber = 421,
            title = "Oseas se casa con Gomer",
            passagesSummary = "Oseas 1:1-11",
            primaryBookId = 28,
            primaryChapter = 1,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(28, "Oseas", 1))
        ),
        ReadingPlanDay(
            dayNumber = 422,
            title = "Los hijos de Oseas reciben nombres proféticos",
            passagesSummary = "Oseas 1–2",
            primaryBookId = 28,
            primaryChapter = 1,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(28, "Oseas", 1), PlanPassageSegment(28, "Oseas", 2))
        ),
        ReadingPlanDay(
            dayNumber = 423,
            title = "Oseas redime nuevamente a su esposa",
            passagesSummary = "Oseas 3:1-5",
            primaryBookId = 28,
            primaryChapter = 3,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(28, "Oseas", 3))
        ),
        ReadingPlanDay(
            dayNumber = 424,
            title = "Joel anuncia el día de Jehová",
            passagesSummary = "Joel 1–3",
            primaryBookId = 29,
            primaryChapter = 1,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(29, "Joel", 1), PlanPassageSegment(29, "Joel", 2), PlanPassageSegment(29, "Joel", 3))
        ),
        ReadingPlanDay(
            dayNumber = 425,
            title = "Promesa del derramamiento del Espíritu",
            passagesSummary = "Joel 2:28-32",
            primaryBookId = 29,
            primaryChapter = 2,
            primaryVerse = 28,
            passages = listOf(PlanPassageSegment(29, "Joel", 2))
        ),
        ReadingPlanDay(
            dayNumber = 426,
            title = "Amós recibe visiones",
            passagesSummary = "Amós 7–9",
            primaryBookId = 30,
            primaryChapter = 7,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(30, "Amós", 7), PlanPassageSegment(30, "Amós", 8), PlanPassageSegment(30, "Amós", 9))
        ),
        ReadingPlanDay(
            dayNumber = 427,
            title = "Amós confronta a Amasías",
            passagesSummary = "Amós 7:10-17",
            primaryBookId = 30,
            primaryChapter = 7,
            primaryVerse = 10,
            passages = listOf(PlanPassageSegment(30, "Amós", 7))
        ),
        ReadingPlanDay(
            dayNumber = 428,
            title = "Jonás huye de Dios",
            passagesSummary = "Jonás 1:1-17",
            primaryBookId = 32,
            primaryChapter = 1,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(32, "Jonás", 1))
        ),
        ReadingPlanDay(
            dayNumber = 429,
            title = "Jonás es arrojado al mar",
            passagesSummary = "Jonás 1:11-17",
            primaryBookId = 32,
            primaryChapter = 1,
            primaryVerse = 11,
            passages = listOf(PlanPassageSegment(32, "Jonás", 1))
        ),
        ReadingPlanDay(
            dayNumber = 430,
            title = "Jonás dentro del gran pez",
            passagesSummary = "Jonás 1:17–2:10",
            primaryBookId = 32,
            primaryChapter = 1,
            primaryVerse = 17,
            passages = listOf(PlanPassageSegment(32, "Jonás", 1), PlanPassageSegment(32, "Jonás", 2))
        ),
        ReadingPlanDay(
            dayNumber = 431,
            title = "Jonás predica en Nínive",
            passagesSummary = "Jonás 3:1-10",
            primaryBookId = 32,
            primaryChapter = 3,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(32, "Jonás", 3))
        ),
        ReadingPlanDay(
            dayNumber = 432,
            title = "Nínive se arrepiente",
            passagesSummary = "Jonás 3:5-10",
            primaryBookId = 32,
            primaryChapter = 3,
            primaryVerse = 5,
            passages = listOf(PlanPassageSegment(32, "Jonás", 3))
        ),
        ReadingPlanDay(
            dayNumber = 433,
            title = "Jonás se enoja con Dios",
            passagesSummary = "Jonás 4:1-11",
            primaryBookId = 32,
            primaryChapter = 4,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(32, "Jonás", 4))
        ),
        ReadingPlanDay(
            dayNumber = 434,
            title = "Miqueas anuncia al gobernante de Belén",
            passagesSummary = "Miqueas 5:2-5",
            primaryBookId = 33,
            primaryChapter = 5,
            primaryVerse = 2,
            passages = listOf(PlanPassageSegment(33, "Miqueas", 5))
        ),
        ReadingPlanDay(
            dayNumber = 435,
            title = "Nahúm anuncia la caída de Nínive",
            passagesSummary = "Nahúm 1–3",
            primaryBookId = 34,
            primaryChapter = 1,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(34, "Nahúm", 1), PlanPassageSegment(34, "Nahúm", 2), PlanPassageSegment(34, "Nahúm", 3))
        ),
        ReadingPlanDay(
            dayNumber = 436,
            title = "Habacuc dialoga con Dios",
            passagesSummary = "Habacuc 1–3",
            primaryBookId = 35,
            primaryChapter = 1,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(35, "Habacuc", 1), PlanPassageSegment(35, "Habacuc", 2), PlanPassageSegment(35, "Habacuc", 3))
        ),
        ReadingPlanDay(
            dayNumber = 437,
            title = "Sofonías anuncia el día de Jehová",
            passagesSummary = "Sofonías 1–3",
            primaryBookId = 36,
            primaryChapter = 1,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(36, "Sofonías", 1), PlanPassageSegment(36, "Sofonías", 2), PlanPassageSegment(36, "Sofonías", 3))
        ),
        ReadingPlanDay(
            dayNumber = 438,
            title = "Hageo llama a reconstruir el templo",
            passagesSummary = "Hageo 1–2",
            primaryBookId = 37,
            primaryChapter = 1,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(37, "Hageo", 1), PlanPassageSegment(37, "Hageo", 2))
        ),
        ReadingPlanDay(
            dayNumber = 439,
            title = "Zacarías recibe las visiones nocturnas",
            passagesSummary = "Zacarías 1–6",
            primaryBookId = 38,
            primaryChapter = 1,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(38, "Zacarías", 1), PlanPassageSegment(38, "Zacarías", 2), PlanPassageSegment(38, "Zacarías", 3), PlanPassageSegment(38, "Zacarías", 4), PlanPassageSegment(38, "Zacarías", 5), PlanPassageSegment(38, "Zacarías", 6))
        ),
        ReadingPlanDay(
            dayNumber = 440,
            title = "El sumo sacerdote Josué es limpiado",
            passagesSummary = "Zacarías 3:1-10",
            primaryBookId = 38,
            primaryChapter = 3,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(38, "Zacarías", 3))
        ),
        ReadingPlanDay(
            dayNumber = 441,
            title = "Visión del candelero y los olivos",
            passagesSummary = "Zacarías 4:1-14",
            primaryBookId = 38,
            primaryChapter = 4,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(38, "Zacarías", 4))
        ),
        ReadingPlanDay(
            dayNumber = 442,
            title = "Visión del rollo volante",
            passagesSummary = "Zacarías 5:1-4",
            primaryBookId = 38,
            primaryChapter = 5,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(38, "Zacarías", 5))
        ),
        ReadingPlanDay(
            dayNumber = 443,
            title = "Visión de los cuatro carros",
            passagesSummary = "Zacarías 6:1-8",
            primaryBookId = 38,
            primaryChapter = 6,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(38, "Zacarías", 6))
        ),
        ReadingPlanDay(
            dayNumber = 444,
            title = "Malaquías anuncia la venida del mensajero",
            passagesSummary = "Malaquías 3:1-5",
            primaryBookId = 39,
            primaryChapter = 3,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(39, "Malaquías", 3))
        ),
        ReadingPlanDay(
            dayNumber = 445,
            title = "Malaquías anuncia al profeta Elías",
            passagesSummary = "Malaquías 4:5-6",
            primaryBookId = 39,
            primaryChapter = 4,
            primaryVerse = 5,
            passages = listOf(PlanPassageSegment(39, "Malaquías", 4))
        ),
        ReadingPlanDay(
            dayNumber = 446,
            title = "Genealogía de Jesucristo",
            passagesSummary = "Mateo 1:1-17",
            primaryBookId = 40,
            primaryChapter = 1,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(40, "Mateo", 1))
        ),
        ReadingPlanDay(
            dayNumber = 447,
            title = "Anuncio del nacimiento de Jesús a José",
            passagesSummary = "Mateo 1:18-25",
            primaryBookId = 40,
            primaryChapter = 1,
            primaryVerse = 18,
            passages = listOf(PlanPassageSegment(40, "Mateo", 1))
        ),
        ReadingPlanDay(
            dayNumber = 448,
            title = "Nacimiento de Jesús",
            passagesSummary = "Mateo 1:18-25; 2:1",
            primaryBookId = 40,
            primaryChapter = 1,
            primaryVerse = 18,
            passages = listOf(PlanPassageSegment(40, "Mateo", 1), PlanPassageSegment(40, "Mateo", 2))
        ),
        ReadingPlanDay(
            dayNumber = 449,
            title = "Los magos visitan a Jesús",
            passagesSummary = "Mateo 2:1-12",
            primaryBookId = 40,
            primaryChapter = 2,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(40, "Mateo", 2))
        ),
        ReadingPlanDay(
            dayNumber = 450,
            title = "Huida a Egipto",
            passagesSummary = "Mateo 2:13-15",
            primaryBookId = 40,
            primaryChapter = 2,
            primaryVerse = 13,
            passages = listOf(PlanPassageSegment(40, "Mateo", 2))
        ),
    )

    private fun part7(): List<ReadingPlanDay> = listOf(
        ReadingPlanDay(
            dayNumber = 451,
            title = "Matanza de los niños de Belén",
            passagesSummary = "Mateo 2:16-18",
            primaryBookId = 40,
            primaryChapter = 2,
            primaryVerse = 16,
            passages = listOf(PlanPassageSegment(40, "Mateo", 2))
        ),
        ReadingPlanDay(
            dayNumber = 452,
            title = "Regreso de Egipto",
            passagesSummary = "Mateo 2:19-23",
            primaryBookId = 40,
            primaryChapter = 2,
            primaryVerse = 19,
            passages = listOf(PlanPassageSegment(40, "Mateo", 2))
        ),
        ReadingPlanDay(
            dayNumber = 453,
            title = "Juan el Bautista predica",
            passagesSummary = "Mateo 3:1-12",
            primaryBookId = 40,
            primaryChapter = 3,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(40, "Mateo", 3))
        ),
        ReadingPlanDay(
            dayNumber = 454,
            title = "Bautismo de Jesús",
            passagesSummary = "Mateo 3:13-17",
            primaryBookId = 40,
            primaryChapter = 3,
            primaryVerse = 13,
            passages = listOf(PlanPassageSegment(40, "Mateo", 3))
        ),
        ReadingPlanDay(
            dayNumber = 455,
            title = "Tentación de Jesús",
            passagesSummary = "Mateo 4:1-11",
            primaryBookId = 40,
            primaryChapter = 4,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(40, "Mateo", 4))
        ),
        ReadingPlanDay(
            dayNumber = 456,
            title = "Jesús comienza a predicar",
            passagesSummary = "Mateo 4:12-17",
            primaryBookId = 40,
            primaryChapter = 4,
            primaryVerse = 12,
            passages = listOf(PlanPassageSegment(40, "Mateo", 4))
        ),
        ReadingPlanDay(
            dayNumber = 457,
            title = "Jesús llama a sus primeros discípulos",
            passagesSummary = "Mateo 4:18-22",
            primaryBookId = 40,
            primaryChapter = 4,
            primaryVerse = 18,
            passages = listOf(PlanPassageSegment(40, "Mateo", 4))
        ),
        ReadingPlanDay(
            dayNumber = 458,
            title = "Jesús sana a muchos",
            passagesSummary = "Mateo 4:23-25",
            primaryBookId = 40,
            primaryChapter = 4,
            primaryVerse = 23,
            passages = listOf(PlanPassageSegment(40, "Mateo", 4))
        ),
        ReadingPlanDay(
            dayNumber = 459,
            title = "Sermón del Monte",
            passagesSummary = "Mateo 5–7",
            primaryBookId = 40,
            primaryChapter = 5,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(40, "Mateo", 5), PlanPassageSegment(40, "Mateo", 6), PlanPassageSegment(40, "Mateo", 7))
        ),
        ReadingPlanDay(
            dayNumber = 460,
            title = "Jesús sana al siervo del centurión",
            passagesSummary = "Mateo 8:5-13",
            primaryBookId = 40,
            primaryChapter = 8,
            primaryVerse = 5,
            passages = listOf(PlanPassageSegment(40, "Mateo", 8))
        ),
        ReadingPlanDay(
            dayNumber = 461,
            title = "Jesús calma la tempestad",
            passagesSummary = "Mateo 8:23-27",
            primaryBookId = 40,
            primaryChapter = 8,
            primaryVerse = 23,
            passages = listOf(PlanPassageSegment(40, "Mateo", 8))
        ),
        ReadingPlanDay(
            dayNumber = 462,
            title = "Jesús libera a los endemoniados gadarenos",
            passagesSummary = "Mateo 8:28-34",
            primaryBookId = 40,
            primaryChapter = 8,
            primaryVerse = 28,
            passages = listOf(PlanPassageSegment(40, "Mateo", 8))
        ),
        ReadingPlanDay(
            dayNumber = 463,
            title = "Jesús sana al paralítico",
            passagesSummary = "Mateo 9:1-8",
            primaryBookId = 40,
            primaryChapter = 9,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(40, "Mateo", 9))
        ),
        ReadingPlanDay(
            dayNumber = 464,
            title = "Jesús llama a Mateo",
            passagesSummary = "Mateo 9:9-13",
            primaryBookId = 40,
            primaryChapter = 9,
            primaryVerse = 9,
            passages = listOf(PlanPassageSegment(40, "Mateo", 9))
        ),
        ReadingPlanDay(
            dayNumber = 465,
            title = "Resurrección de la hija de Jairo",
            passagesSummary = "Mateo 9:18-26",
            primaryBookId = 40,
            primaryChapter = 9,
            primaryVerse = 18,
            passages = listOf(PlanPassageSegment(40, "Mateo", 9))
        ),
        ReadingPlanDay(
            dayNumber = 466,
            title = "Jesús sana a dos ciegos",
            passagesSummary = "Mateo 9:27-31",
            primaryBookId = 40,
            primaryChapter = 9,
            primaryVerse = 27,
            passages = listOf(PlanPassageSegment(40, "Mateo", 9))
        ),
        ReadingPlanDay(
            dayNumber = 467,
            title = "Jesús envía a los doce",
            passagesSummary = "Mateo 10:1-42",
            primaryBookId = 40,
            primaryChapter = 10,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(40, "Mateo", 10))
        ),
        ReadingPlanDay(
            dayNumber = 468,
            title = "Juan el Bautista es encarcelado",
            passagesSummary = "Mateo 11:1-6",
            primaryBookId = 40,
            primaryChapter = 11,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(40, "Mateo", 11))
        ),
        ReadingPlanDay(
            dayNumber = 469,
            title = "Muerte de Juan el Bautista",
            passagesSummary = "Mateo 14:1-12",
            primaryBookId = 40,
            primaryChapter = 14,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(40, "Mateo", 14))
        ),
        ReadingPlanDay(
            dayNumber = 470,
            title = "Primera multiplicación de los panes",
            passagesSummary = "Mateo 14:13-21",
            primaryBookId = 40,
            primaryChapter = 14,
            primaryVerse = 13,
            passages = listOf(PlanPassageSegment(40, "Mateo", 14))
        ),
        ReadingPlanDay(
            dayNumber = 471,
            title = "Jesús camina sobre el mar",
            passagesSummary = "Mateo 14:22-33",
            primaryBookId = 40,
            primaryChapter = 14,
            primaryVerse = 22,
            passages = listOf(PlanPassageSegment(40, "Mateo", 14))
        ),
        ReadingPlanDay(
            dayNumber = 472,
            title = "Jesús sana a la hija de una mujer cananea",
            passagesSummary = "Mateo 15:21-28",
            primaryBookId = 40,
            primaryChapter = 15,
            primaryVerse = 21,
            passages = listOf(PlanPassageSegment(40, "Mateo", 15))
        ),
        ReadingPlanDay(
            dayNumber = 473,
            title = "Segunda multiplicación de los panes",
            passagesSummary = "Mateo 15:32-39",
            primaryBookId = 40,
            primaryChapter = 15,
            primaryVerse = 32,
            passages = listOf(PlanPassageSegment(40, "Mateo", 15))
        ),
        ReadingPlanDay(
            dayNumber = 474,
            title = "Pedro confiesa que Jesús es el Cristo",
            passagesSummary = "Mateo 16:13-20",
            primaryBookId = 40,
            primaryChapter = 16,
            primaryVerse = 13,
            passages = listOf(PlanPassageSegment(40, "Mateo", 16))
        ),
        ReadingPlanDay(
            dayNumber = 475,
            title = "Transfiguración",
            passagesSummary = "Mateo 17:1-13",
            primaryBookId = 40,
            primaryChapter = 17,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(40, "Mateo", 17))
        ),
        ReadingPlanDay(
            dayNumber = 476,
            title = "Jesús paga el impuesto con una moneda",
            passagesSummary = "Mateo 17:24-27",
            primaryBookId = 40,
            primaryChapter = 17,
            primaryVerse = 24,
            passages = listOf(PlanPassageSegment(40, "Mateo", 17))
        ),
        ReadingPlanDay(
            dayNumber = 477,
            title = "Parábola del siervo sin misericordia",
            passagesSummary = "Mateo 18:21-35",
            primaryBookId = 40,
            primaryChapter = 18,
            primaryVerse = 21,
            passages = listOf(PlanPassageSegment(40, "Mateo", 18))
        ),
        ReadingPlanDay(
            dayNumber = 478,
            title = "Entrada triunfal en Jerusalén",
            passagesSummary = "Mateo 21:1-11",
            primaryBookId = 40,
            primaryChapter = 21,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(40, "Mateo", 21))
        ),
        ReadingPlanDay(
            dayNumber = 479,
            title = "Jesús limpia el templo",
            passagesSummary = "Mateo 21:12-17",
            primaryBookId = 40,
            primaryChapter = 21,
            primaryVerse = 12,
            passages = listOf(PlanPassageSegment(40, "Mateo", 21))
        ),
        ReadingPlanDay(
            dayNumber = 480,
            title = "Maldición de la higuera",
            passagesSummary = "Mateo 21:18-22",
            primaryBookId = 40,
            primaryChapter = 21,
            primaryVerse = 18,
            passages = listOf(PlanPassageSegment(40, "Mateo", 21))
        ),
        ReadingPlanDay(
            dayNumber = 481,
            title = "Confrontaciones con los líderes religiosos",
            passagesSummary = "Mateo 21–23",
            primaryBookId = 40,
            primaryChapter = 21,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(40, "Mateo", 21), PlanPassageSegment(40, "Mateo", 22), PlanPassageSegment(40, "Mateo", 23))
        ),
        ReadingPlanDay(
            dayNumber = 482,
            title = "Discurso del Monte de los Olivos",
            passagesSummary = "Mateo 24–25",
            primaryBookId = 40,
            primaryChapter = 24,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(40, "Mateo", 24), PlanPassageSegment(40, "Mateo", 25))
        ),
        ReadingPlanDay(
            dayNumber = 483,
            title = "Judas conspira para entregar a Jesús",
            passagesSummary = "Mateo 26:14-16",
            primaryBookId = 40,
            primaryChapter = 26,
            primaryVerse = 14,
            passages = listOf(PlanPassageSegment(40, "Mateo", 26))
        ),
        ReadingPlanDay(
            dayNumber = 484,
            title = "Última Cena",
            passagesSummary = "Mateo 26:17-30",
            primaryBookId = 40,
            primaryChapter = 26,
            primaryVerse = 17,
            passages = listOf(PlanPassageSegment(40, "Mateo", 26))
        ),
        ReadingPlanDay(
            dayNumber = 485,
            title = "Jesús anuncia la negación de Pedro",
            passagesSummary = "Mateo 26:31-35",
            primaryBookId = 40,
            primaryChapter = 26,
            primaryVerse = 31,
            passages = listOf(PlanPassageSegment(40, "Mateo", 26))
        ),
        ReadingPlanDay(
            dayNumber = 486,
            title = "Getsemaní",
            passagesSummary = "Mateo 26:36-46",
            primaryBookId = 40,
            primaryChapter = 26,
            primaryVerse = 36,
            passages = listOf(PlanPassageSegment(40, "Mateo", 26))
        ),
        ReadingPlanDay(
            dayNumber = 487,
            title = "Arresto de Jesús",
            passagesSummary = "Mateo 26:47-56",
            primaryBookId = 40,
            primaryChapter = 26,
            primaryVerse = 47,
            passages = listOf(PlanPassageSegment(40, "Mateo", 26))
        ),
        ReadingPlanDay(
            dayNumber = 488,
            title = "Juicio ante Caifás",
            passagesSummary = "Mateo 26:57-68",
            primaryBookId = 40,
            primaryChapter = 26,
            primaryVerse = 57,
            passages = listOf(PlanPassageSegment(40, "Mateo", 26))
        ),
        ReadingPlanDay(
            dayNumber = 489,
            title = "Pedro niega a Jesús",
            passagesSummary = "Mateo 26:69-75",
            primaryBookId = 40,
            primaryChapter = 26,
            primaryVerse = 69,
            passages = listOf(PlanPassageSegment(40, "Mateo", 26))
        ),
        ReadingPlanDay(
            dayNumber = 490,
            title = "Jesús ante Pilato",
            passagesSummary = "Mateo 27:1-26",
            primaryBookId = 40,
            primaryChapter = 27,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(40, "Mateo", 27))
        ),
        ReadingPlanDay(
            dayNumber = 491,
            title = "Crucifixión",
            passagesSummary = "Mateo 27:27-56",
            primaryBookId = 40,
            primaryChapter = 27,
            primaryVerse = 27,
            passages = listOf(PlanPassageSegment(40, "Mateo", 27))
        ),
        ReadingPlanDay(
            dayNumber = 492,
            title = "Sepultura de Jesús",
            passagesSummary = "Mateo 27:57-66",
            primaryBookId = 40,
            primaryChapter = 27,
            primaryVerse = 57,
            passages = listOf(PlanPassageSegment(40, "Mateo", 27))
        ),
        ReadingPlanDay(
            dayNumber = 493,
            title = "Resurrección",
            passagesSummary = "Mateo 28:1-10",
            primaryBookId = 40,
            primaryChapter = 28,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(40, "Mateo", 28))
        ),
        ReadingPlanDay(
            dayNumber = 494,
            title = "La gran comisión",
            passagesSummary = "Mateo 28:16-20",
            primaryBookId = 40,
            primaryChapter = 28,
            primaryVerse = 16,
            passages = listOf(PlanPassageSegment(40, "Mateo", 28))
        ),
        ReadingPlanDay(
            dayNumber = 495,
            title = "Juan el Bautista prepara el camino",
            passagesSummary = "Marcos 1:1-8",
            primaryBookId = 41,
            primaryChapter = 1,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(41, "Marcos", 1))
        ),
        ReadingPlanDay(
            dayNumber = 496,
            title = "Bautismo de Jesús",
            passagesSummary = "Marcos 1:9-11",
            primaryBookId = 41,
            primaryChapter = 1,
            primaryVerse = 9,
            passages = listOf(PlanPassageSegment(41, "Marcos", 1))
        ),
        ReadingPlanDay(
            dayNumber = 497,
            title = "Tentación de Jesús",
            passagesSummary = "Marcos 1:12-13",
            primaryBookId = 41,
            primaryChapter = 1,
            primaryVerse = 12,
            passages = listOf(PlanPassageSegment(41, "Marcos", 1))
        ),
        ReadingPlanDay(
            dayNumber = 498,
            title = "Llamamiento de los primeros discípulos",
            passagesSummary = "Marcos 1:16-20",
            primaryBookId = 41,
            primaryChapter = 1,
            primaryVerse = 16,
            passages = listOf(PlanPassageSegment(41, "Marcos", 1))
        ),
        ReadingPlanDay(
            dayNumber = 499,
            title = "Jesús expulsa un espíritu inmundo",
            passagesSummary = "Marcos 1:21-28",
            primaryBookId = 41,
            primaryChapter = 1,
            primaryVerse = 21,
            passages = listOf(PlanPassageSegment(41, "Marcos", 1))
        ),
        ReadingPlanDay(
            dayNumber = 500,
            title = "Jesús sana a la suegra de Pedro",
            passagesSummary = "Marcos 1:29-31",
            primaryBookId = 41,
            primaryChapter = 1,
            primaryVerse = 29,
            passages = listOf(PlanPassageSegment(41, "Marcos", 1))
        ),
        ReadingPlanDay(
            dayNumber = 501,
            title = "Jesús sana a muchos",
            passagesSummary = "Marcos 1:32-34",
            primaryBookId = 41,
            primaryChapter = 1,
            primaryVerse = 32,
            passages = listOf(PlanPassageSegment(41, "Marcos", 1))
        ),
        ReadingPlanDay(
            dayNumber = 502,
            title = "Leproso sanado",
            passagesSummary = "Marcos 1:40-45",
            primaryBookId = 41,
            primaryChapter = 1,
            primaryVerse = 40,
            passages = listOf(PlanPassageSegment(41, "Marcos", 1))
        ),
        ReadingPlanDay(
            dayNumber = 503,
            title = "Paralítico bajado por el techo",
            passagesSummary = "Marcos 2:1-12",
            primaryBookId = 41,
            primaryChapter = 2,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(41, "Marcos", 2))
        ),
        ReadingPlanDay(
            dayNumber = 504,
            title = "Jesús llama a Leví",
            passagesSummary = "Marcos 2:13-17",
            primaryBookId = 41,
            primaryChapter = 2,
            primaryVerse = 13,
            passages = listOf(PlanPassageSegment(41, "Marcos", 2))
        ),
        ReadingPlanDay(
            dayNumber = 505,
            title = "Jesús sana en sábado",
            passagesSummary = "Marcos 3:1-6",
            primaryBookId = 41,
            primaryChapter = 3,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(41, "Marcos", 3))
        ),
        ReadingPlanDay(
            dayNumber = 506,
            title = "Elección de los doce",
            passagesSummary = "Marcos 3:13-19",
            primaryBookId = 41,
            primaryChapter = 3,
            primaryVerse = 13,
            passages = listOf(PlanPassageSegment(41, "Marcos", 3))
        ),
        ReadingPlanDay(
            dayNumber = 507,
            title = "Parábolas del reino",
            passagesSummary = "Marcos 4",
            primaryBookId = 41,
            primaryChapter = 4,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(41, "Marcos", 4))
        ),
        ReadingPlanDay(
            dayNumber = 508,
            title = "Jesús calma la tempestad",
            passagesSummary = "Marcos 4:35-41",
            primaryBookId = 41,
            primaryChapter = 4,
            primaryVerse = 35,
            passages = listOf(PlanPassageSegment(41, "Marcos", 4))
        ),
        ReadingPlanDay(
            dayNumber = 509,
            title = "Endemoniado gadareno",
            passagesSummary = "Marcos 5:1-20",
            primaryBookId = 41,
            primaryChapter = 5,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(41, "Marcos", 5))
        ),
        ReadingPlanDay(
            dayNumber = 510,
            title = "Hija de Jairo y mujer con flujo de sangre",
            passagesSummary = "Marcos 5:21-43",
            primaryBookId = 41,
            primaryChapter = 5,
            primaryVerse = 21,
            passages = listOf(PlanPassageSegment(41, "Marcos", 5))
        ),
        ReadingPlanDay(
            dayNumber = 511,
            title = "Jesús rechaza la incredulidad de Nazaret",
            passagesSummary = "Marcos 6:1-6",
            primaryBookId = 41,
            primaryChapter = 6,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(41, "Marcos", 6))
        ),
        ReadingPlanDay(
            dayNumber = 512,
            title = "Misión de los doce",
            passagesSummary = "Marcos 6:7-13",
            primaryBookId = 41,
            primaryChapter = 6,
            primaryVerse = 7,
            passages = listOf(PlanPassageSegment(41, "Marcos", 6))
        ),
        ReadingPlanDay(
            dayNumber = 513,
            title = "Muerte de Juan el Bautista",
            passagesSummary = "Marcos 6:14-29",
            primaryBookId = 41,
            primaryChapter = 6,
            primaryVerse = 14,
            passages = listOf(PlanPassageSegment(41, "Marcos", 6))
        ),
        ReadingPlanDay(
            dayNumber = 514,
            title = "Alimentación de cinco mil",
            passagesSummary = "Marcos 6:30-44",
            primaryBookId = 41,
            primaryChapter = 6,
            primaryVerse = 30,
            passages = listOf(PlanPassageSegment(41, "Marcos", 6))
        ),
        ReadingPlanDay(
            dayNumber = 515,
            title = "Jesús camina sobre el mar",
            passagesSummary = "Marcos 6:45-52",
            primaryBookId = 41,
            primaryChapter = 6,
            primaryVerse = 45,
            passages = listOf(PlanPassageSegment(41, "Marcos", 6))
        ),
        ReadingPlanDay(
            dayNumber = 516,
            title = "Transfiguración",
            passagesSummary = "Marcos 9:2-13",
            primaryBookId = 41,
            primaryChapter = 9,
            primaryVerse = 2,
            passages = listOf(PlanPassageSegment(41, "Marcos", 9))
        ),
        ReadingPlanDay(
            dayNumber = 517,
            title = "Bartimeo es sanado",
            passagesSummary = "Marcos 10:46-52",
            primaryBookId = 41,
            primaryChapter = 10,
            primaryVerse = 46,
            passages = listOf(PlanPassageSegment(41, "Marcos", 10))
        ),
        ReadingPlanDay(
            dayNumber = 518,
            title = "Entrada triunfal",
            passagesSummary = "Marcos 11:1-11",
            primaryBookId = 41,
            primaryChapter = 11,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(41, "Marcos", 11))
        ),
        ReadingPlanDay(
            dayNumber = 519,
            title = "Purificación del templo",
            passagesSummary = "Marcos 11:15-19",
            primaryBookId = 41,
            primaryChapter = 11,
            primaryVerse = 15,
            passages = listOf(PlanPassageSegment(41, "Marcos", 11))
        ),
        ReadingPlanDay(
            dayNumber = 520,
            title = "Última Cena",
            passagesSummary = "Marcos 14:12-26",
            primaryBookId = 41,
            primaryChapter = 14,
            primaryVerse = 12,
            passages = listOf(PlanPassageSegment(41, "Marcos", 14))
        ),
        ReadingPlanDay(
            dayNumber = 521,
            title = "Getsemaní",
            passagesSummary = "Marcos 14:32-42",
            primaryBookId = 41,
            primaryChapter = 14,
            primaryVerse = 32,
            passages = listOf(PlanPassageSegment(41, "Marcos", 14))
        ),
        ReadingPlanDay(
            dayNumber = 522,
            title = "Arresto y juicio",
            passagesSummary = "Marcos 14–15",
            primaryBookId = 41,
            primaryChapter = 14,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(41, "Marcos", 14), PlanPassageSegment(41, "Marcos", 15))
        ),
        ReadingPlanDay(
            dayNumber = 523,
            title = "Crucifixión",
            passagesSummary = "Marcos 15:21-41",
            primaryBookId = 41,
            primaryChapter = 15,
            primaryVerse = 21,
            passages = listOf(PlanPassageSegment(41, "Marcos", 15))
        ),
        ReadingPlanDay(
            dayNumber = 524,
            title = "Sepultura",
            passagesSummary = "Marcos 15:42-47",
            primaryBookId = 41,
            primaryChapter = 15,
            primaryVerse = 42,
            passages = listOf(PlanPassageSegment(41, "Marcos", 15))
        ),
        ReadingPlanDay(
            dayNumber = 525,
            title = "Resurrección",
            passagesSummary = "Marcos 16:1-8",
            primaryBookId = 41,
            primaryChapter = 16,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(41, "Marcos", 16))
        ),
    )

    private fun part8(): List<ReadingPlanDay> = listOf(
        ReadingPlanDay(
            dayNumber = 526,
            title = "Apariciones y comisión",
            passagesSummary = "Marcos 16:9-20",
            primaryBookId = 41,
            primaryChapter = 16,
            primaryVerse = 9,
            passages = listOf(PlanPassageSegment(41, "Marcos", 16))
        ),
        ReadingPlanDay(
            dayNumber = 527,
            title = "Anuncio del nacimiento de Juan",
            passagesSummary = "Lucas 1:5-25",
            primaryBookId = 42,
            primaryChapter = 1,
            primaryVerse = 5,
            passages = listOf(PlanPassageSegment(42, "Lucas", 1))
        ),
        ReadingPlanDay(
            dayNumber = 528,
            title = "Anuncio del nacimiento de Jesús a María",
            passagesSummary = "Lucas 1:26-38",
            primaryBookId = 42,
            primaryChapter = 1,
            primaryVerse = 26,
            passages = listOf(PlanPassageSegment(42, "Lucas", 1))
        ),
        ReadingPlanDay(
            dayNumber = 529,
            title = "María visita a Elisabet",
            passagesSummary = "Lucas 1:39-56",
            primaryBookId = 42,
            primaryChapter = 1,
            primaryVerse = 39,
            passages = listOf(PlanPassageSegment(42, "Lucas", 1))
        ),
        ReadingPlanDay(
            dayNumber = 530,
            title = "Nacimiento de Juan",
            passagesSummary = "Lucas 1:57-80",
            primaryBookId = 42,
            primaryChapter = 1,
            primaryVerse = 57,
            passages = listOf(PlanPassageSegment(42, "Lucas", 1))
        ),
        ReadingPlanDay(
            dayNumber = 531,
            title = "Censo de César Augusto",
            passagesSummary = "Lucas 2:1-5",
            primaryBookId = 42,
            primaryChapter = 2,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(42, "Lucas", 2))
        ),
        ReadingPlanDay(
            dayNumber = 532,
            title = "Nacimiento de Jesús en Belén",
            passagesSummary = "Lucas 2:6-20",
            primaryBookId = 42,
            primaryChapter = 2,
            primaryVerse = 6,
            passages = listOf(PlanPassageSegment(42, "Lucas", 2))
        ),
        ReadingPlanDay(
            dayNumber = 533,
            title = "Presentación de Jesús en el templo",
            passagesSummary = "Lucas 2:21-40",
            primaryBookId = 42,
            primaryChapter = 2,
            primaryVerse = 21,
            passages = listOf(PlanPassageSegment(42, "Lucas", 2))
        ),
        ReadingPlanDay(
            dayNumber = 534,
            title = "Jesús a los doce años en el templo",
            passagesSummary = "Lucas 2:41-52",
            primaryBookId = 42,
            primaryChapter = 2,
            primaryVerse = 41,
            passages = listOf(PlanPassageSegment(42, "Lucas", 2))
        ),
        ReadingPlanDay(
            dayNumber = 535,
            title = "Juan comienza su ministerio",
            passagesSummary = "Lucas 3:1-20",
            primaryBookId = 42,
            primaryChapter = 3,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(42, "Lucas", 3))
        ),
        ReadingPlanDay(
            dayNumber = 536,
            title = "Bautismo de Jesús",
            passagesSummary = "Lucas 3:21-22",
            primaryBookId = 42,
            primaryChapter = 3,
            primaryVerse = 21,
            passages = listOf(PlanPassageSegment(42, "Lucas", 3))
        ),
        ReadingPlanDay(
            dayNumber = 537,
            title = "Genealogía de Jesús",
            passagesSummary = "Lucas 3:23-38",
            primaryBookId = 42,
            primaryChapter = 3,
            primaryVerse = 23,
            passages = listOf(PlanPassageSegment(42, "Lucas", 3))
        ),
        ReadingPlanDay(
            dayNumber = 538,
            title = "Tentación",
            passagesSummary = "Lucas 4:1-13",
            primaryBookId = 42,
            primaryChapter = 4,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(42, "Lucas", 4))
        ),
        ReadingPlanDay(
            dayNumber = 539,
            title = "Jesús predica en Nazaret",
            passagesSummary = "Lucas 4:16-30",
            primaryBookId = 42,
            primaryChapter = 4,
            primaryVerse = 16,
            passages = listOf(PlanPassageSegment(42, "Lucas", 4))
        ),
        ReadingPlanDay(
            dayNumber = 540,
            title = "Pesca milagrosa",
            passagesSummary = "Lucas 5:1-11",
            primaryBookId = 42,
            primaryChapter = 5,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(42, "Lucas", 5))
        ),
        ReadingPlanDay(
            dayNumber = 541,
            title = "Jesús llama a los doce",
            passagesSummary = "Lucas 6:12-16",
            primaryBookId = 42,
            primaryChapter = 6,
            primaryVerse = 12,
            passages = listOf(PlanPassageSegment(42, "Lucas", 6))
        ),
        ReadingPlanDay(
            dayNumber = 542,
            title = "Sermón del Llano",
            passagesSummary = "Lucas 6:17-49",
            primaryBookId = 42,
            primaryChapter = 6,
            primaryVerse = 17,
            passages = listOf(PlanPassageSegment(42, "Lucas", 6))
        ),
        ReadingPlanDay(
            dayNumber = 543,
            title = "Jesús resucita al hijo de la viuda de Naín",
            passagesSummary = "Lucas 7:11-17",
            primaryBookId = 42,
            primaryChapter = 7,
            primaryVerse = 11,
            passages = listOf(PlanPassageSegment(42, "Lucas", 7))
        ),
        ReadingPlanDay(
            dayNumber = 544,
            title = "Mujer pecadora unge a Jesús",
            passagesSummary = "Lucas 7:36-50",
            primaryBookId = 42,
            primaryChapter = 7,
            primaryVerse = 36,
            passages = listOf(PlanPassageSegment(42, "Lucas", 7))
        ),
        ReadingPlanDay(
            dayNumber = 545,
            title = "Mujeres sirven a Jesús",
            passagesSummary = "Lucas 8:1-3",
            primaryBookId = 42,
            primaryChapter = 8,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(42, "Lucas", 8))
        ),
        ReadingPlanDay(
            dayNumber = 546,
            title = "Parábola del sembrador",
            passagesSummary = "Lucas 8:4-15",
            primaryBookId = 42,
            primaryChapter = 8,
            primaryVerse = 4,
            passages = listOf(PlanPassageSegment(42, "Lucas", 8))
        ),
        ReadingPlanDay(
            dayNumber = 547,
            title = "Jesús calma la tempestad",
            passagesSummary = "Lucas 8:22-25",
            primaryBookId = 42,
            primaryChapter = 8,
            primaryVerse = 22,
            passages = listOf(PlanPassageSegment(42, "Lucas", 8))
        ),
        ReadingPlanDay(
            dayNumber = 548,
            title = "Legión",
            passagesSummary = "Lucas 8:26-39",
            primaryBookId = 42,
            primaryChapter = 8,
            primaryVerse = 26,
            passages = listOf(PlanPassageSegment(42, "Lucas", 8))
        ),
        ReadingPlanDay(
            dayNumber = 549,
            title = "Hija de Jairo",
            passagesSummary = "Lucas 8:40-56",
            primaryBookId = 42,
            primaryChapter = 8,
            primaryVerse = 40,
            passages = listOf(PlanPassageSegment(42, "Lucas", 8))
        ),
        ReadingPlanDay(
            dayNumber = 550,
            title = "Misión de los doce",
            passagesSummary = "Lucas 9:1-6",
            primaryBookId = 42,
            primaryChapter = 9,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(42, "Lucas", 9))
        ),
        ReadingPlanDay(
            dayNumber = 551,
            title = "Alimentación de cinco mil",
            passagesSummary = "Lucas 9:10-17",
            primaryBookId = 42,
            primaryChapter = 9,
            primaryVerse = 10,
            passages = listOf(PlanPassageSegment(42, "Lucas", 9))
        ),
        ReadingPlanDay(
            dayNumber = 552,
            title = "Pedro confiesa a Cristo",
            passagesSummary = "Lucas 9:18-27",
            primaryBookId = 42,
            primaryChapter = 9,
            primaryVerse = 18,
            passages = listOf(PlanPassageSegment(42, "Lucas", 9))
        ),
        ReadingPlanDay(
            dayNumber = 553,
            title = "Transfiguración",
            passagesSummary = "Lucas 9:28-36",
            primaryBookId = 42,
            primaryChapter = 9,
            primaryVerse = 28,
            passages = listOf(PlanPassageSegment(42, "Lucas", 9))
        ),
        ReadingPlanDay(
            dayNumber = 554,
            title = "Jesús envía a los setenta",
            passagesSummary = "Lucas 10:1-24",
            primaryBookId = 42,
            primaryChapter = 10,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(42, "Lucas", 10))
        ),
        ReadingPlanDay(
            dayNumber = 555,
            title = "Buen Samaritano",
            passagesSummary = "Lucas 10:25-37",
            primaryBookId = 42,
            primaryChapter = 10,
            primaryVerse = 25,
            passages = listOf(PlanPassageSegment(42, "Lucas", 10))
        ),
        ReadingPlanDay(
            dayNumber = 556,
            title = "Marta y María",
            passagesSummary = "Lucas 10:38-42",
            primaryBookId = 42,
            primaryChapter = 10,
            primaryVerse = 38,
            passages = listOf(PlanPassageSegment(42, "Lucas", 10))
        ),
        ReadingPlanDay(
            dayNumber = 557,
            title = "Parábola del hijo pródigo",
            passagesSummary = "Lucas 15:11-32",
            primaryBookId = 42,
            primaryChapter = 15,
            primaryVerse = 11,
            passages = listOf(PlanPassageSegment(42, "Lucas", 15))
        ),
        ReadingPlanDay(
            dayNumber = 558,
            title = "Lázaro y el rico",
            passagesSummary = "Lucas 16:19-31",
            primaryBookId = 42,
            primaryChapter = 16,
            primaryVerse = 19,
            passages = listOf(PlanPassageSegment(42, "Lucas", 16))
        ),
        ReadingPlanDay(
            dayNumber = 559,
            title = "Los diez leprosos",
            passagesSummary = "Lucas 17:11-19",
            primaryBookId = 42,
            primaryChapter = 17,
            primaryVerse = 11,
            passages = listOf(PlanPassageSegment(42, "Lucas", 17))
        ),
        ReadingPlanDay(
            dayNumber = 560,
            title = "Zaqueo recibe a Jesús",
            passagesSummary = "Lucas 19:1-10",
            primaryBookId = 42,
            primaryChapter = 19,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(42, "Lucas", 19))
        ),
        ReadingPlanDay(
            dayNumber = 561,
            title = "Jesús llora sobre Jerusalén",
            passagesSummary = "Lucas 19:41-44",
            primaryBookId = 42,
            primaryChapter = 19,
            primaryVerse = 41,
            passages = listOf(PlanPassageSegment(42, "Lucas", 19))
        ),
        ReadingPlanDay(
            dayNumber = 562,
            title = "Última Cena",
            passagesSummary = "Lucas 22:7-38",
            primaryBookId = 42,
            primaryChapter = 22,
            primaryVerse = 7,
            passages = listOf(PlanPassageSegment(42, "Lucas", 22))
        ),
        ReadingPlanDay(
            dayNumber = 563,
            title = "Getsemaní",
            passagesSummary = "Lucas 22:39-46",
            primaryBookId = 42,
            primaryChapter = 22,
            primaryVerse = 39,
            passages = listOf(PlanPassageSegment(42, "Lucas", 22))
        ),
        ReadingPlanDay(
            dayNumber = 564,
            title = "Arresto",
            passagesSummary = "Lucas 22:47-53",
            primaryBookId = 42,
            primaryChapter = 22,
            primaryVerse = 47,
            passages = listOf(PlanPassageSegment(42, "Lucas", 22))
        ),
        ReadingPlanDay(
            dayNumber = 565,
            title = "Pedro niega a Jesús",
            passagesSummary = "Lucas 22:54-62",
            primaryBookId = 42,
            primaryChapter = 22,
            primaryVerse = 54,
            passages = listOf(PlanPassageSegment(42, "Lucas", 22))
        ),
        ReadingPlanDay(
            dayNumber = 566,
            title = "Jesús ante Pilato y Herodes",
            passagesSummary = "Lucas 23:1-25",
            primaryBookId = 42,
            primaryChapter = 23,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(42, "Lucas", 23))
        ),
        ReadingPlanDay(
            dayNumber = 567,
            title = "Crucifixión",
            passagesSummary = "Lucas 23:26-49",
            primaryBookId = 42,
            primaryChapter = 23,
            primaryVerse = 26,
            passages = listOf(PlanPassageSegment(42, "Lucas", 23))
        ),
        ReadingPlanDay(
            dayNumber = 568,
            title = "Sepultura",
            passagesSummary = "Lucas 23:50-56",
            primaryBookId = 42,
            primaryChapter = 23,
            primaryVerse = 50,
            passages = listOf(PlanPassageSegment(42, "Lucas", 23))
        ),
        ReadingPlanDay(
            dayNumber = 569,
            title = "Resurrección",
            passagesSummary = "Lucas 24:1-12",
            primaryBookId = 42,
            primaryChapter = 24,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(42, "Lucas", 24))
        ),
        ReadingPlanDay(
            dayNumber = 570,
            title = "Camino a Emaús",
            passagesSummary = "Lucas 24:13-35",
            primaryBookId = 42,
            primaryChapter = 24,
            primaryVerse = 13,
            passages = listOf(PlanPassageSegment(42, "Lucas", 24))
        ),
        ReadingPlanDay(
            dayNumber = 571,
            title = "Jesús aparece a los discípulos",
            passagesSummary = "Lucas 24:36-49",
            primaryBookId = 42,
            primaryChapter = 24,
            primaryVerse = 36,
            passages = listOf(PlanPassageSegment(42, "Lucas", 24))
        ),
        ReadingPlanDay(
            dayNumber = 572,
            title = "Ascensión",
            passagesSummary = "Lucas 24:50-53",
            primaryBookId = 42,
            primaryChapter = 24,
            primaryVerse = 50,
            passages = listOf(PlanPassageSegment(42, "Lucas", 24))
        ),
        ReadingPlanDay(
            dayNumber = 573,
            title = "El Verbo se hace carne",
            passagesSummary = "Juan 1:1-18",
            primaryBookId = 43,
            primaryChapter = 1,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(43, "Juan", 1))
        ),
        ReadingPlanDay(
            dayNumber = 574,
            title = "Juan el Bautista testifica de Jesús",
            passagesSummary = "Juan 1:19-34",
            primaryBookId = 43,
            primaryChapter = 1,
            primaryVerse = 19,
            passages = listOf(PlanPassageSegment(43, "Juan", 1))
        ),
        ReadingPlanDay(
            dayNumber = 575,
            title = "Primeros discípulos",
            passagesSummary = "Juan 1:35-51",
            primaryBookId = 43,
            primaryChapter = 1,
            primaryVerse = 35,
            passages = listOf(PlanPassageSegment(43, "Juan", 1))
        ),
        ReadingPlanDay(
            dayNumber = 576,
            title = "Bodas de Caná",
            passagesSummary = "Juan 2:1-12",
            primaryBookId = 43,
            primaryChapter = 2,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(43, "Juan", 2))
        ),
        ReadingPlanDay(
            dayNumber = 577,
            title = "Jesús limpia el templo",
            passagesSummary = "Juan 2:13-25",
            primaryBookId = 43,
            primaryChapter = 2,
            primaryVerse = 13,
            passages = listOf(PlanPassageSegment(43, "Juan", 2))
        ),
        ReadingPlanDay(
            dayNumber = 578,
            title = "Jesús y Nicodemo",
            passagesSummary = "Juan 3:1-21",
            primaryBookId = 43,
            primaryChapter = 3,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(43, "Juan", 3))
        ),
        ReadingPlanDay(
            dayNumber = 579,
            title = "Juan el Bautista vuelve a testificar de Jesús",
            passagesSummary = "Juan 3:22-36",
            primaryBookId = 43,
            primaryChapter = 3,
            primaryVerse = 22,
            passages = listOf(PlanPassageSegment(43, "Juan", 3))
        ),
        ReadingPlanDay(
            dayNumber = 580,
            title = "Jesús y la mujer samaritana",
            passagesSummary = "Juan 4:1-42",
            primaryBookId = 43,
            primaryChapter = 4,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(43, "Juan", 4))
        ),
        ReadingPlanDay(
            dayNumber = 581,
            title = "Jesús sana al hijo del noble",
            passagesSummary = "Juan 4:46-54",
            primaryBookId = 43,
            primaryChapter = 4,
            primaryVerse = 46,
            passages = listOf(PlanPassageSegment(43, "Juan", 4))
        ),
        ReadingPlanDay(
            dayNumber = 582,
            title = "Jesús sana al paralítico de Betesda",
            passagesSummary = "Juan 5:1-18",
            primaryBookId = 43,
            primaryChapter = 5,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(43, "Juan", 5))
        ),
        ReadingPlanDay(
            dayNumber = 583,
            title = "Alimentación de cinco mil",
            passagesSummary = "Juan 6:1-15",
            primaryBookId = 43,
            primaryChapter = 6,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(43, "Juan", 6))
        ),
        ReadingPlanDay(
            dayNumber = 584,
            title = "Jesús camina sobre el mar",
            passagesSummary = "Juan 6:16-21",
            primaryBookId = 43,
            primaryChapter = 6,
            primaryVerse = 16,
            passages = listOf(PlanPassageSegment(43, "Juan", 6))
        ),
        ReadingPlanDay(
            dayNumber = 585,
            title = "Jesús enseña sobre el pan de vida",
            passagesSummary = "Juan 6:22-71",
            primaryBookId = 43,
            primaryChapter = 6,
            primaryVerse = 22,
            passages = listOf(PlanPassageSegment(43, "Juan", 6))
        ),
        ReadingPlanDay(
            dayNumber = 586,
            title = "Mujer sorprendida en adulterio",
            passagesSummary = "Juan 7:53–8:11",
            primaryBookId = 43,
            primaryChapter = 7,
            primaryVerse = 53,
            passages = listOf(PlanPassageSegment(43, "Juan", 7), PlanPassageSegment(43, "Juan", 8))
        ),
        ReadingPlanDay(
            dayNumber = 587,
            title = "Jesús sana al ciego de nacimiento",
            passagesSummary = "Juan 9:1-41",
            primaryBookId = 43,
            primaryChapter = 9,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(43, "Juan", 9))
        ),
        ReadingPlanDay(
            dayNumber = 588,
            title = "Jesús es el Buen Pastor",
            passagesSummary = "Juan 10:1-42",
            primaryBookId = 43,
            primaryChapter = 10,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(43, "Juan", 10))
        ),
        ReadingPlanDay(
            dayNumber = 589,
            title = "Resurrección de Lázaro",
            passagesSummary = "Juan 11:1-46",
            primaryBookId = 43,
            primaryChapter = 11,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(43, "Juan", 11))
        ),
        ReadingPlanDay(
            dayNumber = 590,
            title = "Caifás conspira contra Jesús",
            passagesSummary = "Juan 11:47-57",
            primaryBookId = 43,
            primaryChapter = 11,
            primaryVerse = 47,
            passages = listOf(PlanPassageSegment(43, "Juan", 11))
        ),
        ReadingPlanDay(
            dayNumber = 591,
            title = "María unge a Jesús",
            passagesSummary = "Juan 12:1-8",
            primaryBookId = 43,
            primaryChapter = 12,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(43, "Juan", 12))
        ),
        ReadingPlanDay(
            dayNumber = 592,
            title = "Entrada triunfal",
            passagesSummary = "Juan 12:12-19",
            primaryBookId = 43,
            primaryChapter = 12,
            primaryVerse = 12,
            passages = listOf(PlanPassageSegment(43, "Juan", 12))
        ),
        ReadingPlanDay(
            dayNumber = 593,
            title = "Jesús lava los pies de los discípulos",
            passagesSummary = "Juan 13:1-20",
            primaryBookId = 43,
            primaryChapter = 13,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(43, "Juan", 13))
        ),
        ReadingPlanDay(
            dayNumber = 594,
            title = "Jesús anuncia al traidor",
            passagesSummary = "Juan 13:21-30",
            primaryBookId = 43,
            primaryChapter = 13,
            primaryVerse = 21,
            passages = listOf(PlanPassageSegment(43, "Juan", 13))
        ),
        ReadingPlanDay(
            dayNumber = 595,
            title = "Discurso del aposento alto",
            passagesSummary = "Juan 13–17",
            primaryBookId = 43,
            primaryChapter = 13,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(43, "Juan", 13), PlanPassageSegment(43, "Juan", 14), PlanPassageSegment(43, "Juan", 15), PlanPassageSegment(43, "Juan", 16), PlanPassageSegment(43, "Juan", 17))
        ),
        ReadingPlanDay(
            dayNumber = 596,
            title = "Oración sacerdotal de Jesús",
            passagesSummary = "Juan 17:1-26",
            primaryBookId = 43,
            primaryChapter = 17,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(43, "Juan", 17))
        ),
        ReadingPlanDay(
            dayNumber = 597,
            title = "Arresto de Jesús",
            passagesSummary = "Juan 18:1-11",
            primaryBookId = 43,
            primaryChapter = 18,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(43, "Juan", 18))
        ),
        ReadingPlanDay(
            dayNumber = 598,
            title = "Jesús ante Anás y Caifás",
            passagesSummary = "Juan 18:12-27",
            primaryBookId = 43,
            primaryChapter = 18,
            primaryVerse = 12,
            passages = listOf(PlanPassageSegment(43, "Juan", 18))
        ),
        ReadingPlanDay(
            dayNumber = 599,
            title = "Jesús ante Pilato",
            passagesSummary = "Juan 18:28–19:16",
            primaryBookId = 43,
            primaryChapter = 18,
            primaryVerse = 28,
            passages = listOf(PlanPassageSegment(43, "Juan", 18), PlanPassageSegment(43, "Juan", 19))
        ),
        ReadingPlanDay(
            dayNumber = 600,
            title = "Crucifixión",
            passagesSummary = "Juan 19:17-37",
            primaryBookId = 43,
            primaryChapter = 19,
            primaryVerse = 17,
            passages = listOf(PlanPassageSegment(43, "Juan", 19))
        ),
    )

    private fun part9(): List<ReadingPlanDay> = listOf(
        ReadingPlanDay(
            dayNumber = 601,
            title = "Sepultura",
            passagesSummary = "Juan 19:38-42",
            primaryBookId = 43,
            primaryChapter = 19,
            primaryVerse = 38,
            passages = listOf(PlanPassageSegment(43, "Juan", 19))
        ),
        ReadingPlanDay(
            dayNumber = 602,
            title = "María Magdalena encuentra la tumba vacía",
            passagesSummary = "Juan 20:1-18",
            primaryBookId = 43,
            primaryChapter = 20,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(43, "Juan", 20))
        ),
        ReadingPlanDay(
            dayNumber = 603,
            title = "Jesús aparece a los discípulos",
            passagesSummary = "Juan 20:19-23",
            primaryBookId = 43,
            primaryChapter = 20,
            primaryVerse = 19,
            passages = listOf(PlanPassageSegment(43, "Juan", 20))
        ),
        ReadingPlanDay(
            dayNumber = 604,
            title = "Tomás ve al Cristo resucitado",
            passagesSummary = "Juan 20:24-29",
            primaryBookId = 43,
            primaryChapter = 20,
            primaryVerse = 24,
            passages = listOf(PlanPassageSegment(43, "Juan", 20))
        ),
        ReadingPlanDay(
            dayNumber = 605,
            title = "Pesca milagrosa después de la resurrección",
            passagesSummary = "Juan 21:1-14",
            primaryBookId = 43,
            primaryChapter = 21,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(43, "Juan", 21))
        ),
        ReadingPlanDay(
            dayNumber = 606,
            title = "Jesús restaura a Pedro",
            passagesSummary = "Juan 21:15-25",
            primaryBookId = 43,
            primaryChapter = 21,
            primaryVerse = 15,
            passages = listOf(PlanPassageSegment(43, "Juan", 21))
        ),
        ReadingPlanDay(
            dayNumber = 607,
            title = "Ascensión de Jesucristo",
            passagesSummary = "Hechos 1:1-11",
            primaryBookId = 44,
            primaryChapter = 1,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(44, "Hechos", 1))
        ),
        ReadingPlanDay(
            dayNumber = 608,
            title = "Los discípulos esperan en Jerusalén",
            passagesSummary = "Hechos 1:12-14",
            primaryBookId = 44,
            primaryChapter = 1,
            primaryVerse = 12,
            passages = listOf(PlanPassageSegment(44, "Hechos", 1))
        ),
        ReadingPlanDay(
            dayNumber = 609,
            title = "Elección de Matías",
            passagesSummary = "Hechos 1:15-26",
            primaryBookId = 44,
            primaryChapter = 1,
            primaryVerse = 15,
            passages = listOf(PlanPassageSegment(44, "Hechos", 1))
        ),
        ReadingPlanDay(
            dayNumber = 610,
            title = "Pentecostés",
            passagesSummary = "Hechos 2:1-41",
            primaryBookId = 44,
            primaryChapter = 2,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(44, "Hechos", 2))
        ),
        ReadingPlanDay(
            dayNumber = 611,
            title = "La iglesia primitiva comparte sus bienes",
            passagesSummary = "Hechos 2:42-47",
            primaryBookId = 44,
            primaryChapter = 2,
            primaryVerse = 42,
            passages = listOf(PlanPassageSegment(44, "Hechos", 2))
        ),
        ReadingPlanDay(
            dayNumber = 612,
            title = "Pedro y Juan sanan al cojo",
            passagesSummary = "Hechos 3:1-10",
            primaryBookId = 44,
            primaryChapter = 3,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(44, "Hechos", 3))
        ),
        ReadingPlanDay(
            dayNumber = 613,
            title = "Pedro predica en el templo",
            passagesSummary = "Hechos 3:11-26",
            primaryBookId = 44,
            primaryChapter = 3,
            primaryVerse = 11,
            passages = listOf(PlanPassageSegment(44, "Hechos", 3))
        ),
        ReadingPlanDay(
            dayNumber = 614,
            title = "Pedro y Juan ante el Sanedrín",
            passagesSummary = "Hechos 4:1-22",
            primaryBookId = 44,
            primaryChapter = 4,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(44, "Hechos", 4))
        ),
        ReadingPlanDay(
            dayNumber = 615,
            title = "La iglesia ora por valentía",
            passagesSummary = "Hechos 4:23-31",
            primaryBookId = 44,
            primaryChapter = 4,
            primaryVerse = 23,
            passages = listOf(PlanPassageSegment(44, "Hechos", 4))
        ),
        ReadingPlanDay(
            dayNumber = 616,
            title = "Los creyentes comparten sus bienes",
            passagesSummary = "Hechos 4:32-37",
            primaryBookId = 44,
            primaryChapter = 4,
            primaryVerse = 32,
            passages = listOf(PlanPassageSegment(44, "Hechos", 4))
        ),
        ReadingPlanDay(
            dayNumber = 617,
            title = "Ananías y Safira",
            passagesSummary = "Hechos 5:1-11",
            primaryBookId = 44,
            primaryChapter = 5,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(44, "Hechos", 5))
        ),
        ReadingPlanDay(
            dayNumber = 618,
            title = "Milagros de los apóstoles",
            passagesSummary = "Hechos 5:12-16",
            primaryBookId = 44,
            primaryChapter = 5,
            primaryVerse = 12,
            passages = listOf(PlanPassageSegment(44, "Hechos", 5))
        ),
        ReadingPlanDay(
            dayNumber = 619,
            title = "Los apóstoles son encarcelados y liberados",
            passagesSummary = "Hechos 5:17-42",
            primaryBookId = 44,
            primaryChapter = 5,
            primaryVerse = 17,
            passages = listOf(PlanPassageSegment(44, "Hechos", 5))
        ),
        ReadingPlanDay(
            dayNumber = 620,
            title = "Elección de los siete diáconos",
            passagesSummary = "Hechos 6:1-7",
            primaryBookId = 44,
            primaryChapter = 6,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(44, "Hechos", 6))
        ),
        ReadingPlanDay(
            dayNumber = 621,
            title = "Esteban es acusado",
            passagesSummary = "Hechos 6:8-15",
            primaryBookId = 44,
            primaryChapter = 6,
            primaryVerse = 8,
            passages = listOf(PlanPassageSegment(44, "Hechos", 6))
        ),
        ReadingPlanDay(
            dayNumber = 622,
            title = "Discurso de Esteban",
            passagesSummary = "Hechos 7:1-53",
            primaryBookId = 44,
            primaryChapter = 7,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(44, "Hechos", 7))
        ),
        ReadingPlanDay(
            dayNumber = 623,
            title = "Muerte de Esteban",
            passagesSummary = "Hechos 7:54-60",
            primaryBookId = 44,
            primaryChapter = 7,
            primaryVerse = 54,
            passages = listOf(PlanPassageSegment(44, "Hechos", 7))
        ),
        ReadingPlanDay(
            dayNumber = 624,
            title = "Persecución de la iglesia",
            passagesSummary = "Hechos 8:1-4",
            primaryBookId = 44,
            primaryChapter = 8,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(44, "Hechos", 8))
        ),
        ReadingPlanDay(
            dayNumber = 625,
            title = "Felipe en Samaria",
            passagesSummary = "Hechos 8:4-25",
            primaryBookId = 44,
            primaryChapter = 8,
            primaryVerse = 4,
            passages = listOf(PlanPassageSegment(44, "Hechos", 8))
        ),
        ReadingPlanDay(
            dayNumber = 626,
            title = "Felipe y el eunuco etíope",
            passagesSummary = "Hechos 8:26-40",
            primaryBookId = 44,
            primaryChapter = 8,
            primaryVerse = 26,
            passages = listOf(PlanPassageSegment(44, "Hechos", 8))
        ),
        ReadingPlanDay(
            dayNumber = 627,
            title = "Conversión de Saulo",
            passagesSummary = "Hechos 9:1-19",
            primaryBookId = 44,
            primaryChapter = 9,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(44, "Hechos", 9))
        ),
        ReadingPlanDay(
            dayNumber = 628,
            title = "Pablo predica en Damasco",
            passagesSummary = "Hechos 9:20-31",
            primaryBookId = 44,
            primaryChapter = 9,
            primaryVerse = 20,
            passages = listOf(PlanPassageSegment(44, "Hechos", 9))
        ),
        ReadingPlanDay(
            dayNumber = 629,
            title = "Pedro sana a Eneas",
            passagesSummary = "Hechos 9:32-35",
            primaryBookId = 44,
            primaryChapter = 9,
            primaryVerse = 32,
            passages = listOf(PlanPassageSegment(44, "Hechos", 9))
        ),
        ReadingPlanDay(
            dayNumber = 630,
            title = "Pedro resucita a Tabita/Dorcas",
            passagesSummary = "Hechos 9:36-43",
            primaryBookId = 44,
            primaryChapter = 9,
            primaryVerse = 36,
            passages = listOf(PlanPassageSegment(44, "Hechos", 9))
        ),
        ReadingPlanDay(
            dayNumber = 631,
            title = "Visión de Pedro",
            passagesSummary = "Hechos 10:1-23",
            primaryBookId = 44,
            primaryChapter = 10,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(44, "Hechos", 10))
        ),
        ReadingPlanDay(
            dayNumber = 632,
            title = "Cornelio recibe el evangelio",
            passagesSummary = "Hechos 10:24-48",
            primaryBookId = 44,
            primaryChapter = 10,
            primaryVerse = 24,
            passages = listOf(PlanPassageSegment(44, "Hechos", 10))
        ),
        ReadingPlanDay(
            dayNumber = 633,
            title = "Pedro explica la conversión de los gentiles",
            passagesSummary = "Hechos 11:1-18",
            primaryBookId = 44,
            primaryChapter = 11,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(44, "Hechos", 11))
        ),
        ReadingPlanDay(
            dayNumber = 634,
            title = "La iglesia de Antioquía",
            passagesSummary = "Hechos 11:19-30",
            primaryBookId = 44,
            primaryChapter = 11,
            primaryVerse = 19,
            passages = listOf(PlanPassageSegment(44, "Hechos", 11))
        ),
        ReadingPlanDay(
            dayNumber = 635,
            title = "Santiago hijo de Zebedeo es ejecutado",
            passagesSummary = "Hechos 12:1-2",
            primaryBookId = 44,
            primaryChapter = 12,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(44, "Hechos", 12))
        ),
        ReadingPlanDay(
            dayNumber = 636,
            title = "Pedro es encarcelado",
            passagesSummary = "Hechos 12:3-6",
            primaryBookId = 44,
            primaryChapter = 12,
            primaryVerse = 3,
            passages = listOf(PlanPassageSegment(44, "Hechos", 12))
        ),
        ReadingPlanDay(
            dayNumber = 637,
            title = "Pedro es liberado milagrosamente",
            passagesSummary = "Hechos 12:7-19",
            primaryBookId = 44,
            primaryChapter = 12,
            primaryVerse = 7,
            passages = listOf(PlanPassageSegment(44, "Hechos", 12))
        ),
        ReadingPlanDay(
            dayNumber = 638,
            title = "Muerte de Herodes Agripa I",
            passagesSummary = "Hechos 12:20-23",
            primaryBookId = 44,
            primaryChapter = 12,
            primaryVerse = 20,
            passages = listOf(PlanPassageSegment(44, "Hechos", 12))
        ),
        ReadingPlanDay(
            dayNumber = 639,
            title = "Primer viaje misionero de Pablo y Bernabé",
            passagesSummary = "Hechos 13–14",
            primaryBookId = 44,
            primaryChapter = 13,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(44, "Hechos", 13), PlanPassageSegment(44, "Hechos", 14))
        ),
        ReadingPlanDay(
            dayNumber = 640,
            title = "Pablo y Bernabé en Antioquía de Pisidia",
            passagesSummary = "Hechos 13:13-52",
            primaryBookId = 44,
            primaryChapter = 13,
            primaryVerse = 13,
            passages = listOf(PlanPassageSegment(44, "Hechos", 13))
        ),
        ReadingPlanDay(
            dayNumber = 641,
            title = "Pablo es apedreado en Listra",
            passagesSummary = "Hechos 14:8-20",
            primaryBookId = 44,
            primaryChapter = 14,
            primaryVerse = 8,
            passages = listOf(PlanPassageSegment(44, "Hechos", 14))
        ),
        ReadingPlanDay(
            dayNumber = 642,
            title = "Concilio de Jerusalén",
            passagesSummary = "Hechos 15:1-35",
            primaryBookId = 44,
            primaryChapter = 15,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(44, "Hechos", 15))
        ),
        ReadingPlanDay(
            dayNumber = 643,
            title = "Pablo y Bernabé se separan",
            passagesSummary = "Hechos 15:36-41",
            primaryBookId = 44,
            primaryChapter = 15,
            primaryVerse = 36,
            passages = listOf(PlanPassageSegment(44, "Hechos", 15))
        ),
        ReadingPlanDay(
            dayNumber = 644,
            title = "Segundo viaje misionero de Pablo",
            passagesSummary = "Hechos 16–18",
            primaryBookId = 44,
            primaryChapter = 16,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(44, "Hechos", 16), PlanPassageSegment(44, "Hechos", 17), PlanPassageSegment(44, "Hechos", 18))
        ),
        ReadingPlanDay(
            dayNumber = 645,
            title = "Pablo y Silas en Filipos",
            passagesSummary = "Hechos 16:11-24",
            primaryBookId = 44,
            primaryChapter = 16,
            primaryVerse = 11,
            passages = listOf(PlanPassageSegment(44, "Hechos", 16))
        ),
        ReadingPlanDay(
            dayNumber = 646,
            title = "Pablo y Silas en la cárcel",
            passagesSummary = "Hechos 16:25-34",
            primaryBookId = 44,
            primaryChapter = 16,
            primaryVerse = 25,
            passages = listOf(PlanPassageSegment(44, "Hechos", 16))
        ),
        ReadingPlanDay(
            dayNumber = 647,
            title = "El terremoto de Filipos",
            passagesSummary = "Hechos 16:25-34",
            primaryBookId = 44,
            primaryChapter = 16,
            primaryVerse = 25,
            passages = listOf(PlanPassageSegment(44, "Hechos", 16))
        ),
        ReadingPlanDay(
            dayNumber = 648,
            title = "Pablo en Atenas",
            passagesSummary = "Hechos 17:16-34",
            primaryBookId = 44,
            primaryChapter = 17,
            primaryVerse = 16,
            passages = listOf(PlanPassageSegment(44, "Hechos", 17))
        ),
        ReadingPlanDay(
            dayNumber = 649,
            title = "Pablo en Corinto",
            passagesSummary = "Hechos 18:1-17",
            primaryBookId = 44,
            primaryChapter = 18,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(44, "Hechos", 18))
        ),
        ReadingPlanDay(
            dayNumber = 650,
            title = "Tercer viaje misionero",
            passagesSummary = "Hechos 18:23–21:17",
            primaryBookId = 44,
            primaryChapter = 18,
            primaryVerse = 23,
            passages = listOf(PlanPassageSegment(44, "Hechos", 18), PlanPassageSegment(44, "Hechos", 19), PlanPassageSegment(44, "Hechos", 20), PlanPassageSegment(44, "Hechos", 21))
        ),
        ReadingPlanDay(
            dayNumber = 651,
            title = "Pablo en Éfeso",
            passagesSummary = "Hechos 19:1-41",
            primaryBookId = 44,
            primaryChapter = 19,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(44, "Hechos", 19))
        ),
        ReadingPlanDay(
            dayNumber = 652,
            title = "Los hijos de Esceva",
            passagesSummary = "Hechos 19:11-20",
            primaryBookId = 44,
            primaryChapter = 19,
            primaryVerse = 11,
            passages = listOf(PlanPassageSegment(44, "Hechos", 19))
        ),
        ReadingPlanDay(
            dayNumber = 653,
            title = "Disturbio de los plateros en Éfeso",
            passagesSummary = "Hechos 19:23-41",
            primaryBookId = 44,
            primaryChapter = 19,
            primaryVerse = 23,
            passages = listOf(PlanPassageSegment(44, "Hechos", 19))
        ),
        ReadingPlanDay(
            dayNumber = 654,
            title = "Pablo resucita a Eutico",
            passagesSummary = "Hechos 20:7-12",
            primaryBookId = 44,
            primaryChapter = 20,
            primaryVerse = 7,
            passages = listOf(PlanPassageSegment(44, "Hechos", 20))
        ),
        ReadingPlanDay(
            dayNumber = 655,
            title = "Pablo se despide de los ancianos de Éfeso",
            passagesSummary = "Hechos 20:17-38",
            primaryBookId = 44,
            primaryChapter = 20,
            primaryVerse = 17,
            passages = listOf(PlanPassageSegment(44, "Hechos", 20))
        ),
        ReadingPlanDay(
            dayNumber = 656,
            title = "Pablo llega a Jerusalén",
            passagesSummary = "Hechos 21:1-26",
            primaryBookId = 44,
            primaryChapter = 21,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(44, "Hechos", 21))
        ),
        ReadingPlanDay(
            dayNumber = 657,
            title = "Pablo es arrestado",
            passagesSummary = "Hechos 21:27-36",
            primaryBookId = 44,
            primaryChapter = 21,
            primaryVerse = 27,
            passages = listOf(PlanPassageSegment(44, "Hechos", 21))
        ),
        ReadingPlanDay(
            dayNumber = 658,
            title = "Pablo da su testimonio ante el pueblo",
            passagesSummary = "Hechos 22:1-21",
            primaryBookId = 44,
            primaryChapter = 22,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(44, "Hechos", 22))
        ),
        ReadingPlanDay(
            dayNumber = 659,
            title = "Pablo ante el concilio",
            passagesSummary = "Hechos 22:30–23:35",
            primaryBookId = 44,
            primaryChapter = 22,
            primaryVerse = 30,
            passages = listOf(PlanPassageSegment(44, "Hechos", 22), PlanPassageSegment(44, "Hechos", 23))
        ),
        ReadingPlanDay(
            dayNumber = 660,
            title = "Conspiración para matar a Pablo",
            passagesSummary = "Hechos 23:12-35",
            primaryBookId = 44,
            primaryChapter = 23,
            primaryVerse = 12,
            passages = listOf(PlanPassageSegment(44, "Hechos", 23))
        ),
        ReadingPlanDay(
            dayNumber = 661,
            title = "Pablo ante Félix",
            passagesSummary = "Hechos 24:1-27",
            primaryBookId = 44,
            primaryChapter = 24,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(44, "Hechos", 24))
        ),
        ReadingPlanDay(
            dayNumber = 662,
            title = "Pablo ante Festo",
            passagesSummary = "Hechos 25:1-27",
            primaryBookId = 44,
            primaryChapter = 25,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(44, "Hechos", 25))
        ),
        ReadingPlanDay(
            dayNumber = 663,
            title = "Pablo ante Agripa",
            passagesSummary = "Hechos 26:1-32",
            primaryBookId = 44,
            primaryChapter = 26,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(44, "Hechos", 26))
        ),
        ReadingPlanDay(
            dayNumber = 664,
            title = "Pablo viaja a Roma",
            passagesSummary = "Hechos 27:1-44",
            primaryBookId = 44,
            primaryChapter = 27,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(44, "Hechos", 27))
        ),
        ReadingPlanDay(
            dayNumber = 665,
            title = "Naufragio de Pablo",
            passagesSummary = "Hechos 27:14-44",
            primaryBookId = 44,
            primaryChapter = 27,
            primaryVerse = 14,
            passages = listOf(PlanPassageSegment(44, "Hechos", 27))
        ),
        ReadingPlanDay(
            dayNumber = 666,
            title = "Pablo en Malta",
            passagesSummary = "Hechos 28:1-10",
            primaryBookId = 44,
            primaryChapter = 28,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(44, "Hechos", 28))
        ),
        ReadingPlanDay(
            dayNumber = 667,
            title = "Pablo llega a Roma",
            passagesSummary = "Hechos 28:11-31",
            primaryBookId = 44,
            primaryChapter = 28,
            primaryVerse = 11,
            passages = listOf(PlanPassageSegment(44, "Hechos", 28))
        ),
        ReadingPlanDay(
            dayNumber = 668,
            title = "Pablo recuerda su conversión",
            passagesSummary = "Gálatas 1:11-24",
            primaryBookId = 48,
            primaryChapter = 1,
            primaryVerse = 11,
            passages = listOf(PlanPassageSegment(48, "Gálatas", 1))
        ),
        ReadingPlanDay(
            dayNumber = 669,
            title = "Pablo visita Jerusalén",
            passagesSummary = "Gálatas 1:18-24",
            primaryBookId = 48,
            primaryChapter = 1,
            primaryVerse = 18,
            passages = listOf(PlanPassageSegment(48, "Gálatas", 1))
        ),
        ReadingPlanDay(
            dayNumber = 670,
            title = "Pablo confronta a Pedro en Antioquía",
            passagesSummary = "Gálatas 2:11-14",
            primaryBookId = 48,
            primaryChapter = 2,
            primaryVerse = 11,
            passages = listOf(PlanPassageSegment(48, "Gálatas", 2))
        ),
        ReadingPlanDay(
            dayNumber = 671,
            title = "Pablo y los apóstoles reconocen sus ministerios",
            passagesSummary = "Gálatas 2:1-10",
            primaryBookId = 48,
            primaryChapter = 2,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(48, "Gálatas", 2))
        ),
        ReadingPlanDay(
            dayNumber = 672,
            title = "Institución de la Cena del Señor recordada por Pablo",
            passagesSummary = "1 Corintios 11:23-26",
            primaryBookId = 46,
            primaryChapter = 11,
            primaryVerse = 23,
            passages = listOf(PlanPassageSegment(46, "1 Corintios", 11))
        ),
        ReadingPlanDay(
            dayNumber = 673,
            title = "Cristo resucitado aparece a testigos",
            passagesSummary = "1 Corintios 15:1-8",
            primaryBookId = 46,
            primaryChapter = 15,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(46, "1 Corintios", 15))
        ),
        ReadingPlanDay(
            dayNumber = 674,
            title = "Pablo enumera sus sufrimientos",
            passagesSummary = "2 Corintios 11:23-33",
            primaryBookId = 47,
            primaryChapter = 11,
            primaryVerse = 23,
            passages = listOf(PlanPassageSegment(47, "2 Corintios", 11))
        ),
        ReadingPlanDay(
            dayNumber = 675,
            title = "Pablo recibe revelación y habla de un “aguijón”",
            passagesSummary = "2 Corintios 12:1-10",
            primaryBookId = 47,
            primaryChapter = 12,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(47, "2 Corintios", 12))
        ),
    )

    private fun part10(): List<ReadingPlanDay> = listOf(
        ReadingPlanDay(
            dayNumber = 676,
            title = "Pablo habla de su encarcelamiento",
            passagesSummary = "Filipenses 1:7, 12-14",
            primaryBookId = 50,
            primaryChapter = 1,
            primaryVerse = 7,
            passages = listOf(PlanPassageSegment(50, "Filipenses", 1))
        ),
        ReadingPlanDay(
            dayNumber = 677,
            title = "Pablo y Silas recuerdan su ministerio en Tesalónica",
            passagesSummary = "1 Tesalonicenses 1–2",
            primaryBookId = 52,
            primaryChapter = 1,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(52, "1 Tesalonicenses", 1), PlanPassageSegment(52, "1 Tesalonicenses", 2))
        ),
        ReadingPlanDay(
            dayNumber = 678,
            title = "Pablo recuerda la persecución sufrida",
            passagesSummary = "2 Tesalonicenses 1:4-7",
            primaryBookId = 53,
            primaryChapter = 1,
            primaryVerse = 4,
            passages = listOf(PlanPassageSegment(53, "2 Tesalonicenses", 1))
        ),
        ReadingPlanDay(
            dayNumber = 679,
            title = "Pablo da instrucciones a Timoteo sobre la iglesia",
            passagesSummary = "1 Timoteo",
            primaryBookId = 54,
            primaryChapter = 1,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(54, "1 Timoteo", 1))
        ),
        ReadingPlanDay(
            dayNumber = 680,
            title = "Pablo escribe desde una situación de encarcelamiento",
            passagesSummary = "2 Timoteo 1:8; 2:9",
            primaryBookId = 55,
            primaryChapter = 1,
            primaryVerse = 8,
            passages = listOf(PlanPassageSegment(55, "2 Timoteo", 1), PlanPassageSegment(55, "2 Timoteo", 2))
        ),
        ReadingPlanDay(
            dayNumber = 681,
            title = "Pablo pide que Marcos venga a verlo",
            passagesSummary = "2 Timoteo 4:11",
            primaryBookId = 55,
            primaryChapter = 4,
            primaryVerse = 11,
            passages = listOf(PlanPassageSegment(55, "2 Timoteo", 4))
        ),
        ReadingPlanDay(
            dayNumber = 682,
            title = "Pablo menciona su primer juicio",
            passagesSummary = "2 Timoteo 4:16-17",
            primaryBookId = 55,
            primaryChapter = 4,
            primaryVerse = 16,
            passages = listOf(PlanPassageSegment(55, "2 Timoteo", 4))
        ),
        ReadingPlanDay(
            dayNumber = 683,
            title = "Pablo deja a Tito en Creta",
            passagesSummary = "Tito 1:5",
            primaryBookId = 56,
            primaryChapter = 1,
            primaryVerse = 5,
            passages = listOf(PlanPassageSegment(56, "Tito", 1))
        ),
        ReadingPlanDay(
            dayNumber = 684,
            title = "Onésimo es enviado de regreso a Filemón",
            passagesSummary = "Filemón 1:8-21",
            primaryBookId = 57,
            primaryChapter = 1,
            primaryVerse = 8,
            passages = listOf(PlanPassageSegment(57, "Filemón", 1))
        ),
        ReadingPlanDay(
            dayNumber = 685,
            title = "Los héroes de la fe son recordados",
            passagesSummary = "Hebreos 11",
            primaryBookId = 58,
            primaryChapter = 11,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(58, "Hebreos", 11))
        ),
        ReadingPlanDay(
            dayNumber = 686,
            title = "Santiago recuerda a Elías",
            passagesSummary = "Santiago 5:17-18",
            primaryBookId = 59,
            primaryChapter = 5,
            primaryVerse = 17,
            passages = listOf(PlanPassageSegment(59, "Santiago", 5))
        ),
        ReadingPlanDay(
            dayNumber = 687,
            title = "Pedro recuerda el monte de la transfiguración",
            passagesSummary = "2 Pedro 1:16-18",
            primaryBookId = 61,
            primaryChapter = 1,
            primaryVerse = 16,
            passages = listOf(PlanPassageSegment(61, "2 Pedro", 1))
        ),
        ReadingPlanDay(
            dayNumber = 688,
            title = "Judas recuerda el juicio sobre los ángeles",
            passagesSummary = "Judas 6",
            primaryBookId = 65,
            primaryChapter = 6,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(65, "Judas", 6))
        ),
        ReadingPlanDay(
            dayNumber = 689,
            title = "Judas recuerda Sodoma y Gomorra",
            passagesSummary = "Judas 7",
            primaryBookId = 65,
            primaryChapter = 7,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(65, "Judas", 7))
        ),
        ReadingPlanDay(
            dayNumber = 690,
            title = "Judas menciona la disputa entre Miguel y el diablo",
            passagesSummary = "Judas 9",
            primaryBookId = 65,
            primaryChapter = 9,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(65, "Judas", 9))
        ),
        ReadingPlanDay(
            dayNumber = 691,
            title = "Juan recibe la revelación en Patmos",
            passagesSummary = "Apocalipsis 1:1-20",
            primaryBookId = 66,
            primaryChapter = 1,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 1))
        ),
        ReadingPlanDay(
            dayNumber = 692,
            title = "Visión de Cristo glorificado",
            passagesSummary = "Apocalipsis 1:9-20",
            primaryBookId = 66,
            primaryChapter = 1,
            primaryVerse = 9,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 1))
        ),
        ReadingPlanDay(
            dayNumber = 693,
            title = "Mensaje a Éfeso",
            passagesSummary = "Apocalipsis 2:1-7",
            primaryBookId = 66,
            primaryChapter = 2,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 2))
        ),
        ReadingPlanDay(
            dayNumber = 694,
            title = "Mensaje a Esmirna",
            passagesSummary = "Apocalipsis 2:8-11",
            primaryBookId = 66,
            primaryChapter = 2,
            primaryVerse = 8,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 2))
        ),
        ReadingPlanDay(
            dayNumber = 695,
            title = "Mensaje a Pérgamo",
            passagesSummary = "Apocalipsis 2:12-17",
            primaryBookId = 66,
            primaryChapter = 2,
            primaryVerse = 12,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 2))
        ),
        ReadingPlanDay(
            dayNumber = 696,
            title = "Mensaje a Tiatira",
            passagesSummary = "Apocalipsis 2:18-29",
            primaryBookId = 66,
            primaryChapter = 2,
            primaryVerse = 18,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 2))
        ),
        ReadingPlanDay(
            dayNumber = 697,
            title = "Mensaje a Sardis",
            passagesSummary = "Apocalipsis 3:1-6",
            primaryBookId = 66,
            primaryChapter = 3,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 3))
        ),
        ReadingPlanDay(
            dayNumber = 698,
            title = "Mensaje a Filadelfia",
            passagesSummary = "Apocalipsis 3:7-13",
            primaryBookId = 66,
            primaryChapter = 3,
            primaryVerse = 7,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 3))
        ),
        ReadingPlanDay(
            dayNumber = 699,
            title = "Mensaje a Laodicea",
            passagesSummary = "Apocalipsis 3:14-22",
            primaryBookId = 66,
            primaryChapter = 3,
            primaryVerse = 14,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 3))
        ),
        ReadingPlanDay(
            dayNumber = 700,
            title = "Visión del trono celestial",
            passagesSummary = "Apocalipsis 4:1-11",
            primaryBookId = 66,
            primaryChapter = 4,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 4))
        ),
        ReadingPlanDay(
            dayNumber = 701,
            title = "El libro sellado",
            passagesSummary = "Apocalipsis 5:1-5",
            primaryBookId = 66,
            primaryChapter = 5,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 5))
        ),
        ReadingPlanDay(
            dayNumber = 702,
            title = "El Cordero recibe el libro",
            passagesSummary = "Apocalipsis 5:6-14",
            primaryBookId = 66,
            primaryChapter = 5,
            primaryVerse = 6,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 5))
        ),
        ReadingPlanDay(
            dayNumber = 703,
            title = "Primer sello",
            passagesSummary = "Apocalipsis 6:1-2",
            primaryBookId = 66,
            primaryChapter = 6,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 6))
        ),
        ReadingPlanDay(
            dayNumber = 704,
            title = "Segundo sello",
            passagesSummary = "Apocalipsis 6:3-4",
            primaryBookId = 66,
            primaryChapter = 6,
            primaryVerse = 3,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 6))
        ),
        ReadingPlanDay(
            dayNumber = 705,
            title = "Tercer sello",
            passagesSummary = "Apocalipsis 6:5-6",
            primaryBookId = 66,
            primaryChapter = 6,
            primaryVerse = 5,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 6))
        ),
        ReadingPlanDay(
            dayNumber = 706,
            title = "Cuarto sello",
            passagesSummary = "Apocalipsis 6:7-8",
            primaryBookId = 66,
            primaryChapter = 6,
            primaryVerse = 7,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 6))
        ),
        ReadingPlanDay(
            dayNumber = 707,
            title = "Quinto sello",
            passagesSummary = "Apocalipsis 6:9-11",
            primaryBookId = 66,
            primaryChapter = 6,
            primaryVerse = 9,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 6))
        ),
        ReadingPlanDay(
            dayNumber = 708,
            title = "Sexto sello",
            passagesSummary = "Apocalipsis 6:12-17",
            primaryBookId = 66,
            primaryChapter = 6,
            primaryVerse = 12,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 6))
        ),
        ReadingPlanDay(
            dayNumber = 709,
            title = "Los 144.000 sellados",
            passagesSummary = "Apocalipsis 7:1-8",
            primaryBookId = 66,
            primaryChapter = 7,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 7))
        ),
        ReadingPlanDay(
            dayNumber = 710,
            title = "La gran multitud",
            passagesSummary = "Apocalipsis 7:9-17",
            primaryBookId = 66,
            primaryChapter = 7,
            primaryVerse = 9,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 7))
        ),
        ReadingPlanDay(
            dayNumber = 711,
            title = "Séptimo sello",
            passagesSummary = "Apocalipsis 8:1",
            primaryBookId = 66,
            primaryChapter = 8,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 8))
        ),
        ReadingPlanDay(
            dayNumber = 712,
            title = "Primera trompeta",
            passagesSummary = "Apocalipsis 8:7",
            primaryBookId = 66,
            primaryChapter = 8,
            primaryVerse = 7,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 8))
        ),
        ReadingPlanDay(
            dayNumber = 713,
            title = "Segunda trompeta",
            passagesSummary = "Apocalipsis 8:8-9",
            primaryBookId = 66,
            primaryChapter = 8,
            primaryVerse = 8,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 8))
        ),
        ReadingPlanDay(
            dayNumber = 714,
            title = "Tercera trompeta",
            passagesSummary = "Apocalipsis 8:10-11",
            primaryBookId = 66,
            primaryChapter = 8,
            primaryVerse = 10,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 8))
        ),
        ReadingPlanDay(
            dayNumber = 715,
            title = "Cuarta trompeta",
            passagesSummary = "Apocalipsis 8:12-13",
            primaryBookId = 66,
            primaryChapter = 8,
            primaryVerse = 12,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 8))
        ),
        ReadingPlanDay(
            dayNumber = 716,
            title = "Quinta trompeta",
            passagesSummary = "Apocalipsis 9:1-12",
            primaryBookId = 66,
            primaryChapter = 9,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 9))
        ),
        ReadingPlanDay(
            dayNumber = 717,
            title = "Sexta trompeta",
            passagesSummary = "Apocalipsis 9:13-21",
            primaryBookId = 66,
            primaryChapter = 9,
            primaryVerse = 13,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 9))
        ),
        ReadingPlanDay(
            dayNumber = 718,
            title = "El ángel y el librito",
            passagesSummary = "Apocalipsis 10:1-11",
            primaryBookId = 66,
            primaryChapter = 10,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 10))
        ),
        ReadingPlanDay(
            dayNumber = 719,
            title = "Los dos testigos",
            passagesSummary = "Apocalipsis 11:1-14",
            primaryBookId = 66,
            primaryChapter = 11,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 11))
        ),
        ReadingPlanDay(
            dayNumber = 720,
            title = "Séptima trompeta",
            passagesSummary = "Apocalipsis 11:15-19",
            primaryBookId = 66,
            primaryChapter = 11,
            primaryVerse = 15,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 11))
        ),
        ReadingPlanDay(
            dayNumber = 721,
            title = "La mujer y el dragón",
            passagesSummary = "Apocalipsis 12:1-17",
            primaryBookId = 66,
            primaryChapter = 12,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 12))
        ),
        ReadingPlanDay(
            dayNumber = 722,
            title = "La bestia que sube del mar",
            passagesSummary = "Apocalipsis 13:1-10",
            primaryBookId = 66,
            primaryChapter = 13,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 13))
        ),
        ReadingPlanDay(
            dayNumber = 723,
            title = "La segunda bestia",
            passagesSummary = "Apocalipsis 13:11-18",
            primaryBookId = 66,
            primaryChapter = 13,
            primaryVerse = 11,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 13))
        ),
        ReadingPlanDay(
            dayNumber = 724,
            title = "El Cordero y los 144.000",
            passagesSummary = "Apocalipsis 14:1-5",
            primaryBookId = 66,
            primaryChapter = 14,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 14))
        ),
        ReadingPlanDay(
            dayNumber = 725,
            title = "Los tres ángeles",
            passagesSummary = "Apocalipsis 14:6-13",
            primaryBookId = 66,
            primaryChapter = 14,
            primaryVerse = 6,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 14))
        ),
        ReadingPlanDay(
            dayNumber = 726,
            title = "La cosecha de la tierra",
            passagesSummary = "Apocalipsis 14:14-20",
            primaryBookId = 66,
            primaryChapter = 14,
            primaryVerse = 14,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 14))
        ),
        ReadingPlanDay(
            dayNumber = 727,
            title = "Las siete copas",
            passagesSummary = "Apocalipsis 15–16",
            primaryBookId = 66,
            primaryChapter = 15,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 15), PlanPassageSegment(66, "Apocalipsis", 16))
        ),
        ReadingPlanDay(
            dayNumber = 728,
            title = "Primera copa",
            passagesSummary = "Apocalipsis 16:1-2",
            primaryBookId = 66,
            primaryChapter = 16,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 16))
        ),
        ReadingPlanDay(
            dayNumber = 729,
            title = "Segunda copa",
            passagesSummary = "Apocalipsis 16:3",
            primaryBookId = 66,
            primaryChapter = 16,
            primaryVerse = 3,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 16))
        ),
        ReadingPlanDay(
            dayNumber = 730,
            title = "Tercera copa",
            passagesSummary = "Apocalipsis 16:4-7",
            primaryBookId = 66,
            primaryChapter = 16,
            primaryVerse = 4,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 16))
        ),
        ReadingPlanDay(
            dayNumber = 731,
            title = "Cuarta copa",
            passagesSummary = "Apocalipsis 16:8-9",
            primaryBookId = 66,
            primaryChapter = 16,
            primaryVerse = 8,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 16))
        ),
        ReadingPlanDay(
            dayNumber = 732,
            title = "Quinta copa",
            passagesSummary = "Apocalipsis 16:10-11",
            primaryBookId = 66,
            primaryChapter = 16,
            primaryVerse = 10,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 16))
        ),
        ReadingPlanDay(
            dayNumber = 733,
            title = "Sexta copa",
            passagesSummary = "Apocalipsis 16:12-16",
            primaryBookId = 66,
            primaryChapter = 16,
            primaryVerse = 12,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 16))
        ),
        ReadingPlanDay(
            dayNumber = 734,
            title = "Armagedón",
            passagesSummary = "Apocalipsis 16:13-16",
            primaryBookId = 66,
            primaryChapter = 16,
            primaryVerse = 13,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 16))
        ),
        ReadingPlanDay(
            dayNumber = 735,
            title = "Séptima copa",
            passagesSummary = "Apocalipsis 16:17-21",
            primaryBookId = 66,
            primaryChapter = 16,
            primaryVerse = 17,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 16))
        ),
        ReadingPlanDay(
            dayNumber = 736,
            title = "La gran Babilonia",
            passagesSummary = "Apocalipsis 17:1-18",
            primaryBookId = 66,
            primaryChapter = 17,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 17))
        ),
        ReadingPlanDay(
            dayNumber = 737,
            title = "Caída de Babilonia",
            passagesSummary = "Apocalipsis 18:1-24",
            primaryBookId = 66,
            primaryChapter = 18,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 18))
        ),
        ReadingPlanDay(
            dayNumber = 738,
            title = "Alabanza en el cielo",
            passagesSummary = "Apocalipsis 19:1-10",
            primaryBookId = 66,
            primaryChapter = 19,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 19))
        ),
        ReadingPlanDay(
            dayNumber = 739,
            title = "El jinete sobre el caballo blanco",
            passagesSummary = "Apocalipsis 19:11-21",
            primaryBookId = 66,
            primaryChapter = 19,
            primaryVerse = 11,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 19))
        ),
        ReadingPlanDay(
            dayNumber = 740,
            title = "La derrota de la bestia y el falso profeta",
            passagesSummary = "Apocalipsis 19:19-21",
            primaryBookId = 66,
            primaryChapter = 19,
            primaryVerse = 19,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 19))
        ),
        ReadingPlanDay(
            dayNumber = 741,
            title = "Satanás es atado",
            passagesSummary = "Apocalipsis 20:1-3",
            primaryBookId = 66,
            primaryChapter = 20,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 20))
        ),
        ReadingPlanDay(
            dayNumber = 742,
            title = "El reinado de mil años",
            passagesSummary = "Apocalipsis 20:4-6",
            primaryBookId = 66,
            primaryChapter = 20,
            primaryVerse = 4,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 20))
        ),
        ReadingPlanDay(
            dayNumber = 743,
            title = "Satanás es soltado",
            passagesSummary = "Apocalipsis 20:7-10",
            primaryBookId = 66,
            primaryChapter = 20,
            primaryVerse = 7,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 20))
        ),
        ReadingPlanDay(
            dayNumber = 744,
            title = "Derrota definitiva de Satanás",
            passagesSummary = "Apocalipsis 20:9-10",
            primaryBookId = 66,
            primaryChapter = 20,
            primaryVerse = 9,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 20))
        ),
        ReadingPlanDay(
            dayNumber = 745,
            title = "El juicio ante el gran trono blanco",
            passagesSummary = "Apocalipsis 20:11-15",
            primaryBookId = 66,
            primaryChapter = 20,
            primaryVerse = 11,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 20))
        ),
        ReadingPlanDay(
            dayNumber = 746,
            title = "Cielo nuevo y tierra nueva",
            passagesSummary = "Apocalipsis 21:1-8",
            primaryBookId = 66,
            primaryChapter = 21,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 21))
        ),
        ReadingPlanDay(
            dayNumber = 747,
            title = "La Nueva Jerusalén",
            passagesSummary = "Apocalipsis 21:9-27",
            primaryBookId = 66,
            primaryChapter = 21,
            primaryVerse = 9,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 21))
        ),
        ReadingPlanDay(
            dayNumber = 748,
            title = "El río de agua de vida",
            passagesSummary = "Apocalipsis 22:1-5",
            primaryBookId = 66,
            primaryChapter = 22,
            primaryVerse = 1,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 22))
        ),
        ReadingPlanDay(
            dayNumber = 749,
            title = "El regreso de Cristo anunciado",
            passagesSummary = "Apocalipsis 22:6-21",
            primaryBookId = 66,
            primaryChapter = 22,
            primaryVerse = 6,
            passages = listOf(PlanPassageSegment(66, "Apocalipsis", 22))
        ),
    )

}
