package com.punicapp.testtask.utils;

import com.squareup.otto.Bus;

public interface IBusRegistration {
    void register(Bus eventBus);

    void unregister(Bus eventBus);
}
