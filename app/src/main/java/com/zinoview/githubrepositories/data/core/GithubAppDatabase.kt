package com.zinoview.githubrepositories.data.core

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zinoview.githubrepositories.data.repositories.cache.CacheGithubRepository
import com.zinoview.githubrepositories.data.users.cache.CacheGithubUser


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */

@Database(entities = [CacheGithubRepository::class,CacheGithubUser::class], version = 1, exportSchema = false)
abstract class GithubAppDatabase :  RoomDatabase() {

    abstract fun dao() : GithubDao

    companion object {
        private var instance: GithubAppDatabase? = null

        @Synchronized
        fun database(context: Context) : GithubAppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    GithubAppDatabase::class.java,
                    NAME_DB,
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }

        private const val NAME_DB = "github_app_db"
    }


}