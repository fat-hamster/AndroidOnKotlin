package com.dmgpersonal.androidonkotlin.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.dmgpersonal.androidonkotlin.R
import com.dmgpersonal.androidonkotlin.view.cities.CitiesListFragment
import com.dmgpersonal.androidonkotlin.view.history.HistoryListFragment
import com.dmgpersonal.androidonkotlin.view.map.MapsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, CitiesListFragment.newInstance())
                .commitNow()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.history_menu_item -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, HistoryListFragment.newInstance())
                    .addToBackStack("")
                    .commit()
            }

            R.id.map_menu_item -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MapsFragment())
                    .addToBackStack("")
                    .commit()
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }

        return true
    }
}