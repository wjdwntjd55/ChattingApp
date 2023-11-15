package com.test.chatting.ui.mypage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.test.chatting.databinding.FragmentMyPageBinding
import com.test.chatting.model.Key
import com.test.chatting.model.User
import com.test.chatting.repository.LoginRepository.Companion.CURRENT_USER_UID
import com.test.chatting.ui.main.MainActivity


class MyPageFragment : Fragment() {

    lateinit var mainActivity: MainActivity
    lateinit var binding: FragmentMyPageBinding

    private lateinit var viewModel: MyPageViewModel

    val TAG = "MyPageFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        binding = FragmentMyPageBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(this)[MyPageViewModel::class.java]

        viewModel.getCurrentLoginUserInfo(CURRENT_USER_UID)

        inintView()
        updateUserInfo()
        signOut()


        return binding.root
    }

    fun inintView() {

        viewModel.currentUserLiveData.observe(viewLifecycleOwner) { userInfo ->

            binding.editTextMyPageUsername.setText(userInfo.username)
            binding.editTextMyPageUsername.isEnabled = false

            binding.editTextMyPageDescription.setText(userInfo.description)

        }

    }

    fun updateUserInfo() {

        binding.buttonMyPageApply.setOnClickListener {
            val description = binding.editTextMyPageDescription.text.toString()

            viewModel.upDateDescription(description)

            mainActivity.hideKeyboard()

        }

    }

    fun signOut() {

        binding.buttonMyPageSignOut.setOnClickListener {
            viewModel.logOut()
            mainActivity.replaceFragment(MainActivity.LOGIN_FRAGMENT, false, null)
        }

    }


}