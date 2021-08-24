package com.zinoview.githubrepositories.ui.core

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.zinoview.githubrepositories.R


/**
 * @author Zinoview on 22.08.2021
 * k.gig@list.ru
 */
abstract class BaseFragment(@LayoutRes layoutResId: Int) : Fragment(layoutResId) {

     class MockBaseFragment: BaseFragment(R.layout.failure) {
        override fun searchByQuery(searchView: androidx.appcompat.widget.SearchView)
            = throw IllegalStateException("MockBaseFragment not use this method")

        override fun collapseState()
            = throw IllegalStateException("MockBaseFragment not use this method")

        override fun previousFragment()
            = throw IllegalStateException("MockBaseFragment not use this method")
    }

    abstract fun searchByQuery(searchView: androidx.appcompat.widget.SearchView)

    abstract fun collapseState()

    abstract fun previousFragment() : BaseFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        val menuInflater = requireActivity().menuInflater
        menuInflater.inflate(R.menu.search_menu,menu)

        val searchItem = requireNotNull(menu.findItem(R.id.action_search))
        val searchView = searchItem.actionView as androidx.appcompat.widget.SearchView

        searchByQuery(searchView)

        searchItem.collapseState {
            collapseState()
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    protected fun changeTitleToolbar(title: String) {
        val toolbar = (requireActivity() as MainActivity).toolbar
        toolbar?.let { toolbar ->
            toolbar.title = title
        }
    }

    private fun MenuItem.collapseState(collapse: () -> Unit) {
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

    fun onBackPressed() : Boolean {
        val previousFragment = previousFragment()
        return if (previousFragment is MockBaseFragment) {
            true
        } else {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.container,previousFragment)
                .commit()
            false
        }
    }

    protected companion object {
        const val GITHUB_USER_NAME_EXTRA = "githubUserName"
    }
}