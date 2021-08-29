package com.zinoview.githubrepositories.data.repositories.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zinoview.githubrepositories.data.repositories.cache.CacheGithubRepository
import com.zinoview.githubrepositories.data.users.cache.CacheGithubUser
import com.zinoview.githubrepositories.ui.users.CollapseOrExpandState
import io.reactivex.Single


/**
 * @author Zinoview on 28.08.2021
 * k.gig@list.ru
 */

@Dao
interface RepositoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepositories(repositories: List<CacheGithubRepository>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepository(repository: CacheGithubRepository)

    //search by repo name or language
    @Query("SELECT * FROM `github_repo_table` where owner like :owner and repo like:query or language like:query")
    fun repository(owner: String,query: String) : Single<CacheGithubRepository?>

    @Query("select * from `github_repo_table` where owner like :query")
    fun repositories(query: String) : Single<List<CacheGithubRepository>>

    @Query("select * from github_repo_table where collapse = 1 and owner like :owner")
    fun repositoriesByIsCollapsedState(owner: String) : Single<List<CacheGithubRepository>>

    @Query("select * from github_repo_table where collapse = 0 and owner like :owner")
    fun repositoriesByIsNotCollapsedState(owner: String) : Single<List<CacheGithubRepository>>

    fun repositoriesByState(owner: String,state: CollapseOrExpandState) : Single<List<CacheGithubRepository>> {
        return when (state) {
            is CollapseOrExpandState.Collapsed -> repositoriesByIsCollapsedState(owner)
            is CollapseOrExpandState.Expanded -> repositoriesByIsNotCollapsedState(owner)
            else -> repositories(owner)
        }
    }
}