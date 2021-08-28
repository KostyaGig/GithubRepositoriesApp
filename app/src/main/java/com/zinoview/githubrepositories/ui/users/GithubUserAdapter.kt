package com.zinoview.githubrepositories.ui.users

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zinoview.githubrepositories.R
import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.ui.core.*
import com.zinoview.githubrepositories.ui.users.view.GithubUserBioTextView
import com.zinoview.githubrepositories.ui.users.view.GithubUserNameTextView
import com.zinoview.githubrepositories.ui.users.view.GithubUserProfileImageView


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
class GithubUserAdapter(
    private val githubUserItemViewTypeFactory: Abstract.FactoryMapper<UiGithubUserState,Int>,
    private val githubUserViewHolderFactory: Abstract.FactoryMapper<Pair<Int,ViewGroup>,GithubUserViewHolder>,
    ) : RecyclerView.Adapter<GithubUserAdapter.GithubUserViewHolder>(),
        GithubAdapter<UiGithubUserState> {

    private val list = ArrayList<UiGithubUserState>()

    override fun update(list: List<UiGithubUserState>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun update(item: UiGithubUserState,position: Int) {
        this.list[position] = item
        notifyItemChanged(position)
    }

    override fun getItemViewType(position: Int): Int =
        githubUserItemViewTypeFactory.map(list[position])

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GithubUserViewHolder = githubUserViewHolderFactory.map(Pair(viewType, parent))

    override fun onBindViewHolder(holder: GithubUserViewHolder, position: Int)
        =  holder.bind(list[position])

    override fun getItemCount(): Int = list.size

    abstract class GithubUserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        open fun bind(state: UiGithubUserState) {}

        class Progress(itemView: View) : GithubUserViewHolder(itemView)

        class Base(
            itemView: View,
            private val onItemClickListener: GithubOnItemClickListener,
            private val collapseOrExpandListener: CollapseOrExpandListener<UiGithubUserState>
        ) : GithubUserViewHolder(itemView) {

            private val nameTextView = itemView.findViewById<GithubUserNameTextView>(R.id.name)
            private val bioTextView = itemView.findViewById<GithubUserBioTextView>(R.id.bio)
            private val profileImage = itemView.findViewById<GithubUserProfileImageView>(R.id.image)

            private val collapseImage = itemView.findViewById<GithubCollapseImage>(R.id.collapse_image)
            private val subItem = itemView.findViewById<GithubLinearLayout>(R.id.sub_item)

            override fun bind(state: UiGithubUserState) {
                state.map(listOf(
                    nameTextView,
                    bioTextView,
                    profileImage
                ))

                state.mapCollapseOrExpandState(
                    listOf(
                        collapseImage,
                        subItem
                    )
                )

                itemView.setOnClickListener {
                    state.notifyAboutItemClick(onItemClickListener)
                }

                collapseImage.setOnClickListener {
                    state.notifyAboutCollapseOrExpand(collapseOrExpandListener,adapterPosition)
                }

            }
        }

        class Empty(itemView: View) : GithubUserViewHolder(itemView) {
            private val emptyDataTextView = itemView.findViewById<GithubUserNameTextView>(R.id.empty_data)

            override fun bind(state: UiGithubUserState) {
                state.map(listOf(emptyDataTextView))
            }
        }

        class Failure(itemView: View) : GithubUserViewHolder(itemView) {
            private val errorTextView = itemView.findViewById<GithubErrorTextView>(R.id.error)

            override fun bind(state: UiGithubUserState) = state.map(listOf(errorTextView))
        }
    }
}


