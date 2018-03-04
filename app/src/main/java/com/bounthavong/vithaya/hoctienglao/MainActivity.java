package com.bounthavong.vithaya.hoctienglao;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import android.view.MenuItem;

import com.bounthavong.vithaya.hoctienglao.config.Default;
import com.bounthavong.vithaya.hoctienglao.fragments.PhrasesFragment;
import com.bounthavong.vithaya.hoctienglao.json.ReadJson;
import com.bounthavong.vithaya.hoctienglao.model.dao.LevelDAO;

import io.realm.Realm;
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    BottomNavigationView navigation;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    selectedFragment = PhrasesFragment.newInstance(false);
                    break;
                case R.id.navigation_dashboard:
                    selectedFragment = PhrasesFragment.newInstance(true);
                    break;
                case R.id.navigation_notifications:
                    selectedFragment = PhrasesFragment.newInstance(false);
                    break;
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout,selectedFragment);
            transaction.addToBackStack(null);
            transaction.commit();
            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = getSharedPreferences(Default.SAVE_APP, Context.MODE_PRIVATE);
        boolean isFirstRun = sp.getBoolean(Default.IS_FIRST_RUN,true);
        setContentView(R.layout.activity_main);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if (isFirstRun){
            SharedPreferences.Editor editor = sp.edit();
            LevelDAO levelDAO = new LevelDAO(Realm.getDefaultInstance());
            levelDAO.saveData(ReadJson.loadJSONFromAsset(this));
            editor.putBoolean(Default.IS_FIRST_RUN,false);
            editor.commit();
        }
        changeFragment();
    }

    private void changeFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, PhrasesFragment.newInstance(false));
        transaction.addToBackStack(null);
        transaction.commit();
        navigation.setSelectedItemId(R.id.navigation_home);
    }

}
