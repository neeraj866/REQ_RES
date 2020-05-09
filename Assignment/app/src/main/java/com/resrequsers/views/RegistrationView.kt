package com.resrequsers.views

interface RegistrationView {
    fun showErrorMessage(message: String)
    fun registrationSuccessful()
    fun registrationError(message: String)
}