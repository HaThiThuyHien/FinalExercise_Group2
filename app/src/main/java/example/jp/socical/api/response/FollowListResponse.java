package example.jp.socical.api.response;

/**
 * Created by Toi on 11/24/2016.
 */
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

import example.jp.socical.bean.FollowDataBean;
import vn.app.base.api.response.BaseResponse;

public class FollowListResponse extends BaseResponse{

    @SerializedName("data")
    public List<FollowDataBean> followDatas = new ArrayList<FollowDataBean>();
}
