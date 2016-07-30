package com.ct.fitorto.network;

import com.ct.fitorto.model.JsonResponseAddFeed;
import com.ct.fitorto.model.JsonResponseCategory;
import com.ct.fitorto.model.JsonResponseFeed;
import com.ct.fitorto.model.JsonResponseFollow;
import com.ct.fitorto.model.JsonResponseFriends;
import com.ct.fitorto.model.JsonResponseFriendsProfile;
import com.ct.fitorto.model.JsonResponseKeywords;
import com.ct.fitorto.model.JsonResponseNotification;
import com.ct.fitorto.model.JsonResponseSearch;
import com.ct.fitorto.model.JsonResponseSearchFriends;
import com.ct.fitorto.model.JsonResponseSocial;
import com.ct.fitorto.model.JsonResponseUpdateProfile;
import com.ct.fitorto.model.JsonResponseUser;
import com.ct.fitorto.model.JsonResponseUserProfile;
import com.ct.fitorto.model.JsonResponseUserUpdate;
import com.ct.fitorto.model.JsonResponselikeshare;
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

            FitortoApiInterface = retrofit.create(FitortoApiInterface.class);
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
        Call<JsonResponseSearch> search1(@Field("gymName") String gymname);

        @POST("getKeywords.php")
        Call<JsonResponseKeywords> getResponseKeywordsCall();

        @FormUrlEncoded
        @POST("loginUser.php")
        Call<JsonResponseUser> login(@Field("emailID") String emailID,
                                     @Field("password") String password,
                                     @Field("androidRegKey") String androidRegKey,
                                     @Field("iosRegKey") String iosRegKey);

        @POST("city.php")
        Call<JsonResponseUser> city();


        @FormUrlEncoded
        @POST("registerUser.php")
        Call<JsonResponseUser> registration(
                @Field("emailID") String emailID,
                @Field("password") String password,
                @Field("userName") String userName,
                @Field("phoneNo") String phoneNo,
                @Field("androidRegKey") String androidRegKey,
                @Field("iosRegKey") String iosRegKey);

        @FormUrlEncoded
        @POST("getFeeds.php")
        Call<JsonResponseFeed> getResponseFedd(@Field("userID") String userlID);

        @FormUrlEncoded
        @POST("socialLogin.php")
        Call<JsonResponseSocial> getResponseSocial(@Field("userName") String userName,
                                                   @Field("emailID") String emailID,
                                                   @Field("phoneNo") String phoneNo,
                                                   @Field("profilePic") String profilePic,
                                                   @Field("gender") String gender,
                                                   @Field("androidRegKey") String androidRegKey,
                                                   @Field("iosRegKey") String iosRegKey);

        @FormUrlEncoded
        @POST("likeShare.php")
        Call<JsonResponselikeshare> getResponselikeshare(@Field("userID") String userID,
                                                         @Field("feedID") String feedID,
                                                         @Field("flag") String flag,
                                                         @Field("isLike") String isLike,
                                                         @Field("isUser") String isUser);


        @Multipart
        @POST("addFeed.php")
        Call<JsonResponseAddFeed> getResponseFeed(@Part("userID") RequestBody userID,
                                                  @Part("feed") RequestBody feed,
                                                  @Part("url") RequestBody url,
                                                  @Part("flag") RequestBody flag,
                                                  @Part("file\"; filename=\"file\" ") RequestBody file);

        @FormUrlEncoded
        @POST("friends.php")
        Call<JsonResponseFriends> getFriendsList(@Field("userID") String userID);

        @FormUrlEncoded
        @POST("searchFriend.php")
        Call<JsonResponseSearchFriends> searchFriends(@Field("userID") String userID,
                                                      @Field("keyWords") String keyWords);

        @FormUrlEncoded
        @POST("follow.php")
        Call<JsonResponseSearchFriends> follow(@Field("userID") String userID,
                                               @Field("friendID") String keyWords,
                                               @Field("flag") String flag);


        @FormUrlEncoded
        @POST("getUserProfile.php")
        Call<JsonResponseUserProfile> getResponseUserprofile(@Field("userID") String userID
        );

        @FormUrlEncoded
        @POST("updateUser.php")
        Call<JsonResponseUserUpdate> getResponseUserUpdate(@Field("userID") String userID,
                                                           @Field("userName") String userName,
                                                           @Field("phoneNo") String phoneNo,
                                                           @Field("dob") String dob,
                                                           @Field("gender") String gender,
                                                           @Field("address") String address,
                                                           @Field("bloodGroup") String bloodGroup,
                                                           @Field("status") String status
        );


        @Multipart
        @POST("updateProfilePic.php")
        Call<JsonResponseUpdateProfile> getResponseUpdateProfile(@Part("userID") RequestBody userID,
                                                                 @Part("file\"; filename=\"file\" ") RequestBody file);

        @FormUrlEncoded
        @POST("getUserFeeds.php")
        Call<JsonResponseFeed> getUserFeed(@Field("userID") String userId);

        @FormUrlEncoded
        @POST("friendProfile.php")
        Call<JsonResponseFriendsProfile> getResponseFriendProfile(@Field("userID") String userID,
                                                                  @Field("friendID") String friendID
        );

        @FormUrlEncoded
        @POST("follow.php")
        Call<JsonResponseFollow> getResponseFollow(@Field("userID") String userID,
                                                   @Field("friendID") String friendID,
                                                   @Field("flag") String flag
        );


        @FormUrlEncoded
        @POST("getNotifications.php")
        Call<JsonResponseNotification> getNotification(@Field("userID") String userID);


    }

    public static RequestBody getStringRequestBody(String s) {
        return RequestBody.create(MediaType.parse(MEDIA_TYPE_STRING), s);
    }
}
