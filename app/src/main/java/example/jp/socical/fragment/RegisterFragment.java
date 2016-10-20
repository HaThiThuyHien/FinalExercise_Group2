package example.jp.socical.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import example.jp.socical.Gson.GsonRequest;
import example.jp.socical.Gson.NetworkUtil;
import example.jp.socical.R;
import example.jp.socical.bean.RegisterBean;
import vn.app.base.util.NetworkUtils;

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
        btnRegister = (Button)root.findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Gọi đến giao diện Home
                requestRegister();
            }
        });
    }

    @Override
    protected void initData() {

    }

    public void requestRegister() {
        String url = "https://polar-plains-86888.herokuapp.com/api/regist";

        strUserName = "HaHien";
        strEmail = "thuyhienctu@gmail.com";
        strPassword = "123456789";

        Map<String, String> params = new HashMap<>();
        params.put("username", strUserName);
        params.put("email", strEmail);
        params.put("password", strPassword);

        GsonRequest<RegisterBean> registerBeanGsonRequest = new GsonRequest<>(Request.Method.POST, url,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Error Connect", error.getMessage());
                    }
                }, RegisterBean.class);
        registerBeanGsonRequest.setListener(new Response.Listener<RegisterBean>(){

            @Override
            public void onResponse(RegisterBean response) {
                Log.i("Connect API", response.toString());
            }
        });

        registerBeanGsonRequest.setParams(params);
        NetworkUtil.getsInstance(getContext()).addToRequestQueue(registerBeanGsonRequest);
    }


}
