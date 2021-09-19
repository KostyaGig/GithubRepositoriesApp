package com.zinoview.githubrepositories.data.repositories.download.file

import com.zinoview.githubrepositories.data.repositories.download.file.exception.WritingFileException
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.FileOutputStream
import java.io.InputStream


/**
 * @author Zinoview on 06.09.2021
 * k.gig@list.ru
 */

interface FileWriter<T> {

    fun write(path: String,data: T)

    fun addSavedFileProgress(savedFileProgress: (Int) -> Unit = {})

    class Base(
        private val kbs: Kbs,
        private val percentageOfNumber: PercentageOfNumber
    ) : FileWriter<ResponseBody> {

        private val writingFileHandler =  WritingFileHandler.Base()
        private var onProgressUpdate: (Int) -> Unit = {}

        override fun write(path: String,data: ResponseBody) {
            if (writingFileHandler.isWriting()) {
                throw WritingFileException()
            } else {
                writeToFile(path,data)
            }
        }

        private fun writeToFile(path: String,data: ResponseBody) {
            val inputStream: InputStream?
            val dataByteString = data.byteString()

            val sizeFile = dataByteString.size
            val sizeFilInKbs = kbs.toKb(sizeFile)
            val progressSaveData = ProgressSavedData.Base(sizeFilInKbs,percentageOfNumber)

            inputStream = dataByteString.toResponseBody().byteStream()
            val fos = FileOutputStream(path)
            fos.use { output ->
                val buffer = ByteArray(2 * 1024)
                var read: Int
                while (inputStream.read(buffer).also { read = it } != -1) {
                    writingFileHandler.changeState(true)
                    progressSaveData.updateProgress(kbs.toKb(read))
                    Thread.sleep(130)
                    progressSaveData.onProgressUpdate(onProgressUpdate)
                    output.write(buffer, 0, read)
                }
                writingFileHandler.changeState(false)
                inputStream.close()
                output.flush()
            }
        }

        override fun addSavedFileProgress(savedFileProgress: (Int) -> Unit) {
            this.onProgressUpdate = savedFileProgress
        }

    }
}