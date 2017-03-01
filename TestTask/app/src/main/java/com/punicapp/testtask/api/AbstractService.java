package com.punicapp.testtask.api;

import android.content.Context;

import com.squareup.otto.Bus;

public abstract class AbstractService {
    protected Bus eventBus;
    protected Context context;

    public void init(Bus eventBus, Context c) {
        this.eventBus = eventBus;
        this.context = c;
        eventBus.register(this);
    }
}
