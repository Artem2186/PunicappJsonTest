package com.punicapp.testtask.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;

import android.view.MenuItem;
import android.view.View;

import com.punicapp.testtask.R;
import com.punicapp.testtask.activities.progresshandlers.IProgressController;
import com.punicapp.testtask.activities.progresshandlers.ViewDataListProgressController;
import com.punicapp.testtask.adapters.DataListAdapter;
import com.punicapp.testtask.events.request.OnGetDataRequestEvent;
import com.punicapp.testtask.events.request.OnLoadDataRequestEvent;
import com.punicapp.testtask.events.respond.AfterGetDataRespondEvent;
import com.punicapp.testtask.events.respond.AfterLoadingCompletedEvent;
import com.punicapp.testtask.model.app.InternalModel;
import com.punicapp.testtask.utils.AppThemeUtils;
import com.punicapp.testtask.utils.recycleview.OnItemClickListener;
import com.punicapp.testtask.utils.recycleview.RecyclerItemClickListener;
import com.squareup.otto.Subscribe;

import io.realm.RealmResults;

public class ViewDataAsListActivity extends AppRootActivity {

    private SwipeRefreshLayout refresher;
    private RecyclerView container;

    private SwipeRefreshLayout.OnRefreshListener swipeListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            requestLoading();
        }
    };
    private DataListAdapter adapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected int provideScreenLayout() {
        return R.layout.view_data_as_list_ac;
    }

    @Override
    protected void onCreateScreenContent(Bundle savedInstanceState) {

        refresher = (SwipeRefreshLayout) findViewById(R.id.data_refresher);
        container = (RecyclerView) findViewById(R.id.data_list);

        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        adapter = new DataListAdapter(inflater);
        container.setLayoutManager(layoutManager);
        container.setAdapter(adapter);

        refresher.setOnRefreshListener(swipeListener);
        container.addOnItemTouchListener(new RecyclerItemClickListener(this, container, new ListItemClickListener()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestDataSet();
    }

    private void requestLoading() {
        eventBus.post(new OnLoadDataRequestEvent(1));
    }

    @Subscribe
    public void onDataSetUpdated(AfterLoadingCompletedEvent event) {
        //we need just update realm dataset
        adapter.notifyDataSetChanged();
    }

    private void requestDataSet() {
        eventBus.post(new OnGetDataRequestEvent(2));
    }

    @Subscribe
    public void onDataSetObtained(AfterGetDataRespondEvent event) {
        RealmResults<InternalModel> dataSet = event.getData();
        adapter.initDataSet(dataSet);
        if (dataSet.size() == 0) {
            requestLoading();
        }
    }

    @Override
    protected IProgressController buildProgressController() {
        return new ViewDataListProgressController(refresher);
    }

    protected class ListItemClickListener implements OnItemClickListener {
        @Override
        public void onItemClick(View view, int position) {
            openDetailViewByPosition(position);
        }

        @Override
        public void onLongItemClick(View view, int position) {

        }
    }

    private void openDetailViewByPosition(int position) {
        Intent next = new Intent(this, ViewDetailsDataInfoActivity.class);
        next.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        next.putExtra(ViewDetailsDataInfoActivity.POSITION, position);
        startActivity(next);
        overridePendingTransition(-1, -1);
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

}
