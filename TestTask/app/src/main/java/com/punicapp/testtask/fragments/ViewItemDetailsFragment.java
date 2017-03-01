package com.punicapp.testtask.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.punicapp.testtask.R;
import com.punicapp.testtask.TtApp;
import com.punicapp.testtask.events.request.OnGetDataForDetailDisplayingEvent;
import com.punicapp.testtask.events.request.OnPageActivatedEvent;
import com.punicapp.testtask.events.respond.AfterGetDataForDetailDisplayingEvent;
import com.punicapp.testtask.model.app.InternalModel;
import com.punicapp.testtask.utils.picasso.OkHttp3Downloader;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

public class ViewItemDetailsFragment extends Fragment {

    public static final String POSITION_ARG = "POSTION";

    private TextView headerView;
    private View rootView;
    private ImageView imageView;
    private Bus eventBus;
    private int position;
    private InternalModel content;
    private Picasso picasso;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        initPicasso();

        this.position = getArguments().getInt(POSITION_ARG);

        rootView = inflater.inflate(R.layout.view_details_fr, container, false);
        headerView = (TextView) rootView.findViewById(R.id.details_header);
        imageView = (ImageView) rootView.findViewById(R.id.details_image);
        eventBus = TtApp.getEventBus();
        return rootView;
    }

    private void initPicasso() {
        Picasso.Builder picassoBuilder = new Picasso.Builder(getActivity());
        picassoBuilder.downloader(new OkHttp3Downloader.Builder(getActivity()).build());
        picasso = picassoBuilder.build();
    }

    @Subscribe
    public void onReceiveDataToDisplay(AfterGetDataForDetailDisplayingEvent event) {
        if (event.getCode() != position) {
            return;
        }
        content = event.getData();
    }

    private void initUI(InternalModel data) {
        String title = data.getTitle();
        String imageUrl = data.getImageUrl();

        headerView.setText(title);
        picasso.load(imageUrl)
                .placeholder(R.drawable.stub_ico)
                .error(R.drawable.error_ico)
                .into(imageView);
    }

    @Override
    public void onResume() {
        super.onResume();
        eventBus.register(this);
        eventBus.post(new OnGetDataForDetailDisplayingEvent(position));
    }

    @Override
    public void onPause() {
        super.onPause();
        eventBus.unregister(this);
    }

    public static Fragment newInstance(int position) {
        ViewItemDetailsFragment instance = new ViewItemDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(POSITION_ARG, position);
        instance.setArguments(args);
        return instance;
    }

    @Subscribe
    public void onPageActivated(OnPageActivatedEvent event) {
        if (event.getCode() == position) {
            initUI(content);
        }
    }
}
