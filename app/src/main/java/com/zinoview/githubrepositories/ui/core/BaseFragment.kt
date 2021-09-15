package com.zinoview.githubrepositories.ui.core

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.zinoview.githubrepositories.R
import com.zinoview.githubrepositories.core.GAApp
import com.zinoview.githubrepositories.data.core.prefs.CachedState
import com.zinoview.githubrepositories.ui.users.CollapseOrExpandState
import com.zinoview.githubrepositories.ui.users.CollapseOrExpandStateFactory


/**
 * @author Zinoview on 22.08.2021
 * k.gig@list.ru
 */
abstract class BaseFragment(@LayoutRes layoutResId: Int) : Fragment(layoutResId) {

    private lateinit var searchView: SearchView

    private val activity by lazy {
        requireActivity() as MainActivity
    }

    private val collapseOrExpandStateFactory by lazy {
        val resource = (activity.application as GAApp).resource()
        CollapseOrExpandStateFactory.Base(resource)
    }


    protected fun <T : ViewModel> viewModel(clazz: Class<T>,owner: ViewModelStoreOwner)
        = (activity.application as GAApp).viewModel(clazz,owner)

    protected fun <T : CachedState> cachedState(clazz: Class<T>)
        = (activity.application as GAApp).cachedState(clazz)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        message("onCreate")
    }

    override fun onStop() {
        super.onStop()
        message("onStop")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        message("onCreateOptionsMenu")
        val menuInflater = requireActivity().menuInflater
        menuInflater.inflate(R.menu.main_menu,menu)

        val searchItem = requireNotNull(menu.findItem(R.id.action_search))
        searchView = searchItem.actionView as SearchView

        searchByQuery(searchView)

        searchItem.menuCollapsedState {
            menuCollapsedState()
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onResume() {
        super.onResume()
        message("OnResume")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        if (itemId != R.id.action_filter && itemId != R.id.action_search) {
            val state = collapseOrExpandStateFactory.map(itemId)
            dataByState(state)
        }

        return true
    }

    private fun MenuItem.menuCollapsedState(collapse: () -> Unit) {
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

    protected fun changeTitleToolbar(title: String) {
        val toolbar = activity.toolbar
        toolbar?.let {
            it.title = title
        }
    }

    protected fun changeTitleToolbar(text: String? = "",stringResId: Int) {
        val toolbar = activity.toolbar
        val title = "$text" + activity.resources.getString(stringResId)

    }

    protected fun changeTitleToolbar(stringResId: Int,text: String? = "") {
        val toolbar = activity.toolbar
        val title = activity.resources.getString(stringResId) + "$text"
        toolbar?.let {
            it.title = title
        }
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

    abstract fun dataByState(state: CollapseOrExpandState)

    abstract fun searchByQuery(searchView: SearchView)

    abstract fun menuCollapsedState()

    abstract fun previousFragment() : BaseFragment
}