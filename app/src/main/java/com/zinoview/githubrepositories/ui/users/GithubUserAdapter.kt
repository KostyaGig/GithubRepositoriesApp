package com.zinoview.githubrepositories.ui.users

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.zinoview.githubrepositories.R
import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.ui.view.AbstractView
import java.lang.IllegalStateException


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
class GithubUserAdapter(
    private val githubUserItemViewTypeFactory: Abstract.Factory<UiGithubUserState,Int>,
    private val githubUserViewHolderFactory: Abstract.Factory<Pair<Int,ViewGroup>,GithubUserViewHolder>
) : RecyclerView.Adapter<GithubUserAdapter.GithubUserViewHolder>() {

    private val list = ArrayList<UiGithubUserState>()

    fun update(list: List<UiGithubUserState>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int =
        githubUserItemViewTypeFactory.fetch(list[position])

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GithubUserViewHolder = githubUserViewHolderFactory.fetch(Pair(viewType, parent))

    override fun onBindViewHolder(holder: GithubUserViewHolder, position: Int) =
        holder.bind(list[position])

    override fun getItemCount(): Int = list.size

    abstract class GithubUserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        open fun bind(state: UiGithubUserState) {}

        class Progress(itemView: View) : GithubUserViewHolder(itemView)

        class Base(itemView: View) : GithubUserViewHolder(itemView) {

            private val nameTextView = itemView.findViewById<GithubUserNameTextView>(R.id.name)
            private val bioTextView = itemView.findViewById<GithubUserBioTextView>(R.id.bio)
            private val profileImage = itemView.findViewById<GithubUserProfileImageView>(R.id.image)

            override fun bind(state: UiGithubUserState)
                = state.map(listOf(
                    nameTextView,
                    bioTextView,
                    profileImage
                ))
        }

        class Failure(itemView: View) : GithubUserViewHolder(itemView) {
            private val errorTextView = itemView.findViewById<GithubErrorTextView>(R.id.error)

            override fun bind(state: UiGithubUserState) = state.map(listOf(errorTextView))
        }
    }
}

