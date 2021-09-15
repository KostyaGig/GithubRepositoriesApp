package com.zinoview.githubrepositories.data.repositories.download.file

import com.zinoview.githubrepositories.core.Save
import okhttp3.ResponseBody


/**
 * @author Zinoview on 06.09.2021
 * k.gig@list.ru
 */
interface File : Save<ResponseBody> {

    fun create(owner: String, repo: String)

    fun isNotExist(owner: String, repo: String) : Boolean
    class ZipFile(
        private val folder: Folder
    ) : File {

        override fun create(owner: String, repo: String)
            = folder.createFile(owner, repo)

        override fun saveData(data: ResponseBody)
            = folder.saveData(data)

        override fun isNotExist(owner: String,repo: String): Boolean
            = folder.fileIsNotExist(owner,repo)
    }


}