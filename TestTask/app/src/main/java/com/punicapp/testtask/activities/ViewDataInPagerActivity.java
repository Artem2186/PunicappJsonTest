package com.punicapp.testtask.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.activeandroid.Model;
import com.activeandroid.query.Select;
import com.punicapp.testtask.R;
import com.punicapp.testtask.adapters.ViewDetailsPagerAdapter;
import com.punicapp.testtask.model.DataModel;

import java.util.List;


public class ViewDataInPagerActivity extends AppRootActivity {

    public static final String POSITION = "position";

    private ViewPager itemsPager;
    private ViewDetailsPagerAdapter detailsPagerAdapter;
    private int position;

    public void selectPageManually(int position) {
        itemsPager.setCurrentItem(position, false);
    }

    @Override
    protected void onCreateScreenContent(LayoutInflater inflater, ViewGroup root, Bundle savedInstanceState) {
        inflater.inflate(R.layout.view_details_data_info_ac, root);
        itemsPager = (ViewPager) findViewById(R.id.pager);
        detailsPagerAdapter = new ViewDetailsPagerAdapter(getSupportFragmentManager());
        itemsPager.setAdapter(detailsPagerAdapter);
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
        initPagerContent();
        selectPageManually(position);

    }

    private void initPagerContent() {
        List<DataModel> dataToDisplay = new Select().from(DataModel.class).execute();
        detailsPagerAdapter.initDataSet(dataToDisplay);
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
