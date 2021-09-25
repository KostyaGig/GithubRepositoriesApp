package com.zinoview.githubrepositories.ui.repositories

import org.junit.Assert.*
import org.junit.Test
import kotlin.math.exp


/**
 * Test for [TempGithubUserName.Test]
 */

class TempGithubUserNameTest {

    @Test
    fun test_success_add_name() {

        val testTempGithubUserName = TempGithubUserName.Test()

        var expected = "default name"
        var actual = testTempGithubUserName.currentName()

        assertEquals(expected, actual)

        val newCurrentName = "Bob"
        testTempGithubUserName.addName(newCurrentName)

        expected = "Bob"
        actual = testTempGithubUserName.currentName()

        assertEquals(expected, actual)
    }
}