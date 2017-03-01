package com.punicapp.testtask.api.http.rx;

import rx.functions.Func1;

public class EmptyDataFilter implements Func1<Integer, Boolean> {
    @Override
    public Boolean call(Integer integer) {
        return integer > 0;
    }
}
