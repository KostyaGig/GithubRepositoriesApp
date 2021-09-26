package com.zinoview.githubrepositories.data.repositories.download.file

import org.junit.Assert.*
import org.junit.Test

/**
 * Test for [Kbs.Test]
 **/

class KbsTest {

    @Test
    fun success_transform_bytes_to_kbs() {

        val bytes = 102903
        val expected = 100
        val actual = Kbs.Test().toKb(bytes)
        assertEquals(expected, actual)
    }
}