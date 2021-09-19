package com.zinoview.githubrepositories.ui.users

import com.zinoview.githubrepositories.R
import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.core.Resource
import com.zinoview.githubrepositories.data.repositories.DataByStateNotFoundException
import com.zinoview.githubrepositories.domain.repositories.download.exceptions.NoConnectionException
import com.zinoview.githubrepositories.ui.core.message
import io.reactivex.exceptions.CompositeException
import retrofit2.adapter.rxjava2.HttpException


/**
 * @author Zinoview on 20.08.2021
 * k.gig@list.ru
 * Note for Composite Exception: which we throw exception inside onErrorResumeNext: getting CompositeException
 * Example:
 * Observable.onErrorResumeNext {
 *      throw MyException()
 * }.onError{ e -> e == CompositeException }
 */

interface UiGithubExceptionMapper : Abstract.FactoryMapper<Throwable, String> {

    class Base(
        private val resource: Resource
    ) : UiGithubExceptionMapper {

        override fun map(src: Throwable): String = when(src) {
            is NoConnectionException -> resource.string(R.string.no_connection_error)
            is HttpException, is retrofit2.HttpException -> resource.string(R.string.github_not_found_error)
            is DataByStateNotFoundException, is CompositeException -> resource.string(R.string.data_by_state_not_found)
            else -> {
                message("Some went wrong exc: ${src::class.java}")
                resource.string(R.string.generic_error)
            }
        }

    }
}
