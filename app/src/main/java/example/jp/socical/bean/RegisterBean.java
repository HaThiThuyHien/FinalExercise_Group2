package example.jp.socical.bean;

/**
 * Created by HaHien on 10/20/2016.
 */

import com.google.gson.annotations.SerializedName;

public class RegisterBean {

    @SerializedName("status")
    public Integer status;

    @SerializedName("message")
    public String message;

    @SerializedName("data")
    public Data data;
}
