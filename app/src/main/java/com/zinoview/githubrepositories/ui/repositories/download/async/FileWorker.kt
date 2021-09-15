package com.zinoview.githubrepositories.ui.repositories.download.async

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.zinoview.githubrepositories.data.repositories.download.file.CachedFile
import com.zinoview.githubrepositories.ui.core.message

import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody


/**
 * @author Zinoview on 09.09.2021
 * k.gig@list.ru
 */
class FileWorker(
    context: Context,
    workerParameters: WorkerParameters,
    private val cachedFile: CachedFile
) : Worker(context,workerParameters) {

    override fun doWork(): Result {
        //todo move to const "data" and rewrite
        val data = inputData.getByteArray("data")?.toResponseBody()
        message("WORKING, DATA ${data?.byteString()?.size}")
//        cachedFile.writeToFile(data)
        return Result.success()
    }

}