package example.jp.socical.api.response;

import vn.app.base.api.response.BaseResponse;

/**
 * Created by Toi on 10/24/2016.
 */
import com.google.gson.annotations.SerializedName;

public class RegisterResponse extends BaseResponse {


    @SerializedName("data")
    public DataLoginResponse dataRegister;
}
