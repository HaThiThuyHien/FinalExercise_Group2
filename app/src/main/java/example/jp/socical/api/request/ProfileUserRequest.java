package example.jp.socical.api.request;

import com.android.volley.Request;

import java.util.HashMap;
import java.util.Map;

import example.jp.socical.api.response.ProfileUserResponse;
import example.jp.socical.constant.APIConstant;
import vn.app.base.api.volley.core.ObjectApiRequest;
import vn.app.base.util.SharedPrefUtils;

/**
 * Created by Hien_BRSE on 11/22/2016.
 */

public class ProfileUserRequest extends ObjectApiRequest<ProfileUserResponse>{

    String userId;

    public ProfileUserRequest(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean isRequiredAuthorization() {
        return false;
    }

    @Override
    public String getRequestURL() {
        return APIConstant.PROFILE_USER;
    }

    @Override
    public boolean isRequiredAccessToken() {
        return false;
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        Map<String,String> header = new HashMap<>();
        header.put(APIConstant.TOKEN, SharedPrefUtils.getAccessToken());
        return header;
    }

    @Override
    public Map<String, String> getRequestParams() {

        Map<String,String> params = new HashMap<>();
        if (!userId.isEmpty()) {
            params.put(APIConstant.USER_ID, userId);
            return params;
        } else {
            return null;
        }
    }

    @Override
    public Class<ProfileUserResponse> getResponseClass() {
        return ProfileUserResponse.class;
    }

    @Override
    public int getMethod() {
        return Request.Method.POST;
    }
}
