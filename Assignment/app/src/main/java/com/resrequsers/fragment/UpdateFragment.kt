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
import com.resrequsers.presenter.UpdatePresenter
import com.resrequsers.views.UpdateView
import kotlinx.android.synthetic.main.fragment_update.*
import javax.inject.Inject

class UpdateFragment : BaseFragment(), UpdateView {

    @Inject
    lateinit var updatePresenter: UpdatePresenter

    companion object {
        fun getCallingFragment(): Fragment {
            return UpdateFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_update, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialiseDependency()
        updatePresenter.updateView = this

        val bundle = arguments
        when {
            bundle != null -> {
                first_name.setText(bundle.getString("first_name"))
                last_name.setText(bundle.getString("last_name"))
                update_email.setText(bundle.getString("email"))
            }
        }

        update.setOnClickListener {
            Utility.showProgressDialog(requireContext())
            updatePresenter.checkInputs(
                first_name.text.toString(),
                last_name.text.toString(),
                update_email.text.toString()
            )
        }
    }

    override fun showErrorMessage(message: String) {
        Utility.dismissDialog()
        Utility.showMessage(container, message)
    }

    override fun updateSuccessful() {
        Utility.dismissDialog()
        Utility.showMessage(container, "Update Successfully")
        activity!!.supportFragmentManager.popBackStack()
    }

    override fun updateError(message: String) {
        Utility.dismissDialog()
        Utility.showMessage(container, message)
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

}