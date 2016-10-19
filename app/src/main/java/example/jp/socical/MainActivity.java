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

    @BindView(R.id.headerTitle)
    TextView tvTitle;

    @BindView(R.id.headerRight)
    TextView tvHeaderRight;

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

        ivBack = (ImageView)findViewById(R.id.headerBack);
        ivMenu = (ImageView)findViewById(R.id.headerMenu);
        tvTitle = (TextView)findViewById(R.id.headerTitle);
        tvHeaderRight = (TextView)findViewById(R.id.headerRight);

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
                //showAdnHideRightView(tvUpdate);
                break;
            case HeaderOption.RIGHT_DELETE:
                //showAdnHideRightView(tvDelete);
                break;
        }
    }

    private void showAdnHideRightView(View target) {
        //showOrHide(tvUpdate, target);
        //showOrHide(tvUpdate, target);
    }

    protected void showOrHide(View subject, View target) {
        subject.setVisibility(subject == target ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

    }

    public void setToolbar(int screenNo) {

        ivBack.setVisibility(View.GONE);
        ivMenu.setVisibility(View.GONE);
        tvTitle.setVisibility(View.GONE);
        tvHeaderRight.setVisibility(View.GONE);

        switch (screenNo) {
            case HeaderOption.MENU_HOME:
                ivMenu.setVisibility(View.VISIBLE);
                tvTitle.setVisibility(View.VISIBLE);
                tvTitle.setText("Home");
                break;
            case HeaderOption.MENU_PROFILE:
                ivBack.setVisibility(View.VISIBLE);
                tvTitle.setVisibility(View.VISIBLE);
                tvTitle.setText("Profile");
                break;
            case HeaderOption.MENU_PROFILE_USER:
                ivBack.setVisibility(View.VISIBLE);
                tvTitle.setVisibility(View.VISIBLE);
                tvTitle.setText("Profile");
                tvHeaderRight.setVisibility(View.VISIBLE);
                tvHeaderRight.setText("Update");
                break;
            case HeaderOption.MENU_FAVOURITE:
                ivBack.setVisibility(View.VISIBLE);
                tvTitle.setVisibility(View.VISIBLE);
                tvTitle.setText("Favourite");
                break;
            case HeaderOption.MENU_NEARBY:
                ivBack.setVisibility(View.VISIBLE);
                tvTitle.setVisibility(View.VISIBLE);
                tvTitle.setText("Nearby");
                break;
            case HeaderOption.MENU_DETAIL:
                ivBack.setVisibility(View.VISIBLE);
                tvTitle.setVisibility(View.VISIBLE);
                tvTitle.setText("Detail");
                break;
            case HeaderOption.MENU_DETAIL_USER:
                ivBack.setVisibility(View.VISIBLE);
                tvTitle.setVisibility(View.VISIBLE);
                tvTitle.setText("Detail");
                tvHeaderRight.setVisibility(View.VISIBLE);
                tvHeaderRight.setText("Delete");
                break;
            case HeaderOption.MENU_UPLOAD:
                ivBack.setVisibility(View.VISIBLE);
                tvTitle.setVisibility(View.VISIBLE);
                tvTitle.setText("Post Image");
                break;
            case HeaderOption.MENU_FOLLOW:
                ivBack.setVisibility(View.VISIBLE);
                tvTitle.setVisibility(View.VISIBLE);
                tvTitle.setText("Follow");
                break;
        }
    }

}
