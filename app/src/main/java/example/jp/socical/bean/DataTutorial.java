package example.jp.socical.bean;

/**
 * Created by Hien_BRSE on 12/5/2016.
 */
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataTutorial {

    @SerializedName("tutorial")
    public List<TutorialBean> tutorial = null;

    @SerializedName("user")
    public UserBean user;
}
