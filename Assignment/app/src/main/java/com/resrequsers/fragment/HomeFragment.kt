package com.resrequsers.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.resrequsers.dependency.module.ApplicationModule
import com.resrequsers.R
import com.resrequsers.common_classes.Utility
import com.resrequsers.dependency.components.DaggerApplicationComponent
import com.resrequsers.models.users_models.UserResponse
import com.resrequsers.presenter.HomePresenter
import com.resrequsers.views.HomeView
import kotlinx.android.synthetic.main.dialog_delete.*
import kotlinx.android.synthetic.main.dialog_search.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.search
import kotlinx.android.synthetic.main.item_user.*
import javax.inject.Inject

class HomeFragment : BaseFragment(), HomeView {

    @Inject
    lateinit var homePresenter: HomePresenter

    private var dialog: Dialog? = null
    private var confirmDialog: Dialog? = null

    /*
    * this will return the object of HomeFragment
    */
    companion object {
        fun getCallingFragment(): Fragment {
            return HomeFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialiseDependency()
        homePresenter.homeView = this
        create_user.setOnClickListener {
            navigator.loadRegisterFragment(activity!!)
        }
        logout.setOnClickListener {
            navigator.loadLoginFragment(activity!!)
        }
        search.setOnClickListener {
            showSearchDialog()
        }
        list_all_users.setOnClickListener {
            navigator.loadUserListFragment(activity!!)
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

    /*
  * This method will show the search user dialog
  */
    private fun showSearchDialog() {
        dialog =
            Dialog(requireContext(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)

        val lp = dialog!!.window?.attributes
        lp?.dimAmount = 0.5f

        dialog!!.window?.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        dialog!!.setContentView(R.layout.dialog_search)

        dialog!!.item_container.visibility = View.GONE
        dialog!!.search.setOnClickListener {
            Utility.hideKeyboard(dialog!!.search)
            Utility.showProgressDialog(requireContext())
            homePresenter.checkId(dialog!!.search_text.text.toString())
        }
        dialog!!.close.setOnClickListener {
            dialog!!.dismiss()
        }
        dialog!!.show()
    }

    override fun showErrorMessage(message: String) {
        Utility.dismissDialog()
        Utility.showMessage(container, message)
    }


    override fun searchSuccessful(userResponse: UserResponse) {
        Utility.dismissDialog()
        dialog!!.item_container.visibility = View.VISIBLE
        val userName = "${userResponse.data.first_name} ${userResponse.data.last_name}"
        dialog!!.user_name.text = userName
        dialog!!.user_email.text = userResponse.data.email
        Utility.setImage(requireContext(), userResponse.data.avatar, dialog!!.user_image)

        dialog!!.delete_user.setOnClickListener {
            showDeleteConfirmationDialog(userResponse.data.id)
        }
        dialog!!.edit_user.setOnClickListener {
            navigator.loadUpdateFragment(activity!!, userResponse.data)
            dialog!!.dismiss()
        }
    }

    /*
    * This method is will display the confirmation dialog before deleting the user
    */
    private fun showDeleteConfirmationDialog(id: Int) {
        confirmDialog =
            Dialog(requireContext(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen)
        confirmDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)

        val lp = confirmDialog!!.window?.attributes
        lp?.dimAmount = 0.5f

        confirmDialog!!.window?.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        confirmDialog!!.setContentView(R.layout.dialog_delete)

        confirmDialog!!.no.setOnClickListener {
            confirmDialog!!.dismiss()
        }

        confirmDialog!!.yes.setOnClickListener {
            Utility.showProgressDialog(requireContext())
            homePresenter.callDeleteUser(id)
        }
        confirmDialog!!.show()
    }

    override fun userError(message: String) {
        Utility.dismissDialog()
        Utility.showMessage(container, message)
        dialog!!.item_container.visibility = View.GONE
    }

    override fun deleteSuccessful() {
        Utility.dismissDialog()
        Utility.showMessage(container, "User has been deleted successfully")

        confirmDialog!!.dismiss()
        dialog!!.dismiss()
    }
}