package com.zinoview.githubrepositories.ui.core.view

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import com.zinoview.githubrepositories.R


/**
 * @author Zinoview on 25.08.2021
 * k.gig@list.ru
 */
class GithubCollapseImageView : androidx.appcompat.widget.AppCompatImageView, CollapseView {

    //region constructors
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    //endregion

    override fun map(isCollapsed: Boolean) {
        if (isCollapsed) {
            image(R.drawable.ic_collapse)
        } else {
            image(R.drawable.ic_expand)
        }
    }

    private fun image(@DrawableRes drawableRes: Int)
        = this.setImageDrawable(resources.getDrawable(drawableRes))
}