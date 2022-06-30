package com.dmgpersonal.androidonkotlin.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dmgpersonal.androidonkotlin.R
import com.dmgpersonal.androidonkotlin.view.cities.CitiesListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, CitiesListFragment.newInstance())
                .commitNow()
        }
    }
}