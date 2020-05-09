package com.resrequsers.activities

import android.os.Bundle
import android.os.Handler
import com.resrequsers.dependency.module.ApplicationModule
import com.resrequsers.R
import com.resrequsers.dependency.components.DaggerApplicationComponent

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        initialiseDependency()
        // Handler is used to stop opening the main activity for 2 seconds
        Handler().postDelayed({
            navigator.loadMainActivity(this)
            finish()
        }, 2000)
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
