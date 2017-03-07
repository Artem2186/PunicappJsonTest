package com.punicapp.testtask.utils;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.Window;
import android.view.WindowManager;

import com.punicapp.testtask.R;
import com.punicapp.testtask.TtApp;

public class AppThemeUtils {
    private static AppThemes sTheme = AppThemes.Default;

    public static void changeToTheme(Activity activity, AppThemes theme) {
        sTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
        activity.overridePendingTransition(-1, -1);
    }

    public static void onActivityCreateSetTheme(Activity activity) {
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        switch (sTheme) {
            case Light:
                activity.setTheme(R.style.AppThemeLight);
                colorStatusBar(window, activity, R.color.mainLight);
                break;
            case Dark:
                activity.setTheme(R.style.AppTheme);
                colorStatusBar(window, activity, R.color.mainDark);
                break;
            case Red:
                activity.setTheme(R.style.AppThemeRed);
                colorStatusBar(window, activity, R.color.mainRedDark);
                break;
            default:
                activity.setTheme(R.style.AppTheme);
                colorStatusBar(window, activity, R.color.mainDark);
                break;
        }

    }

    private static void colorStatusBar(Window window, Context context, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(context, color));
        }
    }

    public static int getSnackColor(Context uiContext, int color) {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = uiContext.getTheme();
        theme.resolveAttribute(color, typedValue, true);
        return typedValue.data;
    }

    public enum AppThemes {
        Light, Dark, Red, Default;
    }
}
