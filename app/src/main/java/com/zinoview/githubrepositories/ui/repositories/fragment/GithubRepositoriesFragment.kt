package com.zinoview.githubrepositories.ui.repositories.fragment

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import androidx.work.WorkManager
import com.zinoview.githubrepositories.R
import com.zinoview.githubrepositories.core.DisposableStore
import com.zinoview.githubrepositories.core.GAApp
import com.zinoview.githubrepositories.data.repositories.cache.prefs.RepositoryCachedState
import com.zinoview.githubrepositories.ui.core.*
import com.zinoview.githubrepositories.ui.core.adapter.CollapseOrExpandStateListener
import com.zinoview.githubrepositories.ui.core.adapter.GithubOnItemClickListener
import com.zinoview.githubrepositories.ui.core.cache.StoreListTotalCache
import com.zinoview.githubrepositories.ui.core.cache.UiTempCache
import com.zinoview.githubrepositories.ui.repositories.*
import com.zinoview.githubrepositories.ui.repositories.cache.RepositoriesTempCache
import com.zinoview.githubrepositories.ui.repositories.download.TempRepository
import com.zinoview.githubrepositories.ui.repositories.download.UiGithubDownloadFileState
import com.zinoview.githubrepositories.ui.users.CollapseOrExpandState
import com.zinoview.githubrepositories.ui.users.fragment.GithubUsersFragment
import io.reactivex.disposables.CompositeDisposable


/**
 * @author Zinoview on 22.08.2021
 * k.gig@list.ru
 */

class GithubRepositoriesFragment : BaseFragment(R.layout.github_repository_fragment) {

    private lateinit var adapter: GithubRepositoryAdapter
    private lateinit var githubQueryDisposableStore: DisposableStore
    private lateinit var githubRepositoryTotalCache: UiTempCache<UiGithubRepositoryState>
    private lateinit var titleToolbar: TitleToolbar

    private val githubUserName: TempGithubUserName = TempGithubUserName.GithubUserName()
    private var githubTempRepository = TempRepository.Base("","")

    private val githubRepositoryViewModel by lazy  {
        viewModel(GithubRepositoryViewModel.Base::class.java,this)
    }

    private val repositoryCachedState by lazy {
        cachedState(RepositoryCachedState.Base::class.java)
    }

    private val requestAccessWriteExternalStoragePermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        isGranted.downloadRepository()
    }

    private fun Boolean.downloadRepository() {
        if (this) githubTempRepository.download(githubRepositoryViewModel)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        githubQueryDisposableStore = DisposableStore.Base(CompositeDisposable())
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val itemsState = ItemsState.Base(repositoryCachedState)
        titleToolbar = TitleToolbar.Base(itemsState)
        githubRepositoryTotalCache = RepositoriesTempCache(
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
                GithubRepositoryItemViewTypeFactory.Base(),
                GithubRepositoryViewHolderFactory.Base(
                    object : GithubOnItemClickListener<Pair<String,String>> {
                        override fun onItemClick(data: Pair<String,String>) {
                            githubTempRepository = githubTempRepository.newTempRepository(data.first,data.second)
                            requestAccessWriteExternalStoragePermission()
                        }
                    },
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

            val workManager = WorkManager.getInstance(requireContext())

            githubRepositoryViewModel.downloadRepoObserve(this) { uiGithubDownloadRepositoryState ->
                uiGithubDownloadRepositoryState.forEach {
                    it.map(workManager)
                }
            }

            githubUserName.repositories(githubRepositoryViewModel)
        }

        //TODO просто протестить получаем ли мы размерность данных в ворк ьэнэджере,щас закончился лимит
        //todo поулчить инстанс фабрики workmanager'a,затем протестить сохранение файла
        //todo посомтреть можно ли передать нашему ,недавно созданному workmanager'у в конструктор FileWriter для записи в файл
        //В качестве входящих данных (inputData) в ворк manager передавать responseBody,назодящийся в стэйте WaitingDownload
        //Нужно отслеживать состояние прогресса,успеха,фэйла
        //Также сделать update загрузки файла
        //Решить проблему с InputStream (закрывать его)
    }

    private fun requestAccessWriteExternalStoragePermission() {
        requestAccessWriteExternalStoragePermission.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
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