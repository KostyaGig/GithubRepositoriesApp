package com.zinoview.githubrepositories.ui.repositories

import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.ui.core.AbstractView
import com.zinoview.githubrepositories.ui.core.message


/**
 * @author Zinoview on 21.08.2021
 * k.gig@list.ru
 */
data class UiGithubRepository(
    private val name: String,
    private val private: Boolean,
    private val language: String
) : Abstract.Object.Ui.GithubRepository<UiGithubRepositoryState>,
    Abstract.FactoryMapper<List<AbstractView>,Unit> {

    override fun map(mapper: Abstract.RepositoryMapper<UiGithubRepositoryState>): UiGithubRepositoryState
        = mapper.map(name, private, language)

    override fun map(src: List<AbstractView>) {
        src.map { view -> view.map(name, private, language) }
    }

}