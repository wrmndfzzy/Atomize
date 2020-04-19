package com.wrmndfzzy.atomize;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class LicenseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);
        WebView licView = findViewById(R.id.licViews);
        licView.getSettings().setUseWideViewPort(true);
        licView.loadUrl("file:///android_asset/licenses.html");
    }
}
