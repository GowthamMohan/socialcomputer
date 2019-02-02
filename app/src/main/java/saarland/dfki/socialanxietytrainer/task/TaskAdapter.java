package saarland.dfki.socialanxietytrainer.task;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import saarland.dfki.socialanxietytrainer.ExecuteTaskActivity;
import saarland.dfki.socialanxietytrainer.R;

/**
 * https://www.youtube.com/watch?v=gGFvbvkZiMs
 * https://abhiandroid.com/programming/json
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {

    private TaskSet tasks;
    private Activity activity;

    public TaskAdapter(TaskSet tasks, Activity a) {
        this.tasks = tasks;
        this.activity = a;
    }

    @NonNull
    @Override
    //viewgroup = parent, i = type
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup,false);
        return new TaskViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder tvh, int i) {
        final Task t = tasks.get(i);
        tvh.setCategoryText(t.getCategory());
        tvh.setDescriptionText(t.getDescription());
        tvh.getLinearLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, ExecuteTaskActivity.class);
                i.putExtra("category", t.getCategory());
                i.putExtra("description", t.getDescription());
                i.putExtra("level", "" + t.getLevel());
                i.putExtra("id", "" + t.getId());
                activity.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.getSize();
    }
}
