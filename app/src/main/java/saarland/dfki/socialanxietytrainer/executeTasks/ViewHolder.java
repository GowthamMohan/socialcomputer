package saarland.dfki.socialanxietytrainer.executeTasks;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import saarland.dfki.socialanxietytrainer.R;

public class ViewHolder extends RecyclerView.ViewHolder {

    private TextView description;
    private TextView category;
    private TextView difficulty;

    public ViewHolder(View v){
        super(v);
        category = (TextView) v.findViewById(R.id.category);
        difficulty = (TextView) v.findViewById(R.id.difficulty);
        description = (TextView)v.findViewById(R.id.description);

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

    public void setDifficultyText(String str) {
        difficulty.setText(str);
    }

    public void setCategoryText(String str) {
        category.setText(str);
    }

    public void setDescriptionText(String str) {
        description.setText(str);
    }
}
