package example.jp.socical.api.request;

import com.android.volley.Request;

import java.util.HashMap;
import java.util.Map;

import example.jp.socical.constant.APIConstant;
import vn.app.base.api.response.BaseResponse;
import vn.app.base.api.volley.core.ObjectApiRequest;
import vn.app.base.util.SharedPrefUtils;

/**
 * Created by Hien_BRSE on 12/8/2016.
 */

public class PostCommentRequest extends ObjectApiRequest<BaseResponse> {

    String imageId;
    String comment;

    public PostCommentRequest(String imageId, String comment) {
        this.imageId = imageId;
        this.comment = comment;
    }

    @Override
    public boolean isRequiredAuthorization() {
        return false;
    }

    @Override
    public String getRequestURL() {
        return APIConstant.COMMENT;
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
        Map<String, String> params = new HashMap<>();
        params.put(APIConstant.IMAGE_ID, imageId);
        params.put(APIConstant.COMMENT_POST, comment);
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
