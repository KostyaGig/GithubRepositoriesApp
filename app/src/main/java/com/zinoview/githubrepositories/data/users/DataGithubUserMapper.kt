package com.zinoview.githubrepositories.data.users

import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.ui.message
import java.lang.NullPointerException


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
class DataGithubUserMapper : Abstract.UserMapper<DataGithubUser> {

    override fun map(name: String, bio: String, imageUrl: String): DataGithubUser {
        return try {
            message("Bio length ${bio.length}")
            DataGithubUser(name,bio,imageUrl)
        } catch (e: NullPointerException) {
            DataGithubUser(name,"Empty bio",imageUrl)
        }
    }
}