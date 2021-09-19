package com.zinoview.githubrepositories.ui.repositories.download

import com.zinoview.githubrepositories.core.Save

import com.zinoview.githubrepositories.ui.core.CommunicationModel
import com.zinoview.githubrepositories.ui.core.ListWrapper
import okhttp3.ResponseBody

/**
 * @author Zinoview on 05.09.2021
 * k.gig@list.ru
 */

sealed class UiGithubDownloadFileState :
    CommunicationModel,
    ListWrapper<UiGithubDownloadFileState> {

    open fun handleState(snackBarWrapper: SnackBarWrapper,save: Save<ResponseBody>) = Unit

    object Progress : UiGithubDownloadFileState() {

        override fun wrap(): List<UiGithubDownloadFileState> = listOf(this)

        override fun handleState(snackBarWrapper: SnackBarWrapper,save: Save<ResponseBody>)
            = snackBarWrapper.show("Loading...")
    }

    object Exist : UiGithubDownloadFileState() {

        override fun wrap(): List<UiGithubDownloadFileState> = listOf(this)

        override fun handleState(snackBarWrapper: SnackBarWrapper,save: Save<ResponseBody>)
            = snackBarWrapper.show("This file already exist")
    }

    class Big(
        private val size: Int,
        private val message: String,
        private val data: ResponseBody
    ) : UiGithubDownloadFileState(), Save<Save<ResponseBody>> {

        override fun handleState(snackBarWrapper: SnackBarWrapper,save: Save<ResponseBody>)
            = snackBarWrapper.showWithAction(message+size + "kbs","Download",this)

        override fun saveData(save: Save<ResponseBody>)
            = save.saveData(data)

        override fun wrap(): List<UiGithubDownloadFileState> = listOf(this)
    }

    class WaitingToDownload(
        private val data: ResponseBody,
    ) : UiGithubDownloadFileState() {

        override fun handleState(snackBarWrapper: SnackBarWrapper,save: Save<ResponseBody>)
            = save.saveData(data)

        override fun wrap(): List<UiGithubDownloadFileState> = listOf(this)
    }

    class Failure(private val message: String) : UiGithubDownloadFileState() {

        override fun wrap(): List<UiGithubDownloadFileState> = listOf(this)

        override fun handleState(snackBarWrapper: SnackBarWrapper, save: Save<ResponseBody>)
            = snackBarWrapper.show("Error: $message")
    }

}

