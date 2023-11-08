package com.test.chatting.ui.userlist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.test.chatting.databinding.ItemUserListBinding
import com.test.chatting.model.User

class UserListAdapter(private var allUserDataList : MutableList<User>) : RecyclerView.Adapter<UserListAdapter.UserListViewHolder>() {

    inner class UserListViewHolder(binding: ItemUserListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val userProfile: ImageView
        val userName: TextView
        val userDescription: TextView

        init {
            userProfile = binding.imageViewUserProfileUserList
            userName = binding.textViewUserNameUserList
            userDescription = binding.textViewUserDescriptionUserList
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val binding = ItemUserListBinding.inflate(LayoutInflater.from(parent.context))
        val userListViewHolder = UserListViewHolder(binding)

        binding.root.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        return userListViewHolder
    }

    override fun getItemCount(): Int {
        return allUserDataList.size
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        holder.userName.text = allUserDataList[position].username
    }

}