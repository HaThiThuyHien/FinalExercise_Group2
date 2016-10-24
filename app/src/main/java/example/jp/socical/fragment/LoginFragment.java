package example.jp.socical.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import example.jp.socical.R;
import example.jp.socical.api.request.LoginRequest;
import example.jp.socical.api.response.LoginResponse;
import example.jp.socical.commonclass.StringEncryption;
import vn.app.base.api.volley.callback.ApiObjectCallBack;
import vn.app.base.util.FragmentUtil;
import vn.app.base.util.SharedPrefUtils;

public class LoginFragment extends NoHeaderFragment {

    @BindView(R.id.btnLogin)
    Button btnLogin;

    @BindView(R.id.btnRegister)
    Button btnRegister;

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
                login();
                // gọi đến màn hình Home
//                if (bcheck) {
//                    FragmentUtil.pushFragment(getActivity(), HomeFragment.newInstance(), null);
//                } else { // gọi đến màn hình Tutorial
//                    //FragmentUtil.pushFragment(getActivity(), TutorialFragment.newInstance(), null);
//                    FragmentUtil.pushFragment(getActivity(), RegisterFragment.newInstance(), null);
//                }
            }
        });

        btnRegister = (Button)root.findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentUtil.pushFragment(getActivity(), RegisterFragment.newInstance(), null);
            }
        });
    }

    @Override
    protected void initData() {

    }

    public void login(){
        String strUser = "ThinhHoang";
        String strPass = "123456789";
        String strPassSHA1 = "";

        StringEncryption stringEncryption = new StringEncryption();
        try {
            strPassSHA1 = stringEncryption.SHA1(strPass);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        LoginRequest loginRequest = new LoginRequest(strUser, strPassSHA1);
        loginRequest.setRequestCallBack(new ApiObjectCallBack<LoginResponse>() {
            @Override
            public void onSuccess(LoginResponse data) {
                if (data.dataResponse != null) {
                    SharedPrefUtils.saveAccessToken(data.dataResponse.token);
                    FragmentUtil.pushFragment(getActivity(), TutorialFragment.newInstance(), null);
                }
            }

            @Override
            public void onFail(int failCode, String message) {
                Log.i("Connect API fail", message);
            }
        });

        loginRequest.execute();
    }

}
