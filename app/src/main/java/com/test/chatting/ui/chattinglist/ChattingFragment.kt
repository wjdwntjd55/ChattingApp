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
import com.test.chatting.databinding.FragmentChattingBinding
import com.test.chatting.model.ChattingItem
import com.test.chatting.model.Key
import com.test.chatting.model.User
import com.test.chatting.repository.LoginRepository.Companion.CURRENT_USER_UID
import com.test.chatting.ui.main.MainActivity

class ChattingFragment : Fragment() {

    lateinit var mainActivity: MainActivity
    lateinit var binding: FragmentChattingBinding

    lateinit var viewModel: ChattingViewModel

    private lateinit var otherUser: User
    private lateinit var chatRoomId: String
    private lateinit var otherUserUid: String

    private val allChattingItemList = mutableListOf<ChattingItem>()

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

        sendMessage()

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        
        mainActivity.showBottomNavigationView()
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

            viewModel.updateInfo(CURRENT_USER_UID, otherUser, chatRoomId, message)
            binding.editTextChattingMessage.text.clear()

        }

    }

}