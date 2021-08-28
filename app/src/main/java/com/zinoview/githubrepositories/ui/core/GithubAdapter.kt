package com.zinoview.githubrepositories.ui.core


/**
 * @author Zinoview on 24.08.2021
 * k.gig@list.ru
 */
interface GithubAdapter<T : CommunicationModel> {

    fun update(list: List<T>)

    fun update(item: T,position: Int)
}

interface CollapseOrExpandListener<T : CommunicationModel> {

    fun onChangeCollapseState(item: T,position: Int)
}

interface GithubOnItemClickListener {

    fun onItemClick(githubUserName: String)
}