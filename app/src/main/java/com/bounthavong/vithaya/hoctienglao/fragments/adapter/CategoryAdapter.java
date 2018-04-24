package com.bounthavong.vithaya.hoctienglao.fragments.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bounthavong.vithaya.hoctienglao.R;
import com.bounthavong.vithaya.hoctienglao.fragments.adapter.viewholder.CategoryVH;
import com.bounthavong.vithaya.hoctienglao.model.Category;


import io.realm.RealmList;

/**
 * Created by Boy- on 3/3/2561.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryVH>{
    RealmList<Category> categories;
    Context context;
    boolean isQui;
    public CategoryAdapter(RealmList<Category> categories, Context context,boolean isQui) {
        this.categories = categories;
        this.context = context;
        this.isQui = isQui;
    }

    @Override
    public CategoryVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);
        return new CategoryVH(itemView,context);
    }

    @Override
    public void onBindViewHolder(CategoryVH holder, int position) {
        holder.bindData(categories.get(position),position,isQui);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
