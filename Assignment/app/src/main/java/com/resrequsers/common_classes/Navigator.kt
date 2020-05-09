package com.resrequsers.common_classes

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.resrequsers.R
import com.resrequsers.activities.MainActivity
import com.resrequsers.fragment.*
import com.resrequsers.models.users_models.Data
import javax.inject.Inject

/*
 * This class used for navigating between activities and fragments
 */
class Navigator @Inject constructor() {

    /*
   * This method is used to load the main activity
   */
    fun loadMainActivity(context: Context) {
        val intentToLaunch = MainActivity.getCallingIntent(context)
        context.startActivity(intentToLaunch)
    }

    /*
   * This method is used to load the login fragment
   */
    fun loadLoginFragment(context: FragmentActivity) {
        val fragmentToLoad = LoginFragment.getCallingFragment()
        replaceFragment(context, fragmentToLoad)
    }

    /*
   * This method is used to load the Home fragment
   */
    fun loadHomeFragment(context: FragmentActivity) {
        val fragmentToLoad = HomeFragment.getCallingFragment()
        replaceFragment(context, fragmentToLoad)
    }

    /*
   * This method is used to load the user list fragment
   */
    fun loadUserListFragment(context: FragmentActivity) {
        val fragmentToLoad = UserListFragment.getCallingFragment()
        addFragment(context, fragmentToLoad)
    }

    /*
   * This method is used to load the update fragment and add data in the bundle to pass
   */
    fun loadUpdateFragment(context: FragmentActivity, data: Data) {
        val fragmentToLoad = UpdateFragment.getCallingFragment()
        val bundle = Bundle()
        bundle.putString("first_name", data.first_name)
        bundle.putString("last_name", data.last_name)
        bundle.putString("email", data.email)
        fragmentToLoad.arguments = bundle
        addFragment(context, fragmentToLoad)
    }

    fun loadRegisterFragment(context: FragmentActivity) {
        val fragmentToLoad = RegisterFragment.getCallingFragment()
        addFragment(context, fragmentToLoad)
    }

    /*
    * This method is used to replace the fragment
    */
    private fun replaceFragment(context: FragmentActivity, fragmentToLoad: Fragment) {
        val manager = context.supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.main_container, fragmentToLoad)
        transaction.commit()
    }

    /*
    * This method is used to add the fragment
    */
    private fun addFragment(context: FragmentActivity, fragmentToLoad: Fragment) {
        val manager = context.supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.add(R.id.main_container, fragmentToLoad).addToBackStack("register")
        transaction.commit()
    }
}