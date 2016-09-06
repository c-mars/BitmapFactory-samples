package com.example.cmars.labtest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.Optional;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.img)
    ImageView imageView;

    @OnClick(R.id.fab) void fab(View view) {
        Bitmap placeholder = BitmapFactory.decodeResource(getResources(),R.drawable.img);

//        BitmapFactory will return null if resource is not Drawable
        Optional<Bitmap> bitmap = Optional.ofNullable(BitmapFactory.decodeResource(getResources(), R.raw.code)); //R.raw.star

        imageView.setImageBitmap(bitmap.orElse(placeholder));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}
