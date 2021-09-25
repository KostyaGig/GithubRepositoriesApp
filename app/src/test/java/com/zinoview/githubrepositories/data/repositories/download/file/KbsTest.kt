package com.zinoview.githubrepositories.data.repositories.download.file

import com.zinoview.githubrepositories.ui.core.cache.list.ContainsItem
import org.junit.Assert.*
import org.junit.Test

/**
 * Test for [Kbs.Test]
 **/

class KbsTest {

    @Test
    fun success_transform_bytes_to_kbs() {

        val bytes = 102903
        val expected = 101
        val actual = Kbs.Test().toKb(bytes)
        assertEquals(expected, actual)
    }
}