package com.zinoview.githubrepositories.ui.users

import android.content.Context
import android.util.AttributeSet
import com.squareup.picasso.Picasso
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

    override fun map(name: String, bio: String, imageUrl: String)
        = Picasso.get().load(imageUrl).into(this)


}