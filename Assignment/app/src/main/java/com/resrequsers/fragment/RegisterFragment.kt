package com.resrequsers.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.resrequsers.dependency.module.ApplicationModule
import com.resrequsers.DaggerApplicationComponent
import com.resrequsers.R
import com.resrequsers.common_classes.Utility
import com.resrequsers.presenter.RegistrationPresenter
import com.resrequsers.views.RegistrationView
import kotlinx.android.synthetic.main.fragment_register.*
import javax.inject.Inject

class RegisterFragment : BaseFragment(), RegistrationView {

    @Inject
    lateinit var registrationPresenter: RegistrationPresenter

    /*
    * this will return the object of RegistrationFragment
    */
    companion object {
        fun getCallingFragment(): Fragment {
            return RegisterFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialiseDependency()
        registrationPresenter.registrationView = this
        register.setOnClickListener {
            Utility.hideKeyboard(register)
            Utility.showProgressDialog(requireContext())
            registrationPresenter.checkInputs(
                first_name.text.toString(),
                last_name.text.toString(),
                register_email.text.toString(),
                register_password.text.toString(),
                re_password.text.toString()
            )
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

    override fun registrationSuccessful() {
        Utility.dismissDialog()
        Utility.showMessage(container, "Registration successful")
        activity!!.supportFragmentManager.popBackStack()
    }

    override fun registrationError(message: String) {
        Utility.dismissDialog()
        Utility.showMessage(container, message)
    }
}