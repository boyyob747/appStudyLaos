package com.bounthavong.vithaya.hoctienglao.evenbus;

import com.bounthavong.vithaya.hoctienglao.model.Vocabulary;

import io.realm.RealmList;

/**
 * Created by Boy- on 3/3/2561.
 */

public class PharasesEvent {
    private RealmList<Vocabulary> vocabularies;

    public PharasesEvent(RealmList<Vocabulary> vocabularies) {
        this.vocabularies = vocabularies;
    }

    public RealmList<Vocabulary> getVocabularies() {
        return vocabularies;
    }

    public void setVocabularies(RealmList<Vocabulary> vocabularies) {
        this.vocabularies = vocabularies;
    }
}
