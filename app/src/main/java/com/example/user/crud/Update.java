package com.example.user.crud;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.app.PendingIntent.getActivity;


public class Update extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static final String TAG = "TAG";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        // Create a new user with a first and last name
       // Bundle bundle = getIntent().getExtras();
        //String noteId=bundle.getString("noteId");
        //boolean refresh_flag=bundle.getBoolean("refresh_flag");
        Intent intent =this.getIntent();
        String noteId=intent.getStringExtra("noteId");
        Log.d(TAG,"GG :"+noteId );

        final EditText titleEdit=(EditText)findViewById(R.id.Title_input);
        final EditText articleEdit=(EditText)findViewById(R.id.Article_input) ;

        final DocumentReference docRef = db.collection("Note").document(noteId);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    Log.d(TAG, "Current data: " + snapshot.get("Title"));
                    titleEdit.setText(snapshot.get("Title").toString());
                    articleEdit.setText(snapshot.get("Article").toString());
                    Log.d(TAG,"TEST :" +(snapshot.get("Title").toString()));

                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });




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
               // Bundle bundle = getIntent().getExtras();
                //String noteId=bundle.getString("noteId");
                //boolean refresh_flag=bundle.getBoolean("refresh_flag");
                Intent intent =this.getIntent();
                String noteId=intent.getStringExtra("noteId");


                Log.d(TAG,"U_NOTEID :" +noteId);

                final EditText titleEdit=(EditText)findViewById(R.id.Title_input);
                final EditText articleEdit=(EditText)findViewById(R.id.Article_input) ;
                Date date = new Date();
                String titleInput=titleEdit.getText().toString();
                String articleInput=articleEdit.getText().toString();
                final Map<String, Object> Note = new HashMap<>();
                Note.put("Title", titleInput);
                Note.put("Article", articleInput);
                Note.put("Time", date);

// Add a new document with a generated ID
                db.collection("Note").document(noteId)
                        .set(Note)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {


                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully written!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                            }
                        });
                Toast.makeText(Update.this, "成功儲存", Toast.LENGTH_SHORT).show();
                Intent intent_refresh_flag =new Intent(Update.this,MainActivity.class);
                intent_refresh_flag.putExtra("refresh_flag",true);
               // Bundle bundle_refresh_flag=new Bundle();
               // bundle_refresh_flag.putBoolean("refresh_flag",true);
               // intent_refresh_flag.putExtras(bundle_refresh_flag);
                finish();

                return true;

        }
        return super.onOptionsItemSelected(item);
    }


}
