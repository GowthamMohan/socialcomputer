package saarland.dfki.socialanxietytrainer.executeTasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import saarland.dfki.socialanxietytrainer.R;

public class SetupTasks extends AsyncTask<Void,Void,Void> {

    private JSONObject obj;
    private String jsonStr;
    private ArrayList<Task> taskList;

    private Activity a;

    //https://www.tutorialspoint.com/android/android_json_parser.htm
    //https://howtodoinjava.com/java/io/how-to-read-data-from-inputstream-into-string-in-java/

    public SetupTasks(Activity a){
        this.a = a;
    }


    @Override
    protected Void doInBackground(Void... voids) {

        taskList = new ArrayList<>();
        try{
            //read file to string
            InputStream stream = a.getAssets().open("taskList.json");
            BufferedReader br = new BufferedReader(new InputStreamReader(stream));
            StringBuilder str = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                str.append(line);
            }
            jsonStr = str.toString();
            br.close();
            //create json object from string, extract and save the content
            obj = new JSONObject(jsonStr);
            JSONArray tasks = obj.getJSONArray("tasks");
            for(int i = 0; i < tasks.length(); i++){
                JSONObject current_task = tasks.getJSONObject(i);
                String desc = current_task.getString("description");
                String cat = current_task.getString("category");
                int diff = current_task.getInt("difficulty");

                Task t = new Task(i,desc,cat,diff);
                taskList.add(new Task(i,desc,cat,diff));

            }
        }catch (JSONException e){
            e.printStackTrace();

        }catch(FileNotFoundException e){
            e.printStackTrace();

        }catch (IOException e) {
            e.printStackTrace();
        }

        RecyclerView rv = a.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager= new LinearLayoutManager(a);

        //rv.setHasFixedSize(true);
        rv.setLayoutManager(layoutManager);
        TaskAdapter adapter = new TaskAdapter(taskList, a);
        rv.setAdapter(adapter);

        return null;
    }
}
