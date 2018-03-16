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
import android.widget.Toast;

import com.bounthavong.vithaya.hoctienglao.R;
import com.bounthavong.vithaya.hoctienglao.activity.StudyActivity;
import com.bounthavong.vithaya.hoctienglao.activity.listener.ItemRecyclerClickListener;
import com.bounthavong.vithaya.hoctienglao.evenbus.PharasesEvent;
import com.bounthavong.vithaya.hoctienglao.fragments.adapter.StudyAdapter;
import com.bounthavong.vithaya.hoctienglao.fragments.adapter.listener.LAOTouchListener;
import com.bounthavong.vithaya.hoctienglao.fragments.adapter.viewholder.StudyVH;
import com.bounthavong.vithaya.hoctienglao.model.Level;
import com.bounthavong.vithaya.hoctienglao.model.Vocabulary;
import com.bounthavong.vithaya.hoctienglao.model.dao.LevelDAO;
import com.bounthavong.vithaya.hoctienglao.model.dao.VocabularyDAO;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.Realm;
import io.realm.RealmList;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudyFragment extends Fragment {
    public static final String TAG = StudyFragment.class.getName();
    @BindView(R.id.recycler_study)
    RecyclerView recyclerStudy;
    Unbinder unbinder;
    StudyAdapter studyAdapter ;
    RealmList<Level> levels;
    LevelDAO levelDAO;
    public static StudyFragment newInstance() {
        StudyFragment fragment = new StudyFragment();
        return fragment;
    }

    public StudyFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_study, container, false);
        unbinder = ButterKnife.bind(this, view);
        levels = new RealmList<>();
        levelDAO = new LevelDAO(Realm.getDefaultInstance());
        setWidget();
        return view;
    }

    private void setWidget() {

        levels = levelDAO.getAllLevel();
        studyAdapter = new StudyAdapter(levels,getContext());
        recyclerStudy.setHasFixedSize(true);
        recyclerStudy.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerStudy.addItemDecoration(new VerticalLineDecorator(2));
        recyclerStudy.setAdapter(studyAdapter);
        studyAdapter.setItemClick(new ItemRecyclerClickListener() {
            @Override
            public void onClick(View view, int position) {
                VocabularyDAO vocabularyDAO = new VocabularyDAO(Realm.getDefaultInstance());
                RealmList<Vocabulary> vocabularies = vocabularyDAO.getallNotRemember(levels.get(position));
                if (vocabularies.size() == 0){
                    Toast.makeText(getContext(),"Bạn đã học hết từ rồi !",Toast.LENGTH_SHORT).show();
                }else{
                    RealmList<Vocabulary> ranDomVB = vocabularyDAO.getVbRandom(vocabularies,vocabularies.size(),-1);
                    PharasesEvent pharasesEvent = new PharasesEvent(ranDomVB);
                    pharasesEvent.setTitle(levels.get(position).getName());
                    EventBus.getDefault().postSticky(pharasesEvent);
                    Intent intent = new Intent(getContext(), StudyActivity.class);
                    startActivityForResult(intent,101);
                }

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101){
            levels = levelDAO.getAllLevel();
            studyAdapter.updateList(levels);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
