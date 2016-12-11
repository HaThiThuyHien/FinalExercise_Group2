package example.jp.socical.api.request;

import com.android.volley.Response;

import java.io.File;
import java.util.Map;

import example.jp.socical.api.response.ProfileUserResponse;
import vn.app.base.api.volley.core.MultiPartRequest;

/**
 * Created by Toi on 12/12/2016.
 */

public class UpdateProfileRequest extends MultiPartRequest<ProfileUserResponse> {

    public UpdateProfileRequest(int method, String url, Response.ErrorListener listener, Class<ProfileUserResponse> mClass, Map<String, String> mHeader, Response.Listener<ProfileUserResponse> mListener, Map<String, String> mStringParts, Map<String, File> mFileParts) {
        super(method, url, listener, mClass, mHeader, mListener, mStringParts, mFileParts);
    }
}
