package example.jp.socical.api.request;

import com.android.volley.Request;

import java.util.HashMap;
import java.util.Map;

import example.jp.socical.constant.APIConstant;
import vn.app.base.api.response.BaseResponse;
import vn.app.base.api.volley.core.ObjectApiRequest;
import vn.app.base.util.SharedPrefUtils;

/**
 * Created by Hien_BRSE on 11/22/2016.
 */

public class FollowRequest extends ObjectApiRequest<BaseResponse>{

    String userId;
    int isFollow = 2;

    public FollowRequest(String userId, int isFollow) {
        this.userId = userId;
        this.isFollow = isFollow;
    }

    @Override
    public boolean isRequiredAuthorization() {
        return false;
    }

    @Override
    public String getRequestURL() {
        return APIConstant.FOLLOW;
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
        }

        if (isFollow != 2) {
            params.put(APIConstant.IS_FOLLOW, Integer.toString(isFollow));
        }

        return params;
    }

    @Override
    public Class<BaseResponse> getResponseClass() {
        return BaseResponse.class;
    }

    @Override
    public int getMethod() {
        return Request.Method.POST;
    }
}
