package example.jp.socical.api.request;

import com.android.volley.Response;

import java.io.File;
import java.util.Map;

import example.jp.socical.api.response.UploadImageResponse;
import vn.app.base.api.volley.core.MultiPartRequest;

/**
 * Created by Toi on 10/24/2016.
 */

public class UploadImageRequest extends MultiPartRequest<UploadImageResponse>{

    public UploadImageRequest(int method, String url, Response.ErrorListener listener, Class<UploadImageResponse> mClass, Map<String, String> mHeader, Response.Listener<UploadImageResponse> mListener, Map<String, String> mStringParts, Map<String, File> mFileParts) {
        super(method, url, listener, mClass, mHeader, mListener, mStringParts, mFileParts);
    }
}
