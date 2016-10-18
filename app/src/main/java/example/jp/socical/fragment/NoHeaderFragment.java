package example.jp.socical.fragment;

import android.os.Bundle;
import android.view.View;

import example.jp.socical.bean.HeaderControlBean;
import example.jp.socical.constant.HeaderOption;
import vn.app.base.fragment.CommonFragment;

/**
 * Created by HaHien on 10/16/2016.
 */

public abstract class NoHeaderFragment extends CommonFragment{

    @Override
    protected void initView(View root) {
        if (commonListener != null) {
            commonListener.onCommonUIHandle(hideHeaderBundle());
        }
    }

    private Bundle hideHeaderBundle() {
        Bundle bundle = new Bundle();
        HeaderControlBean headerControlBean = new HeaderControlBean(getScreenTitle(), HeaderOption.HIDE_HEADER);
        bundle.putParcelable(HeaderOption.HEADER_CONTROL, headerControlBean);
        return bundle;
    }

}
