package com.punicapp.testtask.events.progress;


import com.punicapp.testtask.events.request.RequestEvent;

public class RequestStopsEvent extends ProgressDataEvent {
    public RequestStopsEvent(double timestamp, RequestEvent initialEvent) {
        super(timestamp, initialEvent);
    }
}
