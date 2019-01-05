package saarland.dfki.socialanxietytrainer.executeTasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import saarland.dfki.socialanxietytrainer.MainActivity;
import saarland.dfki.socialanxietytrainer.R;

public class SetupAsyncTask extends AsyncTask<Void,Void,Void> {

    private JSONObject jsonObject;
    private String file_as_string;
    private ArrayList<Task> tasks[][];
    private int nr_categories;
    private int nr_levels;
    private Activity mainActivity;
    private int counter;
    private ArrayList<Task> current_selection;
    private int[] levels;
    private TaskManager taskManager;



    //https://www.tutorialspoint.com/android/android_json_parser.htm
    //https://howtodoinjava.com/java/io/how-to-read-data-from-inputstream-into-string-in-java/

    //use this constructor fur dummy values
    public SetupAsyncTask(Activity activity){
        this.mainActivity = activity;
        counter = 0;
        levels = new int[]{3, 1, 1, 1, 1, 1};
    }
    //later use this constructor with the results from the questionnaire
    public SetupAsyncTask(Activity activity, int[] levels) {
        this.mainActivity = activity;
        counter = 0;
        this.levels = levels;
    }


    @Override
    protected Void doInBackground(Void... voids) {
        try{
            //read file to string
            InputStream stream = mainActivity.getAssets().open("tasks.json");
            BufferedReader br = new BufferedReader(new InputStreamReader(stream));
            StringBuilder str = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                str.append(line);
            }
            file_as_string = str.toString();
            br.close();
            //create json object from string, extract and save the content
            jsonObject = new JSONObject(file_as_string);


            nr_categories = (Integer) jsonObject.get("nr_categories");
            nr_levels = (Integer)jsonObject.get("nr_levels");
            tasks = new ArrayList[nr_categories][nr_levels];


            for(int c = 0; c < nr_categories; c++){

                String list_name = "tasks_f" + Integer.toString(c+1);
                String category_text = jsonObject.getJSONArray(list_name).getJSONObject(0).getString("category_text");

                for ( int l = 0; l < nr_levels; l++) {
                    String name = "level_" + Integer.toString(l+1);
                    JSONArray taskList = jsonObject.getJSONArray(list_name).getJSONObject(l +1).getJSONArray(name);
                    addList(taskList, c,l,category_text);

                }
            }


            taskManager = new TaskManager(tasks,levels);
            ((MainActivity)mainActivity).setTaskManager(taskManager); //this cast is safe
            current_selection = taskManager.select();

        }catch (JSONException e){
            e.printStackTrace();

        }catch(FileNotFoundException e){
            e.printStackTrace();

        }catch (IOException e) {
            e.printStackTrace();
        }

        RecyclerView rv = mainActivity.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager= new LinearLayoutManager(mainActivity);

        //rv.setHasFixedSize(true);
        rv.setLayoutManager(layoutManager);
        TaskAdapter adapter = new TaskAdapter(current_selection, mainActivity);
        rv.setAdapter(adapter);

        return null;
    }

    public void addList(JSONArray jsonArray, int category,int level,String cat_text){
        ArrayList<Task> taskList = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject current_task = jsonArray.getJSONObject(i);
                String desc = current_task.getString("description");
                Task task = new Task(counter++,desc,cat_text,level + 1);
                taskList.add(task);
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        tasks[category][level] = taskList;
    }


}
