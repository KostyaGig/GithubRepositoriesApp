package com.zinoview.githubrepositories.ui.users

import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.domain.users.GithubUserInteractor
import com.zinoview.githubrepositories.ui.core.BaseGithubUserRequest


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */

class Remote(
     githubUserInteractor: GithubUserInteractor,
     communication: GithubUserCommunication,
     githubUserDisposableStore: GithubDisposableStore,
     uiGithubUserMapper: Abstract.UserMapper<UiGithubUser>,
     exceptionMapper: Abstract.FactoryMapper<Throwable,String>
) : BaseGithubUserRequest(
    githubUserInteractor,
    communication,
    githubUserDisposableStore,
    uiGithubUserMapper,
    exceptionMapper
)
