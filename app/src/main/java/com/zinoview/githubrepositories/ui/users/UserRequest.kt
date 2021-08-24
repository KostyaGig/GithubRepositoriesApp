package com.zinoview.githubrepositories.ui.users

import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.domain.core.GithubInteractor
import com.zinoview.githubrepositories.domain.users.DomainGithubUser
import com.zinoview.githubrepositories.ui.core.Communication


/**
 * @author Zinoview on 24.08.2021
 * k.gig@list.ru
 */

class UserRequest(
    interactor: GithubInteractor<DomainGithubUser>,
    communication: Communication<UiGithubUserState>,
    githubUserDisposableStore: GithubDisposableStore,
    exceptionMapper: Abstract.FactoryMapper<Throwable, String>,
    private val uiGithubUserStateMapper: Abstract.UserMapper<UiGithubUserState>,
    private val uiGithubUserMapper: Abstract.UserMapper<UiGithubUser>
) : GithubRequest<DomainGithubUser,UiGithubUserState,UiGithubUser>(
    interactor,
    communication,
    githubUserDisposableStore,
    exceptionMapper
) {
    override fun progress(): List<UiGithubUserState>
        = listOf(UiGithubUserState.Progress)

    override fun base(uiModel: UiGithubUser): List<UiGithubUserState>
        = uiModel.map(uiGithubUserStateMapper).wrap()

    override fun failure(messageError: String): List<UiGithubUserState>
        = UiGithubUserState.Fail(messageError).wrap()

    override fun uiModel(domainModel: DomainGithubUser): UiGithubUser
        = domainModel.map(uiGithubUserMapper)
}