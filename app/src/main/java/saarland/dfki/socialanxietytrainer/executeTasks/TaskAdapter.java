package saarland.dfki.socialanxietytrainer.executeTasks;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import saarland.dfki.socialanxietytrainer.R;


//https://www.youtube.com/watch?v=gGFvbvkZiMs
//https://abhiandroid.com/programming/json

public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {

    private  ArrayList<Task> taskList;

    public TaskAdapter(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    @NonNull
    @Override
    //viewgroup = parent, i = type
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item,viewGroup,false);
        return new TaskViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder tvh, int i) {
        Task t = taskList.get(i);
        tvh.setCategoryText(t.getCategory());
        tvh.setDifficultyText(Integer.toString(t.getDifficulty()));
        tvh.setDescriptionText(t.getDescription());
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
