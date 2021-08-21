package com.zinoview.githubrepositories.ui.users

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zinoview.githubrepositories.R
import com.zinoview.githubrepositories.core.Abstract


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
class GithubUserViewHolderFactory : Abstract.FactoryMapper<Pair<Int,ViewGroup>, GithubUserAdapter.GithubUserViewHolder> {

    override fun map(src: Pair<Int,ViewGroup>): GithubUserAdapter.GithubUserViewHolder = when(src.first) {
        1 -> GithubUserAdapter.GithubUserViewHolder.Progress(
            R.layout.progress.makeView(src.second)
        )
        2 -> GithubUserAdapter.GithubUserViewHolder.Base(
            R.layout.github_user_layout.makeView(src.second)
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