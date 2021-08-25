package com.zinoview.githubrepositories.sl.users

import com.zinoview.githubrepositories.data.core.Text
import com.zinoview.githubrepositories.data.users.DataGithubUserMapper
import com.zinoview.githubrepositories.data.users.GithubUserRepository
import com.zinoview.githubrepositories.data.users.cache.CacheGithubUserMapper
import com.zinoview.githubrepositories.data.users.cache.GithubUserCacheDataSource
import com.zinoview.githubrepositories.data.users.cloud.GithubUserCloudDataSource
import com.zinoview.githubrepositories.data.users.cloud.GithubUserService
import com.zinoview.githubrepositories.domain.users.DomainGithubUserMapper
import com.zinoview.githubrepositories.domain.users.GithubUserInteractor
import com.zinoview.githubrepositories.sl.core.BaseModule
import com.zinoview.githubrepositories.sl.core.CoreModule
import com.zinoview.githubrepositories.ui.core.BaseViewModel
import com.zinoview.githubrepositories.ui.repositories.GithubRepositoryViewModel
import com.zinoview.githubrepositories.ui.repositories.UiGithubRepositoryState
import com.zinoview.githubrepositories.ui.users.*
import com.zinoview.githubrepositories.ui.users.cache.Local
import com.zinoview.githubrepositories.ui.users.cache.LocalGithubUserRequest
import io.reactivex.disposables.CompositeDisposable


/**
 * @author Zinoview on 25.08.2021
 * k.gig@list.ru
 */
class GithubUserModule(
    private val coreModule: CoreModule
) : BaseModule<BaseViewModel<UiGithubUserState>> {

    override fun viewModel(): BaseViewModel<UiGithubUserState> {
        val githubUserInteractor = GithubUserInteractor.Base(
            GithubUserRepository.Base(
                GithubUserCloudDataSource.Base(
                    coreModule.service(GithubUserService::class.java)
                ),
                GithubUserCacheDataSource.Base(
                    coreModule.githubDao
                ),
                DataGithubUserMapper(coreModule.text),
                CacheGithubUserMapper()
            ),
            DomainGithubUserMapper()
        )
        val communication = GithubUserCommunication()
        val disposableStore = GithubDisposableStore.Base(CompositeDisposable())

        val mappers = Triple(
            UiGithubUserMapper(),
            UiGithubUserStateMapper(),
            coreModule.exceptionMapper
        )
        return GithubUserViewModel.Base(
            Remote(
                githubUserInteractor,
                communication,
                disposableStore,
                mappers.first,
                mappers.third
            ),
            Local(
                githubUserInteractor,
                communication,
                disposableStore,
                coreModule.resource,
                mappers
            ),
            disposableStore,
            communication
        )
    }
}