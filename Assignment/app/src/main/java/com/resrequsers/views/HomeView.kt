package com.resrequsers.views

import com.resrequsers.models.users_models.UserResponse

interface HomeView {
    fun showErrorMessage(message: String)
    fun searchSuccessful(userResponse: UserResponse)
    fun userError(message: String)
    fun deleteSuccessful()
}