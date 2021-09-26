package com.zinoview.githubrepositories.ui.core.view

import android.content.Context
import android.util.AttributeSet
import com.zinoview.githubrepositories.data.core.Text
import java.lang.IllegalArgumentException


/**
 * @author Zinoview on 30.08.2021
 * k.gig@list.ru
 */

abstract class GithubTextView : androidx.appcompat.widget.AppCompatTextView, AbstractView {

    abstract fun githubViewType() : GithubViewType

    //region constructors
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
    //endregion


    override fun map(
        name: String,
        private: Boolean,
        language: String,
        owner: String,
        urlRepository: String,
        defaultBranch: String,
        isCollapsed: Boolean
    ) = when(githubViewType()) {
        is GithubViewType.RepositoryName -> {
            if (isCollapsed) {
                val text = Text.GithubName()
                val shortName = text.subText(name)
                setText(shortName)
            } else {
                setText(name)
            }
        }

        is GithubViewType.RepositoryLanguage -> setText(language)
        is GithubViewType.RepositoryDefaultBranch -> setText(defaultBranch)
        is GithubViewType.Error -> setText(name)
        else -> throw IllegalArgumentException("Not found viewType for repo ${githubViewType()}")
    }

    override fun map(name: String, bio: String, imageUrl: String, isCollapsed: Boolean)
        = when(githubViewType()) {
            is GithubViewType.UserName -> setText(name)
            is GithubViewType.UserBio -> setText(bio)
            is GithubViewType.Error -> setText(name)
            else -> throw IllegalArgumentException("Not found viewType for user ${githubViewType()}")
    }
}