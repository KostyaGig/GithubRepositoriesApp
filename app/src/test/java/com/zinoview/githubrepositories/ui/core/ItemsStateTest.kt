package com.zinoview.githubrepositories.ui.core

import org.junit.Assert.*
import org.junit.Test

/**
* Test for [ItemsState.Test]
*/

class ItemsStateTest {

    @Test
    fun test_update_current_state() {
        val itemsState = ItemsState.Test()

        var expected = ItemsState.Test.TestItemsState.UNKNOWN
        var actual = itemsState.currentState()

        assertEquals(expected, actual)

        itemsState.updateState(ItemsState.Test.TestItemsState.GOOD)

        expected = ItemsState.Test.TestItemsState.GOOD
        actual = itemsState.currentState()

        assertEquals(expected, actual)
    }
}