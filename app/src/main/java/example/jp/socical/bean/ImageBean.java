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

    public String publicId;

    public Integer version;

    public Integer width;

    public Integer height;

    public String format;

    public String resourceType;

    public String createdAt;

    public Integer bytes;

    public String type;

    public String url;

    public String secureUrl;

    public String userId;

    public String caption;

    public String location;

    public String lat;

    public String _long;

    public List<String> hashtag = new ArrayList<String>();

    public String id;

    public Boolean isFavourite;
}
