package com.bounthavong.vithaya.hoctienglao.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bounthavong.vithaya.hoctienglao.R;
import com.bounthavong.vithaya.hoctienglao.activity.PhrasesActivity;
import com.bounthavong.vithaya.hoctienglao.evenbus.PharasesEvent;
import com.bounthavong.vithaya.hoctienglao.fragments.adapter.LevelAdapter;
import com.bounthavong.vithaya.hoctienglao.model.Level;
import com.bounthavong.vithaya.hoctienglao.model.Vocabulary;
import com.bounthavong.vithaya.hoctienglao.model.dao.VocabularyDAO;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhrasesFragment extends Fragment {
    private static final String TAG = PhrasesFragment.class.getName();
    View view;
    @BindView(R.id.recycler_level)
    RecyclerView recyclerLevel;
    Unbinder unbinder;
    @BindView(R.id.icon_fav)
    ImageView iconFav;
    @BindView(R.id.icon_all_phrares)
    ImageView iconAllPhrares;

    private LevelAdapter levelAdapter;
    RealmResults<Level> levels;
    Realm realm;

    public static PhrasesFragment newInstance() {
        PhrasesFragment fragment = new PhrasesFragment();
        return fragment;
    }

    public PhrasesFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_base, container, false);
        unbinder = ButterKnife.bind(this, view);
        realm = Realm.getDefaultInstance();
        setWidget();
        return view;
    }

    private void setWidget() {
        levels = realm.where(Level.class).findAll();
        Log.i(TAG, levels.toString());
        levelAdapter = new LevelAdapter(levels, getContext());
        recyclerLevel.setHasFixedSize(true);
        recyclerLevel.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerLevel.addItemDecoration(new VerticalLineDecorator(2));
        recyclerLevel.setAdapter(levelAdapter);
        iconAllPhrares.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VocabularyDAO vocabularyDAO = new VocabularyDAO(Realm.getDefaultInstance());
                RealmList<Vocabulary> vocabularies = new RealmList<>();
                vocabularies.addAll(vocabularyDAO.getAllVocabulary());
                PharasesEvent pharasesEvent = new PharasesEvent(vocabularies);
                pharasesEvent.setTitle(getResources().getString(R.string.all_phrases));
                EventBus.getDefault().postSticky(pharasesEvent);
                Intent intent = new Intent(getContext(), PhrasesActivity.class);
                startActivity(intent);
            }
        });
        iconFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VocabularyDAO vocabularyDAO = new VocabularyDAO(Realm.getDefaultInstance());
                RealmList<Vocabulary> vocabularies = new RealmList<>();
                vocabularies.addAll(vocabularyDAO.getAllFav());
                PharasesEvent pharasesEvent = new PharasesEvent(vocabularies);
                pharasesEvent.setTitle(getResources().getString(R.string.fav));
                EventBus.getDefault().postSticky(pharasesEvent);
                Intent intent = new Intent(getContext(), PhrasesActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
