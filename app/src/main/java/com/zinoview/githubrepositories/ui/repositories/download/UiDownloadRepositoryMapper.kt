package com.zinoview.githubrepositories.ui.repositories.download

import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.core.Resource
import okhttp3.ResponseBody


/**
 * @author Zinoview on 05.09.2021
 * k.gig@list.ru
 */
class UiDownloadRepositoryMapper(
    private val resource: Resource,
    private val exceptionMapper: UiDownloadExceptionMapper
) : Abstract.DownloadFileMapper<UiDownloadFile> {

    override fun map(): UiDownloadFile
        = UiDownloadFile.Exist

    override fun map(size: Long): UiDownloadFile
        = UiDownloadFile.Big(
            resource,
            size
        )

    override fun map(data: ResponseBody): UiDownloadFile
        = UiDownloadFile.WaitingToDownload(
            resource,
            data
        )

    override fun map(e: Exception): UiDownloadFile
        = UiDownloadFile.Failure(
            e,
            exceptionMapper
        )
}