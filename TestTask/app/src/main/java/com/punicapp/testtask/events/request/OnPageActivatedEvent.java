package com.punicapp.testtask.events.request;


public class OnPageActivatedEvent extends RequestEvent {
    /**
     * @param code event identificator
     */
    public OnPageActivatedEvent(long code) {
        super(code);
    }
}
