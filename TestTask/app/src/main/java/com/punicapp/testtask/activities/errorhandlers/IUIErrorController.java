package com.punicapp.testtask.activities.errorhandlers;

import com.punicapp.testtask.events.error.ApiErrorEvent;
import com.punicapp.testtask.utils.IBusRegistration;

/**
 * Errors from API and DataBase will be handled inside instances of this interface
 */
public interface IUIErrorController extends IBusRegistration {

    /**
     * @param event event that contain info about error see {@link ApiErrorEvent}
     */
    void onApiError(ApiErrorEvent event);
}
