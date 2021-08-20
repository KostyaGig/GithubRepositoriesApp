package com.zinoview.githubrepositories.data.users.cloud

import com.google.gson.annotations.SerializedName
import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.data.users.DataGithubUser


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
data class CloudGithubUser(
    @SerializedName("login")
    private val name: String,
    @SerializedName("bio")
    private val bio: String?,
    @SerializedName("avatar_url")
    private val profileImageUrl: String
) : Abstract.Object.Data {

    override fun map(mapper: Abstract.UserMapper<DataGithubUser>): DataGithubUser {
        return if(bio.isNullOrBlank()) {
            mapper.map(name,"Empty bio",profileImageUrl)
        } else {
            mapper.map(name,bio,profileImageUrl)
        }
    }

}