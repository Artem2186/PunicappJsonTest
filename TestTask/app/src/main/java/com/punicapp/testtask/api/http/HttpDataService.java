package com.punicapp.testtask.api.http;

import android.content.Context;

import com.punicapp.testtask.api.IDataService;
import com.punicapp.testtask.api.http.retrofit.IRetrofitApi;
import com.punicapp.testtask.api.http.rx.AbstractAppSubscriber;
import com.punicapp.testtask.api.http.rx.ApiToAppDataConverter;
import com.punicapp.testtask.api.http.rx.EmptyDataFilter;
import com.punicapp.testtask.events.request.OnGetDataRequestEvent;
import com.punicapp.testtask.events.request.RequestEvent;
import com.punicapp.testtask.events.respond.AfterGetDataRespondEvent;
import com.punicapp.testtask.events.respond.AfterLoadingCompletedEvent;
import com.punicapp.testtask.events.request.OnLoadDataRequestEvent;
import com.punicapp.testtask.model.api.ApiModel;
import com.punicapp.testtask.model.app.InternalModel;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.functions.Func1;

public class HttpDataService extends ApiDataService implements IDataService {

    private IRetrofitApi service;
    public static final Func1<Integer, Boolean> EMPTY_DATA_FILTER = new EmptyDataFilter();

    @Override
    public void init(Bus eventBus, Context context) {
        super.init(eventBus, context);
        service = adapter.create(IRetrofitApi.class);
    }


    @Subscribe
    public void onLoadDataRequest(OnLoadDataRequestEvent event) {
        Observable<Integer> requestSequence = service.loadApiData()
                .flatMap(new ApiToAppDataConverter<ApiModel, InternalModel>(InternalModel.class))
                .filter(EMPTY_DATA_FILTER);
        requestSequence = updateSubscriptionPreffs(requestSequence);
        requestSequence.subscribe(new AbstractAppSubscriber<Integer>(event) {
            @Override
            public void handleDataSet(Integer dataSet, RequestEvent initial) {
                eventBus.post(new AfterLoadingCompletedEvent(initial, dataSet));
            }
        });

    }

    @Override
    @Subscribe
    public void onGetDataRequest(final OnGetDataRequestEvent event) {
        Realm realm = Realm.getDefaultInstance();
        realm.where(InternalModel.class)
                .findAll()
                .sort("id")
                .asObservable()
                .subscribe(new AbstractAppSubscriber<RealmResults<InternalModel>>(event, AbstractAppSubscriber.Mode.Silent) {
                    @Override
                    public void handleDataSet(RealmResults<InternalModel> dataSet, RequestEvent initial) {
                        eventBus.post(new AfterGetDataRespondEvent(initial, dataSet));
                    }
                });
    }

}
