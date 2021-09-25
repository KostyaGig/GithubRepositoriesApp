package com.zinoview.githubrepositories.data.repositories.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.data.repositories.DataGithubRepository


/**
 * @author Zinoview on 24.08.2021
 * k.gig@list.ru
 */

@Entity(tableName = "github_repo_table",primaryKeys = ["repo","owner"])
data class CacheGithubRepository(
    @ColumnInfo(name = "repo")
    val repo: String,
    @ColumnInfo(name = "lock")
    val lock: Boolean,
    @ColumnInfo(name = "language")
    val language: String,
    @ColumnInfo(name = "owner")
    val owner: String,
    @ColumnInfo(name = "urlRepository")
    val urlRepository: String,
    @ColumnInfo(name = "defaultBranch")
    val defaultBranch: String,
    @ColumnInfo(name = "collapse")
    val isCollapsed: Boolean
) : Abstract.Object.Data.GithubRepository {

    override fun map(mapper: Abstract.RepositoryMapper<DataGithubRepository>): DataGithubRepository
        = mapper.map(repo,lock,language,owner,urlRepository,defaultBranch, isCollapsed)
}