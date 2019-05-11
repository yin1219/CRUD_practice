package com.example.user.crud;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder>{
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG ="TAG" ;
    public Context context;
    public List<Note> noteList;
    public NoteListAdapter(Context context, List<Note> noteList){
        this.noteList =noteList;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.readlist_item,parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.titleText.setText(noteList.get(position).getTitle());
        holder.timeText.setText(String.valueOf(noteList.get(position).getTime()));
        final String noteId = noteList.get(position).notrId;
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"ID :"+noteId);
                Intent intent = new Intent(context, Read.class);
                Bundle bundle=new Bundle();
                //bundle.putString("noteId",noteId);
                intent.putExtra("noteId",noteId);
                //bundle.putBoolean("refresh_flag",false);
                //intent.putExtras(bundle);




                context.startActivity(intent);




            }
            public boolean onLongClick(View v){ //實作onLongClick介面定義的方法
                Log.d(TAG,"LCID :"+noteId);


                return true;
            }
        });

    }


    public int getItemCount() {
        return noteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public TextView titleText;
        public TextView timeText;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            titleText =(TextView)mView.findViewById(R.id.Title_text);
            timeText =(TextView)mView.findViewById(R.id.Time_text);

        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // requestCode = 對應上面的startActivityForResult的第二個參數
        if (requestCode == 1) {
            // resultCode = 對應NextActivity中setResult()的第一個參數
            if (resultCode == RESULT_OK) {
                // 將NextActivity回傳的資料取出來
                Bundle bundleResult = data.getExtras();
            }
        }

    }
}
