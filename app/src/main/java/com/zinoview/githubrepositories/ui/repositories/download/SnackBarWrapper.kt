package com.zinoview.githubrepositories.ui.repositories.download

import android.view.View
import com.google.android.material.snackbar.Snackbar

interface SnackBarWrapper {

    fun show(text: String)

    fun showWithAction(text: String,action: String,bigFileState: UiGithubDownloadFileState.Big)

    class Base(
        private val view: View,
        private val action : (UiGithubDownloadFileState.Big) -> Unit = {}
    ) : SnackBarWrapper {

        override fun show(text: String)
            = Snackbar.make(view,text,Snackbar.LENGTH_SHORT).show()

        override fun showWithAction(text: String, action: String,bigFileState: UiGithubDownloadFileState.Big) {
            Snackbar.make(view,text,Snackbar.LENGTH_INDEFINITE).apply {
                duration = DURATION.toInt()
                setAction(action) {
                    action(bigFileState)
                }.show()
            }
        }

        private companion object {
            const val DURATION = 8000
        }
    }
}
