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

public class FavouriteRequest extends ObjectApiRequest<BaseResponse> {

    String imageId;
    int isFavourite = 2;

    public FavouriteRequest(String imageId, int isFavourite) {
        this.imageId = imageId;
        this.isFavourite = isFavourite;
    }

    @Override
    public boolean isRequiredAuthorization() {
        return false;
    }

    @Override
    public String getRequestURL() {
        return APIConstant.FAVOURITE;
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
        if (!imageId.isEmpty()) {
            params.put(APIConstant.IMAGE_ID, imageId);
        }

        if (isFavourite != 2) {
            params.put(APIConstant.IS_FAVOURITE, Integer.toString(isFavourite));
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
