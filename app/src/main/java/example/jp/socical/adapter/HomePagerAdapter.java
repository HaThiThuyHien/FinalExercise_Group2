package example.jp.socical.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import example.jp.socical.constant.CommonConstant;
import example.jp.socical.fragment.NewFragment;

/**
 * Created by HaHien on 10/13/2016.
 */

public class HomePagerAdapter extends FragmentPagerAdapter{

    private String tabTitles[] = new String[] { "New", "Follow" };

    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return NewFragment.newInstance(CommonConstant.HOME_NEW);
            case 1:
                return NewFragment.newInstance(CommonConstant.HOME_FOLLOW);
            default:
                return NewFragment.newInstance(CommonConstant.HOME_NEW);
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

}
