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
import com.bounthavong.vithaya.hoctienglao.model.Level;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {
    private static final String TAG = BaseFragment.class.getName();
    View view;
    @BindView(R.id.recycler_level)
    RecyclerView recyclerLevel;
    Unbinder unbinder;
    private LevelAdapter levelAdapter;
    RealmResults<Level> levels ;
    Realm realm;
    public static BaseFragment newInstance() {
        BaseFragment fragment = new BaseFragment();
        return fragment;
    }

    public BaseFragment() {
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
