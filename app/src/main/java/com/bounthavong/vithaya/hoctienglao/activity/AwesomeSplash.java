package com.bounthavong.vithaya.hoctienglao.activity;

import android.content.Intent;

import com.bounthavong.vithaya.hoctienglao.MainActivity;
import com.bounthavong.vithaya.hoctienglao.R;
import com.daimajia.androidanimations.library.Techniques;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

/** Created by boy- on 14/2/2018 AD. */
public class AwesomeSplash extends com.viksaa.sssplash.lib.activity.AwesomeSplash {

    // DO NOT OVERRIDE onCreate()!
    // if you need to start some services do it in initSplash()!

    @Override
    public void initSplash(ConfigSplash configSplash) {

        configSplash.setBackgroundColor(R.color.icons); // any color you want form colors.xml
        configSplash.setAnimCircularRevealDuration(1000); // int ms
        configSplash.setRevealFlagX(Flags.REVEAL_RIGHT); // or Flags.REVEAL_LEFT
        configSplash.setRevealFlagY(Flags.REVEAL_BOTTOM); // or Flags.REVEAL_TOP

        configSplash.setLogoSplash(R.drawable.elephant); // or any other drawable
        configSplash.setAnimLogoSplashDuration(1500); // int ms
        configSplash.setAnimLogoSplashTechnique(
                Techniques
                        .Bounce); // choose one form Techniques (ref:
                                  // https://github.com/daimajia/AndroidViewAnimations)

        configSplash.setTitleSplash("");
        configSplash.setTitleTextColor(R.color.icons);
        configSplash.setTitleTextSize(30f); // float value
        configSplash.setAnimTitleDuration(10);
        configSplash.setAnimTitleTechnique(Techniques.FlipInX);
    }

    @Override
    public void animationsFinished() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
