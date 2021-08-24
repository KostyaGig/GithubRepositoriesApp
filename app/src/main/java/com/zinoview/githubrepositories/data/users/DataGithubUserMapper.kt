package com.zinoview.githubrepositories.data.users

import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.data.core.Text

/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
class DataGithubUserMapper(
    private val text: Text
) : Abstract.UserMapper<DataGithubUser> {

    override fun map(name: String, bio: String, imageUrl: String): DataGithubUser
        =  DataGithubUser(text.subString(name),bio,imageUrl)

}