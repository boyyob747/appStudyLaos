package com.bounthavong.vithaya.hoctienglao.fragments.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import android.view.View;
import android.widget.TextView;

import com.bounthavong.vithaya.hoctienglao.R;
import com.bounthavong.vithaya.hoctienglao.json.ReadJson;
import com.bounthavong.vithaya.hoctienglao.model.Category;
import com.bounthavong.vithaya.hoctienglao.utils.Utils;

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
    public void bindData(final Category category){
        mTxtNameCategory.setText(category.getName());
//        mImgIcon.setImageDrawable(view.getResources().getDrawable(R.drawable.ic_bookmark_red_300_24dp));
        mImgIcon.setImageResource(Utils.getIdimgWithString(category.getIcon_img(),context));
    }
}
