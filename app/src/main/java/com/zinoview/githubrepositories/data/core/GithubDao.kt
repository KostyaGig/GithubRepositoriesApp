package com.zinoview.githubrepositories.data.core

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zinoview.githubrepositories.data.repositories.cache.RepositoryDao
import com.zinoview.githubrepositories.data.users.cache.CacheGithubUser
import com.zinoview.githubrepositories.data.users.cache.UserDao
import io.reactivex.Single


/**
 * @author Zinoview on 23.08.2021
 * k.gig@list.ru
 */

@androidx.room.Dao
interface GithubDao : Dao, UserDao, RepositoryDao