package com.zinoview.githubrepositories.data.repositories.cache

import com.zinoview.githubrepositories.core.Save


/**
 * @author Zinoview on 24.08.2021
 * k.gig@list.ru
 */
interface SaveList<T> : Save<T> {

    fun saveListData(listData: List<T>)
}