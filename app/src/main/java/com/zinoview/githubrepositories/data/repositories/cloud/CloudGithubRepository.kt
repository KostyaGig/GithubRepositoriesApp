package com.zinoview.githubrepositories.data.repositories.cloud

import com.google.gson.annotations.SerializedName
import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.data.repositories.DataGithubRepository
import com.zinoview.githubrepositories.data.repositories.cache.CacheGithubRepository
import com.zinoview.githubrepositories.data.repositories.cache.CacheGithubRepositoryMapper


/**
 * @author Zinoview on 21.08.2021
 * k.gig@list.ru
 */
class CloudGithubRepository(
    @SerializedName("name")
    private val name: String,
    @SerializedName("private")
    private val private: Boolean,
    @SerializedName("language")
    private val language: String?
) : Abstract.Object.Cache.GithubRepository<CacheGithubRepository> {

    override fun map(mapper: Abstract.RepositoryMapper<DataGithubRepository>): DataGithubRepository
        = if (language == null)
            mapper.map(name,private,"Unknown")
        else mapper.map(name,private, language)

    override fun map(mapper: CacheGithubRepositoryMapper,userName: String): CacheGithubRepository
        = if (language == null) {
            CacheGithubRepository(name,private,"Unknown",userName)
        } else {
            CacheGithubRepository(name,private,language,userName)
        }
    }

