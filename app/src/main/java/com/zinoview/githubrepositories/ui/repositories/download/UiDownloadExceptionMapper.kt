package com.zinoview.githubrepositories.ui.repositories.download

import com.zinoview.githubrepositories.R
import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.core.Resource
import com.zinoview.githubrepositories.domain.repositories.download.exceptions.NoConnectionException
import com.zinoview.githubrepositories.domain.repositories.download.exceptions.WorkServerException
import com.zinoview.githubrepositories.domain.repositories.download.exceptions.ZipFileNotFound
import java.io.IOException
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.net.UnknownHostException


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
            is WorkServerException -> resource.string(R.string.server_error)
            is ZipFileNotFound -> resource.string(R.string.file_not_found_error)
            else -> resource.string(R.string.generic_error)
        }
    }
}

