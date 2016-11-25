package example.jp.socical.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Loader;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import example.jp.socical.R;
import example.jp.socical.bean.DataLoginBean;
import example.jp.socical.bean.UserBean;
import example.jp.socical.manager.UserManager;
import vn.app.base.imageloader.ImageLoader;
import vn.app.base.util.FragmentUtil;
import vn.app.base.util.StringUtil;

public class MenuFragment extends Fragment implements View.OnClickListener{

    /**
     * Remember the position of the selected item.
     */
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    /**
     * Per the design guidelines, you should show the drawer on launch until the user manually
     * expands it. This shared preference tracks this.
     */
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    /**
     * A pointer to the current callbacks instance (the Activity).
     */
    private NavigationDrawerCallbacks mCallbacks;

    private DrawerLayout mDrawerLayout;

    public View contentView;

    private View mFragmentContainerView;

    private int mCurrentSelectedPosition = 0;
    private boolean mUserLearnedDrawer;

    TextView tvUserName;

    CircleImageView civAvatar;

    LinearLayout llMenu1;
    LinearLayout llMenu2;
    LinearLayout llMenu3;
    LinearLayout llMenu4;
    LinearLayout llMenu5;
    LinearLayout llMenu6;

    int currentMenuPos = 0;

    View rootView;

    DataLoginBean currentUser;

    public MenuFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);
        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
        }

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(
                R.layout.fragment_menu, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initControl(view);
    }

    private void initControl(View view) {
        tvUserName = (TextView)view.findViewById(R.id.txtUser);
        civAvatar = (CircleImageView)view.findViewById(R.id.menu_avatar);

        llMenu1 = (LinearLayout)view.findViewById(R.id.menu_2);
        llMenu2 = (LinearLayout)view.findViewById(R.id.menu_3);
        llMenu3 = (LinearLayout)view.findViewById(R.id.menu_4);
        llMenu4 = (LinearLayout)view.findViewById(R.id.menu_5);
        llMenu5 = (LinearLayout)view.findViewById(R.id.menu_6);
        llMenu6 = (LinearLayout)view.findViewById(R.id.menu_7);

        tvUserName.setOnClickListener(this);
        civAvatar.setOnClickListener(this);

        llMenu1.setOnClickListener(this);
        llMenu2.setOnClickListener(this);
        llMenu3.setOnClickListener(this);
        llMenu4.setOnClickListener(this);
        llMenu5.setOnClickListener(this);
        llMenu6.setOnClickListener(this);

        currentUser = UserManager.getCurrentUser();
        ImageLoader.loadImage(view.getContext(), R.drawable.loading_list_image_220, currentUser.avatar, civAvatar);
        StringUtil.displayText(currentUser.username, tvUserName);
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    public void setCurrentMenu(int position) {
        View currentMenu = getMenuByPosition(currentMenuPos);
        if (currentMenu != null) {
            currentMenu.setSelected(false);
        }

        this.currentMenuPos = position;
        currentMenu = getMenuByPosition(currentMenuPos);

        if (currentMenu != null) {
            currentMenu.setSelected(true);
        }
    }

    public View getMenuByPosition(int position) {
        switch (position) {
            case 1:
                return llMenu1;
            case 2:
                return llMenu2;
            case 3:
                return llMenu3;
            case 4:
                return llMenu4;
            case 5:
                return llMenu5;
            case 6:
                return llMenu6;
            default:
                return null;
        }
    }

    /**
     * Users of this fragment must call this method to set up the navigation drawer interactions.
     *
     * @param fragmentId   The android:id of this fragment in its activity's layout.
     * @param drawerLayout The DrawerLayout containing this fragment's UI.
     */
    public void setUp(int fragmentId, DrawerLayout drawerLayout) {

        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        contentView = getActivity().findViewById(R.id.container);

        mDrawerLayout.setScrimColor(Color.TRANSPARENT);

        ImageView imgDrawer = (ImageView)getActivity().findViewById(R.id.headerMenu);

        imgDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawers();
                } else {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }

                getActivity().invalidateOptionsMenu();
            }
        });

        mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                if (!isAdded()) {
                    return;
                }
                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(getActivity());
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
                }

                getActivity().invalidateOptionsMenu();

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                if (!isAdded()) {
                    return;
                }
                getActivity().invalidateOptionsMenu();

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        setCurrentMenu(3);

    }

    private void selectItem(int position) {
        if (position != 2 && position != 4 && position != 5) {
            setCurrentMenu(position);
        }
        mCurrentSelectedPosition = position;
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(position);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity = context instanceof Activity ? (Activity)context : null;

        if (activity != null) {
            try {
                mCallbacks = (NavigationDrawerCallbacks)activity;
            }catch (ClassCastException e){
                throw new ClassCastException("Activity must implement NavigationDraweraCallbacks");
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtUser:
            case R.id.menu_avatar:
                // chuyen sang man hinh Profile
                FragmentUtil.pushFragment(getActivity(), ProfileUserFragment.newInstance(currentUser.id), null, "ProfileUserFragment");
                setCurrentMenu(0);
                break;
            case R.id.menu_2:
                // chuyen sang man hinh Home
                FragmentUtil.pushFragment(getActivity(), HomeFragment.newInstance(), null, "HomeFragment");
                setCurrentMenu(1);
                break;
            case R.id.menu_3:
                // chuyen sang man hinh Post
                FragmentUtil.pushFragment(getActivity(), UploadFragment.newInstance(), null, "UploadFragment");
                setCurrentMenu(2);
                break;
            case R.id.menu_4:
                // chuyen sang man hinh Favourite
                //FragmentUtil.pushFragment(getActivity(), UploadFragment.newInstance(), null, "UploadFragment");
                setCurrentMenu(3);
                break;
            case R.id.menu_5:
                // chuyen sang man hinh Nearby
                FragmentUtil.pushFragment(getActivity(), NearbyFragment.newInstance(), null, "NearbyFragment");
                setCurrentMenu(4);
                break;
            case R.id.menu_6:
                // chuyen sang man hinh Follow
                FragmentUtil.pushFragment(getActivity(), FollowFragment.newInstance(), null, "FollowFragment");
                setCurrentMenu(5);
                break;
            case R.id.menu_7:
                // chuyen sang man hinh Logout
                setCurrentMenu(6);
                logout();
                break;
            default:
                break;
        }

    }

    private void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Exit");
        builder.setMessage("Are you sure you want to exit application?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UserManager.clearUserData();
                FragmentUtil.replaceFragment(getActivity(), LoginFragment.newInstance(), null);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();

    }

    /**
     * Callbacks interface that all activities using this fragment must implement.
     */
    public interface NavigationDrawerCallbacks {
        /**
         * Called when an item in the navigation drawer is selected.
         */
        void onNavigationDrawerItemSelected(int position);
    }



}
