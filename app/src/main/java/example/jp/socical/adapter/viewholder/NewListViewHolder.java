package example.jp.socical.adapter.viewholder;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
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
import vn.app.base.adapter.viewholder.OnClickViewHolder;
import vn.app.base.imageloader.ImageLoader;
import vn.app.base.util.DebugLog;
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

    public NewListViewHolder(View itemView) {
        super(itemView);
    }

    public void bind (NewsBean newBean) {
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
            ivLike.setImageResource(R.drawable.icon_favorite);
        }else {
            ivLike.setImageResource(R.drawable.icon_no_favourite);
        }
        bLike = newBean.image.isFavourite;

        ImageLoader.loadImage(itemView.getContext(), R.drawable.loading_list_image_220, newBean.image.url, ivPicture);

    }

    @OnClick(R.id.imgAvatar)
    public void clickImagAvatar() {
        //DebugLog.i("select avatar");
    }

    @OnClick(R.id.imgPicture)
    public void clickImgPicture(){
        //DebugLog.i("select imgPicture");
    }

    @OnClick(R.id.btnFollow)
    public void clickBtnFollow(){
        if (bFollow) {
            bFollow = false;
            btnFollow.setBackgroundResource(R.drawable.btnfollow_bg);
            btnFollow.setText("Follow");
        }else {
            bFollow = true;
            btnFollow.setBackgroundResource(R.drawable.btnfollowing_bg);
            btnFollow.setText("Following");
        }
    }

    @OnClick(R.id.imgLike)
    public void clickImgLike(){
        if (bLike) {
            bLike = false;
            ivLike.setImageResource(R.drawable.icon_no_favourite);
        }else {
            bLike = true;
            ivLike.setImageResource(R.drawable.icon_favorite);
        }
    }
}
