package com.zinoview.githubrepositories.ui.users.view

import android.content.Context
import android.util.AttributeSet
import com.squareup.picasso.Picasso
import com.zinoview.githubrepositories.R
import com.zinoview.githubrepositories.ui.core.AbstractView


/**
 * @author Zinoview on 20.08.2021
 * k.gig@list.ru
 */
class GithubUserProfileImageView : androidx.appcompat.widget.AppCompatImageView, AbstractView {
    //region constructors
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
    //endregion

    override fun map(name: String, bio: String, imageUrl: String,isCollapsed: Boolean)
        = Picasso.get().load(imageUrl).into(this)

    override fun map(
        name: String,
        private: Boolean,
        language: String,
        owner: String,
        urlRepository: String,
        defaultBranch: String,
        isCollapsed: Boolean
    ) = if (private)
            setImageResource(R.drawable.ic_private)
        else
            setImageResource(R.drawable.ic_public)
}