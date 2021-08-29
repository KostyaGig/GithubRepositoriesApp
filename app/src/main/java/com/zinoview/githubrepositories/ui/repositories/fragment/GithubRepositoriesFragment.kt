package com.zinoview.githubrepositories.ui.repositories.fragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import com.zinoview.githubrepositories.R
import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.core.GithubDisposableStore
import com.zinoview.githubrepositories.ui.core.UiTotalCache
import com.zinoview.githubrepositories.ui.core.BaseFragment
import com.zinoview.githubrepositories.ui.core.CollapseOrExpandListener
import com.zinoview.githubrepositories.ui.core.GithubObservableQuery
import com.zinoview.githubrepositories.ui.repositories.*
import com.zinoview.githubrepositories.ui.repositories.cache.RepositoriesTotalCache
import com.zinoview.githubrepositories.ui.users.CollapseOrExpandState
import com.zinoview.githubrepositories.ui.users.CollapseOrExpandStateFactory
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

        githubRepositoryTotalCache = RepositoriesTotalCache(ArrayList()) { replacedItem ->
            githubRepositoryViewModel.saveData(replacedItem.wrap())
        }

        arguments?.let {
            val userName = it.getString(GITHUB_USER_NAME_EXTRA)
            githubUserName.addName(userName)

            changeTitleToolbar(R.string.repos, userName)

            adapter = GithubRepositoryAdapter(
                GithubRepositoryItemViewTypeFactory(),
                GithubRepositoryViewHolderFactory(
                    object : CollapseOrExpandListener<UiGithubRepositoryState> {
                        override fun onChangeCollapseState(item: UiGithubRepositoryState, position: Int) {
                            githubRepositoryTotalCache.add(item)
                            adapter.update(item,position)
                        }
                    })
                )

            githubRepositoryTotalCache.addAdapter(adapter)

            val recyclerView = view.findViewById<RecyclerView>(R.id.github_repository_recycler_view)
            recyclerView.adapter = adapter

            userName?.let { name ->
                githubRepositoryViewModel.data(name)
            }

            githubRepositoryViewModel.observe(this) { uiGithubRepositoryState ->

                //На этапе прихода нам стейта Base нужно проверять empty он или нет
                //Если empty: отдать в коммуникации Empty state
                //todo handle state,which our sent empty list
                //todo which repo already cached and we search repo by name sometimes maybe не быть такого препозитория,у мен ыскакиевет somewentwrong task: watching class exception and correctly handle error
                githubRepositoryTotalCache.add(uiGithubRepositoryState)
                adapter.update(uiGithubRepositoryState)
            }
        }
    }

    override fun dataByState(state: CollapseOrExpandState)
        = githubUserName.repositoriesByState(state,githubRepositoryViewModel)

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