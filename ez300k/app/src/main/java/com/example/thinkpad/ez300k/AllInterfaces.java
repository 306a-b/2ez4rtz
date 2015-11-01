package com.example.thinkpad.ez300k;

/**
 * Created by ThinkPad on 31.10.2015.
 */
import java.util.List;

import retrofit.Callback;
import retrofit.client.Response;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
public interface AllInterfaces {
    @GET("/bepop")
    void getWhat(Callback<Response> callback);

    @GET("/token")
    void getToken(Callback<Response> callback);

    @GET("/")
    void getGroupInfo(Callback<Response> callback);

    @POST("/")
    void postSubscribe(@Body IntToSend groupId);

    @POST("/")
    void postBePop(@Body IntToSend songId);

}
