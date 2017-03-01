package com.punicapp.testtask.events.progress;

import com.punicapp.testtask.events.IAppEvent;
import com.punicapp.testtask.events.request.RequestEvent;

/**
 *
 * */
public class ProgressDataEvent implements IAppEvent {

    protected double timestamp;
    protected RequestEvent request;

    public ProgressDataEvent(double timestamp, RequestEvent request) {
        this.timestamp = timestamp;
        this.request = request;
    }

    @Override
    public long getCode() {
        if (request != null) {
            request.getCode();
        }
        return ZERO_CODE;
    }
}
