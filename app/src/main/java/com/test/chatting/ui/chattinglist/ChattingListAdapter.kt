package com.test.chatting.ui.chattinglist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.test.chatting.databinding.ItemChattingListBinding

class ChattingListAdapter: RecyclerView.Adapter<ChattingListAdapter.ChattingListViewHolder>() {

    inner class ChattingListViewHolder(binding: ItemChattingListBinding): RecyclerView.ViewHolder(binding.root) {

        val userProfile: ImageView
        val userName: TextView
        val lastMessage: TextView

        init {
            userProfile = binding.imageViewUserProfileChattingList
            userName = binding.textViewUserNameChattingList
            lastMessage = binding.textViewLastMessageChattingList
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChattingListViewHolder {

        val binding = ItemChattingListBinding.inflate(LayoutInflater.from(parent.context))
        val chattingListViewHolder = ChattingListViewHolder(binding)

        binding.root.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        return chattingListViewHolder
    }

    override fun getItemCount(): Int {
        return 20
    }

    override fun onBindViewHolder(holder: ChattingListViewHolder, position: Int) {
        holder.userName.text = "홍길동"
        holder.lastMessage.text = "마지막 메시지 입니다"
    }

}