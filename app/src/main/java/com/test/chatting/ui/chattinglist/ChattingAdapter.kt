package com.test.chatting.ui.chattinglist

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.chatting.databinding.ItemChattingMyBinding
import com.test.chatting.databinding.ItemChattingOtherBinding
import com.test.chatting.model.ChattingItem
import com.test.chatting.model.User

class ChattingAdapter(val otherUserItem: User, private var allChattingItemList : MutableList<ChattingItem>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    inner class ChattingViewHolder(binding: ItemChattingOtherBinding): RecyclerView.ViewHolder(binding.root) {

        val userProfile: ImageView
        val userName: TextView
        val message: TextView

        init {
            userProfile = binding.circleImageViewUserProfileChatitingItem
            userName = binding.textViewUsernameChattingItem
            message = binding.textViewMessageChattingItem
        }

    }

    // 유저 자신 채팅
    inner class MyChattingViewHolder(itemChattingMyBinding: ItemChattingMyBinding): RecyclerView.ViewHolder(itemChattingMyBinding.root) {

        val message: TextView

        init {
            message = itemChattingMyBinding.textViewMessageChattingItemMy
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (allChattingItemList[position].userUid == otherUserItem.userId) {
            CHATTING_ROOM_OWNER_OTHER
        } else {
            CHATTING_ROOM_OWNER_MY
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemChattingOtherBinding.inflate(LayoutInflater.from(parent.context))
        val itemChattingMyBinding = ItemChattingMyBinding.inflate(LayoutInflater.from(parent.context))

        val chattingViewHolder = ChattingViewHolder(binding)
        val myChattingViewHolder = MyChattingViewHolder(itemChattingMyBinding)

        val params = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        binding.root.layoutParams = params
        itemChattingMyBinding.root.layoutParams = params

        return if(viewType == CHATTING_ROOM_OWNER_OTHER){
            chattingViewHolder
        }else {
            myChattingViewHolder
        }
    }

    override fun getItemCount(): Int {
        return allChattingItemList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when(holder){
            is ChattingViewHolder -> {

                val context = holder.itemView.context

                if (otherUserItem.userProfile.isNotEmpty()) {
                    Glide.with(context)
                        .load(otherUserItem.userProfile)
                        .into(holder.userProfile)
                }

                holder.userName.text = otherUserItem.username
                holder.message.text = allChattingItemList[position].message
                holder.message.gravity = Gravity.START
            }

            is MyChattingViewHolder -> {
                holder.message.text = allChattingItemList[position].message

            }

        }
    }

    companion object {
        const val CHATTING_ROOM_OWNER_MY = 1
        const val CHATTING_ROOM_OWNER_OTHER = 2
    }

}