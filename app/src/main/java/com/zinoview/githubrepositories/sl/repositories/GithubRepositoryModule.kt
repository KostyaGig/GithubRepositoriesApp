package com.zinoview.githubrepositories.sl.repositories

import com.zinoview.githubrepositories.data.repositories.DataGithubRepositoryMapper
import com.zinoview.githubrepositories.data.repositories.GithubRepoRepository
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
import com.zinoview.githubrepositories.core.DisposableStore
import com.zinoview.githubrepositories.data.repositories.download.DownloadRepoRepository
import com.zinoview.githubrepositories.data.repositories.download.cloud.GithubDownloadRepoCloudDataSource
import com.zinoview.githubrepositories.data.repositories.download.cloud.GithubDownloadRepoService
import com.zinoview.githubrepositories.data.repositories.download.file.*
import com.zinoview.githubrepositories.domain.core.DomainDownloadExceptionMapper
import com.zinoview.githubrepositories.domain.repositories.download.DomainDownloadRepositoryMapper
import com.zinoview.githubrepositories.ui.core.cache.SaveCache
import com.zinoview.githubrepositories.ui.repositories.cache.Local
import com.zinoview.githubrepositories.ui.repositories.download.DownloadRepositoryCommunication
import com.zinoview.githubrepositories.ui.repositories.download.DownloadRepositoryExceptionMappersStore
import com.zinoview.githubrepositories.ui.repositories.download.UiDownloadExceptionMapper
import com.zinoview.githubrepositories.ui.repositories.download.UiDownloadRepositoryMapper
import io.reactivex.disposables.CompositeDisposable


/**
 * @author Zinoview on 25.08.2021
 * k.gig@list.ru
 */
class GithubRepositoryModule(
    private val coreModule: CoreModule
) : BaseModule<BaseViewModel<UiGithubRepositoryState>> {

    override fun viewModel(): BaseViewModel<UiGithubRepositoryState> {
        val repoCommunication = GithubRepositoryCommunication()
        val repoDownloadCommunication = DownloadRepositoryCommunication()
        val disposableStore = DisposableStore.Base(CompositeDisposable())

        val repositoryMappersStore = RepositoryMappersStore.Base(
            UiGithubRepositoryMapper(),
            UiGithubRepositoryStateMapper(),
            UiDownloadRepositoryMapper(
                coreModule.resource,
                UiDownloadExceptionMapper.Base(coreModule.resource)
            ),
            coreModule.exceptionMapper
        )

        val interactor = GithubRepositoryInteractor.Base(
            GithubRepoRepository.Base(
                GithubRepositoryCacheDataSource.Base(
                    coreModule.githubDao
                ),
                GithubRepositoryCloudDataSource.Base(
                    coreModule.networkService(GithubRepositoryService::class.java)
                ),
                CacheGithubRepositoryMapper(),
                DataGithubRepositoryMapper(),
                coreModule.repositoryCachedState
            ),
            DomainGithubRepositoryMapper(),
            DownloadRepoRepository.Base(
                GithubDownloadRepoCloudDataSource.Base(
                    coreModule.networkService(GithubDownloadRepoService::class.java)
                ),
                File.ZipFile(
                    Folder.Base(
                        coreModule.fileWriter,
                        coreModule.cachedFile,
                        CheckFileByExist.Base()
                    )
                ),
                SizeFile.Base(),
                Kbs.Base()
            ),
            DomainDownloadRepositoryMapper(
                DomainDownloadExceptionMapper.Base()
            )
        )
        return GithubRepositoryViewModel.Base(
            Remote.Base(
                interactor,
                repoCommunication,
                repoDownloadCommunication,
                disposableStore,
                repositoryMappersStore,
                DownloadRepositoryExceptionMappersStore.Base(
                    DomainDownloadExceptionMapper.Base(),
                    UiDownloadExceptionMapper.Base(coreModule.resource)
                )
            ),
            Local.Base(interactor),
            repoCommunication,
            repoDownloadCommunication,
            disposableStore,
            SaveCache.Repository(coreModule.githubDao,com.zinoview.githubrepositories.ui.repositories.CacheGithubRepositoryMapper.Base())
        )
    }
}