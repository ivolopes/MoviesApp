package cubos.com.br.moviesapp.presenter

import com.example.ivo.mvpteste.view.interfaces.MainView
import cubos.com.br.moviesapp.model.Movie
import cubos.com.br.moviesapp.presenter.interfaces.Presenter
import cubos.com.br.moviesapp.service.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainPresenter( private var mainView: MainView): Presenter {

    override fun onCreate() {

    }

    fun requestingObjects(page: Int, genre: Int){

        RetrofitClient.getClient().getMoviesByGenre(genre, page).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val body = it.body()
                    mainView.onSuccess(
                            body?.movies ?: ArrayList<Movie>(),
                            body?.page ?: -1,
                            body?.totalPages ?: -1)
                },{
                    mainView.onError(it?.message ?: "")
                })

    }

    override fun onPause() {}
    override fun onResume() {}
    override fun onDestroy() {}

}