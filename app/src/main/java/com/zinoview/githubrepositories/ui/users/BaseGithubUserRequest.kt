package com.zinoview.githubrepositories.ui.users

import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.domain.core.GithubInteractor
import com.zinoview.githubrepositories.domain.repositories.DomainGithubRepository
import com.zinoview.githubrepositories.domain.users.DomainGithubUser
import com.zinoview.githubrepositories.domain.users.GithubUserInteractor
import com.zinoview.githubrepositories.ui.core.Communication
import com.zinoview.githubrepositories.ui.core.CommunicationModel
import com.zinoview.githubrepositories.ui.repositories.UiGithubRepository
import com.zinoview.githubrepositories.ui.repositories.UiGithubRepositoryState
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


/**
 * @author Zinoview on 21.08.2021
 * k.gig@list.ru
 */

abstract class GithubRequest<D, M : CommunicationModel,U>(
    private val githubInteractor: GithubInteractor<D>,
    private val communication: Communication<M>,
    private val githubUserDisposableStore: GithubDisposableStore,
    private val exceptionMapper: Abstract.FactoryMapper<Throwable,String>
) : GithubUserRequest<String>, CleanDisposable {

    abstract fun progress() : List<M>

    abstract fun base(uiModel: U) : List<M>

    abstract fun failure(messageError: String) : List<M>

    abstract fun uiModel(domainModel: D) : U

    override fun request(param: String) {
        communication.changeValue(progress())
        githubInteractor
            .data(param)
            .subscribeOn(Schedulers.io())
            .flatMap { domainModel ->
                Single.just(uiModel(domainModel))
            }.observeOn(AndroidSchedulers.mainThread())
            .subscribe({ uiModel ->
                uiModel?.let { ui ->
                    communication.changeValue(base(ui))
                }
            }, { error ->
                error?.let { throwable ->
                    val messageError = exceptionMapper.map(throwable)
                    communication.changeValue(failure(messageError))
                }
            }).addToDisposableStore(githubUserDisposableStore)
    }

    override fun Disposable.addToDisposableStore(store: GithubDisposableStore)
        = githubUserDisposableStore.add(this)
}





