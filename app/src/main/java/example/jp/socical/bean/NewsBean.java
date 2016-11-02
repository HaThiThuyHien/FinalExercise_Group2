package example.jp.socical.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Toi on 10/25/2016.
 */

public class NewsBean {

    public UserBean user;

    public ImageBean image;

    public NewsBean(UserBean user, ImageBean image) {
        this.user = user;
        this.image = image;
    }
}
