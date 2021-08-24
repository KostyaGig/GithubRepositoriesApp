package com.zinoview.githubrepositories.data.repositories.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.data.repositories.DataGithubRepository


/**
 * @author Zinoview on 24.08.2021
 * k.gig@list.ru
 */
@Entity(tableName = "github_repo_table",primaryKeys = ["repo","userName"])
data class CacheGithubRepository(
    @ColumnInfo(name = "repo")
    val repo: String,
    @ColumnInfo(name = "lock")
    val lock: Boolean,
    @ColumnInfo(name = "language")
    val language: String,
    @ColumnInfo(name = "userName")
    val userName: String
) : Abstract.Object.Data.GithubRepository {

    override fun map(mapper: Abstract.RepositoryMapper<DataGithubRepository>): DataGithubRepository
        = mapper.map(repo,lock,language)
}