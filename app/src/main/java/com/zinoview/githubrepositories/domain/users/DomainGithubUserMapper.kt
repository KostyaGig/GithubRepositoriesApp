package com.zinoview.githubrepositories.domain.users

import com.zinoview.githubrepositories.core.Abstract


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
class DomainGithubUserMapper : Abstract.UserMapper<DomainGithubUser> {

    override fun map(name: String, bio: String, imageUrl: String): DomainGithubUser
        = DomainGithubUser(name,bio,imageUrl)

}