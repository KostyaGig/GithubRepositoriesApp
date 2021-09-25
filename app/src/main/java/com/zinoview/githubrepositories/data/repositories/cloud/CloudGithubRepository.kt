package com.zinoview.githubrepositories.data.repositories.cloud

import com.google.gson.annotations.SerializedName
import com.zinoview.githubrepositories.core.Abstract


/**
 * @author Zinoview on 21.08.2021
 * k.gig@list.ru
 */

//todo make suppelements for all Repo model for fields (language,urlRepository,defaultBranch)
//А то очень огромные классы


class CloudGithubRepository(
    @SerializedName("name")
    private val name: String,
    @SerializedName("private")
    private val private: Boolean,
    @SerializedName("language")
    private val language: String?,
    @SerializedName("html_url")
    private val urlRepository: String,
    @SerializedName("default_branch")
    private val defaultBranch: String
) : Abstract.Object.Cache.GithubRepository {

    override fun <T> map(mapper: Abstract.RepositoryMapper<T>, owner: String): T
        = conditionMap(mapper,owner)

    private fun <T> conditionMap(mapper: Abstract.RepositoryMapper<T>,owner: String): T
        = if (language == null) {
            mapper.map(
                name,
                private,
                "Unknown",
                owner,
                urlRepository,
                defaultBranch,
                true
            )
        } else {
            mapper.map(name,
                private,
                language,
                owner,
                urlRepository
                ,defaultBranch,
                true
            )
        }
}

