package com.bounthavong.vithaya.hoctienglao.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;

import com.bounthavong.vithaya.hoctienglao.R;
import com.bounthavong.vithaya.hoctienglao.activity.adapter.TestAdapter;
import com.bounthavong.vithaya.hoctienglao.evenbus.PharasesEvent;
import com.bounthavong.vithaya.hoctienglao.model.Level;
import com.bounthavong.vithaya.hoctienglao.model.Question;
import com.bounthavong.vithaya.hoctienglao.model.Vocabulary;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.RealmList;

public class TestActivity extends AppCompatActivity {

    private static final String TAG = TestActivity.class.getName();
    @BindView(R.id.recycler_test)
    RecyclerView recyclerTest;
    @BindView(R.id.sub_mit)
    Button subMit;
    private RealmList<Vocabulary> vocabularies;
    private TestAdapter testAdapter;
    public static ArrayList<Question> questions = new ArrayList<>();
    private Level level;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.kiem_tra));
        setAdapter();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private void setAdapter() {
        testAdapter = new TestAdapter(vocabularies, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        recyclerTest.setHasFixedSize(true);
        recyclerTest.setLayoutManager(layoutManager);
        recyclerTest.addItemDecoration(itemDecoration);
        recyclerTest.setAdapter(testAdapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(PharasesEvent pharasesEvent) {
        vocabularies = pharasesEvent.getVocabularies();
        level = pharasesEvent.getLevel();
        Log.wtf(TAG, vocabularies.toString());
        EventBus.getDefault().removeStickyEvent(pharasesEvent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.sub_mit)
    public void onViewClicked() {
        testAdapter.submit(level);
    }
}
