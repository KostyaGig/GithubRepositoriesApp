package com.zinoview.githubrepositories.ui.repositories.download

import com.zinoview.githubrepositories.R
import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.core.Resource
import com.zinoview.githubrepositories.domain.repositories.download.exceptions.NoConnectionException
import com.zinoview.githubrepositories.domain.repositories.download.exceptions.WorkServerException
import com.zinoview.githubrepositories.domain.repositories.download.exceptions.ZipFileNotFound
import java.lang.Exception
import java.lang.IllegalArgumentException


/**
 * @author Zinoview on 05.09.2021
 * k.gig@list.ru
 */

interface UiDownloadExceptionMapper : Abstract.FactoryMapper<Exception, String> {

    class Base(
        private val resource: Resource
    ) : UiDownloadExceptionMapper{

        override fun map(src: Exception): String = when(src) {
            is NoConnectionException -> resource.string(R.string.no_connection_error)
            is WorkServerException -> resource.string(R.string.github_user_generic_error)
            is ZipFileNotFound -> resource.string(R.string.file_not_found_error)
            else -> throw IllegalArgumentException("DownloadExceptionMapper not handle this exception ${src::class.java}")
        }
    }
}

