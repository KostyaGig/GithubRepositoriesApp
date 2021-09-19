package com.zinoview.githubrepositories.data.repositories.download.file

import com.zinoview.githubrepositories.R
import com.zinoview.githubrepositories.core.Resource
import io.reactivex.Observable
import okhttp3.ResponseBody
import java.lang.Exception

interface AsyncFileWriter {

    fun writeToFile(filePath: String, data: ResponseBody) : Observable<WriteFileResult>

    class Base(
        private val fileWriter: FileWriter<ResponseBody>,
        private val exceptionMapper: WritingFIleExceptionMapper,
        private val resource: Resource
    ) : AsyncFileWriter {

        override fun writeToFile(filePath: String,data: ResponseBody): Observable<WriteFileResult> {
            return Observable.create { subscriber ->
                try {
                    fileWriter.addSavedFileProgress { progress ->
                        subscriber.onNext(WriteFileResult.Progress(progress))
                    }
                    fileWriter.write(filePath,data)
                    subscriber.onNext(WriteFileResult.Success(
                        resource.string(R.string.saved_file_text)
                    ))
                } catch (e: Exception) {
                    val errorMessage = exceptionMapper.map(e)
                    subscriber.onNext(WriteFileResult.Failure(errorMessage))
                }
            }
        }

    }
}