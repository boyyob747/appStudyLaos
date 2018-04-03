package com.bounthavong.vithaya.hoctienglao;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.bounthavong.vithaya.hoctienglao.activity.PhrasesActivity;
import com.bounthavong.vithaya.hoctienglao.config.Default;
import com.bounthavong.vithaya.hoctienglao.fragments.PhrasesFragment;
import com.bounthavong.vithaya.hoctienglao.fragments.StudyFragment;
import com.bounthavong.vithaya.hoctienglao.json.ReadJson;
import com.bounthavong.vithaya.hoctienglao.model.Vocabulary;
import com.bounthavong.vithaya.hoctienglao.model.dao.LevelDAO;
import com.bounthavong.vithaya.hoctienglao.model.dao.VocabularyDAO;
import com.bounthavong.vithaya.hoctienglao.webservices.RetrofitClient;
import com.bounthavong.vithaya.hoctienglao.webservices.model.Data;
import com.bounthavong.vithaya.hoctienglao.webservices.model.dao.DataDAO;
import com.thin.downloadmanager.DefaultRetryPolicy;
import com.thin.downloadmanager.DownloadRequest;
import com.thin.downloadmanager.DownloadStatusListener;
import com.thin.downloadmanager.ThinDownloadManager;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.ResourceObserver;
import io.realm.Realm;
import io.realm.RealmList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private CompositeDisposable composite = new CompositeDisposable();
    BottomNavigationView navigation;
    @BindView(R.id.avi_loding)
    AVLoadingIndicatorView aviLoding;
    private ThinDownloadManager downloadManager;
    private int index = 0;
    VocabularyDAO vocabularyDAO;
    private RealmList<Vocabulary> vocabularies = new RealmList<>();
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
                    selectedFragment = StudyFragment.newInstance();
                    break;
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, selectedFragment);
            transaction.addToBackStack(null);
            transaction.commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = getSharedPreferences(Default.SAVE_APP, Context.MODE_PRIVATE);
        boolean isFirstRun = sp.getBoolean(Default.IS_FIRST_RUN, true);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        downloadManager = new ThinDownloadManager();
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//        TextView textView = (TextView) navigation.findViewById(R.id.navigation_home).findViewById(R.id.largeLabel);
//        textView.setTextSize(16);
        if (isFirstRun) {
            Toast.makeText(this,"Đang tải dữ liệu hãy đợi",Toast.LENGTH_LONG).show();
            aviLoding.show();
            downloadData();
        }else{
            aviLoding.hide();
            changeFragment();
        }
    }
    private void saveData(List<Data> datas){
        LevelDAO levelDAO = new LevelDAO(Realm.getDefaultInstance());
        levelDAO.saveData(datas);
        vocabularyDAO = new VocabularyDAO(Realm.getDefaultInstance());
        vocabularies.addAll(vocabularyDAO.getAllVocabulary());
        if (vocabularies.size() != 0){
            downloadMp3(vocabularies.get(index));
        }
    }
    private void changeIsFristRun(){
        SharedPreferences sp = getSharedPreferences(Default.SAVE_APP, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(Default.IS_FIRST_RUN, false);
        editor.commit();
        changeFragment();
    }
    private void downloadData() {
        DataDAO dataDAO = new DataDAO(RetrofitClient.getClient(Default.BASE_URL));
        Observable<List<Data>> observable = dataDAO.getAllData();
        ResourceObserver<List<Data>> resourceObserver = new ResourceObserver<List<Data>>() {
            @Override
            public void onNext(List<Data> datas) {
                Log.d(TAG,datas.size() + "");
                saveData(datas);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG,e.getMessage());
            }

            @Override
            public void onComplete() {
            }
        };
        composite.add(observable.subscribeWith(resourceObserver));

    }
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }
    private void downloadMp3(final Vocabulary vocabulary){
        try{
            Uri downloadUri = Uri.parse(Default.URL_MP3 + vocabulary.getSound_vocabulary());
            Uri destinationUri = Uri.parse(this.getExternalCacheDir().toString()+"/"+index+"."+vocabulary.getSound_vocabulary());
            DownloadRequest downloadRequest = new DownloadRequest(downloadUri)
                    .setRetryPolicy(new DefaultRetryPolicy())
                    .setDestinationURI(destinationUri).setPriority(DownloadRequest.Priority.HIGH)
                    .setDownloadContext(getApplicationContext())//Optional
                    .setDownloadListener(new DownloadStatusListener() {
                        @Override
                        public void onDownloadComplete(int id) {
                            String filePath = getApplication().getExternalCacheDir().toString()+"/"+index+"."+vocabulary.getSound_vocabulary();
                            Log.d(TAG,"filePath = " + filePath);
                            vocabularyDAO.setMp3Path(vocabulary,filePath);
                            ++index;
                            if (index != vocabularies.size()){
                                downloadMp3(vocabularies.get(index));
                            }else{
                                aviLoding.hide();
                                changeIsFristRun();
                            }
                        }

                        @Override
                        public void onDownloadFailed(int id, int errorCode, String errorMessage) {
                            Log.d(TAG,"onDownloadFailed =" +errorMessage);
                        }

                        @Override
                        public void onProgress(int id, long totalBytes, long downlaodedBytes, int progress) {
                            Log.d(TAG,"progress " + progress);
                        }
                    });
            int downloadId = downloadManager.add(downloadRequest);
        }catch (Exception e){
            Log.d(TAG,e.getMessage());
        }


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                Intent intent = new Intent(this, PhrasesActivity.class);
                startActivity(intent);
                return true;
            default:
                finish();
                return true;
        }
    }

    private void changeFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, PhrasesFragment.newInstance(false));
        transaction.addToBackStack(null);
        transaction.commit();
        navigation.setSelectedItemId(R.id.navigation_home);
    }

}
