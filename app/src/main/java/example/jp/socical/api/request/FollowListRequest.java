package example.jp.socical.api.request;

import com.android.volley.Request;

import java.util.HashMap;
import java.util.Map;

import example.jp.socical.api.response.FollowListResponse;
import example.jp.socical.constant.APIConstant;
import vn.app.base.api.volley.core.ObjectApiRequest;
import vn.app.base.util.SharedPrefUtils;

/**
 * Created by Hien_BRSE on 11/24/2016.
 */

public class FollowListRequest extends ObjectApiRequest<FollowListResponse> {

    @Override
    public boolean isRequiredAuthorization() {
        return false;
    }

    @Override
    public String getRequestURL() {
        return APIConstant.FOLLOW_LIST;
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
        return null;
    }

    @Override
    public Class<FollowListResponse> getResponseClass() {
        return FollowListResponse.class;
    }

    @Override
    public int getMethod() {
        return Request.Method.GET;
    }
}
