package com.dmgpersonal.androidonkotlin.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dmgpersonal.androidonkotlin.R
import com.dmgpersonal.androidonkotlin.view.cities.CitiesListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, CitiesListFragment.newInstance())
                .commitNow()
        }

//        val root = findViewById<ConstraintLayout>(R.id.rootLayout)
//        val connectionLiveData = ConnectionLiveData(root.context)
//        connectionLiveData.observe(this) { isConnected ->
//            isConnected?.let {
//                when (it) {
//                    true -> onAlertDialog(root, "Network is available")
//                    false -> onAlertDialog(root, "Network is not available")
//                }
//            }
//        }
    }

}