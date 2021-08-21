package com.zinoview.githubrepositories.domain.users

import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.data.users.GithubUserRepository
import com.zinoview.githubrepositories.ui.message
import io.reactivex.Flowable
import io.reactivex.Single


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
interface GithubUserInteractor {

    fun user(query: String): Single<DomainGithubUser>

    fun users() : Single<List<DomainGithubUser>>

    class Base(
        private val githubUserRepository: GithubUserRepository,
        private val domainGithubUserMapper: Abstract.UserMapper<DomainGithubUser>
    ) : GithubUserInteractor {

        override fun user(query: String): Single<DomainGithubUser>{
            message("one user interactor")
            message("Request current user thread -> ${Thread.currentThread().name}")
            return githubUserRepository.user(query).flatMap {dataGithubUser ->
                Single.just(dataGithubUser.map(domainGithubUserMapper))
            }
        }

        override fun users(): Single<List<DomainGithubUser>>
            = githubUserRepository.users().flatMap { dataGithubUsers ->
                Single.just( dataGithubUsers.map { it.map(domainGithubUserMapper) } )
        }
    }
}