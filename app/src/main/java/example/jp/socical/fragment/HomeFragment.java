package example.jp.socical.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import butterknife.BindView;
import example.jp.socical.MainActivity;
import example.jp.socical.R;
import example.jp.socical.adapter.HomePagerAdapter;
import example.jp.socical.constant.HeaderOption;

public class HomeFragment extends HeaderFragment{

    @BindView(R.id.vpPager)
    ViewPager viewPager;

    @BindView(R.id.layout_tab)
    TabLayout tabLayout;

    MenuFragment menuFragment;

    public static HomeFragment newInstance() {
        HomeFragment homeFragment = new HomeFragment();
        return homeFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void getArgument(Bundle bundle) {

    }

    @Override
    protected void initView(View root) {
        super.initView(root);

        ((MainActivity)getActivity()).setToolbar(HeaderOption.MENU_HOME);

        menuFragment =(MenuFragment)
                getActivity().getSupportFragmentManager().findFragmentById(R.id.nagigation_drawer);
    }

    @Override
    protected void initData() {

        HomePagerAdapter homePagerAdapter;
        homePagerAdapter = new HomePagerAdapter(getChildFragmentManager());

        viewPager.setAdapter(homePagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public String getScreenTitle() {
        return "Home";
    }

    @Override
    protected int getLeftIcon() {
        return super.getLeftIcon();
    }
}
