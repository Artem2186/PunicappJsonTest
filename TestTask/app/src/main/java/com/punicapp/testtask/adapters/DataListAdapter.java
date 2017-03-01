package com.punicapp.testtask.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.punicapp.testtask.R;
import com.punicapp.testtask.model.app.InternalModel;

import java.util.List;

public class DataListAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private final LayoutInflater inflater;
    private List<InternalModel> dataContainer;

    public DataListAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayout = inflater.inflate(R.layout.list_item_v, parent, false);
        return new RecyclerViewHolder(itemLayout);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        InternalModel item = dataContainer.get(position);
        TextView header = (TextView) holder.findViewById(R.id.item_header);
        header.setText(item.getTitle());
    }

    @Override
    public int getItemCount() {
        return dataContainer == null ? 0 : dataContainer.size();
    }

    public void initDataSet(List<InternalModel> newData) {
        if (dataContainer == null) {
            dataContainer = newData;
        }
        notifyDataSetChanged();
    }
}
