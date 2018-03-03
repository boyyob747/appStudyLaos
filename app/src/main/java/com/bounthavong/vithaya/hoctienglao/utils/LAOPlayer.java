package com.bounthavong.vithaya.hoctienglao.utils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

/**
 * Created by Boy- on 3/3/2561.
 */

public class LAOPlayer {
    MediaPlayer p = null;
    Context context;

    public LAOPlayer(Context context) {
        this.context = context;
    }

    public void playSound(String fileName) {
        p = new MediaPlayer();
        try {
            AssetFileDescriptor afd = context.getAssets().openFd("data/audio/"+fileName + ".mp3");
            p.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            p.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        p.start();
    }
}
