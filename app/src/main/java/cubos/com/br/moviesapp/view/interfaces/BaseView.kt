package cubos.com.br.moviesapp.view.interfaces

interface BaseView {

    fun onSuccess(obj: Any, page: Int, totalPages: Int)
    fun onError(message: String)

}