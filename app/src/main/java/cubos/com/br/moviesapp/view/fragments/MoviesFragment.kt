package cubos.com.br.moviesapp.view.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cubos.com.br.moviesapp.presenter.MoviePresenter
import com.example.ivo.mvpteste.view.interfaces.MovieView
import cubos.com.br.moviesapp.R
import cubos.com.br.moviesapp.enums.GenresEnum
import cubos.com.br.moviesapp.model.Movie
import cubos.com.br.moviesapp.view.activities.DetailActivity
import cubos.com.br.moviesapp.view.adapter.MoviesAdapter
import kotlinx.android.synthetic.main.fragment_movies.*

class MoviesFragment : Fragment(), MovieClicked, MovieView{

    private var moviePresenter: MoviePresenter? = null

    lateinit var idGenre : GenresEnum
    private var page = 1
    private var totalPages = 1
    private var adapter: MoviesAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        page = 1
        totalPages = 1

        moviePresenter = MoviePresenter(this)

        setUpRecycler()
        showProgress()
        moviePresenter?.requestingObjects(page, idGenre.id)

        swipeRefreshLayout.setOnRefreshListener {
            page = 1
            moviePresenter?.requestingObjects(page, idGenre.id)
        }

        recyclerViewMovies.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastItem = (recyclerView.layoutManager as GridLayoutManager).findLastVisibleItemPosition()
                val total = recyclerView.layoutManager?.itemCount

                if (dy > 0 && lastItem == total?.minus(1)) {
                    moviePresenter?.requestingObjects(page, idGenre.id)
                    showProgress()
                }
            }
        })

    }

    override fun onMovieClicked(movie: Movie) {
        val intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra("movie", movie)
        activity?.startActivity(intent)
    }

    override fun onError(message: String) {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun onSuccess(obj: Any, page: Int, totalPages: Int) {

        @Suppress("UNCHECKED_CAST")
        val movies = obj as ArrayList<Movie>

        this.totalPages = totalPages
        this.page++

        adapter?.movies = movies
        recyclerViewMovies.post {
            showProgress(false)
            adapter?.movies = movies
            adapter?.notifyDataSetChanged()
        }
        swipeRefreshLayout.isRefreshing = false
    }

    override fun updateRecyclerView(lista: List<String>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun setUpRecycler(){
        recyclerViewMovies.layoutManager = GridLayoutManager(context, 2)
        adapter = MoviesAdapter(this, arrayListOf())
        recyclerViewMovies.adapter = adapter
        recyclerViewMovies.itemAnimator = DefaultItemAnimator()
    }

    private fun showProgress(show: Boolean = true){
        if( show ) {
            frameProgress.visibility = View.VISIBLE
        }else{
            frameProgress.visibility = View.GONE
        }
    }

    companion object {
        fun newInstance(id: Int): MoviesFragment {
            val fragment = MoviesFragment()
            fragment.idGenre = GenresEnum.value(id)
            return fragment
        }
    }
}