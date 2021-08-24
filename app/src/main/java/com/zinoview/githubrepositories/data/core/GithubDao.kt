package com.zinoview.githubrepositories.data.core

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zinoview.githubrepositories.data.repositories.cache.CacheGithubRepository
import com.zinoview.githubrepositories.data.users.cache.CacheGithubUser
import io.reactivex.Single


/**
 * @author Zinoview on 23.08.2021
 * k.gig@list.ru
 */

@androidx.room.Dao
interface GithubDao : Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: CacheGithubUser)

    @Query("select * from `github_users_table` where name like :query")
    fun user(query: String) : Single<CacheGithubUser?>

    @Query("select * from `github_users_table`")
    fun users() : Single<List<CacheGithubUser>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepositories(repositories: List<CacheGithubRepository>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepository(repository: CacheGithubRepository)

    @Query("SELECT * FROM `github_repo_table` where userName like :query and repo like:repo")
    fun repository(query: String,repo: String) : Single<CacheGithubRepository?>

    @Query("select * from `github_repo_table` where userName like :query")
    fun repositories(query: String) : Single<List<CacheGithubRepository>>
}