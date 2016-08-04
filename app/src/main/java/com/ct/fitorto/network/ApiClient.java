package com.ct.fitorto.network;

import com.ct.fitorto.model.JsonResponseCategory;
import com.ct.fitorto.model.JsonResponseUser;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


/**
 * Created by Ashwini on 5/25/2016.
 */
public interface ApiClient {
    String BASE_URL = "http://52.40.44.62/FitortoServices/";

    @FormUrlEncoded
    @POST("registerUser.php")
    Call<JsonResponseUser> operation(@Field("userName") String name, @Field("emailID") String email,
                                     @Field("password") String pass, @Field("phoneNo") String phone);

    @FormUrlEncoded
    @POST("loginUser.php")
    Call<JsonResponseUser> operation(@Field("emailID") String email,
                                     @Field("password") String pass);

    @FormUrlEncoded
    @POST("getCategories.php")
    Call<JsonResponseCategory> category(@Field("city") String city

    );
}
