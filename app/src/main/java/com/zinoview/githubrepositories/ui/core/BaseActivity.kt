package com.zinoview.githubrepositories.ui.core

import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity


/**
 * @author Zinoview on 21.08.2021
 * k.gig@list.ru
 */
abstract class BaseActivity : AppCompatActivity() {


    protected fun MenuItem.collapseState(collapse: () -> Unit) {
        this.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                collapse()
                return true
            }
        })
    }
}