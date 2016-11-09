package example.jp.socical.api.request;

import com.android.volley.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import example.jp.socical.api.response.UploadImageResponse;
import vn.app.base.api.volley.core.ObjectApiRequest;
import vn.app.base.util.SharedPrefUtils;

/**
 * Created by Toi on 10/24/2016.
 */

public class UploadImageRequest extends ObjectApiRequest<UploadImageResponse> {

    String strimgPicture;
    String strcaption;
    String strlocation;
    String strlat;
    String strlong;
    ArrayList<String> hashtag;

    public UploadImageRequest(String strimgPicture, String strcaption, String strlocation, String strlat, String strlong, ArrayList<String> hashtag) {
        this.strimgPicture = strimgPicture;
        this.strcaption = strcaption;
        this.strlocation = strlocation;
        this.strlat = strlat;
        this.strlong = strlong;
        this.hashtag = hashtag;
    }

    @Override
    public boolean isRequiredAuthorization() {
        return true;
    }

    @Override
    public String getRequestURL() {
        String url = "https://polar-plains-86888.herokuapp.com//api/image/upload";
        return url;
    }

    @Override
    public boolean isRequiredAccessToken() {
        return false;
    }

    @Override
    public Map<String, String> getRequestParams() {
        Map<String, String> params = new HashMap<>();
        return params;
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        Map<String,String> header = new HashMap<>();
        header.put("token", SharedPrefUtils.getAccessToken());
        return header;
    }

    @Override
    public Class<UploadImageResponse> getResponseClass() {
        return UploadImageResponse.class;
    }

    @Override
    public int getMethod() {
        return Request.Method.POST;
    }
}
