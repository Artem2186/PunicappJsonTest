package com.punicapp.testtask.events.respond;

import com.punicapp.testtask.events.IAppEvent;
import com.punicapp.testtask.events.request.RequestEvent;

/**
 * Base respond event for interacting with services
 */
public class RespondEvent implements IAppEvent {

    protected RequestEvent request;

    /**
     * @param request initial request event see {@link RequestEvent}
     */
    public RespondEvent(RequestEvent request) {
        this.request = request;
    }

    public RequestEvent getRequest() {
        return request;
    }

    @Override
    public long getCode() {
        if (request != null) {
            return request.getCode();
        }
        return ZERO_CODE;
    }
}
