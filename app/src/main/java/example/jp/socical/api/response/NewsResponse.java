package example.jp.socical.api.response;

import example.jp.socical.bean.NewsBean;
import vn.app.base.api.response.BaseResponse;

/**
 * Created by Toi on 10/25/2016.
 */
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class NewsResponse extends BaseResponse {
    @SerializedName("data")
    public List<NewsBean> data = new ArrayList<NewsBean>();
}
