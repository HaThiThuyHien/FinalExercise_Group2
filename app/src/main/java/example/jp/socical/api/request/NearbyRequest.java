package example.jp.socical.api.request;

import com.android.volley.Request;

import java.util.HashMap;
import java.util.Map;

import example.jp.socical.api.response.NearbyResponse;
import example.jp.socical.constant.APIConstant;
import vn.app.base.api.volley.core.ObjectApiRequest;
import vn.app.base.util.SharedPrefUtils;

/**
 * Created by Hien_BRSE on 11/18/2016.
 */

public class NearbyRequest extends ObjectApiRequest<NearbyResponse>{

    double dbLat;
    double dbLong;

    public NearbyRequest(double dbLat, double dbLong) {
        this.dbLat = dbLat;
        this.dbLong = dbLong;
    }

    @Override
    public boolean isRequiredAuthorization() {
        return false;
    }

    @Override
    public String getRequestURL() {
        return APIConstant.NEARBY;
    }

    @Override
    public boolean isRequiredAccessToken() {
        return false;
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        Map<String,String> header = new HashMap<>();
        header.put("token", SharedPrefUtils.getAccessToken());
        return header;
    }

    @Override
    public Map<String, String> getRequestParams() {

        Map<String, String> params = new HashMap<>();
        params.put("lat", Double.toString(dbLat));
        params.put("long", Double.toString(dbLong));
        return params;
    }

    @Override
    public Class<NearbyResponse> getResponseClass() {
        return NearbyResponse.class;
    }

    @Override
    public int getMethod() {
        return Request.Method.POST;
    }
}
