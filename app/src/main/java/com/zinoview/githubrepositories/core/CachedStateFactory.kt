package com.zinoview.githubrepositories.core

import com.zinoview.githubrepositories.data.core.prefs.CachedState
import com.zinoview.githubrepositories.data.repositories.cache.prefs.RepositoryCachedState
import com.zinoview.githubrepositories.data.users.cache.prefs.UserCachedState
import java.lang.IllegalArgumentException


/**
 * @author Zinoview on 30.08.2021
 * k.gig@list.ru
 */
class CachedStateFactory<T : CachedState>(
    private val userCachedState: UserCachedState,
    private val repositoryCachedState: RepositoryCachedState
)
    : Abstract.FactoryMapper<Class<T>, CachedState>  {

    override fun map(src: Class<T>): CachedState = when(src) {
        UserCachedState.Base::class.java -> userCachedState
        RepositoryCachedState.Base::class.java -> repositoryCachedState
        else -> throw IllegalArgumentException("CachedStateFactory not found class ${src::class.java}")
    }
}