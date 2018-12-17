package saarland.dfki.socialanxietytrainer.executeTasks;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import saarland.dfki.socialanxietytrainer.R;


//https://www.youtube.com/watch?v=gGFvbvkZiMs
//https://abhiandroid.com/programming/json

public class TaskAdapter extends RecyclerView.Adapter<ViewHolder> {

    private  ArrayList<Task> taskList;
    private Context context;

    public TaskAdapter(ArrayList<Task> taskList, Context context) {
        this.taskList = taskList;
        this.context = context;
    }


    @NonNull
    @Override
    //viewgroup = parent, i = type
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder myViewHolder, int i) {
        Task t = taskList.get(i);
        myViewHolder.setCategoryText(t.getCategory());
        myViewHolder.setDifficultyText(Integer.toString(t.getDifficulty()));
        myViewHolder.setDescriptionText(t.getDescription());
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
