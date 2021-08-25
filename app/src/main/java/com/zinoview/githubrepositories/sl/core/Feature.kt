package com.zinoview.githubrepositories.sl.core


/**
 * @author Zinoview on 25.08.2021
 * k.gig@list.ru
 */
sealed class Feature {

    object Repository : Feature()

    object User : Feature()
}
