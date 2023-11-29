package com.test.chatting.ui.chattinglist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.chatting.R
import com.test.chatting.databinding.FragmentChattingListBinding
import com.test.chatting.model.ChattingRoomItem
import com.test.chatting.repository.LoginRepository.Companion.CURRENT_USER_UID
import com.test.chatting.ui.main.MainActivity
import kotlin.math.log


class ChattingListFragment : Fragment() {

    lateinit var mainActivity: MainActivity
    lateinit var binding: FragmentChattingListBinding

    lateinit var viewModel: ChattingListViewModel

    var allMyChattingRoomInfo = mutableListOf<ChattingRoomItem>()

    val TAG = "ChattingListFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        binding = FragmentChattingListBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(this)[ChattingListViewModel::class.java]

        viewModel.getMyChattingRoom(CURRENT_USER_UID)

        viewModel.myChattingRoomLiveData.observe(viewLifecycleOwner) { myChattingRoomData ->
            allMyChattingRoomInfo.clear()
            myChattingRoomData.forEach { allMyChattingRoomInfo.add(it) }
            initRecyclerView()
        }

        initRecyclerView()

        return binding.root
    }

    private fun initRecyclerView() {
        val recyclerView = binding.recyclerViewChattingList
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = ChattingListAdapter(mainActivity, allMyChattingRoomInfo)
    }
}