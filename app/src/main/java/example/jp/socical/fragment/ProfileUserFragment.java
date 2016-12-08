package example.jp.socical.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import example.jp.socical.MainActivity;
import example.jp.socical.R;
import example.jp.socical.adapter.ProfileUserAdapter;
import example.jp.socical.adapter.viewholder.ProfileUserHeaderViewHolder;
import example.jp.socical.api.request.ImageListRequest;
import example.jp.socical.api.request.ProfileUserRequest;
import example.jp.socical.api.response.NewsResponse;
import example.jp.socical.api.response.ProfileUserResponse;
import example.jp.socical.bean.DataLoginBean;
import example.jp.socical.bean.NewsBean;
import example.jp.socical.bean.ProfileUserBean;
import example.jp.socical.callback.OnUserEdit;
import example.jp.socical.constant.FragmentActionConstant;
import example.jp.socical.constant.HeaderOption;
import example.jp.socical.manager.UserManager;
import vn.app.base.adapter.DividerItemDecoration;
import vn.app.base.api.volley.callback.ApiObjectCallBack;
import vn.app.base.callback.OnRecyclerViewItemClick;
import vn.app.base.fragment.CommonFragment;
import vn.app.base.util.FragmentUtil;
import vn.app.base.util.SharedPrefUtils;

public class ProfileUserFragment extends CommonFragment implements OnUserEdit, SwipeRefreshLayout.OnRefreshListener, OnRecyclerViewItemClick {

    public static final String USER_PHOTO = "USER_PHOTO";

    public static final String USER_ID = "USER_ID";

    @BindView(R.id.rvProfileUser)
    RecyclerView rvProfileUser;

    @BindView(R.id.swipe_refresh_profile_user_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.fabCamera)
    FloatingActionButton fbCamera;

    ProfileUserAdapter profileUserAdapter;

    List<NewsBean> profileUserItemBeanList;

    NewsBean profileUserBean;

    ProfileUserHeaderViewHolder profileUserHeaderViewHolder;

    DataLoginBean currentUser;

    String userPhotoPath;

    String selectUserId;

    public static ProfileUserFragment newInstance(String selectUserIdUserId) {
        ProfileUserFragment profileUserFragment = new ProfileUserFragment();
        Bundle bundle = new Bundle();
        bundle.putString("USER_ID", selectUserIdUserId);
        profileUserFragment.setArguments(bundle);
        return profileUserFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_profile_user;
    }

    @Override
    protected void initView(View root) {
        swipeRefreshLayout.setOnRefreshListener(this);

        rvProfileUser.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvProfileUser.addItemDecoration(new DividerItemDecoration(getActivity(), null));
        setCanRefresh(false);

        currentUser = UserManager.getCurrentUser();

        if (selectUserId.equals(currentUser.id)) {
            ((MainActivity)getActivity()).setToolbar(HeaderOption.MENU_PROFILE_USER);
            fbCamera.setVisibility(View.VISIBLE);
        } else {
            ((MainActivity)getActivity()).setToolbar(HeaderOption.MENU_PROFILE);
            fbCamera.setVisibility(View.GONE);
        }
    }

    public void setCanRefresh(boolean canRefresh) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setEnabled(canRefresh);
        }
    }

    @Override
    protected void getArgument(Bundle bundle) {
        selectUserId = bundle.getString(USER_ID);
    }

    @Override
    protected void initData() {
        if (profileUserItemBeanList == null) {
            getProfileUser(false);
        }else {
            //setUserData();
        }
    }

    private void getProfileUser(final boolean isRefresh) {
        //setUpData();
        getHeaderData();
    }

    private void setUpData(){
        //profileUserAdapter = new ProfileUserAdapter();
        //getHeaderData();
        //getImageDataList();
        rvProfileUser.setAdapter(profileUserAdapter);
        profileUserAdapter.setOnUserEdit(this);
        profileUserAdapter.setOnRecyclerViewItemClick(this);
    }

    private void getHeaderData(){
        ProfileUserRequest userRequest;

        if (selectUserId.equals(currentUser.id)) {
            userRequest = new ProfileUserRequest("");
        } else {
            userRequest = new ProfileUserRequest(selectUserId);
        }

        userRequest.setRequestCallBack(new ApiObjectCallBack<ProfileUserResponse>() {
            @Override
            public void onSuccess(ProfileUserResponse data) {
                setHeaderProfilUser(data.profileUserData);

                getImageDataList();
            }

            @Override
            public void onFail(int failCode, String message) {
                initialNetworkError();
            }
        });

        userRequest.execute();
    }

    private void getImageDataList() {
        ImageListRequest imageListRequest;

        if (selectUserId.equals(currentUser.id)) {
            imageListRequest = new ImageListRequest("");
        } else {
            imageListRequest = new ImageListRequest(selectUserId);
        }

        imageListRequest.setRequestCallBack(new ApiObjectCallBack<NewsResponse>() {
            @Override
            public void onSuccess(NewsResponse data) {
                setImageList(data.data);
                setUpData();
            }

            @Override
            public void onFail(int failCode, String message) {
                initialNetworkError();
            }
        });
        imageListRequest.execute();
    }

    private void setHeaderProfilUser(ProfileUserBean profilUser) {
        if (profileUserAdapter == null) {
            profileUserAdapter = new ProfileUserAdapter();
            profileUserAdapter.setHeader(profilUser);
        }
    }

    private void setImageList(List<NewsBean> imageList) {
        profileUserItemBeanList = imageList;
        profileUserAdapter.setItems(profileUserItemBeanList);
    }


    @Override
    protected void initialResponseHandled() {
        super.initialResponseHandled();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onChangePhoto(int position) {
        if (commonListener != null) {
            Bundle bundle = new Bundle();
            bundle.putInt(FragmentActionConstant.FRAGMENT_ACTION, FragmentActionConstant.PICK_IMAGE);
            commonListener.onCommonUIHandle(bundle);
        }
    }

    @Override
    public void onClickPhoto(String url) {
    }

    @Override
    public void onFragmentDataHandle(Bundle bundle) {
        super.onFragmentDataHandle(bundle);

        if (bundle != null) {
            userPhotoPath = bundle.getString(USER_PHOTO, null);
            if (userPhotoPath != null) {
                profileUserAdapter.notifyDataSetChanged();
            }
        }
    }



    @Override
    public void onItemClick(View view, int position) {

    }

    @OnClick(R.id.fabCamera)
    public void onClickCamera() {
        FragmentUtil.pushFragment(getActivity(), UploadFragment.newInstance(), null);
    }

    @Override
    public void onFragmentUIHandle(Bundle bundle) {
        super.onFragmentUIHandle(bundle);
        //int isUpdate = bundle.getInt("")
    }
}
