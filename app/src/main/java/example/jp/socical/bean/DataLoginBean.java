package example.jp.socical.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Toi on 10/25/2016.
 */

public class DataLoginBean {
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
    public String createAt;
}
