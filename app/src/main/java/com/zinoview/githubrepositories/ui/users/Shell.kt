package com.zinoview.githubrepositories.ui.users

import com.zinoview.githubrepositories.core.Abstract


/**
 * @author Zinoview on 21.08.2021
 * k.gig@list.ru
 * This class work which common wrapper over(над) object
 * From english shell - оболочка
 */

interface Shell<T> {

    fun wrap() : List<T>
}