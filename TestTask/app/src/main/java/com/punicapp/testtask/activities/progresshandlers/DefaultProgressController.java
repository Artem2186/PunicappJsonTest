package com.punicapp.testtask.activities.progresshandlers;

import android.content.Context;
import android.view.View;

import com.punicapp.testtask.events.progress.RequestStartsEvent;
import com.punicapp.testtask.events.progress.RequestStopsEvent;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class DefaultProgressController implements IProgressController {
    protected Context context;
    protected View progressView;

    public DefaultProgressController(Context context, View progressView) {
        this.context = context;
        this.progressView = progressView;
        initProgress();
    }

    private void initProgress() {
        if (progressView != null) {
            progressView.setVisibility(View.GONE);
        }
    }

    @Subscribe
    @Override
    public void onRequestStart(RequestStartsEvent event) {
        if (progressView != null) {
            progressView.setVisibility(View.VISIBLE);
        }
    }


    @Subscribe
    @Override
    public void onRequestEnd(RequestStopsEvent event) {
        if (progressView != null) {
            progressView.setVisibility(View.GONE);
        }
    }

    @Override
    public void register(Bus eventBus) {
        eventBus.register(this);
    }

    @Override
    public void unregister(Bus eventBus) {
        eventBus.unregister(this);
        if (progressView != null) {
            progressView.setVisibility(View.GONE);
        }
    }

}
