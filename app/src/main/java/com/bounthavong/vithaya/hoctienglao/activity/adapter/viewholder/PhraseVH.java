package com.bounthavong.vithaya.hoctienglao.activity.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bounthavong.vithaya.hoctienglao.R;
import com.bounthavong.vithaya.hoctienglao.activity.listener.ItemRecyclerClickListener;
import com.bounthavong.vithaya.hoctienglao.model.Vocabulary;
import com.bounthavong.vithaya.hoctienglao.utils.LAOPlayer;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Boy- on 3/3/2561.
 */

public class PhraseVH extends RecyclerView.ViewHolder implements View.OnClickListener{
    Context context;
    View view;
    @BindView(R.id.layout_vb_lao)
    LinearLayout mLayoutVbLao;
    @BindView(R.id.btn_mark)
    Button mBtnMark;
    @BindView(R.id.txt_vocabulary_vn)
    TextView mTxtVbVN;
    @BindView(R.id.layout_vb_show)
    LinearLayout mLayoutVbShow;
    @BindView(R.id.txt_vocabulary_karaoke)
    TextView mTxtVbKara;
    @BindView(R.id.btn_play_audio)
    Button mBtnPlayAudio;
    @BindView(R.id.txt_vocabulary_lao)
    TextView mTxtVbLao;
    public ItemRecyclerClickListener mitemClick;
    LAOPlayer laoPlayer;
    public PhraseVH(View itemView,Context context) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        this.context = context;
        this.view = itemView;
        laoPlayer = new LAOPlayer(context);
        itemView.setOnClickListener(this);
    }
    public void bindData(final Vocabulary vocabulary){

        mTxtVbLao.setText(vocabulary.getVocabulary_lao());
        mTxtVbKara.setText(vocabulary.getVocabulary_karaoke());
        mTxtVbVN.setText(vocabulary.getVocabulary_vn());
        mBtnPlayAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                laoPlayer.playSound(vocabulary.getSound_vocabulary());
            }
        });
    }

    @Override
    public void onClick(View view) {
        this.mitemClick.onClick(view, getAdapterPosition());
    }
    public void setItemRecyclerClickListener(ItemRecyclerClickListener itemRecyclerClickListener) {
        this.mitemClick = itemRecyclerClickListener;
    }
    public void setVisibilityLayoutShow(int visi){
        mLayoutVbShow.setVisibility(visi);
    }
}
