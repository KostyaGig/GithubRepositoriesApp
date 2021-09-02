package com.zinoview.githubrepositories.ui.core.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.zinoview.githubrepositories.ui.core.view.CollapseView


/**
 * @author Zinoview on 25.08.2021
 * k.gig@list.ru
 */
class GithubLinearLayout : LinearLayout, CollapseView {

    //region constructors
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    //endregion


    override fun map(isCollapsed: Boolean) = if(isCollapsed)
            visibility(View.GONE)
        else
            visibility(View.VISIBLE)


    private fun visibility(visibility: Int) {
        this.visibility = visibility
    }
}