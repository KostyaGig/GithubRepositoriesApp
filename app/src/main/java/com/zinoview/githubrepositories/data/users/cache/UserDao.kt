package com.zinoview.githubrepositories.data.users.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zinoview.githubrepositories.data.repositories.cache.CacheGithubRepository
import com.zinoview.githubrepositories.ui.users.CollapseOrExpandState
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

    @Query("select * from github_users_table where collapse = 1")
    fun usersByIsCollapsedState() : Single<List<CacheGithubUser>>

    @Query("select * from github_users_table where collapse = 0")
    fun usersByIsNotCollapsedState() : Single<List<CacheGithubUser>>

    fun usersByState(state: CollapseOrExpandState) : Single<List<CacheGithubUser>> {
        return when (state) {
            is CollapseOrExpandState.Collapsed -> usersByIsCollapsedState()
            is CollapseOrExpandState.Expanded -> usersByIsNotCollapsedState()
            else -> users()
        }
    }
}