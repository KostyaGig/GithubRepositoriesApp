package com.zinoview.githubrepositories.ui.users.cache

import com.zinoview.githubrepositories.R
import com.zinoview.githubrepositories.core.*
import com.zinoview.githubrepositories.domain.users.GithubUserInteractor
import com.zinoview.githubrepositories.ui.core.BaseGithubUserRequest
import com.zinoview.githubrepositories.ui.users.*
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * @author Zinoview on 21.08.2021
 * k.gig@list.ru
 */
abstract class LocalGithubUserRequest(
    private val githubUserInteractor: GithubUserInteractor,
    private val communication: GithubUserCommunication,
    private val githubUserDisposableStore: DisposableStore,
    private val resource: Resource,
    private val userMappersStore: UserMappersStore
) : BaseGithubUserRequest(
    githubUserInteractor,
    communication,
    githubUserDisposableStore,
    userMappersStore
), SaveState {

     fun request() {
        communication.changeValue(UiGithubUserState.Progress.wrap())
        githubUserInteractor
            .users()
            .subscribeOn(Schedulers.io())
            .flatMap { domainGithubUsers ->
                Single.just( domainGithubUsers.map { it.map(userMappersStore.uiUserMapper()) } )
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ uiGithubUsers ->
                uiGithubUsers?.let { users ->
                    if (users.isNotEmpty())
                        communication.changeValue(users.map { it.map(userMappersStore.uiUserStateMapper()) })
                    else
                        communication.changeValue(listOf(UiGithubUserState.Empty))
                }
            }, { error ->
                error?.let { throwable ->
                    communication.changeValue(UiGithubUserState.Fail(resource.string(R.string.local_error) + throwable.message).wrap())
                }
            }).addToDisposableStore(githubUserDisposableStore)
    }


    override fun saveState(state: CollapseOrExpandState)
        = githubUserInteractor.saveState(state)
}