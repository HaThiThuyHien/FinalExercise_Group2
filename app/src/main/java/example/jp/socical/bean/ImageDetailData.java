package example.jp.socical.bean;

/**
 * Created by Toi on 11/21/2016.
 */
import com.google.gson.annotations.SerializedName;

public class ImageDetailData {

    @SerializedName("user")
    public UserBean user;

    @SerializedName("comment")
    public String comment;

    @SerializedName("created_at")
    public String createdAt;
}
