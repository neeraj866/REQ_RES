package com.resrequsers.common_classes

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.resrequsers.custom_views.CustomProgressDialog
import javax.inject.Singleton


object Utility {

    var progressDialog: CustomProgressDialog? = null

    @Singleton
    fun hideKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    @Singleton
    fun setImage(context: Context, url: String, imageView: ImageView) {
        Glide.with(context).load(url).apply(RequestOptions().circleCrop()).into(imageView)
    }

    @Singleton
    fun showMessage(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }

    @Singleton
    fun showProgressDialog(context: Context) {
        when (progressDialog) {
            null -> {
                progressDialog = CustomProgressDialog()
            }
        }
        progressDialog?.show(context)
    }

    @Singleton
    fun dismissDialog() {
        when {
            progressDialog != null -> {
                progressDialog?.dismissDialog()
            }
        }
    }
}