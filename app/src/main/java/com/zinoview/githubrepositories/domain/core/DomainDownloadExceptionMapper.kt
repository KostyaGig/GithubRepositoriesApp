package com.zinoview.githubrepositories.domain.core

import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.domain.repositories.download.exceptions.NoConnectionException
import com.zinoview.githubrepositories.domain.repositories.download.exceptions.WorkServerException
import com.zinoview.githubrepositories.domain.repositories.download.exceptions.ZipFileNotFound
import retrofit2.HttpException
import java.io.FileNotFoundException
import java.io.IOException
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.net.UnknownHostException


/**
 * @author Zinoview on 05.09.2021
 * k.gig@list.ru
 */

interface DomainDownloadExceptionMapper : Abstract.FactoryMapper<Exception, IOException> {

    class Base : DomainDownloadExceptionMapper {
        override fun map(src: Exception): IOException = when(src) {
            is UnknownHostException -> NoConnectionException()
            is retrofit2.adapter.rxjava2.HttpException, is HttpException -> WorkServerException()
            is FileNotFoundException -> ZipFileNotFound()
            else -> throw IllegalArgumentException("DownloadExceptionMapper not handle this exception ${src::class.java}")
        }
    }
}
