package com.cuplix.cupita

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.cuplix.cupita.core.utils.CheckNetworkConnection
import com.cuplix.cupita.databinding.ActivityMainBinding
import com.cuplix.cupita.movies.MovieFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        CheckNetworkConnection(this).checkConnection()
        setFragment(MovieFragment())
        initNav()

    }

    private fun initNav() {

        mainBinding.equalNavigationBar.setNavigationChangeListener { _, position ->
            when (position) {
                0 -> setFragment(MovieFragment())
                1 -> moveToFavoriteFragment()
            }
        }

    }

    private fun setFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().apply {
            replace(R.id.container_main_fragment, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        }.commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount <= 1) {
            finish()
        } else {
            super.onBackPressed()
        }
    }

    private fun moveToFavoriteFragment() {
        val fragment = instantiateFragment()
        Log.d("fragmentName", fragment.toString())
        if (fragment != null) {
            setFragment(fragment)
        }
    }

    private fun instantiateFragment(): Fragment? {
        return try {
            Class.forName("com.cuplix.favorite.FavoriteFragment").newInstance() as Fragment
        } catch (e: Exception) {
            Toast.makeText(this, "Module not found", Toast.LENGTH_SHORT).show()
            null
        }
    }

}