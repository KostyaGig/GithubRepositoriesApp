package com.zinoview.githubrepositories.ui.core

import com.zinoview.githubrepositories.ui.repositories.UiGithubRepositoryState


/**
 * @author Zinoview on 24.08.2021
 * k.gig@list.ru
 */
interface GithubAdapter<T : CommunicationModel> {

    fun update(list: List<T>)
}