package com.zinoview.githubrepositories.core

import android.content.Context
import androidx.annotation.StringRes
import com.zinoview.githubrepositories.R


/**
 * @author Zinoview on 20.08.2021
 * k.gig@list.ru
 */
interface Resource {

    fun string(@StringRes stringResId: Int) : String

    class Base(
        private val context: Context
    ) : Resource {

        override fun string(stringResId: Int): String
            = context.resources.getString(stringResId)
    }

    class Test : Resource {

        override fun string(stringResId: Int): String {
            return when(stringResId) {
                R.string.no_connection_error -> "No connection"
                else -> "Generic error"
            }
        }

    }
}