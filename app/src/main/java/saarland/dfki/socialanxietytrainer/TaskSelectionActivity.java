package saarland.dfki.socialanxietytrainer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import saarland.dfki.socialanxietytrainer.execute_tasks.Task;


public class TaskSelectionActivity extends AppCompatActivity {

    private final String PATH = "saarland/dfki/socialanxietytrainer/execute_tasks/tasks.json";
    private JSONObject obj;
    private String jsonStr;
    private ArrayList<Task> taskList;


   //https://www.tutorialspoint.com/android/android_json_parser.htm

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


            try{

                jsonStr = new FileReader(PATH).toString();
                obj = new JSONObject(jsonStr);
                JSONArray tasks = obj.getJSONArray("tasks");

                for(int i = 0; i < tasks.length(); i++){
                    JSONObject current_task = tasks.getJSONObject(i);
                    String desc = current_task.getString("description");
                    String cat = current_task.getString("category");
                    int diff = current_task.getInt("difficulty");

                    Task t = new Task(i,desc,cat,diff);
                    taskList.add(new Task(i,desc,cat,diff));
                    Log.d("TaskSelectionActivity",t.toString());

                }
                

            }catch (JSONException e){
                Log.e("TaskSelectionActivity", "json went wrong");

            }catch(FileNotFoundException e){
                Log.e("TaskSelectionActivity", "file not found");
            }

    }


    //------------------------------------------------------------------------------------


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}

