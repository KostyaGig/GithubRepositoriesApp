package com.zinoview.githubrepositories.ui.repositories

import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.data.repositories.GithubRepoRepository
import org.junit.Assert.*
import org.junit.Test


/**
 * Test for [NumberMapper]
 **/


class FactoryMapperTest {

    @Test
    fun test_success_map() {

        val number = "two"
        val expected = 2
        val actual = NumberMapper().map(number)

        assertEquals(expected, actual)
    }

    private inner class NumberMapper : Abstract.FactoryMapper<String,Int> {

        override fun map(src: String): Int {
            return when(src) {
                "one" -> 1
                "two" -> 2
                else -> 100
            }
        }

    }
}