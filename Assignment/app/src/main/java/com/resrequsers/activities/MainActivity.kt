package com.resrequsers.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.resrequsers.dependency.module.ApplicationModule
import com.resrequsers.DaggerApplicationComponent
import com.resrequsers.R
import com.resrequsers.common_classes.Utility
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {


    /*
    * this will return the object of MainActivity
    */
    companion object {
        fun getCallingIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialiseDependency()
        navigator.loadLoginFragment(this)
        main_container.setOnClickListener {
            Utility.hideKeyboard(main_container)
        }
    }

    /*
   * This method is used to allow the class to user the dagger components
   */
    private fun initialiseDependency() {
        val applicationComponent =
            DaggerApplicationComponent.builder()
                .applicationModule(
                    ApplicationModule(
                        application
                    )
                )
                .build()
        applicationComponent.inject(this)
    }
}
