package com.zinoview.githubrepositories.data.users.cache

import com.zinoview.githubrepositories.core.Abstract


/**
 * @author Zinoview on 20.08.2021
 * k.gig@list.ru
 */
class CacheGithubUserMapper : Abstract.UserMapper<CacheGithubUser> {

    override fun map(name: String, bio: String, imageUrl: String,isCollapsed: Boolean): CacheGithubUser
        = CacheGithubUser(name,bio,imageUrl,isCollapsed)
}