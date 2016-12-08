package example.jp.socical.bean;

/**
 * Created by Hien_BRSE on 12/5/2016.
 */
import com.google.gson.annotations.SerializedName;

public class TutorialBean {
    @SerializedName("title")
    public String title;

    @SerializedName("image")
    public String image;

    @SerializedName("show_avatar")
    public Boolean showAvatar;
}
