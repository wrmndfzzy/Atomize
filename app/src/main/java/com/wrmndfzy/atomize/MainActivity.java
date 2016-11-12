package com.wrmndfzy.atomize;

import android.Manifest;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import com.nicdahlquist.pngquant.LibPngQuant;

public class MainActivity extends AppCompatActivity {

    TextView noImg;
    ImageView preView;
    private static final int SELECT_PICTURE = 1;
    public static String selectedImagePath;
    private boolean imgSelected = false;
    static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 2;

    File extFolder = new File(Environment.getExternalStorageDirectory() + "/Atomize");

    Switch deleteSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noImg = (TextView) findViewById(R.id.noImg);
        final Button select = (Button) findViewById(R.id.select);
        deleteSwitch = (Switch) findViewById(R.id.deleteSwitch);

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        }

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/png");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
            }
        });
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
                    Toast.makeText(MainActivity.this, "Read permissions are required to run this app.", Toast.LENGTH_LONG).show();
                    Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                    homeIntent.addCategory( Intent.CATEGORY_HOME );
                    homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(homeIntent);
                }
                return;
            }

            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length <= 0
                        && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MainActivity.this, "Write permissions are required to run this app.", Toast.LENGTH_LONG).show();
                    Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                    homeIntent.addCategory( Intent.CATEGORY_HOME );
                    homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(homeIntent);
                }
            }
        }
    }

    //Thanks: http://codetheory.in/android-pick-select-image-from-gallery-with-intents/
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {

                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(this, selectedImageUri);

                if (selectedImagePath != null) {
                    imgSelected = true;
                    try {
                       Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                       Log.d("imgSelected", String.valueOf(bitmap));
                       preView = (ImageView) findViewById(R.id.imgPreview);
                       preView.setImageBitmap(bitmap);
                       noImg.setVisibility(View.GONE);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Select a locally stored image.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    // listen for atomize once picture is chosen
    public void atomize(View v) {
        if(imgSelected){
            if(!extFolder.exists() && !extFolder.isDirectory()){
                try{
                    if (!extFolder.mkdirs()) {
                        Log.d("extFolder", "directory cannot be created");
                    }
                }
                catch(Exception e){
                    //fileProbDialog();
                }
            }
            Toast.makeText(MainActivity.this, "Atomizing...", Toast.LENGTH_LONG).show();
            quantize();
            Toast.makeText(MainActivity.this, "Done!", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(MainActivity.this, "Please select an image.", Toast.LENGTH_LONG).show();
        }
    }

    public void quantize() {
        final Thread atomThread = new Thread(new Runnable() {
            public void run() {

                File input = new File(selectedImagePath);
                String imageName = input.getName();
                File output = new File(extFolder + "/" + imageName);
                if (output.exists()) {
                    if (!output.delete()) {
                        Log.d("output", "exists, but cannot be deleted");
                    }

                }
                new LibPngQuant().pngQuantFile(input, output);
                if (deleteSwitch.isChecked()) {
                    if (!input.delete()) {
                        Log.d("input", "cannot be deleted");
                    }
                    Log.d("switch", "checked");
                }
            }
        });
        atomThread.start();
    }

    // File path methods taken from aFileChooser, thanks to iPaulPro: https://github.com/iPaulPro/aFileChooser
    public static String getPath(final Context context, final Uri uri) {
        // DocumentProvider
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}
