package example.jp.socical.bean;

/**
 * Created by HaHien on 10/20/2016.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("_id")
    public String id;

    @SerializedName("token")
    public String token;

    @SerializedName("username")
    public String username;

    @SerializedName("email")
    public String email;

    @SerializedName("avatar")
    public String avatar;

    @SerializedName("create_at")
    public Integer createAt;
}
