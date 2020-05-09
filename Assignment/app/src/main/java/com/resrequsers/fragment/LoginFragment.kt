package com.resrequsers.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.resrequsers.dependency.module.ApplicationModule
import com.resrequsers.R
import com.resrequsers.common_classes.Utility
import com.resrequsers.dependency.components.DaggerApplicationComponent
import com.resrequsers.models.login_models.LoginResponse
import com.resrequsers.presenter.LoginPresenter
import com.resrequsers.views.LoginView
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject

class LoginFragment : BaseFragment(), LoginView {

    @Inject
    lateinit var loginPresenter: LoginPresenter

    /*
    * this will return the object of LoginFragment
    */
    companion object {
        fun getCallingFragment(): Fragment {
            return LoginFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialiseDependency()

        loginPresenter.loginView = this

        login.setOnClickListener {
            Utility.hideKeyboard(login)
            Utility.showProgressDialog(requireContext())
            loginPresenter.checkInputs(email.text.toString(), password.text.toString())
        }
    }

    /*
     * This method is used to allow the class to user the dagger components
    */
    private fun initialiseDependency() {
        val applicationComponent = DaggerApplicationComponent.builder()
            .applicationModule(
                ApplicationModule(
                    requireActivity().application
                )
            ).build()
        applicationComponent.inject(this)
    }

    override fun showErrorMessage(message: String) {
        Utility.dismissDialog()
        Utility.showMessage(container, message)
    }

    override fun loginSuccessful(loginResponse: LoginResponse) {
        Utility.dismissDialog()
        Utility.showMessage(container, "Login Success")
        navigator.loadHomeFragment(activity!!)
    }

    override fun loginError(message: String) {
        Utility.dismissDialog()
        Utility.showMessage(container, message)
    }
}