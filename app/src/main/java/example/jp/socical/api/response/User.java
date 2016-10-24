package example.jp.socical.api.response;

/**
 * Created by Toi on 10/24/2016.
 */
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("_id")
    public String id;

    @SerializedName("username")
    public String username;

    @SerializedName("avatar")
    public String avatar;

    @SerializedName("is_following")
    public Boolean isFollowing;
}
