package com.zinoview.githubrepositories

import android.util.Log
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */

@Entity(tableName = "repo_table")
data class CacheRepository(
    @ColumnInfo(name = "repoId") @PrimaryKey(autoGenerate = true) val repoId: Int? = null,
    @ColumnInfo(name = "name")
    val repoName: String
) {

    fun print() = message("--CACHE-- Repo name: $repoName")
}