package com.zinoview.githubrepositories.data.repositories.download.file

import com.zinoview.githubrepositories.ui.core.cache.list.ContainsItem
import org.junit.Assert.*
import org.junit.Test

/**
 * Test for [SizeFile.Test]
 **/

class SizeFileTest {

    @Test
    fun big_size_file_check() {
        val bigFileSize = 134
        val expected = true
        val actual = SizeFile.Test().isBigSizeFile(bigFileSize)
        assertEquals(expected, actual)
    }

    @Test
    fun small_size_file_check() {
        val smallFileSize = 47
        val expected = false
        val actual = SizeFile.Test().isBigSizeFile(smallFileSize)
        assertEquals(expected, actual)
    }

}