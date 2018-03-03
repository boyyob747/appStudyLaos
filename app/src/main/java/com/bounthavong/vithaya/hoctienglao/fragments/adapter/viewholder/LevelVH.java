package com.bounthavong.vithaya.hoctienglao.fragments.adapter.viewholder;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.bounthavong.vithaya.hoctienglao.R;
import com.bounthavong.vithaya.hoctienglao.fragments.adapter.CategoryAdapter;
import com.bounthavong.vithaya.hoctienglao.model.Level;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Boy- on 3/3/2561.
 */

public class LevelVH extends RecyclerView.ViewHolder{
    @BindView(R.id.name_level)
    TextView mTxtName;
    @BindView(R.id.recycler_category)
    RecyclerView mRecyclerCategory;
    Context context;
    CategoryAdapter categoryAdapter;
    public LevelVH(View itemView,Context context) {
        super(itemView);
        this.context = context;
        ButterKnife.bind(this,itemView);
    }
    public void bindData(Level level){
        mTxtName.setText(level.getName());
        categoryAdapter = new CategoryAdapter(level.getCategories(),context);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 2);
        mRecyclerCategory.setLayoutManager(mLayoutManager);
//        mRecyclerCategory.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        mRecyclerCategory.setItemAnimator(new DefaultItemAnimator());
        mRecyclerCategory.setAdapter(categoryAdapter);
    }

    private int dpToPx(int dp) {
        Resources r = itemView.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
