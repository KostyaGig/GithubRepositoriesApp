package com.zinoview.githubrepositories.data.repositories.download.file

import com.zinoview.githubrepositories.R
import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.core.Resource
import com.zinoview.githubrepositories.data.repositories.download.file.exception.WritingFileException

interface WritingFIleExceptionMapper : Abstract.FactoryMapper<Exception,String> {

    class Base (
        private val resource: Resource
    ) : WritingFIleExceptionMapper {

        override fun map(src: Exception): String
                = when(src) {
            is WritingFileException -> resource.string(R.string.waiting_while_previous_fle_will_be_saved)
            else -> resource.string(R.string.generic_error)
        }
    }
}
