package io.keepcoding.eh_ho.topics

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.keepcoding.eh_ho.R
import io.keepcoding.eh_ho.databinding.ViewTopicBinding
import io.keepcoding.eh_ho.extensions.inflater
import io.keepcoding.eh_ho.model.Topic

class TopicsAdapter(diffUtilItemCallback: DiffUtil.ItemCallback<Topic> = DIFF) :
    ListAdapter<Topic, TopicsAdapter.TopicViewHolder>(diffUtilItemCallback) {

    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder =
        TopicViewHolder(parent)

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) =
        holder.bind(getItem(position))

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<Topic>() {
            override fun areItemsTheSame(oldItem: Topic, newItem: Topic): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Topic, newItem: Topic): Boolean =
                oldItem == newItem
        }
    }

    inner class TopicViewHolder(
        parent: ViewGroup,
        private val binding: ViewTopicBinding = ViewTopicBinding.inflate(
            parent.inflater,
            parent,
            false
        )
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(topic: Topic) {
            with(binding) {
                val ctx = root.context
                title.text = ctx.getString(R.string.title_placeholder, topic.title)
                lastUser.text =
                    ctx.getString(R.string.last_user_placeholder, topic.lastPosterUsername)
                posts.text = ctx.getString(R.string.posts_placeholder, topic.postsCount)
                replies.text = ctx.getString(R.string.replies_placeholder, topic.replyCount)
                likes.text = ctx.getString(R.string.likes_placeholder, topic.likeCount)
                pinned.text = ctx.getString(R.string.pinned_placeholder, topic.pinned.toString())
                bumped.text = ctx.getString(R.string.bumped_placeholder, topic.bumped.toString())

                root.setOnClickListener {
                    listener?.onItemClick(topic)
                }
            }
        }
    }

    fun interface Listener {
        fun onItemClick(topic: Topic)
    }
}