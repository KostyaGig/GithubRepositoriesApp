package com.zinoview.githubrepositories.data.core


/**
 * @author Zinoview on 23.08.2021
 * k.gig@list.ru
 */
interface Text {

    fun subString(text: String) : String

    class GithubName : Text {
        override fun subString(text: String) = if (text.length > 15) {
            "${text.substring(0,15)}.."
        } else {
            text
        }
    }
}