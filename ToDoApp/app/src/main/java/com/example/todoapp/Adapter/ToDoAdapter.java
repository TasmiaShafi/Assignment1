package com.example.todoapp.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.MainActivity;
import com.example.todoapp.Model.ToDoModel;
import com.example.todoapp.R;
import com.example.todoapp.Utils.DatabaseHelper;

import java.util.List;

class ToDOAdapter extends RecyclerView.Adapter<ToDOAdapter.MyViewHolder>{

    private List<ToDoModel> mList;
    private MainActivity activity;
    private DatabaseHelper myDB;
    public ToDOAdapter(DatabaseHelper DB,MainActivity activity){
        this.activity=activity;
        this.myDB=DB;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final ToDoModel item=mList.get(position);
        holder.mCheckBox.setText(item.getTask());
        holder.mCheckBox.setChecked(toBoolean(item.getStatus()));
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    myDB.updateStatus(item.getId(),1);
                }
                else {
                    myDB.updateStatus(item.getId(),0);
                }
            }
        });

    }
    public boolean toBoolean(int num){
        return num!=0;
    }
    //it'll be helpful when we creating the class to add new Task
    public Context getContext(){
        return activity;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    public void setTasks(List<ToDoModel> mList){
        this.mList=mList;
        notifyDataSetChanged();
    }
    public void deleteTask(int position){
        ToDoModel item=mList.get(position);
        myDB.deleteTask(item.getId());
        mList.remove(position);
        notifyItemRemoved(position);
    }
    public void editItem(int position){
        ToDoModel item=mList.get(position);
        Bundle bundle=new Bundle();
        bundle.putInt("id",item.getId());
        bundle.putString("task",item.getTask());
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        CheckBox mCheckBox;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            mCheckBox=itemView.findViewById(R.id.mcheckbox);
        }
    }
}