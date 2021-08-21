package com.zinoview.githubrepositories.data.users


/**
 * @author Zinoview on 20.08.2021
 * k.gig@list.ru
 */
interface GithubDataSource<T> {

    fun fetchData(param: String) : T
}