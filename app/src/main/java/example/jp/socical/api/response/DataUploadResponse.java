package example.jp.socical.api.response;

/**
 * Created by Toi on 10/24/2016.
 */
import com.google.gson.annotations.SerializedName;

public class DataUploadResponse {

    @SerializedName("user")
    public User user;

    @SerializedName("image")
    public ImageUpload image;
}
