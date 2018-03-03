package com.bounthavong.vithaya.hoctienglao.model.dao;

import com.bounthavong.vithaya.hoctienglao.model.Vocabulary;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Boy- on 3/3/2561.
 */

public class VocabularyDAO {
    Realm realm;

    public VocabularyDAO(Realm realm) {
        this.realm = realm;
    }
    public RealmResults<Vocabulary> getAllVocabulary(){
        return realm.where(Vocabulary.class).findAll();
    }
    public RealmResults<Vocabulary> getAllFav(){
        return realm.where(Vocabulary.class).equalTo("isFavorite",true).findAll();
    }
    public void setFav(final Vocabulary vocabulary){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                vocabulary.setFavorite(!vocabulary.isFavorite());
            }
        });
    }
}
