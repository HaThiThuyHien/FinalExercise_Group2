package example.jp.socical;

import android.app.Activity;
import android.content.Intent;
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

import com.android.camera.CropImageIntentBuilder;

import butterknife.BindView;
import butterknife.OnClick;
import example.jp.socical.callback.OnUpdateProfile;
import example.jp.socical.constant.FragmentActionConstant;
import example.jp.socical.constant.HeaderOption;
import example.jp.socical.fragment.HomeFragment;
import example.jp.socical.fragment.LoginFragment;
import example.jp.socical.fragment.MenuFragment;
import example.jp.socical.fragment.ProfileUserFragment;
import example.jp.socical.fragment.RegisterFragment;
import vn.app.base.activity.CommonActivity;
import vn.app.base.util.FragmentUtil;
import vn.app.base.util.ImagePickerUtil;

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

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    MenuFragment menuFragment;

    ActionBarDrawerToggle drawerToggle;

    ImagePickerUtil imagePickerUtil = new ImagePickerUtil();

    int iScreenNo = 0;

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

        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
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
        if (bundle == null) {
            return;
        }

        if (bundle.containsKey(FragmentActionConstant.FRAGMENT_ACTION)) {
            int framentAction = bundle.getInt(FragmentActionConstant.FRAGMENT_ACTION);
            if (framentAction == FragmentActionConstant.PICK_IMAGE) {
                handlePickPhoto();
            }
        }
    }

    private void handlePickPhoto() {
        imagePickerUtil.pickImage(this, false);
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
        iScreenNo = screenNo;

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
                tvTitle.setText("User");
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

    @OnClick(R.id.headerBack)
    public void clickHeaderBack(){
        FragmentUtil.popBackStack(this);
    }

    @OnClick(R.id.headerRight)
    public void clickHeaderRight(){
        if (iScreenNo == HeaderOption.MENU_PROFILE_USER){
            if(fragmentListener != null){
                Bundle bundle = new Bundle();
                bundle.putInt(FragmentActionConstant.HEADER_CLICK, FragmentActionConstant.UPDATE);
                fragmentListener.onFragmentUIHandle(bundle);
            }

        }else if (iScreenNo == HeaderOption.MENU_DETAIL_USER){
            Bundle bundle = new Bundle();
            bundle.putInt(FragmentActionConstant.HEADER_CLICK, FragmentActionConstant.DELETE);
            fragmentListener.onFragmentUIHandle(bundle);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagePickerUtil.handleResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == ImagePickerUtil.PICTURE_PICKER_REQUEST_CODE) {
                imagePickerUtil.createImageFile(this);
                CropImageIntentBuilder cropImage = new CropImageIntentBuilder(800, 800, imagePickerUtil.outputFileUri);
                cropImage.setOutlineColor(0xFF03A9F4);
                cropImage.setSourceImage(data.getData());
                startActivityForResult(cropImage.getIntent(this), ImagePickerUtil.PICTURE_CROP_REQUEST_CODE);
            } else if (requestCode == ImagePickerUtil.PICTURE_CROP_REQUEST_CODE) {
                if (fragmentListener != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString(ProfileUserFragment.USER_PHOTO, imagePickerUtil.outputFileUri.getPath());
                    fragmentListener.onFragmentDataHandle(bundle);
                }
            }
        }
    }
}
