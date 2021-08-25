package com.zinoview.githubrepositories.ui.users.cache

import com.zinoview.githubrepositories.ui.core.*
import org.junit.Assert.*
import org.junit.Test

/**
 * @author Zinoview on 20.08.2021
 * k.gig@list.ru
 */
class GithubUserTotalCacheTest {

    @Test
    fun test_success_save_total_cache() {
        val storeCache = ArrayList<String>()
        val totalCache = TestTotalCache(storeCache)

        val cache = ArrayList<TestCommunicationModel>()
        cache.add(TestCommunicationModel("Element1"))
        cache.add(TestCommunicationModel("Element2"))

        totalCache.add(cache)

        var expected = listOf<String>("Element1","Element2")
        var actual = totalCache.actual()

        assertEquals(expected, actual)

        val cache2 = ArrayList<TestCommunicationModel>()
        cache2.add(TestCommunicationModel("Frikina"))
        cache2.add(TestCommunicationModel("Bazz"))

        totalCache.add(cache2)

        expected = listOf<String>("Element1","Element2","Frikina","Bazz")
        actual = totalCache.actual()

        assertEquals(expected, actual)
    }

    private data class TestCommunicationModel(
        private val data: String
    ) : CommunicationModel {

        override fun isBase(): Boolean
            = throw IllegalStateException("TestCommunicationModel not use this method")

        fun data() = data
    }

    private inner class TestTotalCache(private val stringList: MutableList<String>) : UiTotalCache<TestCommunicationModel> {

        private val list = ArrayList<TestCommunicationModel>()

        override fun add(items: List<TestCommunicationModel>) {
            list.addAll(items)

            val stringData = items.map { it.data() }
            stringList.addAll(stringData)
        }

        fun actual() = stringList

        override fun updateAdapter()
            = throw IllegalStateException("Not use for test implements")

        override fun addAdapter(adapter: GithubAdapter<TestCommunicationModel>)
            = throw IllegalStateException("Not use for test implements")
    }
}