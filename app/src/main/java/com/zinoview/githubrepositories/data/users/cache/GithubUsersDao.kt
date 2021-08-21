package com.zinoview.githubrepositories.data.users.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
@Dao
interface GithubUsersDao : com.zinoview.githubrepositories.data.users.cache.Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: CacheGithubUser)

    @Query("SELECT * FROM `github_users_table` WHERE name LIKE :query")
    fun user(query: String) : CacheGithubUser?

    @Query("select * from `github_users_table`")
    fun users() : Single<List<CacheGithubUser>>
}