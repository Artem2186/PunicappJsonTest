package com.punicapp.testtask.events.respond;

import com.punicapp.testtask.events.request.RequestEvent;
import com.punicapp.testtask.events.respond.RespondEvent;
import com.punicapp.testtask.model.app.InternalModel;

import io.realm.RealmResults;

public class AfterGetDataRespondEvent extends RespondEvent {
    private final RealmResults<InternalModel> data;

    /**
     * @param initial initial request event see {@link RequestEvent}
     * @param dataSet data from local storage
     */
    public AfterGetDataRespondEvent(RequestEvent initial, RealmResults<InternalModel> dataSet) {
        super(initial);
        this.data = dataSet;
    }

    public RealmResults<InternalModel> getData() {
        return data;
    }
}
