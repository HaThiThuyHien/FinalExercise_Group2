package example.jp.socical.api.request;

import com.android.volley.Request;

import java.util.HashMap;
import java.util.Map;

import example.jp.socical.api.response.RegisterResponse;
import vn.app.base.api.volley.core.ObjectApiRequest;

/**
 * Created by Toi on 10/24/2016.
 */

public class RegisterRequest extends ObjectApiRequest<RegisterResponse>{
    String userId;
    String email;
    String pass;

    public RegisterRequest(String userId, String email, String pass) {
        this.userId = userId;
        this.email = email;
        this.pass = pass;
    }

    @Override
    public boolean isRequiredAuthorization() {
        return false;
    }

    @Override
    public String getRequestURL() {
        String url = "https://polar-plains-86888.herokuapp.com/api/regist";
        return url;
    }

    @Override
    public boolean isRequiredAccessToken() {
        return false;
    }

    @Override
    public Map<String, String> getRequestParams() {
        Map<String, String> params = new HashMap<>();
        params.put("username", userId);
        params.put("email", email);
        params.put("password", pass);
        return params;
    }

    @Override
    public Class<RegisterResponse> getResponseClass() {
        return RegisterResponse.class;
    }

    @Override
    public int getMethod() {
        return Request.Method.POST;
    }
}
