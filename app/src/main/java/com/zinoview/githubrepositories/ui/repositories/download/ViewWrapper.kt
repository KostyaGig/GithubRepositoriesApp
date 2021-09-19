package com.zinoview.githubrepositories.ui.repositories.download

interface ViewWrapper {

    fun showSnackBar(text: String)

    fun showDialog()

    fun hideDialog()

    fun showActualProgress(progress: Int)

    class Base(
        private val snackBarWrapper: SnackBarWrapper,
        private val dialogWrapper: DialogWrapper
    ) : ViewWrapper {

        override fun showSnackBar(text: String)
            = snackBarWrapper.show(text)

        override fun showDialog()
            = dialogWrapper.show()

        override fun hideDialog()
            = dialogWrapper.hide()

        override fun showActualProgress(progress: Int)
            = dialogWrapper.showActualProgress(progress)
    }
}