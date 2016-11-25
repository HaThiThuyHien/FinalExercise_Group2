package example.jp.socical.bean;

/**
 * Created by Hien_BRSE on 11/22/2016.
 */
import com.google.gson.annotations.SerializedName;

public class ProfileUserBean {

    @SerializedName("_id")
    public String id;

    @SerializedName("username")
    public String username;

    @SerializedName("avatar")
    public String avatar;

    @SerializedName("is_following")
    public Boolean isFollowing;

    @SerializedName("follow")
    public Integer follow;

    @SerializedName("follower")
    public Integer follower;

    @SerializedName("post")
    public Integer post;
}
