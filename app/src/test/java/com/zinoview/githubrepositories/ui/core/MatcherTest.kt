package com.zinoview.githubrepositories.ui.core

import org.junit.Assert.*
import org.junit.Test

/**
 * Test for [Matcher.TestMatcherModel]
 */


class MatcherTest {

    @Test
    fun test_success_matches_items() {
        val item = "liko"
        val matcherModel = Matcher.TestMatcherModel("liko")

        val expected = true
        val actual = matcherModel.matches(item)

        assertEquals(expected, actual)
    }
}