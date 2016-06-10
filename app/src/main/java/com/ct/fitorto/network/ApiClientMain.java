package com.ct.fitorto.network;

import com.ct.fitorto.model.JsonResponseAddFeed;
import com.ct.fitorto.model.JsonResponseCategory;
import com.ct.fitorto.model.JsonResponseFeed;
import com.ct.fitorto.model.JsonResponseKeywords;
import com.ct.fitorto.model.JsonResponseSearch;
import com.ct.fitorto.model.JsonResponseSocial;
import com.ct.fitorto.model.JsonResponseUser;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Ashwini on 5/25/2016.
 */
public class ApiClientMain {
    private static FitortoApiInterface FitortoApiInterface;  // interface for Retrofit api
    public static final String URL = "http://52.40.44.62/FitortoServices/";
    public static final String MEDIA_TYPE_STRING = "text/plain";
    public static final String MEDIA_TYPE_IMAGE = "image/*";

    public static FitortoApiInterface getApiClient() {

        if (FitortoApiInterface == null) {

            OkHttpClient client = new OkHttpClient();
            client.setConnectTimeout(20, TimeUnit.SECONDS);
            client.setReadTimeout(15, TimeUnit.SECONDS);
            client.setWriteTimeout(15, TimeUnit.SECONDS);

                    /*RequestInterceptor requestInterceptor = new RequestInterceptor() {
                        @Override
                        public void intercept(RequestFacade request) {
                            // Log.e("Retrofit Request Body", request.toString());
                        }
                    };
        */
            //            GsonConverter gsonConverter = new GsonConverter(new Gson());

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            FitortoApiInterface= retrofit.create(FitortoApiInterface.class);
        }
        return FitortoApiInterface;
    }


    public interface FitortoApiInterface {


        @FormUrlEncoded
        @POST("getCategories.php")
        Call<JsonResponseCategory> category(@Field("city") String city);

        @FormUrlEncoded
        @POST("getKeywords.php")
        Call<JsonResponseSearch> keywords();

        @FormUrlEncoded
        @POST("search.php")
        Call<JsonResponseSearch> search(@Field("userID") String userID,
                                        @Field("area") String area,
                                        @Field("city") String city,
                                        @Field("keywords") String keywords);
        @FormUrlEncoded
        @POST("search.php")
        Call<JsonResponseSearch> search1(@Field("gymName")String gymname);

        @POST("getKeywords.php")
        Call<JsonResponseKeywords> getResponseKeywordsCall() ;

        @FormUrlEncoded
        @POST("loginUser.php")
        Call<JsonResponseUser> city(@Field("emailID") String emailID,
                                    @Field("password") String password
        );
        @FormUrlEncoded
        @POST("getFeeds.php")
        Call<JsonResponseFeed> getResponseFedd(@Field("userID") String userlID) ;

        @FormUrlEncoded
        @POST("socialLogin.php")
        Call<JsonResponseSocial> getResponseSocial(@Field("userName") String userName,
                                                   @Field("emailID") String emailID,
                                                   @Field("phoneNo") String phoneNo,
                                                   @Field("profilePic") String profilePic,
                                                   @Field("gender") String gender);




        @Multipart
        @POST("addFeed.php")
        Call<JsonResponseAddFeed> getResponseFeed(@Part("userID") RequestBody  userID,
                                                  @Part("feed") RequestBody feed,
                                                  @Part("url") RequestBody url,
                                                  @Part("flag") RequestBody flag,
                                                  @Part("file\"; filename=\"file\" ") RequestBody file) ;
              /*  @FormUrlEncoded
                @POST("addFeed.php")
                Call<JsonResponseAddFeed> getResponseFeed( @Field("userID") String userID,
                                                           @Field("feed") String feed,
                                                           @Field("url") String url,
                                                           @Field("flag") String flag)*/
        ;

                /*@POST("loginUser.php")
                Call<JsonResponseUser> city();*/
    }

    public static RequestBody getStringRequestBody(String s) {
        return RequestBody.create(MediaType.parse(MEDIA_TYPE_STRING), s);
    }
}
