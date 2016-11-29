package example.jp.socical.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import example.jp.socical.R;
import example.jp.socical.adapter.NewListAdapter;
import example.jp.socical.api.request.FavouriteRequest;
import example.jp.socical.api.request.FollowRequest;
import example.jp.socical.api.request.NewsRequest;
import example.jp.socical.api.response.NewsResponse;
import example.jp.socical.bean.NewsBean;
import example.jp.socical.callback.OnNewItemClick;
import vn.app.base.api.response.BaseResponse;
import vn.app.base.api.volley.callback.ApiObjectCallBack;
import vn.app.base.customview.endlessrecycler.EndlessRecyclerOnScrollListener;
import vn.app.base.util.DebugLog;
import vn.app.base.util.FragmentUtil;

public class NewFragment extends NoHeaderFragment implements SwipeRefreshLayout.OnRefreshListener,
        OnNewItemClick{

    public static final String HOME_TYPE = "HOME_TYPE";

    @BindView(R.id.rvNew)
    RecyclerView recyclerView;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.fabCamera)
    FloatingActionButton fabCamera;

    NewListAdapter newListAdapter;

    List<NewsBean> newBeanList;

    NewsBean userProfileBean;

    int type;
    String last_query_timestamp;
    int num;

    int home_type;

    public static NewFragment newInstance(int type) {
        NewFragment fragment = new NewFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(HOME_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_new;
    }

    @Override
    protected void getArgument(Bundle bundle) {
        home_type = bundle.getInt(HOME_TYPE);
    }

    @Override
    protected void initView(View root) {
        super.initView(root);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setEnabled(true);
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore(int currentPage) {
                getNews(true);
                //newListAdapter.notifyDataSetChanged();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    };

    public void loadNextDataFromApi(int page) {

    }

    @Override
    protected void initData() {
        if (newBeanList == null) {
            getNews(false);
        } else {
            handleNewsData(newBeanList, true);
        }
    }

    @OnClick(R.id.fabCamera)
    public void clickFabCamera() {
        // Di chuyển đến màn hình Detail
        FragmentUtil.pushFragment(getActivity(), UploadFragment.newInstance(), null);
    }

    private void getNews(final boolean isRefresh) {
        NewsRequest newsRequest;

        if (!isRefresh) {
            newsRequest = new NewsRequest(home_type, "", 0);
        } else {
            String str_last_time;
            newsRequest = new NewsRequest(home_type, last_query_timestamp, 0);
        }

        newsRequest.setRequestCallBack(new ApiObjectCallBack<NewsResponse>() {
            @Override
            public void onSuccess(NewsResponse data) {
                initialResponseHandled();
                //setupData(isRefresh);

                //if (data.data != null) {
                //    newBeanList = data.data;
                //}

                handleNewsData(data.data, isRefresh);
            }

            @Override
            public void onFail(int failCode, String message) {
                initialNetworkError();
            }
        });
        newsRequest.execute();
    }

    private void setupData(boolean refresh, List<NewsBean> inNewsBean) {
        if (refresh && newBeanList != null) {
            newBeanList.clear();
        } else {
            newBeanList = new ArrayList<>();
        }


    }

    public void getLasttime(List<NewsBean> inNewsBean) {
        if (inNewsBean != null) {
            int size = inNewsBean.size();
            if (size > 0) {
                NewsBean newsBeanLast = inNewsBean.get(size - 1);
                last_query_timestamp = newsBeanLast.image.createdAt;
            } else {
                last_query_timestamp = "";
            }
        }
    }

    private void handleNewsData(List<NewsBean> inNewsBean, boolean isrefresh) {

        if (isrefresh) {
            int size = inNewsBean.size();
            for (int i = 0; i < size -1 ; i++) {
                NewsBean newsBean = inNewsBean.get(i);
                this.newBeanList.add(newsBean);
                newListAdapter.notifyDataSetChanged();
            }
        } else {

            this.newBeanList = inNewsBean;
            newListAdapter = new NewListAdapter(newBeanList);
            recyclerView.setAdapter(newListAdapter);
            newListAdapter.setOnNewItemClick(this);

            DebugLog.i(inNewsBean.toString());
        }

        getLasttime(newBeanList);
    }

    @Override
    public void onRefresh() {
        getNews(false);
    }

    @Override
    protected void initialResponseHandled() {
        super.initialResponseHandled();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onFavouriteClick(String imageId, int favourite) {
        FavouriteRequest favouriteRequest = new FavouriteRequest(imageId, favourite);
        favouriteRequest.setRequestCallBack(new ApiObjectCallBack<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse data) {
                if (data.status == 1) {
                    newListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFail(int failCode, String message) {
                initialNetworkError();
            }
        });
        favouriteRequest.execute();
    }

    @Override
    public void onFollowClick(final String userId, final int follow) {
        FollowRequest followRequest = new FollowRequest(userId, follow);
        followRequest.setRequestCallBack(new ApiObjectCallBack<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse data) {
                if (data.status == 1) {
                    setChangeFollow(userId, follow);
                    newListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFail(int failCode, String message) {
                initialNetworkError();
                DebugLog.i(message);
            }
        });
        followRequest.execute();
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
        openMap(strlat, strlong);
    }

    private void openMap(String strLat, String strLong) {
        //double dbLat = Double.valueOf(strLat);
        //double dbLong = Double.valueOf(strLong);

        String strGeo = "geo:" + strLat + "," + strLong + "?z=zom";

        Uri uri = Uri.parse(strGeo);

        Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            getActivity().startActivity(mapIntent);
        }
    }

    private void setChangeFollow(String userId, int follow) {
        int size = newBeanList.size();

        for (int i = 0; i < size; i++) {
            NewsBean newsBean = newBeanList.get(i);
            if ((newsBean != null) && (newsBean.user.id.equals(userId))) {
                if (follow == 0) {
                    newBeanList.get(i).user.isFollowing = false;
                } else if (follow == 1) {
                    newBeanList.get(i).user.isFollowing = true;
                }
            }
        }
    }
}
