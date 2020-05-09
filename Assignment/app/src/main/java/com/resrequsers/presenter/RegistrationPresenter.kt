package com.resrequsers.presenter

import android.text.TextUtils
import android.util.Patterns
import com.resrequsers.service.ServiceBuilder
import com.resrequsers.models.register_models.RegistrationRequest
import com.resrequsers.models.register_models.RegistrationResponse
import com.resrequsers.views.RegistrationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RegistrationPresenter @Inject constructor() {

    var registrationView: RegistrationView? = null

    fun checkInputs(
        first_name: String,
        last_name: String,
        email: String,
        password: String,
        re_password: String
    ) {
        when {
            TextUtils.isEmpty(first_name) -> {
                registrationView?.showErrorMessage("Please enter first name")
            }
            TextUtils.isEmpty(last_name) -> {
                registrationView?.showErrorMessage("Please enter last name")
            }
            TextUtils.isEmpty(email) -> {
                registrationView?.showErrorMessage("Please enter email")
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                registrationView?.showErrorMessage("Please enter the correct email")
            }
            TextUtils.isEmpty(password) -> {
                registrationView?.showErrorMessage("Please enter password")
            }
            TextUtils.isEmpty(re_password) -> {
                registrationView?.showErrorMessage("Please enter re password")
            }
            !TextUtils.equals(password, re_password) -> {
                registrationView?.showErrorMessage("Password doesn't match")
            }
            else -> {
                callRegisterApi(first_name, last_name, email, password)
            }
        }
    }

    private fun callRegisterApi(
        first_name: String,
        last_name: String,
        email: String,
        password: String
    ) {
        val call =
            ServiceBuilder.getRetrofit()
                .registerRequest(RegistrationRequest(first_name, last_name, email, password))

        call.enqueue(object : Callback<RegistrationResponse> {
            override fun onFailure(call: Call<RegistrationResponse>, t: Throwable) {
                registrationView?.registrationError(t.message.toString())
            }

            override fun onResponse(
                call: Call<RegistrationResponse>,
                response: Response<RegistrationResponse>
            ) {
                when {
                    response.isSuccessful -> {
                        registrationView?.registrationSuccessful()
                    }
                    else -> {
                        registrationView?.registrationError("Something went wrong")
                    }
                }
            }

        })
    }
}