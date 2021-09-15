package com.zinoview.githubrepositories.ui.core


/**
 * @author Zinoview on 23.08.2021
 * k.gig@list.ru
 */

//todo создать отдельно CommunicationModel и отдельно,к примеру, CommunicationModelState(название продумать) в нем будут эти 2 нижних метода а значит в UiGithubDownloadFileState пропадут ненужгные методы
interface CommunicationModel : CommunicationMatcher<CommunicationModel> {

    fun isBase() : Boolean

    fun isCollapsed() : Boolean
}

