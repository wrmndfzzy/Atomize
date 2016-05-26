package org.frap129.atomize;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.nicdahlquist.pngquant.LibPngQuant;

import java.io.File;

public class MainActivity extends Activity {

    private static final int SELECT_PICTURE = 1;
    public static String selectedImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Button select = (Button) findViewById(R.id.select);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/png");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                        "Select Picture"), SELECT_PICTURE);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
            }
        }
    }

    public String getPath(Uri uri) {
        // just some safety built in
        if( uri == null ) {
            // TODO perform some logging or show user feedback
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        // this is our fallback here
        return uri.getPath();

        // listen for atomize once picture is chosen
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
                        File picture = new File(selectedImagePath);
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
