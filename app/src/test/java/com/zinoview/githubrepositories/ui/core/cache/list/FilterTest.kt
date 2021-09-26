package com.zinoview.githubrepositories.ui.core.cache.list

import org.junit.Assert.*
import org.junit.Test

/**
 * Test for [Filter.TestLengthStringFilter]
 **/

class FilterTest {

    @Test
    fun test_filter_string_by_length() {

        val strings = listOf("Common string","HIS","lsplapw","lofiko","mena","01isjnf","[ps")
        val testLengthStringFilter = Filter.TestLengthStringFilter()

        val expectedSize = 4
        val actualSize = testLengthStringFilter.filter(strings).size

        assertEquals(expectedSize,actualSize)
    }
}