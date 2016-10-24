package example.jp.socical.api.request;

import com.android.volley.Request;

import java.util.HashMap;
import java.util.Map;

import example.jp.socical.api.response.LoginResponse;
import vn.app.base.api.volley.core.ObjectApiRequest;

/**
 * Created by Toi on 10/24/2016.
 */

public class LoginRequest extends ObjectApiRequest<LoginResponse> {

    String userId;
    String pass;

    public LoginRequest(String userId, String pass) {
        this.userId = userId;
        this.pass = pass;
    }

    @Override
    public boolean isRequiredAuthorization() {
        return false;
    }

    @Override
    public String getRequestURL() {
        String url = "https://polar-plains-86888.herokuapp.com/api/login";
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
        params.put("password", pass);
        return params;
    }

    @Override
    public Class<LoginResponse> getResponseClass() {
        return LoginResponse.class;
    }

    @Override
    public int getMethod() {
        return Request.Method.POST;
    }
}
