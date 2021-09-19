package com.zinoview.githubrepositories.ui.repositories.download

import android.app.Dialog
import android.widget.ProgressBar
import android.widget.TextView
import com.zinoview.githubrepositories.R

interface DialogWrapper {

    fun show()

    fun hide()

    fun showActualProgress(progress: Int)

    class Base(
        private val dialog: Dialog
    ) : DialogWrapper {

        private var progressBar: ProgressBar? = null
        private var progressTextView: TextView? = null

        override fun showActualProgress(progress: Int) {
            progressBar?.progress = progress
            progressTextView?.text = "$progress%"
        }

        override fun show() {
            if (progressBar != null && progressTextView != null)
                showDialog()
            else {
                progressBar = dialog.findViewById(R.id.progress_bar)
                progressTextView = dialog.findViewById(R.id.progress_tv)
                showDialog()
            }
        }

        override fun hide() {
            progressTextView = null
            progressBar = null
            dialog.dismiss()
        }

        private fun showDialog() = dialog.show()

    }
}