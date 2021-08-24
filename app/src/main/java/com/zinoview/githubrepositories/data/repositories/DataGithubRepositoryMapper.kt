package com.zinoview.githubrepositories.data.repositories

import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.data.core.Text
import com.zinoview.githubrepositories.data.repositories.DataGithubRepository


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
class DataGithubRepositoryMapper(
    private val text: Text
) : Abstract.RepositoryMapper<DataGithubRepository> {

    override fun map(name: String, private: Boolean, language: String): DataGithubRepository
        = DataGithubRepository(text.subString(name), private, language)
}