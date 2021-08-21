package com.zinoview.githubrepositories.ui.users

import com.zinoview.githubrepositories.R
import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.core.Resource
import retrofit2.adapter.rxjava2.HttpException
import java.net.UnknownHostException


/**
 * @author Zinoview on 20.08.2021
 * k.gig@list.ru
 */
class UiGithubExceptionMapper(
    private val resource: Resource
) : Abstract.FactoryMapper<Throwable, String> {

    override fun map(src: Throwable): String = when(src) {
        is UnknownHostException -> resource.string(R.string.no_connection_error)
        is HttpException -> resource.string(R.string.github_user_not_found_error)
        else -> resource.string(R.string.github_user_generic_error)
    }

}