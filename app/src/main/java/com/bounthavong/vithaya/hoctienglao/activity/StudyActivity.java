package com.bounthavong.vithaya.hoctienglao.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bounthavong.vithaya.hoctienglao.R;
import com.bounthavong.vithaya.hoctienglao.evenbus.PharasesEvent;
import com.bounthavong.vithaya.hoctienglao.model.Vocabulary;
import com.bounthavong.vithaya.hoctienglao.model.dao.VocabularyDAO;
import com.bounthavong.vithaya.hoctienglao.utils.LAOPlayer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmList;

public class StudyActivity extends AppCompatActivity {

    private static final String TAG = StudyActivity.class.getName();
    @BindView(R.id.txt_vocabulary_karaoke)
    TextView txtVocabularyKaraoke;
    @BindView(R.id.txt_vocabulary_lao)
    TextView txtVocabularyLao;
    @BindView(R.id.txt_vocabulary_vn)
    TextView txtVocabularyVn;
    @BindView(R.id.btn_is_remember)
    Button btnIsRemember;
    @BindView(R.id.btn_is_not_remember)
    Button btnIsNotRemember;
    @BindView(R.id.btn_show)
    Button btnShow;
    @BindView(R.id.btn_play_audio)
    Button btnPlayAudio;
    private RealmList<Vocabulary> vocabularies;
    private String title = "";
    private LAOPlayer laoPlayer;
    private Vocabulary vocabulary;
    private int index = 0;
    private VocabularyDAO vocabularyDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        laoPlayer = new LAOPlayer(this);
        setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        vocabularyDAO = new VocabularyDAO(Realm.getDefaultInstance());
        if (vocabulary != null){
            txtVocabularyLao.setText(vocabulary.getVocabulary_lao());
            txtVocabularyKaraoke.setText(vocabulary.getVocabulary_karaoke());
            txtVocabularyVn.setText(vocabulary.getVocabulary_vn());
            txtVocabularyVn.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        laoPlayer.stopPlaying();
    }

    @OnClick({R.id.btn_is_remember, R.id.btn_is_not_remember, R.id.btn_show})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_is_remember:
                setData(true);
                vocabularyDAO.setRemember(vocabulary,true);
                Log.i(TAG,"is rember = "+vocabulary.isRemember());
                aswer(false);
                break;
            case R.id.btn_is_not_remember:
                vocabularyDAO.setRemember(vocabulary,false);
                setData(false);
                aswer(false);
                break;
            case R.id.btn_show:
                aswer(true);
                break;
        }
    }
    public void setData(boolean isRember){
        if (isRember){
            vocabularies = vocabularyDAO.getJustNotRemember(vocabularies);
        }
        ++index;
        if (index == vocabularies.size()){
            index = 0;
        }
        vocabulary = vocabularies.get(index);
        txtVocabularyLao.setText(vocabulary.getVocabulary_lao());
        txtVocabularyKaraoke.setText(vocabulary.getVocabulary_karaoke());
        txtVocabularyVn.setText(vocabulary.getVocabulary_vn());
        txtVocabularyVn.setVisibility(View.GONE);

    }
    public void aswer(boolean isShow){
        if (isShow){
            txtVocabularyVn.setVisibility(View.VISIBLE);
            btnShow.setVisibility(View.GONE);
            btnIsNotRemember.setVisibility(View.VISIBLE);
            btnIsRemember.setVisibility(View.VISIBLE);
        }else{
            txtVocabularyVn.setVisibility(View.GONE);
            btnShow.setVisibility(View.VISIBLE);
            btnIsNotRemember.setVisibility(View.GONE);
            btnIsRemember.setVisibility(View.GONE);
        }
    }
    
    @OnClick(R.id.btn_play_audio)
    public void onViewClicked() {
        if (vocabulary != null){
            laoPlayer.stopPlaying();
            laoPlayer.playSound(vocabulary.getSound_vocabulary());
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(PharasesEvent pharasesEvent) {
        vocabularies = pharasesEvent.getVocabularies();
        vocabulary = vocabularies.first();
        title = pharasesEvent.getTitle();
        Log.i(TAG, "vocabularies size = " + vocabularies.size());
        EventBus.getDefault().removeStickyEvent(pharasesEvent);
    }
}
