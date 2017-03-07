package com.punicapp.testtask.api;

import android.support.annotation.NonNull;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.google.gson.GsonBuilder;
import com.punicapp.testtask.BuildConfig;
import com.punicapp.testtask.api.events.OnErrorEvent;
import com.punicapp.testtask.api.events.OnRequestEvent;
import com.punicapp.testtask.api.events.OnRespondEvent;
import com.punicapp.testtask.api.retrofit.IRetrofitApi;
import com.punicapp.testtask.model.DataModel;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiDataProvider {

    public static final String HTTP_DELIM = "://";

    private IRetrofitApi service;
    private Bus eventBus;

    public void init(Bus eventBus) {
        Retrofit adaptet = buildRestAdapter();
        this.eventBus = eventBus;
        service = adaptet.create(IRetrofitApi.class);
        eventBus.register(this);
    }

    protected Retrofit buildRestAdapter() {
        OkHttpClient client = initClient();

        Retrofit adapter = new Retrofit
                .Builder()
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()))
                .baseUrl(composeUrl())
                .client(client)
                .build();
        return adapter;

    }

    @NonNull
    private OkHttpClient initClient() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        initHeadersInterception(builder);
        return builder.build();
    }

    protected void initHeadersInterception(OkHttpClient.Builder builder) {
        HttpHeaderInterceptor interceptor = new HttpHeaderInterceptor();
        builder.addInterceptor(interceptor);
    }

    protected String composeUrl() {
        StringBuilder sb = new StringBuilder();
        sb.append(BuildConfig.API_PROTOCOL);
        sb.append(HTTP_DELIM);
        sb.append(BuildConfig.API_HOST);
        return sb.toString();
    }

    @Subscribe
    public void onRequestData(final OnRequestEvent event) {
        Call<List<DataModel>> callable = service.loadApiData();
        callable.enqueue(new Callback<List<DataModel>>() {
            @Override
            public void onResponse(Call<List<DataModel>> call, Response<List<DataModel>> response) {
                if (response.isSuccessful()) {
                    List<DataModel> data = response.body();
                    saveModels(data);
                    eventBus.post(new OnRespondEvent());
                } else {
                    eventBus.post(new OnErrorEvent());
                }
            }

            @Override
            public void onFailure(Call<List<DataModel>> call, Throwable t) {
                eventBus.post(new OnErrorEvent());
            }
        });
    }

    private void saveModels(List<DataModel> dataModel) {
        ActiveAndroid.beginTransaction();
        try {
            new Delete().from(DataModel.class).execute();
            for (DataModel model : dataModel) {
                model.save();
            }
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
    }

}
