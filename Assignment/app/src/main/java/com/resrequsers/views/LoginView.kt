package com.resrequsers.views

import com.resrequsers.models.login_models.LoginResponse

interface LoginView {
    fun showErrorMessage(message: String)
    fun loginSuccessful(loginResponse: LoginResponse)
    fun loginError(message: String)
}