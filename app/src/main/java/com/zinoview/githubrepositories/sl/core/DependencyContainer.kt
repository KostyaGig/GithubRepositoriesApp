package com.zinoview.githubrepositories.sl.core

import com.zinoview.githubrepositories.sl.repositories.GithubRepositoryModule
import com.zinoview.githubrepositories.sl.users.GithubUserModule
import java.lang.IllegalArgumentException


/**
 * @author Zinoview on 25.08.2021
 * k.gig@list.ru
 */
interface DependencyContainer {

    fun module(feature: Feature) : BaseModule<*>

    class Base(
        private val coreModule: CoreModule
    ) : DependencyContainer {

        override fun module(feature: Feature): BaseModule<*> = when(feature) {
                is Feature.User -> GithubUserModule(coreModule)
                is Feature.Repository -> GithubRepositoryModule(coreModule)
                else -> throw IllegalArgumentException("not feature $feature")
            }
        }
    }