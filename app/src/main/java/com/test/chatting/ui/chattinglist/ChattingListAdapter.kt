package com.test.chatting.ui.chattinglist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.test.chatting.databinding.ItemChattingListBinding
import com.test.chatting.model.ChattingRoomItem
import com.test.chatting.ui.main.MainActivity

class ChattingListAdapter(
    private val mainActivity: MainActivity,
    private var allUserDataList : MutableList<ChattingRoomItem>): RecyclerView.Adapter<ChattingListAdapter.ChattingListViewHolder>() {

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
        return allUserDataList.size
    }

    override fun onBindViewHolder(holder: ChattingListViewHolder, position: Int) {
        holder.userName.text = allUserDataList[position].otherUserName
        holder.lastMessage.text = allUserDataList[position].lastMessage
    }

}