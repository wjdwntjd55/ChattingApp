package com.test.chatting.ui.chattinglist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.test.chatting.BuildConfig
import com.test.chatting.R
import com.test.chatting.databinding.FragmentChattingBinding
import com.test.chatting.model.ChattingItem
import com.test.chatting.model.Key
import com.test.chatting.model.User
import com.test.chatting.repository.LoginRepository.Companion.CURRENT_USER_UID
import com.test.chatting.ui.main.MainActivity
import com.test.chatting.ui.mypage.MyPageViewModel
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class ChattingFragment : Fragment() {

    lateinit var mainActivity: MainActivity
    lateinit var binding: FragmentChattingBinding

    lateinit var viewModel: ChattingViewModel
    lateinit var fcmViewModel: FCMViewModel
    lateinit var myPageViewModel: MyPageViewModel

    private lateinit var otherUser: User
    private lateinit var chatRoomId: String
    private lateinit var otherUserUid: String
    private lateinit var currentUser: User

    private val allChattingItemList = mutableListOf<ChattingItem>()

    val TAG = "ChattingFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        binding = FragmentChattingBinding.inflate(layoutInflater)

        mainActivity.hideBottomNavigationView()

        otherUser = arguments?.getParcelable("otherUser") ?: User("", "", "", "", "")

        viewModel = ViewModelProvider(this)[ChattingViewModel::class.java]
        fcmViewModel = ViewModelProvider(this, FCMViewModelFactory(requireContext()))[FCMViewModel::class.java]
        myPageViewModel = ViewModelProvider(this)[MyPageViewModel::class.java]

        viewModel.createChattingRoom(CURRENT_USER_UID, otherUser)
        myPageViewModel.getCurrentLoginUserInfo(CURRENT_USER_UID)

        Log.d(TAG, otherUser.toString() )

        viewModel.chattingRoomLiveData.observe(viewLifecycleOwner) { chattingRoomData ->
            Log.d(TAG, chattingRoomData.toString())
            chatRoomId = chattingRoomData.chatRoomId.toString()
            otherUserUid = chattingRoomData.otherUserUid.toString()

            viewModel.getAllChattingData(chatRoomId)

        }

        viewModel.chattingItemListLiveData.observe(viewLifecycleOwner) { chattingItemList ->
            allChattingItemList.clear()
            allChattingItemList.addAll(chattingItemList)

            initRecyclerView()
        }

        myPageViewModel.currentUserLiveData.observe(viewLifecycleOwner) { userInfo ->
            currentUser = userInfo
        }

        initToolbar()
        sendMessage()

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        
        mainActivity.showBottomNavigationView()
    }

    private fun initToolbar() {
        val toolbar = binding.materialToolbarChatting
        toolbar.title = otherUser.username
        toolbar.setTitleTextAppearance(context, R.style.Typography_Medium24)

        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24)
        toolbar.setNavigationOnClickListener {
            mainActivity.removeFragment(MainActivity.CHATTING_FRAGMENT)
        }
    }

    private fun initRecyclerView() {
        val recyclerView = binding.recyclerViewChatting
        recyclerView.adapter = ChattingAdapter(otherUser, allChattingItemList)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val lastItemPosition = allChattingItemList.size - 1
        if (lastItemPosition >= 0) {
            recyclerView.scrollToPosition(lastItemPosition)
        }
    }

    fun sendMessage() {

        binding.buttonChattingSend.setOnClickListener {
            val message = binding.editTextChattingMessage.text.toString()

            if (message.isEmpty()) {
                Snackbar.make(binding.root, "빈 메시지를 전송할 수는 없습니다.", Snackbar.LENGTH_LONG).show()
                mainActivity.hideKeyboard()

                return@setOnClickListener
            }

            viewModel.createMessage(chatRoomId, message)

            viewModel.updateInfo(currentUser, otherUser, chatRoomId, message, currentUser.userProfile)
            binding.editTextChattingMessage.text.clear()

            fcmViewModel.sendFCM(currentUser.username, message, otherUser.fcmToken)

        }

    }

}