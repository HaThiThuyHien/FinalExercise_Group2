package example.jp.socical;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.view.menu.MenuView;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
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

    @BindView(R.id.headerUpdate)
    TextView tvUpdate;

    @BindView(R.id.headerDelete)
    TextView tvDelete;

    @BindView(R.id.headerTitle)
    TextView tvTitle;

    private MenuFragment menuFragment;

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
        setUpInitScreen(LoginFragment.newInstance(), null);

//        menuFragment = (MenuFragment)getSupportFragmentManager().findFragmentById(R.id.nagigation_drawer);
//
//
//        btnTest = (Button)findViewById(R.id.btnTest);
//        btnTest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                menuFragment.setUp(R.id.nagigation_drawer, (DrawerLayout)findViewById(R.id.drawer_layout));
//            }
//        });
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

    private void handleLeftIcon(int value) {
        switch (value) {
            case HeaderOption.LEFT_NO_OPTION:
                showAndHideIconLeft(null);
                break;
            case HeaderOption.LEFT_MENU:
                showAndHideIconLeft(ivMenu);
                break;
            case HeaderOption.LEFT_BACK:
                showAndHideIconLeft(ivBack);
                break;
        }
    }

    private void showAndHideIconLeft(View target) {
        showOrHide(ivMenu, target);
        showOrHide(ivBack, target);
    }

    private void handleRightView(int value) {
        switch (value) {
            case HeaderOption.RIGHT_NO_OPTION:
                showAdnHideRightView(null);
                break;
            case HeaderOption.RIGHT_UPDATE:
                showAdnHideRightView(tvUpdate);
                break;
            case HeaderOption.RIGHT_DELETE:
                showAdnHideRightView(tvDelete);
                break;
        }
    }

    private void showAdnHideRightView(View target) {
        showOrHide(tvUpdate, target);
        showOrHide(tvUpdate, target);
    }

    protected void showOrHide(View subject, View target) {
        subject.setVisibility(subject == target ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

    }

}
