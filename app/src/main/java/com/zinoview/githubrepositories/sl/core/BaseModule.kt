package com.zinoview.githubrepositories.sl.core

import androidx.lifecycle.ViewModel
import com.zinoview.githubrepositories.ui.core.BaseViewModel


/**
 * @author Zinoview on 25.08.2021
 * k.gig@list.ru
 */
interface BaseModule<T : ViewModel> {

    fun viewModel() : T
}