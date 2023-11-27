package com.test.chatting.ui.chattinglist

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.test.chatting.databinding.ItemChattingBinding
import com.test.chatting.model.ChattingItem
import com.test.chatting.model.User

class ChattingAdapter(val otherUserItem: User, private var allChattingItemList : MutableList<ChattingItem>): RecyclerView.Adapter<ChattingAdapter.ChattingViewHolder>() {
    inner class ChattingViewHolder(binding: ItemChattingBinding): RecyclerView.ViewHolder(binding.root) {

        val userName: TextView
        val message: TextView

        init {
            userName = binding.textViewUsernameChattingItem
            message = binding.textViewMessageChattingItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChattingViewHolder {
        val binding = ItemChattingBinding.inflate(LayoutInflater.from(parent.context))
        val chattingViewHolder = ChattingViewHolder(binding)

        binding.root.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        return chattingViewHolder
    }

    override fun getItemCount(): Int {
        return allChattingItemList.size
    }

    override fun onBindViewHolder(holder: ChattingViewHolder, position: Int) {

        if (allChattingItemList[position].userUid == otherUserItem.userId) {
            holder.userName.text = otherUserItem.username
            holder.message.text = allChattingItemList[position].message
            holder.message.gravity = Gravity.START
        } else {
            holder.userName.isVisible = false
            holder.message.text = allChattingItemList[position].message
            holder.message.gravity = Gravity.END
        }

    }

}