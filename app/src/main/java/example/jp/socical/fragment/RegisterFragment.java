package example.jp.socical.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.OnClick;
import example.jp.socical.MainActivity;
import example.jp.socical.R;
import example.jp.socical.api.request.RegisterRequest;
import example.jp.socical.api.response.RegisterResponse;
import example.jp.socical.bean.RegisterBean;
import example.jp.socical.commonclass.StringEncryption;
import vn.app.base.api.volley.callback.ApiObjectCallBack;
import vn.app.base.util.FragmentUtil;
import vn.app.base.util.SharedPrefUtils;

public class RegisterFragment extends NoHeaderFragment {

    @BindView(R.id.btnRegister)
    Button btnRegister;

    // DATA TEST
    String strUserName;
    String strEmail;
    String strPassword;
    String strAvatar;

    RegisterBean registerBean;

    public static RegisterFragment newInstance() {
        RegisterFragment registerFragment = new RegisterFragment();
        return registerFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register;
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

    }

    public void requestRegister() {

        strUserName = "ThinhHoang";
        strEmail = "ThinhHoang@gmail.com";
        strPassword = "123456789";

        String strPassSHA1 ="";
        StringEncryption stringEncryption = new StringEncryption();
        try {
            strPassSHA1 = stringEncryption.SHA1(strPassword);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        RegisterRequest registerRequest = new RegisterRequest(strUserName, strEmail, strPassSHA1);
        registerRequest.setRequestCallBack(new ApiObjectCallBack<RegisterResponse>() {
            @Override
            public void onSuccess(RegisterResponse data) {
                SharedPrefUtils.saveAccessToken(data.dataRegister.token);
                FragmentUtil.pushFragment(getActivity(), TutorialFragment.newInstance(), null);
            }

            @Override
            public void onFail(int failCode, String message) {
            }
        });
        registerRequest.execute();
    }

    @OnClick(R.id.btnRegister)
    public void clickBtnRegister(){
        requestRegister();
    }
}
