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
import cubos.com.br.moviesapp.view.interfaces.MovieView
import cubos.com.br.moviesapp.R
import cubos.com.br.moviesapp.enums.GenresEnum
import cubos.com.br.moviesapp.model.Movie
import cubos.com.br.moviesapp.utils.AppUtils
import cubos.com.br.moviesapp.view.activities.DetailActivity
import cubos.com.br.moviesapp.view.adapter.MoviesAdapter
import kotlinx.android.synthetic.main.fragment_movies.*

class MoviesFragment : Fragment(), MovieClicked, MovieView {

    private var moviePresenter: MoviePresenter? = null

    lateinit var genre : GenresEnum
    private var page = 1
    private var totalPages = 1
    private var adapter: MoviesAdapter? = null
    private var isScrolled = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        genre = GenresEnum.value(arguments?.getInt("idGenre"))

        page = 1
        totalPages = 1

        moviePresenter = MoviePresenter(this)

        setUpRecycler()
        moviePresenter?.requestingObjects(page, genre.id)

        swipeRefreshLayout.setOnRefreshListener {
            page = 1
            moviePresenter?.requestingObjects(page, genre.id)
        }

        recyclerViewMovies.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if( newState == RecyclerView.SCROLL_STATE_SETTLING ){
                    isScrolled = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastItem = (recyclerView.layoutManager as GridLayoutManager).findLastVisibleItemPosition()
                val total = recyclerView.layoutManager?.itemCount

                if (dy > 0 && lastItem == total?.minus(1) && page < totalPages && isScrolled) {
                    isScrolled = false
                    moviePresenter?.requestingObjects(page, genre.id)
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
        AppUtils.showAlert(message, activity!!)
        swipeRefreshLayout.isRefreshing = false
    }

    override fun showProgress() {
        showProgressLocal()
    }

    override fun hideProgress() {
        showProgressLocal(false)
    }

    override fun onSuccess(obj: Any, page: Int, totalPages: Int) {

        @Suppress("UNCHECKED_CAST")
        val movies = obj as ArrayList<Movie>

        this.totalPages = totalPages
        this.page++

        adapter?.movies = movies
        recyclerViewMovies.post {
            adapter?.movies = movies
            adapter?.notifyDataSetChanged()
        }
        swipeRefreshLayout.isRefreshing = false
    }

    private fun setUpRecycler(){
        recyclerViewMovies.layoutManager = GridLayoutManager(context, 2)
        adapter = MoviesAdapter(this, arrayListOf())
        recyclerViewMovies.adapter = adapter
        recyclerViewMovies.itemAnimator = DefaultItemAnimator()
    }

    private fun showProgressLocal(show: Boolean = true){
        if( show ) {
            frameProgress.visibility = View.VISIBLE
        }else{
            frameProgress.visibility = View.GONE
        }
    }

    companion object {
        fun newInstance(id: Int): MoviesFragment {
            val fragment = MoviesFragment()
            val args = Bundle()
            args.putInt("idGenre", id)
            fragment.arguments = args
            return fragment
        }
    }
}