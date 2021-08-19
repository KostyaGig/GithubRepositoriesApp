package com.zinoview.githubrepositories

import android.util.Log
import com.google.gson.annotations.SerializedName


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
data class CloudRepository(
    @SerializedName("name")
    private val repoName: String
) {

    fun print() = message("Repo name: $repoName")
    fun map() = CacheRepository(repoName = repoName)
}