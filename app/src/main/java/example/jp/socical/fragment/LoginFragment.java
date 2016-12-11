package example.jp.socical.fragment;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.OnClick;
import example.jp.socical.MainActivity;
import example.jp.socical.R;
import example.jp.socical.api.request.LoginRequest;
import example.jp.socical.api.response.LoginResponse;
import example.jp.socical.bean.DataLoginBean;
import example.jp.socical.commonclass.StringEncryption;
import example.jp.socical.constant.CommonConstant;
import example.jp.socical.manager.UserManager;
import vn.app.base.api.volley.callback.ApiObjectCallBack;
import vn.app.base.util.DialogUtil;
import vn.app.base.util.FragmentUtil;
import vn.app.base.util.KeyboardUtil;
import vn.app.base.util.SharedPrefUtils;
import vn.app.base.util.StringUtil;

public class LoginFragment extends NoHeaderFragment {

    private String user;
    private String pass;

    @BindView(R.id.tvUserName)
    EditText etUserName;

    @BindView(R.id.tvPassword)
    EditText etPass;

    @BindView(R.id.btnLogin)
    Button btnLogin;

    @BindView(R.id.btnRegister)
    Button btnRegister;

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
        ((MainActivity) getActivity()).setToolbar(0);
        etPass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    login();
                    return true;
                }
                return false;
            }
        });
        etUserName.requestFocus();
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.btnLogin)
    public void login() {
        user = etUserName.getText().toString().trim();
        if (!StringUtil.checkStringValid(user)) {
            DialogUtil.showOkBtnDialog(getActivity(), CommonConstant.title, "User name is blank");
            return;
        }

        if (!StringUtil.checkStringValid(etPass.getText().toString().trim())) {
            DialogUtil.showOkBtnDialog(getActivity(), CommonConstant.title, "Pass is blank");
            return;
        }

        try {
            pass = StringEncryption.SHA1(etPass.getText().toString().trim());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        loginRequest();
    }

    public void loginRequest(){
        showCoverNetworkLoading();
        LoginRequest loginRequest = new LoginRequest(user, pass);
        loginRequest.setRequestCallBack(new ApiObjectCallBack<LoginResponse>() {
            @Override
            public void onSuccess(LoginResponse data) {
                if (data.dataResponse != null) {
                    hideCoverNetworkLoading();
                    String user1 = "";
                    String user2 = "";

                    DataLoginBean userData = UserManager.getCurrentUser();
                    if (userData != null) {
                        user1 = userData.username;
                    }
                    SharedPrefUtils.saveAccessToken(data.dataResponse.token);
                    UserManager.saveCurrentUser(data.dataResponse);
                    user2 = data.dataResponse.username;

                    if (!user2.equals(user1)) {
                        FragmentUtil.pushFragment(getActivity(), TutorialFragment.newInstance(), null, "TutorialFragment");
                    } else {
                        FragmentUtil.pushFragment(getActivity(), HomeFragment.newInstance(), null, "HomeFragment");
                    }
                }
            }

            @Override
            public void onFail(int failCode, String message) {
                hideCoverNetworkLoading();
                DialogUtil.showOkBtnDialog(getActivity(), CommonConstant.title, "User or password incorrect");
                KeyboardUtil.hideKeyboard(getActivity());
            }
        });
        loginRequest.execute();
        KeyboardUtil.hideKeyboard(getActivity());
    }

    @OnClick(R.id.btnRegister)
    public void goToRegisterFragment() {
        FragmentUtil.pushFragment(getActivity(), RegisterFragment.newInstance(), null, "RegisterFragment");
    }
}
