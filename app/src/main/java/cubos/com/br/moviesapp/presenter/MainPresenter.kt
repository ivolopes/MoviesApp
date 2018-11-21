package cubos.com.br.moviesapp.presenter

import cubos.com.br.moviesapp.model.Movie
import cubos.com.br.moviesapp.presenter.interfaces.Presenter
import cubos.com.br.moviesapp.service.RetrofitClient
import cubos.com.br.moviesapp.view.interfaces.MainView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainPresenter(private var mainView: MainView) : Presenter{

    fun requestingObjects(query: String, page: Int){

        RetrofitClient.getClient().searchMovies(query, page).subscribeOn(Schedulers.io())
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

    override fun onCreate() {}
    override fun onPause() {}
    override fun onResume() {}
    override fun onDestroy() {}

}