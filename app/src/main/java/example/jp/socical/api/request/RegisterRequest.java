package example.jp.socical.api.request;

import com.android.volley.Response;

import java.io.File;
import java.util.Map;

import example.jp.socical.api.response.RegisterResponse;
import vn.app.base.api.volley.core.MultiPartRequest;

/**
 * Created by Toi on 12/10/2016.
 */

public class RegisterRequest extends MultiPartRequest<RegisterResponse>{

    public RegisterRequest(int method, String url, Response.ErrorListener listener, Class<RegisterResponse> mClass, Map<String, String> mHeader, Response.Listener<RegisterResponse> mListener, Map<String, String> mStringParts, Map<String, File> mFileParts) {
        super(method, url, listener, mClass, mHeader, mListener, mStringParts, mFileParts);
    }
}
