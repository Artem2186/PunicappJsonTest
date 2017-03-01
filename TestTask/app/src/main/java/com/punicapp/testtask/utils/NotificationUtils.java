package com.punicapp.testtask.utils;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import com.punicapp.testtask.R;

/**
 * Utility for notification messages displaying
 */
public class NotificationUtils {
    /**
     * @param rootView view where snackbar popup will be attached
     * @param msg      message to display
     * @param showType constannt from {@link Snackbar}
     */
    public static Snackbar showPopupNotification(View rootView, String msg, int showType, Context context) {
        Snackbar snackbar = Snackbar.make(rootView, msg, showType);
        snackbar.getView().setBackgroundColor(AppThemeUtils.getSnackColor(R.attr.snackColor, context));
        View view = snackbar.getView();
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        snackbar.setActionTextColor(Color.DKGRAY);
        return snackbar;
    }
}
