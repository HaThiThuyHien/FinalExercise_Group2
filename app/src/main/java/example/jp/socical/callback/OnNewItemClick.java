package example.jp.socical.callback;

import example.jp.socical.bean.NewsBean;

/**
 * Created by Hien_BRSE on 11/29/2016.
 */

public interface OnNewItemClick {

    void onFavouriteClick(String imageId, int favourite);

    void onFollowClick(String userId, int follow);

    void onAvatarClick(String userId);

    void onPictureClick(NewsBean imageProfile);

    void onPinMapClick(String strlat, String strlong);
}
