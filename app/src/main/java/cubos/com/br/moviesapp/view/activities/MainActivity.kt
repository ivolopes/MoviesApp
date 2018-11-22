package cubos.com.br.moviesapp.view.activities

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import cubos.com.br.moviesapp.R
import cubos.com.br.moviesapp.model.Movie
import cubos.com.br.moviesapp.presenter.MainPresenter
import cubos.com.br.moviesapp.utils.AppUtils
import cubos.com.br.moviesapp.view.adapter.GenresTabLayoutAdapter
import cubos.com.br.moviesapp.view.adapter.MoviesAdapter
import cubos.com.br.moviesapp.view.fragments.MovieClicked
import cubos.com.br.moviesapp.view.interfaces.MainView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MovieClicked, MainView {

    private var mainPresenter: MainPresenter? = null
    private var query = ""
    private var page = 1
    private var adapter: MoviesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        mainPresenter = MainPresenter(this)
        initTab()
        setUpRecycler()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.menu_search, menu)

        val searchItem = menu?.findItem(R.id.action_search)

        val searchManager = getSystemService(android.content.Context.SEARCH_SERVICE) as SearchManager

        var searchView: SearchView? = null
        if (searchItem != null) {
            searchView = searchItem.actionView as SearchView
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }

        MenuItemCompat.setOnActionExpandListener(searchItem, object : MenuItemCompat.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                showListMain()
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                showListMain(false)
                query = ""
                return true
            }
        })

        searchView?.setOnQueryTextListener( object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                if( newText != null && newText.trim() != "") {
                    query = newText
                    mainPresenter?.requestingObjects(query, page)
                }
                return false
            }

        })


        return super.onCreateOptionsMenu(menu)
    }

    override fun onSuccess(obj: Any, page: Int, totalPages: Int) {

        @Suppress("UNCHECKED_CAST")
        val movies = obj as ArrayList<Movie>

        recyclerViewMoviesMain.post {
            adapter?.movies = movies
            adapter?.notifyDataSetChanged()
        }

    }

    override fun onError(message: String) {
        AppUtils.showAlert(message, this)
    }

    override fun onMovieClicked(movie: Movie) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("movie", movie)
        startActivity(intent)
    }

    private fun showListMain(show: Boolean = true){

        if( show ){
            tabLayout.visibility = View.GONE
            viewPager.visibility = View.GONE
            recyclerViewMoviesMain.visibility = View.VISIBLE
        }else{
            tabLayout.visibility = View.VISIBLE
            viewPager.visibility = View.VISIBLE
            recyclerViewMoviesMain.visibility = View.GONE
            initTab()
        }
    }

    private fun initTab(){
        val tabAdapter = GenresTabLayoutAdapter(supportFragmentManager)
        viewPager.adapter = tabAdapter

        tabLayout.setupWithViewPager(viewPager)
        val tab = tabLayout.getTabAt(0)
        tab?.select()
    }

    private fun setUpRecycler(){
        recyclerViewMoviesMain.layoutManager = GridLayoutManager(this, 2)
        adapter = MoviesAdapter(this, arrayListOf())
        recyclerViewMoviesMain.adapter = adapter
        recyclerViewMoviesMain.itemAnimator = DefaultItemAnimator()
    }
}
