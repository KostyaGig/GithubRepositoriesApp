package com.zinoview.githubrepositories.ui.users.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import com.zinoview.githubrepositories.R
import com.zinoview.githubrepositories.core.GAApp
import com.zinoview.githubrepositories.ui.core.BaseFragment
import com.zinoview.githubrepositories.ui.repositories.fragment.GithubRepositoriesFragment
import com.zinoview.githubrepositories.ui.users.*
import com.zinoview.githubrepositories.ui.core.UiTotalCache
import io.reactivex.disposables.CompositeDisposable


/**
 * @author Zinoview on 22.08.2021
 * k.gig@list.ru
 */
class GithubUsersFragment : BaseFragment(R.layout.github_user_fragment) {

    private val githubUserViewModel by lazy {
        (requireActivity().application as GAApp).githubUserViewModel
    }

    lateinit var githubUserTotalCache: UiTotalCache<UiGithubUserState>
    lateinit var adapter: GithubUserAdapter
    lateinit var githubQueryDisposableStore: GithubDisposableStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        githubQueryDisposableStore = GithubDisposableStore.Base(CompositeDisposable())
        changeTitleToolbar("Github Users")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        githubUserTotalCache = User(ArrayList())

        adapter = GithubUserAdapter(
            GithubUserItemViewTypeFactory(),
            GithubUserViewHolderFactory(object : GithubOnItemClickListener {
                override fun onItemClick(githubUserName: String) {
                    val bundle = Bundle().apply {
                        putString(GITHUB_USER_NAME_EXTRA,githubUserName)
                    }
                    val githubRepositoriesFragment = GithubRepositoriesFragment().apply {
                        arguments = bundle
                    }
                    val transaction = requireActivity().supportFragmentManager.beginTransaction()
                    with(transaction) {
                        replace(R.id.container,githubRepositoriesFragment)
                        commit()
                    }

                }
            })
        )

        githubUserTotalCache.addAdapter(adapter)

        val recyclerView = view.findViewById<RecyclerView>(R.id.github_user_recycler_view)
        recyclerView.adapter = adapter

        githubUserViewModel.observe(this) { githubUserUiState ->
            githubUserTotalCache.add(githubUserUiState)
            adapter.update(githubUserUiState)
        }

        githubUserViewModel.users()
    }

    override fun searchByQuery(searchView: SearchView) {
        val githubObservableQuery = GithubObservableQuery.Base(githubQueryDisposableStore)
        githubObservableQuery.query(searchView,
            { query ->
                githubUserViewModel.cachedUser(query)
            }, {
                //if empty query show recyclerview with data
                githubUserTotalCache.updateAdapter()
            }
        )
    }

    override fun collapseState() {
        githubUserTotalCache.updateAdapter()
    }

    override fun onStop() {
        githubQueryDisposableStore.dispose()
        super.onStop()
    }

    override fun previousFragment(): BaseFragment = MockBaseFragment()
}