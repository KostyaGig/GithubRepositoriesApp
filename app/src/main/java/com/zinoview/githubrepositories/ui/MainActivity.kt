package com.zinoview.githubrepositories.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import com.zinoview.githubrepositories.R
import com.zinoview.githubrepositories.core.GAApp
import com.zinoview.githubrepositories.ui.core.BaseActivity
import com.zinoview.githubrepositories.ui.users.*
import com.zinoview.githubrepositories.ui.users.cache.GithubUserTotalCache
import io.reactivex.disposables.CompositeDisposable

//todo remove it
fun Any.message(message: String) {
    Log.d("GithubTest",message)
}

class MainActivity : BaseActivity() {

    private val githubUserViewModel by lazy {
        (application as GAApp).githubUserViewModel
    }

    lateinit var githubUserTotalCache: GithubUserTotalCache<UiGithubUserState>
    lateinit var adapter: GithubUserAdapter
    lateinit var githubQueryDisposableStore: GithubDisposableStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        githubUserTotalCache = GithubUserTotalCache.Base(ArrayList())
        githubQueryDisposableStore = GithubDisposableStore.Base(CompositeDisposable())

        adapter = GithubUserAdapter(
            GithubUserItemViewTypeFactory(),
            GithubUserViewHolderFactory()
        )

        githubUserTotalCache.addAdapter(adapter)


        val recyclerView = findViewById<RecyclerView>(R.id.github_user_recycler_view)
        recyclerView.adapter = adapter

        githubUserViewModel.observe(this) { githubUserUiState ->
            githubUserTotalCache.add(githubUserUiState)
            adapter.update(githubUserUiState)
        }

        githubUserViewModel.users()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.search_menu,menu)

        val searchItem = requireNotNull(menu?.findItem(R.id.action_search))
        val searchView = searchItem.actionView as SearchView

        searchItem.collapseState {
            githubUserTotalCache.updateAdapter()
        }

        val githubObservableQuery = GithubObservableQuery.Base(githubQueryDisposableStore)
        githubObservableQuery.query(searchView,
            { query ->
                githubUserViewModel.cachedUser(query)
            }, {
                //if empty query show recyclerview with data
                githubUserTotalCache.updateAdapter()
            }
        )

        return super.onCreateOptionsMenu(menu)
    }

    override fun onStop() {
        githubQueryDisposableStore.dispose()
        super.onStop()
    }

}
