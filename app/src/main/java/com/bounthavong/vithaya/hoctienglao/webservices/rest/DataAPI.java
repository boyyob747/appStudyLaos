package com.bounthavong.vithaya.hoctienglao.webservices.rest;

import com.bounthavong.vithaya.hoctienglao.webservices.model.Data;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Boy- on 17/3/2561.
 */

public interface DataAPI {
    @GET("apilevel")
    Observable<List<Data>> getAllData();
}
