package example.jp.socical.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import butterknife.BindView;
import example.jp.socical.MainActivity;
import example.jp.socical.R;
import example.jp.socical.adapter.CommentAdapter;
import example.jp.socical.adapter.ImageDetailListAdapter;
import example.jp.socical.api.request.ImageDetailRequest;
import example.jp.socical.api.response.ImageDetailResponse;
import example.jp.socical.bean.CommentBean;
import example.jp.socical.bean.ImageBean;
import example.jp.socical.bean.ImageDetailData;
import example.jp.socical.bean.NewsBean;
import example.jp.socical.bean.UserBean;
import example.jp.socical.constant.HeaderOption;
import vn.app.base.adapter.DividerItemDecoration;
import vn.app.base.api.volley.callback.ApiObjectCallBack;
import vn.app.base.fragment.BaseSwipeRefreshFragment;

public class ImageDetailFragment extends BaseSwipeRefreshFragment {

    public static final String IMAGE_DETAIL_DATA = "IMAGE_DETAIL_DATA";

    ImageDetailListAdapter imageDetailListAdapter;

    List<ImageDetailData> imageDetailDataList;

    NewsBean newsBeanHeader;

    public static ImageDetailFragment newInstance(NewsBean imageDetailData) {
        ImageDetailFragment imageDetailFragment = new ImageDetailFragment();
        Bundle bundle =  new Bundle();
        bundle.putParcelable(IMAGE_DETAIL_DATA, imageDetailData);
        imageDetailFragment.setArguments(bundle);

        return imageDetailFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_image_detail;
    }

    @Override
    protected void onRefreshData() {

    }

    @Override
    protected void initView(View root) {
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvList.addItemDecoration(new DividerItemDecoration(getActivity(), null));
        //rvImageDetail.setLayoutManager(new LinearLayoutManager(getActivity()));
        //rvImageDetail.addItemDecoration(new DividerItemDecoration(getActivity(), null));
        setCanRefresh(false);
    }

    @Override
    protected void getArgument(Bundle bundle) {
        newsBeanHeader = bundle.getParcelable(IMAGE_DETAIL_DATA);
    }

    @Override
    protected void initData() {
        if (imageDetailDataList == null) {
            getImageDetailData();

            //dumyData();
        }
    }

    private void getImageDetailData() {
        ImageDetailRequest imageDetailRequest = new ImageDetailRequest();
        imageDetailRequest.setRequestCallBack(new ApiObjectCallBack<ImageDetailResponse>() {
            @Override
            public void onSuccess(ImageDetailResponse data) {
                initialResponseHandled();
                setImageDetailData(data.data);
            }

            @Override
            public void onFail(int failCode, String message) {

            }
        });
        imageDetailRequest.execute();
    }

    private void setImageDetailData(List<ImageDetailData> imgDetailDataList) {
        if (imgDetailDataList != null) {
            imageDetailDataList = imgDetailDataList;
            imageDetailListAdapter = new ImageDetailListAdapter();
            imageDetailListAdapter.setHeader(newsBeanHeader);
            imageDetailListAdapter.setItems(imgDetailDataList);
            rvList.setAdapter(imageDetailListAdapter);
        }
    }

    private void dumyData() {

        imageDetailDataList = new ArrayList<>();

        ImageDetailData imageDetailData = new ImageDetailData();
        for (int i = 0; i < 5; i++) {
            imageDetailData.comment = "Buc anh nay dep qua";
            imageDetailDataList.add(imageDetailData);
        }

        for (int j = 0; j < 5; j++) {
            imageDetailData.comment = "Ban chup o dau vay, cho toi xin dia chi noi ban chup duoc khong? toi cung muon den do de chup anh";
            imageDetailDataList.add(imageDetailData);
        }

        imageDetailListAdapter = new ImageDetailListAdapter();
        //imageDetailListAdapter.setHeader(newsBean);
        imageDetailListAdapter.setHeader(newsBeanHeader);
        imageDetailListAdapter.setItems(imageDetailDataList);
        rvList.setAdapter(imageDetailListAdapter);
        //rvImageDetail.setAdapter(imageDetailListAdapter);
    }

}
