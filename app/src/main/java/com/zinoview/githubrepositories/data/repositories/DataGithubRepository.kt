package com.zinoview.githubrepositories.data.repositories

import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.domain.repositories.DomainGithubRepository


/**
 * @author Zinoview on 21.08.2021
 * k.gig@list.ru
 */
class DataGithubRepository(
    private val name: String,
    private val private: Boolean,
    private val language: String,
    private val owner: String,
    private val urlRepository: String,
    private val defaultBranch: String,
    private val isCollapsed: Boolean
) : Abstract.Object.Domain.GithubRepository {

    override fun map(mapper: Abstract.RepositoryMapper<DomainGithubRepository>): DomainGithubRepository
        = mapper.map(name, private, language,owner,urlRepository,defaultBranch,isCollapsed)

}