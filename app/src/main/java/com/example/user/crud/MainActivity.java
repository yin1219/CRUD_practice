package com.example.user.crud;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.example.user.crud.NoteListAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG ="TAG" ;
    FirebaseFirestore db ;
    private RecyclerView mMainList;
    private NoteListAdapter noteListAdapter;
    private List<Note>  noteList;
    //private NoteId noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //boolean refresh_flag;


        //Bundle bundle_refresh_flag = getIntent().getExtras();
        //bundle_refresh_flag.putBoolean("refresh_flag",false);
        //refresh_flag=bundle_refresh_flag.getBoolean("refresh_flag");
        Intent intent_refresh_flag=this.getIntent();
       /* refresh_flag=intent_refresh_flag.getBooleanExtra("refresh_flag",false);
               Log.d(TAG,"bFlag :" +refresh_flag);
               Log.d(TAG,"abFlag :" +intent_refresh_flag.getBooleanExtra("refresh_flag",false));
                if(refresh_flag){
            refresh();
        }*/


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, Write.class);
                startActivity(intent);


            }
        });

        noteList=new ArrayList<>();
        noteListAdapter = new NoteListAdapter(getApplicationContext(),noteList);

        mMainList = (RecyclerView) findViewById(R.id.mainList) ;
        mMainList.setHasFixedSize(true);
        mMainList.setLayoutManager(new LinearLayoutManager(this));
        mMainList.setAdapter(noteListAdapter);
        //registerForContextMenu(mMainList);//長按
        db =FirebaseFirestore.getInstance();

        db.collection("Note").orderBy("Time", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent( QuerySnapshot snapshots,
                                        FirebaseFirestoreException e)  {
                        if (e !=null) {

                        }
                        for ( DocumentChange document:snapshots.getDocumentChanges()){
                            if(document.getType()==DocumentChange.Type.ADDED&&document.getType()!=DocumentChange.Type.REMOVED){
                                String title=document.getDocument().getString("Title");
                                Log.d(TAG,"Title :"+title);

                                Note note = document.getDocument().toObject(Note.class)
                                        .withId(document.getDocument().getId());
                                noteList.add(note);
                                noteListAdapter.notifyDataSetChanged();
                            }


                        }

                    }
                });


        onResume();
    }

    protected void onDestroy(){
        super.onDestroy();
        finish();


    }

    protected void onRestart() {

        super.onRestart();
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        boolean refresh_flag;


        //Bundle bundle_refresh_flag = getIntent().getExtras();
        //bundle_refresh_flag.putBoolean("refresh_flag",false);
        //refresh_flag=bundle_refresh_flag.getBoolean("refresh_flag");
        Intent intent_refresh_flag=this.getIntent();
        refresh_flag=intent_refresh_flag.getBooleanExtra("refresh_flag",false);
        Log.d(TAG,"bFlag :" +refresh_flag);
        Log.d(TAG,"abFlag :" +intent_refresh_flag.getBooleanExtra("refresh_flag",false));
        if(refresh_flag){
            refresh();
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, Write.class);
                startActivity(intent);


            }
        });

        noteList=new ArrayList<>();
        noteListAdapter = new NoteListAdapter(getApplicationContext(),noteList);

        mMainList = (RecyclerView) findViewById(R.id.mainList) ;
        mMainList.setHasFixedSize(true);
        mMainList.setLayoutManager(new LinearLayoutManager(this));
        mMainList.setAdapter(noteListAdapter);
        registerForContextMenu(mMainList);//長按
        db =FirebaseFirestore.getInstance();

        db.collection("Note").orderBy("Time", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent( QuerySnapshot snapshots,
                                         FirebaseFirestoreException e)  {
                        if (e !=null) {

                        }
                        for ( DocumentChange document:snapshots.getDocumentChanges()){
                            if(document.getType()==DocumentChange.Type.ADDED&&document.getType()!=DocumentChange.Type.REMOVED){
                                String title=document.getDocument().getString("Title");
                                Log.d(TAG,"Title :"+title);

                                Note note = document.getDocument().toObject(Note.class)
                                        .withId(document.getDocument().getId());
                                noteList.add(note);
                                noteListAdapter.notifyDataSetChanged();
                            }


                        }

                    }
                });


        onResume();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            refresh();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    /*  @Override
    public void onCreateContextMenu(ContextMenu menu_list, View v, ContextMenu.ContextMenuInfo menuInfo) {
         getMenuInflater().inflate(R.menu.menu_list,menu_list);
         super.onCreateContextMenu(menu_list, v, menuInfo);
     }

     @Override

     public boolean onContextItemSelected(MenuItem item){
         if(item.getItemId() == R.id.op0){
             Log.d(TAG,"NID :"+noteId);
             return true;
         }
         else if(item.getItemId() == R.id.op1){
             finish();
         }
         else if(item.getItemId() == R.id.op2){
             finish();
         }
         return super.onContextItemSelected(item);
     }*/
    public void refresh() {

        Intent intent =new Intent(MainActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
        overridePendingTransition(0,0);//跳頁閃爍


    }


}
