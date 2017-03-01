package com.punicapp.testtask.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;

import com.punicapp.testtask.R;
import com.punicapp.testtask.adapters.ViewDetailsPagerAdapter;
import com.punicapp.testtask.events.request.OnGetDataForDetailDisplayingEvent;
import com.punicapp.testtask.events.request.OnGetDataRequestEvent;
import com.punicapp.testtask.events.request.OnPageActivatedEvent;
import com.punicapp.testtask.events.respond.AfterGetDataForDetailDisplayingEvent;
import com.punicapp.testtask.events.respond.AfterGetDataRespondEvent;
import com.punicapp.testtask.events.respond.AfterLoadingCompletedEvent;
import com.punicapp.testtask.model.app.InternalModel;
import com.squareup.otto.Subscribe;

import io.realm.RealmResults;


public class ViewDetailsDataInfoActivity extends AppRootActivity {

    public static final String POSITION = "position";

    private ViewPager itemsPager;
    private ViewDetailsPagerAdapter detailsPagerAdapter;
    private int position;
    private ViewPager.OnPageChangeListener pagetSelectionHandler = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            notifyPageAboutSelection(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    private Handler handler;

    public void selectPageManually(int position) {
        itemsPager.setCurrentItem(position, false);
        notifyPageAboutSelection(position);
    }

    private void notifyPageAboutSelection(int position) {
        eventBus.post(new OnPageActivatedEvent(position));
    }

    @Override
    protected int provideScreenLayout() {
        return R.layout.view_details_data_info_ac;
    }

    @Override
    protected void onCreateScreenContent(Bundle savedInstanceState) {
        itemsPager = (ViewPager) findViewById(R.id.pager);
        detailsPagerAdapter = new ViewDetailsPagerAdapter(getSupportFragmentManager());
        itemsPager.setAdapter(detailsPagerAdapter);
        handler = new Handler();
        initPagerPosition();
    }

    private void initPagerPosition() {
        if (getIntent().getExtras() != null &&
                getIntent().getExtras().get(POSITION) != null) {
            position = getIntent().getExtras().getInt(POSITION);
        } else {
            position = 0;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestDataSet();
        itemsPager.addOnPageChangeListener(pagetSelectionHandler);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                selectPageManually(position);
            }
        }, 100);

    }

    @Override
    protected void onPause() {
        super.onPause();
        itemsPager.removeOnPageChangeListener(pagetSelectionHandler);
    }

    @Override
    protected boolean isUpButtonVisible() {
        return true;
    }

    private void requestDataSet() {
        eventBus.post(new OnGetDataRequestEvent(3));
    }

    @Subscribe
    public void onDataSetObtained(AfterGetDataRespondEvent event) {
        RealmResults<InternalModel> dataSet = event.getData();
        detailsPagerAdapter.initDataSet(dataSet);
    }

    @Subscribe
    public void onDataLoaded(AfterLoadingCompletedEvent event) {
        detailsPagerAdapter.notifyDataSetChanged();
    }

    @Subscribe
    public void onDetaisDisplayingRequest(OnGetDataForDetailDisplayingEvent event) {
        InternalModel item = detailsPagerAdapter.getModelByPosition((int) event.getCode());
        item.load();
        eventBus.post(new AfterGetDataForDetailDisplayingEvent(event, item));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(POSITION, itemsPager.getCurrentItem());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        position = savedInstanceState.getInt(POSITION);
    }
}
