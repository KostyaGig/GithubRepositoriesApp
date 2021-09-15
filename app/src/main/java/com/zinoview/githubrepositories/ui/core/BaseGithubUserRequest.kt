package com.zinoview.githubrepositories.ui.core

import com.zinoview.githubrepositories.core.DisposableStore
import com.zinoview.githubrepositories.domain.users.GithubUserInteractor
import com.zinoview.githubrepositories.ui.users.*
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


/**
 * @author Zinoview on 24.08.2021
 * k.gig@list.ru
 */
abstract class BaseGithubUserRequest (
    private val githubUserInteractor: GithubUserInteractor,
    private val communication: GithubUserCommunication,
    private val githubUserDisposableStore: DisposableStore,
    private val uiMappersStore: UserMappersStore
) : GithubUserRequest<String>, CleanDisposable {

    override fun data(param: String) {
        communication.changeValue(UiGithubUserState.Progress.wrap())
        githubUserInteractor
            .data(param)
            .subscribeOn(Schedulers.io())
            .flatMap { domainGithubUser ->
                Single.just(domainGithubUser.map(uiMappersStore.uiUserMapper()))
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ uiGithubUser ->
                uiGithubUser?.let { user ->
                    communication.changeValue(user.map(uiMappersStore.uiUserStateMapper()).wrap())
                }
            }, { error ->
                error?.let { throwable ->
                    val messageError = uiMappersStore.uiGithubExceptionMapper().map(throwable)
                    communication.changeValue(UiGithubUserState.Fail(messageError).wrap())
                }
            }).addToDisposableStore(githubUserDisposableStore)
    }

    override fun Disposable.addToDisposableStore(store: DisposableStore)
        = store.add(this)
}