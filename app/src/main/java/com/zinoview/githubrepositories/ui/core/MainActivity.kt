package com.zinoview.githubrepositories.ui.core

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.zinoview.githubrepositories.R
import com.zinoview.githubrepositories.ui.users.fragment.GithubUsersFragment

//todo remove it
fun Any.message(message: String) {
    Log.d("GithubTest",message)
}

class MainActivity : AppCompatActivity() {

    val toolbar by lazy {
       supportActionBar
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container,GithubUsersFragment())
            .commit()
    }

    override fun onBackPressed() {
        val baseFragments = supportFragmentManager.fragments.map { it as BaseFragment }
        if(baseFragments[0].onBackPressed()) super.onBackPressed()
    }
}
