package com.resrequsers.dependency.components

import android.content.Context
import com.resrequsers.dependency.module.ApplicationModule
import com.resrequsers.activities.BaseActivity
import com.resrequsers.activities.MainActivity
import com.resrequsers.activities.SplashActivity
import com.resrequsers.fragment.*
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun context(): Context

    fun inject(baseActivity: BaseActivity)

    fun inject(SplashActivity: SplashActivity)

    fun inject(MainActivity: MainActivity)

    fun inject(LoginFragment: LoginFragment)

    fun inject(RegisterFragment: RegisterFragment)

    fun inject(HomeFragment: HomeFragment)

    fun inject(UpdateFragment: UpdateFragment)

    fun inject(UserListFragment: UserListFragment)
}