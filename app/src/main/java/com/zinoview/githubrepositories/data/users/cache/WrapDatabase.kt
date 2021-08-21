package com.zinoview.githubrepositories.data.users.cache

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase


/**
 * @author Zinoview on 21.08.2021
 * k.gig@list.ru
 */
interface WrapDatabase<T : Dao> {


    fun daoDatabase() : T

    class GithubUser(private val context: Context) : WrapDatabase<GithubUsersDao> {

        override fun daoDatabase(): GithubUsersDao
            =  GithubUsersDatabase.database(context).dao()

    }


}