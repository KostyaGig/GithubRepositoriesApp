package com.zinoview.githubrepositories.ui.users.cache

import com.zinoview.githubrepositories.core.DisposableStore
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
    githubUserDisposableStore: DisposableStore,
    resource: Resource,
    userMappersStore: UserMappersStore
) : LocalGithubUserRequest(
    githubUserInteractor,
    communication,
    githubUserDisposableStore,
    resource,
    userMappersStore
)