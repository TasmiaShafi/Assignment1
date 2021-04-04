package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.example.todoapp.Adapter.ToDoAdapter;
import com.example.todoapp.Model.ToDoModel;
import com.example.todoapp.Utils.DatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnDialogCloseListner{
    private RecyclerView mRecyclerview;
    private FloatingActionButton fab;
    private DatabaseHelper myDB;
    private List<ToDoModel> mList;
    private ToDoAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerview=findViewById(R.id.recyclerView);
        fab=findViewById(R.id.fab);
        myDB=new DatabaseHelper(MainActivity.this);
        mList=new ArrayList<>();
        adapter=new ToDoAdapter(myDB,MainActivity.this);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerview.setAdapter(adapter);
        mList=myDB.getAllTasks();
        Collections.reverse(mList); //to get latest one on the top
        adapter.setTasks(mList);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTask.newInstance().show(getSupportFragmentManager(),AddTask.TAG);

            }
        });
    }

    @Override
    public void onDialogClose(DialogInterface dialogInterface) {
        mList=myDB.getAllTasks();
        Collections.reverse(mList); //to get latest one on the top
        adapter.setTasks(mList);
        adapter.notifyDataSetChanged();


    }
}