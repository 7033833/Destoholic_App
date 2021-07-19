package com.example.destoholicstudent.retrofit;


import androidx.annotation.NonNull;

import com.example.destoholicstudent.model.LoginResponse;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIInterface {

    @POST("destohol_app/login.php")
    Call<LoginResponse> loginUser(@Body RequestBody login);

    @POST("destohol_app/register.php")
    Call<LoginResponse> createUser(@Body RequestBody login);

    @NonNull
    @GET("destohol_app/files/CodiceFiscalExampleForm.pdf")
    Call<ResponseBody> getCodiceFiscale();

    @NonNull
    @GET("destohol_app/files/FiscalCodeForm.pdf")
    Call<ResponseBody> getCodiceFiscale1();

    @NonNull
    @GET("destohol_app/files/ISO_APPLY_PERMIT_STAY_Permesso_Di_Soggiorno.pdf")
    Call<ResponseBody> getCodiceFiscale2();
}