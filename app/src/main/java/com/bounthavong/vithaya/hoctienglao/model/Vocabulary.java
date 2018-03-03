package com.bounthavong.vithaya.hoctienglao.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Boy- on 3/3/2561.
 */

public class Vocabulary extends RealmObject{
    private String vocabulary_lao;
    private String vocabulary_vn;
    private String sound_vocabulary;
    private String vocabulary_karaoke;
    private boolean isFavorite = false;

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String getVocabulary_lao() {
        return vocabulary_lao;
    }

    public void setVocabulary_lao(String vocabulary_lao) {
        this.vocabulary_lao = vocabulary_lao;
    }

    public String getVocabulary_vn() {
        return vocabulary_vn;
    }

    public void setVocabulary_vn(String vocabulary_vn) {
        this.vocabulary_vn = vocabulary_vn;
    }

    public String getSound_vocabulary() {
        return sound_vocabulary;
    }

    public void setSound_vocabulary(String sound_vocabulary) {
        this.sound_vocabulary = sound_vocabulary;
    }

    public String getVocabulary_karaoke() {
        return vocabulary_karaoke;
    }

    public void setVocabulary_karaoke(String vocabulary_karaoke) {
        this.vocabulary_karaoke = vocabulary_karaoke;
    }

    @Override
    public String toString() {
        return "Vocabulary{" +
                "vocabulary_lao='" + vocabulary_lao + '\'' +
                ", vocabulary_vn='" + vocabulary_vn + '\'' +
                ", sound_vocabulary='" + sound_vocabulary + '\'' +
                ", vocabulary_karaoke='" + vocabulary_karaoke + '\'' +
                '}';
    }
}
