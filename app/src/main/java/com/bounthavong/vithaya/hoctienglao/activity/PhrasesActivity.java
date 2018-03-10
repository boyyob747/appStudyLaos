package com.bounthavong.vithaya.hoctienglao.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bounthavong.vithaya.hoctienglao.R;
import com.bounthavong.vithaya.hoctienglao.activity.adapter.PhrasesAdapter;
import com.bounthavong.vithaya.hoctienglao.activity.listener.ItemRecyclerClickListener;
import com.bounthavong.vithaya.hoctienglao.evenbus.PharasesEvent;
import com.bounthavong.vithaya.hoctienglao.model.Vocabulary;
import com.bounthavong.vithaya.hoctienglao.utils.LAOPlayer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.Sort;

public class PhrasesActivity extends AppCompatActivity {

    private static final String TAG = PhrasesActivity.class.getName();
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_phrases)
    public RecyclerView recyclerPhrases;
    public PhrasesAdapter phrasesAdapter;
    @BindView(R.id.search_view)
    TextInputLayout searchView;
    @BindView(R.id.btn_search)
    Button btnSearch;
    @BindView(R.id.layout_search)
    LinearLayout layoutSearch;

    private RealmList<Vocabulary> vocabularies;
    private boolean isFirstClick = true;
    private int currentPosition = -1;
    private View currentView;
    private LAOPlayer laoPlayer;
    private String title = PhrasesActivity.class.getName();
    Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phrases);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        realm = Realm.getDefaultInstance();
        setTitle(title);
        setWidget();
    }

    LinearLayout linearLayoutCurrent = null;

    public void setWidget() {
        laoPlayer = new LAOPlayer(this);
        if (vocabularies != null) {
            phrasesAdapter = new PhrasesAdapter(vocabularies, this);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            DividerItemDecoration itemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
            recyclerPhrases.setHasFixedSize(true);
            recyclerPhrases.setLayoutManager(layoutManager);
            recyclerPhrases.addItemDecoration(itemDecoration);
            recyclerPhrases.setAdapter(phrasesAdapter);
            phrasesAdapter.setItemClick(new ItemRecyclerClickListener() {
                @Override
                public void onClick(View view, int position) {
                    laoPlayer.stopPlaying();
                    laoPlayer.playSound(vocabularies.get(position).getSound_vocabulary());
                }
            });
        } else {
            setTitle("Tìm kiểm");
            layoutSearch.setVisibility(View.VISIBLE);
            if(searchView.requestFocus()) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
            btnSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String keyWord = searchView.getEditText().getText().toString();
                    RealmResults<Vocabulary> realmResults = realm.where(Vocabulary.class).contains("vocabulary_vn", keyWord, Case.INSENSITIVE).findAllSortedAsync("vocabulary_vn", Sort.ASCENDING);
                        vocabularies = new RealmList<>();
                        vocabularies.addAll(realmResults);
                        if (vocabularies.size() == 0){
                            Snackbar.make(view,"Không có từ này",Snackbar.LENGTH_SHORT).show();
                        }else{
                            phrasesAdapter = new PhrasesAdapter(vocabularies, getApplicationContext());
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                            DividerItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext(), layoutManager.getOrientation());
                            recyclerPhrases.setHasFixedSize(true);
                            recyclerPhrases.setLayoutManager(layoutManager);
                            recyclerPhrases.addItemDecoration(itemDecoration);
                            recyclerPhrases.setAdapter(phrasesAdapter);
                            phrasesAdapter.setItemClick(new ItemRecyclerClickListener() {
                                @Override
                                public void onClick(View view, int position) {
                                    laoPlayer.stopPlaying();
                                    laoPlayer.playSound(vocabularies.get(position).getSound_vocabulary());
                                }
                            });
                        }
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        laoPlayer.stopPlaying();
        stopTimerTask();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    //
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(PharasesEvent pharasesEvent) {
        vocabularies = pharasesEvent.getVocabularies();
        title = pharasesEvent.getTitle();
        Log.i(TAG, "vocabularies size = " + vocabularies.size());
        EventBus.getDefault().removeStickyEvent(pharasesEvent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_play, menu);
        return true;
    }

    Timer timer;
    TimerTask timerTask;
    int index = 0;
    final Handler handler = new Handler();

    private void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                //use a handler to run process
                handler.post(new Runnable() {
                    public void run() {
                        recyclerPhrases.findViewHolderForAdapterPosition(index).itemView.performClick();
                        index = index + 1;
                        if (index == vocabularies.size()) {
                            stopTimerTask();
                        }
                    }
                });
            }
        };
    }

    private void startTimer() {
        timer = new Timer();
        initializeTimerTask();
        timer.schedule(timerTask, 0, 3000); //
    }

    private void stopTimerTask() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void auto() {
        index = 0;
        startTimer();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_play:
                auto();
                return true;
            default:
                finish();
                return true;
        }
    }
}
