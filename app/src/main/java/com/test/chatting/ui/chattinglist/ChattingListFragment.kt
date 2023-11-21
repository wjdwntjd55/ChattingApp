package com.test.chatting.ui.chattinglist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.chatting.R
import com.test.chatting.databinding.FragmentChattingListBinding
import com.test.chatting.ui.main.MainActivity


class ChattingListFragment : Fragment() {

    lateinit var mainActivity: MainActivity
    lateinit var binding: FragmentChattingListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        binding = FragmentChattingListBinding.inflate(layoutInflater)

        initRecyclerView()

        return binding.root
    }

    private fun initRecyclerView() {
        val recyclerView = binding.recyclerViewChattingList
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = ChattingListAdapter()
    }
}