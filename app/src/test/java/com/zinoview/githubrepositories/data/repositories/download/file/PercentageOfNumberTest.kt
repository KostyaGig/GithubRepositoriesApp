package com.zinoview.githubrepositories.data.repositories.download.file

import com.zinoview.githubrepositories.ui.core.cache.list.ContainsItem
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Test for [PercentageOfNumber.Test]
 **/

class PercentageOfNumberTest {

    private var percentageOfNumber: PercentageOfNumber? = null

    @Before
    fun setUp() {
        percentageOfNumber = PercentageOfNumber.Test()
    }

    @Test
    fun success_taking_percentage_of_number() {

        val number1 = 100
        val number2 = 50
        val expected = 0.5
        val actual = percentageOfNumber?.take(number1, number2)
        assertEquals(expected, actual)
    }

    @Test
    fun success_taking_percentage_of_number_more() {
        val number1 = 90
        val number2 = 30
        val expected = 0.3
        val actual = percentageOfNumber?.take(number1, number2)
        assertEquals(expected, actual)
    }

    @After
    fun clear() {
        percentageOfNumber = null
    }
}