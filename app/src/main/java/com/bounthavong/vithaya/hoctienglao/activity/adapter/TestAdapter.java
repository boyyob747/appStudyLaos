package com.bounthavong.vithaya.hoctienglao.activity.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bounthavong.vithaya.hoctienglao.R;
import com.bounthavong.vithaya.hoctienglao.activity.TestActivity;
import com.bounthavong.vithaya.hoctienglao.activity.adapter.viewholder.TestVH;
import com.bounthavong.vithaya.hoctienglao.model.Level;
import com.bounthavong.vithaya.hoctienglao.model.Question;
import com.bounthavong.vithaya.hoctienglao.model.Vocabulary;
import com.bounthavong.vithaya.hoctienglao.model.dao.LevelDAO;
import com.bounthavong.vithaya.hoctienglao.model.dao.VocabularyDAO;
import com.bounthavong.vithaya.hoctienglao.utils.Utils;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.realm.Realm;
import io.realm.RealmList;

public class TestAdapter extends RecyclerView.Adapter<TestVH>{
    private static final String TAG = TestAdapter.class.getName();
    private RealmList<Vocabulary> vocabularies;
    private Context context;
    private VocabularyDAO vocabularyDAO;
    private ArrayList<Question> questions = new ArrayList<>();
    private ArrayList< RealmList<Vocabulary>> listsQuestion = new ArrayList<>();
    private ArrayList<Integer> indexRightRandomLists = new ArrayList<>();
    public TestAdapter(RealmList<Vocabulary> vocabularies, Context context) {
        this.vocabularies = vocabularies;
        this.context = context;
        vocabularyDAO = new VocabularyDAO(Realm.getDefaultInstance());
        int i = 0;
        for (Vocabulary vocabulary : vocabularies){
            questions.add(new Question());
            int indexRightRandom = Utils.randInt(0, 3);
            indexRightRandomLists.add(indexRightRandom);
            RealmList<Vocabulary> vocabulariesQuestion = vocabularyDAO.getVbRandom(vocabularies, 4,i);
            vocabulariesQuestion.set(indexRightRandom, vocabulary);
            listsQuestion.add(vocabulariesQuestion);
            ++i;
        }
    }

    @Override
    public TestVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_test_question, parent, false);
        return new TestVH(itemView);
    }

    @Override
    public void onBindViewHolder(TestVH holder, int position) {
        holder.groupAswser.setTag(position);
        Question question = questions.get(position);
        if (question.isChecked()){
            holder.groupAswser.check(question.getId());
        }else{
            holder.groupAswser.check(-1);
        }
        holder.groupAswser.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int pos = (int) radioGroup.getTag();
                Question question1 = questions.get(pos);
                question1.setId(i);
                question1.setChecked(true);
            }
        });
        switch (indexRightRandomLists.get(position)) {
            case 0:
                questions.get(position).setIdCorrect(holder.radio1.getId());
                break;
            case 1:
                questions.get(position).setIdCorrect(holder.radio2.getId());
                break;
            case 2:
                questions.get(position).setIdCorrect(holder.radio3.getId());
                break;
            case 3:
                questions.get(position).setIdCorrect(holder.radio4.getId());
                break;
        }

        holder.bindData(vocabularies.get(position),position,listsQuestion.get(position));
        if (question.getIsCorrect() != -1){
            if (question.getIsCorrect() == 0){
                if (question.getId() == holder.radio1.getId()){
                    holder.radio1.setButtonDrawable(R.drawable.false_check);
                    holder.radio1.setTextColor(Color.RED);
                }else if (question.getId() == holder.radio2.getId()){
                    holder.radio2.setButtonDrawable(R.drawable.false_check);
                    holder.radio2.setTextColor(Color.RED);
                }else if (question.getId() == holder.radio3.getId()){
                    holder.radio3.setButtonDrawable(R.drawable.false_check);
                    holder.radio3.setTextColor(Color.RED);
                }else if (question.getId() == holder.radio4.getId()){
                    holder.radio4.setButtonDrawable(R.drawable.false_check);
                    holder.radio4.setTextColor(Color.RED);
                }
            }else{
                if (question.getId() == holder.radio1.getId()){
                    holder.radio1.setTextColor(Color.GREEN);
                }else if (question.getId() == holder.radio2.getId()){
                    holder.radio2.setTextColor(Color.GREEN);
                }else if (question.getId() == holder.radio3.getId()){
                    holder.radio3.setTextColor(Color.GREEN);
                }else if (question.getId() == holder.radio4.getId()){
                    holder.radio4.setTextColor(Color.GREEN);
                }
            }
        }
    }
    public void submit(Level level){
        int sum = 0;
        for (Question question : questions){
            if (question.getId() == question.getIdCorrect()){
                question.setIsCorrect(1);
                ++sum;
            }else{
                question.setIsCorrect(0);
            }
        }
        LevelDAO levelDAO = new LevelDAO(Realm.getDefaultInstance());

        levelDAO.setDiem((10*sum)/questions.size(),level);
        notifyDataSetChanged();
        int questionRight = sum;
        SweetAlertDialog alertDialog;
        if (questionRight == 0){
            alertDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
        }else{
            alertDialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
        }
        alertDialog.setTitleText(context.getResources().getString(R.string.kiem_tra_da_hoan_thanh))
                .setContentText(context.getResources().getString(R.string.ban_da_tren_dap_an_dunng,questionRight,questions.size()))
                .setConfirmText("OK")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismiss();
                    }
                }).show();
    }
    @Override
    public int getItemCount() {
        return vocabularies.size();
    }
}
