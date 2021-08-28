package com.zinoview.githubrepositories.ui.users



/**
 * @author Zinoview on 20.08.2021
 * k.gig@list.ru
 */
interface GithubUserRequest<T> {

    fun data(param: T)
}