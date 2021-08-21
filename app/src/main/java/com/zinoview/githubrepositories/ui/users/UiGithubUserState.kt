package com.zinoview.githubrepositories.ui.users

import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.ui.core.AbstractView


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
sealed class UiGithubUserState : Shell<UiGithubUserState>, Abstract.FactoryMapper<List<AbstractView>,Unit> {

    override fun map(src: List<AbstractView>) = Unit

    override fun wrap(): List<UiGithubUserState>
        = listOf(Default)

    object Default : UiGithubUserState()

    object Progress : UiGithubUserState() {

        override fun wrap(): List<UiGithubUserState>
            = listOf(this)
    }

    data class Base(
        private val githubUiUser: UiGithubUser
    ) : UiGithubUserState() {

        override fun map(src: List<AbstractView>) = githubUiUser.map(src)

        override fun wrap(): List<UiGithubUserState>
            = listOf(this)
    }

    //if our local data empty
    object Empty : UiGithubUserState() {

        override fun map(src: List<AbstractView>)
            = src.forEach { view -> view.map("Empty data", "", "") }
    }

    class Fail(private val message: String) : UiGithubUserState() {

        override fun map(src: List<AbstractView>) =
            src.forEach { view-> view.map(message,"","") }

        override fun wrap(): List<UiGithubUserState>
            = listOf(this)
    }
}

