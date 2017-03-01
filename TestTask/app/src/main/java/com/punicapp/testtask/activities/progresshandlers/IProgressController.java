package com.punicapp.testtask.activities.progresshandlers;

import com.punicapp.testtask.events.progress.RequestStartsEvent;
import com.punicapp.testtask.events.progress.RequestStopsEvent;
import com.punicapp.testtask.utils.IBusRegistration;

public interface IProgressController extends IBusRegistration {

    void onRequestStart(RequestStartsEvent event);

    void onRequestEnd(RequestStopsEvent event);
}
