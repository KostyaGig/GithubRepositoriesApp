package com.zinoview.githubrepositories.ui.users

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zinoview.githubrepositories.R
import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.ui.core.AbstractListener
import com.zinoview.githubrepositories.ui.core.CollapseOrExpandListener
import com.zinoview.githubrepositories.ui.core.GithubOnItemClickListener


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
class GithubUserViewHolderFactory(
    private val listener: GithubOnItemClickListener,
    private val collapseOrExpandListener: CollapseOrExpandListener<UiGithubUserState>
) : Abstract.FactoryMapper<Pair<Int,ViewGroup>, GithubUserAdapter.GithubUserViewHolder> {

    override fun map(src: Pair<Int,ViewGroup>): GithubUserAdapter.GithubUserViewHolder = when(src.first) {
        1 -> GithubUserAdapter.GithubUserViewHolder.Progress(
            R.layout.progress.makeView(src.second)
        )
        2 -> GithubUserAdapter.GithubUserViewHolder.Base(
            R.layout.github_user_layout.makeView(src.second),
            listener,
            collapseOrExpandListener
        )
        3 -> GithubUserAdapter.GithubUserViewHolder.Empty(
            R.layout.empty.makeView(src.second)
        )
        else -> GithubUserAdapter.GithubUserViewHolder.Failure(
            R.layout.failure.makeView(src.second)
        )
    }
}

fun Int.makeView(parent: ViewGroup) : View
        = LayoutInflater.from(parent.context).inflate(this,parent,false)