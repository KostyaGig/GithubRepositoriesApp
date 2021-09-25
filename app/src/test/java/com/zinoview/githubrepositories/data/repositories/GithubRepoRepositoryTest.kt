package com.zinoview.githubrepositories.data.repositories

import com.zinoview.githubrepositories.data.repositories.cloud.GithubRepositoryCloudDataSource
import com.zinoview.githubrepositories.data.repositories.download.DownloadRepoRepository
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Test for [GithubRepoRepository.Test]
 **/

class GithubRepoRepositoryTest {

    private var testRepository: GithubRepoRepository<GithubRepositoryCloudDataSource.Test.TestCloudRepository,List<GithubRepositoryCloudDataSource.Test.TestCloudRepository>>? = null

    @Before
    fun setUp() {
        testRepository = GithubRepoRepository.Test(
            GithubRepositoryCloudDataSource.Test()
        )
    }

    @Test
    fun fetch_repository() {
        val testCloudRepositoryModel = testRepository?.repository("Bib","Repos#2789")
        val expected = true
        val actual = testCloudRepositoryModel?.same("Bib")
        assertEquals(expected, actual)

    }

    @Test
    fun fetch_repositories() {
        val expectedCloudRepositories = listOf(
            GithubRepositoryCloudDataSource.Test.TestCloudRepository("Bill1", "Java"),
            GithubRepositoryCloudDataSource.Test.TestCloudRepository("Bill2", "Scala"),
            GithubRepositoryCloudDataSource.Test.TestCloudRepository("Bill3", "Kotlin")
        )

        val actualCloudRepositories = testRepository?.repositories("Bill")

        assertEquals(expectedCloudRepositories,actualCloudRepositories)
    }

    @After
    fun clear() {
        testRepository = null
    }
}