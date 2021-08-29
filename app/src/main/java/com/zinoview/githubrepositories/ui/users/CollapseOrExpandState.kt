package com.zinoview.githubrepositories.ui.users


sealed class CollapseOrExpandState {

    object Collapsed : CollapseOrExpandState()
    object Expanded : CollapseOrExpandState()
    object Any : CollapseOrExpandState()

    object Empty : CollapseOrExpandState()
}
