package example.jp.socical.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import example.jp.socical.bean.DataTutorial;
import example.jp.socical.constant.CommonConstant;
import example.jp.socical.fragment.NewFragment;
import example.jp.socical.fragment.TutorialDetailFragment;

/**
 * Created by Hien_BRSE on 12/5/2016.
 */

public class TutorialPagerAdapter extends FragmentPagerAdapter {

    DataTutorial dataTutorial;

    public TutorialPagerAdapter(FragmentManager fm, DataTutorial dataTutorial) {
        super(fm);
        this.dataTutorial = dataTutorial;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return TutorialDetailFragment.newInstance(dataTutorial.tutorial.get(0), dataTutorial.user);
            case 1:
                return TutorialDetailFragment.newInstance(dataTutorial.tutorial.get(1), dataTutorial.user);
            case 2:
                return TutorialDetailFragment.newInstance(dataTutorial.tutorial.get(2), dataTutorial.user);
            case 3:
                return TutorialDetailFragment.newInstance(dataTutorial.tutorial.get(3), dataTutorial.user);
            default:
                return TutorialDetailFragment.newInstance(dataTutorial.tutorial.get(0), dataTutorial.user);
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
