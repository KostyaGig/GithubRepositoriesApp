package com.zinoview.githubrepositories.domain.users

import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.data.users.GithubUserRepository
import com.zinoview.githubrepositories.domain.core.GithubInteractor
import io.reactivex.Single


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
interface GithubUserInteractor : GithubInteractor<DomainGithubUser> {

    fun users() : Single<List<DomainGithubUser>>

    class Base(
        private val githubUserRepository: GithubUserRepository,
        private val domainGithubUserMapper: Abstract.UserMapper<DomainGithubUser>
    ) : GithubUserInteractor {

        override fun data(query: String): Single<DomainGithubUser>{
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