package com.zinoview.githubrepositories.ui.core

import com.zinoview.githubrepositories.ui.repositories.TempGithubUserName
import com.zinoview.githubrepositories.ui.users.CollapseOrExpandState


/**
 * @author Zinoview on 30.08.2021
 * k.gig@list.ru
 */

interface TitleToolbar {

    fun title() : String

    fun title(name: TempGithubUserName) : String

    class Base(
        private val itemsState: ItemsState<CollapseOrExpandState>
    ) : TitleToolbar {

        override fun title(): String
            = "(${itemsState.currentState().asString()})"

        override fun title(name: TempGithubUserName)
            = name.currentName() + title()
    }
}