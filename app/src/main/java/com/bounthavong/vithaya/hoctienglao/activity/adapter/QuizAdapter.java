package com.bounthavong.vithaya.hoctienglao.activity.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;

import com.bounthavong.vithaya.hoctienglao.R;

import com.bounthavong.vithaya.hoctienglao.activity.adapter.viewholder.QuizVH;
import com.bounthavong.vithaya.hoctienglao.activity.listener.ItemRecyclerClickListener;
import com.bounthavong.vithaya.hoctienglao.model.Vocabulary;

import io.realm.RealmList;

/**
 * Created by Boy- on 3/3/2561.
 */

public class QuizAdapter extends RecyclerView.Adapter<QuizVH>{
    Context context;
    RealmList<Vocabulary> vocabularies;
    int indexRightRandom;
    int maxSize ;
    ItemRecyclerClickListener mitemClick;
    int positionChoosed = RecyclerView.NO_POSITION;
    boolean isAwser = false;
    boolean isNotRight = false;
    public QuizAdapter(Context context, RealmList<Vocabulary> vocabularies, int indexRightRandom, int maxSize) {
        this.context = context;
        this.vocabularies = vocabularies;
        this.indexRightRandom = indexRightRandom;
        this.maxSize = maxSize;
    }

    @Override
    public QuizVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_quiz, parent, false);
        return new QuizVH(itemView,context);
    }

    @Override
    public void onBindViewHolder(QuizVH holder, int position) {
       holder.binhData(vocabularies.get(position));
       holder.setItemRecyclerClickListener(new ItemRecyclerClickListener() {
           @Override
           public void onClick(View view, int position) {
               mitemClick.onClick(view, position);
                   positionChoosed = position;
                   isAwser = true;
                   notifyDataSetChanged();
           }
       });
       if (isAwser){
           if (position == positionChoosed){
               holder.mRadioVb.setButtonDrawable(R.drawable.ic_radio_button_checked_white_24dp);
               if (positionChoosed == indexRightRandom){
                   holder.setColorQT(R.color.green,true);
               }else{
                   holder.setColorQT(R.color.primary_dark,true);
               }
           }else{
               if (position == indexRightRandom){
                   holder.setColorQT(R.color.green,true);
                   holder.mRadioVb.setButtonDrawable(R.drawable.ic_radio_button_unchecked_white_24dp);
               }else{
                   holder.setColorQT(R.color.icons,false);
               }
           }
       }
    }
    public void setIsNotRight(boolean isNotRight){
        this.isNotRight = isNotRight;
    }
    @Override
    public int getItemCount() {
        return vocabularies.size();
    }
    public void updateList(RealmList<Vocabulary> vocabularies,int indexRightRandom){
        this.vocabularies = vocabularies;
        this.indexRightRandom = indexRightRandom;
        this.isAwser = false;
        this.positionChoosed = RecyclerView.NO_POSITION;
        this.isNotRight = false;
        notifyDataSetChanged();
    }
    public void setItemClick(ItemRecyclerClickListener ItemRecyclerClickListener) {
        this.mitemClick = ItemRecyclerClickListener;
    }
}
