package com.test.chatting.ui.userlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.chatting.R
import com.test.chatting.databinding.FragmentUserListBinding
import com.test.chatting.ui.main.MainActivity

class UserListFragment : Fragment() {

    lateinit var mainActivity: MainActivity
    lateinit var binding: FragmentUserListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        binding = FragmentUserListBinding.inflate(layoutInflater)

        val recyclerView = binding.recyclerViewUserList
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = UserListAdapter()


        return binding.root
    }


}