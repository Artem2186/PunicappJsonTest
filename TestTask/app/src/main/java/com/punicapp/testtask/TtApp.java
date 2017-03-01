package com.punicapp.testtask;

import android.app.Application;
import android.content.Context;

import com.punicapp.testtask.api.ServicesManager;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import java.security.SecureRandom;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class TtApp extends Application {

    private static Context context;
    private static Bus eventBus;
    private static ServicesManager servicesManager;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
        eventBus = new Bus(ThreadEnforcer.MAIN);
        servicesManager = new ServicesManager();
        servicesManager.init(eventBus, context);
        realmInit();
    }

    public static Context getContext() {
        return context;
    }

    public static Bus getEventBus() {
        return eventBus;
    }

    public void realmInit() {
        Realm.init(this);

        byte[] key = new byte[64];
        new SecureRandom().nextBytes(key);

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
