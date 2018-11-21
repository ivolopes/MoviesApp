package cubos.com.br.moviesapp.view.fragments

import cubos.com.br.moviesapp.model.Movie

interface MovieClicked {

    fun onMovieClicked(movie: Movie)
}