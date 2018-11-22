package cubos.com.br.moviesapp.model

import cubos.com.br.moviesapp.service.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainModel {
    fun searchMovie(callBackModel:CallBackModel, query: String, page: Int){
        RetrofitClient.getClient().searchMovies(query, page).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val body = it.body()
                    callBackModel.onSucessCallback(body!!)
                },{
                    callBackModel.onErrorCallback(it?.localizedMessage ?: "Ocorreu algum erro")
                })
    }
}