package com.example.data.bible

data class BibleVersion(
    val code: String,
    val name: String,
    val shortName: String,
    val description: String,
    val tag: String
)

data class BibleBook(
    val name: String,
    val testament: String, // "Antiguo Testamento" or "Nuevo Testamento"
    val chaptersCount: Int,
    val category: String,
    val abbreviation: String,
    val order: Int
)

object BibleCatalog {

    val versions: List<BibleVersion> = listOf(
        BibleVersion(
            code = "RVR1960",
            name = "Reina-Valera 1960",
            shortName = "RVR 1960",
            description = "Traducción clásica y solemne. La más extendida e histórica en el mundo hispanohablante.",
            tag = "Clásica"
        ),
        BibleVersion(
            code = "NVI",
            name = "Nueva Versión Internacional",
            shortName = "NVI",
            description = "Traducción fiel y contemporánea directamente de los textos originales hebreos, arameos y griegos.",
            tag = "Contemporánea"
        ),
        BibleVersion(
            code = "NTV",
            name = "Nueva Traducción Viviente",
            shortName = "NTV",
            description = "Traducción de equivalencia dinámica con prosa cálida, clara y de fácil comprensión.",
            tag = "Dinámica"
        ),
        BibleVersion(
            code = "TLA",
            name = "Traducción en Lenguaje Actual",
            shortName = "TLA",
            description = "Lenguaje sencillo, directo y cotidiano, ideal para lectura familiar y devocional.",
            tag = "Sencilla"
        ),
        BibleVersion(
            code = "DHH",
            name = "Dios Habla Hoy",
            shortName = "DHH",
            description = "Traducción popular ecuménica con vocabulario claro y accesible.",
            tag = "Popular"
        ),
        BibleVersion(
            code = "LBLA",
            name = "La Biblia de las Américas",
            shortName = "LBLA",
            description = "Traducción formal y literal rigurosa, excelente para exégesis y estudio bíblico minucioso.",
            tag = "Literal"
        )
    )

    val books: List<BibleBook> = listOf(
        // === ANTIGUO TESTAMENTO (39 libros) ===
        // Pentateuco
        BibleBook("Génesis", "Antiguo Testamento", 50, "Pentateuco", "Gn", 1),
        BibleBook("Éxodo", "Antiguo Testamento", 40, "Pentateuco", "Éx", 2),
        BibleBook("Levítico", "Antiguo Testamento", 27, "Pentateuco", "Lv", 3),
        BibleBook("Números", "Antiguo Testamento", 36, "Pentateuco", "Nm", 4),
        BibleBook("Deuteronomio", "Antiguo Testamento", 34, "Pentateuco", "Dt", 5),

        // Históricos
        BibleBook("Josué", "Antiguo Testamento", 24, "Históricos", "Jos", 6),
        BibleBook("Jueces", "Antiguo Testamento", 21, "Históricos", "Jue", 7),
        BibleBook("Rut", "Antiguo Testamento", 4, "Históricos", "Rt", 8),
        BibleBook("1 Samuel", "Antiguo Testamento", 31, "Históricos", "1 S", 9),
        BibleBook("2 Samuel", "Antiguo Testamento", 24, "Históricos", "2 S", 10),
        BibleBook("1 Reyes", "Antiguo Testamento", 22, "Históricos", "1 R", 11),
        BibleBook("2 Reyes", "Antiguo Testamento", 25, "Históricos", "2 R", 12),
        BibleBook("1 Crónicas", "Antiguo Testamento", 29, "Históricos", "1 Cr", 13),
        BibleBook("2 Crónicas", "Antiguo Testamento", 36, "Históricos", "2 Cr", 14),
        BibleBook("Esdras", "Antiguo Testamento", 10, "Históricos", "Esd", 15),
        BibleBook("Nehemías", "Antiguo Testamento", 13, "Históricos", "Neh", 16),
        BibleBook("Ester", "Antiguo Testamento", 10, "Históricos", "Est", 17),

        // Poéticos y Sapienciales
        BibleBook("Job", "Antiguo Testamento", 42, "Poéticos", "Job", 18),
        BibleBook("Salmos", "Antiguo Testamento", 150, "Poéticos", "Sal", 19),
        BibleBook("Proverbios", "Antiguo Testamento", 31, "Poéticos", "Pr", 20),
        BibleBook("Eclesiastés", "Antiguo Testamento", 12, "Poéticos", "Ec", 21),
        BibleBook("Cantares", "Antiguo Testamento", 8, "Poéticos", "Cnt", 22),

        // Profetas Mayores
        BibleBook("Isaías", "Antiguo Testamento", 66, "Profetas Mayores", "Is", 23),
        BibleBook("Jeremías", "Antiguo Testamento", 52, "Profetas Mayores", "Jer", 24),
        BibleBook("Lamentaciones", "Antiguo Testamento", 5, "Profetas Mayores", "Lm", 25),
        BibleBook("Ezequiel", "Antiguo Testamento", 48, "Profetas Mayores", "Ez", 26),
        BibleBook("Daniel", "Antiguo Testamento", 12, "Profetas Mayores", "Dn", 27),

        // Profetas Menores
        BibleBook("Oseas", "Antiguo Testamento", 14, "Profetas Menores", "Os", 28),
        BibleBook("Joel", "Antiguo Testamento", 3, "Profetas Menores", "Jl", 29),
        BibleBook("Amós", "Antiguo Testamento", 9, "Profetas Menores", "Am", 30),
        BibleBook("Abdías", "Antiguo Testamento", 1, "Profetas Menores", "Abd", 31),
        BibleBook("Jonás", "Antiguo Testamento", 4, "Profetas Menores", "Jon", 32),
        BibleBook("Miqueas", "Antiguo Testamento", 7, "Profetas Menores", "Miq", 33),
        BibleBook("Nahúm", "Antiguo Testamento", 3, "Profetas Menores", "Nah", 34),
        BibleBook("Habacuc", "Antiguo Testamento", 3, "Profetas Menores", "Hab", 35),
        BibleBook("Sofonías", "Antiguo Testamento", 3, "Profetas Menores", "Sof", 36),
        BibleBook("Hageo", "Antiguo Testamento", 2, "Profetas Menores", "Hag", 37),
        BibleBook("Zacarías", "Antiguo Testamento", 14, "Profetas Menores", "Zac", 38),
        BibleBook("Malaquías", "Antiguo Testamento", 4, "Profetas Menores", "Mal", 39),

        // === NUEVO TESTAMENTO (27 libros) ===
        // Evangelios
        BibleBook("Mateo", "Nuevo Testamento", 28, "Evangelios", "Mt", 40),
        BibleBook("Marcos", "Nuevo Testamento", 16, "Evangelios", "Mc", 41),
        BibleBook("Lucas", "Nuevo Testamento", 24, "Evangelios", "Lc", 42),
        BibleBook("Juan", "Nuevo Testamento", 21, "Evangelios", "Jn", 43),

        // Histórico
        BibleBook("Hechos", "Nuevo Testamento", 28, "Histórico", "Hch", 44),

        // Epístolas Paulinas
        BibleBook("Romanos", "Nuevo Testamento", 16, "Epístolas Paulinas", "Ro", 45),
        BibleBook("1 Corintios", "Nuevo Testamento", 16, "Epístolas Paulinas", "1 Co", 46),
        BibleBook("2 Corintios", "Nuevo Testamento", 13, "Epístolas Paulinas", "2 Co", 47),
        BibleBook("Gálatas", "Nuevo Testamento", 6, "Epístolas Paulinas", "Gál", 48),
        BibleBook("Efesios", "Nuevo Testamento", 6, "Epístolas Paulinas", "Ef", 49),
        BibleBook("Filipenses", "Nuevo Testamento", 4, "Epístolas Paulinas", "Fil", 50),
        BibleBook("Colosenses", "Nuevo Testamento", 4, "Epístolas Paulinas", "Col", 51),
        BibleBook("1 Tesalonicenses", "Nuevo Testamento", 5, "Epístolas Paulinas", "1 Ts", 52),
        BibleBook("2 Tesalonicenses", "Nuevo Testamento", 3, "Epístolas Paulinas", "2 Ts", 53),
        BibleBook("1 Timoteo", "Nuevo Testamento", 6, "Epístolas Paulinas", "1 Ti", 54),
        BibleBook("2 Timoteo", "Nuevo Testamento", 4, "Epístolas Paulinas", "2 Ti", 55),
        BibleBook("Tito", "Nuevo Testamento", 3, "Epístolas Paulinas", "Tit", 56),
        BibleBook("Filemón", "Nuevo Testamento", 1, "Epístolas Paulinas", "Flm", 57),

        // Epístolas Generales
        BibleBook("Hebreos", "Nuevo Testamento", 13, "Epístolas Generales", "He", 58),
        BibleBook("Santiago", "Nuevo Testamento", 5, "Epístolas Generales", "Stg", 59),
        BibleBook("1 Pedro", "Nuevo Testamento", 5, "Epístolas Generales", "1 P", 60),
        BibleBook("2 Pedro", "Nuevo Testamento", 3, "Epístolas Generales", "2 P", 61),
        BibleBook("1 Juan", "Nuevo Testamento", 5, "Epístolas Generales", "1 Jn", 62),
        BibleBook("2 Juan", "Nuevo Testamento", 1, "Epístolas Generales", "2 Jn", 63),
        BibleBook("3 Juan", "Nuevo Testamento", 1, "Epístolas Generales", "3 Jn", 64),
        BibleBook("Judas", "Nuevo Testamento", 1, "Epístolas Generales", "Jds", 65),

        // Profecía
        BibleBook("Apocalipsis", "Nuevo Testamento", 22, "Profecía", "Ap", 66)
    )

    fun findVersion(code: String): BibleVersion {
        return versions.firstOrNull { it.code.equals(code, ignoreCase = true) }
            ?: versions.first()
    }

    fun findBook(name: String): BibleBook? {
        val clean = name.trim().lowercase()
        return books.firstOrNull {
            it.name.lowercase() == clean || it.abbreviation.lowercase() == clean
        }
    }
}
