package cubos.com.br.moviesapp.view.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.squareup.picasso.Picasso
import cubos.com.br.moviesapp.BuildConfig
import cubos.com.br.moviesapp.R
import cubos.com.br.moviesapp.model.Movie
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity(){

    var movie : Movie? = null

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if( intent.hasExtra("movie") ){
            movie = intent.extras["movie"] as Movie
        }

        supportActionBar?.title = movie?.title
        textViewDescription.text = movie?.overview

        Picasso.get().load(BuildConfig.SERVER_URL_IMAGE + movie?.posterPath).into(imageViewPictureMovie)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        val id = item?.itemId

        if( id == android.R.id.home ){
            finish()
        }

        return super.onOptionsItemSelected(item)
    }
}