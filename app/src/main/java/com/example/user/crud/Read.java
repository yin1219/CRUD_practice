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
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class Read extends AppCompatActivity {

    private static final String TAG ="flag" ;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public TextView title;
    public TextView article;
    public TextView time;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        Bundle bundle = getIntent().getExtras();
        String noteId=bundle.getString("noteId");
       // Intent intent =this.getIntent();
       // String noteId=intent.getStringExtra("noteId");
        Log.d(TAG, "TesT: "+noteId);
        title =(TextView)findViewById(R.id.Title);
        article =(TextView)findViewById(R.id.Article);
        time =(TextView)findViewById(R.id.Time);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_read);
        setSupportActionBar(toolbar);


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
                    title.setText(snapshot.get("Title").toString());
                    article.setText(snapshot.get("Article").toString());
                    time.setText(snapshot.get("Time").toString());

                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_read, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_update:
                //Bundle bundle = getIntent().getExtras();
                //String noteId=bundle.getString("noteId");
                //boolean refresh_flag=bundle.getBoolean("refresh_flag");
                Intent intent =this.getIntent();
                String noteId=intent.getStringExtra("noteId");
                Log.d(TAG,"NOTEID :" +noteId);

                Intent intent_Update=new Intent(Read.this, Update.class);
                intent_Update.putExtra("noteId",noteId);
                //Bundle bundle2=new Bundle();
                //bundle2.putString("noteId",noteId);
                //bundle2.putBoolean("refresh_flag",refresh_flag);
                //intent_Update.putExtras(bundle2);
                startActivity(intent_Update);
                finish();


                return true;
            case R.id.action_delete:
                //bundle = getIntent().getExtras();
                //noteId=bundle.getString("noteId");

                intent =this.getIntent();
                noteId=intent.getStringExtra("noteId");
                Log.d(TAG,"NOTEID :" +noteId);

                db.collection ( "Note"). document (noteId)
                        .delete ()
                        .addOnSuccessListener (new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess (Void aVoid) {
                                Log.d (TAG, "DocumentSnapshot successfully deleted!");
                            }
                        })
                        .addOnFailureListener (new OnFailureListener() {
                            @Override
                            public void onFailure (@NonNull Exception e) {
                                Log.w (TAG, "Error deleting document", e);
                            }
                        });
                Toast.makeText(Read.this, "成功刪除", Toast.LENGTH_SHORT).show();

               Intent intent_Main=new Intent(Read.this, MainActivity.class);

                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
