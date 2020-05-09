package com.resrequsers.presenter

import com.resrequsers.service.ServiceBuilder
import com.resrequsers.models.users_models.Data
import com.resrequsers.models.users_models.UsersResponse
import com.resrequsers.views.UserListView
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class UserListPresenter @Inject constructor() {
    var userListView: UserListView? = null

    fun callUserListApi(page: Int) {
        val call = ServiceBuilder.getRetrofit().getUsers(page)
        call.enqueue(object : retrofit2.Callback<UsersResponse> {
            override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                userListView?.usersError(t.message.toString())
            }

            override fun onResponse(call: Call<UsersResponse>, response: Response<UsersResponse>) {
                when {
                    response.isSuccessful -> {
                        userListView?.getUsers(response.body()!!)
                    }
                    else -> {
                        userListView?.usersError("No user found")
                    }
                }
            }
        })
    }

    fun callDeleteUser(data: Data) {
        val call = ServiceBuilder.getRetrofit().deleteUser(data.id)
        call.enqueue(object : retrofit2.Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                userListView?.usersError(t.message.toString())
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                when {
                    response.isSuccessful -> {
                        userListView?.deleteSuccessful(data)
                    }
                    else -> {
                        userListView?.usersError("Something went wrong")
                    }
                }
            }
        })
    }
}