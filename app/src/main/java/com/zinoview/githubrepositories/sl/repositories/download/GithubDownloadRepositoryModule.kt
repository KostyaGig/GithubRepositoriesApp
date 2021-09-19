package com.zinoview.githubrepositories.sl.repositories.download

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
import com.zinoview.githubrepositories.ui.repositories.download.*
import io.reactivex.disposables.CompositeDisposable


/**
 * @author Zinoview on 25.08.2021
 * k.gig@list.ru
 */
class GithubDownloadRepositoryModule(
    private val coreModule: CoreModule
) : BaseModule<WriteFileViewModel.Base> {

    override fun viewModel(): WriteFileViewModel.Base {
        return WriteFileViewModel.Base(
            WriteFileCommunication(),
            coreModule.cachedFile,
            DisposableStore.Base(CompositeDisposable())
        )
    }
}