package saarland.dfki.socialanxietytrainer.executeTasks;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import saarland.dfki.socialanxietytrainer.R;

public class TaskViewHolder extends RecyclerView.ViewHolder {

    private TextView description;
    private TextView category;
    private TextView difficulty;
    private LinearLayout linearLayout;

    public TaskViewHolder(View v){
        super(v);
        category = (TextView) v.findViewById(R.id.category);
        difficulty = (TextView) v.findViewById(R.id.difficulty);
        description = (TextView)v.findViewById(R.id.description);
        linearLayout = (LinearLayout)v.findViewById(R.id.linear_layout_tasks);

    }

    public TextView getCategory() {
        return category;
    }

    public TextView getDescription() {
        return description;
    }

    public TextView getDifficulty() {
        return difficulty;
    }

    public LinearLayout getLinearLayout() {
        return linearLayout;
    }

    public void setDifficultyText(String str) {
        difficulty.setText("Difficulty: " + str);
    }

    public void setCategoryText(String str) {
        category.setText("Category: " + str);
    }

    public void setDescriptionText(String str) {
        description.setText("Description: " + str);
    }


}
