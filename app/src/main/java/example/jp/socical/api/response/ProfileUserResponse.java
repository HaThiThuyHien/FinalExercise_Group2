package example.jp.socical.api.response;

import com.google.gson.annotations.SerializedName;
import example.jp.socical.bean.ProfileUserBean;
import vn.app.base.api.response.BaseResponse;

/**
 * Created by Hien_BRSE on 11/22/2016.
 */

public class ProfileUserResponse extends BaseResponse{

    @SerializedName("status")
    public Integer status;

    @SerializedName("message")
    public String message;

    @SerializedName("data")
    public ProfileUserBean profileUserData;
}
