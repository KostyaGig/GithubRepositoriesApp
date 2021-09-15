package com.zinoview.githubrepositories.ui.repositories

import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.core.Same
import com.zinoview.githubrepositories.data.repositories.cache.CacheGithubRepository
import com.zinoview.githubrepositories.ui.core.adapter.AbstractListener
import com.zinoview.githubrepositories.ui.core.adapter.GithubOnItemClickListener
import com.zinoview.githubrepositories.ui.core.view.AbstractView

/**
 * @author Zinoview on 21.08.2021
 * k.gig@list.ru
 */
open class UiGithubRepository(
    private val name: String,
    private val private: Boolean,
    private val language: String,
    private val owner: String,
    private val urlRepository: String,
    private val defaultBranch: String,
    private val isCollapsed: Boolean
) : Abstract.Object.Ui.GithubRepository<UiGithubRepositoryState>,
    Abstract.FactoryMapper<List<AbstractView>,Unit>,
    Abstract.UniqueMapper<CacheGithubRepository,Boolean>,
    AbstractListener<GithubOnItemClickListener<Pair<String,String>>>,
    Same<UiGithubRepository>,
    ModelState<UiGithubRepository> {

    class Mock :
        UiGithubRepository(
            "",
            false,
            "",
            "",
            "",
            "",
            true
        )

    override fun map(mapper: Abstract.RepositoryMapper<UiGithubRepositoryState>): UiGithubRepositoryState
        = mapper.map(name, private, language,owner,urlRepository, defaultBranch, isCollapsed)

    override fun map(src: List<AbstractView>) {
        src.map { view -> view.map(name, private, language,owner,urlRepository, defaultBranch, isCollapsed) }
    }

    override fun mapTo(isCollapsed: Boolean) : CacheGithubRepository
        = CacheGithubRepository(name, private,language,owner,urlRepository, defaultBranch, isCollapsed)

    override fun modelWithChangedState(): UiGithubRepository
        = UiGithubRepository(name, private, language, owner, urlRepository, defaultBranch, isCollapsed.not())

    override fun notifyAboutItemClick(listener: GithubOnItemClickListener<Pair<String, String>>)
        = listener.onItemClick(Pair(owner,name))

    override fun same(element: UiGithubRepository): Boolean
        = element.same(name, language)

    private fun same(name: String,language: String)
        = this.name == name && this.language == language


}