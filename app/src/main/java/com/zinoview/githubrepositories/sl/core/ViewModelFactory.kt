package com.zinoview.githubrepositories.sl.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zinoview.githubrepositories.ui.repositories.GithubRepositoryViewModel
import com.zinoview.githubrepositories.ui.users.GithubUserViewModel
import java.lang.IllegalArgumentException


/**
 * @author Zinoview on 25.08.2021
 * k.gig@list.ru
 */
class ViewModelFactory(
    private val container: DependencyContainer
) : ViewModelProvider.Factory {

    private val featuresByClass = HashMap<Class<*>,Feature>().apply {
        put(GithubUserViewModel.Base::class.java,Feature.User)
        put(GithubRepositoryViewModel.Base::class.java,Feature.Repository)
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val feature = featuresByClass[modelClass] ?:
            throw IllegalArgumentException("Feature by class ${modelClass::class.java} not found")
        return container.module(feature).viewModel() as T
    }
}