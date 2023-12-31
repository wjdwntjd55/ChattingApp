package com.test.chatting.ui.userlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.chatting.databinding.ItemUserListBinding
import com.test.chatting.model.User
import com.test.chatting.ui.main.MainActivity

class UserListAdapter(
    private val mainActivity: MainActivity,
    private var allUserDataList : MutableList<User>) : RecyclerView.Adapter<UserListAdapter.UserListViewHolder>() {

    inner class UserListViewHolder(binding: ItemUserListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val userProfile: ImageView
        val userName: TextView
        val userDescription: TextView

        init {
            userProfile = binding.imageViewUserProfileUserList
            userName = binding.textViewUserNameUserList
            userDescription = binding.textViewUserDescriptionUserList

            binding.root.setOnClickListener {

                val otherUser = allUserDataList[adapterPosition]

                val bundle = Bundle()
                bundle.putParcelable("otherUser", otherUser)

                mainActivity.replaceFragment(MainActivity.CHATTING_FRAGMENT, true, bundle)
            }
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
        holder.userDescription.text = allUserDataList[position].description

        if (allUserDataList[position].userProfile.isNotEmpty()) {
            Glide.with(mainActivity)
                .load(allUserDataList[position].userProfile)
                .into(holder.userProfile)
        }

    }

}