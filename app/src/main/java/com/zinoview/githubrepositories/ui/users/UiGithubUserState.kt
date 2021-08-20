package com.zinoview.githubrepositories.ui.users

import com.zinoview.githubrepositories.ui.view.AbstractView


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
sealed class UiGithubUserState {

    //todo implements interface for overriding this methods
    open fun map(views: List<AbstractView>) = Unit

    open fun wrapToList() : List<UiGithubUserState> = listOf(Empty)

    object Empty : UiGithubUserState()

    object Progress : UiGithubUserState() {
        override fun wrapToList(): List<UiGithubUserState> = listOf(this)
    }

    class Base(
        private val githubUiUser: UiGithubUser
    ) : UiGithubUserState() {

        override fun map(views: List<AbstractView>) = githubUiUser.map(views)

        override fun wrapToList(): List<UiGithubUserState> = listOf(this)
    }

    class Fail(private val message: String) : UiGithubUserState() {

        override fun map(views: List<AbstractView>) =
            views.forEach { view-> view.map(message,"","") }

        override fun wrapToList(): List<UiGithubUserState> = listOf(this)
    }
}

