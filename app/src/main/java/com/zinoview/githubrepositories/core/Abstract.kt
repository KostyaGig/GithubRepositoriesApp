package com.zinoview.githubrepositories.core

import android.view.View
import com.zinoview.githubrepositories.R
import com.zinoview.githubrepositories.data.users.DataGithubUser
import com.zinoview.githubrepositories.data.users.cache.CacheGithubUser
import com.zinoview.githubrepositories.domain.users.DomainGithubUser
import com.zinoview.githubrepositories.ui.users.UiGithubUser


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
abstract class Abstract {

    interface Object<T,M : Mapper> {

        fun map(mapper: M) : T

        interface Data : Object<DataGithubUser,UserMapper<DataGithubUser>> {
            // for дальнейшем такая же иерархия будет и для домена, и для юай

//            interface GithubUser<T,M> : Data<T,M>
//            interface GithubRepository : Data<T,M>
        }

        interface Cache<T> : Data {
            fun map(mapper: UserMapper<T>) : T
        }

        interface Domain : Object<DomainGithubUser,UserMapper<DomainGithubUser>>

        interface Ui<T> : Object<T,UserMapper<T>>
    }

    interface Mapper

    interface UserMapper<T> : Mapper {
        fun map(name: String,bio: String,imageUrl: String) : T
    }

    interface FactoryMapper<S,R> {

        fun map(src: S) : R
    }
}