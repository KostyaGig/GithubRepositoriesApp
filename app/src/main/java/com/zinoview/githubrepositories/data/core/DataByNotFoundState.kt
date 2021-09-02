package com.zinoview.githubrepositories.data.core

import io.reactivex.Single


/**
 * @author Zinoview on 30.08.2021
 * k.gig@list.ru
 */
interface DataByNotFoundState<T> {

    fun dataByNotFoundState() : Single<List<T>>
}