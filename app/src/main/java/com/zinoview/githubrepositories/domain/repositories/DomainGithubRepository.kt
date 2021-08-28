package com.zinoview.githubrepositories.domain.repositories

import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.ui.repositories.UiGithubRepository


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
data class DomainGithubRepository(
    private val name: String,
    private val private: Boolean,
    private val language: String,
    private val owner: String,
    private val urlRepository: String,
    private val defaultBranch: String,
    private val isCollapsed: Boolean
) : Abstract.Object.Ui.GithubRepository<UiGithubRepository> {

    override fun map(mapper: Abstract.RepositoryMapper<UiGithubRepository>): UiGithubRepository
         = mapper.map(name,private,language,owner,urlRepository, defaultBranch, isCollapsed)

}