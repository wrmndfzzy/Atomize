package com.wrmndfzzy.atomize.intro;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.github.paolorotolo.appintro.AppIntro2;
import com.wrmndfzzy.atomize.R;

import java.util.ArrayList;

public class IntroActivity extends AppIntro2 {

    static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 2;

    Button pDialogConfirm;

    // Please DO NOT override onCreate. Use init.
    @Override
    public void init(Bundle savedInstanceState) {

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#616161"));
        colors.add(Color.parseColor("#B71C1C"));
        colors.add(Color.parseColor("#0D47A1"));

        addSlide(IntroSlideFragment.newInstance(R.layout.intro_slide1));
        addSlide(IntroSlideFragment.newInstance(R.layout.intro_slide2));
        addSlide(IntroSlideFragment.newInstance(R.layout.intro_slide3));
        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            addSlide(IntroSlideFragment.newInstance(R.layout.intro_slide4));
            colors.add(Color.parseColor("#1B5E20"));
        }
        addSlide(IntroSlideFragment.newInstance(R.layout.intro_slide5));

        colors.add(Color.parseColor("#7B1FA2"));
        setAnimationColors(colors);

        // OPTIONAL METHODS
        // Override bar/separator color.
        /*setBarColor(Color.parseColor("#3F51B5"));
        setSeparatorColor(Color.parseColor("#2196F3"));*/
        /*askForPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 4);
        askForPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 4);
        setSwipeLock(false);*/

        // Hide Skip/Done button.
        //showSkipButton(false);
        setProgressButtonEnabled(true);
    }

    /*@Override
    public void onSkipPressed() {
        // Do something when users tap on Skip button.
        IntroActivity.this.finish();
    }*/

    @Override
    public void onDonePressed() {
        // Do something when users tap on Done button.
        if ((ContextCompat.checkSelfPermission(IntroActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)||(ContextCompat.checkSelfPermission(IntroActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            permissionsDialog();
        }
        else{
            IntroActivity.this.finish();
        }
    }

    @Override
    public void onSlideChanged() {
        // Do something when the slide changes.
    }

    @Override
    public void onNextPressed() {
        // Do something when users tap on Next button.
    }

    public void introPermissions(View v){
        try {
            if (ContextCompat.checkSelfPermission(IntroActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(IntroActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

            if (ContextCompat.checkSelfPermission(IntroActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(IntroActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length <= 0
                        && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(IntroActivity.this, "Read permissions are required to run this app.", Toast.LENGTH_LONG).show();
                    Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                    homeIntent.addCategory( Intent.CATEGORY_HOME );
                    homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(homeIntent);
                }
                break;
            }

            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length <= 0
                        && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(IntroActivity.this, "Write permissions are required to run this app.", Toast.LENGTH_LONG).show();
                    Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                    homeIntent.addCategory( Intent.CATEGORY_HOME );
                    homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(homeIntent);
                }
                break;
            }
        }
    }

    protected void permissionsDialog(){
        final Dialog pDialog = new Dialog(IntroActivity.this);
        pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pDialog.setTitle("Missing Permissions");
        pDialog.setContentView(R.layout.intro_permissions_dialog);
        pDialogConfirm = (Button) pDialog.findViewById(R.id.ipDialogConfirm);
        pDialogConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pDialog.dismiss();
                IntroActivity.this.finish();
            }
        });
        pDialog.show();
    }

}