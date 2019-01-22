package saarland.dfki.socialanxietytrainer.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import saarland.dfki.socialanxietytrainer.MainActivity;
import saarland.dfki.socialanxietytrainer.Preferences;
import saarland.dfki.socialanxietytrainer.R;
import saarland.dfki.socialanxietytrainer.classification.AnxietyLevel;

/**
 * https://www.tutorialspoint.com/android/android_json_parser.htm
 * https://howtodoinjava.com/java/io/how-to-read-data-from-inputstream-into-string-in-java/
 */
public class SetupAsyncTask extends AsyncTask<Void, Void, Void> {

    private String tasksStr;
    private Activity activity;
    private TaskSet currentSelection;
    private TaskManager taskManager;

    public SetupAsyncTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            //read file to string
            InputStream stream = activity.getAssets().open("tasks.json");
            BufferedReader br = new BufferedReader(new InputStreamReader(stream));
            StringBuilder str = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                str.append(line);
            }
            tasksStr = str.toString();
            br.close();

            TaskSet tasks = TaskSet.fromJson(tasksStr);
            taskManager = new TaskManager(tasks);
            ((MainActivity) activity).setTaskManager(taskManager); //this cast is safe
            AnxietyLevel level = Preferences.Companion.getAnxietyLevel(activity);
            currentSelection = taskManager.select(level);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        RecyclerView rv = activity.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);

        //rv.setHasFixedSize(true);
        rv.setLayoutManager(layoutManager);
        TaskAdapter adapter = new TaskAdapter(currentSelection, activity);
        rv.setAdapter(adapter);

        return null;
    }

}
