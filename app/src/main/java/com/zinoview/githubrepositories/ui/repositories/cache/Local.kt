package com.zinoview.githubrepositories.ui.repositories.cache

import com.zinoview.githubrepositories.R
import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.core.GithubDisposableStore
import com.zinoview.githubrepositories.core.Resource
import com.zinoview.githubrepositories.domain.repositories.GithubRepositoryInteractor
import com.zinoview.githubrepositories.ui.repositories.GithubRepositoryCommunication
import com.zinoview.githubrepositories.ui.repositories.UiGithubRepository
import com.zinoview.githubrepositories.ui.repositories.UiGithubRepositoryState
import com.zinoview.githubrepositories.ui.users.CleanDisposable
import com.zinoview.githubrepositories.ui.users.CollapseOrExpandState
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

interface Local {

    fun repositoriesByState(owner: String, state: CollapseOrExpandState)

    class Base(
        private val githubRepositoryInteractor: GithubRepositoryInteractor,
        private val communication: GithubRepositoryCommunication,
        private val githubRepositoryDisposableStore: GithubDisposableStore,
        private val resource: Resource,
        private val mappers: Pair<
                Abstract.RepositoryMapper<UiGithubRepository>,
                Abstract.RepositoryMapper<UiGithubRepositoryState>,
                >
    ) : Local, CleanDisposable {

        override fun repositoriesByState(owner: String, state: CollapseOrExpandState) {
            communication.changeValue(UiGithubRepositoryState.Progress.wrap())
            githubRepositoryInteractor
                .repositoriesByState(owner,state)
                .subscribeOn(Schedulers.io())
                .flatMap { domainGithubRepos ->
                    Single.just(domainGithubRepos.map { it.map(mappers.first) })
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( { uiGithubRepos ->
                    uiGithubRepos?.let { repos ->
                        if (repos.isNotEmpty())
                            communication.changeValue(repos.map { it.map(mappers.second) })
                        else
                            communication.changeValue(listOf(UiGithubRepositoryState.Empty))
                    }
                }, { error ->
                    error?.let { throwable ->
                        communication.changeValue(UiGithubRepositoryState.Fail(resource.string(R.string.local_error) + throwable.message).wrap())
                    }
                }).addToDisposableStore(githubRepositoryDisposableStore)
        }

        override fun Disposable.addToDisposableStore(store: GithubDisposableStore)
            = store.add(this)
    }
}