package com.zinoview.githubrepositories.domain.repositories.download

import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.data.repositories.download.DataDownloadFile
import com.zinoview.githubrepositories.ui.repositories.download.UiDownloadFile
import okhttp3.ResponseBody


/**
 * @author Zinoview on 05.09.2021
 * k.gig@list.ru
 */
sealed class DomainDownloadFile : Abstract.Object.Ui.GithubDownloadRepository<UiDownloadFile> {

    object Exist : DomainDownloadFile() {

        override fun map(mapper: Abstract.DownloadFileMapper<UiDownloadFile>): UiDownloadFile
            = mapper.map()
    }

    class Big(
        private val size: Long
    ) : DomainDownloadFile() {

        override fun map(mapper: Abstract.DownloadFileMapper<UiDownloadFile>): UiDownloadFile
            = mapper.map(size)
    }

    class WaitingToDownload(
        private val data: ResponseBody
    ) : DomainDownloadFile() {

        override fun map(mapper: Abstract.DownloadFileMapper<UiDownloadFile>): UiDownloadFile
            = mapper.map(data)
    }

    class Failure(private val e: Exception) : DomainDownloadFile() {
        override fun map(mapper: Abstract.DownloadFileMapper<UiDownloadFile>): UiDownloadFile
            = mapper.map(e)
    }
}