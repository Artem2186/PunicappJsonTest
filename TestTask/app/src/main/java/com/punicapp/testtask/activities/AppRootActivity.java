package com.punicapp.testtask.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.punicapp.testtask.R;
import com.punicapp.testtask.TtApp;
import com.punicapp.testtask.activities.errorhandlers.DefaultErrorController;
import com.punicapp.testtask.activities.errorhandlers.IUIErrorController;
import com.punicapp.testtask.activities.progresshandlers.DefaultProgressController;
import com.punicapp.testtask.activities.progresshandlers.IProgressController;
import com.punicapp.testtask.utils.AppThemeUtils;
import com.punicapp.testtask.utils.IBusRegistration;
import com.squareup.otto.Bus;


public abstract class AppRootActivity extends AppCompatActivity implements IBusRegistration {
    protected IUIErrorController errorHandler;
    protected IProgressController progressHandler;

    protected Bus eventBus;
    protected LayoutInflater inflater;
    protected View progressContainer;
    protected ViewGroup rootContainer;
    protected Toolbar toolbarView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppThemeUtils.onActivityCreateSetTheme(this);
        inflater = LayoutInflater.from(this);
        eventBus = TtApp.getEventBus();
        setContentView(provideRootLayout());
        onCreateToolbar();
        onCreateMainContent(savedInstanceState);
    }

    protected void onCreateMainContent(Bundle savedInstanceState) {
        progressContainer = findViewById(R.id.progress_layout);
        rootContainer = (ViewGroup) findViewById(R.id.root_layout);

        inflater.inflate(provideScreenLayout(), rootContainer);
        onCreateScreenContent(savedInstanceState);

        progressHandler = buildProgressController();
        errorHandler = buildErrorController();
    }

    protected void onCreateToolbar() {
        toolbarView = (Toolbar) findViewById(R.id.app_tb);
        setSupportActionBar(toolbarView);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);

        actionBar.setDisplayHomeAsUpEnabled(isUpButtonVisible());
        actionBar.setHomeButtonEnabled(isUpButtonVisible());
    }

    protected boolean isUpButtonVisible() {
        return false;
    }

    protected abstract int provideScreenLayout();

    protected abstract void onCreateScreenContent(Bundle savedInstanceState);

    protected IProgressController buildProgressController() {
        return new DefaultProgressController(this, progressContainer);
    }

    protected IUIErrorController buildErrorController() {
        return new DefaultErrorController(this, rootContainer);
    }


    protected int provideRootLayout() {
        return R.layout.app_view_root_ac;
    }

    @Override
    protected void onResume() {
        super.onResume();
        register(eventBus);

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregister(eventBus);

    }

    public void register(Bus eventBus) {
        eventBus.register(this);
        if (errorHandler != null)
            errorHandler.register(eventBus);
        if (progressHandler != null) {
            progressHandler.register(eventBus);
        }
    }


    public void unregister(Bus eventBus) {
        eventBus.unregister(this);
        if (errorHandler != null)
            errorHandler.unregister(eventBus);
        if (progressHandler != null) {
            progressHandler.unregister(eventBus);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                handleUpPressed();
                return true;
        }
        return false;
    }

    protected void handleUpPressed() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(-1, -1);
    }
}