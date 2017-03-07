package com.punicapp.testtask.utils;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import com.punicapp.testtask.R;

public class NotificationUtils {
    public static Snackbar showPopupNotification(Context uiContext, View rootView, String msg, int showType) {
        Snackbar snackbar = Snackbar.make(rootView, msg, showType);
        snackbar.getView().setBackgroundColor(AppThemeUtils.getSnackColor(uiContext,R.attr.snackColor));
        View view = snackbar.getView();
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        snackbar.setActionTextColor(Color.DKGRAY);
        return snackbar;
    }
}
