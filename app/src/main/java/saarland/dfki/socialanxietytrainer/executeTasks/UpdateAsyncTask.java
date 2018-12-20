package saarland.dfki.socialanxietytrainer.executeTasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import saarland.dfki.socialanxietytrainer.MainActivity;
import saarland.dfki.socialanxietytrainer.R;

public class UpdateAsyncTask extends AsyncTask<Void,Void,Void> {

    private Activity mainActivity;
    private ArrayList<Task> tasks[][];
    private TaskManager taskManager;
    private ArrayList<Task> current_selection;

    public UpdateAsyncTask(Activity mainActivity){
        this.mainActivity = mainActivity;
        this.taskManager = ((MainActivity)mainActivity).getTaskManager();
        this.tasks = taskManager.getTasks();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        current_selection = taskManager.select();
        RecyclerView rv = mainActivity.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager= new LinearLayoutManager(mainActivity);

        //rv.setHasFixedSize(true);
        rv.setLayoutManager(layoutManager);
        TaskAdapter adapter = new TaskAdapter(current_selection, mainActivity);
        rv.setAdapter(adapter);

        return null;
    }
}
