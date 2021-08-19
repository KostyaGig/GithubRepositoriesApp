package com.zinoview.githubrepositories

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
@Database(entities = [CacheRepository::class], version = 1, exportSchema = false)
abstract class RepoDatabase :  RoomDatabase() {

    abstract fun dao() : RepoDao

    companion object {
        private var instance: RepoDatabase? = null

        @Synchronized
        fun database(context: Context) : RepoDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    RepoDatabase::class.java,
                "repo_db",
                ).build()
            }
            return instance!!
        }
    }

}