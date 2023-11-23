package com.test.chatting.ui.chattinglist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.test.chatting.databinding.FragmentChattingBinding
import com.test.chatting.model.User
import com.test.chatting.repository.LoginRepository.Companion.CURRENT_USER_UID
import com.test.chatting.ui.main.MainActivity

class ChattingFragment : Fragment() {

    lateinit var mainActivity: MainActivity
    lateinit var binding: FragmentChattingBinding

    lateinit var viewModel: ChattingViewModel

    private lateinit var otherUser: User

    val TAG = "ChattingFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        binding = FragmentChattingBinding.inflate(layoutInflater)

        mainActivity.hideBottomNavigationView()

        otherUser = arguments?.getParcelable("otherUser") ?: User("", "", "")

        viewModel = ViewModelProvider(this)[ChattingViewModel::class.java]

        viewModel.createChattingRoom(CURRENT_USER_UID, otherUser)

        Log.d(TAG, otherUser.toString() )

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        
        mainActivity.showBottomNavigationView()
    }


}