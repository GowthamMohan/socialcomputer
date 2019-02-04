package saarland.dfki.socialanxietytrainer.tutorial;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import saarland.dfki.socialanxietytrainer.TutorialFragment;

public class TutorialPagerAdapter extends FragmentPagerAdapter {

    private FragmentManager fragmentManager;

    public TutorialPagerAdapter(FragmentManager fragmentManager) {
       super(fragmentManager);
    }

    @Override
    public Fragment getItem(int i){
        Fragment fragment = new TutorialFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index", i);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return 8;
    }
}