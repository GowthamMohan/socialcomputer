package saarland.dfki.socialanxietytrainer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import saarland.dfki.socialanxietytrainer.tutorial.TutorialPagerAdapter;

//source https://www.youtube.com/watch?v=9phSvgqpNtE

public class TutorialActivity extends FragmentActivity {

    private ViewPager viewPager;
    private ArrayList<String> text_list;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        text_list = setupTexts();

        setContentView(R.layout.activity_tutorial);
        viewPager = (ViewPager) findViewById(R.id.view_pager_tutorial);
        TutorialPagerAdapter tutorialPagerAdapter = new TutorialPagerAdapter(getSupportFragmentManager(),text_list);
        viewPager.setAdapter(tutorialPagerAdapter);

    }

    public ArrayList<String> setupTexts(){

        ArrayList<String> tmp = new ArrayList<>();

        try {
            //read file to string

            InputStream stream = getAssets().open("tutorialTexts.json");

            BufferedReader br = new BufferedReader(new InputStreamReader(stream));
            StringBuilder str = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                str.append(line);
            }
            String jsonStr = str.toString();
            br.close();
            //create json object from string, extract and save the content
            JSONObject obj = new JSONObject(jsonStr);
            JSONArray texts= obj.getJSONArray("texts");

            for(int i = 0; i < texts.length(); i++) {
                JSONObject current_task = texts.getJSONObject(i);
                String content = current_task.getString("content");
                tmp.add(content);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }catch (JSONException e){
            e.printStackTrace();
        }

        return tmp;

    }
}



