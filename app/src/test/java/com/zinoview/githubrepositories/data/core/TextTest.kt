package com.zinoview.githubrepositories.data.core

import org.junit.Assert.*
import org.junit.Test

/**
 * Test for [Text.Test]
 */


class TextTest {

    @Test
    fun test_substring() {
        val string = "Common text"
        val expected = "Common text"
        val actual = Text.Test().subText(string)

        assertEquals(expected, actual)
    }

    @Test
    fun test_not_substring() {
        val string = "Common text and common data"
        val expected = "Common text and..."
        val actual = Text.Test().subText(string)

        assertEquals(expected, actual)
    }


}