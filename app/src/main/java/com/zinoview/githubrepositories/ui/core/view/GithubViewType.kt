package com.zinoview.githubrepositories.ui.core.view


/**
 * @author Zinoview on 31.08.2021
 * k.gig@list.ru
 */
sealed class GithubViewType {

    object RepositoryName : GithubViewType()
    object RepositoryLanguage : GithubViewType()
    object RepositoryDefaultBranch : GithubViewType()

    object RepositoryPrivateImage : GithubViewType()
    object RepositoryLanguageColorImage : GithubViewType()


    object UserName : GithubViewType()
    object UserBio : GithubViewType()

    object UserProfileImage : GithubViewType()

    object Error : GithubViewType()
}