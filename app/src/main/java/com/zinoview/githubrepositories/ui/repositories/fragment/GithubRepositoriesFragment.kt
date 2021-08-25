package com.zinoview.githubrepositories.ui.repositories.fragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import com.zinoview.githubrepositories.R
import com.zinoview.githubrepositories.core.GAApp
import com.zinoview.githubrepositories.ui.core.BaseFragment
import com.zinoview.githubrepositories.ui.repositories.*
import com.zinoview.githubrepositories.ui.users.*
import com.zinoview.githubrepositories.ui.core.UiTotalCache
import com.zinoview.githubrepositories.ui.repositories.cache.Repository
import com.zinoview.githubrepositories.ui.users.fragment.GithubUsersFragment
import io.reactivex.disposables.CompositeDisposable


/**
 * @author Zinoview on 22.08.2021
 * k.gig@list.ru
 */

class GithubRepositoriesFragment : BaseFragment(R.layout.github_repository_fragment) {

    private lateinit var adapter: GithubRepositoryAdapter
    private lateinit var githubQueryDisposableStore: GithubDisposableStore
    private lateinit var githubRepositoryTotalCache: UiTotalCache<UiGithubRepositoryState>

    private val githubUserName: Name = Name.GithubUserName()

    private val githubRepositoryViewModel by lazy  {
        viewModel(GithubRepositoryViewModel.Base::class.java,this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        githubQueryDisposableStore = GithubDisposableStore.Base(CompositeDisposable())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        githubRepositoryTotalCache = Repository(ArrayList())

        arguments?.let {
            val userName = it.getString(GITHUB_USER_NAME_EXTRA)
            githubUserName.addName(userName)

            changeTitleToolbar("$userName/repos")

            adapter = GithubRepositoryAdapter(
                GithubRepositoryItemViewTypeFactory(),
                GithubRepositoryViewHolderFactory()
            )

            githubRepositoryTotalCache.addAdapter(adapter)

            val recyclerView = view.findViewById<RecyclerView>(R.id.github_repository_recycler_view)
            recyclerView.adapter = adapter

            userName?.let { name ->
                githubRepositoryViewModel.repositories(name)
            }

            githubRepositoryViewModel.observe(this) { uiGithubRepositoryState ->
                githubRepositoryTotalCache.add(uiGithubRepositoryState)
                adapter.update(uiGithubRepositoryState)
            }
        }
    }

    override fun searchByQuery(searchView: SearchView) {
        val githubObservableQuery = GithubObservableQuery.Base(githubQueryDisposableStore)
        githubObservableQuery.query(searchView,
            { query ->
                githubUserName.searchRepository(query,githubRepositoryViewModel)
            }, {
                //if empty query show recyclerview with data
                githubRepositoryTotalCache.updateAdapter()
            }
        )
    }

    override fun collapseState() {
        githubRepositoryTotalCache.updateAdapter()
    }

    override fun onStop() {
        githubQueryDisposableStore.dispose()
        super.onStop()
    }

    override fun previousFragment(): BaseFragment = GithubUsersFragment()
}