package com.punicapp.testtask.events.request;

public class OnGetDataRequestEvent extends RequestEvent {
    /**
     * @param code event identificator
     */
    public OnGetDataRequestEvent(long code) {
        super(code);
    }
}
