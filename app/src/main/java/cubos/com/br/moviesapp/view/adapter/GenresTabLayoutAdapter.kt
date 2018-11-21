package cubos.com.br.moviesapp.view.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import cubos.com.br.moviesapp.enums.GenresEnum
import cubos.com.br.moviesapp.view.fragments.MoviesFragment

class GenresTabLayoutAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> MoviesFragment.newInstance(GenresEnum.ACTION.id)
            1 -> MoviesFragment.newInstance(GenresEnum.DRAMA.id)
            2 -> MoviesFragment.newInstance(GenresEnum.FANTASY.id)
            3 -> MoviesFragment.newInstance(GenresEnum.FICTION.id)
            else -> MoviesFragment.newInstance(GenresEnum.ACTION.id)
        }
    }

    override fun getPageTitle(position: Int) = when (position) {
        0 -> GenresEnum.ACTION.title
        1 -> GenresEnum.DRAMA.title
        2 -> GenresEnum.FANTASY.title
        3-> GenresEnum.FICTION.title
        else -> GenresEnum.ACTION.title
    }

    override fun getCount(): Int {
        return 4
    }
}