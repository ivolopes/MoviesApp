package cubos.com.br.moviesapp.service

import cubos.com.br.moviesapp.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    companion object {
        private val httpClient = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val original = chain.request()
                    val url = original.url()
                            .newBuilder()
                            .addQueryParameter("api_key", "d81149d6c54875abf5845ef2f9e0a81c")
                            .addQueryParameter("language", "pt-BR")
                            .build()
                    val requestBuilder = original.newBuilder().url(url)
                    chain.proceed(requestBuilder.build())
                }
                .build()

        fun getRetrofit() = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(BuildConfig.SERVER_URL)
                    .client(httpClient)
                    .build()

        fun getClient() = getRetrofit().create(MoviesService::class.java)
    }
}