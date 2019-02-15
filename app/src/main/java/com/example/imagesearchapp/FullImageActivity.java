package com.example.imagesearchapp;

import android.app.Activity;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;

public class FullImageActivity extends AppCompatActivity {
    ImageView img;
    Bitmap bitmap;
    Button addComment;
    EditText editTextComment;
    ListView comment_listView;
    DBHandler dbHandler;
    ArrayList<String> getAllcomments;
    ArrayAdapter<String> arrayAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullimage);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Intent i = getIntent();
        int position = i.getExtras().getInt("id");
        ImageAdapter imageAdapter = new ImageAdapter(this);

        getSupportActionBar().setTitle(imageAdapter.getTitle(position));

        img = (ImageView) findViewById(R.id.image);
        addComment = (Button)findViewById(R.id.commentButton);
        editTextComment = (EditText)findViewById(R.id.commentEditText);

        getAllcomments = new ArrayList<String>();
        comment_listView = (ListView)findViewById(R.id.listView);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getAllcomments);
        comment_listView.setAdapter(arrayAdapter);

        final String url = imageAdapter.getItem(position);

        new DownloadImage().execute(url);

        dbHandler = new DBHandler(getApplicationContext());

        displayComments(url);

        addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment = editTextComment.getText().toString().trim();

                if(!comment.isEmpty()){
                    boolean isAdded = dbHandler.onInsert(url,comment);
                    if(isAdded){
                        Toast.makeText(getApplicationContext(),"Comment added successfully",Toast.LENGTH_SHORT).show();
                        editTextComment.setText("");
                        displayComments(url);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Sorry!! There was some error in adding your comment",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Please type your comment before adding",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void displayComments(String url){
        getAllcomments.clear();
        Cursor cursor = dbHandler.getAllData(url);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()){
            while (!cursor.isAfterLast()) {
                getAllcomments.add(cursor.getString(1));
                arrayAdapter.notifyDataSetChanged();
                cursor.moveToNext();
            }
        }
    }

    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... URL) {
            String imageURL = URL[0];
            Bitmap bitmap = null;
            try {
                InputStream input = new java.net.URL(imageURL).openStream();
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            img.setImageBitmap(result);
        }
    }

}
