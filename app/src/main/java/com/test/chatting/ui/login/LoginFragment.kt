package com.test.chatting.ui.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.test.chatting.databinding.FragmentLoginBinding
import com.test.chatting.ui.main.MainActivity

class LoginFragment : Fragment() {

    lateinit var mainActivity: MainActivity
    lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel

    val TAG = "LoginFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        mainActivity.hideBottomNavigationView()

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        signBtn()
        loginBtn()
        observeLoginResult()

        return binding.root
    }

    fun signBtn() {

        binding.buttonLoginJoin.setOnClickListener {
            mainActivity.replaceFragment(MainActivity.JOIN_FRAGMENT, true, null)
        }

    }

    fun loginBtn() {

        binding.buttonLogin.setOnClickListener {
            val email = binding.editTextLoginEmail.text.toString()
            val password = binding.editTextLoginPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.loginUser(email, password)
            } else {
                mainActivity.hideKeyboard()
                Snackbar.make(binding.root, "이메일 또는 패스워드가 입력되지 않았습니다.", Snackbar.LENGTH_LONG).show()
            }
        }

    }

    fun observeLoginResult() {

        viewModel.loginResultLiveData.observe(viewLifecycleOwner) { loginResult ->
            if (loginResult) {
                mainActivity.hideKeyboard()
                mainActivity.bottomNavigation()
                mainActivity.replaceFragment(MainActivity.USER_LIST_FRAGMENT, false, null)
                Snackbar.make(binding.root, "로그인에 성공했습니다", Snackbar.LENGTH_LONG).show()
            } else {
                mainActivity.hideKeyboard()
                Snackbar.make(binding.root, "이메일 또는 패스워드가 틀렸습니다.", Snackbar.LENGTH_LONG).show()
            }
        }
    }


}