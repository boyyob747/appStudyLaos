package com.bounthavong.vithaya.hoctienglao.config;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by Boy- on 3/3/2561.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);

    }
}
