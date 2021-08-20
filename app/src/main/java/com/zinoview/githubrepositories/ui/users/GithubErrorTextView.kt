package com.zinoview.githubrepositories.ui.users

import android.content.Context
import android.util.AttributeSet


/**
 * @author Zinoview on 20.08.2021
 * k.gig@list.ru
 */

class GithubErrorTextView : GithubTextView {
    //region constructors
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
    //endregion

    override fun isName(): Boolean = true
}