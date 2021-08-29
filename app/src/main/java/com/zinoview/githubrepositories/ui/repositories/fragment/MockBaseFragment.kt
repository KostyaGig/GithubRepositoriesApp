package com.zinoview.githubrepositories.ui.repositories.fragment

import com.zinoview.githubrepositories.R
import com.zinoview.githubrepositories.ui.core.BaseFragment
import com.zinoview.githubrepositories.ui.users.CollapseOrExpandState


/**
 * @author Zinoview on 25.08.2021
 * k.gig@list.ru
 */

class MockBaseFragment: BaseFragment(R.layout.failure) {
    override fun searchByQuery(searchView: androidx.appcompat.widget.SearchView)
            = throw IllegalStateException("MockBaseFragment not use this method")

    override fun collapseState()
            = throw IllegalStateException("MockBaseFragment not use this method")

    override fun dataByState(state: CollapseOrExpandState)
            = throw IllegalStateException("MockBaseFragment not use this method")

    override fun previousFragment()
            = throw IllegalStateException("MockBaseFragment not use this method")
}