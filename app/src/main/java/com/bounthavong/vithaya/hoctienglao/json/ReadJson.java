package com.bounthavong.vithaya.hoctienglao.json;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Boy- on 3/3/2561.
 */

public class ReadJson {
    public static String loadJSONFromAsset(Activity activity) {
        String json = null;
        try {
            InputStream is = activity.getAssets().open("data/data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
    public static Drawable loadImg(Context context, String name){
        try {
            InputStream ims = context.getAssets().open("data/img/"+name);
            Drawable d = Drawable.createFromStream(ims, null);
            return d;
        }
        catch(IOException ex) {
            return null;
        }
    }
}
