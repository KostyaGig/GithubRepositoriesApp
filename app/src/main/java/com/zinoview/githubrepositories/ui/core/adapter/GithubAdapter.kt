package com.zinoview.githubrepositories.ui.core.adapter

import com.zinoview.githubrepositories.ui.core.CommunicationModel


/**
 * @author Zinoview on 24.08.2021
 * k.gig@list.ru
 */
interface GithubAdapter<T : CommunicationModel> {

    fun update(list: List<T>)

    fun update(item: T,position: Int)
}



interface GithubOnItemClickListener {

    fun onItemClick(githubUserName: String)
}