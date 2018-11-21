package cubos.com.br.moviesapp.view.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import cubos.com.br.moviesapp.model.Movie

class DetailActivity : AppCompatActivity(){

    var movie : Movie? = null

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if( intent.hasExtra("movie") ){
            movie = intent.extras.getSerializable("movie") as Movie
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        val id = item?.itemId

        if( id == android.R.id.home ){
            finish()
        }

        return super.onOptionsItemSelected(item)
    }
}