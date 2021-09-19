package com.zinoview.githubrepositories.data.repositories.download.file

import com.zinoview.githubrepositories.ui.core.CommunicationModel
import com.zinoview.githubrepositories.ui.core.ListWrapper
import com.zinoview.githubrepositories.ui.repositories.download.ViewWrapper

sealed class WriteFileResult : CommunicationModel,ListWrapper<WriteFileResult> {

    open fun handleResult(viewWrapper: ViewWrapper) = Unit

    class Progress(
        private val progress: Int
    ) : WriteFileResult() {

        override fun handleResult(viewWrapper: ViewWrapper) =
            viewWrapper.showActualProgress(progress)

        override fun wrap(): List<WriteFileResult>
            = listOf(this)
    }

    class Success(
        private val message: String
    ) : WriteFileResult() {

        override fun handleResult(viewWrapper: ViewWrapper) = viewWrapper.run {
            hideDialog()
            showSnackBar(message)
        }

        override fun wrap(): List<WriteFileResult>
            = listOf(this)
    }

    class Failure(
        private val message: String
    ) : WriteFileResult() {

        override fun handleResult(viewWrapper: ViewWrapper) = viewWrapper.run {
            hideDialog()
            showSnackBar(message)
        }

        override fun wrap(): List<WriteFileResult>
            = listOf(this)
    }
}


