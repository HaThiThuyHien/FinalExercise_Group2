package example.jp.socical.api.response;

/**
 * Created by Toi on 10/24/2016.
 */
import com.google.gson.annotations.SerializedName;

public class DataLoginResponse {

    @SerializedName("_id")
    public String id;

    @SerializedName("token")
    public String token;

    @SerializedName("username")
    public String username;

    @SerializedName("email")
    public String email;

    @SerializedName("avatar")
    public String avatar;

    @SerializedName("create_at")
    public String createAt;
}
