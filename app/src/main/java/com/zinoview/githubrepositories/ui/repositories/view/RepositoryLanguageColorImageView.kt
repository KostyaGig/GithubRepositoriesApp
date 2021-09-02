package com.zinoview.githubrepositories.ui.repositories.view

import android.content.Context
import android.util.AttributeSet
import com.zinoview.githubrepositories.ui.core.view.GithubImageView
import com.zinoview.githubrepositories.ui.core.view.GithubTextView
import com.zinoview.githubrepositories.ui.core.view.GithubViewType


/**
 * @author Zinoview on 20.08.2021
 * k.gig@list.ru
 */
class RepositoryLanguageColorImageView : GithubImageView {

    //region constructors
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
    //endregion

    override fun githubViewType(): GithubViewType
        = GithubViewType.RepositoryLanguageColorImage
}