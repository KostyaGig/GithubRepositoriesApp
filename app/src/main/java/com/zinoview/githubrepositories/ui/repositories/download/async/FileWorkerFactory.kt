package com.zinoview.githubrepositories.ui.repositories.download.async

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.zinoview.githubrepositories.data.repositories.download.file.CachedFile


/**
 * @author Zinoview on 11.09.2021
 * k.gig@list.ru
 */
class FileWorkerFactory(
    private val cachedFile: CachedFile
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker
        = FileWorker(appContext,workerParameters,cachedFile)
}