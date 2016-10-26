package example.jp.socical.api.request;

import com.android.volley.Request;

import java.util.HashMap;
import java.util.Map;

import example.jp.socical.api.response.NewsResponse;
import vn.app.base.api.volley.core.ObjectApiRequest;
import vn.app.base.util.SharedPrefUtils;

/**
 * Created by Toi on 10/25/2016.
 */

public class NewsRequest extends ObjectApiRequest<NewsResponse>{

    int type;
    long last_query_timestamp;
    int num;

//    public NewsRequest(int type, long last_query_timestamp, int num) {
//        this.type = type;
//        this.last_query_timestamp = last_query_timestamp;
//        this.num = num;
//    }

    @Override
    public boolean isRequiredAuthorization() {
        return true;
    }

    @Override
    public String getRequestURL() {
        String url = "https://polar-plains-86888.herokuapp.com/api/home";
        return url;
    }

    @Override
    public boolean isRequiredAccessToken() {
        return false;
    }

    @Override
    public Map<String, String> getRequestParams() {

        Map<String, String> params = new HashMap<>();
        params.put("type", Integer.toString(0));
//        params.put("last_query_timestamp", Long.toString(last_query_timestamp));
//        params.put("num", Integer.toString(num));
        return params;
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        Map<String,String> header = new HashMap<>();
        header.put("token", SharedPrefUtils.getAccessToken());
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
