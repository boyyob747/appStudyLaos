package com.bounthavong.vithaya.hoctienglao.model.dao;

import android.util.Log;

import com.bounthavong.vithaya.hoctienglao.model.Category;
import com.bounthavong.vithaya.hoctienglao.model.Level;
import com.bounthavong.vithaya.hoctienglao.model.Vocabulary;
import com.bounthavong.vithaya.hoctienglao.webservices.model.Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by Boy- on 3/3/2561.
 */

public class LevelDAO {
    Realm realm;

    public LevelDAO(Realm realm) {
        this.realm = realm;
    }
    public void saveData(final List<Data> datas){

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                try {
                    for (Data data : datas ){
                        Level level = realm.createObject(Level.class);
                        level.setName(data.getName());
                        RealmList<Category> categories = new RealmList<>();
                        for (Data.CategoriesBean categoriesBean : data.getCategories()){
                            Category category = realm.createObject(Category.class);
                            category.setName(categoriesBean.getName());
                            category.setIcon_img(categoriesBean.getUrl_img());
                            RealmList<Vocabulary> vocabularies = new RealmList<>();
                            for (Data.CategoriesBean.VocabulariesBean vocabulariesBean : categoriesBean.getVocabularies()){
                                Vocabulary vocabulary = realm.createObject(Vocabulary.class);
                                vocabulary.setVocabulary_lao(vocabulariesBean.getVb_lao());
                                vocabulary.setVocabulary_vn(vocabulariesBean.getVb_vn());
                                vocabulary.setVocabulary_karaoke(vocabulariesBean.getVb_karaoke());
                                vocabulary.setSound_vocabulary(vocabulariesBean.getUrl_mp3());
                                vocabularies.add(vocabulary);
                            }
                            category.setVocabularies(vocabularies);
                            categories.add(category);
                        }
                        level.setCategories(categories);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public RealmList<Level> getAllLevel(){
        RealmList<Level> levels = new RealmList<>();
        levels.addAll(realm.where(Level.class).findAll());
        return levels;
    }
}
