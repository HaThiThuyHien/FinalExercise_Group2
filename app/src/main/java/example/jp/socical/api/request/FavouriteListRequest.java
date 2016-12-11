package example.jp.socical.api.request;

import com.android.volley.Request;

import java.util.HashMap;
import java.util.Map;

import example.jp.socical.api.response.NewsResponse;
import example.jp.socical.constant.APIConstant;
import vn.app.base.api.volley.core.ObjectApiRequest;
import vn.app.base.util.SharedPrefUtils;

/**
 * Created by Toi on 12/11/2016.
 */

public class FavouriteListRequest extends ObjectApiRequest<NewsResponse> {

    String userId;

    public FavouriteListRequest(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean isRequiredAuthorization() {
        return false;
    }

    @Override
    public String getRequestURL() {
        return APIConstant.FAVOURITE_LIST;
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
        if (userId != null) {
            Map<String, String> params = new HashMap<>();
            params.put(APIConstant.USER_ID, userId);
            return params;
        } else {
            return null;
        }
    }

    @Override
    public Class<NewsResponse> getResponseClass() {
        return NewsResponse.class;
    }

    @Override
    public int getMethod() {
        return Request.Method.POST;
    }
}
