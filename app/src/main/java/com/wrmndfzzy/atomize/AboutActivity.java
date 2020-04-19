package com.wrmndfzzy.atomize;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.wrmndfzzy.atomize.intro.IntroActivity;

import us.feras.mdv.MarkdownView;

public class AboutActivity extends AppCompatActivity {

    private ListView list;
    private String[] aboutArray = {"Open source licenses", "About app", "About Wrmndfzzy", "See intro again"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        list = findViewById(R.id.about_list);

        ArrayAdapter adapter = new ArrayAdapter<>(this,
                R.layout.about_list_item, aboutArray);

        list.setAdapter(adapter);

        addClickListener();
    }

    private void addClickListener() {
        list.setOnItemClickListener((av, v, pos, id) -> {
            if (pos == 0) {
                Intent i = new Intent(AboutActivity.this, LicenseActivity.class);
                startActivity(i);
            } else if (pos == 1) {
                aboutAppDialog();
            } else if (pos == 2) {
                aboutWrmndfzzyDialog();

            } else {
                Intent i = new Intent(AboutActivity.this, IntroActivity.class);
                startActivity(i);
            }
        });
    }

    protected void aboutAppDialog() {
        final Dialog aaDialog = new Dialog(AboutActivity.this);
        aaDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        aaDialog.setTitle("About App");
        aaDialog.setContentView(R.layout.aboutapp_dialog);
        MarkdownView markdownView = aaDialog.findViewById(R.id.atomizeReadme);
        Button aaDialogConfirm = aaDialog.findViewById(R.id.aaDialogConfirm);
        markdownView.loadMarkdownFile("file:///android_asset/readme.md", "file:///android_asset/readme.css");
        aaDialogConfirm.setOnClickListener(v -> aaDialog.dismiss());
        aaDialog.show();
    }

    protected void aboutWrmndfzzyDialog() {
        final Dialog awDialog = new Dialog(AboutActivity.this);
        awDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        awDialog.setTitle("About Wrmndfzzy");
        awDialog.setContentView(R.layout.aboutwrmndfzzy_dialog);
        MarkdownView markdownView = awDialog.findViewById(R.id.wrmndfzzyReadme);
        Button aaDialogConfirm = awDialog.findViewById(R.id.awDialogConfirm);
        markdownView.loadMarkdownFile("file:///android_asset/wrmndfzzy.md", "file:///android_asset/wrmndfzzy.css");
        aaDialogConfirm.setOnClickListener(v -> awDialog.dismiss());
        awDialog.show();
    }
}
