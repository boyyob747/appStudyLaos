package com.bounthavong.vithaya.hoctienglao.fragments.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bounthavong.vithaya.hoctienglao.R;
import com.bounthavong.vithaya.hoctienglao.config.Default;
import com.bounthavong.vithaya.hoctienglao.json.ReadJson;

import com.bounthavong.vithaya.hoctienglao.model.Category;
import com.bounthavong.vithaya.hoctienglao.utils.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import at.markushi.ui.CircleButton;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryVH extends RecyclerView.ViewHolder{
    View view;
    @BindView(R.id.icon_category)
    CircleButton mImgIcon;
    @BindView(R.id.name_category)
    TextView mTxtNameCategory;
    Context context;
    public CategoryVH(View itemView,Context context) {
        super(itemView);
        view = itemView;
        this.context = context;
        ButterKnife.bind(this,itemView);
    }
    public void bindData(final Category category,int position){
        String name = itemView.getResources().getString(R.string.bai_hoc,position+1) + " " + category.getName();
        mTxtNameCategory.setText(name);
//        RequestOptions requestOptions = new RequestOptions();
//        requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        Log.d("MainActivity","url img = " + Default.URL_IMAGE + category.getIcon_img());
//        Glide.with(itemView).load(Default.URL_IMAGE + category.getIcon_img())
//                .into(mImgIcon);
    }
}
