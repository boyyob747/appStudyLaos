package com.bounthavong.vithaya.hoctienglao.fragments.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bounthavong.vithaya.hoctienglao.R;
import com.bounthavong.vithaya.hoctienglao.fragments.adapter.viewholder.LevelVH;
import com.bounthavong.vithaya.hoctienglao.model.Level;

import io.realm.RealmResults;

public class LevelAdapter extends RecyclerView.Adapter<LevelVH>{
    RealmResults<Level> levels;
    Context context;
    public LevelAdapter(RealmResults<Level> levels, Context context) {
        this.levels = levels;
        this.context = context;
    }

    @Override
    public LevelVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_level, parent, false);

        return new LevelVH(itemView,context);
    }

    @Override
    public void onBindViewHolder(LevelVH holder, int position) {
        holder.bindData(levels.get(position));
    }

    @Override
    public int getItemCount() {
        return levels.size();
    }
}
