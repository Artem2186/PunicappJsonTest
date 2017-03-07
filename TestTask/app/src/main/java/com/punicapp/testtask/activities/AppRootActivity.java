package com.punicapp.testtask.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.punicapp.testtask.R;
import com.punicapp.testtask.utils.AppThemeUtils;
import com.punicapp.testtask.utils.NotificationUtils;


public abstract class AppRootActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppThemeUtils.onActivityCreateSetTheme(this);
        setContentView(R.layout.app_view_root_ac);
        onCreateMainContent(savedInstanceState);
    }

    protected void onCreateMainContent(Bundle savedInstanceState) {
        ViewGroup rootContainer = (ViewGroup) findViewById(R.id.root_layout);
        LayoutInflater inflater = LayoutInflater.from(this);
        onCreateScreenContent(inflater, rootContainer, savedInstanceState);
    }

    protected abstract void onCreateScreenContent(LayoutInflater inflater,ViewGroup rootContainer, Bundle savedInstanceState);

    protected void showError(String stubMessage) {
        View content = findViewById(android.R.id.content);
        Snackbar snackbar = NotificationUtils.showPopupNotification(this, content, stubMessage, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}