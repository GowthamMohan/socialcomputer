package saarland.dfki.socialanxietytrainer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static com.estimote.sdk.EstimoteSDK.getApplicationContext;


public class TutorialFragment extends Fragment {


    public TutorialFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(getArguments().getInt("index") == 8) {
            Intent intent = new Intent(getActivity(), SelectGenderActivity.class);
            startActivity(intent);
        }

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tutorial, container, false);
        TextView tutorialTextView = (TextView) view.findViewById(R.id.tutorial_text);
        String text = getArguments().getString("current_text");
        int index = getArguments().getInt("index");
        tutorialTextView.setText((index + 1) + ".\n\n" + text);



        return view;
    }




}





