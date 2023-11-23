package com.test.chatting.ui.chattinglist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.chatting.databinding.FragmentChattingBinding
import com.test.chatting.model.User
import com.test.chatting.ui.main.MainActivity

class ChattingFragment : Fragment() {

    lateinit var mainActivity: MainActivity
    lateinit var binding: FragmentChattingBinding

    private lateinit var otherUser: User

    val TAG = "ChattingFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        binding = FragmentChattingBinding.inflate(layoutInflater)

        otherUser = arguments?.getParcelable("otherUser") ?: User("", "", "")

        Log.d(TAG, otherUser.toString() )

        return binding.root
    }


}