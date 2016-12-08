package example.jp.socical.api.request;

import com.android.volley.Request;

import java.util.HashMap;
import java.util.Map;

import example.jp.socical.api.response.ImageDetailResponse;
import example.jp.socical.constant.APIConstant;
import vn.app.base.api.volley.core.ObjectApiRequest;
import vn.app.base.util.SharedPrefUtils;

/**
 * Created by Toi on 11/21/2016.
 */

public class ImageDetailRequest extends ObjectApiRequest<ImageDetailResponse>{

    String imageId;

    public ImageDetailRequest(String imageId) {
        this.imageId = imageId;
    }

    @Override
    public boolean isRequiredAuthorization() {
        return false;
    }

    @Override
    public String getRequestURL() {
        return APIConstant.COMMENT_LIST;
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
        if (imageId != null) {
            params.put(APIConstant.IMAGE_ID, imageId);
            return params;
        } else {
            return null;
        }
    }

    @Override
    public Class<ImageDetailResponse> getResponseClass() {
        return ImageDetailResponse.class;
    }

    @Override
    public int getMethod() {
        return Request.Method.POST;
    }
}
