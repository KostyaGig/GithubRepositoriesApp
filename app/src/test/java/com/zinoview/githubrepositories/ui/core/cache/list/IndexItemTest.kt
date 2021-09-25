package com.zinoview.githubrepositories.ui.core.cache.list

import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Test for [IndexItem.Test]
 */

class IndexItemTest {

    private var indexItem: IndexItem<String>? = null

    @Before
    fun setUp() {
        indexItem = IndexItem.Test()
    }

    @Test
    fun fetching_index_item_by_item() {
        val items = listOf<String>("lol","bib","bob")
        val item = "bib"

        val expected = 1
        val actual = indexItem?.indexByItem(items,item)

        assertEquals(expected, actual)
    }

    @After
    fun clear() {
        indexItem = null
    }
}
