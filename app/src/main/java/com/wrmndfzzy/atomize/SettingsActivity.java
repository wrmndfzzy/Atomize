package com.wrmndfzzy.atomize;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SettingsActivity extends AppCompatActivity {

    private ListView list;
    private String[] settingsArray = {"Theme (PLACEHOLDER)", "Compression Settings (PLACEHOLDER)", "Directory/Filename Settings (PLACEHOLDER)"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        list = (ListView) findViewById(R.id.settings_list);

        ArrayAdapter adapter = new ArrayAdapter<>(this,
                R.layout.settings_list_item, settingsArray);

        list.setAdapter(adapter);

        addClickListener();
    }

    private void addClickListener(){
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> av, View v, int pos,
                                    long id) {
                if(pos == 0){

                }
                else if(pos == 1){

                }
                else{

                }
            }
        });
    }
}
