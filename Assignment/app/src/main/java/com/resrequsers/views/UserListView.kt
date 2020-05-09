package com.resrequsers.views

import com.resrequsers.models.users_models.Data
import com.resrequsers.models.users_models.UsersResponse

interface UserListView {
    fun showErrorMessage(message: String)
    fun deleteSuccessful(data: Data)
    fun usersError(message: String)
    fun getUsers(usersResponse: UsersResponse)
}