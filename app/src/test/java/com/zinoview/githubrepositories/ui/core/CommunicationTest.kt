package com.zinoview.githubrepositories.ui.core

import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test


/**
 * Test for [Communication.TestCommunication.Test]
 */

class CommunicationTest {

    private var communication: Communication.TestCommunication<String>? = null

    @Before
    fun setUp() {
        communication = Communication.TestCommunication.Test()

    }

    @Test
    fun test_live_data_value() {
        val newValue = "John"
        communication?.changeValue(newValue)

        var expected = "John"
        var actual = communication?.lastValue()

        assertEquals(expected, actual)

        communication?.changeValue("Vasya")

        communication?.changeValue("Jek")

        expected = "Jek"
        actual = communication?.lastValue()

        assertEquals(expected, actual)
    }

    @Test
    fun test_live_data_observe() {
        communication?.observe()
        var expected = 1
        var actual = communication?.countObserve()

        assertEquals(expected, actual)

        communication?.observe()

        communication?.observe()

        expected = 3
        actual = communication?.countObserve()
        assertEquals(expected, actual)
    }

    @After
    fun clear() {
        communication = null
    }
}