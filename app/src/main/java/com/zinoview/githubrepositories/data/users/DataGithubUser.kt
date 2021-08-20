package com.zinoview.githubrepositories.data.users

import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.domain.users.DomainGithubUser


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
data class DataGithubUser(
    private val name: String,
    private val bio: String,
    private val profileImageUrl: String
) : Abstract.Object.Domain {

    override fun map(mapper: Abstract.UserMapper<DomainGithubUser>): DomainGithubUser
        = mapper.map(name,bio,profileImageUrl)
}