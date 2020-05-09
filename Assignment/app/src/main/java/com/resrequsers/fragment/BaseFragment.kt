package com.resrequsers.fragment

import android.content.Context
import androidx.fragment.app.Fragment
import com.resrequsers.common_classes.Navigator
import javax.inject.Inject

open class BaseFragment : Fragment() {

    @Inject
    lateinit var navigator: Navigator

    fun showProgressDialog(context: Context) {
        
    }
}