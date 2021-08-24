package com.zinoview.githubrepositories.core

import android.view.View
import com.zinoview.githubrepositories.R
import com.zinoview.githubrepositories.data.repositories.DataGithubRepository
import com.zinoview.githubrepositories.data.repositories.cache.CacheGithubRepository
import com.zinoview.githubrepositories.data.repositories.cache.CacheGithubRepositoryMapper
import com.zinoview.githubrepositories.data.users.DataGithubUser
import com.zinoview.githubrepositories.data.users.cache.CacheGithubUser
import com.zinoview.githubrepositories.domain.repositories.DomainGithubRepository
import com.zinoview.githubrepositories.domain.users.DomainGithubUser
import com.zinoview.githubrepositories.ui.repositories.UiGithubRepository
import com.zinoview.githubrepositories.ui.users.UiGithubUser


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
            interface GithubRepository<T> : Data.GithubRepository {
                fun map(mapper: CacheGithubRepositoryMapper,userName: String) : T
            }
        }

        interface Domain<T,M: Mapper> : Object<T,M> {

            interface GithubUser : Domain<DomainGithubUser,UserMapper<DomainGithubUser>>
            interface GithubRepository : Domain<DomainGithubRepository,RepositoryMapper<DomainGithubRepository>>
        }

        interface Ui<T,M : Mapper> : Object<T,M> {

            interface GithubUser<T> : Ui<T,UserMapper<T>>
            interface GithubRepository<T> : Ui<T,RepositoryMapper<T>>
        }
    }

    interface Mapper

    interface UserMapper<T> : Mapper {
        fun map(name: String,bio: String,imageUrl: String) : T
    }

    interface RepositoryMapper<T> : Mapper {

        fun map(name: String, private: Boolean,language: String) : T
    }

    interface FactoryMapper<S,R> {

        fun map(src: S) : R
    }
}