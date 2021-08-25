package com.zinoview.githubrepositories.ui.core

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.zinoview.githubrepositories.R
import com.zinoview.githubrepositories.core.GAApp
import com.zinoview.githubrepositories.ui.repositories.fragment.MockBaseFragment


/**
 * @author Zinoview on 22.08.2021
 * k.gig@list.ru
 */
abstract class BaseFragment(@LayoutRes layoutResId: Int) : Fragment(layoutResId) {

    abstract fun searchByQuery(searchView: androidx.appcompat.widget.SearchView)

    abstract fun collapseState()

    abstract fun previousFragment() : BaseFragment

    private val activity by lazy {
        requireActivity() as MainActivity
    }

    protected fun <T : ViewModel> viewModel(clazz: Class<T>,owner: ViewModelStoreOwner)
        = (activity.application as GAApp).viewModel(clazz,owner)

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
        val toolbar = activity.toolbar
        toolbar?.let {
            it.title = title
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