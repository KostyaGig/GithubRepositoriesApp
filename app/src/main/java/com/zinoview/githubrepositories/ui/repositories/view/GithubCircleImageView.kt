package com.zinoview.githubrepositories.ui.repositories.view

import android.content.Context
import android.util.AttributeSet
import com.zinoview.githubrepositories.ui.core.AbstractView
import java.lang.IllegalStateException


/**
 * @author Zinoview on 20.08.2021
 * k.gig@list.ru
 */
class GithubCircleImageView : androidx.appcompat.widget.AppCompatImageView, AbstractView {
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
        = throw IllegalStateException("GithubCircleImage view not use it")

    override fun map(
        name: String,
        private: Boolean,
        language: String,
        owner: String,
        urlRepository: String,
        defaultBranch: String,
        isCollapsed: Boolean
    ) {
        val drawableImageFactory = DrawableImageFactory()
        val drawableByLanguage = drawableImageFactory.map(language)
        setImageDrawable(resources.getDrawable(drawableByLanguage))
    }


}