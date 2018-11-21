package cubos.com.br.moviesapp.presenter

import com.example.ivo.mvpteste.view.interfaces.MovieView
import cubos.com.br.moviesapp.model.Movie
import cubos.com.br.moviesapp.presenter.interfaces.Presenter
import cubos.com.br.moviesapp.service.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MoviePresenter(private var movieView: MovieView): Presenter {

    private var movies = arrayListOf<Movie>()

    override fun onCreate() {

    }

    fun requestingObjects(page: Int, genre: Int){

        RetrofitClient.getClient().getMoviesByGenre(genre, page).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val body = it.body()

                    if( body?.page!! <= 1){
                        movies = arrayListOf()
                    }
                    movies.addAll(body.movies ?: ArrayList())

                    movieView.onSuccess(
                            movies,
                            body.page,
                            body.totalPages)
                },{
                    movieView.onError(it?.message ?: "")
                })

    }

    override fun onPause() {}
    override fun onResume() {}
    override fun onDestroy() {}

}