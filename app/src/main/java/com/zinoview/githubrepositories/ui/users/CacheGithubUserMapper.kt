package com.zinoview.githubrepositories.ui.users

import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.data.users.cache.CacheGithubUser


/**
 * @author Zinoview on 27.08.2021
 * k.gig@list.ru
 */
interface CacheGithubUserMapper : Abstract.Mapper {

    fun map(isCollapsed: Boolean,uiGithubUser: UiGithubUser) : CacheGithubUser

    class Base : CacheGithubUserMapper {
        override fun map(isCollapsed: Boolean,uiGithubUser: UiGithubUser): CacheGithubUser
            =  uiGithubUser.mapTo(isCollapsed)
    }
}