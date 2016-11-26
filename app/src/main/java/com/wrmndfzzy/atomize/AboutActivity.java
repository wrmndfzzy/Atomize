package com.wrmndfzzy.atomize;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AboutActivity extends AppCompatActivity {

    String[] aboutArray = {"Open source licenses", "About app", "About Wrmndfzzy"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ArrayAdapter adapter = new ArrayAdapter<>(this,
                R.layout.about_list_item, aboutArray);

        ListView listView = (ListView) findViewById(R.id.about_list);
        listView.setAdapter(adapter);
    }
}
