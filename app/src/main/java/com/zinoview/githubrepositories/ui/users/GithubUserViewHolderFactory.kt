package com.zinoview.githubrepositories.ui.users

import android.view.ViewGroup
import com.zinoview.githubrepositories.R
import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.ui.core.AbstractViewHolderFactory
import com.zinoview.githubrepositories.ui.core.adapter.CollapseOrExpandStateListener
import com.zinoview.githubrepositories.ui.core.adapter.GithubOnItemClickListener


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */

interface GithubUserViewHolderFactory : Abstract.FactoryMapper<Pair<Int,ViewGroup>, GithubUserAdapter.GithubUserViewHolder> {

    class Base (
        private val listener: GithubOnItemClickListener<String>,
        private val collapseOrExpandListener: CollapseOrExpandStateListener<UiGithubUserState>
    ) : GithubUserViewHolderFactory, AbstractViewHolderFactory() {

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
}


