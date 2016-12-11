package example.jp.socical.adapter.viewholder;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import example.jp.socical.R;
import example.jp.socical.bean.NewsBean;
import example.jp.socical.callback.OnNewItemClick;
import vn.app.base.adapter.viewholder.OnClickViewHolder;
import vn.app.base.imageloader.ImageLoader;
import vn.app.base.util.StringUtil;

/**
 * Created by HaHien on 10/17/2016.
 */

public class NewListViewHolder extends OnClickViewHolder {

    public static final int LAYOUT_ID = R.layout.item_list_news;

    // User
    @BindView(R.id.imgAvatar)
    ImageView ivAvatar;

    @BindView(R.id.txtUser)
    TextView tvUser;

    @BindView(R.id.btnFollow)
    Button btnFollow;

    // Image
    @BindView(R.id.imgPicture)
    ImageView ivPicture;

    @BindView(R.id.txtPinMap)
    TextView tvPinMap;

    @BindView(R.id.txtCaption)
    TextView tvCaption;

    @BindView(R.id.txtHashtag)
    TextView tvHashtag;

    @BindView(R.id.imgLike)
    ImageView ivLike;

    @BindView(R.id.rlPinMap)
    LinearLayout rlPinMap;

    boolean bFollow = false;
    boolean bLike = false;

    NewsBean userProfile;

    FragmentActivity fragmentActivity;

    OnNewItemClick onNewItemClick;

    public NewListViewHolder(View itemView) {
        super(itemView);
        fragmentActivity = (FragmentActivity)itemView.getContext();
    }

    public void bind (NewsBean newBean, OnNewItemClick itemClick) {

        this.userProfile = newBean;

        this.onNewItemClick = itemClick;

        // User
        ImageLoader.loadImage(itemView.getContext(), R.drawable.loading_list_image_220, newBean.user.avatar, ivAvatar);
        StringUtil.displayText(newBean.user.username,tvUser);
        if (newBean.user.isFollowing) {
            btnFollow.setBackgroundResource(R.drawable.btnfollowing_bg);
            btnFollow.setText("Following");
        }else {
            btnFollow.setBackgroundResource(R.drawable.btnfollow_bg);
            btnFollow.setText("Follow");
        }
        bFollow = newBean.user.isFollowing;

        StringUtil.displayText(newBean.image.caption, tvCaption);

        int size = newBean.image.hashtag.size();
        String strHashtag ="";
        for (int i = 0; i < size; i++) {
            strHashtag += newBean.image.hashtag.get(i);
            strHashtag += " ";
        }
        StringUtil.displayText(strHashtag, tvHashtag);
        if (newBean.image.location != "") {
            StringUtil.displayText(newBean.image.location, tvPinMap);
        } else {
            rlPinMap.setVisibility(View.GONE);
        }

        if (newBean.image.isFavourite) {
            ivLike.setImageResource(R.drawable.icon_favourite);
        }else {
            ivLike.setImageResource(R.drawable.icon_no_favourite);
        }
        bLike = newBean.image.isFavourite;

        ImageLoader.loadImage(itemView.getContext(), R.drawable.loading_list_image_220, newBean.image.url, ivPicture);

    }

    @OnClick(R.id.imgAvatar)
    public void clickImagAvatar() {
        if (onNewItemClick != null) {
            onNewItemClick.onAvatarClick(userProfile.user.id);
        }
    }

    @OnClick(R.id.imgPicture)
    public void clickImgPicture(){
        if (onNewItemClick != null) {
            onNewItemClick.onPictureClick(userProfile);
        }
    }

    @OnClick(R.id.btnFollow)
    public void clickBtnFollow(){

        if (onNewItemClick != null) {
            if (bFollow) {
                onNewItemClick.onFollowClick(userProfile.user.id, 0);
            } else {
                onNewItemClick.onFollowClick(userProfile.user.id, 1);
            }
        }
    }

    @OnClick(R.id.imgLike)
    public void clickImgLike(){
        if (onNewItemClick != null) {
            if (bLike) {
                onNewItemClick.onFavouriteClick(userProfile.image.id, 0);
            } else {
                onNewItemClick.onFavouriteClick(userProfile.image.id, 1);
            }
        }
    }

    @OnClick(R.id.txtPinMap)
    public void clickPinMap() {
        if (onNewItemClick != null) {
            onNewItemClick.onPinMapClick(userProfile.image.lat, userProfile.image._long);
        }
    }
}
