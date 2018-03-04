package com.bounthavong.vithaya.hoctienglao.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.bounthavong.vithaya.hoctienglao.R;
import com.bounthavong.vithaya.hoctienglao.activity.adapter.PhrasesAdapter;
import com.bounthavong.vithaya.hoctienglao.activity.listener.ItemRecyclerClickListener;
import com.bounthavong.vithaya.hoctienglao.evenbus.PharasesEvent;
import com.bounthavong.vithaya.hoctienglao.fragments.adapter.listener.LAOTouchListener;
import com.bounthavong.vithaya.hoctienglao.model.Vocabulary;
import com.bounthavong.vithaya.hoctienglao.utils.LAOPlayer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmList;

public class PhrasesActivity extends AppCompatActivity {

    private static final String TAG = PhrasesActivity.class.getName();
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_phrases)
    public RecyclerView recyclerPhrases;
    public PhrasesAdapter phrasesAdapter ;
    private RealmList<Vocabulary> vocabularies;
    private boolean isFirstClick = true;
    private int currentPosition = -1;
    private View currentView;
    private LAOPlayer laoPlayer;
    private String title  = PhrasesActivity.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phrases);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(title);
        setWidget();
    }
    LinearLayout linearLayoutCurrent = null;

    public void setWidget() {
        laoPlayer = new LAOPlayer(this);
        if (vocabularies != null){
            phrasesAdapter = new PhrasesAdapter(vocabularies,this);
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
        Log.i(TAG,"vocabularies size = " + vocabularies.size());
        EventBus.getDefault().removeStickyEvent(pharasesEvent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_play,menu);
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
                        if (index == vocabularies.size()){
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
    public void auto(){
        index = 0;
        startTimer();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_play :
                auto();
                return true;
            default:
                finish();
                return true;
        }
    }
}
