package com.zinoview.githubrepositories.ui.repositories

import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.domain.repositories.GithubRepositoryInteractor
import com.zinoview.githubrepositories.ui.core.CleanDisposable
import com.zinoview.githubrepositories.core.GithubDisposableStore
import com.zinoview.githubrepositories.ui.users.GithubUserRequest
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


/**
 * @author Zinoview on 21.08.2021
 * k.gig@list.ru
 */
interface Remote : GithubUserRequest<String> {

    fun repository(owner: String, repo: String)

    class Base(
        private val githubRepositoryInteractor: GithubRepositoryInteractor,
        private val communication: GithubRepositoryCommunication,
        private val githubRepositoryDisposableStore: GithubDisposableStore,
        private val mappers: Triple<
                Abstract.RepositoryMapper<UiGithubRepository>,
                Abstract.RepositoryMapper<UiGithubRepositoryState>,
                Abstract.FactoryMapper<Throwable,String>
                >
        ) : Remote, CleanDisposable {

        override fun data(owner: String) {
            communication.changeValue(UiGithubRepositoryState.Progress.wrap())
            githubRepositoryInteractor
                .data(owner)
                .subscribeOn(Schedulers.io())
                .flatMap { domainGithubRepositories ->
                    Single.just(domainGithubRepositories.map { it.map(mappers.first) })
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( { uiGithubRepositories ->
                    if (uiGithubRepositories.isEmpty())
                        communication.changeValue(UiGithubRepositoryState.Empty.wrap())
                    else
                        communication.changeValue(uiGithubRepositories.map { it.map(mappers.second) })
                }, { error->
                    error?.let { throwable ->
                        val messageError = mappers.third.map(throwable)
                        communication.changeValue(UiGithubRepositoryState.Fail(messageError).wrap())
                    }
                }).addToDisposableStore(githubRepositoryDisposableStore)
        }

        override fun repository(owner: String, repo: String) {
            communication.changeValue(UiGithubRepositoryState.Progress.wrap())
            githubRepositoryInteractor
                .repository(owner, repo)
                .subscribeOn(Schedulers.io())
                .flatMap { domainGithubRepository ->
                    Single.just(domainGithubRepository.map(mappers.first))
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( { uiGithubRepository ->
                    communication.changeValue(uiGithubRepository.map(mappers.second).wrap())
                }, { error->
                    error?.let { throwable ->
                        val messageError = mappers.third.map(throwable)
                        communication.changeValue(UiGithubRepositoryState.Fail(messageError).wrap())
                    }
                }).addToDisposableStore(githubRepositoryDisposableStore)
        }

        override fun Disposable.addToDisposableStore(store: GithubDisposableStore)
            = githubRepositoryDisposableStore.add(this)
    }
}