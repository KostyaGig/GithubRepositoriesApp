package com.zinoview.githubrepositories.ui.repositories.download

import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.domain.core.DomainDownloadExceptionMapper
import com.zinoview.githubrepositories.ui.users.UiGithubExceptionMapper


/**
 * @author Zinoview on 07.09.2021
 * k.gig@list.ru
 */
interface DownloadRepositoryExceptionMappersStore {

    fun domainDownloadExceptionMapper() : DomainDownloadExceptionMapper

    fun uiDownloadExceptionMapper() : UiDownloadExceptionMapper

    class Base(
        private val domainDownloadExceptionMapper: DomainDownloadExceptionMapper,
        private val uiDownloadExceptionMapper: UiDownloadExceptionMapper
    ) : DownloadRepositoryExceptionMappersStore {

        override fun domainDownloadExceptionMapper(): DomainDownloadExceptionMapper
            = domainDownloadExceptionMapper

        override fun uiDownloadExceptionMapper(): UiDownloadExceptionMapper
            = uiDownloadExceptionMapper
    }
}