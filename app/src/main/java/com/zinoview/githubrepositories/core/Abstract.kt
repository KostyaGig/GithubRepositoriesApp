package com.zinoview.githubrepositories.core

import com.zinoview.githubrepositories.data.repositories.DataGithubRepository
import com.zinoview.githubrepositories.data.users.DataGithubUser
import com.zinoview.githubrepositories.domain.repositories.DomainGithubRepository
import com.zinoview.githubrepositories.domain.repositories.download.DomainDownloadFile
import com.zinoview.githubrepositories.domain.users.DomainGithubUser
import com.zinoview.githubrepositories.ui.repositories.download.UiDownloadFile
import okhttp3.ResponseBody


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
abstract class Abstract {

    interface Object<T,M : Mapper> {

        fun map(mapper: M) : T

        interface Data<T,M : Mapper> : Object<T,M> {

            interface GithubUser : Data<DataGithubUser,UserMapper<DataGithubUser>>
            interface GithubRepository : Data<DataGithubRepository,RepositoryMapper<DataGithubRepository>>
        }

        interface Cache {
            interface GithubUser<T> : Data.GithubUser{
                fun map(mapper: UserMapper<T>) : T
            }
            interface GithubRepository {
                fun <T> map(mapper: RepositoryMapper<T>,owner: String) : T
            }
        }

        interface Domain<T,M: Mapper> : Object<T,M> {

            interface GithubUser : Domain<DomainGithubUser,UserMapper<DomainGithubUser>>
            interface GithubRepository : Domain<DomainGithubRepository,RepositoryMapper<DomainGithubRepository>>
            interface GithubDownloadRepository : Domain<DomainDownloadFile, DownloadFileMapper<DomainDownloadFile>>
        }

        interface Ui<T,M : Mapper> : Object<T,M> {

            interface GithubUser<T> : Ui<T,UserMapper<T>>
            interface GithubRepository<T> : Ui<T,RepositoryMapper<T>>
            interface GithubDownloadRepository<T> : Ui<T,DownloadFileMapper<UiDownloadFile>>
        }
    }

    interface Mapper

    interface UserMapper<T> : Mapper {
        fun map(name: String,bio: String,imageUrl: String,isCollapsed: Boolean) : T
    }

    interface RepositoryMapper<T> : Mapper {

        fun map(
            name: String,
            private: Boolean,
            language: String,
            owner: String,
            urlRepository: String,
            defaultBranch: String,
            isCollapsed: Boolean
        ) : T
    }

    interface DownloadFileMapper<T> : Mapper {

        fun map() : T
        fun map(size: Long) : T
        fun map(data: ResponseBody) : T
        fun map(e: Exception) : T
    }

    interface FactoryMapper<S,R> {

        fun map(src: S) : R

    }

    interface UniqueMapper<T,P> {
        fun mapTo(param: P) : T
    }
}