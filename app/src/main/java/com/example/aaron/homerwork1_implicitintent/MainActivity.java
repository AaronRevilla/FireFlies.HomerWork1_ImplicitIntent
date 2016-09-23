package com.example.aaron.homerwork1_implicitintent;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE_OK = 1;
    ArrayList<ImageItem> imageItems;
    private GridView gridView;
    private GridViewAdapter gridAdapter;
    static final String appDirectoryName = "MyFirstHomeWork";
    String tmpNamePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        imageItems = new ArrayList<ImageItem>();

        gridView = (GridView) findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(this, R.layout.activity_detalles_imagen, imageItems);
        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                                                ImageItem item = (ImageItem) parent.getItemAtPosition(position);
                                                //Create intent
                                                Intent intent = new Intent(MainActivity.this, DetallesImagen.class);
                                                intent.putExtra("title", item.getTitle());
                                                intent.putExtra("image", item.getImage());

                                                //Start details activity
                                                startActivity(intent);
                                            }
                                        });

        /*
        FloatingActionButton cameraButton = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openCamera(View view) {
        Intent openCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (openCamera.resolveActivity(getPackageManager()) != null) {
            /*
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile(generateName());
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {

                Uri photoURI = FileProvider.getUriForFile(this, "com.example.android.fileprovider", photoFile);
                openCamera.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                startActivityForResult(openCamera, REQUEST_IMAGE_CAPTURE_OK);
            }
            */
            startActivityForResult(openCamera, REQUEST_IMAGE_CAPTURE_OK);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE_OK && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageItems.add(new ImageItem(imageBitmap, generateName()));
            //galleryAddPic();
        }
    }

    public String generateName(){
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp;
        return imageFileName;
    }

    private File createImageFile(String nameImg) throws IOException {
        // Create an image file name
        File storageDir = new File(getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES), appDirectoryName);
        //File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                nameImg,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        Log.d("DEBUG", image.toString());
        image.mkdirs();
        // Save a file: path for use with ACTION_VIEW intents
        tmpNamePhoto = "file:" + image.getAbsolutePath();
        return image;
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(tmpNamePhoto);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("ArrayImages", imageItems);
        //Log.d(LOG_CAT, "OnSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        imageItems = savedInstanceState.getParcelableArrayList("ArrayImages");
        //Log.d(LOG_CAT, "OnRestoreInstanceState");
    }
}
