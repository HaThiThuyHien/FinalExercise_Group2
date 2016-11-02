package example.jp.socical.bean;

/**
 * Created by Toi on 10/30/2016.
 */

public class ProfileUserHeaderBean {
    public String avatar;
    public String userName;
    public int followCount;
    public int followerCount;
    public int postCount;

    public ProfileUserHeaderBean(String avatar, String userName, int followCount, int followerCount, int postCount) {
        this.avatar = avatar;
        this.userName = userName;
        this.followCount = followCount;
        this.followerCount = followerCount;
        this.postCount = postCount;
    }
}
