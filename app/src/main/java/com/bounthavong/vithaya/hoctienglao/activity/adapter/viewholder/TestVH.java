package com.bounthavong.vithaya.hoctienglao.activity.adapter.viewholder;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bounthavong.vithaya.hoctienglao.R;
import com.bounthavong.vithaya.hoctienglao.model.Question;
import com.bounthavong.vithaya.hoctienglao.model.Vocabulary;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmList;

public class TestVH extends RecyclerView.ViewHolder {
    @BindView(R.id.radio_1)
    public RadioButton radio1;
    @BindView(R.id.radio_2)
    public RadioButton radio2;
    @BindView(R.id.radio_3)
    public RadioButton radio3;
    @BindView(R.id.radio_4)
    public RadioButton radio4;
    @BindView(R.id.group_aswser)
    public RadioGroup groupAswser;
    @BindView(R.id.txt_question)
    TextView mTxtQuestion;

    public TestVH(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }

    public void bindData(Vocabulary vocabulary, int position, RealmList<Vocabulary> vocabulariesQuestion) {
        mTxtQuestion.setText((position + 1) + " . " + vocabulary.getVocabulary_lao() + " là gì trong tiếng việt ? ");
        radio1.setText(vocabulariesQuestion.get(0).getVocabulary_vn());
        radio2.setText(vocabulariesQuestion.get(1).getVocabulary_vn());
        radio3.setText(vocabulariesQuestion.get(2).getVocabulary_vn());
        radio4.setText(vocabulariesQuestion.get(3).getVocabulary_vn());
        radio1.setTextColor(Color.BLACK);
        radio2.setTextColor(Color.BLACK);
        radio3.setTextColor(Color.BLACK);
        radio4.setTextColor(Color.BLACK);

        radio1.setButtonDrawable(R.drawable.checkbox);
        radio2.setButtonDrawable(R.drawable.checkbox);
        radio3.setButtonDrawable(R.drawable.checkbox);
        radio4.setButtonDrawable(R.drawable.checkbox);
    }
}
