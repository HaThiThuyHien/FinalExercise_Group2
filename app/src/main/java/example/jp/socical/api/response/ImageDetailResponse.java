package example.jp.socical.api.response;

/**
 * Created by Toi on 11/21/2016.
 */
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

import example.jp.socical.bean.ImageDetailData;
import vn.app.base.api.response.BaseResponse;

public class ImageDetailResponse extends BaseResponse{

    @SerializedName("status")
    public Integer status;

    @SerializedName("message")
    public String message;

    @SerializedName("data")
    public List<ImageDetailData> data = new ArrayList<ImageDetailData>();


}
