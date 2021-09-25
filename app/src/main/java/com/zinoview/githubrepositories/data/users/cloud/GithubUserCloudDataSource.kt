package com.zinoview.githubrepositories.data.users.cloud

import com.zinoview.githubrepositories.data.core.MutableGithubDataSource
import io.reactivex.Single


/**
 * @author Zinoview on 20.08.2021
 * k.gig@list.ru
 */
interface GithubUserCloudDataSource<T> : MutableGithubDataSource<T,Unit>{

    class Base(
        private val githubService: GithubUserService
        ) : GithubUserCloudDataSource<Single<CloudGithubUser>> {

        override fun fetchData(param: String): Single<CloudGithubUser>
            = githubService.user(param)

        override fun saveData(data: Unit)
            = throw IllegalStateException("GithubUserCloudDataSource not use saveData() method")
    }

    class Test : GithubUserCloudDataSource<Test.TestDataGithubUser> {

        override fun saveData(data: Unit) =
            throw IllegalStateException("TestGithubUserCloudDataSource not use saveData() method")

        override fun fetchData(param: String): TestDataGithubUser = TestDataGithubUser(
            param,
            "This is short $param bio"
        )

        data class TestDataGithubUser(
            private val name: String,
            private val bio: String
        ) {

            fun same(name: String, bio: String): Boolean
                = this.name == name && this.bio == bio
        }
    }


}