package example.jp.socical.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;
import example.jp.socical.MainActivity;
import example.jp.socical.R;
import example.jp.socical.adapter.TutorialPagerAdapter;
import example.jp.socical.api.request.TutorialRequest;
import example.jp.socical.api.response.TutorialResponse;
import example.jp.socical.bean.DataTutorial;
import me.relex.circleindicator.CircleIndicator;
import vn.app.base.api.volley.callback.ApiObjectCallBack;
import vn.app.base.util.FragmentUtil;

public class TutorialFragment extends NoHeaderFragment {

    DataTutorial dataTutorial;
    TutorialPagerAdapter tutorialPagerAdapter;

    @BindView(R.id.vp_tutorial)
    ViewPager vpTutorial;

    @BindView(R.id.btnSkip)
    Button btnSkip;

    @BindView(R.id.tutorial_indicator)
    CircleIndicator indicator;

    public static TutorialFragment newInstance() {
        TutorialFragment tutorialFragment = new TutorialFragment();
        return tutorialFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tutorial;
    }

    @Override
    protected void getArgument(Bundle bundle) {

    }

    @Override
    protected void initView(View root) {
        super.initView(root);

        ((MainActivity) getActivity()).setToolbar(0);
    }

    @Override
    protected void initData() {
        if (dataTutorial == null) {
            getDataTutorial();
        } else {
            handleTutorialData(dataTutorial);
        }
    }

    @Override
    protected boolean isStartWithLoading() {
        return dataTutorial == null;
    }

    @OnClick(R.id.btnSkip)
    public void onSkip() {
        FragmentUtil.pushFragment(getActivity(), HomeFragment.newInstance(), null, "HomeFragment");
    }

    private void getDataTutorial() {
        TutorialRequest tutorialRequest = new TutorialRequest();
        tutorialRequest.setRequestCallBack(new ApiObjectCallBack<TutorialResponse>() {
            @Override
            public void onSuccess(TutorialResponse data) {
                hideCoverNetworkLoading();
                initialResponseHandled();
                handleTutorialData(data.dataTutorial);
            }

            @Override
            public void onFail(int failCode, String message) {
                initialNetworkError();
            }
        });
        tutorialRequest.execute();
        showCoverNetworkLoading();
    }

    private void handleTutorialData(DataTutorial dataTutorial) {
        tutorialPagerAdapter = new TutorialPagerAdapter(getChildFragmentManager(),dataTutorial);
        vpTutorial.setAdapter(tutorialPagerAdapter);
        indicator.setViewPager(vpTutorial);
    }
}
