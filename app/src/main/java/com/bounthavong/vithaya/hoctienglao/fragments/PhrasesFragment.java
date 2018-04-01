package com.bounthavong.vithaya.hoctienglao.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
import com.bounthavong.vithaya.hoctienglao.activity.QuizActivity;
import com.bounthavong.vithaya.hoctienglao.config.Default;
import com.bounthavong.vithaya.hoctienglao.evenbus.PharasesEvent;
import com.bounthavong.vithaya.hoctienglao.fragments.adapter.LevelAdapter;
import com.bounthavong.vithaya.hoctienglao.model.Level;
import com.bounthavong.vithaya.hoctienglao.model.Vocabulary;
import com.bounthavong.vithaya.hoctienglao.model.dao.LevelDAO;
import com.bounthavong.vithaya.hoctienglao.model.dao.VocabularyDAO;
import com.bounthavong.vithaya.hoctienglao.webservices.RetrofitClient;
import com.bounthavong.vithaya.hoctienglao.webservices.model.Data;
import com.bounthavong.vithaya.hoctienglao.webservices.model.dao.DataDAO;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import at.markushi.ui.CircleButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.ResourceObserver;
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
    CircleButton iconFav;
    @BindView(R.id.icon_all_phrares)
    CircleButton iconAllPhrares;

    private LevelAdapter levelAdapter;
    RealmResults<Level> levels;
    Realm realm;
    boolean isQuiz = false;


    public static PhrasesFragment newInstance(boolean isQuiz) {
        PhrasesFragment fragment = new PhrasesFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(Default.IS_QUIZ,isQuiz);
        fragment.setArguments(bundle);
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
        Bundle bundle = getArguments();
        if (bundle != null){
            isQuiz = bundle.getBoolean(Default.IS_QUIZ);
        }
        if (isQuiz){
            getActivity().setTitle(getResources().getString(R.string.title_quiz));
        }else{
            getActivity().setTitle(getResources().getString(R.string.title_phrases));
        }
        Log.i(TAG,"isQuiz = "+isQuiz);
        setWidget();
        return view;
    }



    private void setWidget() {
        levels = realm.where(Level.class).findAll();
        Log.i(TAG, levels.toString());
        levelAdapter = new LevelAdapter(levels, getContext(),isQuiz);
        recyclerLevel.setHasFixedSize(true);
        recyclerLevel.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerLevel.addItemDecoration(new VerticalLineDecorator(2));
        recyclerLevel.setAdapter(levelAdapter);
        iconAllPhrares.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPharese(getResources().getString(R.string.all_phrases),false);
            }
        });
        iconFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPharese(getResources().getString(R.string.fav),true);
            }
        });
    }
    public void startPharese(String title,boolean isFav){
        VocabularyDAO vocabularyDAO = new VocabularyDAO(Realm.getDefaultInstance());
        RealmList<Vocabulary> vocabularies = new RealmList<>();
        if (isFav){
            vocabularies.addAll(vocabularyDAO.getAllFav());
            if (isQuiz){
                if (vocabularies.size() < 5){
                    Snackbar.make(getView(),"Làm ơn đánh dấu ít nhất 5 cụm từ ưa thích",Snackbar.LENGTH_LONG).show();
                    return;
                }
            }
        }else{
            vocabularies.addAll(vocabularyDAO.getAllVocabulary());
        }
        PharasesEvent pharasesEvent = new PharasesEvent(vocabularies);
        pharasesEvent.setTitle(title);
        EventBus.getDefault().postSticky(pharasesEvent);
        Intent intent;
        if (!isQuiz){
            intent = new Intent(getContext(), PhrasesActivity.class);
        }else{
            intent = new Intent(getContext(), QuizActivity.class);
        }
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
