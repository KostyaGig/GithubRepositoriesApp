package com.zinoview.githubrepositories.ui.users

import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.ui.core.AbstractView
import com.zinoview.githubrepositories.ui.core.CommunicationModel
import com.zinoview.githubrepositories.ui.core.Shell


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
sealed class UiGithubUserState :
    Shell<UiGithubUserState>,
    Abstract.FactoryMapper<List<AbstractView>,Unit>,
    CommunicationModel {

    override fun map(src: List<AbstractView>) = Unit

    override fun wrap(): List<UiGithubUserState>
        = listOf(Default)

    open fun map(listener: GithubOnItemClickListener) = Unit

    override fun isBase(): Boolean = false

    object Default : UiGithubUserState()

    object Progress : UiGithubUserState() {

        override fun wrap(): List<UiGithubUserState>
            = listOf(this)
    }

    data class Base(
        private val uiGithubUser: UiGithubUser
    ) : UiGithubUserState() {

        override fun map(src: List<AbstractView>) = uiGithubUser.map(src)

        override fun wrap(): List<UiGithubUserState>
            = listOf(this)

        override fun map(listener: GithubOnItemClickListener)
            = uiGithubUser.notify(listener)

        override fun isBase(): Boolean = true
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
