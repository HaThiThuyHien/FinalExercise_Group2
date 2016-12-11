package example.jp.socical.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import example.jp.socical.MainActivity;
import example.jp.socical.R;
import example.jp.socical.adapter.ProfileUserAdapter;
import example.jp.socical.adapter.viewholder.ProfileUserHeaderViewHolder;
import example.jp.socical.api.request.FavouriteRequest;
import example.jp.socical.api.request.FollowRequest;
import example.jp.socical.api.request.ImageListRequest;
import example.jp.socical.api.request.ProfileUserRequest;
import example.jp.socical.api.request.UpdateProfileRequest;
import example.jp.socical.api.response.NewsResponse;
import example.jp.socical.api.response.ProfileUserResponse;
import example.jp.socical.bean.DataLoginBean;
import example.jp.socical.bean.NewsBean;
import example.jp.socical.bean.ProfileUserBean;
import example.jp.socical.callback.OnNewItemClick;
import example.jp.socical.callback.OnUserEdit;
import example.jp.socical.constant.APIConstant;
import example.jp.socical.constant.CommonConstant;
import example.jp.socical.constant.FragmentActionConstant;
import example.jp.socical.constant.HeaderOption;
import example.jp.socical.manager.UserManager;
import vn.app.base.adapter.DividerItemDecoration;
import vn.app.base.api.response.BaseResponse;
import vn.app.base.api.volley.callback.ApiObjectCallBack;
import vn.app.base.fragment.CommonFragment;
import vn.app.base.util.BitmapUtil;
import vn.app.base.util.DialogUtil;
import vn.app.base.util.FragmentUtil;
import vn.app.base.util.NetworkUtils;
import vn.app.base.util.SharedPrefUtils;

public class ProfileUserFragment extends CommonFragment implements OnUserEdit,
        SwipeRefreshLayout.OnRefreshListener,
        OnNewItemClick {

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
    ProfileUserBean headerData;

    ProfileUserHeaderViewHolder profileUserHeaderViewHolder;

    DataLoginBean currentUser;

    String userPhotoPath;

    String selectUserId;

    File avatarFile;

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
        userPhotoPath = "";
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
            reLoad();
        }
    }

    private void reLoad() {
        if (profileUserAdapter == null) {
            profileUserAdapter = new ProfileUserAdapter();
        }
        if (headerData != null ) {
            profileUserAdapter.setHeader(headerData);
            profileUserAdapter.setItems(profileUserItemBeanList);
            setUpData();
        }
    }

    private void getProfileUser(final boolean isRefresh) {
        getHeaderData();
    }

    private void setUpData(){
        rvProfileUser.setAdapter(profileUserAdapter);
        profileUserAdapter.setOnUserEdit(this);
        profileUserAdapter.setOnNewItemClick(this);
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
        showCoverNetworkLoading();
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
                hideCoverNetworkLoading();
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void setHeaderProfilUser(ProfileUserBean profilUser) {
        if (profileUserAdapter == null) {
            this.headerData = profilUser;
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
                changAvatarUser(userPhotoPath);
            }
        }
    }

    public void changAvatarUser(String filePath) {
        //Bitmap bitmap = BitmapUtil.decodeFromFile(filePath, 800, 800);
        //if (bitmap != null) {
        //ProfileUserBean headerData = profileUserAdapter.getHeader();
        this.headerData.avatar = filePath;
        profileUserAdapter.setHeader(headerData);
        profileUserAdapter.notifyDataSetChanged();
        //}
    }

    @OnClick(R.id.fabCamera)
    public void onClickCamera() {
        FragmentUtil.pushFragment(getActivity(), UploadFragment.newInstance(), null);
    }

    @Override
    public void onFragmentUIHandle(Bundle bundle) {
        super.onFragmentUIHandle(bundle);
        int isUpdate = bundle.getInt(FragmentActionConstant.HEADER_CLICK);
        if (isUpdate == FragmentActionConstant.UPDATE) {
            updateProfile();
        }
    }

    public void updateProfile() {
        if (userPhotoPath.equals("")) {
            DialogUtil.showOkBtnDialog(getActivity(), CommonConstant.title, "No file selected.");
            return;
        }

        Map<String, String> header = new HashMap<>();
        header.put(APIConstant.TOKEN, SharedPrefUtils.getAccessToken());

        Bitmap bitmap = BitmapUtil.decodeFromFile(userPhotoPath, 800, 800);

        try {
            creatFilefromBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!avatarFile.exists()) {
            DialogUtil.showOkBtnDialog(getActivity(), CommonConstant.title, "Not find file");
            return;
        }

        Map<String, File> filePart = new HashMap<>();
        filePart.put(APIConstant.AVATAR, avatarFile);

        Map<String, String> params = new HashMap<>();

        UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest(Request.Method.POST, APIConstant.UPDATE_PROFILE, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideCoverNetworkLoading();
                DialogUtil.showOkBtnDialog(getActivity(), CommonConstant.title, "Update Avatar Fail");
            }
        }, ProfileUserResponse.class, header, new Response.Listener<ProfileUserResponse>() {
            @Override
            public void onResponse(ProfileUserResponse response) {
                hideCoverNetworkLoading();
                updateData(response.profileUserData);
            }
        }, params, filePart);
        NetworkUtils.getInstance(getActivity().getApplicationContext()).addToRequestQueue(updateProfileRequest);
        showCoverNetworkLoading();
    }

    private void updateData(ProfileUserBean headerData){
        this.headerData = headerData;
        profileUserAdapter.setHeader(headerData);
        profileUserAdapter.notifyDataSetChanged();
    }

    private File creatFilefromBitmap(Bitmap bitmap) throws IOException {

        File imageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/InstagramFaker");
        imageDir.mkdir();
        avatarFile = new File(imageDir, "avatarCropped.jpg");
        OutputStream fOut = new FileOutputStream(avatarFile);
        Bitmap getBitmap = bitmap;
        getBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
        fOut.flush();
        fOut.close();
        return avatarFile;
    }

    @Override
    public void onFavouriteClick(final String imageId, final int favourite) {
        FavouriteRequest favouriteRequest = new FavouriteRequest(imageId, favourite);
        favouriteRequest.setRequestCallBack(new ApiObjectCallBack<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse data) {
                if (data.status == 1) {
                    hideCoverNetworkLoading();
                    setChangeFavourite(imageId, favourite);
                    profileUserAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFail(int failCode, String message) {
                hideCoverNetworkLoading();
                DialogUtil.showOkBtnDialog(getActivity(), CommonConstant.title, "Change of favorite failed");
            }
        });
        favouriteRequest.execute();
        showCoverNetworkLoading();
    }

    private void setChangeFavourite(String imageId, int favourite) {
        if (profileUserItemBeanList == null) {
            return;
        }
        int size = profileUserItemBeanList.size();
        for (int i = 0; i < size; i++) {
            NewsBean newsBean = profileUserItemBeanList.get(i);
            if ((newsBean != null) && (newsBean.image.id.equals(imageId))) {
                if (favourite == 0) {
                    profileUserItemBeanList.get(i).image.isFavourite= false;
                } else if (favourite == 1) {
                    profileUserItemBeanList.get(i).image.isFavourite = true;
                }
            }
        }
    }

    @Override
    public void onFollowClick(final String userId, final int follow) {
        FollowRequest followRequest = new FollowRequest(userId, follow);
        followRequest.setRequestCallBack(new ApiObjectCallBack<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse data) {
                if (data.status == 1) {
                    hideCoverNetworkLoading();
                    setChangeFollow(userId, follow);
                    profileUserAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFail(int failCode, String message) {
                DialogUtil.showOkBtnDialog(getActivity(), CommonConstant.title, "Change of follow failed");
                initialNetworkError();
            }
        });
        followRequest.execute();
        showCoverNetworkLoading();
    }

    private void setChangeFollow(String userId, int follow) {
        if (profileUserItemBeanList == null) {
            return;
        }
        int size = profileUserItemBeanList.size();
        for (int i = 0; i < size; i++) {
            NewsBean newsBean = profileUserItemBeanList.get(i);
            if ((newsBean != null) && (newsBean.user.id.equals(userId))) {
                if (follow == 0) {
                    profileUserItemBeanList.get(i).user.isFollowing = false;
                } else if (follow == 1) {
                    profileUserItemBeanList.get(i).user.isFollowing = true;
                }
            }
        }
    }

    @Override
    public void onAvatarClick(String userId) {
        //FragmentUtil.pushFragment(getActivity(), ProfileUserFragment.newInstance(userId),null, "ProfileUserFragment");
    }

    @Override
    public void onPictureClick(NewsBean imageProfile) {
        FragmentUtil.pushFragment(getActivity(), ImageDetailFragment.newInstance(imageProfile),null, "ImageDetailFragment");
    }

    @Override
    public void onPinMapClick(String strlat, String strlong) {
        String strGeo = "geo:" + strlat + "," + strlong + "?z=zom";
        Uri uri = Uri.parse(strGeo);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            getActivity().startActivity(mapIntent);
        }
    }
}
