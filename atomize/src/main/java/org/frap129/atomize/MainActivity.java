package org.frap129.atomize;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.nicdahlquist.pngquant.LibPngQuant;

import java.io.File;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button select = (Button) findViewById(R.id.select);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browseIntent = new Intent(MainActivity.this, BrowsePictureActivity.class);
                startActivity(browseIntent);
            }
        });

        final Button atomize = (Button) findViewById(R.id.atomize);
        atomize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Atomizing...",
                        Toast.LENGTH_LONG).show();

                new AsyncTask<Void, Void, Long>() {
                    @Override
                    protected Long doInBackground(Void... params) {
                        long startMillis = System.currentTimeMillis();
                        File picture = new File(BrowsePictureActivity.selectedImagePath);
                        new LibPngQuant().pngQuantFile(picture, picture);
                        long endMillis = System.currentTimeMillis();
                        return endMillis - startMillis;
                    }

                    @Override
                    protected void onPostExecute(Long millis) {
                        Toast.makeText(MainActivity.this, "Done. Processing took " + millis + "ms.",
                                Toast.LENGTH_LONG).show();
                    }
                }.execute();
            }
        });
    }
}
