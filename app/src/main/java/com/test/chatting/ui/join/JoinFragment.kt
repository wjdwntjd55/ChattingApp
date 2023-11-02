package com.test.chatting.ui.join

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.test.chatting.R
import com.test.chatting.databinding.FragmentJoinBinding
import com.test.chatting.ui.main.MainActivity


class JoinFragment : Fragment() {

    lateinit var mainActivity: MainActivity
    lateinit var binding: FragmentJoinBinding
    private lateinit var viewModel: JoinViewModel

    val TAG = "JoinFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        binding = FragmentJoinBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(this)[JoinViewModel::class.java]

        joinAccount()

        return binding.root
    }

    private fun joinAccount() {
        binding.buttonJoin.setOnClickListener {
            val email = binding.editTextJoinEmail.text.toString()
            val password = binding.editTextJoinPassword.text.toString()

            viewModel.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "성공")
                        mainActivity.removeFragment(MainActivity.JOIN_FRAGMENT)
                    } else {
                        Log.d(TAG, task.exception.toString())
                    }
                }
        }
    }


}