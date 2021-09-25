package com.zinoview.githubrepositories.data.repositories.download

import com.zinoview.githubrepositories.data.repositories.download.file.SizeFile
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.net.UnknownHostException

/**
 * Test for [DownloadRepoRepository.Test]
 **/

class DownloadRepoRepositoryTest {

    private var repository: DownloadRepoRepository.Test? = null

    @Before
    fun setUp() {
        repository = DownloadRepoRepository.Test()
    }

    @Test
    fun test_download_repository() {


        var expected = true
        var actual = repository?.download("owner","repo")

        assertEquals(expected, actual is TestDataDownloadFile.Exist)

        expected = true
        actual = repository?.download("owner","repo")

        assertEquals(expected,actual is TestDataDownloadFile.Failure)

        expected = true
        actual = repository?.download("owner","repo")
        assertEquals(expected,actual is TestDataDownloadFile.WaitingToDownload)

        expected = true
        actual = repository?.download("owner","repo")
        assertEquals(expected,actual is TestDataDownloadFile.Big)
    }

    @Test
    fun test_failure_download_repository() {

        repository?.setException(UnknownHostException())
        repository?.setCount(2)

        var expected = true
        val actual = repository?.download("owner","repo")

        assertEquals(expected,actual is TestDataDownloadFile.Failure)

        expected = true
        val actualException = actual?.exception()
        assertEquals(expected, actualException is UnknownHostException)
    }

    @After
    fun clear() {
        repository = null
    }
}