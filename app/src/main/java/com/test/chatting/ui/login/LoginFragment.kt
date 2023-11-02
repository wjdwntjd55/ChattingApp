package com.test.chatting.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.chatting.databinding.FragmentLoginBinding
import com.test.chatting.ui.main.MainActivity

class LoginFragment : Fragment() {

    lateinit var mainActivity: MainActivity
    lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        return binding.root
    }


}