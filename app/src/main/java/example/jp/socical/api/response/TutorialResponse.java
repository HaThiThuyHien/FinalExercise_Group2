package example.jp.socical.api.response;

/**
 * Created by Hien_BRSE on 12/5/2016.
 */
import com.google.gson.annotations.SerializedName;

import example.jp.socical.bean.DataTutorial;
import vn.app.base.api.response.BaseResponse;

public class TutorialResponse extends BaseResponse{

    @SerializedName("data")
    public DataTutorial dataTutorial;
}
