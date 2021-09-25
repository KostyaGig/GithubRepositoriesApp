package com.zinoview.githubrepositories.data.repositories.download

import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.domain.repositories.download.DomainDownloadFile
import okhttp3.ResponseBody
import java.lang.IllegalStateException


/**
 * @author Zinoview on 05.09.2021
 * k.gig@list.ru
 */


sealed class DataDownloadFile : Abstract.Object.Domain.GithubDownloadRepository {

    object Exist : DataDownloadFile() {

        override fun map(mapper: Abstract.DownloadFileMapper<DomainDownloadFile>): DomainDownloadFile
            = mapper.map()
    }

    class Big(
        private val size: Int,
        private val data: ResponseBody
    ) : DataDownloadFile() {

        override fun map(mapper: Abstract.DownloadFileMapper<DomainDownloadFile>): DomainDownloadFile
            = mapper.map(size,data)
    }

    class WaitingToDownload(
        private val data: ResponseBody
    ) : DataDownloadFile() {

        override fun map(mapper: Abstract.DownloadFileMapper<DomainDownloadFile>): DomainDownloadFile
            = mapper.map(data)
    }

    class Failure(private val e: Exception) : DataDownloadFile() {

        override fun map(mapper: Abstract.DownloadFileMapper<DomainDownloadFile>): DomainDownloadFile
            = mapper.map(e)
    }
}

sealed class TestDataDownloadFile {

    open fun exception() : java.lang.Exception = IllegalStateException()

    object Exist : TestDataDownloadFile() {}

    class Big(
        private val size: Int,
        private val data: ResponseBody
    ) : TestDataDownloadFile() {}

    class WaitingToDownload(
        private val data: ResponseBody
    ) : TestDataDownloadFile() {}

    class Failure(private val e: Exception) : TestDataDownloadFile() {

        override fun exception(): java.lang.Exception = e
    }
}
