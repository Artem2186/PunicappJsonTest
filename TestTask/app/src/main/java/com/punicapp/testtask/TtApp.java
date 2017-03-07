package com.punicapp.testtask;

import android.content.Context;

import com.punicapp.testtask.api.ApiDataProvider;
import com.punicapp.testtask.utils.picasso.OkHttp3Downloader;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;
import com.squareup.picasso.Picasso;

public class TtApp extends com.activeandroid.app.Application  {

    private static Context context;
    private static Bus eventBus;
    private static ApiDataProvider dataProvider;
    private static Picasso picasso;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
        eventBus = new Bus(ThreadEnforcer.MAIN);
        dataProvider = new ApiDataProvider();
        dataProvider.init(eventBus);
        initPicasso();
    }

    public static Picasso getPicasso() {
        return picasso;
    }

    public static Bus getEventBus(){
        return eventBus;
    }

    public void initPicasso() {
        Picasso.Builder picassoBuilder = new Picasso.Builder(context);
        picassoBuilder.downloader(new OkHttp3Downloader.Builder(context).build());
        picasso = picassoBuilder.build();
    }

    public static Context getContext() {
        return context;
    }
}
