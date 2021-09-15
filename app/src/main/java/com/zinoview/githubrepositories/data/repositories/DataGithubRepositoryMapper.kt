package com.zinoview.githubrepositories.data.repositories

import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.data.core.Text
import com.zinoview.githubrepositories.ui.core.message


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
class DataGithubRepositoryMapper : Abstract.RepositoryMapper<DataGithubRepository> {

    override fun map(
        name: String,
        private: Boolean,
        language: String,
        owner: String,
        urlRepository: String,
        defaultBranch: String,
        isCollapsed: Boolean
    ): DataGithubRepository
    {
        return DataGithubRepository(name, private, language, owner, urlRepository, defaultBranch, isCollapsed)
    }
}