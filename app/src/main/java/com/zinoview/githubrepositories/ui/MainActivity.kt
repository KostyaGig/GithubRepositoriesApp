package com.zinoview.githubrepositories.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu

import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import com.zinoview.githubrepositories.R
import com.zinoview.githubrepositories.core.GAApp
import com.zinoview.githubrepositories.ui.users.GithubObservableQuery
import com.zinoview.githubrepositories.ui.users.GithubUserAdapter
import com.zinoview.githubrepositories.ui.users.GithubUserItemViewTypeFactory
import com.zinoview.githubrepositories.ui.users.GithubUserViewHolderFactory
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


//todo remove it
fun Any.message(message: String) {
    Log.d("GithubTest",message)
}

class MainActivity : AppCompatActivity() {

    private val githubUserViewModel by lazy {
        (application as GAApp).githubUserViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //region this is need!

//        val repoDao = RepoDatabase.database(this).dao()

//        val repositories = service
//            .reposUser()
//            .subscribeOn(Schedulers.io())
//            .map {repositories ->
//                val cacheRepositories = repositories.map { it.map() }
//                repoDao.insertRepositories(cacheRepositories)
//                repositories
//            }
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                { repo ->
//                    message("Single -> onSuccess -> currentThread: ${Thread.currentThread().name}")
//                    repo.forEach {
//                        it.print()
//                    }
//                }, { error ->
//                    message("Single -> onError -> ${error.message} ")
//                }
//            )


//        findViewById<TextView>(R.id.tv).setOnClickListener {
//            repoDao.repositories()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({ repo ->
//                    message("Single -> onSuccess -> currentThread: ${Thread.currentThread().name}")
//                    repo.forEach {
//                        it.print()
//                    }
//                },{error ->
//                    message("Single -> onError -> ${error.message} ")
//                })
//        }
        //endregion

        val adapter = GithubUserAdapter(
            GithubUserItemViewTypeFactory(),
            GithubUserViewHolderFactory()
        )

        val recyclerView = findViewById<RecyclerView>(R.id.github_user_recycler_view)
        recyclerView.adapter = adapter
        githubUserViewModel.observe(this) {githubUserUiState ->
            adapter.update(githubUserUiState)
        }

        githubUserViewModel.user("KostyaGig")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.search_menu,menu)

        val searchItem = requireNotNull(menu?.findItem(R.id.action_search))
        val searchView = searchItem.actionView as SearchView

        val githubObservableQuery = GithubObservableQuery.Base()
        githubObservableQuery.query(searchView) { query ->
            githubUserViewModel.user(query)
        }

        return super.onCreateOptionsMenu(menu)
    }
}
