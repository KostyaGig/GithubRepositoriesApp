package com.zinoview.githubrepositories.ui.core.view

import android.content.Context
import android.util.AttributeSet
import com.squareup.picasso.Picasso
import com.zinoview.githubrepositories.R
import com.zinoview.githubrepositories.ui.repositories.view.DrawableImageFactory
import java.lang.IllegalArgumentException


/**
 * @author Zinoview on 31.08.2021
 * k.gig@list.ru
 */
abstract class GithubImageView : androidx.appcompat.widget.AppCompatImageView, AbstractView {

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
        is GithubViewType.RepositoryPrivateImage -> {
            if (private)
                setImageResource(R.drawable.ic_private)
            else
                setImageResource(R.drawable.ic_public)
        }
        is GithubViewType.RepositoryLanguageColorImage -> {
            val drawableImageFactory = DrawableImageFactory()
            val drawableByLanguage = drawableImageFactory.map(language)
            setImageDrawable(resources.getDrawable(drawableByLanguage))
        }
        else -> throw IllegalArgumentException("Not found viewType for repo (image) ${githubViewType()}")
    }

    override fun map(name: String, bio: String, imageUrl: String, isCollapsed: Boolean)
        = when(githubViewType()) {
          is GithubViewType.UserProfileImage -> Picasso.get().load(imageUrl).into(this)
          else -> throw IllegalArgumentException("Not found viewType for user (image) ${githubViewType()}")
    }

}