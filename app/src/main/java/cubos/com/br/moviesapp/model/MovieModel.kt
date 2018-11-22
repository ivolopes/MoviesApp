package cubos.com.br.moviesapp.model

import cubos.com.br.moviesapp.service.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MovieModel{

    fun getMovieByGenre(callBackModel:CallBackModel, genre: Int, page: Int){
        RetrofitClient.getClient().getMoviesByGenre(genre, page).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val body = it.body()
                    callBackModel.onSucessCallback(body!!)
                },{
                    callBackModel.onErrorCallback(it?.localizedMessage ?: "Ocorreu algum erro")
                })
    }

}