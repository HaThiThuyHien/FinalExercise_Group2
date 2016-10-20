package example.jp.socical.bean;

/**
 * Created by HaHien on 10/17/2016.
 */

public class NewBean {

    public String avatar;
    public String tvUser;
    public boolean bFollow;
    public String tvCmt;
    public String tvAddress;
    public boolean bLike;
    public String imgMap;
    public String tvPinMap;

    public NewBean(String avatar, String user, boolean bFollow, String tvAddress, String tvCmt, boolean bLike, String imgMap, String tvPinMap) {
        this.avatar = avatar;
        this.tvUser = user;
        this.bFollow = bFollow;
        this.tvAddress = tvAddress;
        this.tvCmt = tvCmt;
        this.bLike = bLike;
        this.imgMap = imgMap;
        this.tvPinMap = tvPinMap;
    }
}
