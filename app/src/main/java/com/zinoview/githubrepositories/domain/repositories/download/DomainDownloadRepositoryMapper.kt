package com.zinoview.githubrepositories.domain.repositories.download

import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.domain.core.DomainDownloadExceptionMapper
import okhttp3.ResponseBody


/**
 * @author Zinoview on 05.09.2021
 * k.gig@list.ru
 */
class DomainDownloadRepositoryMapper(
    private val exceptionMapper: DomainDownloadExceptionMapper
) : Abstract.DownloadFileMapper<DomainDownloadFile> {

    override fun map(): DomainDownloadFile
        = DomainDownloadFile.Exist

    override fun map(size: Long): DomainDownloadFile
        = DomainDownloadFile.Big(size)

    override fun map(data: ResponseBody): DomainDownloadFile
        = DomainDownloadFile.WaitingToDownload(data)

    override fun map(e: Exception): DomainDownloadFile {
        val ioException = exceptionMapper.map(e)
        return DomainDownloadFile.Failure(ioException)
    }
}