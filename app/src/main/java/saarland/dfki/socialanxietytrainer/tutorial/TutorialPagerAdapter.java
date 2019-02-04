package saarland.dfki.socialanxietytrainer.tutorial;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.ArrayList;

import saarland.dfki.socialanxietytrainer.TutorialFragment;

public class TutorialPagerAdapter extends FragmentPagerAdapter {

    private FragmentManager fragmentManager;
    private ArrayList<String> text_list;

    public TutorialPagerAdapter(FragmentManager fragmentManager, ArrayList<String> text_list) {
       super(fragmentManager);
       this.text_list = text_list;

    }

    @Override
    public Fragment getItem(int i){
        Fragment fragment = new TutorialFragment();
        Bundle bundle = new Bundle();
        if(i < text_list.size()) {
            bundle.putString("current_text", text_list.get(i));
        }
        bundle.putInt("index",i);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return 9;
    }


}