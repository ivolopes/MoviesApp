package cubos.com.br.moviesapp.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import cubos.com.br.moviesapp.BuildConfig
import cubos.com.br.moviesapp.R
import cubos.com.br.moviesapp.model.Movie
import cubos.com.br.moviesapp.view.fragments.MovieClicked

class MoviesAdapter(val movieClicked: MovieClicked, var movies: ArrayList<Movie>) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>(){

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {

        val movie = movies[position]

        holder.text.text = movie.title

        Picasso.get()
                .load(BuildConfig.SERVER_URL_IMAGE + movie.posterPath).error(R.drawable.movie_default)
                .into(holder.image)

        holder.image.setOnClickListener {
            movieClicked.onMovieClicked(movie)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_movies_item, parent, false)
        return MovieViewHolder(view)
    }

    inner class MovieViewHolder(mView: View) : RecyclerView.ViewHolder(mView){

        val image : ImageView = mView.findViewById(R.id.imageViewMovie)
        val text: TextView = mView.findViewById(R.id.textViewMovie)

    }
}
