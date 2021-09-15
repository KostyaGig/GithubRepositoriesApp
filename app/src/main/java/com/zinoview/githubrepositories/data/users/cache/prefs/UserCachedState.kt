package com.zinoview.githubrepositories.data.users.cache.prefs

import android.content.Context
import com.zinoview.githubrepositories.data.core.prefs.AbstractCachedState
import com.zinoview.githubrepositories.data.core.prefs.CachedState
import com.zinoview.githubrepositories.data.core.prefs.CollapseOrExpandStateFactory
import com.zinoview.githubrepositories.data.core.prefs.SavedValueStateFactory
import com.zinoview.githubrepositories.data.users.cache.CacheGithubUser
import com.zinoview.githubrepositories.data.users.cache.GithubUserCacheDataSource
import com.zinoview.githubrepositories.ui.users.CollapseOrExpandState
import io.reactivex.Single


/**
 * @author Zinoview on 29.08.2021
 * k.gig@list.ru
 */

interface UserCachedState : CachedState {

    fun users(cacheDataSource: GithubUserCacheDataSource) : Single<List<CacheGithubUser>>

    fun user(userName: String,cacheDataSource: GithubUserCacheDataSource) : Single<CacheGithubUser?>

    class Base(
        context: Context,
        private val savedValueStateFactory: SavedValueStateFactory,
        private val collapseOrExpandStateFactory: CollapseOrExpandStateFactory
    ) : UserCachedState, AbstractCachedState() {

        private val statePreferences = context.getSharedPreferences(USERS_PREFERENCES_NAME,Context.MODE_PRIVATE)

        override fun saveState(state: CollapseOrExpandState) {
            val savedValue = savedValueStateFactory.map(state)
            statePreferences.edit().putString(KEY_STATE, savedValue).apply()
        }

        override fun users(cacheDataSource: GithubUserCacheDataSource): Single<List<CacheGithubUser>>
            = cacheDataSource.usersByState(state())

        override fun user(userName: String,cacheDataSource: GithubUserCacheDataSource): Single<CacheGithubUser?>
            = cacheDataSource.userByState(userName,state())

        private fun state() : CollapseOrExpandState {
            val savedValue = requireNotNull(statePreferences.getString(KEY_STATE, DEFAULT))
            return collapseOrExpandStateFactory.map(savedValue)
        }

        override fun currentState(): CollapseOrExpandState {
            val currentState = requireNotNull(statePreferences.getString(KEY_STATE, DEFAULT))
            return collapseOrExpandStateFactory.map(currentState)
        }

        private companion object {
            const val USERS_PREFERENCES_NAME = "users_state"
        }
    }
}