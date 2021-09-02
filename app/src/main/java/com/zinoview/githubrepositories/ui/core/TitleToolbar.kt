package com.zinoview.githubrepositories.ui.core

import androidx.appcompat.widget.Toolbar
import com.zinoview.githubrepositories.ui.repositories.Name


/**
 * @author Zinoview on 30.08.2021
 * k.gig@list.ru
 */
interface TitleToolbar {

    fun title() : String

    fun title(name: Name) : String

    class Base(
        private val itemsState: ItemsState
    ) : TitleToolbar {

        override fun title(): String
            = "(${itemsState.currentState().asString()})"

        override fun title(name: Name)
            = name.currentName() + title()
    }
}