package com.punicapp.testtask.events.request;

public class OnLoadDataRequestEvent extends RequestEvent {

    /**
     * @param code event identificator
     */
    public OnLoadDataRequestEvent(long code) {
        super(code);
    }
}
