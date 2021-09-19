package com.zinoview.githubrepositories.ui.users.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import com.zinoview.githubrepositories.R
import com.zinoview.githubrepositories.core.DisposableStore
import com.zinoview.githubrepositories.data.users.cache.prefs.UserCachedState
import com.zinoview.githubrepositories.ui.core.cache.UiTempCache
import com.zinoview.githubrepositories.ui.core.*
import com.zinoview.githubrepositories.ui.repositories.fragment.GithubRepositoriesFragment
import com.zinoview.githubrepositories.ui.users.*
import com.zinoview.githubrepositories.ui.core.MockBaseFragment
import com.zinoview.githubrepositories.ui.core.adapter.CollapseOrExpandStateListener
import com.zinoview.githubrepositories.ui.core.adapter.GithubOnItemClickListener
import com.zinoview.githubrepositories.ui.core.cache.StoreListTotalCache
import com.zinoview.githubrepositories.ui.users.cache.UsersTempCache
import io.reactivex.disposables.CompositeDisposable


/**
 * @author Zinoview on 22.08.2021
 * k.gig@list.ru
 */

class GithubUsersFragment : BaseFragment(R.layout.github_user_fragment) {

    private lateinit var githubUserTotalCache: UiTempCache<UiGithubUserState>
    private lateinit var adapter: GithubUserAdapter
    private lateinit var githubQueryDisposableStore: DisposableStore

    private lateinit var titleToolbar: TitleToolbar

    private val githubUserViewModel by lazy {
        viewModel(GithubUserViewModel.Base::class.java,this)
    }

    private val userCachedState by lazy {
        cachedState(UserCachedState.Base::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        githubQueryDisposableStore = DisposableStore.Base(CompositeDisposable())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val itemsState = ItemsState.Base(userCachedState)
        titleToolbar = TitleToolbar.Base(itemsState)

        changeTitleToolbar(R.string.users_page,titleToolbar.title())
        githubUserTotalCache = UsersTempCache(
            itemsState,
            StoreListTotalCache.Base(
                ArrayList(),
                ArrayList(),
                ArrayList()
        ) { replacedItem ->
            githubUserViewModel.saveData(replacedItem.wrap())
        })

        adapter = GithubUserAdapter(
            GithubUserItemViewTypeFactory.Base(),
            GithubUserViewHolderFactory.Base(object : GithubOnItemClickListener<String> {
                override fun onItemClick(githubUserName: String) {
                    val bundle = Bundle().apply {
                        putString(GITHUB_USER_NAME_EXTRA,githubUserName)
                    }
                    val githubRepositoriesFragment = GithubRepositoriesFragment(
                        requireActivity() as MainActivity
                    ).apply {
                        arguments = bundle
                    }
                    val transaction = requireActivity().supportFragmentManager.beginTransaction()
                    with(transaction) {
                        replace(R.id.container,githubRepositoriesFragment)
                        commit()
                    }
                }
            }, object : CollapseOrExpandStateListener<UiGithubUserState> {
                override fun onChangeCollapseState(item: UiGithubUserState, position: Int) {
                    githubUserTotalCache.add(item)
                    adapter.update(item,position)
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

        //Example: current selection state Expanded i change state и надо удалять его сразу из списка
        githubUserViewModel.users()
    }

    override fun dataByState(state: CollapseOrExpandState) {
        githubUserViewModel.saveState(state)
        githubUserTotalCache.updateCurrentItemsState(state)
        githubUserViewModel.users()
        changeTitleToolbar(R.string.users_page,titleToolbar.title())
    }

    override fun searchByQuery(searchView: SearchView) {
        val githubObservableQuery = GithubObservableQuery.Base(githubQueryDisposableStore)
        githubObservableQuery.query(searchView,
            { searchQuery ->
                githubUserViewModel.user(searchQuery)
            }, {
                //if empty query show recyclerview with data
                githubUserTotalCache.updateAdapter()
            }
        )
    }

    override fun menuCollapsedState() {
        githubUserTotalCache.updateAdapter()
    }

    override fun onDestroy() {
        githubQueryDisposableStore.dispose()
        super.onDestroy()
    }

    override fun previousFragment(): BaseFragment = MockBaseFragment()
}