package example.jp.socical.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import example.jp.socical.R;
import vn.app.base.util.FragmentUtil;

public class LoginFragment extends NoHeaderFragment {

    @BindView(R.id.btnLogin)
    Button btnLogin;

    boolean bcheck = false;

    public static LoginFragment newInstance() {
        LoginFragment loginFragment = new LoginFragment();
        return loginFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void getArgument(Bundle bundle) {

    }

    @Override
    protected void initView(View root) {
        super.initView(root);

        btnLogin = (Button) root.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // gọi đến màn hình Home
                if (bcheck) {
                    FragmentUtil.pushFragment(getActivity(), HomeFragment.newInstance(), null);
                } else { // gọi đến màn hình Tutorial
                    FragmentUtil.pushFragment(getActivity(), TutorialFragment.newInstance(), null);
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

}
