package com.zinoview.githubrepositories.ui.repositories

import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.ui.repositories.download.UiDownloadRepositoryMapper
import com.zinoview.githubrepositories.ui.users.UiGithubExceptionMapper


/**
 * @author Zinoview on 07.09.2021
 * k.gig@list.ru
 */
interface RepositoryMappersStore {

    fun uiRepositoryMapper() : Abstract.RepositoryMapper<UiGithubRepository>

    fun uiRepositoryStateMapper() : Abstract.RepositoryMapper<UiGithubRepositoryState>

    fun uiDownloadRepositoryMapper() : UiDownloadRepositoryMapper

    fun uiGithubExceptionMapper() : UiGithubExceptionMapper

    class Base(
        private val uiRepositoryMapper: Abstract.RepositoryMapper<UiGithubRepository>,
        private val uiRepositoryStateMapper: Abstract.RepositoryMapper<UiGithubRepositoryState>,
        private val uiDownloadRepositoryMapper: UiDownloadRepositoryMapper,
        private val uiGithubExceptionMapper: UiGithubExceptionMapper
    ) : RepositoryMappersStore {

        override fun uiRepositoryMapper(): Abstract.RepositoryMapper<UiGithubRepository>
            = uiRepositoryMapper

        override fun uiRepositoryStateMapper(): Abstract.RepositoryMapper<UiGithubRepositoryState>
            = uiRepositoryStateMapper

        override fun uiDownloadRepositoryMapper(): UiDownloadRepositoryMapper
            = uiDownloadRepositoryMapper

        override fun uiGithubExceptionMapper(): UiGithubExceptionMapper
            = uiGithubExceptionMapper
    }
}