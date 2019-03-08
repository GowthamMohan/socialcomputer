package saarland.dfki.socialanxietytrainer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;




public class TutorialFragment extends Fragment {


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
        tutorialTextView.setText((index + 1) + "/8 \n\n" + text);



        return view;
    }




}





