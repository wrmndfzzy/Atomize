package com.wrmndfzzy.atomize;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class AboutActivity extends AppCompatActivity {

    private ListView list;
    private String[] aboutArray = {"Open source licenses", "About app", "About Wrmndfzzy"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        list = (ListView) findViewById(R.id.about_list);

        ArrayAdapter adapter = new ArrayAdapter<>(this,
                R.layout.about_list_item, aboutArray);

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
                    aboutAppDialog();
                }
                else{

                }
            }
        });
    }

    protected void aboutAppDialog(){
        final Dialog aaDialog = new Dialog(AboutActivity.this);
        aaDialog.setTitle("File Name");
        aaDialog.setContentView(R.layout.aboutapp_dialog);
        Button aaDialogConfirm = (Button) aaDialog.findViewById(R.id.aaDialogConfirm);
        aaDialogConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aaDialog.dismiss();
            }
        });
        aaDialog.show();
    }
}
