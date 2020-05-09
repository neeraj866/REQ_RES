package com.resrequsers.presenter

import com.resrequsers.models.login_models.LoginRequest
import com.resrequsers.models.login_models.LoginResponse
import android.text.TextUtils
import android.util.Patterns
import com.resrequsers.service.ServiceBuilder
import com.resrequsers.views.LoginView
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class LoginPresenter @Inject constructor() {
    var loginView: LoginView? = null

    fun checkInputs(email: String, password: String) {
        when {
            TextUtils.isEmpty(email) -> {
                loginView?.showErrorMessage("Please enter email")
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                loginView?.showErrorMessage("Please enter the correct email")
            }
            TextUtils.isEmpty(password) -> {
                loginView?.showErrorMessage("Please enter password")
            }
            else -> {
                callLoginApi(email, password)
            }
        }
    }

    private fun callLoginApi(email: String, password: String) {
        val call = ServiceBuilder.getRetrofit().loginRequest(LoginRequest(email, password))
        call.enqueue(object : retrofit2.Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                loginView?.loginError(t.message.toString())
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                when {
                    response.isSuccessful -> {
                        loginView?.loginSuccessful(response.body()!!)
                    }
                    else -> {
                        loginView?.showErrorMessage("Wrong email or password!")

                    }
                }
            }

        })
    }
}