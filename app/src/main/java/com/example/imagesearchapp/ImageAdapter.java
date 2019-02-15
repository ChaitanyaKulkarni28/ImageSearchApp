package com.example.imagesearchapp;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    private Context context;

    static int totalImages;
    static String arr[];
    static ArrayList<String> titles;
    //int totalImages = 2;
    //String arr[] = {"https://i.imgur.com/MqFJacv.jpg","https://i.imgur.com/Ni5IhYU.png"};

    public ImageAdapter(Context c, ArrayList<String> arrayList, ArrayList<String> titleList){
        context = c;
        totalImages = arrayList.size();
        arr = new String[totalImages];
        arrayList.toArray(arr);
        titles = new ArrayList<>(titleList);
    }

    public String getTitle(int pos){
        return titles.get(pos);
    }
    public ImageAdapter(Context c){
        context = c;
    }

    @Override
    public int getCount() {
        return totalImages;
    }

    @Override
    public String getItem(int i) {
      //  return MainActivity.arrayList.get(i);
        return arr[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView;
        if (view == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(350, 350));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) view;
        }
        String url = getItem(i);

        Picasso.get()
                .load(url)
                .fit()
                .centerCrop().into(imageView);
        return imageView;
    }
}
