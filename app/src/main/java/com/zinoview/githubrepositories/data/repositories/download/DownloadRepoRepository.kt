package com.zinoview.githubrepositories.data.repositories.download

import com.zinoview.githubrepositories.core.DisposableStore
import com.zinoview.githubrepositories.data.repositories.download.cloud.GithubDownloadRepoCloudDataSource
import com.zinoview.githubrepositories.data.repositories.download.file.SizeFile
import com.zinoview.githubrepositories.ui.core.CleanDisposable
import com.zinoview.githubrepositories.ui.core.message
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import okhttp3.ResponseBody
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
           return try {
                createFile(owner,repo)
               val responseBody = githubDownloadRepoCloudDataSource.download(owner, repo)
               responseBody
                   .flatMap { body ->
                       val bytesString = body.byteString()
                       val contentSize = bytesString.size.toLong()
                       return@flatMap if (sizeFile.isBigSizeFile(contentSize)) {
                           Single.just(DataDownloadFile.Big(contentSize))
                       }
                       else {
                           val newResponseBody = bytesString.toResponseBody()
                           Single.just(DataDownloadFile.WaitingToDownload(newResponseBody))
                       }
                   }
            } catch (e: Exception) {
                message("download repo download method exception: ${e::class.java}, message ${e.message}")
                Single.just(DataDownloadFile.Failure(e))
            }
        }


        private fun createFile(owner: String, repo: String) = file.create(owner, repo)
    }
}