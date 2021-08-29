package com.zinoview.githubrepositories.sl.repositories

import com.zinoview.githubrepositories.data.repositories.DataGithubRepositoryMapper
import com.zinoview.githubrepositories.data.repositories.GithubRepositoryRepository
import com.zinoview.githubrepositories.data.repositories.cache.CacheGithubRepositoryMapper
import com.zinoview.githubrepositories.data.repositories.cache.GithubRepositoryCacheDataSource
import com.zinoview.githubrepositories.data.repositories.cloud.GithubRepositoryCloudDataSource
import com.zinoview.githubrepositories.data.repositories.cloud.GithubRepositoryService
import com.zinoview.githubrepositories.domain.repositories.DomainGithubRepositoryMapper
import com.zinoview.githubrepositories.domain.repositories.GithubRepositoryInteractor
import com.zinoview.githubrepositories.sl.core.BaseModule
import com.zinoview.githubrepositories.sl.core.CoreModule
import com.zinoview.githubrepositories.ui.core.BaseViewModel
import com.zinoview.githubrepositories.ui.repositories.*
import com.zinoview.githubrepositories.core.GithubDisposableStore
import com.zinoview.githubrepositories.ui.core.SaveCache
import com.zinoview.githubrepositories.ui.repositories.cache.Local
import io.reactivex.disposables.CompositeDisposable


/**
 * @author Zinoview on 25.08.2021
 * k.gig@list.ru
 */
class GithubRepositoryModule(
    private val coreModule: CoreModule
) : BaseModule<BaseViewModel<UiGithubRepositoryState>> {

    override fun viewModel(): BaseViewModel<UiGithubRepositoryState> {
        val communication = GithubRepositoryCommunication()
        val disposableStore = GithubDisposableStore.Base(CompositeDisposable())

        val mappers = Triple(
            UiGithubRepositoryMapper(),
            UiGithubRepositoryStateMapper(),
            coreModule.exceptionMapper
        )

        val interactor = GithubRepositoryInteractor.Base(
            GithubRepositoryRepository.Base(
                GithubRepositoryCacheDataSource.Base(
                    coreModule.githubDao
                ),
                GithubRepositoryCloudDataSource.Base(
                    coreModule.service(GithubRepositoryService::class.java)
                ),
                CacheGithubRepositoryMapper(),
                DataGithubRepositoryMapper(coreModule.text)
            ),
            DomainGithubRepositoryMapper(),
        )
        return GithubRepositoryViewModel.Base(
            Remote.Base(
                interactor,
                communication,
                disposableStore,
                mappers
            ),
            Local.Base(
                interactor,
                communication,
                disposableStore,
                coreModule.resource,
                Pair(
                    mappers.first,
                    mappers.second
                )
            ),
            communication,
            disposableStore,
            SaveCache.Repository(coreModule.githubDao,com.zinoview.githubrepositories.ui.repositories.CacheGithubRepositoryMapper.Base())
        )
    }
}