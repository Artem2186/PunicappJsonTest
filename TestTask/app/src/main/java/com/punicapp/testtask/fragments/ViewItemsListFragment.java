package com.punicapp.testtask.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.activeandroid.query.Select;
import com.punicapp.testtask.R;
import com.punicapp.testtask.TtApp;
import com.punicapp.testtask.activities.ViewDataInPagerActivity;
import com.punicapp.testtask.adapters.DataListAdapter;
import com.punicapp.testtask.api.events.OnRespondEvent;
import com.punicapp.testtask.model.DataModel;
import com.squareup.otto.Subscribe;

public class ViewItemsListFragment extends Fragment {

    private RecyclerView container;
    private DataListAdapter adapter;

    private LinearLayoutManager layoutManager;

    private final DataListAdapter.OnItemClickHandler itemsClickHandler = new DataListAdapter.OnItemClickHandler() {
        @Override
        public void onClick(int position) {
            openDetailViewByPosition(position);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup viewContainer, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.view_data_as_list_fr, viewContainer, false);

        container = (RecyclerView) rootView.findViewById(R.id.data_list);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        adapter = new DataListAdapter(inflater, itemsClickHandler);
        container.setLayoutManager(layoutManager);
        container.setAdapter(adapter);

        return rootView;
    }

    private void openDetailViewByPosition(int position) {
        if (!isAdded()) {
            return;
        }

        Intent next = new Intent(getActivity(), ViewDataInPagerActivity.class);
        next.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        next.putExtra(ViewDataInPagerActivity.POSITION, position);
        getActivity().startActivity(next);
        getActivity().overridePendingTransition(-1, -1);
    }


    @Override
    public void onResume() {
        super.onResume();
        TtApp.getEventBus().register(this);
        showData();
    }

    @Override
    public void onPause() {
        super.onPause();
        TtApp.getEventBus().unregister(this);
    }

    @Subscribe
    public void onDataReceived(OnRespondEvent event) {
        showData();
    }

    protected void showData() {
        adapter.initDataSet(new Select().from(DataModel.class).<DataModel>execute());
    }

    public static Fragment getInstance() {
        return new ViewItemsListFragment();
    }
}
