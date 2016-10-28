package example.jp.socical.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class NearbyFragment extends HeaderFragment {

    public static NearbyFragment newInstance() {
        NearbyFragment nearbyFragment = new NearbyFragment();
        return nearbyFragment;
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void getArgument(Bundle bundle) {

    }

    @Override
    protected void initData() {

    }
}
