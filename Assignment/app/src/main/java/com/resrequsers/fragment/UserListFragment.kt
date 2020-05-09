package com.resrequsers.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.resrequsers.dependency.module.ApplicationModule
import com.resrequsers.R
import com.resrequsers.common_classes.Utility
import com.resrequsers.adaptor.UserListAdapter
import com.resrequsers.dependency.components.DaggerApplicationComponent
import com.resrequsers.models.users_models.Data
import com.resrequsers.models.users_models.UsersResponse
import com.resrequsers.presenter.UserListPresenter
import com.resrequsers.views.UserListView
import kotlinx.android.synthetic.main.dialog_delete.*
import kotlinx.android.synthetic.main.fragment_user_list.*
import javax.inject.Inject

class UserListFragment : BaseFragment(), UserListView, UserListAdapter.OnItemSelectListener {

    @Inject
    lateinit var userListPresenter: UserListPresenter

    /*
    * this will return the object of UserListFragment
    */
    companion object {
        fun getCallingFragment(): Fragment {
            return UserListFragment()
        }
    }

    var pageCount = 1
    var layoutManager: LinearLayoutManager? = null
    var userList: MutableList<Data> = ArrayList()

    var userListAdapter: UserListAdapter? = null
    var totalPage = 0
    private var confirmDialog: Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialiseDependency()
        setUserListAdapter()

        userListPresenter.userListView = this
        Utility.showProgressDialog(requireContext())
        userListPresenter.callUserListApi(pageCount)

        load_more.setOnClickListener {
            when {
                pageCount < totalPage -> {
                    Utility.showProgressDialog(requireContext())
                    pageCount += 1
                    userListPresenter.callUserListApi(pageCount)
                }
            }
        }
    }

    /*
    * This method will initialize the UserListAdapter and set adapter to the user_list recyclerView
    */
    private fun setUserListAdapter() {
        userListAdapter = UserListAdapter(activity)
        layoutManager = LinearLayoutManager(activity!!)
        user_list.layoutManager = layoutManager
        userListAdapter?.userItems = userList
        user_list.adapter = userListAdapter
        userListAdapter?.onItemSelectListener = this
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

    override fun deleteSuccessful(data: Data) {
        Utility.dismissDialog()
        Utility.showMessage(container, "User has been deleted successfully")
        confirmDialog!!.dismiss()
        userList.remove(data)
        userListAdapter?.notifyDataSetChanged()
    }

    override fun usersError(message: String) {
        Utility.dismissDialog()
        Utility.showMessage(container, message)
    }

    override fun getUsers(usersResponse: UsersResponse) {
        Utility.dismissDialog()
        totalPage = usersResponse.total_pages
        when (pageCount) {
            totalPage -> {
                load_more.visibility = View.GONE
            }
        }
        userList.addAll(usersResponse.data)
        userListAdapter!!.notifyDataSetChanged()
    }

    override fun itemSelectedForDelete(data: Data) {
        showDeleteConfirmationDialog(data)
    }

    /*
    * This method is will display the confirmation dialog before deleting the user
    */
    private fun showDeleteConfirmationDialog(data: Data) {
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
            userListPresenter.callDeleteUser(data)
        }
        confirmDialog!!.show()
    }


    override fun itemSelectedForUpdate(data: Data) {
        navigator.loadUpdateFragment(activity!!, data)
    }
}