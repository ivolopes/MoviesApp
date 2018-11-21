package cubos.com.br.moviesapp.view.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cubos.com.br.moviesapp.presenter.MainPresenter
import com.example.ivo.mvpteste.view.interfaces.MainView
import cubos.com.br.moviesapp.R
import cubos.com.br.moviesapp.enums.GenresEnum
import cubos.com.br.moviesapp.model.Movie
import cubos.com.br.moviesapp.view.adapter.MoviesAdapter
import kotlinx.android.synthetic.main.fragment_movies.*

class MoviesFragment : Fragment(), MovieClicked, MainView{

    private var mainPresenter: MainPresenter? = null

    lateinit var idGenre : GenresEnum
    private var nextPage = 1
    private var adapter: MoviesAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TODO dependency injection
        mainPresenter = MainPresenter(this)

        setUpRecycler()
        showProgress()
        mainPresenter?.requestingObjects(nextPage, idGenre.id)
    }

    override fun onMovieClicked(movie: Movie) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onError(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSuccess(obj: Any, page: Int, totalPages: Int) {

        @Suppress("UNCHECKED_CAST")
        val movies = obj as ArrayList<Movie>

        adapter?.movies = movies
        recyclerViewMovies.post {
            showProgress(false)
            adapter?.movies = movies
            adapter?.notifyDataSetChanged()
        }
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