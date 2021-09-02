package com.zinoview.githubrepositories.data.core


import com.zinoview.githubrepositories.data.repositories.cache.RepositoryDao
import com.zinoview.githubrepositories.data.users.cache.UserDao


/**
 * @author Zinoview on 23.08.2021
 * k.gig@list.ru
 */

@androidx.room.Dao
interface GithubDao : Dao, UserDao, RepositoryDao