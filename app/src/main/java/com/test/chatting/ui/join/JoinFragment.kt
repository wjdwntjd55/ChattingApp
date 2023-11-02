package com.test.chatting.ui.join

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.chatting.R
import com.test.chatting.databinding.FragmentJoinBinding
import com.test.chatting.ui.main.MainActivity


class JoinFragment : Fragment() {

    lateinit var mainActivity: MainActivity
    lateinit var binding: FragmentJoinBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        binding = FragmentJoinBinding.inflate(layoutInflater)

        return binding.root
    }


}