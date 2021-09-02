package com.zinoview.githubrepositories.data.repositories.cache.prefs

import android.content.Context
import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.data.core.prefs.AbstractCachedState
import com.zinoview.githubrepositories.data.core.prefs.CachedState
import com.zinoview.githubrepositories.data.repositories.cache.CacheGithubRepository
import com.zinoview.githubrepositories.data.repositories.cache.GithubRepositoryCacheDataSource
import com.zinoview.githubrepositories.ui.users.CollapseOrExpandState
import io.reactivex.Single


/**
 * @author Zinoview on 29.08.2021
 * k.gig@list.ru
 */

interface RepositoryCachedState : CachedState {

    fun repositories(owner: String,cacheDataSource: GithubRepositoryCacheDataSource) : Single<List<CacheGithubRepository>>

    fun repository(owner: String,repo: String,cacheDataSource: GithubRepositoryCacheDataSource) : Single<CacheGithubRepository?>

    class Base(
        context: Context,
        private val savedValueStateFactory: Abstract.FactoryMapper<CollapseOrExpandState,String>,
        private val collapseOrExpandStateFactory: Abstract.FactoryMapper<String,CollapseOrExpandState>
    ) : RepositoryCachedState, AbstractCachedState() {

        private val statePreferences = context.getSharedPreferences(REPOSITORY_PREFERENCES_NAME,Context.MODE_PRIVATE)

        override fun repositories(
            owner: String,
            cacheDataSource: GithubRepositoryCacheDataSource
        ): Single<List<CacheGithubRepository>>
            = cacheDataSource.repositoriesByState(owner,state())

        override fun repository(
            owner: String,
            repo: String,
            cacheDataSource: GithubRepositoryCacheDataSource
        ): Single<CacheGithubRepository?>
            = cacheDataSource.fetchRepository(owner,repo,state())

        override fun saveState(state: CollapseOrExpandState) {
            val savedValue = savedValueStateFactory.map(state)
            statePreferences.edit().putString(KEY_STATE, savedValue).apply()
        }

        private fun state() : CollapseOrExpandState {
            val savedValue = requireNotNull(statePreferences.getString(KEY_STATE, DEFAULT))
            return collapseOrExpandStateFactory.map(savedValue)
        }

        override fun currentState(): CollapseOrExpandState {
            val currentState = requireNotNull(statePreferences.getString(KEY_STATE, DEFAULT))
            return collapseOrExpandStateFactory.map(currentState)
        }

        private companion object {
            const val REPOSITORY_PREFERENCES_NAME = "repos_state"
        }
    }
}