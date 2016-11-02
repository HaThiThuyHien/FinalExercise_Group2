package example.jp.socical.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Toi on 10/25/2016.
 */

public class UserBean {
    public String id;

    public String username;

    public String avatar;

    public Boolean isFollowing;
}
