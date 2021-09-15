package com.zinoview.githubrepositories.data.repositories.download.file

import okhttp3.ResponseBody
import java.io.File


/**
 * @author Zinoview on 11.09.2021
 * k.gig@list.ru
 */
interface CachedFile {

    fun writeToFile(data: ResponseBody)

    fun cacheFile(file: File)

    class Base(
        private val fileWriter: FileWriter<ResponseBody>
    ) : CachedFile {

        private var cacheFile: File = File(MOCK_PATH)

        override fun cacheFile(file: File) {
            cacheFile = file
        }

        override fun writeToFile(data: ResponseBody)
            = fileWriter.write(cacheFile.absolutePath,data)
    }

    private companion object {
        const val MOCK_PATH = "Not/Exist/Path"
    }
}