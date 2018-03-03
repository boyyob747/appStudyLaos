package com.bounthavong.vithaya.hoctienglao.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bounthavong.vithaya.hoctienglao.R;
import com.bounthavong.vithaya.hoctienglao.fragments.adapter.LevelAdapter;
import com.bounthavong.vithaya.hoctienglao.json.ReadJson;
import com.bounthavong.vithaya.hoctienglao.model.Level;
import com.bounthavong.vithaya.hoctienglao.model.dao.LevelDAO;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.Realm;
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
    private LevelAdapter levelAdapter;
    RealmResults<Level> levels ;
    Realm realm;
    public static PhrasesFragment newInstance() {
        PhrasesFragment fragment = new PhrasesFragment();
        return fragment;
    }

    public PhrasesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_phrases, container, false);
        unbinder = ButterKnife.bind(this, view);
        realm = Realm.getDefaultInstance();
        setWidget();
        return view;
    }

    private void setWidget() {
        LevelDAO levelDAO = new LevelDAO(realm);
        levelDAO.saveData(ReadJson.loadJSONFromAsset(getActivity()));
        levels = realm.where(Level.class).findAll();
        Log.i(TAG,levels.toString());
        levelAdapter = new LevelAdapter(levels,getContext());
        recyclerLevel.setHasFixedSize(true);
        recyclerLevel.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerLevel.addItemDecoration(new VerticalLineDecorator(2));
        recyclerLevel.setAdapter(levelAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
