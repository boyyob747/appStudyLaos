package com.bounthavong.vithaya.hoctienglao.fragments.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bounthavong.vithaya.hoctienglao.R;
import com.bounthavong.vithaya.hoctienglao.json.ReadJson;
import com.bounthavong.vithaya.hoctienglao.model.Category;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmList;

/**
 * Created by Boy- on 3/3/2561.
 */

public class CategoryVH extends RecyclerView.ViewHolder{
    View view;
    @BindView(R.id.icon_category)
    ImageView mImgIcon;
    @BindView(R.id.name_category)
    TextView mTxtNameCategory;
    Context context;
    public CategoryVH(View itemView,Context context) {
        super(itemView);
        view = itemView;
        this.context = context;
        ButterKnife.bind(this,itemView);
    }
    public void bindData(Category category){
        mTxtNameCategory.setText(category.getName());
//        mImgIcon.setImageDrawable(ReadJson.loadImg(context,"number.png"));
        mImgIcon.setBackgroundResource(R.drawable.);
    }
}
