package com.zinoview.githubrepositories.data.repositories.download.file

interface WritingFileHandler {

    fun isWriting() : Boolean

    fun changeState(isWriting: Boolean)

    class Base : WritingFileHandler {

        private var isWritingFile = false

        override fun isWriting(): Boolean
            = isWritingFile

        override fun changeState(isWriting: Boolean) {
            isWritingFile = isWriting
        }
    }
}