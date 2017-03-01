package com.punicapp.testtask.events.respond;

import com.punicapp.testtask.events.request.RequestEvent;

/**
 * Created by ARTem on 28.02.2017.
 */
public class AfterLoadingCompletedEvent extends RespondEvent {
    private final Integer loadedItemz;

    /**
     * @param request initial request event see {@link RequestEvent}
     * @param dataSet count of loaded data. unused
     */
    public AfterLoadingCompletedEvent(RequestEvent request, Integer dataSet) {
        super(request);
        this.loadedItemz = dataSet;
    }
}
