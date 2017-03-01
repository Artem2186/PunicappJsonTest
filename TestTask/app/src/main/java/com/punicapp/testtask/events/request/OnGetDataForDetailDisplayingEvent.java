package com.punicapp.testtask.events.request;

public class OnGetDataForDetailDisplayingEvent extends RequestEvent {
    /**
     * @param code event identificator
     */
    public OnGetDataForDetailDisplayingEvent(long code) {
        super(code);
    }
}
