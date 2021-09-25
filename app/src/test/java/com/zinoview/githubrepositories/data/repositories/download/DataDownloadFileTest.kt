package com.zinoview.githubrepositories.data.repositories.download

import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.core.Resource
import com.zinoview.githubrepositories.domain.core.DomainDownloadExceptionMapper
import com.zinoview.githubrepositories.domain.repositories.download.DomainDownloadRepositoryMapper
import com.zinoview.githubrepositories.ui.core.cache.list.ContainsItem
import com.zinoview.githubrepositories.ui.repositories.download.UiDownloadExceptionMapper
import com.zinoview.githubrepositories.ui.repositories.download.UiDownloadFile
import com.zinoview.githubrepositories.ui.repositories.download.UiDownloadRepositoryMapper
import com.zinoview.githubrepositories.ui.repositories.download.UiGithubDownloadFileState
import okhttp3.ResponseBody
import org.junit.Assert.*
import org.junit.Test
import java.lang.IllegalStateException
import java.net.UnknownHostException

/**
 * Test for [TestDataDownloadFile]
 **/

class DataDownloadFileTest {

    @Test
    fun success_map_model() {
        val dataModel = DataDownloadFile.Failure(UnknownHostException("No connection"))
        val domainModel = dataModel.map(
            DomainDownloadRepositoryMapper(
                DomainDownloadExceptionMapper.Base()
            )
        )

        val resourceTest = Resource.Test()
        val uiModel = domainModel.map(UiDownloadRepositoryMapper(
            resourceTest,
            UiDownloadExceptionMapper.Base(resourceTest)
        ))

        val uiStateModel = uiModel.mapTo(Unit)

        val expected = true
        val actual = uiStateModel is UiGithubDownloadFileState.Failure

        assertEquals(expected, actual)

        val expectedErrorMessage = "No connection"
        val actualErrorMessage = uiStateModel.errorMessage()

        assertEquals(expectedErrorMessage,actualErrorMessage)
    }
}