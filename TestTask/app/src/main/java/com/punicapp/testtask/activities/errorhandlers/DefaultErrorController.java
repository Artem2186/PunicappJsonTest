package com.punicapp.testtask.activities.errorhandlers;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.punicapp.testtask.R;
import com.punicapp.testtask.events.error.ApiErrorEvent;
import com.punicapp.testtask.utils.NotificationUtils;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class DefaultErrorController implements IUIErrorController {

    protected Context context;
    protected View rootView;

    public DefaultErrorController(Context context, View rootView) {
        this.context = context;
        this.rootView = rootView;
    }

    @Override
    public void register(Bus bus) {
        bus.register(this);
    }

    @Override
    public void unregister(Bus eventBus) {
        eventBus.unregister(this);
    }


    @Override
    @Subscribe
    public void onApiError(ApiErrorEvent event) {
        String stubMessage = context.getResources().getString(R.string.stubErrorMessage);
        Snackbar snackbar = NotificationUtils.showPopupNotification(((Activity) context).findViewById(android.R.id.content), stubMessage, Snackbar.LENGTH_LONG, context);
        snackbar.show();
    }
}
