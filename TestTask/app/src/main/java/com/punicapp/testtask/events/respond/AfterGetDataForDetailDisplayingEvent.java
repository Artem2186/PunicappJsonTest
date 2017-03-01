package com.punicapp.testtask.events.respond;

import com.punicapp.testtask.events.request.RequestEvent;
import com.punicapp.testtask.events.respond.RespondEvent;
import com.punicapp.testtask.model.app.InternalModel;

public class AfterGetDataForDetailDisplayingEvent extends RespondEvent {
    private final InternalModel data;

    /**
     * @param request initial request event see {@link RequestEvent}
     * @param model   data to displaying inside fragment
     */
    public AfterGetDataForDetailDisplayingEvent(RequestEvent request, InternalModel model) {
        super(request);
        this.data = model;
    }

    public InternalModel getData() {
        return data;
    }
}
