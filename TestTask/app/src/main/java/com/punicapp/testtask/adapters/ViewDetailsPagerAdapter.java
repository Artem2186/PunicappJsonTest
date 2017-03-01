package com.punicapp.testtask.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.punicapp.testtask.fragments.ViewItemDetailsFragment;
import com.punicapp.testtask.model.app.InternalModel;

import java.util.ArrayList;
import java.util.List;

public class ViewDetailsPagerAdapter extends FragmentStatePagerAdapter {

    private List<InternalModel> dataSet;

    public ViewDetailsPagerAdapter(FragmentManager fm) {
        super(fm);
        dataSet = new ArrayList<>();
    }


    @Override
    public Fragment getItem(int position) {
        return ViewItemDetailsFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return dataSet == null ? 0 : dataSet.size();
    }

    public void initDataSet(List<InternalModel> newData) {
        this.dataSet = newData;
        notifyDataSetChanged();
    }

    public InternalModel getModelByPosition(int current) {
        if (dataSet == null || dataSet.size() == 0) {
            return null;
        }
        return dataSet.get(current);
    }
}
