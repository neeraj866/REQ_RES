package com.resrequsers.activities

import androidx.appcompat.app.AppCompatActivity
import com.resrequsers.common_classes.Navigator
import javax.inject.Inject

open class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var  navigator: Navigator
}