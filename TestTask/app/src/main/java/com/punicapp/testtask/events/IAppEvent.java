package com.punicapp.testtask.events;

/**
 * Base inteface for Application events
 */
public interface IAppEvent {
    int ZERO_CODE = -1;

    /**
     * return event identificator
     */
    long getCode();
}
