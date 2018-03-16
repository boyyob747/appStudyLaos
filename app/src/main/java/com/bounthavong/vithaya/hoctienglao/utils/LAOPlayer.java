package com.bounthavong.vithaya.hoctienglao.utils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by Boy- on 3/3/2561.
 */

public class LAOPlayer {
    MediaPlayer mediaPlayer = null;
    Context context;

    public LAOPlayer(Context context) {
        this.context = context;
    }

//    public void playSound(String fileName) {
//        mediaPlayer = new MediaPlayer();
//        try {
//            AssetFileDescriptor afd = context.getAssets().openFd("data/audio/"+fileName + ".mp3");
//            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
//            afd.close();
//            mediaPlayer.prepare();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        mediaPlayer.start();
//    }
    public void playUrl(String url){
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.start();
    }
    public boolean isPlaying(){
        if (mediaPlayer != null){
            return mediaPlayer.isPlaying();
        }else{
            return true;
        }
    }
    public void stopPlaying(){
        if (mediaPlayer != null){
            mediaPlayer.stop();
        }
    }
}
