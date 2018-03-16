package com.bounthavong.vithaya.hoctienglao.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bounthavong.vithaya.hoctienglao.R;
import com.bounthavong.vithaya.hoctienglao.activity.adapter.QuizAdapter;
import com.bounthavong.vithaya.hoctienglao.activity.listener.ItemRecyclerClickListener;
import com.bounthavong.vithaya.hoctienglao.evenbus.PharasesEvent;
import com.bounthavong.vithaya.hoctienglao.model.Vocabulary;
import com.bounthavong.vithaya.hoctienglao.model.dao.VocabularyDAO;
import com.bounthavong.vithaya.hoctienglao.utils.LAOPlayer;
import com.bounthavong.vithaya.hoctienglao.utils.Utils;
import com.daimajia.numberprogressbar.NumberProgressBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import io.realm.Realm;
import io.realm.RealmList;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = QuizActivity.class.getName();
    @BindView(R.id.txt_vocabulary_karaoke)
    TextView txtVocabularyKaraoke;
    @BindView(R.id.txt_vocabulary_lao)
    TextView txtVocabularyLao;
    @BindView(R.id.recycler_quiz)
    RecyclerView recyclerQuiz;
    @BindView(R.id.txt_point_right)
    TextView txtPointRight;
    @BindView(R.id.txt_point_unright)
    TextView txtPointUnright;
    @BindView(R.id.btn_play_audio)
    Button btnPlayAudio;
    @BindView(R.id.layout_play_audio)
    LinearLayout layoutPlayAudio;
    @BindView(R.id.layout_right)
    LinearLayout layoutRight;
    @BindView(R.id.number_progress_bar)
    NumberProgressBar numberProgressBar;
    private RealmList<Vocabulary> vocabularies;
    private String title;
    private QuizAdapter quizAdapter;
    private VocabularyDAO vocabularyDAO;
    private RealmList<Vocabulary> vocabulariesRandom;
    private RealmList<Vocabulary> vocabulariesQuestion = new RealmList<>();
    private int indexRightRandom = 0;
    String soundVb = "";
    LAOPlayer laoPlayer;
    Vocabulary vocabulary;
    int maxSize = 0;
    int questionRight = 0;
    int questionNotRight = 0;
    int indexNext = 0;
    boolean isNotRight = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setWidget();
        maxSize = Utils.getSizeHeightQuestion(getWindowManager().getDefaultDisplay().getHeight(), (Utils.getViewHeight(layoutRight) + Utils.getViewHeight(layoutRight)));
    }

    private void setWidget() {
        laoPlayer = new LAOPlayer(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        recyclerQuiz.setHasFixedSize(true);
        recyclerQuiz.setLayoutManager(layoutManager);
        recyclerQuiz.addItemDecoration(itemDecoration);
        vocabularyDAO = new VocabularyDAO(Realm.getDefaultInstance());
        vocabulariesRandom = vocabularyDAO.getVbRandom(vocabularies, vocabularies.size() < 10 ? vocabularies.size() : 10,-1);
        numberProgressBar.setMax(vocabulariesRandom.size() - 1);
        Log.i(TAG,"vocabulariesRandom size = "+vocabulariesRandom.size());
        Log.i(TAG,"vocabulariesRandom "+vocabulariesRandom.toString());
        vocabulary = vocabulariesRandom.first();
        getQuestion();
        btnPlayAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                laoPlayer.stopPlaying();
                laoPlayer.playUrl(soundVb);
            }
        });

    }

    private void showAlert() {
        SweetAlertDialog alertDialog;
        if (questionRight == 0){
            alertDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
        }else{
            alertDialog = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE);
        }

        alertDialog.setTitleText(getResources().getString(R.string.cau_do_da_hoan_thanh))
                .setContentText(getResources().getString(R.string.ban_da_tren_dap_an_dunng,questionRight,vocabulariesRandom.size()))
                .setConfirmText("OK")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismiss();
                        finish();
                    }
                }).show();
    }

    public void countQuestionRight() {
        txtPointRight.setText("" + questionRight);
        txtPointUnright.setText("" + questionNotRight);
    }

    public void getQuestion() {
        txtVocabularyLao.setText(vocabulary.getVocabulary_lao());
        txtVocabularyKaraoke.setText(vocabulary.getVocabulary_karaoke());
        soundVb = vocabulary.getSound_vocabulary();
        indexRightRandom = Utils.randInt(0, 3);
        vocabulariesQuestion = vocabularyDAO.getVbRandom(vocabulariesRandom, 4,indexNext);
        vocabulariesQuestion.set(indexRightRandom, vocabulary);
        quizAdapter = new QuizAdapter(this,vocabulariesQuestion,indexRightRandom,0);
        recyclerQuiz.setAdapter(quizAdapter);
        quizAdapter.setItemClick(new ItemRecyclerClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (isNotRight){
                    isNotRight = false;
                    getQuestion();
                }else{
                    ++indexNext;
                    numberProgressBar.setProgress(indexNext);
                    if (indexNext == vocabulariesRandom.size()){
                        showAlert();
                    }else{
                        ((RadioButton) view.findViewById(R.id.radio_vocabulary)).setChecked(true);
                        vocabulary = vocabulariesRandom.get(indexNext);

                        if (vocabulariesQuestion.get(position) == vocabulariesQuestion.get(indexRightRandom)) {
                            Snackbar.make(view, "Đúng!", 250).show();
                            ++questionRight;
                            getQuestion();
                        } else {
                            ++questionNotRight;
                            isNotRight = true;
                            quizAdapter.setIsNotRight(true);
                        }
                        countQuestionRight();
                    }
                }
            }
        });
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

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(PharasesEvent pharasesEvent) {
        vocabularies = pharasesEvent.getVocabularies();
        title = pharasesEvent.getTitle();
        Log.i(TAG, "vocabularies size = " + vocabularies.size());
        EventBus.getDefault().removeStickyEvent(pharasesEvent);
    }
}
