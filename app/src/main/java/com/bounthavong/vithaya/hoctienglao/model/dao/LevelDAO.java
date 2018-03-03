package com.bounthavong.vithaya.hoctienglao.model.dao;

import android.util.Log;

import com.bounthavong.vithaya.hoctienglao.model.Category;
import com.bounthavong.vithaya.hoctienglao.model.Level;
import com.bounthavong.vithaya.hoctienglao.model.Vocabulary;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    public void saveData(final String json){

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                JSONArray jObject = null;
                try {
                    jObject = new JSONArray(json);
                    for (int i = 0 ; i < jObject.length() ; i++){
                        JSONObject levelObject = jObject.getJSONObject(i);
                        String nameLevel = levelObject.getString("name");
                        Level level = realm.createObject(Level.class);
                        level.setName(nameLevel);
                        JSONArray arrayCategories = levelObject.getJSONArray("categories");
                        RealmList<Category> categories = new RealmList<>();
                        for (int j = 0 ;j < arrayCategories.length() ; j++){
                            JSONObject categoriesObject = arrayCategories.getJSONObject(j);
                            Category category = realm.createObject(Category.class);
                            String nameCategories = categoriesObject.getString("name");
                            String icon_img = categoriesObject.getString("icon_img");
                            category.setName(nameCategories);
                            category.setIcon_img(icon_img);
                            RealmList<Vocabulary> vocabularies = new RealmList<>();
                            JSONArray arrayVocabularies = categoriesObject.getJSONArray("vocabularies");
                            for (int k = 0 ; k < arrayVocabularies.length() ; k++){
                                Vocabulary vocabulary = realm.createObject(Vocabulary.class);
                                String vocabulary_lao = arrayVocabularies.getJSONObject(k).getString("vocabulary_lao");
                                String vocabulary_vn = arrayVocabularies.getJSONObject(k).getString("vocabulary_vn");
                                String vocabulary_karaoke = arrayVocabularies.getJSONObject(k).getString("vocabulary_karaoke");
                                String sound_vocabulary =  arrayVocabularies.getJSONObject(k).getString("sound_vocabulary");
                                vocabulary.setVocabulary_lao(vocabulary_lao);
                                vocabulary.setVocabulary_vn(vocabulary_vn);
                                vocabulary.setVocabulary_karaoke(vocabulary_karaoke);
                                vocabulary.setSound_vocabulary(sound_vocabulary);
                                vocabularies.add(vocabulary);
                            }
                            category.setVocabularies(vocabularies);
                            categories.add(category);
                        }
                        level.setCategories(categories);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
