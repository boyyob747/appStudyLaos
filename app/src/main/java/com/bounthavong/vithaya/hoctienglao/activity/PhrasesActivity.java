package com.bounthavong.vithaya.hoctienglao.activity;

import android.os.Bundle;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmList;

public class PhrasesActivity extends AppCompatActivity {

    private static final String TAG = PhrasesActivity.class.getName();
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_phrases)
    RecyclerView recyclerPhrases;
    private PhrasesAdapter phrasesAdapter ;
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

    private void setWidget() {
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
                    laoPlayer.playSound(vocabularies.get(position).getSound_vocabulary());
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_play :
                Snackbar.make(getCurrentFocus(),"Cliked",Snackbar.LENGTH_LONG).show();
                return true;
            default:
                finish();
                return true;
        }
    }
}
