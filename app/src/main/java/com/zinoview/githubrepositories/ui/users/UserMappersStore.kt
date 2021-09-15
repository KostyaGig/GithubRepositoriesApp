package com.zinoview.githubrepositories.ui.users

import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.ui.repositories.download.UiDownloadRepositoryMapper
import com.zinoview.githubrepositories.ui.users.UiGithubExceptionMapper


/**
 * @author Zinoview on 07.09.2021
 * k.gig@list.ru
 */
interface UserMappersStore {

    fun uiUserMapper() : Abstract.UserMapper<UiGithubUser>

    fun uiUserStateMapper() : Abstract.UserMapper<UiGithubUserState>

    fun uiGithubExceptionMapper() : UiGithubExceptionMapper


    class Base(
        private val uiUserMapper: Abstract.UserMapper<UiGithubUser>,
        private val uiUserStateMapper: Abstract.UserMapper<UiGithubUserState>,
        private val uiDownloadRepositoryMapper: UiGithubExceptionMapper,
    ) : UserMappersStore {

        override fun uiUserMapper(): Abstract.UserMapper<UiGithubUser>
            = uiUserMapper

        override fun uiUserStateMapper(): Abstract.UserMapper<UiGithubUserState>
            = uiUserStateMapper

        override fun uiGithubExceptionMapper(): UiGithubExceptionMapper
            = uiDownloadRepositoryMapper

    }
}