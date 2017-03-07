package com.punicapp.testtask.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.punicapp.testtask.R;
import com.punicapp.testtask.model.DataModel;

import java.util.ArrayList;
import java.util.List;

public class DataListAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private final LayoutInflater inflater;
    private OnItemClickHandler clickHandler;
    private List<DataModel> dataContainer;

    public interface OnItemClickHandler {
        void onClick(int position);
    }


    public DataListAdapter(LayoutInflater inflater, OnItemClickHandler clickHandler) {
        this.inflater = inflater;
        this.clickHandler = clickHandler;
        dataContainer = new ArrayList<>();
    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayout = inflater.inflate(R.layout.list_item_v, parent, false);
        RecyclerViewHolder holder = new RecyclerViewHolder(itemLayout);
        itemLayout.setOnClickListener(new ItemClickHandler(holder, clickHandler));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        DataModel item = dataContainer.get(position);

        TextView header = (TextView) holder.findViewById(R.id.item_header);
        header.setText(item.getTitle());
    }

    @Override
    public int getItemCount() {
        return dataContainer == null ? 0 : dataContainer.size();
    }

    public void initDataSet(List<DataModel> updated) {
        dataContainer.clear();
        if (updated != null) {
            dataContainer.addAll(updated);
        }
        notifyDataSetChanged();
    }

    private static class ItemClickHandler implements View.OnClickListener {
        private RecyclerViewHolder holder;
        private OnItemClickHandler handler;

        public ItemClickHandler(RecyclerViewHolder holder, OnItemClickHandler handler) {
            this.holder = holder;
            this.handler = handler;
        }

        @Override
        public void onClick(View v) {
            if (handler != null) {
                handler.onClick(holder.getAdapterPosition());
            }
        }
    }
}
