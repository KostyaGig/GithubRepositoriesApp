package com.zinoview.githubrepositories.ui.repositories.download

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.zinoview.githubrepositories.core.DisposableStore
import com.zinoview.githubrepositories.data.repositories.download.file.CachedFile
import com.zinoview.githubrepositories.data.repositories.download.file.WriteFileResult
import com.zinoview.githubrepositories.ui.core.CleanDisposable
import com.zinoview.githubrepositories.ui.core.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import java.lang.IllegalStateException

interface WriteFileViewModel : ViewModel<WriteFileResult>,CleanDisposable {

    fun writeData(data: ResponseBody)

    class Base(
        private val writeFileCommunication: WriteFileCommunication,
        private val cachedFile: CachedFile,
        private val disposableStore: DisposableStore
    ) : WriteFileViewModel, androidx.lifecycle.ViewModel() {

        override fun observe(owner: LifecycleOwner, observer: Observer<List<WriteFileResult>>)
            = writeFileCommunication.observe(owner,observer)

        override fun saveData(data: List<WriteFileResult>) = throw IllegalStateException("WriteViewModel saveData() not use it method")

        override fun writeData(data: ResponseBody) {
            val writeFileResult = cachedFile.write(data)
            writeFileResult
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    writeFileCommunication.changeValue(result.wrap())
                },{},{}).addToDisposableStore(disposableStore)
        }

        override fun Disposable.addToDisposableStore(store: DisposableStore)
            = store.add(this)
    }
}
