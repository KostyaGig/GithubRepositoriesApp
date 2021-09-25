package com.zinoview.githubrepositories.data.repositories.cloud

import com.google.gson.annotations.SerializedName
import com.zinoview.githubrepositories.data.core.MutableGithubDataSource
import io.reactivex.Single


/**
 * @author Zinoview on 21.08.2021
 * k.gig@list.ru
 */
interface GithubRepositoryCloudDataSource<T,E> : MutableGithubDataSource<E,Unit> {

    fun repository(name: String,repo: String) : T

    class Base(
        private val githubRepositoryService: GithubRepositoryService
    ) : GithubRepositoryCloudDataSource<Single<CloudGithubRepository>,Single<List<CloudGithubRepository>>> {

        override fun repository(name: String, repo: String): Single<CloudGithubRepository>
            = githubRepositoryService.repository(name,repo)

        override fun fetchData(param: String): Single<List<CloudGithubRepository>>
            = githubRepositoryService.repositories(param)

        override fun saveData(data: Unit)
            = throw IllegalStateException("GithubRepositoryCloudDataSource not use it method")
    }

    class Test : GithubRepositoryCloudDataSource<Test.TestCloudRepository,List<Test.TestCloudRepository>> {

        override fun repository(name: String, repo: String): TestCloudRepository
            = TestCloudRepository(
                name,
                repo
            )

        override fun fetchData(param: String): List<TestCloudRepository> {
            return listOf(
                TestCloudRepository(param + "1","Java"),
                TestCloudRepository(param + "2","Scala"),
                TestCloudRepository(param + "3","Kotlin")
            )
        }

        override fun saveData(data: Unit)
            = throw java.lang.IllegalStateException("TestCloudDataSource not use saveData()")

        data class TestCloudRepository(
            private val name: String,
            private val language: String
        ) {

            fun same(name: String) : Boolean
                = this.name == name
        }
    }
}