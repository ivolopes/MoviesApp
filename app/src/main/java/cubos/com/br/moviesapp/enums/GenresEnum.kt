package cubos.com.br.moviesapp.enums

enum class GenresEnum(var id: Int, var title: String) {

    FANTASY(14, "Fantasia"),
    DRAMA(18, "Drama"),
    ACTION(28, "Ação"),
    FICTION(878, "Ficção");

    companion object  {
        fun value(id: Int?): GenresEnum = when(id) {
            14 -> FANTASY
            18 -> DRAMA
            28 -> ACTION
            else -> FICTION
        }
    }

}