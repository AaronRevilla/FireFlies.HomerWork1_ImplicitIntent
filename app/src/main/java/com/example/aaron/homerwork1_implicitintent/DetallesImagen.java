package com.example.aaron.homerwork1_implicitintent;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetallesImagen extends AppCompatActivity {

    ImageView imagen;
    TextView textoImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_imagen);
        imagen = ((ImageView) findViewById(R.id.image));
        textoImg = ((TextView) findViewById(R.id.text));
        Intent intent = getIntent();
        imagen.setImageBitmap(((Bitmap) intent.getParcelableExtra("image")));
        textoImg.setText(intent.getStringExtra("title"));
    }
}
