package com.zinoview.githubrepositories.ui.repositories

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zinoview.githubrepositories.R
import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.ui.core.*
import com.zinoview.githubrepositories.ui.repositories.view.GithubCircleImageView
import com.zinoview.githubrepositories.ui.users.UiGithubUserState
import com.zinoview.githubrepositories.ui.users.view.GithubUserBioTextView
import com.zinoview.githubrepositories.ui.users.view.GithubUserNameTextView
import com.zinoview.githubrepositories.ui.users.view.GithubUserProfileImageView


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
class GithubRepositoryAdapter(
    private val githubRepositoryItemViewTypeFactory: Abstract.FactoryMapper<UiGithubRepositoryState,Int>,
    private val githubRepositoryViewHolderFactory: Abstract.FactoryMapper<Pair<Int,ViewGroup>,GithubRepositoryViewHolder>
) : RecyclerView.Adapter<GithubRepositoryAdapter.GithubRepositoryViewHolder>(),
    GithubAdapter<UiGithubRepositoryState> {

    private val list = ArrayList<UiGithubRepositoryState>()

    override fun update(list: List<UiGithubRepositoryState>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun update(item: UiGithubRepositoryState, position: Int) {
        this.list[position] = item
        notifyItemChanged(position)
    }

    override fun getItemViewType(position: Int): Int =
        githubRepositoryItemViewTypeFactory.map(list[position])

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GithubRepositoryViewHolder = githubRepositoryViewHolderFactory.map(Pair(viewType, parent))

    override fun onBindViewHolder(holder: GithubRepositoryViewHolder, position: Int) =
        holder.bind(list[position])

    override fun getItemCount(): Int = list.size

    abstract class GithubRepositoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        open fun bind(state: UiGithubRepositoryState) {}

        class Progress(itemView: View) : GithubRepositoryViewHolder(itemView)

        class Base(
            itemView: View,
            private val collapseOrExpandListener: CollapseOrExpandListener<UiGithubRepositoryState>
        ) : GithubRepositoryViewHolder(itemView) {

            private val nameTextView = itemView.findViewById<GithubUserNameTextView>(R.id.name)
            private val languageTextView = itemView.findViewById<GithubUserBioTextView>(R.id.language)
            private val colorImage = itemView.findViewById<GithubCircleImageView>(R.id.color_image)

            private val collapseImage = itemView.findViewById<GithubCollapseImage>(R.id.collapse_image)
            private val privateImage = itemView.findViewById<GithubUserProfileImageView>(R.id.private_image)

            private val subItem = itemView.findViewById<GithubLinearLayout>(R.id.sub_item)


            override fun bind(state: UiGithubRepositoryState) {

                state.map(listOf(
                    nameTextView,
                    languageTextView,
                    privateImage,
                    colorImage
                ))

                state.mapCollapseOrExpandState(
                    listOf(
                        collapseImage,
                        subItem
                    )
                )

                collapseImage.setOnClickListener {
                    state.notifyAboutCollapseOrExpand(collapseOrExpandListener,adapterPosition)
                }
            }
        }

        class Empty(itemView: View) : GithubRepositoryAdapter.GithubRepositoryViewHolder(itemView) {
            private val emptyDataTextView = itemView.findViewById<GithubUserNameTextView>(R.id.empty_data)

            override fun bind(state: UiGithubRepositoryState) {
                state.map(listOf(emptyDataTextView))
            }
        }

        class Failure(itemView: View) : GithubRepositoryViewHolder(itemView) {
            private val errorTextView = itemView.findViewById<GithubErrorTextView>(R.id.error)

            override fun bind(state: UiGithubRepositoryState) = state.map(listOf(errorTextView))
        }
    }
}

