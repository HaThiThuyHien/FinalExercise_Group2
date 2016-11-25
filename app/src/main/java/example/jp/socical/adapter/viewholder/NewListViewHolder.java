package example.jp.socical.adapter.viewholder;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import example.jp.socical.R;
import example.jp.socical.bean.NewsBean;
import example.jp.socical.callback.OnChangeFavourite;
import example.jp.socical.callback.OnChangeFollow;
import example.jp.socical.callback.OnClickAvatar;
import example.jp.socical.callback.OnClickPicture;
import example.jp.socical.fragment.ImageDetailFragment;
import vn.app.base.adapter.viewholder.OnClickViewHolder;
import vn.app.base.imageloader.ImageLoader;
import vn.app.base.util.DebugLog;
import vn.app.base.util.FragmentUtil;
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

    boolean bFollow = false;
    boolean bLike = false;

    NewsBean userProfile;

    FragmentActivity fragmentActivity;

    OnClickPicture onClickPicture;

    OnChangeFollow onChangeFollow;

    OnChangeFavourite onChangeFavourite;

    OnClickAvatar onClickAvatar;

    public NewListViewHolder(View itemView) {
        super(itemView);
        fragmentActivity = (FragmentActivity)itemView.getContext();
    }

    public void bind (NewsBean newBean, OnClickPicture clickPicture,
                      OnChangeFollow follow,
                      OnChangeFavourite favourite,
                      OnClickAvatar onClickAvatar) {

        this.userProfile = newBean;

        this.onClickPicture = clickPicture;
        this.onChangeFollow = follow;
        this.onChangeFavourite = favourite;
        this.onClickAvatar = onClickAvatar;

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
        StringUtil.displayText(newBean.image.location, tvPinMap);

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
        if (onClickAvatar != null) {
            onClickAvatar.onClickAvatar(userProfile.user.id);
        }
    }

    @OnClick(R.id.imgPicture)
    public void clickImgPicture(){
        if (onClickPicture != null) {
            onClickPicture.callImageDetailFrament(userProfile);
        }
    }

    @OnClick(R.id.btnFollow)
    public void clickBtnFollow(){

        if (onChangeFollow != null) {
            if (bFollow) {
                onChangeFollow.changeFollow(userProfile.user.id, 0);
            } else {
                onChangeFollow.changeFollow(userProfile.user.id, 1);
            }
        }
    }

    @OnClick(R.id.imgLike)
    public void clickImgLike(){
        if (bLike) {
            onChangeFavourite.changeFavourite(userProfile.image.id, 0);
        } else {
            onChangeFavourite.changeFavourite(userProfile.image.id, 1);
        }

    }
}
