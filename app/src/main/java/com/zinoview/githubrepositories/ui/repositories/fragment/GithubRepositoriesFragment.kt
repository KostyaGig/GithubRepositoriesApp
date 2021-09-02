package com.zinoview.githubrepositories.ui.repositories.fragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import com.zinoview.githubrepositories.R
import com.zinoview.githubrepositories.core.GithubDisposableStore
import com.zinoview.githubrepositories.data.repositories.cache.prefs.RepositoryCachedState
import com.zinoview.githubrepositories.ui.core.*
import com.zinoview.githubrepositories.ui.core.adapter.CollapseOrExpandStateListener
import com.zinoview.githubrepositories.ui.core.cache.StoreListTotalCache
import com.zinoview.githubrepositories.ui.core.cache.UiTotalCache
import com.zinoview.githubrepositories.ui.repositories.*
import com.zinoview.githubrepositories.ui.repositories.cache.RepositoriesTotalCache
import com.zinoview.githubrepositories.ui.users.CollapseOrExpandState
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
    private lateinit var titleToolbar: TitleToolbar

    private val githubUserName: Name = Name.GithubUserName()

    private val githubRepositoryViewModel by lazy  {
        viewModel(GithubRepositoryViewModel.Base::class.java,this)
    }

    private val repositoryCachedState by lazy {
        cachedState(RepositoryCachedState.Base::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        githubQueryDisposableStore = GithubDisposableStore.Base(CompositeDisposable())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val itemsState = ItemsState.Base(repositoryCachedState)
        titleToolbar = TitleToolbar.Base(itemsState)
        githubRepositoryTotalCache = RepositoriesTotalCache(
            itemsState,
            StoreListTotalCache.Base(
                ArrayList(),
                ArrayList(),
                ArrayList()
            ) { replacedItem ->
                githubRepositoryViewModel.saveData(replacedItem.wrap())
            })

        arguments?.let {
            val userName = it.getString(GITHUB_USER_NAME_EXTRA)
            githubUserName.addName(userName)

            changeTitleToolbar(R.string.repos, titleToolbar.title(githubUserName))

            adapter = GithubRepositoryAdapter(
                GithubRepositoryItemViewTypeFactory(),
                GithubRepositoryViewHolderFactory(
                    object : CollapseOrExpandStateListener<UiGithubRepositoryState> {
                        override fun onChangeCollapseState(item: UiGithubRepositoryState, position: Int) {
                            githubRepositoryTotalCache.add(item)
                            adapter.update(item,position)
                        }
                    })
                )

            githubRepositoryTotalCache.addAdapter(adapter)

            val recyclerView = view.findViewById<RecyclerView>(R.id.github_repository_recycler_view)
            recyclerView.adapter = adapter

            githubRepositoryViewModel.observe(this) { uiGithubRepositoryState ->
                githubRepositoryTotalCache.add(uiGithubRepositoryState)
                adapter.update(uiGithubRepositoryState)
            }

            githubUserName.repositories(githubRepositoryViewModel)
        }
    }

    override fun dataByState(state: CollapseOrExpandState) {
        githubRepositoryViewModel.saveState(state)
        githubRepositoryTotalCache.updateCurrentItemsState(state)
        githubUserName.repositories(githubRepositoryViewModel)
        changeTitleToolbar(R.string.repos,titleToolbar.title(githubUserName))
    }

    override fun searchByQuery(searchView: SearchView) {
        val githubObservableQuery = GithubObservableQuery.Base(githubQueryDisposableStore)
        githubObservableQuery.query(searchView,
            { searchQuery ->
                githubUserName.searchRepository(searchQuery,githubRepositoryViewModel)
            }, {
                //if empty query show recyclerview with data
                githubRepositoryTotalCache.updateAdapter()
            }
        )
    }

    override fun menuCollapsedState() {
        githubRepositoryTotalCache.updateAdapter()
    }

    override fun onDestroy() {
        githubQueryDisposableStore.dispose()
        super.onDestroy()
    }

    override fun previousFragment(): BaseFragment = GithubUsersFragment()
}