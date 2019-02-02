package saarland.dfki.socialanxietytrainer.task;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import saarland.dfki.socialanxietytrainer.R;

public class TaskViewHolder extends RecyclerView.ViewHolder {

    private TextView description;
    private TextView category;
    private LinearLayout linearLayout;

    public TaskViewHolder(View v) {
        super(v);
        category = (TextView) v.findViewById(R.id.category);
        description = (TextView)v.findViewById(R.id.description);
        linearLayout = (LinearLayout)v.findViewById(R.id.linear_layout_tasks);
    }

    public TextView getCategory() {
        return category;
    }

    public TextView getDescription() {
        return description;
    }

    public LinearLayout getLinearLayout() {
        return linearLayout;
    }

    public void setCategoryText(String str) {
        category.setText("Category: " + str);
    }

    public void setDescriptionText(String str) {
        description.setText("Description: " + str);
    }


}
