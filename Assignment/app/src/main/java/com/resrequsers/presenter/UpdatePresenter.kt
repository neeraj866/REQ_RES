package com.resrequsers.presenter

import android.text.TextUtils
import android.util.Patterns
import com.resrequsers.service.ServiceBuilder
import com.resrequsers.models.update_models.UpdateRequest
import com.resrequsers.models.update_models.UpdateResponse
import com.resrequsers.views.UpdateView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class UpdatePresenter @Inject constructor() {

    var updateView: UpdateView? = null

    fun checkInputs(
        first_name: String,
        last_name: String,
        email: String
    ) {
        when {
            TextUtils.isEmpty(first_name) -> {
                updateView?.showErrorMessage("Please enter first name")
            }
            TextUtils.isEmpty(last_name) -> {
                updateView?.showErrorMessage("Please enter last name")
            }
            TextUtils.isEmpty(email) -> {
                updateView?.showErrorMessage("Please enter email")
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                updateView?.showErrorMessage("Please enter the correct email")
            }
            else -> {
                callUpdateApi(first_name, last_name, email)
            }
        }
    }

    private fun callUpdateApi(
        first_name: String,
        last_name: String,
        email: String
    ) {
        val call =
            ServiceBuilder.getRetrofit()
                .updateRequest(UpdateRequest(first_name, last_name, email))

        call.enqueue(object : Callback<UpdateResponse> {
            override fun onFailure(call: Call<UpdateResponse>, t: Throwable) {
                updateView?.updateError(t.message.toString())
            }

            override fun onResponse(
                call: Call<UpdateResponse>,
                response: Response<UpdateResponse>
            ) {
                when {
                    response.isSuccessful -> {
                        updateView?.updateSuccessful()
                    }
                    else -> {
                        updateView?.updateError("Something went wrong")
                    }
                }
            }

        })
    }
}