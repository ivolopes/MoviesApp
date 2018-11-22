package cubos.com.br.moviesapp.presenter

import cubos.com.br.moviesapp.model.*
import cubos.com.br.moviesapp.presenter.interfaces.Presenter
import cubos.com.br.moviesapp.service.RetrofitClient
import cubos.com.br.moviesapp.view.interfaces.MainView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainPresenter(private var mainView: MainView) : Presenter, CallBackModel {

    private val mainModel = MainModel()

    override fun onSucessCallback(movieResponse: MovieResponse) {
        mainView.onSuccess(
                movieResponse.movies ?: ArrayList<Movie>(),
                movieResponse.page,
                movieResponse.totalPages)
    }

    override fun onErrorCallback(message: String) {
        mainView.onError(message)
    }

    fun requestingObjects(query: String, page: Int){
        mainModel.searchMovie(this, query, page)
    }

    override fun onCreate() {}
    override fun onPause() {}
    override fun onResume() {}
    override fun onDestroy() {}

}