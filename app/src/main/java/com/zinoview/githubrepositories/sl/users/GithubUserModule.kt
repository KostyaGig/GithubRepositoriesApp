package com.zinoview.githubrepositories.sl.users

import com.zinoview.githubrepositories.core.DisposableStore
import com.zinoview.githubrepositories.data.users.DataGithubUserMapper
import com.zinoview.githubrepositories.data.users.GithubUserRepository
import com.zinoview.githubrepositories.data.users.cache.CacheGithubUserMapper
import com.zinoview.githubrepositories.data.users.cache.GithubUserCacheDataSource
import com.zinoview.githubrepositories.data.users.cloud.GithubUserCloudDataSource
import com.zinoview.githubrepositories.data.users.cloud.GithubUserService
import com.zinoview.githubrepositories.domain.core.DomainDownloadExceptionMapper
import com.zinoview.githubrepositories.domain.users.DomainGithubUserMapper
import com.zinoview.githubrepositories.domain.users.GithubUserInteractor
import com.zinoview.githubrepositories.sl.core.BaseModule
import com.zinoview.githubrepositories.sl.core.CoreModule
import com.zinoview.githubrepositories.ui.core.BaseViewModel
import com.zinoview.githubrepositories.ui.core.cache.SaveCache
import com.zinoview.githubrepositories.ui.users.*
import com.zinoview.githubrepositories.ui.users.cache.Local
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
                    coreModule.networkService(GithubUserService::class.java)
                ),
                GithubUserCacheDataSource.Base(
                    coreModule.githubDao
                ),
                DataGithubUserMapper(coreModule.text),
                CacheGithubUserMapper(),
                coreModule.userCachedState),
            DomainGithubUserMapper(),
            DomainDownloadExceptionMapper.Base()
        )
        val communication = GithubUserCommunication()
        val disposableStore = DisposableStore.Base(CompositeDisposable())

        val userMappersStore = UserMappersStore.Base(
            UiGithubUserMapper(),
            UiGithubUserStateMapper(),
            coreModule.exceptionMapper
        )

        return GithubUserViewModel.Base(
            Remote(
                githubUserInteractor,
                communication,
                disposableStore,
                userMappersStore
            ),
            Local(
                githubUserInteractor,
                communication,
                disposableStore,
                coreModule.resource,
                userMappersStore
            ),
            disposableStore,
            communication,
            SaveCache.User(
                coreModule.githubDao,
                com.zinoview.githubrepositories.ui.users.CacheGithubUserMapper.Base()
            )
        )
    }
}