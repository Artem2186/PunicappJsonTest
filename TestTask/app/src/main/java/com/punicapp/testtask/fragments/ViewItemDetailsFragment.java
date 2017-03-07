package com.punicapp.testtask.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.punicapp.testtask.R;
import com.punicapp.testtask.TtApp;

import com.punicapp.testtask.model.DataModel;
import com.squareup.picasso.Picasso;

public class ViewItemDetailsFragment extends Fragment {

    public static final String ID_ARG = "POSTION";

    private TextView headerView;
    private View rootView;
    private ImageView imageView;
    private long id;

    private Picasso picasso;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        picasso = TtApp.getPicasso();
        this.id = getArguments().getLong(ID_ARG);

        rootView = inflater.inflate(R.layout.view_details_fr, container, false);
        headerView = (TextView) rootView.findViewById(R.id.details_header);
        imageView = (ImageView) rootView.findViewById(R.id.details_image);
        initUI();
        return rootView;
    }


    private void initUI() {
        DataModel data = DataModel.load(DataModel.class, id);
        if (data == null) {
            return;
        }
        String title = data.getTitle();
        String imageUrl = data.getImageUrl();

        headerView.setText(title);
        picasso.load(imageUrl)
                .placeholder(R.drawable.stub_ico)
                .error(R.drawable.error_ico)
                .into(imageView);
    }


    public static Fragment newInstance(long id) {
        ViewItemDetailsFragment instance = new ViewItemDetailsFragment();
        Bundle args = new Bundle();
        args.putLong(ID_ARG, id);
        instance.setArguments(args);
        return instance;
    }
}
