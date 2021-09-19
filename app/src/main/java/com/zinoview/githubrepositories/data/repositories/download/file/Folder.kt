package com.zinoview.githubrepositories.data.repositories.download.file

import android.os.Environment
import com.zinoview.githubrepositories.core.Save
import com.zinoview.githubrepositories.ui.core.message
import okhttp3.ResponseBody
import java.io.File


/**
 * @author Zinoview on 06.09.2021
 * k.gig@list.ru
 */
interface Folder : Save<ResponseBody> {

    fun create()

    fun createFile(owner: String, repo: String)

    fun fileIsNotExist(owner: String,repo: String) : Boolean

    class Base(
        private val fileWriter: FileWriter<ResponseBody>,
        private val cachedFile: CachedFile,
        private val checkFileByExist: CheckFileByExist
    ) : Folder {

        private val baseFolder by lazy {
            File(Environment.getExternalStorageDirectory(), BASE_PATH)
        }

        private var zipFile = File(MOCK_PATH)

        override fun create() {
            if (baseFolder.exists().not()) {
                baseFolder.mkdirs()
            }
        }

        override fun createFile(owner: String, repo: String) {
            create()
            if (fileIsNotExist(owner, repo)) {
                zipFile = File( "$baseFolder/$owner _ $repo$ZIP_TYPE_FILE")
                zipFile.createNewFile()
                cachedFile.cacheFile(zipFile)
            }
        }

        override fun saveData(data: ResponseBody)
            = fileWriter.write(zipFile.absolutePath,data)

        override fun fileIsNotExist(owner: String,repo: String): Boolean {
            val path = "$baseFolder/$owner _ $repo$ZIP_TYPE_FILE"
            return checkFileByExist.isNotExist(path)
        }

        private companion object {
            const val BASE_PATH = "GithubRepositories/Downloads"
            const val ZIP_TYPE_FILE = ".zip"
            const val MOCK_PATH = "Not/Exist/Path"
        }
    }

}