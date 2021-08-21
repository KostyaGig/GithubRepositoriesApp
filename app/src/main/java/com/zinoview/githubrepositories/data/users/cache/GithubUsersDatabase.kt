package com.zinoview.githubrepositories.data.users.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */

@Database(entities = [CacheGithubUser::class], version = 1, exportSchema = false)
abstract class GithubUsersDatabase :  RoomDatabase() {

    abstract fun dao() : GithubUsersDao

    companion object {
        private var instance: GithubUsersDatabase? = null

        @Synchronized
        fun database(context: Context) : GithubUsersDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    GithubUsersDatabase::class.java,
                    NAME_DB,
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }

        private const val NAME_DB = "users_db"
    }


}