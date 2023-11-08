package com.test.chatting.ui.userlist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.chatting.R
import com.test.chatting.databinding.FragmentUserListBinding
import com.test.chatting.model.User
import com.test.chatting.ui.main.MainActivity

class UserListFragment : Fragment() {

    lateinit var mainActivity: MainActivity
    lateinit var binding: FragmentUserListBinding

    val TAG = "UserListFragment"

    private lateinit var viewModel: UserListViewModel

    var allUserInfo = mutableListOf<User>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        binding = FragmentUserListBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(this)[UserListViewModel::class.java]

        viewModel.getAllUserInfo()

        viewModel.userListLiveData.observe(viewLifecycleOwner) { userList ->
            userList.forEach { allUserInfo.add(it) }

            Log.d(TAG, "userList : ${userList}")
            initRecyclerView()

        }



        return binding.root
    }

    private fun initRecyclerView() {
        val recyclerView = binding.recyclerViewUserList
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = UserListAdapter(allUserInfo)
    }


}