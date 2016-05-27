package org.frap129.atomize;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nicdahlquist.pngquant.LibPngQuant;

public class MainActivity extends Activity {

    TextView noImg;
    ImageView imgView;
    private static final int SELECT_PICTURE = 1;
    public static String selectedImagePath;
    private boolean imgSelected = false;

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

        imgView = (ImageView) findViewById(R.id.imgPreview);
    }

    public String getPath(Uri uri) {
        // just some safety built in
        /*if( uri == null ) {
            // TODO perform some logging or show user feedback
            return null;
        }*/
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        Cursor cursor = null;
        try {
            String[] projection = {MediaStore.Images.Media.DATA};
            cursor = getContentResolver().query(uri, projection, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String result = cursor.getString(column_index);
            return result;
        }
        finally {
            if(cursor != null){
                cursor.close();
            }
        }
        // this is our fallback here
        //picSelected = true;
        //return uri.getPath();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
                if(selectedImagePath != null){
                    imgSelected = true;
                    noImg.setVisibility(View.GONE);
                    imgView.setImageURI(Uri.parse(getPath(selectedImageUri)));
                }
                else{
                    imgSelected = false;
                }
            }
        }
    }

    // listen for atomize once picture is chosen
    public void atomize(View v) {
        if(imgSelected == true){
            Toast.makeText(MainActivity.this, "Atomizing...",
                    Toast.LENGTH_LONG).show();

            new AsyncTask<Void, Void, Long>() {
                protected Long doInBackground(Void... params) {
                    long startMillis = System.currentTimeMillis();
                    String inPicture = selectedImagePath;
                    String outPicture = selectedImagePath+"quant.png";
                    new LibPngQuant().pngQuantFile(inPicture, outPicture);
                    long endMillis = System.currentTimeMillis();
                    return endMillis - startMillis;
                }

                protected void onPostExecute(Long millis) {
                    Toast.makeText(MainActivity.this, "Done. Processing took " + millis + "ms.",
                            Toast.LENGTH_LONG).show();
                }
            }.execute();
        }
        else{
            Toast.makeText(MainActivity.this, "Please select an image.",
                    Toast.LENGTH_LONG).show();
        }
    }

}
