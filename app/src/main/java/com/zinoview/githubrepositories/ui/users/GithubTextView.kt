package com.zinoview.githubrepositories.ui.users

import android.content.Context
import android.util.AttributeSet
import com.zinoview.githubrepositories.ui.core.AbstractView


/**
 * @author Zinoview on 20.08.2021
 * k.gig@list.ru
 */
abstract class GithubTextView : androidx.appcompat.widget.AppCompatTextView, AbstractView {
    //region constructors
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
    //endregion

    override fun map(name: String, bio: String, imageUrl: String) = if (isName()) {
        setText(name)
    } else {
        setText(bio)
    }

    override fun map(name: String, private: Boolean, language: String) = if (isName()) {
        setText(name)
    } else {
        setText(language)
    }

    abstract fun isName(): Boolean
}