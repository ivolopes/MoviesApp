package cubos.com.br.moviesapp.service

import cubos.com.br.moviesapp.model.MovieResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesService{

    @GET("discover/movie")
    fun getMoviesByGenre(@Query("with_genres")genre : Int,
                         @Query("page") page : Int): Observable<Response<MovieResponse>>

    @GET("search/movie")
    fun searchMovies(@Query("query")query : String,
                     @Query("page") page : Int) : Observable<Response<MovieResponse>>

}