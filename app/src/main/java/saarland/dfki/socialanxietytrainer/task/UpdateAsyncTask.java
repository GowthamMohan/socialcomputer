package saarland.dfki.socialanxietytrainer.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import saarland.dfki.socialanxietytrainer.MainActivity;
import saarland.dfki.socialanxietytrainer.Preferences;
import saarland.dfki.socialanxietytrainer.R;
import saarland.dfki.socialanxietytrainer.classification.AnxietyLevel;

public class UpdateAsyncTask extends AsyncTask<Void, Void, Void> {

    private Activity activity;
    private TaskSet tasks;
    private TaskManager taskManager;
    private TaskSet currentSelection;

    public UpdateAsyncTask(Activity activity) {
        this.activity = activity;
        this.taskManager = ((MainActivity)activity).getTaskManager();
        this.tasks = taskManager.getTasks();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        AnxietyLevel level = Preferences.Companion.getAnxietyLevel(activity);
        currentSelection = taskManager.select(level);
        RecyclerView rv = activity.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager= new LinearLayoutManager(activity);

        //rv.setHasFixedSize(true);
        rv.setLayoutManager(layoutManager);
        TaskAdapter adapter = new TaskAdapter(currentSelection, activity);
        rv.setAdapter(adapter);

        return null;
    }
}
