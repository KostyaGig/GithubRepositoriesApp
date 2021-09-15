package com.zinoview.githubrepositories.data.repositories.download.file

import com.zinoview.githubrepositories.ui.core.message
import okhttp3.ResponseBody
import java.io.FileOutputStream
import java.io.InputStream


/**
 * @author Zinoview on 06.09.2021
 * k.gig@list.ru
 */
interface FileWriter<T> {

    fun write(path: String,data: T)

    class Base : FileWriter<ResponseBody> {


        override fun write(path: String,data: ResponseBody) {
            message("Write data size ${data.contentLength()}")
            val inputStream: InputStream?
            inputStream = data.byteStream()
            val fos = FileOutputStream(path)
            fos.use { output ->
                val buffer = ByteArray(4 * 1024)
                var read: Int
                while (inputStream.read(buffer).also { read = it } != -1) {
                    output.write(buffer, 0, read)
                }
                output.flush()
            }
            //todo close inputSTREAN
        }

    }
}