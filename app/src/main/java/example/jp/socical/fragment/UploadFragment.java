package example.jp.socical.fragment;

import android.os.Bundle;

import example.jp.socical.R;

public class UploadFragment extends HeaderFragment {

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
    protected void initData() {

    }
}
