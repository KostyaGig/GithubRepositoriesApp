package com.zinoview.githubrepositories.data.repositories.download

import com.zinoview.githubrepositories.data.repositories.download.cloud.GithubDownloadRepoCloudDataSource
import com.zinoview.githubrepositories.data.repositories.download.file.Kbs
import com.zinoview.githubrepositories.data.repositories.download.file.SizeFile
import com.zinoview.githubrepositories.ui.core.message
import io.reactivex.Single
import okhttp3.ResponseBody.Companion.toResponseBody


/**
 * @author Zinoview on 05.09.2021
 * k.gig@list.ru
 */
interface DownloadRepoRepository {

    fun download(owner: String, repo: String): Single<DataDownloadFile>

    class Base(
        private val githubDownloadRepoCloudDataSource: GithubDownloadRepoCloudDataSource,
        private val file: com.zinoview.githubrepositories.data.repositories.download.file.File,
        private val sizeFile: SizeFile,
        private val kbs: Kbs
    ) : DownloadRepoRepository {

        override fun download(owner: String, repo: String) : Single<DataDownloadFile> {
            return if (file.isNotExist(owner, repo)) {
                message("file not exist")
                downloadRepository(owner, repo)
            } else {
                Single.just(DataDownloadFile.Exist)
            }
        }

        private fun downloadRepository(owner: String,repo: String) : Single<DataDownloadFile>  {
            createFile(owner,repo)
               val responseBody = githubDownloadRepoCloudDataSource.download(owner, repo)
               return responseBody
                   .flatMap { body ->
                       val bytesString = body.byteString()
                       val contentSize = bytesString.size.toLong()
                       val contentSizeInKbs = kbs.toKb(contentSize)
                       val newResponseBody = bytesString.toResponseBody()
                       return@flatMap if (sizeFile.isBigSizeFile(contentSizeInKbs)) {
                           Single.just(DataDownloadFile.Big(contentSizeInKbs,newResponseBody))
                       }
                       else {
                           Single.just(DataDownloadFile.WaitingToDownload(newResponseBody))
                       }
                   }
        }

        private fun createFile(owner: String, repo: String) = file.create(owner, repo)
    }
}