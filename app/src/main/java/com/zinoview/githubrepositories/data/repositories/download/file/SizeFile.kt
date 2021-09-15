package com.zinoview.githubrepositories.data.repositories.download.file

import com.zinoview.githubrepositories.ui.core.message
import com.zinoview.githubrepositories.ui.core.message2


/**
 * @author Zinoview on 07.09.2021
 * k.gig@list.ru
 */
interface SizeFile {

    fun isBigSizeFile(size: Long) : Boolean

    class Base : SizeFile {

        override fun isBigSizeFile(size: Long): Boolean {
            val result = size > BIG_FILE_SIZE
            message2("size file compare: $size result $result")
            return result
        }

        /**
         * BIG_SIZE_FILE > 10Mb
         */
        private companion object {
            const val BIG_FILE_SIZE = 10000000L
        }
    }
}