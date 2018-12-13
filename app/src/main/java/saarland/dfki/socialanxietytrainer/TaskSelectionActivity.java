package saarland.dfki.socialanxietytrainer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.estimote.sdk.repackaged.gson_v2_3_1.com.google.gson.JsonObject;
import com.estimote.sdk.repackaged.gson_v2_3_1.com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;

public class TaskSelectionActivity extends AppCompatActivity {

    private final String PATH = "execute_tasks/tasks.json";
    private JsonObject obj;

    private File file;
    private JsonParser parser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            parser = new JsonParser();

            try{

                obj = (JsonObject) parser.parse(new FileReader(PATH));



            }catch(FileNotFoundException e){
                e.printStackTrace();
            }

    }


    //------------------------------------------------------------------------------------


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}

