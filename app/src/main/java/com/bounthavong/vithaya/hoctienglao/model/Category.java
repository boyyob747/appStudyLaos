package com.bounthavong.vithaya.hoctienglao.model;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Category extends RealmObject{
    private String name;
    private String icon_img;
    private RealmList<Vocabulary> vocabularies;

    public String getIcon_img() {
        return icon_img;
    }

    public void setIcon_img(String icon_img) {
        this.icon_img = icon_img;
    }

    public RealmList<Vocabulary> getVocabularies() {
        return vocabularies;
    }

    public void setVocabularies(RealmList<Vocabulary> vocabularies) {
        this.vocabularies = vocabularies;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", icon_img='" + icon_img + '\'' +
                ", vocabularies=" + vocabularies +
                '}';
    }
}
