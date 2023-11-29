package com.test.chatting.ui.chattinglist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.test.chatting.databinding.ItemChattingListBinding
import com.test.chatting.model.ChattingRoomItem
import com.test.chatting.model.User
import com.test.chatting.ui.main.MainActivity

class ChattingListAdapter(
    private val mainActivity: MainActivity,
    private var allMyChattingRoomDataList : MutableList<ChattingRoomItem>): RecyclerView.Adapter<ChattingListAdapter.ChattingListViewHolder>() {

    inner class ChattingListViewHolder(binding: ItemChattingListBinding): RecyclerView.ViewHolder(binding.root) {

        val userProfile: ImageView
        val userName: TextView
        val lastMessage: TextView

        init {
            userProfile = binding.imageViewUserProfileChattingList
            userName = binding.textViewUserNameChattingList
            lastMessage = binding.textViewLastMessageChattingList

            binding.root.setOnClickListener {

                val otherUserName = allMyChattingRoomDataList[adapterPosition].otherUserName
                val otherUserUid = allMyChattingRoomDataList[adapterPosition].otherUserUid

                val otherUser = User(userId = otherUserUid!!, username = otherUserName!!, description = "")

                val bundle = Bundle()
                bundle.putParcelable("otherUser", otherUser)

                mainActivity.replaceFragment(MainActivity.CHATTING_FRAGMENT, true, bundle)

            }

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
        return allMyChattingRoomDataList.size
    }

    override fun onBindViewHolder(holder: ChattingListViewHolder, position: Int) {
        holder.userName.text = allMyChattingRoomDataList[position].otherUserName
        holder.lastMessage.text = allMyChattingRoomDataList[position].lastMessage
    }

}