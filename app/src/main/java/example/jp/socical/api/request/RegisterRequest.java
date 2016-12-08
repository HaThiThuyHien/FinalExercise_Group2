package example.jp.socical.api.request;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import example.jp.socical.api.response.RegisterResponse;
import example.jp.socical.constant.APIConstant;
import vn.app.base.api.volley.core.ObjectApiRequest;
import vn.app.base.api.volley.core.UploadBinaryApiRequest;

/**
 * Created by Toi on 10/24/2016.
 */

public class RegisterRequest extends UploadBinaryApiRequest<RegisterResponse>{

    File fileAvatar;
    String userName;
    String email;
    String pass;

    public RegisterRequest(File fileAvatar, String userName, String email, String pass) {
        this.fileAvatar = fileAvatar;
        this.userName = userName;
        this.email = email;
        this.pass = pass;

        if (fileAvatar != null) {
            Map<String, File> avatar = new HashMap<>();
            avatar.put(APIConstant.AVATAR, fileAvatar);
            setRequestFiles(avatar);
        }
    }

    @Override
    public String getRequestURL() {
        return APIConstant.RIGISTER;
    }

    @Override
    public boolean isRequiredAccessToken() {
        return false;
    }

    @Override
    public Map<String, String> getRequestParams() {
        Map<String,String> params = new HashMap<>();
        params.put(APIConstant.USER_NAME, userName);
        params.put(APIConstant.EMAIL, email);
        params.put(APIConstant.PASS, pass);

        return params;
    }

    @Override
    public Class<RegisterResponse> getResponseClass() {
        return RegisterResponse.class;
    }

    @Override
    public int getMethod() {
        return Request.Method.POST;
    }

    @Override
    public void onRequestSuccess(RegisterResponse response) {
    }

    @Override
    public void onRequestError(VolleyError error) {

    }
}
