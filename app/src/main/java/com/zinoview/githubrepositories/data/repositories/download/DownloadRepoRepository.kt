package com.zinoview.githubrepositories.data.repositories.download

import com.zinoview.githubrepositories.data.repositories.download.cloud.GithubDownloadRepoCloudDataSource
import com.zinoview.githubrepositories.data.repositories.download.file.Kbs
import com.zinoview.githubrepositories.data.repositories.download.file.SizeFile
import com.zinoview.githubrepositories.domain.repositories.download.exceptions.NoConnectionException
import com.zinoview.githubrepositories.ui.core.message
import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.BufferedSource
import java.lang.IllegalStateException
import java.net.UnknownHostException


/**
 * @author Zinoview on 05.09.2021
 * k.gig@list.ru
 */

interface DownloadRepoRepository<T> {

    fun download(owner: String, repo: String): T


    class Base(
        private val githubDownloadRepoCloudDataSource: GithubDownloadRepoCloudDataSource,
        private val file: com.zinoview.githubrepositories.data.repositories.download.file.File,
        private val sizeFile: SizeFile,
        private val kbs: Kbs
    ) : DownloadRepoRepository<Single<DataDownloadFile>> {

        override fun download(owner: String, repo: String) : Single<DataDownloadFile> {
            return if (file.isNotExist(owner, repo)) {
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

    class Test : DownloadRepoRepository<TestDataDownloadFile> {

        private var count = 0
        private var exception: Exception = IllegalStateException()

        override fun download(owner: String, repo: String): TestDataDownloadFile {

            val result = when (count) {
                0 -> {
                    TestDataDownloadFile.Exist
                }
                1 -> {
                    TestDataDownloadFile.Failure(exception)
                }
                2 -> {
                    TestDataDownloadFile.WaitingToDownload(TestResponseBody())
                }
                else -> TestDataDownloadFile.Big(1023,TestResponseBody())
            }

            count++

            if (count > 3) {
                count = 0
            }

            return result
        }

        fun setCount(count: Int) {
            this.count = count
        }

        fun setException(exception: java.lang.Exception) {
            this.exception = exception
        }

        private inner class TestResponseBody : ResponseBody() {
            override fun contentLength(): Long
                = throw IllegalStateException("TestResponseBody not use this contentLength()")

            override fun contentType(): MediaType
                = throw IllegalStateException("TestResponseBody not use this contentType()")

            override fun source(): BufferedSource
                = throw IllegalStateException("TestResponseBody not use this source()")

        }
    }
}