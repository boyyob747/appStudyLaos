package com.bounthavong.vithaya.hoctienglao.model.dao;

import android.util.Log;

import com.bounthavong.vithaya.hoctienglao.model.Category;
import com.bounthavong.vithaya.hoctienglao.model.Level;
import com.bounthavong.vithaya.hoctienglao.model.Vocabulary;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmList;
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
    public RealmList<Vocabulary> getVbRandom(RealmList<Vocabulary> vocabularieslist,int limit,int indexNext) {
        RealmList<Vocabulary> vocabularies = vocabularieslist;
        int maxSize = vocabularies.size();
        Random random = new Random();
        int randomNumber;
        List<Integer> questionIds = new ArrayList<>();
        RealmList<Vocabulary> realmList = new RealmList<>();
        while (questionIds.size() < limit) {
            randomNumber = random.nextInt(maxSize);
            Log.i("TAG","Ran = "+randomNumber+ " indexNext = "+indexNext);
            if (!questionIds.contains(randomNumber)) {
                if (indexNext != -1){
                    if (indexNext == randomNumber){

                    }else{
                        questionIds.add(randomNumber);
                        realmList.add(vocabularies.get(randomNumber));
                    }
                }else{
                    questionIds.add(randomNumber);
                    realmList.add(vocabularies.get(randomNumber));
                }

            }
        }
        return realmList;
    }
    public void setRemember(final Vocabulary vocabulary, final boolean isRember){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                vocabulary.setRemember(isRember);
            }
        });
    }
    public RealmList<Vocabulary> getJustNotRemember(RealmList<Vocabulary> vocabularies){
        RealmList<Vocabulary> vocabularyRealmList = new RealmList<>();
        for (Vocabulary vocabulary : vocabularies){
            if (!vocabulary.isRemember()){
                vocabularyRealmList.add(vocabulary);
            }
        }
        return vocabularyRealmList;
    }
    public RealmList<Vocabulary> getallNotRemember(Level level){
        RealmList<Vocabulary> vocabularies = new RealmList<>();
            for (Category category : level.getCategories()){
                for(Vocabulary vocabulary : category.getVocabularies()){
                    if (!vocabulary.isRemember()){
                        vocabularies.add(vocabulary);
                    }
                }
            }
        return vocabularies;
    }
}
