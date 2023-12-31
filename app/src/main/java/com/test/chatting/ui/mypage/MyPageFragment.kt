package com.test.chatting.ui.mypage

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.test.chatting.R
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

    val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        // Callback is invoked after th user selects a media item or closes the photo picker.
        if (uri != null) {
            viewModel.updateSelectedUri(uri)
            binding.imageViewMyPageUserProfile.setImageURI(uri)
            Log.d(TAG, "Selected URI: $uri")
        } else {
            Log.d(TAG, "No media selected")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        binding = FragmentMyPageBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(this)[MyPageViewModel::class.java]

        viewModel.getCurrentLoginUserInfo(CURRENT_USER_UID)

        inintView()
        updateUserProfile()
        updateUserInfo()
        signOut()


        return binding.root
    }

    fun inintView() {

        viewModel.currentUserLiveData.observe(viewLifecycleOwner) { userInfo ->

            binding.editTextMyPageUsername.setText(userInfo.username)
            binding.editTextMyPageUsername.isEnabled = false

            binding.editTextMyPageDescription.setText(userInfo.description)

            if (userInfo.userProfile.isNotEmpty()) {
                Glide.with(this)
                    .load(userInfo.userProfile)
                    .into(binding.imageViewMyPageUserProfile)
            }

        }

    }

    fun updateUserProfile() {

        binding.imageViewMyPageUserProfile.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

    }

    fun updateUserInfo() {

        binding.buttonMyPageApply.setOnClickListener {
            val description = binding.editTextMyPageDescription.text.toString()

            if (viewModel.selectedUri.value != null) {
                val photoUri = viewModel.selectedUri.value ?: return@setOnClickListener

                viewModel.upLoadImage(photoUri)
            }

            viewModel.upDateDescription(description)

            mainActivity.hideKeyboard()
            Snackbar.make(binding.root, "변경되었습니다", Snackbar.LENGTH_LONG).show()

        }

    }

    fun signOut() {

        binding.buttonMyPageSignOut.setOnClickListener {
            viewModel.logOut()
            mainActivity.selectBottomNavigationItem(R.id.user_list_menu)
            mainActivity.replaceFragment(MainActivity.LOGIN_FRAGMENT, false, null)
        }

    }


}