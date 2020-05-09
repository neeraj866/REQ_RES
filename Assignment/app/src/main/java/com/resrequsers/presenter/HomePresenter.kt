package com.resrequsers.presenter

import android.text.TextUtils
import com.resrequsers.service.ServiceBuilder
import com.resrequsers.models.users_models.UserResponse
import com.resrequsers.views.HomeView
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class HomePresenter @Inject constructor() {
    var homeView: HomeView? = null

    fun checkId(id: String) {
        when {
            TextUtils.isEmpty(id) -> {
                homeView?.showErrorMessage("Please enter the user id first")
            }
            else -> {
                callSearchUserApi(id.toInt())
            }
        }
    }

    private fun callSearchUserApi(id: Int) {
        val call = ServiceBuilder.getRetrofit().getUser(id)
        call.enqueue(object : retrofit2.Callback<UserResponse> {
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                homeView?.userError(t.message.toString())
            }

            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                when {
                    response.isSuccessful -> {
                        homeView?.searchSuccessful(response.body()!!)
                    }
                    else -> {
                        homeView?.userError("No user found")
                    }
                }
            }
        })
    }

    fun callDeleteUser(id: Int) {
        val call = ServiceBuilder.getRetrofit().deleteUser(id)
        call.enqueue(object : retrofit2.Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                homeView?.userError(t.message.toString())
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                when {
                    response.isSuccessful -> {
                        homeView?.deleteSuccessful()
                    }
                    else -> {
                        homeView?.userError("Something went wrong")
                    }
                }
            }
        })
    }
}