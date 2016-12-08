package example.jp.socical.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Toi on 10/25/2016.
 */

public class ImageBean {

    @SerializedName("_id")
    public String id;

    @SerializedName("public_id")
    public String publicId;

    @SerializedName("version")
    public Integer version;

    @SerializedName("width")
    public Integer width;

    @SerializedName("height")
    public Integer height;

    @SerializedName("format")
    public String format;

    @SerializedName("resource_type")
    public String resourceType;

    @SerializedName("created_at")
    public String createdAt;

    @SerializedName("bytes")
    public Integer bytes;

    @SerializedName("type")
    public String type;

    @SerializedName("url")
    public String url;

    @SerializedName("secure_url")
    public String secureUrl;

    @SerializedName("user_id")
    public String userId;

    @SerializedName("caption")
    public String caption;

    @SerializedName("location")
    public String location;
    @SerializedName("lat")
    public String lat;

    @SerializedName("long")
    public String _long;

    @SerializedName("hashtag")
    public List<String> hashtag = new ArrayList<String>();

    @SerializedName("is_favourite")
    public Boolean isFavourite;
}
