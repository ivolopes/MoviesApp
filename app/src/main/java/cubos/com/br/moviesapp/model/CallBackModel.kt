package cubos.com.br.moviesapp.model

interface CallBackModel {
    fun onSucessCallback(movieResponse: MovieResponse)
    fun onErrorCallback(message: String)
}