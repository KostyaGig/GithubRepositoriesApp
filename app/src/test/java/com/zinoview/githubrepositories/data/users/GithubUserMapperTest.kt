package com.zinoview.githubrepositories.data.users

import com.zinoview.githubrepositories.core.Abstract
import org.junit.Assert.*
import org.junit.Test

/**
 * Test for [DataGithubUserMapper]
 */

class GithubUserMapperTest {

    @Test
    fun test_success_map() {

        val name = "Bib"
        val bio = "This is bio bib"

        val expected = "Bib-This is bio bib"
        val actual = TestMapper().map(name,bio,"",true)

        assertEquals(expected, actual)
    }

    @Test
    fun test_failure_map() {

        val name = "Bib"
        val bio = ""

        val expected = "Bib-"
        val actual = TestMapper().map(name,bio,"",true)

        assertEquals(expected, actual)
    }

    private inner class TestMapper : Abstract.UserMapper<String> {

        override fun map(
            name: String,
            bio: String,
            imageUrl: String,
            isCollapsed: Boolean
        ): String
            = "$name-$bio"

    }
}