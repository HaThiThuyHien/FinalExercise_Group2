package example.jp.socical.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import example.jp.socical.fragment.FollowFragment;
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
                return NewFragment.newInstance();
            case 1:
                return FollowFragment.newInstance();
            default:
                return NewFragment.newInstance();
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
