package com.bounthavong.vithaya.hoctienglao.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Boy- on 3/3/2561.
 */

public class Level extends RealmObject{

    private String name;
    private RealmList<Category> categories;

    public RealmList<Category> getCategories() {
        return categories;
    }

    public void setCategories(RealmList<Category> categories) {
        this.categories = categories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Level{" +
                "name='" + name + '\'' +
                ", categories=" + categories +
                '}';
    }
}
