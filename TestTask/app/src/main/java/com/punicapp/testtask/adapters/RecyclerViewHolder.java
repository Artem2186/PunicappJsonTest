package com.punicapp.testtask.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private Map<Integer, View> localViewCache = new HashMap<Integer, View>();

    public RecyclerViewHolder(View itemView) {
        super(itemView);
    }

    public View getView() {
        return itemView;
    }

    public View findViewById(int id) {
        View view = localViewCache.get(id);
        if (view == null) {
            view = itemView.findViewById(id);
            if (view != null) {
                localViewCache.put(id, view);
            }
        }
        return view;
    }
}