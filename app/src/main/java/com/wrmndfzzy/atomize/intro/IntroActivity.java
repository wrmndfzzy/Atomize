package com.wrmndfzzy.atomize.intro;

import android.Manifest;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro2;
import com.wrmndfzzy.atomize.R;

public class IntroActivity extends AppIntro2 {

    // Please DO NOT override onCreate. Use init.
    @Override
    public void init(Bundle savedInstanceState) {

        addSlide(IntroSlideFragment.newInstance(R.layout.intro_slide1));
        addSlide(IntroSlideFragment.newInstance(R.layout.intro_slide2));
        addSlide(IntroSlideFragment.newInstance(R.layout.intro_slide3));
        addSlide(IntroSlideFragment.newInstance(R.layout.intro_slide4));
        addSlide(IntroSlideFragment.newInstance(R.layout.intro_slide5));


        // OPTIONAL METHODS
        // Override bar/separator color.
        /*setBarColor(Color.parseColor("#3F51B5"));
        setSeparatorColor(Color.parseColor("#2196F3"));*/
        askForPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 4);
        askForPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 4);

        // Hide Skip/Done button.
        //showSkipButton(false);
        setProgressButtonEnabled(true);
        setFlowAnimation();
        //setFadeAnimation();
    }

    /*@Override
    public void onSkipPressed() {
        // Do something when users tap on Skip button.
        IntroActivity.this.finish();
    }*/

    @Override
    public void onDonePressed() {
        // Do something when users tap on Done button.
        IntroActivity.this.finish();
    }

    @Override
    public void onSlideChanged() {
        // Do something when the slide changes.
    }

    @Override
    public void onNextPressed() {
        // Do something when users tap on Next button.
    }

}
