package com.zinoview.githubrepositories.ui.core

import com.zinoview.githubrepositories.core.Save


/**
 * @author Zinoview on 28.08.2021
 * k.gig@list.ru
 */
interface ViewModel<T : CommunicationModel> : Observe<T>, Save<List<T>> {
}