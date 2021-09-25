package com.zinoview.githubrepositories.ui.core

import org.junit.Assert.*
import org.junit.Test


/**
 * Test for [ListWrapper.TestModel]
 */


class ListWrapperTest {

    @Test
    fun test_success_wrap_to_list_object() {

        val testModel = ListWrapper.TestModel()

        val expected = listOf(testModel)
        val actual = testModel.wrap()

        assertEquals(expected,actual)
    }
}