package com.bounthavong.vithaya.hoctienglao.fragments.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bounthavong.vithaya.hoctienglao.R;
import com.bounthavong.vithaya.hoctienglao.activity.listener.ItemRecyclerClickListener;
import com.bounthavong.vithaya.hoctienglao.model.Level;
import com.bounthavong.vithaya.hoctienglao.model.dao.VocabularyDAO;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * Created by Boy- on 4/3/2561.
 */

public class StudyVH extends RecyclerView.ViewHolder implements View.OnClickListener {
    View view;
    @BindView(R.id.name_level)
    TextView mTxtNameLevel;
    @BindView(R.id.count_vb_not_remember)
    TextView mTxtCountVB;
    Context context;
    VocabularyDAO vocabularyDAO;
    public ItemRecyclerClickListener mitemClick;
    public StudyVH(View itemView,Context context) {
        super(itemView);
        view = itemView;
        this.context = context;
        vocabularyDAO = new VocabularyDAO(Realm.getDefaultInstance());
        ButterKnife.bind(this,itemView);
    }
    public void bindData(final Level level){
        mTxtNameLevel.setText(level.getName());
        if (level.getDiem() != -1){
            mTxtCountVB.setText(context.getResources().getString(R.string.ket_qua_kiem_tra_la,level.getDiem()));
        }else{
            mTxtCountVB.setText(context.getResources().getString(R.string.ban_chua_kiem_tra));
        }

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        this.mitemClick.onClick(view, getAdapterPosition());
    }
    public void setItemRecyclerClickListener(ItemRecyclerClickListener itemRecyclerClickListener) {
        this.mitemClick = itemRecyclerClickListener;
    }
}
