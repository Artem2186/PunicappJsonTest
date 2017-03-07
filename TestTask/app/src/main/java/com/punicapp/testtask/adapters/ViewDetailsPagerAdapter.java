package com.punicapp.testtask.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.punicapp.testtask.fragments.ViewItemDetailsFragment;
import com.punicapp.testtask.model.DataModel;

import java.util.ArrayList;
import java.util.List;

public class ViewDetailsPagerAdapter extends FragmentStatePagerAdapter {

    private List<DataModel> dataSet;

    public ViewDetailsPagerAdapter(FragmentManager fm) {
        super(fm);
        dataSet = new ArrayList<>();
    }


    @Override
    public Fragment getItem(int position) {
        DataModel model = dataSet.get(position);
        return ViewItemDetailsFragment.newInstance(model.getId());
    }

    @Override
    public int getCount() {
        return dataSet == null ? 0 : dataSet.size();
    }

    public void initDataSet(List<DataModel> dataUpdated) {
        this.dataSet.clear();
        this.dataSet.addAll(dataUpdated);
        notifyDataSetChanged();
    }
}
