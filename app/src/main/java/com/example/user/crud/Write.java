package com.example.user.crud;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Write extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static final String TAG = "TAG";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        // Create a new user with a first and last name

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_write);
        setSupportActionBar(toolbar);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_write, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_save:

                EditText titleEdit=(EditText)findViewById(R.id.Title_input);
                EditText articleEdit=(EditText)findViewById(R.id.Article_input) ;




                Date date = new Date();
                String titleInput=titleEdit.getText().toString();
                String articleInput=articleEdit.getText().toString();
                final Map<String, Object> Note = new HashMap<>();
                Note.put("Title", titleInput);
                Note.put("Article", articleInput);
                Note.put("Time", date);

// Add a new document with a generated ID
                db.collection("Note")
                        .add(Note)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {


                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                            }
                        });
                Toast.makeText(Write.this, "成功儲存", Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(Write.this,MainActivity.class);
                startActivity(intent);
                finish();

                return true;

        }
        return super.onOptionsItemSelected(item);
    }


}
