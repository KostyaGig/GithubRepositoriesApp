package com.zinoview.githubrepositories.ui.repositories

import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.domain.repositories.GithubRepositoryInteractor
import com.zinoview.githubrepositories.ui.users.CleanDisposable
import com.zinoview.githubrepositories.ui.users.GithubDisposableStore
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.net.UnknownHostException


/**
 * @author Zinoview on 21.08.2021
 * k.gig@list.ru
 */
interface Remote {

    fun repositories(userName: String)

    fun repository(userName: String, repo: String)

    //todo fix constructor parameters and compare this class with BaseGithubUserRequest
    class Base(
        private val githubRepositoryInteractor: GithubRepositoryInteractor,
        private val communication: GithubRepositoryCommunication,
        private val githubRepositoryDisposableStore: GithubDisposableStore,
        private val uiGithubRepositoryMapper: Abstract.RepositoryMapper<UiGithubRepository>,
        private val uiGithubRepositoryStateMapper: Abstract.RepositoryMapper<UiGithubRepositoryState>,
        private val exceptionMapper: Abstract.FactoryMapper<Throwable,String>,
        ) : Remote, CleanDisposable {

        override fun repositories(userName: String) {
            communication.changeValue(UiGithubRepositoryState.Progress.wrap())
            githubRepositoryInteractor
                .data(userName)
                .subscribeOn(Schedulers.io())
                .flatMap { domainGithubRepositories ->
                    Single.just(domainGithubRepositories.map { it.map(uiGithubRepositoryMapper) })
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( { uiGithubRepositories ->
                    communication.changeValue(uiGithubRepositories.map { it.map(uiGithubRepositoryStateMapper) })
                }, { error->
                    error?.let { throwable ->
                        val messageError = exceptionMapper.map(throwable)
                        communication.changeValue(UiGithubRepositoryState.Fail(messageError).wrap())
                    }
                }).addToDisposableStore(githubRepositoryDisposableStore)
        }

        override fun repository(userName: String, repo: String) {
            communication.changeValue(UiGithubRepositoryState.Progress.wrap())
            githubRepositoryInteractor
                .repository(userName, repo)
                .subscribeOn(Schedulers.io())
                .flatMap { domainGithubRepository ->
                    Single.just(domainGithubRepository.map(uiGithubRepositoryMapper))
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( { uiGithubRepository ->
                    communication.changeValue(uiGithubRepository.map(uiGithubRepositoryStateMapper).wrap())
                }, { error->
                    error?.let { throwable ->
                        val messageError = exceptionMapper.map(throwable)
                        communication.changeValue(UiGithubRepositoryState.Fail(messageError).wrap())
                    }
                }).addToDisposableStore(githubRepositoryDisposableStore)
        }

        override fun Disposable.addToDisposableStore(store: GithubDisposableStore)
            = githubRepositoryDisposableStore.add(this)
    }
}