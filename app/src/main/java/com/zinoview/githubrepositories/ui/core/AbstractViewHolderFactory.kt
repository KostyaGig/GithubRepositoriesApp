package com.zinoview.githubrepositories.ui.core

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


/**
 * @author Zinoview on 30.08.2021
 * k.gig@list.ru
 */

abstract class AbstractViewHolderFactory {

    protected fun Int.makeView(parent: ViewGroup) : View
        = LayoutInflater.from(parent.context).inflate(this,parent,false)
}