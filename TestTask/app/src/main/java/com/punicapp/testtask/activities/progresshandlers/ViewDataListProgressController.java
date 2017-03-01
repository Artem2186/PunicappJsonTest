package com.punicapp.testtask.activities.progresshandlers;

import android.support.v4.widget.SwipeRefreshLayout;

import com.punicapp.testtask.events.progress.RequestStartsEvent;
import com.punicapp.testtask.events.progress.RequestStopsEvent;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;


public class ViewDataListProgressController implements IProgressController {

    protected SwipeRefreshLayout refresher;

    public ViewDataListProgressController(SwipeRefreshLayout refresher) {
        this.refresher = refresher;
    }

    @Override
    @Subscribe
    public void onRequestStart(RequestStartsEvent event) {
        refresher.setRefreshing(true);
    }

    @Override
    @Subscribe
    public void onRequestEnd(RequestStopsEvent event) {
        refresher.setRefreshing(false);
        refresher.setActivated(true);
    }

    @Override
    public void register(Bus eventBus) {
        eventBus.register(this);
    }

    @Override
    public void unregister(Bus eventBus) {
        eventBus.unregister(this);
    }
}
