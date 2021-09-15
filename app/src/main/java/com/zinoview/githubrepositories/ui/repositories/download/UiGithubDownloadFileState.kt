package com.zinoview.githubrepositories.ui.repositories.download

import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.ui.core.CommunicationModel
import com.zinoview.githubrepositories.ui.core.ListWrapper
import com.zinoview.githubrepositories.ui.core.message
import com.zinoview.githubrepositories.ui.repositories.download.async.FileWorker
import okhttp3.ResponseBody
import java.lang.IllegalStateException

/**
 * @author Zinoview on 05.09.2021
 * k.gig@list.ru
 */

sealed class UiGithubDownloadFileState :
    CommunicationModel,
    ListWrapper<UiGithubDownloadFileState>,
    Abstract.FactoryMapper<WorkManager,Unit>
{

    override fun isBase(): Boolean = throw IllegalStateException("UiGithubDownloadRepositoryState not use isBase()")
    override fun isCollapsed(): Boolean = throw IllegalStateException("UiGithubDownloadRepositoryState not use isCollapsed()")
    override fun matches(model: CommunicationModel): Boolean
        = throw IllegalStateException("UiGithubDownloadRepositoryState not use matches()")

    override fun map(src: WorkManager) = Unit

    object Progress : UiGithubDownloadFileState() {

        override fun wrap(): List<UiGithubDownloadFileState>
            = listOf(this)

    }

    object Exist : UiGithubDownloadFileState() {

        override fun wrap(): List<UiGithubDownloadFileState>
                = listOf(this)
    }

    class Big(
        private val size: Long,
        private val message: String
    ) : UiGithubDownloadFileState() {

        override fun wrap(): List<UiGithubDownloadFileState>
            = listOf(this)

    }

    class WaitingToDownload(
        private val data: ResponseBody,
        private val message: String
    ) : UiGithubDownloadFileState() {

        //todo rewrite
        override fun map(src: WorkManager) {
            message("waiting to download  -> map()")
            val workRequest = OneTimeWorkRequest.Builder(FileWorker::class.java)
                .setInputData(
                    Data.Builder()
                        .putByteArray("data",data.bytes())
                        .build()
                )
                .build()

                src.enqueue(workRequest)
            }
        }

        override fun wrap(): List<UiGithubDownloadFileState>
            = listOf(this)
    class Failure(private val message: String) : UiGithubDownloadFileState() {

        override fun wrap(): List<UiGithubDownloadFileState>
                = listOf(this)

    }
}

