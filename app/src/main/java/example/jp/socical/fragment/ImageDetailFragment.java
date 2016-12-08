package example.jp.socical.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import example.jp.socical.MainActivity;
import example.jp.socical.R;
import example.jp.socical.adapter.ImageDetailListAdapter;
import example.jp.socical.api.request.ImageDetailRequest;
import example.jp.socical.api.request.PostCommentRequest;
import example.jp.socical.api.response.ImageDetailResponse;
import example.jp.socical.bean.DataLoginBean;
import example.jp.socical.bean.ImageDetailData;
import example.jp.socical.bean.NewsBean;
import example.jp.socical.callback.DrawableClickListener;
import example.jp.socical.commonclass.CustomEditText;
import example.jp.socical.constant.HeaderOption;
import example.jp.socical.manager.UserManager;
import vn.app.base.adapter.DividerItemDecoration;
import vn.app.base.api.response.BaseResponse;
import vn.app.base.api.volley.callback.ApiObjectCallBack;
import vn.app.base.fragment.BaseSwipeRefreshFragment;

public class ImageDetailFragment extends BaseSwipeRefreshFragment{

    public static final String IMAGE_DETAIL_DATA = "IMAGE_DETAIL_DATA";

    ImageDetailListAdapter imageDetailListAdapter;

    List<ImageDetailData> imageDetailDataList;

    NewsBean newsBeanHeader;

    DataLoginBean loginUser;

    @BindView(R.id.txtPostCmt)
    CustomEditText etPostCmt;

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
        setCanRefresh(false);

        etPostCmt.setDrawableClickListener(new DrawableClickListener() {
            @Override
            public void onClick(DrawablePosition target) {
                switch (target){
                    case LEFT:
                        break;
                    case RIGHT:
                        sendComment();
                        break;
                    default:
                            break;
                }
            }
        });
    }

    public void sendComment() {
        String imageId;
        String comment;

        comment = etPostCmt.getText().toString();
        imageId = newsBeanHeader.image.id;

        showCoverNetworkLoading();

        PostCommentRequest postCommentRequest = new PostCommentRequest(imageId, comment);
        postCommentRequest.setRequestCallBack(new ApiObjectCallBack<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse data) {
                if (data.status == 1) {
                    getImageDetailData();
                }
            }

            @Override
            public void onFail(int failCode, String message) {
                initialNetworkError();
            }
        });

        postCommentRequest.execute();
    }

    @Override
    protected void getArgument(Bundle bundle) {
        newsBeanHeader = bundle.getParcelable(IMAGE_DETAIL_DATA);
    }

    @Override
    protected void initData() {

        loginUser = UserManager.getCurrentUser();

        if ((loginUser != null) && (newsBeanHeader.user.id != null)) {
            String selectUserId = newsBeanHeader.user.id;
            if (selectUserId.equals(loginUser.id)) {
                ((MainActivity)getActivity()).setToolbar(HeaderOption.MENU_DETAIL_USER);
            } else {
                ((MainActivity)getActivity()).setToolbar(HeaderOption.MENU_DETAIL);
            }
        }

        if (imageDetailDataList == null) {
            getImageDetailData();
        }
    }

    private void getImageDetailData() {

        showCoverNetworkLoading();

        String imageId = "";

        if (newsBeanHeader != null) {
            imageId = newsBeanHeader.image.id;
        }

        ImageDetailRequest imageDetailRequest = new ImageDetailRequest(imageId);
        imageDetailRequest.setRequestCallBack(new ApiObjectCallBack<ImageDetailResponse>() {
            @Override
            public void onSuccess(ImageDetailResponse data) {
                hideCoverNetworkLoading();
                initialResponseHandled();
                setImageDetailData(data.data);
            }

            @Override
            public void onFail(int failCode, String message) {
                initialNetworkError();
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
}
