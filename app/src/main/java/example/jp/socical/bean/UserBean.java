package example.jp.socical.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Toi on 10/25/2016.
 */

public class UserBean{

    @SerializedName("_id")
    public String id;

    @SerializedName("username")
    public String username;

    @SerializedName("avatar")
    public String avatar;

    @SerializedName("is_following")
    public Boolean isFollowing;
}
