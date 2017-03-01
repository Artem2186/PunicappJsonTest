package com.punicapp.testtask.api;

import com.punicapp.testtask.events.request.OnGetDataRequestEvent;
import com.punicapp.testtask.events.request.OnLoadDataRequestEvent;

public interface IDataService {

    void onLoadDataRequest(OnLoadDataRequestEvent event);

    void onGetDataRequest(OnGetDataRequestEvent event);
}
