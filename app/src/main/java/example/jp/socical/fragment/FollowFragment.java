package example.jp.socical.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.List;

import example.jp.socical.MainActivity;
import example.jp.socical.R;
import example.jp.socical.adapter.FollowListAdapter;
import example.jp.socical.api.request.FollowListRequest;
import example.jp.socical.api.request.FollowRequest;
import example.jp.socical.api.response.FollowListResponse;
import example.jp.socical.bean.DataLoginBean;
import example.jp.socical.bean.FollowDataBean;
import example.jp.socical.constant.HeaderOption;
import vn.app.base.adapter.DividerItemDecoration;
import vn.app.base.api.volley.callback.ApiObjectCallBack;
import vn.app.base.callback.OnRecyclerViewItemClick;
import vn.app.base.fragment.BaseSwipeRefreshFragment;
import vn.app.base.util.FragmentUtil;

/**
 * Created by Toi on 11/24/2016.
 */

public class FollowFragment extends BaseSwipeRefreshFragment implements OnRecyclerViewItemClick{

    List<FollowDataBean> followDataBeanList;

    FollowListAdapter followListAdapter;

    public static FollowFragment newInstance() {
        FollowFragment followFragment = new FollowFragment();
        return followFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_follow;
    }

    @Override
    protected void initView(View root) {
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        setCanRefresh(false);
    }

    @Override
    protected void onRefreshData() {

    }

    @Override
    protected void getArgument(Bundle bundle) {

    }

    @Override
    protected void initData() {

        ((MainActivity)getActivity()).setToolbar(HeaderOption.MENU_FOLLOW);
        if (followDataBeanList == null) {
            getFollowData();
        }
    }

    private void getFollowData() {
        FollowListRequest followListRequest = new FollowListRequest();
        followListRequest.setRequestCallBack(new ApiObjectCallBack<FollowListResponse>() {
            @Override
            public void onSuccess(FollowListResponse data) {
                if (data.followDatas != null) {
                    setData(data.followDatas);
                }
            }

            @Override
            public void onFail(int failCode, String message) {
                initialNetworkError();
            }
        });
        followListRequest.execute();
    }

    private void setData(List<FollowDataBean> followData) {
        followDataBeanList = followData;
        followListAdapter = new FollowListAdapter(followDataBeanList);
        rvList.setAdapter(followListAdapter);
        followListAdapter.setOnRecyclerViewItemClick(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        FollowDataBean followDataBean = followDataBeanList.get(position);
        if (followDataBean != null) {
            FragmentUtil.pushFragment(getActivity(), ProfileUserFragment.newInstance(followDataBean.user.id),null, "ProfileUserFragment");
        }
    }
}
