package com.zinoview.githubrepositories.data.core

import android.content.Context


/**
 * @author Zinoview on 21.08.2021
 * k.gig@list.ru
 */
interface WrapDatabase<T : Dao> {
    fun daoDatabase() : T

    class Base(private val context: Context) : WrapDatabase<GithubDao> {

        override fun daoDatabase(): GithubDao
            =  GithubAppDatabase.database(context).dao()
    }
}