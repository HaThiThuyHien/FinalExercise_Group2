package example.jp.socical.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.List;

import example.jp.socical.MainActivity;
import example.jp.socical.R;
import example.jp.socical.adapter.NewListAdapter;
import example.jp.socical.api.request.FavouriteListRequest;
import example.jp.socical.api.request.FavouriteRequest;
import example.jp.socical.api.request.FollowRequest;
import example.jp.socical.api.response.NewsResponse;
import example.jp.socical.bean.NewsBean;
import example.jp.socical.callback.OnNewItemClick;
import example.jp.socical.constant.CommonConstant;
import example.jp.socical.constant.HeaderOption;
import vn.app.base.api.response.BaseResponse;
import vn.app.base.api.volley.callback.ApiObjectCallBack;
import vn.app.base.fragment.BaseSwipeRefreshFragment;
import vn.app.base.util.DialogUtil;
import vn.app.base.util.FragmentUtil;

/**
 * Created by Toi on 12/11/2016.
 */

public class FavouriteFragment extends BaseSwipeRefreshFragment implements OnNewItemClick{

    NewListAdapter favouriteAdapter;

    List<NewsBean> favouriteBeanList;

    String userId;

    public static FavouriteFragment newInstance() {
        FavouriteFragment favouriteFragment = new FavouriteFragment();
        return favouriteFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_favouritelist;
    }

    @Override
    protected void onRefreshData() {

    }

    @Override
    protected void initView(View root) {
        ((MainActivity)getActivity()).setToolbar(HeaderOption.MENU_FAVOURITE);
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected void getArgument(Bundle bundle) {

    }

    @Override
    protected void initData() {
        if (favouriteBeanList == null) {
            getFavouriteData();
        } else {
            setDisplayData(favouriteBeanList);
        }
    }

    private void getFavouriteData(){
        FavouriteListRequest favouriteListRequest = new FavouriteListRequest(userId);
        favouriteListRequest.setRequestCallBack(new ApiObjectCallBack<NewsResponse>() {
            @Override
            public void onSuccess(NewsResponse data) {
                hideCoverNetworkLoading();
                initialResponseHandled();
                setDisplayData(data.data);
            }

            @Override
            public void onFail(int failCode, String message) {
                hideCoverNetworkLoading();
                DialogUtil.showOkBtnDialog(getActivity(), CommonConstant.title, "Get Favourite List Error");
            }
        });
        favouriteListRequest.execute();
        showCoverNetworkLoading();
    }

    private void setDisplayData(List<NewsBean> inFavouriteBeanList){
        this.favouriteBeanList = inFavouriteBeanList;
        favouriteAdapter = new NewListAdapter(favouriteBeanList);
        rvList.setAdapter(favouriteAdapter);
        favouriteAdapter.setOnNewItemClick(this);
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
                    favouriteAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFail(int failCode, String message) {
                initialNetworkError();
            }
        });
        favouriteRequest.execute();
        showCoverNetworkLoading();
    }

    private void setChangeFavourite(String imageId, int favourite) {
        if (favouriteBeanList == null) {
            return;
        }
        int size = favouriteBeanList.size();
        for (int i = 0; i < size; i++) {
            NewsBean newsBean = favouriteBeanList.get(i);
            if ((newsBean != null) && (newsBean.image.id.equals(imageId))) {
                if (favourite == 0) {
                    favouriteBeanList.get(i).image.isFavourite= false;
                } else if (favourite == 1) {
                    favouriteBeanList.get(i).image.isFavourite = true;
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
                    favouriteAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFail(int failCode, String message) {
                initialNetworkError();
            }
        });
        followRequest.execute();
        showCoverNetworkLoading();
    }

    private void setChangeFollow(String userId, int follow) {
        if (favouriteBeanList == null) {
            return;
        }
        int size = favouriteBeanList.size();
        for (int i = 0; i < size; i++) {
            NewsBean newsBean = favouriteBeanList.get(i);
            if ((newsBean != null) && (newsBean.user.id.equals(userId))) {
                if (follow == 0) {
                    favouriteBeanList.get(i).user.isFollowing = false;
                } else if (follow == 1) {
                    favouriteBeanList.get(i).user.isFollowing = true;
                }
            }
        }
    }

    @Override
    public void onAvatarClick(String userId) {
        FragmentUtil.pushFragment(getActivity(), ProfileUserFragment.newInstance(userId),null, "ProfileUserFragment");
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
