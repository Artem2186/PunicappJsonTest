package com.punicapp.testtask.events.request;

import com.punicapp.testtask.events.IAppEvent;

/**
 * Base request event for interacting with services
 */
public class RequestEvent implements IAppEvent {

    protected long code;

    /**
     * @param code event identificator
     */
    public RequestEvent(long code) {
        this.code = code;
    }

    @Override
    public long getCode() {
        return code;
    }
}
