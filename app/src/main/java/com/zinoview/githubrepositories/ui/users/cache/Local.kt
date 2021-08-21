package com.zinoview.githubrepositories.ui.users.cache

import com.zinoview.githubrepositories.R
import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.core.Resource
import com.zinoview.githubrepositories.domain.users.GithubUserInteractor
import com.zinoview.githubrepositories.ui.users.*
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


/**
 * @author Zinoview on 20.08.2021
 * k.gig@list.ru
 */

class Local(
    githubUserInteractor: GithubUserInteractor,
    communication: GithubUserCommunication,
    githubUserDisposableStore: GithubDisposableStore,
    resource: Resource,
    uiGithubMappers: Triple<
            Abstract.UserMapper<UiGithubUser>,
            Abstract.UserMapper<UiGithubUserState>,
            Abstract.FactoryMapper<Throwable,String>
            >
) : LocalGithubUserRequest(
    githubUserInteractor,
    communication,
    githubUserDisposableStore,
    resource,
    uiGithubMappers
)