package com.zinoview.githubrepositories.ui.repositories

import android.view.ViewGroup
import com.zinoview.githubrepositories.R
import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.ui.core.AbstractViewHolderFactory
import com.zinoview.githubrepositories.ui.core.adapter.CollapseOrExpandStateListener


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
class GithubRepositoryViewHolderFactory(
    private val collapseOrExpandListener: CollapseOrExpandStateListener<UiGithubRepositoryState>
) : Abstract.FactoryMapper<Pair<Int,ViewGroup>, GithubRepositoryAdapter.GithubRepositoryViewHolder>,
    AbstractViewHolderFactory() {

    override fun map(src: Pair<Int,ViewGroup>): GithubRepositoryAdapter.GithubRepositoryViewHolder = when(src.first) {
        1 -> GithubRepositoryAdapter.GithubRepositoryViewHolder.Progress(
            R.layout.progress.makeView(src.second)
        )
        2 -> GithubRepositoryAdapter.GithubRepositoryViewHolder.Base(
            R.layout.github_repository_layout.makeView(src.second),
            collapseOrExpandListener
        )
        3 -> GithubRepositoryAdapter.GithubRepositoryViewHolder.Empty(
                R.layout.empty.makeView(src.second)
            )
        else -> GithubRepositoryAdapter.GithubRepositoryViewHolder.Failure(
            R.layout.failure.makeView(src.second)
        )
    }

}
