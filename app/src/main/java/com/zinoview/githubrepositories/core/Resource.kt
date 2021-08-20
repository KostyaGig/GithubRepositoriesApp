package com.zinoview.githubrepositories.core

import android.content.Context
import androidx.annotation.StringRes


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
}