package com.zinoview.githubrepositories.ui.repositories.download

import com.zinoview.githubrepositories.R
import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.core.Resource
import okhttp3.ResponseBody


/**
 * @author Zinoview on 05.09.2021
 * k.gig@list.ru
 */
sealed class UiDownloadFile :
    Abstract.UniqueMapper<UiGithubDownloadFileState,Unit>
{

    object Exist : UiDownloadFile() {

        override fun mapTo(param: Unit): UiGithubDownloadFileState
            = UiGithubDownloadFileState.Exist
    }

    class Big(
        private val resource: Resource,
        private val size: Long
    ) : UiDownloadFile() {

        override fun mapTo(param: Unit): UiGithubDownloadFileState
            = UiGithubDownloadFileState.Big(
                size,
                resource.string(R.string.file_is_big)
            )
    }

    class WaitingToDownload(
        private val resource: Resource,
        private val data: ResponseBody,
    ) : UiDownloadFile() {

        override fun mapTo(param: Unit): UiGithubDownloadFileState
            = UiGithubDownloadFileState.WaitingToDownload(
                data,
                resource.string(R.string.file_in_waiting_download)
            )
    }

    class Failure(
            private val e: Exception,
            private val exceptionMapper: UiDownloadExceptionMapper
        ) : UiDownloadFile() {

        override fun mapTo(param: Unit): UiGithubDownloadFileState
            = UiGithubDownloadFileState.Failure(
                exceptionMapper.map(e)
            )
    }
}