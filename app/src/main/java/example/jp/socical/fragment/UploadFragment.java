package example.jp.socical.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
