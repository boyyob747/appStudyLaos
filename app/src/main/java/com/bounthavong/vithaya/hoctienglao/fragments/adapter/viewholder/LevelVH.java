package com.bounthavong.vithaya.hoctienglao.fragments.adapter.viewholder;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.bounthavong.vithaya.hoctienglao.R;
import com.bounthavong.vithaya.hoctienglao.activity.PhrasesActivity;
import com.bounthavong.vithaya.hoctienglao.evenbus.PharasesEvent;
import com.bounthavong.vithaya.hoctienglao.fragments.adapter.CategoryAdapter;
import com.bounthavong.vithaya.hoctienglao.fragments.adapter.listener.LAOTouchListener;
import com.bounthavong.vithaya.hoctienglao.model.Level;

import org.greenrobot.eventbus.EventBus;

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
    public void bindData(final Level level){
        mTxtName.setText(level.getName());
        categoryAdapter = new CategoryAdapter(level.getCategories(),context);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 2);
        mRecyclerCategory.setLayoutManager(mLayoutManager);
        mRecyclerCategory.setItemAnimator(new DefaultItemAnimator());
        mRecyclerCategory.setAdapter(categoryAdapter);
        mRecyclerCategory.addOnItemTouchListener(new LAOTouchListener(context, mRecyclerCategory, new LAOTouchListener.OnTouchActionListener() {
            @Override
            public void onLeftSwipe(View view, int position) {

            }

            @Override
            public void onRightSwipe(View view, int position) {

            }

            @Override
            public void onClick(View view, int position) {
                PharasesEvent pharasesEvent = new PharasesEvent(level.getCategories().get(position).getVocabularies());
                EventBus.getDefault().postSticky(pharasesEvent);
                Intent intent = new Intent(context, PhrasesActivity.class);
                context.startActivity(intent);
            }
        }));
    }
}
