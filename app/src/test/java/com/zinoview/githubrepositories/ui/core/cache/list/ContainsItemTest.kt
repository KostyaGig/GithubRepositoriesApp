package com.zinoview.githubrepositories.ui.core.cache.list

import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Test for [ContainsItem.Test]
 **/

class ContainsItemTest {

    private var containsItem: ContainsItem<String>? = null

    @Before
    fun setUp() {
        containsItem = ContainsItem.Test()
    }

    @Test
    fun test_not_contains_items() {
        val firstItems = listOf("Koil","Kilo","Fiou")
        val secondItems = listOf("Koil","Kilo","Fiou1","llsa")

        val expected = true
        val actual = containsItem?.notContains(firstItems,secondItems)
        assertEquals(expected, actual)
    }

    @Test
    fun test_not_contains_item() {
        val items = listOf("Koil","Kilo","Fiou")
        val item = "Kulo"

        val expected = true
        val actual = containsItem?.notContains(items,item)
        assertEquals(expected, actual)
    }

    @Test
    fun test_contains_item() {
        val item1 = "Kulo"
        val item2 = "Kulo"

        val expected = true
        val actual = containsItem?.contains(item1,item2)
        assertEquals(expected, actual)
    }

    @After
    fun clean() {
        containsItem = null
    }
}