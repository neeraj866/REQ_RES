package com.resrequsers.service

import com.resrequsers.models.login_models.LoginRequest
import com.resrequsers.models.login_models.LoginResponse
import com.resrequsers.models.register_models.RegistrationRequest
import com.resrequsers.models.register_models.RegistrationResponse
import com.resrequsers.models.update_models.UpdateRequest
import com.resrequsers.models.update_models.UpdateResponse
import com.resrequsers.models.users_models.UserResponse
import com.resrequsers.models.users_models.UsersResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("users")
    fun getUsers(@Query("page") page: Int): Call<UsersResponse>

    @GET("users/{id}")
    fun getUser(@Path("id") id: Int): Call<UserResponse>

    @DELETE("users/{id}")
    fun deleteUser(@Path("id") id: Int): Call<Void>

    @POST("login")
    fun loginRequest(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("register")
    fun registerRequest(@Body registrationRequest: RegistrationRequest): Call<RegistrationResponse>

    @PATCH("register")
    fun updateRequest(@Body updateRequest: UpdateRequest): Call<UpdateResponse>
}