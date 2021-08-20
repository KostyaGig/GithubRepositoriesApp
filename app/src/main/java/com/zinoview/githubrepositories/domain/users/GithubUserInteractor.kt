package com.zinoview.githubrepositories.domain.users

import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.data.users.GithubUserRepository
import io.reactivex.Single


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
interface GithubUserInteractor {

    fun user(query: String): Single<DomainGithubUser>

    class Base(
        private val githubUserRepository: GithubUserRepository,
        private val mapper: Abstract.UserMapper<DomainGithubUser>
    ) : GithubUserInteractor {

        override fun user(query: String): Single<DomainGithubUser> {
            return githubUserRepository.user(query).flatMap {
                    Single.just(it.map(mapper))
            }
        }
    }
}