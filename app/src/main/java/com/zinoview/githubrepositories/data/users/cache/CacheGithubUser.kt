package com.zinoview.githubrepositories.data.users.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.data.core.CacheModel
import com.zinoview.githubrepositories.data.users.DataGithubUser


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */

@Entity(tableName = "github_users_table")
data class CacheGithubUser(
    @PrimaryKey
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "bio")
    val bio: String,
    @ColumnInfo(name = "profileImageUrl")
    val profileImageUrl: String,
    @ColumnInfo(name = "collapse")
    val isCollapsed: Boolean
) : Abstract.Object.Data.GithubUser, CacheModel {

    override fun map(mapper: Abstract.UserMapper<DataGithubUser>): DataGithubUser
        = mapper.map(name,bio,profileImageUrl,isCollapsed)
}