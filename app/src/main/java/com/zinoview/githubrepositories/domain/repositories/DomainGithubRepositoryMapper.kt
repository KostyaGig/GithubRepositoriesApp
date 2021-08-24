package com.zinoview.githubrepositories.domain.repositories

import com.zinoview.githubrepositories.core.Abstract


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
class DomainGithubRepositoryMapper : Abstract.RepositoryMapper<DomainGithubRepository> {

    override fun map(name: String, private: Boolean, language: String): DomainGithubRepository
        = DomainGithubRepository(name,private, language)

}