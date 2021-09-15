package com.zinoview.githubrepositories.ui.users

import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.core.DisposableStore
import com.zinoview.githubrepositories.domain.users.GithubUserInteractor
import com.zinoview.githubrepositories.ui.core.BaseGithubUserRequest


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */

class Remote(
    githubUserInteractor: GithubUserInteractor,
    communication: GithubUserCommunication,
    githubUserDisposableStore: DisposableStore,
    uiMappersStore: UserMappersStore
) : BaseGithubUserRequest(
    githubUserInteractor,
    communication,
    githubUserDisposableStore,
    uiMappersStore
)
