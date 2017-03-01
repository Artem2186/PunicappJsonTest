package com.punicapp.testtask.utils.recycleview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;


public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
    private Context context;
    private RecyclerView parentView;
    private OnItemClickListener mListener;

    private GestureDetector mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            View child = parentView.findChildViewUnder(e.getX(), e.getY());
            if (child != null && mListener != null) {
                mListener.onLongItemClick(child, parentView.getChildAdapterPosition(child));
            }
        }
    });


    public RecyclerItemClickListener(Context context, RecyclerView parentView, OnItemClickListener listener) {
        this.context = context;
        this.parentView = parentView;
        mListener = listener;
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
            return true;
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }
}