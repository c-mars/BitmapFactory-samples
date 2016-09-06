package com.example.cmars.labtest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

    private File writeToFile(Bitmap bitmap) {
        //create a file to write bitmap data
        final String FILE_NAME = "scaled";
        File f = new File(getCacheDir(), FILE_NAME);
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

//Convert bitmap to byte array
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();

//write the bytes in file

        try {
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return f;
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

        File scaledFile = writeToFile(scaled);
        assert scaledFile != null;
        String path = scaledFile.getAbsolutePath();
        Bitmap reopened = BitmapFactory.decodeFile(path);

        imageView.setImageBitmap(reopened);
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
