package example.jp.socical.api.request;

import com.android.volley.Request;

import java.util.HashMap;
import java.util.Map;

import example.jp.socical.api.response.NewsResponse;
import example.jp.socical.constant.APIConstant;
import vn.app.base.api.volley.core.ObjectApiRequest;
import vn.app.base.util.SharedPrefUtils;

/**
 * Created by Toi on 10/25/2016.
 */

public class NewsRequest extends ObjectApiRequest<NewsResponse>{

    int type;
    String last_query_timestamp;
    int num;

    public NewsRequest(int type, String last_query_timestamp, int num) {
        this.type = type;
        this.last_query_timestamp = last_query_timestamp;
        this.num = num;
    }

    @Override
    public boolean isRequiredAuthorization() {
        return true;
    }

    @Override
    public String getRequestURL() {
        return APIConstant.HOME;
    }

    @Override
    public boolean isRequiredAccessToken() {
        return false;
    }

    @Override
    public Map<String, String> getRequestParams() {

        Map<String, String> params = new HashMap<>();
        params.put(APIConstant.HOME_TYPE, Integer.toString(type));
        if (!last_query_timestamp.isEmpty()) {
            params.put(APIConstant.LAST_TIMESTAMP, last_query_timestamp);
        }
        //if (num != 0) {
        //    params.put("num", Integer.toString(num));
        //}
        return params;
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        Map<String,String> header = new HashMap<>();
        header.put(APIConstant.TOKEN, SharedPrefUtils.getAccessToken());
        return header;
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
