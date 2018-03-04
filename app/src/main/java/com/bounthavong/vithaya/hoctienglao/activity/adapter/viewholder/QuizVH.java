package com.bounthavong.vithaya.hoctienglao.activity.adapter.viewholder;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bounthavong.vithaya.hoctienglao.R;
import com.bounthavong.vithaya.hoctienglao.activity.listener.ItemRecyclerClickListener;
import com.bounthavong.vithaya.hoctienglao.model.Vocabulary;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Boy- on 3/3/2561.
 */

public class QuizVH extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.radio_vocabulary)
    public RadioButton mRadioVb;
    @BindView(R.id.txt_vocabulary_vn)
    TextView mTxtVbVN;
    @BindView(R.id.layout_question)
    public ConstraintLayout mLayoutQT;
    public ItemRecyclerClickListener mitemClick;
    Context context;
    View view;
    public QuizVH(View itemView, Context context) {
        super(itemView);
        view = itemView;
        this.context = context;
        ButterKnife.bind(this,itemView);
        itemView.setOnClickListener(this);
        mRadioVb.setOnClickListener(this);
    }
    public void binhData(Vocabulary vocabulary){
        mTxtVbVN.setText(vocabulary.getVocabulary_vn());
    }
    public void setItemRecyclerClickListener(ItemRecyclerClickListener itemRecyclerClickListener) {
        this.mitemClick = itemRecyclerClickListener;
    }
    public void setColorQT(int idColor,boolean isNeedWhite){
        mLayoutQT.setBackgroundColor(view.getResources().getColor(idColor));
        if (isNeedWhite){
            mTxtVbVN.setTextColor(view.getResources().getColor(R.color.icons));
        }
    }
    @Override
    public void onClick(View view) {
        this.mitemClick.onClick(view, getAdapterPosition());
    }
}
