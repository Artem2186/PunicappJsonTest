package com.punicapp.testtask.api.http.rx;

import com.punicapp.testtask.model.DaoConveter;
import com.punicapp.testtask.model.IDao;

import java.io.IOException;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import rx.Observable;
import rx.functions.Func1;

public class ApiToAppDataConverter<T extends IDao, U extends RealmObject & IDao> implements Func1<List<T>, Observable<Integer>> {

    private Class<U> destType;

    public ApiToAppDataConverter(Class<U> destType) {
        this.destType = destType;
    }

    @Override
    public Observable<Integer> call(List<T> t) {
        Realm instance = Realm.getDefaultInstance();
        try {
            List<U> result = DaoConveter.convert(t, destType);
            if (result == null || result.size() == 0) {
                return Observable.error(new StoringDataException());
            }
            instance.beginTransaction();
            for (U item : result) {
                instance.copyToRealmOrUpdate(item);
            }
            instance.commitTransaction();
            return Observable.just(result.size());
        } catch (Throwable exc) {
            return Observable.error(new StoringDataException());
        } finally {
            instance.close();
        }
    }
}
