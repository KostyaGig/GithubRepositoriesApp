package com.zinoview.githubrepositories.domain.users

import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.core.SaveState
import com.zinoview.githubrepositories.data.users.GithubUserRepository
import com.zinoview.githubrepositories.domain.core.GithubInteractor
import com.zinoview.githubrepositories.domain.core.DomainDownloadExceptionMapper
import com.zinoview.githubrepositories.ui.users.CollapseOrExpandState
import io.reactivex.Single


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
interface GithubUserInteractor : GithubInteractor<DomainGithubUser>,
    SaveState {

    fun users() : Single<List<DomainGithubUser>>

    class Base(
        private val githubUserRepository: GithubUserRepository,
        private val domainGithubUserMapper: Abstract.UserMapper<DomainGithubUser>,
        private val domainDownloadExceptionMapper: DomainDownloadExceptionMapper
    ) : GithubUserInteractor {

        override fun data(query: String): Single<DomainGithubUser>{
            return try {
                githubUserRepository.user(query).flatMap {dataGithubUser ->
                    Single.just(dataGithubUser.map(domainGithubUserMapper))
                }
            } catch (e: Exception) {
                throw domainDownloadExceptionMapper.map(e)
            }
        }

        override fun users(): Single<List<DomainGithubUser>>
            = githubUserRepository.users().flatMap { dataGithubUsers ->
                Single.just( dataGithubUsers.map { it.map(domainGithubUserMapper) } )
        }

        override fun saveState(state: CollapseOrExpandState)
            = githubUserRepository.saveState(state)
    }
}