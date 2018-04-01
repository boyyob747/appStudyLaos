package com.bounthavong.vithaya.hoctienglao.model;

/**
 * Created by Boy- on 1/4/2561.
 */

public class Question {
    int id;
    boolean isChecked;
    int idCorrect;
    int isCorrect = -1;

    public int getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(int isCorrect) {
        this.isCorrect = isCorrect;
    }

    public int getIdCorrect() {
        return idCorrect;
    }

    public void setIdCorrect(int idCorrect) {
        this.idCorrect = idCorrect;
    }

    public Question() {
        this.isChecked = false;
        this.id = 0;
        this.idCorrect = -1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", isChecked=" + isChecked +
                ", idCorrect=" + idCorrect +
                '}';
    }
}
