package com.zinoview.githubrepositories.ui.users.cache

import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.core.GithubDisposableStore
import com.zinoview.githubrepositories.core.Resource
import com.zinoview.githubrepositories.domain.users.GithubUserInteractor
import com.zinoview.githubrepositories.ui.users.*



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