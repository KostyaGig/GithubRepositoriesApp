package com.zinoview.githubrepositories.data.users.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zinoview.githubrepositories.data.repositories.cache.CacheGithubRepository
import io.reactivex.Single


/**
 * @author Zinoview on 28.08.2021
 * k.gig@list.ru
 */

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: CacheGithubUser)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(users: List<CacheGithubUser>)

    @Query("select * from `github_users_table` where name like :query")
    fun user(query: String) : Single<CacheGithubUser?>

    @Query("select * from `github_users_table`")
    fun users() : Single<List<CacheGithubUser>>
}