package cubos.com.br.moviesapp.presenter

import cubos.com.br.moviesapp.model.CallBackModel
import cubos.com.br.moviesapp.view.interfaces.MovieView
import cubos.com.br.moviesapp.model.Movie
import cubos.com.br.moviesapp.model.MovieModel
import cubos.com.br.moviesapp.model.MovieResponse
import cubos.com.br.moviesapp.presenter.interfaces.Presenter
import cubos.com.br.moviesapp.service.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MoviePresenter(private var movieView: MovieView): Presenter, CallBackModel {

    private var movies = arrayListOf<Movie>()
    private val movieModel = MovieModel()

    override fun onCreate() {

    }

    override fun onSucessCallback(movieResponse: MovieResponse) {

        if( movieResponse.page <= 1){
            movies = arrayListOf()
        }
        movies.addAll(movieResponse.movies ?: ArrayList())

        movieView.hideProgress()
        movieView.onSuccess(
                movies,
                movieResponse.page,
                movieResponse.totalPages)
    }

    override fun onErrorCallback(message: String) {
        movieView.hideProgress()
        movieView.onError(message)
    }

    fun requestingObjects(page: Int, genre: Int){
        movieView.showProgress()
        movieModel.getMovieByGenre(this, genre, page)

    }

    override fun onPause() {}
    override fun onResume() {}
    override fun onDestroy() {}

}