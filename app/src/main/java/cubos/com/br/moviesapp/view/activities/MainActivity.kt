package cubos.com.br.moviesapp.view.activities

import android.app.SearchManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.view.Menu
import cubos.com.br.moviesapp.R
import cubos.com.br.moviesapp.view.adapter.GenresTabLayoutAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tabAdapter = GenresTabLayoutAdapter(supportFragmentManager)
        viewPager.adapter = tabAdapter

        tabLayout.setupWithViewPager(viewPager)
        val tab = tabLayout.getTabAt(0)
        tab?.select()
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

        searchView?.setOnQueryTextListener( object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

                return true;
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return false
            }

        })


        return super.onCreateOptionsMenu(menu)
    }
}
