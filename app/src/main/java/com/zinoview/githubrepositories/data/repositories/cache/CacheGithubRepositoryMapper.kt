package com.zinoview.githubrepositories.data.repositories.cache

/**
 * @author Zinoview on 23.08.2021
 * k.gig@list.ru
 */
interface CacheGithubRepositoryMapper {

    fun map(name: String, private: Boolean,language: String,userName: String) : CacheGithubRepository

    class Base : CacheGithubRepositoryMapper {

        override fun map(name: String, private: Boolean,language: String,userName: String) : CacheGithubRepository
            = CacheGithubRepository(name,private,language,userName)
    }

}