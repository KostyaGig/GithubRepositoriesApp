package com.zinoview.githubrepositories.data.repositories.download.file

interface ProgressSavedData {

    fun updateProgress(readKb: Int)

    fun onProgressUpdate(onProgressUpdate: (Int) -> Unit)

    class Base(
        private val fileSize: Int,
        private val percentageOfNumber: PercentageOfNumber
    ) : ProgressSavedData {

        private var savedKBs = 0

        override fun updateProgress(readKb: Int) {
            savedKBs += readKb
        }

        override fun onProgressUpdate(onProgressUpdate: (Int) -> Unit)
                = onProgressUpdate.invoke(progressInPercentage())

        private fun progressInPercentage(): Int
                = percentageOfNumber.take(fileSize,savedKBs)
    }
}