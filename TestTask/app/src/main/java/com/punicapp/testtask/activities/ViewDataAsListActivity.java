package com.punicapp.testtask.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.punicapp.testtask.R;
import com.punicapp.testtask.TtApp;
import com.punicapp.testtask.api.events.OnErrorEvent;
import com.punicapp.testtask.api.events.OnRequestEvent;
import com.punicapp.testtask.api.events.OnRespondEvent;
import com.punicapp.testtask.fragments.ViewItemsListFragment;
import com.punicapp.testtask.utils.AppThemeUtils;
import com.squareup.otto.Subscribe;

public class ViewDataAsListActivity extends AppRootActivity {

    private View progressView;
    public static final String TAG = "content:fragment";

    @Override
    protected void onCreateScreenContent(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflater.inflate(R.layout.view_data_as_list_ac,container);

        progressView = findViewById(R.id.progressView);
        progressView.setVisibility(View.GONE);

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentByTag(TAG) == null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Fragment contentFragment = ViewItemsListFragment.getInstance();
            fragmentTransaction.add(R.id.fragmentPlace, contentFragment, TAG);
            fragmentTransaction.commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        TtApp.getEventBus().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        TtApp.getEventBus().unregister(this);
    }

    private void requestLoading() {
        if (progressView.getVisibility() == View.VISIBLE) {
            return;
        }
        TtApp.getEventBus().post(new OnRequestEvent());
        progressView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        if (provideOptionsList() != -1) {
            menuInflater.inflate(provideOptionsList(), menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_apptheme_light:
                AppThemeUtils.changeToTheme(this, AppThemeUtils.AppThemes.Light);
                return true;
            case R.id.action_apptheme_dark:
                AppThemeUtils.changeToTheme(this, AppThemeUtils.AppThemes.Dark);
                return true;
            case R.id.action_apptheme_red:
                AppThemeUtils.changeToTheme(this, AppThemeUtils.AppThemes.Red);
                return true;
            case R.id.action_update:
                requestLoading();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected int provideOptionsList() {
        return R.menu.theme_menu;
    }


    @Subscribe
    public void onDataReceived(OnRespondEvent event) {
        progressView.setVisibility(View.GONE);
    }

    @Subscribe
    public void onError(OnErrorEvent event) {
        progressView.setVisibility(View.GONE);
        String stubMessage = TtApp.getContext().getResources().getString(R.string.stubErrorMessage);
        showError(stubMessage);
    }

}
