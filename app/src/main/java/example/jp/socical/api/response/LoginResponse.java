package example.jp.socical.api.response;

import example.jp.socical.bean.DataLoginBean;
import vn.app.base.api.response.BaseResponse;

/**
 * Created by Toi on 10/24/2016.
 */
import com.google.gson.annotations.SerializedName;

public class LoginResponse extends BaseResponse {

    @SerializedName("data")
    public DataLoginBean dataResponse;
}

