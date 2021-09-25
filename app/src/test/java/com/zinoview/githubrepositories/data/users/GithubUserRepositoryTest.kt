package com.zinoview.githubrepositories.data.users

import com.zinoview.githubrepositories.data.repositories.GithubRepoRepository
import com.zinoview.githubrepositories.data.users.cloud.GithubUserCloudDataSource
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Test for [GithubUserRepository.Test]
 * */

class GithubUserRepositoryTest {

    private var repository: GithubUserRepository<GithubUserCloudDataSource.Test.TestDataGithubUser,List<GithubUserCloudDataSource.Test.TestDataGithubUser>>? = null

    @Before
    fun setUp() {
        repository = GithubUserRepository.Test(
            GithubUserCloudDataSource.Test()
        )
    }
    @Test
    fun test_fetch_user_from_cloud() {
        val cloudUser = repository?.user("Bib")
        val expected = true
        val actual = cloudUser?.same("Bib","This is short Bib bio")

        assertEquals(expected, actual)
    }

    @Test
    fun test_fetch_users_from_cache() {
        val expected = listOf(
            GithubUserCloudDataSource.Test.TestDataGithubUser("Bib","This is short Bib bio"),
            GithubUserCloudDataSource.Test.TestDataGithubUser("Lob","This is short Lob bio"),
            GithubUserCloudDataSource.Test.TestDataGithubUser("Tip","This is short Tip bio")
        )
        val actual = repository?.users()

        assertEquals(expected, actual)
    }
}