package com.tauqeer.weatherapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import java.io.InputStream;
import java.net.URL;

public class DownloadImageFromWeb extends AsyncTask<String,Void, Bitmap> {

    ImageView imageView;

    public DownloadImageFromWeb(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        String urlOfImage = urls[0];
        Bitmap icon = null;
        try {
            InputStream is = new URL(urlOfImage).openStream();
            icon = BitmapFactory.decodeStream(is);
        }catch (Exception e) {
            System.out.println("EXCEPTION: "+e.getMessage());
        }
        return icon;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
        super.onPostExecute(bitmap);
    }
}
