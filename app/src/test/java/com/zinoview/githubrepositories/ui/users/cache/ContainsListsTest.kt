package com.zinoview.githubrepositories.ui.users.cache

import org.junit.Test
import org.junit.Assert.*

class ContainsListsTest {

    @Test
    fun test_add_unique_elements() {
        val users = mutableListOf<TestUser>()
        val collapseStateUsers = listOf<TestUser>(
            TestUserCollapseState("Vasya",true)
        )
        if (users.containsAll(collapseStateUsers).not()) {
            users.addAll(collapseStateUsers)
        }

        val expected = listOf<TestUser>(TestUserCollapseState("Vasya",true))
        assertEquals(expected,users)
    }


    private interface TestUser {

        fun same(name: String,isCollapsed: Boolean) : Boolean
    }

    private data class TestUserCollapseState(
        private val name: String,
        private val isCollapsed: Boolean
    ) : TestUser {

        override fun equals(other: Any?): Boolean {
            val testUserCollapseState = other as TestUserCollapseState
            return testUserCollapseState.same(name,isCollapsed)
        }

        override fun hashCode(): Int {
            return super.hashCode()
        }

        override fun same(name: String, isCollapsed: Boolean): Boolean
            = this.name == name && this.isCollapsed == isCollapsed
    }
}