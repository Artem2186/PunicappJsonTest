package com.punicapp.testtask.api;

import android.content.Context;

import com.punicapp.testtask.api.http.ApiDataService;
import com.punicapp.testtask.api.http.HttpDataService;
import com.squareup.otto.Bus;

public class ServicesManager {

    protected ApiDataService dataService;

    public void init(Bus eventBus, Context context) {
        dataService = new HttpDataService();
        dataService.init(eventBus, context);
    }
}
