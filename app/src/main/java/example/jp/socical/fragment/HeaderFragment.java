package example.jp.socical.fragment;

import android.os.Bundle;
import android.view.View;

import example.jp.socical.bean.HeaderControlBean;
import example.jp.socical.constant.HeaderOption;
import vn.app.base.fragment.CommonFragment;

/**
 * Created by HaHien on 10/16/2016.
 */

public abstract class HeaderFragment extends CommonFragment{

    @Override
    protected void initView(View root) {
        if (commonListener != null) {
            commonListener.onCommonUIHandle(showHeaderBundle());
        }
    }

    private Bundle showHeaderBundle() {
        Bundle bundle = new Bundle();
        HeaderControlBean headerControlBean = new HeaderControlBean(getScreenTitle());
        headerControlBean.setHeaderOption(isHeaderTransparent() ? HeaderOption.SHOW_HEADER_TRANSPARENT : HeaderOption.SHOW_HEADER, getLeftIcon(), getRightIcon());
        bundle.putParcelable(HeaderOption.HEADER_CONTROL, headerControlBean);
        return bundle;
    }

    protected int getLeftIcon() {
        return 0;
    }

    protected int getRightIcon() {
        return 0;
    }

    protected boolean isHeaderTransparent() {
        return false;
    }

}
