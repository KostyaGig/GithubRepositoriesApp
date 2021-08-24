package com.zinoview.githubrepositories.ui.repositories

import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.ui.core.AbstractView
import com.zinoview.githubrepositories.ui.core.CommunicationModel
import com.zinoview.githubrepositories.ui.core.Shell
import com.zinoview.githubrepositories.ui.core.message


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
sealed class UiGithubRepositoryState :
    Shell<UiGithubRepositoryState>,
    Abstract.FactoryMapper<List<AbstractView>,Unit>,
    CommunicationModel {

    override fun wrap(): List<UiGithubRepositoryState>
        = listOf(Default)

    override fun map(src: List<AbstractView>) = Unit

    override fun isBase(): Boolean = false

    object Default : UiGithubRepositoryState()

    object Empty : UiGithubRepositoryState() {

        override fun map(src: List<AbstractView>)
            = src.forEach { it.map("EmptyData","","") }

        fun list() = listOf(this)
    }

    object Progress : UiGithubRepositoryState() {

        override fun wrap(): List<UiGithubRepositoryState>
            = listOf(this)
    }

    data class Base(
        private val uiGithubRepository: UiGithubRepository
    ) : UiGithubRepositoryState() {

        override fun wrap(): List<UiGithubRepositoryState>
            = listOf(this)

        override fun map(src: List<AbstractView>)
            = uiGithubRepository.map(src)

        override fun isBase(): Boolean = true
    }

    class Fail(private val message: String) : UiGithubRepositoryState() {

        override fun wrap(): List<UiGithubRepositoryState>
            = listOf(this)

        override fun map(src: List<AbstractView>)
            = src.first().map(message,"","")
    }

}

