package com.bounthavong.vithaya.hoctienglao.webservices.model.dao;

import com.bounthavong.vithaya.hoctienglao.webservices.model.Data;
import com.bounthavong.vithaya.hoctienglao.webservices.rest.DataAPI;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by Boy- on 17/3/2561.
 */

public class DataDAO {
    Retrofit retrofit;
    DataAPI dataAPI;

    public DataDAO(Retrofit retrofit) {
        this.retrofit = retrofit;
        this.dataAPI = this.retrofit.create(DataAPI.class);
    }
    public Observable<List<Data>> getAllData() {
        Observable<List<Data>> observable =
                dataAPI
                        .getAllData()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }
}
