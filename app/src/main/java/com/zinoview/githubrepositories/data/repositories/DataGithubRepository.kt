package com.zinoview.githubrepositories.data.repositories

import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.domain.repositories.DomainGithubRepository
import com.zinoview.githubrepositories.ui.core.message


/**
 * @author Zinoview on 21.08.2021
 * k.gig@list.ru
 */
class DataGithubRepository(
    private val name: String,
    private val private: Boolean,
    private val language: String
) : Abstract.Object.Domain.GithubRepository {

    override fun map(mapper: Abstract.RepositoryMapper<DomainGithubRepository>): DomainGithubRepository
        = mapper.map(name, private, language)

    fun print() = message("Data github repo name $name, language $language")
}