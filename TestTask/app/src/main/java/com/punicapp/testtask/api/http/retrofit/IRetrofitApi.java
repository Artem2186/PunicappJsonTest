package com.punicapp.testtask.api.http.retrofit;

import com.punicapp.testtask.model.api.ApiModel;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

public interface IRetrofitApi {

    @GET("/androids")
    Observable<List<ApiModel>> loadApiData();
}
