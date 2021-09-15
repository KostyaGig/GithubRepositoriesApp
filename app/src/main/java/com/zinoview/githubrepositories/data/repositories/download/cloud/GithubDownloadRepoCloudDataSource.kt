package com.zinoview.githubrepositories.data.repositories.download.cloud

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response


/**
 * @author Zinoview on 21.08.2021
 * k.gig@list.ru
 */
interface GithubDownloadRepoCloudDataSource {

    fun download(owner: String, repo: String) : Single<ResponseBody>

    class Base(
        private val githubRepositoryService: GithubDownloadRepoService
    ) : GithubDownloadRepoCloudDataSource {

        override fun download(owner: String, repo: String): Single<ResponseBody>
            = githubRepositoryService.downloadRepository(owner,repo)
    }
}