package com.workshop.retrofitexample.data.network;

import com.workshop.retrofitexample.data.model.PhotoModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WorkshopApi {

    @GET("/photos")
    Call<List<PhotoModel>> getAllPhotos();

}
