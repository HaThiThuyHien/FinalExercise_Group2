package example.jp.socical.api.response;

/**
 * Created by Hien_BRSE on 11/17/2016.
 */

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import example.jp.socical.bean.NewsBean;
import vn.app.base.api.response.BaseResponse;

public class NearbyResponse extends BaseResponse{
    @SerializedName("data")
    public List<NewsBean> data = new ArrayList<NewsBean>();
}
