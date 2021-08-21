package com.zinoview.githubrepositories.ui.users.cache

import com.zinoview.githubrepositories.ui.users.UiGithubUserState
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
        val totalCache = GithubUserTotalCache.Test(storeCache)

        val cache = ArrayList<String>()
        cache.add("Element1")
        cache.add("Element2")

        totalCache.add(cache)

        var expected = listOf<String>("Element1","Element2")
        var actual = totalCache.actual()

        assertEquals(expected, actual)


        val cache2 = ArrayList<String>()
        cache2.add("Frikina")
        cache2.add("Bazz")

        totalCache.add(cache2)

        expected = listOf<String>("Element1","Element2","Frikina","Bazz")
        actual = totalCache.actual()

        assertEquals(expected, actual)
    }
}