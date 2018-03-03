package com.bounthavong.vithaya.hoctienglao.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bounthavong.vithaya.hoctienglao.R;
import com.bounthavong.vithaya.hoctienglao.activity.adapter.viewholder.PhraseVH;
import com.bounthavong.vithaya.hoctienglao.activity.listener.ItemRecyclerClickListener;
import com.bounthavong.vithaya.hoctienglao.model.Vocabulary;
import com.bounthavong.vithaya.hoctienglao.model.dao.VocabularyDAO;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by Boy- on 3/3/2561.
 */

public class PhrasesAdapter extends RecyclerView.Adapter<PhraseVH>{
    RealmList<Vocabulary> vocabularies;
    Context context;
    ItemRecyclerClickListener mitemClick;
    private int currentSelectedPosition = RecyclerView.NO_POSITION;
    public PhrasesAdapter(RealmList<Vocabulary> vocabularies, Context context) {
        this.vocabularies = vocabularies;
        this.context = context;
    }

    @Override
    public PhraseVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_phrase, parent, false);
        return new PhraseVH(itemView,context);
    }

    @Override
    public void onBindViewHolder(PhraseVH holder, final int position) {
        holder.bindData(vocabularies.get(position));
        holder.setItemRecyclerClickListener(new ItemRecyclerClickListener() {
            @Override
            public void onClick(View view, int position) {
                mitemClick.onClick(view, position);
                currentSelectedPosition = position;
                notifyDataSetChanged();
            }
        });
        holder.mBtnMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VocabularyDAO vocabularyDAO = new VocabularyDAO(Realm.getDefaultInstance());
                vocabularyDAO.setFav(vocabularies.get(position));
                notifyDataSetChanged();
            }
        });
        if (currentSelectedPosition == position) {
            holder.setVisibilityLayoutShow(View.VISIBLE);
            holder.setColorLayoutLA(false,vocabularies.get(position).isFavorite());
        } else {
            holder.setVisibilityLayoutShow(View.GONE);
            holder.setColorLayoutLA(true,vocabularies.get(position).isFavorite());
        }
    }
    public void setLayoutAllGone(){

    }
    @Override
    public int getItemCount() {
        return vocabularies.size();
    }
    public void setItemClick(ItemRecyclerClickListener ItemRecyclerClickListener) {
        this.mitemClick = ItemRecyclerClickListener;
    }
}
