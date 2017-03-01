package com.punicapp.testtask.events.progress;

import com.punicapp.testtask.events.request.RequestEvent;

public class RequestStartsEvent extends ProgressDataEvent {
    public RequestStartsEvent(double timestamp, RequestEvent initialEvent) {
        super(timestamp, initialEvent);
    }
}
