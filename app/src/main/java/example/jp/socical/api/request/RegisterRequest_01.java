package example.jp.socical.api.request;

import com.android.volley.Response;

import java.io.File;
import java.util.Map;

import example.jp.socical.api.response.RegisterResponse;
import vn.app.base.api.volley.core.MultiPartRequest;

/**
 * Created by Hien_BRSE on 12/8/2016.
 */

public class RegisterRequest_01 extends MultiPartRequest<RegisterResponse> {

    public RegisterRequest_01(int method, String url, Response.ErrorListener listener, Class<RegisterResponse> mClass, Map<String, String> mHeader, Response.Listener<RegisterResponse> mListener, Map<String, String> mStringParts, Map<String, File> mFileParts) {
        super(method, url, listener, mClass, mHeader, mListener, mStringParts, mFileParts);
    }
}
