package com.zinoview.githubrepositories.data.repositories.download.file

import io.reactivex.Observable
import okhttp3.ResponseBody
import java.io.File


/**
 * @author Zinoview on 11.09.2021
 * k.gig@list.ru
 */
interface CachedFile {

    fun cacheFile(file: File)

    fun write(data: ResponseBody) : Observable<WriteFileResult>

    class Base(
        private val asyncFileWriter: AsyncFileWriter
    ) : CachedFile {

        private var cacheFile: File = File(MOCK_PATH)

        override fun cacheFile(file: File) {
            cacheFile = file
        }

        override fun write(data: ResponseBody): Observable<WriteFileResult>
            = asyncFileWriter.writeToFile(cacheFile.absolutePath,data)
    }

    private companion object {
        const val MOCK_PATH = "Not/Exist/Path"
    }
}