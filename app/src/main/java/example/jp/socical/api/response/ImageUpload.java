package example.jp.socical.api.response;

/**
 * Created by Toi on 10/24/2016.
 */
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ImageUpload {

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
    public Integer createdAt;

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

    @SerializedName("_id")
    public String id;

    @SerializedName("is_favourite")
    public Boolean isFavourite;
}
