package com.test.chatting.ui.chattinglist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.test.chatting.databinding.ItemChattingBinding

class ChattingAdapter: RecyclerView.Adapter<ChattingAdapter.ChattingViewHolder>() {

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
        return 10
    }

    override fun onBindViewHolder(holder: ChattingViewHolder, position: Int) {
        holder.userName.text = "홍길동"
        holder.message.text = "안녕!!"
    }

}