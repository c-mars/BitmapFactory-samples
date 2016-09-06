package com.example.cmars.labtest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import java.util.Optional;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.img)
    ImageView imageView;

    private static Bitmap scaleBitmap(Bitmap src, final int maxWidth, final int maxHeight) {

        float originalWidth = src.getWidth();
        float originalHeight = src.getHeight();
        float ratio = originalHeight / originalWidth;

        float newWidth;
        float newHeight;
        if (originalHeight / (float) maxHeight > originalWidth / (float) maxWidth) {
            newHeight = maxHeight;
            newWidth = newHeight / ratio;
        } else {
            newWidth = maxWidth;
            newHeight = newWidth * ratio;
        }
        return Bitmap.createScaledBitmap(src, (int) newWidth, (int) newHeight, false);
    }

    @OnClick(R.id.fab) void fab(View view) {
        Bitmap placeholder = BitmapFactory.decodeResource(getResources(),R.drawable.img);

//        BitmapFactory will return null if resource is not Drawable
        Optional<Bitmap> bitmap = Optional.ofNullable(BitmapFactory.decodeResource(getResources(), R.raw.city)); //R.raw.code, R.raw.star

        final int MAX_WIDTH = 200;
        final int MAX_HEIGHT = 1000;
        Bitmap scaled = scaleBitmap(bitmap.orElse(placeholder),
                MAX_WIDTH,
                MAX_HEIGHT);

        imageView.setImageBitmap(scaled);
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
