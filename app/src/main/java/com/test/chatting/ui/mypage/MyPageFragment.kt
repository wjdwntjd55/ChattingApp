package com.test.chatting.ui.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.chatting.databinding.FragmentMyPageBinding
import com.test.chatting.ui.main.MainActivity


class MyPageFragment : Fragment() {

    lateinit var mainActivity: MainActivity
    lateinit var binding: FragmentMyPageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        binding = FragmentMyPageBinding.inflate(layoutInflater)

        return binding.root
    }
}