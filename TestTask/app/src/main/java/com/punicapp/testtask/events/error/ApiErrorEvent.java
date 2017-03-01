package com.punicapp.testtask.events.error;

import com.punicapp.testtask.events.request.RequestEvent;

import java.io.Serializable;

/**
 * Event that will be sent by service to UI for handling
 * This event contains info about error
 */
public class ApiErrorEvent implements Serializable {

    private RequestEvent parentEvent;
    private Throwable exception;

    public ApiErrorEvent(RequestEvent parentEvent, Throwable exception) {
        this.parentEvent = parentEvent;
        this.exception = exception;
    }

    public Throwable getCause() {
        return exception;
    }
}
