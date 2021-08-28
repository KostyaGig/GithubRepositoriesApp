package com.zinoview.githubrepositories.ui.repositories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zinoview.githubrepositories.R
import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.ui.core.CollapseOrExpandListener


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
class GithubRepositoryViewHolderFactory(
    private val collapseOrExpandListener: CollapseOrExpandListener<UiGithubRepositoryState>
) : Abstract.FactoryMapper<Pair<Int,ViewGroup>, GithubRepositoryAdapter.GithubRepositoryViewHolder> {

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
//todo compare this file with GithubRepositoryViewHolder and move shared elements
fun Int.makeView(parent: ViewGroup) : View
        = LayoutInflater.from(parent.context).inflate(this,parent,false)