package com.punicapp.testtask.api.http;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.punicapp.testtask.BuildConfig;
import com.punicapp.testtask.api.AbstractService;
import com.punicapp.testtask.utils.ExecutorProvider;
import com.punicapp.testtask.utils.GsonUtilities;
import com.squareup.otto.Bus;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public abstract class ApiDataService extends AbstractService {

    public static final String HTTP_DELIM = "://";
    protected Retrofit adapter;

    @Override
    public void init(Bus eventBus, Context context) {
        super.init(eventBus, context);
        buildRestAdapter();
    }

    protected void buildRestAdapter() {
        OkHttpClient client = initClient();

        Gson gson = GsonUtilities.getGson();
        adapter = new Retrofit
                .Builder()
                .baseUrl(provideBaseUrl())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

    }

    @NonNull
    private OkHttpClient initClient() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        initHeadersInterception(builder);
        initLogs(builder);
        return builder.build();
    }


    protected void initLogs(OkHttpClient.Builder builder) {
        if (!BuildConfig.DEBUG) {
            return;
        }
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(interceptor);
    }

    protected void initHeadersInterception(OkHttpClient.Builder builder) {
        HttpHeaderInterceptor interceptor = new HttpHeaderInterceptor();
        builder.addInterceptor(interceptor);
    }

    protected String provideBaseUrl() {
        String protocol = BuildConfig.API_PROTOCOL;
        String host = BuildConfig.API_HOST;
        return composeApirUrl(protocol, host);
    }


    protected String composeApirUrl(String serverProtocol, String serverHost) {
        StringBuilder sb = new StringBuilder();
        sb.append(serverProtocol);
        sb.append(HTTP_DELIM);
        sb.append(serverHost);
        return sb.toString();
    }

    protected <T> Observable<T> updateSubscriptionPreffs(Observable<T> observable) {
        observable = observable.subscribeOn(Schedulers.from(ExecutorProvider.defaultHttpExecutor()));
        observable = observable.observeOn(AndroidSchedulers.mainThread());
        return observable;
    }
}
