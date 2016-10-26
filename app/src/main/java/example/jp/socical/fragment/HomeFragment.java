package example.jp.socical.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.BindView;
import example.jp.socical.MainActivity;
import example.jp.socical.R;
import example.jp.socical.adapter.HomePagerAdapter;
import example.jp.socical.constant.HeaderOption;

public class HomeFragment extends HeaderFragment implements MenuFragment.NavigationDrawerCallbacks{

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

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

        viewPager = (ViewPager)root.findViewById(R.id.vpPager);
        tabLayout = (TabLayout)root.findViewById(R.id.layout_tab);

//        toolbar = (Toolbar)getActivity().findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout)root.findViewById(R.id.drawer_layout);

        menuFragment =(MenuFragment)
                getActivity().getSupportFragmentManager().findFragmentById(R.id.nagigation_drawer);
    }

    @Override
    protected void initData() {

        HomePagerAdapter homePagerAdapter;
        homePagerAdapter = new HomePagerAdapter(getActivity().getSupportFragmentManager());

        viewPager.setAdapter(homePagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public String getScreenTitle() {
        return "Home";
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

    }

    @Override
    protected int getLeftIcon() {
        return R.id.headerMenu;
    }
}
