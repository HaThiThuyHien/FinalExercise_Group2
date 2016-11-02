package example.jp.socical.api.response;

/**
 * Created by Toi on 10/24/2016.
 */
import com.google.gson.annotations.SerializedName;

import example.jp.socical.bean.ImageBean;
import example.jp.socical.bean.UserBean;

public class DataUploadResponse {

    @SerializedName("user")
    public UserBean user;

    @SerializedName("image")
    public ImageBean image;
}
