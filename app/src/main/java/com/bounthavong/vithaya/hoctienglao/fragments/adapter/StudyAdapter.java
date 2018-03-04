package com.bounthavong.vithaya.hoctienglao.fragments.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bounthavong.vithaya.hoctienglao.R;
import com.bounthavong.vithaya.hoctienglao.activity.listener.ItemRecyclerClickListener;
import com.bounthavong.vithaya.hoctienglao.fragments.adapter.viewholder.CategoryVH;
import com.bounthavong.vithaya.hoctienglao.fragments.adapter.viewholder.StudyVH;
import com.bounthavong.vithaya.hoctienglao.model.Level;
import com.bounthavong.vithaya.hoctienglao.model.Vocabulary;

import io.realm.RealmList;

/**
 * Created by Boy- on 4/3/2561.
 */

public class StudyAdapter extends RecyclerView.Adapter<StudyVH>{
    RealmList<Level> levels;
    Context context;
    ItemRecyclerClickListener mitemClick;
    public StudyAdapter(RealmList<Level> levels, Context context) {
        this.levels = levels;
        this.context = context;
    }

    @Override
    public StudyVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_study, parent, false);
        return new StudyVH(itemView,context);
    }

    @Override
    public void onBindViewHolder(StudyVH holder, int position) {
        holder.bindData(levels.get(position));
        holder.setItemRecyclerClickListener(new ItemRecyclerClickListener() {
            @Override
            public void onClick(View view, int position) {
                mitemClick.onClick(view, position);
            }
        });
    }
    public void setItemClick(ItemRecyclerClickListener ItemRecyclerClickListener) {
        this.mitemClick = ItemRecyclerClickListener;
    }
    public void updateList(RealmList<Level> levels){
        this.levels = levels;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return levels.size();
    }
}
