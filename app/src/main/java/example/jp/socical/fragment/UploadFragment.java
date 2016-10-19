package example.jp.socical.fragment;

import android.os.Bundle;
import android.view.View;

import example.jp.socical.MainActivity;
import example.jp.socical.R;
import example.jp.socical.constant.HeaderOption;

public class UploadFragment extends HeaderFragment {

    MainActivity mainActivity;

    public static UploadFragment newInstance() {
        UploadFragment uploadFragment = new UploadFragment();
        return uploadFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_upload;
    }

    @Override
    protected void getArgument(Bundle bundle) {

    }

    @Override
    public String getScreenTitle() {
        return "Post Image";
    }

    @Override
    protected void initView(View root) {
        super.initView(root);
        ((MainActivity)getActivity()).setToolbar(HeaderOption.MENU_UPLOAD);
    }

    @Override
    protected void initData() {

    }


}
