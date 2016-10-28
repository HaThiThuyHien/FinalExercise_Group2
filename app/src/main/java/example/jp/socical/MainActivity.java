package example.jp.socical;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.view.menu.MenuView;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import example.jp.socical.constant.HeaderOption;
import example.jp.socical.fragment.HomeFragment;
import example.jp.socical.fragment.LoginFragment;
import example.jp.socical.fragment.MenuFragment;
import vn.app.base.activity.CommonActivity;

public class MainActivity extends CommonActivity implements MenuFragment.NavigationDrawerCallbacks{

    @BindView(R.id.toolbar)
    RelativeLayout rlHeader;

    @BindView(R.id.headerMenu)
    ImageView ivMenu;

    @BindView(R.id.headerBack)
    ImageView ivBack;

    @BindView(R.id.headerTitle)
    TextView tvTitle;

    @BindView(R.id.headerRight)
    TextView tvHeaderRight;

    MenuFragment menuFragment;

    DrawerLayout drawerLayout;

    ActionBarDrawerToggle drawerToggle;


    @Override
    protected String getNoConnectionMessage() {
        return null;
    }

    @Override
    protected String getErrorAPIMessage() {
        return null;
    }

    @Override
    protected String getTimeOutMessage() {
        return null;
    }

    @Override
    public int setContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        rlHeader = (RelativeLayout)findViewById(R.id.toolbar);
        ivBack = (ImageView)findViewById(R.id.headerBack);
        ivMenu = (ImageView)findViewById(R.id.headerMenu);
        tvTitle = (TextView)findViewById(R.id.headerTitle);
        tvHeaderRight = (TextView)findViewById(R.id.headerRight);

        setUpInitScreen(LoginFragment.newInstance(), "LoginFragment");
    }

    @Override
    public void initData() {

    }

    @Override
    public void onCommonDataHandle(Bundle bundle) {

    }

    @Override
    public void onCommonUIHandle(Bundle bundle) {
    }


    @Override
    public void onNavigationDrawerItemSelected(int position) {

    }

    public void setToolbar(int screenNo) {

        rlHeader.setVisibility(View.GONE);
        ivBack.setVisibility(View.GONE);
        ivMenu.setVisibility(View.GONE);
        tvTitle.setVisibility(View.GONE);
        tvHeaderRight.setVisibility(View.GONE);

        switch (screenNo) {
            case HeaderOption.MENU_HOME:
                rlHeader.setVisibility(View.VISIBLE);
                ivMenu.setVisibility(View.VISIBLE);
                tvTitle.setVisibility(View.VISIBLE);
                tvTitle.setText("Home");
                break;
            case HeaderOption.MENU_PROFILE:
                rlHeader.setVisibility(View.VISIBLE);
                ivBack.setVisibility(View.VISIBLE);
                tvTitle.setVisibility(View.VISIBLE);
                tvTitle.setText("Profile");
                break;
            case HeaderOption.MENU_PROFILE_USER:
                rlHeader.setVisibility(View.VISIBLE);
                ivBack.setVisibility(View.VISIBLE);
                tvTitle.setVisibility(View.VISIBLE);
                tvTitle.setText("Profile");
                tvHeaderRight.setVisibility(View.VISIBLE);
                tvHeaderRight.setText("Update");
                break;
            case HeaderOption.MENU_FAVOURITE:
                rlHeader.setVisibility(View.VISIBLE);
                ivBack.setVisibility(View.VISIBLE);
                tvTitle.setVisibility(View.VISIBLE);
                tvTitle.setText("Favourite");
                break;
            case HeaderOption.MENU_NEARBY:
                rlHeader.setVisibility(View.VISIBLE);
                ivBack.setVisibility(View.VISIBLE);
                tvTitle.setVisibility(View.VISIBLE);
                tvTitle.setText("Nearby");
                break;
            case HeaderOption.MENU_DETAIL:
                rlHeader.setVisibility(View.VISIBLE);
                ivBack.setVisibility(View.VISIBLE);
                tvTitle.setVisibility(View.VISIBLE);
                tvTitle.setText("Detail");
                break;
            case HeaderOption.MENU_DETAIL_USER:
                rlHeader.setVisibility(View.VISIBLE);
                ivBack.setVisibility(View.VISIBLE);
                tvTitle.setVisibility(View.VISIBLE);
                tvTitle.setText("Detail");
                tvHeaderRight.setVisibility(View.VISIBLE);
                tvHeaderRight.setText("Delete");
                break;
            case HeaderOption.MENU_UPLOAD:
                rlHeader.setVisibility(View.VISIBLE);
                ivBack.setVisibility(View.VISIBLE);
                tvTitle.setVisibility(View.VISIBLE);
                tvTitle.setText("Post Image");
                break;
            case HeaderOption.MENU_FOLLOW:
                rlHeader.setVisibility(View.VISIBLE);
                ivBack.setVisibility(View.VISIBLE);
                tvTitle.setVisibility(View.VISIBLE);
                tvTitle.setText("Follow");
                break;
        }

        if (screenNo == HeaderOption.MENU_HOME) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            menuFragment = (MenuFragment)
                    getSupportFragmentManager().findFragmentById(R.id.nagigation_drawer);

            menuFragment.setUp(
                    R.id.nagigation_drawer,
                    drawerLayout);

        } else {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }

}
