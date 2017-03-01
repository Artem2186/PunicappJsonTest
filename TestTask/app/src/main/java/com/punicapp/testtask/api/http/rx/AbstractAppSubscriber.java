package com.punicapp.testtask.api.http.rx;

import com.punicapp.testtask.TtApp;
import com.punicapp.testtask.events.error.ApiErrorEvent;
import com.punicapp.testtask.events.progress.RequestStartsEvent;
import com.punicapp.testtask.events.progress.RequestStopsEvent;
import com.punicapp.testtask.events.request.RequestEvent;
import com.squareup.otto.Bus;

import rx.Subscriber;

public abstract class AbstractAppSubscriber<T> extends Subscriber<T> {

    public enum Mode {
        Silent, Normal
    }


    protected RequestEvent initial;
    protected Bus eventBus;
    protected Mode mode;

    public AbstractAppSubscriber(RequestEvent initial) {
        this(initial, Mode.Normal);
    }

    public AbstractAppSubscriber(RequestEvent init, Mode mode) {
        this.initial = init;
        eventBus = TtApp.getEventBus();
        this.mode = mode;
        doBeforeCall();
    }

    protected void doBeforeCall() {
        if (mode != Mode.Normal) {
            return;
        }

        if (eventBus != null) {
            eventBus.post(new RequestStartsEvent(System.currentTimeMillis(), initial));
        }
    }

    protected void doAfterCall() {
        if (mode != Mode.Normal) {
            return;
        }

        if (eventBus != null) {
            eventBus.post(new RequestStopsEvent(System.currentTimeMillis(), initial));
        }
    }

    @Override
    public void onCompleted() {
        doAfterCall();
    }

    @Override
    public void onError(Throwable e) {
        doAfterCall();
        handleError(e);
    }

    protected void handleError(Throwable e) {
        eventBus.post(new ApiErrorEvent(initial, e));
    }

    @Override
    public void onNext(T tDataSet) {
        handleDataSet(tDataSet, initial);
    }

    public abstract void handleDataSet(T dataSet, RequestEvent initial);
}
