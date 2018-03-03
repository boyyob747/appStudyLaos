package com.bounthavong.vithaya.hoctienglao;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import android.view.MenuItem;
import android.widget.TextView;

import com.bounthavong.vithaya.hoctienglao.fragments.PhrasesFragment;

import io.realm.Realm;
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private TextView mTextMessage;
    private Realm realm;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    selectedFragment = PhrasesFragment.newInstance();
                    break;
                case R.id.navigation_dashboard:
                    selectedFragment = PhrasesFragment.newInstance();
                    break;
                case R.id.navigation_notifications:
                    selectedFragment = PhrasesFragment.newInstance();
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
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, PhrasesFragment.newInstance());
        transaction.addToBackStack(null);
        transaction.commit();
        navigation.setSelectedItemId(R.id.navigation_home);
    }

}
