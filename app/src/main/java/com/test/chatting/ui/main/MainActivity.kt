package com.test.chatting.ui.main

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.test.chatting.R
import com.test.chatting.databinding.ActivityMainBinding
import com.test.chatting.ui.join.JoinFragment
import com.test.chatting.ui.login.LoginFragment
import com.test.chatting.ui.mypage.MyPageFragment
import com.test.chatting.ui.userlist.UserListFragment

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        replaceFragment(LOGIN_FRAGMENT, false, null)

    }

    fun replaceFragment(name:String, addToBackStack:Boolean, bundle:Bundle?){

        // Fragment 교체 상태로 설정
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        var newFragment: Fragment? = null

        // 새로운 Fragment를 담을 변수
        newFragment = when(name){
            LOGIN_FRAGMENT -> LoginFragment()
            JOIN_FRAGMENT -> JoinFragment()
            USER_LIST_FRAGMENT -> UserListFragment()
            MY_PAGE_FRAGMENT -> MyPageFragment()
            else -> Fragment()
        }

        newFragment?.arguments = bundle

        if(newFragment != null) {

            // Fragment를 교채한다.
            fragmentTransaction.replace(R.id.fragmentContainerView_main, newFragment)

            if (addToBackStack == true) {
                // Fragment를 Backstack에 넣어 이전으로 돌아가는 기능이 동작할 수 있도록 한다.
                fragmentTransaction.addToBackStack(name)
            }

            // 교체 명령이 동작하도록 한다.
            fragmentTransaction.commit()
        }
    }

    // Fragment를 BackStack에서 제거한다.
    fun removeFragment(name: String){
        supportFragmentManager.popBackStack(name, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val currentFocusView = currentFocus
        currentFocusView?.let {
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    fun showBottomNavigationView() {
        activityMainBinding.bottomNavigationViewMain.visibility = View.VISIBLE
    }

    fun hideBottomNavigationView() {
        activityMainBinding.bottomNavigationViewMain.visibility = View.GONE
    }
    fun bottomNavigation() {

        activityMainBinding.bottomNavigationViewMain.run {
            visibility = View.VISIBLE

            setOnItemSelectedListener {
                when(it.itemId) {
                    R.id.user_list_menu -> {
                        replaceFragment(USER_LIST_FRAGMENT, false, null)
                        return@setOnItemSelectedListener true
                    }

                    R.id.setting_menu -> {
                        replaceFragment(MY_PAGE_FRAGMENT, false, null)
                        return@setOnItemSelectedListener true
                    }

                    else -> return@setOnItemSelectedListener false
                }
            }
        }

    }

    companion object {
        val LOGIN_FRAGMENT = "LoginFragment"
        val JOIN_FRAGMENT = "JoinFragment"
        val USER_LIST_FRAGMENT = "UserListFragment"
        val MY_PAGE_FRAGMENT = "MyPageFragment"
    }

}