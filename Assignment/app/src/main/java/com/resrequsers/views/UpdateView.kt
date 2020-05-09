package com.resrequsers.views

interface UpdateView {
    fun showErrorMessage(message: String)
    fun updateSuccessful()
    fun updateError(message: String)
}