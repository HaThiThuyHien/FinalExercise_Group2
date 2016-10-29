package example.jp.socical.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import example.jp.socical.R;
import example.jp.socical.adapter.NewListAdapter;
import example.jp.socical.api.request.NewsRequest;
import example.jp.socical.api.response.NewsResponse;
import example.jp.socical.bean.NewsBean;
import vn.app.base.api.volley.callback.ApiObjectCallBack;
import vn.app.base.customview.endlessrecycler.EndlessRecyclerOnScrollListener;
import vn.app.base.util.DebugLog;
import vn.app.base.util.FragmentUtil;

public class NewFragment extends NoHeaderFragment implements SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.rvNew)
    RecyclerView recyclerView;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.fabCamera)
    FloatingActionButton fabCamera;

    NewListAdapter newListAdapter;

    List<NewsBean> newBeanList;

    int type;
    long last_query_timestamp;
    int num;

    public static NewFragment newInstance() {
        NewFragment fragment = new NewFragment();
    return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_new;
    }

    @Override
    protected void getArgument(Bundle bundle) {

    }

    @Override
    protected void initView(View root) {
        super.initView(root);

        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore(int currentPage) {

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    };

    @Override
    protected void initData() {
        if (newBeanList == null) {
            getNews(false);
        } else {
            handleNewsData(newBeanList);
        }
    }

    @OnClick(R.id.fabCamera)
    public void clickFabCamera() {
        // Di chuyển đến màn hình Detail
        FragmentUtil.pushFragment(getActivity(), UploadFragment.newInstance(), null);
    }

    private void getNews(final boolean isRefresh) {

        NewsRequest newsRequest = new NewsRequest();
        newsRequest.setRequestCallBack(new ApiObjectCallBack<NewsResponse>() {
            @Override
            public void onSuccess(NewsResponse data) {
                initialResponseHandled();
                handleNewsData(data.data);
            }

            @Override
            public void onFail(int failCode, String message) {
                initialNetworkError();
            }
        });
        newsRequest.execute();
    }

    private void handleNewsData(List<NewsBean> inNewsBean) {
        this.newBeanList = inNewsBean;
        newListAdapter = new NewListAdapter(newBeanList);
        recyclerView.setAdapter(newListAdapter);
        DebugLog.i(inNewsBean.toString());
    }

    @Override
    public void onRefresh() {

    }
}
